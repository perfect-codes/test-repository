# -*- coding: utf-8 -*-
import urllib2
import urllib
import json
import pymysql as db
import sys

#开发数据库
rds_host = 'rm-2zeu2qjf0b3nnt0s8.mysql.rds.aliyuncs.com'
rds_user = 'ruubypaytest'
rds_password = 'Ruyixing2017'
rds_charset = 'utf8'
dbname = 'ruubypayactivity'
sql = "select id,name from isl_station_water"
updateSql = "update isl_station_water set lng=%s,lat=%s where id=%s"

url = "https://restapi.amap.com/v3/geocode/geo"
constant = {'key': 'f4faaa0ad7f3fb1d51bc0ed627daabb9', 'city': '北京', 'address': '西直门地铁站'}

def get_location (station_name):
    constant['address'] = "%s地铁站" % station_name
    data = urllib.urlencode(constant)
    # print(data)
    req = urllib2.Request(url='%s%s%s' % (url, '?', data))
    res = urllib2.urlopen(req)
    res = res.read()
    # print(res)
    json_object = json.loads(res)
    location_str = json_object['geocodes'][0]['location']
    location_list = location_str.split(',', 2)
    return location_list

def stations():
    try:
        rds_conn = db.connect(host=rds_host, port=3306, user=rds_user, passwd=rds_password, db=dbname, charset='utf8')
        cursor = rds_conn.cursor()
        cursor.execute(sql)
        results = cursor.fetchall()
        for row in results:
            id = row[0]
            station_name = row[1]
            location = get_location(station_name)
            cursor.execute(updateSql, (str(location[0]), str(location[1]), id))
            print("站点%s转换完成(%s,%s)" % (station_name, str(location[0]), str(location[1])))
        rds_conn.commit()

    except db.Error, e:
        print("Error %d: %s" % (e.args[0], e.args[1]))
        sys.exit(1)
    finally:
        if rds_conn:
            rds_conn.close()

if __name__ == '__main__':
    print("-----高德位置转坐标-----")
    # location_list = get_location('牡丹园')
    # print(location_list)
    stations()
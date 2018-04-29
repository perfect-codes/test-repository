#!/usr/bin/python
# -*- coding: UTF-8 -*-
import MySQLdb as db
import sys

sql = "INSERT INTO jf_beta_seed_user (USER_ID) VALUES(%s)"
host = 'rm-2zenh1cz37u3l355q.mysql.rds.aliyuncs.com'
user = 'ruubypaytest'
password = 'Ruyixing2017'
# host = 'localhost'
# user = 'root'
# password = ''
dbName = 'ruubypayactivity'
fileLocation = 'seeduser.txt'

dataFile = open(fileLocation)
try:
    content = dataFile.read()
except IOError:
    print("Error:文件读取错误")
finally:
    if dataFile:
        dataFile.close()
records = content.split(',')
#print(records)
conn = db.connect(host, user, password, dbName, charset="utf8")
cursor = conn.cursor()
error_point = ''
try:
    for record in records:
        print("Info:当前操作USERID: %s" % record)
        cursor.execute(sql,(record,))
    conn.commit()
    print("Success:数据导入成功")
except db.Error, e:
    if conn:
        conn.rollback()
    print("Error %d: %s" % (e.args[0], e.args[1]))
    sys.exit(1)
finally:
    if cursor:
        cursor.close()
    if conn:
        conn.close()

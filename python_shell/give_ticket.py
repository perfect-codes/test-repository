#!/usr/bin/python
# -*- coding: UTF-8 -*-
import pymysql as db
# import MySQLdb as db
import csv
import sys
import time

# 积分池ID
pool_id = 2
# 积分池过期时间
pool_validity_date = '2018-10-27 10:49:07'
# 赠送时间
operate_date = '2018-05-02 00:00:00'
# 赠送积分总数
count = 100
# 交易类型(1-兑换商品，2-获得活动积分, 3-FAM购票，4-二维码过闸，5-积分过期)
trade_type = 4
# 交易描述
trade_remark = '二维码过闸问题协助奖励'
# 数据文件，同级名称
fileLocation = 'ticket.csv'

# 性能
# rds_host = 'rm-2zenh1cz37u3l355q.mysql.rds.aliyuncs.com'
# 开发
rds_host = 'rm-2zeu2qjf0b3nnt0s8.mysql.rds.aliyuncs.com'
rds_user = 'ruubypaytest'
rds_password = 'Ruyixing2017'
rds_charset = 'utf8'

# 性能
drds_host = 'drds1l2m1mygi6pv.drds.aliyuncs.com'
# 开发
#drds_host = 'drdsvl48c1vp9m6q.drds.aliyuncs.com'
drds_user = 'ruubypaymarkting'
drds_password = 'Ruyixing2017'
drds_charset = 'utf8'
# 数据库
db_points = 'ruubypaypoints2'
db_marketing = 'ruubypaymarkting'
# SQL
update_points_user_account_sub_sql = "UPDATE jf_user_account_sub SET BALANCE = BALANCE + %s, UPDATE_DATE = %s WHERE USER_ID = %s AND VALIDITY_DATE = %s AND ORIG_TYPE = 1"
insert_points_user_account_sub_sql = "INSERT INTO jf_user_account_sub (USER_ID, ORIG_TYPE, ORIG_ID, BALANCE, VALIDITY_DATE, STATUS, FREEZE_AMOUNT, CREATE_DATE, UPDATE_DATE) VALUES (%s, 1, %s, %s, %s, 1, 0.00, %s, NULL)"
update_points_user_account_sql = "UPDATE jf_user_account SET BALANCE = BALANCE + %s, UPDATE_DATE = %s WHERE USER_ID = %s"
insert_points_user_account_sql = "INSERT INTO jf_user_account (USER_ID, BALANCE, CREATE_DATE, UPDATE_DATE, FREEZE_AMOUNT) VALUES (%s, %s, %s, NULL, 0.00)"
insert_marketing_account_record_sql = "INSERT INTO jf_user_account_record (USER_ID, TRADE_REMARK, TRADE_TYPE, TRADE_AMOUNT, BALANCE, CREATE_DATE, SHARDING_ID) VALUES (%s, %s, %s, %s, %s, %s, %s)"
update_points_pool_sql = "UPDATE jf_pool SET USED_AMOUNT = USED_AMOUNT + %s WHERE ID = %s"

insert_marketing_user_ticket_sql = "INSERT INTO jf_user_ticket (TICKET_ID,USER_ID,USER_CARD_NO,PRODUCT_TYPE,STATUS,RECEIVE_DATE,RECEIVE_CHANNEL,USED_NUM,SELL_ORDER_NO,SELL_AMOUNT,SHARDING_ID) VALUES ('10001',%s,%s,'35',1,'2018-12-03 18:02:55',1,0,%s,5,%s)"

def main():
    start_time = time.time()
    print('-----开始执行-----')
    data_file = open(fileLocation)
    users = []
    try:
        csv_reader = csv.reader(data_file)
        #rds_conn = db.connect(host=rds_host, port=3306, user=rds_user, passwd=rds_password, db=db_points,
                      #        charset=rds_charset)
        drds_conn = db.connect(host=drds_host, port=3306, user=drds_user, passwd=drds_password, db=db_marketing,
                               charset=drds_charset)
        for row in csv_reader:
            user_id = row[0]
            ticket_no = row[1]
            order_no = int(time.time())
            str1 = str(order_no) + str(user_id)
            for i in range(0,100):
                insert_db_marketing(drds_conn, user_id,ticket_no, str1)
                print('为 用户%s 赠送票%s 成功' % (user_id,count))
        # 结束时间
        end_time = time.time()
        gap = end_time - start_time
        print('-----执行结束,用时: %s秒-----' % (gap,))
    except IOError:
        print("Error:文件读取错误")
        sys.exit(1)
    finally:
        if drds_conn:
            drds_conn.close()
        if data_file:
            data_file.close()


# 计算分片键
def get_shardid(user_id):
    return int(user_id) % 256


# 插入用户账户明细
def insert_db_points(conn, user_id):
    # conn = db.connect(host=rds_host, port=3306, user=rds_user, passwd=rds_password, db=db_points, charset=rds_charset)
    cursor = conn.cursor()
    try:
        rowcount = cursor.execute(update_points_user_account_sub_sql, (count, operate_date, user_id, pool_validity_date))
        # 子账户不存在，执行插入
        if rowcount == 0:
            cursor.execute(insert_points_user_account_sub_sql, (user_id, pool_id, count, pool_validity_date, operate_date))
        rowcount = cursor.execute(update_points_user_account_sql, (count, operate_date, user_id))
        # 账户不存在，执行插入
        if rowcount == 0:
            cursor.execute(insert_points_user_account_sql, (user_id, count, operate_date))
        # 更新积分池
        cursor.execute(update_points_pool_sql, (count, pool_id))

        conn.commit()
    except db.Error, e:
        if conn:
            conn.rollback()
        print("Error %d: %s" % (e.args[0], e.args[1]))
        return False
    finally:
        if cursor:
            cursor.close()
    return True


# 插入用户账户明细
def insert_db_marketing(conn, user_id,user_card_no,order_no):
    shard_id = get_shardid(user_id)
    cursor = conn.cursor()
    try:
        #cursor.execute(insert_marketing_account_record_sql, (user_id, trade_remark, trade_type, count, count, operate_date, shard_id))
        cursor.execute(insert_marketing_user_ticket_sql,(user_id,user_card_no,order_no,shard_id))
        conn.commit()
    except db.Error, e:
        if conn:
            conn.rollback()
        print("Error %d: %s" % (e.args[0], e.args[1]))
    finally:
        if cursor:
            cursor.close()


if __name__ == '__main__':
    main()
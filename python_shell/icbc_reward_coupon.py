#!/usr/bin/python
# -*- coding: UTF-8 -*-
import pymysql as db
import csv
import time
import threadpool
import sys

activityId = 1
paychannel = 1
# 储蓄卡券池ID
depositCouponId = 100006
# 信用卡券池ID
creditCouponId = 100006
# 领取时间
receiveDate = '2018-04-23 00:00:00'
# 有效期截止时间
validityDate = '2019-04-23 00:00:00'
# 奖励项子类型(积分：0，优惠券：1-抵扣券固定金额，2-抵扣券随机金额，3-折扣券，4-免单券，5-免票券)
reward_sub_type = 4

rds_host = 'rm-2zenh1cz37u3l355q.mysql.rds.aliyuncs.com'
rds_user = 'ruubypaytest'
rds_password = 'Ruyixing2017'
rds_charset = 'utf8'

drds_host = 'drds1l2m1mygi6pv.drds.aliyuncs.com'
drds_user = 'ruubypaymarkting'
drds_password = 'Ruyixing2017'
drds_charset = 'utf8'
# 数据库
db_activity = 'ruubypayactivity'
db_points = 'ruubypaypoints'
db_marketing = 'ruubypaymarkting'
# SQL
select_points_coupon_sql = 'SELECT group_concat(ID) FROM (SELECT ID FROM (SELECT ID, RAND() * ID RID FROM jf_coupon_detail WHERE COUPON_ID = %s and STATUS=2 LIMIT 5000 ) t ORDER BY RID LIMIT 10 ) t2'
update_points_coupon_sql = 'UPDATE jf_coupon_detail SET USER_ID = %s ,STATUS = 3, RECEIVE_DATE = now() WHERE STATUS = 2 AND ID IN %s'
insert_marketing_user_coupon_sql = 'INSERT INTO jf_user_coupon(COUPON_ID,USER_ID,COUPON_CODE,AMOUNT_TYPE,AMOUNT,USE_AMOUNT,STATUS,RECEIVE_DATE,VALIDITY_DATE,SHARDING_ID) VALUES (%s,%s,%s,4,0.00,0.00,1,%s,%s,%s)'
insert_activity_reward_sql = 'INSERT INTO bind_paychannel_reward (USER_ID,ACTIVITY_ID,PAY_CHANNEL,BANKCARD_TYPE,REWARD_TYPE,REWARD_SUB_TYPE,REWARD_OBJECT_ID,REWARD_QUANTITY,STATUS,CREATE_DATE) VALUES (%s,%s,3,%s,2,%s,%s,%s,%s,%s)'
# 创建连接
# db_points_conn = db.connect(host=rds_host, port=3306, user=rds_user, passwd=rds_password, db=db_points, charset='utf8')
# db_activity_conn = db.connect(host=rds_host, port=3306, user=rds_user, passwd=rds_password, db=db_points, charset='utf8')
# db_marketing_conn = db.connect(host=rds_host, port=3306, user=rds_user, passwd=rds_password, db=db_points, charset='utf8')

fileLocation = 'icbc_user_data.csv'


def thread_run(userId, cardType):
    # print("当前执行userId: " + userId)
    db_points_conn = db.connect(host=rds_host, port=3306, user=rds_user, passwd=rds_password, db=db_points,
                                charset=rds_charset)
    # print('%s,%s' % (userId, cardType))
    if cardType == '1':
        coupon_id = depositCouponId
    else:
        coupon_id = creditCouponId
    idstr = select_db_points(db_points_conn, userId, coupon_id)
    # print(idstr)
    # 计算sharding_id
    sharding_id = get_shardid(userId)
    ids = idstr.split(',')
    coupons = []
    for id in ids:
        # TODO 增加id转coupon_code逻辑
        coupon_code = str(id)
        coupon = (coupon_id, userId, coupon_code, receiveDate, validityDate, sharding_id)
        coupons.append(coupon)
    # print(coupons)
    db_marketing_conn = db.connect(host=drds_host, port=3306, user=drds_user, passwd=drds_password, db=db_marketing,
                                   charset=drds_charset)
    rowcount = insert_db_marketing(db_marketing_conn, coupons)
    print('用户%s获得%s张券' % (userId, rowcount))
    # 记录赠券成功或失败
    db_activity_conn = db.connect(host=rds_host, port=3306, user=rds_user, passwd=rds_password, db=db_activity,
                                  charset=rds_charset)
    if rowcount == 10:
        status = 1
    else:
        status = 2
    insert_db_activity(db_activity_conn, userId, cardType, coupon_id, rowcount, status)
    time.sleep(2)

def main():
    data_file = open(fileLocation)
    try:
        csv_reader = csv.reader(data_file)
        for row in csv_reader:
            user_id = row[0]
            # 银行卡类型(1 - 储蓄卡，2 - 信用卡)
            card_type = row[1]
            # print('%s,%s' % (userId, cardType))
            if card_type == '1':
                coupon_id = depositCouponId
            else:
                coupon_id = creditCouponId
            thread_run(user_id, coupon_id)
        # seed = ["a", "b", "c", "d", "e", "f"]
        # thread_pool = threadpool.ThreadPool(5)
        # requests = threadpool.makeRequests(thread_run, seed)
        # for req in requests:
        #     thread_pool.putRequest(req)
        #     thread_pool.wait()
    except IOError:
        print("Error:文件读取错误")
    finally:
        if data_file:
            data_file.close()


# 计算分片键
def get_shardid(userId):
    return int(userId) % 256


# 查询随机优惠券,返回id字符串
def select_db_points(conn, userId, couponId):
    cursor = conn.cursor()
    try:
        # TODO 增加券池数量是否充足的判断

        # 查询随机券列表
        cursor.execute(select_points_coupon_sql, couponId)
        idstr = str(cursor.fetchone()[0])
        # print(str(idstr))
        ids = tuple(idstr.split(','))
        # print(ids)
        # TODO 修改券池券状态,完成后打开
        count = cursor.execute(update_points_coupon_sql, (userId, ids))
        # print('修改券状态：%s张' % count)
        conn.commit()
        return idstr
    except db.Error, e:
        if conn:
            conn.rollback()
        print("Error %d: %s" % (e.args[0], e.args[1]))
        # sys.exit(1)
    finally:
        if cursor:
            cursor.close()
        # if conn:
        #     conn.close()


# 插入发券记录表bind_paychannel_reward
def insert_db_marketing(conn, coupons):
    cursor = conn.cursor()
    try:
        rowcount = cursor.executemany(insert_marketing_user_coupon_sql, coupons)
        conn.commit()
        return rowcount
    except db.Error, e:
        if conn:
            conn.rollback()
        print("Error %d: %s" % (e.args[0], e.args[1]))
    finally:
        if cursor:
            cursor.close()


# 插入发券记录表bind_paychannel_reward
def insert_db_activity(conn, userId, cardType, couponId, count, status):
    cursor = conn.cursor()
    try:
        n = cursor.execute(insert_activity_reward_sql,
                           (userId, activityId, cardType, reward_sub_type, couponId, count, status, receiveDate))
        conn.commit()
        return n
    except db.Error, e:
        if conn:
            conn.rollback()
        print("Error %d: %s" % (e.args[0], e.args[1]))
    finally:
        if cursor:
            cursor.close()


if __name__ == '__main__':
    main()

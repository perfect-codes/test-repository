#!/usr/bin/python
# -*- coding: UTF-8 -*-
import pymysql as db
# import MySQLdb as db
import csv
import time
import sys
import threading
# 活动ID
activityId = 99
# 储蓄卡券池ID
coupon_id = 300006
coupon_publish_id = 300006
# 领取时间
receiveDate = '2018-04-23 00:00:00'
# 有效期截止时间
validityDate = '2019-04-23 00:00:00'
# 奖励项子类型(积分：0，优惠券：1-抵扣券固定金额，2-抵扣券随机金额，3-折扣券，4-免单券，5-免票券)
reward_sub_type = 4
# 赠券数量
count = 100
# 数据文件，同级名称
fileLocation = 'icbc_user_data.csv'

# rds_host = 'rm-2zenh1cz37u3l355q.mysql.rds.aliyuncs.com'
rds_host = 'rm-2zeu2qjf0b3nnt0s8.mysql.rds.aliyuncs.com'
rds_user = 'ruubypaytest'
rds_password = 'Ruyixing2017'
rds_charset = 'utf8'

# drds_host = 'drds1l2m1mygi6pv.drds.aliyuncs.com'
drds_host = 'drdsvl48c1vp9m6q.drds.aliyuncs.com'
drds_user = 'ruubypaymarkting'
drds_password = 'Ruyixing2017'
drds_charset = 'utf8'
# 数据库
db_activity = 'ruubypayactivity'
db_marketing = 'ruubypaymarkting'
# SQL
insert_marketing_user_coupon_sql = 'INSERT INTO jf_user_coupon(COUPON_ID,USER_ID,AMOUNT_TYPE,AMOUNT,USE_AMOUNT,STATUS,RECEIVE_DATE,VALIDITY_DATE,SHARDING_ID) VALUES (%s,%s,4,0.00,0.00,1,%s,%s,%s)'
insert_activity_reward_sql = 'INSERT INTO bind_paychannel_reward (USER_ID,ACTIVITY_ID,PAY_CHANNEL,BANKCARD_TYPE,REWARD_TYPE,REWARD_SUB_TYPE,REWARD_OBJECT_ID,REWARD_QUANTITY,STATUS,CREATE_DATE) VALUES (%s,%s,3,%s,2,%s,%s,%s,%s,%s)'
count_marketing_user_coupon_sql = 'select count(ID) from jf_user_coupon WHERE COUPON_ID = %s'
update_marketing_coupon_pub_sql = 'UPDATE jf_coupon_publish set RECEIVE_NUM = %s where ID = %s'
select_activity_reward_exist_sql = 'SELECT ID FROM bind_paychannel_reward WHERE USER_ID = %s LIMIT 1'



def thread_run(users, check_flag):
    db_marketing_conn = db.connect(host=drds_host, port=3306, user=drds_user, passwd=drds_password, db=db_marketing,
                                   charset=drds_charset)
    db_activity_conn = db.connect(host=rds_host, port=3306, user=rds_user, passwd=rds_password, db=db_activity,
                                  charset=rds_charset)
    for user in users:
        coupons = []
        user_id = user[0]
        card_type = user[1]
        sharding_id = user[2]
        for i in range(count):
            coupon = (coupon_id, user_id, receiveDate, validityDate, sharding_id)
            coupons.append(coupon)
        # 插入用户券
        if check_flag == 'y':
            is_exist = has_exist(db_activity_conn, user_id)
        if 'n' == check_flag or ('y' == check_flag and is_exist == 0):
            rowcount = insert_db_marketing(db_marketing_conn, coupons)
            print '线程 %s 为用户 %s发券... %s张' % (threading.current_thread().name, user_id, rowcount)
            if rowcount == 10:
                status = 1
            else:
                status = 2
            # 记录赠券成功或失败
            insert_db_activity(db_activity_conn, user[0], user[1], count, status)
        else:
            print '线程 %s 为用户 %s发券... 已跳过' % (threading.current_thread().name, user_id)
    # print(users)
    # time.sleep(2)
    # 关闭连接
    if db_marketing_conn:
        db_marketing_conn.close()
    if db_activity_conn:
        db_activity_conn.close()


def main():
    # check_flag = raw_input('是否忽略已发放,已发放用户不再发放(y/n)?')
    # if check_flag == 'y':
    #     print('-----开始执行,忽略已发放-----')
    # elif check_flag == 'n':
    #     print('-----开始执行,全部发放-----')
    # else:
    #     print('非法输入，程序退出')
    #     sys.exit(1)
    check_flag = 'n'
    print('-----开始执行-----')
    # 开始时间
    start_time = time.time()
    data_file = open(fileLocation)
    try:
        csv_reader = csv.reader(data_file)
        users0 = []
        users1 = []
        users2 = []
        users3 = []
        users4 = []
        for row in csv_reader:
            user_id = row[0]
            # 银行卡类型(1 - 储蓄卡，2 - 信用卡)
            card_type = row[1]
            sharding_id = get_shardid(user_id)
            user = (user_id, card_type, sharding_id)
            if sharding_id % 5 == 0:
                users0.append(user)
            elif sharding_id % 5 == 1:
                users1.append(user)
            elif sharding_id % 5 == 2:
                users2.append(user)
            elif sharding_id % 5 == 3:
                users3.append(user)
            else:
                users4.append(user)
        # 开启多线程处理
        thread0 = threading.Thread(target=thread_run, name='thread0', args=(users0, check_flag))
        thread1 = threading.Thread(target=thread_run, name='thread1', args=(users1, check_flag))
        thread2 = threading.Thread(target=thread_run, name='thread2', args=(users2, check_flag))
        thread3 = threading.Thread(target=thread_run, name='thread3', args=(users3, check_flag))
        thread4 = threading.Thread(target=thread_run, name='thread4', args=(users4, check_flag))

        thread0.start()
        thread1.start()
        thread2.start()
        thread3.start()
        thread4.start()

        thread0.join()
        thread1.join()
        thread2.join()
        thread3.join()
        thread4.join()

        # 更新券发行数量
        update_db_marketing()
        # 结束时间
        end_time = time.time()
        gap = end_time - start_time
        print('-----执行结束,用时: %s秒-----' % (gap,))
    except IOError:
        print("Error:文件读取错误")
    finally:
        if data_file:
            data_file.close()

# def main():
#     flag = raw_input('是否从上次执行结果开始(y/n)?')
#     if flag == 'y':
#         print('从上次执行结果开始执行')
#     elif flag == 'n':
#         print('重新执行')
#     else:
#         print('非法输入，程序退出')
#         sys.exit(1)
#     print('执行主逻辑')

# 计算分片键
def get_shardid(userId):
    return int(userId) % 256


# 判断记录是否存在
def has_exist(conn, userId):
    cursor = conn.cursor()
    try:
        cursor.execute(select_activity_reward_exist_sql, (userId,))
        conn.commit()
        return cursor.rowcount
    except db.Error, e:
        if conn:
            conn.rollback()
        print("Error %d: %s" % (e.args[0], e.args[1]))
    finally:
        if cursor:
            cursor.close()

# 更新券发行数量
def update_db_marketing():
    conn = db.connect(host=drds_host, port=3306, user=drds_user, passwd=drds_password, db=db_marketing,
                      charset=drds_charset)
    cursor = conn.cursor()
    try:
        cursor.execute(count_marketing_user_coupon_sql, (coupon_id,))
        receive_num = cursor.fetchone()[0]
        cursor.execute(update_marketing_coupon_pub_sql, (receive_num, coupon_publish_id))
        conn.commit()
    except db.Error, e:
        if conn:
            conn.rollback()
        print("Error %d: %s" % (e.args[0], e.args[1]))
    finally:
        if cursor:
            cursor.close()
        if conn:
            conn.close()


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
def insert_db_activity(conn, userId, cardType, count, status):
    cursor = conn.cursor()
    try:
        cursor.execute(insert_activity_reward_sql,
                       (userId, activityId, cardType, reward_sub_type, coupon_id, count, status, receiveDate))
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

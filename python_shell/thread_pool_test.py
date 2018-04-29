#!/usr/bin/python
# -*- coding: UTF-8 -*-
import pymysql as db
#import MySQLdb as db
import csv
import sys
import Queue
import threading
import time

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
count = 10
# 数据文件，同级名称
fileLocation = 'user.csv'
check_flag = 'n'

# 性能
# rds_host = 'rm-2zenh1cz37u3l355q.mysql.rds.aliyuncs.com'
# 开发
rds_host = 'rm-2zeu2qjf0b3nnt0s8.mysql.rds.aliyuncs.com'
rds_user = 'ruubypaytest'
rds_password = 'Ruyixing2017'
rds_charset = 'utf8'

# 性能
# drds_host = 'drds1l2m1mygi6pv.drds.aliyuncs.com'
# 开发
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


class WorkManager(object):
    def __init__(self, list=[], thread_num=2):
        self.work_queue = Queue.Queue()
        self.threads = []
        self.__init_work_queue(list)
        self.__init_thread_pool(thread_num)

    """
        初始化线程
    """

    def __init_thread_pool(self, thread_num):
        for i in range(thread_num):
            self.threads.append(Work(self.work_queue, name='Thread_'+str(i)))

    """
        初始化工作队列
    """

    def __init_work_queue(self, blocks):
        for block in blocks:
            if len(block) == 0:
                continue
            self.work_queue.put(block)

    """
        等待所有线程运行完毕
    """

    def wait_allcomplete(self):
        for item in self.threads:
            if item.isAlive(): item.join()


class Work(threading.Thread):

    def __init__(self, work_queue, name):
        threading.Thread.__init__(self, name=name)
        # 初始化数据库连接
        self.db_marketing_conn = db.connect(host=drds_host, port=3306, user=drds_user, passwd=drds_password, db=db_marketing,
                                       charset=drds_charset)
        self.db_activity_conn = db.connect(host=rds_host, port=3306, user=rds_user, passwd=rds_password, db=db_activity,
                                      charset=rds_charset)
        self.work_queue = work_queue
        self.start()

    # 插入发券记录表bind_paychannel_reward
    def insert_db_marketing(self, coupons):
        conn = self.db_marketing_conn
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
    def insert_db_activity(self, records):
        conn = self.db_activity_conn
        cursor = conn.cursor()
        try:
            cursor.executemany(insert_activity_reward_sql, records)
            conn.commit()
        except db.Error, e:
            if conn:
                conn.rollback()
            print("Error %d: %s" % (e.args[0], e.args[1]))
        finally:
            if cursor:
                cursor.close()

    # 判断记录是否存在
    def has_exist(self, userId):
        conn = self.db_activity_conn
        cursor = conn.cursor()
        try:
            cursor.execute(select_activity_reward_exist_sql, (userId,))
            return cursor.rowcount
        except db.Error, e:
            if conn:
                conn.rollback()
            print("Error %d: %s" % (e.args[0], e.args[1]))
        finally:
            if cursor:
                cursor.close()

    def run(self):
        # 死循环，从而让创建的线程在一定条件下关闭退出
        while True:
            try:
                block = self.work_queue.get(block=False)
                # print ('线程 %s 执行:%s' % (threading.current_thread().name, block))
                # time.sleep(1)
                coupons = []
                records = []
                for user in block:
                    # 模拟处理时间
                    user_id = user[0]
                    card_type = user[1]
                    shard_id = user[2]
                    # 插入用户券
                    if check_flag == 'y':
                        is_exist = self.has_exist(user_id)
                    if 'n' == check_flag or ('y' == check_flag and is_exist == 0):
                        for i in range(count):
                            coupon = (coupon_id, user_id, receiveDate, validityDate, shard_id)
                            coupons.append(coupon)
                        record = (user_id, activityId, card_type, reward_sub_type, coupon_id, count, 1, receiveDate)
                        records.append(record)
                        print('线程 %s 正在为用户 %s 发券' % (threading.current_thread().name, user_id))
                    else:
                        print('线程 %s 跳过用户 %s...' % (threading.current_thread().name, user_id))
                self.insert_db_marketing(coupons)
                self.insert_db_activity(records)
                # print('线程 %s 为 %s个用户发券 %s张' % (threading.current_thread().name, len(records), len(coupons)))
                # # 通知系统任务完成
                # self.work_queue.task_done()
            except:
                print ('线程 %s 结束' % (threading.current_thread().name,))
                # 关闭连接
                self.db_marketing_conn.close()
                self.db_activity_conn.close()
                break


def main():
    start_time = time.time()
    print('-----开始执行-----')
    data_file = open(fileLocation)
    try:
        csv_reader = csv.reader(data_file)
        # 定义列表
        blocks = []
        for i in range(256):
            block = []
            blocks.append(block)
        for row in csv_reader:
            user_id = row[0]
            # 银行卡类型(1 - 储蓄卡，2 - 信用卡)
            card_type = row[1]
            sharding_id = get_shardid(user_id)
            user = (user_id, card_type, sharding_id)
            blocks[sharding_id].append(user)
        work_manager = WorkManager(blocks, 5)
        work_manager.wait_allcomplete()
        # 更新券发行数量
        update_db_marketing()
        # 结束时间
        end_time = time.time()
        gap = end_time - start_time
        print('-----执行结束,用时: %s秒-----' % (gap,))
    except IOError:
        print("Error:文件读取错误")
        sys.exit(1)
    finally:
        if data_file:
            data_file.close()


# 计算分片键
def get_shardid(userId):
    return int(userId) % 256


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


if __name__ == '__main__':
    main()

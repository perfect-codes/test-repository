#!/usr/bin/python
# -*- coding: UTF-8 -*-
import pymysql

host = 'rm-2zenh1cz37u3l355q.mysql.rds.aliyuncs.com'
user = 'ruubypaytest'
password = 'Ruyixing2017'
dbName = 'ruubypayactivity'
# 创建连接
conn = pymysql.connect(host=host, port=3306, user=user, passwd=password, db=dbName, charset='utf8')
# 创建游标
cursor = conn.cursor()
# 执行SQL，并返回收影响行数
effect_row = cursor.execute("select * from jf_beta_seed_user")

print(effect_row)

cursor.close()

conn.close()
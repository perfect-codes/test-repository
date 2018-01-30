/**
 * Project Name:vtest
 * File Name:Condition.java
 * Package Name:com.wanwei.vtest
 * Create Date:2017年9月5日 下午2:34:17
 * Copyright (c) 2017, wanweiyingchuang All Rights Reserved.
 */
package com.perfectcodes.repository;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO 类描述
 * @author xupengfei
 * @version 
 */
public abstract class Condition {

	List<Object> params = new ArrayList<Object>();
	StringBuffer condition = new StringBuffer(" where 1=1");
	private boolean flag = true;
	private String sql = null;
	private String countSql = null;

	public Condition(String sql, String countSql) {
		this.sql = sql;
		this.countSql = countSql;
	}

	public String getSql() {
		dealSql();
		return sql + condition.toString();
	}

	public String getCountSql() {
		dealSql();
		return countSql + condition.toString();
	}

	public Object[] getArgs() {
		return params.toArray();
	}

	private void dealSql() {
		if (flag) {
			initWhere(condition, params);
			flag = false;
		}
	}

	protected abstract void initWhere(StringBuffer condition, List<Object> params);
}

package com.flj.miracle.base.repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用查询条件类
 * @author xupengfei
 * @version 
 */
public abstract class Condition {

	List<Object> params = new ArrayList<Object>();
	StringBuffer condition = new StringBuffer(" where 1=1");
	private boolean flag = true;
	private String sql;
	private String countSql;

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

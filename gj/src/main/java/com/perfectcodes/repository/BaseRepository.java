/**
 * Project Name:vtest
 * File Name:BaseRepository.java
 * Package Name:com.wanwei.vtest
 * Create Date:2017年9月4日 上午8:25:48
 * Copyright (c) 2017, wanweiyingchuang All Rights Reserved.
 */
package com.perfectcodes.repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO 通用数据库交互类
 * @author xupengfei
 * @version 
 */
@Repository
public class BaseRepository {
	
	@Autowired
	private EntityManager em;
	
	/**
	 * 添加
	 * @author xupengfei
	 * @date 2017年9月4日
	 * @param obj
	 * @see
	 */
	@Transactional
	public void save(Object obj){
		em.persist(obj);
	}
	
	/**
	 * 删除
	 * @author xupengfei
	 * @date 2017年9月4日
	 * @param obj
	 * @see
	 */
	@Transactional
	public void delete(Object obj){
		em.remove(obj);
	}
	
	/**
	 * 修改
	 * @author xupengfei
	 * @date 2017年9月4日
	 * @param obj
	 * @see
	 */
	@Transactional
	public void update(Object obj){
		em.merge(obj);
	}
	
	/**
	 * 根据ID查询
	 * @author xupengfei
	 * @date 2017年9月4日
	 * @param clazz 表对应的类类型
	 * @param id ID
	 * @return
	 * @see
	 */
	public <T> T findById(Class<T> clazz,Long id){
		return em.find(clazz,id);
	}
	
	/**
	 * 执行修改、删除类sql
	 * @author xupengfei
	 * @date 2017年9月4日
	 * @param sql SQL语句
	 * @param args 参数列表
	 * @return
	 * @see
	 */
	public int updateBySQL(String sql,String... args){
		Query query = em.createNativeQuery(sql);
		for(int i=0;i<args.length;i++){
			query.setParameter(i+1,args[i]);
		}
		return query.executeUpdate();
	}
	
	/**
	 * 执行查询类sql
	 * @author xupengfei
	 * @date 2017年9月4日
	 * @param sql SQL语句
	 * @param args 参数列表
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> listArrayBySQL(String sql,String... args){
		Query query = em.createNativeQuery(sql);
		for(int i=0;i<args.length;i++){
			query.setParameter(i+1,args[i]);
		}
		return query.getResultList();
	}
	
	/**
	 * 执行查询类sql
	 * @author xupengfei
	 * @date 2017年9月4日
	 * @param sql SQL语句
	 * @param args 参数列表
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> listMapBySQL(String sql,String... args){
		Query query = em.createNativeQuery(sql);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);  
		for(int i=0;i<args.length;i++){
			query.setParameter(i+1,args[i]);
		}
		return query.getResultList();
	}
	
	/**
	 * 执行查询类sql
	 * @author xupengfei
	 * @date 2017年9月4日
	 * @param sql SQL语句
	 * @param countSql count(*)语句
	 * @param pageable 分页参数对象
	 * @param args 参数列表
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	public Page<Map<String,Object>> pageMapBySQL(String sql,String countSql,Pageable pageable,String... args){
		Query query = em.createNativeQuery(sql);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);  
		for(int i=0;i<args.length;i++){
			query.setParameter(i+1,args[i]);
		}
		query.setFirstResult(pageable.getOffset()).setMaxResults(pageable.getPageSize());
		int total = ((BigInteger) em.createNativeQuery(countSql).getSingleResult()).intValue();
		return new PageImpl<Map<String,Object>>(query.getResultList(), pageable,total);
	}
	
	/**
	 * 执行修改、删除类ql
	 * @author xupengfei
	 * @date 2017年9月4日
	 * @param qlStr 查询语句
	 * @param args 参数列表
	 * @return
	 * @see
	 */
	public int updateByHQL(String queryStr,String... args){
		Query query = em.createQuery(queryStr);
		for(int i=0;i<args.length;i++){
			query.setParameter(i+1,args[i]);
		}
		return query.executeUpdate();
	}
	
	/**
	 * 执行查询类jpql
	 * @author xupengfei
	 * @date 2017年9月4日
	 * @param qlStr 查询语句
	 * @param clazz 结果类型
	 * @param args 参数列表
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> listByHQL(String queryStr,Class<T> clazz,String... args){
		Query query = em.createQuery(queryStr,clazz);
		for(int i=0;i<args.length;i++){
			query.setParameter(i+1,args[i]);
		}
		return query.getResultList();
	}
	
	/**
	 * 执行查询类jpql
	 * @author xupengfei
	 * @date 2017年9月4日
	 * @param queryStr 查询语句
	 * @param countStr 统计语句
	 * @param pageable 分页参数对象
	 * @param clazz 结果类型
	 * @param args 参数列表
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	public <T> Page<T> pageByHQL(String queryStr,String countStr,Pageable pageable,Class<T> clazz,String... args){
		Query query = em.createQuery(queryStr,clazz);
		for(int i=0;i<args.length;i++){
			query.setParameter(i+1,args[i]);
		}
		query.setFirstResult(pageable.getOffset()).setMaxResults(pageable.getPageSize());
		Long total = (Long)em.createQuery(countStr).getSingleResult();
		return new PageImpl<T>(query.getResultList(), pageable,total);
	}
	
	
}

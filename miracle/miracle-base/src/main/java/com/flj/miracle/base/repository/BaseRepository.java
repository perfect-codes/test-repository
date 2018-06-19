package com.flj.miracle.base.repository;

import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * TODO 通用数据库交互类
 *
 * @author xupengfei
 */
@Repository
public class BaseRepository {

    @PersistenceContext
    private EntityManager em;

    /**
     * 添加
     *
     * @param obj
     * @author xupengfei
     * @date 2017年9月4日
     * @see
     */
    @Transactional(rollbackFor = Exception.class)
    public void save(Object obj) {
        em.persist(obj);
    }

    /**
     * 删除
     *
     * @param obj
     * @author xupengfei
     * @date 2017年9月4日
     * @see
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(Object obj) {
        em.remove(obj);
    }

    /**
     * 修改
     *
     * @param obj
     * @author xupengfei
     * @date 2017年9月4日
     * @see
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(Object obj) {
        em.merge(obj);
    }

    /**
     * 根据ID查询
     *
     * @param clazz 表对应的类类型
     * @param id    ID
     * @return
     * @author xupengfei
     * @date 2017年9月4日
     * @see
     */
    public <T> T findById(Class<T> clazz, Long id) {
        return em.find(clazz, id);
    }

    /**
     * 执行修改、删除类sql
     *
     * @param sql  SQL语句
     * @param args 参数列表
     * @return
     * @author xupengfei
     * @date 2017年9月4日
     * @see
     */
    @Transactional(rollbackFor = Exception.class)
    public int updateBySQL(String sql, Object... args) {
        Query query = em.createNativeQuery(sql);
        for (int i = 0; i < args.length; i++) {
            query.setParameter(i + 1, args[i]);
        }
        return query.executeUpdate();
    }

    /**
     * 执行查询类sql
     *
     * @param sql  SQL语句
     * @param args 参数列表
     * @return
     * @author xupengfei
     * @date 2017年9月4日
     * @see
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> listArrayBySQL(String sql, Object... args) {
        Query query = em.createNativeQuery(sql);
        for (int i = 0; i < args.length; i++) {
            query.setParameter(i + 1, args[i]);
        }
        return query.getResultList();
    }

    /**
     * sql条件查询
     *
     * @param sql  SQL语句
     * @param sort 排序信息，如果不需要排序设为null
     * @param args 参数列表，没有参数可不填
     * @return
     * @author xupengfei
     * @date 2017年9月5日
     * @see
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> listMapBySQL(String sql, Sort sort, Object... args) {
        Query query = em.createNativeQuery(sql += getOrderStr(sort));
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        for (int i = 0; i < args.length; i++) {
            query.setParameter(i + 1, args[i]);
        }
        return query.getResultList();
    }

    /**
     * sql条件查询
     *
     * @param condition 条件信息
     * @param sort      排序信息，如果不需要排序设为null
     * @return
     * @author xupengfei
     * @date 2017年9月5日
     * @see
     */
    public List<Map<String, Object>> listMapBySQL(Condition condition, Sort sort) {
        return this.listMapBySQL(condition.getSql(), sort, condition.getArgs());
    }

    /**
     * sql条件查询
     *
     * @param sql      SQL语句
     * @param countSql count(*)语句
     * @param pageable 分页信息
     * @param args     参数列表，没有参数可不填
     * @return
     * @author xupengfei
     * @date 2017年9月4日
     * @see
     */
    @SuppressWarnings("unchecked")
    public Page<Map<String, Object>> pageMapBySQL(String sql, String countSql, Pageable pageable, Object... args) {
        sql += getOrderStr(pageable.getSort());
        Query query = em.createNativeQuery(sql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        Query countQuery = em.createNativeQuery(countSql);
        for (int i = 0; i < args.length; i++) {
            query.setParameter(i + 1, args[i]);
            countQuery.setParameter(i + 1, args[i]);
        }
        query.setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize());
        int total = ((BigInteger) countQuery.getSingleResult()).intValue();
        return new PageImpl<Map<String, Object>>(query.getResultList(), pageable, total);
    }

    /**
     * 复杂sql条件查询
     *
     * @param condition 条件信息
     * @param pageable  分页信息
     * @return
     * @author xupengfei
     * @date 2017年9月5日
     * @see
     */
    public Page<Map<String, Object>> pageMapBySQL(Condition condition, Pageable pageable) {
        return this.pageMapBySQL(condition.getSql(), condition.getCountSql(), pageable, condition.getArgs());
    }

    /**
     * 执行修改、删除类ql
     *
     * @param queryStr 查询语句
     * @param args     参数列表
     * @return
     * @author xupengfei
     * @date 2017年9月4日
     * @see
     */
    public int updateByHQL(String queryStr, Object... args) {
        Query query = em.createQuery(queryStr);
        for (int i = 0; i < args.length; i++) {
            query.setParameter(i + 1, args[i]);
        }
        return query.executeUpdate();
    }

    /**
     * 执行查询类jpql
     *
     * @param queryStr 查询语句
     * @param clazz    结果类型
     * @param args     参数列表
     * @return
     * @author xupengfei
     * @date 2017年9月4日
     * @see
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> listByHQL(String queryStr, Class<T> clazz, Object... args) {
        Query query = em.createQuery(queryStr, clazz);
        for (int i = 0; i < args.length; i++) {
            query.setParameter(i + 1, args[i]);
        }
        return query.getResultList();
    }

    /**
     * 执行查询类jpql
     *
     * @param queryStr 查询语句
     * @param countStr 统计语句
     * @param pageable 分页参数对象
     * @param clazz    结果类型
     * @param args     参数列表
     * @return
     * @author xupengfei
     * @date 2017年9月4日
     * @see
     */
    @SuppressWarnings("unchecked")
    public <T> Page<T> pageByHQL(String queryStr, String countStr, Pageable pageable, Class<T> clazz, Object... args) {
        Query query = em.createQuery(queryStr, clazz);
        for (int i = 0; i < args.length; i++) {
            query.setParameter(i + 1, args[i]);
        }
        query.setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize());
        Long total = (Long) em.createQuery(countStr).getSingleResult();
        return new PageImpl<T>(query.getResultList(), pageable, total);
    }

    /**
     * 获取排序
     *
     * @param sort 排序信息
     * @return
     * @author xupengfei
     * @date 2017年9月5日
     * @see
     */
    private String getOrderStr(Sort sort) {
        if (sort != null) {
            StringBuffer orderstr = new StringBuffer(" order by");
            Iterator<Order> orders = sort.iterator();
            while (orders.hasNext()) {
                Order order = orders.next();
                orderstr.append(" " + order.getProperty() + " " + order.getDirection()).append(',');
            }
            return orderstr.deleteCharAt(orderstr.length() - 1).toString();
        }
        return "";
    }

}

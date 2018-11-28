package com.ruubypay.service;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 通用数据库交互类
 *
 * @author xpf
 * @date 2018/10/24 下午10:31
 */
@Service
public class DatabaseService {

    @PersistenceContext
    private EntityManager em;

    public <T> void save(T obj) {
        em.persist(obj);
    }

    public <T> void delete(T obj) {
        em.remove(obj);
    }

    public <T> void update(T obj) {
        em.merge(obj);
    }

    public <T> T findOne(Class<T> clazz, Long id) {
        return em.find(clazz, id);
    }

    public int update(String sql, Object... args) {
        Query query = em.createNativeQuery(sql);
        for (int i = 0; i < args.length; i++) {
            query.setParameter(i + 1, args[i]);
        }
        return query.executeUpdate();
    }

    public List<Object[]> getObjectArrayList(String sql, Object... args) {
        Query query = em.createNativeQuery(sql);
        for (int i = 0; i < args.length; i++) {
            query.setParameter(i + 1, args[i]);
        }
        return query.getResultList();
    }

    public List<Map<String, Object>> getMapList(String sql, Sort sort, Object... args) {
        Query query = em.createNativeQuery(sql += getOrderStr(sort));
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        for (int i = 0; i < args.length; i++) {
            query.setParameter(i + 1, args[i]);
        }
        return query.getResultList();
    }

    public Page<Map<String, Object>> getMapPage(String sql, Pageable pageable, Object... args) {
        sql += getOrderStr(pageable.getSort());
        Query query = em.createNativeQuery(sql);
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        Query countQuery = em.createNativeQuery(getCountSql(sql));
        for (int i = 0; i < args.length; i++) {
            query.setParameter(i + 1, args[i]);
            countQuery.setParameter(i + 1, args[i]);
        }
        query.setFirstResult(pageable.getOffset()).setMaxResults(pageable.getPageSize());
        int total = ((BigInteger) countQuery.getSingleResult()).intValue();
        return new PageImpl<Map<String, Object>>(query.getResultList(), pageable, total);
    }

    public List getBeanList(String sql, Class clazz, Object... args) {
        Query query = em.createNativeQuery(sql,clazz);
        for (int i = 0; i < args.length; i++) {
            query.setParameter(i + 1, args[i]);
        }
        return query.getResultList();
    }

    public List getBeanListSorted(String sql, Class clazz, Sort sort, Object... args) {
        sql += getOrderStr(sort);
        Query query = em.createNativeQuery(sql,clazz);
        for (int i = 0; i < args.length; i++) {
            query.setParameter(i + 1, args[i]);
        }
        return query.getResultList();
    }

    public Page getBeanPage(String sql, Class clazz, Pageable pageable, Object... args) {
        sql += getOrderStr(pageable.getSort());
        Query query = em.createNativeQuery(sql,clazz);
        Query countQuery = em.createNativeQuery(getCountSql(sql));
        for (int i = 0; i < args.length; i++) {
            query.setParameter(i + 1, args[i]);
            countQuery.setParameter(i + 1, args[i]);
        }
        query.setFirstResult(pageable.getOffset()).setMaxResults(pageable.getPageSize());
        int total = ((BigInteger) countQuery.getSingleResult()).intValue();
        List list = query.getResultList();
        return new PageImpl(list, pageable, total);
    }

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

    private String getCountSql(String sql) {
        return "select count(*) from (" + sql + ") countTable";
    }

}

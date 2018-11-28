package com.ruubypay.repository;

import com.ruubypay.domain.Perm;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * Perm的Repository类
 * @Author AutoCoder
 * @Date 2018-6-6 15:12:23
 *
 */
@Repository
public interface PermRepository extends CrudRepository<Perm,Long>,JpaSpecificationExecutor<Perm> {

    List<Perm> findByType(int type);

    List<Perm> findByParentIdOrderByCodeAsc(long parentId);
}
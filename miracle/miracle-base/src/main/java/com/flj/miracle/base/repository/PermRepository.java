package com.flj.miracle.base.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.flj.miracle.base.domain.Perm;
/**
 *
 * Perm的Repository类
 * @Author AutoCoder
 * @Date 2018-6-6 15:12:23
 *
 */
@Repository
public interface PermRepository extends CrudRepository<Perm,Long>,JpaSpecificationExecutor<Perm> {
	
}
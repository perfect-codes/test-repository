package com.ruubypay.repository;

import com.ruubypay.domain.UserRole;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * 用户角色
 * @author xpf
 * @date 2018/11/13 12:23 AM
 */
@Repository
public interface UserRoleRepository extends CrudRepository<UserRole,Long>,JpaSpecificationExecutor<UserRole> {

    void deleteByUserId(long userId);
}
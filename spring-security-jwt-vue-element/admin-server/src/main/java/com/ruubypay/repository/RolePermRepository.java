package com.ruubypay.repository;

import com.ruubypay.domain.RolePerm;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户角色
 * @author xpf
 * @date 2018/11/13 12:23 AM
 */
@Repository
public interface RolePermRepository extends CrudRepository<RolePerm,Long>,JpaSpecificationExecutor<RolePerm> {

    @Query(value = "SELECT p.id,p.code,p.name,if(isnull(rp.id),FALSE ,TRUE) FROM sys_perm p LEFT JOIN sys_role_perm rp ON p.id = rp.perm_id AND rp.role_id = ?1 WHERE p.parent_id = ?2 ORDER BY p.code",nativeQuery = true)
    List<Object[]> findPermsByRoleId(long roleId,long menuId);

    @Query(value = "SELECT p.id,p.code,p.name,if(isnull(rp.id),FALSE ,TRUE) FROM sys_perm p LEFT JOIN sys_role_perm rp ON p.id = rp.perm_id AND rp.role_id = ?1 WHERE p.type = 1",nativeQuery = true)
    List<Object[]> findMenusByRoleId(long roleId);

    void removeByRoleId(long roleId);
}
package com.ruubypay.type2.repository;

import com.ruubypay.type2.domain.SysUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SysUserRoleRepository extends JpaRepository<SysUserRole, Long> {

    List<SysUserRole> findByUserId(Long userId);

    @Query(value = "SELECT GROUP_CONCAT(DISTINCT(p.code)) FROM sys_role_perm rp JOIN sys_perm p ON rp.perm_id = p.id WHERE rp.role_id IN (SELECT ur.role_id FROM sys_user_role ur WHERE ur.user_id = ?)",nativeQuery = true)
    String findUserPermsByUserId(Long userId);
}

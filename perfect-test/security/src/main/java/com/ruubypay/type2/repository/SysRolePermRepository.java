package com.ruubypay.type2.repository;

import com.ruubypay.type2.domain.SysRolePerm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SysRolePermRepository extends JpaRepository<SysRolePerm,Long>{
    @Query("from SysRolePerm rp where rp.role.id = ?1")
    List<SysRolePerm> findByRoleId(Long roleId);
}

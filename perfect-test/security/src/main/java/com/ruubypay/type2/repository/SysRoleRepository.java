package com.ruubypay.type2.repository;

import com.ruubypay.type2.domain.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysRoleRepository extends JpaRepository<SysRole,Long> {

}

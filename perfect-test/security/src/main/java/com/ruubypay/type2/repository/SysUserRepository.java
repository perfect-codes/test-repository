package com.ruubypay.type2.repository;

import com.ruubypay.type2.domain.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserRepository extends JpaRepository<SysUser, Long> {

    SysUser findByUsername(String username);

    SysUser findByUsernameAndPassword(String username, String password);
}

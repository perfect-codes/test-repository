package com.ruubypay.type2.repository;

import com.ruubypay.type2.domain.SysPerm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysPermRepository extends JpaRepository<SysPerm,Long> {
}

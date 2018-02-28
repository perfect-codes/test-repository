package com.perfectcodes.repository;

import com.perfectcodes.domain.Bank;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface BankRepository extends CrudRepository<Bank,Long>,JpaSpecificationExecutor<Bank>{

}

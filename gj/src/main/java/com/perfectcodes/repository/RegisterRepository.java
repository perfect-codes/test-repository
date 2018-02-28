package com.perfectcodes.repository;

import com.perfectcodes.domain.Register;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface RegisterRepository extends CrudRepository<Register,Long>,JpaSpecificationExecutor<Register>{

}

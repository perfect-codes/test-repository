package com.perfectcodes.repository;

import com.perfectcodes.domain.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long>,JpaSpecificationExecutor<User>{

    User findByOpenid(String openid) throws Exception;

}

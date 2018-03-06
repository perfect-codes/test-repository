package com.perfectcodes.repository;

import com.perfectcodes.domain.Seller;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface SellerRepository extends CrudRepository<Seller,Long>,JpaSpecificationExecutor<Seller>{
    Seller findByOpenid(String openid) throws Exception;
}

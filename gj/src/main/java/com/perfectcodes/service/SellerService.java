package com.perfectcodes.service;

import com.perfectcodes.domain.Seller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SellerService {

    void addBean(Seller bean) throws Exception;

    void delBean(Seller bean) throws Exception;

    void updateBean(Seller bean) throws Exception;

    Seller getBean(Long id) throws Exception;

    Page<Seller> pageBean(Seller model, Pageable pageable) throws Exception;

    Seller findByOpenid(String openid) throws Exception;

    List<Seller> findByLeader(Long leader) throws Exception;
}

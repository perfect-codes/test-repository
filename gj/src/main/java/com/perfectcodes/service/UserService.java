package com.perfectcodes.service;

import com.perfectcodes.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    void addBean(User bean) throws Exception;

    void delBean(User bean) throws Exception;

    void updateBean(User bean) throws Exception;

    User getBean(Long id) throws Exception;

    User findByIdcardAndBankCode(String idcard,String bankCode) throws Exception;

    Page<User> pageBean(User model, Pageable pageable) throws Exception;
}

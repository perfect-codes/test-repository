package com.perfectcodes.service;

import com.perfectcodes.domain.Bank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BankService {
    void addBean(Bank bean) throws Exception;

    void delBean(Bank bean) throws Exception;

    void updateBean(Bank bean) throws Exception;

    Bank getBean(Long id) throws Exception;

    List<Bank> findAll(Bank model) throws Exception;

    Page<Bank> pageBean(Bank model, Pageable pageable) throws Exception;

    Bank getBeanByCode(String code) throws Exception;
}

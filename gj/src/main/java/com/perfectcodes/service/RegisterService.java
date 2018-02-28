package com.perfectcodes.service;

import com.perfectcodes.domain.Register;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RegisterService {
    void addBean(Register bean) throws Exception;

    void delBean(Register bean) throws Exception;

    void updateBean(Register bean) throws Exception;

    Register getBean(Long id) throws Exception;

    Page<Register> pageBean(Register model, Pageable pageable) throws Exception;
}

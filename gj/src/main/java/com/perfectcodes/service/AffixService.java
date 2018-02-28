package com.perfectcodes.service;

import com.perfectcodes.domain.Affix;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AffixService {
    void addBean(Affix bean) throws Exception;

    void delBean(Affix bean) throws Exception;

    void updateBean(Affix bean) throws Exception;

    Affix getBean(Long id) throws Exception;

    List<Affix> findAll(Affix model) throws Exception;

    Page<Affix> pageBean(Affix model, Pageable pageable) throws Exception;
}

package com.perfectcodes.service;

import com.perfectcodes.domain.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BannerService {
    void addBean(Banner bean) throws Exception;

    void delBean(Banner bean) throws Exception;

    void updateBean(Banner bean) throws Exception;

    Banner getBean(Long id) throws Exception;

    List<Banner> findAll(Banner model) throws Exception;

    Page<Banner> pageBean(Banner model, Pageable pageable) throws Exception;
}

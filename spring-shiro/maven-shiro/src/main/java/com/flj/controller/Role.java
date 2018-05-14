package com.flj.controller;

import java.io.Serializable;

/**
 * 
 * @author xpf
 * @date 2018/5/12 下午4:51
 */
public class Role implements Serializable {

    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

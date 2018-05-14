package com.flj.controller;

import java.io.Serializable;
/**
 *
 * @author xpf
 * @date 2018/5/12 下午4:51
 */
public class User implements Serializable {

    private Long id;
    private String username;
    private String password;
    private Short status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }
}

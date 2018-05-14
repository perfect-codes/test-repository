package com.flj.controller;

import java.io.Serializable;

/**
 * 
 * @author xpf
 * @date 2018/5/12 下午4:51
 */
public class Perm implements Serializable {

    private Long id;
    /**
     * 父节点ID
     */
    private Long pid;
    private String name;
    private String code;
    private Integer type;
    /**
     * 菜单地址
     */
    private String url;
    /**
     * 图标
     */
    private String icon;

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

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}

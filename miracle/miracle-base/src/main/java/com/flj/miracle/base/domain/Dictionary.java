package com.flj.miracle.base.domain;

import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "sys_dictionary")
public class Dictionary implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 编码
     */
    private String code;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 父节点
     */
    private Long parentId;
    /**
     * 级别
     */
    private Integer level;
    /**
     * 叶子节点
     */
    private Boolean isLeaf;
    private Date createDate;
    private Date updateDate;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Boolean getLeaf() {
        return isLeaf;
    }

    public void setLeaf(Boolean leaf) {
        isLeaf = leaf;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dictionary that = (Dictionary) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(code, that.code) &&
                Objects.equals(status, that.status) &&
                Objects.equals(parentId, that.parentId) &&
                Objects.equals(level, that.level) &&
                Objects.equals(isLeaf, that.isLeaf);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, code, status, parentId, level, isLeaf);
    }
}

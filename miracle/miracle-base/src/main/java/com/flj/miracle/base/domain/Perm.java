package com.flj.miracle.base.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "sys_perm")
public class Perm implements Serializable {

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
     * 类型 1操作权限 2菜单
     */
    private Integer type;
    /**
     * 链接地址，菜单有效
     */
    private String link;
    /**
     * 父节点
     */
    private Long parentId;
    /**
     * 级别
     */
    private Integer level;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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
        Perm perm = (Perm) o;
        return Objects.equals(id, perm.id) &&
                Objects.equals(name, perm.name) &&
                Objects.equals(code, perm.code) &&
                Objects.equals(status, perm.status) &&
                Objects.equals(type, perm.type) &&
                Objects.equals(link, perm.link) &&
                Objects.equals(parentId, perm.parentId) &&
                Objects.equals(level, perm.level);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, code, status, type, link, parentId, level);
    }
}

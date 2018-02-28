package com.perfectcodes.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Affix implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 50)
    private String originName;
    @Column(length = 50)
    private String name;
    @Column(length = 200)
    private String path;
    @Column(length = 1)
    private Integer status;
    @Column(length = 1)
    private Integer objType;
    private Long objId;
    private Date createDate;

    public Long getId() {
        return id;
    }

    public Affix setId(Long id) {
        this.id = id;
        return this;
    }

    public String getOriginName() {
        return originName;
    }

    public Affix setOriginName(String originName) {
        this.originName = originName;
        return this;
    }

    public String getName() {
        return name;
    }

    public Affix setName(String name) {
        this.name = name;
        return this;
    }

    public String getPath() {
        return path;
    }

    public Affix setPath(String path) {
        this.path = path;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public Affix setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Integer getObjType() {
        return objType;
    }

    public Affix setObjType(Integer objType) {
        this.objType = objType;
        return this;
    }

    public Long getObjId() {
        return objId;
    }

    public Affix setObjId(Long objId) {
        this.objId = objId;
        return this;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Affix setCreateDate(Date createDate) {
        this.createDate = createDate;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":")
                .append(id);
        sb.append(",\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"path\":\"")
                .append(path).append('\"');
        sb.append(",\"status\":")
                .append(status);
        sb.append(",\"objType\":")
                .append(objType);
        sb.append(",\"objId\":")
                .append(objId);
        sb.append(",\"createDate\":\"")
                .append(createDate).append('\"');
        sb.append('}');
        return sb.toString();
    }
}

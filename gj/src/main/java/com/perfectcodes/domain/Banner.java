package com.perfectcodes.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Banner implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer type;
    @Column(length = 50)
    private String title;
    private String imgUrl;
    private Integer status;
    private Integer indexOrder;//顺序

    public Long getId() {
        return id;
    }

    public Banner setId(Long id) {
        this.id = id;
        return this;
    }

    public Integer getType() {
        return type;
    }

    public Banner setType(Integer type) {
        this.type = type;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Banner setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Banner setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public Banner setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Integer getIndexOrder() {
        return indexOrder;
    }

    public Banner setIndexOrder(Integer indexOrder) {
        this.indexOrder = indexOrder;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":")
                .append(id);
        sb.append(",\"type\":")
                .append(type);
        sb.append(",\"title\":\"")
                .append(title).append('\"');
        sb.append(",\"imgUrl\":\"")
                .append(imgUrl).append('\"');
        sb.append(",\"status\":")
                .append(status);
        sb.append(",\"indexOrder\":")
                .append(indexOrder);
        sb.append('}');
        return sb.toString();
    }
}

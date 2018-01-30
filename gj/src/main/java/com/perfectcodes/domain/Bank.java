package com.perfectcodes.domain;

import javax.persistence.*;
import java.io.Serializable;
@Entity
public class Bank implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 50)
    private String name;
    private Integer status;
    private String applyUrl;
    private String queryUrl;
    private String logoUrl;
    private String tag;
    private String posterUrl;
    private String applyPostUrl;
    private String htmlUrl;
    private String comment;
    private Integer indexOrder;//顺序

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getApplyUrl() {
        return applyUrl;
    }

    public void setApplyUrl(String applyUrl) {
        this.applyUrl = applyUrl;
    }

    public String getQueryUrl() {
        return queryUrl;
    }

    public void setQueryUrl(String queryUrl) {
        this.queryUrl = queryUrl;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getApplyPostUrl() {
        return applyPostUrl;
    }

    public void setApplyPostUrl(String applyPostUrl) {
        this.applyPostUrl = applyPostUrl;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getIndexOrder() {
        return indexOrder;
    }

    public void setIndexOrder(Integer indexOrder) {
        this.indexOrder = indexOrder;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":")
                .append(id);
        sb.append(",\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"status\":")
                .append(status);
        sb.append(",\"applyUrl\":\"")
                .append(applyUrl).append('\"');
        sb.append(",\"queryUrl\":\"")
                .append(queryUrl).append('\"');
        sb.append(",\"logoUrl\":\"")
                .append(logoUrl).append('\"');
        sb.append(",\"tag\":\"")
                .append(tag).append('\"');
        sb.append(",\"posterUrl\":\"")
                .append(posterUrl).append('\"');
        sb.append(",\"applyPostUrl\":\"")
                .append(applyPostUrl).append('\"');
        sb.append(",\"htmlUrl\":\"")
                .append(htmlUrl).append('\"');
        sb.append(",\"comment\":\"")
                .append(comment).append('\"');
        sb.append(",\"indexOrder\":")
                .append(indexOrder);
        sb.append('}');
        return sb.toString();
    }
}

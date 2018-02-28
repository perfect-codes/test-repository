package com.perfectcodes.common;

import java.io.Serializable;

public class CommonResp implements Serializable{

    private Integer status;
    private String message;
    private Object data;

    public Integer getStatus() {
        return status;
    }

    public CommonResp setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public CommonResp setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public CommonResp setData(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"status\":")
                .append(status);
        sb.append(",\"message\":\"")
                .append(message).append('\"');
        sb.append(",\"data\":")
                .append(data);
        sb.append('}');
        return sb.toString();
    }
}

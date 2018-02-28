package com.perfectcodes.web.vo;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

public class UserSupplyVo implements Serializable {

    @NotBlank(message = "姓名输入不合法")
    private String name;
    @NotBlank(message = "手机号输入不合法")
    @Length(min = 11,max = 11,message = "手机号长度不合法")
    private String telphone;
    @NotBlank
    private String randomCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getRandomCode() {
        return randomCode;
    }

    public void setRandomCode(String randomCode) {
        this.randomCode = randomCode;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"telphone\":\"")
                .append(telphone).append('\"');
        sb.append(",\"randomCode\":\"")
                .append(randomCode).append('\"');
        sb.append('}');
        return sb.toString();
    }
}

package com.perfectcodes.web.vo;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

public class UserVo implements Serializable {

    @NotBlank(message = "姓名错误")
    private String name;
    @NotBlank(message = "身份证号错误")
    @Length(min = 18,max = 18,message = "身份证号错误")
    private String idcard;
    @NotBlank(message = "手机号错误")
    @Length(min = 11,max = 11,message = "手机号错误")
    private String telephone;
//    @NotBlank
//    @Length(min = 4,max = 4,message = "验证码错误")
//    private String randomCode;
    private String seller;
    private String bankCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

//    public String getRandomCode() {
//        return randomCode;
//    }
//
//    public void setRandomCode(String randomCode) {
//        this.randomCode = randomCode;
//    }
    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"idcard\":\"")
                .append(idcard).append('\"');
        sb.append(",\"telephone\":\"")
                .append(telephone).append('\"');
        sb.append(",\"seller\":")
                .append(seller);
        sb.append(",\"bankCode\":\"")
                .append(bankCode).append('\"');
        sb.append('}');
        return sb.toString();
    }
}

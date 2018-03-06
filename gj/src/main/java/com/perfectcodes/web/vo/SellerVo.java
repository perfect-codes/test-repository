package com.perfectcodes.web.vo;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class SellerVo implements Serializable {

    @NotBlank(message = "姓名错误")
    private String name;
    @NotBlank(message = "手机号错误")
    @Length(min = 11,max = 11,message = "手机号错误")
    private String telephone;
    @NotBlank(message = "身份证号错误")
    @Length(min = 18,max = 18,message = "身份证号错误")
    private String idcard;
    @NotBlank(message = "支付宝账户错误")
    private String aliAccount;
    @NotBlank(message = "支付宝昵称错误")
    private String aliNickName;
    private Long leader;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getAliAccount() {
        return aliAccount;
    }

    public void setAliAccount(String aliAccount) {
        this.aliAccount = aliAccount;
    }

    public String getAliNickName() {
        return aliNickName;
    }

    public void setAliNickName(String aliNickName) {
        this.aliNickName = aliNickName;
    }

    public Long getLeader() {
        return leader;
    }

    public void setLeader(Long leader) {
        this.leader = leader;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"telephone\":\"")
                .append(telephone).append('\"');
        sb.append(",\"idcard\":\"")
                .append(idcard).append('\"');
        sb.append(",\"aliAccount\":\"")
                .append(aliAccount).append('\"');
        sb.append(",\"aliNickName\":\"")
                .append(aliNickName).append('\"');
        sb.append(",\"leader\":")
                .append(leader);
        sb.append('}');
        return sb.toString();
    }
}

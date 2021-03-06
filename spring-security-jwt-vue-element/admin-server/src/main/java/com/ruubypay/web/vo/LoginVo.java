package com.ruubypay.web.vo;
/**
 * 登录业务类
 * @author xpf
 * @date 2018/6/6 下午6:59
 */
public class LoginVo {

    private String account;
    private String password;
    private String code;
    private Integer rememberMe;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Integer rememberMe) {
        this.rememberMe = rememberMe;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"account\":\"")
                .append(account).append('\"');
        sb.append(",\"password\":\"")
                .append(password).append('\"');
        sb.append(",\"code\":\"")
                .append(code).append('\"');
        sb.append(",\"rememberMe\":")
                .append(rememberMe);
        sb.append('}');
        return sb.toString();
    }
}

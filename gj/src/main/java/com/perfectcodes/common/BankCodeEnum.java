package com.perfectcodes.common;

public enum BankCodeEnum {

    BANK_SH("sh","上海"),
    BANK_ZX("zx","中信"),
    BANK_JH("jh","交行"),
    BANK_GD_PK("gd_pk","光大普卡"),
    BANK_GD_BJ("gd_bj","光大白金"),
    BANK_XY("xy","兴业"),
    BANK_HX("hx","华夏"),
    BANK_PA("pa","平安"),
    BANK_GZ("gz","广州"),
    BANK_MS("ms","民生"),
    BANK_PF_CRB("pf_crb","浦发成人版"),
    BANK_PF_XYK("pf_xyk","浦发校园卡");

    private String code;
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    BankCodeEnum(String code, String name){
        this.code = code;
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }

}

package com.perfectcodes.common;

public enum ErrorCodeEnum {
    ERROR_UNIQUE("90001","记录已存在"),
    ERROR_STATUS("90002","状态不匹配");
    private String val;
    private String desc;

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    ErrorCodeEnum(String val, String desc){
        this.setVal(val);
        this.setDesc(desc);
    }
}

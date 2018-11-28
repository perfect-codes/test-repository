package com.ruubypay.common;

public enum ErrorCodeEnum {
    SUCCESS(20000,"操作成功"),
    ERROR(9000,"操作失败，请重试"),
    ERROR_UNIQUE(9001,"记录已存在"),
    ERROR_STATUS(9002,"状态不匹配"),
    ERROR_NOT_EXIST(9003,"记录不存在"),
    ERROR_ROLE(9004,"角色或者类型不匹配"),
    ERROR_AUTHORITY(9005,"权限不足"),
    ERROR_NO_LOGIN(9006,"未登录");

    private Integer val;
    private String desc;

    public Integer getVal() {
        return val;
    }

    public void setVal(Integer val) {
        this.val = val;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    ErrorCodeEnum(Integer val, String desc){
        this.setVal(val);
        this.setDesc(desc);
    }
}

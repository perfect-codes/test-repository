package com.perfectcodes.common;

public enum StatusEnum {

    OFFLINE(0,"下线"),
    NORMAL(1,"正常"),
    DELETE(-1,"删除");
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

    StatusEnum(int val, String desc){
        this.val = val;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return getDesc();
    }
}

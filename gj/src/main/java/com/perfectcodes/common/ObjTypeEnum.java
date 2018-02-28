package com.perfectcodes.common;

public enum ObjTypeEnum {

    QR_IMAGE(1,"收款码图片");
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

    ObjTypeEnum(int val, String desc){
        this.val = val;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return getDesc();
    }
}

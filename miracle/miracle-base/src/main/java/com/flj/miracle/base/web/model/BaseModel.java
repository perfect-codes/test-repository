package com.flj.miracle.base.web.model;

/**
 * BaseModel类
 * @Author AutoCoder
 * @Date 2018-6-6 15:12:23
 */
public class BaseModel{
    /**
     * 操作：增加/修改
     */
    protected String oper;

    public String getOper() {
        return oper;
    }

    public void setOper(String oper) {
        this.oper = oper;
    }
}
package com.ruubypay.common;
/**
 * 自定义异常
 * @author xpf
 * @date 2018/6/6 下午6:56
 */
public class GeneralException extends Exception {

    private Integer errorCode;

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public GeneralException(Integer errorCode,String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public GeneralException(Integer errorCode,String message){
        super(message);
        this.errorCode = errorCode;
    }

    public GeneralException(ErrorCodeEnum codeEnum){
        super(codeEnum.getDesc());
        this.errorCode = codeEnum.getVal();
    }
}

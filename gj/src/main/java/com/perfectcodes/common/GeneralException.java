package com.perfectcodes.common;

public class GeneralException extends Exception {

    private String errorCode;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public GeneralException(String errorCode,String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public GeneralException(String errorCode,String message){
        super(message);
        this.errorCode = errorCode;
    }

    public GeneralException(ErrorCodeEnum codeEnum){
        super(codeEnum.getDesc());
        this.errorCode = codeEnum.getVal();
    }
}

package com.ruubypay.common;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
/**
 * 通用返回信息类
 * @author xpf
 * @date 2018/10/28 上午7:21
 */
@Data
@Accessors(chain = true)
public class CommonResp implements Serializable{

    private Integer code;
    private String message;
    private Object data;

    public CommonResp(ErrorCodeEnum codeEnum){
        this.code = codeEnum.getVal();
        this.message = codeEnum.getDesc();
    }
}

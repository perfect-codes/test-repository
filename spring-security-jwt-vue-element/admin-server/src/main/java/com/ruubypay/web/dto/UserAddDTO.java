package com.ruubypay.web.dto;

import lombok.Data;

@Data
public class UserAddDTO {
    /**
     * 手机号
     */
    private String phone;
    /**
     * 姓名
     */
    private String trueName;
    /**
     * 用户名
     */
    private String userName;
}

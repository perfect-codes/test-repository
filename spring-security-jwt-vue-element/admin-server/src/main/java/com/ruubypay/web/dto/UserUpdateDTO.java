package com.ruubypay.web.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserUpdateDTO implements Serializable{
    private Long id;
    private String username;
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 密码
     */
    private String password;
    /**
     * 姓名
     */
    private String truename;
    /**
     * 性别 1男 2女
     */
    private Integer gender;
}

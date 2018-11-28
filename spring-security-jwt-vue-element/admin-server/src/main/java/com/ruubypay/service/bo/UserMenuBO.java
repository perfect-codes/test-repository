package com.ruubypay.service.bo;

import lombok.Data;

import java.util.List;

/**
 * 用户菜单业务类
 * @author xpf
 * @date 2018/11/8 9:49 AM
 */
@Data
public class UserMenuBO {
    private Long id;
    private String code;
    private String name;
    private String link;
    /**
     * 菜单分类
     */
    private Integer type;
}

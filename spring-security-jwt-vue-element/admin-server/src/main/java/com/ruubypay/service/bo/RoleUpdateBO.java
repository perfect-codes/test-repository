package com.ruubypay.service.bo;

import lombok.Data;

/**
 * 角色修改
 * @author xpf
 * @date 2018/11/13 3:15 PM
 */
@Data
public class RoleUpdateBO {
    private Long id;
    private String name;
    private String code;
    private Boolean status;
}

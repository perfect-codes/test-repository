package com.ruubypay.service.bo;

import lombok.Data;

import java.util.List;

/**
 * 角色菜单业务类
 * @author xpf
 * @date 2018/11/8 9:49 AM
 */
@Data
public class RoleMenuBO {
    private Long id;
    private String code;
    private String name;
    private Boolean hadAllPerm;
    private Boolean hadPartPerm;
    private List<MenuPermBO> perms;
    private List<Long> permIds;
    private List<Long> hadPermIds;
}

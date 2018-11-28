package com.ruubypay.web.dto;

import lombok.Data;
/**
 * 添加权限
 * @author xpf
 * @date 2018/10/29 上午9:54
 */
@Data
public class PermAddDTO {
    /**
     * 名称
     */
    private String name;
    /**
     * 编码
     */
    private String code;
    /**
     * 菜单节点
     */
    private Long parentId;
}

package com.ruubypay.web.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 菜单添加
 * @author xpf
 * @date 2018/10/29 上午9:49
 */
@Data
public class MenuAddDTO implements Serializable{
    /**
     * 名称
     */
    private String name;
    /**
     * 编码
     */
    private String code;
    /**
     * 链接地址
     */
    private String link;
    /**
     * 父节点
     */
    private Long parentId;
    /**
     * 级别
     */
    private Integer level;
}

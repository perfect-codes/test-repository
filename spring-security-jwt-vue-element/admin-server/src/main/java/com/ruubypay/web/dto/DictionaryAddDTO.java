package com.ruubypay.web.dto;

import lombok.Data;
/**
 * 添加数据字典
 * @author xpf
 * @date 2018/10/29 上午10:12
 */
@Data
public class DictionaryAddDTO {
    /**
     * 名称
     */
    private String name;
    /**
     * 父节点
     */
    private Long parentId;
}

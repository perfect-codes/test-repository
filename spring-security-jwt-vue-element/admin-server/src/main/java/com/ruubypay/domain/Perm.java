package com.ruubypay.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;
@Data
@Entity
@Table(name = "sys_perm")
@JsonIgnoreProperties({"roles"})
public class Perm implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 编码
     */
    private String code;
    /**
     * 类型 1菜单 2操作权限
     */
    private Integer type;
    /**
     * 链接地址，菜单有效
     */
    private String link;
    /**
     * 父节点
     */
    private Long parentId;
}

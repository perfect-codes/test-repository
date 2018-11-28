package com.ruubypay.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
@Data
@Entity
@Table(name = "sys_dictionary")
public class Dictionary implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 父节点
     */
    private Long parentId;
    private Date createDate;
    private Date updateDate;

}

package com.flj.miracle.base.web.model;

import com.flj.miracle.base.web.model.BaseModel;
import java.util.Date;
import java.util.Date;
/**
 * Role的Model类
 * @Author AutoCoder
 * @Date 2018-6-6 15:12:23
 */
public class RoleModel extends BaseModel{

	/**
	 * id
	 */
	private Long id;
	/**
	 * name
	 */
	private String name;
	/**
	 * code
	 */
	private String code;
	/**
	 * status
	 */
	private Integer status;
	/**
	 * createDate
	 */
	private Date createDate;
	/**
	 * updateDate
	 */
	private Date updateDate;
	
	
	public Long getId(){
		return id;
	}
	public void setId(Long id){
		this.id = id;
	}
	
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	
	public String getCode(){
		return code;
	}
	public void setCode(String code){
		this.code = code;
	}
	
	public Integer getStatus(){
		return status;
	}
	public void setStatus(Integer status){
		this.status = status;
	}
	
	public Date getCreateDate(){
		return createDate;
	}
	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}
	
	public Date getUpdateDate(){
		return updateDate;
	}
	public void setUpdateDate(Date updateDate){
		this.updateDate = updateDate;
	}
}
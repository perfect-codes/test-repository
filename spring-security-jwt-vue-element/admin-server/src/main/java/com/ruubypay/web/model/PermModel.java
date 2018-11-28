package com.ruubypay.web.model;

import java.util.Date;
/**
 * Perm的Model类
 * @Author AutoCoder
 * @Date 2018-6-6 15:12:23
 */
public class PermModel extends BaseModel{

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
	 * type
	 */
	private Integer type;
	/**
	 * link
	 */
	private String link;
	/**
	 * parentId
	 */
	private Long parentId;
	/**
	 * level
	 */
	private Integer level;
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
	
	public Integer getType(){
		return type;
	}
	public void setType(Integer type){
		this.type = type;
	}
	
	public String getLink(){
		return link;
	}
	public void setLink(String link){
		this.link = link;
	}
	
	public Long getParentId(){
		return parentId;
	}
	public void setParentId(Long parentId){
		this.parentId = parentId;
	}
	
	public Integer getLevel(){
		return level;
	}
	public void setLevel(Integer level){
		this.level = level;
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
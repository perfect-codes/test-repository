package com.flj.miracle.base.web.model;

/**
 * Dictionary的Model类
 * @Author AutoCoder
 * @Date 2018-6-6 15:12:23
 */
public class DictionaryModel extends BaseModel{

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
	 * parentId
	 */
	private Long parentId;
	/**
	 * level
	 */
	private Integer level;
	/**
	 * 父级别名称
	 */
	private String parentName;

	
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

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
}
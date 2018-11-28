package com.ruubypay.web.model;

import java.util.Date;
/**
 * User的Model类
 * @Author AutoCoder
 * @Date 2018-6-6 15:12:23
 */
public class UserModel extends BaseModel{

	/**
	 * id
	 */
	private Long id;
	/**
	 * phone
	 */
	private String phone;
	/**
	 * email
	 */
	private String email;
	/**
	 * idNumber
	 */
	private String idNumber;
	/**
	 * password
	 */
	private String password;
	/**
	 * truename
	 */
	private String truename;
	/**
	 * gender
	 */
	private Integer gender;
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
	
	public String getPhone(){
		return phone;
	}
	public void setPhone(String phone){
		this.phone = phone;
	}
	
	public String getEmail(){
		return email;
	}
	public void setEmail(String email){
		this.email = email;
	}
	
	public String getIdNumber(){
		return idNumber;
	}
	public void setIdNumber(String idNumber){
		this.idNumber = idNumber;
	}
	
	public String getPassword(){
		return password;
	}
	public void setPassword(String password){
		this.password = password;
	}
	
	public String getTruename(){
		return truename;
	}
	public void setTruename(String truename){
		this.truename = truename;
	}
	
	public Integer getGender(){
		return gender;
	}
	public void setGender(Integer gender){
		this.gender = gender;
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
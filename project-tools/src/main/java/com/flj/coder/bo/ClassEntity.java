package com.flj.coder.bo;

import java.util.List;

public class ClassEntity{
	/**
	 * �ļ���
	 */
	private String fileName;
	/**
	 * ����
	 */
	private String className;
	/**
	 * ����
	 */
	private String packageName;
	/**
	 * ������
	 */
	private String parentPackageName;
	/**
	 * ��Ŀ·��
	 */
	private String parentPath;
	/**
	 * ���Ա������
	 */
	private List<PropertyEntity> properties;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getParentPath() {
		return parentPath;
	}
	public void setParentPath(String parentPath) {
		this.parentPath = parentPath;
	}
	public String getParentPackageName() {
		return parentPackageName;
	}
	public void setParentPackageName(String parentPackageName) {
		this.parentPackageName = parentPackageName;
	}
	public List<PropertyEntity> getProperties() {
		return properties;
	}
	public void setProperties(List<PropertyEntity> properties) {
		this.properties = properties;
	}
	
}

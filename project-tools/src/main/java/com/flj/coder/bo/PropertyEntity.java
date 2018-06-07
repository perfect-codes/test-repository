package com.flj.coder.bo;

public class PropertyEntity {
	/**
	 * �ֶ�����ȫ��
	 */
	private String fieldTypeFullName;
	/**
	 * �ֶ�������
	 */
	private String fieldTypeName;
	/**
	 * �ֶ���
	 */
	private String fieldName;
	/**
	 * �ֶ�ע��(������)
	 */
	private String fieldComment;

	public String getFieldTypeFullName() {
		return fieldTypeFullName;
	}

	public void setFieldTypeFullName(String fieldTypeFullName) {
		this.fieldTypeFullName = fieldTypeFullName;
	}

	public String getFieldTypeName() {
		return fieldTypeName;
	}

	public void setFieldTypeName(String fieldTypeName) {
		this.fieldTypeName = fieldTypeName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldComment() {
		return fieldComment;
	}

	public void setFieldComment(String fieldComment) {
		this.fieldComment = fieldComment;
	}
}

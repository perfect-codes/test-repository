package ${bean.parentPackageName}.web.model;

import ${bean.parentPackageName}.web.model.BaseModel;
<#list bean.properties as property>
<#if !property.fieldTypeFullName?starts_with('java.lang')>
import ${property.fieldTypeFullName};
</#if>
</#list>
/**
 * ${bean.className}的Model类
 * @Author AutoCoder
 * @Date ${.now?datetime}
 */
public class ${bean.className}Model extends BaseModel{

	<#list bean.properties as property>
	/**
	 * ${property.fieldComment}
	 */
	private ${property.fieldTypeName} ${property.fieldName};
	</#list>
	
	<#list bean.properties as property>
	
	public ${property.fieldTypeName} get${property.fieldName?cap_first}(){
		return ${property.fieldName};
	}
	public void set${property.fieldName?cap_first}(${property.fieldTypeName} ${property.fieldName}){
		this.${property.fieldName} = ${property.fieldName};
	}
	</#list>
}
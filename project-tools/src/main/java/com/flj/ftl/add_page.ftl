<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>max</title>
		<script type="text/javascript" src="../head.js"></script>
		<link href="../css/form.css" rel="stylesheet" type="text/css" />
	</head>

	<body>
		<form id="my-form">
			<div id="tools" class="datagrid-toolbar">
				<a id="subBtn" name="subOk" class="easyui-linkbutton" iconCls="icon-save" plain="true">保存</a>
				<a id="closeBtn" name="backOk" class="easyui-linkbutton" iconCls="icon-close" plain="true">关闭</a>
			</div>
			<p id="errorMsg" style="color:red;margin-top:5px;"></p>
			<table class="formTable">
	<#list bean.properties as property>
		<#if property.fieldTypeName !="id">
			<#if property.fieldTypeName=="String">
				<tr>
					<td width="161" class="tdc1">
						${property.fieldComment}：
					</td>
					<td>
						<input type="text" name="${property.fieldName}" id="${property.fieldName}" data-options="required:false" class="easyui-validatebox">
					</td>
				</tr>
			</#if>
			<#if property.fieldTypeName=="Integer" || property.fieldTypeName=="Long">
				<tr>
					<td width="161" class="tdc1">
						${property.fieldComment}：
					</td>
					<td>
						<input type="text" name="${property.fieldName}" id="${property.fieldName}" class="easyui-numberbox" value="0" data-options="required:false,min:0,max:9999" />
					</td>
				</tr>
			</#if>
			<#if property.fieldTypeName=="Date">
				<tr>
					<td width="161" class="tdc1">
						${property.fieldComment}：
					</td>
					<td>
						<input type="text" name="${property.fieldName}" id="${property.fieldName}" readonly="readonly" class="easyui-validatebox Wdate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
					</td>
				</tr>
			</#if>
		</#if>
	</#list>
			</table>
		</form>
	</body>

</html>
<script>
	function checkFormMe(form) {
		return $('#my-form').form('validate');
	}

	var BaseModel = Backbone.Model.extend({ urlRoot: contextPath + 'manager/${bean.className?lower_case}' });
	var baseModel;
	var p_id = $.getUrlParam("id");
	if(p_id == null) {
		baseModel = new BaseModel();
	} else {
		baseModel = new BaseModel({ id: p_id, i: new Date().getTime() });
	}
	var baseView = new BaseView({ el: $("#my-form"), model: baseModel });
	if(p_id != null) {
		baseView.doLoad();
	}
</script>
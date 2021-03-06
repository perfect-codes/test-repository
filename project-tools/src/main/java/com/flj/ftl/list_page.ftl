<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>max</title>
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
    <meta http-equiv="description" content="this is my page"/>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
	<script type="text/javascript" src="../head.js"></script>	
</head>
<body>
	<div id="tools" class="datagrid-toolbar">
		<a onclick="add();" contextMenu="true:add();" class="easyui-linkbutton" iconCls="icon-add" plain="true">新增</a>
		<a onclick="edit();" contextMenu="true:edit();" class="easyui-linkbutton" iconCls="icon-edit" plain="true">编辑</a> 
		<a onclick="del();" contextMenu="true:del();" class="easyui-linkbutton" iconCls="icon-cancel" plain="true">删除</a>
		<a onclick="$('#search').toggle();" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
    </div>
    <div id="search" class="datagrid-toolbar" style="display:none;">
		<table>
			<tr>
		<#list bean.properties as property>
			<#if property.fieldTypeName == "String" && property.fieldName != "status">
				<td align="right">${property.fieldComment}：</td>
				<td align="left"><input name="${property.fieldName}" /></td>
			</#if>
			<#if property.fieldName == "status">
				<td align="right">状态：</td>
				<td align="left">
					<select name="status"> 
						<option value="">==全部==</option>
					</select>
				</td>
			</#if>
		</#list>
				<td>
					<a class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="query();">确定</a>
					<a class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="requery();">清空</a>
				</td>
			</tr>
		</table>
    </div>
	<table id="list_data" ></table>
</body>
</html>
<script>

	$('#list_data').datagrid({
		url:contextPath+"manager/${bean.className?lower_case}s?i="+new Date().getTime(),
		fitColumns:true,
		singleSelect:false,
		rownumbers:true,
		pagination:true,
		sortName:'id',
		sortOrder:'desc',
		method:'POST',
		striped:true,//隔行变色
		//onRowContextMenu: onRowContextMenu,//右键菜单
		//onDblClickRow:dbClick,//行的双击事件
		columns:[[
	<#list bean.properties as property>
		<#if property.fieldName == "id">
			{field:'id',checkbox:true},
		</#if>
		<#if property.fieldName != "id">
			{field:'${property.fieldName}',title:'${property.fieldComment}',width:100}<#if property_has_next>,</#if>
		</#if>
	</#list>
		]]
	});
	
	//状态
	function statusRenderer(value, row, index) {
		switch (value) {
			case 1:
				return '<font style="color:red">已使用</font>';
			case 0:
				return '<font style="color:green">未使用</font>';
			default:
				return ''
		}
	}
	//添加的方法
	function add() {
		parent.addTabs("新增", contextPath + "system/${bean.className?lower_case}/${bean.className?lower_case}_add.html?_pfrid=" + $.getUrlParam("_frid"));
	}
	//修改的方法
	function edit() {
		var id = getId('#list_data', 'data');//获取选中的行
		if (id == "") {
			return;
		}
		parent.addTabs("编辑-" + id, contextPath + "system/${bean.className?lower_case}/${bean.className?lower_case}_add.html?id=" + id + "&_pfrid="	+ $.getUrlParam("_frid"));
	}
	//行的双击事件
	function dbClick(rowIndex, rowData) {
		parent.addTabs("编辑-" + rowData['id'], contextPath
				+ "system/${bean.className?lower_case}/${bean.className?lower_case}_add.html?id=" + rowData['id'] + "&_pfrid=" + $.getUrlParam("_frid"));
	}
	
	//删除事件
	function del(){
		var ids = getIds('#list_data','data');//获取选中的行ids
		if(ids == ""){
			return;
		}
		$.messager.confirm('提示','确定要删除吗?',function(r){
			if(r){
				max_deletesData(contextPath+'manager/${bean.className?lower_case}/deletes',ids);
			}
		});
	}

	//查询
	function query() {
		searchGo(contextPath + "manager/${bean.className?lower_case}s?i=" + new Date().getTime(),"#search", "#list_data");
	}
	//清空
	function requery() {
		clearGo(contextPath + "manager/${bean.className?lower_case}s?i=" + new Date().getTime(),"#search", "#list_data");
	}
	
</script>
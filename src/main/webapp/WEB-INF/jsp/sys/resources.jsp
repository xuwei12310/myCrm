<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/tag.jsp"%>
<%
  request.setAttribute("resourceIdentity", "sys:resources");
%>
<!DOCTYPE html>
<html>
<head>
  <title>资源列表</title>
  <base href="${ctx}">
  <meta http-equiv="content-type" content="text/html; charset=utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <%@include file="/WEB-INF/jsp/common/css.jsp"%>
  <script type="text/javascript">
		var ctx = "${ctx}";
		var resourceName = "资源";
		var resourcePath = "/sys/resources";
  </script>
  <%@include file="/WEB-INF/jsp/common/js.jsp"%>
  <script type="text/javascript" src="${ctx}/resources/js/common/treeCRUD.js"></script>
  <script type="text/javascript" src="${ctx}/resources/js/sys/resources.js"></script>
  <script type="text/javascript">
	$(function () {
	  resources.init();
	})
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'west',border:false,split:true" style="width:150px;">
		<ul id="tree"></ul>
	</div>
	<div data-options="region:'center',border:false"  class="easyui-layout">
		<div data-options="region:'north',border:false" class="searchDiv">
			<form id="searchForm" method="post">
				<input class="easyui-textbox" name="name" style="width:150px" data-options="label:'名称',labelWidth:50">
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="searchBtn">查询</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" id="clearBtn">清空</a>
			</form>
		</div>
		<div data-options="region:'center',border:false">
			<table id="list"></table>
		</div>
	</div>
	<div id="tb">
		<%@include file="/WEB-INF/jsp/common/btn.jsp"%>
	</div>
	<div id="editDialog">
		<form id="editForm" method="post">
			<input type="hidden" name="id" id="id">
			<input type="hidden" name="parentId" id="parentId">
			<table >
				<tr>
					<td class="tdFirstTitle">父节点名称</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="parentName" style="width:100%" data-options="required:true,labelAlign:'right',readonly:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">子节点名称</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" name="name" id="name" style="width:100%" data-options="required:true,labelAlign:'right'">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">标识</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" name="identity" style="width:100%" data-options="required:true,labelAlign:'right'">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">URL地址</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" name="url" style="width:100%" data-options="labelAlign:'right'">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">图标</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" name="icon" style="width:100%" data-options="labelAlign:'right'">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">状态</td>
					<td class="tdFirstContent">
						<input class="easyui-combobox" name="status" id ="status" style="width:100%" data-options="required:true,labelAlign:'right'">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">资源类型</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" name="resourcesType" id="resourcesType" style="width:100%" data-options="required:true,labelAlign:'right'">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">资源类型</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" name="isCRUD" id="isCRUD" style="width:100%" data-options="required:true,labelAlign:'right'">
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>
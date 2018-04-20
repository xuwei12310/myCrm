<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/tag.jsp"%>
<%
  request.setAttribute("resourceIdentity", "sys:role");
%>
<!DOCTYPE html>
<html>
<head>
  <title>职务与权限</title>
  <base href="${ctx}">
  <meta http-equiv="content-type" content="text/html; charset=utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <%@include file="/WEB-INF/jsp/common/css.jsp"%>
  <script type="text/javascript">
		var ctx = "${ctx}";
		var resourceName = "职务";
		var resourcePath = "/sys/role";
  </script>
  <%@include file="/WEB-INF/jsp/common/js.jsp"%>
  <script type="text/javascript" src="${ctx}/resources/js/common/crud.js"></script>
  <script type="text/javascript" src="${ctx}/resources/js/sys/role.js"></script>
  <script type="text/javascript">
 	$(document).ready(function () {
		role.init();
	})
  </script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="searchDiv">
		<form id="searchForm" method="post">
			<input class="easyui-textbox" name="name" style="width:150px" data-options="label:'名称',labelWidth:50">
			<input class="easyui-textbox" name="role" style="width:150px" data-options="label:'标识',labelWidth:50">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="searchBtn">查询</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" id="clearBtn">清空</a>
		</form>
	</div>
	<div data-options="region:'center'">
		<table id="list" data-options="border:false"></table>
	</div>
	<div id="tb">
		<%@include file="/WEB-INF/jsp/common/btn.jsp"%>
		<shiro:hasPermission name = "${resourceIdentity}:resources">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-resources',plain:true" id="resourcesBtn">权限设置</a>
		</shiro:hasPermission>
	</div>
	<div id="editDialog">
		<form id="editForm" method="post" style="padding:5px">
			<input type="hidden" name="id">
			<table >
				<tr>
					<td class="tdFirstTitle">名称</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" name="name" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdTitle">标识</td>
					<td class="tdContent">
						<input class="easyui-textbox" name="role" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdTitle">备注</td>
					<td class="tdContent">
						<input class="easyui-textbox" name="note" style="width:100%">
					</td>
				</tr>
				<tr>
					<td class="tdTitle">状态</td>
					<td class="tdContent">
						<input class="easyui-combobox" name="status" id="status" style="width:100%" data-options="required:true,editable:false">
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<div id="resourcesDialog">
		<div id="resourcesTree"></div>
	</div>
</body>
</html>
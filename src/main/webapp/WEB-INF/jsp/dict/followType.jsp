<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/tag.jsp"%>
<%
  request.setAttribute("resourceIdentity", "dict:followType");
%>
<!DOCTYPE html>
<html>
<head>
  <title>跟进方式</title>
  <base href="${ctx}">
  <meta http-equiv="content-type" content="text/html; charset=utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <%@include file="/WEB-INF/jsp/common/css.jsp"%>
  <script type="text/javascript">
		var ctx = "${ctx}";
		var resourceName = "跟进方式";
		var resourcePath = "/dict/followType";
  </script>
  <%@include file="/WEB-INF/jsp/common/js.jsp"%>
  <script type="text/javascript" src="${ctx}/resources/js/common/crud.js"></script>
  <script type="text/javascript" src="${ctx}/resources/js/dict/followType.js"></script>
  <script type="text/javascript">
	$(function () {
		followType.init();
	})
  </script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="searchDiv">
		<form id="searchForm" method="post">
			<!-- <input class="easyui-textbox" name="code" style="width:180px" data-options="label:'编号',labelWidth:70"> -->
			<input class="easyui-textbox" name="name" style="width:180px" data-options="label:'名称',labelWidth:70">
			<select class="easyui-combobox"  id="statu" name="status" style="width:180px" data-options="label:'状态',labelWidth:70,editable:false">
						    <option value="1">启用</option>  
						    <option value="0">禁用</option>  
			 </select>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="searchBtn">查询</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" id="clearBtn">清空</a>
		</form>
	</div>
	<div data-options="region:'center'">
		<table id="list" data-options="border:false"></table>
	</div>
	<div id="tb">
		<%@include file="/WEB-INF/jsp/common/btn.jsp"%>
	    <shiro:hasPermission name = "${resourceIdentity}:up">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-up',plain:true" id="upBtn">上移</a>
		</shiro:hasPermission>
		<shiro:hasPermission name = "${resourceIdentity}:down">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-down',plain:true" id="downBtn">下移</a>
		</shiro:hasPermission>
	</div>
 	<div id="editDialog">
		<form id="editForm" method="post">
			<input type="hidden" name="id">
			<table class="contentTb">
				<tr>
					<td class="tdTitle">名称：</td>
					<td class="tdContent">
						<input class="easyui-textbox contentInput" name="name"  data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdTitle">状态：</td>
					<td class="tdContent">
						<input class="easyui-combobox contentInput" id="status" name="status"  data-options="required:true,editable:false">
					</td>
				</tr>
				<tr>
					<td class="tdTitle">备注：</td>
					<td class="tdContent">
						<input class="easyui-textbox contentInput" name="note"  >
					</td>
				</tr>
			</table>
		</form>
	</div>

</body>
</html>
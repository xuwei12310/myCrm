<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/tag.jsp"%>
<%
  request.setAttribute("resourceIdentity", ":organization");
%>
<!DOCTYPE html>
<html>
<head>
  <title>列表</title>
  <base href="${ctx}">
  <meta http-equiv="content-type" content="text/html; charset=utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <%@include file="/WEB-INF/jsp/common/css.jsp"%>
  <script type="text/javascript">
		var ctx = "${ctx}";
		var resourceName = "机构";
		var resourcePath = "/admin/organization";
  </script>
  <%@include file="/WEB-INF/jsp/common/js.jsp"%>
  <script type="text/javascript" src="${ctx}/resources/js/common/crud.js"></script>
  <script type="text/javascript" src="${ctx}/resources/js/admin/organization.js"></script>
  <script type="text/javascript">
	$(function () {
		organization.init();
	})
  </script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="searchDiv">
		<form id="searchForm" method="post">
					<input class="easyui-textbox" name="organizationName" style="width:180px" data-options="label:'机构名称',labelWidth:70">
					<input class="easyui-textbox" name="orgType" style="width:180px" data-options="label:'机构类型(1:公司，0:部门)',labelWidth:70">
					<input class="easyui-textbox" name="parentId" style="width:180px" data-options="label:'父机构',labelWidth:70">
					<input class="easyui-textbox" name="parentIds" style="width:180px" data-options="label:'父路径',labelWidth:70">
										<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="searchBtn">查询</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" id="clearBtn">清空</a>
		</form>
	</div>
	<div data-options="region:'center'">
		<table id="list" data-options="border:false"></table>
	</div>
	<div id="tb">
		<%@include file="/WEB-INF/jsp/common/btn.jsp"%>
	</div>
	<div id="editDialog">
		<form id="editForm" method="post">
			<input type="hidden" name="id">
            <table >
				<tr>
					<td class="tdFirstTitle">机构名称</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="organizationName" name="organizationName" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">机构类型(1:公司，0:部门)</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="orgType" name="orgType" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">父机构</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="parentId" name="parentId" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">父路径</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="parentIds" name="parentIds" style="width:100%" data-options="required:true">
					</td>
				</tr>
							            </table>
		</form>
	</div>
</body>
</html>
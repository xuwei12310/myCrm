<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/tag.jsp"%>
<%
  request.setAttribute("resourceIdentity", ":dict");
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
		var resourceName = "字典";
		var resourcePath = "/admin/dict";
  </script>
  <%@include file="/WEB-INF/jsp/common/js.jsp"%>
  <script type="text/javascript" src="${ctx}/resources/js/common/crud.js"></script>
  <script type="text/javascript" src="${ctx}/resources/js/admin/dict.js"></script>
  <script type="text/javascript">
	$(function () {
		dict.init();
	})
  </script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="searchDiv">
		<form id="searchForm" method="post">
					<input class="easyui-textbox" name="dictType" style="width:180px" data-options="label:'类型【会议人员类型participants_category单位类型company_category】',labelWidth:70">
					<input class="easyui-textbox" name="code" style="width:180px" data-options="label:'编号',labelWidth:70">
					<input class="easyui-textbox" name="name" style="width:180px" data-options="label:'名称',labelWidth:70">
					<input class="easyui-textbox" name="status" style="width:180px" data-options="label:'状态1启用0禁用',labelWidth:70">
					<input class="easyui-textbox" name="nameEn" style="width:180px" data-options="label:'名称_英文',labelWidth:70">
					<input class="easyui-textbox" name="isSys" style="width:180px" data-options="label:'是否系统内置【是1、否0】',labelWidth:70">
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
					<td class="tdFirstTitle">类型【会议人员类型participants_category单位类型company_category】</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="dictType" name="dictType" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">编号</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="code" name="code" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">名称</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="name" name="name" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">状态1启用0禁用</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="status" name="status" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">名称_英文</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="nameEn" name="nameEn" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">是否系统内置【是1、否0】</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="isSys" name="isSys" style="width:100%" data-options="required:true">
					</td>
				</tr>
							            </table>
		</form>
	</div>
</body>
</html>
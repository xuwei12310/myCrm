<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/tag.jsp"%>
<%
  request.setAttribute("resourceIdentity", "dict:plot");
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
		var resourceName = "字典小区";
		var resourcePath = "/dict/plot";
  </script>
  <%@include file="/WEB-INF/jsp/common/js.jsp"%>
  <script type="text/javascript" src="${ctx}/resources/js/common/crud.js"></script>
  <script type="text/javascript" src="${ctx}/resources/js/dict/plot.js"></script>
  <script type="text/javascript">
	$(function () {
		plot.init();
	})
  </script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="searchDiv">
		<form id="searchForm" method="post">
					<input class="easyui-textbox" name="area.areaName" id="areaName" style="width:180px" data-options="label:'区/县',labelWidth:70">
					<input type="hidden" name="area.id" id ="areaId">
					<a href="javascript:void(0)" class="easyui-linkbutton  selectAreaBtn" data-options="iconCls:'icon-search'" id="selectAreaBtn" >选择</a>
					<input class="easyui-textbox" name="plotName" style="width:180px" data-options="label:'小区名称',labelWidth:70">
					<input class="easyui-textbox" name="status" id="status" style="width:180px" data-options="label:'状态',labelWidth:70">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="searchBtn">查询</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" id="clearBtn">清空</a>
		</form>
	</div>
	<div data-options="region:'center'">
		<table id="list" data-options="border:false"></table>
	</div>
	<div id="tb">
		<%@include file="/WEB-INF/jsp/common/btn.jsp"%>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-up',plain:true" id="upBtn">上移</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-down',plain:true" id="downBtn">下移</a>
	</div>
	<div id="selectAreaDialog" >
	   <form id="searchAreaForm" method="post" style="margin-bottom:7px;margin-top:7px; margin-left:5px;">
	    <input class="easyui-textbox" name="areaName" id="areaName" style="width:180px" data-options="label:'区/县',labelWidth:70">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="searchAreaBtn">查询</a>
		</form>
		<table id="areaList" data-options="border:false"></table>
	</div>
	<div id="editDialog">
		<form id="editForm" method="post">
			<input type="hidden" name="id">
            <table >
				<tr>
					<td class="tdFirstTitle">区/县</td>
					<td class="tdFirstContent">
					    <input type="hidden" id="areaId" name="area.id" >
						<input class="easyui-textbox" name="area.areaName" id="areaName" style="width:150px" data-options="required:true">
						<a href="javascript:void(0)" class="easyui-linkbutton selectAreaBtn" data-options="iconCls:'icon-search'"  id="selectAreaBtn_">选择</a>
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">小区名称</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" name="plotName" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">状态</td>
					<td class="tdFirstContent">
						<select id="status" class="easyui-combobox" name="status" style="width:100px">
					         <option value="1">启用</option>
    						 <option value="0">禁用</option>
					    </select>
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">备注</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" name="note" style="width:100%">
					</td>
				</tr>
				</table>
		</form>
	</div>
</body>
</html>
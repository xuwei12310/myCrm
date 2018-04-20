<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/tag.jsp"%>
<%
  request.setAttribute("resourceIdentity", "dict:product");
%>
<!DOCTYPE html>
<html>
<head>
  <title>产品</title>
  <base href="${ctx}">
  <meta http-equiv="content-type" content="text/html; charset=utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <%@include file="/WEB-INF/jsp/common/css.jsp"%>
  <script type="text/javascript">
		var ctx = "${ctx}";
		var resourceName = "产品";
		var resourcePath = "/dict/product";
  </script> 
  <%@include file="/WEB-INF/jsp/common/js.jsp"%>
  <script type="text/javascript" src="${ctx}/resources/js/common/crud.js"></script>
  <script type="text/javascript" src="${ctx}/resources/js/dict/product.js"></script>
  <script type="text/javascript">
	$(function () {
		product.init();
	})
  </script>
  <style> 
	.spans{ color:#FF0000} 
</style>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="searchDiv">
		<form id="searchForm" method="post">
			<!-- <input class="easyui-textbox" name="code" style="width:180px" data-options="label:'编号',labelWidth:70"> -->
			<input class="easyui-textbox" name="name" style="width:180px" data-options="label:'产品名称',labelWidth:70">
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
		<shiro:hasPermission name = "${resourceIdentity}:config">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-resources',plain:true" id="config">产品进度设置</a>
		</shiro:hasPermission>
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
					<td class="tdTitle">产品名称：</td>
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
						<input class="easyui-textbox contentInput" name="note" >
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="configDialog" style="display: none">
		<!-- <h2 id="bank" align="center"></h2> -->
		<div class="easyui-layout" data-options="fit:true">
		<div id="tb2">
		<shiro:hasPermission name = "${resourceIdentity}:add">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" id="addScheduleBtn">添加</a>
		</shiro:hasPermission>
		<shiro:hasPermission name = "${resourceIdentity}:modif">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" id="updateScheduleBtn">修改</a>
		</shiro:hasPermission>
		<shiro:hasPermission name = "${resourceIdentity}:del">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" id="deleteScheduleBtn">删除</a>
		</shiro:hasPermission>
	    <shiro:hasPermission name = "${resourceIdentity}:upper">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-up',plain:true" id="upScheduleBtn">上移</a>
		</shiro:hasPermission>
		<shiro:hasPermission name = "${resourceIdentity}:below">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-down',plain:true" id="downScheduleBtn">下移</a>
		</shiro:hasPermission>
		</div>
		<!--  <div data-options="region:'north',border:false" class="searchDivSchedule">
		  <form id="searchFormSchedule" method="post">
			<input class="easyui-textbox" name="scheduleName" style="width:200px" data-options="label:'产品进度名称',labelWidth:100">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="searchScheduleBtn">查询</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" id="clearScheduleBtn">清空</a>
		  </form>
	    </div>  -->
		<div data-options="region:'center'">
			<table id="ScheduleList" data-options="border:false"></table>
		</div>
		</div>
	</div>
	<div id="editScheduleDialog" style="display: none">
		<form id="editScheduleForm" method="post">
			<input type="hidden" name="id">
			<input type="hidden" id="productId" name="dict.id">
			<table class="contentTb">
				<tr>
					<td class="tdTitle" style="width: 100px"><span class="spans">*</span>产品进度名称：</td>
					<td class="tdContent">
						<input type="text" class="easyui-textbox contentInput" name="scheduleName"  data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdTitle" style="width: 100px"><span class="spans">*</span>执行角色：</td>
					<td class="tdContent">
						<input class="easyui-textbox contentInput" name="role"  id="roles" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdTitle" style="width: 100px"><span class="spans">*</span>截止天数：</td>
					<td class="tdContent">
						<input type="number" class="easyui-textbox contentInput" name="duration"  data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdTitle" style="width: 100px"><span class="spans">*</span>提醒时间：</td>
					<td class="tdContent">
						<input type="number" class="easyui-textbox contentInput" name="remind"  data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdTitle" style="width: 100px"><span class="spans">*</span>提醒单位：</td>
					<td class="tdContent">
						<input class="easyui-textbox contentInput" name="remindUnit" id="remindUnit"  data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdTitle">备注：</td>
					<td class="tdContent">
						<input class="easyui-textbox contentInput" name="note"  style="width:100%;height:60px" data-options="multiline:true">
					</td>
				</tr>
			</table>
		</form>
	</div>

</body>
</html>
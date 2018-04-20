<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/tag.jsp"%>
<%
  request.setAttribute("resourceIdentity", "myWorkbench:customerCare");
%>
<!DOCTYPE html>
<html>
<head>
  <title>客户关怀</title>
  <base href="${ctx}">
  <meta http-equiv="content-type" content="text/html; charset=utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <%@include file="/WEB-INF/jsp/common/css.jsp"%>
  <script type="text/javascript">
		var ctx = "${ctx}";
		var resourceName = "客户关怀";
		var resourcePath = "/myWorkbench/customerCare";
  </script>
  <%@include file="/WEB-INF/jsp/common/js.jsp"%>
  <script type="text/javascript" src="${ctx}/resources/js/common/crud.js"></script>
  <script type="text/javascript" src="${ctx}/resources/comp/My97DatePicker/WdatePicker.js"></script>
  <script type="text/javascript" src="${ctx}/resources/comp/My97DatePicker/jquery.my97.js"></script>
  <script type="text/javascript" src="${ctx}/resources/js/myWorkbench/customerCare.js"></script>
   <script type="text/javascript">
	$(function () {
		customerCare.init();
	})
  </script>
   <style> 
	.spans{ color:#FF0000};
</style>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="searchDiv">
		<form id="searchForm" method="post">
	     	<input class="easyui-textbox" name="customer.customerName" style="width:180px" data-options="label:'客户名称',labelWidth:70">
			<!-- <input class="easyui-textbox" name="careContent" style="width:180px" data-options="label:'关怀内容',labelWidth:70"> -->
			<select class="easyui-combobox"  id="statu" name="status" style="width:180px" data-options="label:'状态',labelWidth:70,editable:false">
						    <option value="1">未处理</option>  
						    <option value="2">已处理</option>  
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
	</div>
 	 <div id="editDialog">
		<form id="editForm" method="post">
			<input type="hidden" name="id">
			<table class="contentTb">
				<tr>
					<td class="tdTitle"><span class="spans">*</span>客户名称：</td>
					<td class="tdContent">
						<input type="hidden" name="customer.id" id="customerId">
						<input class="easyui-textbox contentInput" id="customerNameIdBtn" name="customer.customerName"
                          	style="width:100%"
                          	data-options="required:true,editable:false,icons:[{iconCls:'icon-search'}]">
					</td>
				</tr>
				<tr>
					<td class="tdTitle"><span class="spans">*</span>关怀内容：</td>
					<td class="tdContent">
						<textarea class="textarea easyui-validatebox" name="careContent" id="careContent" data-options="required:true" style="width: 100%; height: 100px"></textarea>
					</td>
				</tr>
				<!-- <tr>
					<td class="tdTitle"><span class="spans">*</span>拥有人：</td>
					<td class="tdFirstContent">
						<input type="hidden" name="owner.id" id="ownerId">
						<input class="easyui-textbox contentInput" id="ownerIdBtn" name="owner.username"
                          	style="width:100%"
                          	data-options="required:true,editable:false,icons:[{iconCls:'icon-search'}]">
					</td>
				</tr> -->
				<tr>
					<td class="tdTitle">处理人：</td>
					<td class="tdFirstContent">
						<input type="hidden" name="handle.id" id="handleId">
						<input class="easyui-textbox contentInput" id="handleIdBtn" name="handle.username"
                          	style="width:100%"
                          	data-options="required:false,editable:false,icons:[{iconCls:'icon-search'}]">
					</td>
				</tr>
				<tr>
					<td class="tdTitle">处理时间：</td>
						<td class="tdFirstContent">
						<input class="contentInput easyui-validatebox Wdate dateInput" id="handleTime" name="handleTime" data-options="required:false,editable:false"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})">
					</td>
				</tr>
				<tr>
					<td class="tdTitle">处理详情：</td>
					<td class="tdContent" >
						 <textarea class="textarea easyui-validatebox" name="details"  data-options="required:false" style="width: 100%;height:50px" ></textarea>
						<!-- <textarea id="handleDetails" name="handleDetails" data-options="required:true"></textarea> -->
					</td>
					
				</tr>
				<tr>
					<td class="tdTitle">状态：</td>
					<td class="tdContent">
						<input class="easyui-combobox contentInput"  name="status" id="status" data-options="required:true,editable:false" style="width:100%">
					</td>
				</tr>
				<tr>
					<td class="tdTitle">备注：</td>
					<td class="tdContent">
						<input class="easyui-textbox contentInput"  name="note" style="width:100%">
					</td>
				</tr>
			</table>
		</form>
	</div>
	<!-- 拥有人 -->
	<div id="ownerEditDialog">
	  <div class="easyui-layout" data-options="fit:true,selected:true">
	   <div data-options="region:'north',border:false" class="searchDiv">
		<form id="searchOwnerForm" method="post">
	   		 <input class="easyui-textbox" name="userName" style="width:220px" data-options="label:'姓名',labelWidth:100">
			 <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="searchOwnerBtn">查询</a>
			 <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" id="clearOwnerBtn">清空</a>
		</form>
		</div>
		<div data-options="region:'center',border:false">
		<div id="ownerList" ></div>
		</div>
		</div>
	</div>
	<!-- 处理人 -->
	<div id="handleEditDialog">
	 <div class="easyui-layout" data-options="fit:true,selected:true">
	   <div data-options="region:'north',border:false"  class="searchDiv">
		<form id="searchHandleForm" method="post">
	   		 <input class="easyui-textbox" name="userName" style="width:220px" data-options="label:'姓名',labelWidth:100">
			 <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="searchHandleBtn">查询</a>
			 <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" id="clearHandleBtn">清空</a>
		</form>
		</div>
		<div data-options="region:'center',border:false">
		<div id="handleList" ></div>
		</div>
		</div>
	</div>
	<!-- 客户名称-->
	<div id="customerEditDialog">
	<div class="easyui-layout" data-options="fit:true,selected:true">
	   <div data-options="region:'north',border:false"  class="searchDiv">
		<form id="searchCustomerForm" method="post">
	   		 <input class="easyui-textbox" name="customerName" style="width:220px" data-options="label:'客户名称',labelWidth:100">
			 <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="searchCustomerBtn">查询</a>
			 <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" id="clearCustomerBtn">清空</a>
		</form>
		</div>
		<div data-options="region:'center',border:false">
		<div id="customerList" ></div>
		</div>
		</div>
	</div>
</body>
</html>
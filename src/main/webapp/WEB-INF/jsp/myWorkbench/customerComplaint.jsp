<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/tag.jsp"%>
<%
	request.setAttribute("resourceIdentity", "myWorkbench:customerComplaint");
%>
<!DOCTYPE html>
<html>
<head>
<title>客户投诉</title>
<base href="${ctx}">
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<%@include file="/WEB-INF/jsp/common/css.jsp"%>
<script type="text/javascript">
	var ctx = "${ctx}";
	var resourceName = "客户投诉";
	var resourcePath = "/myWorkbench/customerComplaint";
</script>
<%@include file="/WEB-INF/jsp/common/js.jsp"%>
<script type="text/javascript" src="${ctx}/resources/js/common/crud.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/comp/kindeditor/kindeditor.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/comp/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/js/myWorkbench/customerComplaint.js"></script>
<script type="text/javascript">
	$(function() {
		customerComplaint.init();
	})
</script>
 <style> 
	.spans{ color:#FF0000};
</style>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="searchDiv"
		style="height: 50px">
		<form id="searchForm" method="post">
			<input class="easyui-textbox" name="customer.customerName" style="width: 180px" data-options="label:'客户名称',labelWidth:70">
			<input class="easyui-textbox" id="orderCodeSearchIdBtn" name="order.orderCode" style="width: 220px" data-options="label:'订单编号',labelWidth:70">
			<!-- <input class="easyui-textbox" id="complaintobjectIdBtn" name="complaintObject.name" style="width: 180px" data-options="label:'投诉对象',labelWidth:70"> -->
				 <span class="textbox combo datebox" style="font-size:0;border: 0px;">
						<label class="textbox-label" style="text-decoration:none;text-align: left; width: 79.0476px; height: 30.0952px; line-height: 30.0952px;">投诉时间</label>
						<input class="easyui-validatebox Wdate dateInput" style="width:120px;"  name="complaintTime" data-options="editable:false" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})">
					</span>
					— <input class="easyui-validatebox Wdate dateInput" style="width:120px;" name="note" data-options="editable:false" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})">
				 
				 <select class="easyui-combobox" id="statu" name="status" style="width: 180px"
					data-options="label:'状态',labelWidth:70,editable:false">
					<option value="1">未处理</option>
					<option value="2">已处理</option>
				</select> <a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-search'" id="searchBtn">查询</a> <a
					href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-clear'" id="clearBtn">清空</a>
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
					<td class="tdContent"><input type="hidden" name="customer.id"
						id="customerId"> <input
						class="easyui-textbox contentInput" id="customerNameIdBtn"
						name="customer.customerName" style="width: 100%"
						data-options="required:true,editable:false,icons:[{iconCls:'icon-search'}]">
					</td>
				</tr>
				<tr>
					<td class="tdTitle">订单编号：</td>
					<td class="tdContent"><input type="hidden" name="order.id"
						id="orderId"> <input class="easyui-textbox contentInput"
						id="orderCodeIdBtn" name="order.orderCode" style="width: 100%"
						data-options="required:false,editable:false,icons:[{iconCls:'icon-search'}]">
					</td>
				</tr>
				<tr>
					<td class="tdTitle"><span class="spans">*</span>投诉内容：</td>
					<td class="tdContent">
					<textarea class="textarea easyui-validatebox" name="complaintContent" id="complaintContent" data-options="required:true" style="width: 100%; height: 100px"></textarea>
					</td>
				</tr>
				<tr>
					<td class="tdTitle">投诉对象：</td>
					<td class="tdFirstContent"><input type="hidden"
						name="complaintObject.id" id="complaint_object_id"> <input
						class="easyui-textbox contentInput" id="objectIdBtn"
						name="complaintObject.username" style="width: 100%"
						data-options="required:false,editable:false,icons:[{iconCls:'icon-search'}]">
					</td>
				</tr>
				<tr>
					<td class="tdTitle"><span class="spans">*</span>投诉时间：</td>
					<td class="tdContent">
						<input class="contentInput easyui-validatebox Wdate dateInput" id="complaintTime" name="complaintTime" data-options="required:true,editable:false" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})">
					</td>
				</tr>
				<tr>
					<td class="tdTitle">处理人：</td>
					<td class="tdFirstContent"><input type="hidden"
						name="handle.id" id="handleId"> <input
						class="easyui-textbox contentInput" id="handleIdBtn"
						name="handle.username" style="width: 100%"
						data-options="required:false,editable:false,icons:[{iconCls:'icon-search'}]">
					</td>
				</tr>
				<tr>
					<td class="tdTitle">处理时间：</td>
					<td class="tdFirstContent">
						<input class="contentInput easyui-validatebox Wdate dateInput" id="handleTime" name="handleTime" data-options="required:false,editable:false" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})">
					</td>
				</tr>
				<tr>
					<td class="tdTitle">处理详情：</td>
					<td class="tdContent"><textarea
							class="textarea easyui-validatebox" name="handleDetails"
							data-options="required:false" style="width: 100%; height: 50px"></textarea>
						<!-- <textarea id="handleDetails" name="handleDetails" data-options="required:true"></textarea> -->
					</td>

				</tr>
				<tr>
					<td class="tdTitle">状态：</td>
					<td class="tdContent"><input
						class="easyui-combobox contentInput" name="status" id="status"
						data-options="required:true,editable:false"></td>
				</tr>
				<tr>
					<td class="tdTitle">备注：</td>
					<td class="tdContent"><input
						class="easyui-textbox contentInput" name="note"></td>
				</tr>
			</table>
		</form>
	</div>
	<!-- 订单编号 -->
	<div id="orderEditDialog">
		<div class="easyui-layout" data-options="fit:true,selected:true">
			<div data-options="region:'north',border:false" class="searchDiv">
				<form id="searchOrderForm" method="post">
						<input class="easyui-textbox" name="orderCode"
							style="width: 220px" data-options="label:'订单编号',labelWidth:100">

						<a href="javascript:void(0)" class="easyui-linkbutton"
							data-options="iconCls:'icon-search'" id="searchOrderBtn">查询</a> <a
							href="javascript:void(0)" class="easyui-linkbutton"
							data-options="iconCls:'icon-clear'" id="clearOrderBtn">清空</a>
				</form>
			</div>
			<div data-options="region:'center',border:false">
				<div id="orderList"></div>
			</div>
		</div>
	</div>
	<!-- 订单编号搜索 -->
	<div id="orderEditSearchDialog">
		<div class="easyui-layout" data-options="fit:true,selected:true">
			<div data-options="region:'north',border:false" class="searchDiv">
				<form id="searchOrderSearchForm" method="post">
						<input class="easyui-textbox" name="orderCode"
							style="width: 220px" data-options="label:'订单编号',labelWidth:100">

						<a href="javascript:void(0)" class="easyui-linkbutton"
							data-options="iconCls:'icon-search'" id="searchOrderSearchBtn">查询</a> <a
							href="javascript:void(0)" class="easyui-linkbutton"
							data-options="iconCls:'icon-clear'" id="clearOrderSearchBtn">清空</a>
				</form>
			</div>
			<div data-options="region:'center',border:false">
				<div id="orderSearchList"></div>
			</div>
		</div>
	</div>
	<!-- 投诉对象 -->
	<div id="objectEditDialog">
		<div class="easyui-layout" data-options="fit:true,selected:true">
			<div data-options="region:'north',border:false" class="searchDiv">
				<form id="searchObjectForm" method="post">
						<input class="easyui-textbox" name="userName" style="width: 220px"
							data-options="label:'姓名',labelWidth:100"> <a
							href="javascript:void(0)" class="easyui-linkbutton"
							data-options="iconCls:'icon-search'" id="searchObjectBtn">查询</a>
						<a href="javascript:void(0)" class="easyui-linkbutton"
							data-options="iconCls:'icon-clear'" id="clearObjectBtn">清空</a>
				</form>
			</div>
			<div data-options="region:'center',border:false">
				<div id="objectList"></div>
			</div>
		</div>
	</div>
	<!-- 投诉对象搜索 -->
	<div id="objectSearchEditDialog">
		<div class="easyui-layout" data-options="fit:true,selected:true">
			<div data-options="region:'north',border:false" class="searchDiv">
				<form id="searchObjectSearchForm" method="post">
						<input class="easyui-textbox" name="userName" style="width: 220px"
							data-options="label:'姓名',labelWidth:100"> <a
							href="javascript:void(0)" class="easyui-linkbutton"
							data-options="iconCls:'icon-search'" id="searchObjectSearchBtn">查询</a>
						<a href="javascript:void(0)" class="easyui-linkbutton"
							data-options="iconCls:'icon-clear'" id="clearObjectSearchBtn">清空</a>
				</form>
			</div>
			<div data-options="region:'center',border:false">
				<div id="objectSearchList"></div>
			</div>
		</div>
	</div>
	<!-- 处理人 -->
	<div id="handleEditDialog">
		<div class="easyui-layout" data-options="fit:true,selected:true">
			<div data-options="region:'north',border:false" class="searchDiv">
				<form id="searchHandleForm" method="post">
						<input class="easyui-textbox" name="userName" style="width: 220px"
							data-options="label:'姓名',labelWidth:100"> <a
							href="javascript:void(0)" class="easyui-linkbutton"
							data-options="iconCls:'icon-search'" id="searchHandleBtn">查询</a>
						<a href="javascript:void(0)" class="easyui-linkbutton"
							data-options="iconCls:'icon-clear'" id="clearHandleBtn">清空</a>
				</form>
			</div>
			<div data-options="region:'center',border:false">
				<div id="handleList"></div>
			</div>
		</div>
	</div>
	<!-- 客户名称-->
	<div id="customerEditDialog">
		<div class="easyui-layout" data-options="fit:true,selected:true">
			<div data-options="region:'north',border:false" class="searchDiv">
				<form id="searchCustomerForm" method="post">
						<input class="easyui-textbox" name="customerName"
							style="width: 220px" data-options="label:'客户名称',labelWidth:100">
						<a href="javascript:void(0)" class="easyui-linkbutton"
							data-options="iconCls:'icon-search'" id="searchCustomerBtn">查询</a>
						<a href="javascript:void(0)" class="easyui-linkbutton"
							data-options="iconCls:'icon-clear'" id="clearCustomerBtn">清空</a>
				</form>
			</div>
			<div data-options="region:'center',border:false">
				<div id="customerList"></div>
			</div>
		</div>
	</div>
</body>
</html>
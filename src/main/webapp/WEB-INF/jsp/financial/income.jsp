<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/tag.jsp"%>
<%
  request.setAttribute("resourceIdentity", "financial:income");
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
		var resourceName = "收入登记";
		var resourcePath = "/financial/income";
  </script>
  <%@include file="/WEB-INF/jsp/common/js.jsp"%>
  <script type="text/javascript" src="${ctx}/resources/comp/My97DatePicker/WdatePicker.js"></script>
  <script type="text/javascript" src="${ctx}/resources/js/common/crud.js"></script>
  <script type="text/javascript" src="${ctx}/resources/js/financial/income.js"></script>
  
  <script type="text/javascript">
	$(function () {
		income.init();
	})
  </script>
  <style type="text/css">
  	.tdContent{
  		padding-top: 7px;
  	}
  </style>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="searchDiv">
		<form id="searchForm" method="post">
			<input class="easyui-textbox" name="order.customer.customerName" style="width:180px" data-options="label:'客户',labelWidth:70">
<!-- 			<input class=" Wdate" name="incomeTime" style="width:180px" data-options="label:'日期',labelWidth:70" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})">
 -->			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="searchBtn">查询</a>
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
		<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',border:true" style="height: 170px">
			<form id="editForm" method="post">
				<input type="hidden" name="id">
				<input type="hidden" name="loanItem" id="loanItem">
				<input type="hidden" name="incomeItem" id="incomeItem">
	            <table style="margin-left: 45px">
	            	<tr align="center">
						<td class="tdTitle"><span style="color: red">*</span>订单编号：</td>
						<td class="tdContent">
							<input type="hidden" name="order.id" id="orderId">
							<input class="easyui-textbox contentInput" id="orderCodeIdBtn" name="order.orderCode"
	                          	style="width:100%" 
	                          	data-options="required:true,editable:false,icons:[{iconCls:'icon-search'}]">
						</td>
						<td class="tdTitle"><span style="color: red">*</span>登记时间：</td>
						<td class="tdContent">
							<input class="easyui-validatebox Wdate dateInput"  id="incomeTime" name="incomeTime" data-options="required:true,editable:false" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})">
						</td>
					</tr>
					<tr>
						<td class="tdTitle"><span style="color: red">*</span>金额（元）：</td>
						<td class="tdContent">
							<input class="easyui-numberbox contentInput" name="amount" id="amount" style="width:190px" data-options="required:true,precision:2,min:0">
						</td>
						<td class="tdTitle"><span style="color: red">*</span>是否开票：</td>
						<td class="tdContent">
							<input class="easyui-combobox contentInput" style="width: 100%" id="billing" name="billing"  data-options="required:true,editable:false">
						</td>
					</tr>
					<tr>
						<td class="tdTitle"><span style="color: red">*</span>付款方式：</td>
						<td class="tdContent">
							<input class="easyui-combobox contentInput" style="width: 100%" id="payTypeName" name="payType.id"  data-options="required:true,editable:false">
						</td>
						<td class="tdTitle"><span style="color: red">*</span>期次：</td>
						<td class="tdContent">
							<input class="easyui-combobox contentInput" style="width: 100%" id="periodTimeName" name="periodTime.id"  data-options="required:true,editable:false">
					</tr>
					<tr>
						<td class="tdTitle">备注：</td>
						<td class="tdContent" colspan="3">
							<input class="easyui-textbox contentInput" style="width: 100%" id="note" name="note" >
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center',border:false">
				<div id="tb2">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" id="updateIncomeBtn">编辑</a>
				</div>
				<div id="incomeList"></div>
			</div>
		</div>
	</div>
	
	<!-- 订单 -->
	<div id="orderEditDialog">
		<div class="easyui-layout" data-options="fit:true,selected:true">
			<div data-options="region:'north',border:false"
					 style="height: 45px">
			<form id="searchOrderForm" method="post" style="height:33px;margin:5px">
				 <input type="hidden" name="settlementAuditStatus" value="3"  id="settlementAuditStatus">
		   		 <input class="easyui-textbox" name="orderCode" style="width:220px" data-options="label:'订单编号',labelWidth:100">
				 <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="searchOrderBtn">查询</a>
				 <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" id="clearOrderBtn">清空</a>
			</form>
			</div>
			<div data-options="region:'center',border:false">
				<div id="orderList" ></div>
			</div>
	</div>
	</div>
	<!-- 编辑收入条目 -->
	<div id="incomeDialog">
		<form id="incomeForm" method="post">
		<input type="hidden" name="id">
		<input type="hidden" name="costType" id="costType">
		<input type="hidden" name="fromId" id="fromId">
		<table>
			<tr>
				<td class="tdTitle">本次收款</td>
				<td class="tdContent"><input class="easyui-numberbox contentInput" name="pay" id="editPay" data-options="precision:2,min:0"></td>
			</tr>
			<tr>
				<td class="tdTitle">备注</td>
				<td class="tdContent"><input class="easyui-textbox contentInput" name="note" id="editNote"></td>
			</tr>
		</table>
		</form>
	</div>
</body>
</html>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/tag.jsp"%>
<%
  request.setAttribute("resourceIdentity", "financial:balance");
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
		var resourceName = "订单结算";
		var resourcePath = "/financial/balance";
  </script>
  <%@include file="/WEB-INF/jsp/common/js.jsp"%>
  <script type="text/javascript" src="${ctx}/resources/js/common/crud.js"></script>
  <script type="text/javascript" src="${ctx}/resources/comp/My97DatePicker/WdatePicker.js"></script>
  <script type="text/javascript" src="${ctx}/resources/js/financial/balance.js"></script>
  <script type="text/javascript">
	$(function () {
		balance.init();
	})
  </script>
  <style type="text/css">
  	.tdContent{
  		padding-left:10px;
  	}
  </style>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="searchDiv">
		<form id="searchForm" method="post">
					<input class="easyui-textbox" name="orderCode" style="width:190px" data-options="label:'订单编号',labelWidth:70">
					<input class="easyui-textbox" name="customer.customerName" style="width:190px" data-options="label:'客户名称',labelWidth:70">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="searchBtn">查询</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" id="clearBtn">清空</a>
		</form>
	</div>
	<div id="tb">
		<%@include file="/WEB-INF/jsp/common/btn.jsp"%>
	    <shiro:hasPermission name = "${resourceIdentity}:verify">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-approve',plain:true" id="verifyBtn">送审</a>
		</shiro:hasPermission>
			<shiro:hasPermission name = "${resourceIdentity}:trace">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-follow',plain:true" id="traceBtn">跟踪</a>
		</shiro:hasPermission>
	</div>
	<div data-options="region:'center'">
		<table id="list" data-options="border:false"></table>
	</div>
	<div id="tb">
		<%@include file="/WEB-INF/jsp/common/btn.jsp"%>
	</div>
	<div id="editDialog">
		<form id="addForm" method="post" style="padding-bottom: 6px;padding-top: 5px">
			<input type="hidden" id="orderId">
            <table>
				<tr>
					<td class="tdTitle" style="width: 70px;padding-top: 15px"><span style="color: red">*</span>订单号：</td>
					<td class="tdContent" style="padding-top: 15px">
						<input class="easyui-combobox" id="orderCode" name="id" style="width:280px" data-options="required:true,editable:false">
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="tabsDialog">
		<div class="easyui-tabs" id="tabs" data-options="fit:true,border:false">
			<div title="基本信息">
			<div data-options="region:'center',border:false">
			<form id="editForm" method="post">
				<table>
					<tr>
						<td class="tdTitle" style="width: 100px;padding-top: 5px">订单编号：</td>
						<td class="tdContent" style="padding-top: 5px">
							<input class="easyui-textbox contentInput" id="orderCode" name="orderCode" style="width:280px" data-options="editable:false">
						</td>
					</tr>
					<tr>
						<td class="tdTitle" style="width:100px;padding-top: 5px">客户名称：</td>
						<td class="tdContent" style="padding-top: 5px">
							<input class="easyui-textbox contentInput"  name="customerName" style="width:280px" data-options="editable:false">
						</td>
					</tr>
					<tr>
						<td class="tdTitle" style="width: 100px;padding-top: 5px">产品：</td>
						<td class="tdContent" style="padding-top: 5px">
							<input class="easyui-textbox contentInput"  name="productName" style="width:280px" data-options="editable:false">
						</td>
					</tr>
					<tr>
						<td class="tdTitle" style="width: 100px;padding-top: 5px">银行：</td>
						<td class="tdContent" style="padding-top: 5px">
							<input class="easyui-textbox contentInput"  name="bankName" style="width:280px" data-options="editable:false">
						</td>
					</tr>
					<tr>
						<td class="tdTitle" style="width: 100px;padding-top: 5px">支行：</td>
						<td class="tdContent" style="padding-top: 5px">
							<input class="easyui-textbox contentInput"  name="subbranchName" style="width:280px" data-options="editable:false">
						</td>
					</tr>
					<tr>
						<td class="tdTitle" style="width: 100px;padding-top: 5px">贷款金额（万元）：</td>
						<td class="tdContent" style="padding-top: 5px">
							<input class="easyui-numberbox contentInput"  name="loanAmount" style="width:280px" data-options="editable:false">
						</td>
					</tr>
					<tr>
						<td class="tdTitle" style="width: 100px;padding-top: 5px">月利率（厘/月）：</td>
						<td class="tdContent" style="padding-top: 5px">
							<input class="easyui-numberbox contentInput"  name="lendingRate" style="width:280px" data-options="editable:false">
						</td>
					</tr>
					<tr>
						<td class="tdTitle" style="width: 100px;padding-top: 5px">贷款年限：</td>
						<td class="tdContent" style="padding-top: 5px">
							<input class="easyui-numberbox contentInput"  name="loanTerm" style="width:280px" data-options="editable:false">
						</td>
					</tr>
					<tr>
						<td class="tdTitle" style="width: 100px;padding-top: 5px">还款方式：</td>
						<td class="tdContent" style="padding-top: 5px">
							<input class="easyui-textbox contentInput"  name="repayWayName" style="width:280px" data-options="editable:false">
						</td>
					</tr>
					<tr>
						<td class="tdTitle" style="width: 120px;padding-top: 5px">手续费百分比（%）：</td>
						<td class="tdContent" style="padding-top: 5px">
							<input class="easyui-numberbox contentInput"  name="serviceChargePercent" style="width:280px" data-options="editable:false">
						</td>
					</tr>
					<tr>
						<td class="tdTitle" style="width: 100px;padding-top: 5px">贷款手续费（元）：</td>
						<td class="tdContent" style="padding-top: 5px">
							<input class="easyui-numberbox contentInput"  name="serviceCharge" style="width:280px" data-options="editable:false">
						</td>
					</tr>
					<tr>
						<td class="tdTitle" style="width: 100px;padding-top: 5px">返佣比例：</td>
						<td class="tdContent" style="padding-top: 5px">
							<input class="easyui-numberbox contentInput"  name="commissionRate" style="width:280px" data-options="editable:false">
						</td>
					</tr>
				</table>
				</form>
				</div>
			</div>
			<div title="借款">
				<div id="tb2">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" id="updateLoan">编辑</a>
				</div>
				<table id="loanList" data-options="fit:true,border:false"></table>
			</div>
			<div title="收入项">
				<div id="tb3">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" id="updateIncome">编辑</a>
				</div>
						<table id="incomeList" data-options="fit:true,border:false"></table>
				</div>
			<div title="支出项">
				<div id="tb4">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" id="updatePay">编辑</a>
				</div>
					<table id="payList" data-options="fit:true,border:false"></table>
			</div>
		</div>
	</div>
	<div id="otherDialog">
			<div id="tb5">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" id="selectOhters">选择</a>
				</div>
				<table id="otherList" data-options="fit:true,border:false"></table>
	</div>
	<div id="editLoanDialog">
		<form id="editLoanForm" method="post">
			<input type="hidden" id="orderLoanId" name="id">
			<table class="cententTb">
				<tr>
					<td class="tdTitle">
						借款金额（万元）：
					</td>
					<td class="tdContent">
						<input class="contentInput easyui-numberbox " style="width: 188px;border:solid 0.5px #989696;height: 27px;" name="amount" data-options="editable:false,prescision:2" >
					</td>
				</tr>
				<tr>
					<td class="tdTitle">
						借款利率（%）：
					</td>
					<td class="tdContent">
						<input class="contentInput easyui-numberbox " style="width: 188px;border:solid 0.5px #989696;height: 27px;" name="rate" data-options="editable:false,prescision:2" >
					</td>
				</tr>
				<tr>
					<td class="tdTitle">
						借款日期：
					</td>
					<td class="tdContent">
						<input class="contentInput easyui-textbox" style="width: 188px;" id="beginDate" name="beginDate" data-options="editable:false" >
					</td>
				</tr>
				<tr>
					<td class="tdTitle">
						预计还款日期：
					</td>
					<td class="tdContent">
						<input class="contentInput easyui-textbox" style="width: 188px;"  name="estimateRepayDate" data-options="editable:false" >
					</td>
				</tr>
				<tr>
					<td class="tdTitle">
						<span style="color: red">*</span>实际还款日期：
					</td>
					<td class="tdContent">
						<input class="dateInput easyui-validatebox Wdate " style="width: 176px;padding-left: 10px" id="repayDate" name="repayDate" data-options="required:true,editable:false"  onfocus="onpicking:balance.calDay(),WdatePicker({minDate:'#F{$dp.$D(\'beginDate\')}',dateFmt:'yyyy-MM-dd'})">
					</td>
				</tr>
				<tr>
					<td class="tdTitle">
						实际借款天数：
					</td>
					<td class="tdContent">
						<input class="contentInput easyui-numberbox" style="width: 188px;" id="loanDay" name="loanDay" data-options="editable:false">
					</td>
				</tr>
				<tr>
					<td class="tdTitle">
					 	参考利息（元）：
					</td>
					<td class="tdContent">
						<input class="contentInput easyui-numberbox" style="width: 188px;" id="referInterest" name="referInterest" data-options="precision:2,editable:false">
					</td>
				</tr>
				<tr>
					<td class="tdTitle">
						<span style="color: red">*</span>实际利息（元）：
					</td>
					<td class="tdContent">
						<input class="contentInput easyui-numberbox" style="width: 188px;" id="interest" name="interest" data-options="precision:2,required:true">
					</td>
				</tr>
				<tr>
					<td class="tdTitle">
						应收金额（万元）：
					</td>
					<td class="tdContent">
						<input class="contentInput easyui-numberbox" style="width: 188px;" id="receiveAmount" name="receiveAmount" data-options="precision:2,editable:false">
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="editIncomeDialog">
		<form id="editIncomeForm" method="post">
			<input type="hidden" id="orderIncomeId" name="id">
			<input type="hidden" id="orderId2" name="order.id">
			<table class="cententTb">
				<tr>
					<td class="tdTitle">
						收入项：
					</td>
					<td class="tdContent">
						<input class="contentInput easyui-textbox" style="width: 188px;" name="costTypeName"  data-options="editable:false,precision:2" >
					</td>
				</tr>
				<tr>
					<td class="tdTitle">
						参考金额：
					</td>
					<td class="tdContent">
						<input class="contentInput easyui-numberbox" style="width: 188px;" name="estimateAmount"  data-options="editable:false,precision:2" >
					</td>
				</tr>
				<tr>
					<td class="tdTitle">
						<span style="color: red">*</span>实际金额：
					</td>
					<td class="tdContent">
						<input class="contentInput easyui-numberbox" style="width: 188px;" name="receiveAmount"  data-options="required:true,precision:2" >
					</td>
				</tr>
				<tr>
					<td class="tdTitle">
						备注：
					</td>
					<td class="tdContent">
						<input class="contentInput easyui-textbox" style="width: 188px;" name="note"  >
					</td>
				</tr>
			<!-- 	<tr>
					<td class="tdTitle">
						已收金额：
					</td>
					<td class="tdContent">
						<input class="contentInput easyui-numberbox" style="width: 188px;" name="receivedAmount"  data-options="precision:2" >
					</td>
				</tr>
				<tr>
					<td class="tdTitle">
						待收金额：
					</td>
					<td class="tdContent">
						<input class="contentInput easyui-numberbox" style="width: 188px;" name="receivingAmount"  data-options="precision:2" >
					</td>
				</tr> -->
			</table>
		</form>
	</div>
		<div id="editPayDialog">
		<form id="editPayForm" method="post">
			<input type="hidden" id="orderPayId" name="id">
			<table class="cententTb">
				<tr>
					<td class="tdTitle">
						<span style="color: red">*</span>是否成本：
					</td>
					<td class="tdContent">
						<input class="contentInput easyui-comboboxbox" style="width: 188px;" name="isCost" id="isCost" data-options="required:true" >
					</td>
				</tr>
				<tr>
					<td class="tdTitle">
						<span style="color: red">*</span>实际金额：
					</td>
					<td class="tdContent">
						<input class="contentInput easyui-numberbox" style="width: 188px;" name="payAmount"  data-options="precision:2,required:true" >
					</td>
				</tr>
				<tr>
					<td class="tdTitle">
						<span style="color: red">*</span>收款账户名：
					</td>
					<td class="tdContent">
						<input class="contentInput easyui-textbox" style="width: 188px;" name="receiveAccountName"  data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdTitle">
						<span style="color: red">*</span>收款账户开户行：
					</td>
					<td class="tdContent">
						<input class="contentInput easyui-textbox" style="width: 188px;" name="receiveAccountBank" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdTitle">
						<span style="color: red">*</span>收款账号：
					</td>
					<td class="tdContent">
						<input class="contentInput easyui-numberbox" style="width: 188px;" data-options="required:true" name="receiveAccountNumber"  >
					</td>
				</tr>
				<tr>
					<td class="tdTitle">
						备注：
					</td>
					<td class="tdContent">
						<input class="contentInput easyui-textbox" style="width: 188px;" name="note"  >
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="editPayOtherDialog">
		<form id="editPayOtherForm" method="post" >
			<input type="hidden" id="orderPayId2" name="id">
			<input type="hidden" id="otherId" name="otherPartners.id">
			<table class="cententTb">
				<tr>
					<td class="tdTitle">
						<span style="color: red">*</span>实际金额：
					</td>
					<td class="tdContent">
						<input class="contentInput easyui-numberbox" style="width: 188px;" name="payAmount"  data-options="precision:2,required:true" >
					</td>
				</tr>
				<tr>
					<td class="tdTitle">
						<span style="color: red">*</span>支出对象：
					</td>
					<td class="tdContent">
						<input class="contentInput easyui-textbox" style="width: 188px;" id="others" name="object"  data-options="editable:false,required:true,icons:[{iconCls:'icon-search'}]" >
					</td>
				</tr>
				<tr>
					<td class="tdTitle">
						<span style="color: red">*</span>收款账户名：
					</td>
					<td class="tdContent">
						<input class="contentInput easyui-textbox" style="width: 188px;" name="receiveAccountName"  data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdTitle">
						<span style="color: red">*</span>收款账户开户行：
					</td>
					<td class="tdContent">
						<input class="contentInput easyui-textbox" style="width: 188px;" name="receiveAccountBank" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdTitle">
						<span style="color: red">*</span>收款账号：
					</td>
					<td class="tdContent">
						<input class="contentInput easyui-numberbox" style="width: 188px;" data-options="required:true" name="receiveAccountNumber"  >
					</td>
				</tr>
				<tr>
					<td class="tdTitle">
						备注：
					</td>
					<td class="tdContent">
						<input class="contentInput easyui-textbox" style="width: 188px;" name="note"  >
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>
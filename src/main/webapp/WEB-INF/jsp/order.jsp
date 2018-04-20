<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/tag.jsp"%>
<%
  request.setAttribute("resourceIdentity", ":order");
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
		var resourceName = "订单";
		var resourcePath = "/admin/order";
  </script>
  <%@include file="/WEB-INF/jsp/common/js.jsp"%>
  <script type="text/javascript" src="${ctx}/resources/js/common/crud.js"></script>
  <script type="text/javascript" src="${ctx}/resources/js/admin/order.js"></script>
  <script type="text/javascript">
	$(function () {
		order.init();
	})
  </script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="searchDiv">
		<form id="searchForm" method="post">
					<input class="easyui-textbox" name="orderCode" style="width:180px" data-options="label:'订单编号(年月日+两位流水）',labelWidth:70">
					<input class="easyui-textbox" name="customerId" style="width:180px" data-options="label:'客户',labelWidth:70">
					<input class="easyui-textbox" name="productId" style="width:180px" data-options="label:'产品',labelWidth:70">
					<input class="easyui-textbox" name="propertyId" style="width:180px" data-options="label:'抵押房产',labelWidth:70">
					<input class="easyui-textbox" name="bankId" style="width:180px" data-options="label:'银行',labelWidth:70">
					<input class="easyui-textbox" name="branchId" style="width:180px" data-options="label:'支行',labelWidth:70">
					<input class="easyui-textbox" name="signingDate" style="width:180px" data-options="label:'签约日期（年月日）',labelWidth:70">
					<input class="easyui-textbox" name="estimateFinishTime" style="width:180px" data-options="label:'预计完成日期（年月日）',labelWidth:70">
					<input class="easyui-textbox" name="loanAmount" style="width:180px" data-options="label:'贷款金额（万元）',labelWidth:70">
					<input class="easyui-textbox" name="lendingRate" style="width:180px" data-options="label:'月利率（厘/月）',labelWidth:70">
					<input class="easyui-textbox" name="loanTerm" style="width:180px" data-options="label:'贷款年限',labelWidth:70">
					<input class="easyui-textbox" name="repayWayId" style="width:180px" data-options="label:'还款方式',labelWidth:70">
					<input class="easyui-textbox" name="repayNote" style="width:180px" data-options="label:'',labelWidth:70">
					<input class="easyui-textbox" name="serviceChargePercent" style="width:180px" data-options="label:'手续费百分比（%）',labelWidth:70">
					<input class="easyui-textbox" name="serviceCharge" style="width:180px" data-options="label:'手续费（元）',labelWidth:70">
					<input class="easyui-textbox" name="matchmakerId" style="width:180px" data-options="label:'介绍人id',labelWidth:70">
					<input class="easyui-textbox" name="commissionAmount" style="width:180px" data-options="label:'返佣金额',labelWidth:70">
					<input class="easyui-textbox" name="commissionRate" style="width:180px" data-options="label:'返佣比例（%）',labelWidth:70">
					<input class="easyui-textbox" name="commissionReason" style="width:180px" data-options="label:'返佣理由',labelWidth:70">
					<input class="easyui-textbox" name="csAssistantId" style="width:180px" data-options="label:'客服助理',labelWidth:70">
					<input class="easyui-textbox" name="csPrincipalId" style="width:180px" data-options="label:'客服负责人',labelWidth:70">
					<input class="easyui-textbox" name="followUserId" style="width:180px" data-options="label:'跟单人',labelWidth:70">
					<input class="easyui-textbox" name="ownerId" style="width:180px" data-options="label:'拥有人',labelWidth:70">
					<input class="easyui-textbox" name="processInstanceId" style="width:180px" data-options="label:'',labelWidth:70">
					<input class="easyui-textbox" name="auditStatus" style="width:180px" data-options="label:'审核状态（1草稿2审核中3审核通过4重新调整5放弃6审核不通过）',labelWidth:70">
					<input class="easyui-textbox" name="status" style="width:180px" data-options="label:'状态（1已立项2已签约3已放款4完结）',labelWidth:70">
					<input class="easyui-textbox" name="cost" style="width:180px" data-options="label:'订单成本（元）',labelWidth:70">
					<input class="easyui-textbox" name="receiveAmount" style="width:180px" data-options="label:'应收金额（元）',labelWidth:70">
					<input class="easyui-textbox" name="payAmount" style="width:180px" data-options="label:'应付金额（元）',labelWidth:70">
					<input class="easyui-textbox" name="companyLoanAmount" style="width:180px" data-options="label:'借款总金额（万元）',labelWidth:70">
					<input class="easyui-textbox" name="companyLoanInterest" style="width:180px" data-options="label:'借款利息总额（元）',labelWidth:70">
					<input class="easyui-textbox" name="receivablesAccountStatus" style="width:180px" data-options="label:'收款账户审核状态（1草稿2审核中3审核通过4审核不通过）',labelWidth:70">
					<input class="easyui-textbox" name="loanDate" style="width:180px" data-options="label:'放款日期',labelWidth:70">
					<input class="easyui-textbox" name="loanMoney" style="width:180px" data-options="label:'放款金额',labelWidth:70">
					<input class="easyui-textbox" name="settlementCharge" style="width:180px" data-options="label:'结算手续费',labelWidth:70">
					<input class="easyui-textbox" name="settlementClerk" style="width:180px" data-options="label:'结算员',labelWidth:70">
					<input class="easyui-textbox" name="settlementSubmitDate" style="width:180px" data-options="label:'结算提交时间',labelWidth:70">
					<input class="easyui-textbox" name="settlementSubmitUserId" style="width:180px" data-options="label:'结算提交人Id',labelWidth:70">
					<input class="easyui-textbox" name="settlementAuditStatus" style="width:180px" data-options="label:'结算审核状态（1草稿2审核中3审核通过4审核不通过）',labelWidth:70">
					<input class="easyui-textbox" name="brokerageAuditStatus" style="width:180px" data-options="label:'佣金审核状态（1草稿2审核中3审核通过4审核不通过）',labelWidth:70">
					<input class="easyui-textbox" name="brokerageGrantState" style="width:180px" data-options="label:'佣金发放状态',labelWidth:70">
					<input class="easyui-textbox" name="brokerageTotal" style="width:180px" data-options="label:'佣金总额',labelWidth:70">
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
					<td class="tdFirstTitle">订单编号(年月日+两位流水）</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="orderCode" name="orderCode" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">客户</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="customerId" name="customerId" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">产品</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="productId" name="productId" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">抵押房产</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="propertyId" name="propertyId" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">银行</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="bankId" name="bankId" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">支行</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="branchId" name="branchId" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">签约日期（年月日）</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="signingDate" name="signingDate" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">预计完成日期（年月日）</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="estimateFinishTime" name="estimateFinishTime" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">贷款金额（万元）</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="loanAmount" name="loanAmount" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">月利率（厘/月）</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="lendingRate" name="lendingRate" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">贷款年限</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="loanTerm" name="loanTerm" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">还款方式</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="repayWayId" name="repayWayId" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle"></td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="repayNote" name="repayNote" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">手续费百分比（%）</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="serviceChargePercent" name="serviceChargePercent" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">手续费（元）</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="serviceCharge" name="serviceCharge" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">介绍人id</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="matchmakerId" name="matchmakerId" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">返佣金额</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="commissionAmount" name="commissionAmount" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">返佣比例（%）</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="commissionRate" name="commissionRate" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">返佣理由</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="commissionReason" name="commissionReason" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">客服助理</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="csAssistantId" name="csAssistantId" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">客服负责人</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="csPrincipalId" name="csPrincipalId" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">跟单人</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="followUserId" name="followUserId" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">拥有人</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="ownerId" name="ownerId" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle"></td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="processInstanceId" name="processInstanceId" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">审核状态（1草稿2审核中3审核通过4重新调整5放弃6审核不通过）</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="auditStatus" name="auditStatus" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">状态（1已立项2已签约3已放款4完结）</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="status" name="status" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">订单成本（元）</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="cost" name="cost" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">应收金额（元）</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="receiveAmount" name="receiveAmount" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">应付金额（元）</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="payAmount" name="payAmount" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">借款总金额（万元）</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="companyLoanAmount" name="companyLoanAmount" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">借款利息总额（元）</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="companyLoanInterest" name="companyLoanInterest" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">收款账户审核状态（1草稿2审核中3审核通过4审核不通过）</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="receivablesAccountStatus" name="receivablesAccountStatus" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">放款日期</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="loanDate" name="loanDate" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">放款金额</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="loanMoney" name="loanMoney" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">结算手续费</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="settlementCharge" name="settlementCharge" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">结算员</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="settlementClerk" name="settlementClerk" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">结算提交时间</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="settlementSubmitDate" name="settlementSubmitDate" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">结算提交人Id</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="settlementSubmitUserId" name="settlementSubmitUserId" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">结算审核状态（1草稿2审核中3审核通过4审核不通过）</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="settlementAuditStatus" name="settlementAuditStatus" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">佣金审核状态（1草稿2审核中3审核通过4审核不通过）</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="brokerageAuditStatus" name="brokerageAuditStatus" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">佣金发放状态</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="brokerageGrantState" name="brokerageGrantState" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">佣金总额</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="brokerageTotal" name="brokerageTotal" style="width:100%" data-options="required:true">
					</td>
				</tr>
						            </table>
		</form>
	</div>
</body>
</html>
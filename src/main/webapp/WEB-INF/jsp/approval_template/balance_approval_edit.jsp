<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/tag.jsp"%>
<%
  request.setAttribute("resourceIdentity", "myWorkbench:order");
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
		var resourceName = "审批";
  </script>
  <%@include file="/WEB-INF/jsp/common/js.jsp"%>
  <script type="text/javascript" src="${ctx}/resources/js/common/crud.js"></script>
  <script type="text/javascript" src="${ctx}/resources/js/approval_template/balance_approval_edit.js"></script>
  <script type="text/javascript">
	$(function () {
        balance_approval_edit.init();
	})
  </script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center'">
		<div id="infoTabs" class="easyui-tabs" data-options="fit:true,border:false">
			<div title="基本信息">
				<div class="easyui-layout" data-options="fit:true,selected:true">
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
								<input class="easyui-textbox contentInput"  name="loanAmount" style="width:280px" data-options="editable:false">
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
			</div>
			<!-- End 基本信息 -->

			<div title="借款">
				<div id="tb2">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						data-options="iconCls:'icon-edit'" id="updateLoan">编辑</a>
				</div>
				<table id="loanList" data-options="fit:true,border:false"></table>
			</div>
			<div title="收入项">
				<div id="tb3">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						data-options="iconCls:'icon-edit'" id="updateIncome">编辑</a>
				</div>
				<table id="incomeList" data-options="fit:true,border:false"></table>
			</div>
			<div title="支出项">
				<div id="tb4">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						data-options="iconCls:'icon-edit'" id="updatePay">编辑</a>
				</div>
				<table id="payList" data-options="fit:true,border:false"></table>
			</div>
			<div title="审批">
				<div class="easyui-layout" data-options="fit:true" >
					<div id="managerBtn" data-options="region:'north'" style="height: 185px">
						<div data-options="" >
							<a href="javascript:void(0)" class="easyui-linkbutton approvalBtn" data-options="iconCls:'icon-approve',plain:true" id="handleBtn1"><span>重新提交</span></a>
							<a href="javascript:void(0)" class="easyui-linkbutton approvalBtn" data-options="iconCls:'icon-approve',plain:true" id="handleBtn2"><span>放弃</span></a>
						</div>
						<div data-options="">
							<span style="margin: 10px;font-size: 24px">批注:</span>
							<textarea  name="comment" id="comment"  maxlength="200" style="width:99%;height:90px;margin-top: 10px;margin-bottom: 10px;"/></textarea>
						</div>
					</div>
					<div data-options="region:'center'">
						<div id="approvalList" data-options="border:false"></div>
					</div>
				</div>
			</div>
			<!-- End 审批 -->
		</div>
	</div>
	<div id="editLoanDialog">
		<form id="editLoanForm" method="post">
			<input type="hidden" id="orderLoanId" name="id">
			<table class="cententTb">
				<tr>
					<td class="tdTitle">
						<span style="color: red">*</span>实际还款日期：
					</td>
					<td class="tdContent">
						<input class="contentInput easyui-validatebox Wdate" style="width: 188px;border:solid 0.5px #989696;height: 27px;" name="repayDate" data-options="required:true,editable:false" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})">
					</td>
				</tr>
				<tr>
					<td class="tdTitle">
						<span style="color: red">*</span>实际利息：
					</td>
					<td class="tdContent">
						<input class="contentInput easyui-numberbox" style="width: 188px;" name="interest" data-options="precision:2,required:true">
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
						<span style="color: red">*</span>支付日期：
					</td>
					<td class="tdContent">
						<input class="contentInput easyui-validatebox Wdate" style="width: 188px;border:solid 0.5px #989696;height: 27px;" name="payTime" data-options="required:true,editable:false" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})">
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
<script type="text/javascript">
    var orderId=${id};
    var taskId=${taskId};
    var processInstanceId=${processInstanceId};
</script>
</html>
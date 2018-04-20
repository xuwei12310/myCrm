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
  <script type="text/javascript" src="${ctx}/resources/js/approval_template/order_approval_view.js"></script>
  <script type="text/javascript">
	$(function () {
        order_approval_view.init();
	})
  </script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center'">
		<div id="infoTabs" class="easyui-tabs" data-options="fit:true,border:false">
			<div title="基本信息">
				<div class="easyui-layout" data-options="fit:true,selected:true">
					<div data-options="region:'center',border:false">
						<form id="editModForm">
							<input type="hidden" id="orderId" name="id">
							<table>
								<tr>
									<td class="tdFirstTitle">客户</td>
									<td class="tdFirstContent">
										<input class="easyui-textbox" id="customerNameEdit" style="width:100%"
											   data-options="editable:false">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle">产品</td>
									<td class="tdFirstContent">
										<input class="easyui-textbox" id="productIdEdit" name="product.id" style="width:100%" data-options="editable:false">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle">预计完成日期</td>
									<td class="tdFirstContent">
										<input class="easyui-textbox" name="estimateFinishTime" style="width:100%" data-options="editable:false">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle">拥有人</td>
									<td class="tdFirstContent">
										<input class="easyui-textbox" id="ownerIdEdit" style="width:100%"
											   data-options="editable:false">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle">客服助理</td>
									<td class="tdFirstContent">
										<input class="easyui-textbox" id="csAssistantIdEdit" style="width:100%"
											   data-options="editable:false">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle">客服负责人</td>
									<td class="tdFirstContent">
										<input class="easyui-textbox" id="csPrincipalIdEdit" style="width:100%"
											   data-options="editable:false">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle">跟单人</td>
									<td class="tdFirstContent">
										<input class="easyui-textbox" id="followUserIdEdit" style="width:100%"
											   data-options="editable:false">
									</td>
								</tr>
							</table>
						</form>
					</div>
				</div>
			</div>
			<!-- End 基本信息 -->
			<div title="贷款方案">
				<div class="easyui-layout" data-options="fit:true,selected:true">
					<div data-options="region:'center',border:false">
						<form id="editLoanForm" method="post" action="${ctx}/myWorkbench/order/updateLoan.jhtml">
							<input type="hidden" name="id">
							<table>
								<tr>
									<td class="tdFirstTitle">银行</td>
									<td class="tdFirstContent">
										<input class="easyui-textbox" id="bankId" name="bank.id" style="width:100%"
											   data-options="editable:false">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle">银行支行</td>
									<td class="tdFirstContent">
										<input class="easyui-textbox" id="branchId" name="branch.id" style="width:100%" data-options="editable:false">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle">*贷款金额（万元）</td>
									<td class="tdFirstContent">
										<input class="easyui-textbox serviceCost" id="loanAmount" name="loanAmount" style="width:100%" data-options="editable:false">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle">*月利率（厘/月）</td>
									<td class="tdFirstContent">
										<input class="easyui-textbox" id="lendingRate" name="lendingRate" style="width:100%"
											   data-options="editable:false">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle">*贷款年限</td>
									<td class="tdFirstContent">
										<input class="easyui-textbox" id="loanTerm" name="loanTerm" style="width:100%"
											   data-options="editable:false">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle">*还款方式:</td>
									<td class="tdFirstContent">
										<input class="easyui-textbox" id="repayWayId" name="repayWay.id" style="width:100%"
											   data-options="editable:false">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle">*手续费百分比（%）</td>
									<td class="tdFirstContent">
										<input class="easyui-textbox" id="serviceChargePercent" name="serviceChargePercent" style="width:100%"
											   data-options="editable:false">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle">*手续费（元）</td>
									<td class="tdFirstContent">
										<input class="easyui-textbox" id="serviceCharge" name="serviceCharge" style="width:100%"
											   data-options="editable:false">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle">介绍人</td>
									<td class="tdFirstContent">
										<input class="easyui-textbox" id="matchmakerId" style="width:100%"
											   data-options="editable:false">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle">返佣比例（%）</td>
									<td class="tdFirstContent">
										<input class="easyui-textbox" id="commissionRate" name="commissionRate" style="width:100%"
											   data-options="editable:false">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle">返佣（元）</td>
									<td class="tdFirstContent">
										<input class="easyui-textbox" id="commissionAmount" name="commissionAmount" style="width:100%"
											   data-options="editable:false">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle">返佣理由</td>
									<td class="tdFirstContent">
										<input class="easyui-textbox" id="commissionReason" name="commissionReason" style="width:100%"
											   data-options="editable:false">
									</td>
								</tr>
							</table>
						</form>
					</div>
				</div>
			</div>
			<!-- End 贷款方案 -->
			<div title="放款抵押">
				<table id="orderPropertyList" data-options="border:false"></table>
			</div>
			<!-- End 放款抵押 -->
			<div title="收款账号">
				<table id="orderReceivablesList" data-options="border:false"></table>
			</div>
			<!-- End 收款账号 -->
			<div title="审批">
				<div class="easyui-layout" data-options="fit:true" >
					<c:if test="${!taskId.equals('undefined')}">
						<div id="managerBtn" data-options="region:'north'" style="height: 185px">
							<div data-options="" >
								<a href="javascript:void(0)" class="easyui-linkbutton approvalBtn" data-options="iconCls:'icon-approve',plain:true" id="handleBtn1"><span>同意</span></a>
								<a href="javascript:void(0)" class="easyui-linkbutton approvalBtn" data-options="iconCls:'icon-approve',plain:true" id="handleBtn2"><span>驳回</span></a>
							</div>
							<div data-options="">
								<span style="margin: 10px;font-size: 24px">批注:</span>
								<textarea  name="comment" id="comment"  maxlength="200" style="width:99%;height:90px;margin-top: 10px;margin-bottom: 10px;"/></textarea>
							</div>
						</div>
					</c:if>
					<div data-options="region:'center'">
						<div id="approvalList" data-options="border:false"></div>
					</div>
				</div>
			</div>
			<!-- End 审批 -->
		</div>
	</div>

</body>
<script type="text/javascript">
    var orderId=${orderId};
    var taskId=${taskId};
    var processInstanceId=${processInstanceId};
</script>
</html>
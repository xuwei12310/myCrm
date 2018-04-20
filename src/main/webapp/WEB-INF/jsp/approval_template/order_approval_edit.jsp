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
  <script type="text/javascript" src="${ctx}/resources/js/approval_template/order_approval_edit.js"></script>
  <script type="text/javascript">
	$(function () {
        order_approval_edit.init();
	})
  </script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center'">
		<div id="infoTabs" class="easyui-tabs" data-options="fit:true,border:false">
			<div title="基本信息">
				<div class="easyui-layout" data-options="fit:true,selected:true">
					<div data-options="region:'north',border:false"
						 style="height: 40px">
						<a id="saveUpdateBtn" href="javascript:void(0)"
						   class="easyui-linkbutton"
						   data-options="plain:true,iconCls:'icon-save'">保存</a>
					</div>
					<div data-options="region:'center',border:false">
						<form id="editModForm" method="post" action="${ctx}/myWorkbench/order/update.jhtml">
							<input type="hidden" id="orderId" name="id">
							<table>
								<tr>
									<td class="tdFirstTitle">客户</td>
									<td class="tdFirstContent">
										<input class="easyui-textbox" id="customerNameEdit"
											   style="width:100%"
											   data-options="editable:false">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle">产品</td>
									<td class="tdFirstContent">
										<input class="easyui-combobox" id="productIdEdit" name="product.id" style="width:100%" data-options="required:true">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle">预计完成日期</td>
									<td class="tdFirstContent">
										<input class="easyui-datebox" name="estimateFinishTime" style="width:100%" data-options="required:true,editable:false">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle">拥有人</td>
									<td class="tdFirstContent">
										<input type="hidden" id="ownerIdEditHidden" name="owner.id">
										<input class="easyui-textbox" id="ownerIdEdit" style="width:100%"
											   data-options="required:true,editable:false,icons:[{iconCls:'icon-search'}]">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle">客服助理</td>
									<td class="tdFirstContent">
										<input type="hidden" id="csAssistantIdEditHidden" name="csAssistant.id">
										<input class="easyui-textbox" id="csAssistantIdEdit" style="width:100%"
											   data-options="required:true,editable:false,icons:[{iconCls:'icon-search'}]">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle">客服负责人</td>
									<td class="tdFirstContent">
										<input type="hidden" id="csPrincipalIdEditHidden" name="csPrincipal.id">
										<input class="easyui-textbox" id="csPrincipalIdEdit" style="width:100%"
											   data-options="required:true,editable:false,icons:[{iconCls:'icon-search'}]">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle">跟单人</td>
									<td class="tdFirstContent">
										<input type="hidden" id="followUserIdEditHidden" name="followUser.id">
										<input class="easyui-textbox" id="followUserIdEdit" style="width:100%"
											   data-options="required:true,editable:false,icons:[{iconCls:'icon-search'}]">
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
					<div data-options="region:'north',border:false"
						 style="height: 40px">
						<a id="saveLoanBtn" href="javascript:void(0)"
						   class="easyui-linkbutton"
						   data-options="plain:true,iconCls:'icon-save'">保存</a>
					</div>
					<div data-options="region:'center',border:false">
						<form id="editLoanForm" method="post" action="${ctx}/myWorkbench/order/updateLoan.jhtml">
							<input type="hidden" name="id">
							<table>
								<tr>
									<td class="tdFirstTitle">银行</td>
									<td class="tdFirstContent">
										<input class="easyui-combobox" id="bankId" name="bank.id" style="width:100%"
											   data-options="required:true,editable:false">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle">银行支行</td>
									<td class="tdFirstContent">
										<input type="hidden" id="branchIdHidden" name="branch.id">
										<input class="easyui-textbox" id="branchId" name="branch.id" style="width:100%" data-options="required:true,editable:false,icons:[{iconCls:'icon-search'}]    ">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle">*贷款金额（万元）</td>
									<td class="tdFirstContent">
										<input class="easyui-numberbox serviceCost" id="loanAmount" name="loanAmount" style="width:100%" data-options="required:true,precision:2,">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle">*月利率（厘/月）</td>
									<td class="tdFirstContent">
										<input class="easyui-numberbox" id="lendingRate" name="lendingRate" style="width:100%"
											   data-options="required:true,precision:2,">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle">*贷款年限</td>
									<td class="tdFirstContent">
										<input class="easyui-numberbox" id="loanTerm" name="loanTerm" style="width:100%"
											   data-options="required:true,min:0">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle">*还款方式:</td>
									<td class="tdFirstContent">
										<input class="easyui-combobox" id="repayWayId" name="repayWay.id" style="width:100%"
											   data-options="required:true,editable:false,">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle">*手续费百分比（%）</td>
									<td class="tdFirstContent">
										<input class="easyui-numberbox serviceCost" id="serviceChargePercent" name="serviceChargePercent" style="width:100%"
											   data-options="required:true,precision:2,">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle">*手续费（元）</td>
									<td class="tdFirstContent">
										<input class="easyui-numberbox" id="serviceCharge" name="serviceCharge" style="width:100%"
											   data-options="required:true,precision:2,editable:false">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle">介绍人</td>
									<td class="tdFirstContent">
										<input type="hidden" id="matchmakerIdHidden" name="matchmaker.id">
										<input class="easyui-textbox" id="matchmakerId" style="width:100%"
											   data-options="editable:false,icons:[{iconCls:'icon-search'}]">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle">返佣比例（%）</td>
									<td class="tdFirstContent">
										<input class="easyui-numberbox" id="commissionRate" name="commissionRate" style="width:100%"
											   data-options="precision:2,">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle">返佣（元）</td>
									<td class="tdFirstContent">
										<input class="easyui-numberbox" id="commissionAmount" name="commissionAmount" style="width:100%"
											   data-options="precision:2,">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle">返佣理由</td>
									<td class="tdFirstContent">
										<input class="easyui-textbox" id="commissionReason" name="commissionReason" style="width:100%"
											   data-options="">
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

	<!-- 客服助理 -->
	<div id="csAssistantDialog">
		<div class="easyui-layout" data-options="fit:true,selected:true">
			<div data-options="region:'north',border:false"
				 style="height: 35px">
				<form id="csAssistantForm" method="post">
					<input class="easyui-textbox" name="name" style="width:220px" data-options="label:'姓名',labelWidth:100">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="searchCsAssistantBtn">查询</a>
				</form>
			</div>
			<div data-options="region:'center',border:false">
				<div id="csAssistantDialogList"></div>
			</div>
		</div>
	</div>

	<!-- 客户 -->
	<div id="customerDialog">
		<div class="easyui-layout" data-options="fit:true,selected:true">
			<div data-options="region:'north',border:false"
				 style="height: 35px">
				<form id="searchCustomerForm" method="post">
					<input class="easyui-textbox" name="customerName" style="width:220px" data-options="label:'姓名',labelWidth:100">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="searchCustomerBtn">查询</a>
				</form>
			</div>
			<div data-options="region:'center',border:false">
				<div id="customerDialogList"></div>
			</div>
		</div>
	</div>

	<!-- 客服负责人 -->
	<div id="csPrincipalDialog">
		<div class="easyui-layout" data-options="fit:true,selected:true">
			<div data-options="region:'north',border:false"
				 style="height: 35px">
				<form id="csPrincipalForm" method="post">
					<input class="easyui-textbox" name="name" style="width:220px" data-options="label:'姓名',labelWidth:100">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="searchCsPrincipalBtn">查询</a>
				</form>
			</div>
			<div data-options="region:'center',border:false">
				<div id="csPrincipalDialogList"></div>
			</div>
		</div>
	</div>
	<!-- 跟单人 -->
	<div id="followUserDialog">
		<div class="easyui-layout" data-options="fit:true,selected:true">
			<div data-options="region:'north',border:false"
				 style="height: 35px">
				<form id="followUserForm" method="post">
					<input class="easyui-textbox" name="name" style="width:220px" data-options="label:'姓名',labelWidth:100">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="searchFollowUserBtn">查询</a>
				</form>
			</div>
			<div data-options="region:'center',border:false">
				<div id="followUserDialogList"></div>
			</div>
		</div>
	</div>
	<!-- 拥有人 -->
	<div id="ownerDialog">
		<div class="easyui-layout" data-options="fit:true,selected:true">
			<div data-options="region:'north',border:false"
				 style="height: 35px">
				<form id="ownerForm" method="post">
					<input class="easyui-textbox" name="name" style="width:220px" data-options="label:'姓名',labelWidth:100">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="searchOwnerBtn">查询</a>
				</form>
			</div>
			<div data-options="region:'center',border:false">
				<div id="ownerDialogList"></div>
			</div>
		</div>
	</div>
	<!-- 介绍人 -->
	<div id="matchmakerDialog">
		<div class="easyui-layout" data-options="fit:true,selected:true">
			<div data-options="region:'north',border:false"
				 style="height: 35px">
				<form id="matchmakerForm" method="post">
					<input class="easyui-textbox" name="name" style="width:220px" data-options="label:'姓名',labelWidth:100">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="searchMatchmakerBtn">查询</a>
				</form>
			</div>
			<div data-options="region:'center',border:false">
				<div id="matchmakerDialogList"></div>
			</div>
		</div>
	</div>

	<!-- 房产 -->
	<div id="propertyDialog">
		<div class="easyui-layout" data-options="fit:true,selected:true">
			<div data-options="region:'north',border:false"
				 style="height: 35px">
				<form id="searchPropertyForm" method="post">

				</form>
			</div>
			<div data-options="region:'center',border:false">
				<div id="propertyDialogList"></div>
			</div>
		</div>
	</div>

	<!-- 收款账号 -->
	<div id="orderReceivablesDialog">
		<div class="easyui-layout" data-options="fit:true,selected:true">
			<div data-options="region:'center',border:false">
				<form id="receivablesForm" method="post">
					<input type="hidden" id="receivablesOrderId" name="order.id" value="${orderId}">
					<input type="hidden" name="id">
					<table>
						<tr>
							<td class="tdFirstTitle">账户类型</td>
							<td class="tdFirstContent">
								<input class="easyui-combobox" style="width:100%" id="accountType" name="accountType"
									   data-options="required:true">
							</td>
						</tr>
						<tr>
							<td class="tdFirstTitle">账户名</td>
							<td class="tdFirstContent">
								<input class="easyui-textbox" name="accountName" style="width:100%" data-options="required:true">
							</td>
						</tr>
						<tr>
							<td class="tdFirstTitle">开户行</td>
							<td class="tdFirstContent">
								<input class="easyui-textbox" name="accountBank" style="width:100%" data-options="required:true">
							</td>
						</tr>
						<tr>
							<td class="tdFirstTitle">账号</td>
							<td class="tdFirstContent">
								<input class="easyui-textbox" name="accountNumber" style="width:100%"
									   data-options="required:true">
							</td>
						</tr>
						<tr>
							<td class="tdFirstTitle">理由</td>
							<td class="tdFirstContent">
								<input class="easyui-textbox" style="width:100%" name="reason"
									   data-options="">
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>

	<!-- 支行 -->
	<div id="branchDialog">
		<div class="easyui-layout" data-options="fit:true,selected:true">
			<div data-options="region:'north',border:false"
				 style="height: 35px">
				<form id="searchBranchForm" method="post">
					<input type="hidden" id="branchBankId" name="bankid">
					<input class="easyui-textbox" name="subbranchName" style="width:220px" data-options="label:'名称',labelWidth:100">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="searchBranchBtn">查询</a>
				</form>
			</div>
			<div data-options="region:'center',border:false">
				<div id="branchDialogList"></div>
			</div>
		</div>
	</div>

</body>
<script type="text/javascript">
    var orderId=${orderId};
    var taskId=${taskId};
    var processInstanceId=${processInstanceId};
</script>
</html>
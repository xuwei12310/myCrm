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
		var resourceName = "订单";
		var resourcePath = "/myWorkbench/order";
  </script>
  <%@include file="/WEB-INF/jsp/common/js.jsp"%>
  <script type="text/javascript" src="${ctx}/resources/js/common/crud.js"></script>
  <script type="text/javascript" src="${ctx}/resources/js/myWorkbench/order.js"></script>
	<script type="text/javascript" src="${ctx}/resources/comp/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="${ctx}/resources/js/myWorkbench/orderDetails.js"></script>
	<script type="text/javascript" src="${ctx}/resources/js/common/dateUtil.js"></script>
  <script type="text/javascript">
	$(function () {
		order.init();
	})
  </script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="searchDiv" style="height: 80px;">
		<form id="searchForm" method="post">
			<input class="easyui-textbox" name="orderCode" style="width:180px" data-options="label:'订单编号',labelWidth:70">
			<input class="easyui-textbox" name="customer.customerName" style="width:180px" data-options="label:'客户名称',labelWidth:70">
			<span class="textbox combo datebox" style="font-size:0;border: 0px;">
				<label class="textbox-label" style="text-decoration:none;text-align: left; width: 79.0476px; height: 30.0952px; line-height: 30.0952px;">签约日期</label>
			<input class="easyui-validatebox Wdate dateInput" id="signingDateStart" name="signingDateStart" style="width:125px" data-options="label:'',labelWidth:70,editable:false" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'signingDateEnd\')}'})">
			</span>
			- <input class="easyui-validatebox Wdate dateInput" id="signingDateEnd" name="signingDateEnd" style="width:125px" data-options="label:'-',labelWidth:10,editable:false" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'signingDateStart\')}'})">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="searchBtn">查询</a>
			<div style="height: 5px"></div>
			<input class="easyui-textbox" id="product" name="product.id" style="width:180px" data-options="label:'产品',labelWidth:70,editable:false">
			<span class="textbox combo datebox" style="font-size:0;border: 0px;">
				<label class="textbox-label" style="text-decoration:none;text-align: left; width: 79.0476px; height: 30.0952px; line-height: 30.0952px;">预计完成日期</label>
			<input class="easyui-validatebox Wdate dateInput" id="estimateFinishTimeStart" name="estimateFinishTimeStart" style="width:125px" data-options="editable:false" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'estimateFinishTimeEnd\')}'})">
			</span>
			- <input class="easyui-validatebox Wdate dateInput" id="estimateFinishTimeEnd" name="estimateFinishTimeEnd" style="width:125px" data-options="editable:false" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'estimateFinishTimeStart\')}'})">
			<input class="easyui-textbox" name="owner.name" style="width:180px" data-options="label:'拥有人',labelWidth:70">
			<%--<input class="easyui-textbox" name="auditStatus" style="width:180px" data-options="label:'审核状态（1草稿2审核中3审核通过4审核不通过）',labelWidth:70">
			<input class="easyui-textbox" name="status" style="width:180px" data-options="label:'状态（1已立项2已签约3已放款4完结）',labelWidth:70">
			<input class="easyui-textbox" name="receivablesAccountStatus" style="width:180px" data-options="label:'收款账户审核状态（1草稿2审核中3审核通过4审核不通过）',labelWidth:70">--%>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" id="clearBtn">清空</a>
		</form>
	</div>
	<div data-options="region:'center'">
		<table id="list" data-options="border:false"></table>
	</div>
	<div id="tb">
		<%@include file="/WEB-INF/jsp/common/btn.jsp"%>
		<shiro:hasPermission name = "${resourceIdentity}:approve">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-approve',plain:true" id="approveBtn">送审</a>
		</shiro:hasPermission>
		<shiro:hasPermission name = "${resourceIdentity}:trace">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-follow',plain:true" id="traceBtn">跟踪</a>
		</shiro:hasPermission>
		<shiro:hasPermission name = "${resourceIdentity}:progress">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-end',plain:true" id="progressBtn">订单进度</a>
		</shiro:hasPermission>
		<shiro:hasPermission name = "${resourceIdentity}:end">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-end',plain:true" id="endBtn">完结</a>
		</shiro:hasPermission>
	</div>

	<div id="editDialog">
		<form id="editForm" method="post">
            <table >
				<tr>
					<td class="tdFirstTitle"><span style="color: red">*</span>客户</td>
					<td class="tdFirstContent">
						<input type="hidden" id="customerNameHidden" name="customer.id">
						<input class="easyui-textbox contentInput" id="customerName"
							   style="width:100%"
							   data-options="required:true,editable:false,icons:[{iconCls:'icon-search'}]">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle"><span style="color: red">*</span>产品</td>
					<td class="tdFirstContent">
						<input class="easyui-combobox" id="productId" name="product.id" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle"><span style="color: red">*</span>预计完成日期</td>
					<td class="tdFirstContent">
						<input class="easyui-validatebox Wdate dateInput" name="estimateFinishTime" style="width:100%" data-options="required:true,editable:false" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle"><span style="color: red">*</span>拥有人</td>
					<td class="tdFirstContent">
						<input type="hidden" id="ownerIdHidden" name="owner.id">
						<input class="easyui-textbox" id="ownerId" style="width:100%"
							   data-options="required:true,editable:false,icons:[{iconCls:'icon-search'}]">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle"><span style="color: red">*</span>跟单人</td>
					<td class="tdFirstContent">
						<input type="hidden" id="followUserIdHidden" name="followUser.id">
						<input class="easyui-textbox" id="followUserId" style="width:100%"
							   data-options="required:true,editable:false,icons:[{iconCls:'icon-search'}]">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle"><span style="color: red">*</span>客服负责人</td>
					<td class="tdFirstContent">
						<input type="hidden" id="csPrincipalIdHidden" name="csPrincipal.id">
						<input class="easyui-textbox" id="csPrincipalId" style="width:100%"
							   data-options="required:true,editable:false,icons:[{iconCls:'icon-search'}]">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle"><span style="color: red">*</span>客服助理</td>
					<td class="tdFirstContent">
						<input type="hidden" id="csAssistantIdHidden" name="csAssistant.id">
						<input class="easyui-textbox" id="csAssistantId" style="width:100%"
							   data-options="required:true,editable:false,icons:[{iconCls:'icon-search'}]">
					</td>
				</tr>
			</table>
		</form>
	</div>

	<div id="modEditDialog">
		<div id="updateTabs" class="easyui-tabs" data-options="fit:true,border:false">

			<shiro:hasPermission name = "${resourceIdentity}:basic">
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
											<input type="hidden" id="customerNameEditHidden">
											<input class="easyui-textbox contentInput" id="customerNameEdit"
												   style="width:100%"
												   data-options="required:true,editable:false">
										</td>
									</tr>
									<tr>
										<td class="tdFirstTitle">产品</td>
										<td class="tdFirstContent">
											<input class="easyui-textbox" id="productIdEdit" style="width:100%" data-options="required:true,editable:false">
										</td>
									</tr>
									<tr>
										<td class="tdFirstTitle"><span style="color: red">*</span>预计完成日期</td>
										<td class="tdFirstContent">
											<input class="easyui-validatebox Wdate dateInput" name="estimateFinishTime" style="width:100%" data-options="required:true,editable:false" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})">
										</td>
									</tr>
									<tr>
										<td class="tdFirstTitle"><span style="color: red">*</span>拥有人</td>
										<td class="tdFirstContent">
											<input type="hidden" id="ownerIdEditHidden" name="owner.id">
											<input class="easyui-textbox" id="ownerIdEdit" style="width:100%"
												   data-options="required:true,editable:false,icons:[{iconCls:'icon-search'}]">
										</td>
									</tr>
									<tr>
										<td class="tdFirstTitle"><span style="color: red">*</span>跟单人</td>
										<td class="tdFirstContent">
											<input type="hidden" id="followUserIdEditHidden" name="followUser.id">
											<input class="easyui-textbox" id="followUserIdEdit" style="width:100%"
												   data-options="required:true,editable:false,icons:[{iconCls:'icon-search'}]">
										</td>
									</tr>
									<tr>
										<td class="tdFirstTitle"><span style="color: red">*</span>客服负责人</td>
										<td class="tdFirstContent">
											<input type="hidden" id="csPrincipalIdEditHidden" name="csPrincipal.id">
											<input class="easyui-textbox" id="csPrincipalIdEdit" style="width:100%"
												   data-options="required:true,editable:false,icons:[{iconCls:'icon-search'}]">
										</td>
									</tr>
									<tr>
										<td class="tdFirstTitle"><span style="color: red">*</span>客服助理</td>
										<td class="tdFirstContent">
											<input type="hidden" id="csAssistantIdEditHidden" name="csAssistant.id">
											<input class="easyui-textbox" id="csAssistantIdEdit" style="width:100%"
												   data-options="required:true,editable:false,icons:[{iconCls:'icon-search'}]">
										</td>
									</tr>
								</table>
							</form>
						</div>
					</div>
				</div>
			</shiro:hasPermission>
			<!-- End 基本信息 -->
			<shiro:hasPermission name = "${resourceIdentity}:loanProgram">
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
											   data-options="editable:false">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle">银行支行</td>
									<td class="tdFirstContent">
										<input type="hidden" id="branchIdHidden" name="branch.id">
										<input class="easyui-textbox" id="branchId" name="branch.id" style="width:100%" data-options="editable:false,icons:[{iconCls:'icon-search'}]    ">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle"><span style="color: red">*</span>贷款金额（万元）</td>
									<td class="tdFirstContent">
										<input class="easyui-numberbox serviceCost" id="loanAmount" name="loanAmount" style="width:100%" data-options="required:true,precision:2,">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle"><span style="color: red">*</span>月利率（厘/月）</td>
									<td class="tdFirstContent">
										<input class="easyui-numberbox" id="lendingRate" name="lendingRate" style="width:100%"
											   data-options="required:true,precision:2,">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle"><span style="color: red">*</span>贷款年限</td>
									<td class="tdFirstContent">
										<input class="easyui-numberbox" id="loanTerm" name="loanTerm" style="width:100%"
											   data-options="required:true,min:0">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle"><span style="color: red">*</span>还款方式:</td>
									<td class="tdFirstContent">
										<input class="easyui-combobox" id="repayWayId" name="repayWay.id" style="width:100%"
											   data-options="required:true,editable:false,">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle"><span style="color: red">*</span>手续费百分比（%）</td>
									<td class="tdFirstContent">
										<input class="easyui-numberbox serviceCost" id="serviceChargePercent" name="serviceChargePercent" style="width:100%"
											   data-options="required:true,precision:2">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle"><span style="color: red">*</span>手续费（元）</td>
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
			</shiro:hasPermission>
			<!-- End 贷款方案 -->
			<shiro:hasPermission name = "${resourceIdentity}:property">
				<div title="抵押房产">
					<table id="orderPropertyList" data-options="border:false"></table>
				</div>
			</shiro:hasPermission>
			<!-- End 抵押房产 -->
			<shiro:hasPermission name = "${resourceIdentity}:receivables">
				<div title="收款账号">
					<table id="orderReceivablesList" data-options="border:false"></table>
				</div>
			</shiro:hasPermission>
			<!-- End 收款账号 -->
			<shiro:hasPermission name = "${resourceIdentity}:loan">
				<div title="借款">
					<table id="orderLoanList" data-options="border:false"></table>
				</div>
			</shiro:hasPermission>
			<!-- End 借款 -->
			<shiro:hasPermission name = "${resourceIdentity}:material">
				<div title="订单材料">
					<table id="materialsList" data-options="border:false"></table>
				</div>
			</shiro:hasPermission>
			<!-- End 订单材料 -->
			<shiro:hasPermission name = "${resourceIdentity}:bank">
				<div title="合作银行">
					<table id="bankList" data-options="border:false"></table>
				</div>
			</shiro:hasPermission>
			<!-- End 合作银行 -->
			<shiro:hasPermission name = "${resourceIdentity}:company">
				<div title="评估公司">
					<table id="companyList" data-options="border:false"></table>
				</div>
			</shiro:hasPermission>
			<!-- End 负债 -->
			<shiro:hasPermission name = "${resourceIdentity}:borrow">
				<div title="放款">
				<div class="easyui-layout" data-options="fit:true,selected:true">
					<div data-options="region:'north',border:false"
						 style="height: 40px">
						<a id="saveBorrowBtn" href="javascript:void(0)"
						   class="easyui-linkbutton"
						   data-options="plain:true,iconCls:'icon-save'">保存</a>
					</div>
					<div data-options="region:'center',border:false">
						<form id="editBorrowForm" method="post" action="${ctx}/myWorkbench/order/updateBorrow.jhtml">
							<input type="hidden" id="borrowId" name="id">
							<table>
								<tr>
									<td class="tdFirstTitle">贷款金额（万元）</td>
									<td class="tdFirstContent">
										<input class="easyui-textbox" id="borrowMoney" style="width:100%"
											   data-options="required:true,editable:false">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle"><span style="color: red">*</span>放款金额（万元）</td>
									<td class="tdFirstContent">
										<input class="easyui-numberbox" id="loanMoney" name="loanMoney" style="width:100%" data-options="required:true,precision:4">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle"><span style="color: red">*</span>放款日期</td>
									<td class="tdFirstContent">
										<input class="easyui-validatebox Wdate dateInput" id="loanDate" name="loanDate" style="width:100%" data-options="required:true,editable:false" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle"><span style="color: red">*</span>手续费百分比（%）</td>
									<td class="tdFirstContent">
										<input class="easyui-numberbox" id="borrowServiceChargePercent" name="serviceChargePercent" style="width:100%"
											   data-options="required:true,precision:2,">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle"><span style="color: red">*</span>手续费（元）</td>
									<td class="tdFirstContent">
										<input class="easyui-numberbox" id="borrowServiceCharge" name="serviceCharge" style="width:100%"
											   data-options="required:true,precision:2">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle">介绍人</td>
									<td class="tdFirstContent">
										<input class="easyui-textbox" id="borrowMatchmakerId" style="width:100%"
											   data-options="editable:false,">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle">返佣比例（%）</td>
									<td class="tdFirstContent">
										<input class="easyui-numberbox" id="borrowCommissionRate" style="width:100%"
											   data-options="precision:2,editable:false">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle">返佣（元）</td>
									<td class="tdFirstContent">
										<input class="easyui-numberbox" id="borrowCommissionAmount"  style="width:100%"
											   data-options="precision:2,editable:false">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle">返佣理由</td>
									<td class="tdFirstContent">
										<input class="easyui-textbox" id="borrowCommissionReason" style="width:100%"
											   data-options="editable:false">
									</td>
								</tr>
							</table>
						</form>
					</div>
				</div>
			</div>
			</shiro:hasPermission>
			<!-- End 负债 -->
		</div>
	</div>


	<!-- 客户 -->
	<div id="customerDialog">
		<div class="easyui-layout" data-options="fit:true,selected:true">
			<div data-options="region:'north',border:false"
				 style="height: 40px;margin-top: 6px;margin-left: 3px">
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

	<!-- 房产 -->
	<div id="propertyDialog">
		<div class="easyui-layout" data-options="fit:true,selected:true">
			<div data-options="region:'center',border:false">
				<div id="propertyDialogList"></div>
			</div>
		</div>
	</div>

	<!-- 支行 -->
	<div id="branchDialog">
		<div class="easyui-layout" data-options="fit:true,selected:true">
			<div data-options="region:'north',border:false"
				 style="height: 40px;margin-top: 6px;margin-left: 3px">
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

	<!-- 客服助理 -->
	<div id="csAssistantDialog">
		<div class="easyui-layout" data-options="fit:true,selected:true">
			<div data-options="region:'north',border:false"
				 style="height: 40px;margin-top: 6px;margin-left: 3px">
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

	<!-- 客服负责人 -->
	<div id="csPrincipalDialog">
		<div class="easyui-layout" data-options="fit:true,selected:true">
			<div data-options="region:'north',border:false"
				 style="height: 40px;margin-top: 6px;margin-left: 3px">
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
				 style="height: 40px;margin-top: 6px;margin-left: 3px">
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
				 style="height: 40px;margin-top: 6px;margin-left: 3px">
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
				 style="height: 40px;margin-top: 6px;margin-left: 3px">
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

	<!-- 新增介绍人 -->
	<div id="matchmakerAddDialog">
		<div class="easyui-layout" data-options="fit:true,selected:true">
			<div data-options="region:'center',border:false">
				<form id="matchmakerAddForm" method="post" action="${ctx}/dict/otherPartners/create.jhtml">
					<input type="hidden" name="id">
					<table class="contentTb">
						<tr>
							<td class="tdTitle"><span style="color: red">*</span>名称：</td>
							<td class="tdContent">
								<input class="easyui-textbox contentInput" name="name"  data-options="required:true">
							</td>
						</tr>
						<tr>
							<td class="tdTitle">地址：</td>
							<td class="tdContent">
								<input class="easyui-textbox contentInput" name="address"  >
							</td>
						</tr>
						<tr>
							<td class="tdTitle">联系电话：</td>
							<td class="tdContent">
								<input class="easyui-textbox contentInput" name="telephone" >
							</td>
						</tr>
						<tr>
							<td class="tdTitle"><span style="color: red">*</span>状态：</td>
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
		</div>
	</div>

	<!-- 收款账号 -->
	<div id="orderReceivablesDialog">
		<div class="easyui-layout" data-options="fit:true,selected:true">
			<div data-options="region:'center',border:false">
				<form id="receivablesForm" method="post">
					<input type="hidden" id="receivablesOrderId" name="order.id">
					<input type="hidden" name="id">
					<table>
						<tr>
							<td class="tdFirstTitle"><span style="color: red">*</span>账户类型</td>
							<td class="tdFirstContent">
								<input class="easyui-combobox" style="width:100%" id="accountType" name="accountType"
									   data-options="required:true">
							</td>
						</tr>
						<tr>
							<td class="tdFirstTitle"><span style="color: red">*</span>账户名</td>
							<td class="tdFirstContent">
								<input class="easyui-textbox" name="accountName" style="width:100%" data-options="required:true">
							</td>
						</tr>
						<tr>
							<td class="tdFirstTitle"><span style="color: red">*</span>开户行</td>
							<td class="tdFirstContent">
								<input class="easyui-textbox" name="accountBank" style="width:100%" data-options="required:true">
							</td>
						</tr>
						<tr>
							<td class="tdFirstTitle"><span style="color: red">*</span>账号</td>
							<td class="tdFirstContent">
								<input class="easyui-validatebox textbox" name="accountNumber" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)" onblur="this.v();" style="width: 146px;height: 30px;line-height: 30px;margin: 0px;height: 30px;line-height: 30px;padding-left: 10px;padding-right: 10px;"
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

	<!-- 借款 -->
	<div id="orderLoanDialog">
		<div class="easyui-layout" data-options="fit:true,selected:true">
			<div data-options="region:'center',border:false">
				<form id="LoanForm" method="post">
					<input type="hidden" id="LoanOrderId" name="order.id">
					<input type="hidden" name="id">
					<table>
						<tr>
							<td class="tdFirstTitle"><span style="color: red">*</span>借款金额（万元）</td>
							<td class="tdFirstContent">
								<input class="easyui-numberbox interest" style="width:100%" name="amount" id="amount"
									   data-options="required:true,min:0,precision:4">
							</td>
						</tr>
						<tr>
							<td class="tdFirstTitle"><span style="color: red">*</span>借款利率(%)</td>
							<td class="tdFirstContent">
								<input class="easyui-numberbox interest" name="rate" id="rate" style="width:100%" data-options="required:true,precision:2,min:0">
							</td>
						</tr>
						<tr>
							<td class="tdFirstTitle"><span style="color: red">*</span>利率单位</td>
							<td class="tdFirstContent">
								<input class="easyui-combobox" style="width:100%" id="rateType" name="rateType"
									   data-options="required:true">
							</td>
						</tr>
						<tr>
							<td class="tdFirstTitle"><span style="color: red">*</span>借款日期</td>
							<td class="tdFirstContent">
								<input class="easyui-validatebox Wdate dateInput loanDay" name="beginDate" id="beginDate" style="width:100%" data-options="required:true,editable:false" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'estimateRepayDate\')}'})">
							</td>
						</tr>
						<tr>
							<td class="tdFirstTitle"><span style="color: red">*</span>预计还款日期</td>
							<td class="tdFirstContent">
								<input class="easyui-validatebox Wdate dateInput loanDay" name="estimateRepayDate" id="estimateRepayDate" style="width:100%"
									   data-options="required:true,editable:false" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'beginDate\')}'})">
							</td>
						</tr>
						<tr>
							<td class="tdFirstTitle"><span style="color: red">*</span>预计借款天数</td>
							<td class="tdFirstContent">
								<input class="easyui-numberbox interest" style="width:100%" name="estimateLoanDay" id="estimateLoanDay"
									   data-options="required:true,min:1,editable:false">
							</td>
						</tr>
						<tr>
							<td class="tdFirstTitle"><span style="color: red">*</span>预计利息（元）</td>
							<td class="tdFirstContent">
								<input class="easyui-numberbox" style="width:100%" name="estimateInterest" id="estimateInterest"
									   data-options="required:true,precision:2,min:0">
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>

	<!-- 材料 -->
	<div id="orderMaterialsDialog">
		<div class="easyui-layout" data-options="fit:true,selected:true">
			<div data-options="region:'center',border:false">
				<form id="materialsForm" method="post" enctype="multipart/form-data">
					<input type="hidden" id="materialsOrderId" name="order.id">
					<input type="hidden" name="id">
					<table>
						<tr>
							<td class="tdFirstTitle"><span style="color: red">*</span>名称</td>
							<td class="tdFirstContent">
								<input class="easyui-textbox" style="width:100%" name="name"
									   data-options="required:true,">
							</td>
						</tr>
						<tr>
							<td class="tdFirstTitle">注意事项</td>
							<td class="tdFirstContent">
								<input class="easyui-textbox" name="notice" style="width:100%" data-options="">
							</td>
						</tr>
						<tr>
							<td class="tdFirstTitle"><span style="color: red">*</span>份数</td>
							<td class="tdFirstContent">
								<input class="easyui-numberbox" name="number" style="width:100%" data-options="required:true,min:1">
							</td>
						</tr>
						<tr>
							<td class="tdFirstTitle"><span style="color: red">*</span>是否完成</td>
							<td class="tdFirstContent">
								<input class="easyui-combobox" id="isFinish" name="isFinish" style="width:100%"
									   data-options="required:true,editable:false">
							</td>
						</tr>
						<tr>
							<td class="tdFirstTitle">附件</td>
							<td class="tdFirstContent">
								<input class="easyui-filebox" id="attachName" name="file" style="width: 100%;"
									   data-options="prompt:'选择附件',buttonText:'选择附件',accept:'image/png,image/jpeg,image/gif'">
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>

	<!-- 合作银行 -->
	<div id="orderBankDialog">
		<div class="easyui-layout" data-options="fit:true,selected:true">
			<div data-options="region:'center',border:false">
				<form id="bankForm" method="post">
					<input type="hidden" id="bankOrderId" name="order.id">
					<input type="hidden" name="id">
					<table>
						<tr>
							<td class="tdFirstTitle"><span style="color: red">*</span>银行</td>
							<td class="tdFirstContent">
								<input class="easyui-combobox" style="width:100%" name="bank.id" id="bank"
									   data-options="required:true,editable:false">
							</td>
						</tr>
						<tr>
							<td class="tdFirstTitle"><span style="color: red">*</span>支行</td>
							<td class="tdFirstContent">
								<input type="hidden" id="subbranchHidden" name="subbranch.id">
								<input class="easyui-textbox" id="subbranch" style="width:100%" data-options="required:true,editable:false,icons:[{iconCls:'icon-search'}]">
							</td>
						</tr>
						<tr>
							<td class="tdFirstTitle">备注</td>
							<td class="tdFirstContent">
								<input class="easyui-textbox" name="note" style="width:100%"
									   data-options="">
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>

	<!-- 合作银行 签约时间-->
	<div id="orderBankTimeDialog">
		<div class="easyui-layout" data-options="fit:true,selected:true">
			<div data-options="region:'center',border:false">
				<table>
					<input type="hidden" id="signOrderBankid">
					<tr>
						<td class="tdFirstTitle"><span style="color: red">*</span>签约时间</td>
						<td class="tdFirstContent">
							<input class="easyui-validatebox Wdate dateInput" style="width:100%" name="singTime" id="singTime"
								   data-options="required:true,editable:false" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})">
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>

	<!-- 评估公司 -->
	<div id="orderCompanyDialog">
		<div class="easyui-layout" data-options="fit:true,selected:true">
			<div data-options="region:'center',border:false">
				<form id="companyForm" method="post">
					<input type="hidden" id="companyOrderId" name="order.id">
					<input type="hidden" name="id">
					<table>
						<tr>
							<td class="tdFirstTitle"><span style="color: red">*</span>评估公司</td>
							<td class="tdFirstContent">
								<input class="easyui-combobox" style="width:100%" name="assessmentCompany.id" id="assessmentCompanyId"
									   data-options="required:true,editable:false">
							</td>
						</tr>
						<tr>
							<td class="tdFirstTitle"><span style="color: red">*</span>开户行</td>
							<td class="tdFirstContent">
								<input class="easyui-textbox" id="companyBank" name="bank" style="width:100%" data-options="required:true">
							</td>
						</tr>
						<tr>
							<td class="tdFirstTitle"><span style="color: red">*</span>账户</td>
							<td class="tdFirstContent">
								<input class="easyui-textbox" name="account" id="account" style="width:100%" data-options="required:true">
							</td>
						</tr>
						<tr>
							<td class="tdFirstTitle"><span style="color: red">*</span>卡号</td>
							<td class="tdFirstContent">
								<input class="easyui-validatebox textbox" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)" onblur="this.v();"  id="cardNumber" name="cardNumber" style="width: 146px;height: 30px;line-height: 30px;margin: 0px;height: 30px;line-height: 30px;padding-left: 10px;padding-right: 10px;"
									   data-options="required:true">
							</td>
						</tr>
						<tr>
							<td class="tdFirstTitle"><span style="color: red">*</span>评估结算</td>
							<td class="tdFirstContent">
								<input class="easyui-combobox" id="isAssessment" name="isAssessment" style="width:100%"
									   data-options="required:true">
							</td>
						</tr>
						<tr>
							<td class="tdFirstTitle">评估费</td>
							<td class="tdFirstContent">
								<input class="easyui-numberbox" name="assessmenFee" style="width:100%"
									   data-options="precision:2,min:0">
							</td>
						</tr>
						<tr>
							<td class="tdFirstTitle">工本费</td>
							<td class="tdFirstContent">
								<input class="easyui-numberbox" name="fee" style="width:100%"
									   data-options="precision:2,min:0">
							</td>
						</tr>
						<tr>
							<td class="tdFirstTitle">备注</td>
							<td class="tdFirstContent">
								<input class="easyui-textbox" name="note" style="width:100%"
									   data-options="">
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>

	<!-- 借款审批历史 -->
	<div id="loanHistoryDialog">
		<div class="easyui-layout" data-options="fit:true,selected:true">
			<div data-options="region:'center',border:false">
				<div id="approvalList" data-options="border:false"></div>
			</div>
		</div>
	</div>
	<!-- 订单进度 -->
	<div id="orderProgressDialog">
		<div class="easyui-layout" data-options="fit:true">
			<table id="orderProgressTable">
				<tr>
					<td class="tdFirstTitle">订单编号</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="orderCode" style="width:100%"
							   data-options="editable:false">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">客户名称</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="orderCustomer" style="width:100%" data-options="editable:false">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">产品名称</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="orderProductName" style="width:100%" data-options="editable:false">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">贷款金额</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="orderAmount" style="width:100%"
							   data-options="editable:false">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">实际放款</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="orderLoan" style="width:100%"
							   data-options="editable:false">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">签约日期</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="orderSignDate" style="width:100%"
							   data-options="editable:false">
					</td>
				</tr>
			</table>
		</div>
	</div>

</body>
</html>
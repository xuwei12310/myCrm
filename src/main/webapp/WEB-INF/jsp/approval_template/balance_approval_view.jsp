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
  <script type="text/javascript" src="${ctx}/resources/js/approval_template/balance_approval_view.js"></script>
  <script type="text/javascript">
	$(function () {
        balance_approval_view.init();
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
				<table id="loanList" data-options="fit:true,border:false"></table>
			</div>
			<div title="收入项">
				<table id="incomeList" data-options="fit:true,border:false"></table>
			</div>
			<div title="支出项">
				<table id="payList" data-options="fit:true,border:false"></table>
			</div>
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
    var orderId=${id};
    var taskId=${taskId};
    var processInstanceId=${processInstanceId};
</script>
</html>
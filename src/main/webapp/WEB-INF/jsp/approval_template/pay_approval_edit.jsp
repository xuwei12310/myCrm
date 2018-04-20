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
  <script type="text/javascript" src="${ctx}/resources/js/approval_template/pay_approval_edit.js"></script>
  <script type="text/javascript">
	$(function () {
        pay_approval_edit.init();
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
					<form id="editForm" method="post" style="padding-bottom: 6px;padding-top: 5px">
						<input type="hidden" name="id">
						<input type="hidden" id="orderId" name="order.id">
						<input type="hidden" id="objectId" name="objectId">
			            <table >
							<tr>
								<td class="tdTitle" style="width: 85px"><span style="color: red">*</span>费用类型：</td>
								<td class="tdContent">
									<input class="easyui-combobox" id="costTypeId" name="costType.id" style="width:250px" data-options="required:true,editable:false">
								</td>
							</tr>
							<tr>
								<td class="tdTitle"><span style="color: red">*</span>是否成本：</td>
								<td class="tdContent">
									<input class="easyui-combobox" id="isCost" name="isCost" style="width:250px" data-options="required:true">
								</td>
							</tr>
							<tr>
								<td class="tdTitle"><span style="color: red">*</span>对象类型：</td>
								<td class="tdContent">
									<input class="easyui-combobox" id="payObjectType" name="payObjectType" style="width:250px" data-options="required:true">
								</td>
							</tr>
							<tr>
								<td class="tdTitle">订单编号：</td>
								<td class="tdContent">
									<input class="easyui-textbox" id="order" name="orderCode" style="width:250px" data-options="editable:false,icons:[{iconCls:'icon-search'}]">
								</td>
							</tr>
							<tr>
								<td class="tdTitle"><span style="color: red">*</span>支出对象：</td>
								<td class="tdContent">
									<input class="easyui-textbox" id="object" name="object" style="width:250px" data-options="required:true,editable:false,icons:[{iconCls:'icon-search'}]">
								</td>
							</tr>
							<tr>
								<td class="tdTitle"><span style="color: red">*</span>金额（元）：</td>
								<td class="tdContent">
									<input class="easyui-numberbox" id="payAmount" name="payAmount" style="width:250px" data-options="required:true,precision:2">
								</td>
							</tr>
							<tr>
								<td class="tdTitle"><span style="color: red">*</span>账户名：</td>
								<td class="tdContent">
									<input class="easyui-textbox" id="receiveAccountName" name="receiveAccountName" style="width:250px" data-options="required:true">
								</td>
							</tr>
						
							<tr>
								<td class="tdTitle"><span style="color: red">*</span>收款账号：</td>
								<td class="tdContent">
									<input class="easyui-numberbox" id="receiveAccountNumber" name="receiveAccountNumber" style="width:250px" data-options="required:true">
								</td>
							</tr>
							<tr>
								<td class="tdTitle"><span style="color: red">*</span>开户行：</td>
								<td class="tdContent">
									<input class="easyui-textbox" id="receiveAccountBank" name="receiveAccountBank" style="width:250px" data-options="required:true">
								</td>
							</tr>
							<tr>
								<td class="tdTitle"><span style="color: red">*</span>付款账号：</td>
								<td class="tdContent">
									<input class="easyui-combobox" id="payBankAccount" name="payBankAccount.id" style="width:250px" data-options="required:true,editable:false">
								</td>
							</tr>
							<tr>
								<td class="tdTitle"><span style="color: red">*</span>支出日期：</td>
								<td class="tdContent">
									<input class="easyui-validatebox Wdate" id="payTime" name="payTime" style="width:248px;border:solid 0.5px #989696;background-color:#e6e6e6;height:28px" data-options="required:true,editable:false" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})">
								</td>
							</tr>
							<tr>
								<td class="tdTitle">备注：</td>
								<td class="tdContent">
									<input class="easyui-textbox" id="note" name="note" style="width:250px">
								</td>
							</tr>
						</table>
						</form>
					</div>
				</div>
			</div>
			<!-- End 基本信息 -->
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
	<!-- 订单 -->
	<div id="orderDialog">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north',border:false" style="padding:5px">
				<form id="orderSearchForm" method="post">
							<input class="easyui-textbox" id="orderSearch" name="orderCode" style="width:190px" data-options="label:'订单编号',labelWidth:70">
							<input class="easyui-textbox" id="customerSearch" name="customer.customerName" style="width:190px" data-options="label:'客户名称',labelWidth:70">
							<input class="easyui-textbox" id="" name="owner.name" style="width:190px" data-options="label:'拥有人',labelWidth:70">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="orderSearchBtn">查询</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" id="orderClearBtn">清空</a>
				</form>
			</div>
			<div id="tb2">
				<shiro:hasPermission name="${resourceIdentity}:create">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" id="orderSelected">选择</a>
				</shiro:hasPermission>
			</div>
			<div data-options="region:'center'">
				<table id="orderList" data-options="border:false"></table>
			</div>
		</div>
	</div>
	<!-- 支出对象 -->
	<div id="objectDialog">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north',border:false" style="padding:5px">
				<form id="objectSearchForm" method="post">
							<input class="easyui-textbox" id="objectSearch" name="name" style="width:190px" data-options="label:'名称',labelWidth:60">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="objectSearchBtn">查询</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" id="objectClearBtn">清空</a>
				</form>
			</div>
			<div id="tb3">
				<shiro:hasPermission name="${resourceIdentity}:create">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" id="objectSelected">选择</a>
				</shiro:hasPermission>
			</div>
			<div data-options="region:'center'">
				<table id="objectList" data-options="border:false"></table>
			</div>
		</div>
	</div>


</body>
<script type="text/javascript">
    var payId=${id};
    var taskId=${taskId};
    var processInstanceId=${processInstanceId};
</script>
</html>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/tag.jsp"%>
<%
  request.setAttribute("resourceIdentity", "financial:brokerageDistribute");
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
  <script type="text/javascript" src="${ctx}/resources/js/approval_template/brokerage_audit_view.js"></script>
  <script type="text/javascript">
	$(function () {
		brokerage_audit_view.init();
	})
  </script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center'">
		<div id="infoTabs" class="easyui-tabs" data-options="fit:true,border:false">
			<div title="基本信息">
				<table>
					<tr>
						<td class="tdFirstTitle"><span style="font-size: 18px">合同编号:</span></td>
						<td class="tdFirstContent">
							<span id="orderCodeBD" style="font-size: 18px;margin-left: 12px"></span>
						</td>
					</tr>
					<tr>
						<td class="tdFirstTitle"><span style="font-size: 18px">签约时间:</span></td>
						<td class="tdFirstContent">
							<span id="signingDateBD" style="font-size: 18px;margin-left: 12px"></span>
						</td>
					</tr>
					<tr>
						<td class="tdFirstTitle"><span style="font-size: 18px">客户:</span></td>
						<td class="tdFirstContent">
							<span id="customerNameBD" style="font-size: 18px;margin-left: 12px"></span>
						</td>
					</tr>
					<tr>
						<td class="tdFirstTitle"><span style="font-size: 18px">订单总收入:</span></td>
						<td class="tdFirstContent">
							<span id="receiveAmountBD" style="font-size: 18px;margin-left: 12px"></span>
						</td>
					</tr>
					<tr>
						<td class="tdFirstTitle"><span style="font-size: 18px">订单总支出:</span></td>
						<td class="tdFirstContent">
							<span id="loanMoneyBD" style="font-size: 18px;margin-left: 12px"></span>
						</td>
					</tr>
				</table>
			</div>
			<!-- End 基本信息 -->
	        <div title="结算信息">
	            <table id="balanceList" data-options="border:false"></table>
	        </div>
	        <!-- End 结算信息-->
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
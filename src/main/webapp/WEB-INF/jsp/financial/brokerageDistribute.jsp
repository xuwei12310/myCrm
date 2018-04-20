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
		var resourceName = "佣金分配";
		var resourcePath = "/financial/brokerageDistribute";
  </script>
  <%@include file="/WEB-INF/jsp/common/js.jsp"%>
  <script type="text/javascript" src="${ctx}/resources/js/common/crud.js"></script>
  <script type="text/javascript" src="${ctx}/resources/js/financial/brokerageDistribute.js"></script>
  <script type="text/javascript">
	$(function () {
		brokerageDistribute.init();
	})
  </script>
  <style> 
	.spans{ color:#FF0000} 
</style>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="searchDiv">
		<form id="searchForm" method="post">
			<input class="easyui-textbox" name="orderCode" style="width:220px" data-options="label:'订单合同',labelWidth:100">
			<input class="easyui-textbox" name="brokerageGrantState" id="brokerageGrantState" style="width:220px" data-options="label:'发放状态',labelWidth:100">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="searchBtn">查询</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" id="clearBtn">清空</a>
		</form>
	</div>
	<div data-options="region:'center'">
		<table id="list" data-options="border:false"></table>
	</div>
	<div id="tb">
		<shiro:hasPermission name = "${resourceIdentity}:update">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" id="updateBtn">编辑</a>
		</shiro:hasPermission>
		<shiro:hasPermission name = "${resourceIdentity}:verify">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-approve',plain:true" id="verifyBtn">送审</a>
		</shiro:hasPermission>
		<shiro:hasPermission name = "${resourceIdentity}:grant">
			<a href="javascript:void(0)" class="easyui-menubutton" menu="#grantType" data-options="plain:true" id="grantBtn">发放</a>
    		<div id="grantType" style="width:100px;">
       	 		<div>已发放</div>
        		<div>未发放</div>
    		</div>
		</shiro:hasPermission>
		<shiro:hasPermission name = "${resourceIdentity}:trace">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-follow',plain:true" id="traceBtn">跟踪</a>
		</shiro:hasPermission>
	</div>
	<div id="BDEditDialog">
		<input type="hidden" name="id" id="idUpdate">
		<input type="hidden" id="btype">
		<div id="updateBDTabs" class="easyui-tabs" data-options="fit:true,border:false">
			<div title="基本信息">
				<table>
					<tr>
						<td class="tdFirstTitle"><span style="font-size: 13px">合同编号:</span></td>
						<td class="tdFirstContent">
							<span id="orderCodeBD" style="font-size: 13px;margin-left: 12px"></span>
						</td>
					</tr>
					<tr>
						<td class="tdFirstTitle"><span style="font-size: 13px">签约时间:</span></td>
						<td class="tdFirstContent">
							<span id="signingDateBD" style="font-size: 13px;margin-left: 12px"></span>
						</td>
					</tr>
					<tr>
						<td class="tdFirstTitle"><span style="font-size: 13px">客户:</span></td>
						<td class="tdFirstContent">
							<span id="customerNameBD" style="font-size: 13px;margin-left: 12px"></span>
						</td>
					</tr>
					<tr>
						<td class="tdFirstTitle"><span style="font-size: 13px">订单总收入:</span></td>
						<td class="tdFirstContent">
							<span id="receiveAmountBD" style="font-size: 13px;margin-left: 12px"></span>
						</td>
					</tr>
					<tr>
						<td class="tdFirstTitle"><span style="font-size: 13px">订单总支出:</span></td>
						<td class="tdFirstContent">
							<span id="loanMoneyBD" style="font-size: 13px;margin-left: 12px"></span>
						</td>
					</tr>
				</table>
			</div>
			<!-- End 基本信息 -->
	        <div title="结算信息">
	            <table id="balanceList" data-options="border:false"></table>
	        </div>
		</div>
	</div>
	<div id="modBDEditDialog">
		<form id="modBDForm" method="post">
            <table>
            	<tr>
					<td class="tdFirstTitle">订单角色</td>
					<td class="tdFirstContent">
						<span id = "ownerTypeName"></span>
					</td>
				</tr>
				<tr>
					<td class="tdTitle">姓名</td>
					<td class="tdContent">
						<input type="hidden" name="bid" id="brokerageId">
						<span id = "ownerNames"></span>
					</td>
				</tr>
				<tr>
					<td class="tdTitle">参考值</td>
					<td class="tdContent">
						<input class="easyui-textbox contentInput" name="reference" readonly="readonly">
					</td>
				</tr>
				<tr>
					<td class="tdTitle"><span class="spans">*</span>实际值</td>
					<td class="tdContent">
						<input class="easyui-textbox contentInput" name="actual"  data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdTitle">备注</td>
					<td class="tdContent">
						<input class="easyui-textbox contentInput" name="note"  style="height:100px" data-options="multiline:true">
					</td>
				</tr>
				</table>
		</form>
	</div>
</body>
</html>
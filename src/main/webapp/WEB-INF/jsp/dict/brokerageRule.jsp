<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/tag.jsp"%>
<%
  request.setAttribute("resourceIdentity", "dict:brokerageRule");
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
		var resourceName = "佣金规则";
		var resourcePath = "/dict/brokerageRule";
  </script>
  <%@include file="/WEB-INF/jsp/common/js.jsp"%>
  <script type="text/javascript" src="${ctx}/resources/js/common/crud.js"></script>
  <script type="text/javascript" src="${ctx}/resources/js/dict/brokerageRule.js"></script>
  <script type="text/javascript">
	$(function () {
		brokerageRule.init();
	})
  </script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" style="width:100%; height:50px;">
			<div style="font-size:15px;margin-top:15px;margin-left:15px;">业务佣金规则</div> 
	</div>
	<div id="editDialog" data-options="region:'center'">
		<form id="editForm" method="post">
		<input type="hidden" name="id">
		<table>
			<tr>
				<td class="tdTitle"><div style="font-size:16px;margin-top:15px;margin-left:15px;margin-right:5px;margin-bottom:5px;">拥有人：</div></td>
				<td class="tdContent">
					<div style="margin-top:15px;margin-left:15px;margin-right:5px;margin-bottom:5px;">
						<input class="easyui-textbox contentInput" type="number" name="ruleOwner" style="width: 400px;height: 35px" data-options="required:true">%
					</div>
				</td>
			</tr>
			<tr>
				<td class="tdTitle"><div style="font-size:16px;margin-top:15px;margin-left:15px;margin-right:5px;margin-bottom:5px;">跟单人：</div></td>
				<td class="tdContent">
					<div style="margin-top:15px;margin-left:15px;margin-right:5px;margin-bottom:5px;">
						<input class="easyui-textbox contentInput" type="number" name="ruleFollow" style="width: 400px;height: 35px" data-options="required:true">元
					</div>
				</td>
			</tr>
			<tr>
				<td class="tdTitle"><div style="font-size:16px;margin-top:15px;margin-left:15px;margin-right:5px;margin-bottom:5px;">客服负责人：</div></td>
				<td class="tdContent">
					<div style="margin-top:15px;margin-left:15px;margin-right:5px;margin-bottom:5px;">
						<input class="easyui-textbox contentInput" type="number" name="ruleCsPrincipal" style="width: 400px;height: 35px" data-options="required:true">元
					</div>
				</td>
			</tr>
			<tr>
				<td class="tdTitle"><div style="font-size:16px;margin-top:15px;margin-left:15px;margin-right:5px;margin-bottom:5px;">客服助理：</div></td>
				<td class="tdContent">
					<div style="margin-top:15px;margin-left:15px;margin-right:5px;margin-bottom:5px;">
						<input class="easyui-textbox contentInput" type="number" name="ruleCsAssistant" style="width: 400px;height: 35px" data-options="required:true">元
					</div>
				</td>
			</tr>
			<tr>
				<td class="tdTitle"><div style="font-size:16px;margin-top:15px;margin-left:15px;margin-right:5px;margin-bottom:5px;">备注：</div></td>
				<td class="tdContent">
					<div style="margin-top:15px;margin-left:15px;margin-right:5px;margin-bottom:5px;">
						<input class="easyui-textbox" name="note"  style="width: 400px;height: 80px" data-options="multiline:true">
					</div>
				</td>
			</tr>
		</table>
		</form>
		<div style="margin-top:30px;margin-left:151px;margin-right:10px;margin-bottom:10px;">
			<a id="updateBrokerageRuleBtn" href="javascript:void(0)"
				class="easyui-linkbutton"
				data-options="iconCls:'icon-save'" style="width:80px">保存</a>
			<a id="resetBtn" href="javascript:void(0)"
				class="easyui-linkbutton"
				data-options="iconCls:'icon-reload'" style="margin-left:50px;width:80px">重置</a>
		</div>
	</div>
</body>
</html>
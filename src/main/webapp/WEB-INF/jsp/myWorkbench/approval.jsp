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
		var resourcePath = "/myWorkbench/approval";
  </script>
  <%@include file="/WEB-INF/jsp/common/js.jsp"%>
  <script type="text/javascript" src="${ctx}/resources/js/common/crud.js"></script>
  <script type="text/javascript" src="${ctx}/resources/js/myWorkbench/approval.js"></script>
  <script type="text/javascript">
	$(function () {
        approval.init();
	})
  </script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center'">
		<div id="approvalTabs" class="easyui-tabs" data-options="fit:true,border:false">
			<div title="待处理的">
				<div class="easyui-layout" data-options="fit:true,border:false" style="margin-top: 2px">
					<div id="todotb" data-options="region:'north',border:false">
						<%--<shiro:hasPermission name = "${resourceIdentity}:trace">--%>
						<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-approve',plain:true" id="approvalBtn">审批</a>
						<%--</shiro:hasPermission>--%>
					</div>
					<div data-options="region:'center'">
						<table id="todoList" data-options="border:false"></table>
					</div>
				</div>
			</div>
			<div title="我提交的">
				<div class="easyui-layout" data-options="fit:true,border:false" style="margin-top: 2px">
					<div id="submittb" data-options="region:'north',border:false">
						<%--<shiro:hasPermission name = "${resourceIdentity}:trace">--%>
						<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-approve',plain:true" id="submitDetailsBtn">详情</a>
						<%--</shiro:hasPermission>--%>
					</div>
					<div data-options="region:'center'">
						<table id="submitList" data-options="border:false"></table>
					</div>
				</div>
			</div>
			<div title="已审核的">
				<div class="easyui-layout" data-options="fit:true,border:false" style="margin-top: 2px">
					<div id="donetb" data-options="region:'north',border:false">
						<%--<shiro:hasPermission name = "${resourceIdentity}:trace">--%>
						<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-approve',plain:true" id="doneDetailsBtn">详情</a>
						<%--</shiro:hasPermission>--%>
					</div>
					<div data-options="region:'center'">
						<table id="doneList" data-options="border:false"></table>
					</div>
				</div>
			</div>
		</div>
	</div>


	<div id="approvalDialogiframe">
		<iframe id="dialogiframe" scrolling="auto" frameborder="0" src="" style="width:100%;height:99%;"></iframe>
	</div>


</body>
</html>
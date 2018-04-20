<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/tag.jsp"%>
<%
  request.setAttribute("resourceIdentity", "myWorkbench:orderSchedule");
%>
<!DOCTYPE html>
<html>
<head>
  <title>列表</title>
  <base href="${ctx}">
  <meta http-equiv="content-type" content="text/html; charset=utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/comp/My97DatePicker/skin/WdatePicker.css"/>
  <%@include file="/WEB-INF/jsp/common/css.jsp"%>
  <script type="text/javascript">
		var ctx = "${ctx}";
		var resourceName = "订单_进度";
		var resourcePath = "/myWorkbench/orderSchedule";
  </script>
  <%@include file="/WEB-INF/jsp/common/js.jsp"%>
  <script type="text/javascript" src="${ctx}/resources/js/common/crud.js"></script>
  <script type="text/javascript" src="${ctx}/resources/js/common/dateUtil.js"></script>
  <script type="text/javascript" src="${ctx}/resources/comp/My97DatePicker/WdatePicker.js"></script>
  <script type="text/javascript" src="${ctx}/resources/js/myWorkbench/orderSchedule.js"></script>
  <script type="text/javascript">
	$(function () {
		orderSchedule.init();
	})
  </script>
  <style> 
	.spans{ color:#FF0000} 
	#exportColumnsMain li {
            float: left !important;
        }
</style>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="searchDiv">
		<form id="searchForm" method="post">
			<input class="easyui-textbox" name="scheduleDictId" id="productType" style="width:200px" data-options="label:'产品',labelWidth:70,required:true">
			<input class="easyui-textbox" name="orderCodes" style="width:220px" data-options="label:'订单编号',labelWidth:100">
			<input class="easyui-textbox" name="customerNames" style="width:200px" data-options="label:'客户',labelWidth:70">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="searchBtn">查询</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" id="clearBtn">清空</a>
		</form>
	</div>
	<div data-options="region:'center'">
		<table id="list" data-options="border:false"></table>
	</div>
	<div id="completeEditDialog">
		<form id="completeEditForm" method="post">
			<input type="hidden" name="id">
			<table >
				<tr>
					<td class="tdFirstTitle"><span class="spans">*</span>是否完成</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox contentInput" id="isComplete" name="isComplete" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle"><span class="spans">*</span>完成时间</td>
					<td class="tdFirstContent">
						<input class="contentDateInput Wdate textbox easyui-validatebox contentInput" style="height:30px" name="actualDate" id="actualDate" data-options="required:true" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'});">
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>
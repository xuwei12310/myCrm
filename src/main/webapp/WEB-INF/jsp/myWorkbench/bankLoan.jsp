<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/tag.jsp"%>
<%
  request.setAttribute("resourceIdentity", "myWorkbench:commission");
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
		var resourceName = "佣金统计";
		var resourcePath = "/myWorkbench/bankLoan";
  </script>
  <%@include file="/WEB-INF/jsp/common/js.jsp"%>
  <script type="text/javascript" src="${ctx}/resources/js/common/crud.js"></script>
  <script type="text/javascript" src="${ctx}/resources/js/myWorkbench/bankLoan.js"></script>
	<script type="text/javascript" src="${ctx}/resources/comp/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="${ctx}/resources/js/myWorkbench/orderDetails.js"></script>
  <script type="text/javascript">
	$(function () {
        bankLoan.init();
	})
  </script>
  <style> 
	.spans{ color:#FF0000} 
</style>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="searchDiv" style="height: 80px;">
		<form id="searchForm" method="post">
			<input class="easyui-combobox" name="bank" id="bank" style="width:180px" data-options="label:'银行',labelWidth:70">
			<span class="textbox combo datebox" style="font-size:0;border: 0px;">
				<label class="textbox-label" style="text-decoration:none;text-align: left; width: 79.0476px; height: 30.0952px; line-height: 30.0952px;">签约日期</label>
			<input class="easyui-validatebox Wdate dateInput" id="signingDateStart" name="signingDateStart" style="width:125px" data-options="label:'',labelWidth:70,editable:false" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'signingDateEnd\')}'})">
			</span>
			- <input class="easyui-validatebox Wdate dateInput" id="signingDateEnd" name="signingDateEnd" style="width:125px" data-options="label:'-',labelWidth:10,editable:false" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'signingDateStart\')}'})">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="searchBtn">查询</a>
			<div style="height: 5px"></div>
			<input class="easyui-combobox" id="product" name="product" style="width:180px" data-options="label:'产品',labelWidth:70,editable:false">
			<span class="textbox combo datebox" style="font-size:0;border: 0px;">
				<label class="textbox-label" style="text-decoration:none;text-align: left; width: 79.0476px; height: 30.0952px; line-height: 30.0952px;">预计完成日期</label>
			<input class="easyui-validatebox Wdate dateInput" id="estimateFinishTimeStart" name="estimateFinishTimeStart" style="width:125px" data-options="editable:false" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'estimateFinishTimeEnd\')}'})">
			</span>
			- <input class="easyui-validatebox Wdate dateInput" id="estimateFinishTimeEnd" name="estimateFinishTimeEnd" style="width:125px" data-options="editable:false" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'estimateFinishTimeStart\')}'})">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" id="clearBtn">清空</a>
		</form>
	</div>
	<div data-options="region:'center'">
		<table id="list" data-options="border:false"></table>
	</div>
</body>
</html>
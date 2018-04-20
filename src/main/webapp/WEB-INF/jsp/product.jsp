<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/tag.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
  request.setAttribute("resourceIdentity", ":product");
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
		var resourceName = "";
		var resourcePath = "/product";
  </script>
  <%@include file="/WEB-INF/jsp/common/js.jsp"%>
  <script type="text/javascript" src="${ctx}/resources/js/common/crud.js"></script>
  <script type="text/javascript" src="${ctx}/resources/js/product.js"></script>
  <script type="text/javascript" src="${ctx}/resources/js/common/kindeditor/kindeditor.js"></script>
  <script type="text/javascript" src="${ctx}/resources/css/My97DatePicker/WdatePicker.js"></script>
  <script type="text/javascript">
	$(function () {
		product.init();
	})
  </script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="searchDiv">
		<form id="searchForm" method="post">
					<input class="easyui-textbox" name="productName" style="width:180px" data-options="label:'产品名：',labelWidth:70">
					<span class="textbox combo datebox" style="font-size:0;border: 0px;">
						<label class="textbox-label" style="text-decoration:none;text-align: left; width: 60px; height: 30.0952px; line-height: 30.0952px;">价格区间：</label>
						<input class="easyui-numberbox " style="width:120px;" id="priceSearch1" name="price" data-options="precision:2" >
					</span>
					— <input class="easyui-numberbox " style="width:120px;" id="priceSearch2" name="price2" data-options="precision:2" >
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="searchBtn">查询</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" id="clearBtn">清空</a>
		</form>
	</div>
	<div data-options="region:'center'">
		<table id="list" data-options="border:false"></table>
	</div>
	<div id="tb">
		<%@include file="/WEB-INF/jsp/common/btn.jsp"%>
	</div>
	<div id="editDialog">
		<form id="editForm" method="post">
			<input type="hidden" name="id">
            <table style="margin-right: 5px">
				<tr>
					<td class="tdFirstTitle" style="width: 65px">产品名：</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="productName" name="productName" style="width:280px" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">售价：</td>
					<td class="tdFirstContent">
						<input class="easyui-numberbox" id="price" name="price" style="width:280px" data-options="required:true,precision:2">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">发行时间：</td>
					<td class="tdFirstContent">
						<input class="dateInput easyui-validatebox Wdate" id="createTime" name="createTime" style="width:270px;padding-left:10px" data-options="required:true,editable:false" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" >
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">产品详情：</td>
					<td class="tdFirstContent">
						<textarea  id="details" name="details" style="width:280px;height:100px;font-size:13px"></textarea>
					</td>
				</tr>
		            </table>
		</form>
	</div>
</body>
</html>
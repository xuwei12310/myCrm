<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/tag.jsp"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
  request.setAttribute("resourceIdentity", "myWorkbench:kpi");
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
		var resourceName = "业务指标";
		var resourcePath = "/admin/myWorkbench/kpi";
  </script>
  <%@include file="/WEB-INF/jsp/common/js.jsp"%>
  <script type="text/javascript" src="${ctx}/resources/js/common/crud.js"></script>
   <script type="text/javascript" src="${ctx}/resources/comp/My97DatePicker/WdatePicker.js"></script>
  <script type="text/javascript" src="${ctx}/resources/js/admin/myWorkbench/kpi.js"></script>
  <script type="text/javascript">
	$(function () {
		kpi.init();
	})
  </script>
   <style> 
	.spans{ color:#FF0000};
	
</style>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="searchDiv">
		<form id="searchForm" method="post">
		            年份：<input class="contentInput easyui-validatebox Wdate dateInput" id="yearsearch" name="year" data-options="editable:false"  onfocus="WdatePicker({dateFmt:'yyyy'})">
		            所属公司: <input class="easyui-combotree contentInput" name="compyName" id="companyId" data-options="panelHeight:'auto'">
		            部门:<input class="easyui-combotree contentInput" name="depName" id="organizationId" data-options="panelHeight:'auto'">
					<input class="easyui-textbox" id="employeeNameSearch" name="userId.name" style="width: 180px" data-options="label:'员工',labelWidth:70">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="searchBtn">查询</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" id="clearBtn">清空</a>
		</form>
	</div>
	<div data-options="region:'center'">
		<table id="list" data-options="border:false"></table>
	</div>
	<div id="tb">
		<shiro:hasPermission name = "${resourceIdentity}:create">
	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" id="addBtn">添加指标</a>
        </shiro:hasPermission>
        <shiro:hasPermission name = "${resourceIdentity}:update">
	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" id="updateBtn">修改指标</a>
        </shiro:hasPermission>
       <shiro:hasPermission name = "${resourceIdentity}:delete">
	       <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" id="deleteBtn">删除指标</a>
        </shiro:hasPermission>
          <shiro:hasPermission name = "${resourceIdentity}:view">
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" id="updateAchieveBtn">更新业绩</a>
          </shiro:hasPermission>
	       <!-- <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-view',plain:true" id="chartsBtn">走势图</a> -->
	       <span style="margin-left: 550px"> 单位：万元</span>
	</div>
	<div id="editDialog">
		<div  class="easyui-layout"  data-options="fit:true">
			<div data-options="region:'north',border:false" class="searchDiv" style="height: 90px">
				<form id="editForm" method="post">
					<input type="hidden" name="id">
					<input type="hidden" name="tararray" id="tararray">
					 <input type="hidden"  id='StoreEmployeeId'>
                       <input type="hidden"  id='StoreEmployeeName'>
		            <table >
		                <tr>
							<td class="tdTitle"><span class="spans">*</span>年份:</td>
							
							<td class="tdContent">
								<input class="contentInput easyui-validatebox Wdate dateInput" id="year" name="year" data-options="required:true,editable:false" onfocus="WdatePicker({dateFmt:'yyyy'})" >
							</td>
						</tr>
						<tr>
							<td class="tdTitle"><span class="spans">*</span>员工:</td>
							<td class="tdContent">
								<input type="hidden" name="employee" id="userId">
								<input class="easyui-textbox contentInput" id="employeeName"  style="width:100%" data-options="required:true,editable:false,icons:[{iconCls:'icon-search'}] " >
							</td>
						</tr>
					 </table>
				</form>
			</div>
			<div data-options="region:'center',border:false">
			     <table id="monthList"></table>  
			</div>
		</div>
	</div>
	<div id="editmodifDialog">
		<div  class="easyui-layout"  data-options="fit:true">
			<div data-options="region:'north',border:false" class="searchDiv" style="height: 90px">
				<form id="editmodifForm" method="post">
					<input type="hidden" name="id" id="modifId">
					<input type="hidden" name="tararray" id="tararraymodif">
					<input type="hidden" name="year" id="modifyears">
		            <table >
		                <tr>
							<td class="tdTitle">年份:</td>
							<td class="tdContent">
								<input class="contentInput easyui-validatebox Wdate dateInput" id="modifyear" name="year" data-options="required:false,editable:false" onfocus="WdatePicker({dateFmt:'yyyy'})" disabled>
							</td>
						</tr>
						<tr>
							<td class="tdTitle">员工:</td>
							<td class="tdContent">
								<input type="hidden" name="userId.id" id="userIdmodif">
								<input class="easyui-textbox contentInput" id="employeeNamemodif"  style="width:100%" data-options="required:false,editable:false,icons:[{iconCls:'icon-search'}]" disabled>
							</td>
						</tr>
					 </table>
				</form>
			</div>
			<div data-options="region:'center',border:false">
			     <table id="monthmodifList"></table>  
			</div>
		</div>
	</div>
	<div id="editAchieveDialog">
		<div  class="easyui-layout"  data-options="fit:true">
			<div data-options="region:'north',border:false" class="searchDiv" style="height: 90px">
				<form id="editAchieveForm" method="post">
		            <table >
		                <tr>
							<td class="tdTitle"><span class="spans">*</span>月份:</td>
							<td class="tdContent">
								<input class="contentInput easyui-validatebox Wdate dateInput" id="updatemonth" name="updatemonth" data-options="required:true" onfocus="WdatePicker({dateFmt:'yyyy-MM'})" >
							</td>
						</tr>
					 </table>
				</form>
			</div>
		</div>
	</div>
	<div id="employeeEditDialog">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'west',border:false" class="searchDiv" style="width: 420px">
				     <div class="easyui-layout" data-options="fit:true">
				           <div data-options="region:'north',border:false" class="searchDiv" style="height: 50px">
				                <form id="employeeEditSearch" >
					               <input class="easyui-textbox" name="name" style="width:180px" data-options="label:'姓名',labelWidth:50">
					               <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'"  id="employeeEditSearchBtn">查询</a>
					               &nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-clear'"  id="employeeEditClearBtn">清空</a>
					           </form>
				           </div>
				            <div data-options="region:'center',border:false">
					              <table id="employeeleftGrid" title="员工列表"></table>
					        </div>
				     </div>
			</div>
			<div data-options="region:'center',border:false" class="searchDiv">
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="kpi.employee_leftToRight()" style="font-weight:bold;position: absolute;top:40%;width: 46px">右移</a>
				<br /> <br /> <br />
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="kpi.employee_rightToRight()" style="font-weight:bold;position: absolute;top:50%;width: 46px">左移</a>
			</div>
			<div data-options="region:'east',border:false" class="searchDiv" style="width: 410px;padding-top: 54px">
				<table id="employeerightGrid" title="已选员工"></table>
			</div>
		</div>
	</div>
	<!-- 选择员工 -->
	<div id="selectEmployeeEditDialog">
	  <div class="easyui-layout" data-options="fit:true,selected:true">
	   <div data-options="region:'north',border:false" class="searchConditonDiv">
		<form id="searchEmployeeForm" method="post">
	   		 <input class="easyui-textbox" name="name" style="width:180px" data-options="label:'姓名',labelWidth:50">
			 <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="searchEmployeeBtn">查询</a>
			 <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" id="clearEmployeeBtn">清空</a>
		</form>
		</div>
		<div data-options="region:'center',border:false">
		<div id="employeeList" ></div>
		</div>
		</div>
	</div>
</body>
</html>
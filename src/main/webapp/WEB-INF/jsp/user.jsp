<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/tag.jsp"%>
<%
  request.setAttribute("resourceIdentity", ":user");
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
		var resourcePath = "/admin/user";
  </script>
  <%@include file="/WEB-INF/jsp/common/js.jsp"%>
  <script type="text/javascript" src="${ctx}/resources/js/common/crud.js"></script>
  <script type="text/javascript" src="${ctx}/resources/js/admin/user.js"></script>
  <script type="text/javascript">
	$(function () {
		user.init();
	})
  </script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="searchDiv">
		<form id="searchForm" method="post">
					<input class="easyui-textbox" name="name" style="width:180px" data-options="label:'',labelWidth:70">
					<input class="easyui-textbox" name="idNumber" style="width:180px" data-options="label:'',labelWidth:70">
					<input class="easyui-textbox" name="email" style="width:180px" data-options="label:'',labelWidth:70">
					<input class="easyui-textbox" name="username" style="width:180px" data-options="label:'',labelWidth:70">
					<input class="easyui-textbox" name="password" style="width:180px" data-options="label:'',labelWidth:70">
					<input class="easyui-textbox" name="status" style="width:180px" data-options="label:'',labelWidth:70">
					<input class="easyui-textbox" name="isAdmin" style="width:180px" data-options="label:'',labelWidth:70">
					<input class="easyui-textbox" name="companyId" style="width:180px" data-options="label:'公司',labelWidth:70">
					<input class="easyui-textbox" name="organizationId" style="width:180px" data-options="label:'部门id',labelWidth:70">
								<input class="easyui-textbox" name="isLock" style="width:180px" data-options="label:'',labelWidth:70">
					<input class="easyui-textbox" name="lockTime" style="width:180px" data-options="label:'',labelWidth:70">
					<input class="easyui-textbox" name="loginCount" style="width:180px" data-options="label:'',labelWidth:70">
					<input class="easyui-textbox" name="loginFailureCount" style="width:180px" data-options="label:'',labelWidth:70">
					<input class="easyui-textbox" name="loginIp" style="width:180px" data-options="label:'',labelWidth:70">
					<input class="easyui-textbox" name="loginTime" style="width:180px" data-options="label:'',labelWidth:70">
						<input class="easyui-textbox" name="phone" style="width:180px" data-options="label:'手机',labelWidth:70">
						<input class="easyui-textbox" name="pwdPrefix" style="width:180px" data-options="label:'微信登录获取到的openid',labelWidth:70">
					<input class="easyui-textbox" name="pwdSuffix" style="width:180px" data-options="label:'',labelWidth:70">
					<input class="easyui-textbox" name="wechat" style="width:180px" data-options="label:'',labelWidth:70">
					<input class="easyui-textbox" name="wxOpenId" style="width:180px" data-options="label:'微信登录获取到的openid',labelWidth:70">
					<input class="easyui-textbox" name="isSynchro" style="width:180px" data-options="label:'是否已同步',labelWidth:70">
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
            <table >
				<tr>
					<td class="tdFirstTitle"></td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="name" name="name" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle"></td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="idNumber" name="idNumber" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle"></td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="email" name="email" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle"></td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="username" name="username" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle"></td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="password" name="password" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle"></td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="status" name="status" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle"></td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="isAdmin" name="isAdmin" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">公司</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="companyId" name="companyId" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">部门id</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="organizationId" name="organizationId" style="width:100%" data-options="required:true">
					</td>
				</tr>
							<tr>
					<td class="tdFirstTitle"></td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="isLock" name="isLock" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle"></td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="lockTime" name="lockTime" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle"></td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="loginCount" name="loginCount" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle"></td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="loginFailureCount" name="loginFailureCount" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle"></td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="loginIp" name="loginIp" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle"></td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="loginTime" name="loginTime" style="width:100%" data-options="required:true">
					</td>
				</tr>
					<tr>
					<td class="tdFirstTitle">手机</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="phone" name="phone" style="width:100%" data-options="required:true">
					</td>
				</tr>
					<tr>
					<td class="tdFirstTitle">微信登录获取到的openid</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="pwdPrefix" name="pwdPrefix" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle"></td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="pwdSuffix" name="pwdSuffix" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle"></td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="wechat" name="wechat" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">微信登录获取到的openid</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="wxOpenId" name="wxOpenId" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">是否已同步</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="isSynchro" name="isSynchro" style="width:100%" data-options="required:true">
					</td>
				</tr>
	            </table>
		</form>
	</div>
</body>
</html>
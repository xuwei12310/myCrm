<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@include file="/WEB-INF/jsp/common/tag.jsp"%>
<%
  request.setAttribute("resourceIdentity", "sys:organization");
%>
<!DOCTYPE html>
<html>
<head>
  <title>部门列表</title>
  <base href="${ctx}">
  <meta http-equiv="content-type" content="text/html; charset=utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <%@include file="/WEB-INF/jsp/common/css.jsp"%>
  <script type="text/javascript">
		var ctx = "${ctx}";
		var resourceName = "部门员工";
		var resourcePath = "/sys/organization";
  </script>
  <%@include file="/WEB-INF/jsp/common/js.jsp"%>
  <script type="text/javascript" src="${ctx}/resources/js/common/treeCRUD.js"></script>
  <script type="text/javascript" src="${ctx}/resources/js/sys/organization.js"></script>
  <script type="text/javascript">
	$(function () {
		$("#companyId").combotree({
			animate:true,
    		lines:true,
    	  	dnd:true,
    	  	parentField:"pid",
    	  	url: app.getUrl("getOrganizationByOrgType")+'?orgType=1'
		});
		
		$('#roleId').combobox({  
			multiple:true,
			editable:false,
			data:CSIT.syncCallService(organization.url.getRole()),
			valueField:'id',
			textField: 'name'
	  	 });
		organization.init();
	})
  </script>
</head>
<body class="easyui-layout">
	<div data-options="region:'west',border:false,split:true" style="width:170px;    border-right: 4px solid #c3c3c3">
		<div class="easyui-layout" data-options="fit:true" >
			<div data-options="region:'north',border:false" style="height:155px;margin-top:10px;padding-left:25px;margin-bottom: 5px;border-bottom: rgba(128, 128, 128, 0.41) solid 2px;">
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add'" id="addOrganizationBtn">新增组织机构</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" id="updateOrganizationBtn" style="margin-top:5px">编辑组织机构</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" id="deleteOrganizationBtn" style="margin-top:5px">删除组织机构</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" id="peizhiOrgBankAccountBtn" style="margin-top:5px;margin-bottom: 5px">银行账号配置</a>
			</div>
			<div data-options="region:'center',border:false" style="margin-top: 10px">
				<ul id="tree"></ul>
			</div>
		</div>
	</div>
	<div data-options="region:'center',border:false"  class="easyui-layout">
		<div data-options="region:'north',border:false" class="searchDiv">
			<form id="searchForm" method="post">
				<input class="easyui-textbox" name="name" style="width:150px" data-options="label:'姓名',labelWidth:65">
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="searchBtn">查询</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" id="clearBtn">清空</a>
			</form>
		</div>
		<div data-options="region:'center',border:false">
			<table id="list"></table>
		</div>
	</div>
	
	<div id="tb">
		<shiro:hasPermission name = "${resourceIdentity}:addUser">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" id="addUserBtn">添加员工</a>
		</shiro:hasPermission>
		<shiro:hasPermission name = "${resourceIdentity}:updateUser">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" id="updateUserBtn">修改员工</a>
		</shiro:hasPermission>
		<shiro:hasPermission name = "${resourceIdentity}:mulUpdateStatus">
			<a href="javascript:void(0)" class="easyui-menubutton" data-options="menu:'#userStatus',plain:true,iconCls:'icon-status'" id="mulUserStatus">状态</a>
		</shiro:hasPermission>
		<shiro:hasPermission name = "${resourceIdentity}:resetPwd">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-resetPwd',plain:true" id="resetPwd">重置密码</a>
		</shiro:hasPermission>
	    <shiro:hasPermission name = "${resourceIdentity}:delete">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" id="delete">删除</a>
		</shiro:hasPermission> 
	</div>
	<div id="userStatus" style="width: 80px;">
		<div data-options="name:'1',iconCls:'icon-info'">启用</div>
		<div data-options="name:'0',iconCls:'icon-warn'">禁用</div>
	</div>
	<div id="editDialog">
		<form id="editForm" method="post">
			<input type="hidden" name="id" id="id">
			<table class="contentTb">
				<tr>
					<td class="tdFirstTitle" style="width: 55px">上级：</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox contentInput" name="parentId" id="parentId" data-options="required:true,panelHeight:'auto'">
					</td>
				</tr>
				<tr>
					<td class="tdTitle">类型：</td>
					<td class="tdContent">
						<input class="easyui-combobox contentInput" id="orgType" name="orgType"  data-options="required:true,editable:false">
					</td>
				</tr>
				<tr>
					<td class="tdTitle">名称：</td>
					<td class="tdContent">
						<input class="easyui-textbox contentInput" name="organizationName" id="organizationName" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdTitle">描述：</td>
					<td class="tdContent">
						<input class="easyui-textbox contentInput" name="note" id="note" data-options="multiline:true" style="height:150px;">
					</td>
				</tr>
			</table>
		</form>
	</div>
   <!--  添加员工 -->
	<div id="editUserDialog">
		<form id="editUserForm" method="post">
			<input type="hidden" name="id" id="id">
			<input type="hidden" name="roleIds" id="roleIds">
			<table class="contentTb">
				<tr>
					<td class="tdFirstTitle" style="width:80px">用户名：</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox contentInput" name="username" data-options="required:true" >
					</td>
				</tr>
				<!-- <tr id="passwordTr">
					<td class="tdTitle">密码：</td>
					<td class="tdContent">
						<input class="easyui-textbox contentInput" type="password" name="password" id="password" data-options="required:true">
					</td>
				</tr> -->
				<tr>
					<td class="tdTitle">姓名：</td>
					<td class="tdContent">
						<input class="easyui-textbox contentInput" name="name" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdTitle">是否管理员：</td>
					<td class="tdContent">
						<input class="easyui-combobox contentInput" id="isAdmin" name="isAdmin" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdTitle">所属公司：</td>
					<td class="tdContent">
						<input class="easyui-combotree contentInput" name="company.id" id="companyId" data-options="required:true,panelHeight:'auto'">
					</td>
				</tr>
				<tr>
					<td class="tdTitle">所属部门：</td>
					<td class="tdContent">
						<input class="easyui-combotree contentInput" name="organization.id" id="organizationId" data-options="required:true,panelHeight:'auto'">
					</td>
				</tr>
				<tr>
					<td class="tdTitle">职务：</td>
					<td class="tdContent">
						<input class="easyui-textbox contentInput" name="roleId" id="roleId" data-options="required:true,panelHeight:'200'">
					</td>
				</tr>
				<tr>
					<td class="tdTitle">手机：</td>
					<td class="tdContent">
						<input class="easyui-textbox contentInput" name="phone" data-options="validType:'mobile'">
					</td>
				</tr>
				<tr>
					<td class="tdTitle">邮箱：</td>
					<td class="tdContent">
						<input class="easyui-textbox contentInput" name="email" data-options="validType:'email'">
					</td>
				</tr>
				<tr>
					<td class="tdTitle">微信号：</td>
					<td class="tdContent">
						<input class="easyui-textbox contentInput" name="wechat">
					</td>
				</tr>
				<tr>
					<td class="tdTitle">状态:</td>
					<td class="tdContent">
						<input id="status" class="easyui-combobox" name="status"  style="width:100%" data-options="required:true,editable:false" />
					</td>
				</tr>
				<tr><td></td><td class="tdContent">注:默认密码为a123456</td></tr>
			<!-- 	<tr>
					<td class="tdTitle">性别：</td>
					<td class="tdContent">
						<input class="easyui-textbox contentInput" name="sex" id="sex" data-options="required:true,panelHeight:'auto'">
					</td>
				</tr> -->
			
			</table>
		</form>
	</div>
	<!-- 配置银行账户 -->
	<!-- <div id="orgBankAccountDialog">
		<div class="easyui-layout" data-options="fit:true,selected:true">
			<div data-options="region:'north',border:false" >
			</div>
			<div data-options="region:'center',border:false">
				<div id="tb1">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" id="addOrgBankAccountBtn">添加</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" id="updateOrgBankAccountBtn">修改</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" id="deleteOrgBankAccountBtn">删除</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-exit',plain:true" id="exitOrgBankAccountBtn">退出</a>
				</div>
				<div id="orgBankAccountList"></div>
			</div>
		</div>
	</div> -->
	<!-- <div id="orgBankAccountDialog">
		<div class="easyui-layout" data-options="fit:true,selected:true">
			
			<div data-options="region:'center',border:false">
				<div id="tb1">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" id="addOrgBankAccountBtn">添加</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" id="updateOrgBankAccountBtn">修改</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" id="deleteOrgBankAccountBtn">删除</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-exit',plain:true" id="exitOrgBankAccountBtn">退出</a>
				</div>
				<div id="orgBankAccountList"></div>
			</div>
		</div>
	</div> -->
	<div id="orgBankAccountDialog">
		<div class="easyui-layout" data-options="fit:true,selected:true">
			<div data-options="region:'north',border:false"
				 style="height: 35px">
					<div id="tb1">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" id="addOrgBankAccountBtn">添加</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" id="updateOrgBankAccountBtn">修改</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" id="deleteOrgBankAccountBtn">删除</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-exit',plain:true" id="exitOrgBankAccountBtn">退出</a>
				</div>
			</div>
			<div data-options="region:'center',border:false">
			
				<div id="orgBankAccountList"></div>
			</div>
		</div>
	</div>
	<!-- 添加银行账号 -->
	<div id="orgBankAccountEditDialog">
		<form id="orgBankAccountEditForm" method="post">
			<input type="hidden" id="bankId" name="id">
			<input type="hidden" id="orgId" name="orgId">
            <table >
				<tr>
					<td class="tdFirstTitle">部门</td>
					<td class="tdFirstContent" style="width:200px">
						<input class="easyui-textbox" id="orgName" name="orgName" style="width:100%"  readonly="readonly">
					</td>
				</tr>
				<tr>
					<td class="tdTitle">账户名</td>
					<td class="tdContent" >
						<input class="easyui-textbox" id="accountName" name="accountName" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdTitle">开户行</td>
					<td class="tdContent">
						<input class="easyui-textbox" id="bank" name="bank" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdTitle">账号</td>
					<td class="tdContent">
						<input class="easyui-textbox" id="accountNumber" name="accountNumber" style="width:100%" data-options="required:true,validType:'number'">
					</td>
				</tr>
				<tr>
					<td class="tdTitle">状态</td>
					<td class="tdContent">
						<input class="easyui-combobox" style="width: 100%" id="bankAccountStatus" name="status"  data-options="required:true,editable:false">
					</td>
				</tr>
				<tr>
					<td class="tdTitle">备注</td>
					<td class="tdContent">
						<input class="easyui-textbox" id="bankNote" name="note" style="width:100%" >
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>
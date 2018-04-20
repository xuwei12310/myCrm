<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/tag.jsp"%>
<%
  request.setAttribute("resourceIdentity", "dict:cooperateBank");
%>
<!DOCTYPE html>
<html>
<head>
  <title>合作银行</title>
  <base href="${ctx}">
  <meta http-equiv="content-type" content="text/html; charset=utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <%@include file="/WEB-INF/jsp/common/css.jsp"%>
  <script type="text/javascript">
		var ctx = "${ctx}";
		var resourceName = "合作银行";
		var resourcePath = "/dict/cooperateBank";
  </script> 
  <%@include file="/WEB-INF/jsp/common/js.jsp"%>
  <script type="text/javascript" src="${ctx}/resources/js/common/crud.js"></script>
  <script type="text/javascript" src="${ctx}/resources/js/dict/cooperateBank.js"></script>
  <script type="text/javascript">
	$(function () {
		cooperateBank.init();
	})
  </script>
  <style type="text/css">
  	.tdTitle{
  		padding: 5px 0px 0px 15px;
  	}
  	.tdContent{
  		padding-top: 8px
  	}
  </style>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="searchDiv">
		<form id="searchForm" method="post">
			<!-- <input class="easyui-textbox" name="code" style="width:180px" data-options="label:'编号',labelWidth:70"> -->
			<input class="easyui-textbox" name="bankName" style="width:180px" data-options="label:'银行名称',labelWidth:70">
			<select class="easyui-combobox"  id="statu" name="status" style="width:180px" data-options="label:'状态',labelWidth:70,editable:false">
                            <option value="1">启用</option>  
                            <option value="0">禁用</option>  
             </select>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="searchBtn">查询</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" id="clearBtn">清空</a>
		</form>
	</div>
	<div data-options="region:'center'">
		<table id="list" data-options="border:false"></table>
	</div>
	<div id="tb">
		<%@include file="/WEB-INF/jsp/common/btn.jsp"%>
		<shiro:hasPermission name = "${resourceIdentity}:config">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-resources',plain:true" id="config">配置支行</a>
		</shiro:hasPermission>
	    <shiro:hasPermission name = "${resourceIdentity}:up">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-up',plain:true" id="upBtn">上移</a>
		</shiro:hasPermission>
		<shiro:hasPermission name = "${resourceIdentity}:down">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-down',plain:true" id="downBtn">下移</a>
		</shiro:hasPermission>
	</div>
 	<div id="editDialog">
		<form id="editForm" method="post">
			<input type="hidden" name="id">
			<table class="contentTb">
				<!-- <tr>
					<td class="tdFirstTitle">编号：</td>
					<td class="tdFirstContent"><input class="easyui-textbox contentInput" name="code"  data-options="required:true">
					</td>
				</tr> -->
				<tr>
					<td class="tdTitle" style="width: 85px">银行名称：</td>
					<td class="tdContent">
						<input class="easyui-textbox contentInput" name="bankName"  data-options="required:true">
					</td>
				</tr>
					<tr>
					<td class="tdTitle">状态：</td>
					<td class="tdContent">
                        <input class="easyui-combobox contentInput" id="status" name="status"  data-options="required:true,editable:false">
                    </td>
				</tr>
				<tr>
					<td class="tdTitle">备注：</td>
					<td class="tdContent">
						<input class="easyui-textbox contentInput" name="note" >
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="configDialog" style="display: none">
		<!-- <h2 id="bank" align="center"></h2> -->
		<div class="easyui-layout" data-options="fit:true">
		<div id="tb2">
		<shiro:hasPermission name = "${resourceIdentity}:config">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" id="addBranchBtn">添加</a>
		</shiro:hasPermission>
		<shiro:hasPermission name = "${resourceIdentity}:config">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" id="updateBranchBtn">修改</a>
		</shiro:hasPermission>
		<shiro:hasPermission name = "${resourceIdentity}:config">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" id="deleteBranchBtn">删除</a>
		</shiro:hasPermission>
	    <shiro:hasPermission name = "${resourceIdentity}:config">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-up',plain:true" id="upBranchBtn">上移</a>
		</shiro:hasPermission>
		<shiro:hasPermission name = "${resourceIdentity}:config">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-down',plain:true" id="downBranchBtn">下移</a>
		</shiro:hasPermission>
		</div>
		<div data-options="region:'center'">
			<table id="branchList" data-options="border:false"></table>
		</div>
		</div>
	</div>
	<div id="editBranchDialog" style="display: none">
		<form id="editBranchForm" method="post">
			<input type="hidden" name="id">
			<input type="hidden" id="bankId" name="bank.id">
			<table class="contentTb">
				<!-- <tr>
					<td class="tdFirstTitle">编号：</td>
					<td class="tdFirstContent"><input class="easyui-textbox contentInput" name="code"  data-options="required:true">
					</td>
				</tr> -->
				<tr>
					<td class="tdTitle" style="width: 85px">支行名称：</td>
					<td class="tdContent">
						<input class="easyui-textbox contentInput" name="subbranchName"  data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdTitle">代号：</td>
					<td class="tdContent">
						<input class="easyui-textbox contentInput" name="bankCode"  data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdTitle">地址：</td>
					<td class="tdContent">
						<input class="easyui-textbox contentInput" name="address"  >
					</td>
				</tr>
				<tr>
					<td class="tdTitle">银行联系人：</td>
					<td class="tdContent">
						<input class="easyui-textbox contentInput" name="contacts" >
					</td>
				</tr>
				<tr>
					<td class="tdTitle">联系电话：</td>
					<td class="tdContent">
						<input class="easyui-textbox contentInput" name="phone" data-options="validType:'mobile'">
					</td>
				</tr>
				<tr>
					<td class="tdTitle">状态:</td>
					<td class="tdContent">
                        <input class="easyui-combobox contentInput" id="branchStatus" name="status"  data-options="required:true,editable:false">
                    </td>
				</tr>
				<tr>
					<td class="tdTitle">备注：</td>
					<td class="tdContent">
						<input class="easyui-textbox contentInput" name="note" >
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/tag.jsp"%>
<%
  request.setAttribute("resourceIdentity", ":customer");
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
		var resourceName = "客户";
		var resourcePath = "/admin/customer";
  </script>
  <%@include file="/WEB-INF/jsp/common/js.jsp"%>
  <script type="text/javascript" src="${ctx}/resources/js/common/crud.js"></script>
  <script type="text/javascript" src="${ctx}/resources/js/admin/customer.js"></script>
  <script type="text/javascript">
	$(function () {
		customer.init();
	})
  </script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="searchDiv">
		<form id="searchForm" method="post">
					<input class="easyui-textbox" name="customerType" style="width:180px" data-options="label:'客户类型1个人2企业',labelWidth:70">
					<input class="easyui-textbox" name="customerName" style="width:180px" data-options="label:'客户名称',labelWidth:70">
					<input class="easyui-textbox" name="sex" style="width:180px" data-options="label:'性别1男0女',labelWidth:70">
					<input class="easyui-textbox" name="mobilePhone" style="width:180px" data-options="label:'手机号',labelWidth:70">
					<input class="easyui-textbox" name="telephone" style="width:180px" data-options="label:'固定电话',labelWidth:70">
					<input class="easyui-textbox" name="email" style="width:180px" data-options="label:'邮箱',labelWidth:70">
					<input class="easyui-textbox" name="idNumber" style="width:180px" data-options="label:'身份证号',labelWidth:70">
					<input class="easyui-textbox" name="idAddress" style="width:180px" data-options="label:'身份证地址',labelWidth:70">
					<input class="easyui-textbox" name="customerSourceId" style="width:180px" data-options="label:'客户来源',labelWidth:70">
					<input class="easyui-textbox" name="customerStageId" style="width:180px" data-options="label:'客户阶段',labelWidth:70">
					<input class="easyui-textbox" name="customerStatusId" style="width:180px" data-options="label:'客户状态',labelWidth:70">
					<input class="easyui-textbox" name="ownerId" style="width:180px" data-options="label:'拥有人',labelWidth:70">
					<input class="easyui-textbox" name="gradeId" style="width:180px" data-options="label:'智能评级',labelWidth:70">
					<input class="easyui-textbox" name="placeId" style="width:180px" data-options="label:'户籍地',labelWidth:70">
					<input class="easyui-textbox" name="maritalId" style="width:180px" data-options="label:'婚姻状况',labelWidth:70">
					<input class="easyui-textbox" name="liveAreaId" style="width:180px" data-options="label:'居住地区',labelWidth:70">
					<input class="easyui-textbox" name="livePlotId" style="width:180px" data-options="label:'居住小区',labelWidth:70">
					<input class="easyui-textbox" name="birthdate" style="width:180px" data-options="label:'出生日期',labelWidth:70">
					<input class="easyui-textbox" name="company" style="width:180px" data-options="label:'就职单位',labelWidth:70">
					<input class="easyui-textbox" name="occupation" style="width:180px" data-options="label:'职业',labelWidth:70">
					<input class="easyui-textbox" name="spouseName" style="width:180px" data-options="label:'配偶姓名',labelWidth:70">
					<input class="easyui-textbox" name="spousePlaceId" style="width:180px" data-options="label:'配偶户籍地',labelWidth:70">
					<input class="easyui-textbox" name="spouseMobilePhone" style="width:180px" data-options="label:'配偶手机号',labelWidth:70">
					<input class="easyui-textbox" name="spouseIdNumber" style="width:180px" data-options="label:'配偶身份证号',labelWidth:70">
					<input class="easyui-textbox" name="spouseCompany" style="width:180px" data-options="label:'配偶就职单位',labelWidth:70">
					<input class="easyui-textbox" name="spouseOccupation" style="width:180px" data-options="label:'配偶职业',labelWidth:70">
					<input class="easyui-textbox" name="creditRatingId" style="width:180px" data-options="label:'信用等级',labelWidth:70">
					<input class="easyui-textbox" name="creditRatingAttachId" style="width:180px" data-options="label:'信用等级附件',labelWidth:70">
					<input class="easyui-textbox" name="lastTrackTime" style="width:180px" data-options="label:'最后跟进时间(年月日）',labelWidth:70">
					<input class="easyui-textbox" name="photoId" style="width:180px" data-options="label:'照片',labelWidth:70">
					<input class="easyui-textbox" name="wxOpenId" style="width:180px" data-options="label:'微信登录获取到的openid',labelWidth:70">
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
					<td class="tdFirstTitle">客户类型1个人2企业</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="customerType" name="customerType" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">客户名称</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="customerName" name="customerName" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">性别1男0女</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="sex" name="sex" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">手机号</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="mobilePhone" name="mobilePhone" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">固定电话</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="telephone" name="telephone" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">邮箱</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="email" name="email" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">身份证号</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="idNumber" name="idNumber" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">身份证地址</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="idAddress" name="idAddress" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">客户来源</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="customerSourceId" name="customerSourceId" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">客户阶段</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="customerStageId" name="customerStageId" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">客户状态</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="customerStatusId" name="customerStatusId" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">拥有人</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="ownerId" name="ownerId" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">智能评级</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="gradeId" name="gradeId" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">户籍地</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="placeId" name="placeId" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">婚姻状况</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="maritalId" name="maritalId" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">居住地区</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="liveAreaId" name="liveAreaId" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">居住小区</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="livePlotId" name="livePlotId" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">出生日期</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="birthdate" name="birthdate" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">就职单位</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="company" name="company" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">职业</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="occupation" name="occupation" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">配偶姓名</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="spouseName" name="spouseName" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">配偶户籍地</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="spousePlaceId" name="spousePlaceId" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">配偶手机号</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="spouseMobilePhone" name="spouseMobilePhone" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">配偶身份证号</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="spouseIdNumber" name="spouseIdNumber" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">配偶就职单位</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="spouseCompany" name="spouseCompany" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">配偶职业</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="spouseOccupation" name="spouseOccupation" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">信用等级</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="creditRatingId" name="creditRatingId" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">信用等级附件</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="creditRatingAttachId" name="creditRatingAttachId" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">最后跟进时间(年月日）</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="lastTrackTime" name="lastTrackTime" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">照片</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="photoId" name="photoId" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">微信登录获取到的openid</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox" id="wxOpenId" name="wxOpenId" style="width:100%" data-options="required:true">
					</td>
				</tr>
						            </table>
		</form>
	</div>
</body>
</html>
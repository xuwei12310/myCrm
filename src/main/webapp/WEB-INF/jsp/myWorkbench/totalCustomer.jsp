<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/tag.jsp"%>
<%
  request.setAttribute("resourceIdentity", "myWorkbench:totalCustomer");
%>
<!DOCTYPE html>
<html>
<head>
  <title>列表</title>
  <base href="${ctx}">
  <meta http-equiv="content-type" content="text/html; charset=utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <%@include file="/WEB-INF/jsp/common/css.jsp"%>
<%--   <link rel="stylesheet" type="text/css" href="${ctx}/resources/comp/My97DatePicker/skin/WdatePicker.css" > --%>
  <script type="text/javascript">
		var ctx = "${ctx}";
		var resourceName = "客户";
		var resourcePath = "/myWorkbench/totalCustomer";
  </script>
  <%@include file="/WEB-INF/jsp/common/js.jsp"%>
  <script type="text/javascript" src="${ctx}/resources/js/common/crud.js"></script>
  <script type="text/javascript" src="${ctx}/resources/comp/kindeditor/kindeditor.js"></script>
  <script type="text/javascript" src="${ctx}/resources/comp/My97DatePicker/WdatePicker.js"></script>
  <script type="text/javascript" src="${ctx}/resources/comp/plupload-2.1.9/js/plupload.full.min.js"></script>
  <script type="text/javascript" src="${ctx}/resources/js/common/pluploadUtil.js"></script>
  <script type="text/javascript" src="${ctx}/resources/js/myWorkbench/totalCustomer.js"></script>
  <script type="text/javascript" src="${ctx}/resources/js/myWorkbench/customerDetails.js"></script>
  <script type="text/javascript" src="${ctx}/resources/js/myWorkbench/followAttach.js"></script>
  <script type="text/javascript">
	$(function () {
		totalCustomer.init();
	})
  </script>

   <style> 
	.spans{ color:#FF0000} 
	#exportColumnsMain li {
            float: left !important;
        }
</style>
</head>
<body class="easyui-layout" id="cenInfo">
	<div data-options="region:'north',border:false" class="searchDiv" style="height: 120px">
		<div class="easyui-layout" data-options="fit:true,selected:true">
			<div data-options="region:'north',border:false" style="height: 40px" id="queryContent">
				<table class="contentTb" style="height: 40px;width: 500px">
					<tr>
						<td style="padding-left:20px">
							<shiro:hasPermission name = "myWorkbench:totalCustomer:view">
								<a href="javascript:void(0)" onclick="totalCustomer.wholeCustomer()" style="text-decoration:none;">全部客户</a><span class="badge color-grey" id="wholeCustomerNum"></span>
							</shiro:hasPermission>
						</td>
						<td style="padding-left:20px">
							<shiro:hasPermission name = "myWorkbench:totalCustomer:viewPersonal">
								<a href="javascript:void(0)" onclick="totalCustomer.personalCustomer()" style="text-decoration:none;">个体用户</a><span class="badge color-grey" id="personalCustomerNum"></span>
							</shiro:hasPermission>
						</td>
						<td style="padding-left:20px">
							<shiro:hasPermission name = "myWorkbench:totalCustomer:viewEnterprise">
								<a href="javascript:void(0)" onclick="totalCustomer.enterpriseCustomer()()" style="text-decoration:none;">企业客户</a><span class="badge color-grey" id="enterpriseCustomerNum"></span>
							</shiro:hasPermission>
						</td>
						<td style="padding-left:20px">
							<shiro:hasPermission name = "myWorkbench:totalCustomer:viewTodayUpdate">
								<a href="javascript:void(0)" onclick="totalCustomer.todayUpdate()" style="text-decoration:none;">今日更新</a><span class="badge color-blue" id="todayUpdateNum"></span>
							</shiro:hasPermission>
						</td>
					</tr>
				</table>
					
			</div>
			<div data-options="region:'center',border:false">
				<form id="searchForm" method="post">
					<input type="hidden" name="todayTime" id="todayTime">
					<input class="easyui-textbox contentInput" name="customerName" style="width:200px;" data-options="label:'客户名称',labelWidth:100">
					<input class="easyui-textbox contentInput" name="mobilePhone" style="width:240px" data-options="label:'手机号',labelWidth:100">
					<input class="easyui-textbox contentInput" name="idNumber" style="width:240px" data-options="label:'身份证号',labelWidth:100">
					<span class="textbox combo datebox" style="font-size:0;border: 0px;">
						<label class="textbox-label" style="text-decoration:none;text-align: left; width: 79.0476px; height: 30.0952px; line-height: 30.0952px;">录入时间</label>
						<input class="easyui-validatebox Wdate dateInput" style="width:120px;" id="createTimeMins" name="createTimeMin" data-options="editable:false" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'createTimeMaxs\')}'})">
					</span>
					— <input class="easyui-validatebox Wdate dateInput" style="width:120px;" id="createTimeMaxs" name="createTimeMax" data-options="editable:false" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'createTimeMins\')}'})">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="searchBtn">查询</a><br>
					<div style="height: 5px"></div>
					<input class="easyui-textbox contentInput" name="customerType" id="customerTypeSearch"  style="width:200px" data-options="label:'类型',labelWidth:100">
					<input class="easyui-textbox contentInput" name="customerStatus.id" style="width:240px" id="customerStatusSearch"  data-options="label:'客户状态',labelWidth:100">
					<input class="easyui-textbox contentInput" name="livePlot.plotName" style="width:240px" data-options="label:'小区',labelWidth:100">
					<span class="textbox combo datebox" style="font-size:0;border: 0px;">
						<label class="textbox-label" style="text-decoration:none;text-align: left; width: 79.0476px; height: 30.0952px; line-height: 30.0952px;">最后跟进时间</label>
						<input class="easyui-validatebox Wdate dateInput" style="width:120px;" id="lastTrackTimeMins" name="lastTrackTimeMin" data-options="editable:false" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'lastTrackTimeMaxs\')}'})">
					</span>
					— <input class="easyui-validatebox Wdate dateInput" style="width:120px;" id="lastTrackTimeMaxs" name="lastTrackTimeMax" data-options="editable:false" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'lastTrackTimeMins\')}'})">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" id="clearBtn">清空</a>
				</form>
				<form id="queryCustomForm" style="display: none;">
					<div id="customList"></div>
				</form>
			</div>
		</div>
		
	</div>
	<div data-options="region:'center'">
		<table id="list" data-options="border:false"></table>
	</div>
	<div id="tb">
		<%@include file="/WEB-INF/jsp/common/btn.jsp"%>
		<shiro:hasPermission name = "myWorkbench:customerTransfer:transfer">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-role',plain:true" id="transferBtn">转交</a>
		</shiro:hasPermission>
<%-- 		<shiro:hasPermission name = "myWorkbench:totalCustomer:import"> --%>
<!-- 			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-basket_put',plain:true" id="importBtn">导入</a> -->
<%-- 		</shiro:hasPermission> --%>
		<shiro:hasPermission name = "myWorkbench:totalCustomer:export">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-excel',plain:true" id="exportBtn">导出</a>
		</shiro:hasPermission>
		<shiro:hasPermission name = "myWorkbench:totalCustomer:follow">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-followup',plain:true" id="followBtn">跟进</a>
		</shiro:hasPermission>
		<shiro:hasPermission name = "myWorkbench:totalCustomer:customQuery">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" id="customQueryBtn">自定义查询</a>
		</shiro:hasPermission>
		<shiro:hasPermission name = "myWorkbench:customerViewHistory:view">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-browse',plain:true" id="browsingHistoryBtn">浏览历史</a>
		</shiro:hasPermission>
	</div>
	<div id="editDialog">
		<form id="editForm" method="post" enctype="multipart/form-data">
            <table style="width: 980px">
            	<tr>
                	<td class="parting_line" colspan="6">客户信息</td>
            	</tr>
				<tr>
					<td class="tdFirstTitle"><span class="spans">*</span>客户名称</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox contentInput" name="customerName"  data-options="required:true">
					</td>
					<td class="tdFirstTitle"><span class="spans">*</span>性别</td>
					<td class="tdFirstContent">
                		<input class="easyui-combobox contentInput" name="sexOne" id="sexOne"  data-options="required:true">
					</td>
					<td class="tdFirstTitle"></td>
					<td class="tdFirstContent" rowspan="4">
<!-- 						<div id="Imgdiv" onclick="totalCustomer.menuSH()"> -->
						<div id="Imgdiv">
        					<img id="Img" width="200px" height="190px"/>
    					</div>
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle"><span class="spans">*</span>手机号</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox contentInput" name="mobilePhone"  data-options="required:true,validType:'mobile'">
					</td>
					<td class="tdFirstTitle">固定电话</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox contentInput" name="telephone"  data-options="required:false,validType:'phone'">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle"><span class="spans">*</span>身份证号</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox contentInput" name="idNumber" id="idNumber"  data-options="required:true,validType:'idcardTwo'">
					</td>
					<td class="tdFirstTitle">拥有人</td>
					<td class="tdFirstContent">
						<input type="hidden" name="owner.id" id="ownerId">
						<input class="easyui-textbox contentInput" id="ownerIdBtn" name="owner.username"
                          	
                          	data-options="required:false,editable:false,icons:[{iconCls:'icon-search'}]">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">邮箱</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox contentInput" name="email"  data-options="required:false,validType:'email'">
					</td>
					<td class="tdFirstTitle"><span class="spans">*</span>客户状态</td>
					<td class="tdFirstContent">
						<input class="easyui-combobox contentInput" name="customerStatus.id" id="customerStatusId"  data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle"><span class="spans">*</span>客户来源</td>
					<td class="tdFirstContent">
						<input class="easyui-combobox contentInput" name="customerSource.id" id="customerSourceId"  data-options="required:true">
					</td>
					<td class="tdFirstTitle">客户阶段</td>
					<td class="tdFirstContent">
						<input class="easyui-combobox contentInput" name="customerStage.id" id="customerStageId"  data-options="required:false">
					</td>
					<td class="tdFirstTitle"></td>
					<td class="tdFirstContent">
						<input class="easyui-filebox contentInput" id="attachPhoto" name="attachPhoto"  data-options="onChange:function(){totalCustomer.imgsrc()},prompt:'选择照片',buttonText:'选择照片',accept:'image/png,image/jpeg,image/gif'" >
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">智能评级</td>
					<td class="tdFirstContent">
						<input class="easyui-combobox contentInput" name="gradeId" id="gradeId"  data-options="required:false">
					</td>
					<td class="tdFirstTitle">婚姻状况</td>
					<td class="tdFirstContent">
						<input class="easyui-combobox contentInput" name="maritalId" id="maritalId"  data-options="required:false">
					</td>
					<td class="tdFirstTitle">出生日期</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox contentInput" name="birthdate" id="birthdate"  data-options="required:false,editable:false">
					</td>
				</tr>
				<tr>
				
					<td class="tdFirstTitle">户籍地</td>
					<td class="tdFirstContent">
						<input type="hidden" name="place.id" id="placeId">
						<input class="easyui-textbox contentInput" id="placeIdBtn" name="place.areaName"
                          	
                          	data-options="required:false,editable:false,icons:[{iconCls:'icon-search'}]">
					</td>
					<td class="tdFirstTitle"><span class="spans">*</span>居住地区</td>
					<td class="tdFirstContent">
						<input type="hidden" name="liveArea.id" id="liveAreaId">
						<input class="easyui-textbox contentInput" id="liveAreaIdBtn" name="liveArea.showName"
                          	
                          	data-options="required:true,editable:false,icons:[{iconCls:'icon-search'}]">
					</td>
					<td class="tdFirstTitle"><span class="spans">*</span>小区</td>
					<td class="tdFirstContent">
						<input type="hidden" name="livePlot.id" id="livePlotId">
						<input class="easyui-textbox contentInput" id="livePlotIdBtn" name="livePlot.plotName"
                          	
                          	data-options="required:true,editable:false,icons:[{iconCls:'icon-search'}]">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">身份证地址</td>
					<td class="tdFirstContent" colspan="5">
						<input class="easyui-textbox contentInput" name="idAddress" style="width: 854px" data-options="required:false">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">就职单位</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox contentInput" name="company"  data-options="required:false">
					</td>
					<td class="tdFirstTitle">职业</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox contentInput" name="occupation"  data-options="required:false">
					</td>
				</tr>
				<tr>
                	<td class="parting_line" colspan="6">配偶信息</td>
            	</tr>
				<tr>
					<td class="tdFirstTitle">配偶姓名</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox contentInput" name="spouseName"  data-options="required:false">
					</td>
					<td class="tdFirstTitle">配偶户籍地</td>
					<td class="tdFirstContent">
						<input type="hidden" name="spousePlace.id" id="spousePlaceId">
						<input class="easyui-textbox contentInput" id="spousePlaceIdBtn" name="spousePlace.areaName"
                          	
                          	data-options="required:false,editable:false,icons:[{iconCls:'icon-search'}]">
					</td>
					<td class="tdFirstTitle">配偶手机号</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox contentInput" name="spouseMobilePhone"  data-options="required:false,validType:'mobile'">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">配偶身份证号</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox contentInput" name="spouseIdNumber"  data-options="required:false,validType:'idcardTwo'">
					</td>
					<td class="tdFirstTitle">配偶就职单位</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox contentInput" name="spouseCompany"  data-options="required:false">
					</td>
					<td class="tdFirstTitle">配偶职业</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox contentInput" name="spouseOccupation"  data-options="required:false">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">备注</td>
					<td class="tdFirstContent" colspan="5" >
						<input class="easyui-textbox" name="note"  style="width:854px;height:60px" data-options="multiline:true">
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<div id="addSuccessDialog">
		<input type="hidden" id="addId">
		<div align="center" style="margin-top: 30px; color: red">
			请选择继续完善产权信息,车产信息,信用状况等信息<br> 或选择退出编辑界面
		</div>
		<div align="center" >
			<table class="contentAddContinue">
			<tr>
				<td class="tdFirstTitle"><a href="javascript:void(0)"
					class="easyui-linkbutton" data-options="iconCls:'icon-edit'"
					id="continueUpdateBtn">继续完善</a></td>
				<td class="tdFirstTitle"><a href="javascript:void(0)"
					class="easyui-linkbutton" data-options="iconCls:'icon-exit'"
					id="exitAddSuccessDialogBtn">直接退出</a></td>
			</tr>
		</table>
		</div>
	</div>
	
	<div id="modEditDialog">
		<div id="updateCustomerTabs" class="easyui-tabs" data-options="fit:true,border:false">
			 <div title="基本信息">
			 	<div class="easyui-layout" data-options="fit:true,selected:true">
			 		<div data-options="region:'north',border:false"
						style="height: 40px">
						<a id="saveUpdateBtn" href="javascript:void(0)"
							class="easyui-linkbutton"
							data-options="plain:true,iconCls:'icon-save'">保存</a>
					</div>
					<div data-options="region:'center',border:false">
						<form id="editModForm" method="post" enctype="multipart/form-data">
							<input type="hidden" name="id" id="idUpdate">
            				<table style="width: 980px">
            					<tr>
                					<td class="parting_line" colspan="6">客户信息</td>
            					</tr>
								<tr>
									<td class="tdFirstTitle"><span class="spans">*</span>客户名称</td>
									<td class="tdFirstContent">
										<input class="easyui-textbox contentInput" name="customerName"  data-options="required:true">
									</td>
									<td class="tdFirstTitle"><span class="spans">*</span>性别</td>
									<td class="tdFirstContent">
                						<input class="easyui-combobox contentInput" name="sex" id="sex"  data-options="required:true">
									</td>
									<td class="tdFirstTitle"></td>
									<td class="tdFirstContent"  colspan="2" rowspan="4">
										<input type="hidden" name="attachId" id="attachId">
										<div id="ImgModdiv">
        									<img id="ImgMod" width="200px" height="190px"/>
    									</div>
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle"><span class="spans">*</span>手机号</td>
									<td class="tdFirstContent">
										<input class="easyui-textbox contentInput" name="mobilePhone"  data-options="required:true,validType:'mobile'">
									</td>
									<td class="tdFirstTitle">固定电话</td>
									<td class="tdFirstContent">
										<input class="easyui-textbox contentInput" name="telephone"  data-options="required:false,validType:'phone'">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle"><span class="spans">*</span>身份证号</td>
									<td class="tdFirstContent">
										<input class="easyui-textbox contentInput" name="idNumber" id="idNumberMod"  data-options="required:true,validType:'idcardTwo'">
									</td>
									<td class="tdFirstTitle">拥有人</td>
									<td class="tdFirstContent">
										<input type="hidden" name="owner.id" id="ownerIdMod">
										<input class="easyui-textbox contentInput" id="ownerIdModBtn" name="owner.username"
	                 			         	
	                			          	data-options="required:false,editable:false,icons:[{iconCls:'icon-search'}]">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle">邮箱</td>
									<td class="tdFirstContent">
										<input class="easyui-textbox contentInput" name="email"  data-options="required:false,validType:'email'">
									</td>
									<td class="tdFirstTitle"><span class="spans">*</span>客户状态</td>
									<td class="tdFirstContent">
										<input class="easyui-combobox contentInput" name="customerStatus.id" id="customerStatusIdMod"  data-options="required:true">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle"><span class="spans">*</span>客户来源</td>
									<td class="tdFirstContent">
										<input class="easyui-combobox contentInput" name="customerSource.id" id="customerSourceIdMod"  data-options="required:true">
									</td>
									<td class="tdFirstTitle">客户阶段</td>
									<td class="tdFirstContent">
										<input class="easyui-combobox contentInput" name="customerStage.id" id="customerStageIdMod"  data-options="required:false">
									</td>
									<td class="tdFirstTitle"></td>
									<td class="tdFirstContent">
										<input class="easyui-filebox contentInput" id="attachPhotoMod" name="attachPhotoMod" data-options="onChange:function(){totalCustomer.imgsrcMod()},prompt:'选择照片',buttonText:'选择照片',accept:'image/png,image/jpeg,image/gif'" >
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle">智能评级</td>
									<td class="tdFirstContent">
										<input class="easyui-combobox contentInput" name="gradeId" id="gradeIdMod"  data-options="required:false">
									</td>
									<td class="tdFirstTitle">婚姻状况</td>
									<td class="tdFirstContent">
										<input class="easyui-combobox contentInput" name="maritalId" id="maritalIdMod"  data-options="required:false">
									</td>
									<td class="tdFirstTitle">出生日期</td>
									<td class="tdFirstContent">
										<input class="easyui-textbox contentInput" name="birthdate" id="birthdateMod"  data-options="required:false,editable:false">
									</td>
								</tr>
								<tr>
									<td class="tdFirstTitle">户籍地</td>
									<td class="tdFirstContent">
										<input type="hidden" name="place.id" id="placeIdMod">
										<input class="easyui-textbox contentInput" id="placeIdModBtn" name="place.areaName"
                          					
                          					data-options="required:false,editable:false,icons:[{iconCls:'icon-search'}]">
									</td>
								<td class="tdFirstTitle"><span class="spans">*</span>居住地区</td>
								<td class="tdFirstContent">
									<input type="hidden" name="liveArea.id" id="liveAreaIdMod">
									<input class="easyui-textbox contentInput" id="liveAreaIdModBtn" name="liveArea.showName"
                          				
                          				data-options="required:true,editable:false,icons:[{iconCls:'icon-search'}]">
								</td>
								<td class="tdFirstTitle"><span class="spans">*</span>小区</td>
								<td class="tdFirstContent">
									<input type="hidden" name="livePlot.id" id="livePlotIdMod">
									<input class="easyui-textbox contentInput" id="livePlotIdModBtn" name="livePlot.plotName"
                         			 	
                        			  	data-options="required:true,editable:false,icons:[{iconCls:'icon-search'}]">
								</td>
							</tr>
							<tr>
								<td class="tdFirstTitle">身份证地址</td>
								<td class="tdFirstContent" colspan="5">
									<input class="easyui-textbox contentInput" name="idAddress" style="width: 854px" data-options="required:false">
								</td>
							</tr>
							<tr>
								<td class="tdFirstTitle">就职单位</td>
								<td class="tdFirstContent">
									<input class="easyui-textbox contentInput" name="company"  data-options="required:false">
								</td>
								<td class="tdFirstTitle">职业</td>
								<td class="tdFirstContent">
									<input class="easyui-textbox contentInput" name="occupation"  data-options="required:false">
								</td>
							</tr>
							<tr>
                				<td class="parting_line" colspan="6">配偶信息</td>
            				</tr>
							<tr>
								<td class="tdFirstTitle">配偶姓名</td>
								<td class="tdFirstContent">
									<input class="easyui-textbox contentInput" name="spouseName"  data-options="required:false">
								</td>
								<td class="tdFirstTitle">配偶户籍地</td>
								<td class="tdFirstContent">
									<input type="hidden" name="spousePlace.id" id="spousePlaceIdMod">
									<input class="easyui-textbox contentInput" id="spousePlaceIdModBtn" name="spousePlace.areaName"
                          				
                          				data-options="required:false,editable:false,icons:[{iconCls:'icon-search'}]">
								</td>
								<td class="tdFirstTitle">配偶手机号</td>
								<td class="tdFirstContent">
									<input class="easyui-textbox contentInput" name="spouseMobilePhone"  data-options="required:false,validType:'mobile'">
								</td>
							</tr>
							<tr>
								<td class="tdFirstTitle">配偶身份证号</td>
								<td class="tdFirstContent">
									<input class="easyui-textbox contentInput" name="spouseIdNumber"  data-options="required:false,validType:'idcardTwo'">
								</td>
								<td class="tdFirstTitle">配偶就职单位</td>
								<td class="tdFirstContent">
									<input class="easyui-textbox contentInput" name="spouseCompany"  data-options="required:false">
								</td>
								<td class="tdFirstTitle">配偶职业</td>
								<td class="tdFirstContent">
									<input class="easyui-textbox contentInput" name="spouseOccupation"  data-options="required:false">
								</td>
							</tr>
							<tr>
								<td class="tdFirstTitle">备注</td>
								<td class="tdFirstContent" colspan="5" >
									<input class="easyui-textbox" name="note"  style="width:854px;height:60px" data-options="multiline:true">
								</td>
							</tr>
						</table>
					</form>
					</div>
			 	</div>
			 </div>
			 <!-- End 基本信息 -->
		     <div title="产权信息">
		         <table id="customerPropertyList" data-options="border:false"></table>
		     </div>
		     <!-- End 产权信息 -->
		     <div title="车产信息">
		         <table id="customerCarList" data-options="border:false"></table>
		     </div>
		     <!-- End 车产信息 -->
		     <div title="信用状况">
		         <div class="easyui-layout" data-options="fit:true,selected:true">
			 		<div data-options="region:'north',border:false"
						style="height: 40px">
						<a id="saveCreditBtn" href="javascript:void(0)"
							class="easyui-linkbutton"
							data-options="plain:true,iconCls:'icon-save'">保存</a>
					</div>
					<div data-options="region:'center',border:false">
						<form id="editCreditForm" method="post" enctype="multipart/form-data">
							<table  style="width:400px">
            					<tr>
									<td class="tdFirstTitle">信用等级:</td>
									<td class="tdFirstContent">
										<input type="hidden" name="id" id="cusCreditId">	
										<input class="easyui-combobox contentInput" name="creditRatingId" id="creditRatingId"  data-options="required:false">
									</td>
									<td></td>
								</tr>
								<tr>
									<td class="tdFirstTitle">信用等级附件:</td>
									<td class="tdFirstContent">
										<input type="hidden" name="creditRatingAttachId" id="creditRatingAttachId">	
										<input class="easyui-filebox contentInput" id="attachCreditName" name="attachCreditName"  data-options="prompt:'选择附件',buttonText:'选择附件',accept:'image/png,image/jpeg,image/gif'" >
									</td>
									<td class="tdFirstContent">
										<span id="crId">&nbsp;&nbsp;<a href="javascript:void(0)" style="text-decoration:none;" onclick="customerDetails.seeCreditAttach();">预览</a></span>
									</td>
								</tr>
<!-- 								<tr> -->
<!-- 									<td class="tdFirstTitle"></td> -->
<!-- 									<td class="tdFirstContent"> -->
<!-- 										<span id="attachCName"></span> -->
										
<!-- 									</td> -->
<!-- 									<td class="tdFirstContent"> -->
<!-- 										<span id="cusCreditType">&nbsp;&nbsp;<a href="javascript:void(0)" style="text-decoration:none;" onclick="customerDetails.previewCreditAttach();">预览</a></span> -->
<!-- 									</td> -->
<!-- 								</tr> -->
							</table>
						</form>
					</div>
				</div>
		     </div>
		     <!-- End 信用状况 -->
		     <div title="联系人">
		         <table id="customerRelationshipList" data-options="border:false"></table>
		     </div>
		     <!-- End 联系人 -->
		     <div title="其他财力">
		         <table id="customerOtherResourcesList" data-options="border:false"></table>
		     </div>
		     <!-- End 其他财力 -->
		     <div title="负债">
		         <table id="customerLiabilitiesList" data-options="border:false"></table>
		     </div>
		     <!-- End 负债 -->
		</div>
	</div>
	
	<!-- 新增小区 -->
	<div id="addPlotDialog">
		<form id="addPlotForm" method="post">
            <table>
            	<tr>
					<td class="tdFirstTitle">居住地区</td>
					<td class="tdFirstContent">
						<span id = "liveAreaName"></span>
					</td>
				</tr>
				<tr>
					<td class="tdTitle"><span class="spans">*</span>小区名称</td>
					<td class="tdContent">
						<input type="hidden" name="area.id" id="addPlotAreaId">
						<input class="easyui-textbox contentInput" name="plotName"  data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdTitle"><span class="spans">*</span>状态</td>
					<td class="tdContent">
						<select id="status" class="easyui-combobox contentInput" name="status" data-options="required:true,editable:false">
					         <option value="1" selected="selected">启用</option>
    						 <option value="0">禁用</option>
					    </select>
					</td>
				</tr>
				<tr>
					<td class="tdTitle">备注</td>
					<td class="tdContent">
						<input class="easyui-textbox contentInput" name="note" >
					</td>
				</tr>
				</table>
		</form>
	</div>
	
	<!-- 新增修改负债信息 -->
	<div id="customerLiabilitiesDialog">
		<form id="customerLiabilitiesForm" method="post">
            <table style="width:400px">
            	<tr>
					<td class="tdFirstTitle" width="70px"><span class="spans">*</span>负债名称:</td>
					<td class="tdFirstContent">
						<input type="hidden" name="id">	
						<input type="hidden" name="customerId" id="cusLiabilitiesId">	
						<input class="easyui-textbox contentInput" name="name"  data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdTitle"><span class="spans">*</span>金额(万元):</td>
					<td class="tdContent">
						<input class="easyui-numberbox contentInput" name="value"  data-options="required:true,min:0,precision:4">
					</td>
				</tr>
				<tr>
					<td class="tdTitle">备注</td>
					<td class="tdContent">
						<input class="easyui-textbox contentInput" name="note"  style="height:100px" data-options="multiline:true">
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<!-- 新增修改其他财力信息 -->
	<div id="customerOtherResourcesDialog">
		<form id="customerOtherResourcesForm" method="post">
            <table style="width:400px">
            	<tr>
					<td class="tdFirstTitle" width="70px"><span class="spans">*</span>财力名称:</td>
					<td class="tdFirstContent">
						<input type="hidden" name="id">	
						<input type="hidden" name="customerId" id="cusOtherResourcesId">	
						<input class="easyui-textbox contentInput" name="name"  data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdTitle"><span class="spans">*</span>价值(万元):</td>
					<td class="tdContent">
						<input class="easyui-numberbox contentInput" name="value"  data-options="required:true,min:0,precision:4">
					</td>
				</tr>
				<tr>
					<td class="tdTitle">备注</td>
					<td class="tdContent">
						<input class="easyui-textbox contentInput" name="note"  style="height:100px" data-options="multiline:true">
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<!-- 新增修改联系人 -->
	<div id="customerRelationshipEditDialog">
		<form id="customerRelationshipForm" method="post" enctype="multipart/form-data">
            <table style="width:400px">
            	<tr>
					<td class="tdFirstTitle" width="70px"><span class="spans">*</span>姓名:</td>
					<td class="tdFirstContent" colspan="3">
						<input type="hidden" name="id">	
						<input type="hidden" name="customerId" id="cusRelationshipId">	
						<input class="easyui-textbox contentInput" name="name"  data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdTitle">性别:</td>
					<td class="tdContent" colspan="3">
						<input class="easyui-combobox contentInput" name="sex" id="sexRts"  data-options="required:false">
					</td>
				</tr>
				<tr>
					<td class="tdTitle"><span class="spans">*</span>关系:</td>
					<td class="tdContent" colspan="3">
						<input class="easyui-textbox contentInput" name="relationship"  data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdTitle"><span class="spans">*</span>联系电话:</td>
					<td class="tdContent" colspan="3">
						<input class="easyui-textbox contentInput" name="telephone"  data-options="required:true,validType:'mobile'">
					</td>
				</tr>
				<tr>
					<td class="tdTitle"><span class="spans">*</span>身份证号:</td>
					<td class="tdContent" colspan="3">
						<input class="easyui-textbox contentInput" name="idNumber"   data-options="required:true,validType:'idcardTwo'">
					</td>
				</tr>
				<tr>
					<td class="tdTitle">单位:</td>
					<td class="tdContent" colspan="3">
						<input class="easyui-textbox contentInput" name="unit"  data-options="required:false">
					</td>
				</tr>
				<tr>
					<td class="tdTitle">职业:</td>
					<td class="tdContent" colspan="3">
						<input class="easyui-textbox contentInput" name="vocation"  data-options="required:false">
					</td>
				</tr>
				<tr>
					<td class="tdTitle">上传附件:</td>
					<td class="tdContent" colspan="3">
						<input type="hidden" name="attachId" id="rtsAttachId">	
						<input class="easyui-filebox contentInput" id="attachRtsName" name="attachRtsName" data-options="prompt:'选择附件',buttonText:'选择附件',accept:'image/png,image/jpeg,image/gif'" >
						<span>&nbsp;&nbsp;<a href="javascript:void(0)" style="text-decoration:none;" onclick="customerDetails.seeRtsAttach();">预览</a></span>
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<!-- 新增修改车产信息 -->
	<div id="customerCarEditDialog">
		<form id="customerCarForm" method="post" enctype="multipart/form-data">
            <table style="width:420px">
            	<tr>
					<td class="tdFirstTitle" width="70px"><span class="spans">*</span>品牌:</td>
					<td class="tdFirstContent">
						<input type="hidden" name="id">	
						<input type="hidden" name="customerId" id="cusCarId">	
						<input class="easyui-textbox contentInput" name="brand"  data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdTitle"><span class="spans">*</span>型号:</td>
					<td class="tdContent">
						<input class="easyui-textbox contentInput" name="model"  data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdTitle"><span class="spans">*</span>年份:</td>
					<td class="tdContent">
						<input class="contentInput easyui-validatebox Wdate dateInput" id="years" name="years" data-options="required:true,editable:false" onfocus="WdatePicker({dateFmt:'yyyy'})">
					</td>
				</tr>
				<tr>
					<td class="tdTitle"><span class="spans">*</span>估值(万元):</td>
					<td class="tdContent">
						<input class="easyui-numberbox contentInput" name="valuation" data-options="required:true,min:0,precision:4">
					</td>
				</tr>
				<tr>
					<td class="tdTitle">车况:</td>
					<td class="tdContent">
						<input class="easyui-textbox contentInput" name="carStatus" >
					</td>
				</tr>
				<tr>
					<td class="tdTitle">上传附件:</td>
					<td class="tdContent">
						<input type="hidden" name="attachId" id="carAttachId">	
						<input class="easyui-filebox contentInput" id="attachCarName" name="attachCarName"  data-options="prompt:'选择附件',buttonText:'选择附件',accept:'image/png,image/jpeg,image/gif'" >
						<span>&nbsp;&nbsp;<a href="javascript:void(0)" style="text-decoration:none;" onclick="customerDetails.seeCarAttach();">预览</a></span>
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<!-- 新增修改产权信息 -->
	<div id="customerPropertyDialog">
		<form id="customerPropertyForm" method="post" enctype="multipart/form-data">
            <table style="width: 680px">
            	<tr>
					<td class="tdFirstTitle" width="100px"><span class="spans">*</span>产权证号:</td>
					<td class="tdFirstContent" colspan="2">
						<input type="hidden" name="id">
						<input class="easyui-textbox contentInput" name="certificate"  data-options="required:true">
					</td>
					<td class="tdFirstTitle"  width="100px"><span class="spans">*</span>所有人:</td>
					<td class="tdFirstContent" colspan="2">
						<input type="hidden" name="customerId" id="cusPropertyId">
						<input class="easyui-textbox contentInput" name="owner"  data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">房屋地址:</td>
					<td class="tdFirstContent" colspan="2">
						<input class="easyui-textbox contentInput" name="houseAddress" >
					</td>
					<td class="tdFirstTitle">土地证号:</td>
					<td class="tdFirstContent" colspan="2">
						<input class="easyui-textbox contentInput" name="landCertificateNumber" >
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">共有情况:</td>
					<td class="tdFirstContent" colspan="2">
						<select id="isCommon" class="easyui-combobox contentInput" name="isCommon" data-options="editable:false">
					         <option value="1" selected="selected">是</option>
    						 <option value="0">否</option>
					    </select>
					</td>
					<td class="tdFirstTitle">房屋性质:</td>
					<td class="tdFirstContent" colspan="2">
						<input class="easyui-combobox contentInput" name="housingNature.id" id="housingNatureId"  data-options="required:false">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">有无土地证:</td>
					<td class="tdFirstContent" colspan="2">
						<select id="havaLandCertificate" class="easyui-combobox contentInput" name="havaLandCertificate" data-options="editable:false">
					         <option value="1" selected="selected">有</option>
    						 <option value="0">无</option>
					    </select>
					</td>
					<td class="tdFirstTitle">土地性质:</td>
					<td class="tdFirstContent" colspan="2">
						<input class="easyui-combobox contentInput" name="landNature.id" id="landNatureId"  data-options="required:false">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle"><span class="spans">*</span>居住地区:</td>
					<td class="tdFirstContent" colspan="2">
						<input type="hidden" name="areaId.id" id="areasid">
						<input class="easyui-textbox contentInput" id="areasidBtn" name="areaShowName"
                          	
                          	data-options="required:true,editable:false,icons:[{iconCls:'icon-search'}]">
					</td>
					<td class="tdFirstTitle"><span class="spans">*</span>小区:</td>
					<td class="tdFirstContent" colspan="2">
					<input type="hidden" name="plotId.id" id="plotsid">
						<input class="easyui-textbox contentInput" id="plotsidBtn" name="plotPlotName"
                          	
                          	data-options="required:true,editable:false,icons:[{iconCls:'icon-search'}]">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">建筑面积(平方):</td>
					<td class="tdFirstContent" colspan="2">
						<input class="easyui-numberbox contentInput" name="area" data-options="required:true,min:0">
					</td>
					<td class="tdFirstTitle">房产价值(万元):</td>
					<td class="tdFirstContent" colspan="2">
						<input class="easyui-numberbox contentInput" name="propertyValue" data-options="min:0,precision:4">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">备注:</td>
					<td class="tdFirstContent" colspan="5">
						<input class="easyui-textbox" name="note"  style="width:539px;height:60px" data-options="multiline:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle">上传附件:</td>
					<td class="tdFirstContent" colspan="2">
						<input type="hidden" name="attachId" id="proAttachId">	
						<input class="easyui-filebox contentInput" id="attachName"  name="attachName"  data-options="prompt:'选择附件',buttonText:'选择附件',accept:'image/png,image/jpeg,image/gif'" >
					</td>
					<td class="tdFirstContent" colspan="3">
						<span>&nbsp;&nbsp;<a href="javascript:void(0)" style="text-decoration:none;" onclick="customerDetails.seeAttach();">预览</a></span>
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<!-- 户籍地 -->
	<div id="placeEditDialog">
		<div class="easyui-layout" data-options="fit:true,selected:true">
			 <div data-options="region:'north',border:false" class="searchConditonDiv">
				<form id="searchPlaceForm" method="post">
	   		 		<input class="easyui-textbox contentInput" name="areaName"  data-options="label:'区/县名称',labelWidth:80">
				 	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="searchPlaceBtn">查询</a>
				</form>
			</div>
			<div data-options="region:'center',border:false">
				<div id="placeList" ></div>
			</div>
		</div>
	</div>
	<!-- 配偶户籍地 -->
	<div id="spousePlaceEditDialog">
		<div class="easyui-layout" data-options="fit:true,selected:true">
			 <div data-options="region:'north',border:false" class="searchConditonDiv">
				<form id="searchSpousePlaceForm" method="post">
	   		 		<input class="easyui-textbox contentInput" name="areaName" style="width:220px" data-options="label:'区/县名称',labelWidth:80">
			 		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="searchSpousePlaceBtn">查询</a>
				</form>
			</div>
			<div data-options="region:'center',border:false">
				<div id="spousePlaceList" ></div>
			</div>
		</div>
	</div>
	<!-- 居住地区 -->
	<div id="liveAreaEditDialog">
		<div class="easyui-layout" data-options="fit:true,selected:true">
			 <div data-options="region:'north',border:false" class="searchConditonDiv">
				<form id="searchLiveAreaForm" method="post">
	   				 <input class="easyui-textbox contentInput" name="areaName" style="width:220px" data-options="label:'区/县名称',labelWidth:80">
			 		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="searchLiveAreaBtn">查询</a>
				</form>
			</div>
			<div data-options="region:'center',border:false">
				<div id="liveAreaList" ></div>
			</div>
		</div>
	</div>
	<!-- 小区 -->
	<div id="livePlotEditDialog">
		<div class="easyui-layout" data-options="fit:true,selected:true">
			 <div data-options="region:'north',border:false" class="searchConditonDiv">
				<form id="searchLivePlotForm" method="post">
			 		<input type="hidden" name="liveAreaid" id="areaData">
	   		 		<input class="easyui-textbox contentInput" name="plotName" style="width:220px" data-options="label:'小区名',labelWidth:80">
			 		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="searchLivePlotBtn">查询</a>
				</form>
			</div>
			<div data-options="region:'center',border:false">
				<div id="livePlotList" ></div>
			</div>
		</div>
	</div>
	<!-- 拥有人 -->
	<div id="ownerEditDialog">
		<div class="easyui-layout" data-options="fit:true,selected:true">
			 <div data-options="region:'north',border:false" class="searchConditonDiv">
				<form id="searchOwnerForm" method="post">
	   			 	<input class="easyui-textbox contentInput" name="userName" style="width:220px" data-options="label:'姓名',labelWidth:80">
			 		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="searchOwnerBtn">查询</a>
				</form>
			</div>
			<div data-options="region:'center',border:false">
				<div id="ownerList" ></div>
			</div>
		</div>
	</div>
	
	<!-- 户籍地 -->
	<div id="placeModEditDialog">
		<div class="easyui-layout" data-options="fit:true,selected:true">
			 <div data-options="region:'north',border:false" class="searchConditonDiv">
				<form id="searchPlaceModForm" method="post">
	   		 		<input class="easyui-textbox contentInput" name="areaName" style="width:220px" data-options="label:'区/县名称',labelWidth:80">
			 		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="searchPlaceModBtn">查询</a>
				</form>
			</div>
			<div data-options="region:'center',border:false">
				<div id="placeModList" ></div>
			</div>
		</div>
	</div>
	<!-- 配偶户籍地 -->
	<div id="spousePlaceModEditDialog">
		<div class="easyui-layout" data-options="fit:true,selected:true">
			 <div data-options="region:'north',border:false" class="searchConditonDiv">
				<form id="searchSpousePlaceModForm" method="post">
	   				<input class="easyui-textbox contentInput" name="areaName" style="width:220px" data-options="label:'区/县名称',labelWidth:80">
			 		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="searchSpousePlaceModBtn">查询</a>
				</form>
			</div>
			<div data-options="region:'center',border:false">
				<div id="spousePlaceModList" ></div>
			</div>
		</div>	
	</div>
	<!-- 居住地区 -->
	<div id="liveAreaModEditDialog">
		<div class="easyui-layout" data-options="fit:true,selected:true">
			 <div data-options="region:'north',border:false" class="searchConditonDiv">
				<form id="searchLiveAreaModForm" method="post">
	   		 		<input class="easyui-textbox contentInput" name="areaName" style="width:220px" data-options="label:'区/县名称',labelWidth:80">
			 		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="searchLiveAreaModBtn">查询</a>
				</form>
			</div>
			<div data-options="region:'center',border:false">
				<div id="liveAreaModList" ></div>
			</div>
		</div>
	</div>
	<!-- 小区 -->
	<div id="livePlotModEditDialog">
		<div class="easyui-layout" data-options="fit:true,selected:true">
			 <div data-options="region:'north',border:false" class="searchConditonDiv">
				<form id="searchLivePlotModForm" method="post">
				 	<input type="hidden" name="liveAreaid" id="areaDataOne">
	   			 	<input class="easyui-textbox contentInput" name="plotName" style="width:220px" data-options="label:'小区名',labelWidth:80">
			 		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="searchLivePlotModBtn">查询</a>
				</form>
			</div>
			<div data-options="region:'center',border:false">
				<div id="livePlotModList" ></div>
			</div>
		</div>	
	</div>
	<!-- 拥有人 -->
	<div id="ownerModEditDialog">
		<div class="easyui-layout" data-options="fit:true,selected:true">
			 <div data-options="region:'north',border:false" class="searchConditonDiv">
				<form id="searchOwnerModForm" method="post">
	   				 <input class="easyui-textbox contentInput" name="userName" style="width:220px" data-options="label:'姓名',labelWidth:80">
					 <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="searchOwnerModBtn">查询</a>
				</form>	
			</div>
			<div data-options="region:'center',border:false">
				<div id="ownerModList" ></div>
			</div>
		</div>
	</div>
	<div id="proAreaEditDialog">
		<div class="easyui-layout" data-options="fit:true,selected:true">
			 <div data-options="region:'north',border:false" class="searchConditonDiv">
				<form id="searchProAreaForm" method="post">
	   		 		<input class="easyui-textbox contentInput" name="areaName" style="width:220px" data-options="label:'区/县名称',labelWidth:80">
			 		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="searchProAreaBtn">查询</a>
				</form>
			</div>
			<div data-options="region:'center',border:false">
				<div id="proAreaList" ></div>
			</div>
		</div>
	</div>
	<div id="proPlotEditDialog">
		<div class="easyui-layout" data-options="fit:true,selected:true">
			 <div data-options="region:'north',border:false" class="searchConditonDiv">
				<form id="searchProPlotForm" method="post">
			 		<input type="hidden" name="liveAreaid" id="areaProData">
	   		 		<input class="easyui-textbox contentInput" name="plotName" style="width:220px" data-options="label:'小区名',labelWidth:80">
			 		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="searchProPlotBtn">查询</a>
				</form>
			</div>
			<div data-options="region:'center',border:false">
				<div id="proPlotList" ></div>
			</div>
		</div>
	</div>
	<!-- 附件预览 -->
	<div id="imgEditDialog" align="center">
		<form>
			<img id="ImgPreview"/>
		</form>
	</div>
	<!-- 转交 -->
	<div id="transferEditDialog">
		<form id="transferForm" method="post">
            <table style="width: 360px">
            	<tr>
					<td class="tdFirstContent" colspan="2" align="center">
						移交客户名称：<span id = "transferName"></span>
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle"><span class="spans">*</span>转给:</td>
					<td class="tdFirstContent">
						<input type="hidden" name="customer.id" id="cusTransferId">
						<input type="hidden" name="fromUser.id" id="fromUserId">
						<input type="hidden" name="toUser.id" id="toUserId">
						<input class="easyui-textbox contentInput" id="toUserBtn" name="toUserName"
                          	
                          	data-options="required:true,editable:false,icons:[{iconCls:'icon-search'}]">
					</td>
				</tr>
				<tr>
					<td class="tdFirstTitle"><span class="spans">*</span>原因:</td>
					<td class="tdFirstContent">
						<input class="easyui-textbox contentInput" name="reason"  style="height:100px" data-options="required:true,multiline:true">
					</td>
				</tr>
				<tr>
					<td class="tdFirstContent" colspan="2" align="center">
						<a href="javascript:void(0)" onclick="totalCustomer.TransferRecord()" style="text-decoration:none;">查看转交历史</a>
					</td>
				</tr>
				</table>
		</form>
	</div>
	<!-- 转交人 -->
	<div id="transferUserEditDialog">
		<div class="easyui-layout" data-options="fit:true,selected:true">
			 <div data-options="region:'north',border:false" class="searchConditonDiv">
				<form id="searchTransferUserForm" method="post">
	   				 <input class="easyui-textbox contentInput" name="userName" data-options="label:'姓名',labelWidth:80">
					 <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="searchTransferUserBtn">查询</a>
				</form>	
			</div>
			<div data-options="region:'center',border:false">
				<div id="transferUserList" ></div>
			</div>
		</div>
	</div>
	<!-- 转交记录-->
	<div id="transferRecordEditDialog">
		<div id="transferRecordList" ></div>
	</div>
	<!-- 导出excel -->
	<div id="exportDialogMain" style="padding: 10px">
    	<form id="exportFormMain" method="post">
       	 	<div style="width:100%;height:30px;line-height:20px">
            	<span style="float: left; padding-top: 5px;">标题：</span>
            	<input class="easyui-textbox contentInput" name="titleExport" id="titleExport" style="width: 310px;height:20px"
                  	 data-options="">
                  	 &nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" id="allChooseMain">全选</a>
                  	 &nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" id="reverseChooseMain">反选</a>
       		 </div>
        	 <ul id="exportColumnsMain"></ul>
        	 <input type="hidden" id="fieldsMain" name="fieldsExport"> <input
            		type="hidden" id="fieldNamesMain" name="fieldNamesExport"> <input
            		type="hidden" id="defaultTitleExport"> <input type="hidden"
                                                          id="selectedIds" name="selectedIds"/>
         	 <input type="hidden" id="participantsIds" name="participantsIds"/>
        	 <div id="exportParamsDiv"></div>
        	 <button type="submit" id="submitBtn" style="display: none"></button>
    	</form>
	</div>
	<!-- 浏览记录-->
	<div id="viewHistoryEditDialog">
		<div id="viewHistoryList" ></div>
	</div>
	<!-- 跟进 -->
	<div id="followEditDialog" style="margin-bottom: 15px;padding-bottom: 5px">
				<form id="followEditForm" method="post">
					<input type="hidden" name="id">
					<input type="hidden" name="attachIds" id="attachIds">
					<input type="hidden" id="customerId" name="customer.id">
					<input type="hidden" id="followPersonnelId" name="followPersonnel.id">
					<input type="hidden" id="taskFollowPersonnelId" name="doUserId.id" >
					<input type="hidden" id="ids" >
					<input type="hidden" id="isActive" name="isActive">
					<input type="hidden" id="storeId">
					<input type="hidden" id="storeName">
					<table class="contentTb" id="followTable" style="padding-bottom: 5px">
						<tr>
							<td class="tdTitle" style="width: 20%"><span style="color: red">*</span>客户：</td>
							<td class="tdContent">
								<span id="customerName"></span>
			<!-- 						<input class="easyui-textbox contentInput" id="customerName" name="customer.customerName"  data-options="required:true,editable:false,icons:[{iconCls:'icon-search'}]"> -->
							</td>
							<td class="tdTitle"><span style="color: red">*</span>跟进人员：</td>
							<td class="tdContent">
								<input class="easyui-textbox  contentInput" id="followPersonnel" name="followPersonnel.name" data-options="required:true,editable:false,icons:[{iconCls:'icon-search'}]">
							</td>
						</tr>
						<tr>    
							<td class="tdTitle"><span style="color: red">*</span>跟进时间：</td>
							<td class="tdContent">
								<input class="contentInput easyui-validatebox Wdate dateInput"  id="startDate" name="followTime" data-options="required:true,editable:false" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})">
							</td>
							<td class="tdTitle"><span style="color: red">*</span>跟 进 方 式：</td>
							<td class="tdContent">
								<input class="easyui-combobox contentInput"  id="followType" name="followType.id"  data-options="required:true,editable:false">
							</td>
						</tr>
						<tr>
							<td class="tdTitle"><span style="color: red">*</span>跟进详情：</td>
							<td class="tdContent" style="width: 442px" colspan="3">
								<textarea id="followDetails" name="followDetails"></textarea>
							</td>
						</tr>
						<tr>
							<td class="tdTitle"><span style="color: red">*</span>跟进阶段：</td>
							<td class="tdContent">
								<input class="easyui-combobox contentInput" id="followStage" name="followStage.id"  data-options="required:true,editable:false">
							</td>
							<td class="tdTitle"><span style="color: red">*</span>更新客户状态:</td>
							<td class="tdContent">
								<input class="easyui-combobox contentInput" id="customerStatus" name="afterStatus.id"  data-options="required:true,editable:false">
							</td>
						</tr>
<!-- 						<tr> -->
<!-- 							<td class="tdTitle">上传附件：</td> -->
<!-- 							<td class="tdContent"> -->
<!-- 								<input class="easyui-filebox contentInput" data-options="buttonText:'选择文件'"  -->
<!-- 								name="attach" id="file" /> -->
<!-- 							</td> -->
<!-- 						</tr> -->
						<tr style="margin-top: 10px;margin-bottom: 5px;height: 35px" id="create">
							<td class="tdTitle"><input type="checkbox" style="height: 17px;width: 17px;margin-top: 2px;margin-right: 5px" id="createMission"></td>
							<td class="tdContent">
								<h4 style="font-weight: 700;font-size: 16px">创建下次跟进任务</h4>
							</td>
						</tr>
						<tbody style="display: none;" id="missionBolock">
							<tr>
							    <td class="tdTitle"><span style="color: red">*</span>跟进内容：</td>
							    <td class="tdContent" colspan="3">
								     <input class="easyui-textbox" id="taskContent" name="taskContent" style="width: 504px;height:80px" data-options="multiline:true">
							    </td>
						    </tr>
							<tr>
								<td class="tdTitle"><span style="color: red">*</span>下次跟进时间：</td>
								<td class="tdContent">	
									<input class="contentInput easyui-validatebox Wdate dateInput"  id="nextDate" name="doTime" data-options="required:true,editable:false" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})">
								</td>
								<td class="tdTitle"><span style="color: red">*</span>跟进人员：</td>
								<td class="tdContent">
									<input class="easyui-textbox  contentInput" id="taskFollowPeople"  data-options="required:true,editable:false,icons:[{iconCls:'icon-search'}]">
								</td>
							</tr>
							<tr>
								<td class="tdTitle">抄送给：</td>
								<td class="tdContent" colspan="3">
								<input type="hidden" id="receiveTaskId" name="receiveTaskId">
								<input class="easyui-textbox contentInput"style="width: 504px;" id="receiverNameTask"  data-options="editable:false,icons:[{iconCls:'icon-search'}]">
								</td>
							</tr>
							<tr>
								<td class="tdTitle">提醒：</td>
								<td class="tdContent">
								<span>提前</span>
									<input class="easyui-numberbox contentInput" style="width: 40px;height:25px" name="remind" id="taskRemind">
									<input class="easyui-combobox contentInput" style="width: 70px;height:25px" id="remind" name="status"  data-options="editable:false">
								</td>
							</tr>
						</tbody>
					</table>
				</form>
			
				<table class="contentTb"  style="padding-bottom: 5px">
					<tr>
						<td class="tdTitle" style="width:115px"><label for="workNote"> 附件：</label></td>
						<td class="tdContent"  colspan="3">
							<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-attach'" id="uploader">选择文件</a>
						</td>
					</tr>
					<tr>
						<td class="tdTitle">&nbsp;</td>
						<td class="tdContent" colspan="3">
							<div id="filelist">
							</div>
						</td>
					</tr>
				</table>
	</div>
	<!-- 客户列表 -->
	<div id="customerDialog">
		<div class="easyui-layout" data-options="fit:true">
			<div id="tb2">
				<shiro:hasPermission name = "${resourceIdentity}:create">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" id="customerSelected">选择</a>
				</shiro:hasPermission>
			</div>
			<div data-options="region:'center'">
				<table id="customerList" data-options="border:false"></table>
			</div>
		</div>
	</div>
	<!-- 跟进人员 -->
	<div id="followPeopleDialog">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north',border:false" class="searchDiv" style="height: 45px">
                <form id="followPeopleSearch" >
	               <input class="easyui-textbox" name="name" style="width:180px" data-options="label:'姓名',labelWidth:50">
	               <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'"  id="followPeopleSearchBtn">查询</a>
	               &nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-clear'"  id="followPeopleClearBtn">清空</a>
	           </form>
		    </div>
			<div id="tb3">
				<shiro:hasPermission name = "${resourceIdentity}:create">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" id="followPeopleSelected">选择</a>
				</shiro:hasPermission>
			</div>
			<div id="tb4">
				<shiro:hasPermission name = "${resourceIdentity}:create">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" id="taskFollowPeopleSelected">选择</a>
				</shiro:hasPermission>
			</div>
			<div data-options="region:'center'">
				<table id="followPeopleList" data-options="border:false"></table>
			</div>
		</div>
	</div>
	<!-- 抄送人 -->
	<div id="taskreceiverEditDialog">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'west',border:false" class="searchDiv" style="width: 420px">
				     <div class="easyui-layout" data-options="fit:true">
				           <div data-options="region:'north',border:false" class="searchDiv" style="height: 55px">
				                <form id="taskreceiverEditSearch" >
					               <input class="easyui-textbox" name="name" style="width:180px" data-options="label:'姓名',labelWidth:50">
					               <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'"  id="taskreceiverEditSearchBtn">查询</a>
					               &nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-clear'"  id="taskreceiverEditClearBtn">清空</a>
					           </form>
				           </div>
				            <div data-options="region:'center',border:false">
					              <table id="taskreceiverleftGrid" title="用户列表"></table>
					        </div>
				     </div>
			</div>
			<div data-options="region:'center',border:false" class="searchDiv">
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="totalCustomer.taskreceiver_leftToRight()" style="font-weight:bold;position: absolute;top:40%;width: 46px">右移</a>
				<br /> <br /> <br />
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="totalCustomer.taskreceiver_rightToRight()" style="font-weight:bold;position: absolute;top:50%;width: 46px">左移</a>
			</div>
			<div data-options="region:'east',border:false" class="searchDiv" style="width: 410px">
				<table id="taskreceiverrightGrid" title="已选用户" ></table>
			</div>
		</div>
	</div>
</body>
</html>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/tag.jsp"%>
<%
  request.setAttribute("resourceIdentity", "myWorkbench:follow");
%>
<!DOCTYPE html>
<html>
<head>
  <title>客户跟进</title>
  <base href="${ctx}">
  <meta http-equiv="content-type" content="text/html; charset=utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <%@include file="/WEB-INF/jsp/common/css.jsp"%>
  <script type="text/javascript">
		var ctx = "${ctx}";
		var resourceName = "客户跟进";
		var resourcePath = "/myWorkbench/follow";
  </script>
  <%@include file="/WEB-INF/jsp/common/js.jsp"%>
  <script type="text/javascript" src="${ctx}/resources/js/common/crud.js"></script>
  <script type="text/javascript" src="${ctx}/resources/comp/kindeditor/kindeditor.js"></script>
  <script type="text/javascript" src="${ctx}/resources/comp/My97DatePicker/WdatePicker.js"></script>
  <script type="text/javascript" src="${ctx}/resources/comp/plupload-2.1.9/js/plupload.full.min.js"></script>
  <script type="text/javascript" src="${ctx}/resources/js/common/pluploadUtil.js"></script>
  <script type="text/javascript" src="${ctx}/resources/js/myWorkbench/follow.js"></script>
  <script type="text/javascript" src="${ctx}/resources/js/myWorkbench/followAttach.js"></script>
  <script type="text/javascript">
	$(function () {
		follow.init();
	})
  </script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="searchDiv">
		<form id="searchForm" method="post">
			<!-- <input class="easyui-textbox" name="code" style="width:180px" data-options="label:'编号',labelWidth:70"> -->
			<input class="easyui-textbox" name="customer.customerName" style="width:180px" data-options="label:'客户名称',labelWidth:70">
			<input class="easyui-textbox" name="customer.mobilePhone" style="width:180px" data-options="label:'客户电话',labelWidth:70">
			<span class="textbox combo datebox" style="font-size:0;border: 0px;">
						<label class="textbox-label" style="text-decoration:none;text-align: left; width: 79.0476px; height: 30.0952px; line-height: 30.0952px;">跟进时间</label>
						<input class="easyui-validatebox Wdate dateInput" style="width:120px;" id="followTimeMins" name="followTimeMins" data-options="editable:false" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'followTimeMaxs\')}'})">
					</span>
					— <input class="easyui-validatebox Wdate dateInput" style="width:120px;" id="followTimeMaxs" name="followTimeMaxs" data-options="editable:false" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'followTimeMins\')}'})">
			<!-- <select class="easyui-combobox"  id="statu" name="status" style="width:180px" data-options="label:'状态',labelWidth:70,editable:false">
						    <option value="1">启用</option>  
						    <option value="0">禁用</option>  
			 </select> -->
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="searchBtn">查询</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" id="clearBtn">清空</a>
		</form>
	</div>
	<div data-options="region:'center'">
		<table id="list" data-options="border:false"></table>
	</div>
	<div id="tb">
		<%@include file="/WEB-INF/jsp/common/btn.jsp"%>
		<shiro:hasPermission name="${resourceIdentity}:download">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-download',plain:true" id="downloadBtn">附件下载</a>
		</shiro:hasPermission>
	</div>
 	<div id="editDialog">
		<form id="editForm" method="post">
			<input type="hidden" name="id">
			<input type="hidden" name="attachIds" id="attachIds">
			<input type="hidden" id="customerId" name="customer.id">
			<input type="hidden" id="followPersonnelId" name="followPersonnel.id">
			<input type="hidden" id="taskFollowPersonnelId" name="doUserId.id" >
			<input type="hidden" id="ids" >
			<input type="hidden" id="isActive" name="isActive">
			<input type="hidden" id="storeId">
			<input type="hidden" id="storeName">
			<table class="contentTb" id="editTable" style="margin-bottom: 15px;">
				<tr>
					<td class="tdTitle" style="width: 20%"><span style="color: red">*</span>客户：</td>
					<td class="tdContent">
						<input class="easyui-textbox contentInput" id="customerName" name="customer.customerName" style="width: 188px"  data-options="required:true,editable:false,icons:[{iconCls:'icon-search'}]">
					</td>
					<td class="tdTitle"><span style="color: red">*</span>跟进人员：</td>
					<td class="tdContent">
						<input class="easyui-textbox  contentInput" style="width: 188px;" id="followPersonnel" name="followPersonnel.name" data-options="required:true,editable:false,icons:[{iconCls:'icon-search'}]">
					</td>
				</tr>
				<tr>    
					<td class="tdTitle"><span style="color: red">*</span>跟进时间：</td>
					<td class="tdContent">
						<input class="dateInput easyui-validatebox Wdate" style="width: 187px;" id="startDate" name="followTime" data-options="required:true,editable:false" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})">
					</td>
					<td class="tdTitle"><span style="color: red">*</span>跟 进 方 式：</td>
					<td class="tdContent">
						<input class="easyui-combobox contentInput" style="width: 188px" id="followType" name="followType.id"  data-options="required:true,editable:false">
					</td>
				</tr>
				<tr>
					<td class="tdTitle">跟进详情：</td>
					<td class="tdContent" style="width: 442px" colspan="3">
						<textarea id="followDetails" name="followDetails"></textarea>
					</td>
				</tr>
				<tr>
					<td class="tdTitle"><span style="color: red">*</span>跟进阶段：</td>
					<td class="tdContent">
						<input class="easyui-combobox contentInput" style="width: 188px" id="followStage" name="followStage.id"  data-options="required:true,editable:false">
					</td>
					<td class="tdTitle"><span style="color: red">*</span>更新客户状态:</td>
					<td class="tdContent">
						<input class="easyui-combobox contentInput" style="width: 188px" id="customerStatus" name="afterStatus.id"  data-options="required:true,editable:false">
					</td>
				</tr>
			<!-- 	<tr>
					<td class="tdFirstTitle" width="50px"><label for="workNote"> 附件：</label></td>
					<td class="tdFirstContent"  colspan="3">
						<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-attach'" id="uploader">选择文件</a>
					</td>
				</tr>
			<tr>
				<td class="tdTitle">&nbsp;</td>
				<td class="tdContent" colspan="3">
					<div id="filelist">
					</div>
				</td>
			</tr> -->
				<tr style="margin-top: 5px;margin-bottom: 5px;height: 35px" id="create">
					<td class="tdTitle"><input type="checkbox" style="height: 17px;width: 17px;margin-top: 2px;margin-right: 5px" id="createMission"></td>
					<td class="tdContent">
						<h4 style="font-weight: 700;font-size: 16px">创建下次跟进任务</h4>
					</td>
				</tr>
				<tbody style="display: none;" id="missionBolock">
					<tr>
					    <td class="tdTitle"><span style="color: red">*</span>跟进内容：</td>
					    <td class="tdContent" colspan="3">
						     <input class="easyui-textbox" id="taskContent" name="taskContent" style="width: 478px;height:80px" data-options="multiline:true">
					    </td>
				    </tr>
					<tr>
						<td class="tdTitle"><span style="color: red">*</span>下次跟进时间：</td>
						<td class="tdContent">	
							<input class="dateInput easyui-validatebox Wdate" style="width: 187px;" id="nextDate" name="doTime" data-options="editable:false" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm', minDate:'%y-%M-%d'})">
						</td>
						<td class="tdTitle"><span style="color: red">*</span>跟进人员：</td>
						<td class="tdContent">
							<input class="easyui-textbox  contentInput" style="width: 188px;" id="taskFollowPeople"  data-options="editable:false,icons:[{iconCls:'icon-search'}]">
						</td>
					</tr>
					<tr>
						<td class="tdTitle">抄送给：</td>
						<td class="tdContent" colspan="3">
						<input type="hidden" id="receiveTaskId" name="receiveTaskId">
						<input class="easyui-textbox contentInput"style="width: 478px;" id="receiverNameTask"  data-options="editable:false,icons:[{iconCls:'icon-search'}]">
						</td>
					</tr>
					<tr>
						<td class="tdTitle">提醒：</td>
						<td class="tdContent">
						<span>提前</span>
							<input class="easyui-numberbox contentInput" style="width: 40px;height:25px" name="remind" id="taskRemind">
							<input class="easyui-combobox contentInput" style="width: 70px;height:25px" id="remind" name="remindUnit"  data-options="editable:false">
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
	<div id="customerDialog">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north',border:false" class="searchDiv" style="height: 45px">
                <form id="customerSearch" >
	               <input class="easyui-textbox" name="customerName" style="width:180px" data-options="label:'姓名',labelWidth:50">
	               <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'"  id="customerSearchBtn">查询</a>
	               &nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-clear'"  id="customerClearBtn">清空</a>
	           </form>
		    </div>
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
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="follow.taskreceiver_leftToRight()" style="font-weight:bold;position: absolute;top:40%;width: 46px">右移</a>
				<br /> <br /> <br />
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="follow.taskreceiver_rightToRight()" style="font-weight:bold;position: absolute;top:50%;width: 46px">左移</a>
			</div>
			<div data-options="region:'east',border:false" class="searchDiv" style="width: 410px">
				<table id="taskreceiverrightGrid" title="已选用户" ></table>
			</div>
		</div>
	</div>
</body>
</html>
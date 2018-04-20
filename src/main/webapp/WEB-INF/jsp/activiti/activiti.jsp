<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/tag.jsp"%>
<%
  request.setAttribute("resourceIdentity", "sys:activiti");
%>
<!DOCTYPE html>
<html>
<head>
  <title>工作流</title>
  <base href="${ctx}">
  <meta http-equiv="content-type" content="text/html; charset=utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <%@include file="/WEB-INF/jsp/common/css.jsp"%>
  <script type="text/javascript">
		var ctx = "${ctx}";
		var resourceName = "工作流";
		var resourcePath = "/sys/activiti";
  </script> 
  <%@include file="/WEB-INF/jsp/common/js.jsp"%>
  <script type="text/javascript" src="${ctx}/resources/js/common/crud.js"></script>
  <script type="text/javascript" src="${ctx}/resources/js/activiti/activiti.js"></script>
  <script type="text/javascript">
	$(function () {
        activiti.init();
	})
  </script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="searchDiv">
		<form id="searchForm" method="post">
			<input class="easyui-textbox" name="name" style="width:180px" data-options="label:'名称',labelWidth:70">
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
		<form id="editForm" method="post" enctype="multipart/form-data">
			<input type="hidden" name="id">
			<table class="contentTb">
				<tr>
					<td class="tdTitle">文件：</td>
					<td class="tdContent">
						<input class="easyui-filebox contentInput" name="file"  data-options="required:true,prompt:'选择文件',buttonText:'选择文件',accept:'zip,bar,bpmn,bpmn20.xml'">
					</td>
				</tr>
			</table>
		</form>
	</div>



</body>
</html>
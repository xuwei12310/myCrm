<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/tag.jsp"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
  request.setAttribute("resourceIdentity", "myWorkbench:todo");
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
		var resourceName = "待办任务";
		var resourcePath = "/myWorkbench/todo";
  </script>
  <%@include file="/WEB-INF/jsp/common/js.jsp"%>
  <script type="text/javascript" src="${ctx}/resources/js/common/crud.js"></script>
  <script type="text/javascript" src="${ctx}/resources/comp/My97DatePicker/WdatePicker.js"></script>
  <script type="text/javascript" src="${ctx}/resources/comp/kindeditor/kindeditor.js"></script>
    <script type="text/javascript" src="${ctx}/resources/comp/plupload-2.1.9/js/plupload.full.min.js"></script>
    <script type="text/javascript" src="${ctx}/resources/js/common/pluploadUtil.js"></script>
    <script type="text/javascript" src="${ctx}/resources/js/myWorkbench/todo.js"></script>
    <script type="text/javascript" src="${ctx}/resources/js/myWorkbench/todoAttach.js"></script>
    <script type="text/javascript" src="${ctx}/resources/js/myWorkbench/followAttach.js"></script>
    <script type="text/javascript">
        $(function () {
            todo.init();
        })

    </script>
    <style>
        .spans {
            color: #FF0000
        };
    </style>
</head>
<body class="easyui-layout">
    <div id="tb">
        <shiro:hasPermission name="${resourceIdentity}:create">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" id="addBtn">添加</a>
        </shiro:hasPermission>
        <shiro:hasPermission name="${resourceIdentity}:view">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-view',plain:true"
               id="viewBtn">查看</a>
        </shiro:hasPermission>
    </div>
    <div id="tb2">
        <shiro:hasPermission name="${resourceIdentity}:addCreate">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true"
               id="addCreateBtn">添加</a>
        </shiro:hasPermission>
        <shiro:hasPermission name="${resourceIdentity}:modifCreate">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true"
               id="updateCreateBtn">修改</a>
        </shiro:hasPermission>
        <shiro:hasPermission name="${resourceIdentity}:delCreate">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true"
               id="deleteCreateBtn">删除</a>
        </shiro:hasPermission>
        <shiro:hasPermission name="${resourceIdentity}:viewCreate">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-view',plain:true"
               id="viewCreateBtn">查看</a>
        </shiro:hasPermission>
     <!--    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-down',plain:true"
           id="downCreateBtn">附件下载</a> -->
    </div>
    <div id="tb3">
        <shiro:hasPermission name="${resourceIdentity}:addCopy">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true"
               id="addCopyBtn">添加</a>
        </shiro:hasPermission>
        <shiro:hasPermission name="${resourceIdentity}:viewCopy">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-view',plain:true"
               id="viewCopyBtn">查看</a>
        </shiro:hasPermission>
    </div>
    <div data-options="region:'center',border:false">
        <div id="updateCustomerTabs" class="easyui-tabs" data-options="fit:true,border:false">
            <div title="我待办的">
                <div class="easyui-layout" data-options="fit:true,border:false" style="margin-top: 2px">
                    <div data-options="region:'north',border:false" class="searchDiv" style="height:100px">
                        <div data-options="region:'center',border:false" class="searchDiv">
                            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'"
                               id="today">今天</a>
                            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'"
                               id="tomorrow">明天</a>
                            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'"
                               id="after">全部</a>
                        </div>
                        <form id="searchForm" method="post">
                            <select class="easyui-combobox" id="statu" name="status" style="width:180px"
                                    data-options="label:'状态',labelWidth:70,editable:false">
                                <option value="1">进行中</option>
                                <option value="2">已完成</option>
                                <option value="3">未完成</option>
                                <option value="4">已取消</option>
                            </select>
                            <select class="easyui-combobox" id="taskType" name="taskType" style="width:180px"
                                    data-options="label:'任务类型',labelWidth:70,editable:false">
                                <option value="1">客户跟进</option>
                                <option value="2">日常任务</option>
                                <option value="3">客户投诉</option>
                                <option value="4">客户关怀</option>
                            </select>
                            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'"
                               id="searchBtn">查询</a>
                            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-clear'"
                               id="clearBtn">清空</a>
                        </form>
                    </div>
                    <div data-options="region:'center'">
                        <table id="list" data-options="border:false"></table>
                    </div>
                </div>
            </div>
            <div title="我创建的">
                <div class="easyui-layout" data-options="fit:true,border:false" style="margin:2px 0px">
                    <div data-options="region:'north',border:false" class="searchDiv" style="height:45px">
                        <form id="searchCreateForm" method="post">
                            <select class="easyui-combobox" id="createStatu" name="status" style="width:180px"
                                    data-options="label:'状态',labelWidth:70,editable:false">
                                <option value="1">进行中</option>
                                <option value="2">已完成</option>
                                <option value="3">未完成</option>
                                <option value="4">已取消</option>
                            </select>
                            <select class="easyui-combobox" id="createTaskType" name="taskType" style="width:180px"
                                    data-options="label:'任务类型',labelWidth:70,editable:false">
                                <option value="1">客户跟进</option>
                                <option value="2">日常任务</option>
                                <option value="3">客户投诉</option>
                                <option value="4">客户关怀</option>
                            </select>
                            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'"
                               id="searchCreateBtn">查询</a>
                            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-clear'"
                               id="clearCreateBtn">清空</a>
                        </form>
                    </div>
                    <div data-options="region:'center'">
                        <table id="createList" data-options="border:false"></table>
                    </div>

                </div>
            </div>
            <div title="抄送我的">
                <div class="easyui-layout" data-options="fit:true,border:false" style="margin: 2px 0px">
                    <div data-options="region:'north',border:false" class="searchDiv" style="height:45px">
                        <form id="searchCopyForm" method="post">
                            <select class="easyui-combobox" id="copyStatu" name="status" style="width:180px"
                                    data-options="label:'状态',labelWidth:70,editable:false">
                                <option value="1">进行中</option>
                                <option value="2">已完成</option>
                                <option value="3">未完成</option>
                                <option value="4">已取消</option>
                            </select>
                            <select class="easyui-combobox" id="copyTaskType" name="taskType" style="width:180px"
                                    data-options="label:'任务类型',labelWidth:70,editable:false">
                                <option value="1">客户跟进</option>
                                <option value="2">日常任务</option>
                                <option value="3">客户投诉</option>
                                <option value="4">客户关怀</option>
                            </select>
                            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'"
                               id="searchCopyBtn">查询</a>
                            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-clear'"
                               id="clearCopyBtn">清空</a>
                        </form>
                    </div>
                    <div data-options="region:'center'">
                        <table id="copyList" data-options="border:false"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="editDialog">
        <form id="editForm" method="post" >
            <input type="hidden" name="id">
             <input type="hidden" name="attachIds" id="attachIdsTodo">
             <input type="hidden" id="storeId">
			<input type="hidden" id="storeName">
            <table>
                <tr>
                    <td class="tdTitle">任务类型：</td>
                    <td class="tdContent">
                        <input class="easyui-combobox contentInput" name="taskType" id="taskTypeOne"
                               data-options="required:false,editable:false" style="width:100%">
                    </td>
                </tr>
                <tr id="cusname">
                    <td class="tdTitle"><span class="spans">*</span>客户名称：</td>
                    <td class="tdContent">
                        <input type="hidden" name="customerId.id" id="customerId">
                        <input class="easyui-textbox contentInput" id="customerNameIdBtn" name="customerId.customerName"
                               style="width:272px"
                               data-options="required:true,editable:false,icons:[{iconCls:'icon-search'}]">
                    </td>
                </tr>
                <tr id="concomplain">
                    <td class="tdTitle"><span class="spans">*</span>关联投诉：</td>
                    <td class="tdContent">
                        <input type="hidden" name="customerComplainId.id" id="customerComplainId">
                        <input class="easyui-textbox contentInput" id="customerComplainIdBtn"
                               name="customerComplainId.complaintContent"
                               style="width:272px"
                               data-options="required:true,editable:false,icons:[{iconCls:'icon-search'}]">
                    </td>
                </tr>
                <tr id="concare">
                    <td class="tdTitle"><span class="spans">*</span>关联关怀：</td>
                    <td class="tdContent">
                        <input type="hidden" name="customerCareId.id" id="customerCareId">
                        <input class="easyui-textbox contentInput" id="customerCareIdBtn" name="customerCareId.careContent"
                               style="width:272px"
                               data-options="required:true,editable:false,icons:[{iconCls:'icon-search'}]">
                    </td>
                </tr>
                <tr>
                    <td class="tdTitle"><span class="spans">*</span>执行时间：</td>
                    <td class="tdFirstContent">
                        <input class="contentInput easyui-validatebox Wdate dateInput" id="doTime" name="doTime"
                               style="width: 100%;" data-options="required:true,editable:false"
                               onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm', minDate:'%y-%M-%d'})">
                    </td>
                </tr>
                <tr>
                    <td class="tdTitle"><span class="spans">*</span>任务内容：</td>
                    <td class="tdFirstContent">
                        <textarea class="textarea easyui-validatebox" name="taskContent" id="taskContentOne"
                                  data-options="required:true" style="width: 100%; height: 200px"></textarea>
                    </td>
                </tr>
                <tr>
                    <td class="tdTitle"><span class="spans">*</span>执行者：</td>
                    <td class="tdFirstContent">
                        <input type="hidden" name="doUserId.id" id="do_user_id">
                        <input class="easyui-textbox contentInput" id="doUserIdBtn" name="doUserId.username"
                               style="width:100%"
                               data-options="required:true,editable:false,icons:[{iconCls:'icon-search'}]">
                    </td>
                </tr>
                <tr>
                    <td class="tdTitle">抄送给：</td>
                    <td class="tdContent" colspan="3">
                        <input type="hidden" id="receivecopyId" name="receiveId">
                        <input class="easyui-textbox contentInput" style="width: 100%;" id="receiverName"
                               data-options="editable:false,icons:[{iconCls:'icon-search'}]">
                    </td>
                </tr>
                <tr>
                    <td class="tdTitle">提醒：</td>
                    <td class="tdContent">
                        <span>提前</span>
                        <input class="easyui-numberbox contentInput" style="width: 40px;height:25px" name="remind"
                               id="remindOne" data-options="required:false,validType:'integer'">
                        <input class="easyui-combobox contentInput" style="width: 70px;height:25px" id="remindUnit"
                               name="remindUnit" data-options="editable:false">
                        提醒执行者
                    </td>
                </tr>
                <!-- <tr>
                    <td class="tdTitle">上传附件：</td>
                    <td class="tdContent">
                        <input class="easyui-filebox" data-options="buttonText:'选择文件'" style="width: 100%;"
                               name="attach" id="file"/>
                    </td>
                </tr> -->
                    
            </table>
        </form> 
                 <table class="contentTb" style="margin-left: 33px">
                    <tr>
						<td class="tdTitle" ><label for="workNote"> 附件：</label></td>
						<td class="tdContent"  colspan="3">
							<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-attach'" id="uploaderTodo">选择文件</a>
						</td>
					</tr>
					<tr>
						<td class="tdTitle">&nbsp;</td>
						<td class="tdContent" colspan="3">
							<div id="todofilelist">
							</div>
						</td>
					</tr> 
				</table>
    </div>
    <div id="receiverEditDialog">
        <div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'west',border:false" class="searchDiv" style="width: 420px">
                <div class="easyui-layout" data-options="fit:true">
                    <div data-options="region:'north',border:false" class="searchDiv" style="height: 80px">
                        <form id="receiverEditSearch">
                            <input class="easyui-textbox" name="name" style="width:180px"
                                   data-options="label:'姓名',labelWidth:50">
                            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'"
                               id="receiverEditSearchBtn">查询</a>
                            &nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton"
                                                       data-options="iconCls:'icon-clear'" id="receiverEditClearBtn">清空</a>
                        </form>
                    </div>
                    <div data-options="region:'center',border:false">
                        <table id="receiverleftGrid" title="用户列表"></table>
                    </div>
                </div>
            </div>
            <div data-options="region:'center',border:false" class="searchDiv">
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="todo.receiver_leftToRight()"
                   style="font-weight:bold;position: absolute;top:40%;width: 46px">右移</a>
                <br/> <br/> <br/>
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="todo.receiver_rightToRight()"
                   style="font-weight:bold;position: absolute;top:50%;width: 46px">左移</a>
            </div>
            <div data-options="region:'east',border:false" class="searchDiv" style="width: 410px">
                <table id="receiverrightGrid" title="已选用户"></table>
            </div>
        </div>
    </div>
    <div id="taskreceiverEditDialog">
        <div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'west',border:false" class="searchDiv" style="width: 420px">
                <div class="easyui-layout" data-options="fit:true">
                    <div data-options="region:'north',border:false" class="searchDiv" style="height: 80px">
                        <form id="taskreceiverEditSearch">
                            <input class="easyui-textbox" name="name" style="width:180px"
                                   data-options="label:'姓名',labelWidth:50">
                            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'"
                               id="taskreceiverEditSearchBtn">查询</a>
                            &nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton"
                                                       data-options="iconCls:'icon-clear'"
                                                       id="taskreceiverEditClearBtn">清空</a>
                        </form>
                    </div>
                    <div data-options="region:'center',border:false">
                        <table id="taskreceiverleftGrid" title="用户列表"></table>
                    </div>
                </div>
            </div>
            <div data-options="region:'center',border:false" class="searchDiv">
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="todo.taskreceiver_leftToRight()"
                   style="font-weight:bold;position: absolute;top:40%;width: 46px">右移</a>
                <br/> <br/> <br/>
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="todo.taskreceiver_rightToRight()"
                   style="font-weight:bold;position: absolute;top:50%;width: 46px">左移</a>
            </div>
            <div data-options="region:'east',border:false" class="searchDiv" style="width: 410px">
                <table id="taskreceiverrightGrid" title="已选用户"></table>
            </div>
        </div>
    </div>
    <div id="editCommentContentDialog">
        <form id="editCommentContentForm" method="post">
            <input type="hidden" name="id" id="todoIdCommentContent">
            <table>
                <tr>
                    <td class="tdTitle">评论内容：</td>
                    <td class="tdFirstContent">
                        <textarea class="textarea" name="comment" id="commentContent" data-options="required:true"
                                  style="width:300px; height: 150px"></textarea>
                    </td>
                </tr>
            </table>
        </form>
    </div>

    <div id="editComplectExplainDialog">
        <form id="editComplectExplainForm" method="post">
            <input type="hidden" name="id" id="ComplectExplain">
            <input type="hidden" name="customerComplainId.id" id="complainIdTodo">
            <input type="hidden" name="customerCareId.id" id="careIdTodo">
            <table>
                <tr>
                    <td class="tdTitle">完成说明：</td>
                    <td class="tdFirstContent">
                        <textarea class="textarea" name="reason" id="reason" data-options="required:false"
                                  style="width:300px; height: 150px"></textarea>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div id="editNotComplectDialog">
        <form id="editNotComplectForm" method="post">
            <input type="hidden" name="id" id="notComplect">
            <table>
                <tr>
                    <td class="tdTitle">未完成说明：</td>
                    <td class="tdFirstContent">
                        <textarea class="textarea" name="reason" id="reason" data-options="required:false"
                                  style="width:300px; height: 150px"></textarea>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div id="editCancelDialog">
        <form id="editCancelForm" method="post">
            <input type="hidden" name="id" id="cancel">
            <table>
                <tr>
                    <td class="tdTitle">取消说明：</td>
                    <td class="tdFirstContent">
                        <textarea class="textarea" name="reason" id="reason" data-options="required:false"
                                  style="width:300px; height: 150px"></textarea>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div id="viewDialog" style="font-size: 14px;height:580px" class="easyui-layout">
        <div data-options="region:'north',border:true" style="height:36px;">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true"
               id="notComplectBtn">未完成</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true"
               id="ComplectBtn">完成</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true"
               id="cancelBtn">取消</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-exit',plain:true"
               id="exitCommentBtn">退出</a>
        </div>
        <div data-options="region:'center',border:true" style="height:300px;">
            <form id="viewForm" method="post">
                <input type="hidden" id="customeridOne">
                <table class="contentTb" style="margin-left: 10px">
                    <tr>
                        <td class="tdTitle">执行者:</td>
                        <td class="tdFirstContent"><span id="doUserPersonIdBtn"></span></td>
                        <td class="tdTitle">执行时间:</td>
                        <td class="tdFirstContent"><span id="doTimeView"></span></td>
                        <td class="tdTitle">客户状态:</td>
                        <td class="tdFirstContent"><span id="cusStatus"></span></td>
                    </tr>
                    <tr>
                        <td class="tdTitle">任务类型:</td>
                        <td class="tdFirstContent"><span id="taskTypes"></span></td>
                        <td class="tdTitle" id="cusnameOne">客户名称:</td>
                        <td class="tdFirstContent"><span id="customerName"></span></td>
                    </tr>
                </table>
                <div style="margin-left: 10px;margin-top: 10px">
                    <span class="content" style="">任务内容：</span>
                    <div>
                        <textarea readonly="readonly" class="textarea" name="taskContent"
                                  style="width:591px; height: 150px;"></textarea>
                    </div>
                </div>
            </form>
        </div>
        <div data-options="region:'south',border:true" style="height:280px;padding-left: 10px">
            <div id="commentpanel" class="easyui-panel" title="评论" style="width:595px;background:#fafafa;">
                <form id="commentForm" method="post">
                    <input type="hidden" name="todoId.id" id="todoIdComment">
                    <table class="contentTb" style="width:595px;margin-left:10px;" id="addProcessCommentTable">
                        <tr class="tdFirstTitle">
                            <textarea id="CommentContent" name="comment" style="width:591px; height:56px"></textarea>
                        </tr>
                    </table>
                </form>
                <a href="javascript:void(0)" class="easyui-linkbutton" id="addComment" style="width:70px">评论</a>
                <table class="contentTb" style="width:540px;margin-left:10px;" id="viewCommentTable">
                    <thead>
                    <tr>
                        <td style="width:30%"></td>
                        <td style="width:40%"></td>
                        <td style="width:30%"></td>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <div id="viewCreateDialog" style="font-size: 14px;height:580px" class="easyui-layout">
         <div data-options="region:'north',border:true" style="height:36px;">
         <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-exit',plain:true"  id="exitCommentCreateBtn">退出</a>
         </div>
          <div data-options="region:'center',border:true" style="height:300px;">
            <form id="viewCreateForm" method="post">
                <table class="contentTb" style="margin-left: 10px">
                    <tr>
                        <td class="tdTitle">执行者:</td>
                        <td class="tdFirstContent"><span id="doUserPersonIdCreateBtn"></span></td>
                        <td class="tdTitle">执行时间:</td>
                        <td class="tdFirstContent"><span id="doTimeCreate"></span></td>
                        <td class="tdTitle">客户状态:</td>
                        <td class="tdFirstContent"><span id="cusStatusCreate"></span></td>
                    </tr>
                    <tr>
                         <td class="tdTitle">任务类型:</td>
                        <td class="tdFirstContent"><span id="taskTypesCreate"></span></td>
                        <td class="tdTitle">客户名称:</td>
                        <td class="tdFirstContent"><span id="customerNameCreate"></span></td>
                    </tr>
                </table>
                <div style="margin-left: 10px;margin-top: 10px">
                    <span class="content">任务内容:</span>
                    <div>
                        <textarea class="textarea" name="taskContent" style="width:591px; height: 150px;" readonly="readonly"></textarea>
                    </div>
                </div>
                 </form>
           </div>
         <div data-options="region:'south',border:true" style="height:280px;padding-left: 10px">
            <div id="commentpanel" class="easyui-panel" title="评论"  style="width:595px;background:#fafafa;">
                <form id="commentCreateForm" method="post">
                    <input type="hidden" name="todoId.id" id="todoIdCommentCreate">
                    <table class="contentTb" style="width:595px;margin-left:10px;"  id="addProcessCommentTable">
                        <tr class="tdFirstTitle">
                            <textarea id="CommentContentCreate" name="comment" style="width:591px; height:56px"></textarea>
                        </tr>
                    </table>
                </form>
                <a href="javascript:void(0)" class="easyui-linkbutton" style="width:70px"
                   id="addCommentCreate">评论</a>
                <table class="contentTb" style="width:540px;margin-left:10px;" id="viewCommentTableCreate">
                    <thead>
                    <tr>
                        <td style="width:30%"></td>
                        <td style="width:40%"></td>
                        <td style="width:30%"></td>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
     </div>
    <div id="viewCopyDialog" style="font-size: 14px;height:580px" class="easyui-layout">
        <div data-options="region:'north',border:true" style="height:36px;">
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-exit',plain:true"
                   id="exitCommentCopyBtn">退出</a>
          </div>
           <div data-options="region:'center',border:true" style="height:300px;">
            <form id="viewCopyForm" method="post">
                <table class="contentTb" style="margin-left: 10px">
                    <tr>
                        <td class="tdTitle">执行者:</td>
                        <td class="tdFirstContent"><span id="doUserPersonIdCopyBtn"></span></td>
                        <td class="tdTitle">执行时间:</td>
                        <td class="tdFirstContent"><span id="doTimeCopy"></span></td>
                        <td class="tdTitle"><span>客户状态：</span></td>
                        <td class="tdFirstContent"><span id="cusStatusCopy"></span></td>
                    </tr>
                    <tr>
                        <td class="tdTitle">客户名称:</td>
                        <td class="tdFirstContent"><span id="customerNameCopy"></span></td>

                        <td class="tdTitle">任务类型:</td>
                        <td class="tdFirstContent"><span id="taskTypesCopy"></span></td>

                    </tr>
                </table>
                <div style="margin-left: 10px">
                    <span class="content">任务内容:</span>
                    <div>
                        <textarea class="textarea " name="taskContent" style="width:591px; height: 150px;"
                                  readonly="readonly"></textarea>
                    </div>
                </div>
            </form>
        </div>
        <div data-options="region:'south',border:true" style="height:280px;padding-left: 10px">
            <div id="commentpanel" class="easyui-panel" title="评论" style="width:611px;padding:10px;background:#fafafa;">
                <form id="commentCopyForm" method="post">
                    <input type="hidden" name="todoId.id" id="todoIdCommentCopy">
                    <table class="contentTb" style="width:540px;margin-left:10px;" id="addProcessCommentTable">
                        <tr>
                            <td style="width:100%"></td>
                        </tr>
                        <tr class="tdFirstTitle">
                            <textarea id="CommentContentCopy" name="comment" style="width:591px; height:56px"></textarea>
                        </tr>
                    </table>
                </form>
                <a href="javascript:void(0)" class="easyui-linkbutton" style="width:70px"
                   id="addCommentCopy">评论</a>
                <table class="contentTb" style="width:540px;margin-left:10px;" id="viewCommentTableCopy">
                    <thead>
                    <tr>
                        <td style="width:30%"></td>
                        <td style="width:40%"></td>
                        <td style="width:30%"></td>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <!-- 完成 -->
    <div id="editComplectDialog" >
                <form id="editComplectForm" method="post">
                    <input type="hidden" name="attachIds" id="attachIds">
                    <input type="hidden" name="todoComplectId" id='todoComplect'>
                    <input type="hidden"  id='taskStoreId'>
                       <input type="hidden"  id='taskStoreName'>
                       <input type="hidden" id="todoisActive" name="todoisActive">
                    <table class="contentTb" id="editComplectTable" style="margin-bottom: 15px;">
                        <tr>
                            <td class="tdTitle" style="width: 20%">客户：</td>
                            <td class="tdContent">
                                <input type="hidden" id="customerComplectId" name="customer.id">
                                <input class="easyui-textbox contentInput" id="customerNameComplect"
                                       name="customer.customerName" style="width: 188px"
                                       data-options="required:false,editable:false,">
                            </td>
                            <td class="tdTitle"><span class="spans">*</span>跟进人员：</td>
                            <td class="tdContent">
                                <input type="hidden" id="followPersonnelId" name="followPersonnel.id">
                                <input class="easyui-textbox  contentInput" style="width: 188px;" id="followPersonnel"
                                       name="followPersonnel.name"
                                       data-options="required:true,editable:false,icons:[{iconCls:'icon-search'}]">
                            </td>
                        </tr>
                        <tr>
                            <td class="tdTitle"><span class="spans">*</span>跟进时间：</td>
                            <td class="tdContent">
                                <input class="contentInput easyui-validatebox Wdate dateInput" id="startDate"
                                       style="width: 188px" name="followTime" data-options="required:true,editable:false"
                                       onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})">
                            </td>
                            <td class="tdTitle"><span class="spans">*</span>跟进方式：</td>
                            <td class="tdContent">
                                <input class="easyui-combobox contentInput" style="width: 188px" id="followType"
                                       name="followType.id" data-options="required:true,editable:false">
                            </td>
                        </tr>
                        <tr>
                            <td class="tdTitle">跟进详情：</td>
                            <td class="tdContent" style="width: 442px" colspan="3">
                                <textarea id="followDetails" name="followDetails"></textarea>
                            </td>
                        </tr>
                        <tr>
                            <td class="tdTitle"><span class="spans">*</span>跟进阶段：</td>
                            <td class="tdContent">
                                <input class="easyui-combobox contentInput" style="width: 188px" id="followStage"
                                       name="followStage.id" data-options="required:true,editable:false">
                            </td>
                            <td class="tdTitle"><span class="spans">*</span>更新客户状态:</td>
                            <td class="tdContent">
                                <input class="easyui-combobox contentInput" style="width: 188px" id="customerStatus"
                                       name="afterStatus.id" data-options="required:true,editable:false">
                            </td>
                        </tr>
                        <tr style="margin-top: 10px;margin-bottom: 5px;height: 35px">
                            <td class="tdTitle"><input type="checkbox"
                                                       style="height: 17px;width: 17px;margin-top: 2px;margin-right: 5px"
                                                       id="createMission"></td>
                            <td class="tdContent">
                                <h4 style="font-weight: 700;font-size: 16px">创建下次跟进任务</h4>
                            </td>
                        </tr>
                        <tbody style="display: none;" id="missionBolock">
                        <tr>
                            <td class="tdTitle"><span style="color: red">*</span>跟进内容：</td>
                            <td class="tdContent" colspan="3">
                                <input class="easyui-textbox" name="taskContent" id="taskContentId"
                                       style="width: 480px;height:80px" data-options="multiline:true,required:false">
                            </td>
                        </tr>
                        <tr>
                            <td class="tdTitle"><span class="spans">*</span>下次跟进时间：</td>
                            <td class="tdContent">
                                <input class="contentInput easyui-validatebox Wdate dateInput" id="nextTime" name="doTime"
                                       style="width: 188px;" data-options="required:false,editable:false"
                                       onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm', minDate:'%y-%M-%d'})">
                            </td>
                            <td class="tdTitle"><span class="spans">*</span>跟进人员：</td>
                            <td class="tdContent">
                                <input type="hidden" id="taskFollowPersonnelId" name="doUserId.id">
                                <input class="easyui-textbox  contentInput" style="width: 188px;" id="taskFollowPeople"
                                       data-options="required:false,editable:false,icons:[{iconCls:'icon-search'}]">
                            </td>
                        </tr>
                        <tr>
                            <td class="tdTitle">抄送给：</td>
                            <td class="tdContent" colspan="3">
                                <input type="hidden" id="receiveTaskId" name="receiveTaskId">
                                <input class="easyui-textbox contentInput" style="width: 478px;" id="receiverNameTask"
                                       data-options="editable:false,icons:[{iconCls:'icon-search'}]">
                            </td>
                        </tr>
                        <tr>
                            <td class="tdTitle">提醒：</td>
                            <td class="tdContent">
                                <span>提前</span>
                                <input class="easyui-numberbox contentInput" style="width: 40px;height:25px" name="remind"
                                       id="taskRemind">
                                <input class="easyui-combobox contentInput" style="width: 70px;height:25px"
                                       id="taskRemindUnit" name="remindUnit" data-options="editable:false">
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </form>
               <table class="contentTb"  style="padding-bottom: 5px">
                    <tr>
                       <td class="tdTitle" style="width:115px"><label for="workNote"> 附件：</label></td>
                        <td class="tdContent" colspan="3">
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
    <!-- 跟进人员 -->
    <div id="followPersonnelEditDialog">
        <div class="easyui-layout" data-options="fit:true,selected:true">
            <div data-options="region:'north',border:false" class="searchConditonDiv">
                <form id="searchFollowForm" method="post">
                    <input class="easyui-textbox" name="name"
                           style="width: 200px" data-options="label:'姓名',labelWidth:80">
                    <a href="javascript:void(0)" class="easyui-linkbutton"
                       data-options="iconCls:'icon-search'" id="searchFollowBtn">查询</a>
                    <a href="javascript:void(0)" class="easyui-linkbutton"
                       data-options="iconCls:'icon-clear'" id="clearFollowBtn">清空</a>
                </form>
            </div>
            <div data-options="region:'center',border:false"
            ">
            <table id="followPeopleList" data-options="border:false"></table>
        </div>
    </div>
    </div>
    <!-- 下次跟进人员 -->
    <div id="taskfollowPersonnelEditDialog">
        <div class="easyui-layout" data-options="fit:true,selected:true">
            <div data-options="region:'north',border:false" class="searchConditonDiv">
                <form id="searchtaskFollowForm" method="post">
                    <input class="easyui-textbox" name="name"
                           style="width: 200px" data-options="label:'姓名',labelWidth:80">
                    <a href="javascript:void(0)" class="easyui-linkbutton"
                       data-options="iconCls:'icon-search'" id="searchtaskFollowBtn">查询</a>
                    <a href="javascript:void(0)" class="easyui-linkbutton"
                       data-options="iconCls:'icon-clear'" id="cleartaskFollowBtn">清空</a>
                </form>
            </div>
            <div data-options="region:'center',border:false"
            ">
            <table id="followPeopleTaskList" data-options="border:false"></table>
        </div>
    </div>
    </div>
    <!-- 客户名称-->
    <div id="customerEditDialog">
        <div class="easyui-layout" data-options="fit:true,selected:true">
            <div data-options="region:'north',border:false" class="searchConditonDiv">
                <form id="searchCustomerForm" method="post">
                    <input class="easyui-textbox" name="customerName"
                           style="width: 200px" data-options="label:'客户名称',labelWidth:80">
                    <a href="javascript:void(0)" class="easyui-linkbutton"
                       data-options="iconCls:'icon-search'" id="searchCustomerBtn">查询</a>
                    <a href="javascript:void(0)" class="easyui-linkbutton"
                       data-options="iconCls:'icon-clear'" id="clearCustomerBtn">清空</a>
                </form>
            </div>
            <div data-options="region:'center',border:false">
                <div id="customerList"></div>
            </div>
        </div>
    </div>
    <!-- 执行者 -->
    <div id="ownerEditDialog">
        <div class="easyui-layout" data-options="fit:true,selected:true">
            <div data-options="region:'north',border:false" class="searchConditonDiv">
                <form id="searchOwnerForm" method="post">
                    <input class="easyui-textbox" name="userName" style="width:180px"
                           data-options="label:'姓名',labelWidth:50">
                    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'"
                       id="searchOwnerBtn">查询</a>
                    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-clear'"
                       id="clearOwnerBtn">清空</a>
                </form>
            </div>
            <div data-options="region:'center',border:false">
                <div id="ownerList"></div>
            </div>
        </div>
    </div>
    <!-- 关联投诉 -->
    <div id="complaintEditDialog">
        <div class="easyui-layout" data-options="fit:true,selected:true">
            <div data-options="region:'north',border:false" class="searchConditonDiv">
                <form id="searchComplaintForm" method="post">
                    <input class="easyui-textbox" name="customerComplainId.complaintContent" id="ComplainContent"
                           style="width:220px" data-options="label:'投诉内容',labelWidth:100">
                    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'"
                       id="searchComplaintBtn">查询</a>
                    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-clear'"
                       id="clearComplaintBtn">清空</a>
                </form>
            </div>
            <div data-options="region:'center',border:false">
                <div id="complaintList"></div>
            </div>
        </div>
    </div>
    <!-- 关联关怀 -->
    <div id="careEditDialog">
        <div class="easyui-layout" data-options="fit:true,selected:true">
            <div data-options="region:'north',border:false" class="searchConditonDiv">
                <form id="searchCareForm" method="post">
                    <input class="easyui-textbox" name="customerCareId.careContent" id="customerCareContent"
                           style="width:220px" data-options="label:'关怀内容',labelWidth:100">
                    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'"
                       id="searchCareBtn">查询</a>
                    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-clear'"
                       id="clearCareBtn">清空</a>
                </form>
            </div>
            <div data-options="region:'center',border:false">
                <div id="careList"></div>
            </div>
        </div>
    </div>
</body>
</html>
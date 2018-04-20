var detailEditor=null;
var todo = {
	initCRUDBtn : function() {
        $("#downCreateBtn").click(function(){
        	todo.downCreate();
        });
		//未完成
		$("#notComplectBtn").click(function(){
			todo.notcomplect();
		});
		$("#notComplectCreateBtn").click(function(){
			todo.notcomplect();
		});
		//取消
		$("#cancelBtn").click(function(){
			todo.cancel();
		});
		//取消
		$("#cancelCreateBtn").click(function(){
			todo.cancel();
		});
		//完成
		$("#ComplectBtn").click(function(){
			//任务类型是客户跟进的操作
			if($("#taskTypeOne").val()==1){
				todo.complect();
			}else{
				todo.otherComplect();
			}
			
			//非客户跟进的操作
		});
		// 添加
		$(app.id.addBtnId).click(function() {
			todo.add();
		});
		//添加我创建的
		$("#addCreateBtn").click(function(){
			todo.add();
		});
		//添加抄送我的
		$("#addCopyBtn").click(function(){
			todo.add();
		});
		// 添加评论
		$("#addComment").click(function() {
			todo.addComment();
		});
		// 添加创建的评论
		$("#addCommentCreate").click(function() {
			todo.addCreateComment();
		});
		// 添加抄送的评论
		$("#addCommentCopy").click(function() {
			todo.addCopyComment();
		});
		// 修改
		$(app.id.updateBtnId).click(function() {
			todo.update();
		});
		// 修改我创建的
		$("#updateCreateBtn").click(function() {
			todo.updateCreate();
		});
		// 删除
		$(app.id.deleteBtnId).click(function() {
			todo.mulDelete();
		});
		$("#deleteCreateBtn").click(function(){
			todo.mulCreateDelete();
		});
		
		//查看
		$("#viewBtn").click(function(){
			 todo.view();
		});
		//查看我创建的
		$("#viewCreateBtn").click(function(){
			 todo.viewCreate();
		});
		//查看抄送我的
		$("#viewCopyBtn").click(function(){
			 todo.viewCopy();
		});
		//创建任务
		$("#createMission").click(function(){
			if($("#createMission").is(":checked")){
				$("#todoisActive").val(true);
				$("#taskContentId").textbox({required:true});
				$("#nextTime").validatebox({required:true});
				$("#taskFollowPeople").textbox({required:true});
				$("#editComplectTable").css('height','550px');
				$("#missionBolock").css('display','table-row-group');
				$("#taskRemindUnit").combobox("setValue",1);
				$("#editComplectDialog").dialog('resize',{
					height : document.documentElement.clientHeight<730?document.documentElement.clientHeight:730
				})
				if(document.documentElement.clientHeight>=730){
					$("#editComplectDialog").dialog('center');
				}
			}else{
				$("#todoisActive").val(false);
				$("#editComplectTable").css('height','370px');
				$("#missionBolock").css('display','none');
				$("#nextTime").validatebox({required:false});
				$("#taskFollowPeople").textbox({required:false});
				$("#taskContentId").textbox({required:false});
				$("#editComplectDialog").dialog('resize',{
					height : document.documentElement.clientHeight<500?document.documentElement.clientHeight:500
				})
				if(document.documentElement.clientHeight>=500){
					$("#editComplectDialog").dialog('center');
				}
			}
		});
		//退出
		$("#exitCommentBtn").click(function(){
			$("#viewDialog").dialog('close');
		});
		//退出
		$("#exitCommentCreateBtn").click(function(){
			$("#viewCreateDialog").dialog('close');
		});
		$("#exitCommentCopyBtn").click(function(){
			$("#viewCopyDialog").dialog('close');
		});
		// 查询
		$(app.id.searchBtnId).click(function() {
			todo.search();
		});
		// 清空查询条件
		$(app.id.clearBtnId).click(function() {
			$(app.id.searchFormId).form('clear');
		});
		
		// 查询姓名
		$("#searchFollowBtn").click(function() {
			todo.searchFollow();
		});
		// 清空姓名查询条件
		$("#clearFollowBtn").click(function() {
			$("#searchFollowForm").form('clear');
		});
		// 查询下次跟进姓名
		$("#searchtaskFollowBtn").click(function() {
			todo.searchtaskFollow();
		});
		// 清空下次跟进姓名查询条件
		$("#cleartaskFollowBtn").click(function() {
			$("#searchtaskFollowForm").form('clear');
		});
		// 查询我创建的
		$("#searchCreateBtn").click(function() {
			todo.searchCreate();
		});
		// 查询抄送我的
		$("#searchCopyBtn").click(function() {
			todo.searchCopy();
		});
		// 清空查询我创建的条件
		$("#clearCreateBtn").click(function() {
			$("#searchCreateForm").form('clear');
		});
		// 清空查询抄送我的条件
		$("#clearCopyBtn").click(function() {
			$("#searchCopyForm").form('clear');
		});
		//客户名称
		$("#customerNameIdBtn").textbox({
            onClickIcon: function (index) {
                if (index == 0) {
                	$("#customerEditDialog").dialog('open');
                }
            }
        }); 
		//客户中搜索客户名称
		$("#searchCustomerBtn").click(function(){
			var isValid = $("#searchCustomerForm").form('validate');
			if (!isValid) {
				return false;
			}
			var content = $("#searchCustomerForm").serializeObject();
			$("#customerList").datagrid({
				queryParams : content
			});
    	});
		// 客户中清空查询条件
		$("#clearCustomerBtn").click(function() {
			$("#searchCustomerForm").form('clear');
		});
		//跟进人员
		$("#followPersonnel").textbox({
            onClickIcon: function (index) {
                if (index == 0) {
                	$("#followPersonnelEditDialog").dialog('open');
                }
            }
        }); 
		//下次跟进人员
		$("#taskFollowPeople").textbox({
            onClickIcon: function (index) {
                if (index == 0) {
                	$("#taskfollowPersonnelEditDialog").dialog('open');
                }
            }
        }); 
		//关联投诉
		$("#customerComplainIdBtn").textbox({
            onClickIcon: function (index) {
                if (index == 0) {
                	var customerId=$("#customerId").val();
                	if(customerId==null || customerId=="" || customerId==undefined){
                		$.messager.alert("提示","请先选择客户名称","warning");
                	}
                	else{
                		$("#complaintEditDialog").dialog('open');
                	}
                
                }
            }
        }); 
		//关联关怀
		$("#customerCareIdBtn").textbox({
            onClickIcon: function (index) {
                if (index == 0) {
                	var customerId=$("#customerId").val();
                	if(customerId==null || customerId=="" || customerId==undefined){
                		$.messager.alert("提示","请先选择客户名称","warning");
                	}else{
                		$("#careEditDialog").dialog('open');
                	}
                	
                }
            }
        }); 
		//执行人
		$("#doUserIdBtn").textbox({
            onClickIcon: function (index) {
                if (index == 0) {
                	$("#ownerEditDialog").dialog('open');
                }
            }
        }); 
		//执行人中搜索姓名
		$("#searchOwnerBtn").click(function(){
			var isValid = $("#searchOwnerForm").form('validate');
			if (!isValid) {
				return false;
			}
			var content = $("#searchOwnerForm").serializeObject();
			$("#ownerList").datagrid({
				queryParams : content
			});
    	});
		// 执行人中清空查询条件
		$("#clearOwnerBtn").click(function() {
			$("#searchOwnerForm").form('clear');
		});
		//搜索投诉内容
		$("#searchComplaintBtn").click(function(){
			var isValid = $("#searchComplaintForm").form('validate');
			if (!isValid) {
				return false;
			}
			var complaintContentId=$("#ComplainContent").val();
			var customerId=$("#customerId").val();
			$("#complaintList").datagrid({
				url:app.getUrl("getComplaintByList"),
				queryParams : {"complaintContentId":complaintContentId,"customerId":customerId},
			});
		});
		// 投诉内容清空查询条件
		$("#clearComplaintBtn").click(function() {
			$("#searchComplaintForm").form('clear');
		});
		
		//搜索关怀内容
		$("#searchCareBtn").click(function(){
			var isValid = $("#searchCareForm").form('validate');
			if (!isValid) {
				return false;
			}
			var customerCareContentId=$("#customerCareContent").val();
			var customerId=$("#customerId").val();
			$("#careList").datagrid({
				url:app.getUrl("getCareByList"),
				queryParams : {"customerCareContentId":customerCareContentId,"customerId":customerId},
			});
		});
		// 关怀内容清空查询条件
		$("#clearCareBtn").click(function() {
			$("#searchCareForm").form('clear');
		});
		
		
		//查询姓名未选择
    	$("#receiverEditSearchBtn").click(function(){
    		var content = $("#receiverEditSearch").serializeObject();
    		$('#receiverleftGrid').datagrid({  
    			 url:app.getUrl("listPersonByPage"),
    			 queryParams : content
  		     });
    	});
    	//清空姓名
    	 $("#receiverEditClearBtn").click(function(){
    		 $("#receiverEditSearch").form('clear');
     	});
    	//查询任务姓名未选择
     	$("#taskreceiverEditSearchBtn").click(function(){
     		var content = $("#taskreceiverEditSearch").serializeObject();
     		$('#taskreceiverleftGrid').datagrid({  
     			 url:app.getUrl("listPersonByPage"),
     			 queryParams : content
   		     });
     	});
     	//清空任务姓名
     	 $("#taskreceiverEditClearBtn").click(function(){
     		 $("#taskreceiverEditSearch").form('clear');
      	});
		//抄送人员
	    $("#receiverName").textbox({
            onClickIcon: function (index) {
                if (index == 0) {
                	$("#receiverEditDialog").dialog('open');
                	var affiliatedId = $('#receivecopyId').val();
                	$("#receiverEditSearch").form('clear');
                	$("#storeId").val(affiliatedId);
                	if(affiliatedId!=null){
                	$('#receiverleftGrid').datagrid({
                		url:app.getUrl("listPersonByPage"),
                		queryParams: {
                			"personId":affiliatedId
                		}
            		});
    	    		$("#receiverrightGrid").datagrid({
    	    			queryParams: {
    	    				"personId": affiliatedId
    	    			}
    	    		});
                }
               }
            }
        }); 
		//任务抄送人员
	    $("#receiverNameTask").textbox({
            onClickIcon: function (index) {
                if (index == 0) {
                	$("#taskreceiverEditDialog").dialog('open');
                	var affiliatedId = $('#receiveTaskId').val();
                	$("#taskreceiverEditSearch").form('clear');
                	$("#taskStoreId").val(affiliatedId);
                	$('#taskreceiverleftGrid').datagrid({
                		url:app.getUrl("listPersonByPage"),
                		queryParams: {
                			"personId":affiliatedId
                		}
            		});
    	    		$("#taskreceiverrightGrid").datagrid({
    	    			queryParams: {
    	    				"personId": affiliatedId
    	    			}
    	    		});
                }
            }
        }); 
	    
		//抄送人员搜索姓名
		$("#searchReceiverBtn").click(function(){
			var isValid = $("#searchReceiverForm").form('validate');
			if (!isValid) {
				return false;
			}
			var content = $("#searchReceiverForm").serializeObject();
			$("#receiverList").datagrid({
				queryParams : content
			});
    	});
		// 抄送人员中清空查询条件
		$("#clearReceiverBtn").click(function() {
			$("#searchReceiverForm").form('clear');
		});
	},
	receiver_leftToRight:function(){
		todo.addReceiverPerson();
	},
	addReceiverPerson : function(){
		var checkedRows = $('#receiverleftGrid').datagrid('getChecked');
		 if(checkedRows.length==0){
             $.messager.alert('提示','请选择数据行','warning');
             return;
         }
		var dataid = $('#storeId').val();
	    var dataName = $("#storeName").val();
    	if(dataid!=undefined && dataid!=''){
    		dataid = dataid + ",";
    	}
    	if(dataName!=undefined&& dataName!=''){
    		dataName = dataName + ",";
    	}
    	for(var i=0;i<checkedRows.length;i++){
    		dataid = dataid + checkedRows[i].id + ",";
    		dataName = dataName + checkedRows[i].name + ",";
    		if(i==checkedRows.length-1){
    			dataid=dataid.substr(0, dataid.length - 1);
    			dataName=dataName.substr(0, dataName.length - 1);
    		}
    	}
    	console.log(dataid);
    	$("#storeId").val(dataid);
        $("#storeName").val(dataName);
        $('#receiverleftGrid').datagrid({
    		queryParams: {
    			"personId":dataid
    		}
		});
        $("#receiverrightGrid").datagrid({
			queryParams: {
				"personId": dataid
			}
		});
	},
	//任务抄送人
	taskreceiver_leftToRight:function(){
		todo.addTaskReceiverPerson();
	},
	addTaskReceiverPerson : function(){
		var checkedRows = $('#taskreceiverleftGrid').datagrid('getChecked');
		 if(checkedRows.length==0){
             $.messager.alert('提示','请选择数据行','warning');
             return;
         }
		var dataid = $('#taskStoreId').val();
    	var dataName = $("#taskStoreName").val();
    	if(dataid!=undefined && dataid!=''){
    		dataid = dataid + ",";
    	}
    	if(dataName!=undefined&& dataName!=''){
    		dataName = dataName + ",";
    	}
    	for(var i=0;i<checkedRows.length;i++){
    		dataid = dataid + checkedRows[i].id + ",";
    		dataName = dataName + checkedRows[i].name + ",";
    		if(i==checkedRows.length-1){
    			dataid=dataid.substr(0, dataid.length - 1);
    			dataName=dataName.substr(0, dataName.length - 1);
    		}
    	}
    	$("#taskStoreName").val(dataName);
        $("#taskStoreId").val(dataid);
    	/*$("#receiverNameTask").textbox("setValue", dataName);
        $("#receiveTaskId").val(dataid);*/
        $('#taskreceiverleftGrid').datagrid({
    		queryParams: {
    			"personId":dataid
    		}
		});
        $("#taskreceiverrightGrid").datagrid({
			queryParams: {
				"personId": dataid
			}
		});
	},
	receiver_rightToRight :function(){
		todo.delrightToRight();
	},
	delrightToRight :function(){
		var checkedRows = $('#receiverrightGrid').datagrid('getChecked');
		if(checkedRows.length==0){
            $.messager.alert('提示','请选择数据行','warning');
            return;
        }
		var dataid = $('#storeId').val();
    	var dataName = $("#storeName").val();
    	if(dataid!=undefined && dataid!=''){
    		dataid = dataid + ",";
    	}
    	if(dataName!=undefined&& dataName!=''){
    		dataName = dataName + ",";
    	}
    	for(var i=0;i<checkedRows.length;i++){
    		dataid = dataid.replace(checkedRows[i].id+",",'');
    		dataName = dataName.replace(checkedRows[i].name+",",'');
    		if(i==checkedRows.length-1){
    			dataid=dataid.substr(0, dataid.length - 1);
    			dataName=dataName.substr(0, dataName.length - 1);
    		}
    	}
    	$("#storeName").val(dataName);
        $("#storeId").val(dataid);
    	/*$("#receiverName").textbox("setValue", dataName);
        $("#receivecopyId").val(dataid);*/
        $('#receiverleftGrid').datagrid({
    		queryParams: {
    			"personId":dataid
    		}
		});
    	$("#receiverrightGrid").datagrid({
			 queryParams: {
				 "personId":dataid
			 }
		});
	},
	taskreceiver_rightToRight:function(){
		todo.taskdelrightToRight();
	},
	taskdelrightToRight:function(){
		var checkedRows = $('#taskreceiverrightGrid').datagrid('getChecked');
		if(checkedRows.length==0){
            $.messager.alert('提示','请选择数据行','warning');
            return;
        }
		var dataid = $('#storeId').val();
    	var dataName = $("#storeName").val();
    	if(dataid!=undefined && dataid!=''){
    		dataid = dataid + ",";
    	}
    	if(dataName!=undefined&& dataName!=''){
    		dataName = dataName + ",";
    	}
    	for(var i=0;i<checkedRows.length;i++){
    		dataid = dataid.replace(checkedRows[i].id+",",'');
    		dataName = dataName.replace(checkedRows[i].name+",",'');
    		if(i==checkedRows.length-1){
    			dataid=dataid.substr(0, dataid.length - 1);
    			dataName=dataName.substr(0, dataName.length - 1);
    		}
    	}
    	$("#taskStoreName").val(dataName);
        $("#taskStoreId").val(dataid);
        $('#taskreceiverleftGrid').datagrid({
    		queryParams: {
    			"personId":dataid
    		}
		});
    	$("#taskreceiverrightGrid").datagrid({
			 queryParams: {
				 "personId":dataid
			 }
		});
	},
	initViewList : function(params) {
		var params = $.extend({
			url : app.getUrl("listTodoByPage"),
			pagination : true,
			fit : true,
			border : false,
			toolbar : app.id.toolbarId,
			ctrlSelect : true,
			rownumbers : true,
			queryParams : {status:1},
			columns : [ [
						{
							field : 'taskContent',title : '任务内容',width : "78%",align : 'center'
						},
						{
							field : 'doTime',title : '执行时间',width : "20%",align : 'center',formatter: function(value,row,index){
								if(row.date != '' && row.date !=null){
								    if(row.date<0){
								    	var s=row.date;
								    	return "<span style='color:#FF0000'>"+row.doTime +"&nbsp;&nbsp;"+"["+"超期"+ s.substr(1)+"天"+"]"+"</span>";
								    }else if(row.date>0){
								    	return row.doTime +"&nbsp;&nbsp;"+"["+row.date+"天后"+"]";
								    }else{
								    	if(row.hour<0){
								    		s=row.hour;
								    		m=row.minute;
								    		return "<span style='color:#FF0000'>"+row.doTime +"&nbsp;&nbsp;"+"["+"超期"+ s.substr(1)+"小时"+"-"+m.substr(1)+"分钟"+"]"+"</span>";
								    	}else if(row.hour>0){
								    		return row.doTime +"&nbsp;&nbsp;"+"["+row.hour+"小时"+"-"+row.minute+"分钟"+"后执行"+"]";
								    	}else{
								    		if(row.minute<0){
								    			s=row.minute;
									    		return "<span style='color:#FF0000'>"+row.doTime +"&nbsp;&nbsp;"+"["+"超期"+ s.substr(1)+"分钟"+"]"+"</span>";
									    	}else if(row.minute>0){
									    		return row.doTime +"&nbsp;&nbsp;"+"["+row.minute+"分钟后"+"]";
									    	}else{
									    		return row.doTime;
									    	}
								    	}
								    }
									
								}
		                  },
						} ] ],
			pageSize : 20,
			pageList : [ 10, 20, 30, 40, 50, 100 ],
			onDblClickRow : function() {
				$("#viewBtn").click();
			}
		}, params);
		$(app.id.listId).datagrid(params);
	},
	initViewCreateList : function(params) {
		var params = $.extend({
			url : app.getUrl("listTodoCreateByPage"),
			pagination : true,
			fit : true,
			border : false,
			toolbar : "#tb2",
			ctrlSelect : true,
			rownumbers : true,
			queryParams : {status:1},
			columns : [ [
						{
							field : 'taskContent',title : '任务内容',width : "70%",align : 'center'
						},
						{
							field : 'doTime',title : '执行时间',width : "30%",align : 'center',formatter: function(value,row,index){
								if(row.date != '' && row.date !=null){
										    if(row.date<0){
										    	var s=row.date;
										    	return "<span style='color:#FF0000'>"+row.doTime +"&nbsp;&nbsp;"+"["+"超期"+ s.substr(1)+"天"+"]"+"</span>";
										    }else if(row.date>0){
										    	return row.doTime +"&nbsp;&nbsp;"+"["+row.date+"天后"+"]";
										    }else{
										    	if(row.hour<0){
										    		s=row.hour;
										    		m=row.minute;
										    		return "<span style='color:#FF0000'>"+row.doTime +"&nbsp;&nbsp;"+"["+"超期"+ s.substr(1)+"小时"+"-"+m.substr(1)+"分钟"+"]"+"</span>";
										    	}else if(row.hour>0){
										    		return row.doTime +"&nbsp;&nbsp;"+"["+row.hour+"小时"+"-"+row.minute+"分钟"+"后执行"+"]";
										    	}else{
										    		if(row.minute<0){
										    			s=row.minute;
											    		return "<span style='color:#FF0000'>"+row.doTime +"&nbsp;&nbsp;"+"["+"超期"+ s.substr(1)+"分钟"+"]"+"</span>";
											    	}else if(row.minute>0){
											    		return row.doTime +"&nbsp;&nbsp;"+"["+row.minute+"分钟后"+"]";
											    	}else{
											    		return row.doTime;
											    	}
										    	}
										    }
											
										}
							}
						} ] ],
			pageSize : 20,
			pageList : [ 10, 20, 30, 40, 50, 100 ],
			onDblClickRow : function() {
				$("#updateCreateBtn").click();
			}
		}, params);
		$("#createList").datagrid(params);
	},
	initViewCopyList : function(params){
		var params = $.extend({
			url : app.getUrl("listTodoCopyByPage"),
			pagination : true,
			fit : true,
			border : false,
			toolbar : "#tb3",
			ctrlSelect : true,
			rownumbers : true,
			queryParams : {status:1},
			columns : [ [
						{
							field : 'taskContent',title : '任务内容',width : "70%",align : 'center'
						},
						{
							field : 'doTime',title : '执行时间',width : "30%",align : 'center',formatter: function(value,row,index){
								if(row.date != '' && row.date !=null){
										    if(row.date<0){
										    	var s=row.date;
										    	return "<span style='color:#FF0000'>"+row.doTime +"&nbsp;&nbsp;"+"["+"超期"+ s.substr(1)+"天"+"]"+"</span>";
										    }else if(row.date>0){
										    	return row.doTime +"&nbsp;&nbsp;"+"["+row.date+"天后"+"]";
										    }else{
										    	if(row.hour<0){
										    		s=row.hour;
										    		m=row.minute;
										    		return "<span style='color:#FF0000'>"+row.doTime +"&nbsp;&nbsp;"+"["+"超期"+ s.substr(1)+"小时"+"-"+m.substr(1)+"分钟"+"]"+"</span>";
										    	}else if(row.hour>0){
										    		return row.doTime +"&nbsp;&nbsp;"+"["+row.hour+"小时"+"-"+row.minute+"分钟"+"后执行"+"]";
										    	}else{
										    		if(row.minute<0){
										    			s=row.minute;
											    		return "<span style='color:#FF0000'>"+row.doTime +"&nbsp;&nbsp;"+"["+"超期"+ s.substr(1)+"分钟"+"]"+"</span>";
											    	}else if(row.minute>0){
											    		return row.doTime +"&nbsp;&nbsp;"+"["+row.minute+"分钟后"+"]";
											    	}else{
											    		return row.doTime;
											    	}
										    	}
										    }
											
										}
							}
						} ] ],
			pageSize : 20,
			pageList : [ 10, 20, 30, 40, 50, 100 ],
			onDblClickRow : function() {
				$("#viewCopyBtn").click();
			}
		}, params);
		$("#copyList").datagrid(params);
	},
	//添加评论
	initCommentEditForm : function() {
		$("#commentForm").form({
			onSubmit : function() {
				var isValid = $(this).form('validate');
				if (!isValid) {
					$.messager.progress('close');
				}
				return isValid;
			},
			success : function(data) {
				$.messager.progress('close');
				var data = eval('(' + data + ')');
				if (data.isSuccess) {
					$.messager.alert('提示', '评论成功', 'info', function() {
						$("#CommentContent").html(' ');
						$("#CommentContent").val('');
						 var url=app.getUrl("userInfo");
						 var data ={data:1};
						 var userData = CSIT.syncCallService(url, data);
					    	
						var selectedRow = $(app.id.listId).datagrid('getSelected');
					    var url=app.getUrl("listTodoCommentByPage");
					    	var dataId=selectedRow.id;
					    	var data ={data:dataId};
					    	var commentData = CSIT.syncCallService(url, data);
					    	$('#viewCommentTable tbody').html('');
					    	if(commentData.length>0){
					    		var temp = "";
					    		for(var i=0; i<commentData.length; i++){
					    			var comment = commentData[i];
					    			temp += '<tr style="height: 25px">';
					    			temp += '<td class="commentName">'+(comment.name==undefined?'':comment.name)+'</td>';
					    			temp += '<td>'+(comment.commentTime==undefined?'':comment.commentTime)+'</td>';
					    			if(comment.commentUserId==userData.id){
					        			temp += '  <td><a href="javascript:void(0);" onclick="todo.updateComment('+comment.id+')">编辑</a>';
					        			temp += '      <a href="javascript:void(0);" onclick="todo.mulDeleteComment('+comment.id+')">删除</a></td>';
					        		}
					    			temp += '</tr>';
					    			temp += '<td colspan="2">'+(comment.comment==undefined?'':comment.comment)+'</td>';
					    			temp += '</tr>';
					    		}
					    		$('#viewCommentTable').append(temp);
					    	}
					});
				} else {
					$.messager.alert('提示', data.message, 'error');
				}
			}
		});
	},
	//添加评论-我创建的
	initCreateCommentEditForm: function() {
		$("#commentCreateForm").form({
			onSubmit : function() {
				var isValid = $(this).form('validate');
				if (!isValid) {
					$.messager.progress('close');
				}
				return isValid;
			},
			success : function(data) {
				$.messager.progress('close');
				var data = eval('(' + data + ')');
				if (data.isSuccess) {
					$.messager.alert('提示', '保存成功', 'info', function() {
						$("#CommentContentCreate").html(' ');
						$("#CommentContentCreate").val('');
						 var url=app.getUrl("userInfo");
						 var data ={data:1};
						 var userData = CSIT.syncCallService(url, data);
						var selectedRow = $("#createList").datagrid('getSelected');
					    var url=app.getUrl("listTodoCommentByPage");
					    	var dataId=selectedRow.id;
					    	var data ={data:dataId};
					    	var commentData = CSIT.syncCallService(url, data);
					    	$('#viewCommentTableCreate tbody').html('');
					    	if(commentData.length>0){
					    		var temp = "";
					    		for(var i=0; i<commentData.length; i++){
					    			var comment = commentData[i];
					    			temp += '<tr style="height: 25px">';
					    			temp += '<td class="commentName">'+(comment.name==undefined?'':comment.name)+'</td>';
					    			temp += '<td>'+(comment.commentTime==undefined?'':comment.commentTime)+'</td>';
					    			if(comment.commentUserId==userData.id){
					        			temp += '  <td><a href="javascript:void(0);" onclick="todo.updateCommentCreate('+comment.id+')">编辑</a>';
					        			temp += '      <a href="javascript:void(0);" onclick="todo.mulDeleteCommentCreate('+comment.id+')">删除</a></td>';
					        		}
					    			temp += '</tr>';
					    			temp += '<td colspan="2">'+(comment.comment==undefined?'':comment.comment)+'</td>';
					    			temp += '</tr>';
					    		}
					    		$('#viewCommentTableCreate').append(temp);
					    	}
					});
				} else {
					$.messager.alert('提示', data.message, 'error');
				}
			}
		});
	},
	//添加评论-抄送我的
	initCopyCommentEditForm : function() {
		$("#commentCopyForm").form({
			onSubmit : function() {
				var isValid = $(this).form('validate');
				if (!isValid) {
					$.messager.progress('close');
				}
				return isValid;
			},
			success : function(data) {
				$.messager.progress('close');
				var data = eval('(' + data + ')');
				if (data.isSuccess) {
					$.messager.alert('提示', '保存成功', 'info', function() {
						$("#CommentContentCopy").html(' ');
						$("#CommentContentCopy").val('');
						 var url=app.getUrl("userInfo");
						 var data ={data:1};
						 var userData = CSIT.syncCallService(url, data);
						var selectedRow = $("#copyList").datagrid('getSelected');
					    var url=app.getUrl("listTodoCommentByPage");
					    	var dataId=selectedRow.id;
					    	var data ={data:dataId};
					    	var commentData = CSIT.syncCallService(url, data);
					    	$('#viewCommentTableCopy tbody').html('');
					    	if(commentData.length>0){
					    		var temp = "";
					    		for(var i=0; i<commentData.length; i++){
					    			var comment = commentData[i];
					    			temp += '<tr style="height: 25px">';
					    			temp += '<td class="commentName">'+(comment.name==undefined?'':comment.name)+'</td>';
					    			temp += '<td>'+(comment.commentTime==undefined?'':comment.commentTime)+'</td>';
					    			if(comment.commentUserId==userData.id){
					        			temp += '  <td><a href="javascript:void(0);" onclick="todo.updateCommentCopy('+comment.id+')">编辑</a>';
					        			temp += '      <a href="javascript:void(0);" onclick="todo.mulDeleteCommentCopy('+comment.id+')">删除</a></td>';
					        		}
					    			temp += '</tr>';
					    			temp += '<td colspan="2">'+(comment.comment==undefined?'':comment.comment)+'</td>';
					    			temp += '</tr>';
					    		}
					    		$('#viewCommentTableCopy').append(temp);
					    	}
					});
				} else {
					$.messager.alert('提示', data.message, 'error');
				}
			}
		});
	},
	//编辑评论
	initEditCommentContentForm: function(){
		$("#editCommentContentForm").form({
			onSubmit : function() {
				var isValid = $(this).form('validate');
				if (!isValid) {
					$.messager.progress('close');
				}
				return isValid;
			},
			success : function(data) {
				$.messager.progress('close');
				var data = eval('(' + data + ')');
				if (data.isSuccess) {
					$.messager.alert('提示', '保存成功', 'info', function() {
						$("#editCommentContentDialog").dialog('close');
						$("#CommentContent").html(' ');
						$("#CommentContent").val('');
						 var url=app.getUrl("userInfo");
						 var data ={data:1};
						 var userData = CSIT.syncCallService(url, data);
					    	
						var selectedRow = $(app.id.listId).datagrid('getSelected');
					    var url=app.getUrl("listTodoCommentByPage");
					    	var dataId=selectedRow.id;
					    	var data ={data:dataId};
					    	var commentData = CSIT.syncCallService(url, data);
					    	$('#viewCommentTable tbody').html('');
					    	if(commentData.length>0){
					    		var temp = "";
					    		for(var i=0; i<commentData.length; i++){
					    			var comment = commentData[i];
					    			temp += '<tr style="height: 25px">';
					    			temp += '<td class="commentName">'+(comment.name==undefined?'':comment.name)+'</td>';
					    			temp += '<td>'+(comment.commentTime==undefined?'':comment.commentTime)+'</td>';
					    			if(comment.commentUserId==userData.id){
					        			temp += '  <td><a href="javascript:void(0);" onclick="todo.updateComment('+comment.id+')">编辑</a>';
					        			temp += '      <a href="javascript:void(0);" onclick="todo.mulDeleteComment('+comment.id+')">删除</a></td>';
					        		}
					    			temp += '</tr>';
					    			temp += '<td colspan="2">'+(comment.comment==undefined?'':comment.comment)+'</td>';
					    			temp += '</tr>';
					    		}
					    		$('#viewCommentTable').append(temp);
					    	}
					});
				} else {
					$.messager.alert('提示', data.message, 'error');
				}
			}
		});

	},
	//编辑评论-我创建的
	initEditCommentContentCreateForm: function(){
		$("#editCommentContentForm").form({
			onSubmit : function() {
				var isValid = $(this).form('validate');
				if (!isValid) {
					$.messager.progress('close');
				}
				return isValid;
			},
			success : function(data) {
				$.messager.progress('close');
				var data = eval('(' + data + ')');
				if (data.isSuccess) {
					$.messager.alert('提示', '保存成功', 'info', function() {
						$("#editCommentContentDialog").dialog('close');
						$("#CommentContentCreate").html(' ');
						$("#CommentContentCreate").val('');
						 var url=app.getUrl("userInfo");
						 var data ={data:1};
						 var userData = CSIT.syncCallService(url, data);
						var selectedRow = $("#createList").datagrid('getSelected');
					    var url=app.getUrl("listTodoCommentByPage");
					    	var dataId=selectedRow.id;
					    	var data ={data:dataId};
					    	var commentData = CSIT.syncCallService(url, data);
					    	$('#viewCommentTableCreate tbody').html('');
					    	if(commentData.length>0){
					    		var temp = "";
					    		for(var i=0; i<commentData.length; i++){
					    			var comment = commentData[i];
					    			temp += '<tr style="height: 25px">';
					    			temp += '<td class="commentName">'+(comment.name==undefined?'':comment.name)+'</td>';
					    			temp += '<td>'+(comment.commentTime==undefined?'':comment.commentTime)+'</td>';
					    			if(comment.commentUserId==userData.id){
					        			temp += '  <td><a href="javascript:void(0);" onclick="todo.updateCommentCreate('+comment.id+')">编辑</a>';
					        			temp += '      <a href="javascript:void(0);" onclick="todo.mulDeleteCommentCreate('+comment.id+')">删除</a></td>';
					        		}
					    			temp += '</tr>';
					    			temp += '<td colspan="2">'+(comment.comment==undefined?'':comment.comment)+'</td>';
					    			temp += '</tr>';
					    		}
					    		$('#viewCommentTableCreate').append(temp);
					    	}
					});
				} else {
					$.messager.alert('提示', data.message, 'error');
				}
			}
		});

	},
	initEditCommentContentCopyForm: function(){
		$("#editCommentContentForm").form({
			onSubmit : function() {
				var isValid = $(this).form('validate');
				if (!isValid) {
					$.messager.progress('close');
				}
				return isValid;
			},
			success : function(data) {
				$.messager.progress('close');
				var data = eval('(' + data + ')');
				if (data.isSuccess) {
					$.messager.alert('提示', '保存成功', 'info', function() {
						$("#editCommentContentDialog").dialog('close');
						$("#CommentContentCopy").html(' ');
						$("#CommentContentCopy").val('');
						 var url=app.getUrl("userInfo");
						 var data ={data:1};
						 var userData = CSIT.syncCallService(url, data);
						var selectedRow = $("#copyList").datagrid('getSelected');
					    var url=app.getUrl("listTodoCommentByPage");
					    	var dataId=selectedRow.id;
					    	var data ={data:dataId};
					    	var commentData = CSIT.syncCallService(url, data);
					    	$('#viewCommentTableCopy tbody').html('');
					    	if(commentData.length>0){
					    		var temp = "";
					    		for(var i=0; i<commentData.length; i++){
					    			var comment = commentData[i];
					    			temp += '<tr style="height: 25px">';
					    			temp += '<td class="commentName">'+(comment.name==undefined?'':comment.name)+'</td>';
					    			temp += '<td>'+(comment.commentTime==undefined?'':comment.commentTime)+'</td>';
					    			if(comment.commentUserId==userData.id){
					        			temp += '  <td><a href="javascript:void(0);" onclick="todo.updateCommentCopy('+comment.id+')">编辑</a>';
					        			temp += '      <a href="javascript:void(0);" onclick="todo.mulDeleteCommentCopy('+comment.id+')">删除</a></td>';
					        		}
					    			temp += '</tr>';
					    			temp += '<td colspan="2">'+(comment.comment==undefined?'':comment.comment)+'</td>';
					    			temp += '</tr>';
					    		}
					    		$('#viewCommentTableCopy').append(temp);
					    	}
					});
				} else {
					$.messager.alert('提示', data.message, 'error');
				}
			}
		});

	},

	initNotComplectEditForm : function(){
		$("#editNotComplectForm").form({
			onSubmit : function() {
				var isValid = $(this).form('validate');
				if (!isValid) {
					$.messager.progress('close');
				}
				return isValid;
			},
			success : function(data) {
				$.messager.progress('close');
				var data = eval('(' + data + ')');
				if (data.isSuccess) {
					$.messager.alert('提示', '保存成功', 'info', function() {
						$("#editNotComplectDialog").dialog('close');
						$("#viewDialog").dialog('close');
						$("#list").datagrid('reload');
						$("#commentlist").datagrid('reload');
						$("#createList").datagrid('reload');
					});
				} else {
					$.messager.alert('提示', data.message, 'error');
				}
			}
		});
	},
	initCancelEditForm: function(){
		$("#editCancelForm").form({
			onSubmit : function() {
				var isValid = $(this).form('validate');
				if (!isValid) {
					$.messager.progress('close');
				}
				return isValid;
			},
			success : function(data) {
				$.messager.progress('close');
				var data = eval('(' + data + ')');
				if (data.isSuccess) {
					$.messager.alert('提示', '保存成功', 'info', function() {
						$("#editCancelDialog").dialog('close');
						$("#viewDialog").dialog('close');
						$("#list").datagrid('reload');
						$("#commentlist").datagrid('reload');
						$("#createList").datagrid('reload');
					});
				} else {
					$.messager.alert('提示', data.message, 'error');
				}
			}
		});
	},
	
	initComplectExplainEditForm :function(){
		$("#editComplectExplainForm").form({
			onSubmit : function() {
				var isValid = $(this).form('validate');
				if (!isValid) {
					$.messager.progress('close');
				}
				return isValid;
			},
			success : function(data) {
				$.messager.progress('close');
				var data = eval('(' + data + ')');
				if (data.isSuccess) {
					$.messager.alert('提示', '保存成功', 'info', function() {
						$("#editComplectExplainDialog").dialog('close');
						$("#viewDialog").dialog('close');
						$("#viewDialog").dialog('close');
						$("#list").datagrid('reload');
						$("#commentlist").datagrid('reload');
						$("#createList").datagrid('reload');
					});
				} else {
					$.messager.alert('提示', data.message, 'error');
				}
			}
		});
	},
	initFollowPeopleDialog : function() {
		var params = {
			title : '选择跟进人员',
			width : 550,
			height : document.documentElement.clientHeight-5<400?document.documentElement.clientHeight-5:400,
			closed : true,
			cache : false,
			modal : true,
			onClose : function() {
			}
		};
		$("#followPeopleDialog").dialog(params);
	},
	initEditDialog : function() {
		var params = {
			title : '编辑' + resourceName,
			width : 500,
			height : document.documentElement.clientHeight-5<635?document.documentElement.clientHeight-5:635,
			closed : true,
			cache : false,
			modal : true,
			buttons : [ {
				text : '保存',
				iconCls : 'icon-save',
				handler : function() {
					$(app.id.editFormId).submit();
				}
			}, {
				text : '退出',
				iconCls : 'icon-exit',
				handler : function() {
					$(app.id.editDialogId).dialog('close');
				}
			} ],
			onClose : function() {
				$(app.id.editFormId).form('clear');
			}
		};
		$(app.id.editDialogId).dialog(params);
	},
	initviewDialog : function() {
		var params = {
			title : '查看' + resourceName,
			width : document.documentElement.clientHeight-5<635?document.documentElement.clientHeight-5:635,
			height : document.documentElement.clientHeight-5<635?document.documentElement.clientHeight-5:635,
			closed : true,
			cache : false,
			modal : true,
			onClose : function() {
				$("#viewDialog").form('clear');
			}
		};
		$("#viewDialog").dialog(params);
	},
	initviewCreateDialog: function() {
		var params = {
				title : '查看' + resourceName,
				width : document.documentElement.clientHeight-5<635?document.documentElement.clientHeight-5:635,
				height : document.documentElement.clientHeight-5<635?document.documentElement.clientHeight-5:635,
				closed : true,
				cache : false,
				modal : true,
				onClose : function() {
					$("#viewCreateDialog").form('clear');
				}
			};
			$("#viewCreateDialog").dialog(params);
	},
	initviewCopyDialog : function(){
		var params = {
				title : '查看' + resourceName,
				width : document.documentElement.clientHeight-5<635?document.documentElement.clientHeight-5:635,
				height : document.documentElement.clientHeight-5<635?document.documentElement.clientHeight-5:635,
				closed : true,
				cache : false,
				modal : true,
				onClose : function() {
					$("#viewCopyDialog").form('clear');
				}
			};
			$("#viewCopyDialog").dialog(params);
	},
	initEditForm : function() {
		$(app.id.editFormId).form({
			onSubmit : function() {
				var isValid = $(this).form('validate');
				if (!isValid) {
					$.messager.progress('close');
				}
				var attachIdArray = new Array();
    			$(".uploader_file").each(function(){
    				var attachId = $(this).attr("attachId");
    				attachIdArray.push(attachId);
    			});
    			var attachIds = attachIdArray.join(CSIT.join);
    			$("#attachIdsTodo").val(attachIds);
				return isValid;
			},
			success : function(data) {
				$.messager.progress('close');
				var data = eval('(' + data + ')');
				if (data.isSuccess) {
					$.messager.alert('提示', '保存成功', 'info', function() {
						$(app.id.editDialogId).dialog('close');
						$(app.id.listId).datagrid('reload');
						$("#createList").datagrid('reload');
						$("#copyList").datagrid('reload');
						
					});
				} else {
					$.messager.alert('提示', data.message, 'error');
				}
			}
		});
	},
/*	initCreateEditForm : function(){
		$(app.id.editFormId).form({
			onSubmit : function() {
				var isValid = $(this).form('validate');
				if (!isValid) {
					$.messager.progress('close');
				}
				var attachIdArray = new Array();
    			$(".uploader_file").each(function(){
    				var attachId = $(this).attr("attachId");
    				attachIdArray.push(attachId);
    			});
    			var attachIds = attachIdArray.join(CSIT.join);
    			$("#attachIdsTodo").val(attachIds);
				return isValid;
			},
			success : function(data) {
				$.messager.progress('close');
				var data = eval('(' + data + ')');
				if (data.isSuccess) {
					$.messager.alert('提示', '保存成功', 'info', function() {
						$(app.id.editDialogId).dialog('close');
						$("#createList").datagrid('reload');
						$("#createList").datagrid('reload');
						$("#copyList").datagrid('reload');
					});
				} else {
					$.messager.alert('提示', data.message, 'error');
				}
			}
		});
	},*/
	 //客户名称
    initCustomerEditDialog:function(){
    	$('#customerList').datagrid({  
			  border:true,
			  url:app.getUrl("getCustomerByList"),
			  rownumbers:true,
			  pagination:true,
			  fit:true,
			  toolbar:[{
				  text:'选择',
					iconCls:'icon-ok',
					handler:function(){
						var selectedRow = $('#customerList').datagrid('getSelected');
						if(selectedRow==null){
				    		$.messager.alert('提示','请选择数据行','warning');
				    		return;
				    	}
						$("#customerNameIdBtn").textbox("setValue", selectedRow.customerName);
				        $("#customerId").val(selectedRow.id);
				        $("#customerComplainIdBtn").textbox("setValue", "");
				        $("#customerComplainId").val("");
				        $("#customerCareIdBtn").textbox("setValue", "");
				        $("#customerCareId").val("");
				        $("#customerEditDialog").dialog('close');
				        var url=app.getUrl("getownInfo");
				    	var data ={data:selectedRow.id};
				    	var userData = CSIT.syncCallService(url, data);
				    	if(userData.name!=undefined){
				    	     $("#doUserIdBtn").textbox("setValue", userData.name);
				             $("#do_user_id").val(userData.id);
				              var taskTypeValue = $("#taskTypeOne").combobox('getValue');
					           if(taskTypeValue==3){
					        	   todo.initComplaintEditDialog();
					           }else if(taskTypeValue==4){
					        	   todo.initCareEditDialog();
					           }
				            
				            
				    	}
					}
				}],
	    	  ctrlSelect:true,
			  method:"POST",
		      columns:[[           
		          {field:'customerName',title:'客戶名称',width:100,align:'center'},
		   	  ]],
		   	  onDblClickCell: function(index,field,value){
		   		  var rows = $('#customerList').datagrid('getRows');
		   		  var selectedRow = rows[index];
		   		  $("#customerNameIdBtn").textbox("setValue", selectedRow.customerName);
		          $("#customerId").val(selectedRow.id);
		          $("#customerComplainIdBtn").textbox("setValue", "");
			      $("#customerComplainId").val("");
			      $("#customerCareIdBtn").textbox("setValue", "");
			      $("#customerCareId").val("");
		          $("#customerEditDialog").dialog('close');
		          var url=app.getUrl("getownInfo");
			    	var data ={data:selectedRow.id};
			    	var userData = CSIT.syncCallService(url, data);
			    	if(userData.name!=undefined){
			    	     $("#doUserIdBtn").textbox("setValue", userData.name);
			             $("#do_user_id").val(userData.id);
			             var taskTypeValue = $("#taskTypeOne").combobox('getValue');
				           if(taskTypeValue==3){
				        	   todo.initComplaintEditDialog();
				           }else if(taskTypeValue==4){
				        	   todo.initCareEditDialog();
				           }
			    	}
		   	  }
		});
    	var params = {
	    	title:'选择客户',
			width : 600,
			height : document.documentElement.clientHeight-5<500?document.documentElement.clientHeight-5:500,
			closed : true,
			cache : false,
			modal : true,
		};
	    $("#customerEditDialog").dialog(params);
    },
    //跟进人员
    initfollowPersonnelEditDialog :function(){
    	$('#followPeopleList').datagrid({  
			  border:true,
			  //url:ctx + "/myWorkbench/follow/getUserList.jhtml",
			  url:app.getUrl("getUserList"),
			  rownumbers:true,
			  pagination:true,
			  fit:true,
			  toolbar:[{
				  text:'选择',
					iconCls:'icon-ok',
					handler:function(){
						var selectedRow = $('#followPeopleList').datagrid('getSelected');
						if(selectedRow==null){
				    		$.messager.alert('提示','请选择数据行','warning');
				    		return;
				    	}
						$("#followPersonnel").textbox("setValue", selectedRow.name);
				        $("#followPersonnelId").val(selectedRow.id);
				        $("#followPersonnelEditDialog").dialog('close');
					}
				}],
	    	  ctrlSelect:true,
			  method:"POST",
		      columns:[[           
		          {field:'name',title:'姓名',width:100,align:'center'},
		   	  ]],
		   	  onDblClickCell: function(index,field,value){
		   		  var rows = $('#followPeopleList').datagrid('getRows');
		   		  var selectedRow = rows[index];
		   		$("#followPersonnel").textbox("setValue", selectedRow.name);
		        $("#followPersonnelId").val(selectedRow.id);
		        $("#followPersonnelEditDialog").dialog('close');
		   	  }
		});
  	var params = {
	    	title:'选择跟进人员',
			width : 600,
			height : document.documentElement.clientHeight-5<500?document.documentElement.clientHeight-5:500,
			closed : true,
			cache : false,
			modal : true,
		};
	    $("#followPersonnelEditDialog").dialog(params);
  },
  //下次跟进人员
  inittaskfollowPersonnelEditDialog :function(){
  	$('#followPeopleTaskList').datagrid({  
			  border:true,
			  url:app.getUrl("getUserList"),
			  rownumbers:true,
			  pagination:true,
			  fit:true,
			  toolbar:[{
				  text:'选择',
					iconCls:'icon-ok',
					handler:function(){
						var selectedRow = $('#followPeopleTaskList').datagrid('getSelected');
						if(selectedRow==null){
				    		$.messager.alert('提示','请选择数据行','warning');
				    		return;
				    	}
						$("#taskFollowPeople").textbox("setValue", selectedRow.name);
				        $("#taskFollowPersonnelId").val(selectedRow.id);
				        $("#taskfollowPersonnelEditDialog").dialog('close');
					}
				}],
	    	  ctrlSelect:true,
			  method:"POST",
		      columns:[[           
		          {field:'name',title:'姓名',width:100,align:'center'},
		   	  ]],
		   	  onDblClickCell: function(index,field,value){
		   		  var rows = $('#followPeopleTaskList').datagrid('getRows');
		   		  var selectedRow = rows[index];
		   		$("#taskFollowPeople").textbox("setValue", selectedRow.name);
		        $("#taskFollowPersonnelId").val(selectedRow.id);
		        $("#taskfollowPersonnelEditDialog").dialog('close');
		   	  }
		});
	var params = {
	    	title:'选择跟进人员',
			width : 600,
			height : document.documentElement.clientHeight-5<500?document.documentElement.clientHeight-5:500,
			closed : true,
			cache : false,
			modal : true,
		};
	    $("#taskfollowPersonnelEditDialog").dialog(params);
},
    //关联投诉
    initComplaintEditDialog:function(){
    	var customerId=$("#customerId").val();
    	$('#complaintList').datagrid({  
			  border:true,
			  url:app.getUrl("getComplaintByList"),
			  queryParams : {"customerId":customerId},
			  rownumbers:true,
			  pagination:true,
			  fit:true,
			  toolbar:[{
				  text:'选择',
					iconCls:'icon-ok',
					handler:function(){
						var selectedRow = $('#complaintList').datagrid('getSelected');
						if(selectedRow==null){
				    		$.messager.alert('提示','请选择数据行','warning');
				    		return;
				    	}
						$("#customerComplainIdBtn").textbox("setValue", selectedRow.complaintContent);
				        $("#customerComplainId").val(selectedRow.id);
				        $("#complaintEditDialog").dialog('close');
				      
					}
				}],
	    	  ctrlSelect:true,
			  method:"POST",
		      columns:[[           
                  {field:'complaintContent',title:'投诉内容',width:100,align:'center'},
                  {field:'complaintTime',title:'投诉时间',width:100,align:'center'},
                  
		          
		   	  ]],
		   	  onDblClickCell: function(index,field,value){
		   		  var rows = $('#complaintList').datagrid('getRows');
		   		  var selectedRow = rows[index];
		   		  $("#customerComplainIdBtn").textbox("setValue", selectedRow.complaintContent);
		          $("#customerComplainId").val(selectedRow.id);
		          $("#complaintEditDialog").dialog('close');
		       
		   	  }
		});
    	var params = {
	    	title:'选择投诉',
			width : 600,
			height : document.documentElement.clientHeight-5<500?document.documentElement.clientHeight-5:500,
			closed : true,
			cache : false,
			modal : true,
		};
	    $("#complaintEditDialog").dialog(params);
    },
	 //执行者
    initOwnerEditDialog:function(){
    	$('#ownerList').datagrid({  
			  border:true,
			  url:app.getUrl("getOwnerByList"),
			  rownumbers:true,
			  pagination:true,
			  fit:true,
			  toolbar:[{
				  text:'选择',
					iconCls:'icon-ok',
					handler:function(){
						var selectedRow = $('#ownerList').datagrid('getSelected');
						if(selectedRow==null){
				    		$.messager.alert('提示','请选择数据行','warning');
				    		return;
				    	}
						$("#doUserIdBtn").textbox("setValue", selectedRow.name);
				        $("#do_user_id").val(selectedRow.id);
				        $("#ownerEditDialog").dialog('close');
				      
					}
				}],
	    	  ctrlSelect:true,
			  method:"POST",
		      columns:[[           
                  {field:'name',title:'姓名',width:100,align:'center'}
		   	  ]],
		   	  onDblClickCell: function(index,field,value){
		   		  var rows = $('#ownerList').datagrid('getRows');
		   		  var selectedRow = rows[index];
		   		  $("#doUserIdBtn").textbox("setValue", selectedRow.name);
		          $("#do_user_id").val(selectedRow.id);
		          $("#ownerEditDialog").dialog('close');
		       
		   	  }
		});
    	var params = {
	    	title:'选择执行者',
			width : 600,
			height : document.documentElement.clientHeight-5<500?document.documentElement.clientHeight-5:500,
			closed : true,
			cache : false,
			modal : true,
		};
	    $("#ownerEditDialog").dialog(params);
    },
    //关联关怀
    initCareEditDialog:function(){
    	var customerId=$("#customerId").val();
    	$('#careList').datagrid({  
			  border:true,
			  url:app.getUrl("getCareByList"),
			  queryParams : {"customerId":customerId},
			  rownumbers:true,
			  pagination:true,
			  fit:true,
			  toolbar:[{
				  text:'选择',
					iconCls:'icon-ok',
					handler:function(){
						var selectedRow = $('#careList').datagrid('getSelected');
						if(selectedRow==null){
				    		$.messager.alert('提示','请选择数据行','warning');
				    		return;
				    	}
						$("#customerCareIdBtn").textbox("setValue", selectedRow.careContent);
				        $("#customerCareId").val(selectedRow.id);
				        $("#careEditDialog").dialog('close');
				      
					}
				}],
	    	  ctrlSelect:true,
			  method:"POST",
		      columns:[[           
		          {field:'careContent',title:'关怀内容',width:100,align:'center'},
		          
		   	  ]],
		   	  onDblClickCell: function(index,field,value){
		   		  var rows = $('#careList').datagrid('getRows');
		   		  var selectedRow = rows[index];
		   		  $("#customerCareIdBtn").textbox("setValue", selectedRow.careContent);
		          $("#customerCareId").val(selectedRow.id);
		          $("#careEditDialog").dialog('close');
		   		 
		       
		   	  }
		});
    	var params = {
	    	title:'选择关怀',
			width : 600,
			height : document.documentElement.clientHeight-5<500?document.documentElement.clientHeight-5:500,
			closed : true,
			cache : false,
			modal : true,
		};
	    $("#careEditDialog").dialog(params);
    },
    //抄送人
    initReceiverEditDialog:function(){
		$('#receiverrightGrid').datagrid({  
			  border:true,
			  method:"POST",
			  url:app.getUrl("listDelPersonByPage"),
			  rownumbers:true,
			  fit:true,
		      columns:[[           
		      {field:'ck',checkbox:true},
              {field: 'name', title: '姓名', width: 80, align: "center"},
              {field:'id',hidden:true}
		   	  ]]  
		});
    	$('#receiverleftGrid').datagrid({  
    		  method:"POST",
			  url:app.getUrl("listPersonByPage"),
			  rownumbers:true,
			  pagination:true,
			  fit:true,
	    	  ctrlSelect:true,
		      columns:[[           
		        {field:'ck',checkbox:true},
                {field: 'name', title: '姓名', width: 80, align: "center"},
                {field:'id',hidden:true}
		   	  ]]
		});
    	var params = $.extend({
    		title:'选择抄送人',
    		height:document.documentElement.clientHeight-70,
    	    width:900,
			closed : true,
			cache : false,
			modal : true,
			buttons : [ {
				text : '保存',
				iconCls : 'icon-save',
				handler : function() {
					var dataName="";
					var dataid="";
					var rows=$("#receiverrightGrid").datagrid("getRows");
					for(var i=0;i<rows.length;i++){
						dataName=dataName+rows[i].name+",";
						dataid=dataid+rows[i].id+","
						if(i==rows.length-1){
			    			dataid=dataid.substr(0, dataid.length - 1);
			    			dataName=dataName.substr(0, dataName.length - 1);
			    		}
					};
					$("#receiverName").textbox("setValue", dataName);
			        $("#receivecopyId").val(dataid);
					$("#receiverEditDialog").dialog('close');
				}
			}, {
				text : '退出',
				iconCls : 'icon-exit',
				handler : function() {
					$("#storeId").val("");
					$("#storeName").val("");
					$("#receiverEditDialog").dialog('close');
				}
			} ],
			onClose:function(){
			}
		},params);
    	$("#receiverEditDialog").dialog(params);
	},
	initTaskreceiverEditDialog:function(){
		$('#taskreceiverrightGrid').datagrid({  
			  border:true,
			  method:"POST",
			  url:app.getUrl("listDelPersonByPage"),
			  rownumbers:true,
			  fit:true,
		      columns:[[           
		      {field:'ck',checkbox:true},
            {field: 'name', title: '姓名', width: 80, align: "center"},
            {field:'id',hidden:true}
		   	  ]]  
		});
  	$('#taskreceiverleftGrid').datagrid({  
  		  method:"POST",
			  url:app.getUrl("listPersonByPage"),
			  rownumbers:true,
			  pagination:true,
			  fit:true,
	    	  ctrlSelect:true,
		      columns:[[           
		        {field:'ck',checkbox:true},
              {field: 'name', title: '姓名', width: 80, align: "center"},
              {field:'id',hidden:true}
		   	  ]]
		});
  	var params = $.extend({
  		title:'选择抄送人',
  		height:document.documentElement.clientHeight-70,
  	    width:900,
		closed : true,
		cache : false,
		modal : true,
		buttons : [ {
			text : '保存',
			iconCls : 'icon-save',
			handler : function() {
				var dataName="";
				var dataid="";
				var rows=$("#taskreceiverrightGrid").datagrid("getRows");
				for(var i=0;i<rows.length;i++){
					dataName=dataName+rows[i].name+",";
					dataid=dataid+rows[i].id+","
					if(i==rows.length-1){
		    			dataid=dataid.substr(0, dataid.length - 1);
		    			dataName=dataName.substr(0, dataName.length - 1);
		    		}
				};
				$("#receiverNameTask").textbox("setValue", dataName);
		        $("#receiveTaskId").val(dataid);
				$("#taskreceiverEditDialog").dialog('close');
			}
		}, {
			text : '退出',
			iconCls : 'icon-exit',
			handler : function() {
				$("#taskStoreId").val("");
				$("#taskStoreName").val("");
				$("#taskreceiverEditDialog").dialog('close');
			}
		} ],
			onClose:function(){
			}
		},params);
  	$("#taskreceiverEditDialog").dialog(params);
	},
	initEditCommentDialog : function() {
		var params = {
			title : '编辑',
			width :500,
			height : document.documentElement.clientHeight-5<500?document.documentElement.clientHeight-5:500,
			closed : true,
			cache : false,
			modal : true,
			buttons : [ {
				text : '保存',
				iconCls : 'icon-save',
				handler : function() {
					$("#editCommentForm").submit();
				}
			}, {
				text : '退出',
				iconCls : 'icon-exit',
				handler : function() {
					$("#editCommentDialog").dialog('close');
				}
			} ],
			onClose : function() {
				$("#editCommentDialog").form('clear');
			}
		};
		$("#editCommentDialog").dialog(params);
	},
	initEditCommentContentDialog : function(){
		var params = {
				title : '编辑评论',
				width :450,
				height : document.documentElement.clientHeight-5<350?document.documentElement.clientHeight-5:350,
				closed : true,
				cache : false,
				modal : true,
				buttons : [ {
					text : '保存',
					iconCls : 'icon-save',
					handler : function() {
						$("#editCommentContentForm").submit();
					}
				}, {
					text : '退出',
					iconCls : 'icon-exit',
					handler : function() {
						$("#editCommentContentDialog").dialog('close');
					}
				} ],
				onClose : function() {
					$("#editCommentContentDialog").form('clear');
				}
			};
			$("#editCommentContentDialog").dialog(params);
	},
	initEditNotComplectDialog : function(){
		var params = {
				title : '编辑未完成',
				width :450,
				height : document.documentElement.clientHeight-5<350?document.documentElement.clientHeight-5:350,
				closed : true,
				cache : false,
				modal : true,
				buttons : [ {
					text : '保存',
					iconCls : 'icon-save',
					handler : function() {
						$("#editNotComplectForm").submit();
					}
				}, {
					text : '退出',
					iconCls : 'icon-exit',
					handler : function() {
						$("#editNotComplectDialog").dialog('close');
					}
				} ],
				onClose : function() {
					$("#editNotComplectDialog").form('clear');
				}
			};
			$("#editNotComplectDialog").dialog(params);
	},
	initEditCancelDialog : function(){
		var params = {
				title : '编辑取消说明',
				width :500,
				height : document.documentElement.clientHeight-5<350?document.documentElement.clientHeight-5:350,
				closed : true,
				cache : false,
				modal : true,
				buttons : [ {
					text : '保存',
					iconCls : 'icon-save',
					handler : function() {
						$("#editCancelForm").submit();
					}
				}, {
					text : '退出',
					iconCls : 'icon-exit',
					handler : function() {
						$("#editCancelDialog").dialog('close');
					}
				} ],
				onClose : function() {
					$("#editCancelDialog").form('clear');
				}
			};
			$("#editCancelDialog").dialog(params);
	},
	initEditComplectExplainDialog: function(){
		var params = {
				title : '编辑完成',
				width :500,
				height : document.documentElement.clientHeight-5<350?document.documentElement.clientHeight-5:350,
				closed : true,
				cache : false,
				modal : true,
				buttons : [ {
					text : '保存',
					iconCls : 'icon-save',
					handler : function() {
						$("#editComplectExplainForm").submit();
					}
				}, {
					text : '退出',
					iconCls : 'icon-exit',
					handler : function() {
						$("#editComplectExplainDialog").dialog('close');
					}
				} ],
				onClose : function() {
					$("#editComplectExplainDialog").form('clear');
				}
			};
			$("#editComplectExplainDialog").dialog(params);
	},
	initEditComplectDialog : function() {
		var params = {
			title : '编辑跟进任务',
			zIndex:50,
			width : document.documentElement.clientWidth<650?document.documentElement.clientWidth:650,
			height : document.documentElement.clientHeight<500?document.documentElement.clientHeight:500,
			closed : true,
			cache : false,
			modal : true,
			buttons : [ {
				text : '保存',
				iconCls : 'icon-save',
				handler : function() {
					$("#followDetails").val(detailEditor.html());
					$("#editComplectForm").submit();
				}
			}, {
				text : '退出',
				iconCls : 'icon-exit',
				handler : function() {
					$("#editComplectDialog").dialog('close');
					$("#missionBolock").css('display','none');
					detailEditor.html("");
				}
			} ],
			onClose : function() {
				$("#editComplectForm").form('clear');
				$("#missionBolock").css('display','none');
				detailEditor.html("");
			}
		};
		$("#editComplectDialog").dialog(params);
	},
	initComplectEditForm : function() {
		$("#editComplectForm").form({
			onSubmit : function() {
				var isValid = $(this).form('validate');
				if (!isValid) {
					$.messager.progress('close');
				}
				var attachIdArray = new Array();
    			$(".uploader_file").each(function(){
    				var attachId = $(this).attr("attachId");
    				attachIdArray.push(attachId);
    			});
    			var attachIds = attachIdArray.join(CSIT.join);
    			$("#attachIds").val(attachIds);
				return isValid;
			},
			success : function(data) {
				$.messager.progress('close');
				var data = eval('(' + data + ')');
				if (data.isSuccess) {
					$.messager.alert('提示', '保存成功', 'info', function() {
						$("#editComplectDialog").dialog('close');
						$("#viewDialog").dialog('close');
						$("#list").datagrid('reload');
						$("#copyList").datagrid('reload');
						$("#createList").datagrid('reload');
					});
				} else {
					$.messager.alert('提示', data.message, 'error');
				}
			}
		});
	},
	complect : function(){
	    $("#customerNameComplect").textbox("setValue", $("#customerName").val());
        $("#customerComplectId").val($("#customeridOne").val());
        var date=new Date();
		var year=date.getFullYear();
		var month=date.getMonth()+1;
		$("#todoisActive").val(false);
		/*//获取当前日
		var day=date.getDate(); 
		$("#startDate").val(year+"-"+month+"-"+day);
		$("#nextTime").val(year+"-"+month+"-"+day);
		var user=CSIT.syncCallService(ctx + "/myWorkbench/follow/getCurrentUser.jhtml");
		$("#taskFollowPeople").val(user.id);
		$("#taskFollowPersonnelId").textbox("setValue",user.username);*/
		$("#editComplectDialog").dialog('open');
		todo.initComplectEditForm();
		//清空附件
    	$("#filelist").empty();
		$("#editComplectForm").attr('action', app.getUrl("complete"));
	},
	add : function() {
		$("#cusname").show();
   	    $("#concomplain").hide();
 		$("#concare").hide();
		$("#taskTypeOne").combobox({  
			onChange: function () {  
		           var taskTypeValue = $("#taskTypeOne").combobox('getValue');
		           if(taskTypeValue==1){
		        	   $("#cusname").show();
		 	    	  $("#concomplain").hide();
		 	  		  $("#concare").hide();
		 		      $("#customerComplainId").val("");
		 		      $("#customerComplainIdBtn").textbox("setValue", "");
		 		      $("#customerCareId").val("");
		 		      $("#customerCareIdBtn").textbox("setValue", "");
		 		      $("#customerId").val("");
		 		      $("#customerNameIdBtn").textbox("setValue", "");
		 		      $("#do_user_id").val("");
			 	      $("#doUserIdBtn").textbox("setValue", "");
			 	 	  $("#customerNameIdBtn").textbox({required:true});
			 	 	  $("#customerComplainIdBtn").textbox({required:false});
			 	 	  $("#customerCareIdBtn").textbox({required:false});
		           }else if(taskTypeValue==2){
		        	  $("#cusname").hide();
		  			 $("#concomplain").hide();
		  			 $("#concare").hide();
		  			$("#customerNameIdBtn").textbox({required:false});
		  	   	     var url=app.getUrl("userInfo");
		  		    var data ={data:1};
		  		    var userData = CSIT.syncCallService(url, data);
		  		    if(userData.username!=undefined){
		  		       $("#doUserIdBtn").textbox("setValue", userData.name);
		  		       $("#do_user_id").val(userData.id);
		  		    }
		  		     $("#customerComplainId").val("");
		  			 $("#customerComplainIdBtn").textbox("setValue", "");
		 		     $("#customerCareId").val("");
		 		     $("#customerCareIdBtn").textbox("setValue", "");
		 		     $("#customerId").val("");
		 		     $("#customerNameIdBtn").textbox("setValue", "");
		 		    $("#customerComplainIdBtn").textbox({required:false});
			 	 	  $("#customerCareIdBtn").textbox({required:false});
		           }else if(taskTypeValue==3){
		        	 $("#cusname").show();
		 	    	 $("#concomplain").show();
		 	    	 $("#concare").hide();
		 	    	 $("#customerCareId").val("");
		 	    	 $("#customerCareIdBtn").textbox("setValue", "");
		 	    	 $("#customerId").val("");
		 	    	 $("#customerNameIdBtn").textbox("setValue", "");
		 	    	 $("#do_user_id").val("");
		 	    	 $("#doUserIdBtn").textbox("setValue", "");
		 	    	 $("#customerNameIdBtn").textbox({required:true});
		 	    	 $("#customerComplainIdBtn").textbox({required:true});
			 	 	 $("#customerCareIdBtn").textbox({required:false});
		  		      
		           }else if(taskTypeValue==4){
		        	   $("#cusname").show();
		 	    	   $("#concare").show();
		 	    	   $("#concomplain").hide();
		 	    	   $("#customerComplainId").val("");
		 	    	   $("#customerComplainIdBtn").textbox("setValue", "");
		 	    	   $("#customerId").val("");
		 	    	   $("#customerNameIdBtn").textbox("setValue", "");
		 	    	   $("#do_user_id").val("");
			 	       $("#doUserIdBtn").textbox("setValue", "");
			 	       $("#customerNameIdBtn").textbox({required:true});
			 	       $("#customerComplainIdBtn").textbox({required:false});
				 	   $("#customerCareIdBtn").textbox({required:true});
			        }
			}
		});
		$(app.id.editDialogId).dialog('open');
        $(app.id.editDialogId).scrollTop(0);
		todo.initEditForm();
		$("#taskTypeOne").combobox("setValue",1);
		$("#remindUnit").combobox("setValue",1);
		//清空附件
    	$("#todofilelist").empty();
		$(app.id.editFormId).attr('action', app.url.create());
	},
	addComment : function() {
		var selectedRow = $(app.id.listId).datagrid('getSelected');
		$("#todoIdComment").val(selectedRow.id);
		//初始化提交评论from表单
		todo.initCommentEditForm();
		$("#commentForm").attr('action', app.getUrl("addComment"));
		$("#commentForm").submit();
		
	},
	addCreateComment :function(){
		var selectedRow = $("#createList").datagrid('getSelected');
		$("#todoIdCommentCreate").val(selectedRow.id);
		todo.initCreateCommentEditForm();
		$("#commentCreateForm").attr('action', app.getUrl("addComment"));
		$("#commentCreateForm").submit();
	},
	addCopyComment:function(){
		var selectedRow = $("#copyList").datagrid('getSelected');
		$("#todoIdCommentCopy").val(selectedRow.id);
		todo.initCopyCommentEditForm();
		$("#commentCopyForm").attr('action', app.getUrl("addComment"));
		$("#commentCopyForm").submit();
	},
	//下载附件
	downCreate:function(){
		var selectedRow = $("#createList").datagrid('getSelected');
		if(selectedRow==null){
    		$.messager.alert('提示','请选择数据行','warning');
    		return;
    	}
		alert(selectedRow.id);
	     window.open(ctx + "/myWorkbench/todo/download.jhtml?todoId="+selectedRow.id);
		
	},
	notcomplect :function(){
		
		$("#editNotComplectDialog").dialog('open');
		todo.initNotComplectEditForm();
		$("#editNotComplectForm").attr('action', app.getUrl("addNotComplect"));
	},
	otherComplect :function(){
		$("#editComplectExplainDialog").dialog('open');
		todo.initComplectExplainEditForm();
		$("#editComplectExplainForm").attr('action', app.getUrl("otherComplect"));
	},
	cancel: function(){
		$("#editCancelDialog").dialog('open');
		todo.initCancelEditForm();
		$("#editCancelForm").attr('action', app.getUrl("addCancel"));
	},
	update : function() {
		var selectedRow = $(app.id.listId).datagrid('getSelected');
		if (selectedRow == null) {
			$.messager.alert('提示', '请选择数据行', 'warning');
			return;
		}
		
		$("#customerNameIdBtn").textbox("setValue", selectedRow.customerName);
        $("#customerId").val(selectedRow.customerId);
        $("#doUserIdBtn").textbox("setValue", selectedRow.doUserName);
        $("#do_user_id").val(selectedRow.doUserId);
        $("#todoIdComment").val(selectedRow.id);
	},
	updateCreate: function() {
		var selectedRow = $("#createList").datagrid('getSelected');
		if (selectedRow == null) {
			$.messager.alert('提示', '请选择数据行', 'warning');
			return;
		}
		$("#cusname").show();
   	    $("#concomplain").hide();
 		$("#concare").hide();
 		 $("#customerCareIdBtn").hide();
 		 $("#customerComplainIdBtn").hide();
		//清空附件
    	$("#todofilelist").empty();
		$("#taskTypeOne").combobox({  
			onChange: function () {  
		           var taskTypeValue = $("#taskTypeOne").combobox('getValue');
		           if(taskTypeValue==1){
		        	  $("#cusname").show();
		 	    	  $("#concomplain").hide();
		 	  		  $("#concare").hide();
		 		      $("#customerComplainId").val("");
		 		      $("#customerComplainIdBtn").textbox("setValue", "");
		 		      $("#customerCareId").val("");
		 		      $("#customerCareIdBtn").textbox("setValue", "");
		 		 	 $("#customerId").val(selectedRow.customerId==undefined?"":selectedRow.customerId);
		 			 $("#customerNameIdBtn").textbox("setValue", selectedRow.customerName==undefined?"":selectedRow.customerName);
		 			 $("#doUserIdBtn").textbox("setValue", selectedRow.name==undefined?"":selectedRow.name);
		 	         $("#do_user_id").val(selectedRow.doUserId==undefined?"":selectedRow.doUserId);
		 	        $("#customerNameIdBtn").textbox({required:true});
			 	 	  $("#customerComplainIdBtn").textbox({required:false});
			 	 	  $("#customerCareIdBtn").textbox({required:false});
		           }else if(taskTypeValue==2){
		        	 $("#cusname").hide();
		  			 $("#concomplain").hide();
		  			 $("#concare").hide();
		  			 $("#customerNameIdBtn").textbox({required:false});
		  	   	     var url=app.getUrl("userInfo");
		  		    var data ={data:1};
		  		    var userData = CSIT.syncCallService(url, data);
		  		    if(userData.username!=undefined){
		  		       $("#doUserIdBtn").textbox("setValue", userData.name);
		  		       $("#do_user_id").val(userData.id);
		  		    }
		  		     $("#customerComplainId").val("");
		  			 $("#customerComplainIdBtn").textbox("setValue", "");
		 		     $("#customerCareId").val("");
		 		     $("#customerCareIdBtn").textbox("setValue", "");
		 		    $("#customerId").val(selectedRow.customerId==undefined?"":selectedRow.customerId);
		 			 $("#customerNameIdBtn").textbox("setValue", selectedRow.customerName==undefined?"":selectedRow.customerName);
		 			$("#customerComplainIdBtn").textbox({required:false});
			 	 	  $("#customerCareIdBtn").textbox({required:false});
		           }else if(taskTypeValue==3){
		        	 $("#cusname").show();
		 	    	 $("#concomplain").show();
		 	    	 $("#concare").hide();
		 	    	 $("#customerCareId").val("");
		 	    	 $("#customerCareIdBtn").textbox("setValue", "");
		 	    	 $("#customerComplainId").val(selectedRow.customerComplainId==undefined?"":selectedRow.customerComplainId);
		 	    	 $("#customerComplainIdBtn").textbox("setValue", selectedRow.complaintContent==undefined?"":selectedRow.complaintContent);
		 	    	 $("#customerId").val(selectedRow.customerId==undefined?"":selectedRow.customerId);
		 			 $("#customerNameIdBtn").textbox("setValue", selectedRow.customerName==undefined?"":selectedRow.customerName);
		 			 $("#doUserIdBtn").textbox("setValue", selectedRow.name==undefined?"":selectedRow.name);
		 	         $("#do_user_id").val(selectedRow.doUserId==undefined?"":selectedRow.doUserId);
		 	        $("#customerNameIdBtn").textbox({required:true});
		 	    	 $("#customerComplainIdBtn").textbox({required:true});
			 	 	 $("#customerCareIdBtn").textbox({required:false});
		  		      
		           }else if(taskTypeValue==4){
		        	   $("#cusname").show();
		 	    	   $("#concare").show();
		 	    	   $("#concomplain").hide();
		 	    	   $("#customerComplainId").val("");
		 	    	   $("#customerComplainIdBtn").textbox("setValue", "");
		 	    	   $("#customerCareId").val(selectedRow.customerCareId==undefined?"":selectedRow.customerCareId);
			 	       $("#customerCareIdBtn").textbox("setValue",selectedRow.careContent==undefined?"":selectedRow.careContent);
		 	    	   $("#customerId").val(selectedRow.customerId==undefined?"":selectedRow.customerId);
			 		   $("#customerNameIdBtn").textbox("setValue", selectedRow.customerName==undefined?"":selectedRow.customerName);
			 		   $("#doUserIdBtn").textbox("setValue", selectedRow.name==undefined?"":selectedRow.name);
			 	       $("#do_user_id").val(selectedRow.doUserId==undefined?"":selectedRow.doUserId);
			 	      $("#customerNameIdBtn").textbox({required:true});
			 	       $("#customerComplainIdBtn").textbox({required:false});
				 	   $("#customerCareIdBtn").textbox({required:true});
			        }
			}
		});
        $("#doUserIdBtn").textbox("setValue", selectedRow.name);
        $("#do_user_id").val(selectedRow.doUserId);
        //获取抄送人
        var url=app.getUrl("getReceiverName");
        var dataId=selectedRow.id;
    	var data ={data:dataId};
    	var userData = CSIT.syncCallService(url, data);
    	if(userData.copyName!=undefined){
    	     $("#receiverName").textbox("setValue", userData.copyName);
    	     $("#receivecopyId").val(userData.copyId);
    	}
		$(app.id.editDialogId).dialog('open');
		var result = CSIT.syncCallService(ctx + "/myWorkbench/todo/todoAttach.jhtml",{todoId:selectedRow.id});
    	for (var i = 0; i < result.length; i++) {
    		todoAttach.createAttach(todoAttach.uploaderTodo,result[i]);
		}
		todo.initEditForm();
	//	$("#customerId").val(selectedRow.customerId==undefined?"":selectedRow.customerId);
		$(app.id.editFormId).form('load', selectedRow);
		$(app.id.editFormId).attr('action', app.url.update());
	},
	//更新评论
	updateComment : function(comment) {
		$("#todoIdCommentContent").val(comment);
		//获取评论内容
		var url=app.getUrl("getCommentContent");
    	var comentId ={comentId:comment};
    	var commentContentData = CSIT.syncCallService(url, comentId);
    	$("#commentContent").val(commentContentData.comment);
		$("#editCommentContentDialog").dialog('open');
		todo.initEditCommentContentForm();
		$("#editCommentContentForm").attr('action', app.getUrl("updateComment"));
	},
	//更新评论-我创建的
	updateCommentCreate : function(comment) {
		$("#todoIdCommentContent").val(comment);
		//获取评论内容
		var url=app.getUrl("getCommentContent");
    	var comentId ={comentId:comment};
    	var commentContentData = CSIT.syncCallService(url, comentId);
    	$("#commentContent").val(commentContentData.comment);
		$("#editCommentContentDialog").dialog('open');
		todo.initEditCommentContentCreateForm();
		$("#editCommentContentForm").attr('action', app.getUrl("updateComment"));
	},
	//更新评论-抄送我的
	updateCommentCopy: function(comment) {
		$("#todoIdCommentContent").val(comment);
		//获取评论内容
		var url=app.getUrl("getCommentContent");
    	var comentId ={comentId:comment};
    	var commentContentData = CSIT.syncCallService(url, comentId);
    	$("#commentContent").val(commentContentData.comment);
		$("#editCommentContentDialog").dialog('open');
		todo.initEditCommentContentCopyForm();
		$("#editCommentContentForm").attr('action', app.getUrl("updateComment"));
	},
	view : function(){
		var selectedRow = $(app.id.listId).datagrid('getSelected');
		if (selectedRow == null) {
			$.messager.alert('提示', '请选择数据行', 'warning');
			return;
		}
		$("#cusnameOne").show();
		$("#doUserPersonIdBtn").text(selectedRow.name);
	    $("#doUserPersonId").val(selectedRow.doUserId);
	    $("#doTimeView").text(selectedRow.doTime);
	    if(selectedRow.taskType==1){
	    	taskType='客户跟进';
	    }else if(selectedRow.taskType==2){
	    	$("#cusnameOne").hide();
	    	taskType='日常任务';
	    }else if(selectedRow.taskType==3){
	    	taskType='客户投诉';
	    }else if(selectedRow.taskType==4){
	    	taskType='客户关怀';
	    }else {
	    	taskType='';
	    }
	    $("#taskTypes").text(taskType);
	    $("#taskTypeOne").val(selectedRow.taskType);
	  //  状态(1进行中2已完成3未完成4已取消)
	    if(selectedRow.status==1){
	    	status='进行中';
	    }else if(selectedRow.status==2){
	    	status='已完成';
	    }else if(selectedRow.status==3){
	    	status='未完成';
	    }else if(selectedRow.status==4){
	    	status='已取消';
	    }else{
	    	status='';
	    }
	    $("#complainIdTodo").val(selectedRow.customerComplainId==undefined?'':selectedRow.customerComplainId);
	    $("#careIdTodo").val(selectedRow.customerCareId==undefined?'':selectedRow.customerCareId);
		$("#notComplect").val(selectedRow.id);
	    $("#cancel").val(selectedRow.id);
	    $("#ComplectExplain").val(selectedRow.id);
	    $("#todoComplect").val(selectedRow.id);
	    $("#cusStatus").text(status);
	    $("#customeridOne").val(selectedRow.customerId); 
	    $("#customerName").val(selectedRow.customerName==undefined?'':selectedRow.customerName);
	    $("#customerName").text(selectedRow.customerName==undefined?'':selectedRow.customerName);
		$("#viewDialog").dialog('open');
		$("#viewForm").form('load', selectedRow);
		$('#viewCommentTable tbody').html('');
		 var url=app.getUrl("userInfo");
	    var dataId=selectedRow.id;
	    var data ={data:1};
	    var userData = CSIT.syncCallService(url, data);
	    var url=app.getUrl("listTodoCommentByPage");
    	var dataId=selectedRow.id;
    	var data ={data:dataId};
    	var commentData = CSIT.syncCallService(url, data);
    	if(commentData.length>0){
    		var temp = "";
    		for(var i=0; i<commentData.length; i++){
    			var comment = commentData[i];
    			temp += '<tr style="height: 25px">';
    			temp += '<td class="commentName">'+(comment.name==undefined?'':comment.name)+'</td>';
    			temp += '<td>'+(comment.commentTime==undefined?'':comment.commentTime)+'</td>';
    			if(comment.commentUserId==userData.id){
        			temp += '  <td><a href="javascript:void(0);" onclick="todo.updateComment('+comment.id+')">编辑</a>';
        			temp += '      <a href="javascript:void(0);" onclick="todo.mulDeleteComment('+comment.id+')">删除</a></td>';
        		}
    			temp += '</tr>';
    			temp += '<td colspan="2">'+(comment.comment==undefined?'':comment.comment)+'</td>';
    			
    			temp += '</tr>';
    		}
    		
    		$('#viewCommentTable').append(temp);
    	}

	},
	viewCreate : function(){
		var selectedRow = $("#createList").datagrid('getSelected');
		if (selectedRow == null) {
			$.messager.alert('提示', '请选择数据行', 'warning');
			return;
		}
		$("#doUserPersonIdCreateBtn").text(selectedRow.name);
	    $("#doTimeCreate").text(selectedRow.doTime);
	    if(selectedRow.taskType==1){
	    	taskType='客户跟进';
	    }else if(selectedRow.taskType==2){
	    	taskType='日常任务';
	    }else if(selectedRow.taskType==3){
	    	taskType='客户投诉';
	    }else if(selectedRow.taskType==4){
	    	taskType='客户关怀';
	    }else {
	    	taskType='';
	    }
	    $("#taskTypesCreate").text(taskType);
	    $("#customerNameCreateBtn").text(selectedRow.customerName);
	    $("#customerNameIdCreate").val(selectedRow.customerId);
	  //  状态(1进行中2已完成3未完成4已取消)
	    if(selectedRow.status==1){
	    	status='进行中';
	    }else if(selectedRow.status==2){
	    	status='已完成';
	    }else if(selectedRow.status==3){
	    	status='未完成';
	    }else if(selectedRow.status==4){
	    	status='已取消';
	    }else{
	    	status='';
	    }
	    $("#complainIdTodo").val(selectedRow.customerComplainId==undefined?'':selectedRow.customerComplainId);
	    $("#careIdTodo").val(selectedRow.customerCareId==undefined?'':selectedRow.customerCareId);
	    $("#notComplect").val(selectedRow.id);
	    $("#cancel").val(selectedRow.id);
	    $("#ComplectExplain").val(selectedRow.id);
	    $("#todoComplect").val(selectedRow.id);
	    $("#cusStatusCreate").text(status);
	    $("#customerNameCreate").text(selectedRow.customerName);
		$("#viewCreateDialog").dialog('open');
		$("#viewCreateForm").form('load', selectedRow);
		$('#viewCommentTableCreate tbody').html('');
		 var url=app.getUrl("userInfo");
	    var dataId=selectedRow.id;
	    var data ={data:1};
	    var userData = CSIT.syncCallService(url, data);
	    var url=app.getUrl("listTodoCommentByPage");
    	var dataId=selectedRow.id;
    	var data ={data:dataId};
    	var commentData = CSIT.syncCallService(url, data);
    	if(commentData.length>0){
    		var temp = "";
    		for(var i=0; i<commentData.length; i++){
    			var comment = commentData[i];
    			temp += '<tr style="height: 25px">';
    			temp += '<td class="commentName">'+(comment.name==undefined?'':comment.name)+'</td>';
    			temp += '<td>'+(comment.commentTime==undefined?'':comment.commentTime)+'</td>';
    			if(comment.commentUserId==userData.id){
        			temp += '  <td><a href="javascript:void(0);" onclick="todo.updateCommentCreate('+comment.id+')">编辑</a>';
        			temp += '      <a href="javascript:void(0);" onclick="todo.mulDeleteCommentCreate('+comment.id+')">删除</a></td>';
        		}
    			temp += '</tr>';
    			temp += '<td colspan="2">'+(comment.comment==undefined?'':comment.comment)+'</td>';
    			
    			temp += '</tr>';
    		}
    		
    		$('#viewCommentTableCreate').append(temp);
    	}
	},
	viewCopy  : function(){
		var selectedRow = $("#copyList").datagrid('getSelected');
		if (selectedRow == null) {
			$.messager.alert('提示', '请选择数据行', 'warning');
			return;
		}
		$("#doUserPersonIdCopyBtn").text(selectedRow.name);
	    $("#doUserPersonIdCopy").val(selectedRow.doUserId);
	    $("#doTimeCopy").text(selectedRow.doTime);
	    if(selectedRow.taskType==1){
	    	taskType='客户跟进';
	    }else if(selectedRow.taskType==2){
	    	taskType='日常任务';
	    }else if(selectedRow.taskType==3){
	    	taskType='客户投诉';
	    }else if(selectedRow.taskType==4){
	    	taskType='客户关怀';
	    }else {
	    	taskType='';
	    }
	    $("#taskTypesCopy").text(taskType);
	    $("#customerNameCopyBtn").text(selectedRow.customerName);
	    $("#customerNameIdCopy").val(selectedRow.customerId);
	  //  状态(1进行中2已完成3未完成4已取消)
	    if(selectedRow.status==1){
	    	status='进行中';
	    }else if(selectedRow.status==2){
	    	status='已完成';
	    }else if(selectedRow.status==3){
	    	status='未完成';
	    }else if(selectedRow.status==4){
	    	status='已取消';
	    }else{
	    	status='';
	    }
	    $("#complainIdTodo").val(selectedRow.customerComplainId==undefined?'':selectedRow.customerComplainId);
	    $("#careIdTodo").val(selectedRow.customerCareId==undefined?'':selectedRow.customerCareId);
	    $("#notComplect").val(selectedRow.id);
	    $("#cancel").val(selectedRow.id);
	    $("#ComplectExplain").val(selectedRow.id);
	    $("#todoComplect").val(selectedRow.id);
	    $("#cusStatusCopy").text(status);
	    $("#customerNameCopy").text(selectedRow.customerName);
		$("#viewCopyDialog").dialog('open');
		$("#viewCopyForm").form('load', selectedRow);
		$('#viewCommentTableCopy tbody').html('');
		 var url=app.getUrl("userInfo");
	    var dataId=selectedRow.id;
	    var data ={data:1};
	    var userData = CSIT.syncCallService(url, data);
	    var url=app.getUrl("listTodoCommentByPage");
    	var dataId=selectedRow.id;
    	var data ={data:dataId};
    	var commentData = CSIT.syncCallService(url, data);
    	if(commentData.length>0){
    		var temp = "";
    		for(var i=0; i<commentData.length; i++){
    			var comment = commentData[i];
    			temp += '<tr style="height: 25px">';
    			temp += '<td class="commentName">'+(comment.name==undefined?'':comment.name)+'</td>';
    			temp += '<td>'+(comment.commentTime==undefined?'':comment.commentTime)+'</td>';
    			if(comment.commentUserId==userData.id){
        			temp += '  <td><a href="javascript:void(0);" onclick="todo.updateCommentCopy('+comment.id+')">编辑</a>';
        			temp += '      <a href="javascript:void(0);" onclick="todo.mulDeleteCommentCopy('+comment.id+')">删除</a></td>';
        		}
    			temp += '</tr>';
    			temp += '<td colspan="2">'+(comment.comment==undefined?'':comment.comment)+'</td>';
    			
    			temp += '</tr>';
    		}
    		
    		$('#viewCommentTableCopy').append(temp);
    	}
	},
	mulDelete : function() {
		var checkedRows = $(app.id.listId).datagrid('getChecked');
		if (checkedRows.length == 0) {
			$.messager.alert('提示', '请选择删除的数据行', 'warning');
			return;
		}
		$.messager.confirm("提示", "确定要删除选中的记录?", function(t) {
			if (!t)
				return;
			var idArray = new Array();
			for (var i = 0; i < checkedRows.length; i++) {
				idArray.push(checkedRows[i].id);
			}
			var ids = idArray.join(CSIT.join);
			var content = {
				ids : ids
			};
			$.messager.progress();
			CSIT.asyncCallService(app.url.mulDelete(), content,
					function(result) {
						if (result.isSuccess) {
							$(app.id.listId).datagrid('reload');
						} else {
							$.messager.alert('错误', result.message, "error");
						}
						$.messager.progress('close');
					});
		});
	},
	mulCreateDelete : function() {
		var checkedRows = $("#createList").datagrid('getChecked');
		if (checkedRows.length == 0) {
			$.messager.alert('提示', '请选择删除的数据行', 'warning');
			return;
		}
		$.messager.confirm("提示", "确定要删除选中的记录?", function(t) {
			if (!t)
				return;
			var idArray = new Array();
			for (var i = 0; i < checkedRows.length; i++) {
				idArray.push(checkedRows[i].id);
			}
			var ids = idArray.join(CSIT.join);
			var content = {
				ids : ids
			};
			$.messager.progress();
			CSIT.asyncCallService(app.url.mulDelete(), content,
					function(result) {
						if (result.isSuccess) {
							$("#createList").datagrid('reload');
						} else {
							$.messager.alert('错误', result.message, "error");
						}
						$.messager.progress('close');
					});
		});
	},
	mulDeleteComment : function(comment) {
		$.messager.confirm("提示", "确定要删除选中的记录?", function(t) {
			if (!t)
				return;
			$.messager.progress();
			    var url=app.getUrl("mulDeleteComment");
			    var commentId ={commentId:comment};
			CSIT.asyncCallService(url, commentId,
					function(result) {
						if (result.isSuccess) {
							$("#CommentContent").html(' ');
							$("#CommentContent").val('');
							 var url=app.getUrl("userInfo");
							 var data ={data:1};
							 var userData = CSIT.syncCallService(url, data);
							var selectedRow = $(app.id.listId).datagrid('getSelected');
						    var url=app.getUrl("listTodoCommentByPage");
						    	var dataId=selectedRow.id;
						    	var data ={data:dataId};
						    	var commentData = CSIT.syncCallService(url, data);
						    	$('#viewCommentTable tbody').html('');
						    	if(commentData.length>0){
						    		var temp = "";
						    		for(var i=0; i<commentData.length; i++){
						    			var comment = commentData[i];
						    			temp += '<tr style="height: 25px">';
						    			temp += '<td class="commentName">'+(comment.name==undefined?'':comment.name)+'</td>';
						    			temp += '<td>'+(comment.commentTime==undefined?'':comment.commentTime)+'</td>';
						    			if(comment.commentUserId==userData.id){
						        			temp += '  <td><a href="javascript:void(0);" onclick="todo.updateComment('+comment.id+')">编辑</a>';
						        			temp += '      <a href="javascript:void(0);" onclick="todo.mulDeleteComment('+comment.id+')">删除</a></td>';
						        		}
						    			temp += '</tr>';
						    			temp += '<td colspan="2">'+(comment.comment==undefined?'':comment.comment)+'</td>';
						    			temp += '</tr>';
						    		}
						    		$('#viewCommentTable').append(temp);
						    	}
						} else {
							$.messager.alert('错误', result.message, "error");
						}
						$.messager.progress('close');
					});
		});
	},
	mulDeleteCommentCreate : function(comment) {
		$.messager.confirm("提示", "确定要删除选中的记录?", function(t) {
			if (!t)
				return;
			$.messager.progress();
			    var url=app.getUrl("mulDeleteComment");
			    var commentId ={commentId:comment};
			CSIT.asyncCallService(url, commentId,
					function(result) {
						if (result.isSuccess) {
							$("#CommentContentCreate").html(' ');
							$("#CommentContentCreate").val('');
							 var url=app.getUrl("userInfo");
							 var data ={data:1};
							 var userData = CSIT.syncCallService(url, data);
							var selectedRow = $("#createList").datagrid('getSelected');
						    var url=app.getUrl("listTodoCommentByPage");
						    	var dataId=selectedRow.id;
						    	var data ={data:dataId};
						    	var commentData = CSIT.syncCallService(url, data);
						    	$('#viewCommentTableCreate tbody').html('');
						    	if(commentData.length>0){
						    		var temp = "";
						    		for(var i=0; i<commentData.length; i++){
						    			var comment = commentData[i];
						    			temp += '<tr style="height: 25px">';
						    			temp += '<td class="commentName">'+(comment.name==undefined?'':comment.name)+'</td>';
						    			temp += '<td>'+(comment.commentTime==undefined?'':comment.commentTime)+'</td>';
						    			if(comment.commentUserId==userData.id){
						        			temp += '  <td><a href="javascript:void(0);" onclick="todo.updateCommentCreate('+comment.id+')">编辑</a>';
						        			temp += '      <a href="javascript:void(0);" onclick="todo.mulDeleteCommentCreate('+comment.id+')">删除</a></td>';
						        		}
						    			temp += '</tr>';
						    			temp += '<td colspan="2">'+(comment.comment==undefined?'':comment.comment)+'</td>';
						    			temp += '</tr>';
						    		}
						    		$('#viewCommentTableCreate').append(temp);
						    	}
						} else {
							$.messager.alert('错误', result.message, "error");
						}
						$.messager.progress('close');
					});
		});
	},
	mulDeleteCommentCopy : function(comment) {
		$.messager.confirm("提示", "确定要删除选中的记录?", function(t) {
			if (!t)
				return;
			$.messager.progress();
			    var url=app.getUrl("mulDeleteComment");
			    var commentId ={commentId:comment};
			CSIT.asyncCallService(url, commentId,
					function(result) {
						if (result.isSuccess) {
							$("#CommentContentCopy").html(' ');
							$("#CommentContentCopy").val('');
							 var url=app.getUrl("userInfo");
							 var data ={data:1};
							 var userData = CSIT.syncCallService(url, data);
							var selectedRow = $("#copyList").datagrid('getSelected');
						    var url=app.getUrl("listTodoCommentByPage");
						    	var dataId=selectedRow.id;
						    	var data ={data:dataId};
						    	var commentData = CSIT.syncCallService(url, data);
						    	$('#viewCommentTableCopy tbody').html('');
						    	if(commentData.length>0){
						    		var temp = "";
						    		for(var i=0; i<commentData.length; i++){
						    			var comment = commentData[i];
						    			temp += '<tr style="height: 25px">';
						    			temp += '<td class="commentName">'+(comment.name==undefined?'':comment.name)+'</td>';
						    			temp += '<td>'+(comment.commentTime==undefined?'':comment.commentTime)+'</td>';
						    			if(comment.commentUserId==userData.id){
						        			temp += '  <td><a href="javascript:void(0);" onclick="todo.updateCommentCopy('+comment.id+')">编辑</a>';
						        			temp += '      <a href="javascript:void(0);" onclick="todo.mulDeleteCommentCopy('+comment.id+')">删除</a></td>';
						        		}
						    			temp += '</tr>';
						    			temp += '<td colspan="2">'+(comment.comment==undefined?'':comment.comment)+'</td>';
						    			temp += '</tr>';
						    		}
						    		$('#viewCommentTableCopy').append(temp);
						    	}
						} else {
							$.messager.alert('错误', result.message, "error");
						}
						$.messager.progress('close');
					});
		});
	},
	search : function() {
		var isValid = $(app.id.searchFormId).form('validate');
		if (!isValid) {
			return false;
		}
		var content = $(app.id.searchFormId).serializeObject();
		$(app.id.listId).datagrid({
			url:app.getUrl("listTodoByPage"),
			queryParams : content
		});
	},
	searchFollow : function(){
		var isValid = $("#searchFollowForm").form('validate');
		if (!isValid) {
			return false;
		}
		var content = $("#searchFollowForm").serializeObject();
		$("#followPeopleList").datagrid({
			url:app.getUrl("getUserList"),
			queryParams : content
		});
	},
	searchtaskFollow  : function(){
		var isValid = $("#searchtaskFollowForm").form('validate');
		if (!isValid) {
			return false;
		}
		var content = $("#searchtaskFollowForm").serializeObject();
		$("#followPeopleTaskList").datagrid({
			url:app.getUrl("getUserList"),
			queryParams : content
		});
	},
	searchCreate : function(){
		var isValid = $("#searchCreateForm").form('validate');
		if(!isValid){
			return false;
		}
		var content = $("#searchCreateForm").serializeObject();
		$("#createList").datagrid({
			url:app.getUrl("listTodoCreateByPage"),
			queryParams : content
		});
	},
	searchCopy : function(){
		var isValid = $("#searchCopyForm").form('validate');
		if(!isValid){
			return false;
		}
		var content = $("#searchCopyForm").serializeObject();
		$("#copyList").datagrid({
			url:app.getUrl("listTodoCopyByPage"),
			queryParams : content
		});
	},
	 initKindEditorDialog:function(){
	    	if(detailEditor==null){
	    		detailEditor = KindEditor.create('#followDetails', {
					resizeType:0,
					//allowImageUpload: true, //上传图片框本地上传的功能，false为隐藏，默认为true
				   // allowImageRemote : false, //上传图片框网络图片的功能，false为隐藏，默认为true
					width:'100%',
					height:100,
//					cssPath:ctx+"/resources/css/img.css",
					items:[
					       	'source', '|', 'preview', 'undo', 'redo', 'cut', 'copy', 'paste',
					       	'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
					       	'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
					       	'superscript', 'clearhtml', 'quickformat', 
					       	'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
					       	'italic', 'underline', 'lineheight', 'removeformat',
					       	'table', 'link', 'unlink', 'fullscreen'
				    ],
				   // uploadJson : ctx+'/sys/meeting/uploadImgKindeditor.jhtml'
				});
			}
	    },
	init : function(params) {
		
		todo.initEditDialog();
		todo.initCRUDBtn();
		todo.initEditCommentDialog();
		todo.initCustomerEditDialog();
		todo.initOwnerEditDialog();
		todo.initCareEditDialog();
		todo.initviewDialog();      
		todo.initviewCreateDialog();
		todo.initviewCopyDialog();
		todo.initEditComplectDialog();
		todo.initKindEditorDialog();
		todo.initFollowPeopleDialog();
		todo.initEditNotComplectDialog();
		todo.initEditCancelDialog();
		todo.initEditComplectExplainDialog();
		todo.initEditCommentContentDialog();
		todo.initViewList();
		todo.initViewCreateList();
		todo.initViewCopyList();
		todo.initReceiverEditDialog();
		todo.initTaskreceiverEditDialog();
		todo.initfollowPersonnelEditDialog();
        todo.inittaskfollowPersonnelEditDialog();
        todoAttach.init();
        followAttach.init();
		    $("#taskType").combobox('clear');
		    $("#createTaskType").combobox('clear');
		    $("#copyTaskType").combobox('clear');
		    app.remind($("#remindUnit"));
		    app.remind($("#taskRemindUnit"));
		    $("#today").click(function(){
		    	var today=0;
		    	$("#list").datagrid({
					url:app.getUrl("listTodoByPage"),
					queryParams : {doUserTime:today}
				});
		    });
		    $("#tomorrow").click(function(){
		    	var today=1;
		    	$("#list").datagrid({
					url:app.getUrl("listTodoByPage"),
					queryParams : {doUserTime:today}
				});
		    });
		    $("#after").click(function(){
		    	todo.search();
		    });
		    app.followTypeCombobox($("#followType"));
			app.followStageCombobox($("#followStage"));
			app.customerStatusCombobox($("#customerStatus"));
			app.remind($("#remind"));
			$("#taskTypeOne").combobox({
			        panelHeight: 'auto',
			        editable:false,
			        valueField: 'value',
			        textField: 'text',
			        data: [
			            {value: '1',text: '客户跟进'},
			            {value: '2',text: '日常任务'},
			            {value: '3',text: '客户投诉'},
			            {value: '4',text: '客户关怀'}
			        ]
			 });
	}
}

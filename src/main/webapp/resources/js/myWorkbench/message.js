var message = {
    init: function (params) {
    	app.messageStatusCombobox('#status');
    	message.initBtn();
        message.initViewList();
    },
    initViewList:function(){
    	$("#list").datagrid({
    		url:ctx + "/myWorkbench/message/listByPage.jhtml",
    		pagination:true,
    		fit:true,
    		border:false,
    		toolbar: app.id.toolbarId,
    		ctrlSelect:true,
    		rownumbers:true,
    		pageSize:20,
    		pageList:[10,20,30,40,50,100],
    		columns:[[
 		        {field:'ck',checkbox:true},
 		        {field:'status',title:'状态',width:100,align:'center',
 	 				formatter: function(value,row,index){
 	 					if(value == 1){
 	 						return '已读';
 	 					}else{
 	 						return '未读';
 	 					}
 	 				}
 	 			},
 	 			{field:'remindTime',title:'提醒时间',width:160,align:'center'},
				{field:'msgContent',title:'信息内容',width:450,align:'center',
     	 				formatter:function(value,row,index){
     	 					if(row.status == 1){
     	 						return '<font color="#AAAAAA" style="text-decoration:none;">'+value+'</font>';
     	 					}else{
     	 						return '<font color="#000000" style="font-weight:bold; text-decoration:none;">'+value+'</font>';
     	 					}
     	 				}
     	 			},
				{field:'id',hidden:true}
     		]]
    	});
    },
    initBtn:function(){
    	//查询
    	$(app.id.searchBtnId).click(function(){
    		message.search();
    	});
    	//清空查询条件
    	$(app.id.clearBtnId).click(function(){
    		$(app.id.searchFormId).form('clear');
    	});
    	//设置已读
    	$("#readBtn").click(function(){
    		message.read();
    	});
    	$("#deleteBtn").click(function(){
    		message.mulDelete();
    	});
    },
    search:function(){
    	var isValid = $(app.id.searchFormId).form('validate');
		if (!isValid){
			return false;
		}
    	var content = $(app.id.searchFormId).serializeObject();
    	$(app.id.listId).datagrid({
    		queryParams:content
    	});
    },
    mulDelete:function(){
    	var checkedRows = $("#list").datagrid('getChecked');
    	if(checkedRows.length==0){
    		$.messager.alert('提示','请选择删除的数据行','warning');
    		return;
    	}
    	$.messager.confirm("提示","确定要删除选中数据?",function(t){ 
			if(!t) return;
			var idArray = new Array();
			for(var i=0;i<checkedRows.length;i++){
				idArray.push(checkedRows[i].id);
			}
			var ids = idArray.join(CSIT.join);
			var content = {ids:ids};
			$.messager.progress();
			CSIT.asyncCallService(ctx + "/myWorkbench/message/mulDelete.jhtml",content,function(result){
				if(result.isSuccess){
					$("#list").datagrid('reload');
				}else{
					$.messager.alert('错误',result.message,"error");
				}
				$.messager.progress('close');
			});
    	});
    },
    //设为已读
    read:function(){
    	var checkedRows = $("#list").datagrid('getChecked');
    	if(checkedRows.length==0){
    		$.messager.alert('提示','请选择设置已读的数据行','warning');
    		return;
    	}
    	$.messager.confirm("提示","确定要将选中的记录设置为已读?",function(t){ 
    		if(!t) return;
    		var idArray = new Array();
			for(var i=0;i<checkedRows.length;i++){
				idArray.push(checkedRows[i].id);
			}
			var ids = idArray.join(CSIT.join);
			var content = {ids:ids};
			$.messager.progress();
			CSIT.asyncCallService(ctx + "/myWorkbench/message/readSetUp.jhtml",content,function(result){
				if(result.isSuccess){
					$("#list").datagrid('reload');
				}else{
					$.messager.alert('错误',result.message,"error");
				}
				$.messager.progress('close');
			});
    	});
    }
}

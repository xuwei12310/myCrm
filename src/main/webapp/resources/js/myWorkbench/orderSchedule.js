var orderSchedule = {
    init: function (params) {
    	app.yesOrNoByIntCombobox("#isComplete");
    	app.initCombobox("#productType",ctx + "/common/util/getDictByType.jhtml",{type:"product"});
    	var result = CSIT.syncCallService(ctx + "/common/util/getDictByType.jhtml",{type:"product"});
    	$('#productType').combobox('setValue',result[0].id);
    	app.crud.initEditDialog({});
    	orderSchedule.initBtn();
        orderSchedule.initViewList();
        orderSchedule.initSearch();
        orderSchedule.initCompleteEditDialog();
    },
    initBtn:function(){
    	//查询
    	$(app.id.searchBtnId).click(function(){
    		orderSchedule.search();
    	});
    	//清空查询条件
    	$(app.id.clearBtnId).click(function(){
    		$(app.id.searchFormId).form('clear');
    		app.initCombobox("#productType",ctx + "/common/util/getDictByType.jhtml",{type:"product"});
        	var result = CSIT.syncCallService(ctx + "/common/util/getDictByType.jhtml",{type:"product"});
        	$('#productType').combobox('setValue',result[0].id);
    	});
    },
	initViewList:function(){
		$("#list").datagrid({
			pagination:true,
			fit:true,
			border:false,
			toolbar: app.id.toolbarId,
			ctrlSelect:true,
			rownumbers:true,
			pageSize:20,
			pageList:[10,20,30,40,50,100],
			frozenColumns :[[  
					{field:'orderCode',title:'订单编号',width:100,align:'center'},
					{field:'customerName',title:'客户名称',width:90,align:'center'},
					{field:'productName',title:'产品名称',width:70,align:'center'},
					{field:'loanAmount',title:'贷款金额',width:70,align:'center'},
					{field:'loanMoney',title:'实际放款',width:70,align:'center'},
					{field:'signingDate',title:'签约日期',width:100,align:'center'},
					{field:'ownerName',title:'拥有人',width:100,align:'center'},
					{field:'id',hidden:true}, 
			]],
			onClickCell: function(index,field,value){
    	    	var data = $("#productType").val();
    			var result = CSIT.syncCallService(ctx + "/myWorkbench/orderSchedule/getSchedule.jhtml",{productType:data});
    			for(var i=0;i<result.data.scheduleList.length;i++){
    				if(result.data.scheduleList[i]==field){
    					var rows = $("#list").datagrid('getRows');
    	    	    	var selectedRow = rows[index];
    	    	    	var url=ctx + "/myWorkbench/orderSchedule/getOrderScheduleInfo.jhtml";
    					var data = {orderid:selectedRow.id,scheduleDate:field};
    					CSIT.asyncCallService(url, data,function(result){
    						$("#completeEditDialog").dialog("open");
    						$("#completeEditForm").form('load',result.data.orderSchedule);
    						orderSchedule.initCompleteEditForm();
    						$("#completeEditForm").attr('action',ctx + "/myWorkbench/orderSchedule/update.jhtml");
    					});
    				}
    			}
			},
		})
	},
	initSearch:function(){
		var data = $("#productType").val();
		var result = CSIT.syncCallService(ctx + "/myWorkbench/orderSchedule/getColumns.jhtml",{productType:data});
		var columnse = eval('('+result.data.columns+')');
		var content = $("#searchForm").serializeObject();	
    	$("#list").datagrid({
    		url:ctx + "/myWorkbench/orderSchedule/listOrderScheduleByPage.jhtml",
    		queryParams:content,
    		columns:columnse,
    	});
    },
    
    initCompleteEditDialog:function(){
		var params = {
	    		title:'完成设置',
	    		width : 380,
				height : 200,
				closed : true,
				cache : false,
				modal : true,
				buttons:[{
					text:'保存',
					iconCls:'icon-save',
					handler:function(){
						$("#completeEditForm").submit();
					}
				},{
					text:'退出',
					iconCls:'icon-exit',
					handler:function(){
						$("#completeEditDialog").dialog('close');
					}
				}],
				onClose:function(){
					$("#completeEditDialog").form('clear');
				}
			};
	    	$("#completeEditDialog").dialog(params);
	},
	
	initCompleteEditForm:function(ext){
    	$("#completeEditForm").form({  
    		onSubmit: function(){ 
    			$.messager.progress();
    			var isValid = $(this).form('validate');
    			if(ext){
    				isValid = ext();
    			}
    			if (!isValid){
    				$.messager.progress('close');
    			}
    			return isValid;
    		}, 
    		success:function(data){  
    			$.messager.progress('close');
    			var data = eval('(' + data + ')');  
    			if (data.isSuccess){
    				$.messager.alert('提示','保存成功','info',function(){
    					$("#completeEditDialog").dialog('close');
    					orderSchedule.initSearch()
    				});
    			}else{
    				$.messager.alert('提示',data.message,'error');
    			}
    		}
    	});
    },
    search:function(){
    	var isValid = $(app.id.searchFormId).form('validate');
		if (!isValid){
			return false;
		}
		var data = $("#productType").val();
		var result = CSIT.syncCallService(ctx + "/myWorkbench/orderSchedule/getColumns.jhtml",{productType:data});
		var columnse = eval('('+result.data.columns+')');
		var content = $("#searchForm").serializeObject();	
    	$("#list").datagrid({
    		url:ctx + "/myWorkbench/orderSchedule/listOrderScheduleByPage.jhtml",
    		queryParams:content,
    		columns:columnse,
    	});
    }
};

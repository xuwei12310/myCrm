var brokerageDistribute = {
    init: function (params) {
    	app.brokerageGrantStateCombobox("#brokerageGrantState");
    	brokerageDistribute.initBtn();
    	brokerageDistribute.initEditDialog();
    	brokerageDistribute.initBDEditDialog();
    	brokerageDistribute.initBalanceEditDialog();
    	brokerageDistribute.initModBDEditDialog();
    },
    initEditDialog:function(){
    	$("#list").datagrid({
			url:ctx + "/financial/brokerageDistribute/listByPage.jhtml",
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
					{field:'orderCode',title:'订单合同',width:100,align:'center'},
					{field:'signingDate',title:'签约时间',width:100,align:'center'},
					{field:'customerName',title:'客户',width:100,align:'center'},
					{field:'ownerName',title:'拥有人',width:100,align:'center'},
					{field:'companyLoanAmount',title:'贷款金额',width:100,align:'center'},
					{field:'loanMoney',title:'放款金额',width:100,align:'center'},
					{field:'loanDate',title:'放款时间',width:100,align:'center'},
					{field:'brokerageTotal',title:'佣金总额',width:100,align:'center'},
					{field:'brokerageAuditStatus',title:'审核状态',width:120,align:'center'
						,formatter: function(value,row,index){
							switch(value){
					            case "1":return "草稿";
					            case "2":return "审核中";
					            case "3":return "审核通过";
					            case "4":return "审核不通过";
					            case "5":return "放弃";
					        }
						}
                    },
					{field:'brokerageGrantState',title:'发放状态',width:120,align:'center',
						formatter: function(value,row,index){
	                        if(value == 1){
	                            return "已发放";
	                        }else{
	                            return "未发放";
	                        }
	                    },
                    },
					{field:'oid',hidden:true}
			]],
			onDblClickCell: function(index,field,value){
				brokerageDistribute.update();
		   	},
		})
    },
    initBDEditDialog:function(){
    	$('#updateBDTabs').tabs({
    		border:false,
    	    onSelect:function(title,index){
    	    	if("结算信息"==title){
    	    		var orderId = $("#idUpdate").val();
    	    		$("#balanceList").datagrid({
    	    			url:ctx + "/financial/brokerageDistribute/listBrokerageByPage.jhtml",
    	    			queryParams: {
    	    				"orderId": orderId
    	    			}
    	    		});
    	    	}
    	    }
    	});
		var params = {
		    	title:'编辑佣金结算信息',
		    	width : document.documentElement.clientWidth-5<700?document.documentElement.clientWidth-5:700,
				height : document.documentElement.clientHeight-5<500?document.documentElement.clientHeight-5:500,
				closed : true,
				cache : false,
				modal : true,
		};
		$("#BDEditDialog").dialog(params);
    },
    
    initBalanceEditDialog:function(){
    	$("#balanceList").datagrid({
    		fit:true,
    		border:false,
    		rownumbers:true,
    		singleSelect:true,
    		toolbar: [
    		    {iconCls: 'icon-edit',text:'修改',handler: function(){brokerageDistribute.updateBalance();}},
    		],
       	 	columns:[[
					{field:'ownerType',title:'订单角色',width:120,align:'center',formatter: function(value,row,index){
                        if(value == 1){
                            return "拥有人";
                        }else if(value == 2){
                            return "跟单人";
                        }else if(value == 3){
                            return "客服负责人";
                        }else if(value == 4){
                            return "客服助理";
                        }},},
					{field:'ownerName',title:'姓名',width:100,align:'center'},
					{field:'reference',title:'参考值',width:100,align:'center'},
					{field:'actual',title:'实际值',width:100,align:'center'},
					{field:'note',title:'备注',width:100,align:'center'},
					{field:'bid',hidden:true}
       		]],
    	});
    },
    initBtn:function(){
    	//编辑
    	$("#updateBtn").click(function(){
    		brokerageDistribute.update();
    	});
    	//送审
    	$("#verifyBtn").click(function(){
    		brokerageDistribute.verify();
    	});
    	//跟踪
    	$("#traceBtn").click(function(){
    		brokerageDistribute.trace();
    	});
    	//查询
    	$(app.id.searchBtnId).click(function(){
    		brokerageDistribute.search();
    	});
    	//清空查询条件
    	$(app.id.clearBtnId).click(function(){
    		$(app.id.searchFormId).form('clear');
    	});
    	$("#grantType").menu({
   			onClick : function(payStatus) {
   				if(payStatus.text=="已发放"){
   					brokerageDistribute.modGrantStatus(1);
   				}else{
   					brokerageDistribute.modGrantStatus(0);
   				}
   				
   			}
   		});
    },
    trace:function(){
    	var checkedRows = $(app.id.listId).datagrid('getChecked');
        if(checkedRows.length==0){
            $.messager.alert('提示','请选择跟踪的数据行','warning');
            return;
        }
        if(checkedRows[0].brokerageAuditStatus==2||checkedRows[0].brokerageAuditStatus==4){
            window.open(ctx+"/financial/brokerageDistribute/trace.jhtml?id="+checkedRows[0].oid);
        }else {
            $.messager.alert('提示','只有审核中和审核不通过的才能跟踪','warning');
        }
    },
    updateBalance:function(){
    	var selectedRow = $("#balanceList").datagrid('getSelected');
		if(selectedRow==null){
    		$.messager.alert('提示','请选择数据行','warning');
    		return;
    	}
		var btype = $('#btype').val();
		if(btype == 3){
			$.messager.alert('提示','已审核通过，无法进行修改','warning');
    		return;
		}
		$("#modBDEditDialog").dialog('open');
		if(selectedRow.ownerType==1){
			$('#ownerTypeName').text("拥有人");
		}else if(selectedRow.ownerType==2){
			$('#ownerTypeName').text("跟单人");
		}else if(selectedRow.ownerType==3){
			$('#ownerTypeName').text("客服负责人");
		}else if(selectedRow.ownerType==4){
			$('#ownerTypeName').text("客服助理");
		}
		$('#ownerNames').text(selectedRow.ownerName);
		$('#brokerageId').val(selectedRow.bid);
		$("#modBDForm").form('load',selectedRow);
		brokerageDistribute.initModBDForm();
		$("#modBDForm").attr('action',ctx + "/financial/brokerageDistribute/update.jhtml");
    },
    //修改发放状态
    modGrantStatus:function(index){
    	var checkedRows = $(app.id.listId).datagrid('getChecked');
    	if(checkedRows.length==0){
    		$.messager.alert('提示','请选择修改状态的数据行','warning');
    		return;
    	}
    	$.messager.confirm("提示","确定要修改选中记录的状态?",function(t){ 
			if(!t) return;
			var idArray = new Array();
			for(var i=0;i<checkedRows.length;i++){
				idArray.push(checkedRows[i].oid);
			}
			var ids = idArray.join(CSIT.join);
			var content = {ids:ids,param:index};
			$.messager.progress();
			CSIT.asyncCallService(ctx + "/financial/brokerageDistribute/modGrantStatus.jhtml",content,function(result){
				if(result.isSuccess){
					$(app.id.listId).datagrid('reload');
				}else{
					$.messager.alert('错误',result.message,"error");
				}
				$.messager.progress('close');
			});
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
    update:function(){
    	var selectedRow = $(app.id.listId).datagrid('getSelected');
		if(selectedRow==null){
    		$.messager.alert('提示','请选择数据行','warning');
    		return;
    	}
		$("#BDEditDialog").dialog('open');
		$('#updateBDTabs').tabs({
    		selected:0
    	});
		$('#orderCodeBD').text(selectedRow.orderCode);
		$('#signingDateBD').text(selectedRow.signingDate);
		$('#customerNameBD').text(selectedRow.customerName);
		$('#receiveAmountBD').text(selectedRow.receiveAmount);
		$('#loanMoneyBD').text(selectedRow.loanMoney);
		$('#idUpdate').val(selectedRow.oid);
		$('#btype').val(selectedRow.brokerageAuditStatus);
    },
    initModBDEditDialog:function(){
    	var params = {
	    		title:'编辑佣金分配',
	    		width : 380,
				height : 330,
				closed : true,
				cache : false,
				modal : true,
				buttons:[{
					text:'保存',
					iconCls:'icon-save',
					handler:function(){
						$("#modBDForm").submit();
					}
				},{
					text:'退出',
					iconCls:'icon-exit',
					handler:function(){
						$("#modBDEditDialog").dialog('close');
					}
				}],
				onClose:function(){
					$("#modBDEditDialog").form('clear');
				}
			};
	    	$("#modBDEditDialog").dialog(params);
    },
    initModBDForm:function(ext){
    	$("#modBDForm").form({  
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
    					$("#modBDEditDialog").dialog('close');
    					$("#balanceList").datagrid('reload');
    					$("#list").datagrid('reload');
    				});
    			}else{
    				$.messager.alert('提示',data.message,'error');
    			}
    		}
    	});
    },
    //送审
	verify : function() {
		var checkedRows = $(app.id.listId).datagrid('getChecked');
		if (checkedRows.length == 0) {
			$.messager.alert('提示', '请选择审核的数据行', 'warning');
			return;
		}
		$.messager.confirm("提示", "确定要审核选中的记录?", function(t) {
			if (!t)
				return;
			var idArray = new Array();
			for (var i = 0; i < checkedRows.length; i++) {
				idArray.push(checkedRows[i].oid);
			}
			var ids = idArray.join(CSIT.join);
			var content = {
				ids : ids
			};
			$.messager.progress();
			CSIT.asyncCallService(ctx + "/financial/brokerageDistribute/verify.jhtml", content,
				function(result) {
					console.log(result)
					if (result.isSuccess) {
						$(app.id.listId).datagrid('reload');
					} else {
						$.messager.alert('错误', result.message, "error");
					}
					$.messager.progress('close');
				});
		});
	},
}

var brokerage_audit_edit = {
	init : function(params) {
		brokerage_audit_edit.initCRUDBtn();
		brokerage_audit_edit.initInfo();
		brokerage_audit_edit.initModBDEditDialog();
	},
	initCRUDBtn : function() {
		//处理按钮
        $(".approvalBtn").click(function () {
            var param = {};
            param["taskId"]=taskId;
            param["transition"]=$.trim($(this).text());
            param["comment"]=$("#comment").val();
            handleTask(param);
        });
        function handleTask(param) {
            $.messager.progress();
            CSIT.asyncCallService(ctx + "/myWorkbench/approval/handleTask.jhtml",param,function(result){
                if(result.isSuccess){
                    $.messager.alert('提示',"审批成功","info",function () {
                        window.parent.approval.closeDialogiframe(1);
                    });
                }else {
                    $.messager.alert('错误',result.message,"error");
                }
                $.messager.progress('close');
            });
        };
		//选项卡
		$("#infoTabs").tabs({
			border:false,
			onSelect:function(title,index){
				if("结算信息"==title){
					$("#balanceList").datagrid({
    	        		fit:true,
    	        		border:false,
    	        		url:ctx + "/financial/brokerageDistribute/listApprovalBrokerageByPage.jhtml",
    	        		queryParams: {
    	    				"orderId": orderId
    	    			},
    	    			toolbar: [
    		    		    {iconCls: 'icon-edit',text:'修改',handler: function(){brokerage_audit_edit.updateBalance();}},
    		    		],
    	        		rownumbers:true,
    	        		singleSelect:true,
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
    	    	}else if(title=="审批"){
					 //审批历史
			        $('#approvalList').datagrid({
			            border:true,
			            url:ctx + "/myWorkbench/approval/commentlistByPage.jhtml",
			            pagination:true,
			            fit:true,
			            ctrlSelect:true,
			            method:"POST",
			            queryParams:{id:processInstanceId},
			            columns:[[
			                {field:'taskName',title:'任务',width:100,align:'center'},
			                {field:'action',title:'动作',width:100,align:'center'},
			                {field:'userName',title:'操作人',width:100,align:'center'},
			                {field:'time',title:'操作时间',width:100,align:'center'},
			                {field:'msg',title:'批注',width:100,align:'center'},
			                {field:'id',hidden:true},
			                {field:'taskId',hidden:true}
			            ]]
			        });
				}
			}
		});
	},
	updateBalance:function(){
    	var selectedRow = $("#balanceList").datagrid('getSelected');
		if(selectedRow==null){
    		$.messager.alert('提示','请选择数据行','warning');
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
		brokerage_audit_edit.initModBDForm();
		$("#modBDForm").attr('action',ctx + "/financial/brokerageDistribute/update.jhtml");
    },
	initInfo: function() {
		var url=ctx + "/financial/brokerageDistribute/getOrderBrokerageById.jhtml";
		var param={"orderId":orderId};
		var data=CSIT.syncCallService(url,param);
		$('#infoTabs').tabs({
    		selected:0
    	});
		$('#orderCodeBD').text(data.orderCode);
		$('#signingDateBD').text(data.signingDate);
		$('#customerNameBD').text(data.customerName);
		$('#receiveAmountBD').text(data.receiveAmount);
		$('#loanMoneyBD').text(data.loanMoney);
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
    				});
    			}else{
    				$.messager.alert('提示',data.message,'error');
    			}
    		}
    	});
    },
}
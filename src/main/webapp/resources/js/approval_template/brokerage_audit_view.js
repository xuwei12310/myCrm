var brokerage_audit_view = {
	init : function(params) {
		brokerage_audit_view.initCRUDBtn();
		brokerage_audit_view.initInfo();
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
    	        		rownumbers:true,
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
	}
}
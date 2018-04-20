var balance_approval_view = {

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
		if("基本信息"==title){
					
				}else if("借款"==title){
					$("#loanList").datagrid({
						url : ctx+"/financial/balance/listLoanByPage?orderId="+orderId,
						pagination : true,
						fit : true,
						singleSelect:true,
						border : false,
						columns:[[
						          {field:'amount',title:'借款金额',width:80,align:'center'},
						          {field:'rate',title:'借款利率',width:70,align:'center'},
						          {field:'beginDate',title:'借款日期',width:110,align:'center'},
						          {field:'estimateRepayDate',title:'预计还款日期',width:110,align:'center'},
						          {field:'estimateInterest',title:'预计利息',width:80,align:'center'},
						          {field:'repayDate',title:'实际还款日期',width:110,align:'center'},
						          {field:'loanDay',title:'实际借款天数',width:80,align:'center'},
						          {field:'referInterest',title:'参考利息',width:80,align:'center'},
						          {field:'interest',title:'实际利息',width:80,align:'center'},
						          {field:'receiveAmount',title:'应收金额',width:80,align:'center'},
						          {field:'receivedAmount',title:'已收金额',width:80,align:'center'},
						          {field:'receivingAmount',title:'待收金额',width:80,align:'center'},
						]],
						ctrlSelect : true,
						rownumbers : true,
						pageSize : 10,
						pageList : [ 10, 20, 30, 40, 50, 100 ],
						onDblClickRow : function() {
							$("#updateLoan").click();
						}
					});
				}else if("收入项"==title){
					$("#incomeList").datagrid({
						url : ctx+"/financial/balance/listOrderIncomeByPage?orderId="+orderId,
						pagination : true,
						fit : true,
						singleSelect:true,
						border : false,
						columns:[[
						          {field:'costTypeName',title:'收入项',width:80,align:'center'},
						          {field:'estimateAmount',title:'参考金额',width:70,align:'center'},
						          {field:'receiveAmount',title:'实际金额',width:110,align:'center'},
						          {field:'receivedAmount',title:'已收金额',width:110,align:'center'},
						          {field:'receivingAmount',title:'待收金额',width:80,align:'center'},
						          {field:'note',title:'备注',width:110,align:'center'},
						]],
						ctrlSelect : true,
						rownumbers : true,
						pageSize : 10,
						pageList : [ 10, 20, 30, 40, 50, 100 ],
						onDblClickRow : function() {
							$("#updateIncome").click();
						}
					});
				}else if("支出项"==title){
					$("#payList").datagrid({
						url : ctx+"/financial/balance/listPayByPage?orderId="+orderId,
						pagination : true,
						fit : true,
						singleSelect:true,
						border : false,
						columns:[[
						          {field:'costTypeName',title:'支出项',width:80,align:'center'},
						          {field:'payObjectType',title:'支出对象类型',width:70,align:'center'},
						          {field:'estimatePayAmount',title:'参考金额',width:110,align:'center'},
						          {field:'payAmount',title:'实际金额',width:110,align:'center'},
						          {field:'receiveAccountName',title:'收款账户名',width:80,align:'center'},
						          {field:'receiveAccountBank',title:'收款账户开户行',width:80,align:'center'},
						          {field:'receiveAccountNumber',title:'收款账号',width:80,align:'center'},
						          {field:'payBankAccount',title:'付款账户',width:80,align:'center'},
						          {field:'payTime',title:'支出日期',width:80,align:'center'},
						          {field:'note',title:'备注',width:110,align:'center'},
						]],
						ctrlSelect : true,
						rownumbers : true,
						pageSize : 10,
						pageList : [ 10, 20, 30, 40, 50, 100 ],
						onDblClickRow : function() {
							$("#updatePay").click();
						}
					});
				}
				else if(title=="审批"){
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
	initInfo:function(){
		var url=ctx+"/financial/balance/getBalanceById.jhtml";
		var param={"id":orderId};
		var data=CSIT.syncCallService(url,param)
		$("#editForm").form('load',data[0]);
	},
	init : function(params) {
		balance_approval_view.initCRUDBtn();
		balance_approval_view.initInfo();
	}

}

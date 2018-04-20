var balance_approval_edit = {

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
        //编辑借款
		$("#updateLoan").click(function(){
			balance_approval_edit.updateLoan();
		});
		//编辑收入
		$("#updateIncome").click(function(){
			balance_approval_edit.updateIncome();
		});
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
						toolbar : "#tb2",
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
						toolbar : "#tb3",
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
						toolbar : "#tb4",
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
	initEditDialog : function() {
		var params = {
			title : '编辑' + resourceName,
			width : 430,
			height : document.documentElement.clientHeight-5<550?document.documentElement.clientHeight-5:550,
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
				$(app.id.editDialogId).form('clear');
			}
		};
		$(app.id.editDialogId).dialog(params);
	},
	initEditLoanDialog : function() {
		var params = {
			title : '编辑借款',
			width : 430,
			height : document.documentElement.clientHeight-5<200?document.documentElement.clientHeight-5:200,
			closed : true,
			cache : false,
			buttons : [ {
				text : '保存',
				iconCls : 'icon-save',
				handler : function() {
					$("#editLoanForm").submit();
				}
			}, {
				text : '退出',
				iconCls : 'icon-exit',
				handler : function() {
					$("#editLoanDialog").dialog('close');
				}
			} ],
			modal : true,
			onClose : function() {
				$("#editLoanDialog").form('clear');
			}
		};
		$("#editLoanDialog").dialog(params);
	},
	initEditIncomeDialog : function() {
		var params = {
			title : '编辑收入',
			width : 430,
			height : document.documentElement.clientHeight-5<200?document.documentElement.clientHeight-5:200,
			closed : true,
			cache : false,
			buttons : [ {
				text : '保存',
				iconCls : 'icon-save',
				handler : function() {
					$("#editIncomeForm").submit();
				}
			}, {
				text : '退出',
				iconCls : 'icon-exit',
				handler : function() {
					$("#editIncomeDialog").dialog('close');
				}
			} ],
			modal : true,
			onClose : function() {
				$("#editIncomeDialog").form('clear');
			}
		};
		$("#editIncomeDialog").dialog(params);
	},
	initEditPayDialog : function() {
		var params = {
			title : '编辑支出',
			width : 430,
			height : document.documentElement.clientHeight-5<350?document.documentElement.clientHeight-5:350,
			closed : true,
			cache : false,
			buttons : [ {
				text : '保存',
				iconCls : 'icon-save',
				handler : function() {
					$("#editPayForm").submit();
				}
			}, {
				text : '退出',
				iconCls : 'icon-exit',
				handler : function() {
					$("#editPayDialog").dialog('close');
				}
			} ],
			modal : true,
			onClose : function() {
				$("#editPayDialog").form('clear');
			}
		};
		$("#editPayDialog").dialog(params);
	},
	initEditLoanForm : function() {
		$("#editLoanForm").form({
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
						$("#editLoanDialog").dialog('close');
						$("#loanList").datagrid('reload');
					});
				} else {
					$.messager.alert('提示', data.message, 'error');
				}
			}
		});
	},
	initEditIncomeForm : function() {
		$("#editIncomeForm").form({
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
						$("#editIncomeDialog").dialog('close');
						$("#incomeList").datagrid('reload');
					});
				} else {
					$.messager.alert('提示', data.message, 'error');
				}
			}
		});
	},
	initEditPayForm : function() {
		$("#editPayForm").form({
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
						$("#editPayDialog").dialog('close');
						$("#payList").datagrid('reload');
					});
				} else {
					$.messager.alert('提示', data.message, 'error');
				}
			}
		});
	},
	updateLoan : function() {
		var selectedRow = $("#loanList").datagrid('getSelected');
		if (selectedRow == null) {
			$.messager.alert('提示', '请选择数据行', 'warning');
			return;
		}
		if (selectedRow.isSys == 1) {
			$.messager.alert('提示', '系统内置不能修改', 'warning');
			return;
		}
		$("#editLoanDialog").dialog('open');
		balance_approval_edit.initEditLoanForm();
		$("#orderLoanId").val(selectedRow.id);
		$("#editLoanForm").form('load', selectedRow);
		$("#editLoanForm").attr('action',ctx+"/financial/balance/updateLoan.jhtml");
	},
	updateIncome : function() {
		var selectedRow = $("#incomeList").datagrid('getSelected');
		if (selectedRow == null) {
			$.messager.alert('提示', '请选择数据行', 'warning');
			return;
		}
		if (selectedRow.isSys == 1) {
			$.messager.alert('提示', '系统内置不能修改', 'warning');
			return;
		}
		$("#editIncomeDialog").dialog('open');
		balance_approval_edit.initEditIncomeForm();
		$("#orderIncomeId").val(selectedRow.id);
		$("#orderId2").val(orderId);
		$("#editIncomeForm").form('load', selectedRow);
		$("#editIncomeForm").attr('action', ctx+"/financial/balance/updateIncome.jhtml");
	},
	updatePay : function() {
		var selectedRow = $("#payList").datagrid('getSelected');
		if (selectedRow == null) {
			$.messager.alert('提示', '请选择数据行', 'warning');
			return;
		}
		if (selectedRow.isSys == 1) {
			$.messager.alert('提示', '系统内置不能修改', 'warning');
			return;
		}
		$("#editPayDialog").dialog('open');
		balance_approval_edit.initEditPayForm();
		$("#orderPayId").val(selectedRow.id);
		$("#editPayForm").form('load', selectedRow);
		$("#editPayForm").attr('action',ctx+"/financial/balance/updatePay.jhtml");
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
		balance_approval_edit.initEditDialog();
		balance_approval_edit.initCRUDBtn();
		balance_approval_edit.initEditLoanDialog();
		balance_approval_edit.initEditIncomeDialog();
		balance_approval_edit.initEditPayDialog();
		balance_approval_edit.initInfo();
	}

}

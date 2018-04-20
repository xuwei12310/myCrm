var balance = {

	initCRUDBtn : function() {
		// 添加
		$(app.id.addBtnId).click(function() {
			balance.add();
		});
		// 修改
		$(app.id.updateBtnId).click(function() {
			balance.update();
		});
		// 删除
		$(app.id.deleteBtnId).click(function() {
			balance.mulDelete();
		});
		// 查询
		$(app.id.searchBtnId).click(function() {
			balance.search();
		});
		$("#verifyBtn").click(function(){
			balance.verify();
		});
		// 清空查询条件
		$(app.id.clearBtnId).click(function() {
			$(app.id.searchFormId).form('clear');
		});
		//跟踪
        $("#traceBtn").click(function () {
            var checkedRows = $(app.id.listId).datagrid('getSelected');
            if(checkedRows==null){
                $.messager.alert('提示','请选择跟踪的数据行','warning');
                return;
            }
            if(checkedRows.settlementAuditStatus==2||checkedRows.settlementAuditStatus==4){
                window.open(ctx+"/financial/balance/trace.jhtml?id="+checkedRows.id);
            }else {
                $.messager.alert('提示','只有审核中和审核不通过的才能跟踪','warning');
            }
        });
		//编辑借款
		$("#updateLoan").click(function(){
			balance.updateLoan();
		});
		//编辑收入
		$("#updateIncome").click(function(){
			balance.updateIncome();
		});
		//编辑支出
		$("#updatePay").click(function(){
			balance.updatePay();
		});
		$('input',$("#interest").next('span')).blur(function(){
			var selectedRow=$("#loanList").datagrid("getSelected");
			var interest=$("#interest").textbox("getValue");
			interest=balance.accDiv(interest,10000);
			var receiveAmount=balance.accAdd(interest,selectedRow.amount);
			$("#receiveAmount").textbox("setValue",receiveAmount);
		});
		//支出其他项选择其他合作伙伴
		$("#others").textbox({
			onClickIcon:function(){
				$("#otherList").datagrid({
					url:"myCrm1/dict/otherPartners/listByPage.jhtml",
					queryParams:{"status":1},
					pagination:true,
		    		fit:true,
		    		border:false,
		    		toolbar: "#tb5",
		    		singleSelect:true,
		    		rownumbers:true,
		    		pageSize:10,
		    		pageList:[10,20,30,40,50,100],
		    		columns : [ [ 
					{
						field : 'name',
						title : '名称',
						width : 100,
						align : 'center'
					},{
						field : 'address',
						title : '地址',
						width : 200,
						align : 'center'
					},{
						field : 'telephone',
						title : '联系电话',
						width : 100,
						align : 'center'
					},{
						field : 'note',
						title : '备注',
						width : 100,
						align : 'center'
					}] ]
				});
				$("#otherDialog").dialog('open')
			}
		});
		$("#selectOhters").click(function(){
			var selectedRow=$("#otherList").datagrid("getSelected");
			$("#others").textbox("setValue",selectedRow.name);
			$("#otherId").val(selectedRow.id);
			$("#otherDialog").dialog('close')
		});
	},
	initViewList : function(params) {
		var params = $.extend({
			url : app.url.datagrid(),
			pagination : true,
			fit : true,
			border : false,
			toolbar : app.id.toolbarId,
			ctrlSelect : true,
			rownumbers : true,
			pageSize : 20,
			pageList : [ 10, 20, 30, 40, 50, 100 ],
			onDblClickRow : function() {
				$(app.id.updateBtnId).click();
			}
		}, params);
		$(app.id.listId).datagrid(params);
	},
	calDay:function(){
		var selectedRow=$("#loanList").datagrid("getSelected")
		var repayDate=$("#repayDate").val();
		var beginDate=selectedRow.beginDate;
		var day=balance.DateDiff(repayDate,beginDate);
		var interest=balance.accMul(selectedRow.amount,selectedRow.rate);
		interest=balance.accMul(interest,day);
		interest=balance.accMul(interest,100);
		$("#referInterest").textbox("setValue",interest);
		$("#interest").textbox("setValue",interest);
		interest=balance.accDiv(interest,10000);
		var receiveAmount=balance.accAdd(interest,selectedRow.amount);
		$("#receiveAmount").textbox("setValue",receiveAmount);
		$("#loanDay").textbox("setValue",day);
	},
	 // 两个浮点数求和  
	accAdd: function(num1,num2){  
       var r1,r2,m;  
       try{  
           r1 = num1.toString().split('.')[1].length;  
       }catch(e){  
           r1 = 0;  
       }  
       try{  
           r2=num2.toString().split(".")[1].length;  
       }catch(e){  
           r2=0;  
       }  
       m=Math.pow(10,Math.max(r1,r2));  
       // return (num1*m+num2*m)/m;  
       return Math.round(num1*m+num2*m)/m;  
    },
	//小数乘法
	accMul:function (arg1,arg2)  
	    {  
	        var m=0,s1=arg1.toString(),s2=arg2.toString();  
	        try{m+=s1.split(".")[1].length}catch(e){}  
	        try{m+=s2.split(".")[1].length}catch(e){}  
	        return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)  
	},
	 //浮点数除法
    accDiv:function(arg1,arg2){  
        var t1=0,t2=0,r1,r2;  
        try{t1=arg1.toString().split(".")[1].length}catch(e){}  
        try{t2=arg2.toString().split(".")[1].length}catch(e){}  
        with(Math){  
            r1=Number(arg1.toString().replace(".",""))  
            r2=Number(arg2.toString().replace(".",""))  
            return (r1/r2)*pow(10,t2-t1);  
        }  
    },
	//计算两个日期相减天数
	DateDiff: function  (sDate1,  sDate2){    //sDate1和sDate2是2006-12-18格式  
	       var  aDate,  oDate1,  oDate2,  iDays  
	       aDate  =  sDate1.split("-")  
	       oDate1  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0])    //转换为12-18-2006格式  
	       aDate  =  sDate2.split("-")  
	       oDate2  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0])  
	       iDays  =  parseInt(Math.abs(oDate1  -  oDate2)  /  1000  /  60  /  60  /24)    //把相差的毫秒数转换为天数  
	       return  iDays  
	  },
	initEditDialog : function() {
		var params = {
			title : '编辑' + resourceName,
			width : 430,
			resizable:true,
			height : document.documentElement.clientHeight-5<300?document.documentElement.clientHeight-5:300,
			closed : true,
			cache : false,
			modal : true,
			buttons : [ {
				text : '保存',
				iconCls : 'icon-save',
				handler : function() {
					$("#orderId").val($("#ordeCode").val());
					$("#addForm").submit();
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
	initTabsDialog : function() {
		var params = {
			title : '编辑' + resourceName,
			width : 880,
			resizable:true,
			height : document.documentElement.clientHeight-5<580?document.documentElement.clientHeight-5:580,
			closed : true,
			cache : false,
			modal : true,
			onClose : function() {
				$(app.id.editDialogId).form('clear');
			}
		};
		$("#tabsDialog").dialog(params);
		$("#tabs").tabs({
			border:false,
			onSelect:function(title,index){
				var orderId=$("#orderId").val();
				if("基本信息"==title){
					
				}else if("借款"==title){
					$("#loanList").datagrid({
						url : "myCrm1/financial/balance/listLoanByPage.jhtml?orderId="+orderId,
						pagination : true,
						fit : true,
						singleSelect:true,
						border : false,
						toolbar : "#tb2",
						columns:[[
						          {field:'amount',title:'借款金额（万元）',width:110,align:'center'},
						          {field:'rate',title:'借款利率（%）',width:80,align:'center'},
						          {field:'beginDate',title:'借款日期',width:100,align:'center'},
						          {field:'estimateRepayDate',title:'预计还款日期',width:100,align:'center'},
						          {field:'estimateInterest',title:'预计利息（元）',width:110,align:'center'},
						          {field:'repayDate',title:'实际还款日期',width:100,align:'center'},
						          {field:'loanDay',title:'实际借款天数',width:80,align:'center'},
						          {field:'referInterest',title:'参考利息（元）',width:110,align:'center'},
						          {field:'interest',title:'实际利息（元）',width:110,align:'center'},
						          {field:'receiveAmount',title:'应收金额（万元）',width:110,align:'center'},
						          {field:'receivedAmount',title:'已收金额（万元）',width:110,align:'center'},
						          {field:'receivingAmount',title:'待收金额（万元）',width:110,align:'center'},
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
						url : "myCrm1/financial/balance/listOrderIncomeByPage.jhtml?orderId="+orderId,
						pagination : true,
						fit : true,
						singleSelect:true,
						border : false,
						toolbar : "#tb3",
						columns:[[
						          {field:'costTypeName',title:'收入项',width:80,align:'center'},
						          {field:'estimateAmount',title:'参考金额（元）',width:110,align:'center'},
						          {field:'receiveAmount',title:'实际金额（元）',width:110,align:'center'},
						          {field:'receivedAmount',title:'已收金额（元）',width:110,align:'center'},
						          {field:'receivingAmount',title:'待收金额（元）',width:110,align:'center'},
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
						url : "myCrm1/financial/balance/listPayByPage.jhtml?orderId="+orderId+"&source=1",
						pagination : true,
						fit : true,
						singleSelect:true,
						border : false,
						toolbar : "#tb4",
						columns:[[
						          {field:'costTypeName',title:'支出项',width:80,align:'center'},
						          {field:'payObjectType',title:'支出对象类型',width:90,align:'center',formatter(value,row,index){
						        	  if(value==1){
											return "评估公司";
										}else if(value==2){
											return "合作银行";
										}else if(value==3){
											return "其他合作伙伴";
										}else if(value==4){
											return "客户";
										}
						          }},
						          {field:'object',title:"支出对象",width:90,align:'center'},
						          {field:'isCost',title:"是否成本",width:70,align:'center',formatter:function(value,row,index){
						        	  if(value=="1"){
						        		  return "是";
						        	  }else if(value=="0"){
						        		  return "否";
						        	  }
						          }},
						          {field:'estimatePayAmount',title:'参考金额（元）',width:110,align:'center'},
						          {field:'payAmount',title:'实际金额（元）',width:110,align:'center'},
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
			}
		})
	},
	initEditLoanDialog : function() {
		var params = {
			title : '编辑借款',
			width : 430,
			height : document.documentElement.clientHeight-5<420?document.documentElement.clientHeight-5:420,
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
	initEditIncomeDialog:function() {
		var params = {
			title : '编辑收入',
			width : 430,
			height : document.documentElement.clientHeight-5<300?document.documentElement.clientHeight-5:300,
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
	initEditPayOtherDialog : function() {
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
					$("#editPayOtherForm").submit();
				}
			}, {
				text : '退出',
				iconCls : 'icon-exit',
				handler : function() {
					$("#editPayOtherDialog").dialog('close');
				}
			} ],
			modal : true,
			onClose : function() {
				$("#editPayOtherForm").form('clear');
			}
		};
		$("#editPayOtherDialog").dialog(params);
	},
	initOtherDialog : function() {
		var params = {
			title : '选择其他合作伙伴',
			width : 650,
			height : document.documentElement.clientHeight-5<450?document.documentElement.clientHeight-5:450,
			closed : true,
			cache : false,
			modal : true,
			onClose : function() {
			}
		};
		$("#otherDialog").dialog(params);
	},
	initEditForm : function() {
		$("#addForm").form({
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
						$(app.id.editDialogId).dialog('close');
						var url=app.getUrl("getOrder");
						var d={'id':data.data.id};
						CSIT.asyncCallService(url, d,function(result){
							$("#orderId").val(result.id);
							$("#updateLoan").linkbutton("enable");
							$("#updateIncome").linkbutton("enable");
							$("#updatePay").linkbutton("enable");
							$("#tabsDialog").dialog('open');
							$('#tabs').tabs({
								selected:0
							});
							balance.initEditForm();
							$(app.id.editFormId).form('load', result);
							$(app.id.editFormId).attr('action', app.url.update());
							$(app.id.listId).datagrid('reload');
						});
					});
				} else {
					$.messager.alert('提示', data.message, 'error');
				}
			}
		});
	},
	initEditLoanForm : function() {
		$("#editLoanForm").form({
			onSubmit : function() {
				var isValid = $(this).form('validate');
				if (!isValid) {
					$.messager.progress('close');
				}
				var selectedRow=$(app.id.listId).datagrid('getSelected');
				if(selectedRow!=null && selectedRow.settlementAuditStatus!=1){
					$.messager.alert("提示","该无法修改当前状态订单的信息",'info')
					return false;
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
				var selectedRow=$(app.id.listId).datagrid('getSelected');
				if(selectedRow!=null && selectedRow.settlementAuditStatus!=1){
					$.messager.alert("提示","该无法修改当前状态订单的信息",'info')
					return false;
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
	initEditPayOtherForm : function() {
		$("#editPayOtherForm").form({
			onSubmit : function() {
				var isValid = $(this).form('validate');
				if (!isValid) {
					$.messager.progress('close');
				}
				var selectedRow=$(app.id.listId).datagrid('getSelected');
				if(selectedRow!=null && selectedRow.settlementAuditStatus!=1){
					$.messager.alert("提示","该无法修改当前状态订单的信息",'info')
					return false;
				}
				return isValid;
			},
			success : function(data) {
				$.messager.progress('close');
				var data = eval('(' + data + ')');
				if (data.isSuccess) {
					$.messager.alert('提示', '保存成功', 'info', function() {
						$("#editPayOtherDialog").dialog('close');
						$("#payList").datagrid('reload');
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
				var selectedRow=$(app.id.listId).datagrid('getSelected');
				if(selectedRow!=null && selectedRow.settlementAuditStatus!=1){
					$.messager.alert("提示","该无法修改当前状态订单的信息",'info')
					return false;
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
	add : function() {
		$(app.id.editDialogId).dialog({
			title:"添加"+resourceName
		})
			//订单
		$("#orderCode").combobox({
		  url:"myCrm1/financial/balance/orderCombobox.jhtml",
		  panelHeight: 'auto',
		  editable:false,
		  valueField: 'id',
		  textField: 'name',
		});
		$(app.id.editDialogId).dialog('open');
		balance.initEditForm();
		$("#addForm").attr('action', app.url.create());
	},
	update : function() {
		var selectedRow = $(app.id.listId).datagrid('getSelected');
		if (selectedRow == null) {
			$.messager.alert('提示', '请选择数据行', 'warning');
			return;
		}
		if (selectedRow.isSys == 1) {
			$.messager.alert('提示', '系统内置不能修改', 'warning');
			return;
		}
		$("#orderId").val(selectedRow.id);
		if(selectedRow.settlementAuditStatus!=1){
			$("#updateLoan").linkbutton({"disabled":true});
			$("#updateIncome").linkbutton({"disabled":true});
			$("#updatePay").linkbutton({"disabled":true});
		}else{
			$("#updateLoan").linkbutton("enable");
			$("#updateIncome").linkbutton("enable");
			$("#updatePay").linkbutton("enable");
		}
		$("#tabsDialog").dialog('open');
		$('#tabs').tabs({
			selected:0
		});
		balance.initEditForm();
		$(app.id.editFormId).form('load', selectedRow);
		$(app.id.editFormId).attr('action', app.url.update());
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
		var mainList=$(app.id.listId).datagrid('getSelected');
		if(mainList!=null && mainList.settlementAuditStatus!=1){
			$.messager.alert("提示","该无法修改当前状态订单的信息",'info')
			return ;
		}
		$("#editLoanDialog").dialog('open');
		balance.initEditLoanForm();
		$("#orderLoanId").val(selectedRow.id);
		$("#editLoanForm").form('load', selectedRow);
		$("#editLoanForm").attr('action', app.getUrl("updateLoan"));
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
		var mainList=$(app.id.listId).datagrid('getSelected');
		if(mainList!=null && mainList.settlementAuditStatus!=1){
			$.messager.alert("提示","该无法修改当前状态订单的信息",'info')
			return ;
		}
		$("#editIncomeDialog").dialog('open');
		balance.initEditIncomeForm();
		$("#orderIncomeId").val(selectedRow.id);
		$("#orderId2").val($("#orderId").val());
		$("#editIncomeForm").form('load', selectedRow);
		$("#editIncomeForm").attr('action', app.getUrl("updateIncome"));
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
		};
		var mainList=$(app.id.listId).datagrid('getSelected');
		if(mainList!=null && mainList.settlementAuditStatus!=1){
			$.messager.alert("提示","该无法修改当前状态订单的信息",'info')
			return ;
		}
		if(selectedRow.costTypeName.indexOf("其他")!=-1){
			$("#editPayOtherDialog").dialog('open');
			balance.initEditPayOtherForm();
			$("#editPayOtherForm").form('load', selectedRow);
			$("#editPayOtherForm").attr('action',app.getUrl("updatePay"));
			$("#orderPayId2").val(selectedRow.id);
			$("#otherId").val(selectedRow.objectId);
		}else{
			$("#editPayDialog").dialog('open');
			balance.initEditPayForm();
			$("#orderPayId").val(selectedRow.id);
			$("#editPayForm").form('load', selectedRow);
			$("#editPayForm").attr('action', app.getUrl("updatePay"));
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
				if(checkedRows[i].settlementAuditStatus!=1){
					$.messager.alert("提示","只有草稿可以删除","error");
					return ;
				}else{
					idArray.push(checkedRows[i].id);
				}
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
				idArray.push(checkedRows[i].id);
			}
			var ids = idArray.join(CSIT.join);
			var content = {
				ids : ids
			};
			$.messager.progress();
			CSIT.asyncCallService(app.getUrl("verify"), content,
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
	search : function() {
		var isValid = $(app.id.searchFormId).form('validate');
		if (!isValid) {
			return false;
		}
		var content = $(app.id.searchFormId).serializeObject();
		$(app.id.listId).datagrid({
			queryParams : content
		});
	},
	init : function(params) {
		balance.initEditDialog();
		balance.initCRUDBtn();
		balance.initTabsDialog();
		balance.initEditLoanDialog();
		balance.initEditIncomeDialog();
		balance.initEditPayDialog();
		balance.initOtherDialog();
		balance.initEditPayOtherDialog();
		balance.initViewList({
		columns:[[
  		        {field:'ck',checkbox:true},
  		        {field:'orderCode',title:'订单编号',width:130,align:'center'},
  		        {field:'customerName',title:'客户名称',width:100,align:'center'},
  		        {field:'signingDate',title:'签约日期',width:100,align:'center'},
				{field:'productName',title:'产品',width:90,align:'center'},
				{field:'settlementAuditStatus',title:'审批状态',width:90,align:'center',formatter:function(value,row,index){
					if(value==1){
						return "草稿";
					}else if(value==2){
						return "审核中"
					}else if(value==3){
						return "审核通过";
					}else if(value==4){
						return "重新调整";
					}else if(value==5){
						return "放弃";
					}else if(value==6){
						return "审核不通过";
					}
				}},
				{field:'id',hidden:true}
	     		]]
		});
		//是否成本
		$("#isCost").combobox({
		  panelHeight: 'auto',
		  editable:false,
		  valueField: 'value',
		  textField: 'text',
		  data: [
		      {value: '1',text: '是'},
		      {value: '0',text: '否'}
		  ]
		});
	}

}

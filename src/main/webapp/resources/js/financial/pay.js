var pay = {

	initCRUDBtn : function() {
		// 添加
		$(app.id.addBtnId).click(function() {
			pay.add();
		});
		// 修改
		$(app.id.updateBtnId).click(function() {
			pay.update();
		});
		// 删除
		$(app.id.deleteBtnId).click(function() {
			pay.mulDelete();
		});
		// 查询
		$(app.id.searchBtnId).click(function() {
			pay.search();
		});
		// 清空查询条件
		$(app.id.clearBtnId).click(function() {
			$(app.id.searchFormId).form('clear');
		});
		// 订单查询
		$("#orderSearchBtn").click(function() {
			pay.orderSearch();
		});
		// 清空订单查询条件
		$("#orderClearBtn").click(function() {
			$("#orderSearchForm").form('clear');
		});
		// 支出对象查询
		$("#objectSearchBtn").click(function() {
			pay.objectSearch();
		});
		// 清空支出对象查询条件
		$("#objectClearBtn").click(function() {
			$("#objectSearchForm").form('clear');
		});
		//订单编号列表
		$("#order").textbox({
			onClickIcon:function(){
				/*var objectType=$("#payObjectType").combobox("getValue");
				if(objectType==null || objectType==""){
					$.messager.alert("提示","请先选择支出类型","warn");
					return ;
				}*/
				$("#orderDialog").dialog('open');
				pay.initOrderList();
			}
		});
		//选择对象类型清空支付对象
		$("#payObjectType").combobox({
			onSelect:function(){
				$("#object").textbox("setValue","");
		}
		});
		//选择订单
		$("#orderSelected").click(function(){
			var selectedRow=$("#orderList").datagrid("getSelected");
			if(selectedRow==null){
				$.messager.alert("提示","请选择一条订单记录","warn");
				return ;
            }
            var objectType=$("#payObjectType").combobox("getValue");
			var order=selectedRow.customerName+"  "+selectedRow.orderCode;
			$("#order").textbox('setValue',order);
			$("#orderId").val(selectedRow.id);
			$("#object").textbox("setValue","");
			$("#orderDialog").dialog('close');
		});
		//选择支出对象
		$("#object").textbox({
			onClickIcon:function(){
				var objectType=$("#payObjectType").combobox("getValue");
				if(objectType==null || objectType==""){
					$.messager.alert("提示","请先选择支出类型","warn");
					return ;
				}
				var orderId=$("#orderId").val();
				if(objectType==1){
					$("#objectDialog").dialog({
						title:"选择评估公司"
					});
					pay.initObjectList({
						url:orderId==""?"myCrm1/common/util/getAssessmentList?status=1":"myCrm1/dict/assessmentCompany/listAssessmentCompanyByOrder?orderId="+orderId,
						columns:[[
						{
							field : 'name',
							title : '公司名称',
							width : 100,
							align : 'center'
						},{
							field : 'address',
							title : '地址',
							width : 200,
							align : 'center'
						},{
							field : 'contacts',
							title : '联系人',
							width : 100,
							align : 'center'
						},{
							field : 'telephone',
							title : '联系电话',
							width : 150,
							align : 'center'
						},{
							field : 'note',
							title : '备注',
							width : 150,
							align : 'center'
						}
					]]
					});
				}else if(objectType==2){
					$("#objectDialog").dialog({
						title:"选择合作银行"
					});
					pay.initObjectList({
						url:orderId==""?"myCrm1/common/util/listBranchByPage?status=1":"myCrm1/dict/cooperateBank/listBankByOrder?orderId="+orderId,
						columns:[[
						{
							field : 'subbranchName',
							title : '银行名称',
							width : 100,
							align : 'center'
						},{
							field : 'bankCode',
							title : '代号',
							width : 100,
							align : 'center'
						},{
							field : 'address',
							title : '地址',
							width : 150,
							align : 'center'
						},{
							field : 'contacts',
							title : '银行联系人',
							width : 100,
							align : 'center'
						},{
							field : 'phone',
							title : '联系电话',
							width : 150,
							align : 'center'
						},{
						    field : 'note',
							title : '备注',
							width : 100,
							align : 'center'
						}]]
				});
				}else if(objectType==3){
					$("#objectDialog").dialog({
						title:"选择合作伙伴"
					});
					if(!orderId==""){
						$("#objectList").datagrid("loadData", { total: 0, rows: [] });	
						pay.initObjectList({	
						url:null,
						columns:[[
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
        						}]]});
						
					}else{
					pay.initObjectList({
						url:orderId==""?"myCrm1/common/util/getOtherPartners?status=1":null,
						columns:[[
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
						}]]
				});
					}
				}else if(objectType==4){
					$("#objectDialog").dialog({
						title:"选择客户"
					});
					pay.initObjectList({
						url:orderId==""?"myCrm1/myWorkbench/totalCustomer/listByPage?status=1":"myCrm1/myWorkbench/totalCustomer/listCustomerByOrder?orderId="+orderId,
						columns:[[
							{field:'customerName',title:'客户名称',width:100,align:'center'},
							{field:'customerType',title:'类型',width:100,align:'center',formatter: function(value,row,index){
							    if(value == 1){
							        return "个人";
							    }else{
							        return "企业";
							    }},
							},
							{field:'sex',title:'性别',width:100,align:'center',formatter:function(value,index,row){
								if(value==0){
									return "女";
								}else if(value==1){
									return "男";
								}
							}},
							{field:'telephone',title:'固定电话',width:100,align:'center'},
							{field:'email',title:'邮箱',width:100,align:'center'},
							{field:'idNumber',title:'身份证号',width:100,align:'center'},
							{field:'idAddress',title:'身份证地址',width:100,align:'center'},
							{field:'occupation',title:'职业',width:100,align:'center'},
							{field:'mobilePhone',title:'手机号',width:120,align:'center'}
							]]
				});
				}
				$("#objectDialog").dialog('open');
			}
		});
		$("#objectSelected").click(function(){
			var selectedRow=$("#objectList").datagrid("getSelected");
			if(selectedRow==null){
				$.messager.alert("提示","请选择一条订单记录","warn");
				return ;
            }
            $("#object").textbox('setValue',selectedRow.name);
			if(selectedRow.subbranchName!=null){
				$("#object").textbox('setValue',selectedRow.subbranchName);
			}
			if(selectedRow.customerName!=null){
				$("#object").textbox('setValue',selectedRow.customerName);
			}
			$("#objectId").val(selectedRow.id);
			$("#objectDialog").dialog('close');
		});
		$("#verifyBtn").click(function(){
			pay.verify();
		});
        $("#traceBtn").click(function(){
            pay.trace();
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
	initOrderList : function() {
		
		$("#orderList").datagrid({
			url : "myCrm1/myWorkbench/order/listByPage.jhtml",
			queryParams:{'settlementAuditStatus':3},
			pagination : true,
			fit : true,
			border : false,
			toolbar : "#tb2",
			singleSelect : true,
			rownumbers : true,
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50, 100 ],
			columns:[[
				{field:'orderCode',title:'订单编号',width:100,align:'center'},
				{field:'customerName',title:'客户',width:100,align:'center'},
				{field:'signingDate',title:'签约日期',width:100,align:'center'},
				{field:'productName',title:'产品',width:100,align:'center'},
				{field:'bankName',title:'银行',width:100,align:'center'},
				{field:'subbranchName',title:'支行',width:100,align:'center'},
				{field:'signingDate',title:'签约时间',width:100,align:'center'},
				{field:'estimateFinishTime',title:'预计完成日期',width:100,align:'center'},
				{field:'auditStatus',title:'审核状态',width:100,align:'center',
				    formatter: function(value,row,index){
				        switch(value){
				            case "1":return "草稿";
				            case "2":return "审核中";
				            case "3":return "审核通过";
				            case "4":return "审核不通过";
				            case "5":return "放弃";
				        }
				    }},
				{field:'status',title:'状态',width:100,align:'center',
				    formatter: function(value,row,index){
				        switch(value){
				            case "1":return "已立项";
				            case "2":return "已签约";
				            case "3":return "已放款";
				            case "4":return "完结";
				        }
				    }},
				{field:'csAssistantName',title:'客服助理',width:100,align:'center'},
				{field:'csPrincipalName',title:'客服负责人',width:100,align:'center'},
				{field:'followUserName',title:'跟单人',width:100,align:'center'},
				{field:'ownerName',title:'拥有人',width:100,align:'center'},
				{field:'createName',title:'创建人',width:100,align:'center'}  
			]],
			onDblClickRow : function() {
				$("#orderSelected").click();
			}
		});
	},
	initObjectList : function(params) {
		var params = $.extend({
			pagination : true,
			fit : true,
			border : false,
			toolbar : "#tb3",
			singleSelect : true,
			rownumbers : true,
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50, 100 ],
			onDblClickRow : function() {
				$("#objectSelected").click();
			}
		}, params);
		$("#objectList").datagrid(params);
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
	initOrderDialog : function() {
		var params = {
			title : '选择订单',
			width : document.documentElement.clientWidth-5<1000?document.documentElement.clientWidth-5:1000,
			height : document.documentElement.clientHeight-5<550?document.documentElement.clientHeight-5:550,
			closed : true,
			cache : false,
			modal : true,
			onClose : function() {
				$("#orderDialog").form('clear');
			}
		};
		$("#orderDialog").dialog(params);
	},
	initObjectDialog : function() {
		var params = {
			title : '选择支出对象',
			width : document.documentElement.clientWidth-5<800?document.documentElement.clientWidth-5:800,
			height : document.documentElement.clientHeight-5<550?document.documentElement.clientHeight-5:550,
			closed : true,
			cache : false,
			modal : true,
			onClose : function() {
				$("#objectDialog").form('clear');
			}
		};
		$("#objectDialog").dialog(params);
	},
	initEditForm : function() {
		$(app.id.editFormId).form({
			onSubmit : function() {
				var selectedRow=$(app.id.listId).datagrid('getSelected');
				if(selectedRow!=null &&(selectedRow.source!=1&&selectedRow.auditStatus==3)){
					$.messager.alert("提示","该状态无法修改",'info')
					return false;
				}
				var isValid = $(this).form('validate');
				if (!isValid) {
					$.messager.progress('close');
				}
				console.log(isValid)
				return isValid;
			},
			success : function(data) {
				$.messager.progress('close');
				var data = eval('(' + data + ')');
				if (data.isSuccess) {
					$.messager.alert('提示', '保存成功', 'info', function() {
						$(app.id.editDialogId).dialog('close');
						$(app.id.listId).datagrid('reload');
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
		});
		$(app.id.editDialogId).dialog('open');
		$("#costTypeId").combobox({readonly:false});
		$("#isCost").combobox({readonly:false});
		$("#payObjectType").combobox({readonly:false});
		$("#order").textbox({readonly:false});
		$("#object").textbox({readonly:false});
		$("#payAmount").numberbox({readonly:false});
		$("#receiveAccountName").textbox({readonly:false});
		$("#receiveAccountNumber").numberbox({readonly:false});
		$("#receiveAccountBank").textbox({readonly:false});
		$("#note").textbox({readonly:false});
		pay.initEditForm();
		$("#status").combobox("setValue",1);
		$(app.id.editFormId).attr('action', app.url.create());
		app.yesOrNoByIntCombobox("#isSys");
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
		$(app.id.editDialogId).dialog({
			title:"编辑"+resourceName
		});
		if(selectedRow.source==1 && selectedRow.auditStatus==3){
			$("#costTypeId").combobox({readonly:true});
			$("#isCost").combobox({readonly:true});
			$("#payObjectType").combobox({readonly:true});
			$("#order").textbox({readonly:true});
			$("#object").textbox({readonly:true});
			$("#payAmount").numberbox({readonly:true});
			$("#receiveAccountName").textbox({readonly:true});
			$("#receiveAccountNumber").numberbox({readonly:true});
			$("#receiveAccountBank").textbox({readonly:true});
			$("#payBankAccount").textbox({disabled:false});
			$("#payTime").textbox({disabled:false});
			$("#note").textbox({readonly:false});
			//$("#note").textbox({readonly:true});
		}else if(selectedRow.source==2 && selectedRow.auditStatus==3){
			$("#costTypeId").combobox({readonly:true});
			$("#isCost").combobox({readonly:true});
			$("#payObjectType").combobox({readonly:true});
			$("#order").textbox({readonly:true});
			$("#object").textbox({readonly:true});
			$("#payAmount").numberbox({readonly:true});
			$("#receiveAccountName").textbox({readonly:true});
			$("#receiveAccountNumber").numberbox({readonly:true});
			$("#receiveAccountBank").textbox({readonly:true});
			$("#payBankAccount").textbox({disabled:true});
			$("#payTime").textbox({disabled:true});
			$("#note").textbox({readonly:true});
		}else{
			$("#costTypeId").combobox({readonly:false});
			$("#isCost").combobox({readonly:false});
			$("#payObjectType").combobox({readonly:false});
			$("#order").textbox({readonly:false});
			$("#object").textbox({readonly:false});
			$("#payAmount").numberbox({readonly:false});
			$("#receiveAccountName").textbox({readonly:false});
			$("#receiveAccountNumber").numberbox({readonly:false});
			$("#receiveAccountBank").textbox({readonly:false});
			$("#payBankAccount").textbox({disabled:false});
			$("#payTime").textbox({disabled:false});
			$("#note").textbox({readonly:false});
		}
		$(app.id.editDialogId).dialog('open');
		pay.initEditForm();
		$(app.id.editFormId).form('load', selectedRow);
		$(app.id.editFormId).attr('action', app.url.update());
		app.yesOrNoByIntCombobox("#isSys");
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
			var idArray = [];
			for (var i = 0; i < checkedRows.length; i++) {
				console.log(checkedRows[i].auditStatus)
				if(checkedRows[i].auditStatus!=1){
					$.messager.alert("提示","只有草稿可以删除","error");
					return;
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
			var idArray = [];
			for (var i = 0; i < checkedRows.length; i++) {
				if(checkedRows[i].auditStatus!=1){
                    $.messager.alert('提示', '只有草稿才能送审', 'warning');
					return;
				}
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
    //送审跟踪
    trace : function() {
        var checkedRows = $(app.id.listId).datagrid('getChecked');
        if(checkedRows.length==0){
            $.messager.alert('提示','请选择跟踪的数据行','warning');
            return;
        }
        if(checkedRows[0].auditStatus==2||checkedRows[0].auditStatus==4){
            window.open(ctx+"/financial/pay/trace.jhtml?id="+checkedRows[0].id);
        }else {
            $.messager.alert('提示','只有审核中和审核不通过的才能跟踪','warning');
        }
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
	orderSearch : function() {
		var isValid = $("#orderSearchForm").form('validate');
		if (!isValid) {
			return false;
		}
		$("#settlementAuditStatus").val(3);
		var content = $("#orderSearchForm").serializeObject();
		$("#orderList").datagrid({
			queryParams : content
		});
	},
	objectSearch : function() {
		var isValid = $("#objectSearchForm").form('validate');
		if (!isValid) {
			return false;
		}
		var objectType=$("#payObjectType").val();
		if(objectType==2){
			$("#objectSearch").attr("name","subbranchName");
		}
		if(objectType==4){
			$("#objectSearch").attr("name","customerName");
		}
		var content = $("#objectSearchForm").serializeObject();
		$("#objectList").datagrid({
			queryParams : content
		});
	},
	init : function(params) {
		pay.initEditDialog();
		pay.initOrderDialog();
		pay.initObjectDialog();
		pay.initCRUDBtn();
		pay.initViewList({
		columns:[[
  		        {field:'ck',checkbox:true},
				{field:'source',title:'来源',width:90,align:'center',formatter:function(value,row,index){
					if(value==1){
						return "订单结算";
					}else{
						return "添加";
					}
				}},
				{field:'submitDate',title:'提交时间',width:135,align:'center'},
				{field:'costTypeName',title:'费用类型',width:90,align:'center'},
				{field:'isCost',title:'是否成本',width:80,align:'center',formatter:function(value,row,index){
					if(value==1){
						return "是";
					}else{
						return "否";
					}
				}},
				{field:'payObjectType',title:'支出对象类型',width:100,align:'center',formatter:function(value,row,index){
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
				{field:'object',title:'支出对象',width:120,align:'center'},
				{field:'orderCode',title:'订单编号',width:130,align:'center'},
				{field:'payAmount',title:'支出金额（元）',width:90,align:'center'},
				{field:'submitUserName',title:'提交人',width:100,align:'center'},
				{field:'receiveAccountName',title:'收款账户名',width:100,align:'center'},
				{field:'receiveAccountBank',title:'收款账户开户行',width:100,align:'center'},
				{field:'payBankAccountNumber',title:'付款账号',width:100,align:'center'},
				{field:'receiveAccountNumber',title:'收款账户账号',width:100,align:'center'},
				{field:'payTime',title:'支出日期',width:100,align:'center'},
				{field:'auditStatus',title:'审批状态',width:90,align:'center',formatter:function(value,row,index){
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
				{field:'note',title:'备注',width:100,align:'center'},
				{field:'id',hidden:true}
	     		]]
		});
		app.enableCombobox($('#statu'));
		app.costTypeCombobox($("#costTypeId"));
		app.costTypeCombobox($("#costTypeSearch"));
		$("#statu").combobox('clear');
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
		//是否成本
		$("#auditStatus").combobox({
		  panelHeight: 'auto',
		  editable:false,
		  valueField: 'value',
		  textField: 'text',
		  data: [
		      {value: '1',text: '草稿'},
		      {value: '2',text: '审核中'},
		      {value: '3',text: '审核通过'},
		      {value: '4',text: '重新调整'},
		      {value: '5',text: '放弃'},
		      {value: '6',text: '审核不通过'}
		  ]
		});
		$("#payObjectType").combobox({
			  panelHeight: 'auto',
			  editable:false,
			  valueField: 'value',
			  textField: 'text',
			  data: [
			      {value: '1',text: '评估公司'},
			      {value: '2',text: '合作银行'},
			      {value: '3',text: '其他合作伙伴'},
			      {value: '4',text: '客户'}
			  ]
			});
		$("#sourceSearch").combobox({
			  panelHeight: 'auto',
			  editable:false,
			  valueField: 'value',
			  textField: 'text',
			  data: [
			      {value: '1',text: '订单结算'},
			      {value: '2',text: '添加'}
			  ]
			});
		$("#payBankAccount").combobox({
			  url:"myCrm1/sys/orgBankAccount/payBankAccountCombobox.jhtml",
			  panelHeight: 'auto',
			  editable:false,
			  valueField: 'id',
			  textField: 'accountName'
		});
	}

};

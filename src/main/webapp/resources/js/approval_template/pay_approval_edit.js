var pay_approval_edit = {

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
        }
		// 订单查询
		$("#orderSearchBtn").click(function() {
			pay_approval_edit.orderSearch();
		});
		// 清空订单查询条件
		$("#orderClearBtn").click(function() {
			$("#orderSearchForm").form('clear');
		});
		// 支出对象查询
		$("#objectSearchBtn").click(function() {
			pay_approval_edit.objectSearch();
		});
		// 清空支出对象查询条件
		$("#objectClearBtn").click(function() {
			$("#objectSearchForm").form('clear');
		});
		//订单编号列表
		$("#order").textbox({
			onClickIcon:function(){
				$("#orderDialog").dialog('open');
				pay_approval_edit.initOrderList();
			}
		});
		//选择对象类型清空支付对象
		$("#payObjectType").combobox({
			onSelect:function(){
				$("#object").textbox("setValue","");
		}
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
						url:orderId==""?ctx+"/dict/assessmentCompany/listByPage?status=1":ctx+"/dict/assessmentCompany/listAssessmentCompanyByOrder?orderId="+orderId,
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
						url:orderId==""?ctx+"/dict/cooperateBank/listBranchByPage?status=1":ctx+"/dict/cooperateBank/listBankByOrder?orderId="+orderId,
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
						url:orderId==""?ctx+"/dict/otherPartners/listByPage?status=1":null,
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
						url:orderId==""?ctx+"/myWorkbench/totalCustomer/listByPage?status=1":ctx+"/myWorkbench/totalCustomer/listCustomerByOrder?orderId="+orderId,
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
		//选项卡
		$("#infoTabs").tabs({
			border:false,
			onSelect:function(title,index){
				if(title=="审批"){
					 //审批历史
					console.log("pi:"+processInstanceId)
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
		//保存基本信息
		$("#saveUpdateBtn").click(function(){
			$("#editForm").submit();
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
					pay_approval_edit.initObjectList({
						url:orderId==""?ctx+"/dict/assessmentCompany/listByPage?status=1":ctx+"/dict/assessmentCompany/listAssessmentCompanyByOrder?orderId="+orderId,
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
					pay_approval_edit.initObjectList({
						url:orderId==""?ctx+"/dict/cooperateBank/listBranchByPage?status=1":ctx+"/dict/cooperateBank/listBankByOrder?orderId="+orderId,
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
						pay_approval_edit.initObjectList({	
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
					pay_approval_edit.initObjectList({
						url:orderId==""?ctx+"/dict/otherPartners/listByPage?status=1":null,
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
					pay_approval_edit.initObjectList({
						url:orderId==""?ctx+"/myWorkbench/totalCustomer/listByPage?status=1":ctx+"/myWorkbench/totalCustomer/listCustomerByOrder?orderId="+orderId,
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
			pay_approval_edit.verify();
		});
	},
	initOrderList : function() {
		$("#orderList").datagrid({
			url : ctx+"/myWorkbench/order/listByPage.jhtml",
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
	initEditForm : function() {
		$(app.id.editFormId).form({
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
						$(app.id.listId).datagrid('reload');
					});
				} else {
					$.messager.alert('提示', data.message, 'error');
				}
			}
		});
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
						$(app.id.listId).datagrid('reload');
					});
				} else {
					$.messager.alert('提示', data.message, 'error');
				}
			}
		});
	},
	orderSearch : function() {
		var isValid = $("#orderSearchForm").form('validate');
		if (!isValid) {
			return false;
		}
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
		var objectType=$("#pay_approval_editObjectType").val();
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
	initInfo:function(){
		var url=ctx+"/financial/pay/getPayById.jhtml";
		var param={"id":payId};
		var data=CSIT.syncCallService(url,param);
		$("#editForm").form('load',data[0]);
		pay_approval_edit.initEditForm();
		$("#editForm").attr('action',ctx+"/financial/pay/update.jhtml");
	},
	init : function(params) {
		pay_approval_edit.initEditDialog();
		pay_approval_edit.initOrderDialog();
		pay_approval_edit.initObjectDialog();
		pay_approval_edit.initCRUDBtn();
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
		      {value: '2',text: '通过'},
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
			  url:ctx+"/sys/orgBankAccount/payBankAccountCombobox.jhtml",
			  panelHeight: 'auto',
			  editable:false,
			  valueField: 'id',
			  textField: 'accountName'
		});
		pay_approval_edit.initInfo();
	}

};

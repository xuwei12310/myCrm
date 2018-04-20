var orderDetails = {
		init: function () {
            orderDetails.initBtn();
            orderDetails.initDialog();
			//产权信息
			$("#orderPropertyList").datagrid({
	    		pagination:true,
	    		fit:true,
	    		border:false,
	    		toolbar: [
	    		          {iconCls: 'icon-add',text:'添加',handler: function(){orderDetails.selectProperty();}},
	    		          {iconCls: 'icon-remove',text:'删除',handler: function(){
                              if(orderStatus==5){
                                  $.messager.alert('提示','订单已结算','warning');
                                  return;
                              }
                              if(orderAuditStatus!=1){
                                  $.messager.alert('提示','非草稿状态不能操作','warning');
                                  return;
                              }
	    		          	orderDetails.deleteBtn("#orderPropertyList",ctx + "/myWorkbench/orderProperty/mulDelete.jhtml");
	    		          }},
	    		],
	    		ctrlSelect:true,
	    		rownumbers:true,
	    		pageSize:20,
	    		pageList:[10,20,30,40,50,100],
	       	 	columns:[[
	       	 			{field:'ck',checkbox:true},
						{field:'certificate',title:'产权证号',width:100,align:'center'},
						{field:'owner',title:'所有人',width:100,align:'center'},
						{field:'isCommon',title:'共有情况',width:100,align:'center',formatter: function(value,row,index){
	                        if(value == '1'){
	                            return "是";
	                        }else if (value == '0'){
	                            return "否";
	                        }},
						},
						{field:'area',title:'面积',width:100,align:'center'},
						{field:'housingNatureName',title:'房屋性质',width:100,align:'center'},
						{field:'areaShowName',title:'所在地区',width:100,align:'center'},
						{field:'plotPlotName',title:'小区',width:100,align:'center'},
						{field:'houseAddress',title:'房屋地址',width:100,align:'center'},
						{field:'havaLandCertificate',title:'有无土地证',width:100,align:'center',formatter: function(value,row,index){
	                        if(value == '1'){
	                            return "有";
	                        }else if(value == '0'){
	                            return "无";
	                        }},
						},
						{field:'landCertificateNumber',title:'土地证号',width:100,align:'center'},
						{field:'landNatureName',title:'土地性质',width:100,align:'center'},
						{field:'propertyValue',title:'房产价值',width:100,align:'center'}
	       		]]
	    	});
			//收款账号信息
            $("#orderReceivablesList").datagrid({
                pagination:true,
                fit:true,
                border:false,
                toolbar: [
                    {iconCls: 'icon-add',text:'添加',handler: function(){orderDetails.addReceivables();}},
                    {iconCls: 'icon-edit',text:'修改',handler: function(){orderDetails.updateReceivables();}},
                    {iconCls: 'icon-remove',text:'删除',handler: function(){
                        if(orderStatus==5){
                            $.messager.alert('提示','订单已结算','warning');
                            return;
                        }
                        if(orderAuditStatus!=1){
                            $.messager.alert('提示','非草稿状态不能操作','warning');
                            return;
                        }
                        orderDetails.deleteBtn("#orderReceivablesList",ctx + "/myWorkbench/orderReceivables/mulDelete.jhtml");
                    }},
                ],
                ctrlSelect:true,
                rownumbers:true,
                pageSize:20,
                pageList:[10,20,30,40,50,100],
                columns:[[
                    {field:'ck',checkbox:true},
                    {field:'accountType',title:'账户类型',width:100,align:'center',formatter: function(value,row,index){
                        if(value == 1){
                            return "公司";
                        }else if (value == 2){
                            return "客户";
                        }
                    }},
                    {field:'accountName',title:'姓名',width:100,align:'center'},
                    {field:'accountNumber',title:'卡号',width:100,align:'center'},
                    {field:'accountBank',title:'开户行',width:100,align:'center'},
                    {field:'reason',title:'理由',width:100,align:'center'}
                ]]
            });
            //借款
            $("#orderLoanList").datagrid({
                pagination:true,
                fit:true,
                border:false,
                toolbar: [
                    {iconCls: 'icon-add',text:'添加',handler: function(){orderDetails.addLoan();}},
                    {iconCls: 'icon-edit',text:'修改',handler: function(){orderDetails.updateLoan();}},
                    {iconCls: 'icon-edit',text:'送审',handler: function(){orderDetails.approvalLoan()}},
                    {iconCls: 'icon-edit',text:'跟踪',handler: function(){orderDetails.traceLoan()}},
                    {iconCls: 'icon-edit',text:'审核历史',handler: function(){orderDetails.loanHistory()}},
                    {iconCls: 'icon-remove',text:'删除',handler: function(){orderDetails.deleteLoan()}},
                ],
                ctrlSelect:true,
                rownumbers:true,
                pageSize:20,
                pageList:[10,20,30,40,50,100],
                columns:[[
                    {field:'ck',checkbox:true},
                    {field:'amount',title:'借款金额（万元）',width:100,align:'center'},
                    {field:'rate',title:'借款利率',width:90,align:'center'},
                    {field:'rateType',title:'利率单位',width:50,align:'center',formatter: function(value,row,index){
                        switch (value){
                            case "1":return "日";
                            case "2":return "月";
                        }
                    }},
                    {field:'beginDate',title:'借款日期',width:100,align:'center'},
                    {field:'estimateRepayDate',title:'预计还款日期',width:100,align:'center'},
                    {field:'estimateInterest',title:'预计利息（元）',width:100,align:'center'},
                    {field:'estimateLoanDay',title:'预计借款天数',width:100,align:'center'},
                    {field:'estimateInterest',title:'利息（元）',width:80,align:'center'},
                    {field:'auditStatus',title:'状态',width:70,align:'center',formatter: function(value,row,index){
                        switch (value){
                            case "1":return "草稿";
                            case "2":return "审核中";
                            case "3":return "审核通过";
                            case "4":return "审核不通过";
                        }
                    }},
                    {field:'processInstanceId',hidden:true}
                ]]
            });

            //材料
            $("#materialsList").datagrid({
                pagination:true,
                fit:true,
                border:false,
                toolbar: [
                    {iconCls: 'icon-add',text:'添加',handler: function(){orderDetails.addMaterials();}},
                    {iconCls: 'icon-edit',text:'修改',handler: function(){orderDetails.updateMaterials();}},
                    {iconCls: 'icon-remove',text:'删除',handler: function(){
                        if(orderStatus==5){
                            $.messager.alert('提示','订单已结算','warning');
                            return;
                        }
                        orderDetails.deleteBtn("#materialsList",ctx + "/myWorkbench/orderMaterial/mulDelete.jhtml");
                    }},
                    {iconCls: 'icon-mini-tick',text:'已完成',handler: function(){
                        orderDetails.updateMaterialsStatus(1);
                    }},
                    {iconCls: 'icon-mini-cross',text:'未完成',handler: function(){
                        orderDetails.updateMaterialsStatus(0);
                    }},
                    {iconCls: 'icon-save',text:'下载附件',handler: function(){
                        if(orderStatus==5){
                            $.messager.alert('提示','订单已结算','warning');
                            return;
                        }
                        var selectedRow = $('#materialsList').datagrid('getSelected');
                        if(selectedRow==null){
                            $.messager.alert('提示','请选择数据行','warning');
                            return;
                        }
                        if(selectedRow.attachId==undefined || selectedRow.attachId==''){
                            $.messager.alert('提示','选择的数据无附件','warning');
                            return;
                        }
                        window.open(ctx + "/myWorkbench/orderMaterial/downloadFile.jhtml"+"?attachId="+selectedRow.attachId);
                    }},
                ],
                ctrlSelect:true,
                rownumbers:true,
                pageSize:20,
                pageList:[10,20,30,40,50,100],
                columns:[[
                    {field:'ck',checkbox:true},
                    {field:'name',title:'名称',width:100,align:'center'},
                    {field:'notice',title:'注意事项',width:90,align:'center'},
                    {field:'number',title:'份数',width:100,align:'center'},
                    {field:'isFinish',title:'是否完成',width:70,align:'center',formatter: function(value,row,index){
                        if (value =="1"){
                            return "已完成";
                        }else {
                            return "未完成";
                        }
                    }},
                    {field:'attachId',hidden:true}
                ]]
            });

            //合作银行
            $("#bankList").datagrid({
                pagination:true,
                fit:true,
                border:false,
                toolbar: [
                    {iconCls: 'icon-add',text:'添加',handler: function(){orderDetails.addBank();}},
                    {iconCls: 'icon-edit',text:'修改',handler: function(){orderDetails.updateBank();}},
                    {iconCls: 'icon-remove',text:'删除',handler: function(){
                        if(orderStatus==5){
                            $.messager.alert('提示','订单已结算','warning');
                            return;
                        }
                        if(orderAuditStatus !=3 ){
                            $.messager.alert('提示','订单审核未通过不能操作','warning');
                            return;
                        }
                        if(orderStatus >=3 ){
                            $.messager.alert('提示','订单已放款不能操作','warning');
                            return;
                        }
                        orderDetails.deleteBtn("#bankList",ctx + "/myWorkbench/orderBank/mulDelete.jhtml");
                    }},
                    {iconCls: 'icon-mini-tick',text:'签约',handler: function(){
                        orderDetails.updateBankStatus();
                    }},
                ],
                ctrlSelect:true,
                rownumbers:true,
                pageSize:20,
                pageList:[10,20,30,40,50,100],
                columns:[[
                    {field:'ck',checkbox:true},
                    {field:'bank_name',title:'银行',width:100,align:'center'},
                    {field:'subbranch_name',title:'支行',width:90,align:'center'},
                    {field:'address',title:'银行地址',width:100,align:'center'},
                    {field:'contacts',title:'联系人',width:100,align:'center'},
                    {field:'isSign',title:'是否签约银行',width:100,align:'center',formatter: function(value,row,index){
                        if (value =="1"){
                            return "已签约";
                        }else {
                            return "未签约";
                        }
                    }},
                    {field:'singTime',title:'签约时间',width:100,align:'center'},
                    {field:'note',title:'备注',width:70,align:'center'},
                    {field:'id',hidden:true}
                ]]
            });

            //评估公司
            $("#companyList").datagrid({
                pagination:true,
                fit:true,
                border:false,
                toolbar: [
                    {iconCls: 'icon-add',text:'添加',handler: function(){orderDetails.addCompany();}},
                    {iconCls: 'icon-edit',text:'修改',handler: function(){orderDetails.updateCompany();}},
                    {iconCls: 'icon-remove',text:'删除',handler: function(){
                        if(orderStatus==5){
                            $.messager.alert('提示','订单已结算','warning');
                            return;
                        }
                        orderDetails.deleteBtn("#companyList",ctx + "/myWorkbench/orderCompany/mulDelete.jhtml");
                    }},
                    {iconCls: 'icon-mini-tick',text:'标记为结算公司',handler: function(){
                        orderDetails.updateCompanyStatus(1);
                    }},
                ],
                ctrlSelect:true,
                rownumbers:true,
                pageSize:20,
                pageList:[10,20,30,40,50,100],
                columns:[[
                    {field:'ck',checkbox:true},
                    {field:'name',title:'公司名称',width:100,align:'center'},
                    {field:'address',title:'地址',width:90,align:'center'},
                    {field:'contacts',title:'联系人',width:100,align:'center'},
                    {field:'telephone',title:'联系电话',width:100,align:'center'},
                    {field:'bank',title:'开户行',width:100,align:'center'},
                    {field:'account',title:'账户',width:70,align:'center'},
                    {field:'cardNumber',title:'卡号',width:70,align:'center'},
                    {field:'isAssessment',title:'评估结算',width:70,align:'center',formatter: function(value,row,index){
                        if (value =="1"){
                            return "是";
                        }else {
                            return "否";
                        }
                    }},
                    {field:'assessmenFee',title:'评估费',width:70,align:'center'},
                    {field:'fee',title:'工本费',width:70,align:'center'},
                    {field:'note',title:'备注',width:70,align:'center'},
                    {field:'assessmentCompanyId',hidden:true}
                ]]
            });
		},
		initBtn:function(){
			order.initEditFormDialog("#receivablesForm","#orderReceivablesDialog","#orderReceivablesList");
            order.initEditFormDialog("#LoanForm","#orderLoanDialog","#orderLoanList");
            order.initEditFormDialog("#materialsForm","#orderMaterialsDialog","#materialsList");
            order.initEditFormDialog("#bankForm","#orderBankDialog","#bankList");
            order.initEditFormDialog("#companyForm","#orderCompanyDialog","#companyList");
            //支行
            $("#subbranch").textbox({
                onClickIcon: function (index) {
                    var bank = $("#bank").textbox("getValue");
                    if(bank == undefined || bank==""){
                        $.messager.alert('提示','请选择银行','warning');
                        return;
                    }
                    order.initBranchDialog("#subbranch");
                    $("#branchBankId").val(bank);
                    $("#branchDialog").dialog('open');
                }
            });
		},
		initDialog:function () {
            $("#propertyDialog").dialog({
                title:'选择房产',
                width : 600,
                height : document.documentElement.clientHeight-5<600?document.documentElement.clientHeight-5:600,
                closed : true,
                cache : false,
                modal : true,
            });
            $("#customerPropertyDialog").dialog({
                title:'编辑产权信息',
                width : 700,
                height : document.documentElement.clientHeight-5<500?document.documentElement.clientHeight-5:500,
                closed : true,
                cache : false,
                modal : true,
                buttons:[{
                    text:'保存',
                    iconCls:'icon-save',
                    handler:function(){
                        $("#customerPropertyForm").submit();
                    }
                },{
                    text:'退出',
                    iconCls:'icon-exit',
                    handler:function(){
                        $("#customerPropertyDialog").dialog('close');
                    }
                }],
                onClose:function(){
                    $("input[name='attachName']").val("");
                    $("#attachName").filebox('clear');
                    $("#customerPropertyDialog").form('clear');
                }
            });
            $("#orderReceivablesDialog").dialog({
                title:'新增收款账号',
                width : 280,
                height : 340,
                closed : true,
                cache : false,
                modal : true,
                buttons:[{
                    text:'保存',
                    iconCls:'icon-save',
                    handler:function(){
                    	$("#receivablesOrderId").val($("#orderId").val());
                        $("#receivablesForm").submit();
                    }
                },{
                    text:'退出',
                    iconCls:'icon-exit',
                    handler:function(){
                        $("#orderReceivablesDialog").dialog('close');
                    }
                }],
            });
            $("#accountType").combobox({
                panelHeight: 'auto',
                editable:false,
                valueField: 'value',
                textField: 'text',
                data: [
                    {value: '1',text: '公司'},
                    {value: '2',text: '客户'}
                ]
            });
            $("#orderLoanDialog").dialog({
                title:'新增借款',
                width : 320,
                height : 420,
                closed : true,
                cache : false,
                modal : true,
                buttons:[{
                    text:'保存',
                    iconCls:'icon-save',
                    handler:function(){
                        $("#LoanOrderId").val($("#orderId").val());
                        $("#LoanForm").submit();
                    }
                },{
                    text:'退出',
                    iconCls:'icon-exit',
                    handler:function(){
                        $("#orderLoanDialog").dialog('close');
                    }
                }],
            });
            $("#orderMaterialsDialog").dialog({
                title:'新增相关材料',
                width : 320,
                height : 380,
                closed : true,
                cache : false,
                modal : true,
                buttons:[{
                    text:'保存',
                    iconCls:'icon-save',
                    handler:function(){
                        $("#materialsOrderId").val($("#orderId").val());
                        $("#materialsForm").submit();
                    }
                },{
                    text:'退出',
                    iconCls:'icon-exit',
                    handler:function(){
                        $("#orderMaterialsDialog").dialog('close');
                    }
                }],
            });
            $("#isFinish").combobox({
                panelHeight: 'auto',
                editable:false,
                valueField: 'value',
                textField: 'text',
                data: [
                    {value: '1',text: '已完成'},
                    {value: '0',text: '未完成'}
                ]
            });
            $("#orderBankDialog").dialog({
                title:'新增合作银行',
                width : 320,
                height : 330,
                closed : true,
                cache : false,
                modal : true,
                buttons:[{
                    text:'保存',
                    iconCls:'icon-save',
                    handler:function(){
                        $("#bankOrderId").val($("#orderId").val());
                        $("#bankForm").submit();
                    }
                },{
                    text:'退出',
                    iconCls:'icon-exit',
                    handler:function(){
                        $("#orderBankDialog").dialog('close');
                    }
                }],
            });
            app.initCombobox("#bank",ctx +"/common/util/getBankByList.jhtml");
            app.yesOrNoByIntCombobox("#isSign");
            $("#orderBankTimeDialog").dialog({
                title:'合作银行签约时间',
                width : 320,
                height : 200,
                closed : true,
                cache : false,
                modal : true,
                buttons:[{
                    text:'保存',
                    iconCls:'icon-save',
                    handler:function(){
                        var obid = $("#signOrderBankid").val();
                        var obtime = $("#singTime").val();
                        var content = {id:obid,time:obtime};
                        $.messager.progress();
                        CSIT.asyncCallService(ctx+"/myWorkbench/orderBank/updateStatus.jhtml",content,function(result){
                            if(result.isSuccess){
                                $('#bankList').datagrid('reload');
                                $("#orderBankTimeDialog").dialog('close');
                            }else{
                                $.messager.alert('错误',result.message,"error");
                            }
                            $.messager.progress('close');
                        });
                    }
                },{
                    text:'退出',
                    iconCls:'icon-exit',
                    handler:function(){
                        $("#orderBankTimeDialog").dialog('close');
                    }
                }],
            });

            $("#orderCompanyDialog").dialog({
                title:'新增评估公司',
                width : 320,
                height : 500,
                closed : true,
                cache : false,
                modal : true,
                buttons:[{
                    text:'保存',
                    iconCls:'icon-save',
                    handler:function(){
                        $("#companyOrderId").val($("#orderId").val());
                        $("#companyForm").submit();
                    }
                },{
                    text:'退出',
                    iconCls:'icon-exit',
                    handler:function(){
                        $("#orderCompanyDialog").dialog('close');
                    }
                }],
            });
            app.yesOrNoByIntCombobox("#isAssessment");
            app.initCombobox("#assessmentCompanyId",ctx +"/common/util/getCompany.jhtml");

            $("#loanHistoryDialog").dialog({
                title:'借款审核历史',
                width : 700,
                height : 500,
                closed : true,
                cache : false,
                modal : true
            });
            $("#rateType").combobox({
                panelHeight: 'auto',
                editable:false,
                valueField: 'value',
                textField: 'text',
                data: [
                    {value: '1',text: '日'},
                    {value: '2',text: '月'}
                ]
            });
            //自动计算时间差
            $(".loanDay").blur(function(){
                var begin = $("#beginDate").val();
                var end =$("#estimateRepayDate").val();
                if(begin==''||end==''){
                    return;
                }
                var dayNum = DateDiff2(end,begin);
                $("#estimateLoanDay").numberbox("setValue",dayNum);
            });
            //自动计算利息
            autoInterest=function () {
                var amount=$("#amount").numberbox("getValue");
                var rate=$("#rate").numberbox("getValue");
                var rateType=$("#rateType").combobox("getValue");
                var estimateLoanDay=$("#estimateLoanDay").numberbox("getValue");
                if(amount==''||rate==''||rateType==''||estimateLoanDay==''){
                    return;
                }
                var interest;
                if(rateType==1){
                    interest= amount*rate*estimateLoanDay*100;
                }
                else if(rateType==2){
                    interest= amount*rate*100/30*estimateLoanDay;
                }
                $("#estimateInterest").numberbox("setValue",interest);
            }
            $(".interest").textbox({
                onChange: function(value){
                    autoInterest();
                }
            });
            $("#rateType").combobox({
                onChange: function (n,o) {
                    autoInterest();
                }
            })
        },
		//删除通用函数
    	deleteBtn:function (listId,url) {
            var checkedRows = $(listId).datagrid('getChecked');
            if(checkedRows.length==0){
                $.messager.alert('提示','请选择删除的数据行','warning');
                return;
            }
            $.messager.confirm("提示","该操作将会删除信息，确定要删除选中的信息?",function(t){
                if(!t) return;
                var idArray = [];
                for(var i=0;i<checkedRows.length;i++){
                    idArray.push(checkedRows[i].id);
                }
                var ids = idArray.join(CSIT.join);
				var orderId = $("#orderId").val();
                var content = {ids:ids,orderId:orderId};
                $.messager.progress();
                CSIT.asyncCallService(url,content,function(result){
                    if(result.isSuccess){
                        $(listId).datagrid('reload');
                    }else{
                        $.messager.alert('错误',result.message,"error");
                    }
                    $.messager.progress('close');
                });
            });
        },
		//新增产权信息
    	selectProperty:function(){
            if(orderStatus==5){
                $.messager.alert('提示','订单已结算','warning');
                return;
            }
            if(orderAuditStatus!=1){
                $.messager.alert('提示','非草稿状态不能操作','warning');
                return;
            }
			orderDetails.initPropertyEditDialog();
            $("#propertyDialog").dialog('open');
		},
		//产权
		initPropertyEditDialog:function(){
			$('#propertyDialogList').datagrid({
				border:true,
				url:ctx + "/myWorkbench/customerProperty/listByPage.jhtml",
				rownumbers:true,
				pagination:true,
				fit:true,
				ctrlSelect:true,
				method:"POST",
                queryParams:{
                    "customerId":$("#customerNameEditHidden").val()
                },
				toolbar:[{
					text:'选择',
					iconCls:'icon-ok',
					handler:function(){
						var checkedRows = $('#propertyDialogList').datagrid('getChecked');
						if(checkedRows.length==0){
							$.messager.alert('提示','请选择数据行','warning');
							return;
						}
                        var idArray = [];
                        for(var i=0;i<checkedRows.length;i++){
                            idArray.push(checkedRows[i].id);
                        }
                        var ids = idArray.join(CSIT.join);
						var orderId = $("#orderId").val();
                        var content = {ids:ids,orderId:orderId};
                        $.messager.progress();
                        CSIT.asyncCallService(ctx+"/myWorkbench/orderProperty/create.jhtml",content,function(result){
                            if(result.isSuccess){
                                $("#orderPropertyList").datagrid('reload');
                            }else{
                                $.messager.alert('错误',result.message,"error");
                            }
                            $.messager.progress('close');
                            $("#propertyDialog").dialog('close');
                        });
					}
				}],
				columns:[[
                    {field:'ck',checkbox:true},
                    {field:'certificate',title:'产权证号',width:100,align:'center'},
                    {field:'owner',title:'所有人',width:100,align:'center'},
                    {field:'isCommon',title:'共有情况',width:100,align:'center',formatter: function(value,row,index){
                        if(value == 1){
                            return "是";
                        }else{
                            return "否";
                        }},
                    },
                    {field:'area',title:'面积',width:100,align:'center'},
                    {field:'housingNatureName',title:'房屋性质',width:100,align:'center'},
                    {field:'areaShowName',title:'所在地区',width:100,align:'center'},
                    {field:'plotPlotName',title:'小区',width:100,align:'center'},
                    {field:'houseAddress',title:'房屋地址',width:100,align:'center'},
                    {field:'havaLandCertificate',title:'有无土地证',width:100,align:'center',formatter: function(value,row,index){
                        if(value == 1){
                            return "有";
                        }else{
                            return "无";
                        }},
                    },
                    {field:'landCertificateNumber',title:'土地证号',width:100,align:'center'},
                    {field:'landNatureName',title:'土地性质',width:100,align:'center'},
                    {field:'propertyValue',title:'房产价值',width:100,align:'center'}
                ]],
			});

		},
		//新增收款账号
		addReceivables:function(){
            if(orderStatus==5){
                $.messager.alert('提示','订单已结算','warning');
                return;
            }
            if(orderAuditStatus!=1){
                $.messager.alert('提示','非草稿状态不能操作','warning');
                return;
            }
            $("#receivablesForm").form('clear');
			$("#orderReceivablesDialog").dialog('open');
            $("#receivablesForm").attr("action",ctx+"/myWorkbench/orderReceivables/create.jhtml");
		},
		//修改收款账号
		updateReceivables:function () {
            if(orderStatus==5){
                $.messager.alert('提示','订单已结算','warning');
                return;
            }
            if(orderAuditStatus!=1){
                $.messager.alert('提示','非草稿状态不能操作','warning');
                return;
            }
            var selectedRow = $('#orderReceivablesList').datagrid('getSelected');
            if(selectedRow==null){
                $.messager.alert('提示','请选择数据行','warning');
                return;
            }
            $("#receivablesForm").form('clear');
            $("#receivablesForm").form('load',selectedRow);
            $("#receivablesForm").attr("action",ctx+"/myWorkbench/orderReceivables/update.jhtml");
            $("#orderReceivablesDialog").dialog('open');
        },
        //新增借款
        addLoan:function(){
            if(orderStatus==5){
                $.messager.alert('提示','订单已结算','warning');
                return;
            }

            $("#LoanForm").form('clear');
            $("#orderLoanDialog").dialog('open');
            $("#LoanForm").attr("action",ctx+"/myWorkbench/orderLoan/create.jhtml");
        },
        //修改借款
        updateLoan:function () {
            if(orderStatus==5){
                $.messager.alert('提示','订单已结算','warning');
                return;
            }
            var selectedRow = $('#orderLoanList').datagrid('getSelected');
            if(selectedRow==null){
                $.messager.alert('提示','请选择数据行','warning');
                return;
            }
            if(selectedRow.auditStatus!=1){
                $.messager.alert('提示','只有草稿才能修改','warning');
                return;
            }
            $("#LoanForm").form('clear');
            $("#LoanForm").form('load',selectedRow);
            $("#LoanForm").attr("action",ctx+"/myWorkbench/orderLoan/update.jhtml");
            $("#orderLoanDialog").dialog('open');
        },
        //借款送审
        approvalLoan:function () {
            if(orderStatus==5){
                $.messager.alert('提示','订单已结算','warning');
                return;
            }
            var checkedRows = $('#orderLoanList').datagrid('getChecked');
            if(checkedRows.length==0){
                $.messager.alert('提示','请选择送审的数据行','warning');
                return;
            }
            $.messager.confirm("提示","该操作将会送审，确定要送审选中的信息?",function(t){
                if(!t) return;
                var idArray = [];
                for(var i=0;i<checkedRows.length;i++){
                    if(checkedRows[i].auditStatus!=1){
                        $.messager.alert('提示','只有草稿才能送审','warning');
                        return;
                    }
                    idArray.push(checkedRows[i].id);
                }
                var ids = idArray.join(CSIT.join);
                var content = {ids:ids};
                $.messager.progress();
                CSIT.asyncCallService(ctx+"/myWorkbench/orderLoan/mulApprove.jhtml",content,function(result){
                    if(result.isSuccess){
                        $('#orderLoanList').datagrid('reload');
                    }else{
                        $.messager.alert('错误',result.message,"error");
                    }
                    $.messager.progress('close');
                });
            });
        },
        //借款跟踪
        traceLoan:function () {
            if(orderStatus==5){
                $.messager.alert('提示','订单已结算','warning');
                return;
            }
            var checkedRows = $('#orderLoanList').datagrid('getChecked');
            if(checkedRows.length==0){
                $.messager.alert('提示','请选择送审的数据行','warning');
                return;
            }
            if(checkedRows[0].auditStatus==2||checkedRows[0].auditStatus==4){
                window.open(ctx+"/myWorkbench/orderLoan/trace.jhtml?id="+checkedRows[0].id);
            }else {
                $.messager.alert('提示','只有审核中和审核不通过的才能跟踪','warning');
            }
        },
        //借款送审历史
        loanHistory:function () {
            if(orderStatus==5){
                $.messager.alert('提示','订单已结算','warning');
                return;
            }
            var selectedRow = $('#orderLoanList').datagrid('getSelected');
            if(selectedRow==null){
                $.messager.alert('提示','请选择数据行','warning');
                return;
            }
            if(selectedRow.auditStatus==1){
                $.messager.alert('提示','草稿状态无审核历史','warning');
                return;
            }
            $('#approvalList').datagrid({
                border:true,
                url:ctx + "/myWorkbench/approval/commentlistByPage.jhtml",
                pagination:true,
                fit:true,
                ctrlSelect:true,
                method:"POST",
                queryParams:{id:selectedRow.processInstanceId},
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
            $("#loanHistoryDialog").dialog('open');
        },
        //借款删除
        deleteLoan:function () {
            if(orderStatus==5){
                $.messager.alert('提示','订单已结算','warning');
                return;
            }
            var checkedRows = $("#orderLoanList").datagrid('getChecked');
            if(checkedRows.length==0){
                $.messager.alert('提示','请选择删除的数据行','warning');
                return;
            }
            for (var i;i<checkedRows.length;i++){
                if(checkedRows[i].auditStatus!=1){
                    $.messager.alert('提示','草稿状态才能删除','warning');
                    return;
                }
            }
            orderDetails.deleteBtn("#orderLoanList",ctx + "/myWorkbench/orderLoan/mulDelete.jhtml");
        },
        //新增材料
        addMaterials:function(){
            if(orderStatus==5){
                $.messager.alert('提示','订单已结算','warning');
                return;
            }
            $("#materialsForm").form('clear');
            $("#orderMaterialsDialog").dialog('open');
            $("#materialsForm").attr("action",ctx+"/myWorkbench/orderMaterial/create.jhtml");
        },
        //修改材料
        updateMaterials:function () {
            if(orderStatus==5){
                $.messager.alert('提示','订单已结算','warning');
                return;
            }
            var selectedRow = $('#materialsList').datagrid('getSelected');
            if(selectedRow==null){
                $.messager.alert('提示','请选择数据行','warning');
                return;
            }
            $("#materialsForm").form('clear');
            $("#materialsForm").form('load',selectedRow);
            $("#materialsForm").attr("action",ctx+"/myWorkbench/orderMaterial/update.jhtml");
            $("#orderMaterialsDialog").dialog('open');
        },
        //修改材料状态
        updateMaterialsStatus:function (status) {
            if(orderStatus==5){
                $.messager.alert('提示','订单已结算','warning');
                return;
            }
            var checkedRows = $('#materialsList').datagrid('getChecked');
            if(checkedRows.length==0){
                $.messager.alert('提示','请选择设置的数据行','warning');
                return;
            }
            $.messager.confirm("提示","该操作将会修改状态，确定要修改选中的信息?",function(t){
                if(!t) return;
                var idArray = [];
                for(var i=0;i<checkedRows.length;i++){
                    idArray.push(checkedRows[i].id);
                }
                var ids = idArray.join(CSIT.join);
                var content = {ids:ids,isFinish:status};
                $.messager.progress();
                CSIT.asyncCallService(ctx+"/myWorkbench/orderMaterial/updateStatus.jhtml",content,function(result){
                    if(result.isSuccess){
                        $('#materialsList').datagrid('reload');
                    }else{
                        $.messager.alert('错误',result.message,"error");
                    }
                    $.messager.progress('close');
                });
            });
        },
        //新增合作银行
        addBank:function(){
            if(orderStatus==5){
                $.messager.alert('提示','订单已结算','warning');
                return;
            }
            if(orderAuditStatus !=3 ){
                $.messager.alert('提示','订单审核未通过不能操作','warning');
                return;
            }
            if(orderStatus >=3 ){
                $.messager.alert('提示','订单已放款不能操作','warning');
                return;
            }
            $("#bankForm").form('clear');
            $("#orderBankDialog").dialog('open');
            $("#bankForm").attr("action",ctx+"/myWorkbench/orderBank/create.jhtml");
        },
        //修改合作银行
        updateBank:function () {
            if(orderStatus==5){
                $.messager.alert('提示','订单已结算','warning');
                return;
            }
            if(orderAuditStatus !=3 ){
                $.messager.alert('提示','订单审核未通过不能操作','warning');
                return;
            }
            if(orderStatus >=3 ){
                $.messager.alert('提示','订单已放款不能操作','warning');
                return;
            }
            var selectedRow = $('#bankList').datagrid('getSelected');
            if(selectedRow==null){
                $.messager.alert('提示','请选择数据行','warning');
                return;
            }
            $("#bankForm").form('clear');
            $("#bankForm").form('load',selectedRow);
            $("#isSign").combobox("setValue",selectedRow.isSign);
            $("#bank").combobox("setValue",selectedRow.bankId);
            CSIT.asyncCallService(ctx+"/common/util/getBranchById.jhtml",{id:selectedRow.subbranchId},function(data){
                if(data!=null){
                    $("#subbranchHidden").val(data.id);
                    $("#subbranch").textbox("setValue",data.subbranchName);
                }
            });
            $("#bankForm").attr("action",ctx+"/myWorkbench/orderBank/update.jhtml");
            $("#orderBankDialog").dialog('open');
        },
        //修改合作银行状态
        updateBankStatus:function () {
            if(orderStatus==5){
                $.messager.alert('提示','订单已结算','warning');
                return;
            }
            if(orderAuditStatus !=3 ){
                $.messager.alert('提示','订单审核未通过不能操作','warning');
                return;
            }
            if(orderStatus >=3 ){
                $.messager.alert('提示','订单已放款不能操作','warning');
                return;
            }
            var selectedRow = $('#bankList').datagrid('getSelected');
            if(selectedRow==null){
                $.messager.alert('提示','请选择数据行','warning');
                return;
            }
            $("#signOrderBankid").val(selectedRow.id);
            $("#orderBankTimeDialog").dialog('open');
        },
        //新增评估公司
        addCompany:function(){
            if(orderStatus==5){
                $.messager.alert('提示','订单已结算','warning');
                return;
            }
            $("#companyForm").form('clear');
            $("#orderCompanyDialog").dialog('open');
            $("#companyForm").attr("action",ctx+"/myWorkbench/orderCompany/create.jhtml");
        },
        //修改评估公司
        updateCompany:function () {
            if(orderStatus==5){
                $.messager.alert('提示','订单已结算','warning');
                return;
            }
            var selectedRow = $('#companyList').datagrid('getSelected');
            if(selectedRow==null){
                $.messager.alert('提示','请选择数据行','warning');
                return;
            }
            $("#companyForm").form('clear');
            $("#companyForm").form('load',selectedRow);
            $("#assessmentCompanyId").combobox("setValue",selectedRow.assessmentCompanyId);
            $("#companyForm").attr("action",ctx+"/myWorkbench/orderCompany/update.jhtml");
            $("#orderCompanyDialog").dialog('open');
        },
        //修改评估公司状态
        updateCompanyStatus:function (status) {
            if(orderStatus==5){
                $.messager.alert('提示','订单已结算','warning');
                return;
            }
            var checkedRows = $('#companyList').datagrid('getChecked');
            if(checkedRows.length==0){
                $.messager.alert('提示','请选择设置的数据行','warning');
                return;
            }
            $.messager.confirm("提示","该操作将会修改状态，确定要修改选中的信息?",function(t){
                if(!t) return;
                var idArray = [];
                for(var i=0;i<checkedRows.length;i++){
                    idArray.push(checkedRows[i].id);
                }
                var ids = idArray.join(CSIT.join);
                var content = {ids:ids,isAssessment:status};
                $.messager.progress();
                CSIT.asyncCallService(ctx+"/myWorkbench/orderCompany/updateStatus.jhtml",content,function(result){
                    if(result.isSuccess){
                        $('#companyList').datagrid('reload');
                    }else{
                        $.messager.alert('错误',result.message,"error");
                    }
                    $.messager.progress('close');
                });
            });
        }
};

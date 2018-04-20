var pay_approval_view = {

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
			pay_approval_view.verify();
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
	initInfo:function(){
		var url=ctx+"/financial/pay/getPayById.jhtml";
		var param={"id":payId};
		var data=CSIT.syncCallService(url,param);
		$("#editForm").form('load',data[0]);
		console.log(data[0])
		switch(data[0].isCost){
			case "0":$("#isCost").textbox("setValue","否"); break;
			case "1":$("#isCost").textbox("setValue","是"); break;
		}
		switch(data[0].payObjectType){
		case "1":$("#payObjectType").textbox("setValue","评估公司"); break;
		case "2":$("#payObjectType").textbox("setValue","合作银行"); break;
		case "3":$("#payObjectType").textbox("setValue","其他合作伙伴"); break;
		case "4":$("#payObjectType").textbox("setValue","其他"); break;
	}
		$("#editForm").attr('action',ctx+"/financial/pay/update.jhtml");
	},
	init : function(params) {
		pay_approval_view.initEditDialog();
		pay_approval_view.initCRUDBtn();
		pay_approval_view.initInfo();
	}

};

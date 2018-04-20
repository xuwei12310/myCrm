var order_approval_view = {
    init: function (params) {
        order_approval_view.initApprovalDialog();
        order_approval_view.initBtn();
    },
    initBtn:function(){
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

        $('#infoTabs').tabs({
            border:false,
            onSelect:function(title,index){
                switch (title){
                    case "放款抵押":
                        getDataGrid("#orderPropertyList",ctx + "/myWorkbench/orderProperty/listByPage.jhtml");
                        break;
                    case "收款账号":
                        getDataGrid("#orderReceivablesList",ctx + "/myWorkbench/orderReceivables/listByPage.jhtml");
                        break;
                }
            }
        });
        //根据当前订单获取表格内容
        function getDataGrid(id,url) {
            var data=$(id).datagrid('getData');
            if(data.total == 0){
                $(id).datagrid({
                    url:url,
                    queryParams: {
                        "order.id": orderId
                    }
                });
            }
        }
    },
    initApprovalDialog:function(){
        var url=ctx + "/myWorkbench/order/selectOneById.jhtml";
        var data = CSIT.syncCallService(url, {"orderId":orderId});
        if(data == undefined || data == ''){
            $.messager.alert('提示','订单已被删除','warning');
            return;
        }
        $("#editModForm").form('clear');
        $("#editModForm").form('load',data);
        $("#productIdEdit").textbox("setValue",data.product==undefined?"":data.product.name);
        $("#customerNameEdit").textbox("setValue",data.customer==undefined?"":data.customer.customerName);
        $("#ownerIdEdit").textbox("setValue",data.owner==undefined?"":data.owner.name);
        $("#csAssistantIdEdit").textbox("setValue",data.csAssistant==undefined?"":data.csAssistant.name);
        $("#csPrincipalIdEdit").textbox("setValue",data.csPrincipal==undefined?"":data.csPrincipal.name);
        $("#followUserIdEdit").textbox("setValue",data.followUser==undefined?"":data.followUser.name);

        $("#editLoanForm").form('clear');
        $("#editLoanForm").form('load',data);
        $("#bankId").textbox("setValue",data.bank==undefined?"":data.bank.bankName);
        $("#repayWayId").textbox("setValue",data.repayWay==undefined?"":data.repayWay.name);
        $("#branchId").textbox("setValue",data.branch==undefined?"":data.branch.subbranchName);
        $("#matchmakerId").textbox("setValue",data.matchmaker==undefined?"":data.matchmaker.name);

        $("#orderPropertyList").datagrid({
            pagination:true,
            fit:true,
            border:false,
            ctrlSelect:true,
            rownumbers:true,
            pageSize:20,
            pageList:[10,20,30,40,50,100],
            columns:[[
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
            ]]
        });
        $("#orderReceivablesList").datagrid({
            pagination:true,
            fit:true,
            border:false,
            ctrlSelect:true,
            rownumbers:true,
            pageSize:20,
            pageList:[10,20,30,40,50,100],
            columns:[[
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
    },
};

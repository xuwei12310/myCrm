var order_approval_edit = {
    init: function (params) {
        order_approval_edit.initBasic();
        order_approval_edit.initApprovalDialog();
        order_approval_edit.initBtn();
    },
    initBasic:function () {
        var url=ctx + "/myWorkbench/order/selectOneById.jhtml";
        var data = CSIT.syncCallService(url, {"orderId":orderId});
        if(data == undefined || data == ''){
            $.messager.alert('提示','订单已被删除','warning');
            return;
        }

        app.initCombobox("#productIdEdit",ctx + "/common/util/getDictByType.jhtml",{type:"product"});
        order_approval_edit.initCsAssistantDialog("#csAssistantIdEdit");
        order_approval_edit.initCsPrincipalDialog("#csPrincipalIdEdit");
        order_approval_edit.initFollowUserDialog("#followUserIdEdit");
        order_approval_edit.initOwnerDialog("#ownerIdEdit");
        //基本信息初始化
        $("#editModForm").form('clear');
        $("#editModForm").form('load',data);
        $("#productIdEdit").combobox("setValue",data.product.id==undefined?"":data.product.id);
        CSIT.asyncCallService(ctx+"/common/util/getCustomerNameById.jhtml",{id:data.customer.id},function(data){
            if(data!=undefined && data!=''){
                $("#customerNameEdit").textbox("setValue",data.customerName);
            }
        });
        CSIT.asyncCallService(ctx+"/common/util/getUserNameById.jhtml",{id:data.owner.id},function(data){
            if(data!=undefined && data!=''){
                $("#ownerIdEdit").textbox("setValue",data.name);
                $("#ownerIdEditHidden").val(data.id);
            }
        });
        CSIT.asyncCallService(ctx+"/common/util/getUserNameById.jhtml",{id:data.csAssistant.id},function(data){
            if(data!=undefined && data!=''){
                $("#csAssistantIdEdit").textbox("setValue",data.name);
                $("#csAssistantIdEditHidden").val(data.id);
            }
        });
        CSIT.asyncCallService(ctx+"/common/util/getUserNameById.jhtml",{id:data.csPrincipal.id},function(data){
            if(data!=undefined && data!=''){
                $("#csPrincipalIdEdit").textbox("setValue",data.name);
                $("#csPrincipalIdEditHidden").val(data.id);
            }
        });
        CSIT.asyncCallService(ctx+"/common/util/getUserNameById.jhtml",{id:data.followUser.id},function(data){
            if(data!=undefined && data!=''){
                $("#followUserIdEdit").textbox("setValue",data.name);
                $("#followUserIdEditHidden").val(data.id);
            }
        });
        //贷款方案初始化
        $("#editLoanForm").form('clear');
        $("#editLoanForm").form('load',data);
        app.initCombobox("#bankId",ctx +"/common/util/getBankByList.jhtml");
        app.initCombobox("#repayWayId",ctx +"/common/util/getDictByType.jhtml",{type:"repayWay"});
        order_approval_edit.initMatchmakerDialog("#matchmakerId");
        if(data.bank!=undefined){
            $("#bankId").combobox("setValue",data.bank.id);
        }
        if(data.repayWay!=undefined){
            $("#repayWayId").combobox("setValue",data.repayWay.id);
        }
        if(data.branch != undefined){
            CSIT.asyncCallService(ctx+"/common/util/getBranchById.jhtml",{id:data.branch.id},function(data){
                if(data!=null){
                    $("#branchIdHidden").val(data.id);
                    $("#branchId").textbox("setValue",data.subbranchName);
                }
            });
        }
        if(data.matchmaker !=undefined && data.matchmaker != "" && data.matchmaker.id !=undefined && data.matchmaker.id !=''){
            CSIT.asyncCallService(ctx+"/common/util/getMatchmakerById.jhtml",{id:data.matchmaker.id},function(data){
                if(data!=null){
                    $("#matchmakerIdHidden").val(data.id);
                    $("#matchmakerId").textbox("setValue",data.name);
                }
            });
        }
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
        //弹窗按钮
        //客户
        order_approval_edit.initDialogClick("#customerNameEdit","#customerDialog",
            "#customerDialogList","#searchCustomerBtn","#searchCustomerForm");
        //客户助理
        order_approval_edit.initDialogClick("#csAssistantIdEdit","#csAssistantDialog",
            "#csAssistantDialogList","#searchCsAssistantBtn","#csAssistantForm");
        //客服负责人
        order_approval_edit.initDialogClick("#csPrincipalIdEdit","#csPrincipalDialog",
            "#csPrincipalDialogList","#searchCsPrincipalBtn","#csPrincipalForm");
        //跟单人
        order_approval_edit.initDialogClick("#followUserIdEdit","#followUserDialog",
            "#followUserDialogList","#searchFollowUserBtn","#followUserForm");
        //拥有人
        order_approval_edit.initDialogClick("#ownerIdEdit","#ownerDialog",
            "#ownerDialogList","#searchOwnerBtn","#ownerForm");
        //介绍人
        order_approval_edit.initDialogClick("#matchmakerId","#matchmakerDialog",
            "#matchmakerDialogList","#searchMatchmakerBtn","#matchmakerForm");

        //支行
        $("#branchId").textbox({
            onClickIcon: function (index) {
                var bank = $("#bankId").textbox("getValue");
                if(bank == undefined || bank==""){
                    $.messager.alert('提示','请选择银行','warning');
                    return;
                }
                order_approval_edit.initBranchDialog("#branchId");
                $("#branchBankId").val(bank);
                $("#branchDialog").dialog('open');
            }
        });
        //支行搜索
        $("#searchBranchBtn").click(function(){
            var content = $("#searchBranchForm").serializeObject();
            $("#branchDialogList").datagrid({
                queryParams : content
            });
        });
        //表单提交初始化
        order_approval_edit.initEditFormDialog("#editModForm");
        order_approval_edit.initEditFormDialog("#editLoanForm");
        //保存按钮
        $("#saveUpdateBtn").click(function(){
            $("#editModForm").submit();
        });
        $("#saveLoanBtn").click(function(){
            $("#editLoanForm").submit();
        });

        $(".serviceCost").numberbox({
            onChange:function () {
                var loan = $("#loanAmount").numberbox("getValue")==""?0:$("#loanAmount").numberbox("getValue");
                var percent = $("#serviceChargePercent").numberbox("getValue")==""?0:$("#serviceChargePercent").numberbox("getValue");
                var count = loan * 100 * percent;
                $("#serviceCharge").numberbox("setValue",count);
            }
        });

        $("#commissionRate").numberbox({
            onChange:function () {
                var money = $("#serviceCharge").numberbox("getValue")==""?0:$("#serviceCharge").numberbox("getValue");
                var rate = $("#commissionRate").numberbox("getValue")==""?0:$("#commissionRate").numberbox("getValue");
                $("#commissionAmount").numberbox("setValue",rate * money * 0.01);
            }
        });
        $("#commissionAmount").numberbox({
            onChange:function () {
                var money = $("#serviceCharge").numberbox("getValue")==""?0:$("#serviceCharge").numberbox("getValue");
                var amount = $("#commissionAmount").numberbox("getValue")==""?0:$("#commissionAmount").numberbox("getValue");
                $("#commissionRate").numberbox("setValue",amount / money *100);
            }
        });

        $("#loanMoney").numberbox({
            onChange:function(){
                var money = $("#loanMoney").numberbox("getValue")==""?0:$("#loanMoney").numberbox("getValue");
                var rate = $("#borrowServiceChargePercent").numberbox("getValue")==""?0:$("#borrowServiceChargePercent").numberbox("getValue");
                $("#borrowServiceCharge").numberbox("setValue",rate * money *100);
            }
        });
        $("#borrowServiceChargePercent").numberbox({
            onChange:function(){
                var money = $("#loanMoney").numberbox("getValue")==""?0:$("#loanMoney").numberbox("getValue");
                var rate = $("#borrowServiceChargePercent").numberbox("getValue")==""?0:$("#borrowServiceChargePercent").numberbox("getValue");
                $("#borrowServiceCharge").numberbox("setValue",rate * money *100);
            }
        });
    },
    initApprovalDialog:function(){
        //产权信息
        $("#orderPropertyList").datagrid({
            pagination:true,
            fit:true,
            border:false,
            toolbar: [
                {iconCls: 'icon-add',text:'添加',handler: function(){order_approval_edit.selectProperty();}},
                {iconCls: 'icon-remove',text:'删除',handler: function(){
                    order_approval_edit.deleteBtn("#orderPropertyList",ctx + "/myWorkbench/orderProperty/mulDelete.jhtml");
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
        //选择房产
        $("#propertyDialog").dialog({
            title:'选择房产',
            width : 600,
            height : document.documentElement.clientHeight-5<600?document.documentElement.clientHeight-5:600,
            closed : true,
            cache : false,
            modal : true,
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
    },
    //弹窗按钮事件绑定 通用
    initDialogClick:function ( inputId,dialogId,listId,searchBtnId,formId) {
        $(inputId).textbox({
            onClickIcon: function (index) {
                $(dialogId).dialog('open');
            }
        });
        $(searchBtnId).click(function(){
            var content = $(formId).serializeObject();
            $(listId).datagrid({
                queryParams : content
            });
        });
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
    initEditFormDialog:function (formId,closeId,reloadId) {
        $(formId).form({
            onSubmit: function(){
                $.messager.progress();
                var isValid = $(this).form('validate');
                if (!isValid){
                    $.messager.progress('close');
                }
                return isValid;
            },
            success:function(data){
                $.messager.progress('close');
                if(data==undefined||data==''){
                    $.messager.alert('提示','数据异常','error');
                }
                var data = eval('(' + data + ')');
                if (data.isSuccess){
                    $.messager.alert('提示','保存成功','info',function(){
                        if(closeId != undefined && closeId != ''){
                            $(closeId).dialog('close');
                        }
                        if(reloadId == undefined || reloadId==''){
                            $(app.id.listId).datagrid('reload');
                        }else {
                            $(reloadId).datagrid('reload');
                        }

                    });
                }else{
                    $.messager.alert('提示',data.message,'error');
                }
            }
        });
    },
    //新增产权信息
    selectProperty:function(){
        order_approval_edit.initPropertyEditDialog();
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
    //客户
    initCustomerDialog:function(inputId){
        $('#customerDialogList').datagrid({
            border:true,
            url:ctx + "/common/util/getCustomerByList.jhtml",
            rownumbers:true,
            pagination:true,
            fit:true,
            toolbar:[{
                text:'选择',
                iconCls:'icon-ok',
                handler:function(){
                    var selectedRow = $('#customerDialogList').datagrid('getSelected');
                    if(selectedRow==null){
                        $.messager.alert('提示','请选择数据行','warning');
                        return;
                    }
                    $(inputId).textbox("setValue",selectedRow.customerName);
                    $(inputId+"Hidden").val(selectedRow.id);
                    $("#customerDialog").dialog('close');
                }
            }],
            ctrlSelect:true,
            method:"POST",
            columns:[[
                {field:'customerName',title:'客户姓名',width:100,align:'center'},
                {field:'id',hidden:true}
            ]],
            onDblClickCell: function(index,field,value){
                var rows = $('#customerDialogList').datagrid('getRows');
                var selectedRow = rows[index];
                $(inputId).textbox("setValue",selectedRow.customerName);
                $(inputId+"Hidden").val(selectedRow.id);
                $("#customerDialog").dialog('close');
            }
        });
        var params = {
            title:'选择客户',
            width : 600,
            height : document.documentElement.clientHeight-5<500?document.documentElement.clientHeight-5:500,
            closed : true,
            cache : false,
            modal : true,
        };
        $("#customerDialog").dialog(params);
    },
    //客服助理
    initCsAssistantDialog:function(inputId){
        $('#csAssistantDialogList').datagrid({
            border:true,
            url:ctx + "/common/util/getUserByList.jhtml",
            rownumbers:true,
            pagination:true,
            fit:true,
            toolbar:[{
                text:'选择',
                iconCls:'icon-ok',
                handler:function(){
                    var selectedRow = $('#csAssistantDialogList').datagrid('getSelected');
                    if(selectedRow==null){
                        $.messager.alert('提示','请选择数据行','warning');
                        return;
                    }
                    $(inputId).textbox("setValue",selectedRow.name);
                    $(inputId+"Hidden").val(selectedRow.id);
                    $("#csAssistantDialog").dialog('close');
                }
            }],
            ctrlSelect:true,
            method:"POST",
            columns:[[
                {field:'username',title:'用户名',width:100,align:'center'},
                {field:'name',title:'姓名',width:100,align:'center'},
                {field:'id',hidden:true}
            ]],
            onDblClickCell: function(index,field,value){
                var rows = $('#csAssistantDialogList').datagrid('getRows');
                var selectedRow = rows[index];
                $(inputId).textbox("setValue",selectedRow.name);
                $(inputId+"Hidden").val(selectedRow.id);
                $("#csAssistantDialog").dialog('close');
            }
        });
        var params = {
            title:'选择客服助理',
            width : 600,
            height : document.documentElement.clientHeight-5<500?document.documentElement.clientHeight-5:500,
            closed : true,
            cache : false,
            modal : true,
        };
        $("#csAssistantDialog").dialog(params);
    },
    //客服负责人
    initCsPrincipalDialog:function(inputId){
        $('#csPrincipalDialogList').datagrid({
            border:true,
            url:ctx + "/common/util/getUserByList.jhtml",
            rownumbers:true,
            pagination:true,
            fit:true,
            toolbar:[{
                text:'选择',
                iconCls:'icon-ok',
                handler:function(){
                    var selectedRow = $('#csPrincipalDialogList').datagrid('getSelected');
                    if(selectedRow==null){
                        $.messager.alert('提示','请选择数据行','warning');
                        return;
                    }
                    $(inputId).textbox("setValue",selectedRow.name);
                    $(inputId+"Hidden").val(selectedRow.id);
                    $("#csPrincipalDialog").dialog('close');
                }
            }],
            ctrlSelect:true,
            method:"POST",
            columns:[[
                {field:'username',title:'用户名',width:100,align:'center'},
                {field:'name',title:'姓名',width:100,align:'center'},
                {field:'id',hidden:true}
            ]],
            onDblClickCell: function(index,field,value){
                var rows = $('#csPrincipalDialogList').datagrid('getRows');
                var selectedRow = rows[index];
                $(inputId).textbox("setValue",selectedRow.name);
                $(inputId+"Hidden").val(selectedRow.id);
                $("#csPrincipalDialog").dialog('close');
            }
        });
        var params = {
            title:'选择客服负责人',
            width : 600,
            height : document.documentElement.clientHeight-5<500?document.documentElement.clientHeight-5:500,
            closed : true,
            cache : false,
            modal : true,
        };
        $("#csPrincipalDialog").dialog(params);
    },
    //跟单人
    initFollowUserDialog:function(inputId){
        $('#followUserDialogList').datagrid({
            border:true,
            url:ctx + "/common/util/getUserByList.jhtml",
            rownumbers:true,
            pagination:true,
            fit:true,
            toolbar:[{
                text:'选择',
                iconCls:'icon-ok',
                handler:function(){
                    var selectedRow = $('#followUserDialogList').datagrid('getSelected');
                    if(selectedRow==null){
                        $.messager.alert('提示','请选择数据行','warning');
                        return;
                    }
                    $(inputId).textbox("setValue",selectedRow.name);
                    $(inputId+"Hidden").val(selectedRow.id);
                    $("#followUserDialog").dialog('close');
                }
            }],
            ctrlSelect:true,
            method:"POST",
            columns:[[
                {field:'username',title:'用户名',width:100,align:'center'},
                {field:'name',title:'姓名',width:100,align:'center'},
                {field:'id',hidden:true}
            ]],
            onDblClickCell: function(index,field,value){
                var rows = $('#followUserDialogList').datagrid('getRows');
                var selectedRow = rows[index];
                $(inputId).textbox("setValue",selectedRow.name);
                $(inputId+"Hidden").val(selectedRow.id);
                $("#followUserDialog").dialog('close');
            }
        });
        var params = {
            title:'选择跟单人',
            width : 600,
            height : document.documentElement.clientHeight-5<500?document.documentElement.clientHeight-5:500,
            closed : true,
            cache : false,
            modal : true,
        };
        $("#followUserDialog").dialog(params);
    },
    //拥有人
    initOwnerDialog:function(inputId){
        $('#ownerDialogList').datagrid({
            border:true,
            url:ctx + "/common/util/getUserByList.jhtml",
            rownumbers:true,
            pagination:true,
            fit:true,
            toolbar:[{
                text:'选择',
                iconCls:'icon-ok',
                handler:function(){
                    var selectedRow = $('#ownerDialogList').datagrid('getSelected');
                    if(selectedRow==null){
                        $.messager.alert('提示','请选择数据行','warning');
                        return;
                    }
                    $(inputId).textbox("setValue",selectedRow.name);
                    $(inputId+"Hidden").val(selectedRow.id);
                    $("#ownerDialog").dialog('close');
                }
            }],
            ctrlSelect:true,
            method:"POST",
            columns:[[
                {field:'username',title:'用户名',width:100,align:'center'},
                {field:'name',title:'姓名',width:100,align:'center'},
                {field:'id',hidden:true}
            ]],
            onDblClickCell: function(index,field,value){
                var rows = $('#ownerDialogList').datagrid('getRows');
                var selectedRow = rows[index];
                $(inputId).textbox("setValue",selectedRow.name);
                $(inputId+"Hidden").val(selectedRow.id);
                $("#ownerDialog").dialog('close');
            }
        });
        var params = {
            title:'选择拥有人',
            width : 600,
            height : document.documentElement.clientHeight-5<500?document.documentElement.clientHeight-5:500,
            closed : true,
            cache : false,
            modal : true,
        };
        $("#ownerDialog").dialog(params);
    },
    //介绍人
    initMatchmakerDialog:function(inputId){
        $('#matchmakerDialogList').datagrid({
            border:true,
            url:ctx + "/common/util/getOtherPartners.jhtml",
            rownumbers:true,
            pagination:true,
            fit:true,
            toolbar:[{
                text:'选择',
                iconCls:'icon-ok',
                handler:function(){
                    var selectedRow = $('#matchmakerDialogList').datagrid('getSelected');
                    if(selectedRow==null){
                        $.messager.alert('提示','请选择数据行','warning');
                        return;
                    }
                    $(inputId).textbox("setValue",selectedRow.name);
                    $(inputId+"Hidden").val(selectedRow.id);
                    $("#matchmakerDialog").dialog('close');
                }
            }],
            ctrlSelect:true,
            method:"POST",
            columns:[[
                {field:'name',title:'名称',width:100,align:'center'},
                {field:'telephone',title:'联系电话',width:100,align:'center'},
                {field:'address',title:'地址',width:100,align:'center'},
                {field:'note',title:'备注',width:100,align:'center'},
                {field:'id',hidden:true}
            ]],
            onDblClickCell: function(index,field,value){
                var rows = $('#matchmakerDialogList').datagrid('getRows');
                var selectedRow = rows[index];
                $(inputId).textbox("setValue",selectedRow.name);
                $(inputId+"Hidden").val(selectedRow.id);
                $("#matchmakerDialog").dialog('close');
            }
        });
        var params = {
            title:'选择介绍人',
            width : 600,
            height : document.documentElement.clientHeight-5<500?document.documentElement.clientHeight-5:500,
            closed : true,
            cache : false,
            modal : true,
        };
        $("#matchmakerDialog").dialog(params);
    },
    //支行
    initBranchDialog:function(inputId){
        $('#branchDialogList').datagrid({
            border:true,
            url:ctx + "/common/util/getBankSubbranchByList.jhtml",
            rownumbers:true,
            pagination:true,
            fit:true,
            queryParams:{
                "bankid":$("#bankId").combobox("getValue")
            },
            toolbar:[{
                text:'选择',
                iconCls:'icon-ok',
                handler:function(){
                    var selectedRow = $('#branchDialogList').datagrid('getSelected');
                    if(selectedRow==null){
                        $.messager.alert('提示','请选择数据行','warning');
                        return;
                    }
                    $(inputId).textbox("setValue",selectedRow.subbranchName);
                    $(inputId+"Hidden").val(selectedRow.id);
                    $("#branchDialog").dialog('close');
                }
            }],
            ctrlSelect:true,
            method:"POST",
            columns:[[
                {field : 'subbranchName', title : '支行名称', width : 100, align : 'center'},
                {field : 'bankCode', title : '代号', width : 100, align : 'center'},
                {field : 'address', title : '地址', width : 150, align : 'center'},
                {field : 'contacts', title : '银行联系人', width : 100, align : 'center'},
                {field : 'phone', title : '联系电话', width : 150, align : 'center'},
                {field : 'note', title : '备注', width : 200, align : 'center'}
            ]],
            onDblClickCell: function(index,field,value){
                var rows = $('#branchDialogList').datagrid('getRows');
                var selectedRow = rows[index];
                $(inputId).textbox("setValue",selectedRow.subbranchName);
                $(inputId+"Hidden").val(selectedRow.id);
                $("#branchDialog").dialog('close');
            }
        });
        var params = {
            title:'选择支行',
            width : 600,
            height : document.documentElement.clientHeight-5<500?document.documentElement.clientHeight-5:500,
            closed : true,
            cache : false,
            modal : true,
        };
        $("#branchDialog").dialog(params);
    },
    //新增收款账号
    addReceivables:function(){
        $("#receivablesForm").form('clear');
        $("#orderReceivablesDialog").dialog('open');
        $("#receivablesForm").attr("action",ctx+"/myWorkbench/orderReceivables/create.jhtml");
    },
    //修改收款账号
    updateReceivables:function () {
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

};

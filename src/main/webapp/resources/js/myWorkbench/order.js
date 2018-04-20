var orderAuditStatus;
var orderStatus;
var order = {
    init: function (params) {
    	order.initBtn();
        order.initEditDialog();
        order.initModEditDialog();
    	app.crud.initViewList({
       	 	columns:[[
                {field:'ck',checkbox:true},
                {field:'orderCode',title:'订单编号',width:100,align:'center'},
                {field:'customerName',title:'客户',width:100,align:'center'},
                {field:'signingDate',title:'签约日期',width:100,align:'center'},
                {field:'productName',title:'产品',width:100,align:'center'},
                {field:'bankName',title:'银行',width:100,align:'center'},
                {field:'subbranchName',title:'支行',width:100,align:'center'},
                {field:'loanAmount',title:'贷款金额（万元）',width:100,align:'center'},
                {field:'loanMoney',title:'放款金额',width:100,align:'center'},
                {field:'companyLoanAmount',title:'借款总金额（万元）',width:100,align:'center'},
                {field:'companyLoanInterest',title:'借款利息总额（元）',width:100,align:'center'},
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
                            case "4":return "已结算";
                            case "5":return "完结";
                        }
                    }},
                {field:'cost',title:'订单成本（元）',width:100,align:'center'},
                {field:'receiveAmount',title:'应收金额（元）',width:100,align:'center'},
                {field:'payAmount',title:'应付金额（元）',width:100,align:'center'},
                {field:'csAssistantName',title:'客服助理',width:100,align:'center'},
                {field:'csPrincipalName',title:'客服负责人',width:100,align:'center'},
                {field:'followUserName',title:'跟单人',width:100,align:'center'},
                {field:'ownerName',title:'拥有人',width:100,align:'center'},
                {field:'createName',title:'创建人',width:100,align:'center'},
					/*{field:'lendingRate',title:'月利率（厘/月）',width:100,align:'center'},
					{field:'loanTerm',title:'贷款年限',width:100,align:'center'},
					{field:'repayWayId',title:'还款方式',width:100,align:'center'},
					{field:'serviceChargePercent',title:'手续费百分比（%）',width:100,align:'center'},
					{field:'serviceCharge',title:'手续费（元）',width:100,align:'center'},
					{field:'commissionAmount',title:'返佣金额',width:100,align:'center'},
					{field:'commissionReason',title:'返佣理由',width:100,align:'center'},
					{field:'receivablesAccountStatus',title:'收款账户审核状态（1草稿2审核中3审核通过4审核不通过）',width:100,align:'center'},
					{field:'loanDate',title:'放款日期',width:100,align:'center'},
					{field:'settlementCharge',title:'结算手续费',width:100,align:'center'},
					{field:'settlementClerk',title:'结算员',width:100,align:'center'},*/
                {field:'id',hidden:true},
       		]]
    	});
        app.initCombobox("#product",ctx + "/common/util/getDictByType.jhtml",{type:"product"});
        orderDetails.init();
    },
    initBtn:function(){
        //查询
        $("#searchBtn").click(function(){
            app.crud.search();
        });
        //清空查询条件
        $("#clearBtn").click(function(){
            $(app.id.searchFormId).form('clear');
        });
        //新增
        $("#addBtn").click(function(){
            order.initCustomerDialog("#customerName");
            app.initCombobox("#productId",ctx + "/common/util/getDictByType.jhtml",{type:"product"});
            order.initCsAssistantDialog("#csAssistantId");
            order.initCsPrincipalDialog("#csPrincipalId");
            order.initFollowUserDialog("#followUserId");
            order.initOwnerDialog("#ownerId");
            CSIT.asyncCallService(ctx+"/common/util/getUserInfo.jhtml",null,function(data){
                if(data!=null){
                    $("#ownerId").textbox("setValue",data.name);
                    $("#ownerIdHidden").val(data.id);
                    $("#followUserId").textbox("setValue",data.name);
                    $("#followUserIdHidden").val(data.id);
                }
            });
            order.orderAdd();
        });
        //修改
        $("#updateBtn").click(function(){
            order.orderUpdate();
        });
        //删除
        $("#deleteBtn").click(function(){
            app.crud.mulDelete();
        });

        //支行
        $("#branchId").textbox({
            onClickIcon: function (index) {
                var bank = $("#bankId").textbox("getValue");
                if(bank == undefined || bank==""){
                    $.messager.alert('提示','请选择银行','warning');
                    return;
                }
                order.initBranchDialog("#branchId");
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

        //客户
        order.initDialogClick("#customerName","#customerDialog",
            "#customerDialogList","#searchCustomerBtn","#searchCustomerForm");
        //客户助理
        order.initDialogClick("#csAssistantId","#csAssistantDialog",
            "#csAssistantDialogList","#searchCsAssistantBtn","#csAssistantForm");
        //客服负责人
        order.initDialogClick("#csPrincipalId","#csPrincipalDialog",
            "#csPrincipalDialogList","#searchCsPrincipalBtn","#csPrincipalForm");
        //跟单人
        order.initDialogClick("#followUserId","#followUserDialog",
            "#followUserDialogList","#searchFollowUserBtn","#followUserForm");
        //拥有人
        order.initDialogClick("#ownerId","#ownerDialog",
            "#ownerDialogList","#searchOwnerBtn","#ownerForm");
        //介绍人
        order.initDialogClick("#matchmakerId","#matchmakerDialog",
            "#matchmakerDialogList","#searchMatchmakerBtn","#matchmakerForm");

        $(".serviceCost").numberbox({
            onChange:function () {
                var loan = $("#loanAmount").numberbox("getValue")==""?0:$("#loanAmount").numberbox("getValue");
                var percent = $("#serviceChargePercent").numberbox("getValue")==""?0:$("#serviceChargePercent").numberbox("getValue");
                var count = loan * 100 * percent;
                $("#serviceCharge").numberbox("setValue",count);
                var rate = $("#commissionRate").numberbox("getValue")==""?0:$("#commissionRate").numberbox("getValue");
                $("#commissionAmount").numberbox("setValue",rate * count * 0.01);
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

                var CommissionRate = $("#borrowCommissionRate").numberbox("getValue")==""?0:$("#borrowCommissionRate").numberbox("getValue");
                $("#borrowCommissionAmount").numberbox("setValue",rate * money * CommissionRate);
            }
        });
        $("#borrowServiceChargePercent").numberbox({
            onChange:function(){
                var money = $("#loanMoney").numberbox("getValue")==""?0:$("#loanMoney").numberbox("getValue");
                var rate = $("#borrowServiceChargePercent").numberbox("getValue")==""?0:$("#borrowServiceChargePercent").numberbox("getValue");
                $("#borrowServiceCharge").numberbox("setValue",rate * money *100);

                var CommissionRate = $("#borrowCommissionRate").numberbox("getValue")==""?0:$("#borrowCommissionRate").numberbox("getValue");
                $("#borrowCommissionAmount").numberbox("setValue",rate * money * CommissionRate);
            }
        });

        $("#borrowServiceCharge").numberbox({
            onChange:function(){
                var money = $("#loanMoney").numberbox("getValue")==""?0:$("#loanMoney").numberbox("getValue");
                var charge = $("#borrowServiceCharge").numberbox("getValue")==""?0:$("#borrowServiceCharge").numberbox("getValue");
                if(money!=0){
                    $("#borrowServiceChargePercent").numberbox("setValue", charge / money *0.01 );
                }

                var CommissionRate = $("#borrowCommissionRate").numberbox("getValue")==""?0:$("#borrowCommissionRate").numberbox("getValue");
                $("#borrowCommissionAmount").numberbox("setValue",charge * money * CommissionRate);
            }
        });


        //审核按钮
        $("#approveBtn").click(function(){
            var checkedRows = $(app.id.listId).datagrid('getChecked');
            if(checkedRows.length==0){
                $.messager.alert('提示','请选择审核的数据行','warning');
                return;
            }
            $.messager.confirm("提示","确定要审核选中的记录?",function(t){
                if(!t) return;
                var idArray = [];
                for(var i=0;i<checkedRows.length;i++){
                    if(checkedRows[i].auditStatus!=1){
                        $.messager.alert('提示','只有草稿的才能送审','warning');
                        return ;
                    }
                    idArray.push(checkedRows[i].id);
                }
                var ids = idArray.join(CSIT.join);
                var content = {ids:ids};
                $.messager.progress();
                CSIT.asyncCallService(ctx+"/myWorkbench/order/mulApprove.jhtml",content,function(result){
                    if(result.isSuccess){
                        $(app.id.listId).datagrid('reload');
                    }else{
                        $.messager.alert('错误',result.message,"error");
                    }
                    $.messager.progress('close');
                });
            });
        });
        //跟踪
        $("#traceBtn").click(function () {
            var checkedRows = $(app.id.listId).datagrid('getChecked');
            if(checkedRows.length==0){
                $.messager.alert('提示','请选择跟踪的数据行','warning');
                return;
            }
            if(checkedRows[0].auditStatus==2||checkedRows[0].auditStatus==4){
                window.open(ctx+"/myWorkbench/order/trace.jhtml?id="+checkedRows[0].id);
            }else {
                $.messager.alert('提示','只有审核中和审核不通过的才能跟踪','warning');
            }
        });
        //完结按钮
        $("#endBtn").click(function(){
            var checkedRows = $(app.id.listId).datagrid('getChecked');
            if(checkedRows.length==0){
                $.messager.alert('提示','请选择审核的数据行','warning');
                return;
            }
            if(checkedRows.status==4){
                $.messager.alert('提示','已结算的订单才能完结','warning');
                return;
            }
            $.messager.confirm("提示","确定要完结选中的记录?",function(t){
                if(!t) return;
                var idArray = [];
                for(var i=0;i<checkedRows.length;i++){
                    if(checkedRows[i].auditStatus!=3||checkedRows[i].status!=4){
                        $.messager.alert('提示','只有审核通过且已结算的才能完结','warning');
                        return ;
                    }
                    idArray.push(checkedRows[i].id);
                }
                var ids = idArray.join(CSIT.join);
                var content = {ids:ids};
                $.messager.progress();
                CSIT.asyncCallService(ctx+"/myWorkbench/order/signEnd.jhtml",content,function(result){
                    if(result.isSuccess){
                        $(app.id.listId).datagrid('reload');
                    }else{
                        $.messager.alert('错误',result.message,"error");
                    }
                    $.messager.progress('close');
                });
            });
        });

        $("#orderProgressDialog").dialog({
            title:'订单进度',
            width : 300,
            height : 530,
            closed : true,
            cache : false,
            modal : true
        });

        //订单进度按钮
        $("#progressBtn").click(function(){
            var checkedRows = $(app.id.listId).datagrid('getChecked');
            if(checkedRows.length==0){
                $.messager.alert('提示','请选择审核的数据行','warning');
                return;
            }

            CSIT.asyncCallService(ctx+"/myWorkbench/order/viewProgress.jhtml",{id:checkedRows[0].id},function(data){
                if(data.isSuccess){
                    var orderData = data.data.orderData;
                    var progressData = data.data.sheduleData;
                    $("#orderCode").textbox("setValue",orderData.orderCode);
                    $("#orderCustomer").textbox("setValue",orderData.customer.customerName);
                    $("#orderProductName").textbox("setValue",orderData.product.name);
                    $("#orderAmount").textbox("setValue",orderData.loanAmount);
                    $("#orderLoan").textbox("setValue",orderData.loanMoney);
                    $("#orderSignDate").textbox("setValue",orderData.signingDate);

                    var tr = $("#orderProgressTable tr");
                    console.log(tr);
                    if(tr.length>5){
                        for (var i=6;i<tr.length;i++){
                            tr[i].remove();
                        }
                    }
                    for (var i=0;i<progressData.length;i++){
                        var content= "<tr>" +
                            "<td class='tdFirstTitle'>"+progressData[i].schedule.scheduleName+"</td> " +
                            "<td class='tdFirstContent'>" +
                                "<input type='text' value='"+progressData[i].actualDate+"' style='width:100%' readonly " +
                            "</td> " +
                        "</tr>";
                        $("#orderProgressTable").append(content);
                    }
                }
            });
            $("#orderProgressDialog").dialog('open');
        });
    },
    orderAdd:function () {
        $("#editDialog").dialog('open');
        $("#editForm").form('clear');
        $("#editForm").attr('action',ctx + "/myWorkbench/order/create.jhtml");
        $("#editForm").form({
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
                var data = eval('(' + data + ')');
                if (data.isSuccess){
                    $.messager.alert('提示','保存成功','info',function(){
                        $("#addId").val(data.data.oid);
                        $("#editDialog").dialog('close');
                        $("#modEditDialog").dialog('open');
                        $('#updateTabs').tabs({
                            selected:0
                        });
                        $(app.id.listId).datagrid('reload');
                        var url=ctx + "/myWorkbench/order/selectOneById.jhtml";
                        var addRow = CSIT.syncCallService(url, {orderId:data.data.oid});
                        order.initDialogForm(addRow);
                    });
                }else{
                    $.messager.alert('提示',data.message,'error');
                }
            }
        });

    },
    orderUpdate:function () {
        var selectedRow = $('#list').datagrid('getSelected');
        if(selectedRow==null){
            $.messager.alert('提示','请选择数据行','warning');
            return;
        }
        $("#modEditDialog").dialog('open');
        $('#updateTabs').tabs({
            selected:0
        });
        var url=ctx + "/myWorkbench/order/selectOneById.jhtml";
        var data = CSIT.syncCallService(url, {orderId:selectedRow.id});
        order.initDialogForm(data);
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
                    $("#borrowMatchmakerId").textbox("setValue",data.name);

                }
            });
        }
    },
    //弹窗按钮事件绑定 通用
    initDialogClick:function ( inputId,dialogId,listId,searchBtnId,formId) {
        $(inputId).textbox({
            onClickIcon: function (index) {
                $(dialogId).dialog('open');
            }
        });
        $(inputId+"Edit").textbox({
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
    initEditDialog:function(){
        var params = {
            title:'新建订单',
            width : 350,
            height : 450,
            closed : true,
            cache : false,
            modal : true,
            buttons:[{
                text:'保存',
                iconCls:'icon-save',
                handler:function(){
                    $("#editForm").submit();
                }
            },{
                text:'退出',
                iconCls:'icon-exit',
                handler:function(){
                    $("#editDialog").dialog('close');
                }
            }],
        };
        $("#editDialog").dialog(params);
    },
    initDialogForm:function (data) {
        orderAuditStatus=data.auditStatus;
        orderStatus=data.status;
        if(orderAuditStatus!=1){
            $("#saveUpdateBtn").linkbutton('disable');
            $("#saveLoanBtn").linkbutton('disable');
        }else {
            $("#saveUpdateBtn").linkbutton('enable');
            $("#saveLoanBtn").linkbutton('enable');
        }

        if(orderStatus>=4 || orderAuditStatus!=3){
            $("#saveBorrowBtn").linkbutton('disable');
        }else {
            $("#saveBorrowBtn").linkbutton('enable');
        }


        order.initCsAssistantDialog("#csAssistantIdEdit");
        order.initCsPrincipalDialog("#csPrincipalIdEdit");
        order.initFollowUserDialog("#followUserIdEdit");
        order.initOwnerDialog("#ownerIdEdit");

        $("#editModForm").form('clear');
        $("#editModForm").form('load',data);
        $("#productIdEdit").textbox("setValue",data.product.name);

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
        $("#editLoanForm").form('clear');
        $("#editLoanForm").form('load',data);
        $("#customerNameEditHidden").val(data.customer.id);
        app.initCombobox("#bankId",ctx +"/common/util/getBankByList.jhtml");
        app.initCombobox("#repayWayId",ctx +"/common/util/getDictByType.jhtml",{type:"repayWay"});
        order.initMatchmakerDialog("#matchmakerId");
        order.initModEditForm();
    },
    initModEditDialog:function(){
        $("#saveUpdateBtn").click(function(){
            if(orderStatus==5){
                $.messager.alert('提示','订单已结算','warning');
                return;
            }
            if(orderAuditStatus!=1){
                $.messager.alert('提示','非草稿状态不能操作','warning');
                return;
            }
            $("#editModForm").submit();
        });
        $("#saveLoanBtn").click(function(){
            if(orderStatus==5){
                $.messager.alert('提示','订单已结算','warning');
                return;
            }
            if(orderAuditStatus!=1){
                $.messager.alert('提示','非草稿状态不能操作','warning');
                return;
            }
            $("#editLoanForm").submit();
        });
        $("#saveBorrowBtn").click(function(){
            if(orderAuditStatus!=3){
                $.messager.alert('提示','订单未审核通过','warning');
                return;
            }
            if(orderStatus==4){
                $.messager.alert('提示','订单已结算','warning');
                return;
            }
            if(orderStatus==5){
                $.messager.alert('提示','订单已完结','warning');
                return;
            }

            $("#editBorrowForm").submit();
        });
        //根据当前订单获取表格内容
        function getDataGrid(id,url) {
            var orderId = $("#orderId").val();
            $(id).datagrid({
                url:url,
                queryParams: {
                    "order.id": orderId
                }
            });
        }
        $('#updateTabs').tabs({
            border:false,
            onSelect:function(title,index){
                switch (title){
                    case "抵押房产":
                        getDataGrid("#orderPropertyList",ctx + "/myWorkbench/orderProperty/listByPage.jhtml");
                        break;
                    case "收款账号":
                        getDataGrid("#orderReceivablesList",ctx + "/myWorkbench/orderReceivables/listByPage.jhtml");
                        break;
                    case "借款":
                        getDataGrid("#orderLoanList",ctx + "/myWorkbench/orderLoan/listByPage.jhtml");
                        break;
                    case "订单材料":
                        getDataGrid("#materialsList",ctx + "/myWorkbench/orderMaterial/listByPage.jhtml");
                        break;
                    case "合作银行":
                        getDataGrid("#bankList",ctx + "/myWorkbench/orderBank/listByPage.jhtml");
                        break;
                    case "评估公司":
                        getDataGrid("#companyList",ctx + "/myWorkbench/orderCompany/listByPage.jhtml");
                        break;
                    case "放款":
                        var url=ctx + "/myWorkbench/order/selectOneById.jhtml";
                        var data = CSIT.syncCallService(url, {orderId:$("#orderId").val()});
                        $("#borrowId").val(data.id);
                        if(data.loanAmount!=undefined){
                            $("#borrowMoney").textbox("setValue",data.loanAmount);
                        }
                        if(data.loanMoney!=undefined){
                            $("#loanMoney").textbox("setValue",data.loanMoney);
                        }
                        if(data.loanDate!=undefined){
                            $("#loanDate").val(data.loanDate);
                        }
                        if(data.serviceChargePercent!=undefined){
                            $("#borrowServiceChargePercent").textbox("setValue",data.serviceChargePercent);
                        }
                        if(data.serviceCharge!=undefined){
                            $("#borrowServiceCharge").textbox("setValue",data.serviceCharge);
                        }
                        if(data.loanAmount!=undefined){
                            $("#borrowCommissionRate").textbox("setValue",data.commissionRate);
                        }
                        if(data.loanAmount!=undefined){
                            $("#borrowCommissionAmount").textbox("setValue",data.commissionAmount);
                        }
                        if(data.loanAmount!=undefined){
                            $("#borrowCommissionReason").textbox("setValue",data.commissionReason);
                        }
                        break;
                }
            }
        });
        var params = {
            title:'编辑订单信息',
            width : document.documentElement.clientWidth-5<1000?document.documentElement.clientWidth-5:1000,
            height : document.documentElement.clientHeight-5<770?document.documentElement.clientHeight-5:770,
            closed : true,
            cache : false,
            modal : true,
        };
        $("#modEditDialog").dialog(params);
    },
    initModEditForm:function(){
        order.initEditFormDialog("#editModForm");
        order.initEditFormDialog("#editLoanForm");
        order.initEditFormDialog("#editBorrowForm");
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
            },{
                text:'新增',
                iconCls:'icon-add',
                handler:function(){
                    $("#matchmakerAddForm").form('clear');
                    $("#matchmakerAddDialog").dialog('open');
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

        order.initEditFormDialog("#matchmakerAddForm","#matchmakerAddDialog","#matchmakerDialogList");
        $("#matchmakerAddDialog").dialog({
            title:'新增介绍人',
            width : 370,
            height : 305,
            closed : true,
            cache : false,
            modal : true,
            buttons:[{
                text:'保存',
                iconCls:'icon-save',
                handler:function(){
                    $("#matchmakerAddForm").submit();
                }
            },{
                text:'退出',
                iconCls:'icon-exit',
                handler:function(){
                    $("#matchmakerAddDialog").dialog('close');
                }
            }],
        });
        app.enableCombobox("#status");
    },
};

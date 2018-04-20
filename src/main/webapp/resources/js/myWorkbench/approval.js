var approval = {
    init: function (params) {
        $('#todoList').datagrid({
            border:true,
            url:ctx + "/myWorkbench/approval/todoListByPage.jhtml",
            rownumbers:true,
            pagination:true,
            singleSelect:true,
            fit:true,
            method:"POST",
            columns:[[
                {field:'applyUser',title:'提交人',width:100,align:'center'},
                {field:'createTime',title:'提交时间',width:100,align:'center',
                    formatter: function(value,row,index){
                        var str = value.split(" ");
                        return str[0];
                    }},
                {field:'procDefKey',title:'单据类型',width:100,align:'center'},
                {field:'procInstName',title:'内容摘要',width:300,align:'center'},
                {field:'auditStatus',hidden:true},
                {field:'url',hidden:true},
                {field:'taskId',hidden:true},
                {field:'id',hidden:true},
                {field:'formKey',hidden:true}
            ]],
            onDblClickCell: function(index,field,value){
                approvalBtnEvnet();
            }
        });
        $('#submitList').datagrid({
            border:true,
            url:ctx + "/myWorkbench/approval/submitListByPage.jhtml",
            rownumbers:true,
            pagination:true,
            singleSelect:true,
            fit:true,
            method:"POST",
            columns:[[
                {field:'processInstanceKey',title:'类型',width:100,align:'center'},
                {field:'applyUser',title:'申请人',width:100,align:'center',},
                {field:'processInstanceName',title:'摘要',width:300,align:'center'},
                {field:'taskName',title:'任务名称',width:100,align:'center'},
                {field:'action',title:'动作',width:100,align:'center',},
                {field:'time',title:'完成时间',width:150,align:'center'},
                {field:'msg',title:'批注',width:100,align:'center'},
                {field:'url',hidden:true},
                {field:'businessKey',hidden:true}
            ]],
            onDblClickCell: function(index,field,value){
                submitDetailsEvent();
            }
        });
        $('#doneList').datagrid({
            border:true,
            url:ctx + "/myWorkbench/approval/doneListByPage.jhtml",
            rownumbers:true,
            pagination:true,
            singleSelect:true,
            fit:true,
            method:"POST",
            columns:[[
                {field:'processInstanceKey',title:'类型',width:100,align:'center'},
                {field:'applyUser',title:'申请人',width:100,align:'center',},
                {field:'processInstanceName',title:'摘要',width:300,align:'center'},
                {field:'taskName',title:'任务名称',width:100,align:'center'},
                {field:'action',title:'动作',width:100,align:'center',},
                {field:'time',title:'完成时间',width:150,align:'center'},
                {field:'msg',title:'批注',width:100,align:'center'},
                {field:'url',hidden:true},
                {field:'businessKey',hidden:true}
            ]],
            onDblClickCell: function(index,field,value){
                doneDetailsEvent();
            }
        });
        approval.initBtn();
    },
    initBtn:function(){

        approvalBtnEvnet=function () {
            var checkedRows = $("#todoList").datagrid('getChecked');
            if(checkedRows.length==0){
                $.messager.alert('提示','请选择审批的数据行','warning');
                return;
            }
            var params = {
                title:'审批',
                width : document.documentElement.clientWidth-5>600?document.documentElement.clientWidth-20:600,
                height : document.documentElement.clientHeight-5>500?document.documentElement.clientHeight-20:500,
                closed : false,
                cache : false,
                modal : true,
            };
            var selected = checkedRows[0];
            $("#approvalDialogiframe").dialog(params);
            $("#dialogiframe").attr("src",ctx+selected.url+"?id="+selected.id+"&taskId="+selected.taskId+"&formKey="+selected.formKey);
            $("#approvalDialogiframe").dialog('open');
        };

        submitDetailsEvent=function () {
            var checkedRows = $("#submitList").datagrid('getChecked');
            if(checkedRows.length==0){
                $.messager.alert('提示','请选择审批的数据行','warning');
                return;
            }
            var params = {
                title:'审批详情',
                width : document.documentElement.clientWidth-5>600?document.documentElement.clientWidth-20:600,
                height : document.documentElement.clientHeight-5>500?document.documentElement.clientHeight-20:500,
                closed : false,
                cache : false,
                modal : true,
            };
            var selected = checkedRows[0];
            $("#approvalDialogiframe").dialog(params);
            $("#dialogiframe").attr("src",ctx+selected.url+"?id="+selected.businessKey+"&taskId="+selected.taskId+"&formKey="+selected.formKey);
            $("#approvalDialogiframe").dialog('open');
        };

        doneDetailsEvent=function () {
            var checkedRows = $("#doneList").datagrid('getChecked');
            if(checkedRows.length==0){
                $.messager.alert('提示','请选择审批的数据行','warning');
                return;
            }
            var params = {
                title:'审批详情',
                width : document.documentElement.clientWidth-5>600?document.documentElement.clientWidth-20:600,
                height : document.documentElement.clientHeight-5>500?document.documentElement.clientHeight-20:500,
                closed : false,
                cache : false,
                modal : true,
            };
            var selected = checkedRows[0];
            $("#approvalDialogiframe").dialog(params);
            $("#dialogiframe").attr("src",ctx+selected.url+"?id="+selected.businessKey+"&taskId="+selected.taskId+"&formKey="+selected.formKey);
            $("#approvalDialogiframe").dialog('open');
        };

        //审批按钮
        $("#approvalBtn").click(function () {
            approvalBtnEvnet();
        });
        //我提交的按钮
        $("#submitDetailsBtn").click(function () {
            submitDetailsEvent();
        });
        //已审批的92.详情按钮
        $("#doneDetailsBtn").click(function () {
            doneDetailsEvent();
        });

    },
    closeDialogiframe:function (index) {
        $("#approvalDialogiframe").dialog('close');
        switch (index){
            case 1:
                $('#todoList').datagrid('reload');
                break;
            case 2:
                $('#submitList').datagrid('reload');
                break;
            case 3:
                $('#doneList').datagrid('reload');
                break;
        }

    }
};

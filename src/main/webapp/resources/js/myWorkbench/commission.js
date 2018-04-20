var commission = {
    init: function (params) {
        commission.initBtn();
        commission.initEditDialog();
        commission.initCombobox();
    },
    initEditDialog:function(){
    	$("#list").datagrid({
			url:ctx + "/myWorkbench/commission/listByPage.jhtml",
			pagination:true,
			fit:true,
			border:false,
			toolbar: app.id.toolbarId,
			ctrlSelect:true,
			rownumbers:true,
			pageSize:20,
			pageList:[10,20,30,40,50,100],
   	 		columns:[[
				    {field:'ck',checkbox:true},
					{field:'name',title:'员工',width:100,align:'center'},
					{field:'total',title:'佣金总额',width:100,align:'center'},
					{field:'grant',title:'已发',width:100,align:'center'},
					{field:'ungrant',title:'未发',width:100,align:'center'},
					{field:'id',hidden:true}
			]],
			onDblClickCell: function(index,field,value){
                commission.details();
		   	}
		});

        $("#detailsDialog").dialog({
            title:'详情',
            width : document.documentElement.clientWidth-5<1000?document.documentElement.clientWidth-5:1000,
            height : document.documentElement.clientHeight-5<770?document.documentElement.clientHeight-5:770,
            closed : true,
            cache : false,
            modal : true
        });
        $("#grantList").datagrid({
            pagination:true,
            fit:true,
            border:false,
            ctrlSelect:true,
            rownumbers:true,
            pageSize:20,
            pageList:[10,20,30,40,50,100],
            columns:[[
                {field:'ck',checkbox:true},
                {field:'orderCode',title:'订单编号',width:100,align:'center'},
                {field:'customerName',title:'客户名称',width:100,align:'center'},
                {field:'loanDate',title:'放款日期',width:100,align:'center'},
                {field:'actual',title:'佣金金额',width:100,align:'center'}
            ]]
        });
        $("#ungrantList").datagrid({
            pagination:true,
            fit:true,
            border:false,
            ctrlSelect:true,
            rownumbers:true,
            pageSize:20,
            pageList:[10,20,30,40,50,100],
            columns:[[
                {field:'ck',checkbox:true},
                {field:'orderCode',title:'订单编号',width:100,align:'center'},
                {field:'customerName',title:'客户名称',width:100,align:'center'},
                {field:'loanDate',title:'放款日期',width:100,align:'center'},
                {field:'actual',title:'佣金金额',width:100,align:'center'}
            ]]
        });
    },
	initCombobox:function () {
        $("#department").combotree({
            animate:true,
            lines:true,
            dnd:false,
            parentField:"pid",
            url: ctx+"/sys/organization/getOrganizationByCompany"+'?companyId='+1
        });
        app.initCombobox("#owner",ctx + "/common/util/getUserByOrganization.jhtml",{organizationId:""});
        $("#department").combotree({
			onChange: function (n,o) {
                app.initCombobox("#owner",ctx + "/common/util/getUserByOrganization.jhtml",{organizationId:n});
        	}
        });
    },
    initBtn:function(){
    	//详情
    	$("#viewBtn").click(function(){
            commission.details();
    	});
    	//查询
    	$(app.id.searchBtnId).click(function(){
            commission.search();
    	});
    	//清空查询条件
    	$(app.id.clearBtnId).click(function(){
    		$(app.id.searchFormId).form('clear');
    	});
    },
    search:function(){
    	var isValid = $(app.id.searchFormId).form('validate');
		if (!isValid){
			return false;
		}
    	var content = $(app.id.searchFormId).serializeObject();
    	$(app.id.listId).datagrid({
    		queryParams:content
    	});
    },
    details:function () {
        var selectedRow = $('#list').datagrid('getSelected');
        if(selectedRow==null){
            $.messager.alert('提示','请选择数据行','warning');
            return;
        }
        var uid = selectedRow.id;
        $("#grantList").datagrid({
            url:ctx+"/myWorkbench/commission/grantOrderListByPage",
            queryParams: {
                "uid": uid,
				"type":1
            }
        });
        $("#ungrantList").datagrid({
            url:ctx+"/myWorkbench/commission/grantOrderListByPage",
            queryParams: {
                "uid": uid,
				"type":0
            }
        });
        $("#detailsDialog").dialog('open');
        $('#detailsTabs').tabs({
            selected:0
        });
    }
}

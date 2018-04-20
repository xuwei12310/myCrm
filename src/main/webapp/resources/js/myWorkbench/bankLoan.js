var bankLoan = {
    init: function (params) {
        bankLoan.initBtn();
        bankLoan.initEditDialog();
        bankLoan.initCombobox();
    },
    initEditDialog:function(){
    	$("#list").datagrid({
			url:ctx + "/myWorkbench/bankLoan/listByPage.jhtml",
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
					{field:'bankName',title:'银行',width:100,align:'center'},
					{field:'loanAmount',title:'贷款金额',width:100,align:'center'},
					{field:'companyLoanAmount',title:'借款金额',width:100,align:'center'},
					{field:'companyLoanInterest',title:'利息',width:100,align:'center'},
                    {field:'loanMoney',title:'放款金额',width:100,align:'center'},
                    {field:'serviceCharge',title:'手续费',width:100,align:'center'},
                    {field:'cost',title:'成本',width:100,align:'center'},
                    {field:'brokerageTotal',title:'佣金',width:100,align:'center'},
					{field:'id',hidden:true}
			]]
		});
    },
	initCombobox:function () {
        app.initCombobox("#product",ctx + "/common/util/getDictByType.jhtml",{type:"product"});
        app.initCombobox("#bank",ctx +"/common/util/getBankByList.jhtml");
    },
    initBtn:function(){
    	//查询
    	$(app.id.searchBtnId).click(function(){
            bankLoan.search();
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
    }
}

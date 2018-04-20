var user = {
    init: function (params) {
    	app.crud.initEditDialog({});
        app.crud.initCRUDBtn({
        	add:function(){
        	},
        	update:function(){
        	}
        });
    	app.crud.initViewList({
       	 	columns:[[
       		        {field:'ck',checkbox:true},
					{field:'name',title:'',width:100,align:'center'},
					{field:'idNumber',title:'',width:100,align:'center'},
					{field:'email',title:'',width:100,align:'center'},
					{field:'username',title:'',width:100,align:'center'},
					{field:'password',title:'',width:100,align:'center'},
					{field:'status',title:'',width:100,align:'center'},
					{field:'isAdmin',title:'',width:100,align:'center'},
					{field:'companyId',title:'公司',width:100,align:'center'},
					{field:'organizationId',title:'部门id',width:100,align:'center'},
					{field:'isLock',title:'',width:100,align:'center'},
					{field:'lockTime',title:'',width:100,align:'center'},
					{field:'loginCount',title:'',width:100,align:'center'},
					{field:'loginFailureCount',title:'',width:100,align:'center'},
					{field:'loginIp',title:'',width:100,align:'center'},
					{field:'loginTime',title:'',width:100,align:'center'},
					{field:'phone',title:'手机',width:100,align:'center'},
					{field:'pwdPrefix',title:'微信登录获取到的openid',width:100,align:'center'},
					{field:'pwdSuffix',title:'',width:100,align:'center'},
					{field:'wechat',title:'',width:100,align:'center'},
					{field:'wxOpenId',title:'微信登录获取到的openid',width:100,align:'center'},
					{field:'isSynchro',title:'是否已同步',width:100,align:'center'},
					{field:'id',hidden:true}
       		]]
    	});
    }
}

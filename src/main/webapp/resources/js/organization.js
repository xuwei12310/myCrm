var organization = {
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
					{field:'organizationName',title:'机构名称',width:100,align:'center'},
					{field:'orgType',title:'机构类型(1:公司，0:部门)',width:100,align:'center'},
					{field:'parentId',title:'父机构',width:100,align:'center'},
					{field:'parentIds',title:'父路径',width:100,align:'center'},
					{field:'id',hidden:true}
       		]]
    	});
    }
}

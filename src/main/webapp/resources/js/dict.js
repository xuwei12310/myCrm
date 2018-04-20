var dict = {
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
					{field:'dictType',title:'类型【会议人员类型participants_category单位类型company_category】',width:100,align:'center'},
					{field:'code',title:'编号',width:100,align:'center'},
					{field:'name',title:'名称',width:100,align:'center'},
					{field:'status',title:'状态1启用0禁用',width:100,align:'center'},
					{field:'nameEn',title:'名称_英文',width:100,align:'center'},
					{field:'isSys',title:'是否系统内置【是1、否0】',width:100,align:'center'},
					{field:'id',hidden:true}
       		]]
    	});
    }
}

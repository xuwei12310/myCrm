var roleResources = {
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
					{field:'roleId',title:'',width:100,align:'center'},
					{field:'resourcesId',title:'',width:100,align:'center'},
					{field:'id',hidden:true}
       		]]
    	});
    }
}

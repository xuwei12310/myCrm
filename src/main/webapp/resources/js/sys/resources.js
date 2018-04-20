var resources = {
    init: function (params) {
    	app.treeCRUD.initTree();
    	app.treeCRUD.initViewList({
       	 	columns:[[
       	 	    {field:'ck',checkbox:true},
      	        {field:'name',title:'名称',width:150,align:'center'},
      	        {field:'identity',title:'标识',width:200,align:'center'},
      	        {field:'url',title:'URL地址',width:200,align:'center'},
      	        {field:'icon',title:'图标',width:50,align:'center'},
      	        {field:'resourcesType',title:'类型',width:50,align:'center'}
       		]]
    	});
    	app.treeCRUD.initEditDialog({
        	height:470,
        	width:300,
            toolbar:null,
            buttons:[{
                text:'保存',
                iconCls:'icon-save',
                handler:function(){
                    $(app.id.editFormId).submit();
                }
            },{
                text:'退出',
                iconCls:'icon-exit',
                handler:function(){
                    $(app.id.editDialogId).dialog('close');
                }
            }],
        });
    	app.treeCRUD.initCRUDBtn({
    		add:function(){
    			app.yesOrNoCombobox('#isCRUD');
    	    	app.stateCombobox("#status");
    	    	app.resourcesTypeCombobox("#resourcesType");
    	    	$('#status').combobox('setValue','启用');
    	    	$('#isCRUD').combobox('setValue','否');
    	    	$('#resourcesType').combobox('setValue','菜单');
    	    	$('#isCRUDDiv').show();
        	},
        	update:function(){
            	app.stateCombobox("#status");
            	app.resourcesTypeCombobox("#resourcesType");
            	app.yesOrNoCombobox('#isCRUD');
            	$('#isCRUD').combobox('setValue','否');
            	$('#isCRUDDiv').hide();
        	},
        	save:function(result){
        		var id = $('#id').val();
				var name = $('#name').val();
				//新增
				if(id==''){
					var isCRUD = $('#isCRUD').combobox('getValue');
					var state = 'open';
					if('是'==isCRUD){
						state = 'closed';
					}
					var node = $(app.id.treeId).tree('getSelected');
					$(app.id.treeId).tree('append',{
						parent: (node?node.target:null),
						data:[{
							id:result.data.id,
							text:name,
							state:state
						}]
					});
				}else{
					var updateNote=$(app.id.treeId).tree('find',id);
					updateNote.text=name;
					$(app.id.treeId).tree('update', updateNote);
				}
				$(app.id.editDialogId).dialog('close');
				$(app.id.listId).datagrid('reload');
        	}
    	});
    }
};
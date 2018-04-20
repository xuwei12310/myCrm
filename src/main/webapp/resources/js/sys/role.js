var role = {
	id:{
		resourcesBtnId:'#resourcesBtn',
		resourcesDialogId:'#resourcesDialog',
		resourcesTreeId:'#resourcesTree'
	},
	url: {
        resources: function () {
        	return app.getUrl('resources');
        },
        getGrantResource: function () {
        	return app.getUrl('getGrantResources');
        },
        grantResource: function () {
        	return app.getUrl('grantResources');
        }
    },
    init: function (params) {
    	app.crud.initViewList({
       	 	columns:[[
       		        {field:'ck',checkbox:true},
       		        {field:'name',title:'名称',width:200,align:'center'},
       		        {field:'role',title:'标识',width:200,align:'center'},
       		        {field:'status',title:'状态',width:200,align:'center'},
       		        {field:'isSys',title:'系统内置',width:100,align:'center'}
       		]]
    	});
    	app.crud.initEditDialog({
        	height:260,
         	width:300
        });

        //添加
        $(app.id.addBtnId).click(function(){
            app.stateCombobox("#status");
            $('#status').combobox('setValue','启用');
            app.crud.add();
        });
        //修改
        $(app.id.updateBtnId).click(function(){
            var selectedRow = $(app.id.listId).datagrid('getSelected');
            if(selectedRow.isSys=='是'){
                $.messager.alert('提示','系统内置不能修改','warning');
                return;
            }
            app.crud.update();
        });
        //删除
        $(app.id.deleteBtnId).click(function(){
            var selectedRow = $(app.id.listId).datagrid('getSelected');
            if(selectedRow.isSys=='是'){
                $.messager.alert('提示','系统内置不能删除','warning');
                return;
            }
            app.crud.mulDelete();
        });
        //查询
        $(app.id.searchBtnId).click(function(){
            app.crud.search();
        });
        //清空查询条件
        $(app.id.clearBtnId).click(function(){
            $(app.id.searchFormId).form('clear');
            $(app.id.listId).datagrid({
                queryParams:''
            });
        });
    	
    	role.initResourcesDialog();
    	$(role.id.resourcesBtnId).click(function(){
    		role.resources();
    	});
    },
    initResourcesDialog:function(){
    	$(role.id.resourcesDialogId).dialog({  
    	    title: '权限设置',  
    	    width:400,
    	    height:500,
    	    cache: false, 
    	    closed: true,  
    	    modal: true,
    	    closable:false,
            buttons:[{
    			text:'保存',
    			iconCls:'icon-save',
    			handler:function(){
    				var selectedRow = $(app.id.listId).datagrid('getSelected');
    		    	if(selectedRow==null){
    		    		$.messager.alert('提示','请选择数据行','warning');
    		    		return;
    		    	}
    				//保存当前选择
    				var nodes = $(role.id.resourcesTreeId).tree('getChecked');
    				var idArray = [];
    				for (var i = 0; i < nodes.length; i++) {
						var node = nodes[i];
						var isLeaf = $(role.id.resourcesTreeId).tree('isLeaf',node.target);
						if(isLeaf){
							idArray.push(node.id);
						}
					}
    				var ids = idArray.join(CSIT.join);
    				var url = role.url.grantResource();
    				var data = {ids:ids,roleId:selectedRow.id};
    				CSIT.asyncCallService(url, data,function(){
    					$.messager.alert('提示','保存成功','info',function(){
    						$(role.id.resourcesDialogId).dialog('close');
        				});
    				});
    			}
    		},{
    			text:'退出',
    			iconCls:'icon-exit',
    			handler:function(){
    				$(role.id.resourcesDialogId).dialog('close');
    			}
    		}],
    		onOpen:function(){
    			role.initResourcesTree();
    		}
    	}); 
    },
    initResourcesTree:function(){
    	var selectedRow = $(app.id.listId).datagrid('getSelected');
    	if(selectedRow==null){
    		$.messager.alert('提示','请选择数据行','warning');
    		return;
    	}
    	$(role.id.resourcesTreeId).tree({
			url: role.url.resources(),
			parentField:'pid',
			checkbox:true,
			onLoadSuccess:function(node, data){
				var root = $(role.id.resourcesTreeId).tree('getRoot');
				$(role.id.resourcesTreeId).tree('expandAll',root.target);
				
				var url = role.url.getGrantResource();
				var data = {roleId:selectedRow.id};
				var result = CSIT.syncCallService(url, data);
			
				for (var i = 0; i < result.length; i++) {
					var id = result[i];
					var node = $(role.id.resourcesTreeId).tree('find', id);
					$(role.id.resourcesTreeId).tree('check', node.target);
				}
			}
    	});
    },
    resources:function(){
    	var selectedRow = $(app.id.listId).datagrid('getSelected');
    	if(selectedRow==null){
    		$.messager.alert('提示','请选择数据行','warning');
    		return;
    	}
    	$(role.id.resourcesDialogId).dialog('open');
    }
};
app.crud ={
    initCRUDBtn:function(ext){
    	//添加
    	$(app.id.addBtnId).click(function(){
    		app.crud.add(ext.add,ext.validate);
    	});
    	//修改
    	$(app.id.updateBtnId).click(function(){
    		app.crud.update(ext.update,ext.validate);
    	});
    	//删除
    	$(app.id.deleteBtnId).click(function(){
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
    },
    initAddBtn:function(extInit){
    	//添加
    	$(app.id.addBtnId).click(function(){
    		app.crud.add(extInit);
    	});
    },
    initViewList:function(params){
    	var params = $.extend({
    		url:app.url.datagrid(),
    		pagination:true,
    		fit:true,
    		border:false,
    		toolbar: app.id.toolbarId,
    		ctrlSelect:true,
    		rownumbers:true,
    		pageSize:20,
    		pageList:[10,20,30,40,50,100],
    		onDblClickRow:function(){
    			$(app.id.updateBtnId).click();
    		}
    	},params);
    	$(app.id.listId).datagrid(params);
    },
    initEditDialog:function(params){
    	var params = $.extend({
    		title:'编辑'+resourceName,
			width : 400,
			height : 200,
			closed : true,
			resizable:true,
			cache : false,
			modal : true,
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
			onClose:function(){
				$(app.id.editDialogId).form('clear');
			}
		},params);
    	$(app.id.editDialogId).dialog(params);
    },
    initEditForm:function(ext){
		$(app.id.editFormId).form({  
    		onSubmit: function(){ 
    			var isValid = $(this).form('validate');
    			if(ext){
    				isValid = ext();
    			}
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
    					$(app.id.editDialogId).dialog('close');
    					$(app.id.listId).datagrid('reload');
    					$(app.id.editFormId).form("clear");
    				});
    			}else{
    				$.messager.alert('提示',data.message,'error');
    			}
    		}
    	});
    },
    add:function(extInit,extValidate){
    	$(app.id.editDialogId).dialog('open');
    	if(extInit){
    		extInit();
    	}
    	app.crud.initEditForm(extValidate);
    	$(app.id.editFormId).attr('action',app.url.create());
    },
    update:function(extInit,extValidate){
    	var selectedRow = $(app.id.listId).datagrid('getSelected');
    	if(selectedRow==null){
    		$.messager.alert('提示','请选择数据行','warning');
    		return;
    	}
    	if(extInit){
    		extInit();
    	}
    	$(app.id.editDialogId).dialog('open');
    	app.crud.initEditForm(extValidate);
    	$(app.id.editFormId).form('load',selectedRow);
    	$(app.id.editFormId).attr('action',app.url.update());
    },
    mulDelete:function(){
    	var checkedRows = $(app.id.listId).datagrid('getChecked');
    	if(checkedRows.length==0){
    		$.messager.alert('提示','请选择删除的数据行','warning');
    		return;
    	}
    	$.messager.confirm("提示","确定要删除选中的记录?",function(t){ 
			if(!t) return;
			var idArray = [];
			for(var i=0;i<checkedRows.length;i++){
				idArray.push(checkedRows[i].id);
			}
			var ids = idArray.join(CSIT.join);
			var content = {ids:ids};
			$.messager.progress();
			CSIT.asyncCallService(app.url.mulDelete(),content,function(result){
				if(result.isSuccess){
					$(app.id.listId).datagrid('reload');
				}else{
					$.messager.alert('错误',result.message,"error");
				}
				$.messager.progress('close');
			});
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
};

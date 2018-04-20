app.treeCRUD ={
    initTree:function(params){
    	var params = $.extend({
    		animate:true,
    		lines:true,
    	  	dnd:true,
    	  	parentField:"pId",
    	  	url: app.url.treeRoot(),
    	  	onBeforeExpand:function(node,param){
    	  		$(app.id.treeId).tree('options').url = app.url.treeChildren()+'?parentId='+node.id;  
    	  	},
      		onClick:function(node){ 
    			$(app.id.treeId).tree('expand',node.target);
    			var url = app.url.listChildren()+'?parentId='+node.id;
    			$(app.id.listId).datagrid({
					url:url
				});
      		},
    		onLoadSuccess:function(node, data){
    			if(node==null){
    				var root = $(app.id.treeId).tree('getRoot');
    				$(app.id.treeId).tree('select', root.target);
    				$(app.id.treeId).tree('expand',root.target);
    				var url = app.url.listChildren()+'?parentId='+root.id;
    				$(app.id.listId).datagrid({
    					url:url
    				});
    			}
    		},
    		onDrop: function(targetNode, source, point){  
    			var targetId = $(app.id.treeId).tree('getNode', targetNode).id;  
    			var url = app.url.move();
    			var content = {sourceId:source.id,targetId:targetId,point:point};
    			var result = CSIT.syncCallService(url,content);
    			if (!result.isSuccess){
    				$.messager.alert('错误',result.message,"error");
    			}else{
    				$(app.id.listId).datagrid('reload');
    			}
    		},
    		onBeforeDrop:function(target,source,point){
    			var root = $(app.id.treeId).tree('getRoot');
    			var targetId = $(app.id.treeId).tree('getNode', target).id;  
    			if(root.id==targetId){
    				$.messager.alert("提示",'不能与根节点同级','warning');
    				return false;
    			}
    			return true;
    		}
        },params);
    	$(app.id.treeId).tree(params);
    },
    initCRUDBtn:function(ext){
    	//添加
    	$(app.id.addBtnId).click(function(){
    		app.treeCRUD.add(ext);
    	});
    	//修改
    	$(app.id.updateBtnId).click(function(){
    		app.treeCRUD.update(ext);
    	});
    	//删除
    	$(app.id.deleteBtnId).click(function(){
    		app.treeCRUD.mulDelete();
    	});
    	//查询
    	$(app.id.searchBtnId).click(function(){
    		app.treeCRUD.search();
    	});
    	////用于来自同一个jsp页面  多个查询  弹出选择框  
    	$(app.id.searchSelectBtnId).click(function(){
    		app.treeCRUD.searchSelect();
    	});
    	//清空选择框的查询条件
    	$(app.id.clearSelectBtnId).click(function(){
    		$(app.id.searchSelectFormId).form('clear');
    	});
    	//清空查询条件
    	$(app.id.clearBtnId).click(function(){
    		$(app.id.searchFormId).form('clear');
    	});
    },
    initAddBtn:function(extInit){
    	//添加
    	$(app.id.addBtnId).click(function(){
    		app.add(extInit);
    	});
    },
    initViewList:function(params){
    	var params = $.extend({
    		pagination:true,
    		fit:true,
    		border:false,
    		toolbar: app.id.toolbarId,
    		ctrlSelect:true,
    		rownumbers:true,
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
			cache : false,
			modal : true,
			toolbar:[{
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
    initEditForm:function(saveFN){
		$(app.id.editFormId).form({  
    		onSubmit: function(){ 
    			var isValid = $(this).form('validate');
    			if (!isValid){
    				$.messager.progress('close');
    			}
    			return isValid;
    		}, 
    		success:function(data){  
    			$.messager.progress('close');
    			var result = eval('(' + data + ')');  
    			if (result.isSuccess){
					$.messager.alert('提示','保存成功','info',saveFN(result));
    			}else{
    				$.messager.alert('提示',result.message,'error');
    			}
    		}
    	});
    },
    add:function(ext){
    	//选中父节点
		var selectedNode = $(app.id.treeId).tree('getSelected');
		if(selectedNode==null){
			$.messager.alert('提示','请选择父节点','warning');
			return false;
		}
		$("#parentId").val(selectedNode.id);
		$("#parentName").textbox('setText',selectedNode.text);
		
    	$(app.id.editDialogId).dialog('open');
    	if(ext.add){
    		ext.add();
    	}
    	app.treeCRUD.initEditForm(ext.save);
    	$(app.id.editFormId).attr('action',app.url.create());
    },
    update:function(ext){
    	//选中父节点
		var selectedNode = $(app.id.treeId).tree('getSelected');
		if(selectedNode==null){
			$.messager.alert('提示','请选择父节点','warning');
			return false;
		}
		$("#parentId").val(selectedNode.id);
		$("#parentName").textbox('setText',selectedNode.text);
		
    	var selectedRow = $(app.id.listId).datagrid('getSelected');
    	if(selectedRow==null){
    		$.messager.alert('提示','请选择数据行','warning');
    		return;
    	}
    	if(ext.update){
    		ext.update();
    	}
    	
    	$(app.id.editDialogId).dialog('open');
    	app.treeCRUD.initEditForm(ext.save);
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
					var rows = $(app.id.listId).datagrid('getChecked');
					for(var i=0;i<rows.length>0;i++){
						$(app.id.treeId).tree('remove',$(app.id.treeId).tree('find',rows[i].id).target);
					}
					$(app.id.listId).datagrid('uncheckAll');
					$(app.id.listId).datagrid('reload');
				}else{
					$.messager.alert('错误',result.message,"error");
				}
				$.messager.progress('close');
			});
		});
    },
    search:function(){
    	var content = $(app.id.searchFormId).serializeObject();
    	$(app.id.listId).datagrid({
    		queryParams:content
    	});
    },
    searchSelect:function(){
    	var content = $(app.id.searchSelectFormId).serializeObject();
    	$(app.id.selectListId).datagrid({
    		queryParams:content
    	});
    }
    
};

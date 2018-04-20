var area = {
    initCRUDBtn:function(){
    	//添加
    	$(app.id.addBtnId).click(function(){
    		area.add();
    	});
    	//修改
    	$(app.id.updateBtnId).click(function(){
    		area.update();
    	});
    	//删除
    	$(app.id.deleteBtnId).click(function(){
    		area.mulDelete();
    	});
    	//查询
    	$(app.id.searchBtnId).click(function(){
    		area.search();
    	});
    	//清空查询条件
    	$(app.id.clearBtnId).click(function(){
    		$(app.id.searchFormId).form('clear');
    	});
    	//上移
    	$("#upBtn").click(function(){
    		area.change(-1);
    	});
    	//下移
    	$("#downBtn").click(function(){
    		area.change(1);
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
    initEditDialog:function(){
    	var params = {
    		title:'编辑'+resourceName,
    		width : 350,
			height : 380,
			closed : true,
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
				$('#status',app.id.editDialogId).combobox('select',1); 
			}
		};
    	$(app.id.editDialogId).dialog(params);
    },
    initEditForm:function(){
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
    			var data = eval('(' + data + ')');  
    			if (data.isSuccess){
    				$.messager.alert('提示','保存成功','info',function(){
    					$(app.id.editDialogId).dialog('close');
    					$(app.id.listId).datagrid('reload');
    				});
    			}else{
    				$.messager.alert('提示',data.message,'error');
    			}
    		}
    	});
    },
    add:function(){
    	$(app.id.editDialogId).dialog('open');
    	area.initEditForm();
    	$(app.id.editFormId).attr('action',app.url.create());
    	app.yesOrNoByIntCombobox("#isSys");
    },
    update:function(){
    	var selectedRow = $(app.id.listId).datagrid('getSelected');
    	if(selectedRow==null){
    		$.messager.alert('提示','请选择数据行','warning');
    		return;
    	}
    	if(selectedRow.isSys==1){
    		$.messager.alert('提示','系统内置不能修改','warning');
    		return;
    	}
    	$('#province',app.id.editDialogId).combobox('select',selectedRow.provinceId);
    	$('#city',app.id.editDialogId).combobox('select',selectedRow.cityId);
    	$(app.id.editDialogId).dialog('open');
    	area.initEditForm();
    	$(app.id.editFormId).form('load',selectedRow);
    	$(app.id.editFormId).attr('action',app.url.update());
    	app.yesOrNoByIntCombobox("#isSys");
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
    },
    change:function(direction){
		var selectRow = $(app.id.listId).datagrid('getSelected');
		var selectIndex = $(app.id.listId).datagrid('getRowIndex',selectRow);
		if(selectRow == null){
			$.messager.alert("提示","请选中一条记录","warning");
			return;
		}
		var rows  = $(app.id.listId).datagrid('getRows');
		if(direction==-1){
			if(selectIndex==0){
				$.messager.alert("提示","已经是第一条记录了","warning");
				return;
			}
		}else if(direction==1){//下移
			if(selectIndex==rows.length-1){
				$.messager.alert("提示","已经是最后一条记录了","warning");
				return;
			}
		}
		var updateRow = rows[selectIndex+direction];
		
		var id = selectRow.id;
		var status = selectRow.status;
		var areaCode = selectRow.areaCode;
		var areaName = selectRow.areaName;
		var showName = selectRow.showName ;
		var provinceId = selectRow.provinceId;
		var provinceName = selectRow.provinceName;
		var cityId = selectRow.cityId;
		var cityName =selectRow.cityName;
		if(updateRow.showName==null){
			updateRow.showName = '';
		}
		var array = updateRow.array;
		updateRow.array = selectRow.array;
		//后台更新排序
		var content = {srcId:id,destId:updateRow.id};
		var result = CSIT.syncCallService(app.getUrl('changeArray'),content);
		if(result.isSuccess){
			$(app.id.listId).datagrid('updateRow',{
				index: selectIndex,
				row: updateRow
			});
			$(app.id.listId).datagrid('updateRow',{
				index: selectIndex+direction,
				row: {
					id:id,
					status:status,
					areaCode:areaCode,
					areaName:areaName,
					showName:showName,
					provinceName:provinceName,
					provinceId:provinceId,
					cityId:cityId,
					cityName:cityName
				}
			});
			$(app.id.listId).datagrid('unselectRow',selectIndex);
			$(app.id.listId).datagrid('selectRow',selectIndex+direction);
		}else{
			$.messager.alert("错误",result.message,"error");
		}
	},
	
	initData: function(queryContent){
		var provinceList = CSIT.getProvinceList() ;
		$('#province',queryContent).combobox({
			  data:provinceList,
			  editable:false,
			  valueField:'provinceId',
			  textField:'provinceName',
			  onSelect: function(record){
				  $('#city',queryContent).combobox({
					 data:CSIT.getCityList(record.provinceId) 
				  });
			  }
		  });
		
		$('#city',queryContent).combobox({
			  valueField:'cityId',
			  textField:'cityName'
		  });
		
	},
	initStatus:function(){
		$('#status','#searchForm').combobox({
			data:[
			       {text:'启用',value:'1'},
			       {text:'禁用',value:'0'}
			     ],
		    editable:false ,
		    valueField:'value',
		    textField:'text'
		});
	},
    init: function (params) {
    	area.initEditDialog();
    	area.initCRUDBtn();
    	area.initData($('#editDialog'));
    	area.initData($('#searchForm'));
    	area.initStatus();
    	area.initViewList({
    		columns:[[
         		    {field:'ck',checkbox:true},
  					{field:'provinceId',title:'省份',hidden:'true'},
  					{field:'provinceName',title:'省份',width:100,align:'center'},
  					{field:'cityId',title:'城市',hidden:'true'},
  					{field:'cityName',title:'城市',width:100,align:'center'},
					{field:'areaCode',title:'区/县编号',width:100,align:'center'},
  					{field:'areaName',title:'区/县名称',width:100,align:'center'},
  					{field:'showName',title:'显示名称',width:100,align:'center'},
  					{field:'status',title:'状态',width:100,align:'center',
  						formatter: function(value,row,index){
							if(value == 1){
								return '启用';
							}else if(value == 0){
								return '禁用';
							}
						}
  					},
  					{field:'id',hidden:true},
  					{field:'array',hidden:true}
         		]],
    	});
    }

};

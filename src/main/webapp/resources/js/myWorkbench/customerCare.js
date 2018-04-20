var customerCare = {

	initCRUDBtn : function() {
		// 添加
		$(app.id.addBtnId).click(function() {
			customerCare.add();
		});
		// 修改
		$(app.id.updateBtnId).click(function() {
			customerCare.update();
		});
		// 删除
		$(app.id.deleteBtnId).click(function() {
			customerCare.mulDelete();
		});
		// 查询
		$(app.id.searchBtnId).click(function() {
			customerCare.search();
		});
		// 清空查询条件
		$(app.id.clearBtnId).click(function() {
			$(app.id.searchFormId).form('clear');
		});
		//拥有人
		$("#ownerIdBtn").textbox({
            onClickIcon: function (index) {
                if (index == 0) {
                	$("#ownerEditDialog").dialog('open');
                }
            }
        }); 
		//客户名称
		$("#customerNameIdBtn").textbox({
            onClickIcon: function (index) {
                if (index == 0) {
                	$("#customerEditDialog").dialog('open');
                }
            }
        }); 
		//处理人
		$("#handleIdBtn").textbox({
            onClickIcon: function (index) {
                if (index == 0) {
                	$("#handleEditDialog").dialog('open');
                }
            }
        }); 
		//订单中搜索订单编号
		$("#searchOrderBtn").click(function(){
			var isValid = $("#searchOrderForm").form('validate');
			if (!isValid) {
				return false;
			}
			var content = $("#searchOrderForm").serializeObject();
			$("#ownerList").datagrid({
				queryParams : content
			});
    	});
		// 订单中清空查询条件
		$("#clearOrderBtn").click(function() {
			$("#searchOrderForm").form('clear');
		});
		
		//处理人中搜索用户名
		$("#searchHandleBtn").click(function(){
			var isValid = $("#searchHandleForm").form('validate');
			if (!isValid) {
				return false;
			}
			var content = $("#searchHandleForm").serializeObject();
			$("#handleList").datagrid({
				queryParams : content
			});
    	});
		// 处理人中清空查询条件
		$("#clearHandleBtn").click(function() {
			$("#searchHandleForm").form('clear');
		});
		//客户中搜索客户名称
		$("#searchCustomerBtn").click(function(){
			var isValid = $("#searchCustomerForm").form('validate');
			if (!isValid) {
				return false;
			}
			var content = $("#searchCustomerForm").serializeObject();
			$("#customerList").datagrid({
				queryParams : content
			});
    	});
		// 客户中清空查询条件
		$("#clearCustomerBtn").click(function() {
			$("#searchCustomerForm").form('clear');
		});
		//拥有人中搜索姓名
		$("#searchOwnerBtn").click(function(){
			var isValid = $("#searchOwnerForm").form('validate');
			if (!isValid) {
				return false;
			}
			var content = $("#searchOwnerForm").serializeObject();
			$("#ownerList").datagrid({
				queryParams : content
			});
    	});
		// 拥有人中清空查询条件
		$("#clearOwnerBtn").click(function() {
			$("#searchOwnerForm").form('clear');
		});
	},
	initViewList : function(params) {
		var params = $.extend({
			url : app.url.datagrid(),
			pagination : true,
			fit : true,
			border : false,
			toolbar : app.id.toolbarId,
			ctrlSelect : true,
			rownumbers : true,
			pageSize : 20,
			pageList : [ 10, 20, 30, 40, 50, 100 ],
			onDblClickRow : function() {
				$(app.id.updateBtnId).click();
			}
		}, params);
		$(app.id.listId).datagrid(params);
	},
	initEditDialog : function() {
		var params = {
			title : '编辑' + resourceName,
			width : 400,
			height : document.documentElement.clientHeight-5<535?document.documentElement.clientHeight-5:535,
			closed : true,
			cache : false,
			modal : true,
			buttons : [ {
				text : '保存',
				iconCls : 'icon-save',
				handler : function() {
					$(app.id.editFormId).submit();
				}
			}, {
				text : '退出',
				iconCls : 'icon-exit',
				handler : function() {
					$(app.id.editDialogId).dialog('close');
				}
			} ],
			onClose : function() {
				$(app.id.editDialogId).form('clear');
			}
		};
		$(app.id.editDialogId).dialog(params);
	},
	initEditForm : function() {
		$(app.id.editFormId).form({
			onSubmit : function() {
				var isValid = $(this).form('validate');
				if (!isValid) {
					$.messager.progress('close');
				}
				return isValid;
			},
			success : function(data) {
				$.messager.progress('close');
				var data = eval('(' + data + ')');
				if (data.isSuccess) {
					$.messager.alert('提示', '保存成功', 'info', function() {
						$(app.id.editDialogId).dialog('close');
						$(app.id.listId).datagrid('reload');
					});
				} else {
					$.messager.alert('提示', data.message, 'error');
				}
			}
		});
	},
	 //拥有人
    initOwnerEditDialog:function(){
    	$('#ownerList').datagrid({  
			  border:true,
			  url:app.getUrl("getOwnerByList"),
			  rownumbers:true,
			  pagination:true,
			  fit:true,
			  toolbar:[{
				  text:'选择',
					iconCls:'icon-ok',
					handler:function(){
						var selectedRow = $('#ownerList').datagrid('getSelected');
						$("#ownerIdBtn").textbox("setValue", selectedRow.name);
				        $("#ownerId").val(selectedRow.id);
				        $("#ownerEditDialog").dialog('close');
					}
				}],
	    	  ctrlSelect:true,
			  method:"POST",
		      columns:[[           
                  {field:'name',title:'姓名',width:100,align:'center'}
		          
		   	  ]],
		   	  onDblClickCell: function(index,field,value){
		   		  var rows = $('#ownerList').datagrid('getRows');
		   		  var selectedRow = rows[index];
		   		  $("#ownerIdBtn").textbox("setValue", selectedRow.username);
		          $("#ownerId").val(selectedRow.id);
		          $("#ownerEditDialog").dialog('close');
		   	  }
		});
    	var params = {
	    	title:'选择拥有人',
			width : 600,
			height : document.documentElement.clientHeight-5<500?document.documentElement.clientHeight-5:500,
			closed : true,
			cache : false,
			modal : true,
		};
	    $("#ownerEditDialog").dialog(params);
    },
    //客户名称
    initCustomerEditDialog:function(){
    	$('#customerList').datagrid({  
			  border:true,
			  url:app.getUrl("getCustomerByList"),
			  rownumbers:true,
			  pagination:true,
			  fit:true,
			  toolbar:[{
				  text:'选择',
					iconCls:'icon-ok',
					handler:function(){
						var selectedRow = $('#customerList').datagrid('getSelected');
						if(selectedRow==null){
				    		$.messager.alert('提示','请选择数据行','warning');
				    		return;
				    	}
						$("#customerNameIdBtn").textbox("setValue", selectedRow.customerName);
				        $("#customerId").val(selectedRow.id);
				        $("#customerEditDialog").dialog('close');
					}
				}],
	    	  ctrlSelect:true,
			  method:"POST",
		      columns:[[           
		          {field:'customerName',title:'客戶名称',width:100,align:'center'},
		   	  ]],
		   	  onDblClickCell: function(index,field,value){
		   		  var rows = $('#customerList').datagrid('getRows');
		   		  var selectedRow = rows[index];
		   		  $("#customerNameIdBtn").textbox("setValue", selectedRow.customerName);
		          $("#customerId").val(selectedRow.id);
		          $("#customerEditDialog").dialog('close');
		   	  }
		});
    	var params = {
	    	title:'选择客户',
			width : 600,
			height : document.documentElement.clientHeight-5<500?document.documentElement.clientHeight-5:500,
			closed : true,
			cache : false,
			modal : true,
		};
	    $("#customerEditDialog").dialog(params);
    },
    //处理人
    initHandleEditDialog:function(){
    	$('#handleList').datagrid({  
			  border:true,
			  url:app.getUrl("getOwnerByList"),
			  rownumbers:true,
			  pagination:true,
			  fit:true,
			  toolbar:[{
				  text:'选择',
					iconCls:'icon-ok',
					handler:function(){
						var selectedRow = $('#handleList').datagrid('getSelected');
						if(selectedRow==null){
				    		$.messager.alert('提示','请选择数据行','warning');
				    		return;
				    	}
						$("#handleIdBtn").textbox("setValue", selectedRow.name);
				        $("#handleId").val(selectedRow.id);
				        $("#handleEditDialog").dialog('close');
					}
				}],
	    	  ctrlSelect:true,
			  method:"POST",
		      columns:[[        
                  {field:'name',title:'姓名',width:100,align:'center'}
		          
		   	  ]],
		   	  onDblClickCell: function(index,field,value){
		   		  var rows = $('#handleList').datagrid('getRows');
		   		  var selectedRow = rows[index];
		   		  $("#handleIdBtn").textbox("setValue", selectedRow.name);
		          $("#handleId").val(selectedRow.id);
		          $("#handleEditDialog").dialog('close');
		   	  }
		});
    	var params = {
	    	title:'选择处理人',
			width : 600,
			height : document.documentElement.clientHeight-5<500?document.documentElement.clientHeight-5:500,
			closed : true,
			cache : false,
			modal : true,
		};
	    $("#handleEditDialog").dialog(params);
    },
	add : function() {
		$(app.id.editDialogId).dialog('open');
		customerCare.initEditForm();
		$("#status").combobox("setValue",1);
		$(app.id.editFormId).attr('action', app.url.create());
	},
	update : function() {
		var selectedRow = $(app.id.listId).datagrid('getSelected');
		if (selectedRow == null) {
			$.messager.alert('提示', '请选择数据行', 'warning');
			return;
		}
	    $("#customerNameIdBtn").textbox("setValue", selectedRow.customerName);
	    $("#customerId").val(selectedRow.customerId);
	    
        $("#ownerIdBtn").textbox("setValue", selectedRow.ownerUserName);
        $("#ownerId").val(selectedRow.ownerId);
        
        $("#handleIdBtn").textbox("setValue", selectedRow.handleUserName);
        $("#handleId").val(selectedRow.handleId);
        
		$(app.id.editDialogId).dialog('open');
		customerCare.initEditForm();
		$(app.id.editFormId).form('load', selectedRow);
		$(app.id.editFormId).attr('action', app.url.update());
	},
	mulDelete : function() {
		var checkedRows = $(app.id.listId).datagrid('getChecked');
		if (checkedRows.length == 0) {
			$.messager.alert('提示', '请选择删除的数据行', 'warning');
			return;
		}
		$.messager.confirm("提示", "确定要删除选中的记录?", function(t) {
			if (!t)
				return;
			var idArray = [];
			for (var i = 0; i < checkedRows.length; i++) {
				idArray.push(checkedRows[i].id);
			}
			var ids = idArray.join(CSIT.join);
			var content = {
				ids : ids
			};
			$.messager.progress();
			CSIT.asyncCallService(app.url.mulDelete(), content,
					function(result) {
						if (result.isSuccess) {
							$(app.id.listId).datagrid('reload');
						} else {
							$.messager.alert('错误', result.message, "error");
						}
						$.messager.progress('close');
					});
		});
	},
	search : function() {
		var isValid = $(app.id.searchFormId).form('validate');
		if (!isValid) {
			return false;
		}
		var content = $(app.id.searchFormId).serializeObject();
		$(app.id.listId).datagrid({
			queryParams : content
		});
	},

	init : function(params) {
		customerCare.initEditDialog();
		customerCare.initCRUDBtn();
		customerCare.initOwnerEditDialog();
		customerCare.initCustomerEditDialog();
		customerCare.initHandleEditDialog();
		customerCare.initViewList({
			columns : [ [ {
				field : 'ck',
				checkbox : true
			},
			{
				field : 'customerName',
				title : '客户名称',
				width : 100,
				align : 'center'
			},
			{
				field : 'careContent',
				title : '关怀内容',
				width : 100,
				align : 'center'
			},
			{
				field : 'details',
				title : '处理详情',
				width : 100,
				align : 'center'
			},
			{
				field : 'handleUserName',
				title : '处理人',
				width : 100,
				align : 'center'
			},
			{
				field : 'handleTime',
				title : '处理时间',
				width : 140,
				align : 'center'
			},{
				field : 'status',
				title : '状态',
				width : 100,
				align : 'center',
				formatter : function(value, row, index) {
					if (value == 1) {
						return '未处理';
					} else if (value == 2) {
						return '已处理';
					}
				}
			}, 
			{
				field : 'note',
				title : '备注',
				width : 100,
				align : 'center'
			} ] ]
		});
		    $("#status").combobox({
		        panelHeight: 'auto',
		        editable:false,
		        valueField: 'value',
		        textField: 'text',
		        data: [
		            {value: '1',text: '未处理'},
		            {value: '2',text: '已处理'}
		        ]
		    });
		    $("#statu").combobox('clear');
	}

};

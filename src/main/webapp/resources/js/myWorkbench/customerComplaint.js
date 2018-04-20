var customerComplaint = {

	initCRUDBtn : function() {
		// 添加
		$(app.id.addBtnId).click(function() {
			customerComplaint.add();
		});
		// 修改
		$(app.id.updateBtnId).click(function() {
			customerComplaint.update();
		});
		// 删除
		$(app.id.deleteBtnId).click(function() {
			customerComplaint.mulDelete();
		});
		// 查询
		$(app.id.searchBtnId).click(function() {
			customerComplaint.search();
		});
		// 清空查询条件
		$(app.id.clearBtnId).click(function() {
			$(app.id.searchFormId).form('clear');
		});
		//订单
		$("#orderCodeIdBtn").textbox({
            onClickIcon: function (index) {
                if (index == 0) {
                	var customerId=$("#customerId").val();
                	if(customerId==null || customerId=="" || customerId==undefined){
                		$.messager.alert("提示","请先选择客户名称","warning");
                	}
                	else{
                		$("#orderEditDialog").dialog('open');
                	}
                	
                }
            }
        }); 
		//订单- 搜索框
		$("#orderCodeSearchIdBtn").textbox({
            onClickIcon: function (index) {
                if (index == 0) {
                	$("#orderEditSearchDialog").dialog('open');
                }
            }
        }); 
		//投诉对象
		$("#objectIdBtn").textbox({
            onClickIcon: function (index) {
                if (index == 0) {
                	$("#objectEditDialog").dialog('open');
                }
            }
        }); 
		//投诉对象搜索框
		$("#complaintobjectIdBtn").textbox({
            onClickIcon: function (index) {
                if (index == 0) {
                	$("#objectSearchEditDialog").dialog('open');
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
		//订单中搜索用户名
		$("#searchOrderBtn").click(function(){
			var isValid = $("#searchOrderForm").form('validate');
			if (!isValid) {
				return false;
			}
			var content = $("#searchOrderForm").serializeObject();
			$("#orderList").datagrid({
				queryParams : content
			});
    	});
		// 订单中清空查询条件
		$("#clearOrderBtn").click(function() {
			$("#searchOrderForm").form('clear');
		});
		//投诉对象中搜索用户名
		$("#searchObjectBtn").click(function(){
			var isValid = $("#searchObjectForm").form('validate');
			if (!isValid) {
				return false;
			}
			var content = $("#searchObjectForm").serializeObject();
			$("#objectList").datagrid({
				queryParams : content
			});
    	});
		// 投诉对象清空查询条件
		$("#clearObjectBtn").click(function() {
			$("#searchObjectForm").form('clear');
		});
		//列表投诉对象中搜索用户名
		$("#searchObjectSearchBtn").click(function(){
			var isValid = $("#searchObjectSearchForm").form('validate');
			if (!isValid) {
				return false;
			}
			var content = $("#searchObjectSearchForm").serializeObject();
			$("#objectSearchList").datagrid({
				queryParams : content
			});
    	});
		// 列表投诉对象清空查询条件
		$("#clearObjectSearchBtn").click(function() {
			$("#searchObjectSearchForm").form('clear');
		});
		//列表订单中搜索订单编号
		$("#searchOrderSearchBtn").click(function(){
			var isValid = $("#searchOrderSearchForm").form('validate');
			if (!isValid) {
				return false;
			}
			var content = $("#searchOrderSearchForm").serializeObject();
			$("#orderSearchList").datagrid({
				queryParams : content
			});
    	});
		// 列表订单中清空查询条件
		$("#clearOrderSearchBtn").click(function() {
			$("#searchOrderSearchForm").form('clear');
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
			width : 375,
			height : document.documentElement.clientHeight-5<635?document.documentElement.clientHeight-5:635,
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
	 //订单
    initOrderEditDialog:function(){
    	var customerId=$("#customerId").val();
    	$('#orderList').datagrid({  
			  border:true,
			  url:app.getUrl("getOrderByList"),
			  queryParams : {"customerId":customerId},
			  rownumbers:true,
			  pagination:true,
			  fit:true,
			  toolbar:[{
				  text:'选择',
					iconCls:'icon-ok',
					handler:function(){
						var selectedRow = $('#orderList').datagrid('getSelected');
						if(selectedRow==null){
				    		$.messager.alert('提示','请选择数据行','warning');
				    		return;
				    	}
						$("#orderCodeIdBtn").textbox("setValue", selectedRow.orderCode);
				        $("#orderId").val(selectedRow.id);
				        $("#orderEditDialog").dialog('close');
					}
				}],
	    	  ctrlSelect:true,
			  method:"POST",
		      columns:[[           
		          {field:'orderCode',title:'订单编号',width:100,align:'center'},
		          {field:'signingDate',title:'签约日期',width:100,align:'center'},
		          {field:'estimateFinishTime',title:'预计完成日期',width:100,align:'center'},
		          {field:'loanAmount',title:'贷款金额',width:100,align:'center'},
		   	  ]],
		   	  onDblClickCell: function(index,field,value){
		   		  var rows = $('#orderList').datagrid('getRows');
		   		  var selectedRow = rows[index];
		   		  $("#orderCodeIdBtn").textbox("setValue", selectedRow.orderCode);
		          $("#orderId").val(selectedRow.id);
		          $("#orderEditDialog").dialog('close');
		   	  }
		});
    	var params = {
	    	title:'选择订单',
			width : 600,
			height : document.documentElement.clientHeight-5<500?document.documentElement.clientHeight-5:500,
			closed : true,
			cache : false,
			modal : true,
		};
	    $("#orderEditDialog").dialog(params);
    },
    //订单(搜索框)
    initOrderEditSearchDialog:function(){
    	$('#orderSearchList').datagrid({  
			  border:true,
			  url:app.getUrl("getOrderByList"),
			  rownumbers:true,
			  pagination:true,
			  fit:true,
			  toolbar:[{
				  text:'选择',
					iconCls:'icon-ok',
					handler:function(){
						var selectedRow = $('#orderSearchList').datagrid('getSelected');
						if(selectedRow==null){
				    		$.messager.alert('提示','请选择数据行','warning');
				    		return;
				    	}
						$("#orderCodeSearchIdBtn").textbox("setValue", selectedRow.orderCode);
				        $("#orderCodeSearchIdBtn").val(selectedRow.id);
				        $("#orderEditSearchDialog").dialog('close');
					}
				}],
	    	  ctrlSelect:true,
			  method:"POST",
		      columns:[[           
		          {field:'orderCode',title:'订单编号',width:100,align:'center'},
		   	  ]],
		   	  onDblClickCell: function(index,field,value){
		   		  var rows = $('#orderSearchList').datagrid('getRows');
		   		  var selectedRow = rows[index];
		   		  $("#orderCodeSearchIdBtn").textbox("setValue", selectedRow.orderCode);
		          $("#orderCodeSearchIdBtn").val(selectedRow.id);
		          $("#orderEditSearchDialog").dialog('close');
		   	  }
		});
    	var params = {
	    	title:'选择订单',
			width : 600,
			height : document.documentElement.clientHeight-5<500?document.documentElement.clientHeight-5:500,
			closed : true,
			cache : false,
			modal : true,
		};
	    $("#orderEditSearchDialog").dialog(params);
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
				        customerComplaint.initOrderEditDialog();
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
		          customerComplaint.initOrderEditDialog();
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
			  url:app.getUrl("getHandleByList"),
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
    //选择投诉对象
    initObjectEditDialog:function(){
    	$('#objectList').datagrid({  
			  border:true,
			  url:app.getUrl("getHandleByList"),
			  rownumbers:true,
			  pagination:true,
			  fit:true,
			  toolbar:[{
				  text:'选择',
					iconCls:'icon-ok',
					handler:function(){
						var selectedRow = $('#objectList').datagrid('getSelected');
						if(selectedRow==null){
				    		$.messager.alert('提示','请选择数据行','warning');
				    		return;
				    	}
						$("#objectIdBtn").textbox("setValue", selectedRow.name);
				        $("#complaint_object_id").val(selectedRow.id);
				        $("#objectEditDialog").dialog('close');
					}
				}],
	    	  ctrlSelect:true,
			  method:"POST",
			  pageSize : 10,
			  pageList : [ 10, 20, 30, 40, 50, 100 ],
		      columns:[[           
                  {field:'name',title:'姓名',width:100,align:'center'}
		          
		   	  ]],
		   	  onDblClickCell: function(index,field,value){
		   		  var rows = $('#objectList').datagrid('getRows');
		   		  var selectedRow = rows[index];
		   		  $("#objectIdBtn").textbox("setValue", selectedRow.name);
		          $("#complaint_object_id").val(selectedRow.id);
		          $("#objectEditDialog").dialog('close');
		   	  }
		});
    	var params = {
	    	title:'选择投诉对象',
			width : 600,
			height : document.documentElement.clientHeight-5<500?document.documentElement.clientHeight-5:500,
			closed : true,
			cache : false,
			modal : true,
		};
	    $("#objectEditDialog").dialog(params);
    },
  //搜索框选择投诉对象
    initObjectSearchEditDialog:function(){
    	$('#objectSearchList').datagrid({  
			  border:true,
			  url:app.getUrl("getHandleByList"),
			  rownumbers:true,
			  pagination:true,
			  fit:true,
			  toolbar:[{
				  text:'选择',
					iconCls:'icon-ok',
					handler:function(){
						var selectedRow = $('#objectSearchList').datagrid('getSelected');
						if(selectedRow==null){
				    		$.messager.alert('提示','请选择数据行','warning');
				    		return;
				    	}
					    $("#complaintobjectIdBtn").textbox("setValue", selectedRow.name);
					    $("#complaintobjectIdBtn").val(selectedRow.id);
				        $("#objectSearchEditDialog").dialog('close');
					}
				}],
	    	  ctrlSelect:true,
			  method:"POST",
			  pageSize : 10,
			  pageList : [ 10, 20, 30, 40, 50, 100 ],
		      columns:[[           
                  {field:'name',title:'姓名',width:100,align:'center'}
		          
		   	  ]],
		   	  onDblClickCell: function(index,field,value){
		   		  var rows = $('#objectSearchList').datagrid('getRows');
		   		  var selectedRow = rows[index];
		   		  $("#complaintobjectIdBtn").textbox("setValue", selectedRow.name);
			      $("#complaintobjectIdBtn").val(selectedRow.id);
		          $("#objectSearchEditDialog").dialog('close');
		   	  }
		});
    	var params = {
	    	title:'选择投诉对象',
			width : 600,
			height : document.documentElement.clientHeight-5<500?document.documentElement.clientHeight-5:500,
			closed : true,
			cache : false,
			modal : true,
		};
	    $("#objectSearchEditDialog").dialog(params);
    },
	add : function() {
		$(app.id.editDialogId).dialog('open');
		customerComplaint.initEditForm();
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
        $("#orderCodeIdBtn").textbox("setValue", selectedRow.orderCode);
        $("#orderId").val(selectedRow.orderId);
        $("#objectIdBtn").textbox("setValue", selectedRow.complaintObjectName);
        $("#complaint_object_id").val(selectedRow.complaintObjectId);
        $("#handleIdBtn").textbox("setValue", selectedRow.handleName);
        $("#handleId").val(selectedRow.handleId);
		$(app.id.editDialogId).dialog('open');
		customerComplaint.initEditForm();
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
		customerComplaint.initEditDialog();
		customerComplaint.initCRUDBtn();
		customerComplaint.initCustomerEditDialog();
		customerComplaint.initHandleEditDialog();
		customerComplaint.initObjectEditDialog();
		customerComplaint.initObjectSearchEditDialog();
		
		customerComplaint.initViewList({
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
				field : 'orderCode',
				title : '订单编号',
				width : 100,
				align : 'center'
			},
			{
				field : 'complaintContent',
				title : '投诉内容',
				width : 100,
				align : 'center'
			},
			{
				field : 'complaintObjectName',
				title : '投诉对象',
				width : 100,
				align : 'center'
			},
			{
				field : 'complaintTime',
				title : '投诉时间',
				width : 120,
				align : 'center'
			},
			{
				field : 'handleName',
				title : '处理人',
				width : 100,
				align : 'center'
			},{
				field : 'handleTime',
				title : '处理时间',
				width : 120,
				align : 'center'
			},
			{
				field : 'handleDetails',
				title : '处理详情',
				width : 100,
				align : 'center'
			},
			{
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

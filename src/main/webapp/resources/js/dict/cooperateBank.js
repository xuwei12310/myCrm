var cooperateBank = {

	initCRUDBtn : function() {
		// 添加
		$(app.id.addBtnId).click(function() {
			cooperateBank.add();
		});
		// 添加支行
		$("#addBranchBtn").click(function() {
			cooperateBank.addSubbranch();
		});
		// 修改
		$(app.id.updateBtnId).click(function() {
			cooperateBank.update();
		});
		//修改支行
		$("#updateBranchBtn").click(function() {
			cooperateBank.updateSubbranch();
		});
		// 删除
		$(app.id.deleteBtnId).click(function() {
			cooperateBank.mulDelete();
		});
		// 删除支行
		$("#deleteBranchBtn").click(function() {
			cooperateBank.mulDeleteSubbranch();
		});
		// 查询
		$(app.id.searchBtnId).click(function() {
			cooperateBank.search();
		});
		// 清空查询条件
		$(app.id.clearBtnId).click(function() {
			$(app.id.searchFormId).form('clear');
		});
		//配置支行
		$("#config").click(function(){
			cooperateBank.config();
		});
		// 上移
		$("#upBtn").click(function() {
			cooperateBank.change(-1);
		});
		// 下移
		$("#downBtn").click(function() {
			cooperateBank.change(1);
		});
		//上移支行
		$("#upBranchBtn").click(function() {
			cooperateBank.changeSubbranch(-1);
		});
		// 下移支行
		$("#downBranchBtn").click(function() {
			cooperateBank.changeSubbranch(1);
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
			width : 380,
			height : 220,
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
				$("#status").selected;
				
			}
		};
		$(app.id.editDialogId).dialog(params);
	},
	initEditBranchDialog : function() {
		var params = {
			title : '编辑支行',
			width : 400,
			height : 380,
			closed : true,
			cache : false,
			modal : true,
			buttons : [ {
				text : '保存',
				iconCls : 'icon-save',
				handler : function() {
					$("#editBranchForm").submit();
				}
			}, {
				text : '退出',
				iconCls : 'icon-exit',
				handler : function() {
					$("#editBranchDialog").dialog('close');
				}
			} ],
			onClose : function() {
				$("#editBranchDialog").form('clear');
			}
		};
		$("#editBranchDialog").dialog(params);
	},
	initConfigDialog : function() {
		var params = {
			title : '配置支行' ,
			width : 1200,
			height : 480,
			closed : true,
			cache : false,
			modal : true,
			/*buttons : [ {
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
			} ],*/
			onClose : function() {
				$(app.id.editDialogId).form('clear');
			}
		};
		$("#configDialog").dialog(params);
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
	initEditBranchForm : function() {
		$("#editBranchForm").form({
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
						$("#editBranchDialog").dialog('close');
						$("#branchList").datagrid('reload');
					});
				} else {
					$.messager.alert('提示', data.message, 'error');
				}
			}
		});
	},
	add : function() {
		$(app.id.editDialogId).dialog({
			title:"添加合作银行"
		});
		$(app.id.editDialogId).dialog('open');
		$("#status").combobox("setValue",1);
		cooperateBank.initEditForm();
		$(app.id.editFormId).attr('action', app.url.create());
		//app.yesOrNoByIntCombobox("#isSys");
	},
	addSubbranch : function() {
		var selectedRow = $(app.id.listId).datagrid('getSelected');
		$("#editBranchDialog").dialog({
			title:"添加支行"
		});
		$("#bankId").val(selectedRow.id);
		$("#branchStatus").combobox("setValue",1);
		$("#editBranchDialog").dialog('open');
		cooperateBank.initEditBranchForm();
		$("#editBranchForm").attr('action', app.getUrl("createSubbranch"));
		//app.yesOrNoByIntCombobox("#isSys");
	},
	update : function() {
		var selectedRow = $(app.id.listId).datagrid('getSelected');
		if (selectedRow == null) {
			$.messager.alert('提示', '请选择数据行', 'warning');
			return;
		}
		if (selectedRow.isSys == 1) {
			$.messager.alert('提示', '系统内置不能修改', 'warning');
			return;
		}
		$(app.id.editDialogId).dialog({
			title : '编辑合作银行' 
		});
		$(app.id.editDialogId).dialog('open');
		cooperateBank.initEditForm();
		$(app.id.editFormId).form('load', selectedRow);
		$(app.id.editFormId).attr('action', app.url.update());
		//app.yesOrNoByIntCombobox("#isSys");
	},
	updateSubbranch : function() {
		var selectedRow = $("#branchList").datagrid('getSelected');
		if (selectedRow == null) {
			$.messager.alert('提示', '请选择数据行', 'warning');
			return;
		}
		/*if (selectedRow.isSys == 1) {
			$.messager.alert('提示', '系统内置不能修改', 'warning');
			return;
		}*/
		$(app.id.editDialogId).dialog({
			title : '编辑支行' 
		});
		$("#bankId").val(selectedRow.bankId);
		console.log(selectedRow.array);
		$("#editBranchDialog").dialog('open');
		cooperateBank.initEditBranchForm();
		$("#editBranchForm").form('load', selectedRow);
		$("#editBranchForm").attr('action', app.getUrl("updateSubbranch"));
		//app.yesOrNoByIntCombobox("#isSys");
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
	mulDeleteSubbranch : function() {
		var checkedRows = $("#branchList").datagrid('getChecked');
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
			CSIT.asyncCallService(app.getUrl("mulDeleteSubbranch"), content,
					function(result) {
						if (result.isSuccess) {
							$("#branchList").datagrid('reload');
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
	config : function() {
		var selectedRow = $(app.id.listId).datagrid('getSelected');
		if (selectedRow == null) {
			$.messager.alert('提示', '请选择数据行', 'warning');
			return;
		}
		/*$("#configDialog").dialog({
			title : '配置支行' ,
			width : 800,
			height : 450,
		});*/
		$("#configDialog").dialog({
			title:selectedRow.bankName+"的支行"
		});
		//$("#bank").html(selectedRow.bankName);
		
		$("#branchList").datagrid({
			url : app.getUrl("listBranchByPage"),
			queryParams:{"bankId":selectedRow.id},
			pagination : true,
			fit : true,
			border : false,
			toolbar : "#tb2",
			ctrlSelect : true,
			rownumbers : true,
			pageSize : 10,
			columns:[[{
				field:'ck',
				checkbox:true,
				width:20,
				align:'center'
			},{
				field : 'subbranchName',
				title : '支行名称',
				width : 100,
				align : 'center'
			},{
				field : 'bankCode',
				title : '代号',
				width : 100,
				align : 'center'
			},{
				field : 'address',
				title : '地址',
				width : 150,
				align : 'center'
			},{
				field : 'contacts',
				title : '银行联系人',
				width : 100,
				align : 'center'
			},{
				field : 'phone',
				title : '联系电话',
				width : 150,
				align : 'center'
			},{
				field : 'status',
				title : '状态',
				width : 100,
				align : 'center',
				formatter : function(value, row, index) {
					if (value == 0) {
						return '禁用';
					} else if (value == 1) {
						return '启用';
					}
				}
			},{
				field : 'note',
				title : '备注',
				width : 200,
				align : 'center'
			}]],
			pageList : [ 10, 20, 30, 40, 50, 100 ],
			onDblClickRow : function() {
				//$(app.id.updateBtnId).click();
			}
		});
		$("#configDialog").dialog('open');
	},
	change : function(direction) {
		var selectRow = $(app.id.listId).datagrid('getSelected');
		var selectIndex = $(app.id.listId).datagrid('getRowIndex', selectRow);
		if (selectRow == null) {
			$.messager.alert("提示", "请选中一条记录", "warning");
			return;
		}
		var rows = $(app.id.listId).datagrid('getRows');
		if (direction == -1) {
			if (selectIndex == 0) {
				$.messager.alert("提示", "已经是第一条记录了", "warning");
				return;
			}
		} else if (direction == 1) {// 下移
			if (selectIndex == rows.length - 1) {
				$.messager.alert("提示", "已经是最后一条记录了", "warning");
				return;
			}
		}
		var updateRow = rows[selectIndex + direction];

		var id = selectRow.id;
		var isSys = selectRow.isSys;
		var code = selectRow.bankCode;
		var name = selectRow.bankName;
		/*var address=selectRow.address;
		var contacts=selectRow.contacts;
		var phone=selectRow.phone;*/
		var note=selectRow.note;
		if(note==null){
			note="";
		}
		var array = updateRow.array;
		updateRow.array = selectRow.array;
		// 后台更新排序
		var content = {
			srcId : id,
			destId : updateRow.id
		};
		var result = CSIT.syncCallService(app.getUrl('changeArray'), content);
		if (result.isSuccess) {
			$(app.id.listId).datagrid('updateRow', {
				index : selectIndex,
				row : updateRow
			});
			$(app.id.listId).datagrid('updateRow', {
				index : selectIndex + direction,
				row : {
					id : id,
					isSys : isSys,
					bankCode : code,
					array : array,
					bankName : name,
					note:note
					/*address:address,
					contacts:contacts,
					phone:phone*/
				}
			});
			$(app.id.listId).datagrid('unselectRow', selectIndex);
			$(app.id.listId).datagrid('selectRow', selectIndex + direction);
		} else {
			$.messager.alert("错误", result.message, "error");
		}
	},
	changeSubbranch : function(direction) {
		var selectRow = $("#branchList").datagrid('getSelected');
		var selectIndex = $("#branchList").datagrid('getRowIndex', selectRow);
		if (selectRow == null) {
			$.messager.alert("提示", "请选中一条记录", "warning");
			return;
		}
		var rows = $("#branchList").datagrid('getRows');
		if (direction == -1) {
			if (selectIndex == 0) {
				$.messager.alert("提示", "已经是第一条记录了", "warning");
				return;
			}
		} else if (direction == 1) {// 下移
			if (selectIndex == rows.length - 1) {
				$.messager.alert("提示", "已经是最后一条记录了", "warning");
				return;
			}
		}
		var updateRow = rows[selectIndex + direction];

		var id = selectRow.id;
		//var isSys = selectRow.isSys;
		var code = selectRow.bankCode;
		var name = selectRow.subbranchName;
		var address=selectRow.address;
		var contacts=selectRow.contacts;
		var phone=selectRow.phone;
		var note=selectRow.note;
		var status=selectRow.status;
		if(address==null){
			address="";
		}
		if(contacts==null){
			contacts="";
		}
		if(phone==null){
			phone="";
		}
		if(note==null){
			note="";
		}
		console.log(address);
		var array = updateRow.array;
		updateRow.array = selectRow.array;
		// 后台更新排序
		var content = {
			srcId : id,
			destId : updateRow.id
		};
		var result = CSIT.syncCallService(app.getUrl('changeSubbranchArray'), content);
		if (result.isSuccess) {
			$("#branchList").datagrid('updateRow', {
				index : selectIndex,
				row : updateRow
			});
			$("#branchList").datagrid('updateRow', {
				index : selectIndex + direction,
				row : {
					id : id,
					//isSys : isSys,
					bankCode : code,
					array : array,
					subbranchName : name,
					note:note,
					address:address,
					contacts:contacts,
					phone:phone,
					status:status
				}
			});
			$("#branchList").datagrid('unselectRow', selectIndex);
			$("#branchList").datagrid('selectRow', selectIndex + direction);
		} else {
			$.messager.alert("错误", result.message, "error");
		}
	},
	init : function(params) {
		cooperateBank.initEditDialog();
		cooperateBank.initEditBranchDialog();
		cooperateBank.initConfigDialog();
		cooperateBank.initCRUDBtn();
		cooperateBank.initViewList({
			columns : [ [ {
				field : 'ck',
				checkbox : true
			},
			/* {field:'code',title:'编号',width:100,align:'center'}, */
			{
				field : 'bankName',
				title : '银行名称',
				width : 100,
				align : 'center'
			}, {
				field : 'status',
				title : '状态',
				width : 100,
				align : 'center',
				formatter : function(value, row, index) {
					if (value == 0) {
						return '禁用';
					} else if (value == 1) {
						return '启用';
					}
				}
			}, {
				field : 'note',
				title : '备注',
				width : 100,
				align : 'center',
			}, {
				field : 'isSys',
				title : '是否系统内置',
				width : 100,
				align : 'center',
				formatter : function(value, row, index) {
					if (value == 0) {
						return '否';
					} else if (value == 1) {
						return '是';
					}
				}
			} ] ]
		});
		$('#status', editDialog).combobox({
			panelHeight : 'auto',
			editable : false,
			valueField : 'value',
			textField : 'text',
			data : [ {
				value : '1',
				text : '启用'
			}, {
				value : '0',
				text : '禁用'
			}

			]
		});
		$('#branchStatus').combobox({
			panelHeight : 'auto',
			editable : false,
			valueField : 'value',
			textField : 'text',
			data : [ {
				value : '1',
				text : '启用'
			}, {
				value : '0',
				text : '禁用'
			}

			]
		});
		app.enableCombobox($('#status', editDialog));
		$("#statu").combobox('clear');
	
	}

};

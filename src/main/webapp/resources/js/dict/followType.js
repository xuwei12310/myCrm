var followType = {

	initCRUDBtn : function() {
		// 添加
		$(app.id.addBtnId).click(function() {
			followType.add();
		});
		// 修改
		$(app.id.updateBtnId).click(function() {
			followType.update();
		});
		// 删除
		$(app.id.deleteBtnId).click(function() {
			followType.mulDelete();
		});
		// 查询
		$(app.id.searchBtnId).click(function() {
			followType.search();
		});
		// 清空查询条件
		$(app.id.clearBtnId).click(function() {
			$(app.id.searchFormId).form('clear');
		});
		// 上移
		$("#upBtn").click(function() {
			followType.change(-1);
		});
		// 下移
		$("#downBtn").click(function() {
			followType.change(1);
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
			width : 350,
			height : 235,
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
	add : function() {
		$(app.id.editDialogId).dialog({
			tittle:"添加"+resourceName
		});
		$(app.id.editDialogId).dialog('open');
		followType.initEditForm();
		$("#status").combobox("setValue",1);
		$(app.id.editFormId).attr('action', app.url.create());
		app.yesOrNoByIntCombobox("#isSys");
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
		$(app.id.editDialogId).dialog('open');
		followType.initEditForm();
		$(app.id.editFormId).form('load', selectedRow);
		$(app.id.editFormId).attr('action', app.url.update());
		app.yesOrNoByIntCombobox("#isSys");
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
		//var note = selectRow.note;
		var note = selectRow.note==undefined?'':selectRow.note;
		var code = selectRow.code;
		var name = selectRow.name;
		var status = selectRow.status;
		if(updateRow.note==null){
			updateRow.note = '';
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
					note : note,
					status:status,
					code : code,
					array : array,
					name : name
				}
			});
			$(app.id.listId).datagrid('unselectRow', selectIndex);
			$(app.id.listId).datagrid('selectRow', selectIndex + direction);
		} else {
			$.messager.alert("错误", result.message, "error");
		}
	},

	init : function(params) {
		followType.initEditDialog();
		followType.initCRUDBtn();
		followType.initViewList({
			columns : [ [ {
				field : 'ck',
				checkbox : true
			},
			/* {field:'code',title:'编号',width:100,align:'center'}, */
			{
				field : 'name',
				title : '名称',
				width : 100,
				align : 'center'
			}, {
				field : 'status',
				title : '状态',
				width : 100,
				align : 'center',
				formatter : function(value, row, index) {
					if (value == 1) {
						return '启用';
					} else if (value == 0) {
						return '禁用';
					}
				}
			},

			{
				field : 'note',
				title : '备注',
				width : 100,
				align : 'center'
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
		app.enableCombobox($('#status', editDialog));
		$("#statu").combobox('clear');
	
	}

};

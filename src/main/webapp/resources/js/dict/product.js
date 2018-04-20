var product = {

	initCRUDBtn : function() {
		// 添加
		$(app.id.addBtnId).click(function() {
			product.add();
		});
		//添加进度
		$("#addScheduleBtn").click(function() {
			product.addSchedule();
		});
		// 修改
		$(app.id.updateBtnId).click(function() {
			product.update();
		});
		//修改进度
		$("#updateScheduleBtn").click(function() {
			product.updateSchedule();
		});
		// 删除
		$(app.id.deleteBtnId).click(function() {
			product.mulDelete();
		});
		//删除进度
		$("#deleteScheduleBtn").click(function() {
			product.mulDeleteSchedule();
		});
		// 查询
		$(app.id.searchBtnId).click(function() {
			product.search();
		});
		// 查询产品进度名称
		$("#searchScheduleBtn").click(function() {
			product.searchSchedule();
		});
		// 清空查询条件
		$(app.id.clearBtnId).click(function() {
			$(app.id.searchFormId).form('clear');
		});
		
		// 清空产品进度名称查询条件
		$("#clearScheduleBtn").click(function() {
			$("#clearScheduleBtn").form('clear');
		});
		// 上移
		$("#upBtn").click(function() {
			product.change(-1);
		});
		// 上移进度
		$("#upScheduleBtn").click(function() {
			product.changeSchedule(-1);
		});
		// 下移
		$("#downBtn").click(function() {
			product.change(1);
		});
		// 下移进度
		$("#downScheduleBtn").click(function() {
			product.changeSchedule(1);
		});
		//进度设置
		$("#config").click(function(){
			product.config();
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
			height : 250,
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
	initEditScheduleForm : function() {
		$("#editScheduleForm").form({
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
						$("#editScheduleDialog").dialog('close');
						$("#ScheduleList").datagrid('reload');
					});
				} else {
					$.messager.alert('提示', data.message, 'error');
				}
			}
		});
	},
	initEditScheduleDialog : function() {
		var params = {
			title : '产品进度',
			width : 400,
			height : 350,
			closed : true,
			cache : false,
			modal : true,
			buttons : [ {
				text : '保存',
				iconCls : 'icon-save',
				handler : function() {
					$("#editScheduleForm").submit();
				}
			}, {
				text : '退出',
				iconCls : 'icon-exit',
				handler : function() {
					$("#editScheduleDialog").dialog('close');
				}
			} ],
			onClose : function() {
				$("#editScheduleDialog").form('clear');
			}
		};
		$("#editScheduleDialog").dialog(params);
	},
	
	add : function() {
		$(app.id.editDialogId).dialog('open');
		product.initEditForm();
		$("#status").combobox("setValue",1);
		$(app.id.editFormId).attr('action', app.url.create());
		app.yesOrNoByIntCombobox("#isSys");
	},
	addSchedule : function() {
		var selectedRow = $(app.id.listId).datagrid('getSelected');
		$("#productId").val(selectedRow.id);
		$("#editScheduleDialog").dialog('open');
		product.initEditScheduleForm();
		$("#editScheduleForm").attr('action', app.getUrl("createSchedule"));
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
		product.initEditForm();
		$(app.id.editFormId).form('load', selectedRow);
		$(app.id.editFormId).attr('action', app.url.update());
		app.yesOrNoByIntCombobox("#isSys");
	},
	updateSchedule : function(){
		var selectedRow = $("#ScheduleList").datagrid('getSelected');
		if (selectedRow == null) {
			$.messager.alert('提示', '请选择数据行', 'warning');
			return;
		}
		$("#productId").val(selectedRow.productId);
		$("#editScheduleDialog").dialog('open');
		product.initEditScheduleForm();
		$("#editScheduleForm").form('load', selectedRow);
		$("#editScheduleForm").attr('action', app.getUrl("updateSchedule"));
		
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
	mulDeleteSchedule : function() {
		var checkedRows = $("#ScheduleList").datagrid('getChecked');
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
			CSIT.asyncCallService(app.getUrl("mulDeleteSchedule"), content,
					function(result) {
						if (result.isSuccess) {
							$("#ScheduleList").datagrid('reload');
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
	searchSchedule : function(){
		var isValid = $("#searchFormSchedule").form('validate');
		if (!isValid) {
			return false;
		}
		var content = $("#searchFormSchedule").serializeObject();
		$("#ScheduleList").datagrid({
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
		var code = selectRow.code;
		var note = selectRow.note==undefined?'':selectRow.note;
		var name = selectRow.name;
		var status = selectRow.status;
		var array = updateRow.array;
		if(updateRow.note==null){
			updateRow.note = '';
		}
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
					code : code,
					note : note,
					status:status,
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
	changeSchedule : function(direction) {
		var selectRow = $("#ScheduleList").datagrid('getSelected');
		var selectIndex = $("#ScheduleList").datagrid('getRowIndex', selectRow);
		if (selectRow == null) {
			$.messager.alert("提示", "请选中一条记录", "warning");
			return;
		}
		var rows = $("#ScheduleList").datagrid('getRows');
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
		var note = selectRow.note==undefined?'':selectRow.note;
		var role = selectRow.role==undefined?'':selectRow.role;
		var duration = selectRow.duration==undefined?'':selectRow.duration;
		var remind = selectRow.remind==undefined?'':selectRow.remind;
		var remindUnit = selectRow.remindUnit==undefined?'':selectRow.remindUnit;
		var scheduleName = selectRow.scheduleName;
		var array = updateRow.array;
		if(updateRow.note==null){
			updateRow.note = '';
		}
		updateRow.array = selectRow.array;
		// 后台更新排序
		var content = {
			srcId : id,
			destId : updateRow.id
		};
		var result = CSIT.syncCallService(app.getUrl('changeArraySchedule'), content);
		if (result.isSuccess) {
			$("#ScheduleList").datagrid('updateRow', {
				index : selectIndex,
				row : updateRow
			});
			$("#ScheduleList").datagrid('updateRow', {
				index : selectIndex + direction,
				row : {
					id : id,
					note : note,
					role : role,
					duration : duration,
					remind : remind,
					remindUnit : remindUnit,
					array : array,
					name : name,
					scheduleName :scheduleName
				}
			});
			$("#ScheduleList").datagrid('unselectRow', selectIndex);
			$("#ScheduleList").datagrid('selectRow', selectIndex + direction);
		} else {
			$.messager.alert("错误", result.message, "error");
		}
	},
	
	config : function(){
		var selectedRow = $(app.id.listId).datagrid('getSelected');
		if (selectedRow == null) {
			$.messager.alert('提示', '请选择数据行', 'warning');
			return;
		}
		$("#configDialog").dialog({
			title:selectedRow.name+"的产品进度设置"
		});
		$("#ScheduleList").datagrid({
			url : app.getUrl("listScheduleByPage"),
			queryParams:{"productId":selectedRow.id},
			pagination : true,
			fit : true,
			border : false,
			toolbar : "#tb2",
			ctrlSelect : true,
			rownumbers : true,
			pageSize : 10,
			columns:[[
	          {field:'ck',checkbox:true,width:20,align:'center'},
	          {field : 'scheduleName',title : '产品进度名称',width : 150,align : 'center'},
	          {field : 'role',title : '执行角色',width : 150,align : 'center',formatter: function(value,row,index){
                  if(value == 1){
                      return "拥有人";
                  }else if(value == 2){
                      return "跟单人";
                  }else if(value == 3){
                      return "客服负责人";
                  }else if(value == 4){
                      return "客服助理";
                  }},
				},
	          {field : 'duration',title : '截止天数',width : 100,align : 'center'},
	          {field : 'remind',title : '提前提醒时间',width : 100,align : 'center',formatter: function(value,row,index){
	        	  		if(row.remind != '' && row.remind !=null){
	        	  			if(row.remindUnit != '' && row.remindUnit !=null){
	        	  				if(row.remindUnit == 1){
	        	  					return row.remind + "天";
	        	  				}else{
	        	  					return row.remind + "小时";
	        	  				}
	        	  			}else{
	        	  				return row.remind;
	        	  			}
	        	  		}
                  },
				},
			]],
			pageList : [ 10, 20, 30, 40, 50, 100 ],
			onDblClickRow : function() {
				$("#updateScheduleBtn").click();
			}
		});
		$("#configDialog").dialog('open');
	},
	initConfigDialog : function(){
		var params = {
				width : 1000,
				height : document.documentElement.clientHeight-5<500?document.documentElement.clientHeight-5:500,
				closed : true,
				cache : false,
				modal : true,
			};
		$("#configDialog").dialog(params);
	},
	init : function(params) {
		app.roleCombobox("#roles");
		app.remindUnitCombobox("#remindUnit");
		product.initEditDialog();
		product.initConfigDialog();
		product.initEditScheduleDialog();
		product.initCRUDBtn();
		product.initViewList({
			columns : [ [ {
				field : 'ck',
				checkbox : true
			},
			/* {field:'code',title:'编号',width:100,align:'center'}, */
			{
				field : 'name',
				title : '产品名称',
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

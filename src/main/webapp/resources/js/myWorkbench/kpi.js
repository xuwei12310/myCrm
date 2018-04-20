var selectRowOption = null;
var selectIndexOption = null;
var kpi = {

	initCRUDBtn : function() {
		// 添加
		$(app.id.addBtnId).click(function() {
			kpi.add();
		});
		// 修改
		$(app.id.updateBtnId).click(function() {
			kpi.update();
		});
		// 删除
		$(app.id.deleteBtnId).click(function() {
			kpi.mulDelete();
		});
		// 查询
		$(app.id.searchBtnId).click(function() {
			kpi.search();
		});
		// 清空查询条件
		$(app.id.clearBtnId).click(function() {
			$(app.id.searchFormId).form('clear');
		});
		$("#updateAchieveBtn").click(function(){
			kpi.updateAchieve();
		});
		//员工
		$("#employeeNameSearch").textbox({
            onClickIcon: function (index) {
                if (index == 0) {
                	$("#selectEmployeeEditDialog").dialog('open');
                }
            }
        }); 
		//员工中搜索姓名
		$("#searchEmployeeBtn").click(function(){
			var isValid = $("#searchEmployeeForm").form('validate');
			if (!isValid) {
				return false;
			}
			var content = $("#searchEmployeeForm").serializeObject();
			$("#employeeList").datagrid({
				url:app.getUrl("getEmployeeList"),
				queryParams : content
			});
    	});
		// 员工中清空查询条件
		$("#clearEmployeeBtn").click(function() {
			$("#searchEmployeeForm").form('clear');
		});
		//查询姓名未选择
    	$("#employeeEditSearchBtn").click(function(){
    		var content = $("#employeeEditSearch").serializeObject();
    		$('#employeeleftGrid').datagrid({  
    			 url:app.getUrl("listPersonByPage"),
    			 queryParams : content
  		     });
    	});
    	//清空姓名
    	 $("#employeeEditClearBtn").click(function(){
    		 $("#employeeEditSearch").form('clear');
     	});
    	 //员工
		$("#employeeName").textbox({
			onClickIcon: function (index) {
                if (index == 0) {
                	$("#employeeEditDialog").dialog('open');
                	var affiliatedId = $('#userId').val();
                	$("#employeeEditSearch").form('clear');
                	$("#StoreEmployeeId").val(affiliatedId);
                	$('#employeeleftGrid').datagrid({
                		url:app.getUrl("listPersonByPage"),
                		queryParams: {
                			"personId":affiliatedId
                		}
            		});
    	    		$("#employeerightGrid").datagrid({
    	    			queryParams: {
    	    				"personId": affiliatedId
    	    			}
    	    		});
                }
            }
		});
	},
	initViewList : function(params) {
		var params = $.extend({
			url : app.getUrl("listByPage"),
			pagination : true,
			fit : true,
			border : false,
			toolbar : app.id.toolbarId,
			ctrlSelect : true,
			rownumbers : true,
			pageSize : 20,
				columns : [ [
				        {field:'employeeName',title:'人员/月份',width:100,align:'center'},
	       		        {field:'oneMonth',title:'1月',width:100,align:'center'},
	       		        {field:'twoMonth',title:'2月',width:100,align:'center'},
	       		        {field:'threeMonth',title:'3月',width:100,align:'center'},
	       		        {field:'fourMonth',title:'4月',width:100,align:'center'},
	       		        {field:'fiveMonth',title:'5月',width:100,align:'center'},
	       		        {field:'sixMonth',title:'6月',width:100,align:'center'},
	       		        {field:'sevenMonth',title:'7月',width:100,align:'center'},
	       		        {field:'eightMonth',title:'8月',width:100,align:'center'},
	       		        {field:'nineMonth',title:'9月',width:100,align:'center'},
	       		        {field:'octMonth',title:'10月',width:100,align:'center'},
	       		        {field:'elevenMonth',title:'11月',width:100,align:'center'},
	       		        {field:'twelveMonth',title:'12月',width:100,align:'center'},
	       		        {field:'id',hidden:true}
	       		        ] ],
			pageList : [ 10, 20, 30, 40, 50, 100 ],
			onDblClickRow : function() {
				$(app.id.updateBtnId).click();
			}
		}, params);
		$(app.id.listId).datagrid(params);
	},
	initMonthList:function(params) {
		$("#monthList").datagrid({
			fit : true,
			autoSave:true,
			singleSelect:true,
				columns : [ [
				        {field:'month',title:'月份（月）',width:100,align:'center'},
	       		        {field:'target',title:'指标（万元）',width:100,align:'center',editor:{type:'numberbox',options:{precision:0}}}
	       		        ] ],
	       		     onSelect:function(rowIndex, rowData){
	       				if(selectIndexOption != null&&selectIndexOption != rowIndex){
	       					$(this).datagrid('endEdit', selectIndexOption);
	       				}
	       				$(this).datagrid('beginEdit', rowIndex);
	       				selectRowOption = rowData;
	       				selectIndexOption = rowIndex;
	       				
	       			}
		});
		
	},
	initMonthModifList:function(params) {
		$("#monthmodifList").datagrid({
			fit : true,
			autoSave:true,
			singleSelect:true,
				columns : [ [
				        {field:'month',title:'月份（月）',width:100,align:'center'},
	       		        {field:'target',title:'指标（万元）',width:100,align:'center',editor:{type:'numberbox',options:{precision:2,min:0}}}
	       		        ] ],
	       		     	onSelect:function(rowIndex, rowData){
							if(selectIndexOption != null&&selectIndexOption != rowIndex){
								$(this).datagrid('endEdit', selectIndexOption);
							}
							$(this).datagrid('beginEdit', rowIndex);
							var edit=$(this).datagrid('getEditor',{index:rowIndex,field:'target'});
                            $(edit.target[0]).next('span').find('input').focus();
							selectRowOption = rowData;
							selectIndexOption = rowIndex;
	       				}
		});
		
	},
	initEditDialog : function() {
		var params = {
			title : '编辑' + resourceName,
			width : 400,
			height : document.documentElement.clientHeight-5<645?document.documentElement.clientHeight-5:645,
			closed : true,
			cache : false,
			modal : true,
			buttons : [ {
				text : '保存',
				iconCls : 'icon-save',
				handler : function() {
					if(selectIndexOption != null){
       					$("#monthList").datagrid('endEdit', selectIndexOption);
       				}
					var rows = $("#monthList").datagrid('getRows');
					var rowsList = new Array();
					for (var int = 0; int < rows.length; int++) {
						if(rows[int].target==undefined){
							rowsList.push("0");
						}else{
							rowsList.push(rows[int].target);
						}
					}
					var ss=rowsList.join(',');
					$("#tararray").val(ss);
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
	//选择员工
	initSelectEmployeeEditDialog:function(){
    	$('#employeeList').datagrid({  
			  border:true,
			  url:app.getUrl("getEmployeeList"),
			  rownumbers:true,
			  pagination:true,
			  fit:true,
			  toolbar:[{
				  text:'选择',
					iconCls:'icon-ok',
					handler:function(){
						var selectedRow = $('#employeeList').datagrid('getSelected');
						if(selectedRow==null){
				    		$.messager.alert('提示','请选择数据行','warning');
				    		return;
				    	}
						$("#employeeNameSearch").textbox("setValue", selectedRow.name);
				      //  $("#userIdmodif").val(selectedRow.id);
				        $("#selectEmployeeEditDialog").dialog('close');
				      
					}
				}],
	    	  ctrlSelect:true,
			  method:"POST",
		      columns:[[           
                  {field:'name',title:'姓名',width:100,align:'center'}
		   	  ]],
		   	  onDblClickCell: function(index,field,value){
		   		  var rows = $('#employeeList').datagrid('getRows');
		   		  var selectedRow = rows[index];
		   		  $("#employeeNameSearch").textbox("setValue", selectedRow.name);
		          //$("#userIdmodif").val(selectedRow.id);
		          $("#selectEmployeeEditDialog").dialog('close');
		       
		   	  }
		});
    	var params = {
	    	title:'选择员工',
			width : 600,
			height : document.documentElement.clientHeight-5<535?document.documentElement.clientHeight-5:535,
			closed : true,
			cache : false,
			modal : true,
		};
	    $("#selectEmployeeEditDialog").dialog(params);
    },
	initEditModifDialog : function() {
		var params = {
			title : '编辑' + resourceName,
			width : 400,
			height : document.documentElement.clientHeight-5<635?document.documentElement.clientHeight-5:635,
			closed : true,
			cache : false,
			modal : true,
			buttons : [ {
				text : '保存',
				iconCls : 'icon-save',
				handler : function() {
					if(selectIndexOption != null){
       					$("#monthmodifList").datagrid('endEdit', selectIndexOption);
       				}
					var rows = $("#monthmodifList").datagrid('getRows');
					var rowsList = new Array();
					for (var int = 0; int < rows.length; int++) {
						if(rows[int].target==undefined){
							rowsList.push("0");
						}else{
							rowsList.push(rows[int].target);
						}
					}
					var ss=rowsList.join(',');
					$("#tararraymodif").val(ss);
					$("#editmodifForm").submit();
				}
			}, {
				text : '退出',
				iconCls : 'icon-exit',
				handler : function() {
					$("#editmodifDialog").dialog('close');
				}
			} ],
			onClose : function() {
				$("#editmodifDialog").form('clear');
			}
		};
		$("#editmodifDialog").dialog(params);
	},
	initEditAchieveDialog: function() {
		var params = {
				title : '更新业绩',
				width : 350,
				height : document.documentElement.clientHeight-5<225?document.documentElement.clientHeight-5:225,
				closed : true,
				cache : false,
				modal : true,
				buttons : [ {
					text : '保存',
					iconCls : 'icon-save',
					handler : function() {
						$("#editAchieveForm").submit();
					}
				}, {
					text : '退出',
					iconCls : 'icon-exit',
					handler : function() {
						$("#editAchieveDialog").dialog('close');
					}
				} ],
				onClose : function() {
					$("#editAchieveDialog").form('clear');
				}
			};
			$("#editAchieveDialog").dialog(params);
		},
	initmodifEditForm : function() {
		$("#editmodifForm").form({
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
						$("#editmodifDialog").dialog('close');
						$(app.id.listId).datagrid('reload');
					});
				} else {
					$.messager.alert('提示', data.message, 'error');
				}
			}
		});
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
	initAchieveEditForm :function(){
		$("#editAchieveForm").form({
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
						kpi.search();
						$("#editAchieveDialog").dialog('close');
						
					});
				} else {
					$.messager.alert('提示', data.message, 'error');
				}
			}
		});
	},
	employee_leftToRight:function(){
		kpi.addEmployeePerson();
	},
	addEmployeePerson : function(){
		var checkedRows = $("#employeeleftGrid").datagrid('getChecked');
		if(checkedRows.length==0){
			 $.messager.alert('提示','请选择数据行','warning');
             return;
		}
/*		var dataid = $('#userId').val();
		var dataName = $("#employeeName").textbox('getValue');*/
		var dataid = $('#StoreEmployeeId').val();
    	var dataName = $("#StoreEmployeeName").val();
		if(dataid!=undefined && dataid!=''){
    		dataid = dataid + ",";
    	}
    	if(dataName!=undefined&& dataName!=''){
    		dataName = dataName + ",";
    	}
    	for(var i=0;i<checkedRows.length;i++){
    		dataid = dataid + checkedRows[i].id + ",";
    		dataName = dataName + checkedRows[i].name + ",";
    		if(i==checkedRows.length-1){
    			dataid=dataid.substr(0, dataid.length - 1);
    			dataName=dataName.substr(0, dataName.length - 1);
    		}
    	}
    /*	$("#employeeName").textbox("setValue", dataName);
        $("#userId").val(dataid);*/
    	$("#StoreEmployeeName").val(dataName);
        $("#StoreEmployeeId").val(dataid);
        $('#employeeleftGrid').datagrid({
    		queryParams: {
    			"personId":dataid
    		}
		});
        $("#employeerightGrid").datagrid({
			queryParams: {
				"personId": dataid
			}
		});
	},
	employee_rightToRight :function(){
		kpi.delrightToRight();
	},
	delrightToRight :function(){
		var checkedRows = $('#employeerightGrid').datagrid('getChecked');
		if(checkedRows.length==0){
            $.messager.alert('提示','请选择数据行','warning');
            return;
        }
    /*	var dataid = $('#userId').val();
    	var dataName = $("#employeeName").textbox('getValue');*/
		var dataid = $('#StoreEmployeeId').val();
    	var dataName = $("#StoreEmployeeName").val();
    	if(dataid!=undefined && dataid!=''){
    		dataid = dataid + ",";
    	}
    	if(dataName!=undefined&& dataName!=''){
    		dataName = dataName + ",";
    	}
    	for(var i=0;i<checkedRows.length;i++){
    		dataid = dataid.replace(checkedRows[i].id+",",'');
    		dataName = dataName.replace(checkedRows[i].name+",",'');
    		if(i==checkedRows.length-1){
    			dataid=dataid.substr(0, dataid.length - 1);
    			dataName=dataName.substr(0, dataName.length - 1);
    		}
    	}
    /*	$("#employeeName").textbox("setValue", dataName);
        $("#userId").val(dataid);*/
    	$("#StoreEmployeeName").val(dataName);
        $("#StoreEmployeeId").val(dataid);
        $('#employeeleftGrid').datagrid({
    		queryParams: {
    			"personId":dataid
    		}
		});
    	$("#employeerightGrid").datagrid({
			 queryParams: {
				 "personId":dataid
			 }
		});
	},
	//选择员工
    initEmployeeEditDialog:function(){
		$('#employeerightGrid').datagrid({  
			  border:true,
			  method:"POST",
			  url:app.getUrl("listDelPersonByPage"),
			  rownumbers:true,
			  fit:true,
		      columns:[[           
		      {field:'ck',checkbox:true},
              {field: 'name', title: '姓名', width: 80, align: "center"},
              {field:'id',hidden:true}
		   	  ]]  
		});
    	$('#employeeleftGrid').datagrid({  
    		  method:"POST",
    		  url:app.getUrl("listPersonByPage"),
			  rownumbers:true,
			  pagination:true,
			  fit:true,
	    	  ctrlSelect:true,
		      columns:[[           
		        {field:'ck',checkbox:true},
                {field: 'name', title: '姓名', width: 80, align: "center"},
                {field:'id',hidden:true}
		   	  ]]
		});
    	var params = $.extend({
    		title:'选择员工',
    		height:document.documentElement.clientHeight-70,
    	    width:900,
			closed : true,
			cache : false,
			modal : true,
            buttons:[{
                text:'保存',
                iconCls:'icon-save',
                handler : function() {
					var dataName="";
					var dataid="";
					var rows=$("#employeerightGrid").datagrid("getRows");
					for(var i=0;i<rows.length;i++){
						dataName=dataName+rows[i].name+",";
						dataid=dataid+rows[i].id+","
						if(i==rows.length-1){
			    			dataid=dataid.substr(0, dataid.length - 1);
			    			dataName=dataName.substr(0, dataName.length - 1);
			    		}
					};
					$("#employeeName").textbox("setValue", dataName);
			        $("#userId").val(dataid);
					$("#employeeEditDialog").dialog('close');
				}
            },{
                text:'退出',
                iconCls:'icon-exit',
                handler : function() {
					$("#StoreEmployeeId").val("");
					$("#StoreEmployeeName").val("");
					$("#employeeEditDialog").dialog('close');
				}
            }],
		},params);
    	$("#employeeEditDialog").dialog(params);
	},
	add : function() {
		kpi.initMonthList();
		$(app.id.editDialogId).dialog('open');
		$("#monthList").datagrid('loadData',[{month:'1'},{month:'2'},{month:'3'},{month:'4'},{month:'5'},{month:'6'},{month:'7'},{month:'8'},{month:'9'},{month:'10'},{month:'11'},{month:'12'}]);
		kpi.initEditForm();
		$(app.id.editFormId).attr('action', app.url.create());
	},
	updateAchieve : function(){
		$("#editAchieveDialog").dialog('open');
		kpi.initAchieveEditForm();
		$("#editAchieveForm").attr('action', app.getUrl("getAchieve"));
	},
	update : function() {
		var selectedRow = $(app.id.listId).datagrid('getSelected');
		if (selectedRow == null) {
			$.messager.alert('提示', '请选择数据行', 'warning');
			return;
		}
		kpi.initMonthModifList();
		$("#editmodifDialog").dialog('open');
		$("#monthmodifList").datagrid('loadData',[{month:'1',target:selectedRow.one},{month:'2',target:selectedRow.two},{month:'3',target:selectedRow.three},
		                                          {month:'4',target:selectedRow.four},{month:'5',target:selectedRow.five},{month:'6',target:selectedRow.six},
		                                          {month:'7',target:selectedRow.seven},{month:'8',target:selectedRow.eight},{month:'9',target:selectedRow.nine},
		                                          {month:'10',target:selectedRow.oct},{month:'11',target:selectedRow.eleven},{month:'12',target:selectedRow.twelve}]);
		kpi.initmodifEditForm();
		$("#modifId").val(selectedRow.id);
		$("#modifyear").val(selectedRow.year);
		$("#modifyears").val(selectedRow.year);
		$("#userIdmodif").val(selectedRow.userId);
		$("#employeeNamemodif").textbox("setValue", selectedRow.employeeName);
		$("#editmodifForm").attr('action', app.url.update());
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
			var yearArray = [];
			for (var i = 0; i < checkedRows.length; i++) {
				idArray.push(checkedRows[i].userId);
				yearArray.push(checkedRows[i].year);
			}
			var ids = idArray.join(CSIT.join);
			var years = yearArray.join(CSIT.join);
			var content = {
				ids : ids,
				years : years
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
	//初始化员工-选择部门下拉框数据
	initApartmentId:function(companyId){
		$("#organizationId").combotree({
			animate:true,
    		lines:true,
    	  	dnd:true,
    	  	parentField:"pid",
    	  	url: app.getUrl("getOrganizationByCompany")+'?companyId='+companyId
		});
	},
	init : function(params) {
		kpi.initEditDialog(); 
		kpi.initCRUDBtn();
		kpi.initViewList();
		kpi.initEmployeeEditDialog();
		kpi.initEditModifDialog();
		kpi.initSelectEmployeeEditDialog();
		kpi.initEditAchieveDialog();
		$("#companyId").combotree({
				animate:true,
	    		lines:true,
	    	  	dnd:true,
	    	  	parentField:"pid",
	    	  	url: app.getUrl("getOrganizationByOrgType")+'?orgType=1'
			}); 
		$("#companyId").combotree({  
	         onChange:function(){  
	        	 var companyId = $("#companyId").combotree('getValue');
	        	 kpi.initApartmentId(companyId);
	         }
	     }); 
		var myDate = new Date();
		$("#yearsearch").val(myDate.getFullYear());
	}

};

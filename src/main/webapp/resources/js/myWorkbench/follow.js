var detailEditor=null;
var follow = {

	initCRUDBtn : function() {
		// 添加
		$(app.id.addBtnId).click(function() {
			follow.add();
            $("#editTable").css('height','370px');
		});
		// 修改
		$(app.id.updateBtnId).click(function() {
			follow.update();
		});
		// 删除
		$(app.id.deleteBtnId).click(function() {
			follow.mulDelete();
		});
		// 查询
		$(app.id.searchBtnId).click(function() {
			follow.search();
		});
		// 清空查询条件
		$(app.id.clearBtnId).click(function() {
			$(app.id.searchFormId).form('clear');
		});
		//创建任务
		$("#createMission").click(function(){
			if($("#createMission").is(":checked")){
				$("#taskContent").textbox({required:true});
				$("#nextDate").validatebox({required:true});
				$("#taskFollowPeople").textbox({required:true});
				$("#editTable").css('height','550');
				$("#missionBolock").css('display','table-row-group');
				$("#isActive").val(true);
				$("#remind").combobox("setValue",1);
				$(app.id.editDialogId).dialog('resize',{
					height : document.documentElement.clientHeight<730?document.documentElement.clientHeight:730
				})
				if(document.documentElement.clientHeight>=730){
					$(app.id.editDialogId).dialog('center');
				}
			}else{
				$("#editTable").css('height','370px');
				$("#missionBolock").css('display','none');
				$("#isActive").val(false);
				$("#taskContent").textbox({required:false});
				$("#nextDate").validatebox({required:false});
				$("#taskFollowPeople").textbox({required:false});
				$(app.id.editDialogId).dialog('resize',{
					height : document.documentElement.clientHeight<500?document.documentElement.clientHeight:500
				})
				if(document.documentElement.clientHeight>=500){
					$(app.id.editDialogId).dialog('center');
				}
			}
		});
		//客户列表
		$("#customerName").textbox({
			onClickIcon:function(index){
				$("#customerDialog").dialog('open');
				follow.initCustomerList();
			}
		});
		//跟进人员
		$("#followPersonnel").textbox({
			onClickIcon:function(index){
				$("#tb4").hide();
				$("#followPeopleDialog").dialog({toolbar:"#tb3"});
				$("#followPeopleDialog").dialog('open');
				follow.initFollowPeopleList();
			}
		});
		//任务跟进人员
		$("#taskFollowPeople").textbox({
			onClickIcon:function(index){
				$("#tb3").hide();
				$("#followPeopleDialog").dialog({toolbar:"#tb4"});
				$("#followPeopleDialog").dialog('open');
				$("#followPeopleList").datagrid({
					url:app.getUrl('getUserList'),
					pagination : true,
					fit : true,
					singleSelect : true,
					rownumbers : true,
					pageSize : 10,
					pageList : [ 10, 20, 30, 40, 50, 100 ],
					columns: [[{field:'username',title:'人事编号',width:100,align:'center'},
			        {field:'name',title:'姓名',width:100,align:'center'},
			        {field:'organizations',title:'所属部门',width:200,align:'center'}
		    	]],
				onDblClickRow : function() {
					$("#taskFollowPeopleSelected").click();
				}
				});
			}
		});
		//抄送人员
		$("#receiverNameTask").textbox({
			onClickIcon:function(index){
				$("#taskreceiverEditDialog").dialog('open');
				var dataid=$("#receiveTaskId").val();
				if(dataid!=null){
				 $("#storeId").val(dataid);
				 $('#taskreceiverleftGrid').datagrid({
			    		queryParams: {
			    			"personId":dataid
			    		}
					});
			        $("#taskreceiverrightGrid").datagrid({
						queryParams: {
							"personId": dataid
						}
					});
				}
			}
		});
		//选择客户
		$("#customerSelected").click(function(){
			var selectedRow=$("#customerList").datagrid('getChecked');
			if(selectedRow.length!=1){
				$.messager.alert('提示', '请选择一条数据', 'warning');
				return;
			}
			$("#customerId").val(selectedRow[0].id);
			$("#customerName").textbox('setValue',selectedRow[0].customerName);
			$("#customerDialog").dialog('close');
		});
		//选择跟进人员
		$("#followPeopleSelected").click(function(){
			var selectedRow=$("#followPeopleList").datagrid('getChecked');
			if(selectedRow.length!=1){
				$.messager.alert('提示', '请选择一条数据', 'warning');
				return;
			}
			$("#followPersonnelId").val(selectedRow[0].id);
			$("#followPersonnel").textbox('setValue',selectedRow[0].name);
			$("#followPeopleDialog").dialog('close');
		});
		//选择任务跟进人员
		$("#taskFollowPeopleSelected").click(function(){
			var selectedRow=$("#followPeopleList").datagrid('getChecked');
			if(selectedRow.length!=1){
				$.messager.alert('提示', '请至少选择一条数据', 'warning');
				return;
			}
			$("#taskFollowPersonnelId").val(selectedRow[0].id);
			$("#taskFollowPeople").textbox('setValue',selectedRow[0].name);
			$("#followPeopleDialog").dialog('close');
		});
		//选择抄送人员
		$("#receiverSelected").click(function(){
			var selectedRow=$("#receiverList").datagrid('getChecked');
			if(selectedRow.length==0){
				$.messager.alert('提示', '请选择一条数据', 'warning');
				return;
			}
			var ids="";
			var receiverName="";
			for(var i=0;i<selectedRow.length;i++){
				var id=selectedRow[i].id+",";
				ids+=id;
				var name=selectedRow[i].name+",";
				receiverName+=name;
			}
			receiverName=receiverName.substring(0,receiverName.length-1);
			$("#ids").val(ids);
			$("#receiverName").textbox('setValue',receiverName);
			$("#taskreceiverEditDialog").dialog('close');
		});
		//查询任务姓名未选择
     	$("#taskreceiverEditSearchBtn").click(function(){
     		var content = $("#taskreceiverEditSearch").serializeObject();
     		$('#taskreceiverleftGrid').datagrid({  
     			 url:ctx+"/myWorkbench/todo/listPersonByPage.jhtml",
     			 queryParams : content
   		     });
     	});
     	//清空任务姓名
     	 $("#taskreceiverEditClearBtn").click(function(){
     		 $("#taskreceiverEditSearch").form('clear');
      	});
     	//查询跟进人员
      	$("#followPeopleSearchBtn").click(function(){
      		var content = $("#followPeopleSearch").serializeObject();
      		$('#followPeopleList').datagrid({  
      			 url:app.getUrl('getUserList'),
      			 queryParams : content
    		     });
      	});
      	//清空跟进人员查询
      	 $("#followPeopleClearBtn").click(function(){
      		 $("#followPeopleSearch").form('clear');
       	});
      	//查询客户
       	$("#customerSearchBtn").click(function(){
       		var content = $("#customerSearch").serializeObject();
       		$('#customerList').datagrid({  
       			 queryParams : content
     		     });
       	});
       	//清空客户查询
       	 $("#customerClearBtn").click(function(){
       		 $("#customerSearch").form('clear');
        	});
		//下载
		$("#downloadBtn").click(function(){
			var selectedRow=$("#list").datagrid('getChecked');
			if(selectedRow.length!=1){
				$.messager.alert('提示', '请选择一条数据', 'warning');
				return;
			}
			var data={followId:selectedRow[0].id};
			CSIT.asyncCallService(app.getUrl("getAttach"),data,function(result){
				if(result.isSuccess){
					window.open(ctx+"/myWorkbench/follow/download.jhtml?followId="+selectedRow[0].id);
				}else{
					$.messager.alert("提示",result.message);
				}
			});
		});
	},
	//任务抄送人
	taskreceiver_leftToRight:function(){
		follow.addTaskReceiverPerson();
	},
	addTaskReceiverPerson : function(){
		var checkedRows = $('#taskreceiverleftGrid').datagrid('getChecked');
		 if(checkedRows.length==0){
             $.messager.alert('提示','请选择数据行','warning');
             return;
         }
    	var dataid = $('#storeId').val();
    	var dataName = $("#storeName").val();
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
    	$("#storeName").val(dataName);
        $("#storeId").val(dataid);
        $('#taskreceiverleftGrid').datagrid({
    		queryParams: {
    			"personId":dataid
    		}
		});
        $("#taskreceiverrightGrid").datagrid({
			queryParams: {
				"personId": dataid
			}
		});
	},
	taskreceiver_rightToRight:function(){
		follow.taskdelrightToRight();
	},
	taskdelrightToRight:function(){
		var checkedRows = $('#taskreceiverrightGrid').datagrid('getChecked');
		if(checkedRows.length==0){
            $.messager.alert('提示','请选择数据行','warning');
            return;
        }
    	/*var dataid = $('#receiveTaskId').val();
    	var dataName = $("#receiverNameTask").textbox('getValue');*/
		var dataid = $('#storeId').val();
    	var dataName = $("#storeName").val();
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
    	/*$("#receiverNameTask").textbox("setValue", dataName);
        $("#receiveTaskId").val(dataid);*/
    	$("#storeName").val(dataName);
        $("#storeId").val(dataid);
        $('#taskreceiverleftGrid').datagrid({
    		queryParams: {
    			"personId":dataid
    		}
		});
    	$("#taskreceiverrightGrid").datagrid({
			 queryParams: {
				 "personId":dataid
			 }
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
	initCustomerList:function(){
		$("#customerList").datagrid({
			url:'{ctx}/myWorkbench/totalCustomer/listByPage.jhtml',
			pagination : true,
			fit : true,
			//border : false,
			toolbar:"#tb2",
			//ctrlSelect : true,
			singleSelect:true,
			rownumbers : true,
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50, 100 ],
			columns:[[
							{field:'customerName',title:'客户名称',width:100,align:'center'},
							{field:'customerType',title:'类型',width:100,align:'center',formatter: function(value,row,index){
		                        if(value == 1){
		                            return "个人";
		                        }else{
		                            return "企业";
		                        }},
							},
							{field:'liveAreaShowName',title:'所在地区',width:100,align:'center'},
							{field:'livePlotPlotName',title:'小区',width:100,align:'center'},
							{field:'customerStageName',title:'客户阶段',width:100,align:'center'},
							{field:'customerStatusName',title:'客户状态',width:100,align:'center'},
							{field:'mobilePhone',title:'手机号',width:120,align:'center'},
							{field:'lastTrackTime',title:'最后跟进时间',width:100,align:'center'},
							{field:'ownerUsername',title:'拥有人',width:100,align:'center'},
							{field:'id',hidden:true}
					]],
			onDblClickRow : function() {
				$("#customerSelected").click();
			}
		});
	},
	initFollowPeopleList:function(){
		$("#followPeopleList").datagrid({
			url:app.getUrl('getUserList'),
			pagination : true,
			fit : true,
			singleSelect : true,
			rownumbers : true,
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50, 100 ],
			columns: [[{field:'username',title:'人事编号',width:100,align:'center'},
	        {field:'name',title:'姓名',width:100,align:'center'},
	        {field:'organizations',title:'所属部门',width:200,align:'center'}
    	]],
		onDblClickRow : function() {
			$("#followPeopleSelected").click();
		}
		});
	},
	initTaskreceiverEditDialog:function(){
		$('#taskreceiverrightGrid').datagrid({  
			  border:true,
			  method:"POST",
			  url:ctx+"/myWorkbench/todo/listDelPersonByPage.jhtml",
			  rownumbers:true,
			  fit:true,
		      columns:[[           
		      {field:'ck',checkbox:true},
            {field: 'name', title: '姓名', width: 80, align: "center"},
            {field:'id',hidden:true}
		   	  ]]  
		});
  	$('#taskreceiverleftGrid').datagrid({  
  		  method:"POST",
			  url:ctx+"/myWorkbench/todo/listPersonByPage.jhtml",
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
  		title:'选择抄送人',
  		height:document.documentElement.clientHeight-70,
  	    width:900,
			closed : true,
			cache : false,
			modal : true,
			buttons : [ {
					text : '保存',
					iconCls : 'icon-save',
					handler : function() {
						var dataName="";
						var dataid="";
						var rows=$("#taskreceiverrightGrid").datagrid("getRows");
						for(var i=0;i<rows.length;i++){
							dataName=dataName+rows[i].name+",";
							dataid=dataid+rows[i].id+","
							if(i==rows.length-1){
				    			dataid=dataid.substr(0, dataid.length - 1);
				    			dataName=dataName.substr(0, dataName.length - 1);
				    		}
						};
						$("#receiverNameTask").textbox("setValue", dataName);
				        $("#receiveTaskId").val(dataid);
						$("#taskreceiverEditDialog").dialog('close');
					}
				}, {
					text : '退出',
					iconCls : 'icon-exit',
					handler : function() {
						$("#storeId").val("");
						$("#storeName").val("");
						$("#taskreceiverEditDialog").dialog('close');
					}
				} ],
			onClose:function(){
			}
		},params);
  	$("#taskreceiverEditDialog").dialog(params);
	},
	initEditDialog : function() {
		var params = {
			title : '编辑' + resourceName,
			zIndex:50,
			width : document.documentElement.clientWidth<650?document.documentElement.clientWidth:650,
			height : document.documentElement.clientHeight<500?document.documentElement.clientHeight:500,
			closed : true,
			cache : false,
			modal : true,
			buttons : [ {
				text : '保存',
				iconCls : 'icon-save',
				handler : function() {
					$("#followDetails").val(detailEditor.html());
					$(app.id.editFormId).submit();
				}
			}, {
				text : '退出',
				iconCls : 'icon-exit',
				handler : function() {
					$(app.id.editDialogId).dialog('close');
					$(app.id.editFormId).form('clear');
					$("#missionBolock").css('display','none');
					detailEditor.html("");
				}
			} ],
			onClose : function() {
				$(app.id.editFormId).form('clear');
				$("#editTable").css('height','370px');
				$("#missionBolock").css('display','none');
				detailEditor.html("");
			}
		};
		$(app.id.editDialogId).dialog(params);
	},
	initCustomerDialog : function() {
		var params = {
			title : '选择客户',
			width : document.documentElement.clientWidth<1150?document.documentElement.clientWidth:1150,
			height : document.documentElement.clientHeight<550?document.documentElement.clientHeight:550,
			closed : true,
			cache : false,
			modal : true,
			onClose : function() {
			}
		};
		$("#customerDialog").dialog(params);
	},
	initFollowPeopleDialog : function() {
		var params = {
			title : '选择跟进人员',
			width : 550,
			height : 400,
			closed : true,
			cache : false,
			modal : true,
			onClose : function() {
			}
		};
		$("#followPeopleDialog").dialog(params);
	},
	initReceiverDialog : function() {
		var params = {
			title : '选择要抄送的员工',
			width : document.documentElement.clientWidth<550?document.documentElement.clientWidth:550,
			height : document.documentElement.clientWidth<500?document.documentElement.clientWidth:500,
			closed : true,
			cache : false,
			modal : true,
			onClose : function() {
			}
		};
		$("#taskreceiverEditDialog").dialog(params);
	},
	initEditForm : function() {
		$(app.id.editFormId).form({
			onSubmit : function() {
				var isValid = $(this).form('validate');
    			if (!isValid){
    				$.messager.progress('close');
    			}
    			var attachIdArray = new Array();
    			$(".uploader_file").each(function(){
    				var attachId = $(this).attr("attachId");
    				attachIdArray.push(attachId);
    			});
    			var attachIds = attachIdArray.join(CSIT.join);
    			$("#attachIds").val(attachIds);
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
			title:"添加"+resourceName,
			height : document.documentElement.clientHeight<500?document.documentElement.clientHeight:500
		});
		$(app.id.editDialogId).dialog('open');
		$("#customerName").textbox({disabled:false})
		$("#nextDate").validatebox({required:false});
		$("#taskFollowPeople").textbox({required:false});
		$("#isActive").val(false);
		$("#create").show();
		var date=new Date();
		var year=date.getFullYear();
		var month=date.getMonth()+1;
		//获取当前日
		var day=date.getDate(); 
		$("#startDate").val(year+"-"+month+"-"+day);
		$("#nextDate").val(year+"-"+month+"-"+day);
		var user=CSIT.syncCallService(ctx + "/myWorkbench/follow/getCurrentUser.jhtml");
		$("#followPersonnelId").val(user.id);
		$("#taskFollowPersonnelId").val(user.id);
		$("#followPersonnel").textbox("setValue",user.username);
		$("#taskFollowPeople").textbox("setValue",user.username);
		
		follow.initEditForm();
		$("#status").combobox("setValue",1);
		//清空附件
    	$("#filelist").empty();
		$(app.id.editFormId).attr('action', app.url.create());
		app.yesOrNoByIntCombobox("#isSys");
	},
	update : function() {
		$(app.id.editDialogId).dialog({
			title:"编辑"+resourceName,
			height:430
		});
		//清空附件
    	$("#filelist").empty();
		$("#nextDate").validatebox({required:false});
		$("#taskFollowPeople").textbox({required:false});
		var selectedRow = $(app.id.listId).datagrid('getSelected');
		if (selectedRow == null) {
			$.messager.alert('提示', '请选择数据行', 'warning');
			return;
		}
		if (selectedRow.isSys == 1) {
			$.messager.alert('提示', '系统内置不能修改', 'warning');
			return;
		}
		//$("#file").val("111.jpg");
		$("#create").hide();
		$("#customerName").textbox({disabled:true});
		$("#customerId").val(selectedRow.customerId);
		$("#customerName").textbox('setValue',selectedRow.customerName);
		$("#followPersonnelId").val(selectedRow.followPersonnelId);
		$("#followPersonnel").textbox('setValue',selectedRow.followPersonnelName);
		detailEditor.html(selectedRow.followDetails);
		$(app.id.editDialogId).dialog('open');
		var result = CSIT.syncCallService(ctx + "/myWorkbench/follow/followAttach.jhtml",{followId:selectedRow.id});
    	for (var i = 0; i < result.length; i++) {
    		followAttach.createAttach(followAttach.uploader,result[i]);
		}
		follow.initEditForm();
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
	searchFollow : function(){
		var isValid = $("#searchFollowForm").form('validate');
		if (!isValid) {
			return false;
		}
		var content = $("#searchFollowForm").serializeObject();
		$("#followPeopleList").datagrid({
			url:app.getUrl("getUserList"),
			queryParams : content
		});
	},
    initKindEditorDialog:function(){
    	if(detailEditor==null){
    		detailEditor = KindEditor.create('#followDetails', {
				resizeType:0,
				//allowImageUpload: true, //上传图片框本地上传的功能，false为隐藏，默认为true
			   // allowImageRemote : false, //上传图片框网络图片的功能，false为隐藏，默认为true
				width:'100%',
				height:80,
//				cssPath:ctx+"/resources/css/img.css",
				items:[
				       	'source', '|', 'preview', 'undo', 'redo', 'cut', 'copy', 'paste',
				       	'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
				       	'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
				       	'superscript', 'clearhtml', 'quickformat', 
				       	'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
				       	'italic', 'underline', 'lineheight', 'removeformat',
				       	'table', 'link', 'unlink', 'fullscreen'
			    ],
			   // uploadJson : ctx+'/sys/meeting/uploadImgKindeditor.jhtml'
			});
		}
    },

	init : function(params) {
		follow.initEditDialog();
		//跟进附件上传外引js   followAttach.js
    	followAttach.init();
		follow.initCustomerDialog();
		follow.initFollowPeopleDialog();
		follow.initReceiverDialog();
		follow.initKindEditorDialog();
		follow.initTaskreceiverEditDialog();
		follow.initCRUDBtn();
		follow.initViewList({
			columns : [ [ {
				field : 'ck',
				checkbox : true
			},
			/* {field:'code',title:'编号',width:100,align:'center'}, */
			{
				field : 'customerName',
				title : '客户名称',
				width : 100,
				align : 'center'
			},{
				field : 'telephone',
				title : '电话',
				width : 100,
				align : 'center'
			},{
				field : 'followTime',
				title : '跟进时间',
				width : 100,
				align : 'center'
			},{
				field : 'followTypeName',
				title : '跟进方式',
				width : 100,
				align : 'center'
			},
			{
				field : 'followStageName',
				title : '跟进阶段',
				width : 100,
				align : 'center'
			},{
				field : 'frontStatusName',
				title : '跟进前客户状态',
				width : 100,
				align : 'center'
			},{
				field : 'afterStatusName',
				title : '跟进后客户状态',
				width : 100,
				align : 'center'
			},{
				field : 'followPersonnelName',
				title : '跟进人员',
				width : 100,
				align : 'center'
			},{
				field : 'source',
				title : '来源',
				width : 100,
				align : 'center',
				formatter:function(value,row,index){
					if(value==1){
						return "直接创建";
					}else if(value==2){
						return "任务创建";
					}
				}
			}
			 ] ]
		});
		app.followTypeCombobox($("#followType"));
		app.followStageCombobox($("#followStage"));
		app.customerStatusCombobox($("#customerStatus"));
		app.remind($("#remind"));
	}

};

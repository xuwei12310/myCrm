var detailEditor=null;
var totalCustomer = {
    init: function (params) {
    	app.initCombobox("#customerStatusSearch",ctx + "/common/util/getDictByType.jhtml",{type:"customerStatus"});
    	app.customerTypeCombobox("#customerTypeSearch");
    	totalCustomer.initEditDialog({});
    	totalCustomer.initBtn();
    	totalCustomer.initViewList();
    	//客户详细信息外引js   customerDetails.js
    	customerDetails.init();
    	totalCustomer.initAddSuccessDialog();
    	totalCustomer.initModEditDialog();
    	totalCustomer.initAddPlotDialog();
    	totalCustomer.initTransferEditDialog();
    	totalCustomer.initExportDialog();
    	totalCustomer.initFollowEditDialog();
    	totalCustomer.initCustomerDialog();
    	totalCustomer.initFollowPeopleDialog();
    	totalCustomer.initTaskreceiverEditDialog();
    	//跟进附件上传外引js   followAttach.js
    	followAttach.init();
    },
    //户籍地
    initPlaceEditDialog:function(){
    	$('#placeList').datagrid({  
			  border:true,
			  url:ctx + "/myWorkbench/totalCustomer/getPlaceByList.jhtml",
			  rownumbers:true,
			  pagination:true,
			  fit:true,
			  toolbar:[{
					text:'选择',
					iconCls:'icon-ok',
					handler:function(){
						var selectedRow = $('#placeList').datagrid('getSelected');
						if(selectedRow==null){
				    		$.messager.alert('提示','请选择数据行','warning');
				    		return;
				    	}
						$("#placeIdBtn").textbox("setValue", selectedRow.showName);
				        $("#placeId").val(selectedRow.id);
				        $("#placeEditDialog").dialog('close');
					}
				}],
	    	  ctrlSelect:true,
			  method:"POST",
		      columns:[[      
		          {field:'provinceName',title:'省份',width:100,align:'center'},
		          {field:'cityName',title:'城市',width:160,align:'center'},
		          {field:'areaName',title:'区/县名称',width:100,align:'center'},
		          {field:'showName',title:'显示名称',width:200,align:'center'},
		   	  ]],
		   	  onDblClickCell: function(index,field,value){
		   		  var rows = $('#placeList').datagrid('getRows');
		   		  var selectedRow = rows[index];
		   		  $("#placeIdBtn").textbox("setValue", selectedRow.showName);
		          $("#placeId").val(selectedRow.id);
		          $("#placeEditDialog").dialog('close');
		   	  }
		});
    	var params = {
	    	title:'选择户籍地',
			width : 700,
			height : document.documentElement.clientHeight-5<600?document.documentElement.clientHeight-5:600,
			closed : true,
			cache : false,
			modal : true,
		};
	    $("#placeEditDialog").dialog(params);
    },
    //配偶户籍地
    initSpousePlaceEditDialog:function(){
    	$('#spousePlaceList').datagrid({  
			  border:true,
			  url:ctx + "/myWorkbench/totalCustomer/getPlaceByList.jhtml",
			  rownumbers:true,
			  pagination:true,
			  fit:true,
			  toolbar:[{
				  text:'选择',
					iconCls:'icon-ok',
					handler:function(){
						var selectedRow = $('#spousePlaceList').datagrid('getSelected');
						if(selectedRow==null){
				    		$.messager.alert('提示','请选择数据行','warning');
				    		return;
				    	}
						$("#spousePlaceIdBtn").textbox("setValue", selectedRow.showName);
				        $("#spousePlaceId").val(selectedRow.id);
				        $("#spousePlaceEditDialog").dialog('close');
					}
				}],
	    	  ctrlSelect:true,
			  method:"POST",
		      columns:[[      
		          {field:'provinceName',title:'省份',width:100,align:'center'},
		          {field:'cityName',title:'城市',width:160,align:'center'},
		          {field:'areaName',title:'区/县名称',width:100,align:'center'},
		          {field:'showName',title:'显示名称',width:200,align:'center'},
		   	  ]],
		   	  onDblClickCell: function(index,field,value){
		   		  var rows = $('#spousePlaceList').datagrid('getRows');
		   		  var selectedRow = rows[index];
		   		  $("#spousePlaceIdBtn").textbox("setValue", selectedRow.showName);
		          $("#spousePlaceId").val(selectedRow.id);
		          $("#spousePlaceEditDialog").dialog('close');
		   	  }
		});
    	var params = {
	    	title:'选择户籍地',
			width : 700,
			height : document.documentElement.clientHeight-5<600?document.documentElement.clientHeight-5:600,
			closed : true,
			cache : false,
			modal : true,
		};
	    $("#spousePlaceEditDialog").dialog(params);
    },
    //居住地区
    initLiveAreaEditDialog:function(){
    	$('#liveAreaList').datagrid({  
			  border:true,
			  url:ctx + "/myWorkbench/totalCustomer/getLiveAreaByList.jhtml",
			  rownumbers:true,
			  pagination:true,
			  fit:true,
			  toolbar:[{
				  text:'选择',
					iconCls:'icon-ok',
					handler:function(){
						var selectedRow = $('#liveAreaList').datagrid('getSelected');
						if(selectedRow==null){
				    		$.messager.alert('提示','请选择数据行','warning');
				    		return;
				    	}
						$('#areaData').val(selectedRow.id);
						$("#liveAreaIdBtn").textbox("setValue", selectedRow.showName);
				        $("#liveAreaId").val(selectedRow.id);
				        $("#livePlotIdBtn").textbox("setValue", "");
				        $("#livePlotId").val("");
				        $("#liveAreaEditDialog").dialog('close');
					}
				}],
	    	  ctrlSelect:true,
			  method:"POST",
		      columns:[[      
		          {field:'provinceName',title:'省份',width:100,align:'center'},
		          {field:'cityName',title:'城市',width:160,align:'center'},
		          {field:'areaName',title:'区/县名称',width:100,align:'center'},
		          {field:'showName',title:'显示名称',width:200,align:'center'},
		   	  ]],
		   	  onDblClickCell: function(index,field,value){
		   		  var rows = $('#liveAreaList').datagrid('getRows');
		   		  var selectedRow = rows[index];
		   		  $('#areaData').val(selectedRow.id);
		   		  $("#liveAreaIdBtn").textbox("setValue", selectedRow.showName);
		          $("#liveAreaId").val(selectedRow.id);
		          $("#livePlotIdBtn").textbox("setValue", "");
		          $("#livePlotId").val("");
		          $("#liveAreaEditDialog").dialog('close');
		   	  }
		});
    	var params = {
	    	title:'选择居住地区',
			width : 700,
			height : document.documentElement.clientHeight-5<600?document.documentElement.clientHeight-5:600,
			closed : true,
			cache : false,
			modal : true,
		};
	    $("#liveAreaEditDialog").dialog(params);
    },
    //小区
    initLivePlotEditDialog:function(){
    	$('#livePlotList').datagrid({  
			  border:true,
			  url:ctx + "/myWorkbench/totalCustomer/getLivePlotByList.jhtml",
			  rownumbers:true,
			  pagination:true,
			  fit:true,
			  toolbar:[{
				  	text:'选择',
					iconCls:'icon-ok',
					handler:function(){
						var selectedRow = $('#livePlotList').datagrid('getSelected');
						if(selectedRow==null){
				    		$.messager.alert('提示','请选择数据行','warning');
				    		return;
				    	}
						$("#livePlotIdBtn").textbox("setValue", selectedRow.plotName);
				        $("#livePlotId").val(selectedRow.id);
				        $("#livePlotEditDialog").dialog('close');
					}
				},{
					text:'新增',
					iconCls:'icon-add',
					handler:function(){
						var liveAreaId = $("#liveAreaId").val();
						var liveAreaName = $("#liveAreaIdBtn").textbox("getValue");
						$("#addPlotAreaId").val(liveAreaId);
						$('#liveAreaName').text(liveAreaName);
						$("#addPlotDialog").dialog('open');
						totalCustomer.initAddPlotForm();
						$("#addPlotForm").attr('action',ctx + "/dict/plot/create.jhtml");
					}
				}],
	    	  ctrlSelect:true,
			  method:"POST",
		      columns:[[  
		          {field:'areaName',title:'区县',width:120,align:'center'},
		          {field:'plotName',title:'小区',width:180,align:'center'},
		          {field:'status',title:'状态',width:100,align:'center',formatter: function(value,row,index){
                      if(value == 1){
                          return "启用";
                      }else{
                          return "禁用";
                      }},
					},
		          {field:'note',title:'备注',width:100,align:'center'}
		   	  ]],
		   	  onDblClickCell: function(index,field,value){
		   		  var rows = $('#livePlotList').datagrid('getRows');
		   		  var selectedRow = rows[index];
		   		  $("#livePlotIdBtn").textbox("setValue", selectedRow.plotName);
		          $("#livePlotId").val(selectedRow.id);
		          $("#livePlotEditDialog").dialog('close');
		   	  }
		});
    	var params = {
	    	title:'选择小区',
			width : 700,
			height : document.documentElement.clientHeight-5<600?document.documentElement.clientHeight-5:600,
			closed : true,
			cache : false,
			modal : true,
		};
	    $("#livePlotEditDialog").dialog(params);
    },
    //拥有人
    initOwnerEditDialog:function(){
    	$('#ownerList').datagrid({  
			  border:true,
			  url:ctx + "/myWorkbench/totalCustomer/getOwnerByList.jhtml",
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
		          {field:'username',title:'用户名',width:150,align:'center'},
		          {field:'name',title:'姓名',width:150,align:'center'},
		   	  ]],
		   	  onDblClickCell: function(index,field,value){
		   		  var rows = $('#ownerList').datagrid('getRows');
		   		  var selectedRow = rows[index];
		   		  $("#ownerIdBtn").textbox("setValue", selectedRow.name);
		          $("#ownerId").val(selectedRow.id);
		          $("#ownerEditDialog").dialog('close');
		   	  }
		});
    	var params = {
	    	title:'选择拥有人',
			width : 700,
			height : document.documentElement.clientHeight-5<600?document.documentElement.clientHeight-5:600,
			closed : true,
			cache : false,
			modal : true,
		};
	    $("#ownerEditDialog").dialog(params);
    },
    //转交人
    initTransferUserEditDialog:function(){
    	$('#transferUserList').datagrid({  
			  border:true,
			  url:ctx + "/common/util/getTransferUserByList.jhtml",
			  rownumbers:true,
			  pagination:true,
			  fit:true,
			  toolbar:[{
				  text:'选择',
					iconCls:'icon-ok',
					handler:function(){
						var selectedRow = $('#transferUserList').datagrid('getSelected');
						$("#toUserBtn").textbox("setValue", selectedRow.name);
				        $("#toUserId").val(selectedRow.id);
				        $("#transferUserEditDialog").dialog('close');
					}
				}],
	    	  ctrlSelect:true,
			  method:"POST",
		      columns:[[    
		          {field:'username',title:'用户名',width:150,align:'center'},
		          {field:'name',title:'姓名',width:150,align:'center'},
		   	  ]],
		   	  onDblClickCell: function(index,field,value){
		   		  var rows = $('#transferUserList').datagrid('getRows');
		   		  var selectedRow = rows[index];
		   		  $("#toUserBtn").textbox("setValue", selectedRow.name);
		          $("#toUserId").val(selectedRow.id);
		          $("#transferUserEditDialog").dialog('close');
		   	  }
		});
    	var params = {
	    	title:'选择转交人',
			width : 700,
			height : document.documentElement.clientHeight-5<600?document.documentElement.clientHeight-5:600,
			closed : true,
			cache : false,
			modal : true,
		};
	    $("#transferUserEditDialog").dialog(params);
    },
    //转交记录
    initTransferRecordEditDialog:function(){
    	$('#transferRecordList').datagrid({  
			  border:true,
			  url:ctx + "/myWorkbench/customerTransfer/listByPage.jhtml",
			  rownumbers:true,
			  pagination:true,
			  fit:true,
	    	  ctrlSelect:true,
			  method:"POST",
		      columns:[[    
		          {field:'customerName',title:'移交客户',width:100,align:'center'},
		          {field:'fromUserName',title:'移交前拥有人',width:100,align:'center'},
		          {field:'toUserName',title:'移交后拥有人',width:100,align:'center'},
		          {field:'reason',title:'移交原因',width:200,align:'center'},
		   	  ]],
		});
    	var params = {
	    	title:'转交记录',
			width : 700,
			height : document.documentElement.clientHeight-5<600?document.documentElement.clientHeight-5:600,
			closed : true,
			cache : false,
			modal : true,
		};
	    $("#transferRecordEditDialog").dialog(params);
    },
    initFollowPeopleList:function(){
		$("#followPeopleList").datagrid({
			url:ctx + "/myWorkbench/follow/getUserList.jhtml",
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
				var display =$('#tb3').css('display');
				if(display == 'none'){
					$("#taskFollowPeopleSelected").click();
				}else{
					$("#followPeopleSelected").click();
				}	
			}
		});
	},
    //浏览历史记录
    initViewHistoryEditDialog:function(){
    	$('#viewHistoryList').datagrid({  
			  border:true,
			  url:ctx + "/myWorkbench/customerViewHistory/listByPage.jhtml",
			  rownumbers:true,
			  pagination:true,
			  fit:true,
	    	  ctrlSelect:true,
			  method:"POST",
		      columns:[[    
		          {field:'customerName',title:'浏览客户',width:100,align:'center'},
		          {field:'viewUserName',title:'浏览人',width:100,align:'center'},
		          {field:'viewTime',title:'浏览时间',width:200,align:'center'},
		   	  ]],
		});
    	var params = {
	    	title:'浏览历史记录',
			width : 700,
			height : document.documentElement.clientHeight-5<600?document.documentElement.clientHeight-5:600,
			closed : true,
			cache : false,
			modal : true,
		};
	    $("#viewHistoryEditDialog").dialog(params);
    },
    imgsrc:function(){
    	totalCustomer.PreviewImage($("input[name='attachPhoto']")[0], 'Img', 'Imgdiv');
    },
    imgsrcMod:function(){
    	totalCustomer.PreviewImage($("input[name='attachPhotoMod']")[0], 'ImgMod', 'ImgModdiv');
    },
    //初始化客户列表
	initViewList:function(){
		$("#list").datagrid({
			url:ctx + "/myWorkbench/totalCustomer/listByPage.jhtml",
			pagination:true,
			fit:true,
			border:false,
			toolbar: app.id.toolbarId,
			ctrlSelect:true,
			rownumbers:true,
			pageSize:20,
			pageList:[10,20,30,40,50,100],
   	 		columns:[[
				    {field:'ck',checkbox:true},
					{field:'customerName',title:'客户名称',width:100,align:'center'},
					{field:'customerType',title:'类型',width:60,align:'center',formatter: function(value,row,index){
                        if(value == 1){
                            return "个人";
                        }else{
                            return "企业";
                        }},
					},
					{field:'sex',title:'性别',width:100,align:'center',hidden:true},
					{field:'telephone',title:'固定电话',width:100,align:'center',hidden:true},
					{field:'email',title:'邮箱',width:100,align:'center',hidden:true},
					{field:'idNumber',title:'身份证号',width:100,align:'center',hidden:true},
					{field:'idAddress',title:'身份证地址',width:100,align:'center',hidden:true},
					{field:'gradeId',title:'智能评级',width:100,align:'center',hidden:true},
					{field:'placeShowName',title:'户籍地',width:150,align:'center'},
					{field:'liveAreaShowName',title:'居住地区',width:150,align:'center'},
					{field:'livePlotPlotName',title:'小区',width:120,align:'center'},
					{field:'customerStageName',title:'客户阶段',width:100,align:'center'},
					{field:'customerStatusName',title:'客户状态',width:60,align:'center'},
					{field:'birthdate',title:'出生日期',width:100,align:'center',hidden:true},
					{field:'company',title:'就职单位',width:100,align:'center',hidden:true},
					{field:'occupation',title:'职业',width:100,align:'center',hidden:true},
					{field:'mobilePhone',title:'手机号',width:100,align:'center'},
					{field:'lastTrackTime',title:'最后跟进时间',width:140,align:'center'},
					{field:'createTime',title:'录入时间',width:140,align:'center'},
					{field:'ownerName',title:'拥有人',width:100,align:'center'},
					{field:'lastModifyTime',title:'修改时间',width:140,align:'center'},
					{field:'creditRatingId',title:'信用等级',width:100,align:'center',hidden:true},
					{field:'spouseName',title:'配偶姓名',width:100,align:'center',hidden:true},
					{field:'spousePlaceShowName',title:'配偶户籍地',width:150,align:'center',hidden:true},
					{field:'spouseMobilePhone',title:'配偶手机号',width:100,align:'center',hidden:true},
					{field:'spouseIdNumber',title:'配偶身份证号',width:100,align:'center',hidden:true},
					{field:'spouseCompany',title:'配偶就职单位',width:100,align:'center',hidden:true},
					{field:'spouseOccupation',title:'配偶职业',width:100,align:'center',hidden:true},
					{field:'id',hidden:true}
			]],
			onDblClickCell: function(index,field,value){
				totalCustomer.initPlaceModEditDialog();
		    	totalCustomer.initLiveAreaModEditDialog();
		    	totalCustomer.initLivePlotModEditDialog();
		    	totalCustomer.initSpousePlaceModEditDialog();
		    	totalCustomer.initOwnerModEditDialog();
				totalCustomer.update();
		   	},
			onLoadSuccess : function(date){
				var url=ctx + "/myWorkbench/totalCustomer/countCustomer.jhtml";
		    	var data ={data:1};
		    	var customerData = CSIT.syncCallService(url, data);
		    	$("#wholeCustomerNum").text(customerData.data.wholeCustomerNum);
		    	$("#personalCustomerNum").text(customerData.data.personalCustomerNum);
		    	$("#enterpriseCustomerNum").text(customerData.data.enterpriseCustomerNum);
		    	$("#todayUpdateNum").text(customerData.data.todayUpdateNum);
			},
			onLoadError: function(none){
				$.messager.alert('错误',"查询出错,请重新操作","error");
			}
		})
	},
	add:function(){
		$("#editForm").form('clear');
		var url=ctx + "/myWorkbench/totalCustomer/userInfo.jhtml";
    	var data ={data:1};
    	var userData = CSIT.syncCallService(url, data);
		app.initCombobox("#maritalId",ctx + "/common/util/getDictByType.jhtml",{type:"maritalStatus"});
		app.initCombobox("#customerStatusId",ctx + "/common/util/getDictByType.jhtml",{type:"customerStatus"});
		app.initCombobox("#customerSourceId",ctx + "/common/util/getDictByType.jhtml",{type:"customerSource"});
		app.initCombobox("#customerStageId",ctx + "/common/util/getDictByType.jhtml",{type:"customerStage"});
		app.initCombobox("#gradeId",ctx + "/common/util/getDictByType.jhtml",{type:"grade"});
		app.sexCombobox("#sexOne");
		$("#ownerIdBtn").textbox("setValue", userData.name);
        $("#ownerId").val(userData.id);
		$("#editDialog").dialog('open');
		$("input[name='attachPhoto']").val("");
		$("#attachPhoto").filebox('clear');
		totalCustomer.initEditForm();
		$("#editForm").attr('action',ctx + "/myWorkbench/totalCustomer/create.jhtml");
	},
	update:function(){
		var selectedRow = $(app.id.listId).datagrid('getSelected');
		if(selectedRow==null){
    		$.messager.alert('提示','请选择数据行','warning');
    		return;
    	}
		app.initCombobox("#maritalIdMod",ctx + "/common/util/getDictByType.jhtml",{type:"maritalStatus"});
		app.initCombobox("#customerStatusIdMod",ctx + "/common/util/getDictByType.jhtml",{type:"customerStatus"});
		app.initCombobox("#customerSourceIdMod",ctx + "/common/util/getDictByType.jhtml",{type:"customerSource"});
		app.initCombobox("#customerStageIdMod",ctx + "/common/util/getDictByType.jhtml",{type:"customerStage"});
		app.initCombobox("#gradeIdMod",ctx + "/common/util/getDictByType.jhtml",{type:"grade"});
		app.sexCombobox("#sex");
		$("#modEditDialog").dialog('open');
		$('#updateCustomerTabs').tabs({
    		selected:0
    	});
		var urlTwo=ctx + "/myWorkbench/totalCustomer/imgSrc.jhtml";
		var dataTwo ={customerid:selectedRow.id};
		var imgdata = CSIT.syncCallService(urlTwo,dataTwo);
		$("#ImgMod").attr("src",imgdata.data.src);
		$("#attachId").val(selectedRow.photoId);
		$('#customerSourceIdMod').combobox('setValue',selectedRow.customerSourceid);
		$('#customerStageIdMod').combobox('setValue',selectedRow.customerStageid);
		$('#customerStatusIdMod').combobox('setValue',selectedRow.customerStatusid);
		$("#placeIdModBtn").textbox("setValue", selectedRow.placeShowName);
        $("#placeIdMod").val(selectedRow.placeid);
        $("#liveAreaIdModBtn").textbox("setValue", selectedRow.liveAreaShowName);
        $("#liveAreaIdMod").val(selectedRow.liveAreaid);
        $("#livePlotIdModBtn").textbox("setValue", selectedRow.livePlotPlotName);
        $("#livePlotIdMod").val(selectedRow.livePlotid);
        $("#ownerIdModBtn").textbox("setValue", selectedRow.ownerName);
        $("#ownerIdMod").val(selectedRow.ownerid);
        $("#spousePlaceIdModBtn").textbox("setValue", selectedRow.spousePlaceShowName);
        $("#spousePlaceIdMod").val(selectedRow.spousePlaceid);
        var urlView=ctx + "/myWorkbench/customerViewHistory/create.jhtml";
		var dataView ={"customerId":selectedRow.id};
		CSIT.syncCallService(urlView,dataView);
		$("#editModForm").form('load',selectedRow);
		if(selectedRow.photoId!=null){
			var data ={attachId:selectedRow.photoId};
			var attach=CSIT.syncCallService(ctx + "/common/util/getAttach.jhtml",data);
			$("#attachPhotoMod").filebox("setText",attach.name);
		}
		totalCustomer.initModEditForm();
		$("#editModForm").attr('action',ctx + "/myWorkbench/totalCustomer/update.jhtml");
	},
	dalete:function(){
		var checkedRows = $("#list").datagrid('getChecked');
    	if(checkedRows.length==0){
    		$.messager.alert('提示','请选择删除的数据行','warning');
    		return;
    	}
    	$.messager.confirm("提示","确定要删除选中数据?",function(t){ 
			if(!t) return;
			var idArray = [];
			for(var i=0;i<checkedRows.length;i++){
				idArray.push(checkedRows[i].id);
			}
			var ids = idArray.join(CSIT.join);
			var content = {ids:ids};
			$.messager.progress();
			CSIT.asyncCallService(ctx + "/myWorkbench/totalCustomer/mulDelete.jhtml",content,function(result){
				if(result.isSuccess){
					$("#list").datagrid('reload');
				}else{
					$.messager.alert('错误',result.message,"error");
				}
				$.messager.progress('close');
			});
    	});
	},
	//转交
	transfer:function(){
		var selectedRow = $(app.id.listId).datagrid('getSelected');
		if(selectedRow==null){
    		$.messager.alert('提示','请选择数据行','warning');
    		return;
    	}
    	$('#transferName').text(selectedRow.customerName);
		$('#cusTransferId').val(selectedRow.id);
		$('#fromUserId').val(selectedRow.ownerid);
		$("#transferEditDialog").dialog("open");
		totalCustomer.initTransferForm();
		$("#transferForm").attr('action',ctx + "/myWorkbench/customerTransfer/create.jhtml");
	},
	//查看转交记录
	TransferRecord:function(){
		totalCustomer.initTransferRecordEditDialog();
		var cusTransferId = $('#cusTransferId').val();
		$("#transferRecordEditDialog").dialog("open");
		$('#transferRecordList').datagrid({
    		queryParams: {
    			"customer.id":cusTransferId
    		}
		});
	},
	//导出
	exportTable:function(){
		var data= $(app.id.searchFormId).serializeObject();
		var urlExport =ctx + "/myWorkbench/totalCustomer/exportCustomer.jhtml?"+data;
		app.showExportDialogMain(urlExport,$(app.id.listId));
	},
	//浏览历史
	viewHistory:function(){
		var selectedRow = $(app.id.listId).datagrid('getSelected');
		if(selectedRow==null){
    		$.messager.alert('提示','请选择数据行','warning');
    		return;
    	}
		$("#viewHistoryEditDialog").dialog("open");
		$('#viewHistoryList').datagrid({
    		queryParams: {
    			"customer.id":selectedRow.id
    		}
		});
	},
	initExportDialog:function(){
		$('#exportDialogMain').dialog({  
		    title: '导出Excel',  
		    width:600,
		    height:300,
		    closed: true,  
		    cache: false,  
		    modal: true,
		    closable:true,
		    toolbar:[{
				text:'导出',
				iconCls:'icon-upload',
				handler:function(){
			 		app.onExportOK();
				}
			}],
			onClose:function(){
				$('#exportDialogMain').form('clear');
				$('#exportDialog').dialog('clear');
			}
		});
    },
	initTransferEditDialog:function(){
		var params = {
	    		title:'转交设置',
	    		width : 380,
				height : 330,
				closed : true,
				cache : false,
				modal : true,
				buttons:[{
					text:'保存',
					iconCls:'icon-save',
					handler:function(){
						$("#transferForm").submit();
					}
				},{
					text:'退出',
					iconCls:'icon-exit',
					handler:function(){
						$("#transferEditDialog").dialog('close');
					}
				}],
				onClose:function(){
					$("#transferEditDialog").form('clear');
				}
			};
	    	$("#transferEditDialog").dialog(params);
	},
	initTransferForm:function(ext){
    	$("#transferForm").form({  
    		onSubmit: function(){ 
    			$.messager.progress();
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
    					$("#transferEditDialog").dialog('close');
    					$("#list").datagrid('reload');
    				});
    			}else{
    				$.messager.alert('提示',data.message,'error');
    			}
    		}
    	});
    },
	initModEditDialog:function(){
		$("#saveUpdateBtn").click(function(){
			$("#editModForm").submit();
		});
		$("#saveCreditBtn").click(function(){
			$("#editCreditForm").submit();
		});
		$('#updateCustomerTabs').tabs({
			border:false,
    	    onSelect:function(title,index){
    	    	if("产权信息"==title){
    	    		var customerId = $("#idUpdate").val();
    	    		$("#customerPropertyList").datagrid({
    	    			url:ctx + "/myWorkbench/customerProperty/listByPage.jhtml",
    	    			queryParams: {
    	    				"customerId": customerId
    	    			}
    	    		});
    	    	}
    	    	if("车产信息"==title){
    	    		var customerId = $("#idUpdate").val();
    	    		$("#customerCarList").datagrid({
    	    			url:ctx + "/myWorkbench/consumerCar/listByPage.jhtml",
    	    			queryParams: {
    	    				"customerId": customerId
    	    			}
    	    		});
    	    	}
    	    	if("信用状况"==title){
    	    		$("input[name='attachCreditName']").val("");
		    		$("#attachCreditName").filebox('clear');
    	    		app.initCombobox("#creditRatingId",ctx + "/common/util/getDictByType.jhtml",{type:"creditRating"});
    	    		var customerId = $("#idUpdate").val();
    	    		$("#cusCreditId").val(customerId);
    	    		var url=ctx + "/myWorkbench/totalCustomer/selectById.jhtml";
		    		var data ={customerid:customerId};
		    		var selectedRow = CSIT.syncCallService(url,data);
		    		if(selectedRow.creditRatingAttachId!=null){
		    			var dataOne ={attachId:selectedRow.creditRatingAttachId};
		    			var attach=CSIT.syncCallService(ctx + "/common/util/getAttach.jhtml",dataOne);
		    			$("#attachCreditName").filebox("setText",attach.name);
		    		}
//		    		if(selectedRow.creditRatingAttachId == '' || selectedRow.creditRatingAttachId == null){
//		    			$("#attachCName").css("display","none");
//		    			$("#cusCreditType").css("display","none");
//		    		}else{
//		    			var urlOne=ctx + "/myWorkbench/totalCustomer/getAttachCName.jhtml";
//			    		var dataOne ={creditRatingAttachId:selectedRow.creditRatingAttachId};
//			    		var attSelectedRow = CSIT.syncCallService(urlOne,dataOne);
//			    		$("#attachCName").text(attSelectedRow.data.attachCName);
//			    		$("#attachCName").css("display","block");
//			    		$("#cusCreditType").css("display","block");
//		    		}
    	    		$("#editCreditForm").form('load',selectedRow);
    	    		customerDetails.initEditCreditForm();
    	    		$("#editCreditForm").attr('action',ctx + "/myWorkbench/totalCustomer/modCustomerCredit.jhtml");
    	    	}
    	    	if("联系人"==title){
    	    		var customerId = $("#idUpdate").val();
    	    		$("#customerRelationshipList").datagrid({
    	    			url:ctx + "/myWorkbench/customerRelationship/listByPage.jhtml",
    	    			queryParams: {
    	    				"customerId": customerId
    	    			}
    	    		});
    	    	}
    	    	if("其他财力"==title){
    	    		var customerId = $("#idUpdate").val();
    	    		$("#customerOtherResourcesList").datagrid({
    	    			url:ctx + "/myWorkbench/customerOtherResources/listByPage.jhtml",
    	    			queryParams: {
    	    				"customerId": customerId
    	    			}
    	    		});
    	    	}
    	    	if("负债"==title){
    	    		var customerId = $("#idUpdate").val();
    	    		$("#customerLiabilitiesList").datagrid({
    	    			url:ctx + "/myWorkbench/customerLiabilities/listByPage.jhtml",
    	    			queryParams: {
    	    				"customerId": customerId
    	    			}
    	    		});
    	    	}
    	    }
		});
		var params = {
		    	title:'编辑客户信息',
		    	width : document.documentElement.clientWidth-5<1000?document.documentElement.clientWidth-5:1000,
				height : document.documentElement.clientHeight-5<770?document.documentElement.clientHeight-5:770,
				closed : true,
				cache : false,
				modal : true,
		};
		$("#modEditDialog").dialog(params);
	},
	initAddPlotDialog:function(){
		var params = {
	    		title:'新增小区',
	    		width : 340,
				height : 250,
				closed : true,
				cache : false,
				modal : true,
				buttons:[{
					text:'保存',
					iconCls:'icon-save',
					handler:function(){
						$("#addPlotForm").submit();
					}
				},{
					text:'退出',
					iconCls:'icon-exit',
					handler:function(){
						$("#addPlotDialog").dialog('close');
					}
				}],
				onClose:function(){
					$("#addPlotDialog").form('clear');
					$('#status',"#addPlotDialog").combobox('select',1);
				}
			};
	    	$("#addPlotDialog").dialog(params);
	},
	initAddPlotForm:function(ext){
    	$("#addPlotForm").form({  
    		onSubmit: function(){ 
    			$.messager.progress();
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
    					$("#addPlotDialog").dialog('close');
    					$("#livePlotList").datagrid('reload');
    				});
    			}else{
    				$.messager.alert('提示',data.message,'error');
    			}
    		}
    	});
    },
    initAddPlotModForm:function(ext){
    	$("#addPlotForm").form({  
    		onSubmit: function(){ 
    			$.messager.progress();
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
    					$("#addPlotDialog").dialog('close');
    					$("#livePlotModList").datagrid('reload');
    				});
    			}else{
    				$.messager.alert('提示',data.message,'error');
    			}
    		}
    	});
    },
	initEditDialog:function(){
		var params = {
	    		title:'新建客户',
				width : document.documentElement.clientWidth-5<1000?document.documentElement.clientWidth-5:1000,
				height : document.documentElement.clientHeight-5<770?document.documentElement.clientHeight-5:770,
				closed : true,
				cache : false,
				modal : true,
				buttons:[{
					text:'保存',
					iconCls:'icon-save',
					handler:function(){
						$("#editForm").submit();
					}
				},{
					text:'退出',
					iconCls:'icon-exit',
					handler:function(){
						$("#editDialog").dialog('close');
					}
				}],
			};
	    	$("#editDialog").dialog(params);
	},
	initEditForm:function(ext){
    	$("#editForm").form({  
    		onSubmit: function(){ 
    			$.messager.progress();
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
    					$("#addId").val(data.data.cid);
    					$("#editDialog").dialog('close');
    					$("#addSuccessDialog").dialog('open');
    					$(app.id.listId).datagrid('reload');
    				});
    			}else{
    				$.messager.alert('提示',data.message,'error');
    			}
    		}
    	});
    },
    initModEditForm:function(ext){
    	$("#editModForm").form({  
    		onSubmit: function(){ 
    			$.messager.progress();
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
    					$(app.id.listId).datagrid('reload');
    				});
    			}else{
    				$.messager.alert('提示',data.message,'error');
    			}
    		}
    	});
    },
    //展示图片
    PreviewImage:function(fileObj,imgPreviewId,divPreviewId){  
       var allowExtention=".jpg,.bmp,.gif,.png";//允许上传文件的后缀名document.getElementById("hfAllowPicSuffix").value;  
       var extention=fileObj.value.substring(fileObj.value.lastIndexOf(".")+1).toLowerCase();              
       var browserVersion= window.navigator.userAgent.toUpperCase();  
       if(allowExtention.indexOf(extention)>-1){   
           if(fileObj.files){//HTML5实现预览，兼容chrome、火狐7+等  
               if(window.FileReader){  
                   var reader = new FileReader();   
                   reader.onload = function(e){  
                       document.getElementById(imgPreviewId).setAttribute("src",e.target.result);  
                   };
                   reader.readAsDataURL(fileObj.files[0]);  
               }else if(browserVersion.indexOf("SAFARI")>-1){  
                   alert("不支持Safari6.0以下浏览器的图片预览!");  
               }  
           }else if (browserVersion.indexOf("MSIE")>-1){  
               if(browserVersion.indexOf("MSIE 6")>-1){//ie6  
                   document.getElementById(imgPreviewId).setAttribute("src",fileObj.value);  
               }else{//ie[7-9]  
                   fileObj.select();  
                   if(browserVersion.indexOf("MSIE 9")>-1)  
                       fileObj.blur();//不加上document.selection.createRange().text在ie9会拒绝访问  
                   var newPreview =document.getElementById(divPreviewId+"New");  
                   if(newPreview==null){  
                       newPreview =document.createElement("div");  
                       newPreview.setAttribute("id",divPreviewId+"New");  
                       newPreview.style.width = document.getElementById(imgPreviewId).width+"px";  
                       newPreview.style.height = document.getElementById(imgPreviewId).height+"px";  
                       newPreview.style.border="solid 1px #d2e2e2";  
                   }  
                   newPreview.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale',src='" + document.selection.createRange().text + "')";                              
                   var tempDivPreview=document.getElementById(divPreviewId);  
                   tempDivPreview.parentNode.insertBefore(newPreview,tempDivPreview);  
                   tempDivPreview.style.display="none";                      
               }  
           }else if(browserVersion.indexOf("FIREFOX")>-1){//firefox  
               var firefoxVersion= parseFloat(browserVersion.toLowerCase().match(/firefox\/([\d.]+)/)[1]);  
               if(firefoxVersion<7){//firefox7以下版本  
                   document.getElementById(imgPreviewId).setAttribute("src",fileObj.files[0].getAsDataURL());  
               }else{//firefox7.0+                      
                   document.getElementById(imgPreviewId).setAttribute("src",window.URL.createObjectURL(fileObj.files[0]));  
               }  
           }else{  
               document.getElementById(imgPreviewId).setAttribute("src",fileObj.value);  
           }           
       }else{  
           alert("仅支持"+allowExtention+"为后缀名的文件!");  
           fileObj.value="";//清空选中文件  
           if(browserVersion.indexOf("MSIE")>-1){                          
               fileObj.select();  
               document.selection.clear();  
           }                  
           fileObj.outerHTML=fileObj.outerHTML;  
       }  
    },
    //新增成功。选择是否完善
    initAddSuccessDialog:function(){
    	$("#addSuccessDialog").dialog({
    		title:'客户基本信息保存成功',
    		width : 400,
    		height : 200,
    		closed : true,
    		cache : false,
    		modal : true,
    		onClose:function(){
				$(app.id.listId).datagrid('reload');
			},
    	});
    	$("#continueUpdateBtn").click(function(){
    		totalCustomer.initPlaceModEditDialog();
        	totalCustomer.initLiveAreaModEditDialog();
        	totalCustomer.initLivePlotModEditDialog();
        	totalCustomer.initSpousePlaceModEditDialog();
        	totalCustomer.initOwnerModEditDialog();
    		$("#addSuccessDialog").dialog('close');
    		$("#modEditDialog").dialog('open');
    		var addId = $("#addId").val();
    		var url=ctx + "/myWorkbench/totalCustomer/selectById.jhtml";
    		var data ={customerid:addId};
    		var addRow = CSIT.syncCallService(url, data);
    		app.sexCombobox("#sex");
    		$('#updateCustomerTabs').tabs({
        		selected:0
        	});
    		var urlTwo=ctx + "/myWorkbench/totalCustomer/imgSrc.jhtml";
    		var dataTwo ={customerid:addId};
    		var imgdata = CSIT.syncCallService(urlTwo,dataTwo);
    		$("#ImgMod").attr("src",imgdata.data.src);
    		$("#attachId").val(addRow.photoId);
    		$('#customerSourceIdMod').combobox('setValue',addRow.customerSourceid);
    		$('#customerStageIdMod').combobox('setValue',addRow.customerStageid);
    		$('#customerStatusIdMod').combobox('setValue',addRow.customerStatusid);
    		$("#placeIdModBtn").textbox("setValue", addRow.placeShowName);
            $("#placeIdMod").val(addRow.placeid);
            $("#liveAreaIdModBtn").textbox("setValue", addRow.liveAreaShowName);
            $("#liveAreaIdMod").val(addRow.liveAreaid);
            $("#livePlotIdModBtn").textbox("setValue", addRow.livePlotPlotName);
            $("#livePlotIdMod").val(addRow.livePlotid);
            $("#ownerIdModBtn").textbox("setValue", addRow.ownerName);
            $("#ownerIdMod").val(addRow.ownerid);
            $("#spousePlaceIdModBtn").textbox("setValue", addRow.spousePlaceShowName);
            $("#spousePlaceIdMod").val(addRow.spousePlaceid);
            var urlView=ctx + "/myWorkbench/customerViewHistory/create.jhtml";
    		var dataView ={"customerId":addId};
    		CSIT.syncCallService(urlView,dataView);
    		$("#editModForm").form('load',addRow);
    		totalCustomer.initModEditForm();
    		$("#editModForm").attr('action',ctx + "/myWorkbench/totalCustomer/update.jhtml");
    	});
    	$("#exitAddSuccessDialogBtn").click(function(){
    		$("#addSuccessDialog").dialog('close');
    	});
    },
    //验证身份证
    validateIdCard:function(data) {
    	var card = $(data).textbox("getValue");
    	alert(card);
    },
    search : function() {
		var isValid = $("#searchForm").form('validate');
		if (!isValid) {
			return false;
		}
		var content = $("#searchForm").serializeObject();
		console.log(content);
		$("#list").datagrid({
			queryParams : content
		});
	},
	 //户籍地
    initPlaceModEditDialog:function(){
    	$('#placeModList').datagrid({  
			  border:true,
			  url:ctx + "/myWorkbench/totalCustomer/getPlaceByList.jhtml",
			  rownumbers:true,
			  pagination:true,
			  fit:true,
			  toolbar:[{
				  text:'选择',
					iconCls:'icon-ok',
					handler:function(){
						var selectedRow = $('#placeModList').datagrid('getSelected');
						if(selectedRow==null){
				    		$.messager.alert('提示','请选择数据行','warning');
				    		return;
				    	}
						$("#placeIdModBtn").textbox("setValue", selectedRow.showName);
				        $("#placeIdMod").val(selectedRow.id);
				        $("#placeModEditDialog").dialog('close');
					}
				}],
	    	  ctrlSelect:true,
			  method:"POST",
		      columns:[[           
		          {field:'provinceName',title:'省份',width:100,align:'center'},
		          {field:'cityName',title:'城市',width:160,align:'center'},
		          {field:'areaName',title:'区/县名称',width:100,align:'center'},
		          {field:'showName',title:'显示名称',width:200,align:'center'},
		   	  ]],
		   	  onDblClickCell: function(index,field,value){
		   		  var rows = $('#placeModList').datagrid('getRows');
		   		  var selectedRow = rows[index];
		   		  $("#placeIdModBtn").textbox("setValue", selectedRow.showName);
		          $("#placeIdMod").val(selectedRow.id);
		          $("#placeModEditDialog").dialog('close');
		   	  }
		});
    	var params = {
	    	title:'选择户籍地',
			width : 700,
			height : document.documentElement.clientHeight-5<600?document.documentElement.clientHeight-5:600,
			closed : true,
			cache : false,
			modal : true,
		};
	    $("#placeModEditDialog").dialog(params);
    },
    //配偶户籍地
    initSpousePlaceModEditDialog:function(){
    	$('#spousePlaceModList').datagrid({  
			  border:true,
			  url:ctx + "/myWorkbench/totalCustomer/getPlaceByList.jhtml",
			  rownumbers:true,
			  pagination:true,
			  fit:true,
			  toolbar:[{
				  text:'选择',
					iconCls:'icon-ok',
					handler:function(){
						var selectedRow = $('#spousePlaceModList').datagrid('getSelected');
						if(selectedRow==null){
				    		$.messager.alert('提示','请选择数据行','warning');
				    		return;
				    	}
						$("#spousePlaceIdModBtn").textbox("setValue", selectedRow.showName);
				        $("#spousePlaceIdMod").val(selectedRow.id);
				        $("#spousePlaceModEditDialog").dialog('close');
					}
				}],
	    	  ctrlSelect:true,
			  method:"POST",
		      columns:[[           
		          {field:'provinceName',title:'省份',width:100,align:'center'},
		          {field:'cityName',title:'城市',width:160,align:'center'},
		          {field:'areaName',title:'区/县名称',width:100,align:'center'},
		          {field:'showName',title:'显示名称',width:200,align:'center'},
		   	  ]],
		   	  onDblClickCell: function(index,field,value){
		   		  var rows = $('#spousePlaceModList').datagrid('getRows');
		   		  var selectedRow = rows[index];
		   		  $("#spousePlaceIdModBtn").textbox("setValue", selectedRow.showName);
		          $("#spousePlaceIdMod").val(selectedRow.id);
		          $("#spousePlaceModEditDialog").dialog('close');
		   	  }
		});
    	var params = {
	    	title:'选择户籍地',
			width : 700,
			height : document.documentElement.clientHeight-5<600?document.documentElement.clientHeight-5:600,
			closed : true,
			cache : false,
			modal : true,
		};
	    $("#spousePlaceModEditDialog").dialog(params);
    },
    //居住地区
    initLiveAreaModEditDialog:function(){
    	$('#liveAreaModList').datagrid({  
			  border:true,
			  url:ctx + "/myWorkbench/totalCustomer/getLiveAreaByList.jhtml",
			  rownumbers:true,
			  pagination:true,
			  fit:true,
			  toolbar:[{
				  text:'选择',
					iconCls:'icon-ok',
					handler:function(){
						var selectedRow = $('#liveAreaModList').datagrid('getSelected');
						if(selectedRow==null){
				    		$.messager.alert('提示','请选择数据行','warning');
				    		return;
				    	}
						$('#areaDataOne').val(selectedRow.id);
						$("#liveAreaIdModBtn").textbox("setValue", selectedRow.showName);
				        $("#liveAreaIdMod").val(selectedRow.id);
				        $("#livePlotIdModBtn").textbox("setValue", "");
				        $("#livePlotIdMod").val("");
				        $("#liveAreaModEditDialog").dialog('close');
					}
				}],
	    	  ctrlSelect:true,
			  method:"POST",
		      columns:[[           
		          {field:'provinceName',title:'省份',width:100,align:'center'},
		          {field:'cityName',title:'城市',width:160,align:'center'},
		          {field:'areaName',title:'区/县名称',width:100,align:'center'},
		          {field:'showName',title:'显示名称',width:200,align:'center'},
		   	  ]],
		   	  onDblClickCell: function(index,field,value){
		   		  var rows = $('#liveAreaModList').datagrid('getRows');
		   		  var selectedRow = rows[index];
		   		  $('#areaDataOne').val(selectedRow.id);
		   		  $("#liveAreaIdModBtn").textbox("setValue", selectedRow.showName);
		          $("#liveAreaIdMod").val(selectedRow.id);
		          $("#livePlotIdModBtn").textbox("setValue", "");
		          $("#livePlotIdMod").val("");
		          $("#liveAreaModEditDialog").dialog('close');
		   	  }
		});
    	var params = {
	    	title:'选择居住地区',
			width : 700,
			height : document.documentElement.clientHeight-5<600?document.documentElement.clientHeight-5:600,
			closed : true,
			cache : false,
			modal : true,
		};
	    $("#liveAreaModEditDialog").dialog(params);
    },
    //小区
    initLivePlotModEditDialog:function(){
    	$('#livePlotModList').datagrid({  
			  border:true,
			  url:ctx + "/myWorkbench/totalCustomer/getLivePlotByList.jhtml",
			  rownumbers:true,
			  pagination:true,
			  fit:true,
			  toolbar:[{
				  text:'选择',
					iconCls:'icon-ok',
					handler:function(){
						var selectedRow = $('#livePlotModList').datagrid('getSelected');
						if(selectedRow==null){
				    		$.messager.alert('提示','请选择数据行','warning');
				    		return;
				    	}
						$("#livePlotIdModBtn").textbox("setValue", selectedRow.plotName);
				        $("#livePlotIdMod").val(selectedRow.id);
				        $("#livePlotModEditDialog").dialog('close');
					}
				},{
					text:'新增',
					iconCls:'icon-add',
					handler:function(){
						var liveAreaId = $("#liveAreaIdMod").val();
						var liveAreaName = $("#liveAreaIdModBtn").textbox("getValue");
						$("#addPlotAreaId").val(liveAreaId);
						$('#liveAreaName').text(liveAreaName);
						$("#addPlotDialog").dialog('open');
						totalCustomer.initAddPlotModForm();
						$("#addPlotForm").attr('action',ctx + "/dict/plot/create.jhtml");
					}
				}],
	    	  ctrlSelect:true,
			  method:"POST",
		      columns:[[           
		          {field:'areaName',title:'区县',width:120,align:'center'},
		          {field:'plotName',title:'小区',width:180,align:'center'},
		          {field:'status',title:'状态',width:100,align:'center',formatter: function(value,row,index){
                      if(value == 1){
                          return "启用";
                      }else{
                          return "禁用";
                      }},
					},
		          {field:'note',title:'备注',width:100,align:'center'}
		   	  ]],
		   	  onDblClickCell: function(index,field,value){
		   		  var rows = $('#livePlotModList').datagrid('getRows');
		   		  var selectedRow = rows[index];
		   		  $("#livePlotIdModBtn").textbox("setValue", selectedRow.plotName);
		          $("#livePlotIdMod").val(selectedRow.id);
		          $("#livePlotModEditDialog").dialog('close');
		   	  }
		});
    	var params = {
	    	title:'选择小区',
			width : 700,
			height : document.documentElement.clientHeight-5<600?document.documentElement.clientHeight-5:600,
			closed : true,
			cache : false,
			modal : true,
		};
	    $("#livePlotModEditDialog").dialog(params);
    },
    //拥有人
    initOwnerModEditDialog:function(){
    	$('#ownerModList').datagrid({  
			  border:true,
			  url:ctx + "/myWorkbench/totalCustomer/getOwnerByList.jhtml",
			  rownumbers:true,
			  pagination:true,
			  fit:true,
			  toolbar:[{
				  text:'选择',
					iconCls:'icon-ok',
					handler:function(){
						var selectedRow = $('#ownerModList').datagrid('getSelected');
						if(selectedRow==null){
				    		$.messager.alert('提示','请选择数据行','warning');
				    		return;
				    	}
						$("#ownerIdModBtn").textbox("setValue", selectedRow.name);
				        $("#ownerIdMod").val(selectedRow.id);
				        $("#ownerModEditDialog").dialog('close');
					}
				}],
	    	  ctrlSelect:true,
			  method:"POST",
		      columns:[[           
		          {field:'username',title:'用户名',width:150,align:'center'},
		          {field:'name',title:'姓名',width:150,align:'center'},
		   	  ]],
		   	  onDblClickCell: function(index,field,value){
		   		  var rows = $('#ownerModList').datagrid('getRows');
		   		  var selectedRow = rows[index];
		   		  $("#ownerIdModBtn").textbox("setValue", selectedRow.name);
		          $("#ownerIdMod").val(selectedRow.id);
		          $("#ownerModEditDialog").dialog('close');
		   	  }
		});
    	var params = {
	    	title:'选择拥有人',
			width : 700,
			height : document.documentElement.clientHeight-5<600?document.documentElement.clientHeight-5:600,
			closed : true,
			cache : false,
			modal : true,
		};
	    $("#ownerModEditDialog").dialog(params);
    },
    //跟进
    initFollowEditDialog : function() {
		var params = {
			title : '新增跟进',
			width : document.documentElement.clientWidth-5<680?document.documentElement.clientWidth-5:680,
			height : document.documentElement.clientHeight-5<500?document.documentElement.clientHeight-5:500,
			closed : true,
			cache : false,
			modal : true,
			buttons : [ {
				text : '保存',
				iconCls : 'icon-save',
				handler : function() {
					$("#followDetails").val(detailEditor.html());
					$("#followEditForm").submit();
				}
			}, {
				text : '退出',
				iconCls : 'icon-exit',
				handler : function() {
					$("#followEditDialog").dialog('close');
					$("#missionBolock").css('display','none');
					detailEditor.html("");
				}
			} ],
			onClose : function() {
				$("#followEditForm").form('clear');
				$("#missionBolock").css('display','none');
				detailEditor.html("");
			}
		};
		$("#followEditDialog").dialog(params);
	},
	initTaskreceiverEditDialog:function(){
		$('#taskreceiverrightGrid').datagrid({  
			  border:true,
			  method:"POST",
			  url:ctx + "/myWorkbench/todo/listDelPersonByPage.jhtml",
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
				  url:ctx + "/myWorkbench/todo/listPersonByPage.jhtml",
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
	initFollowEditForm : function() {
		$("#followEditForm").form({
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
						$("#followEditDialog").dialog('close');
					});
				} else {
					$.messager.alert('提示', data.message, 'error');
				}
			}
		});
	},
	//跟进富文本框
	initKindEditorDialog:function(){
    	if(detailEditor==null){
    		detailEditor = KindEditor.create('#followDetails', {
				resizeType:0,
				width:'100%',
				height:100,
				items:[
				       	'source', '|', 'preview', 'undo', 'redo', 'cut', 'copy', 'paste',
				       	'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
				       	'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
				       	'superscript', 'clearhtml', 'quickformat', 
				       	'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
				       	'italic', 'underline', 'lineheight', 'removeformat',
				       	'table', 'link', 'unlink', 'fullscreen'
			    ],
			});
		}
    },
    //跟进
    follow:function(){
    	totalCustomer.initKindEditorDialog();
    	var selectedRow = $(app.id.listId).datagrid('getSelected');
		if(selectedRow==null){
    		$.messager.alert('提示','请选择跟进客户','warning');
    		return;
    	}
    	$("#followEditDialog").dialog({
			height:500
		});
    	app.followTypeCombobox($("#followType"));
    	app.followStageCombobox($("#followStage"));
		app.customerStatusCombobox($("#customerStatus"));
		app.remind($("#remind"));
		var date=new Date();
		var year=date.getFullYear();
		var month=date.getMonth()+1;
		//获取当前日
		var day=date.getDate(); 
		$("#startDate").val(year+"-"+month+"-"+day);
		$("#nextDate").val(year+"-"+month+"-"+day);
		$("#customerId").val(selectedRow.id);
		$("#customerName").text(selectedRow.customerName);
		$("#nextDate").validatebox({required:false});
		$("#taskFollowPeople").textbox({required:false});
		var user=CSIT.syncCallService(ctx + "/myWorkbench/follow/getCurrentUser.jhtml");
		$("#followPersonnelId").val(user.id);
		$("#taskFollowPersonnelId").val(user.id);
		$("#followPersonnel").textbox("setValue",user.username);
		$("#taskFollowPeople").textbox("setValue",user.username);
		$("#create").show();
		$("#isActive").val(false);
		$("#followEditDialog").dialog('open');
        $("#followTable").css('height','370px');
		$("#status").combobox("setValue",1);
		totalCustomer.initFollowEditForm();
		//清空附件
    	$("#filelist").empty();
		$("#followEditForm").attr('action', ctx + "/myWorkbench/follow/create.jhtml");
		app.yesOrNoByIntCombobox("#isSys");
    },
    initCustomerDialog : function() {
		var params = {
			title : '选择客户',
			width : document.documentElement.clientWidth-5<1150?document.documentElement.clientWidth-5:1150,
			height : document.documentElement.clientWidth-5<550?document.documentElement.clientWidth-5:550,
			closed : true,
			cache : false,
			modal : true,
			onClose : function() {
			}
		};
		$("#customerDialog").dialog(params);
	},
	initCustomerList:function(){
		$("#customerList").datagrid({
			url:'crm/myWorkbench/totalCustomer/listByPage.jhtml',
			pagination : true,
			fit : true,
			toolbar:"#tb2",
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
    initBtn:function(){
		//新增
		$("#addBtn").click(function(){
			totalCustomer.initPlaceEditDialog();
	    	totalCustomer.initLiveAreaEditDialog();
	    	totalCustomer.initLivePlotEditDialog();
	    	totalCustomer.initSpousePlaceEditDialog();
	    	totalCustomer.initOwnerEditDialog();
			totalCustomer.add();
    	});
		//修改
		$("#updateBtn").click(function(){
			totalCustomer.initPlaceModEditDialog();
	    	totalCustomer.initLiveAreaModEditDialog();
	    	totalCustomer.initLivePlotModEditDialog();
	    	totalCustomer.initSpousePlaceModEditDialog();
	    	totalCustomer.initOwnerModEditDialog();
			totalCustomer.update();
    	});
		//删除
		$("#deleteBtn").click(function(){
			totalCustomer.dalete();
    	});
		//转交
		$("#transferBtn").click(function(){
			totalCustomer.initTransferUserEditDialog();
			totalCustomer.transfer();
    	});
		//导出
		$("#exportBtn").click(function(){
			totalCustomer.exportTable();
    	});
		//跟进
		$("#followBtn").click(function(){
			totalCustomer.follow();
    	});
		//浏览历史
		$("#browsingHistoryBtn").click(function(){
			totalCustomer.initViewHistoryEditDialog();
			totalCustomer.viewHistory();
    	});
		//导出的反选按钮
		$("#reverseChooseMain").click(function(){
			$('#exportColumnsMain li :checkbox').each(function(){
				$(this).prop('checked',!this.checked);
			});
		});
		//导出的全选按钮
		$("#allChooseMain").click(function(){
			$('#exportColumnsMain li :checkbox').each(function(){
				$(this).prop('checked',true);
			});
		});
		//查询跟进人员
      	$("#followPeopleSearchBtn").click(function(){
      		var content = $("#followPeopleSearch").serializeObject();
      		$('#followPeopleList').datagrid({  
      			 queryParams : content
    		     });
      	});
        //清空跟进人员查询
     	 $("#followPeopleClearBtn").click(function(){
     		 $("#followPeopleSearch").form('clear');
      	});
		//自定义查询
		$("#customQueryBtn").click(function(){
			 //---int型字段格式化---
			  //变量名
			  var intPropertyNames = ["a.customer_type","a.sex","a.grade_id","a.marital_id"];
			  var intPropertyObjs = [];
			  //格式化内容
			  //客户类型
			  var customerTypes = {};
			  customerTypes["个人"]=1;
			  customerTypes["企业"]=2;
			  intPropertyObjs.push(customerTypes);
			  //性别
			  var sexs = {};
			  sexs["男"]=1;
			  sexs["女"]=0;
			  intPropertyObjs.push(sexs);
			  //智能评级
			  var grades = {};
			  var gradesurl=ctx + "/common/util/getDictByType.jhtml";
		      var data ={type:"grade"};
		      var gradesData = CSIT.syncCallService(gradesurl, data);
		      for(var i=0;i<gradesData.length;i++){
		    	  grades[gradesData[i].name]=gradesData[i].id;
		      }
			  intPropertyObjs.push(grades);
			  //婚姻状况
			  var maritals = {};
			  var maritalurl=ctx + "/common/util/getDictByType.jhtml";
		      var dataOne ={type:"maritalStatus"};
		      var maritalData = CSIT.syncCallService(maritalurl, dataOne);
		      for(var i=0;i<maritalData.length;i++){
		    	  maritals[maritalData[i].name]=maritalData[i].id;
		      }
			  intPropertyObjs.push(maritals);
			  //---自定义查询条件
			  var fields =[
			               {"id":'a.customer_type',"text":"客户类型"},
			               {"id":'a.customer_name',"text":"客户名称"},
			               {"id":'a.sex',"text":"性别"},
			               {"id":'a.mobile_phone',"text":"手机号"},
			               {"id":'a.telephone',"text":"固定电话"},
			               {"id":'a.email',"text":"email"},
			               {"id":'a.id_number',"text":"身份证号"},
			               {"id":'a.id_address',"text":"身份证地址"},
			               {"id":'d.name',"text":"客户状态"},
			               {"id":'c.name',"text":"客户阶段"},
			               {"id":'b.name',"text":"客户来源"},
			               {"id":'h.username',"text":"拥有人"},
			               {"id":'a.grade_id',"text":"智能评级"},
			               {"id":'a.marital_id',"text":"婚姻状况"},
			               {"id":'e.show_name',"text":"户籍地"},
			               {"id":'f.show_name',"text":"居住地区"},
			               {"id":'i.plot_name',"text":"居住小区"},
			               {"id":'a.birthdate',"text":"出生日期"},
			               {"id":'a.company',"text":"就职单位"},
			               {"id":'a.occupation',"text":"职业"},
		               ];
			  CSIT.initCustomQueryNew(
					  fields,
					  '#customQueryBtn',
					  '#queryCustomForm',
					  '#customList',
					  '#searchForm',
					  '#list',
					  '#cenInfo',
					  intPropertyNames,
					  intPropertyObjs,{id:""},"#queryContent");
		});
		//查询
		$("#searchBtn").click(function(){
			totalCustomer.search();
    	});
		//清除
		$("#clearBtn").click(function(){
			$("#searchForm").form('clear');
    	});
		$("input",$("#idNumber").next("span")).blur(function(){  
			var card = $("#idNumber").textbox("getValue");
			if(card!=null && card!='' && card!=undefined){
				var url=ctx + "/myWorkbench/totalCustomer/getIdCardToBirthday.jhtml";
		    	var data ={idCard:card};
		    	var result = CSIT.syncCallService(url,data);
		    	$('#birthdate').textbox('setValue', result.data.birthday);
		    	
			}
		});
		$("input",$("#idNumberMod").next("span")).blur(function(){  
			var card = $("#idNumberMod").textbox("getValue");
			if(card!=null && card!='' && card!=undefined){
				var url=ctx + "/myWorkbench/totalCustomer/getIdCardToBirthday.jhtml";
		    	var data ={idCard:card};
		    	var result = CSIT.syncCallService(url,data);
		    	$('#birthdateMod').textbox('setValue', result.data.birthday);	
			}
		});
		$("#searchPlaceBtn").click(function(){
			var isValid = $("#searchPlaceForm").form('validate');
			if (!isValid) {
				return false;
			}
			var content = $("#searchPlaceForm").serializeObject();
			$("#placeList").datagrid({
				queryParams : content
			});
    	});
		$("#searchTransferUserBtn").click(function(){
			var isValid = $("#searchTransferUserForm").form('validate');
			if (!isValid) {
				return false;
			}
			var content = $("#searchTransferUserForm").serializeObject();
			$("#transferUserList").datagrid({
				queryParams : content
			});
    	});
		$("#searchSpousePlaceBtn").click(function(){
			var isValid = $("#searchSpousePlaceForm").form('validate');
			if (!isValid) {
				return false;
			}
			var content = $("#searchSpousePlaceForm").serializeObject();
			$("#spousePlaceList").datagrid({
				queryParams : content
			});
    	});
		$("#searchLiveAreaBtn").click(function(){
			var isValid = $("#searchLiveAreaForm").form('validate');
			if (!isValid) {
				return false;
			}
			var content = $("#searchLiveAreaForm").serializeObject();
			$("#liveAreaList").datagrid({
				queryParams : content
			});
    	});
		$("#searchLivePlotBtn").click(function(){
			var isValid = $("#searchLivePlotForm").form('validate');
			if (!isValid) {
				return false;
			}
			var content = $("#searchLivePlotForm").serializeObject();
			$("#livePlotList").datagrid({
				queryParams : content
			});
    	});
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
		$("#searchPlaceModBtn").click(function(){
			var isValid = $("#searchPlaceModForm").form('validate');
			if (!isValid) {
				return false;
			}
			var content = $("#searchPlaceModForm").serializeObject();
			$("#placeModList").datagrid({
				queryParams : content
			});
    	});
		$("#searchSpousePlaceModBtn").click(function(){
			var isValid = $("#searchSpousePlaceModForm").form('validate');
			if (!isValid) {
				return false;
			}
			var content = $("#searchSpousePlaceModForm").serializeObject();
			$("#spousePlaceModList").datagrid({
				queryParams : content
			});
    	});
		$("#searchLiveAreaModBtn").click(function(){
			var isValid = $("#searchLiveAreaModForm").form('validate');
			if (!isValid) {
				return false;
			}
			var content = $("#searchLiveAreaModForm").serializeObject();
			$("#liveAreaModList").datagrid({
				queryParams : content
			});
    	});
		$("#searchLivePlotModBtn").click(function(){
			var isValid = $("#searchLivePlotModForm").form('validate');
			if (!isValid) {
				return false;
			}
			var content = $("#searchLivePlotModForm").serializeObject();
			$("#livePlotModList").datagrid({
				queryParams : content
			});
    	});
		$("#searchOwnerModBtn").click(function(){
			var isValid = $("#searchOwnerModForm").form('validate');
			if (!isValid) {
				return false;
			}
			var content = $("#searchOwnerModForm").serializeObject();
			$("#ownerModList").datagrid({
				queryParams : content
			});
    	});
		$("#searchProAreaBtn").click(function(){
			var isValid = $("#searchProAreaForm").form('validate');
			if (!isValid) {
				return false;
			}
			var content = $("#searchProAreaForm").serializeObject();
			$("#proAreaList").datagrid({
				queryParams : content
			});
    	});
		$("#searchProPlotBtn").click(function(){
			var isValid = $("#searchProPlotForm").form('validate');
			if (!isValid) {
				return false;
			}
			var content = $("#searchProPlotForm").serializeObject();
			$("#proPlotList").datagrid({
				queryParams : content
			});
    	});
		//跟进人员
		$("#followPersonnel").textbox({
			onClickIcon:function(index){
				$("#tb4").hide();
				$("#followPeopleDialog").dialog({toolbar:"#tb3"});
				$("#followPeopleDialog").dialog('open');
				totalCustomer.initFollowPeopleList();
			}
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
		//创建任务
		$("#createMission").click(function(){
			if($("#createMission").is(":checked")){
				$("#nextDate").validatebox({required:true});
				$("#taskFollowPeople").textbox({required:true});
				$("#taskContent").textbox({required:true});
				$("#followTable").css('height','550px');
				$("#missionBolock").css('display','table-row-group');
				$("#isActive").val(true);
				$("#remind").combobox("setValue",1);
				$("#followEditDialog").dialog('resize',{
					height : document.documentElement.clientHeight<730?document.documentElement.clientHeight:730
				})
				if(document.documentElement.clientHeight>=730){
					$("#followEditDialog").dialog('center');
				}
			}else{
				$("#followTable").css('height','370px');
				$("#missionBolock").css('display','none');
				$("#nextDate").validatebox({required:false});
				$("#isActive").val(false);
				$("#taskFollowPeople").textbox({required:false});
				$("#taskContent").textbox({required:false});
				$("#followEditDialog").dialog('resize',{
					height : document.documentElement.clientHeight<500?document.documentElement.clientHeight:500
				})
				if(document.documentElement.clientHeight>=500){
					$("#followEditDialog").dialog('center');
				}
			}
		});
		//抄送人员
		$("#receiverNameTask").textbox({
			onClickIcon:function(index){
				$("#taskreceiverEditDialog").dialog('open');
				var dataid=$("#receiveTaskId").val();
				if(dataid!=null){
				 var dataid=$("#receiveTaskId").val();
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
		//查询抄送人
     	$("#taskreceiverEditSearchBtn").click(function(){
     		var content = $("#taskreceiverEditSearch").serializeObject();
     		$('#taskreceiverleftGrid').datagrid({  
     			 url:"crm/myWorkbench/todo/listPersonByPage.jhtml",
     			 queryParams : content
   		     });
     	});
     	//清空抄送人姓名
     	 $("#taskreceiverEditClearBtn").click(function(){
     		 $("#taskreceiverEditSearch").form('clear');
      	});
     	//任务跟进人员
 		$("#taskFollowPeople").textbox({
 			onClickIcon:function(index){
 				$("#tb3").hide();
 				$("#followPeopleDialog").dialog({toolbar:"#tb4"});
 				$("#followPeopleDialog").dialog('open');
 				totalCustomer.initFollowPeopleList();
 			}
 		});
		//户籍地
		$("#placeIdBtn").textbox({
            onClickIcon: function (index) {
                if (index == 0) {
                	$("#placeEditDialog").dialog('open');
                }
            }
        }); 
		//居住地区
		$("#liveAreaIdBtn").textbox({
            onClickIcon: function (index) {
                if (index == 0) {
                	$("#liveAreaEditDialog").dialog('open');
                }
            }
        }); 
		//小区
		$("#livePlotIdBtn").textbox({
            onClickIcon: function (index) {
                if (index == 0) {
                	var liveAreaId = $('#liveAreaId').val();
                	if(liveAreaId==null || liveAreaId=="" || liveAreaId==undefined){
                		$.messager.alert("提示","请先选择居住地区","warning");
                	}else{
                		$("#livePlotEditDialog").dialog('open');
                		$('#livePlotList').datagrid({
                    		queryParams: {
                    			"liveAreaid":liveAreaId
                    		}
                		});
                	}
                }
            }
        }); 
		//转交人
		$("#toUserBtn").textbox({
            onClickIcon: function (index) {
                if (index == 0) {
                	var fromUserId = $('#fromUserId').val();
                	$("#transferUserEditDialog").dialog('open');
                	$('#transferUserList').datagrid({
                		queryParams: {
                			"transferUserId":fromUserId
                		}
            		});
                }
            }
        }); 
		//配偶户籍地
		$("#spousePlaceIdBtn").textbox({
            onClickIcon: function (index) {
                if (index == 0) {
                	$("#spousePlaceEditDialog").dialog('open');
                }
            }
        }); 
		//拥有人
		$("#ownerIdBtn").textbox({
            onClickIcon: function (index) {
                if (index == 0) {
                	$("#ownerEditDialog").dialog('open');
                }
            }
        }); 
		//户籍地
		$("#placeIdModBtn").textbox({
            onClickIcon: function (index) {
                if (index == 0) {
                	$("#placeModEditDialog").dialog('open');
                }
            }
        }); 
		//居住地区
		$("#liveAreaIdModBtn").textbox({
            onClickIcon: function (index) {
                if (index == 0) {
                	$("#liveAreaModEditDialog").dialog('open');
                }
            }
        }); 
		//小区
		$("#livePlotIdModBtn").textbox({
            onClickIcon: function (index) {
                if (index == 0) {
                	var liveAreaId = $('#liveAreaIdMod').val();
                	if(liveAreaId==null || liveAreaId=="" || liveAreaId==undefined){
                		$.messager.alert("提示","请先选择居住地区","warning");
                	}else{
                		$("#livePlotModEditDialog").dialog('open');
                		$('#livePlotModList').datagrid({
                    		queryParams: {
                    			"liveAreaid":liveAreaId
                    		}
                		});
                	}
                }
            }
        }); 
		//配偶户籍地
		$("#spousePlaceIdModBtn").textbox({
            onClickIcon: function (index) {
                if (index == 0) {
                	$("#spousePlaceModEditDialog").dialog('open');
                }
            }
        }); 
		//拥有人
		$("#ownerIdModBtn").textbox({
            onClickIcon: function (index) {
                if (index == 0) {
                	$("#ownerModEditDialog").dialog('open');
                }
            }
        });
	},
	//全部客户
	wholeCustomer:function (){
		$("#searchForm").form('clear');
		$('#list').datagrid({
			url:ctx + "/myWorkbench/totalCustomer/listByPage.jhtml",
			queryParams: {}
		});
	},
	//个人客户
	personalCustomer:function (){
		$("#searchForm").form('clear');
		$('#list').datagrid({
			url:ctx + "/myWorkbench/totalCustomer/listPersonalByPage.jhtml",
			queryParams: {
    			"customerType":1
    		}
		});
	},
	//企业客户
	enterpriseCustomer:function (){
		$("#searchForm").form('clear');
		$('#list').datagrid({
			url:ctx + "/myWorkbench/totalCustomer/listEnterpriseByPage.jhtml",
			queryParams: {
    			"customerType":2
    		}
		});
	},
	//今日更新
	todayUpdate:function (){
		$("#searchForm").form('clear');
		$("#list").datagrid({
			url:ctx + "/myWorkbench/totalCustomer/listTodayUpdateByPage.jhtml",
			queryParams : {}
		});
	},
	//任务抄送人
	taskreceiver_leftToRight:function(){
		totalCustomer.addTaskReceiverPerson();
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
    	/*$("#receiverNameTask").textbox("setValue", dataName);
        $("#receiveTaskId").val(dataid);*/
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
		totalCustomer.taskdelrightToRight();
	},
	taskdelrightToRight:function(){
		var checkedRows = $('#taskreceiverrightGrid').datagrid('getChecked');
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
    		dataid = dataid.replace(checkedRows[i].id+",",'');
    		dataName = dataName.replace(checkedRows[i].name+",",'');
    		if(i==checkedRows.length-1){
    			dataid=dataid.substr(0, dataid.length - 1);
    			dataName=dataName.substr(0, dataName.length - 1);
    		}
    	}
    	$("#storeName").val(dataName);
        $("#storeId").val(dataid);
    	/*$("#receiverNameTask").textbox("setValue", dataName);
        $("#receiveTaskId").val(dataid);*/
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
};

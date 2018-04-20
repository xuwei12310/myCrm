var customerDetails = {
	init: function (params) {
		customerDetails.initBtn();
		customerDetails.initCustomerPropertyDialog();                 //产权信息
		customerDetails.initCustomerCarEditDialog();                  //车产信息
		customerDetails.initCustomerRelationshipEditDialog();		  //联系人
		customerDetails.initCustomerOtherResourcesDialog();           //其他财力
		customerDetails.initCustomerLiabilitiesDialog();              //负债
		customerDetails.initImgEditDialog();                          //预览图片
		//产权信息
		$("#customerPropertyList").datagrid({
    		pagination:true,
    		fit:true,
    		border:false,
    		toolbar: [
    		          {iconCls: 'icon-add',text:'新增',handler: function(){customerDetails.addProperty();}},
    		          {iconCls: 'icon-edit',text:'修改',handler: function(){customerDetails.updateProperty();}},
    		          {iconCls: 'icon-remove',text:'删除',handler: function(){customerDetails.deleteProperty();}},
    		          {iconCls: 'icon-download',text:'下载附件',handler: function(){customerDetails.downloadProperty();}},
    		],
    		ctrlSelect:true,
    		rownumbers:true,
    		pageSize:20,
    		pageList:[10,20,30,40,50,100],
       	 	columns:[[
       	 			{field:'ck',checkbox:true},
					{field:'certificate',title:'产权证号',width:100,align:'center'},
					{field:'owner',title:'所有人',width:100,align:'center'},
					{field:'isCommon',title:'共有情况',width:100,align:'center',formatter: function(value,row,index){
                        if(value == 1){
                            return "是";
                        }else{
                            return "否";
                        }},
					},
					{field:'area',title:'面积(平方)',width:100,align:'center'},
					{field:'housingNatureName',title:'房屋性质',width:100,align:'center'},
					{field:'areaShowName',title:'居住地区',width:100,align:'center'},
					{field:'plotPlotName',title:'小区',width:100,align:'center'},
					{field:'houseAddress',title:'房屋地址',width:100,align:'center'},
					{field:'havaLandCertificate',title:'有无土地证',width:100,align:'center',formatter: function(value,row,index){
                        if(value == 1){
                            return "有";
                        }else{
                            return "无";
                        }},
					},
					{field:'landCertificateNumber',title:'土地证号',width:100,align:'center'},
					{field:'landNatureName',title:'土地性质',width:100,align:'center'},
					{field:'propertyValue',title:'房产价值(万元)',width:100,align:'center'}
       		]],
       		//行双击事件
    		onDblClickRow:function(){
    			customerDetails.updateProperty();
    		}
    	});
		//车产信息
		$("#customerCarList").datagrid({
    		pagination:true,
    		fit:true,
    		border:false,
    		toolbar: [
    		          {iconCls: 'icon-add',text:'新增',handler: function(){customerDetails.addCar();}},
    		          {iconCls: 'icon-edit',text:'修改',handler: function(){customerDetails.updateCar();}},
    		          {iconCls: 'icon-remove',text:'删除',handler: function(){customerDetails.deleteCar();}},
    		          {iconCls: 'icon-download',text:'下载附件',handler: function(){customerDetails.downloadCar();}},
    		],
    		ctrlSelect:true,
    		rownumbers:true,
    		pageSize:20,
    		pageList:[10,20,30,40,50,100],
       	 	columns:[[
       	 			{field:'ck',checkbox:true},
					{field:'brand',title:'品牌',width:100,align:'center'},
					{field:'model',title:'型号',width:100,align:'center'},
					{field:'years',title:'年份',width:100,align:'center'},
					{field:'carStatus',title:'车况',width:100,align:'center'},
					{field:'valuation',title:'估值(万元)',width:100,align:'center'},
       		]],
       		//行双击事件
    		onDblClickRow:function(){
    			customerDetails.updateCar();
    		}
    	});
		//联系人
		$("#customerRelationshipList").datagrid({
    		pagination:true,
    		fit:true,
    		border:false,
    		toolbar: [
    		          {iconCls: 'icon-add',text:'新增',handler: function(){customerDetails.addRelationship();}},
    		          {iconCls: 'icon-edit',text:'修改',handler: function(){customerDetails.updateRelationship();}},
    		          {iconCls: 'icon-remove',text:'删除',handler: function(){customerDetails.deleteRelationship();}},
    		],
    		ctrlSelect:true,
    		rownumbers:true,
    		pageSize:20,
    		pageList:[10,20,30,40,50,100],
       	 	columns:[[
       	 			{field:'ck',checkbox:true},
					{field:'name',title:'姓名',width:100,align:'center'},
					{field:'sex',title:'性别',width:100,align:'center',formatter: function(value,row,index){
                        if(value == 1){
                            return "男";
                        }else{
                            return "女";
                        }},
					},
					{field:'relationship',title:'关系',width:100,align:'center'},
					{field:'telephone',title:'联系电话',width:100,align:'center'},
					{field:'unit',title:'单位',width:100,align:'center'},
					{field:'vocation',title:'职业',width:100,align:'center'},
					{field:'idNumber',title:'身份证号',width:150,align:'center'},
//						{field:'company',title:'附件',width:100,align:'center'},
       		]],
       		//行双击事件
    		onDblClickRow:function(){
    			customerDetails.updateRelationship();
    		}
    	});
		//其他财力
		$("#customerOtherResourcesList").datagrid({
    		pagination:true,
    		fit:true,
    		border:false,
    		toolbar: [
    		          {iconCls: 'icon-add',text:'新增',handler: function(){customerDetails.addOtherResources();}},
    		          {iconCls: 'icon-edit',text:'修改',handler: function(){customerDetails.updateOtherResources();}},
    		          {iconCls: 'icon-remove',text:'删除',handler: function(){customerDetails.deleteOtherResources();}},
    		],
    		ctrlSelect:true,
    		rownumbers:true,
    		pageSize:20,
    		pageList:[10,20,30,40,50,100],
       	 	columns:[[
       	 			{field:'ck',checkbox:true},
					{field:'name',title:'财力名称',width:150,align:'center'},
					{field:'value',title:'价值(万元)',width:120,align:'center'},
					{field:'note',title:'备注',width:200,align:'center'}
       		]],
       		//行双击事件
    		onDblClickRow:function(){
    			customerDetails.updateOtherResources();
    		}
    	});
		//负债
		$("#customerLiabilitiesList").datagrid({
    		pagination:true,
    		fit:true,
    		border:false,
    		toolbar: [
    		          {iconCls: 'icon-add',text:'新增',handler: function(){customerDetails.addLiabilities();}},
    		          {iconCls: 'icon-edit',text:'修改',handler: function(){customerDetails.updateLiabilities();}},
    		          {iconCls: 'icon-remove',text:'删除',handler: function(){customerDetails.deleteLiabilities();}},
    		],
    		ctrlSelect:true,
    		rownumbers:true,
    		pageSize:20,
    		pageList:[10,20,30,40,50,100],
       	 	columns:[[
       	 			{field:'ck',checkbox:true},
					{field:'name',title:'负债名称',width:150,align:'center'},
					{field:'value',title:'金额(万元)',width:120,align:'center'},
					{field:'note',title:'备注',width:200,align:'center'}
       		]],
       		//行双击事件
    		onDblClickRow:function(){
    			customerDetails.updateLiabilities();
    		}
    	});
	},
	initBtn:function(){
		$("#areasidBtn").textbox({
            onClickIcon: function (index) {
                if (index == 0) {
                	$("#proAreaEditDialog").dialog('open');
                }
            }
        }); 
		$("#plotsidBtn").textbox({
            onClickIcon: function (index) {
                if (index == 0) {
                	var liveAreaId = $('#areasid').val();
                	if(liveAreaId==null || liveAreaId=="" || liveAreaId==undefined){
                		$.messager.alert("提示","请先选择居住地区","warning");
                	}else{
                		$("#proPlotEditDialog").dialog('open');
                		$('#proPlotList').datagrid({
                    		queryParams: {
                    			"liveAreaid":liveAreaId
                    		}
                		});
                	}
                	
                }
            }
        }); 
	},
	//新增负债信息
	addLiabilities:function(){
		var id = $("#idUpdate").val();
		$("#cusLiabilitiesId").val(id);
		$("#customerLiabilitiesDialog").dialog('open');
		customerDetails.initCustomerLiabilitiesForm();
		$("#customerLiabilitiesForm").attr('action',ctx + "/myWorkbench/customerLiabilities/create.jhtml");
	},
	//修改负债信息
	updateLiabilities:function(){
		var selectedRow = $("#customerLiabilitiesList").datagrid('getSelected');
		if(selectedRow==null){
    		$.messager.alert('提示','请选择数据行','warning');
    		return;
    	}
		$("#customerLiabilitiesDialog").dialog('open');
		$("#customerLiabilitiesForm").form('load',selectedRow);
		customerDetails.initCustomerLiabilitiesForm();
		$("#customerLiabilitiesForm").attr('action',ctx + "/myWorkbench/customerLiabilities/update.jhtml");
	},
	//删除负债信息
	deleteLiabilities:function(){
		var checkedRows = $("#customerLiabilitiesList").datagrid('getChecked');
    	if(checkedRows.length==0){
    		$.messager.alert('提示','请选择删除的数据行','warning');
    		return;
    	}
    	$.messager.confirm("提示","确定要删除选中数据?",function(t){ 
			if(!t) return;
			var idArray = new Array();
			for(var i=0;i<checkedRows.length;i++){
				idArray.push(checkedRows[i].id);
			}
			var ids = idArray.join(CSIT.join);
			var content = {ids:ids};
			$.messager.progress();
			CSIT.asyncCallService(ctx + "/myWorkbench/customerLiabilities/mulDelete.jhtml",content,function(result){
				if(result.isSuccess){
					$("#customerLiabilitiesList").datagrid('reload');
				}else{
					$.messager.alert('错误',result.message,"error");
				}
				$.messager.progress('close');
			});
    	});
	},
	//新增其他财力信息
	addOtherResources:function(){
		var id = $("#idUpdate").val();
		$("#cusOtherResourcesId").val(id);
		$("#customerOtherResourcesDialog").dialog('open');
		customerDetails.initCustomerOtherResourcesForm();
		$("#customerOtherResourcesForm").attr('action',ctx + "/myWorkbench/customerOtherResources/create.jhtml");
	},
	//修改其他财力信息
	updateOtherResources:function(){
		var selectedRow = $("#customerOtherResourcesList").datagrid('getSelected');
		if(selectedRow==null){
    		$.messager.alert('提示','请选择数据行','warning');
    		return;
    	}
		$("#customerOtherResourcesDialog").dialog('open');
		$("#customerOtherResourcesForm").form('load',selectedRow);
		customerDetails.initCustomerOtherResourcesForm();
		$("#customerOtherResourcesForm").attr('action',ctx + "/myWorkbench/customerOtherResources/update.jhtml");
	},
	//删除其他财力信息
	deleteOtherResources:function(){
		var checkedRows = $("#customerOtherResourcesList").datagrid('getChecked');
    	if(checkedRows.length==0){
    		$.messager.alert('提示','请选择删除的数据行','warning');
    		return;
    	}
    	$.messager.confirm("提示","确定要删除选中数据?",function(t){ 
			if(!t) return;
			var idArray = new Array();
			for(var i=0;i<checkedRows.length;i++){
				idArray.push(checkedRows[i].id);
			}
			var ids = idArray.join(CSIT.join);
			var content = {ids:ids};
			$.messager.progress();
			CSIT.asyncCallService(ctx + "/myWorkbench/customerOtherResources/mulDelete.jhtml",content,function(result){
				if(result.isSuccess){
					$("#customerOtherResourcesList").datagrid('reload');
				}else{
					$.messager.alert('错误',result.message,"error");
				}
				$.messager.progress('close');
			});
    	});
	},
	//新增联系人
	addRelationship:function(){
		app.sexCombobox("#sexRts");
		$("#sexRts").combobox("setValue",1);
		var id = $("#idUpdate").val();
		$("#cusRelationshipId").val(id);
		$("#customerRelationshipEditDialog").dialog('open');
		customerDetails.initCustomerRelationshipForm();
		$("#customerRelationshipForm").attr('action',ctx + "/myWorkbench/customerRelationship/create.jhtml");
	},
	//修改联系人
	updateRelationship:function(){
		var selectedRow = $("#customerRelationshipList").datagrid('getSelected');
		if(selectedRow==null){
    		$.messager.alert('提示','请选择数据行','warning');
    		return;
    	}
		app.sexCombobox("#sexRts");
		$("#customerRelationshipEditDialog").dialog('open');
		$("#customerRelationshipForm").form('load',selectedRow);
		if(selectedRow.attachId!=null){
			var data ={attachId:selectedRow.attachId};
			var attach=CSIT.syncCallService(ctx + "/common/util/getAttach.jhtml",data);
			$("#attachRtsName").filebox("setText",attach.name);
		}
		customerDetails.initCustomerRelationshipForm();
		$("#customerRelationshipForm").attr('action',ctx + "/myWorkbench/customerRelationship/update.jhtml");
	},
	//删除联系人
	deleteRelationship:function(){
		var checkedRows = $("#customerRelationshipList").datagrid('getChecked');
    	if(checkedRows.length==0){
    		$.messager.alert('提示','请选择删除的数据行','warning');
    		return;
    	}
    	$.messager.confirm("提示","该操作将会删除附件信息，确定要删除选中的联系人?",function(t){ 
			if(!t) return;
			var idArray = new Array();
			for(var i=0;i<checkedRows.length;i++){
				idArray.push(checkedRows[i].id);
			}
			var ids = idArray.join(CSIT.join);
			var content = {ids:ids};
			$.messager.progress();
			CSIT.asyncCallService(ctx + "/myWorkbench/customerRelationship/mulDelete.jhtml",content,function(result){
				if(result.isSuccess){
					$("#customerRelationshipList").datagrid('reload');
				}else{
					$.messager.alert('错误',result.message,"error");
				}
				$.messager.progress('close');
			});
    	});
	},
	//新增车产信息
	addCar:function(){
		var id = $("#idUpdate").val();
		$("#cusCarId").val(id);
		$("#customerCarEditDialog").dialog('open');
		customerDetails.initCustomerCarForm();
		$("#customerCarForm").attr('action',ctx + "/myWorkbench/consumerCar/create.jhtml");
	},
	//修改车产信息
	updateCar:function(){
		var selectedRow = $("#customerCarList").datagrid('getSelected');
		if(selectedRow==null){
    		$.messager.alert('提示','请选择数据行','warning');
    		return;
    	}
		$("#customerCarEditDialog").dialog('open');
		$("#customerCarForm").form('load',selectedRow);
		if(selectedRow.attachId!=null){
			var data ={attachId:selectedRow.attachId};
			var attach=CSIT.syncCallService(ctx + "/common/util/getAttach.jhtml",data);
			$("#attachCarName").filebox("setText",attach.name);
		}
		customerDetails.initCustomerCarForm();
		$("#customerCarForm").attr('action',ctx + "/myWorkbench/consumerCar/update.jhtml");
	},
	//删除车产信息
	deleteCar:function(){
		var checkedRows = $("#customerCarList").datagrid('getChecked');
    	if(checkedRows.length==0){
    		$.messager.alert('提示','请选择删除的数据行','warning');
    		return;
    	}
    	$.messager.confirm("提示","该操作将会删除附件信息，确定要删除选中的车产信息?",function(t){ 
			if(!t) return;
			var idArray = new Array();
			for(var i=0;i<checkedRows.length;i++){
				idArray.push(checkedRows[i].id);
			}
			var ids = idArray.join(CSIT.join);
			var content = {ids:ids};
			$.messager.progress();
			CSIT.asyncCallService(ctx + "/myWorkbench/consumerCar/mulDelete.jhtml",content,function(result){
				if(result.isSuccess){
					$("#customerCarList").datagrid('reload');
				}else{
					$.messager.alert('错误',result.message,"error");
				}
				$.messager.progress('close');
			});
    	});
	},
	//车产信息下载附件
	downloadCar:function(){
		var selectedRow = $("#customerCarList").datagrid('getSelected');
		if(selectedRow==null){
    		$.messager.alert('提示','请选择数据行','warning');
    		return;
    	}
		if(selectedRow.attachId==null){
			$.messager.alert('提示','无可下载附件','warning');
    		return;
		}
		window.open(ctx + "/myWorkbench/consumerCar/downloadFile.jhtml"+"?attachId="+selectedRow.attachId);
	},
	//新增产权信息
	addProperty:function(){
		customerDetails.initProAreaEditDialog();
		customerDetails.initProPlotEditDialog();
		app.initCombobox("#housingNatureId",ctx + "/common/util/getDictByType.jhtml",{type:"housingNature"});
		app.initCombobox("#landNatureId",ctx + "/common/util/getDictByType.jhtml",{type:"landNature"});
		var id = $("#idUpdate").val();
		$("#cusPropertyId").val(id);
		$("#customerPropertyDialog").dialog('open');
		customerDetails.initCustomerPropertyForm();
		$("#customerPropertyForm").attr('action',ctx + "/myWorkbench/customerProperty/create.jhtml");
	},
	//修改产权信息
	updateProperty:function(){
		customerDetails.initProAreaEditDialog();
		customerDetails.initProPlotEditDialog();
		var selectedRow = $("#customerPropertyList").datagrid('getSelected');
		if(selectedRow==null){
    		$.messager.alert('提示','请选择数据行','warning');
    		return;
    	}
		app.initCombobox("#housingNatureId",ctx + "/common/util/getDictByType.jhtml",{type:"housingNature"});
		app.initCombobox("#landNatureId",ctx + "/common/util/getDictByType.jhtml",{type:"landNature"});
		var id = $("#idUpdate").val();
		$("#cusPropertyId").val(id);
		$('#housingNatureId').combobox('setValue',selectedRow.housingNatureid);
		$('#landNatureId').combobox('setValue',selectedRow.landNatureid);
		$("#areasid").val(selectedRow.areaId);
		$("#plotsid").val(selectedRow.plotId);
		if(selectedRow.attachId!=null){
			var data ={attachId:selectedRow.attachId};
			var attach=CSIT.syncCallService(ctx + "/common/util/getAttach.jhtml",data);
			$("#attachName").filebox("setText",attach.name);
		}
		$("#customerPropertyDialog").dialog('open');
		$("#customerPropertyForm").form('load',selectedRow);
		customerDetails.initCustomerPropertyForm();
		$("#customerPropertyForm").attr('action',ctx + "/myWorkbench/customerProperty/update.jhtml");
	},
	//删除产权信息
	deleteProperty:function(){
		var checkedRows = $("#customerPropertyList").datagrid('getChecked');
    	if(checkedRows.length==0){
    		$.messager.alert('提示','请选择删除的数据行','warning');
    		return;
    	}
    	$.messager.confirm("提示","该操作将会删除附件信息，确定要删除选中的产权信息?",function(t){ 
			if(!t) return;
			var idArray = new Array();
			for(var i=0;i<checkedRows.length;i++){
				idArray.push(checkedRows[i].id);
			}
			var ids = idArray.join(CSIT.join);
			var content = {ids:ids};
			$.messager.progress();
			CSIT.asyncCallService(ctx + "/myWorkbench/customerProperty/mulDelete.jhtml",content,function(result){
				if(result.isSuccess){
					$("#customerPropertyList").datagrid('reload');
				}else{
					$.messager.alert('错误',result.message,"error");
				}
				$.messager.progress('close');
			});
    	});
	},
	//产权信息附件下载
	downloadProperty:function(){
		var selectedRow = $("#customerPropertyList").datagrid('getSelected');
		if(selectedRow==null){
    		$.messager.alert('提示','请选择数据行','warning');
    		return;
    	}
		if(selectedRow.attachId==null){
			$.messager.alert('提示','无可下载附件','warning');
    		return;
		}
		window.open(ctx + "/myWorkbench/customerProperty/downloadFile.jhtml"+"?attachId="+selectedRow.attachId);
	},
	//信用等级修改
	initEditCreditForm:function(ext){
    	$("#editCreditForm").form({  
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
    					$("#list").datagrid('reload');
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
        	    		$("#editCreditForm").form('load',selectedRow);
        	    		customerDetails.initEditCreditForm();
        	    		$("#editCreditForm").attr('action',ctx + "/myWorkbench/totalCustomer/modCustomerCredit.jhtml");
    					
    				});
    			}else{
    				$.messager.alert('提示',data.message,'error');
    			}
    		}
    	});
    },
	//负债
	initCustomerLiabilitiesDialog:function(){
		var params = {
	    		title:'编辑负债信息',
	    		width : 420,
	    		height : document.documentElement.clientHeight-5<300?document.documentElement.clientHeight-5:300,
				closed : true,
				cache : false,
				modal : true,
				buttons:[{
					text:'保存',
					iconCls:'icon-save',
					handler:function(){
						$("#customerLiabilitiesForm").submit();
					}
				},{
					text:'退出',
					iconCls:'icon-exit',
					handler:function(){
						$("#customerLiabilitiesDialog").dialog('close');
					}
				}],
				onClose:function(){
					$("#customerLiabilitiesDialog").form('clear');
				}
			};
	    	$("#customerLiabilitiesDialog").dialog(params);
	},
	//负债
	initCustomerLiabilitiesForm:function(ext){
    	$("#customerLiabilitiesForm").form({  
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
    					$("#customerLiabilitiesDialog").dialog('close');
    					$("#customerLiabilitiesList").datagrid('reload');
    				});
    			}else{
    				$.messager.alert('提示',data.message,'error');
    			}
    		}
    	});
    },
	//其他财力
	initCustomerOtherResourcesDialog:function(){
		var params = {
	    		title:'编辑其他财力',
	    		width : 420,
	    		height : document.documentElement.clientHeight-5<300?document.documentElement.clientHeight-5:300,
				closed : true,
				cache : false,
				modal : true,
				buttons:[{
					text:'保存',
					iconCls:'icon-save',
					handler:function(){
						$("#customerOtherResourcesForm").submit();
					}
				},{
					text:'退出',
					iconCls:'icon-exit',
					handler:function(){
						$("#customerOtherResourcesDialog").dialog('close');
					}
				}],
				onClose:function(){
					$("#customerOtherResourcesDialog").form('clear');
				}
			};
	    	$("#customerOtherResourcesDialog").dialog(params);
	},
	//其他财力
	initCustomerOtherResourcesForm:function(ext){
    	$("#customerOtherResourcesForm").form({  
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
    					$("#customerOtherResourcesDialog").dialog('close');
    					$("#customerOtherResourcesList").datagrid('reload');
    				});
    			}else{
    				$.messager.alert('提示',data.message,'error');
    			}
    		}
    	});
    },
	//联系人
	initCustomerRelationshipEditDialog:function(){
		var params = {
	    		title:'编辑联系人',
	    		width : 420,
	    		height : document.documentElement.clientHeight-5<400?document.documentElement.clientHeight-5:400,
				closed : true,
				cache : false,
				modal : true,
				buttons:[{
					text:'保存',
					iconCls:'icon-save',
					handler:function(){
						$("#customerRelationshipForm").submit();
					}
				},{
					text:'退出',
					iconCls:'icon-exit',
					handler:function(){
						$("#customerRelationshipEditDialog").dialog('close');
					}
				}],
				onClose:function(){
					$("#customerRelationshipEditDialog").form('clear');
				}
			};
	    	$("#customerRelationshipEditDialog").dialog(params);
	},
	//联系人
	initCustomerRelationshipForm:function(ext){
    	$("#customerRelationshipForm").form({  
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
    					$("#customerRelationshipEditDialog").dialog('close');
    					$("#customerRelationshipList").datagrid('reload');
    				});
    			}else{
    				$.messager.alert('提示',data.message,'error');
    			}
    		}
    	});
    },
	//车产信息
	initCustomerCarEditDialog:function(){
		var params = {
	    		title:'编辑车产信息',
	    		width : 450,
	    		height : document.documentElement.clientHeight-5<340?document.documentElement.clientHeight-5:340,
				closed : true,
				cache : false,
				modal : true,
				buttons:[{
					text:'保存',
					iconCls:'icon-save',
					handler:function(){
						$("#customerCarForm").submit();
					}
				},{
					text:'退出',
					iconCls:'icon-exit',
					handler:function(){
						$("#customerCarEditDialog").dialog('close');
					}
				}],
				onClose:function(){
					$("input[name='attachCarName']").val("");
		    		$("#attachCarName").filebox('clear');
					$("#customerCarEditDialog").form('clear');
				}
			};
	    	$("#customerCarEditDialog").dialog(params);
	},
	//车产信息
	initCustomerCarForm:function(ext){
    	$("#customerCarForm").form({  
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
    					$("input[name='attachCarName']").val("");
			    		$("#attachCarName").filebox('clear');
			    		$("#imgEditDialog").dialog('close');
    					$("#customerCarEditDialog").dialog('close');
    					$("#customerCarList").datagrid('reload');
    				});
    			}else{
    				$.messager.alert('提示',data.message,'error');
    			}
    		}
    	});
    },
	//产权信息
	initCustomerPropertyDialog:function(){
		var params = {
	    		title:'编辑产权信息',
	    		width : 700,
	    		height : document.documentElement.clientHeight-5<500?document.documentElement.clientHeight-5:500,
				closed : true,
				cache : false,
				modal : true,
				buttons:[{
					text:'保存',
					iconCls:'icon-save',
					handler:function(){
						$("#customerPropertyForm").submit();
					}
				},{
					text:'退出',
					iconCls:'icon-exit',
					handler:function(){
						$("#customerPropertyDialog").dialog('close');
					}
				}],
				onClose:function(){
					$("input[name='attachName']").val("");
		    		$("#attachName").filebox('clear');
					$("#customerPropertyDialog").form('clear');
				}
			};
	    	$("#customerPropertyDialog").dialog(params);
	},
	//产权信息Form
	initCustomerPropertyForm:function(ext){
    	$("#customerPropertyForm").form({  
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
    					$("input[name='attachName']").val("");
			    		$("#attachName").filebox('clear');
			    		$("#imgEditDialog").dialog('close');
    					$("#customerPropertyDialog").dialog('close');
    					$("#customerPropertyList").datagrid('reload');
    				});
    			}else{
    				$.messager.alert('提示',data.message,'error');
    			}
    		}
    	});
    },
  //居住地区
    initProAreaEditDialog:function(){
    	$('#proAreaList').datagrid({  
			  border:true,
			  url:ctx + "/myWorkbench/totalCustomer/getLiveAreaByList.jhtml",
			  rownumbers:true,
			  pagination:true,
			  fit:true,
			  ctrlSelect:true,
			  method:"POST",
			  toolbar:[{
				  text:'选择',
					iconCls:'icon-ok',
					handler:function(){
						var selectedRow = $('#proAreaList').datagrid('getSelected');
						if(selectedRow==null){
				    		$.messager.alert('提示','请选择数据行','warning');
				    		return;
				    	}
						$('#areaProData').val(selectedRow.id);
						$("#areasidBtn").textbox("setValue", selectedRow.showName);
				        $("#areasid").val(selectedRow.id);
				        $("#plotsidBtn").textbox("setValue", "");
				        $("#plotsid").val("");
				        $("#proAreaEditDialog").dialog('close');
					}
				}],
		      columns:[[      
		          {field:'provinceName',title:'省份',width:100,align:'center'},
		          {field:'cityName',title:'城市',width:100,align:'center'},
		          {field:'areaName',title:'区/县名称',width:100,align:'center'},
		          {field:'showName',title:'显示名称',width:100,align:'center'},
		   	  ]],
		   	  onDblClickCell: function(index,field,value){
		   		  var rows = $('#proAreaList').datagrid('getRows');
		   		  var selectedRow = rows[index];
		   		  $('#areaProData').val(selectedRow.id);
		   		  $("#areasidBtn").textbox("setValue", selectedRow.showName);
		          $("#areasid").val(selectedRow.id);
		          $("#plotsidBtn").textbox("setValue", "");
		          $("#plotsid").val("");
		          $("#proAreaEditDialog").dialog('close');
		   	  }
		});
    	var params = {
	    	title:'选择居住地区',
			width : 600,
			height : document.documentElement.clientHeight-5<600?document.documentElement.clientHeight-5:600,
			closed : true,
			cache : false,
			modal : true,
		};
	    $("#proAreaEditDialog").dialog(params);
    },
    //小区
    initProPlotEditDialog:function(){
    	$('#proPlotList').datagrid({  
			  border:true,
			  url:ctx + "/myWorkbench/totalCustomer/getLivePlotByList.jhtml",
			  rownumbers:true,
			  pagination:true,
			  fit:true,
			  toolbar:[{
				  	text:'选择',
					iconCls:'icon-ok',
					handler:function(){
						var selectedRow = $('#proPlotList').datagrid('getSelected');
						if(selectedRow==null){
				    		$.messager.alert('提示','请选择数据行','warning');
				    		return;
				    	}
						$("#plotsidBtn").textbox("setValue", selectedRow.plotName);
				        $("#plotsid").val(selectedRow.id);
				        $("#proPlotEditDialog").dialog('close');
					}
				},{
					text:'新增',
					iconCls:'icon-add',
					handler:function(){
						var liveAreaId = $("#areasid").val();
						var liveAreaName = $("#areasidBtn").textbox("getValue");
						$("#addPlotAreaId").val(liveAreaId);
						$('#liveAreaName').text(liveAreaName);
						$("#addPlotDialog").dialog('open');
						customerDetails.initAddProPlotForm();
						$("#addPlotForm").attr('action',ctx + "/sys/plot/create.jhtml");
					}
				}],
	    	  ctrlSelect:true,
			  method:"POST",
		      columns:[[  
		          {field:'areaName',title:'区县',width:100,align:'center'},
		          {field:'plotName',title:'小区',width:100,align:'center'},
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
		   		  var rows = $('#proPlotList').datagrid('getRows');
		   		  var selectedRow = rows[index];
		   		  $("#plotsidBtn").textbox("setValue", selectedRow.plotName);
		          $("#plotsid").val(selectedRow.id);
		          $("#proPlotEditDialog").dialog('close');
		   	  }
		});
    	var params = {
	    	title:'选择小区',
			width : 600,
			height : document.documentElement.clientHeight-5<500?document.documentElement.clientHeight-5:500,
			closed : true,
			cache : false,
			modal : true,
		};
	    $("#proPlotEditDialog").dialog(params);
    },
    //图片预览
    initImgEditDialog:function(){
		var params = {
	    		title:'附件预览',
	    		width : $(document).width()-100,
	    		height : $(document).height()-100,
				closed : true,
				cache : false,
				modal : true,
				buttons:[{
					text:'退出预览',
					iconCls:'icon-exit',
					handler:function(){
						$("#imgEditDialog").dialog('close');
					}
				}],
				onClose:function(){
					$("#ImgPreview").attr("src","");
					$("#imgEditDialog").form('clear');
				}
		};
	    $("#imgEditDialog").dialog(params);
	},
	//产权信息预览图片附件
	seeAttach:function(){
		if($("input[name='attachName']")[0].value == '' || $("input[name='attachName']")[0].value == null){
			var proAttachId = $("#proAttachId").val();
			if(proAttachId== '' || proAttachId == null){
				$.messager.alert('提示','请选择上传附件','warning');
			}else{
				var urlTwo=ctx + "/myWorkbench/totalCustomer/imgSrcAttach.jhtml";
	    		var dataTwo ={attachid:proAttachId};
	    		var imgdata = CSIT.syncCallService(urlTwo,dataTwo);
	    		$("#ImgPreview").attr("src",imgdata.data.src);
	    		$("#imgEditDialog").dialog('open');
			}
		}else{
			totalCustomer.PreviewImage($("input[name='attachName']")[0], 'ImgPreview', 'imgEditDialog');
			$("#imgEditDialog").dialog('open');
		}
	},
	//车产信息预览图片附件
	seeCarAttach:function(){
		if($("input[name='attachCarName']")[0].value == '' || $("input[name='attachCarName']")[0].value == null){
			var carAttachId = $("#carAttachId").val();
			if(carAttachId== '' || carAttachId == null){
				$.messager.alert('提示','请选择上传附件','warning');
			}else{
				var urlTwo=ctx + "/myWorkbench/totalCustomer/imgSrcAttach.jhtml";
	    		var dataTwo ={attachid:carAttachId};
	    		var imgdata = CSIT.syncCallService(urlTwo,dataTwo);
	    		$("#ImgPreview").attr("src",imgdata.data.src);
	    		$("#imgEditDialog").dialog('open');
			}
		}else{
			totalCustomer.PreviewImage($("input[name='attachCarName']")[0], 'ImgPreview', 'imgEditDialog');
			$("#imgEditDialog").dialog('open');
		}
	},
	seeRtsAttach:function(){
		if($("input[name='attachRtsName']")[0].value == '' || $("input[name='attachRtsName']")[0].value == null){
			var rtsAttachId = $("#rtsAttachId").val();
			if(rtsAttachId== '' || rtsAttachId == null){
				$.messager.alert('提示','请选择上传附件','warning');
			}else{
				var urlTwo=ctx + "/myWorkbench/totalCustomer/imgSrcAttach.jhtml";
	    		var dataTwo ={attachid:rtsAttachId};
	    		var imgdata = CSIT.syncCallService(urlTwo,dataTwo);
	    		$("#ImgPreview").attr("src",imgdata.data.src);
	    		$("#imgEditDialog").dialog('open');
			}
		}else{
			totalCustomer.PreviewImage($("input[name='attachRtsName']")[0], 'ImgPreview', 'imgEditDialog');
			$("#imgEditDialog").dialog('open');
		}
	},
	seeCreditAttach:function(){
		if($("input[name='attachCreditName']")[0].value == '' || $("input[name='attachCreditName']")[0].value == null){
			var creditRatingAttachId = $("#creditRatingAttachId").val();
			if(creditRatingAttachId== '' || creditRatingAttachId == null){
				$.messager.alert('提示','请选择上传附件','warning');
			}else{
				var urlTwo=ctx + "/myWorkbench/totalCustomer/imgSrcAttach.jhtml";
	    		var dataTwo ={attachid:creditRatingAttachId};
	    		var imgdata = CSIT.syncCallService(urlTwo,dataTwo);
	    		$("#ImgPreview").attr("src",imgdata.data.src);
	    		$("#imgEditDialog").dialog('open');
			}
		}else{
			totalCustomer.PreviewImage($("input[name='attachCreditName']")[0], 'ImgPreview', 'imgEditDialog');
			$("#imgEditDialog").dialog('open');
		}
	},
	previewCreditAttach:function(){
		var creditRatingAttachId = $("#creditRatingAttachId").val();
		var urlTwo=ctx + "/myWorkbench/totalCustomer/imgSrcAttach.jhtml";
		var dataTwo ={attachid:creditRatingAttachId};
		var imgdata = CSIT.syncCallService(urlTwo,dataTwo);
		$("#ImgPreview").attr("src",imgdata.data.src);
		$("#imgEditDialog").dialog('open');
	},
	initAddProPlotForm:function(ext){
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
    					$("#proPlotList").datagrid('reload');
    				});
    			}else{
    				$.messager.alert('提示',data.message,'error');
    			}
    		}
    	});
    },
}

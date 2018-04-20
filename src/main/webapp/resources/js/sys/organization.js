var organization = {
	url: {
		getOrganization: function () {
	        return app.getUrl('getOrganization');//获取所有的部门列表
	    },
		getRole: function () {
		    return app.getUrl('getRole');//获取所有的角色列表
		},
	    orgBankAccountUrl: function(url){
	    	return ctx+'/sys/orgBankAccount/'+url+'.jhtml'; //获取 机构_银行账号的  url
	    }
	},
    init: function (params) {
    	//初始化自定义树
    	organization.initTree();
    	//初始化组织机构对应的员工
    	organization.initViewList();
    	//初始化新增和编辑部门dialog
    	app.treeCRUD.initEditDialog({
    		title:'编辑组织机构',
        	height:300
        });
        app.enableCombobox($('#status'));
        app.yesOrNoByIntCombobox('#isAdmin');
        //初始化自定义员工编辑的Dialog
        organization.initEditUserDialog();
    	//初始化银行账号配置-编辑框 dialog
    	organization.initOrgBankAccountEditDialog();
    	//初始化配置银行账户
    	organization.initOrgBankAccountList();
    	//添加部门
    	$('#addOrganizationBtn').click(function(){
    		//初始化编辑部门的dialog
        	app.treeCRUD.initEditDialog({
        		title:'新增组织机构',
            	height:390
            });
    		organization.add();
    	});
    	//编辑部门
    	$('#updateOrganizationBtn').click(function(){
    		//初始化编辑部门的dialog
        	app.treeCRUD.initEditDialog({
        		title:'编辑组织机构',
            	height:390
            });
    		organization.update();
    	});
        //删除部门
        $('#deleteOrganizationBtn').click(function(){
            organization.mulDelete();
        });
    	//配置银行账户
    	$('#peizhiOrgBankAccountBtn').click(function(){
    		//选中父节点
    		var selectedNode = $(app.id.treeId).tree('getSelected');
    		if(selectedNode==null){
    			$.messager.alert('提示','请选择编辑部门','warning');
    			return false;
    		}
    		/*if(selectedNode.id==1){
    			$.messager.alert('提示','无法编辑总公司','warning');
    			return false;
    		}*/
    		//初始化配置银行账户的dialog
    		$('#orgBankAccountList').datagrid({ 
    			url:organization.url.orgBankAccountUrl('getOrgBankAccountByList')+'?orgId='+selectedNode.id,
    			method:"POST",
    			
    		});
    		var params={
    				title:'配置【'+selectedNode.text+'】银行账户',	
    		};
    		$("#orgBankAccountDialog").dialog(params);
        	$("#orgBankAccountDialog").dialog("open");
        	
    	});
    	//银行账号配置-退出
    	$('#exitOrgBankAccountBtn').click(function(){
    		$("#orgBankAccountDialog").dialog("close");
    	});
    	//银行账号配置-添加
    	$('#addOrgBankAccountBtn').click(function(){
    		$('#orgBankAccountEditDialog').dialog({
        		title:'添加银行账号'
        	});
    		var selectedNode = $(app.id.treeId).tree('getSelected');
    		
        	$('#orgBankAccountEditForm').attr('action', organization.url.orgBankAccountUrl('create'));
        	$("#orgName").textbox("setValue",selectedNode.text);
        	$("#orgId").val(selectedNode.id);
        	$("#bankAccountStatus").combobox("setValue",1);
    		$("#orgBankAccountEditDialog").dialog("open");
    	});
    	//银行账号配置-修改
    	$('#updateOrgBankAccountBtn').click(function(){
    		$('#orgBankAccountEditDialog').dialog({
        		title:'编辑银行账号'
        	});
    		var selectedNode = $(app.id.treeId).tree('getSelected');
    		var selectRow = $('#orgBankAccountList').datagrid('getSelected');
    		if(selectRow==null){
    			$.messager.alert("提示","请选择银行信息数据行！","warning");
    			return;
    		}
    		//初始化数据
    		
    		$("#orgName").textbox("setValue",selectedNode.text);
        	$("#orgId").val(selectedNode.id);
        	$("#bankId").val(selectRow.id);
        	$("#accountName").textbox("setValue",selectRow.accountName);
        	$("#bank").textbox("setValue",selectRow.bank);
        	$("#accountNumber").textbox("setValue",selectRow.accountNumber);
        	$("#bankAccountStatus").combobox("setValue",selectRow.status);
        	$("#bankNote").textbox("setValue",selectRow.note);
        	
        	
        	$('#orgBankAccountEditForm').attr('action', organization.url.orgBankAccountUrl('update'));
    		$("#orgBankAccountEditDialog").dialog("open");
    	});
    	//银行账号配置-删除
    	$('#deleteOrgBankAccountBtn').click(function(){
    		var rows=$("#orgBankAccountList").datagrid('getChecked');
    		if(rows.length==0){
    			$.messager.alert("警告", "请选择要删除的数据行", "warning");
        		return;
    		}
    		var idArray = [];
        	for (var i = 0; i < rows.length; i++) {
        		idArray.push(rows[i].id);
        	}
        	
         	$.messager.confirm("提示", "确定要删除所选记录?", function(t) {
        		if(t){
        			$.messager.progress();
        			var url = organization.url.orgBankAccountUrl('mulDelete');
        			var content = {
    					ids : idArray.join(CSIT.join),
        			};
        			var result = CSIT.syncCallService(url, content);
        			if (result.isSuccess) {
        	  			$("#orgBankAccountList").datagrid('reload');
        			}else{
        				$.messager.alert("错误", result.message, "error");
        			}
        			$.messager.progress('close');
        		}
        	});
    	});
    	
    	
    	
    	//查询部门下的员工
    	$("#searchBtn").click(function(){
    		organization.search();
    	});
    	//清空查询条件
    	$("#clearBtn").click(function(){
    		$('#searchForm').form('clear');
    	});
    	//添加员工
    	$('#addUserBtn').click(function(){
    		organization.addUser();
    	});
    	//编辑员工
    	$('#updateUserBtn').click(function(){
    		organization.updateUser();
    	});
    	//批量修改状态
    	$("#userStatus").menu({
			onClick : function(item) {
				var name = item.name;
				organization.mulUserStatus(name);
			}
		});
    	//重置密码
    	$("#resetPwd").click(function(){
    		console.log(11);
    		organization.resetPwd();
    	});
    	//删除员工
    	$("#delete").click(function(){
    		console.log(22);
    		organization.deleteUser();
    	})
    },
    //初始化编辑银行账号配置的Dialog
	initOrgBankAccountEditDialog:function(){
    	$('#orgBankAccountEditDialog').dialog({
    		title:'编辑银行账号',
			width : 300,
			height : 390,
			closed : true,
			cache : false,
			modal : true,
			buttons:[{
				text:'保存',
				iconCls:'icon-save',
				handler:function(){
					$('#orgBankAccountEditForm').submit();
				}
			},{
				text:'退出',
				iconCls:'icon-exit',
				handler:function(){
					$('#orgBankAccountEditDialog').dialog('close');
				}
			}],
			onClose:function(){
				$('#orgBankAccountEditDialog').form('clear');
			}
    	});
    	$('#orgBankAccountEditForm').form({  
    		onSubmit: function(){ 
    			var isValid = $(this).form('validate');
    			if (!isValid){
    				$.messager.progress('close');
    			}
    			return isValid;
    		}, 
    		success:function(data){  
    			$.messager.progress('close');
    			var result = eval('(' + data + ')');  
    			if (result.isSuccess){
					$.messager.alert('提示','保存成功','info',function(){
    					$('#orgBankAccountEditDialog').dialog('close');
    					$('#orgBankAccountList').datagrid('reload');
    				});
    			}else{
    				$.messager.alert('提示',result.message,'error');
    			}
    		}
    	});
    	
    	
    	//银行账号配置- 状态下拉框
    	app.enableCombobox("#bankAccountStatus");
    	//公司类型-下拉框
    	app.orgTypeCombobox("#orgType");
    	
	},
	//改变部门类型框 中的类型
    changeType:function(parentId,childId){
    
    	$.post(app.getUrl('getOrganizationById'),{orgId:parentId},function(result){
			if(result.orgType != 1){
				$("#orgType").combobox({readonly: true});
				if(childId == null){
					$("#orgType").combobox('setValue',0);
				}else{
					$.post(app.getUrl('getOrganizationById'),{orgId:childId},function(result){
						
					     $("#orgType").combobox('setValue',result.orgType);
					});
				}
			}else{
				$("#orgType").combobox({readonly: false});
				if(childId == null ){
					$("#orgType").combobox('setValue',1);
					
				}else{
					$.post(app.getUrl('getOrganizationById'),{orgId:childId},function(result){
						
					     $("#orgType").combobox('setValue',result.orgType);
					});
				}
			}
		});
    	
    	
    },
    //新增部门
    add:function(){
    	
    	//选中父节点
		var selectedNode = $(app.id.treeId).tree('getSelected');
		if(selectedNode==null){
			$.messager.alert('提示','请选择所属部门','warning');
			return false;
		}
		organization.changeType(selectedNode.id,null);
		$("#parentId").combotree({
			animate:true,
    		lines:true,
    	  	dnd:true,
    	  	parentField:"pid",
    	  	url: app.getUrl("getOrganization"),
    	  	onChange: function(){
    	  		var parentId = $("#parentId").combotree('getValue');

	        	 organization.changeType(parentId,null);
    	  	}
		});
		$('#parentId').combotree('setValue',selectedNode.id); 
		
		
		$(app.id.editDialogId).dialog({
			width:358
		});
    	$(app.id.editDialogId).dialog('open');
    	app.treeCRUD.initEditForm(organization.save);
    	$(app.id.editFormId).attr('action',app.url.create());
    },
    
    //编辑部门
    update:function(ext){
    	
    	
		var selectedNode = $(app.id.treeId).tree('getSelected');
		if(selectedNode==null){
			$.messager.alert('提示','请选择编辑部门','warning');
			return false;
		}
		if(selectedNode.id==1){
			$.messager.alert('提示','无法编辑总公司','warning');
			return false;
		}
		//选中父节点
		var fatherNode = $(app.id.treeId).tree("getParent",selectedNode.target);
		$("#id").val(selectedNode.id);
		$("#organizationName").textbox("setValue",selectedNode.text);
		
		var id = selectedNode.id;
		organization.changeType(fatherNode.id,id);
		
		if(fatherNode!=null&&fatherNode!=undefined){
			$("#parentId").combotree({
				animate:true,
	    		lines:true,
	    	  	dnd:true,
	    	  	parentField:"pid",
	    	  	url: app.getUrl("getOrganization")+'?id='+selectedNode.id,
	    	  	onSelect:function(node){
	    	  		if(node.id == selectedNode.id){
	    	  			$('#parentId').combotree('setValue',fatherNode.id);  
	    	  			$.messager.alert('提示','上级部门无法选中自己','warning');
	    	  		}
	    	  		
	    	  	},
				onChange: function(){
	    	  		var parentId = $("#parentId").combotree('getValue');
	    	  		 organization.changeType(parentId,id);
		        	 
	    	  	}
				});
			$('#parentId').combotree('setValue',fatherNode.id);  
		}
		
		if(selectedNode.attributes.content!=null && selectedNode.attributes.content!=undefined&& selectedNode.attributes.content!=''){
			$("#note").textbox("setValue",selectedNode.attributes.content);
		}
    	$(app.id.editDialogId).dialog('open');
    	app.treeCRUD.initEditForm(organization.save);
    	$(app.id.editFormId).attr('action',app.url.update());
    },
    //删除部门
    mulDelete:function(){
        var selectedNode = $(app.id.treeId).tree('getSelected');
        if(selectedNode==null){
            $.messager.alert('提示','请选择删除部门','warning');
            return false;
        }
        if(selectedNode.id==1){
            $.messager.alert('提示','无法删除总公司','warning');
            return false;
        }
        //选中父节点
        var fatherNode = $(app.id.treeId).tree("getParent",selectedNode.target);
        var id = selectedNode.id;
		var data={
            orgId:id
		};
        CSIT.asyncCallService(ctx+"/sys/organization/deleteOrganizationById",data,function(data){
            if(data.isSuccess){
                $.messager.alert('提示','删除成功','info',function(){
                    var url = app.getUrl('findChildrenUser')+'?organizationId='+fatherNode.id;
                    $(app.id.listId).datagrid({
                        url:url
                    });
                    $(app.id.treeId).tree('reload',fatherNode.target);
                });
            }else{
                $.messager.alert('错误',result.message,"error");
            }
		});
    },
    //新增、编辑的保存后操作
    save:function(result){
		var id = $('#id').val();
		var name = $('#organizationName').val();
		var content = $('#note').val();
		var parentId = $('#parentId').combotree("getValue");
		//新增
		if(id==''){
			//var node = $(app.id.treeId).tree('getSelected');
			//var node = $("parentId").combotree('getSelected');
			var node=$(app.id.treeId).tree('find',parentId);
			$(app.id.treeId).tree('append',{
				parent: (node?node.target:null),
				data:[{
					id:result.data.id,
					text:name,
					state:'open',
					attributes:{
						content:content==undefined?"":content
					}
				}]
			});
		}else{
			var updateNote=$(app.id.treeId).tree('find',id);
			updateNote.text=name;
			updateNote.attributes.content = content;
			$(app.id.treeId).tree('update', updateNote);
			var root = $(app.id.treeId).tree('getRoot');
			$(app.id.treeId).tree('reload',root.target);
		}
		$(app.id.editDialogId).dialog('close');
	},
	//自定义树
	initTree:function(ext){
		$("#tree").tree({
			animate:true,
    		lines:true,
    	  	dnd:true,
    	  	parentField:"pId",
    	  	url: app.url.treeRoot(),
    	  	onBeforeExpand:function(node,param){
    	  		$(app.id.treeId).tree('options').url = app.url.treeChildren()+'?id='+node.id;  
    	  	},
      		onClick:function(node){ 
    			$(app.id.treeId).tree('expand',node.target);
    			var url = app.getUrl('findChildrenUser')+'?organizationId='+node.id;
    			$(app.id.listId).datagrid({
					url:url
				});
      		},
    		onLoadSuccess:function(node, data){
    			if(node==null){
    				var root = $(app.id.treeId).tree('getRoot');
    				$(app.id.treeId).tree('select', root.target);
    				$(app.id.treeId).tree('expand',root.target);
    				var url = app.getUrl('findChildrenUser')+'?organizationId='+root.id;
    				$(app.id.listId).datagrid({
    					url:url
    				});
    			}else{
    				 var t = $(this);  
    		         if(data){
	    		        $(data).each(function(index,d){  
		    		        if(this.state == 'closed'){  
		    		            t.tree('expandAll');
		    		        }  
	    		        });  
	    		    }  
    			}
    		},
    		onDrop: function(targetNode, source, point){  
    			var targetId = $(app.id.treeId).tree('getNode', targetNode).id;  
    			var url = app.url.move();
    			var content = {sourceId:source.id,targetId:targetId,point:point};
    			var result = CSIT.syncCallService(url,content);
    			if (!result.isSuccess){
    				$.messager.alert('错误',result.message,"error");
    			}
    		},
    		onBeforeDrop:function(target,source,point){
    			var root = $(app.id.treeId).tree('getRoot');
    			var targetId = $(app.id.treeId).tree('getNode', target).id;  
    			if(root.id==targetId){
    				$.messager.alert("提示",'不能与根节点同级','warning');
    				return false;
    			}
    			return true;
    		}
		});
	},
	//自定义员工列表
	initViewList:function(){
    	$('#list').datagrid({
    		pagination:true,
    		fit:true,
    		border:true,
    		toolbar:app.id.toolbarId,
    		ctrlSelect:true,
    		rownumbers:true,
    		columns:[[
       	 	    {field:'ck',checkbox:true},
      	        {field:'username',title:'用户名',width:100,align:'center'},
      	        {field:'name',title:'姓名',width:100,align:'center'},
      	        {field:'roles',title:'职务',width:100,align:'center'},
      	        {field:'companyName',title:'公司',width:150,align:'center'},
      	        {field:'organizations',title:'部门机构',width:100,align:'center'},
      	        {field:'phone',title:'手机号',width:100,align:'center'},
      	        {field:'email',title:'邮箱',width:100,align:'center'},
      	        {field:'wechat',title:'微信号',width:100,align:'center'},
      	        {field:'status',title:'状态',width:100,align:'center',
      	        	formatter: function(value,row,index){
						if(value == 1){
							return '启用';
						}else if(value == 0){
							return '禁用';
						}else{
							return '禁用';
						}
      	        	}
      	        }
       		]]
    	});
    },
	//自定义查询部门下的员工
	search:function(){
		var content = $('#searchForm').serializeObject();
		var selectedNode = $(app.id.treeId).tree('getSelected');
		content["organizationId"] = selectedNode.id;
    	$(app.id.listId).datagrid({
    		queryParams:content
    	});
	},
	//自定义员工编辑的Dialog
	initEditUserDialog:function(){
    	$('#editUserDialog').dialog({
    		title:'新增员工',
			width : 400,
			height : 430,
			closed : true,
			cache : false,
			modal : true,
			buttons:[{
				text:'保存',
				iconCls:'icon-save',
				handler:function(){
					$('#editUserForm').submit();
				}
			},{
				text:'退出',
				iconCls:'icon-exit',
				handler:function(){
					$('#editUserDialog').dialog('close');
				}
			}],
			onClose:function(){
				$('#editUserDialog').form('clear');
			}
    	});
    	$('#editUserForm').form({  
    		onSubmit: function(){ 
    			var isValid = $(this).form('validate');
    			if (!isValid){
    				$.messager.progress('close');
    			}
    			//选择职务
    			var roleIds = '';
    			$("input[name='roleId']").each(function(){   
    				var roleId = $(this).val();
    				if(roleId!=null&&roleId!=undefined){
    					roleIds+=roleId+CSIT.join;
    				}
    			});	  
    			$('#roleIds').val(roleIds);
    			return isValid;
    		}, 
    		success:function(data){  
    			$.messager.progress('close');
    			var result = eval('(' + data + ')');  
    			if (result.isSuccess){
					$.messager.alert('提示','保存成功','info',function(){
    					$('#editUserDialog').dialog('close');
    					$('#list').datagrid('reload');
    				});
    			}else{
    				$.messager.alert('提示',result.message,'error');
    			}
    		}
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
	//添加员工
	addUser:function(){
		$('#editUserDialog').dialog({
    		title:'新增员工'
    	});
		//显示密码输入框
		$('#passwordTr').show();
		$('#password').textbox({ required:true });
		app.sexCombobox('#sex');
		$("#status").combobox("setValue",1);
        $("#isAdmin").combobox("setValue",0);
		$('#sex').combobox('setValue','男');  
		$("#companyId").combotree({  
	         onChange:function(){  
	        	 var companyId = $("#companyId").combotree('getValue');
	        	 organization.initApartmentId(companyId);
	         }
	     });  
		$('#editUserDialog').dialog('open');
    	$('#editUserForm').attr('action', app.getUrl('addUser'));

	},
	//编辑员工
	updateUser:function(){
		$('#editUserDialog').dialog({
    		title:'编辑员工'
    	});
		var selectRow = $('#list').datagrid('getSelected');
		if(selectRow==null){
			$.messager.alert("提示","请选择员工数据行！","warning");
			return;
		}
		app.sexCombobox('#sex');
		//隐藏密码输入框
		$('#passwordTr').hide();
		$('#password').textbox({ required:false });
		//$('#sex').combobox('setValue',selectRow.sex); 
		
		 
		 
		$("#companyId").combotree({  
	         onChange:function(){  
	        	 var companyId = $("#companyId").combotree('getValue');
	        	 organization.initApartmentId(companyId);
	         }
	     });
		$('#companyId').combotree('setValue',selectRow.companyId); 
		$('#organizationId').combotree('setValue',selectRow.organizationId); 
		var roleIds = selectRow.roleIds;
		//职务
		if(roleIds!=null&&roleIds!=undefined&&roleIds!=''){
			$('#roleId').combobox('setValue',roleIds.split(","));
		}
		
		$('#editUserForm').form('load',selectRow);//选择时 初始化数据
    	$('#editUserForm').attr('action', app.getUrl('updateUser'));
		$('#editUserDialog').dialog('open');
	},
	//批量修改员工状态
	mulUserStatus:function(status){
		var statusText = '启用';
		if(status == 0){
			statusText = '禁用';
		}
		var rows = $("#list").datagrid('getChecked');
    	if (rows.length == 0) {
    		$.messager.alert("警告", "请选择要" +statusText+ "的数据行", "warning");
    		return;
    	}
    	var idArray = [];
    	for (var i = 0; i < rows.length; i++) {
    		idArray.push(rows[i].id);
    	}
    	$.messager.confirm("提示", "确定要"+statusText+ "所选记录?", function(t) {
    		if(t){
    			$.messager.progress();
    			var url = app.getUrl('mulUserStatus');
    			var content = {
					ids : idArray.join(CSIT.join),
					status : status
    			};
    			var result = CSIT.syncCallService(url, content);
    			if (result.isSuccess) {
    	  			$("#list").datagrid('reload');
    			}else{
    				$.messager.alert("错误", result.message, "error");
    			}
    			$.messager.progress('close');
    		}
    	});
	},
	resetPwd:function(){
		var rows=$("#list").datagrid('getChecked');
		if(rows.length==0){
			$.messager.alert("警告", "请选择要重置密码的数据行", "warning");
    		return;
		}
		var idArray = [];
    	for (var i = 0; i < rows.length; i++) {
    		idArray.push(rows[i].id);
    	}
     	$.messager.confirm("提示", "确定要重置所选记录密码为a123456?", function(t) {
    		if(t){
    			$.messager.progress();
    			var url = app.getUrl('resetPwd');
    			var content = {
					ids : idArray.join(CSIT.join),
    			};
    			var result = CSIT.syncCallService(url, content);
    			if (result.isSuccess) {
    				$.messager.alert("提示", "操作成功", "info");
    	  			$("#list").datagrid('reload');
    			}else{
    				$.messager.alert("错误", result.message, "error");
    			}
    			$.messager.progress('close');
    		}
    	});
	},
	deleteUser:function(){
		var rows=$("#list").datagrid('getChecked');
		if(rows.length==0){
			$.messager.alert("警告", "请选择要删除的数据行", "warning");
    		return;
		}
		var idArray = [];
    	for (var i = 0; i < rows.length; i++) {
    		idArray.push(rows[i].id);
    	}
     	$.messager.confirm("提示", "确定要删除所选记录?", function(t) {
    		if(t){
    			$.messager.progress();
    			var url = app.getUrl('delete');
    			var content = {
					ids : idArray.join(CSIT.join),
    			};
    			var result = CSIT.syncCallService(url, content);
    			if (result.isSuccess) {
    	  			$("#list").datagrid('reload');
    			}else{
    				$.messager.alert("错误", result.message, "error");
    			}
    			$.messager.progress('close');
    		}
    	});
	},
	//自定义银行账户列表
	initOrgBankAccountList:function(){
    	$('#orgBankAccountList').datagrid({
    		pagination:true,
    		fit:true,
    		border:false,
    		height:1000,
    		ctrlSelect:true,
    		rownumbers:true,
    		columns:[[
    		    {field:'ck',checkbox:true},
    		    {field:'id',hidden:true},
				{field:'accountName',title:'账户名',width:100,align:'center'},
				{field:'bank',title:'开户行',width:100,align:'center'},
				{field:'accountNumber',title:'账号',width:100,align:'center'},
      	        {field:'status',title:'状态',width:100,align:'center',
      	        	formatter: function(value,row,index){
						if(value == 1){
							return '启用';
						}else{
							return '禁用';
						}
      	        	}
      	        },
      	      {field:'note',title:'备注',width:100,align:'center'},
       		]]
    	});
		 $('#orgBankAccountList').datagrid('resize',{   
		        height:1000
		 });  
    	var params = {
    	    	title:'配置银行账户',
    			width : 700,
    			height : document.documentElement.clientHeight-5<500?document.documentElement.clientHeight-5:500,
    			closed : true,
    			cache : false,
    			modal : true,
    		};
    	$("#orgBankAccountDialog").dialog(params);
    }
};

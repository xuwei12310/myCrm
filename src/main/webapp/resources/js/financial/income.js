var income = {

	initCRUDBtn : function() {
		// 添加
		$(app.id.addBtnId).click(function() {
			income.add();
		});
		// 修改
		$(app.id.updateBtnId).click(function() {
			income.update();
		});
		// 删除
		$(app.id.deleteBtnId).click(function() {
			income.mulDelete();
		});
		// 查询
		$(app.id.searchBtnId).click(function() {
			income.search();
		});
		// 清空查询条件
		$(app.id.clearBtnId).click(function() {
			$(app.id.searchFormId).form('clear');
		});
		//订单
		$("#orderCodeIdBtn").textbox({
            onClickIcon: function (index) {
                if (index == 0) {
                	$("#orderEditDialog").dialog('open');
                }
            }
        }); 
		//订单中搜索订单编号
		$("#searchOrderBtn").click(function(){
			$("#settlementAuditStatus").val(3);
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
		//编辑收入条目
		$("#updateIncomeBtn").click(function(){
			income.updateIncome();
		});
	},
	 // 两个浮点数求和  
	accAdd: function(num1,num2){  
       var r1,r2,m;  
       try{  
           r1 = num1.toString().split('.')[1].length;  
       }catch(e){  
           r1 = 0;  
       }  
       try{  
           r2=num2.toString().split(".")[1].length;  
       }catch(e){  
           r2=0;  
       }  
       m=Math.pow(10,Math.max(r1,r2));  
       // return (num1*m+num2*m)/m;  
       return Math.round(num1*m+num2*m)/m;  
    },
    // 两个浮点数相减  
    accSub:function(num1,num2){  
       var r1,r2,m;  
       try{  
           r1 = num1.toString().split('.')[1].length;  
       }catch(e){  
           r1 = 0;  
       }  
       try{  
           r2=num2.toString().split(".")[1].length;  
       }catch(e){  
           r2=0;  
       }  
       m=Math.pow(10,Math.max(r1,r2));  
       n=(r1>=r2)?r1:r2;  
       return (Math.round(num1*m-num2*m)/m).toFixed(n);  
    },
    //浮点数乘法
    accMul:function (arg1,arg2)  
    {  
        var m=0,s1=arg1.toString(),s2=arg2.toString();  
        try{m+=s1.split(".")[1].length}catch(e){}  
        try{m+=s2.split(".")[1].length}catch(e){}  
        return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)  
    },
    //浮点数除法
    accDiv:function(arg1,arg2){  
        var t1=0,t2=0,r1,r2;  
        try{t1=arg1.toString().split(".")[1].length}catch(e){}  
        try{t2=arg2.toString().split(".")[1].length}catch(e){}  
        with(Math){  
            r1=Number(arg1.toString().replace(".",""))  
            r2=Number(arg2.toString().replace(".",""))  
            return (r1/r2)*pow(10,t2-t1);  
        }  
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
	initIncomeList:function(){
		$("#incomeList").datagrid({
			fit : true,
			url:app.getUrl("getIncomeItemList"),
			border : false,
			toolbar : "#tb2",
			singleSelect : true,
			rownumbers : true,
			columns:[[
			          	{field:'name',title:'收入项',width:110,align:'center'},
			          	{field:'receiveAmount',title:'应收金额',width:85,align:'center'},
			          	{field:'receivedAmount',title:'已收金额',width:85,align:'center'},
			          	{field:'receivingAmount',title:'待收金额',width:85,align:'center'},
			          	{field:'pay',title:'本次收款',width:85,align:'center'},
			          	{field:'note',title:'备注',width:100,align:'center'},
			          ]],
			onDblClickRow : function() {
				$("#updateIncomeBtn").click();
			},
			onLoadSuccess:function(){
				var amount=$("#amount").val();
				if(amount!=null){
					$("input",$("#amount").next("span")).blur();
				}
			}
		});
	},
	initEditDialog : function() {
		var params = {
			title : '编辑' + resourceName,
			width : document.documentElement.clientWidtht-5<750?document.documentElement.clientWidtht-5:750,
					height : document.documentElement.clientHeight-5<650?document.documentElement.clientHeight-5:650,
			closed : true,
			cache : false,
			modal : true,
			resizable:true,
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
	initIncomeDialog : function() {
		var params = {
			title : '编辑',
			width : document.documentElement.clientWidtht-5<350?document.documentElement.clientWidtht-5:350,
					height : document.documentElement.clientHeight-5<250?document.documentElement.clientHeight-5:250,
			closed : true,
			cache : false,
			modal : true,
			buttons : [ {
				text : '保存',
				iconCls : 'icon-save',
				handler : function() {
						var selectedRow=$("#incomeList").datagrid('getSelected');
						var index=$("#incomeList").datagrid('getRowIndex',selectedRow);
						$("#incomeList").datagrid("updateRow",{
							index:$("#incomeList").datagrid('getRowIndex',selectedRow),
							row:{
								pay:$("#editPay").val(),
								note:$("#editNote").val()
							}
						});
					$("#incomeDialog").dialog('close')
				}
			}, {
				text : '退出',
				iconCls : 'icon-exit',
				handler : function() {
					$("#incomeDialog").dialog('close');
				}
			} ],
			onClose : function() {
				$("#incomeDialog").form('clear');
			}
		};
		$("#incomeDialog").dialog(params);
	},
	initEditForm : function() {
		$(app.id.editFormId).form({
			onSubmit : function() {
				var isValid = $(this).form('validate');
				if (!isValid) {
					$.messager.progress('close');
				};
				var amount=$("#amount").val();
				var calAmount=0;
				var rows=$("#incomeList").datagrid("getRows");
				var loanItem="";
				var incomeItem="";
				for(var i=0;i<rows.length;i++){
					//type=1是收入，2借款
					if(rows[i].type==1 && rows[i].id!=undefined){
						var calItem=rows[i].pay==null?0:rows[i].pay;
						calAmount=income.accAdd(calItem,calAmount);
						var id=rows[i].id==null?"":rows[i].id+"^";
						//var recevingAmount=rows[i].recevingAmount==null?"":rows[i].recevingAmount+"^";
						var pay=rows[i].pay==null?"":rows[i].pay+"^";
						var note=rows[i].note==null?"":rows[i].note+"^";
						incomeItem+=id;
						//如果是修改则fromId（订单借款或订单收入id）不为空
						if(rows[i].fromId!=null){
							var fromId=rows[i].fromId+"^";
							incomeItem+=fromId;
						}
						//incomeItem+=recevingAmount;
						incomeItem+=pay;
						incomeItem+=note;
						
						incomeItem+="_";
					}else if(rows[i].type==2){
						var calItem=rows[i].pay==null?0:rows[i].pay;
						calItem=income.accMul(calItem,10000);
						calAmount=income.accAdd(calItem,calAmount);
						var id=rows[i].id==null?"":rows[i].id+"^";
						//var recevingAmount=rows[i].recevingAmount==null?"":rows[i].recevingAmount+"^";
						var pay=rows[i].pay==null?"":rows[i].pay+"^";
						var note=rows[i].note==null?"":rows[i].note+"^";
						loanItem+=id;
						if(rows[i].fromId!=null){
							var fromId=rows[i].fromId+"^";
							loanItem+=fromId;
						}
						//loanItem+=recevingAmount;
						loanItem+=pay;
						loanItem+=note;
						loanItem+="_";
					}
				}
				if(amount<calAmount){
					$.messager.alert("提示","金额分配错误","warning");
					isValid=false;
				}
				$("#loanItem").val(loanItem);
				$("#incomeItem").val(incomeItem);
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
    	$('#orderList').datagrid({  
			  border:true,
			  url:app.getUrl("getOrderByList"),
			  queryParams:{'settlementAuditStatus':3},
			  rownumbers:true,
			  pagination:true,
			  fit:true,
			  toolbar:[{
				  text:'选择',
					iconCls:'icon-ok',
					handler:function(){
						var selectedRow = $('#orderList').datagrid('getSelected');
						if(selectedRow==null){
							$.messager.alert("提示","请选择一条记录","warning");
							return ;
						}
						$("#orderCodeIdBtn").textbox("setValue", selectedRow.orderCode);
				        $("#orderId").val(selectedRow.id);
				        $("#incomeList").datagrid({
				        	queryParams:{"orderId":selectedRow.id}
				        })
				        $("#orderEditDialog").dialog('close');
					}
				}],
	    	  ctrlSelect:true,
			  method:"POST",
		      columns:[[ 
		          {field:'id',hidden:true},
		          {field:'orderCode',title:'订单编号',width:100,align:'center'},
		          {field:'customerName',title:'客户',width:100,align:'center'},
		          {field:'csAssistantName',title:'客服助理',width:100,align:'center'},
	              {field:'csPrincipalName',title:'客服负责人',width:100,align:'center'},
	              {field:'followUserName',title:'跟单人',width:100,align:'center'},
	              {field:'ownerName',title:'拥有人',width:100,align:'center'},
	              {field:'createName',title:'创建人',width:100,align:'center'},
		   	  ]],
		   	  onDblClickCell: function(index,field,value){
		   		  var rows = $('#orderList').datagrid('getRows');
		   		  var selectedRow = rows[index];
		   		  $("#orderCodeIdBtn").textbox("setValue", selectedRow.orderCode);
		          $("#orderId").val(selectedRow.id);
		          $("#incomeList").datagrid({
			        	queryParams:{"orderId":selectedRow.id}
			        })
		          $("#orderEditDialog").dialog('close');
		   	  }
		});
    	var params = {
	    	title:'选择 订单',
			width : 900,
			height : document.documentElement.clientHeight-5<580?document.documentElement.clientHeight-5:580,
			closed : true,
			cache : false,
			modal : true,
		};
	    $("#orderEditDialog").dialog(params);
    },
	add : function() {
		$("#incomeList").datagrid({
			queryParams:{'orderId':""}
		});
		income.initIncomeList();
		$("#orderCodeIdBtn").textbox({disabled:false});
		 $("#amount").numberbox({disabled:false});
		 $(app.id.editDialogId).dialog({title:"添加"+resourceName});
		$(app.id.editDialogId).dialog('open');
		income.initEditForm();
		income.initChange();
		$("#status").combobox("setValue",1);
		$(app.id.editFormId).attr('action', app.url.create());
	},
	update : function() {
		var selectedRow = $(app.id.listId).datagrid('getSelected');
		if (selectedRow == null) {
			$.messager.alert('提示', '请选择数据行', 'warning');
			return;
		}
        $("#orderCodeIdBtn").textbox("setValue", selectedRow.orderCode);
        $("#orderId").val(selectedRow.orderId);
        $("#payTypeName").combobox("setValue", selectedRow.payTypeId);
        $("#periodTimeName").combobox("setValue", selectedRow.periodTimeId);
        $("#amount").numberbox({disabled:true});
        $("#orderCodeIdBtn").textbox({disabled:true});
        $(app.id.editDialogId).dialog({title:"编辑"+resourceName});
		$(app.id.editDialogId).dialog('open');
		income.initEditForm();
		$(app.id.editFormId).form('load',selectedRow)
		//重新初始化收入明细列表，重置onLoadSuccess事件
		$("#incomeList").datagrid({
			url:app.getUrl("getUpdateIncomeItemList"),
			queryParams:{'incomeId':selectedRow.id},
			fit : true,
			border : false,
			toolbar : "#tb2",
			singleSelect : true,
			rownumbers : true,
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50, 100 ],
			columns:[[
			          	{field:'name',title:'收入项',width:110,align:'center'},
			          	{field:'receiveAmount',title:'应收金额',width:85,align:'center'},
			          	{field:'receivedAmount',title:'已收金额',width:85,align:'center'},
			          	{field:'receivingAmount',title:'待收金额',width:85,align:'center'},
			          	{field:'pay',title:'本次收款',width:85,align:'center'},
			          	{field:'note',title:'备注',width:100,align:'center'},
			          ]],
			onDblClickRow : function() {
				$("#updateIncomeBtn").click();
			},
			onLoadSuccess:function(){
			}
		});
		income.initChange();
		$(app.id.editFormId).attr('action', app.url.update());
	},
	initChange:function(){
		//失去金额焦点，自动分配金额
		$("#amount").numberbox({onChange:function(newV,oldV) {
			//var amount = $("#amount").textbox("getValue");
			var amount=newV;
			var rows=$("#incomeList").datagrid("getRows")
			for (var i = 0; i < rows.length; i++) {
				$("#incomeList").datagrid("updateRow", {
					index : i,
					row : {
						pay : 0
					}
				});
				if (amount > 0) {
					if (rows[i].receivingAmount != null) {
						var pay = 0;
						//是回款
						if(rows[i].name.indexOf("回款")!=-1){
							var receivingAmount=income.accMul(rows[i].receivingAmount,10000);
							var small=income.accDiv(amount,10000);
							var a=parseFloat(amount);
							var b=parseFloat(receivingAmount);
							if (a>b) {
								amount = income.accSub(amount,receivingAmount);
								pay = rows[i].receivingAmount
							} else {
								pay = small;
								amount = 0;
							}
						//不是回款
						}else{
							var a=parseFloat(amount);
							var b=parseFloat(rows[i].receivingAmount);
							if (a>b) {
								amount = income.accSub(amount,rows[i].receivingAmount);
								pay = rows[i].receivingAmount
							} else {
								pay = amount;
								amount = 0;
							}
						}
						$("#incomeList").datagrid("updateRow", {
							index : i,
							row : {
								pay : pay
							}
						});
					}
				}
			}
		}});
	},
	updateIncome : function() {
		var selectedRow = $("#incomeList").datagrid('getSelected');
		if (selectedRow == null) {
			$.messager.alert('提示', '请选择数据行', 'warning');
			return;
		}
		$("#incomeDialog").dialog('open');
		$("#incomeForm").form('load', selectedRow);
		$("#costType").val(selectedRow.name)
		$("#incomeForm").attr('action',app.getUrl("updateItem"));
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
		income.initEditDialog();
		income.initCRUDBtn();
		income.initOrderEditDialog();
		income.initIncomeDialog();
		income.initViewList({
			columns:[[
	       	 	        {field:'id',hidden:true},
	       		        {field:'ck',checkbox:true},
						{field:'incomeTime',title:'登记时间',width:100,align:'center'},
						{field:'amount',title:'金额（元）',width:100,align:'center'},
						{field:'customerName',title:'客户',width:100,align:'center'},
						{field:'orderCode',title:'订单',width:100,align:'center'},
						{field:'ownerName',title:'拥有人',width:100,align:'center'},
						{field:'billing',title:'是否开票',width:100,align:'center',formatter: function(value,row,index){
	                        if(value == 1){
	                            return "是";
	                        }else{
	                            return "否";
	                        }},
						},
						{field:'payTypeName',title:'付款方式',width:100,align:'center'},
						{field:'periodTimeName',title:'期次',width:100,align:'center'},
						{field:'note',title:'备注',width:100,align:'center'}
						
	       		]]
		});
		app.payTypeCombobox($("#payTypeName"));
		app.periodTimeCombobox($("#periodTimeName"));
		app.yesOrNoByIntCombobox($("#billing"));
		
	}

};


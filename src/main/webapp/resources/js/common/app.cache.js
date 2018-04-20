var app={
	id:{
		listId:'#list',
		searchBtnId:'#searchBtn',
		searchFormId:'#searchForm',
		clearBtnId:'#clearBtn',
		
		editDialogId:'#editDialog',
		editFormId:'#editForm',
		
		toolbarId:'#tb',
		addBtnId:'#addBtn',
		updateBtnId:'#updateBtn',
		deleteBtnId:'#deleteBtn',
		
		//用于来自同一个jsp页面  多个查询  弹出选择框  已用在department 选择部门经理，部门主管的那个弹框 
		searchSelectBtnId:'#searchSelectBtn',
		clearSelectBtnId:'#clearSelectBtn',
		searchSelectFormId:'#searchSelectForm',
		selectListId:'#selectList',
		
		
		treeId:'#tree'
	},
	getNoPictureUrl:function () {
		return ctx+'/resources/images/noPicture.png';
    },
	getUrl:function (opt) {
		return ctx+resourcePath+'/'+opt+'.jhtml';
    },
	getTreeUrl:function (opt) {
        if(typeof(resourceTreePath) == "undefined"){
            return ctx+resourcePath+'/'+opt+'.jhtml';
		}
        return ctx+resourceTreePath+'/'+opt+'.jhtml';
    },
    getTemplateUrl:function (downloadTemplatePath,fileName) {
        return ctx + '/admin/common/downloadTemplate.jhtml?filePath=' + downloadTemplatePath + '&fileName=' + encodeURIComponent(encodeURIComponent(fileName));
    },
    //下载
  /*  getdownloadUrl:function (downloadTemplatePath,fileName) {
		return ctx+'/admin/common/downloadTemplate.jhtml?filePath='+downloadTemplatePath+'&fileName='+encodeURIComponent(encodeURIComponent(fileName));;
    },*/
    url: {
        datagrid: function () {
        	return app.getUrl('listByPage');
        },
        update: function () {
        	return app.getUrl('update');
        },
        create: function () {
        	return app.getUrl('create');
        },
        move: function () {
        	return app.getTreeUrl('move');
        },
        mulDelete: function () {
        	return app.getUrl('mulDelete');
        },
        treeRoot: function () {
        	return app.getTreeUrl('treeRoot');
		},
		treeChildren: function () {
			return app.getTreeUrl('treeChildren');
		},
		import: function () {
	        	return app.getUrl('import');
	    },
	    download: function () {
        	return app.getUrl('download');
        },
		listChildren: function () {
			return app.getUrl('findChildren');
		},
        updateStatus: function () {
        	return app.getUrl('updateStatus');
        }
    },
    initDialog:function(id,params){
    	var params = $.extend({
			width : 400,
			height : 200,
			closed : true,
			cache : false,
			modal : true
		},params);
    	$(id).dialog(params);
    },
    initViewList:function(params){
    	var params = $.extend({
    		url:app.url.datagrid(),
    		pagination:true,
    		fit:true,
    		border:false,
    		toolbar: app.id.toolbarId,
    		ctrlSelect:true,
    		rownumbers:true
    	},params);
    	$(app.id.listId).datagrid(params);
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
    }
};
//1男0女
app.sexCombobox = function(id){
    $(id).combobox({
        panelHeight: 'auto',
        editable:false,
        valueField: 'value',
        textField: 'text',
        data: [
            {value: '1',text: '男'},
            {value: '0',text: '女'}
        ]
    });
    $(id).combobox('clear');
};
//1男0女
app.reservationCombobox = function(id){
    $(id).combobox({
        panelHeight: 'auto',
        editable:false,
        valueField: 'value',
        textField: 'text',
        data: [
            {value: '1',text: '预定'},
            {value: '0',text: '不预定'}
        ]
    });
    $(id).combobox('clear');
};
//1发送0未发送
app.issendCombobox = function(id){
    $(id).combobox({
        panelHeight: 'auto',
        editable:false,
        valueField: 'value',
        textField: 'text',
        data: [
            {value: '1',text: '发送'},
            {value: '0',text: '未发送'}
        ]
    });
    $(id).combobox('clear');
};
//公司类型： 1:公司  0：部门
app.orgTypeCombobox = function(id){
	$(id).combobox({
        panelHeight: 'auto',
		  editable:false,
		  valueField: 'value',
		  textField: 'text',
		  data: [
		      {value: '1',text: '公司'},
		      {value: '0',text: '部门'}
		  ]
		});
	$(id).combobox('clear');
};
//1启动0禁止
app.enableCombobox = function(id){
	$(id).combobox({
        panelHeight: 'auto',
		  editable:false,
		  valueField: 'value',
		  textField: 'text',
		  data: [
		      {value: '1',text: '启用'},
		      {value: '0',text: '禁用'}
		  ]
		});
	$(id).combobox('clear');
};
//启用禁用
app.stateCombobox = function(id){
	 $(id).combobox({
		  editable:false,
		  valueField: 'value',
		  textField: 'text',
		  data: [
		      {value: '启用',text: '启用'},
		      {value: '禁用',text: '禁用'}
		  ]
	 });
};
//启用禁用
app.messageStatusCombobox = function(id){
	 $(id).combobox({
		  editable:false,
		  valueField: 'value',
		  textField: 'text',
		  data: [
		      {value: '1',text: '已读'},
		      {value: '0',text: '未读'}
		  ]
	 });
};
//消息类型
app.msgTypeCombobox = function(id){
	 $(id).combobox({
		  editable:false,
		  valueField: 'value',
		  textField: 'text',
		  data: [
		      {value: '1',text: '任务提前提醒'},
		      {value: '2',text: '任务延期提醒'}
		  ]
	 });
};
//佣金发放
app.brokerageGrantStateCombobox = function(id){
	 $(id).combobox({
		  editable:false,
		  valueField: 'value',
		  textField: 'text',
		  data: [
		      {value: '1',text: '已发放'},
		      {value: '0',text: '未发放'}
		  ]
	 });
};
//是否
app.yesOrNoCombobox = function(id){
	 $(id).combobox({
		  editable:false,
		  valueField: 'value',
		  textField: 'text',
		  data: [
		      {value: '是',text: '是'},
		      {value: '否',text: '否'}
		  ]
	 });
};
//是否
app.yesOrNoByIntCombobox = function(id){
    $(id).combobox({
        panelHeight: 'auto',
        editable:false,
        valueField: 'value',
        textField: 'text',
        data: [
            {value: '1',text: '是'},
            {value: '0',text: '否'}
        ]
    });
};
//资源类型
app.resourcesTypeCombobox = function(id){
	 $(id).combobox({
		 panelHeight: 'auto',
		  editable:false,
		  valueField: 'value',
		  textField: 'text',
		  data: [
		      {value: '菜单',text: '菜单'},
		      {value: '权限',text: '权限'}
		  ]
	 });
};
//客户类型
app.customerTypeCombobox = function(id){
    $(id).combobox({
        panelHeight: 'auto',
        editable:false,
        valueField: 'value',
        textField: 'text',
        data: [
            {value: '1',text: '个人'},
            {value: '2',text: '企业'}
        ]
    });
};
//执行角色
app.roleCombobox = function(id){
    $(id).combobox({
        panelHeight: 'auto',
        editable:false,
        valueField: 'value',
        textField: 'text',
        data: [
            {value: '1',text: '拥有人'},
            {value: '2',text: '跟单人'},
            {value: '3',text: '客服负责人'},
            {value: '4',text: '客服助理'}
        ]
    });
};
//提醒单位
app.remindUnitCombobox = function(id){
    $(id).combobox({
        panelHeight: 'auto',
        editable:false,
        valueField: 'value',
        textField: 'text',
        data: [
            {value: '1',text: '天'},
            {value: '2',text: '小时'},
        ]
    });
};
//提醒
app.remind = function(id){
	 $(id).combobox({
		 	panelHeight: 'auto',
	        editable:false,
	        valueField: 'value',
	        textField: 'text',
		  //listHeight: '25px',
		  data: [
		      {value: '1',text: '天'},
		      {value: '2',text: '小时'}
		  ]
	});
};
//跟进方式
app.followTypeCombobox=function(id){
	$(id).combobox({
		panelHeight:'auto',
		editable:false,
		valueField:'id',
		textField:'name',
		url:ctx+"/dict/followType/followTypeCombobox.jhtml"
	})
};
//付款方式
app.payTypeCombobox=function(id){
	$(id).combobox({
		panelHeight:'auto',
		editable:false,
		valueField:'id',
		textField:'name',
		url:ctx+"/dict/payType/payTypeCombobox.jhtml"
	})
};
//期次
app.periodTimeCombobox=function(id){
	$(id).combobox({
		panelHeight:'auto',
		editable:false,
		valueField:'id',
		textField:'name',
		url:ctx+"/dict/periodTime/periodTimeCombobox.jhtml"
	})
};
//跟进阶段
app.followStageCombobox=function(id){
	$(id).combobox({
		panelHeight:'auto',
		editable:false,
		valueField:'id',
		textField:'name',
		url:ctx+"/dict/followStage/followStageCombobox.jhtml"
	})
};
//客户状态
app.customerStatusCombobox=function(id){
	$(id).combobox({
		panelHeight:'auto',
		editable:false,
		valueField:'id',
		textField:'name',
		url:ctx+"/dict/customerStatus/customerStatusCombobox.jhtml"
	})
};
//费用类型
app.costTypeCombobox=function(id){
	$(id).combobox({
		panelHeight:'auto',
		editable:false,
		valueField:'id',
		textField:'name',
		url:ctx+"/dict/costType/costTypeCombobox.jhtml"
	})
};
//修改状态
 app.onMulUpdateStatus = function(status) {
	var rows = $(app.id.listId).datagrid('getChecked');
	if (rows.length == 0) {
		$.messager.alert("警告", "请选择要" + status + "的数据行", "warning");
		return;
	}
	var idArray = [];
	for (var i = 0; i < rows.length; i++) {
		idArray.push(rows[i].id);
	}
	$.messager.confirm("提示", "确定要" + status + "记录?", function(t) {
		if (t) {
			$.messager.progress();
			var url = app.getUrl('mulUpdateStatus');
			var content = {
				ids : idArray.join(CSIT.join),
				status : status
			};
			var result = CSIT.syncCallService(url, content);
			if (result.isSuccess) {
				for (var i = 0; i < rows.length; i++) {
					var updateRow = rows[i];// 修改状态 不走服务器
					var updateRowIndex = $(app.id.listId).datagrid(
							'getRowIndex', updateRow);
					updateRow.status = status;
					$(app.id.listId).datagrid('updateRow', {
						index : updateRowIndex,
						row : updateRow
					});
					$(app.id.listId)
							.datagrid('unselectRow', updateRowIndex);
				}
			} else {
				$.messager.alert("错误", result.message, "error");
			}
			$.messager.progress('close');
			$(app.id.listId).datagrid('reload');
		}
	});
};
 
 
 
 app.onMulUpdateByUrl = function(status,url,param) {
	var rows = $(app.id.listId).datagrid('getChecked');
	if (rows.length == 0) {
		$.messager.alert("警告", "请选择要" + status + "的数据行", "warning");
		return;
	}
	var idArray = [];
	for (var i = 0; i < rows.length; i++) {
		idArray.push(rows[i].id);
	}
	var url=url;
	var param = param;
	$.messager.confirm("提示", "确定要" + status + "记录?", function(t) {
		if (t) {
			$.messager.progress();
			var content = {
				ids : idArray.join(CSIT.join),
				param : param
			};
			var result = CSIT.syncCallService(url, content);
			if (result.isSuccess) {
				for (var i = 0; i < rows.length; i++) {
					var updateRow = rows[i];// 修改状态 不走服务器
					var updateRowIndex = $(app.id.listId).datagrid(
							'getRowIndex', updateRow);
					updateRow.status = status;
					$(app.id.listId).datagrid('updateRow', {
						index : updateRowIndex,
						row : updateRow
					});
					$(app.id.listId)
							.datagrid('unselectRow', updateRowIndex);
				}
			} else {
				$.messager.alert("错误", result.message, "error");
			}
			$.messager.progress('close');
			$(app.id.listId).datagrid('reload');
		}
	});
};
 
//初始化combobox框 返回结果只需要id(id字段固定)和一个名称(字段不限)
app.initCombobox =	function(id,url,param){
		var result = CSIT.syncCallService(url,param);
		var data = [];
		for(var p in result){
		    item={};
			for(var a in result[p]){
		    	if(a=='id'){
		    		item["value"]=result[p][a];
		    	}else{
		    		item["text"]=result[p][a];
		    	}
		    }
		    data.push(item);
		}
		$(id).combobox({
			panelHeight: 'auto',
		  	editable:false,
		  	valueField: 'value',
		  	textField: 'text',
		  	data: data
		});
	};
//打开主界面导出Excel对话框
app.showExportDialogMain=function(action,viewList,defaultTitleExport,selectedIds){
		$('#exportFormMain').attr('action',action);
		$('#selectedIds').val(selectedIds);
		if(defaultTitleExport!=undefined&&defaultTitleExport!=null&&defaultTitleExport!=''){
			$('#defaultTitleExport').val(defaultTitleExport);
			$('#titleExport').val(defaultTitleExport);
		}
		//导出字段
		var exportColumnsHtml =''; 
		var columns = $(viewList).datagrid('getColumnFields');
		var frozenColumns = $(viewList).datagrid('getColumnFields',true);
		for ( var column in frozenColumns) {
			var columnOption =  $(viewList).datagrid('getColumnOption',frozenColumns[column]);
			if(columnOption.title!=undefined){
				var title = columnOption.title.replace("<br>","");
				exportColumnsHtml+= '<li style="width:155px;"><input type="checkbox" field="'+columnOption.field+'" id="'+columnOption.field+'Main" checked="checked" fieldName="'+title+'" style="vertical-align:middle"><label for="'+columnOption.field+'Main">&nbsp;&nbsp;'+title+"</label></li>";
			}
		}
		for ( var column in columns) {
			var columnOption =  $(viewList).datagrid('getColumnOption',columns[column]);
			if(columnOption.title!=undefined){
				var title = columnOption.title.replace("<br>","");
				if(columnOption.hidden){
					exportColumnsHtml+= '<li style="width:155px;"> <input type="checkbox" field="'+columnOption.field+'" id="'+columnOption.field+'Main" fieldName="'+title+'" style="vertical-align:middle"><label for="'+columnOption.field+'Main">&nbsp;&nbsp;'+title+"</label></li>";
				}else{
					exportColumnsHtml+= '<li style="width:155px;"> <input type="checkbox" field="'+columnOption.field+'" id="'+columnOption.field+'Main" checked="checked" fieldName="'+title+'" style="vertical-align:middle"><label for="'+columnOption.field+'Main">&nbsp;&nbsp;'+title+"</label></li>";
				}
			}
		}
		$('#exportColumnsMain').html(exportColumnsHtml);
		//导出参数
		var exportParams = $(viewList).datagrid('options').queryParams;
		var exportParamsDivHtml = '';
		for ( var exportParam in exportParams) {
			var temp = exportParams[exportParam];
			if(temp==null){
				temp = "";
			}
			exportParamsDivHtml+='<input type="hidden" name="'+exportParam+'" value="'+ temp +'">';
		}
		$('#exportParamsDiv').html(exportParamsDivHtml);
		$('#exportDialogMain').dialog('open');
	};

app.onExportOK=function(){
	var fieldArray = [];
	var fieldNameArray = [];
	$('#exportColumnsMain :checkbox:checked').each(function(){
		var field = $(this).attr('field');
		var fieldName = $(this).attr('fieldName');
		fieldArray.push(field);
		fieldNameArray.push(fieldName);
	});
	if(fieldArray.length==0){
		$.messager.alert('警告','请至少选择一列导出','warning');

	}else{
		$('#fieldsMain').val(fieldArray.join(CSIT.join));
		$('#fieldNamesMain').val(fieldNameArray.join(CSIT.join));
		var titleExport = $('#titleExport').val();
		var defaultTitleExport = $('#defaultTitleExport').val();
		if(titleExport==''&&defaultTitleExport!=''){
			$('#titleExport').val(defaultTitleExport);
		}
		$('#submitBtn').click();
		$('#exportDialogMain').dialog('close');
	}
};

//dialog拖动
$(".move").add(".window-shadow").mousedown(function(e)//e鼠标事件
{

    var offset = $(this).offset();//DIV在页面的位置
    var x = e.pageX - offset.left;//获得鼠标指针离DIV元素左边界的距离
    var y = e.pageY - offset.top;//获得鼠标指针离DIV元素上边界的距离
    $(document).bind("mousemove",function(ev)//绑定鼠标的移动事件，因为光标在DIV元素外面也要有效果，所以要用doucment的事件，而不用DIV元素的事件
    {
        $(".window").add(".window-shadow").stop();//加上这个之后

        var _x = ev.pageX - x;//获得X轴方向移动的值
        var _y = ev.pageY - y;//获得Y轴方向移动的值

        //在该DIV的范围内拖动
        var pElement = $("body"),
            pWidth = pElement.width(),
            pHeight = pElement.height();
        //移动的div，以window为例
        var element = $(".window"),
            eWidth = element.width(),
            eHeight = element.height();
        if(_x+eWidth>pWidth){
            _x=pWidth-eWidth;
        }
        if(_x<0){
            _x=0;
        }
        if(_y<0){
            _y=0;
        }
        if(_y+eHeight>pHeight){
            _y=pHeight-eHeight;
        }
        $(".window").add(".window-shadow").animate({left:_x+"px",top:_y+"px"},10);
    });
});
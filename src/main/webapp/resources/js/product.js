var detailEditor=null;
var product = {
	   initKindEditorDialog:function(){
	    	if(detailEditor==null){
	    		detailEditor = KindEditor.create('#details', {
					resizeType:0,
					//allowImageUpload: true, //上传图片框本地上传的功能，false为隐藏，默认为true
				   // allowImageRemote : false, //上传图片框网络图片的功能，false为隐藏，默认为true
					width:'100%',
					height:80,
//						cssPath:ctx+"/resources/css/img.css",
					items:[
					       	'source', '|', 'preview', 'undo', 'redo', 'cut', 'copy', 'paste',
					       	'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
					       	'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
					       	'superscript', 'clearhtml', 'quickformat', 
					       	'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
					       	'italic', 'underline', 'lineheight', 'removeformat',
					       	'table', 'link', 'unlink', 'fullscreen'
				    ],
				   // uploadJson : ctx+'/admin/sys/meeting/uploadImgKindeditor.jhtml'
				});
			}
	    },
	    init: function (params) {
	    	product.initKindEditorDialog();
	    	app.crud.initEditDialog({
	    		width:document.documentElement.clientWidth-5<550?document.documentElement.clientWidth-5:550,
	    	    height : document.documentElement.clientHeight-5<650?document.documentElement.clientHeight-5:650,
	    		buttons : [ {
					text : '保存',
					iconCls : 'icon-save',
					handler : function() {
						$("#details").val(detailEditor.html());
						$(app.id.editFormId).submit();
					}
				}, {
					text : '退出',
					iconCls : 'icon-exit',
					handler : function() {
						$(app.id.editDialogId).dialog('close');
						$(app.id.editFormId).form('clear');
						detailEditor.html("");
					}
				} ],
	    	});
	    	app.crud.initCRUDBtn({
        	add:function(){
	        		var date=new Date();
	        		var year=date.getFullYear();
	        		var month=date.getMonth()+1;
	        		//获取当前日
	        		var day=date.getDate(); 
	        		$("#createTime").val(year+"-"+month+"-"+day);
	        	},
	        	update:function(){
	        		var selectedRow=$(app.id.listId).datagrid("getSelected")
	        		detailEditor.html(selectedRow.details);
	        	}
	    	});
		    app.crud.initViewList({
	       	 	columns:[[
	       		        {field:'ck',checkbox:true},
						{field:'productName',title:'产品名',width:150,align:'center'},
						{field:'price',title:'售价',width:100,align:'center'},
						{field:'createTime',title:'发行时间',width:120,align:'center'},
						{field:'details',title:'产品详情',width:300,align:'center'},
						{field:'id',hidden:true}
	       		]]
	    	});
    }
}

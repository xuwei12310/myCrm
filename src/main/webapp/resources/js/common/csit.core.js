var CSIT={
	serializeObject : function() { 
	    var o = {}; 
	    var fields  = this.serializeArray();
	    $.each(fields, function() { 
            o[this.name] = this.value || ''; 
	    }); 
	    return o; 
	}
};
//添加Cookie
CSIT.addCookie = function (name, value, options) {
	if (arguments.length > 1 && name != null) {
		if (options == null) {
			options = {};
		}
		if (value == null) {
			options.expires = -1;
		}
		if (typeof options.expires == "number") {
			var time = options.expires;
			var expires = options.expires = new Date();
			expires.setTime(expires.getTime() + time * 1000);
		}
		document.cookie = encodeURIComponent(String(name)) + "=" + encodeURIComponent(String(value)) + (options.expires ? "; expires=" + options.expires.toUTCString() : "") + (options.path ? "; path=" + options.path : "") + (options.domain ? "; domain=" + options.domain : ""), (options.secure ? "; secure" : "");
	}
};
//获取Cookie
CSIT.getCookie=function (name) {
	if (name != null) {
		var value = new RegExp("(?:^|; )" + encodeURIComponent(String(name)) + "=([^;]*)").exec(document.cookie);
		return value ? decodeURIComponent(value[1]) : null;
	}
};
// 移除Cookie
CSIT.removeCookie=function (name, options) {
	CSIT.addCookie(name, null, options);
};
CSIT.id_index = 0;
CSIT.genId = function () {
    return 'CSIT_UI_' + (CSIT.id_index++);
};
CSIT.id={
	listId:'#list',
	
	editDialogId:'#editDialog',
	editFormId:'#editForm',
	
	toolbarId:'#tb',
	addBtnId:'#addBtn',
	updateBtnId:'#updateBtn',
	deleteBtnId:'#deleteBtn',
	
	searchBtnId:'#searchBtn',
	searchFormId:'#searchForm',
	clearBtnId:'#clearBtn'
};
CSIT.join ='^'; 
CSIT.empty_row = {rows:[]};
CSIT.empty_row_total = {rows:[],total:1};
CSIT.ClearUrl='common/getEmptyJsonCommon.do';
CSIT.ClearData ={rows:[],total:0};
CSIT.ClearDataWithoutTotal ={rows:[]};
CSIT.ClearCombobxData = [];
CSIT.currTabResourcesId = null;
CSIT.tempId = null;
CSIT.tempId2 = null;
CSIT.showSize = function(size){	
	var mb = 1024 * 1024;
	var kb = 1024;		
	var temp2;
	if( size >= mb ){			
		temp2 = (size / mb) + "";			
		if(temp2.indexOf(".") > 0){				
			temp2 = temp2.substring(0, (temp2.indexOf(".") + 3));
		}	
		return  temp2 + " MB";
	}else if( size >= kb ){			
		temp2 = (size / kb)+"";			
		if(temp2.indexOf(".")>0){
			temp2 = temp2.substring(0, (temp2.indexOf(".") + 3));
		}
		return  temp2 + " KB";
	}else{
		return size + " B";
	}
};

/**
 * 寻父归类
 * @param data
 * @param name
 * @returns {Array}
 */
CSIT.findPid = function (data,name) {
    for (i in data) {
        if (data[i].parentId > 0) {
            for (j in data) {
                if (data[j].id == data[i].parentId) {
                    if (data[j].children == null) {
                        data[j].children = [];
                    }
                    data[j].children.push(data[i]);
                }
            }
        }
    }
    //过滤已经子类
    var rdata = [];
    for (i in data) {
        data[i].text=data[i][name];
        if (data[i].parentId == null || data[i].parentId == 0) {
            rdata.push(data[i]);
        }
    }
    return rdata;
};

/**
 * 同步ajax调用
 * type为可选参数,可传入'json'，返回json格式的对象
 */
 CSIT.syncCallService = function syncCallService(url,data,type){
	var retData;
	$.ajax({
		type : "POST",
		url  : url,
		data : data,
		async : false,
		cache : false,
		error : function(){
			alert("网络连接失败，请重新加载本页面。。。!");
            //top.location=ctx;
	    },
		success:function(data){
    		retData =  data;
		}
	});
	if ( (type == "json"||type==null)&&(typeof retData == "string")&&retData!=undefined && retData!=''){
		retData = eval("(" + retData + ")");
	}
	return retData;
};
/**
 * 异步ajax调用
 * type为可选参数,可传入'json'，返回json格式的对象
 */
 CSIT.asyncCallService = function asyncCallService(url,data,callback, type){
	if ( $.isFunction( data ) ) {
		callback = data;
		data = {};
	}
	$.ajax({
		type : "POST",
		url  : url,
		data : data,
		async : true,
		cache : false,
		error : function(){
			alert("网络连接失败，请重新加载本页面。。。!");
            //top.location=ctx;
	    },
		success:function(data){
			if ( (type == "json"||type==null)&&(typeof data == "string")&&data!=undefined && data!=''){
				data = eval("(" + data + ")");
			}
			callback(data);
		}
	});
};
 
CSIT.matchUserPwd = function(userPwd){
	var r, re; // 声明变量。
	re = new RegExp("^(?!^[0-9]+$)(?!^[a-zA-Z]+$).{6,}$","g"); // 创建正则表达式对象。
	r = re.test(userPwd); // 在字符串 s 中查找匹配。
	return(r);
};
//处理键盘事件 禁止后退键（Backspace）密码或单行、多行文本框除外
function banBackSpace(e){
    var ev = e || window.event;//获取event对象
    var obj = ev.target || ev.srcElement;//获取事件源
    var t = obj.type || obj.getAttribute('type');//获取事件源类型
    //获取作为判断条件的事件类型
    var vReadOnly = obj.readOnly;
    var vDisabled = obj.disabled;
    //处理undefined值情况
    vReadOnly = (vReadOnly == undefined) ? false : vReadOnly;
    vDisabled = (vDisabled == undefined) ? true : vDisabled;
    //当敲Backspace键时，事件源类型为密码或单行、多行文本的，
    //并且readOnly属性为true或disabled属性为true的，则退格键失效
    var flag1= ev.keyCode == 8 && (t=="password" || t=="text" || t=="textarea" || t=="number" || t=="search")&& (vReadOnly==true || vDisabled==true);
    //当敲Backspace键时，事件源类型非密码或单行、多行文本的，则退格键失效
    var flag2= ev.keyCode == 8 && t != "password" && t != "text" && t != "textarea" && t!="number" && t!="search";
    //判断
    if(flag2 || flag1)return false;
}
//禁止退格键 作用于Firefox、Opera
document.onkeypress=banBackSpace;
//禁止退格键 作用于IE、Chrome
document.onkeydown=banBackSpace;

var getRights = function(rightId){
	var url = 'system/queryChildrenRightByKindTeacher.do';
	var content ={rightId:rightId,kind:2};
	return syncCallService(url,content);
};

var getReportRights = function(rightId){
	var url = 'system/queryChildrenReportRightTeacher.do';
	var content ={rightId:rightId};
	return syncCallService(url,content);
};

var checkRight = function(checkArray,result){
	$(checkArray).each(function(index,item){
		$(item).hide();
	});
	for ( var i = 0; i < result.length; i++) {
		$(checkArray).each(function(index,item){
			var btnText = $.trim($(item).text());
			if($.trim(btnText)==$.trim(result[i].rightName)){
				$(item).show();

			}
		});
	}
};


var getReportRightId = function(rightIds){
	for ( var i = 0; i < rightIds.length; i++) {
		if('报表'==$.trim(rightIds[i].text)){
			return rightIds[i].id;
		}
	}
};

var getRightId = function(rightIds,rightId){
	for ( var i = 0; i < rightIds.length; i++) {
		if(rightId==$.trim(rightIds[i].rightId)){
			return rightIds[i];
		}
	}
};

var getJsonLength =  function(jsonData){
	var jsonLength = 0;
	for(var item in jsonData){
		jsonLength++;
	}
	return jsonLength;
};
//格式化日期字符串，yyyy/MM/dd ——> yyyy年MM月dd日
CSIT.formatDateStr = function(dateStr,pattern){
	var year = dateStr.substring(0,4);
	var month = dateStr.substring(5,7);
	var day = dateStr.substring(8,10);
	var newDate = null;
	if('yM_Chinese'==pattern){
		newDate = year+'年'+month+'月';
	}else if('yMd_Chinese'==pattern){
		newDate = year+'年'+month+'月'+day+'日';
	}else if('y'==pattern){
		newDate = year;
	}
	return newDate;
};



(function () {
    var oneRender = function () {
        var athis = $(this);
        var uistr = athis.attr('ui');
//        athis.removeAttr('ui');
        athis.removeClass('cui');
        var uis;
        if (uistr.indexOf('[') == 0)
            try {
                eval('uis=' + uistr + ';');
            } catch (e) {
            }
        else uis = uistr;
        if (typeof(uis) == 'string')uis = [uis];

        $(uis).each(function () {
            var uiName = this;
            var js = uiName;
            if (js.indexOf('Init') == js.length - 4) {
                js = js.substring(0, js.indexOf('Init'));
            }
            var jsPathArray = js.split('_');
            uiName =jsPathArray[0]+'Init';
            if (!athis.attr('c-init-' + uiName)) {
                var optStr = athis.attr(uiName + '-options');
                athis.removeAttr(uiName + '-options');
                if (optStr) {
                    var opts = {};
                    try {
                        var x = 'opts=' + optStr + ';';
                        eval(x);
                    } catch (e) {
                        console.log('error:' + uiName + '-options=' + optStr);
                    }
                }
                if ($.fn[uiName]) {
                    athis[uiName](opts);
                }else if (uiName.indexOf('Init') == uiName.length - 4) {
                    var jsPath = '';
                    for ( var i = 1; i < jsPathArray.length; i++) {
                    	jsPath+='/'+jsPathArray[i];
					}
                    js = 'js/plugins'+jsPath +"/"+ jsPathArray[0] + 'Plugin.js?t=' + new Date().getTime();
                    $.xLazyLoader({
                        js:js,
                        success:function () {
                            athis[uiName](opts);
                        }
                    });
                }
            }
            athis.attr('c-init-' + uiName);
        });
    };
    CSIT.initContent = function (els) {
        return $(els).each(function () {
            var el = $(this);
            if (el.hasClass('cui') || el.attr('ui')){
                oneRender.apply(el, []);
            }
            $('.cui,[ui]', el).each(function () {
                oneRender.apply(this, []);
            });
            return el;
        });
    };
 // 省
    CSIT.provinceList = null;
    CSIT.getProvinceList =  function(){
    	var url = '/myCrm1/dict/province/getAllProvince.jhtml';
    	var data = CSIT.syncCallService(url,null,'json');
    	if(data!=null){
    		CSIT.provinceList = data;
    	}
    	return CSIT.provinceList;
    };
    // 市
    CSIT.cityList = null;
    CSIT.getCityList =  function(provinceId){
    	var url = '/myCrm1/dict/city/getAllCity.jhtml';
    	var content = {provinceId:provinceId};
    	var data = CSIT.syncCallService(url,content,'json');
    	if(data!=null){
    		CSIT.cityList = data;
    	}
    	return CSIT.cityList;
    };
    // 区/县
   /* CSIT.areasList = null;
    CSIT.getAreasList = function(cityId){
    	var url = 'sydq/queryComboboxArea.do';
    	var content = {cityId:cityId};
    	var data = CSIT.syncCallService(url,content,'json');
    	if(data!=null){
    		GSS.areaList = data;
    	}
    	return GSS.areaList;
    }*/
    
    //取得url参数中的name的值
    CSIT.getQuery = function(name) { 
        var searchStr = window.location.search.substr(1); 
        var arrTmp = searchStr.split("&"); 
    	for(var i = 0;i< arrTmp.length;i++){ 
    		var arrTemp = arrTmp[i].split("="); 
    		if(arrTemp[0].toUpperCase()==name.toUpperCase()){
    			return arrTemp[1]; 
    		}
    	} 
    };
    //===============================自定义查询================================
    CSIT.parentheses= [
        {"id":"","text":""},
        {"id":"(","text":"("},
        {"id":")","text":")"}
    ];
    CSIT.operations  = [
        {"id":"=","text":"等于"},
        {"id":"<>","text":"不等于"},
        {"id":">","text":"大于"},
        {"id":">=","text":"大于等于"},
        {"id":"<","text":"小于"},
        {"id":"<=","text":"小于等于"},
        {"id":"likeL","text":"开头是"},
        {"id":"likeR","text":"结尾是"},
        {"id":"like","text":"包含"},
        {"id":"not like","text":"不包含"}
    ];
    CSIT.logicals = [
        {"id":"","text":""},
        {"id":"and","text":"且"},
        {"id":"or","text":"或"}
    ]; 
    /*
     * linkebuttonId - 自定义查询按钮Id
     * customFormId - 自定义查询表单Id
     * customDatagridId - 自定义查询条件列表Id
     * formId - 常规查询表单Id
     * datagridId - 数据列表Id
     * page - 当前页面
     * typeCheckedId 辅导员状态类型查询Id(特殊)
     */
    var oldHeight;//用于存放原高度
    CSIT.initCustomQuery = function(linkebuttonId,customFormId,customDatagridId,formId,datagridId,page,intPropertyNames,intPropertyObjs,defaultParams,typeCheckedId){
    	var oldText = $(linkebuttonId).linkbutton('options').text;
    	if(defaultParams==undefined){
    		defaultParams = {};
    	}
//		if(oldText=='常规查询'){
//			$(linkebuttonId).linkbutton({    
//				text:'自定义查询'  
//			});  
//			$(customFormId).hide();
//			$(formId).show();
//			$(page).layout('panel','north').panel('resize',{
//				height:oldHeight
//			});
//			$(page).layout('resize');  
//			$(customDatagridId).datagrid('loadData',CSIT.empty_row);
//		}else 
		if(oldText=='自定义查询'){
			//如果已经在这个页面，点击无效
			if($(customFormId).is(":visible")){
				return false;
			}
			//存储原高度
			oldHeight = $(page).layout('panel','north').panel('options').height;
//			$(linkebuttonId).linkbutton({    
//				text:'常规查询'  
//			});
			$(page).layout('panel','north').panel('resize',{
				height:220
			});
			$(page).layout('resize');
			$(customFormId).show();
			$(formId).hide();

			var fields = [];
			var frozenColumns = $(datagridId).datagrid('getColumnFields',true);
			var columns = $(datagridId).datagrid('getColumnFields');
			for(var column in frozenColumns){
				var columnOption =  $(datagridId).datagrid('getColumnOption',frozenColumns[column]);
				if(columnOption.field!=undefined&&columnOption.title!=undefined){
					var field = {};
					field.id = 'a.'+columnOption.field;
					field.text = columnOption.title;
					fields.push(field);
				}
			}
			for(var column in columns) {
				var columnOption =  $(datagridId).datagrid('getColumnOption',columns[column]);
				if(columnOption.field!=undefined&&columnOption.title!=undefined){
					var field = {};
					field.id = 'a.'+columnOption.field;
					field.text = columnOption.title;
					fields.push(field);
				}
			}
			var insertCustomRow = function(){
				$(customDatagridId).datagrid('appendRow', {
  					leftP: '',
  					field: '',
  					operation: '',
  					key: '',
  					rightP: '',
  					logical: ''
  				});
  				var rows = $(customDatagridId).datagrid('getRows');
  				$(customDatagridId).datagrid('beginEdit',rows.length-1);
			};
			var removeCustomRow = function(){
				var rows = $(customDatagridId).datagrid('getChecked');
				if(rows.length==0){
					 $.messager.alert('警告',"请选中要删除的记录","warning");
					 return;
				}
				for ( var i = 0; i < rows.length; i++) {
					var rowIndex = $(customDatagridId).datagrid('getRowIndex',rows[i]);
					$(customDatagridId).datagrid('deleteRow',rowIndex);
				}
			};
			var onCustomSearch = function(){
				var rows = $(customDatagridId).datagrid('getRows');
				var customSql = "  ";
				var intProperties = {};
				if(intPropertyNames!=undefined){
					for(var i=0;i<intPropertyNames.length;i++){
						intProperties[intPropertyNames[i]] = intPropertyObjs[i];
					}
				}
				
				for (var i = 0; i < rows.length; i++) {
					var isValid = $(customDatagridId).datagrid('validateRow',i);
					if(!isValid){
						$.messager.alert("警告","请设置必填项","warning");
						return;
					}
					
					var p1Editor = $(customDatagridId).datagrid('getEditor', {index:i,field:'p1'});
					var p1 = $(p1Editor.target).combobox('getValue');
					customSql += " " + p1; 
					
					var fieldEditor = $(customDatagridId).datagrid('getEditor', {index:i,field:'field'});
					var isValidComboboxsArray = [];
					isValidComboboxsArray.push({0:$(fieldEditor.target),1:'字段'});
					if(!isValidComboboxs(isValidComboboxsArray)){
						return;
					}
					var field = $(fieldEditor.target).combobox('getValue');
					customSql += field;
					
					var operationEditor = $(customDatagridId).datagrid('getEditor', {index:i,field:'operation'});
					var operation = $(operationEditor.target).combobox('getValue');
					
					var keyEditor = $(customDatagridId).datagrid('getEditor', {index:i,field:'key'});
					var key = $(keyEditor.target).val();
					//如果是int类型数据，则需要转化
					if(intProperties[field]!=undefined){
						if(intProperties[field][key]!=undefined){
							key = intProperties[field][key];
						}else{
							if(intProperties[field]["非法"]==undefined){
								key = -999;
							}else{
								key=intProperties[field]["非法"];
							}
						}
					}
					
					if (operation == "likeL") {
		                customSql += " like '" + key + "%'";
		            } else if (operation == "likeR") {
		                customSql += " like '%" + key + "'"; 
		            } else if (operation == "like" || operation == "not like") {
		                customSql += " " + operation + " '%" + key + "%'";
		            } else {
		                customSql += " " + operation + " '" + key + "'";
		            }
					
					var p2Editor = $(customDatagridId).datagrid('getEditor', {index:i,field:'p2'});
					var p2 = $(p2Editor.target).combobox('getValue');
					customSql += p2; 
					
					var logicalEditor = $(customDatagridId).datagrid('getEditor', {index:i,field:'logical'});
					var logical = $(logicalEditor.target).combobox('getValue');
					
					if (i != rows.length - 1 && logical == "") {
						var fn = function(){
							$(logicalEditor.target).combobox('showPanel');
						};
						$.messager.alert("警告","请设置关系符","warning",fn);
						return;
				     }
					customSql += " " + logical;
				}
				
				
				
				while (customSql.indexOf("  ") >= 0) {
					customSql = customSql.replace("  ", " ");
				}
				customSql = $.trim(customSql);
				if (customSql.split("(").length != customSql.split(")").length) {
					$.messager.alert("警告","请检查对应的括号.","warning");
					return; 
				}
				var typeChecked = [];
				var idsChecked = null;
				var ui = $(page).attr('ui');
				if('counselor_personnelInit'==ui){//辅导员管理页面
					 $(":checkbox[name=teaStatusType]:checked",page).each(function(){
						  var val = $(this).attr('value');
						  typeChecked.push(val);
					  });
					  if(typeChecked == ''){
						  typeChecked.push('3');
					  }
					  idsChecked=typeChecked.join(',');
					  if(idsChecked!=null){
						
							$.extend(defaultParams,{typeChecked:idsChecked});
					  }
				}
				
				if(customSql==''){
					customSql="  1 = 1 ";
				}
				
				$.extend(defaultParams,{customSql:customSql});
				$(datagridId).datagrid({
					queryParams:defaultParams
				});
			};
			
			var onFixedQuery = function(){
				$(customFormId).hide();
				$(formId).show();
				$(page).layout('panel','north').panel('resize',{
					height:oldHeight
				});
				$(page).layout('resize');  
				$(customDatagridId).datagrid('loadData',CSIT.empty_row);
			};
			
			$(customDatagridId).datagrid({
				width:1000,
				ctrlSelect:true,
				method:"POST",
	  		  	nowrap:true,
	  		  	striped: true,
	  		  	collapsible:true,
	  		  	rownumbers:true,
		  		toolbar: [
		  		    {iconCls: 'icon-add',text:'添加',handler: function(){insertCustomRow();}},
		  		    {iconCls: 'icon-remove',text:'删除',handler: function(){removeCustomRow();}},
		  		    {iconCls: 'icon-search',text:'查询',handler: function(){onCustomSearch();}},
		  		    {iconCls: 'icon-back',text:'返回固定条件查询',handler: function(){onFixedQuery();}}
		  		],
		  		columns:[[
	  		        {field:'ck',checkbox:true},     
	                {field:'p1',title:'括号',width:50,align:"center",
	  		        	editor:{
	                        type:'combobox',
	                        options:{
	                        	editable:false,
	                            valueField:'id',
	                            textField:'text',
	                            data:CSIT.parentheses,
	                            formatter: function(row){
	                            	var opts = $(this).combobox('options');
	                            	var text = row[opts.textField];
	                        		if(text==""){
	                        			return  "<span style='padding:3px;'></span>";
	                        		}
	                        		return text;
	                        	}
	                        }
	                    }},
	                {field:'field',title:'字段',width:130,align:"center",
                    	formatter:function(value){
							for(var i=0; i<fields.length; i++){
								if (fields[i].id == value) return fields[i].text;
							}
							return value;
						},
	                    editor:{
	                        type:'combobox',
	                        options:{
	                        	required: true,
	                            valueField:'id',
	                            textField:'text',
	                            data:fields 
	                        }
	                    }},
	                {field:'operation',title:'运算符',width:80,align:"center",
	                    	formatter:function(value){
								for(var i=0; i<CSIT.operations.length; i++){
									if (CSIT.operations[i].id == value) return CSIT.operations[i].text;
								}
								return value;
							},
		                    editor:{
		                        type:'combobox',
		                        options:{
		                        	editable:false,
		                        	required: true,
		                            valueField:'id',
		                            textField:'text',
		                            data:CSIT.operations 
		                        }
		                    }},
	                {field:'key',title:'关键字',width:200,align:"center",
                    	editor:{
	                        type:'validatebox',
	                        options:{
	                        	required: true
	                        }
	                    }
                	},
	                {field:'p2',title:'括号',width:50,align:"center",
	                	editor:{
	                        type:'combobox',
	                        options:{
	                        	editable:false,
	                            valueField:'id',
	                            textField:'text',
	                            data:CSIT.parentheses,
	                            formatter: function(row){
	                            	var opts = $(this).combobox('options');
	                            	var text = row[opts.textField];
	                        		if(text==""){
	                        			return  "<span style='padding:3px;'></span>";
	                        		}
	                        		return text;
	                        	}
	                        }
	                    }
	                },
	                {field:'logical',title:'关系符',width:70,align:"center",
	                	editor:{
	                        type:'combobox',
	                        options:{
	                        	editable:false,
	                            valueField:'id',
	                            textField:'text',
	                            data:CSIT.logicals,
	                            formatter: function(row){
	                            	var opts = $(this).combobox('options');
	                            	var text = row[opts.textField];
	                        		if(text==""){
	                        			return  "<span style='padding:3px;'></span>";
	                        		}
	                        		return text;
	                        	}
	                        }
	                    }
	                }
	            ]]
			});
			//先清空
			$(customDatagridId).datagrid('loadData',{rows:[]});
			//再加一行
			insertCustomRow();
		}
    };
    
    CSIT.initCustomQueryNew = function(fields,linkebuttonId,customFormId,customDatagridId,formId,datagridId,page,intPropertyNames,intPropertyObjs,defaultParams,typeCheckedId){
    	var oldText = $(linkebuttonId).linkbutton('options').text;
    	if(defaultParams==undefined){
    		defaultParams = {};
    	}
//		if(oldText=='常规查询'){
//			$(linkebuttonId).linkbutton({    
//				text:'自定义查询'  
//			});  
//			$(customFormId).hide();
//			$(formId).show();
//			$(page).layout('panel','north').panel('resize',{
//				height:oldHeight
//			});
//			$(page).layout('resize');  
//			$(customDatagridId).datagrid('loadData',CSIT.empty_row);
//		}else 
		if(oldText=='自定义查询'){
			//如果已经在这个页面，点击无效
			if($(customFormId).is(":visible")){
				return false;
			}
			//存储原高度
			oldHeight = $(page).layout('panel','north').panel('options').height;
//			$(linkebuttonId).linkbutton({    
//				text:'常规查询'  
//			});
			$(page).layout('panel','north').panel('resize',{
				height:230
			});
			$(page).layout('resize');
			$(customFormId).show();
			$(formId).hide();

			var insertCustomRow = function(){
				$(customDatagridId).datagrid('appendRow', {
  					leftP: '',
  					field: '',
  					operation: '',
  					key: '',
  					rightP: '',
  					logical: ''
  				});
  				var rows = $(customDatagridId).datagrid('getRows');
  				$(customDatagridId).datagrid('beginEdit',rows.length-1);
			};
			var removeCustomRow = function(){
				var rows = $(customDatagridId).datagrid('getChecked');
				if(rows.length==0){
					 $.messager.alert('警告',"请选中要删除的记录","warning");
					 return;
				}
				for ( var i = 0; i < rows.length; i++) {
					var rowIndex = $(customDatagridId).datagrid('getRowIndex',rows[i]);
					$(customDatagridId).datagrid('deleteRow',rowIndex);
				}
			};
			var onCustomSearch = function(){
				var rows = $(customDatagridId).datagrid('getRows');
				var customSql = " ";
				var intProperties = {};
				if(intPropertyNames!=undefined){
					for(var i=0;i<intPropertyNames.length;i++){
						intProperties[intPropertyNames[i]] = intPropertyObjs[i];
					}
				}
				
				for (var i = 0; i < rows.length; i++) {
					var isValid = $(customDatagridId).datagrid('validateRow',i);
					if(!isValid){
						$.messager.alert("警告","请设置必填项","warning");
						return;
					}
					
					var p1Editor = $(customDatagridId).datagrid('getEditor', {index:i,field:'p1'});
					var p1 = $(p1Editor.target).combobox('getValue');
					customSql += " " + p1; 
					
					var fieldEditor = $(customDatagridId).datagrid('getEditor', {index:i,field:'field'});
					var isValidComboboxsArray = [];
					isValidComboboxsArray.push({0:$(fieldEditor.target),1:'字段'});
//					if(!isValidComboboxs(isValidComboboxsArray)){
//						return;
//					}
					var field = $(fieldEditor.target).combobox('getValue');
					customSql += field;
					
					var operationEditor = $(customDatagridId).datagrid('getEditor', {index:i,field:'operation'});
					var operation = $(operationEditor.target).combobox('getValue');
					
					var keyEditor = $(customDatagridId).datagrid('getEditor', {index:i,field:'key'});
					var key = $(keyEditor.target).val();
					//如果是int类型数据，则需要转化
					if(intProperties[field]!=undefined){
						if(intProperties[field][key]!=undefined){
							key = intProperties[field][key];
						}else{
							if(intProperties[field]["非法"]==undefined){
								key = -999;
							}else{
								key=intProperties[field]["非法"];
							}
						}
					}
					
					if (operation == "likeL") {
		                customSql += " like '" + key + "%'";
		            } else if (operation == "likeR") {
		                customSql += " like '%" + key + "'"; 
		            } else if (operation == "like" || operation == "not like") {
		                customSql += " " + operation + " '%" + key + "%'";
		            } else {
		                customSql += " " + operation + " '" + key + "'";
		            }
					
					var p2Editor = $(customDatagridId).datagrid('getEditor', {index:i,field:'p2'});
					var p2 = $(p2Editor.target).combobox('getValue');
					customSql += p2; 
					
					var logicalEditor = $(customDatagridId).datagrid('getEditor', {index:i,field:'logical'});
					var logical = $(logicalEditor.target).combobox('getValue');
					
					if (i != rows.length - 1 && logical == "") {
						var fn = function(){
							$(logicalEditor.target).combobox('showPanel');
						};
						$.messager.alert("警告","请设置关系符","warning",fn);
						return;
				     }
					customSql += " " + logical;
				}
				
				
				
				while (customSql.indexOf("  ") >= 0) {
					customSql = customSql.replace("  ", " ");
				}
				customSql = $.trim(customSql);
				if (customSql.split("(").length != customSql.split(")").length) {
					$.messager.alert("警告","请检查对应的括号.","warning");
					return; 
				}
				var typeChecked = [];
				var idsChecked = null;
				var ui = $(page).attr('ui');
				if('counselor_personnelInit'==ui){//辅导员管理页面
					 $(":checkbox[name=teaStatusType]:checked",page).each(function(){
						  var val = $(this).attr('value');
						  typeChecked.push(val);
					  });
					  if(typeChecked == ''){
						  typeChecked.push('3');
					  }
					  idsChecked=typeChecked.join(',');
					  if(idsChecked!=null){
						
							$.extend(defaultParams,{typeChecked:idsChecked});
					  }
					  var teaStatusId = $('#teaStatusSearch',page).combobox('getValue');
					  if(teaStatus!=null){
						  $.extend(defaultParams,{'teaStatus.teaStatusId':teaStatusId});
					  }
				}
				
				if(customSql==''){
//					customSql="  1 = 1 ";
					customSql=null;
				}
				
				$.extend(defaultParams,{customSql:customSql});
				$(datagridId).datagrid({
					queryParams:defaultParams
				});
			};
			
			var onFixedQuery = function(){
				$(customFormId).hide();
				$(formId).show();
				$(page).layout('panel','north').panel('resize',{
					height:oldHeight
				});
				$(page).layout('resize');  
				$(customDatagridId).datagrid('loadData',CSIT.empty_row);
			};
			
			$(customDatagridId).datagrid({
				width:1000,
				ctrlSelect:true,
				method:"POST",
	  		  	nowrap:true,
	  		  	striped: true,
	  		  	collapsible:true,
	  		  	rownumbers:true,
		  		toolbar: [
		  		    {iconCls: 'icon-add',text:'添加',handler: function(){insertCustomRow();}},
		  		    {iconCls: 'icon-remove',text:'删除',handler: function(){removeCustomRow();}},
		  		    {iconCls: 'icon-search',text:'查询',handler: function(){onCustomSearch();}},
		  		    {iconCls: 'icon-back',text:'返回固定条件查询',handler: function(){onFixedQuery();}}
		  		],
		  		columns:[[
	  		        {field:'ck',checkbox:true},     
	                {field:'p1',title:'括号',width:50,align:"center",
	  		        	editor:{
	                        type:'combobox',
	                        options:{
	                        	editable:false,
	                            valueField:'id',
	                            textField:'text',
	                            data:CSIT.parentheses,
	                            formatter: function(row){
	                            	var opts = $(this).combobox('options');
	                            	var text = row[opts.textField];
	                        		if(text==""){
	                        			return  "<span style='padding:3px;'></span>";
	                        		}
	                        		return text;
	                        	}
	                        }
	                    }},
	                {field:'field',title:'字段',width:130,align:"center",
                    	formatter:function(value){
							for(var i=0; i<fields.length; i++){
								if (fields[i].id == value) return fields[i].text;
							}
							return value;
						},
	                    editor:{
	                        type:'combobox',
	                        options:{
	                        	required: true,
	                            valueField:'id',
	                            textField:'text',
	                            data:fields 
	                        }
	                    }},
	                {field:'operation',title:'运算符',width:80,align:"center",
	                    	formatter:function(value){
								for(var i=0; i<CSIT.operations.length; i++){
									if (CSIT.operations[i].id == value) return CSIT.operations[i].text;
								}
								return value;
							},
		                    editor:{
		                        type:'combobox',
		                        options:{
		                        	editable:false,
		                        	required: true,
		                            valueField:'id',
		                            textField:'text',
		                            data:CSIT.operations 
		                        }
		                    }},
	                {field:'key',title:'关键字',width:200,align:"center",
                    	editor:{
	                        type:'validatebox',
	                        options:{
	                        	required: true
	                        }
	                    }
                	},
	                {field:'p2',title:'括号',width:50,align:"center",
	                	editor:{
	                        type:'combobox',
	                        options:{
	                        	editable:false,
	                            valueField:'id',
	                            textField:'text',
	                            data:CSIT.parentheses,
	                            formatter: function(row){
	                            	var opts = $(this).combobox('options');
	                            	var text = row[opts.textField];
	                        		if(text==""){
	                        			return  "<span style='padding:3px;'></span>";
	                        		}
	                        		return text;
	                        	}
	                        }
	                    }
	                },
	                {field:'logical',title:'关系符',width:70,align:"center",
	                	editor:{
	                        type:'combobox',
	                        options:{
	                        	editable:false,
	                            valueField:'id',
	                            textField:'text',
	                            data:CSIT.logicals,
	                            formatter: function(row){
	                            	var opts = $(this).combobox('options');
	                            	var text = row[opts.textField];
	                        		if(text==""){
	                        			return  "<span style='padding:3px;'></span>";
	                        		}
	                        		return text;
	                        	}
	                        }
	                    }
	                }
	            ]]
			});
			//先清空
			$(customDatagridId).datagrid('loadData',{rows:[]});
			//再加一行
			insertCustomRow();
		}
    };
    //===
})();


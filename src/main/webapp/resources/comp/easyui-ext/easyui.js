var sy = sy || {};
/**
 * 更改easyui加载panel时的提示文字
 * 
 * @author 孙宇
 * 
 * @requires jQuery,EasyUI
 */
$.extend($.fn.panel.defaults, {
	loadingMessage : '加载中....'
});

/**
 * 更改easyui加载grid时的提示文字
 * 
 * @author 孙宇
 * 
 * @requires jQuery,EasyUI
 */
$.extend($.fn.datagrid.defaults, {
	loadMsg : '数据加载中....'
});

/**
 * panel关闭时回收内存，主要用于layout使用iframe嵌入网页时的内存泄漏问题
 * 
 * @author 孙宇
 * 
 * @requires jQuery,EasyUI
 * 
 */
$.extend($.fn.panel.defaults, {
	onBeforeDestroy : function() {
		var frame = $('iframe', this);
		try {
			if (frame.length > 0) {
				for (var i = 0; i < frame.length; i++) {
					frame[i].src = '';
					frame[i].contentWindow.document.write('');
					frame[i].contentWindow.close();
				}
				frame.remove();
				if (navigator.userAgent.indexOf("MSIE") > 0) {// IE特有回收内存方法
					try {
						CollectGarbage();
					} catch (e) {
					}
				}
			}
		} catch (e) {
		}
	}
});

/**
 * 防止panel/window/dialog组件超出浏览器边界
 * 
 * @author 孙宇
 * 
 * @requires jQuery,EasyUI
 */
sy.onMove = {
	onMove : function(left, top) {
		var l = left;
		var t = top;
		if (l < 1) {
			l = 1;
		}
		if (t < 1) {
			t = 1;
		}
		var width = parseInt($(this).parent().css('width')) + 14;
		var height = parseInt($(this).parent().css('height')) + 14;
		var right = l + width;
		var buttom = t + height;
		var browserWidth = $(window).width();
		var browserHeight = $(window).height();
		if (right > browserWidth) {
			l = browserWidth - width;
		}
		if (buttom > browserHeight) {
			t = browserHeight - height;
		}
		$(this).parent().css({/* 修正面板位置 */
			left : l,
			top : t
		});
	}
};
$.extend($.fn.dialog.defaults, sy.onMove);
$.extend($.fn.window.defaults, sy.onMove);
$.extend($.fn.panel.defaults, sy.onMove);

/**
 * 
 * 通用错误提示
 * 
 * 用于datagrid/treegrid/tree/combogrid/combobox/form加载数据出错时的操作
 * 
 * @author 孙宇
 * 
 * @requires jQuery,EasyUI
 */
sy.onLoadError = {
	onLoadError : function(XMLHttpRequest) {
		if (parent.$ && parent.$.messager) {
			parent.$.messager.progress('close');
			parent.$.messager.alert('错误', XMLHttpRequest.responseText);
		} else {
			$.messager.progress('close');
			$.messager.alert('错误', XMLHttpRequest.responseText);
		}
	}
};
$.extend($.fn.datagrid.defaults, sy.onLoadError);
$.extend($.fn.treegrid.defaults, sy.onLoadError);
$.extend($.fn.tree.defaults, sy.onLoadError);
$.extend($.fn.combogrid.defaults, sy.onLoadError);
$.extend($.fn.combobox.defaults, sy.onLoadError);
$.extend($.fn.form.defaults, sy.onLoadError);

/**
 * 扩展combobox在自动补全模式时，检查用户输入的字符是否存在于下拉框中，如果不存在则清空用户输入
 * 
 * @author 孙宇
 * 
 * @requires jQuery,EasyUI
 */
$.extend($.fn.combobox.defaults, {
	onShowPanel : function() {
		var _options = $(this).combobox('options');
		if (_options.mode == 'remote') {/* 如果是自动补全模式 */
			var _value = $(this).combobox('textbox').val();
			var _combobox = $(this);
			if (_value.length > 0) {
				$.post(_options.url, {
					q : _value
				}, function(result) {
					if (result && result.length > 0) {
						_combobox.combobox('loadData', result);
					}
				}, 'json');
			}
		}
	},
	onHidePanel : function() {
		var _options = $(this).combobox('options');
		if (_options.mode == 'remote') {/* 如果是自动补全模式 */
			var _data = $(this).combobox('getData');/* 下拉框所有选项 */
			var _value = $(this).combobox('getValue');/* 用户输入的值 */
			var _b = false;/* 标识是否在下拉列表中找到了用户输入的字符 */
			for (var i = 0; i < _data.length; i++) {
				if (_data[i][_options.valueField] == _value) {
					_b = true;
				}
			}
			if (!_b) {/* 如果在下拉列表中没找到用户输入的字符 */
				$(this).combobox('setValue', '');
			}
		}
	}
});

/**
 * 扩展combogrid在自动补全模式时，检查用户输入的字符是否存在于下拉框中，如果不存在则清空用户输入
 * 
 * @author 孙宇
 * 
 * @requires jQuery,EasyUI
 */
$.extend($.fn.combogrid.defaults, {
	onShowPanel : function() {
		var _options = $(this).combogrid('options');
		if (_options.mode == 'remote') {/* 如果是自动补全模式 */
			var _value = $(this).combogrid('textbox').val();
			if (_value.length > 0) {
				$(this).combogrid('grid').datagrid("load", {
					q : _value
				});
			}
		}
	},
	onHidePanel : function() {
		var _options = $(this).combogrid('options');
		if (_options.mode == 'remote') {/* 如果是自动补全模式 */
			var _data = $(this).combogrid('grid').datagrid('getData').rows;/* 下拉框所有选项 */
			var _value = $(this).combogrid('getValue');/* 用户输入的值 */
			var _b = false;/* 标识是否在下拉列表中找到了用户输入的字符 */
			for (var i = 0; i < _data.length; i++) {
				if (_data[i][_options.idField] == _value) {
					_b = true;
				}
			}
			if (!_b) {/* 如果在下拉列表中没找到用户输入的字符 */
				$(this).combogrid('setValue', '');
			}
		}
	}
});

/**
 * 扩展validatebox，添加新的验证功能
 * 
 * @author 孙宇
 * 
 * @requires jQuery,EasyUI
 */
$.extend($.fn.validatebox.defaults.rules, {
	eqPwd : {/* 验证两次密码是否一致功能 */
		validator : function(value, param) {
			return value == $(param[0]).val();
		},
		message : '密码不一致！'
	},
	ajax : {//ajax验证
		validator : function(value, param) {
			var data={};
			data[param[1]]=value;
			var result=$.ajax({url:param[0],
								dataType:"json",
								data:data,
								async:false,
								cache:false,
								type:"post"
							   }).responseText;
			return result=="true";
		},
		message : '{2}'
	},
	minLength : { // 判断最小长度
		validator : function(value, param) {
			return value.length >= param[0];
		},
		message : '最少输入 {0} 个字符。'
	},
	length:{validator:function(value,param){
		var len=$.trim(value).length;
			return len>=param[0]&&len<=param[1];
		},
			message:"内容长度介于{0}和{1}之间."
		},
	minValue:{validator:function(value,param){
			return value >=param[0];
		},
			message:"最小值是{0}."
		},
	maxValue:{validator:function(value,param){
			return value <=param[0];
		},
			message:"最大值是{0}."
		},
	betweenValue:{validator:function(value,param){
			return value >=param[0]&&value<=param[1];
		},
			message:"值介于{0}和{1}之间."
		},
	phoneOrZero : {// 验证电话号码
		validator : function(value) {
			return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value)||/^0$/i.test(value);
		},
		message : '格式不正确,请使用下面格式:0591-88888888或0'
	},
	phone : {// 验证电话号码
		validator : function(value) {
			return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
		},
		message : '格式不正确,请使用下面格式:0591-88888888'
	},
	mobile : {// 验证手机号码
		validator : function(value) {
			return /^(13|14|15|17|18)\d{9}$/i.test(value);
		},
		message : '手机号码格式不正确(正确格式如：13450774432)'
	},
	phoneOrMobile:{//验证手机或电话
		validator : function(value) {
			return /^(13|14|15|17|18)\d{9}$/i.test(value) || /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
		},
		message:'请填入手机或电话号码,如13988888888或0591-8888888'
	},
	phoneOrMobileOrZero:{//验证手机或电话或0
		validator : function(value) {
			return /^(13|14|15|17|18)\d{9}$/i.test(value) || /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value)||/^0$/i.test(value);
		},
		message:'请填入手机或电话号码或0,如13988888888或0591-8888888或0'
	},
	idcard : {// 验证身份证
		validator : function(value) {
			return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value);
		},
		message : '身份证号码格式不正确'
	},
	idcardTwo : {// 验证身份证
		validator : function(value) {
			return /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/.test(value);
		},
		message : '身份证号码格式不正确'
	},
	floatOrInt : {// 验证是否为小数或整数
		validator : function(value) {
			return /^(\d{1,3}(,\d\d\d)*(\.\d{1,3}(,\d\d\d)*)?|\d+(\.\d+))?$/i.test(value);
		},
		message : '请输入数字，并保证格式正确'
	},
	currency : {// 验证货币
		validator : function(value) {
			return /^\d{0,}(\.\d+)?$/i.test(value);
		},
		message : '货币格式不正确'
	},
	money : {// 验证金额
		validator : function(value) {
			return /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/.test(value);
		},
		message : '金额格式不正确'
	},
	qq : {// 验证QQ,从10000开始
		validator : function(value) {
			return /^[1-9]\d{4,9}$/i.test(value);
		},
		message : 'QQ号码格式不正确(正确如：453384319)'
	},
	integer : {// 验证整数
		validator : function(value) {
			return /^[+]?[1-9]+\d*$/i.test(value);
		},
		message : '请输入整数'
	},
	number : {// 验证整数
		validator : function(value) {
			return /^[+]?[1-9]+\d*$/i.test(value);
		},
		message : '请输入数字'
	},
	chinese : {// 验证中文
		validator : function(value) {
			return /^[\u0391-\uFFE5]+$/i.test(value);
		},
		message : '请输入中文'
	},
	english : {// 验证英语
		validator : function(value) {
			return /^[A-Za-z]+$/i.test(value);
		},
		message : '请输入英文'
	},
	unnormal : {// 验证是否包含空格和非法字符
		validator : function(value) {
			return /.+/i.test(value);
		},
		message : '输入值不能为空和包含其他非法字符'
	},
	username : {// 验证用户名
		validator : function(value) {
			return /^[a-zA-Z][a-zA-Z0-9_]{5,15}$/i.test(value);
		},
		message : '用户名不合法（字母开头，允许6-16字节，允许字母数字下划线）'
	},
	faxno : {// 验证传真
		validator : function(value) {
//			return /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/i.test(value);
			return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
		},
		message : '传真号码不正确'
	},
	postalcode : {// 验证邮政编码
		validator : function(value) {
			return /^\d{6}$/i.test(value);
		},
		message : '邮政编码格式不正确'
	},
	ip : {// 验证IP地址
		validator : function(value) {
			return /d+.d+.d+.d+/i.test(value);
		},
		message : 'IP地址格式不正确'
	},
	name : {// 验证姓名，可以是中文或英文
			validator : function(value) {
				return /^[\u0391-\uFFE5]+$/i.test(value)|/^\w+[\w\s]+\w+$/i.test(value);
			},
			message : '姓名格式不正确'
	},
	carNo:{//车牌号
		validator : function(value){
			return /^[\u4E00-\u9FA5][\da-zA-Z]{6}$/.test(value); 
		},
		message : '车牌号码无效（例：粤J12350）'
	},
	carenergin:{//发动机号
		validator : function(value){
			return /^[a-zA-Z0-9]{16}$/.test(value); 
		},
		message : '发动机型号无效(例：FG6H012345654584)'
	},
	email:{//电子邮件
		validator : function(value){
		return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value); 
	},
	message : '请输入有效的电子邮件账号(例：abc@126.com)'	
	},
	photo:{//图片
		validator : function(value){
		return /\.(jpg|jpeg|png|JPG|PNG|JPEG)$/.test(value); 
	},
	message : '图片类型必须是jpeg,jpg,png中的一种'	
	},
	zip:{//格式为zip
		validator : function(value){
		return /\.(zip)$/.test(value); 
		},
		message : '上传文件类型必须是zip'	
	},
	word:{//Word文档
		validator : function(value){
			return /\.(doc|docx)$/.test(value); 
		},
		message : '上传文件类型必须是word文档'	
	},
	msn:{
		validator : function(value){
			return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value); 
		},
		message : '请输入有效的msn账号(例：abc@hotnail(msn/live).com)'
	},
	department:{
		validator : function(value){
			return /^[0-9]*$/.test(value); 
		},
		message : '请输入部门排序号(例：1)'	
	},
	same:{
		validator : function(value, param){
			if($("#"+param[0]).val() != "" && value != ""){
				return $("#"+param[0]).val() == value; 
			}else{
				return true;
			}
		},
		message : '两次输入的密码不一致！'	
	},
	matchUserPwd:{
		validator : function(value){
			var r, re; // 声明变量。
			re = new RegExp("^(?!^[0-9]+$)(?!^[a-zA-Z]+$).{6,}$","g"); // 创建正则表达式对象。
			r = re.test(value); // 在字符串 s 中查找匹配。
			return(r);
		},
		message : '密码长度不小于6位，密码不能全为数字或字母！'	
	},
	percentage:{//分式的值不超过0.1（10%）
		validator:function(value, param){
			var id1 = param[0];
			var id2 = param[1];
			var range = param[2];
			var numerator = parseFloat($('#'+id1,'#'+range).numberbox('getValue'));
			var denominator = parseFloat($('#'+id2,'#'+range).numberbox('getValue'));
			return numerator / denominator <= 0.1;
		},
		message:'必须前10%'
	},
	maxLength:{//内容不超过指定长度
		validator:function(value, param) {
			return value.length <= param[0];
		},
		message:'不超过{0}字'
	}
});

/**
 * 扩展tree和combotree，使其支持平滑数据格式
 * 
 * @author 孙宇
 * 
 * @requires jQuery,EasyUI
 * 
 */
sy.loadFilter = {
	loadFilter : function(data, parent) {
		var opt = $(this).data().tree.options;
		var idField, textField, parentField;
		if (opt.parentField) {
			idField = opt.idField || 'id';
			textField = opt.textField || 'text';
			parentField = opt.parentField || 'pid';
			var i, l, treeData = [], tmpMap = [];
			for (i = 0, l = data.length; i < l; i++) {
				tmpMap[data[i][idField]] = data[i];
			}
			for (i = 0, l = data.length; i < l; i++) {
				if (tmpMap[data[i][parentField]] && data[i][idField] != data[i][parentField]) {
					if (!tmpMap[data[i][parentField]]['children'])
						tmpMap[data[i][parentField]]['children'] = [];
					data[i]['text'] = data[i][textField];
					tmpMap[data[i][parentField]]['children'].push(data[i]);
				} else {
					data[i]['text'] = data[i][textField];
					treeData.push(data[i]);
				}
			}
			return treeData;
		}
		return data;
	}
};
$.extend($.fn.combotree.defaults, sy.loadFilter);
$.extend($.fn.tree.defaults, sy.loadFilter);

/**
 * 扩展treegrid，使其支持平滑数据格式
 * 
 * @author 孙宇
 * 
 * @requires jQuery,EasyUI
 * 
 */
$.extend($.fn.treegrid.defaults, {
	loadFilter : function(data, parentId) {
		var opt = $(this).data().treegrid.options;
		var idField, treeField, parentField;
		if (opt.parentField) {
			idField = opt.idField || 'id';
			treeField = opt.textField || 'text';
			parentField = opt.parentField || 'pid';
			var i, l, treeData = [], tmpMap = [];
			for (i = 0, l = data.length; i < l; i++) {
				tmpMap[data[i][idField]] = data[i];
			}
			for (i = 0, l = data.length; i < l; i++) {
				if (tmpMap[data[i][parentField]] && data[i][idField] != data[i][parentField]) {
					if (!tmpMap[data[i][parentField]]['children'])
						tmpMap[data[i][parentField]]['children'] = [];
					data[i]['text'] = data[i][treeField];
					tmpMap[data[i][parentField]]['children'].push(data[i]);
				} else {
					data[i]['text'] = data[i][treeField];
					treeData.push(data[i]);
				}
			}
			return treeData;
		}
		return data;
	}
});

/**
 * 创建一个模式化的dialog
 * 
 * @author 孙宇
 * 
 * @requires jQuery,EasyUI
 * 
 */
sy.modalDialog = function(options) {
	var opts = $.extend({
		title : '&nbsp;',
		width : 640,
		height : 480,
		modal : true,
		closable:false,
		onClose : function() {
			$(this).dialog('destroy');
		}
	}, options);
	opts.modal = true;// 强制此dialog为模式化，无视传递过来的modal参数
	if (options.url) {
		opts.content = '<iframe id="" src="' + options.url + '" allowTransparency="true" scrolling="auto" width="100%" height="98%" frameBorder="0" name=""></iframe>';
	}
	return $('<div/>').dialog(opts);
};

/**
 * 更换主题
 * 
 * @author 孙宇
 * @requires jQuery,EasyUI
 * @param themeName
 */
sy.changeTheme = function(themeName) {
	var $easyuiTheme = $('#easyuiTheme');
	var url = $easyuiTheme.attr('href');
	var href = url.substring(0, url.indexOf('themes')) + 'themes/' + themeName + '/easyui.css';
	$easyuiTheme.attr('href', href);

	var $iframe = $('iframe');
	if ($iframe.length > 0) {
		for (var i = 0; i < $iframe.length; i++) {
			var ifr = $iframe[i];
			try {
				$(ifr).contents().find('#easyuiTheme').attr('href', href);
			} catch (e) {
				try {
					ifr.contentWindow.document.getElementById('easyuiTheme').href = href;
				} catch (e) {
				}
			}
		}
	}

	sy.cookie('easyuiTheme', themeName, {
		expires : 7
	});
};

/**
 * 滚动条
 * 
 * @author 孙宇
 * @requires jQuery,EasyUI
 */
sy.progressBar = function(options) {
	if (typeof options == 'string') {
		if (options == 'close') {
			$('#syProgressBarDiv').dialog('destroy');
		}
	} else {
		if ($('#syProgressBarDiv').length < 1) {
			var opts = $.extend({
				title : '&nbsp;',
				closable : false,
				width : 300,
				height : 60,
				modal : true,
				content : '<div id="syProgressBar" class="easyui-progressbar" data-options="value:0"></div>'
			}, options);
			$('<div id="syProgressBarDiv"/>').dialog(opts);
			$.parser.parse('#syProgressBarDiv');
		} else {
			$('#syProgressBarDiv').dialog('open');
		}
		if (options.value) {
			$('#syProgressBar').progressbar('setValue', options.value);
		}
	}
};




/**
 * EasyUI DataGrid根据字段动态合并单元格
 * @param tableID 要合并table的id
 * @param colList 要合并的列,用逗号分隔(例如："name,department,office");
 */
function mergeCellsByField(tableID,colList){
    var ColArray = colList.split(",");//需要合并列的属性名数组
    var tTable = $('#'+tableID);//datagrid对象
    var TableRowCnts=tTable.datagrid("getRows").length;//返回当前页的记录的长度
    var tmpA;//需要合并的行数
  //  var tmpB;//合并到第几行
    var PerTxt = "";//循环时存放上一个单元格的值
    var CurTxt = "";//每个要合并单元格的值
    var alertStr = "";
    for (var j=ColArray.length-1;j>=0 ;j-- )
    {
        PerTxt="";
        tmpA=1;
        //tmpB=0;
        for (i=0;i<=TableRowCnts ;i++ )
        {
            if (i==TableRowCnts)
            {
                CurTxt="";
            }
            else
            {
                CurTxt=tTable.datagrid("getRows")[i][ColArray[j]];
                for(var k = j-1;k>=0;k--){
                	CurTxt+=tTable.datagrid("getRows")[i][ColArray[k]];
                }
            }
            if (PerTxt==CurTxt)
            {
            	//行数加1
                tmpA+=1;
            }
            else
            {
                //tmpB+=tmpA;
                tTable.datagrid('mergeCells',{
                    index:i-tmpA,
                    field:ColArray[j],
                    rowspan:tmpA,
                    colspan:null
                });
                //将需要合并的行数设置为1
                tmpA=1;
            }
            PerTxt=CurTxt;
        }
    }
}



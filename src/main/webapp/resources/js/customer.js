var customer = {
    init: function (params) {
    	app.crud.initEditDialog({});
        app.crud.initCRUDBtn({
        	add:function(){
        	},
        	update:function(){
        	}
        });
    	app.crud.initViewList({
       	 	columns:[[
       		        {field:'ck',checkbox:true},
					{field:'customerType',title:'客户类型1个人2企业',width:100,align:'center'},
					{field:'customerName',title:'客户名称',width:100,align:'center'},
					{field:'sex',title:'性别1男0女',width:100,align:'center'},
					{field:'mobilePhone',title:'手机号',width:100,align:'center'},
					{field:'telephone',title:'固定电话',width:100,align:'center'},
					{field:'email',title:'邮箱',width:100,align:'center'},
					{field:'idNumber',title:'身份证号',width:100,align:'center'},
					{field:'idAddress',title:'身份证地址',width:100,align:'center'},
					{field:'customerSourceId',title:'客户来源',width:100,align:'center'},
					{field:'customerStageId',title:'客户阶段',width:100,align:'center'},
					{field:'customerStatusId',title:'客户状态',width:100,align:'center'},
					{field:'ownerId',title:'拥有人',width:100,align:'center'},
					{field:'gradeId',title:'智能评级',width:100,align:'center'},
					{field:'placeId',title:'户籍地',width:100,align:'center'},
					{field:'maritalId',title:'婚姻状况',width:100,align:'center'},
					{field:'liveAreaId',title:'居住地区',width:100,align:'center'},
					{field:'livePlotId',title:'居住小区',width:100,align:'center'},
					{field:'birthdate',title:'出生日期',width:100,align:'center'},
					{field:'company',title:'就职单位',width:100,align:'center'},
					{field:'occupation',title:'职业',width:100,align:'center'},
					{field:'spouseName',title:'配偶姓名',width:100,align:'center'},
					{field:'spousePlaceId',title:'配偶户籍地',width:100,align:'center'},
					{field:'spouseMobilePhone',title:'配偶手机号',width:100,align:'center'},
					{field:'spouseIdNumber',title:'配偶身份证号',width:100,align:'center'},
					{field:'spouseCompany',title:'配偶就职单位',width:100,align:'center'},
					{field:'spouseOccupation',title:'配偶职业',width:100,align:'center'},
					{field:'creditRatingId',title:'信用等级',width:100,align:'center'},
					{field:'creditRatingAttachId',title:'信用等级附件',width:100,align:'center'},
					{field:'lastTrackTime',title:'最后跟进时间(年月日）',width:100,align:'center'},
					{field:'photoId',title:'照片',width:100,align:'center'},
					{field:'wxOpenId',title:'微信登录获取到的openid',width:100,align:'center'},
					{field:'id',hidden:true}
       		]]
    	});
    }
}
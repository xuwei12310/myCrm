var brokerageRule = {
    init: function (params) {
    	brokerageRule.modBrokerageRule();
    	brokerageRule.initEditDialog();
    	brokerageRule.initEditForm();
    	$("#editForm").attr('action',ctx+"/admin/dict/brokerageRule/update.jhtml");
    },
    modBrokerageRule:function(){
    	var url=app.getUrl("selectById");
		var modRow = CSIT.syncCallService(url);
		$("#editForm").form('load',modRow);
    },
    initEditDialog:function(){
    	$("#updateBrokerageRuleBtn").click(function(){
    		$("#editForm").submit();
    	});
    	$("#resetBtn").click(function(){
    		brokerageRule.modBrokerageRule();
    	});
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
    				$.messager.alert('提示','保存成功','info');
    			}else{
    				$.messager.alert('提示',data.message,'error');
    			}
    		}
    	});
    },
}

package com.xuwei.bean;
/**
 * 
 * @description: 字典类型枚举
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年6月29日下午5:36:28
 * @author：hhd
 * @version：1.0
 */
public enum DictType {
	landNature("土地性质"),
	housingNature("房屋性质"),
	repayWay("还款方式"),
	product("产品"),
	creditRating("信用等级"),
	maritalStatus("婚姻状况"),
	grade("智能评级"),
	customerStatus("客户状态"),
	customerStage("客户阶段"),
	customerSource("客户来源"),
	followType("跟进方式"),
	followStage("跟进阶段"),
	payType("付款方式"),
	periodTime("期次"),
	costType("费用类型");
	
	
    private String info;

    DictType(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
    public void setInfo(String info){
    	this.info=info;
    }
}


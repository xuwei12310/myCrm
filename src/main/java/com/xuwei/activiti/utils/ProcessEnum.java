package com.xuwei.activiti.utils;

public enum ProcessEnum {

	order_approve("订单送审","/myWorkbench/order/approveView.jhtml"),
	order_loan_approve("订单借款送审","/myWorkbench/orderLoan/approveView.jhtml"),
	pay("支出登记","/financial/pay/approveView.jhtml"),
	order_balance_approve("订单结算","/financial/balance/approveView.jhtml"),
	yearProjectSurvey("年度问卷审核","/business/yearProjectSurvey/view.jhtml"),
	brokerage_audit("佣金结算","/financial/brokerageDistribute/approveView.jhtml");
	
	ProcessEnum(String value, String viewUrl) {
		this.value = value;
		this.viewUrl = viewUrl;
	}
	
	private String value;
	
	private String viewUrl;
	
	public String getValue() {
		return value;
	}
	

	public String getViewUrl() {
		return viewUrl;
	}

	public void setViewUrl(String viewUrl) {
		this.viewUrl = viewUrl;
	}
	
}

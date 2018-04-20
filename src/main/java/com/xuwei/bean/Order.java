package com.xuwei.bean;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @description: 订单
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月05日 09:22:53
 * @author: hhd
 * @version: 1.0
 */
@TableName(value="t_order",resultMap="BaseResultMap")
public class Order extends Model<Order> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 订单编号(年月日+两位流水）
     */
	@TableField("order_code")
	private String orderCode;
    /**
     * 客户
     */
	@TableField(value = "customer_id" ,el = "customer.id")
	private Customer customer;
    /**
     * 产品
     */
	@TableField(value = "product_id" ,el = "product.id")
	private Dict product;
    /**
     * 抵押房产
     */
	@TableField("property_id")
	private Long propertyId;
    /**
     * 银行
     */
	@TableField(value = "bank_id" ,el = "bank.id")
	private Bank bank;
    /**
     * 支行
     */
	@TableField(value = "branch_id" ,el = "branch.id")
	private BankSubbranch branch;
    /**
     * 签约日期（年月日）
     */
	@TableField("signing_date")
	private String signingDate;

	@TableField(exist = false)
	private String signingDateStart;
	@TableField(exist = false)
	private String signingDateEnd;

    /**
     * 预计完成日期（年月日）
     */
	@TableField("estimate_finish_time")
	private String estimateFinishTime;

	@TableField(exist = false)
	private String estimateFinishTimeStart;
	@TableField(exist = false)
	private String estimateFinishTimeEnd;

    /**
     * 贷款金额（万元）
     */
	@TableField("loan_amount")
	private BigDecimal loanAmount;
    /**
     * 月利率（厘/月）
     */
	@TableField("lending_rate")
	private BigDecimal lendingRate;
    /**
     * 贷款年限
     */
	@TableField("loan_term")
	private Integer loanTerm;
    /**
     * 还款方式
     */
	@TableField(value = "repay_way_id",el = "repayWay.id")
	private Dict repayWay;
    /**
     * 手续费百分比（%）
     */
	@TableField("service_charge_percent")
	private BigDecimal serviceChargePercent;
    /**
     * 手续费（元）
     */
	@TableField("service_charge")
	private BigDecimal serviceCharge;
	/**
	 * 介绍人id
	 */
	@TableField(value = "matchmaker_id",el = "matchmaker.id")
	private OtherPartners matchmaker;
    /**
     * 返佣金额百分比
     */
	@TableField("commission_rate")
	private BigDecimal commissionRate;
	/**
	 * 返佣金额
	 */
	@TableField("commission_amount")
	private BigDecimal commissionAmount;
    /**
     * 返佣理由
     */
	@TableField("commission_reason")
	private String commissionReason;
    /**
     * 客服助理
     */
	@TableField(value = "cs_assistant_id",el = "csAssistant.id")
	private User csAssistant;
    /**
     * 客服负责人
     */
	@TableField(value = "cs_principal_id",el = "csPrincipal.id")
	private User csPrincipal;
    /**
     * 跟单人
     */
	@TableField(value = "follow_user_id",el = "followUser.id")
	private User followUser;
    /**
     * 拥有人
     */
	@TableField(value = "owner_id",el = "owner.id")
	private User owner;
    /**
     * 审核状态（1草稿2审核中3审核通过4审核不通过）
     */
	@TableField("audit_status")
	private Integer auditStatus;


	@TableField("process_instance_id")
	private String processInstanceId;
    /**
     * 状态（1已立项2已签约3已放款4完结）
     */
	private Integer status;
    /**
     * 订单成本（元）
     */
	private BigDecimal cost;
    /**
     * 应收金额（元）
     */
	@TableField("receive_amount")
	private BigDecimal receiveAmount;
    /**
     * 应付金额（元）
     */
	@TableField("pay_amount")
	private BigDecimal payAmount;
    /**
     * 借款总金额（万元）
     */
	@TableField("company_loan_amount")
	private BigDecimal companyLoanAmount;
    /**
     * 借款利息总额（元）
     */
	@TableField("company_loan_interest")
	private BigDecimal companyLoanInterest;
    /**
     * 收款账户审核状态（1草稿2审核中3审核通过4审核不通过）
     */
	@TableField("receivables_account_status")
	private Integer receivablesAccountStatus;
    /**
     * 放款日期
     */
	@TableField("loan_date")
	private String loanDate;
    /**
     * 放款金额
     */
	@TableField("loan_money")
	private BigDecimal loanMoney;
    /**
     * 结算手续费
     */
	@TableField("settlement_charge")
	private BigDecimal settlementCharge;
    /**
     * 结算员
     */
	@TableField("settlement_clerk")
	private Long settlementClerk;
	 /**
     * 结算提交人
     */
	@TableField(value="settlement_submit_user_id",el="settlementSubmitUser.id")
	private User settlementSubmitUser;
	 /**
     * 结算提交时间
     */
	@TableField("settlement_submit_date")
	private String settlementSubmitDate;
	 /**
     * 结算审核状态
     */
	@TableField("settlement_audit_status")
	private Integer settlementAuditStatus;
	/**
	 * 佣金审核状态（1草稿2审核中3审核通过4审核不通过）
	 */
	@TableField("brokerage_audit_status")
	private Integer brokerageAuditStatus;
	/**
	 * 佣金发放状态
	 */
	@TableField("brokerage_grant_state")
	private Integer brokerageGrantState;
	/**
	 * 佣金总额
	 */
	@TableField("brokerage_total")
	private BigDecimal brokerageTotal;
    /**
     * 备注
     */
	private String note;
    /**
     * 创建人
     */
	@TableField("creator_id")
	private Long creatorId;

	@TableField(exist = false)
	private String createName;

    /**
     * 创建时间
     */
	@TableField("create_time")
	private String createTime;
    /**
     * 最后修改时间
     */
	@TableField("last_modify_time")
	private String lastModifyTime;
    /**
     * 最后修改人
     */
	@TableField("last_modifier_id")
	private Long lastModifierId;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Long getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(Long propertyId) {
		this.propertyId = propertyId;
	}

	public String getSigningDate() {
		return signingDate;
	}
	
	public User getSettlementSubmitUser() {
		return settlementSubmitUser;
	}

	public void setSettlementSubmitUser(User settlementSubmitUser) {
		this.settlementSubmitUser = settlementSubmitUser;
	}

	public String getSettlementSubmitDate() {
		return settlementSubmitDate;
	}

	public void setSettlementSubmitDate(String settlementSubmitDate) {
		this.settlementSubmitDate = settlementSubmitDate;
	}

	

	public Integer getSettlementAuditStatus() {
		return settlementAuditStatus;
	}

	public void setSettlementAuditStatus(Integer settlementAuditStatus) {
		this.settlementAuditStatus = settlementAuditStatus;
	}

	public void setSigningDate(String signingDate) {
		this.signingDate = signingDate;
	}

	public String getEstimateFinishTime() {
		return estimateFinishTime;
	}

	public void setEstimateFinishTime(String estimateFinishTime) {
		this.estimateFinishTime = estimateFinishTime;
	}

	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public BigDecimal getLendingRate() {
		return lendingRate;
	}

	public void setLendingRate(BigDecimal lendingRate) {
		this.lendingRate = lendingRate;
	}

	public Integer getLoanTerm() {
		return loanTerm;
	}

	public void setLoanTerm(Integer loanTerm) {
		this.loanTerm = loanTerm;
	}

	public BigDecimal getServiceChargePercent() {
		return serviceChargePercent;
	}

	public void setServiceChargePercent(BigDecimal serviceChargePercent) {
		this.serviceChargePercent = serviceChargePercent;
	}

	public BigDecimal getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(BigDecimal serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public BigDecimal getCommissionAmount() {
		return commissionAmount;
	}

	public void setCommissionAmount(BigDecimal commissionAmount) {
		this.commissionAmount = commissionAmount;
	}

	public String getCommissionReason() {
		return commissionReason;
	}

	public void setCommissionReason(String commissionReason) {
		this.commissionReason = commissionReason;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public BigDecimal getReceiveAmount() {
		return receiveAmount;
	}

	public void setReceiveAmount(BigDecimal receiveAmount) {
		this.receiveAmount = receiveAmount;
	}

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public BigDecimal getCompanyLoanAmount() {
		return companyLoanAmount;
	}

	public void setCompanyLoanAmount(BigDecimal companyLoanAmount) {
		this.companyLoanAmount = companyLoanAmount;
	}

	public BigDecimal getCompanyLoanInterest() {
		return companyLoanInterest;
	}

	public void setCompanyLoanInterest(BigDecimal companyLoanInterest) {
		this.companyLoanInterest = companyLoanInterest;
	}

	public Integer getReceivablesAccountStatus() {
		return receivablesAccountStatus;
	}

	public void setReceivablesAccountStatus(Integer receivablesAccountStatus) {
		this.receivablesAccountStatus = receivablesAccountStatus;
	}

	public String getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(String loanDate) {
		this.loanDate = loanDate;
	}

	public BigDecimal getLoanMoney() {
		return loanMoney;
	}

	public void setLoanMoney(BigDecimal loanMoney) {
		this.loanMoney = loanMoney;
	}

	public BigDecimal getSettlementCharge() {
		return settlementCharge;
	}

	public void setSettlementCharge(BigDecimal settlementCharge) {
		this.settlementCharge = settlementCharge;
	}

	public Long getSettlementClerk() {
		return settlementClerk;
	}

	public void setSettlementClerk(Long settlementClerk) {
		this.settlementClerk = settlementClerk;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(String lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public Long getLastModifierId() {
		return lastModifierId;
	}

	public void setLastModifierId(Long lastModifierId) {
		this.lastModifierId = lastModifierId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Dict getProduct() {
		return product;
	}

	public void setProduct(Dict product) {
		this.product = product;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public BankSubbranch getBranch() {
		return branch;
	}

	public void setBranch(BankSubbranch branch) {
		this.branch = branch;
	}

	public Dict getRepayWay() {
		return repayWay;
	}

	public void setRepayWay(Dict repayWay) {
		this.repayWay = repayWay;
	}

	public User getCsAssistant() {
		return csAssistant;
	}

	public void setCsAssistant(User csAssistant) {
		this.csAssistant = csAssistant;
	}

	public User getCsPrincipal() {
		return csPrincipal;
	}

	public void setCsPrincipal(User csPrincipal) {
		this.csPrincipal = csPrincipal;
	}

	public User getFollowUser() {
		return followUser;
	}

	public void setFollowUser(User followUser) {
		this.followUser = followUser;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getSigningDateStart() {
		return signingDateStart;
	}

	public void setSigningDateStart(String signingDateStart) {
		this.signingDateStart = signingDateStart;
	}

	public String getSigningDateEnd() {
		return signingDateEnd;
	}

	public void setSigningDateEnd(String signingDateEnd) {
		this.signingDateEnd = signingDateEnd;
	}

	public String getEstimateFinishTimeStart() {
		return estimateFinishTimeStart;
	}

	public void setEstimateFinishTimeStart(String estimateFinishTimeStart) {
		this.estimateFinishTimeStart = estimateFinishTimeStart;
	}

	public String getEstimateFinishTimeEnd() {
		return estimateFinishTimeEnd;
	}

	public void setEstimateFinishTimeEnd(String estimateFinishTimeEnd) {
		this.estimateFinishTimeEnd = estimateFinishTimeEnd;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public BigDecimal getCommissionRate() {
		return commissionRate;
	}

	public void setCommissionRate(BigDecimal commissionRate) {
		this.commissionRate = commissionRate;
	}

	public OtherPartners getMatchmaker() {
		return matchmaker;
	}

	public void setMatchmaker(OtherPartners matchmaker) {
		this.matchmaker = matchmaker;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	public Integer getBrokerageAuditStatus() {
		return brokerageAuditStatus;
	}

	public void setBrokerageAuditStatus(Integer brokerageAuditStatus) {
		this.brokerageAuditStatus = brokerageAuditStatus;
	}

	public Integer getBrokerageGrantState() {
		return brokerageGrantState;
	}

	public void setBrokerageGrantState(Integer brokerageGrantState) {
		this.brokerageGrantState = brokerageGrantState;
	}

	public BigDecimal getBrokerageTotal() {
		return brokerageTotal;
	}

	public void setBrokerageTotal(BigDecimal brokerageTotal) {
		this.brokerageTotal = brokerageTotal;
	}

}

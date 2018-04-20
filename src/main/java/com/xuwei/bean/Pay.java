package com.xuwei.bean;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * @description: 支出登记
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月12日 16:48:34
 * @author: xw
 * @version: 1.0
 */
@TableName(value="t_pay",resultMap="BaseResultMap")
public class Pay extends Model<Pay> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 来源1订单结算2添加
     */
	private Integer source;
    /**
     * 费用类型
     */
	@TableField(value="cost_type_id",el="costType.id")
	private Dict costType;
    /**
     * 支出对象1评估公司2合作银行3其他合作伙伴4客户
     */
	@TableField("pay_object_type")
	private Integer payObjectType;
    /**
     * 是否成本1是0否
     */
	@TableField("is_cost")
	private Integer isCost;
    /**
     * 订单
     */
	@TableField(value="order_id",el="order.id")
	private Order order;
    /**
     * 客户
     */
	@TableField(value="customer_id",el="customer.id")
	private Customer customer;
    /**
     * 评估公司
     */
	@TableField(value="assessment_company_id",el="assessmentCompany.id")
	private AssessmentCompany assessmentCompany;
    /**
     * 合作银行
     */
	@TableField(value="bank_subbranch_id",el="bankSubbranch.id")
	private BankSubbranch bankSubbranch;
    /**
     * 其他合作伙伴
     */
	@TableField(value="other_partners_id",el="otherPartners.id")
	private OtherPartners otherPartners;
    /**
     * 预计支出金额
     */
	@TableField("estimate_pay_amount")
	private BigDecimal estimatePayAmount;
    /**
     * 支出时间
     */
	@TableField("pay_time")
	private String payTime;
    /**
     * 支出金额
     */
	@TableField("pay_amount")
	private BigDecimal payAmount;
    /**
     * 付款账号
     */
	@TableField(value="pay_bank_account_id",el="payBankAccount.id")
	private OrgBankAccount payBankAccount;
    /**
     * 收款账户名
     */
	@TableField("receive_account_name")
	private String receiveAccountName;
    /**
     * 收款账户开户行
     */
	@TableField("receive_account_bank")
	private String receiveAccountBank;
    /**
     * 收款账户账号
     */
	@TableField("receive_account_number")
	private String receiveAccountNumber;
    /**
     * 提交时间
     */
	@TableField("submit_date")
	private String submitDate;
    /**
     * 提交人
     */
	@TableField(value="submit_user_id",el="submitUser.id")
	private User submitUser;
    /**
     * 审核状态（1草稿2审核中3审核通过4重新调整5放弃6审核不通过）
     */
	@TableField("audit_status")
	private Integer auditStatus;
	@TableField("process_instance_id")
	private String processInstanceId;
    /**
     * 备注
     */
	private String note;
    /**
     * 创建人
     */
	@TableField("creator_id")
	private Long creatorId;
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

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	
	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public Integer getPayObjectType() {
		return payObjectType;
	}

	public void setPayObjectType(Integer payObjectType) {
		this.payObjectType = payObjectType;
	}

	public Integer getIsCost() {
		return isCost;
	}

	public void setIsCost(Integer isCost) {
		this.isCost = isCost;
	}


	public BigDecimal getEstimatePayAmount() {
		return estimatePayAmount;
	}

	public void setEstimatePayAmount(BigDecimal estimatePayAmount) {
		this.estimatePayAmount = estimatePayAmount;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	
	public String getReceiveAccountName() {
		return receiveAccountName;
	}

	public void setReceiveAccountName(String receiveAccountName) {
		this.receiveAccountName = receiveAccountName;
	}

	public String getReceiveAccountBank() {
		return receiveAccountBank;
	}

	public void setReceiveAccountBank(String receiveAccountBank) {
		this.receiveAccountBank = receiveAccountBank;
	}

	public String getReceiveAccountNumber() {
		return receiveAccountNumber;
	}

	public void setReceiveAccountNumber(String receiveAccountNumber) {
		this.receiveAccountNumber = receiveAccountNumber;
	}

	public String getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate;
	}

	
	public Dict getCostType() {
		return costType;
	}

	public void setCostType(Dict costType) {
		this.costType = costType;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public AssessmentCompany getAssessmentCompany() {
		return assessmentCompany;
	}

	public void setAssessmentCompany(AssessmentCompany assessmentCompany) {
		this.assessmentCompany = assessmentCompany;
	}

	public BankSubbranch getBankSubbranch() {
		return bankSubbranch;
	}

	public void setBankSubbranch(BankSubbranch bankSubbranch) {
		this.bankSubbranch = bankSubbranch;
	}

	public OtherPartners getOtherPartners() {
		return otherPartners;
	}

	public void setOtherPartners(OtherPartners otherPartners) {
		this.otherPartners = otherPartners;
	}

	public OrgBankAccount getPayBankAccount() {
		return payBankAccount;
	}

	public void setPayBankAccount(OrgBankAccount payBankAccount) {
		this.payBankAccount = payBankAccount;
	}

	public User getSubmitUser() {
		return submitUser;
	}

	public void setSubmitUser(User submitUser) {
		this.submitUser = submitUser;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
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

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}

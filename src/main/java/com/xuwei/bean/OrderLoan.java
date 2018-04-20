package com.xuwei.bean;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @description: 订单_借款
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月19日 09:29:11
 * @author: hhd
 * @version: 1.0
 */
@TableName(value="t_order_loan",resultMap="BaseResultMap")
public class OrderLoan extends Model<OrderLoan> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 订单
     */
	@TableField(value="order_id",el="order.id")
	private Order order;
    /**
     * 借款金额（万元）
     */
	private BigDecimal amount;
    /**
     * 借款利率
     */
	private BigDecimal rate;
	/**
	 * 利率单位
	 */
	private Integer rateType;
    /**
     * 借款日期
     */
	@TableField("begin_date")
	private String beginDate;
    /**
     * 预计还款日期
     */
	@TableField("estimate_repay_date")
	private String estimateRepayDate;
    /**
     * 预计借款天数
     */
	@TableField("estimate_loan_day")
	private Integer estimateLoanDay;
    /**
     * 预计利息（元）
     */
	@TableField("estimate_interest")
	private BigDecimal estimateInterest;
    /**
     * 参考利息
     */
	@TableField("refer_interest")
	private BigDecimal referInterest;
    /**
     * 还款日期
     */
	@TableField("repay_date")
	private String repayDate;
    /**
     * 借款天数
     */
	@TableField("loan_day")
	private Integer loanDay;
    /**
     * 利息（元）
     */
	private BigDecimal interest;
    /**
     * 应收金额（元）
     */
	@TableField("receive_amount")
	private BigDecimal receiveAmount;
    /**
     * 已收金额（元）
     */
	@TableField("received_amount")
	private BigDecimal receivedAmount;
    /**
     * 未收金额（元）
     */
	@TableField("receiving_amount")
	private BigDecimal receivingAmount;

	@TableField("process_instance_id")
	private String processInstanceId;
    /**
     * 审核状态（1草稿2审核中3审核通过4审核不通过）
     */
	@TableField("audit_status")
	private Integer auditStatus;
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

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEstimateRepayDate() {
		return estimateRepayDate;
	}

	public void setEstimateRepayDate(String estimateRepayDate) {
		this.estimateRepayDate = estimateRepayDate;
	}

	public Integer getEstimateLoanDay() {
		return estimateLoanDay;
	}

	public void setEstimateLoanDay(Integer estimateLoanDay) {
		this.estimateLoanDay = estimateLoanDay;
	}

	public BigDecimal getEstimateInterest() {
		return estimateInterest;
	}

	public void setEstimateInterest(BigDecimal estimateInterest) {
		this.estimateInterest = estimateInterest;
	}

	public BigDecimal getReferInterest() {
		return referInterest;
	}

	public void setReferInterest(BigDecimal referInterest) {
		this.referInterest = referInterest;
	}

	public String getRepayDate() {
		return repayDate;
	}

	public void setRepayDate(String repayDate) {
		this.repayDate = repayDate;
	}

	public Integer getLoanDay() {
		return loanDay;
	}

	public void setLoanDay(Integer loanDay) {
		this.loanDay = loanDay;
	}

	public BigDecimal getInterest() {
		return interest;
	}

	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

	public BigDecimal getReceiveAmount() {
		return receiveAmount;
	}

	public void setReceiveAmount(BigDecimal receiveAmount) {
		this.receiveAmount = receiveAmount;
	}

	public BigDecimal getReceivedAmount() {
		return receivedAmount;
	}

	public void setReceivedAmount(BigDecimal receivedAmount) {
		this.receivedAmount = receivedAmount;
	}

	public BigDecimal getReceivingAmount() {
		return receivingAmount;
	}

	public void setReceivingAmount(BigDecimal receivingAmount) {
		this.receivingAmount = receivingAmount;
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

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public Integer getRateType() {
		return rateType;
	}

	public void setRateType(Integer rateType) {
		this.rateType = rateType;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}

package com.xuwei.bean;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * @description: 收入分配
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月26日 09:36:12
 * @author: xw
 * @version: 1.0
 */
@TableName(value="t_income_distribute",resultMap="BaseResultMap")
public class IncomeDistribute extends Model<IncomeDistribute> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 收入登记
     */
	@TableField(value="income_id",el="income.id")
	private Income income;
    /**
     * 订单收入
     */
	@TableField(value="order_income_id",el="orderIncome.id")
	private OrderIncome orderIncome;
    /**
     * 订单借款
     */
	@TableField(value="order_loan_id",el="orderLoan.id")
	private OrderLoan orderLoan;
    /**
     * 本次分配金额
     */
	private BigDecimal amount;
    /**
     * 备注
     */
	private String note;
	@TableField(exist=false)
	private String costType;
	@TableField(exist=false)
	private String receiveAmount;
	@TableField(exist=false)
	private String receivedAmount;
	@TableField(exist=false)
	private String receivingAmount;
	@TableField(exist=false)
	private Integer type;//1订单收入2回款
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

	public Income getIncome() {
		return income;
	}

	public void setIncome(Income income) {
		this.income = income;
	}

	public OrderIncome getOrderIncome() {
		return orderIncome;
	}

	public void setOrderIncome(OrderIncome orderIncome) {
		this.orderIncome = orderIncome;
	}

	public OrderLoan getOrderLoan() {
		return orderLoan;
	}

	public void setOrderLoan(OrderLoan orderLoan) {
		this.orderLoan = orderLoan;
	}
	
	public String getCostType() {
		return costType;
	}
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setCostType(String costType) {
		this.costType = costType;
	}

	public String getReceiveAmount() {
		return receiveAmount;
	}

	public void setReceiveAmount(String receiveAmount) {
		this.receiveAmount = receiveAmount;
	}

	public String getReceivedAmount() {
		return receivedAmount;
	}

	public void setReceivedAmount(String receivedAmount) {
		this.receivedAmount = receivedAmount;
	}
	
	public String getReceivingAmount() {
		return receivingAmount;
	}

	public void setReceivingAmount(String receivingAmount) {
		this.receivingAmount = receivingAmount;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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

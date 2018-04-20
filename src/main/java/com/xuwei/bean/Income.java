package com.xuwei.bean;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * @description: 收入登记
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月05日 09:07:52
 * @author: zyd
 * @version: 1.0
 */
@TableName(value="t_income",resultMap="BaseResultMap")
public class Income extends Model<Income> {

    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 时间（年月日）
     */
	@TableField("income_time")
	private String incomeTime;
    /**
     * 金额（元）
     */
	private BigDecimal amount;
    /**
     * 订单
     */
	@TableField(value="order_id",el="order.id")
	private Order order;
	/**
	 * 添加的字段: 拥有人
	 */
	@TableField(exist=false)
	private String ownerName;
    /**
     * 是否开票（1是0否）
     */
	private Integer billing;
    /**
     * 付款方式
     */
	@TableField(value="pay_type_id",el="payType.id")
	private Dict payType;
    /**
     * 期次
     */
	@TableField(value="period_time_id",el="periodTime.id")
	private Dict periodTime;
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

	public String getIncomeTime() {
		return incomeTime;
	}

	public void setIncomeTime(String incomeTime) {
		this.incomeTime = incomeTime;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}


	public Order getOrder() {
		return order;
	}


	public void setOrder(Order order) {
		this.order = order;
	}


	public String getOwnerName() {
		return ownerName;
	}


	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public Integer getBilling() {
		return billing;
	}




	public void setBilling(Integer billing) {
		this.billing = billing;
	}




	public Dict getPayType() {
		return payType;
	}




	public void setPayType(Dict payType) {
		this.payType = payType;
	}




	public Dict getPeriodTime() {
		return periodTime;
	}




	public void setPeriodTime(Dict periodTime) {
		this.periodTime = periodTime;
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




	public static long getSerialversionuid() {
		return serialVersionUID;
	}




	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
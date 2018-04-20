package com.xuwei.bean;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * @description: 订单_收入
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月18日 11:12:28
 * @author: xw
 * @version: 1.0
 */
@TableName(value="t_order_income",resultMap="BaseResultMap")
public class OrderIncome extends Model<OrderIncome> {

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
     * 费用类型
     */
	@TableField(value="cost_type_id",el="costType.id")
	private Dict costType;
    /**
     * 预计金额
     */
	@TableField("estimate_amount")
	private BigDecimal estimateAmount;
    /**
     * 实收金额（元）
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

	public Dict getCostType() {
		return costType;
	}

	public void setCostType(Dict costType) {
		this.costType = costType;
	}

	public BigDecimal getEstimateAmount() {
		return estimateAmount;
	}

	public void setEstimateAmount(BigDecimal estimateAmount) {
		this.estimateAmount = estimateAmount;
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

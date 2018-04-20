package com.xuwei.bean;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * @description: 订单_合作银行
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月18日 15:00:36
 * @author: hhd
 * @version: 1.0
 */
@TableName(value="t_order_bank",resultMap="BaseResultMap")
public class OrderBank extends Model<OrderBank> {

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
     * 银行
     */
	@TableField(value = "bank_id",el = "bank.id")
	private Bank bank;
    /**
     * 合作银行支行
     */
	@TableField(value = "subbranch_id",el = "subbranch.id")
	private BankSubbranch subbranch;
    /**
     * 是否签约银行(1签约0未签约)
     */
	@TableField("is_sign")
	private Integer isSign;
    /**
     * 签约时间(年月日)
     */
	@TableField("sing_time")
	private String singTime;
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
	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public BankSubbranch getSubbranch() {
		return subbranch;
	}

	public void setSubbranch(BankSubbranch subbranch) {
		this.subbranch = subbranch;
	}

	public Integer getIsSign() {
		return isSign;
	}

	public void setIsSign(Integer isSign) {
		this.isSign = isSign;
	}

	public String getSingTime() {
		return singTime;
	}

	public void setSingTime(String singTime) {
		this.singTime = singTime;
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

	public void setOrder(Order order) {
		this.order = order;
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

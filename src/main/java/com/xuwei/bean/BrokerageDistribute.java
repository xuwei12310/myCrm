package com.xuwei.bean;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @description: 佣金分配
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月25日 19:57:40
 * @author: caw
 * @version: 1.0
 */
@TableName(value="t_brokerage_distribute",resultMap="BaseResultMap")
public class BrokerageDistribute extends Model<BrokerageDistribute> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 订单Id
     */
	@TableField(value="order_id", el="order.id")
	private Order order;
    /**
     * 拥有人类型
     */
	@TableField("owner_type")
	private Integer ownerType;
    /**
     * 拥有人id
     */
	@TableField(value="owner_id", el="owner.id")
	private User owner;
    /**
     * 参考值
     */
	private BigDecimal reference;
    /**
     * 实际值
     */
	private BigDecimal actual;
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
	@TableField(exist = false)
	private BigDecimal total;
	@TableField(exist = false)
	private BigDecimal grant;
	@TableField(exist = false)
	private BigDecimal ungrant;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(Integer ownerType) {
		this.ownerType = ownerType;
	}

	public BigDecimal getReference() {
		return reference;
	}

	public void setReference(BigDecimal reference) {
		this.reference = reference;
	}

	public BigDecimal getActual() {
		return actual;
	}

	public void setActual(BigDecimal actual) {
		this.actual = actual;
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

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getGrant() {
		return grant;
	}

	public void setGrant(BigDecimal grant) {
		this.grant = grant;
	}

	public BigDecimal getUngrant() {
		return ungrant;
	}

	public void setUngrant(BigDecimal ungrant) {
		this.ungrant = ungrant;
	}
}

package com.xuwei.bean;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * @description: 客户_其他财力
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月04日 09:43:35
 * @author: caw
 * @version: 1.0
 */
@TableName(value="t_customer_other_resources",resultMap="BaseResultMap")
public class CustomerOtherResources extends Model<CustomerOtherResources> {

    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 客户id
     */
	@TableField("customer_id")
	private Long customerId;
    /**
     * 财产名称
     */
	private String name;
    /**
     * 价值(万元)
     */
	private BigDecimal value;
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

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
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

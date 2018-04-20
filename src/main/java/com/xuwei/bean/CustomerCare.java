package com.xuwei.bean;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * @description: 客户关怀
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年08月31日 10:49:27
 * @author: zjy
 * @version: 1.0
 */
@TableName(value="t_care")
public class CustomerCare extends Model<CustomerCare> {

    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 客户id
     */
	@TableField(value="customer_id",el="customer.id")
	private Customer customer;
	 /**
     * 关怀内容
     */
	@TableField("care_content")
	private String careContent;
	 /**
     * 拥有人
     */
	@TableField(value="owner",el="owner.id")
	private User owner;
	/**
     * 处理人
     */
	@TableField(value="handle_id",el="handle.id")
	private User handle;
	 /**
     * 处理时间
     */
	@TableField("handle_time")
	private String handleTime;
	 /**
     * 处理详情
     */
	@TableField("details")
	private String details;
	 /**
     * 状态
     */
	@TableField("status")
	private String status;
	 /**
     * 备注
     */
	@TableField("note")
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
	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getCareContent() {
		return careContent;
	}

	public void setCareContent(String careContent) {
		this.careContent = careContent;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}


	public User getHandle() {
		return handle;
	}

	public void setHandle(User handle) {
		this.handle = handle;
	}

	public String getHandleTime() {
		return handleTime;
	}

	public void setHandleTime(String handleTime) {
		this.handleTime = handleTime;
	}
	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
	
}

package com.xuwei.bean;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * @description: 客户投诉
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年08月31日 10:49:27
 * @author: zjy
 * @version: 1.0
 */
@TableName(value="t_complaint")
public class CustomerComplaint extends Model<CustomerComplaint> {

    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 订单id
     */
    @TableField(value="order_id",el="order.id")
    private Order order;
    /**
     * 客户id
     */
	@TableField(value="customer_id",el="customer.id")
	private Customer customer;
	 /**
     * 客户投诉内容
     */
	@TableField("complaint_content")
	private String complaintContent;
	/**
     * 投诉对象
     */
	@TableField(value="complaint_object_id",el="complaintObject.id")
	private User complaintObject;
	 /**
     * 投诉时间
     */
	@TableField("complaint_time")
	private String complaintTime;
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
	@TableField("handle_details")
	private String handleDetails;
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
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	@Override
	protected Serializable pkVal() {
		return this.id;
	}
	public String getComplaintContent() {
		return complaintContent;
	}

	public void setComplaintContent(String complaintContent) {
		this.complaintContent = complaintContent;
	}

	public User getComplaintObject() {
		return complaintObject;
	}

	public void setComplaintObject(User complaintObject) {
		this.complaintObject = complaintObject;
	}

	public String getComplaintTime() {
		return complaintTime;
	}

	public void setComplaintTime(String complaintTime) {
		this.complaintTime = complaintTime;
	}

	public String getHandleTime() {
		return handleTime;
	}

	public void setHandleTime(String handleTime) {
		this.handleTime = handleTime;
	}

	public String getHandleDetails() {
		return handleDetails;
	}

	public void setHandleDetails(String handleDetails) {
		this.handleDetails = handleDetails;
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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public User getHandle() {
		return handle;
	}

	public void setHandle(User handle) {
		this.handle = handle;
	}
	
}

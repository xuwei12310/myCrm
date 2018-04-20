package com.xuwei.bean;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * @description: 订单_进度
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月18日 17:41:33
 * @author: caw
 * @version: 1.0
 */
@TableName(value="t_order_schedule",resultMap="BaseResultMap")
public class OrderSchedule extends Model<OrderSchedule> {

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
     * 进度
     */
	@TableField(value="schedule_id",el="schedule.id")
	private Schedule schedule;
    /**
     * 预计完成时间(年月日)
     */
	@TableField("estimate_date")
	private String estimateDate;
    /**
     * 实际完成时间(年月日)
     */
	@TableField("actual_date")
	private String actualDate;
    /**
     * 是否完成1完成0未完成
     */
	@TableField("is_complete")
	private Integer isComplete;
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

	@TableField(exist=false)
	private String roleName;
	
	@TableField(exist=false)
	private Long roleUserId;
	
	@TableField(exist=false)
	private String isCompleteName;

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

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public String getEstimateDate() {
		return estimateDate;
	}

	public void setEstimateDate(String estimateDate) {
		this.estimateDate = estimateDate;
	}

	public String getActualDate() {
		return actualDate;
	}

	public void setActualDate(String actualDate) {
		this.actualDate = actualDate;
	}

	public Integer getIsComplete() {
		return isComplete;
	}

	public void setIsComplete(Integer isComplete) {
		this.isComplete = isComplete;
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

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getIsCompleteName() {
		return isCompleteName;
	}

	public void setIsCompleteName(String isCompleteName) {
		this.isCompleteName = isCompleteName;
	}

	public Long getRoleUserId() {
		return roleUserId;
	}

	public void setRoleUserId(Long roleUserId) {
		this.roleUserId = roleUserId;
	}

}

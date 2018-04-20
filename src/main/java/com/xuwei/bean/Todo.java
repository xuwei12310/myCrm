package com.xuwei.bean;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * @description: 待办任务
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月07日 09:56:45
 * @author: zjy
 * @version: 1.0
 */
@TableName(value="t_todo",resultMap="BaseResultMap")
public class Todo extends Model<Todo> {

    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 任务类型(1跟进2日常3投诉4关怀)
     */
	@TableField("task_type")
	private Integer taskType;
    /**
     * 客户id
     */
	@TableField(value="customer_id",el="customerId.id")
	private Customer customerId;
    /**
     * 执行时间(年月日时分)
     */
	@TableField("do_time")
	private String doTime;
    /**
     * 任务内容
     */
	@TableField("task_content")
	private String taskContent;
    /**
     * 跟进id
     */
	@TableField(value="follow_id",el="followId.id")
	private Follow followId;
    /**
     * 客户关怀
     */
	@TableField(value="customer_care_id",el="customerCareId.id")
	private CustomerCare customerCareId;
    /**
     * 客户投诉
     */
	@TableField(value="customer_complain_id",el="customerComplainId.id")
	private CustomerComplaint customerComplainId;
    /**
     * 执行者
     */
	@TableField(value="do_user_id",el="doUserId.id")
	private User doUserId;
    /**
     * 提醒时间
     */
	private BigDecimal remind;
    /**
     * 提醒单位(1天2小时)
     */
	@TableField("remind_unit")
	private Integer remindUnit;
    /**
     * 状态(1进行中2已完成3未完成4已取消)
     */
	private Integer status;
    /**
     * 未完成说明
     */
	private String reason;
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
    /**
     * 附件Id  
     */
	@TableField(exist=false)
	private Long attachId;
	/**
	 * 日期
	 */
	@TableField(exist=false)
	private String date;
	/**
	 * 小时
	 */
	@TableField(exist=false)
	private String hour;
	/**
	 * 分钟
	 */
	@TableField(exist=false)
	private String minute;
	@TableField(exist=false)
	private Long userCreateTimeId;
	/**
	 * 今日明日后日执行
	 */
	@TableField(exist=false)
	private Integer doUserTime;
	/**
	 * 完成ID
	 */
	@TableField(exist=false)
	private Long todoComplectId;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getTaskType() {
		return taskType;
	}

	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}

	public Long getUserCreateTimeId() {
		return userCreateTimeId;
	}

	public void setUserCreateTimeId(Long userCreateTimeId) {
		this.userCreateTimeId = userCreateTimeId;
	}

	public Long getTodoComplectId() {
		return todoComplectId;
	}

	public void setTodoComplectId(Long todoComplectId) {
		this.todoComplectId = todoComplectId;
	}

	public String getDoTime() {
		return doTime;
	}

	public void setDoTime(String doTime) {
		this.doTime = doTime;
	}

	public String getTaskContent() {
		return taskContent;
	}

	public void setTaskContent(String taskContent) {
		this.taskContent = taskContent;
	}


	public BigDecimal getRemind() {
		return remind;
	}

	public void setRemind(BigDecimal remind) {
		this.remind = remind;
	}

	public Integer getDoUserTime() {
		return doUserTime;
	}

	public void setDoUserTime(Integer doUserTime) {
		this.doUserTime = doUserTime;
	}

	public Integer getRemindUnit() {
		return remindUnit;
	}

	public void setRemindUnit(Integer remindUnit) {
		this.remindUnit = remindUnit;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Customer getCustomerId() {
		return customerId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getMinute() {
		return minute;
	}

	public void setMinute(String minute) {
		this.minute = minute;
	}

	public void setCustomerId(Customer customerId) {
		this.customerId = customerId;
	}

	public Follow getFollowId() {
		return followId;
	}

	public void setFollowId(Follow followId) {
		this.followId = followId;
	}

	public CustomerCare getCustomerCareId() {
		return customerCareId;
	}

	public void setCustomerCareId(CustomerCare customerCareId) {
		this.customerCareId = customerCareId;
	}

	public CustomerComplaint getCustomerComplainId() {
		return customerComplainId;
	}

	public void setCustomerComplainId(CustomerComplaint customerComplainId) {
		this.customerComplainId = customerComplainId;
	}

	public User getDoUserId() {
		return doUserId;
	}

	public void setDoUserId(User doUserId) {
		this.doUserId = doUserId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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

	public Long getAttachId() {
		return attachId;
	}

	public void setAttachId(Long attachId) {
		this.attachId = attachId;
	}

}

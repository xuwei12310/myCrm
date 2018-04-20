/**
 * 
 */
package com.xuwei.bean;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * 
 * @description: 产品进度设置
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年8月31日下午4:06:30
 * @author：zjy
 * @version：1.0
 */
@TableName(value="t_product_schedule")
public class Schedule extends Model<Schedule>{
	private static final long serialVersionUID = 1L;
	/**
     * Id
     */
    @TableId(value="id", type= IdType.AUTO)
	private Long id;
	/**
	 * 所属产品
	 */
	@TableField(value="product_id", el="dict.id")
	private Dict dict;
	/**
	 * 进度名称
	 */
	@TableField("schedule_name")
	private String scheduleName;
	/**
	 * 执行角色(1.拥有人、2.跟单人、3.客服负责人、4.客服助理)
	 */
	@TableField("role")
	private Integer role;
	/**
	 * 截止天数
	 */
	@TableField("duration")
	private Integer duration;
	/**
	 * 提醒时间
	 */
	@TableField("remind")
	private Integer remind;
	/**
	 * 提醒单位（1.天    2.小时）
	 */
	@TableField("remind_unit")
	private Integer remindUnit;
	/**
	 * 排序
	 */
	@TableField("array")
	private Integer array;
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
	
	@TableField(exist=false)
	private Long orderScheduleId;
	
	
	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Dict getDict() {
		return dict;
	}

	public void setDict(Dict dict) {
		this.dict = dict;
	}

	public String getScheduleName() {
		return scheduleName;
	}

	public void setScheduleName(String scheduleName) {
		this.scheduleName = scheduleName;
	}

	public Integer getArray() {
		return array;
	}

	public void setArray(Integer array) {
		this.array = array;
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

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Integer getRemind() {
		return remind;
	}

	public void setRemind(Integer remind) {
		this.remind = remind;
	}

	public Integer getRemindUnit() {
		return remindUnit;
	}

	public void setRemindUnit(Integer remindUnit) {
		this.remindUnit = remindUnit;
	}

	public Long getOrderScheduleId() {
		return orderScheduleId;
	}

	public void setOrderScheduleId(Long orderScheduleId) {
		this.orderScheduleId = orderScheduleId;
	}

}

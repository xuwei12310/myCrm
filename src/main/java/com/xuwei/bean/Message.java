package com.xuwei.bean;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * @description: 消息
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月25日 09:27:40
 * @author: caw
 * @version: 1.0
 */
@TableName(value="t_message")
public class Message extends Model<Message> {

    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 消息类型(1任务提前提醒2任务延期提醒)
     */
	@TableField("msg_type")
	private Integer msgType;
    /**
     * 提醒时间（年月日时分）
     */
	@TableField("remind_time")
	private String remindTime;
    /**
     * 信息内容
     */
	@TableField("msg_content")
	private String msgContent;
    /**
     * 通知人
     */
	@TableField("user_id")
	private Long userId;
    /**
     * 待办任务
     */
	@TableField("todo_id")
	private Long todoId;
    /**
     * 状态（1：已读  0：未读）
     */
	private Integer status;
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

	public Integer getMsgType() {
		return msgType;
	}

	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}

	public String getRemindTime() {
		return remindTime;
	}

	public void setRemindTime(String remindTime) {
		this.remindTime = remindTime;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getTodoId() {
		return todoId;
	}

	public void setTodoId(Long todoId) {
		this.todoId = todoId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
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

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}

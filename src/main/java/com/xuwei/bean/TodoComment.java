package com.xuwei.bean;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * @description: 待办任务_评论
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月14日 08:47:17
 * @author: zjy
 * @version: 1.0
 */
@TableName(value="t_todo_comment",resultMap="BaseResultMap")
public class TodoComment extends Model<TodoComment> {

    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 任务
     */
	@TableField(value="todo_id",el="todoId.id")
	private Todo todoId;
    /**
     * 评论内容
     */
	private String comment;
    /**
     * 评论人
     */
	@TableField(value="comment_user_id",el="commentUserId.id")
	private User commentUserId;
	/**
     *评论时间 
     */
	@TableField("comment_time")
	private String commentTime;
	
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


	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Todo getTodoId() {
		return todoId;
	}

	public void setTodoId(Todo todoId) {
		this.todoId = todoId;
	}

	public User getCommentUserId() {
		return commentUserId;
	}

	public void setCommentUserId(User commentUserId) {
		this.commentUserId = commentUserId;
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

	public String getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(String commentTime) {
		this.commentTime = commentTime;
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

package com.xuwei.bean;
import java.io.Serializable;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * @description: 待办任务_抄送
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月11日 09:06:31
 * @author: zjy
 * @version: 1.0
 */
@TableName(value="t_todo_copyto",resultMap="BaseResultMap")
public class TodoCopyto extends Model<TodoCopyto> {

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
     * 抄送人
     */
	@TableField(value="copyto_user_id",el="copytoUserId.id")
	private User copytoUserId;
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
	 * 抄送ID
	 */
	@TableField(exist=false)
	private String copyId;
	/**
	 * 更新抄送人
	 */
	@TableField(exist=false)
	private String copyName;

	public String getCopyId() {
		return copyId;
	}

	public void setCopyId(String copyId) {
		this.copyId = copyId;
	}

	public String getCopyName() {
		return copyName;
	}

	public void setCopyName(String copyName) {
		this.copyName = copyName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public Todo getTodoId() {
		return todoId;
	}

	public void setTodoId(Todo todoId) {
		this.todoId = todoId;
	}

	public User getCopytoUserId() {
		return copytoUserId;
	}

	public void setCopytoUserId(User copytoUserId) {
		this.copytoUserId = copytoUserId;
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

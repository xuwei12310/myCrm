package com.xuwei.bean;


import java.io.Serializable;

/**
 * @description: 实体基类
 * @copyright: 福建骏华信息有限公司 (c)2016
 * @createTime: 2016年8月12日下午5:27:50
 * @author：lys @version：1.0
 */
public class BaseEntity<ID> implements Serializable {

	private static final long serialVersionUID = -34115333603863619L;
	/**
	 * 主键Id
	 */
	protected ID id;
	/**
	 * 创建时间
	 */
	protected String createTime;
	/**
	 * 创建人
	 */
	protected CoreUser creator;

	protected ID creatorId;
	/**
	 * 最后修改时间
	 */
	protected String lastModifyTime;
	/**
	 * 最后修改人
	 */
	protected CoreUser lastModifier;

	protected ID lastModifierId;
	/**
	 * 备注
	 */
	protected String note;

	public CoreUser getLastModifier() {
		return lastModifier;
	}

	public void setLastModifier(CoreUser lastModifier) {
		this.lastModifier = lastModifier;
	}

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public CoreUser getCreator() {
		return creator;
	}

	public void setCreator(CoreUser creator) {
		this.creator = creator;
	}

	public String getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(String lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public ID getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(ID creatorId) {
		this.creatorId = creatorId;
	}

	public ID getLastModifierId() {
		return lastModifierId;
	}

	public void setLastModifierId(ID lastModifierId) {
		this.lastModifierId = lastModifierId;
	}

	public String getResourceKey() {
		return null;
	}

	public String getResourceName() {
		return null;
	}
}

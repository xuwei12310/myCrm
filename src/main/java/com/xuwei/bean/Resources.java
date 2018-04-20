package com.xuwei.bean;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * @description: 
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年07月14日 16:17:01
 * @author: hhd
 * @version: 1.0
 */
@TableName(value="t_resources",resultMap="BaseResultMap")
public class Resources extends Model<Resources> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
	private String name;
	private String identity;
	private String url;
	private Integer weight;
	private String icon;
	private String status;
	@TableField("resources_type")
	private String resourcesType;
	@TableField("create_time")
	private String createTime;
	@TableField("last_modify_time")
	private String lastModifyTime;
	private String note;
	@TableField("parent_id")
	private Long parentId;
	@TableField("parent_ids")
	private String parentIds;
	@TableField("creator_id")
	private Long creatorId;
	@TableField("last_modifier_id")
	private Long lastModifierId;

	@TableField(exist = false)
	private boolean hasChildren;
	@TableField(exist = false)
	private String isCRUD;

	public boolean isHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}

	public String getIsCRUD() {
		return isCRUD;
	}

	public void setIsCRUD(String isCRUD) {
		this.isCRUD = isCRUD;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getResourcesType() {
		return resourcesType;
	}

	public void setResourcesType(String resourcesType) {
		this.resourcesType = resourcesType;
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public Long getLastModifierId() {
		return lastModifierId;
	}

	public void setLastModifierId(Long lastModifierId) {
		this.lastModifierId = lastModifierId;
	}
	public String makeSelfAsNewParentIds() {
		return getParentIds() + getId() + getSeparator();
	}

	public String getSeparator() {
		return "/";
	}

	public boolean isRoot() {
		return false;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}

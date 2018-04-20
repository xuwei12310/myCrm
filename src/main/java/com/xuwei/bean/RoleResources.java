package com.xuwei.bean;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * @description: 
 * @copyright: 福建骏华信息有限公司 (c)2018
 * @createTime: 2018年01月16日 19:57:11
 * @author: xw
 * @version: 1.0
 */
@TableName(value="t_role_resources",resultMap="BaseResultMap")
public class RoleResources extends Model<RoleResources> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
	@TableField("role_id")
	private Long roleId;
	@TableField("resources_id")
	private Long resourcesId;
	@TableField("create_time")
	private String createTime;
	@TableField("last_modify_time")
	private String lastModifyTime;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getResourcesId() {
		return resourcesId;
	}

	public void setResourcesId(Long resourcesId) {
		this.resourcesId = resourcesId;
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

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}

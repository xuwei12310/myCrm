package com.xuwei.bean;

import java.io.Serializable;
import java.util.Date;

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
@TableName(value="t_user")
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
	private String name;
	@TableField("id_number")
	private String idNumber;
	private String email;
	private String username;
	private String password;
	private Integer status;
	@TableField("is_admin")
	private Integer isAdmin;
	
	@TableField("company_id")
	private Organization company;
	@TableField(value = "organization_id" ,el = "organization.id")
	private Organization organization;
	@TableField("create_time")
	private String createTime;
	@TableField("last_modify_time")
	private String lastModifyTime;
	@TableField("is_lock")
	private Integer isLock;
	@TableField("lock_time")
	private Date lockTime;
	@TableField("login_count")
	private Integer loginCount;
	@TableField("login_failure_count")
	private Integer loginFailureCount;
	@TableField("login_time")
	private Date loginTime;
	@TableField("creator_id")
	private Long creatorId;
	@TableField(exist = false)
	private String roleIds;
	@TableField(exist = false)
	private String roles;
    /**
     * 手机
     */
	private String phone;
	@TableField("last_modifier_id")
	private Long lastModifierId;
	@TableField("pwd_prefix")
	private String pwdPrefix;
	@TableField("pwd_suffix")
	private String pwdSuffix;

	
	public Organization getCompany() {
		return company;
	}
	public void setCompany(Organization company) {
		this.company = company;
	}
	public String getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	public Long getId() {
		return id;
	}
	public Organization getOrganization() {
		return organization;
	}
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
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

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
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


	public Integer getIsLock() {
		return isLock;
	}

	public void setIsLock(Integer isLock) {
		this.isLock = isLock;
	}

	public Date getLockTime() {
		return lockTime;
	}

	public void setLockTime(Date lockTime) {
		this.lockTime = lockTime;
	}

	public Integer getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}

	public Integer getLoginFailureCount() {
		return loginFailureCount;
	}

	public void setLoginFailureCount(Integer loginFailureCount) {
		this.loginFailureCount = loginFailureCount;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getLastModifierId() {
		return lastModifierId;
	}

	public void setLastModifierId(Long lastModifierId) {
		this.lastModifierId = lastModifierId;
	}

	public String getPwdPrefix() {
		return pwdPrefix;
	}

	public void setPwdPrefix(String pwdPrefix) {
		this.pwdPrefix = pwdPrefix;
	}

	public String getPwdSuffix() {
		return pwdSuffix;
	}

	public void setPwdSuffix(String pwdSuffix) {
		this.pwdSuffix = pwdSuffix;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}

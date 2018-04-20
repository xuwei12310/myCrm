package com.xuwei.bean;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.KeySequence;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * ${table.comment}
 * </p>
 *
 * @author ${author}
 * @since 2017-12-08
 */
@TableName(value="COREUSER")
@KeySequence("SEQ_COREUSER_USERID")
public class CoreUser extends Model<CoreUser> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户编号
     */
    @TableId(value="USERID",type = IdType.INPUT)
	private Long userid;
    /**
     * 用户名
     */
	@TableField("USERNAME")
	private String username;
    /**
     * 密码
     */
	@TableField("USERPWD")
	private String userpwd;
    /**
     * 昵称
     */
	@TableField("NICKNAME")
	private String nickname;
    /**
     * 电话
     */
	@TableField("TELNO")
	private String telno;
    /**
     * 是否管理员
     */
	@TableField("ISADMIN")
	private String isadmin;
	@TableField(value="ORGANIZATIONID",el="organization.id")
	private Organization organization;
/*
	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}*/
	
	public String getUsername() {
		return username;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserpwd() {
		return userpwd;
	}
	
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getTelno() {
		return telno;
	}

	public void setTelno(String telno) {
		this.telno = telno;
	}

	public String getIsadmin() {
		return isadmin;
	}

	public void setIsadmin(String isadmin) {
		this.isadmin = isadmin;
	}

	@Override
	protected Serializable pkVal() {
		return this.userid;
	}

}

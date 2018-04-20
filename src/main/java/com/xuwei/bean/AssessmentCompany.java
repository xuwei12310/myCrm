package com.xuwei.bean;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 评估公司
 * </p>
 *
 * @author ${author}
 * @since 2017-08-31
 */
@TableName("t_assessment_company")
public class AssessmentCompany extends Model<AssessmentCompany> {

    private static final long serialVersionUID = 1L;

    /**
     * 字典id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 公司名称
     */
	private String name;
    /**
     * 地址
     */
	private String address;
    /**
     * 联系人
     */
	private String contacts;
    /**
     * 联系电话
     */
	private String telephone;
    /**
     * 是否系统内置【是1、否0】
     */
	@TableField("is_sys")
	private Integer isSys;
    /**
     * 排序
     */
	private Integer array;
    /**
     * 状态1启用0禁用
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Integer getIsSys() {
		return isSys;
	}

	public void setIsSys(Integer isSys) {
		this.isSys = isSys;
	}

	public Integer getArray() {
		return array;
	}

	public void setArray(Integer array) {
		this.array = array;
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

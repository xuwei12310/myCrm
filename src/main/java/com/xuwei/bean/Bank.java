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
 * @description:银行
 * @createTime 下午2:18:36
 * @author xw
 *
 */
@TableName(value="t_bank",resultMap="BaseResultMap")
public class Bank extends Model<Bank>{
	private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
	/**
	 * 银行名称
	 */
	@TableField("bank_name")
	private String bankName;
	/*@TableField("bank_code")
	private String bankCode;
	@TableField("address")
	private String address;
	@TableField("contacts")
	private String contacts;
	@TableField("phone")
	private String phone;*/
	/**
	 * 状态 0禁用 1启用
	 */
	@TableField("status")
	private Integer status;
	/**
	 * 是否内置
	 */
	@TableField("is_sys")
	private Integer isSys;
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
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param bankName the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}



/*	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}*/

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the isSys
	 */
	public Integer getIsSys() {
		return isSys;
	}

	/**
	 * @param isSys the isSys to set
	 */
	public void setIsSys(Integer isSys) {
		this.isSys = isSys;
	}

	/**
	 * @return the array
	 */
	public Integer getArray() {
		return array;
	}

	/**
	 * @param array the array to set
	 */
	public void setArray(Integer array) {
		this.array = array;
	}

	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * @return the creatorId
	 */
	public Long getCreatorId() {
		return creatorId;
	}

	/**
	 * @param creatorId the creatorId to set
	 */
	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	/**
	 * @return the createTime
	 */
	public String getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the lastModifyTime
	 */
	public String getLastModifyTime() {
		return lastModifyTime;
	}

	/**
	 * @param lastModifyTime the lastModifyTime to set
	 */
	public void setLastModifyTime(String lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	/**
	 * @return the lastModifierId
	 */
	public Long getLastModifierId() {
		return lastModifierId;
	}

	/**
	 * @param lastModifierId the lastModifierId to set
	 */
	public void setLastModifierId(Long lastModifierId) {
		this.lastModifierId = lastModifierId;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}
	
	
}

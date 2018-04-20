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
 * @description:银行支行
 * @createTime 下午2:18:36
 * @author xw
 *
 */
@TableName(value="t_bank_subbranch",resultMap="BaseResultMap")
public class BankSubbranch extends Model<BankSubbranch>{
	private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
	/**
	 * 所属银行
	 */
	@TableField(value="bank_id", el="bank.id")
	private Bank bank;
	/*private long bankId;
	public long getBankId() {
		return bankId;
	}
	public void setBankId(long bankId) {
		this.bankId=this.bank.getId();
		this.bankId = bankId;
	}
*/
	/**
	 * 支行名称
	 */
	@TableField("subbranch_name")
	private String subbranchName;
	/**
	 * 银行代号
	 */
	@TableField("bank_code")
	private String bankCode;
	/**
	 * 银行地址
	 */
	@TableField("address")
	private String address;
	/**
	 * 联系人
	 */
	@TableField("contacts")
	private String contacts;
	/**
	 * 联系电话
	 */
	@TableField("phone")
	private String phone;
	/**
	 * 排序
	 */
	@TableField("array")
	private Integer array;
	/**
	 * 状态
	 */
	@TableField("status")
	private Integer status;
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
	 * @return the bank
	 */
	public Bank getBank() {
		return bank;
	}

	/**
	 * @param bank the bank to set
	 */
	public void setBank(Bank bank) {
		this.bank = bank;
	}
	
	

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
	 * @return the subbranchName
	 */
	public String getSubbranchName() {
		return subbranchName;
	}

	/**
	 * @param subbranchName the subbranchName to set
	 */
	public void setSubbranchName(String subbranchName) {
		this.subbranchName = subbranchName;
	}
	
	/**
	 * @return the bankCode
	 */
	public String getBankCode() {
		return bankCode;
	}

	/**
	 * @param bankCode the bankCode to set
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the contacts
	 */
	public String getContacts() {
		return contacts;
	}

	/**
	 * @param contacts the contacts to set
	 */
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
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

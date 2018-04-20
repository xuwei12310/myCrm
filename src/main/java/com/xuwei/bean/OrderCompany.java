package com.xuwei.bean;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @description: 订单_评估公司
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月18日 17:46:41
 * @author: hhd
 * @version: 1.0
 */
@TableName(value="t_order_company",resultMap="BaseResultMap")
public class OrderCompany extends Model<OrderCompany> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 订单
     */
	@TableField(value="order_id",el="order.id")
	private Order order;
    /**
     * 评估公司
     */
	@TableField(value="assessment_company_id",el="assessmentCompany.id")
	private AssessmentCompany assessmentCompany;
    /**
     * 开户行
     */
	private String bank;
    /**
     * 账户
     */
	private String account;
    /**
     * 卡号
     */
	@TableField("card_number")
	private String cardNumber;
    /**
     * 评估结算(1是0否)
     */
	@TableField("is_assessment")
	private Integer isAssessment;
    /**
     * 评估费
     */
	@TableField("assessmen_fee")
	private BigDecimal assessmenFee;
    /**
     * 工本费
     */
	private BigDecimal fee;
    /**
     * 是否生成支出单(1是0否)
     */
	@TableField("is_make_bills")
	private Integer isMakeBills;
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
    public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

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

	
	public AssessmentCompany getAssessmentCompany() {
		return assessmentCompany;
	}

	public void setAssessmentCompany(AssessmentCompany assessmentCompany) {
		this.assessmentCompany = assessmentCompany;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Integer getIsAssessment() {
		return isAssessment;
	}

	public void setIsAssessment(Integer isAssessment) {
		this.isAssessment = isAssessment;
	}

	public BigDecimal getAssessmenFee() {
		return assessmenFee;
	}

	public void setAssessmenFee(BigDecimal assessmenFee) {
		this.assessmenFee = assessmenFee;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public Integer getIsMakeBills() {
		return isMakeBills;
	}

	public void setIsMakeBills(Integer isMakeBills) {
		this.isMakeBills = isMakeBills;
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

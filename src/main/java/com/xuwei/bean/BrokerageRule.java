package com.xuwei.bean;

import com.baomidou.mybatisplus.annotations.TableField;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * @description: 佣金规则
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月25日 19:04:18
 * @author: caw
 * @version: 1.0
 */
@TableName(value="t_brokerage_rule",resultMap="BaseResultMap")
public class BrokerageRule extends Model<BrokerageRule> {

    private static final long serialVersionUID = 1L;

    /**
     * 字典id
     */
	private Long id;
    /**
     * 拥有人
     */
	@TableField("rule_owner")
	private BigDecimal ruleOwner;
    /**
     * 跟单人
     */
	@TableField("rule_follow")
	private BigDecimal ruleFollow;
    /**
     * 客服负责人
     */
	@TableField("rule_cs_principal")
	private BigDecimal ruleCsPrincipal;
    /**
     * 客服助理
     */
	@TableField("rule_cs_assistant")
	private BigDecimal ruleCsAssistant;
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

	public BigDecimal getRuleOwner() {
		return ruleOwner;
	}

	public void setRuleOwner(BigDecimal ruleOwner) {
		this.ruleOwner = ruleOwner;
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

	public BigDecimal getRuleFollow() {
		return ruleFollow;
	}

	public void setRuleFollow(BigDecimal ruleFollow) {
		this.ruleFollow = ruleFollow;
	}

	public BigDecimal getRuleCsPrincipal() {
		return ruleCsPrincipal;
	}

	public void setRuleCsPrincipal(BigDecimal ruleCsPrincipal) {
		this.ruleCsPrincipal = ruleCsPrincipal;
	}

	public BigDecimal getRuleCsAssistant() {
		return ruleCsAssistant;
	}

	public void setRuleCsAssistant(BigDecimal ruleCsAssistant) {
		this.ruleCsAssistant = ruleCsAssistant;
	}

}

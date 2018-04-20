package com.xuwei.bean;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 业务_跟进
 * </p>
 *
 * @author xw
 * @since 2017-09-01
 */
@TableName("t_follow")
public class Follow extends Model<Follow> {

    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 客户id
     */
	@TableField(value="customer_id",el="customer.id")
	private Customer customer;
    /**
     * 跟进时间
     */
	@TableField("follow_time")
	private String followTime;
	@TableField(exist=false)
	private String followTimeMins;
	@TableField(exist=false)
	private String followTimeMaxs;
    /**
     * 跟进方式
     */
	@TableField(value="follow_type_id",el="followType.id")
	private Dict followType;
	 /**
     * 跟进阶段
     */
	@TableField(value="follow_stage_id",el="followStage.id")
	private Dict followStage;
    /**
     * 跟进详情
     */
	@TableField("follow_details")
	private String followDetails;
    /**
     * 跟进人员
     */
	@TableField(value="follow_personnel_id",el="followPersonnel.id")
	private User followPersonnel;
    /**
     * 来源1直接创建2任务创建
     */
	private Integer source;
    /**
     * 更新客户状态
     */
	@TableField(value="front_status_id",el="frontStatus.id")
	private Dict frontStatus;
    /**
     * 跟进后客户状态
     */
	@TableField(value="after_status_id",el="afterStatus.id")
	private Dict afterStatus;
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

	
	public String getFollowTimeMins() {
		return followTimeMins;
	}

	public void setFollowTimeMins(String followTimeMins) {
		this.followTimeMins = followTimeMins;
	}

	public String getFollowTimeMaxs() {
		return followTimeMaxs;
	}

	public void setFollowTimeMaxs(String followTimeMaxs) {
		this.followTimeMaxs = followTimeMaxs;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getFollowTime() {
		return followTime;
	}

	public void setFollowTime(String followTime) {
		this.followTime = followTime;
	}


	public Dict getFollowType() {
		return followType;
	}

	public void setFollowType(Dict followType) {
		this.followType = followType;
	}
	
	public Dict getFollowStage() {
		return followStage;
	}

	public void setFollowStage(Dict followStage) {
		this.followStage = followStage;
	}

	public String getFollowDetails() {
		return followDetails;
	}

	public void setFollowDetails(String followDetails) {
		this.followDetails = followDetails;
	}

	public User getFollowPersonnel() {
		return followPersonnel;
	}

	public void setFollowPersonnel(User followPersonnel) {
		this.followPersonnel = followPersonnel;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	

	public Dict getFrontStatus() {
		return frontStatus;
	}

	public void setFrontStatus(Dict frontStatus) {
		this.frontStatus = frontStatus;
	}

	public Dict getAfterStatus() {
		return afterStatus;
	}

	public void setAfterStatus(Dict afterStatus) {
		this.afterStatus = afterStatus;
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

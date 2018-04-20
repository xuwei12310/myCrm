package com.xuwei.bean;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * @description: 客户
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年08月31日 10:49:27
 * @author: caw
 * @version: 1.0
 */
@TableName(value="t_customer",resultMap="BaseResultMap")
public class Customer extends Model<Customer> {

    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 客户类型1个人2企业
     */
	@TableField("customer_type")
	private Integer customerType;
    /**
     * 客户名称
     */
	@TableField("customer_name")
	private String customerName;
    /**
     * 性别1男2女
     */
	private Integer sex;
    /**
     * 手机号
     */
	@TableField("mobile_phone")
	private String mobilePhone;
    /**
     * 固定电话
     */
	private String telephone;
    /**
     * 邮箱
     */
	private String email;
    /**
     * 身份证号
     */
	@TableField("id_number")
	private String idNumber;
    /**
     * 身份证地址
     */
	@TableField("id_address")
	private String idAddress;
    /**
     * 客户来源
     */
	@TableField(value="customer_source_id",el="customerSource.id")
	private Dict customerSource;
    /**
     * 客户阶段
     */
	@TableField(value="customer_stage_id",el="customerStage.id")
	private Dict customerStage;
    /**
     * 客户状态
     */
	@TableField(value="customer_status_id",el="customerStatus.id")
	private Dict customerStatus;
    /**
     * 拥有人
     */
	@TableField(value="owner_id",el="owner.id")
	private User owner;
    /**
     * 智能评级
     */
	@TableField("grade_id")
	private Long gradeId;
    /**
     * 户籍地
     */
	@TableField(value="place_id",el="place.id")
	private Area place;
    /**
     * 婚姻状况
     */
	@TableField("marital_id")
	private Long maritalId;
    /**
     * 居住地区
     */
	@TableField(value="live_area_id",el="liveArea.id")
	private Area liveArea;
    /**
     * 居住小区
     */
	@TableField(value="live_plot_id",el="livePlot.id")
	private Plot livePlot;
    /**
     * 出生日期
     */
	private String birthdate;
    /**
     * 就职单位
     */
	private String company;
    /**
     * 职业
     */
	private String occupation;
    /**
     * 配偶姓名
     */
	@TableField("spouse_name")
	private String spouseName;
    /**
     * 配偶户籍地
     */
	@TableField(value="spouse_place_id",el="spousePlace.id")
	private Area spousePlace;
    /**
     * 配偶手机号
     */
	@TableField("spouse_mobile_phone")
	private String spouseMobilePhone;
    /**
     * 配偶身份证号
     */
	@TableField("spouse_id_number")
	private String spouseIdNumber;
    /**
     * 配偶就职单位
     */
	@TableField("spouse_company")
	private String spouseCompany;
    /**
     * 配偶职业
     */
	@TableField("spouse_occupation")
	private String spouseOccupation;
    /**
     * 信用等级
     */
	@TableField("credit_rating_id")
	private Long creditRatingId;
    /**
     * 信用等级附件
     */
	@TableField("credit_rating_attach_id")
	private Long creditRatingAttachId;
    /**
     * 最后跟进时间(年月日）
     */
	@TableField("last_track_time")
	private String lastTrackTime;
    /**
     * 照片
     */
	@TableField("photo_id")
	private Long photoId;

	@TableField("wx_open_id")
	private String wxOpenId;
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

	public Integer getCustomerType() {
		return customerType;
	}

	public void setCustomerType(Integer customerType) {
		this.customerType = customerType;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getIdAddress() {
		return idAddress;
	}

	public void setIdAddress(String idAddress) {
		this.idAddress = idAddress;
	}

	public Long getGradeId() {
		return gradeId;
	}

	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}

	public Long getMaritalId() {
		return maritalId;
	}

	public void setMaritalId(Long maritalId) {
		this.maritalId = maritalId;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getSpouseName() {
		return spouseName;
	}

	public void setSpouseName(String spouseName) {
		this.spouseName = spouseName;
	}

	public String getSpouseMobilePhone() {
		return spouseMobilePhone;
	}

	public void setSpouseMobilePhone(String spouseMobilePhone) {
		this.spouseMobilePhone = spouseMobilePhone;
	}

	public String getSpouseIdNumber() {
		return spouseIdNumber;
	}

	public void setSpouseIdNumber(String spouseIdNumber) {
		this.spouseIdNumber = spouseIdNumber;
	}

	public String getSpouseCompany() {
		return spouseCompany;
	}

	public void setSpouseCompany(String spouseCompany) {
		this.spouseCompany = spouseCompany;
	}

	public String getSpouseOccupation() {
		return spouseOccupation;
	}

	public void setSpouseOccupation(String spouseOccupation) {
		this.spouseOccupation = spouseOccupation;
	}

	public Long getCreditRatingId() {
		return creditRatingId;
	}

	public void setCreditRatingId(Long creditRatingId) {
		this.creditRatingId = creditRatingId;
	}

	public Long getCreditRatingAttachId() {
		return creditRatingAttachId;
	}

	public void setCreditRatingAttachId(Long creditRatingAttachId) {
		this.creditRatingAttachId = creditRatingAttachId;
	}

	public String getLastTrackTime() {
		return lastTrackTime;
	}

	public void setLastTrackTime(String lastTrackTime) {
		this.lastTrackTime = lastTrackTime;
	}

	public Long getPhotoId() {
		return photoId;
	}

	public void setPhotoId(Long photoId) {
		this.photoId = photoId;
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

	public Dict getCustomerSource() {
		return customerSource;
	}

	public void setCustomerSource(Dict customerSource) {
		this.customerSource = customerSource;
	}

	public Dict getCustomerStage() {
		return customerStage;
	}

	public void setCustomerStage(Dict customerStage) {
		this.customerStage = customerStage;
	}

	public Dict getCustomerStatus() {
		return customerStatus;
	}

	public void setCustomerStatus(Dict customerStatus) {
		this.customerStatus = customerStatus;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Area getPlace() {
		return place;
	}

	public void setPlace(Area place) {
		this.place = place;
	}

	public Area getLiveArea() {
		return liveArea;
	}

	public void setLiveArea(Area liveArea) {
		this.liveArea = liveArea;
	}

	public Plot getLivePlot() {
		return livePlot;
	}

	public void setLivePlot(Plot livePlot) {
		this.livePlot = livePlot;
	}

	public Area getSpousePlace() {
		return spousePlace;
	}

	public void setSpousePlace(Area spousePlace) {
		this.spousePlace = spousePlace;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}
	//导出字段
	@TableField(value="customerStatusName",exist=false)
	private String customerStatusName;
	
	@TableField(value="customerSourceName",exist=false)
	private String customerSourceName;
	
	@TableField(value="customerStageName",exist=false)
	private String customerStageName;
	
	@TableField(value="ownerName",exist=false)
	private String ownerName;
	
	@TableField(value="placeShowName",exist=false)
	private String placeShowName;
	
	@TableField(value="liveAreaShowName",exist=false)
	private String liveAreaShowName;
	
	@TableField(value="livePlotPlotName",exist=false)
	private String livePlotPlotName;
	
	@TableField(value="spousePlaceShowName",exist=false)
	private String spousePlaceShowName;

	public String getCustomerStatusName() {
		return customerStatusName;
	}

	public void setCustomerStatusName(String customerStatusName) {
		this.customerStatusName = customerStatusName;
	}

	public String getCustomerSourceName() {
		return customerSourceName;
	}

	public void setCustomerSourceName(String customerSourceName) {
		this.customerSourceName = customerSourceName;
	}

	public String getCustomerStageName() {
		return customerStageName;
	}

	public void setCustomerStageName(String customerStageName) {
		this.customerStageName = customerStageName;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getPlaceShowName() {
		return placeShowName;
	}

	public void setPlaceShowName(String placeShowName) {
		this.placeShowName = placeShowName;
	}

	public String getLiveAreaShowName() {
		return liveAreaShowName;
	}

	public void setLiveAreaShowName(String liveAreaShowName) {
		this.liveAreaShowName = liveAreaShowName;
	}

	public String getLivePlotPlotName() {
		return livePlotPlotName;
	}

	public void setLivePlotPlotName(String livePlotPlotName) {
		this.livePlotPlotName = livePlotPlotName;
	}

	public String getSpousePlaceShowName() {
		return spousePlaceShowName;
	}

	public void setSpousePlaceShowName(String spousePlaceShowName) {
		this.spousePlaceShowName = spousePlaceShowName;
	}
	
	@TableField(value="createTimeMin",exist=false)
	private String createTimeMin;
	
	@TableField(value="createTimeMax",exist=false)
	private String createTimeMax;
	
	@TableField(value="lastTrackTimeMin",exist=false)
	private String lastTrackTimeMin;
	
	@TableField(value="lastTrackTimeMax",exist=false)
	private String lastTrackTimeMax;
	
	@TableField(value="customSql",exist=false)
	private String customSql;

	public String getCreateTimeMin() {
		return createTimeMin;
	}

	public void setCreateTimeMin(String createTimeMin) {
		this.createTimeMin = createTimeMin;
	}

	public String getCreateTimeMax() {
		return createTimeMax;
	}

	public void setCreateTimeMax(String createTimeMax) {
		this.createTimeMax = createTimeMax;
	}

	public String getLastTrackTimeMin() {
		return lastTrackTimeMin;
	}

	public void setLastTrackTimeMin(String lastTrackTimeMin) {
		this.lastTrackTimeMin = lastTrackTimeMin;
	}

	public String getLastTrackTimeMax() {
		return lastTrackTimeMax;
	}

	public void setLastTrackTimeMax(String lastTrackTimeMax) {
		this.lastTrackTimeMax = lastTrackTimeMax;
	}

	public String getCustomSql() {
		return customSql;
	}

	public void setCustomSql(String customSql) {
		this.customSql = customSql;
	}

	public String getWxOpenId() {
		return wxOpenId;
	}

	public void setWxOpenId(String wxOpenId) {
		this.wxOpenId = wxOpenId;
	}
}

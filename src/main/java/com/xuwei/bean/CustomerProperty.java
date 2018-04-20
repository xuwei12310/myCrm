package com.xuwei.bean;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @description: 客户_产权
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月04日 09:42:59
 * @author: caw
 * @version: 1.0
 */
@TableName(value="t_customer_property",resultMap="BaseResultMap")
public class CustomerProperty extends Model<CustomerProperty> {

    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 客户
     */
	@TableField("customer_id")
	private Long customerId;
    /**
     * 产权证号
     */
	private String certificate;
    /**
     * 所有人
     */
	private String owner;
    /**
     * 是否共同情况1是0否
     */
	@TableField("is_common")
	private String isCommon;
    /**
     * 面积
     */
	private String area;
    /**
     * 房屋性质
     */
	@TableField(value="housing_nature_id", el="housingNature.id")
	private Dict housingNature;
    /**
     * 所在地区
     */
	@TableField(value="area_id",  el="areaId.id")
	private Area areaId;
    /**
     * 小区
     */
	@TableField(value="plot_id", el="plotId.id")
	private Plot plotId;
    /**
     * 房屋地址
     */
	@TableField("house_address")
	private String houseAddress;
    /**
     * 有无土地证(1有 0 无)
     */
	@TableField("hava_land_certificate")
	private Integer havaLandCertificate;
    /**
     * 土地证号
     */
	@TableField("land_certificate_number")
	private String landCertificateNumber;
    /**
     * 土地性质
     */
	@TableField(value="land_nature_id",el="landNature.id")
	private Dict landNature;
    /**
     * 房产价值
     */
	@TableField("property_value")
	private BigDecimal propertyValue;
    /**
     * 附件
     */
	@TableField("attach_id")
	private Long attachId;
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

	@TableField(exist=false)
	private String isCommonName;
	
	@TableField(exist=false)
	private String havaLandCertificateName;

	public String getIsCommonName() {
		return isCommonName;
	}

	public void setIsCommonName(String isCommonName) {
		this.isCommonName = isCommonName;
	}

	public String getHavaLandCertificateName() {
		return havaLandCertificateName;
	}

	public void setHavaLandCertificateName(String havaLandCertificateName) {
		this.havaLandCertificateName = havaLandCertificateName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getIsCommon() {
		return isCommon;
	}

	public void setIsCommon(String isCommon) {
		this.isCommon = isCommon;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Area getAreaId() {
		return areaId;
	}

	public void setAreaId(Area areaId) {
		this.areaId = areaId;
	}

	public Plot getPlotId() {
		return plotId;
	}

	public void setPlotId(Plot plotId) {
		this.plotId = plotId;
	}

	public String getHouseAddress() {
		return houseAddress;
	}

	public void setHouseAddress(String houseAddress) {
		this.houseAddress = houseAddress;
	}

	public Integer getHavaLandCertificate() {
		return havaLandCertificate;
	}

	public void setHavaLandCertificate(Integer havaLandCertificate) {
		this.havaLandCertificate = havaLandCertificate;
	}

	public String getLandCertificateNumber() {
		return landCertificateNumber;
	}

	public void setLandCertificateNumber(String landCertificateNumber) {
		this.landCertificateNumber = landCertificateNumber;
	}

	public Dict getHousingNature() {
		return housingNature;
	}

	public void setHousingNature(Dict housingNature) {
		this.housingNature = housingNature;
	}

	public Dict getLandNature() {
		return landNature;
	}

	public void setLandNature(Dict landNature) {
		this.landNature = landNature;
	}

	public BigDecimal getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(BigDecimal propertyValue) {
		this.propertyValue = propertyValue;
	}

	public Long getAttachId() {
		return attachId;
	}

	public void setAttachId(Long attachId) {
		this.attachId = attachId;
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

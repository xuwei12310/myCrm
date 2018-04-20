package com.xuwei.bean;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * @description: 区/县
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年08月29日 10:52:26
 * @author: wwh
 * @version: 1.0
 */
@TableName(value="t_area",resultMap="BaseResultMap")
public class Area extends Model<Area> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
	private Long id;
    /**
     * 省份
     */
	@TableField(value="province_id",el="province.id")
	private Province province;
    /**
     * 城市
     */
	@TableField(value = "city_id" ,el ="city.id")
	private City city;
    /**
     * 区/县编号
     */
	@TableField("area_code")
	private String areaCode;
    /**
     * 区/县名称
     */
	@TableField("area_name")
	private String areaName;
    /**
     * 显示名称
     */
	@TableField("show_name")
	private String showName;
    /**
     * 状态1启用0禁用
     */
	private Integer status;
    /**
     * 排序
     */
	private Integer array;
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
	public Province getProvince() {
		return province;
	}
	public void setProvince(Province province) {
		this.province = province;
	}
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getArray() {
		return array;
	}

	public void setArray(Integer array) {
		this.array = array;
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

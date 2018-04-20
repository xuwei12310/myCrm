package com.xuwei.bean;

import java.io.Serializable;

/**
 * @Description:Base实体
 * @Copyright: 福州骏华信息有限公司 (c)2015
 * @Created Date : 2015-1-23
 * @author lys
 * @vesion 1.0
 */
public class BaseTreeEntity<ID extends Serializable>  extends BaseEntity<ID>{
	
	private static final long serialVersionUID = 348559665781241965L;
	/**
	 * 节点名
	 */
	private String name;
	/**
	 * 父节点
	 */
	private ID parentId;
	/**
	 * 父路径
	 */
	private String parentIds;
	/**
	 * 图标
	 */
	private String icon;
	/**
	 * 权重（排序）
	 */
	private Integer weight;
	/**
	 * 排序
	 */
	private Integer array;
	
	
	public Integer getArray() {
		return array;
	}

	public void setArray(Integer array) {
		this.array = array;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public ID getParentId() {
		return parentId;
	}

	public void setParentId(ID parentId) {
		this.parentId = parentId;
	}
	
	public String getParentIds() {
		return this.parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getWeight() {
		return this.weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	
	public String makeSelfAsNewParentIds() {
		if(getParentIds()!=null){
			return getParentIds() + getId() + getSeparator();
		}
		return getId() + getSeparator();
	}

	public String getSeparator() {
		return "/";
	}
}

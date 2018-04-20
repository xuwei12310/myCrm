package com.xuwei.bean;

import com.baomidou.mybatisplus.annotations.TableName;

/**
 * @description: 机构
 * @copyright: 福建骏华信息有限公司 (c)2018
 * @createTime: 2018年01月16日 19:57:11
 * @author: xw
 * @version: 1.0
 */
@TableName(value="t_organization")
public class Organization extends BaseTreeEntity<Long> implements Treeable<Long> {

    private static final long serialVersionUID = 1L;

    /**
	 * 排序
	 */
	private Integer array;
    private boolean hasChildren;
    private String  organizationName;
    private Integer orgType;
   
    
	
	public Integer getOrgType() {
		return orgType;
	}
	public void setOrgType(Integer orgType) {
		this.orgType = orgType;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public Integer getArray() {
		return array;
	}
	public void setArray(Integer array) {
		this.array = array;
	}
	
	@Override
	public boolean isHasChildren() {
		return this.hasChildren;
	}

	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}
	@Override
	public boolean isRoot() {
		return false;
	}
	
	@Override
	public boolean isLeaf() {
		return false;
	}
}

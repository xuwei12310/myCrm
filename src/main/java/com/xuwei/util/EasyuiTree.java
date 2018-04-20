package com.xuwei.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Description: easyui 树节点
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-10-14
 * @author lys
 * @vesion 1.0
 */
public class EasyuiTree implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4833154024150795386L;
	/**
	 * @Description:节点打开关闭状态
	 * @Copyright: 福州骏华信息有限公司 (c)2012
	 * @Created Date : 2012-10-14
	 * @author lys
	 * @vesion 1.0
	 */
	public enum StateType{
		open, closed
	}
	private Object id;
	private String text;
	private String iconCls;
	private Boolean checked;
	private StateType state;
	private Map<String,Object> attributes = new HashMap<String, Object>();
	private List<EasyuiTree> children = new ArrayList<EasyuiTree>();
	private Object pid;

    public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	public StateType getState() {
		return state;
	}
	public void setState(StateType state) {
		this.state = state;
	}
	public Map<String, Object> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	public List<EasyuiTree> getChildren() {
		return children;
	}
	public void setChildren(List<EasyuiTree> children) {
		this.children = children;
	}
	public Object getId() {
		return id;
	}
	public void setId(Object id) {
		this.id = id;
	}
	public Object getPid() {
		return pid;
	}
	public void setPid(Object pid) {
		this.pid = pid;
	}
	
}

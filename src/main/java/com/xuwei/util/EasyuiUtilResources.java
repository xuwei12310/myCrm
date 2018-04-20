package com.xuwei.util;

import java.util.List;

import com.google.common.collect.Lists;
import com.xuwei.bean.Resources;
import com.xuwei.util.EasyuiTree.StateType;

/**
 * @Description: 处理json数据的工具类
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2017-7-13
 * @author hhd
 * @vesion 1.0
 */
public class EasyuiUtilResources{
	
	public  List<EasyuiTree> convertToEasyuiTreeList(List<Resources> models) {
		List<EasyuiTree> trees = Lists.newArrayList();
		if (models == null || models.isEmpty()) {
			return trees;
		}

		for (Resources m : models) {
			EasyuiTree tree = convertToEasyuiTree(m);
			trees.add(tree);
		}
		return trees;
	}
	
	private EasyuiTree convertToEasyuiTree(Resources m) {
		EasyuiTree tree = new EasyuiTree();
		tree.setId(m.getId());
		tree.setText(m.getName());
		tree.setIconCls(m.getIcon());
		tree.setPid(m.getParentId());
		tree.setChecked(false);
		if (m.isHasChildren()) {
			tree.setState(StateType.closed);
		}
		if(m instanceof Resources){
			Resources resources = m;
			tree.getAttributes().put("url", resources.getUrl());
		}
		return tree;
	}
}

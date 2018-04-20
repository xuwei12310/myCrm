package com.xuwei.service;

import java.util.List;

import com.xuwei.bean.Resources;
import com.xuwei.bean.User;

/**
 * @description: 服务类
 * @copyright: 福建骏华信息有限公司 (c)2018
 * @createTime: 2018年01月16日 19:57:11
 * @author: xw
 * @version: 1.0
 */
public interface IMainService {

	/**
	 * @description:
	 * @createTime 2018年2月9日 下午1:13:33
	 * @author xw
	 * @param user
	 */
	List<Resources> findRootResourcesByUser(User user);

	/**
	 * @description:查找树的子节点
	 * @createTime 2018年2月9日 下午6:59:22
	 * @author xw
	 * @param parentId
	 * @param user
	 * @return
	 */
	List<Resources> findMenuTreetResources(Long parentId, User user);
	

}

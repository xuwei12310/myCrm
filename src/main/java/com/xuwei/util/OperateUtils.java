package com.xuwei.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.xuwei.bean.User;
import com.xuwei.realm.Principal;

import java.io.Serializable;

/**
 * @description: 当前操作员工具类
 * @copyright: 福建骏华信息有限公司 (c)2016
 * @createTime: 2016年8月22日下午3:57:15
 * @author：lys
 * @version：1.0
 */
public class OperateUtils implements Serializable {

	private static final long serialVersionUID = 551999135178962485L;

	
	public static User getCurrentUser(){
		Subject localSubject = SecurityUtils.getSubject();
		if (localSubject != null) {
			Principal localPrincipal = (Principal) localSubject.getPrincipal();
			if (localPrincipal != null){
				User user = new User();
				user.setId(localPrincipal.getId());
				user.setUsername(localPrincipal.getUsername());
				user.setName(localPrincipal.getName());
				return user;
			}
		}
		return null;

	}
	
	
	/**
	 * @description: 取得当前用户id
	 * @createTime: 2016年8月24日 下午2:39:29
	 * @author: lys
	 * @return
	 */
	public static Long getCurrentUserId(){
		User currUser = getCurrentUser();
		if(currUser!=null){
			return currUser.getId();
		}
		return null;

	}
	/**
	 * @description: 当前用户是否拥有角色
	 * @createTime: 2016年4月13日 上午9:13:31
	 * @author: lys
	 * @param roleName
	 * @return
	 */
	public static boolean hasRole(String roleName){
		Subject localSubject = SecurityUtils.getSubject();
		if (localSubject != null) {
			Subject currentUser = SecurityUtils.getSubject();
			return currentUser.hasRole(roleName);
		}
		return false;
	}

}

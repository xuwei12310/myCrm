package com.xuwei.service;

import com.xuwei.bean.UserRole;
import com.xuwei.util.ServiceResult;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
 
/**
 * @description: 服务类
 * @copyright: 福建骏华信息有限公司 (c)2018
 * @createTime: 2018年01月16日 19:57:11
 * @author: xw
 * @version: 1.0
 */
public interface IUserRoleService extends IService<UserRole> {
	List<Long> getGrantRole(Long userId);
	/**
	 * @description:授权
	 * @createTime 2018年1月18日 下午5:58:35
	 * @author xw
	 * @param id
	 * @param substring
	 * @return
	 */
	ServiceResult grantRole(Long id, String substring);
	/**
	 * @description:
	 * @createTime 2017年8月30日 上午9:01:35
	 * @author xw
	 * @param idArray
	 * @return 
	 */
    ServiceResult deleteByUserId(String[] idArray);
}

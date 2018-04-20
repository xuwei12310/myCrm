package com.xuwei.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xuwei.bean.CustomerLiabilities;
import com.xuwei.util.ServiceResult;
 
/**
 * @description: 客户_其他财力服务类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月04日 09:44:17
 * @author: caw
 * @version: 1.0
 */
public interface ICustomerLiabilitiesService extends IService<CustomerLiabilities> {
	/**
	 * 
	 * @description: 获取负债（微信端）
	 * @createTime: 2017年9月15日 上午9:31:26
	 * @author: caw
	 * @param page
	 * @param userId
	 * @param totalNum
	 * @param customerid
	 * @return
	 */
	ServiceResult getLiabilitiesList(Page<CustomerLiabilities> page, Long userId, Long totalNum, Long customerid);
	
	/**
	 * 
	 * @description: 新增负债（微信端）
	 * @createTime: 2017年9月15日 上午9:32:13
	 * @author: caw
	 * @param m
	 * @param userId
	 * @return
	 */
	ServiceResult addLiabilities(CustomerLiabilities m, Long userId);
	
	/**
	 * 
	 * @description: 修改负债（微信端）
	 * @createTime: 2017年9月15日 上午9:32:20
	 * @author: caw
	 * @param m
	 * @param userId
	 * @return
	 */
	ServiceResult modLiabilities(CustomerLiabilities m, Long userId);
}

package com.xuwei.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xuwei.bean.CustomerOtherResources;
import com.xuwei.util.ServiceResult;
 
/**
 * @description: 客户_其他财力服务类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月04日 09:43:35
 * @author: caw
 * @version: 1.0
 */
public interface ICustomerOtherResourcesService extends IService<CustomerOtherResources> {
	
	/**
	 * 
	 * @description: 获取其他财产（微信端）
	 * @createTime: 2017年9月15日 上午9:04:39
	 * @author: caw
	 * @param page
	 * @param userId
	 * @param totalNum
	 * @param customerid
	 * @return
	 */
	ServiceResult getOtherResourcesList(Page<CustomerOtherResources> page, Long userId, Long totalNum, Long customerid);
	
	/**
	 * 
	 * @description: 新增其他财产（微信端）
	 * @createTime: 2017年9月15日 上午9:05:43
	 * @author: caw
	 * @param m
	 * @param userId
	 * @return
	 */
	ServiceResult addOtherResources(CustomerOtherResources m, Long userId);
	
	/**
	 * 
	 * @description: 修改其他财产（微信端）
	 * @createTime: 2017年9月15日 上午9:05:57
	 * @author: caw
	 * @param m
	 * @param userId
	 * @return
	 */
	ServiceResult modOtherResources(CustomerOtherResources m, Long userId);
}

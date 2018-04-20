package com.xuwei.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xuwei.bean.CustomerViewHistory;
import com.xuwei.util.ServiceResult;
 
/**
 * @description: 客户_浏览历史服务类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月06日 09:12:17
 * @author: caw
 * @version: 1.0
 */
public interface ICustomerViewHistoryService extends IService<CustomerViewHistory> {
	
	/**
	 * 
	 * @description: 查询客户浏览历史记录
	 * @createTime: 2017年9月6日 下午3:59:12
	 * @author: caw
	 * @param pageM
	 * @param m
	 * @return
	 */
	List<CustomerViewHistory> listCustomerViewHistoryByPage(Page<CustomerViewHistory> pageM, CustomerViewHistory m);
	
	/**
	 * 
	 * @description: 新增浏览历史
	 * @createTime: 2017年9月6日 下午4:34:53
	 * @author: caw
	 * @param m
	 * @return
	 */
	ServiceResult createCustomerViewHistory(CustomerViewHistory m, Long customerId);
}

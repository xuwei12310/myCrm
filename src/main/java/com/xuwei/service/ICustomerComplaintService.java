package com.xuwei.service;
import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xuwei.bean.Customer;
import com.xuwei.bean.CustomerComplaint;
import com.xuwei.bean.Order;
import com.xuwei.bean.User;
 

/**
 * @description: 客户投诉
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年9月3日下午2:34:16
 * @author：zjy
 * @version：1.0
 */
public interface ICustomerComplaintService extends IService<CustomerComplaint> {
	
	/**
	 * 
	 * @description:  查询客户投诉列表
	 * @createTime: 2017年9月3日 下午2:34:59
	 * @author: zjy
	 * @param pageM
	 * @param m
	 * @return
	 */
	List<CustomerComplaint> listCustomerComplaintByPage(Page<CustomerComplaint> pageM, CustomerComplaint m,int viewType);
	
	
	/**
	 * 
	 * @description: 获取客户信息
	 * @createTime: 2017年9月4日 上午10:14:53
	 * @author: zjy
	 * @param pageM
	 * @return
	 */
	List<Customer> getCustemerByList(Page<Customer> pageM,String customerName);
	
	/**
	 * 
	 * @description: 获取用户信息
	 * @createTime: 2017年9月4日 下午2:30:50
	 * @author: zjy
	 * @param pageM
	 * @return
	 */
	List<User> getHandleByList(Page<User> pageM,String userName);
	
	
	
	/**
	 * 
	 * @description: 获取订单信息
	 * @createTime: 2017年9月5日 上午9:37:22
	 * @author: zjy
	 * @param pageM
	 * @param orderCode
	 * @return
	 */
	List<Order> getOrderByList(Page<Order> pageM,String orderCode,String customerId);
}

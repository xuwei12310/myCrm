package com.xuwei.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xuwei.bean.Customer;
import com.xuwei.bean.CustomerComplaint;
import com.xuwei.bean.Order;
import com.xuwei.bean.User;

/**
 * @description: 客户投诉Mapper接口
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年08月31日 10:49:27
 * @author: zjy
 * @version: 1.0
 */
public interface CustomerComplaintMapper extends BaseMapper<CustomerComplaint> {
	
	/**
	 * 
	 * @description: 获取列表
	 * @createTime: 2017年9月4日 上午10:19:53
	 * @author: zjy
	 * @param pageM
	 * @param m
	 * @return
	 */
	List<CustomerComplaint> listCustomerComplaintByPage(Page<CustomerComplaint> pageM,@Param("m")CustomerComplaint m,@Param("viewType")int viewType);
	/**
	 * @description: 获取客户信息
	 * @createTime: 2017年9月4日 上午10:19:39
	 * @author: zjy
	 * @param pageM
	 * @return
	 */
	List<Customer> getCustemerByList(Page<Customer> pageM,@Param("customerName")String customerName);
	
	
	/**
	 * 
	 * @description: 获取用户信息
	 * @createTime: 2017年9月4日 下午2:34:22
	 * @author: zjy
	 * @param pageM
	 * @return
	 */
	List<User> getHandleByList(Page<User> pageM, @Param("userName")String userName);
	/**
	 * 
	 * @description: 获取订单
	 * @createTime: 2017年9月4日 下午2:34:22
	 * @author: zjy
	 * @param pageM
	 * @return
	 */
	List<Order> getOrderByList(Page<Order> pageM, @Param("orderCode")String orderCode,@Param("customerId")String customerId);
}
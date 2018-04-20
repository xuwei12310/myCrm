package com.xuwei.service.impl;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xuwei.bean.Customer;
import com.xuwei.bean.CustomerComplaint;
import com.xuwei.bean.Order;
import com.xuwei.bean.User;
import com.xuwei.mapper.CustomerComplaintMapper;
import com.xuwei.mapper.UserMapper;
import com.xuwei.service.ICustomerComplaintService;

/**
 * @description: 客户关怀类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年08月31日 10:49:27
 * @author: zjy
 * @version: 1.0
 */
@Service
public class CustomerComplaintServiceImpl extends ServiceImpl<CustomerComplaintMapper, CustomerComplaint> implements ICustomerComplaintService {
	@Resource
	private CustomerComplaintMapper customerComplaintMapper;
	@Resource
	private UserMapper userMapper;

	/**
	 * 
	 * @description: 获取列表
	 * @createTime: 2017年9月4日 上午10:16:45
	 * @author: zjy
	 * @param pageM
	 * @return
	 */
	@Override
	public List<CustomerComplaint> listCustomerComplaintByPage(Page<CustomerComplaint> pageM, CustomerComplaint m,int viewType) {
		return customerComplaintMapper.listCustomerComplaintByPage(pageM, m,viewType);
	}

	/**
	 * 
	 * @description: 获取客户信息
	 * @createTime: 2017年9月4日 上午10:16:45
	 * @author: zjy
	 * @param pageM
	 * @return
	 */
	@Override
	public List<Customer> getCustemerByList(Page<Customer> pageM,String customerName) {
		return customerComplaintMapper.getCustemerByList(pageM,customerName);
	}
	/**
	 * 
	 * @description: 获取用户信息
	 * @createTime: 2017年9月4日 上午10:16:45
	 * @author: zjy
	 * @param pageM
	 * @return
	 */
	@Override
	public List<User> getHandleByList(Page<User> pageM,String userName) {
		return customerComplaintMapper.getHandleByList(pageM,userName);
	}
	/**
	 * 
	 * @description: 获取订单信息
	 * @createTime: 2017年9月4日 上午10:16:45
	 * @author: zjy
	 * @param pageM
	 * @return
	 */
	@Override
	public List<Order> getOrderByList(Page<Order> pageM,String orderCode,String customerId) {
		return customerComplaintMapper.getOrderByList(pageM,orderCode,customerId);
	}
}

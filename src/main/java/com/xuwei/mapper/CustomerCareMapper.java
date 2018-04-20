package com.xuwei.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xuwei.bean.Customer;
import com.xuwei.bean.CustomerCare;
import com.xuwei.bean.User;

/**
 * @description: 客户关怀Mapper接口
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年08月31日 10:49:27
 * @author: zjy
 * @version: 1.0
 */
public interface CustomerCareMapper extends BaseMapper<CustomerCare> {
	
	/**
	 * 
	 * @description: 获取列表
	 * @createTime: 2017年9月4日 上午10:19:53
	 * @author: zjy
	 * @param pageM
	 * @param m
	 * @return
	 */
	List<CustomerCare> listCustomerCareByPage(Page<CustomerCare> pageM,@Param("m")CustomerCare m,@Param("viewType")int viewType);
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
	List<User> getOwnerByList(Page<User> pageM, @Param("userName")String userName);
}
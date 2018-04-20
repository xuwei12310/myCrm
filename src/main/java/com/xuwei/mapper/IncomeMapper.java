package com.xuwei.mapper;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xuwei.bean.Customer;
import com.xuwei.bean.Income;
import com.xuwei.bean.User;

/**
 * @description: 收入登记Mapper接口
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月05日 09:07:52
 * @author: zyd
 * @version: 1.0
 */
public interface IncomeMapper extends BaseMapper<Income> {
	
	/**
	 * @description:查询收入信息并分页 
	 * @createTime 2017年10月11日 上午11:08:47
	 * @author xw
	 * @param pageM
	 * @param m
	 * @param ew
	 * @return
	 */
	List<Income> listIncomeByPage(Page<Income> pageM,@Param("m")Income m,@Param("ew") EntityWrapper<Income> ew);
	/**
	 * 
	 * @description:查询客户信息 
	 * @createTime 2017年10月11日 上午11:09:07
	 * @author xw
	 * @param pageM
	 * @param customerName
	 * @return
	 */
	List<Customer> getCustemerByList(Page<Customer> pageM,@Param("customerName")String customerName);
	/**
	 * 
	 * @description:查询拥有人信息 
	 * @createTime 2017年10月11日 上午11:09:10
	 * @author xw
	 * @param pageM
	 * @param userName
	 * @return
	 */
	List<User> getOwnerByList(Page<User> pageM, @Param("userName")String userName);
	
	/**
	 * @description:查询收入条目
	 * @createTime 2017年9月25日 下午4:27:37
	 * @author xw
	 * @param pageM
	 * @param orderId
	 * @return
	 */
	List<Map<String, Object>> getIncomeItemList(Page<Map<String, Object>> pageM, @Param("orderId")Long orderId);
}
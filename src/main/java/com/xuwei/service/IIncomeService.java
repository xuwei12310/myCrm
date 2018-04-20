package com.xuwei.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xuwei.bean.Customer;
import com.xuwei.bean.Income;
import com.xuwei.bean.User;
 
/**
 * @description: 收入登记服务类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月05日 09:07:52
 * @author: zyd
 * @version: 1.0
 */
public interface IIncomeService extends IService<Income> {
	
	/**
	 * 
	 * @description:查询收入信息并分页 
	 * @createTime: 2017年9月6日 上午9:09:35
	 * @author: zyd
	 * @param pageM
	 * @param m
	 * @param ew 
	 * @return
	 */
	List<Income> listIncomeByPage(Page<Income> pageM, Income m, EntityWrapper<Income> ew);
	/**
	 * 
	 * @description:查询客户信息 
	 * @createTime: 2017年9月6日 上午9:09:52
	 * @author: zyd
	 * @param pageM
	 * @param customerName
	 * @return
	 */
	List<Customer> getCustemerByList(Page<Customer> pageM,String customerName);
	
	/**
	 * 
	 * @description:查询拥有人信息 
	 * @createTime: 2017年9月6日 上午9:10:04
	 * @author: zyd
	 * @param pageM
	 * @param userName
	 * @return
	 */
	List<User> getOwnerByList(Page<User> pageM,String userName);
	/**
	 * @description:查询收入条目
	 * @createTime 2017年9月25日 下午4:24:53
	 * @author xw
	 * @param pageM
	 * @param orderId
	 * @return
	 */
	Page<Map<String, Object>> getIncomeItemList(Page<Map<String, Object>> pageM, Long orderId);
	/**
	 * @description:插入收入
	 * @createTime 2017年9月25日 下午7:55:29
	 * @author xw
	 * @param m
	 * @param incomeItem 
	 * @param loanItem 
	 * @return
	 */
	void insert(Income m, String incomeItem, String loanItem);
	/**
	 * @description:修改收入
	 * @createTime 2017年9月26日 下午6:54:02
	 * @author xw
	 * @param oldM
	 * @param incomeItem
	 * @param loanItem
	 * @return
	 */
	void update(Income oldM, String incomeItem, String loanItem);
	/**
	 * @description:删除收入
	 * @createTime 2017年9月26日 下午7:47:58
	 * @author xw
	 * @param asList
	 * @return
	 */
	void delete(List<String> asList);
}

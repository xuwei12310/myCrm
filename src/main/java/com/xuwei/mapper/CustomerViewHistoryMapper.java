package com.xuwei.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xuwei.bean.CustomerViewHistory;

/**
 * @description: 客户_浏览历史Mapper接口
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月06日 09:12:17
 * @author: caw
 * @version: 1.0
 */
public interface CustomerViewHistoryMapper extends BaseMapper<CustomerViewHistory> {
	
	/**
	 * 
	 * @description: 查询客户浏览历史记录
	 * @createTime: 2017年9月6日 下午4:01:58
	 * @author: caw
	 * @param pageM
	 * @param m
	 * @return
	 */
	List<CustomerViewHistory> listCustomerViewHistoryByPage(Page<CustomerViewHistory> pageM, @Param("m")CustomerViewHistory m);
}
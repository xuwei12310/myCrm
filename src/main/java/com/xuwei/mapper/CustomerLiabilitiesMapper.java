package com.xuwei.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xuwei.bean.CustomerLiabilities;

/**
 * @description: 客户_负债Mapper接口
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月04日 09:44:17
 * @author: caw
 * @version: 1.0
 */
public interface CustomerLiabilitiesMapper extends BaseMapper<CustomerLiabilities> {

	/**
	 * 
	 * @description: 获取负债（微信端）
	 * @createTime: 2017年9月15日 上午9:35:04
	 * @author: caw
	 * @param page
	 * @param customerid
	 * @return
	 */
	List<CustomerLiabilities> getLiabilitiesList(Page<CustomerLiabilities> page, @Param("customerid")Long customerid);
}
package com.xuwei.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xuwei.bean.CustomerProperty;

/**
 * @description: 客户_产权Mapper接口
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月04日 09:42:59
 * @author: caw
 * @version: 1.0
 */
public interface CustomerPropertyMapper extends BaseMapper<CustomerProperty> {
	
	/**
	 * 分页查询客户产权
	 * @description: 
	 * @createTime: 2017年9月4日 上午11:17:05
	 * @author: caw
	 * @param pageM
	 * @param customerid
	 * @return
	 */
	List<CustomerProperty> listPropertyByPage(Page<CustomerProperty> pageM,@Param("customerid")Long customerid);
	
	/**
	 * 
	 * @description: 获取产权信息（微信端）
	 * @createTime: 2017年9月13日 下午3:50:13
	 * @author: caw
	 * @param page
	 * @param customerid
	 * @return
	 */
	List<CustomerProperty> getCustomerPropertyList(Page<CustomerProperty> page,@Param("customerid")Long customerid);
	
	/**
	 * 
	 * @description: 查看产权信息（微信端）
	 * @createTime: 2017年9月13日 下午5:18:35
	 * @author: caw
	 * @param id
	 * @return
	 */
	CustomerProperty getCustomerPropertyInfo(@Param("id")Long id);

	/**
	 * 分页查询客户产权
	 * @description:
	 * @createTime: 2017年10月18日 上午11:17:05
	 * @author: hhd
	 * @param pageM
	 * @param ew
	 * @return
	 */
	List<CustomerProperty> selectPageByEw(Page<CustomerProperty> pageM,@Param("ew")EntityWrapper ew);
}
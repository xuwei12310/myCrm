package com.xuwei.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xuwei.bean.CustomerOtherResources;

/**
 * @description: 客户_其他财力Mapper接口
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月04日 09:43:35
 * @author: caw
 * @version: 1.0
 */
public interface CustomerOtherResourcesMapper extends BaseMapper<CustomerOtherResources> {
	/**
	 * 
	 * @description: 获取其他财产（微信端）
	 * @createTime: 2017年9月15日 上午9:10:34
	 * @author: caw
	 * @param page
	 * @param customerid
	 * @return
	 */
	List<CustomerOtherResources> getOtherResourcesList(Page<CustomerOtherResources> page, @Param("customerid")Long customerid);
}
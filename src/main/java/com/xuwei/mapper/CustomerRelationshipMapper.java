package com.xuwei.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xuwei.bean.CustomerRelationship;

/**
 * @description: 客户_联系人Mapper接口
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月04日 09:41:34
 * @author: caw
 * @version: 1.0
 */
public interface CustomerRelationshipMapper extends BaseMapper<CustomerRelationship> {
	/**
	 * @description: 获取联系人
     * @createTime: 2017年9月14日 下午5:16:39
     * @author: caw  
	 * @param page
	 * @param customerid
	 * @return
	 */
	List<CustomerRelationship> getRelationshiList(Page<CustomerRelationship> page, @Param("customerid")Long customerid);
}
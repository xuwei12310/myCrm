package com.xuwei.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.xuwei.bean.OrderCompany;

/**
 * @description: 订单_评估公司Mapper接口
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月18日 17:46:41
 * @author: hhd
 * @version: 1.0
 */
public interface OrderCompanyMapper extends BaseMapper<OrderCompany> {

	/**
	 * @description:
	 * @createTime 2017年9月15日 下午3:07:01
	 * @author xw
	 * @param id 订单id
	 * @param isAssessment 是否评估公司
	 * @return
	 */
	List<OrderCompany> selectAssessmentCompany(@Param("id")Long id,Integer isAssessment);

	/**
	 * 分页查询合作公司
	 * @param page
	 * @param orderId
	 * @return
	 */
	List<Map<String,Object>> queryByPage(Pagination page, @Param("orderId") Long orderId);
	/**
	 * 分页查询合作公司
	 * @param page
	 * @param orderId
	 * @return
	 */
	List<OrderCompany> selectListByPage(Pagination page, @Param("orderId") Long orderId);


	/**
	 * 查询合作公司
	 * @param id
	 * @return
	 */
	OrderCompany getCompanyById(@Param("id") Long id);

}
package com.xuwei.mapper;


import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xuwei.bean.Pay;

/**
 * @description: 支出登记Mapper接口
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月12日 16:48:34
 * @author: xw
 * @version: 1.0
 */
public interface PayMapper extends BaseMapper<Pay> {

	/**
	 * @description:分页查询支出
	 * @createTime 2017年9月13日 上午11:08:11
	 * @author xw
	 * @param pageM
	 * @param m
	 * @param ew 
	 * @return
	 */
	List<Pay> listByPage(Page<Pay> pageM, @Param("m")Pay m, @Param("ew")EntityWrapper<Pay> ew);

	/**
	 * @description:批量审核
	 * @createTime 2017年9月14日 下午2:15:54
	 * @author xw
	 * @param asList
	 * @return
	 */
	int verifyByIds(@Param("ids")List<String> asList);

	/**
	 * @description:
	 * @createTime 2017年9月20日 下午2:35:06
	 * @author xw
	 * @param id
	 * @param commissionAmount
	 */
	void updateCommission(@Param("orderId")Long id,@Param("commissionAmount")BigDecimal commissionAmount);

	/**
	 * @description:订单结算模块分页查询支出
	 * @createTime 2017年10月13日 上午10:38:13
	 * @author xw
	 * @param pageM
	 * @param m
	 * @return
	 */
	List<Pay> listByBanlancePage(Page<Pay> pageM,@Param("m")Pay m);

}
package com.xuwei.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xuwei.bean.OrderLoan;

/**
 * @description: 订单_借款Mapper接口
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月19日 09:29:11
 * @author: hhd
 * @version: 1.0
 */
public interface OrderLoanMapper extends BaseMapper<OrderLoan> {

	/**
	 * @description:
	 * @createTime 2017年9月20日 上午9:02:53
	 * @author xw
	 * @param pageM
	 * @param orderLoan
	 * @return
	 */
	List<OrderLoan> listByPage(Page<OrderLoan> pageM, @Param("orderLoan")OrderLoan orderLoan);

}
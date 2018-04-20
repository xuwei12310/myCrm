package com.xuwei.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xuwei.bean.OrderLoan;
import com.xuwei.util.ServiceResult;
/**
 * @description: 订单_借款服务类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月19日 09:29:11
 * @author: hhd
 * @version: 1.0
 */
public interface IOrderLoanService extends IService<OrderLoan> {


	/**
	 * @description:
	 * @createTime 2017年9月15日 下午5:33:27
	 * @author xw
	 * @param pageM
	 * @param order
	 * @return
	 */
	Page<OrderLoan> listByPage(Page<OrderLoan> pageM, OrderLoan orderLoan);

	/**
	 *
	 * @description: 获取借款信息（微信端）
	 * @createTime: 2017年9月25日 下午4:43:13
	 * @author: hhd
	 * @param page
	 * @param totalNum
	 * @param orderid
	 * @return
	 */
	ServiceResult getLoanByPage(Page<OrderLoan> page, Long totalNum, Long orderid);

	
}

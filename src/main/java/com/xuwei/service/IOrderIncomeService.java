package com.xuwei.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xuwei.bean.OrderIncome;
 
/**
 * @description: 订单_收入服务类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月18日 11:12:28
 * @author: xw
 * @version: 1.0
 */
public interface IOrderIncomeService extends IService<OrderIncome> {

	/**
	 * @description:
	 * @createTime 2017年9月18日 上午11:20:57
	 * @author xw
	 * @param pageM
	 * @param income
	 * @return
	 */
	Page<OrderIncome> listByPage(Page<OrderIncome> pageM, OrderIncome income);

	/**
	 * @description:修改收入项，修改支出返佣参考值
	 * @createTime 2017年9月20日 上午11:23:57
	 * @author xw
	 * @param orderIncome
	 */
	void updateOrderIncome(OrderIncome orderIncome);
	
}

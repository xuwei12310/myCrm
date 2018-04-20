package com.xuwei.service;

import java.util.List;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xuwei.bean.Bank;
import com.xuwei.bean.Order;
import com.xuwei.util.ServiceResult;

/**
 * @description: 字典服务类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年07月19日 16:31:12
 * @author: lxb
 * @version: 1.0
 */
public interface IBankService extends IService<Bank> {

	/**
	 * 
	 * @description: 上移下移
	 * @createTime: 2017年7月20日 上午11:20:59
	 * @author: lxb
	 * @param srcId
	 * @param destId
	 */

	ServiceResult changeArray(Long srcId, Long destId);

	/**
	 * 
	 * @description: 取下个排序值
	 * @createTime: 2017年8月9日 下午3:54:23
	 * @author: lxb
	 * @param dictType
	 * @return
	 */
	Integer nextArray();
	/**
	 *
	 * @description: 银行统计
	 * @createTime: 2017年10月18日 下午5:03:23
	 * @author: hhd
	 * @param pageM
	 * @return
	 */
	List<Order> selectStatisticsByPage(Page<Order> pageM, EntityWrapper ew);

}

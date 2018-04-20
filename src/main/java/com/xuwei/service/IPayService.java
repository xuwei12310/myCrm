package com.xuwei.service;


import java.util.List;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xuwei.bean.Pay;
 
/**
 * @description: 支出登记服务类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月12日 16:48:34
 * @author: xw
 * @version: 1.0
 */
public interface IPayService extends IService<Pay> {

	/**
	 * @description:分页查询支出
	 * @createTime 2017年9月13日 上午11:06:40
	 * @author xw
	 * @param pageM
	 * @param m
	 * @param ew 
	 * @return
	 */
	Page<Pay> listByPage(Page<Pay> pageM, Pay m, EntityWrapper<Pay> ew);

	/**
	 * @description:批量审核
	 * @createTime 2017年9月14日 下午2:14:08
	 * @author xw
	 * @param asList
	 * @return
	 */
	boolean verifyByIds(List<String> asList);

	/**
	 * @description:订单结算模块的分页查询
	 * @createTime 2017年10月13日 上午10:36:58
	 * @author xw
	 * @param pageM
	 * @param pay
	 * @param object
	 * @return
	 */
	Page<Pay> listByBanlancePage(Page<Pay> pageM, Pay pay);
	
}

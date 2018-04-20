package com.xuwei.service;

import java.util.List;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xuwei.bean.BrokerageDistribute;
import com.xuwei.bean.Order;
import com.xuwei.util.ServiceResult;
 
/**
 * @description: 佣金分配服务类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月25日 19:57:40
 * @author: caw
 * @version: 1.0
 */
public interface IBrokerageDistributeService extends IService<BrokerageDistribute> {
	
	/**
	 * 
	 * @description: 根据订单id获取佣金分配信息
	 * @createTime: 2017年9月26日 上午9:06:56
	 * @author: caw
	 * @param pageM
	 * @param m
	 * @return
	 */
	List<BrokerageDistribute> listBrokerageByPage(BrokerageDistribute m);
	
	/**
	 * 
	 * @description: 生成佣金分配
	 * @createTime: 2017年9月26日 上午9:54:12
	 * @author: caw
	 * @param orderId
	 * @return
	 */
	boolean addBrokerageDistribute(Long orderId);
	
	/**
	 * 
	 * @description: 修改发放状态
	 * @createTime: 2017年9月26日 下午2:29:53
	 * @author: caw
	 * @param param
	 * @param idArray
	 * @return
	 */
	ServiceResult modGrantStatus(Integer param,String[] idArray);
	
	/**
	 * 
	 * @description: 获取订单信息
	 * @createTime: 2017年9月26日 下午7:02:10
	 * @author: caw
	 * @param orderId
	 * @return
	 */
	Order getOrderBrokerageById(Long orderId);
	
	/**
	 * 
	 * @description: 修改佣金总额
	 * @createTime: 2017年9月27日 下午8:02:51
	 * @author: caw
	 * @param orderId
	 * @return
	 */
	boolean modBrokerageTotal(Long orderId);

	/**
	 *
	 * @description: 佣金统计
	 * @createTime: 2017年10月18日 下午10:20:08
	 * @author: hhd
	 * @param pageM
	 * @param ew
	 * @return
	 */
	List<BrokerageDistribute> getCommissionStatisticsList(Page<BrokerageDistribute> pageM, EntityWrapper ew, int viewType);

	/**
	 *
	 * @description: 佣金发放查询
	 * @createTime: 2017年10月18日 下午10:20:08
	 * @author: hhd
	 * @param pageM
	 * @param ew
	 * @return
	 */
	List<BrokerageDistribute> grantOrderList(Page<BrokerageDistribute> pageM, EntityWrapper ew);
}

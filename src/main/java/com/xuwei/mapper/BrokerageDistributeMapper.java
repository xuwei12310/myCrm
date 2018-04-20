package com.xuwei.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xuwei.bean.BrokerageDistribute;
import com.xuwei.bean.Order;


/**
 * @description: 佣金分配Mapper接口
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月25日 19:57:40
 * @author: caw
 * @version: 1.0
 */
public interface BrokerageDistributeMapper extends BaseMapper<BrokerageDistribute> {
	/**
	 * 
	 * @description: 根据订单id获取佣金分配信息
	 * @createTime: 2017年9月26日 上午9:11:06
	 * @author: caw
	 * @param pageM
	 * @param m
	 * @return
	 */
	List<BrokerageDistribute> listBrokerageByPage(@Param("m")BrokerageDistribute m);
	
	/**
	 * 
	 * @description: 获取订单信息
	 * @createTime: 2017年9月26日 下午7:03:23
	 * @author: caw
	 * @param orderId
	 * @return
	 */
	Order getOrderBrokerageById(@Param("orderId")Long orderId);
	
	/**
	 * 
	 * @description: 获取佣金分配
	 * @createTime: 2017年9月27日 下午8:07:02
	 * @author: caw
	 * @param orderId
	 * @return
	 */
	List<BrokerageDistribute> getBrokerageList(@Param("orderId")Long orderId);


	/**
	 * @description: 获取佣金统计
	 * @createTime: 2017年10月18日 10:21:37
	 * @author: hhd
	 * @param pageM
	 * @param ew
	 * @param viewType
	 * @return
	 */
	List<BrokerageDistribute> getCommissionStatisticsList(Page<BrokerageDistribute> pageM, @Param("ew")EntityWrapper ew, @Param("viewType")int viewType);

	/**
	 * @description: 获取佣金统计
	 * @createTime: 2017年10月18日 10:21:37
	 * @author: hhd
	 * @param pageM
	 * @param ew
	 * @return
	 */
	List<BrokerageDistribute> grantOrderList(Page<BrokerageDistribute> pageM, @Param("ew")EntityWrapper ew);
}
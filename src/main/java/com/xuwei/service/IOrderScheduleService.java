package com.xuwei.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xuwei.bean.Order;
import com.xuwei.bean.OrderSchedule;
import com.xuwei.util.ServiceResult;
 
/**
 * @description: 订单_进度服务类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月18日 17:41:33
 * @author: caw
 * @version: 1.0
 */
public interface IOrderScheduleService extends IService<OrderSchedule> {
	
	/**
	 * 
	 * @description: 自定义列
	 * @createTime: 2017年9月19日 下午4:39:59
	 * @author: caw
	 * @param productType
	 * @return
	 */
	ServiceResult getColumns(Long productType);
	
	/**
	 * 
	 * @description: 分页查询
	 * @createTime: 2017年9月20日 下午2:22:08
	 * @author: caw
	 * @param scheduleDictId
	 * @param orderCodes
	 * @param customerNames
	 * @return
	 */
	List<Map<String, Object>> listOrderScheduleByPage(Page<Order> pageM,Long scheduleDictId, String orderCodes, String customerNames, int viewType);
	
	/**
	 * 
	 * @description: 获取进度
	 * @createTime: 2017年9月20日 下午2:30:16
	 * @author: caw
	 * @param productType
	 * @return
	 */
	ServiceResult getSchedule(Long productType);
	
	/**
	 * 
	 * @description: 获取订单进度信息
	 * @createTime: 2017年9月20日 下午2:45:02
	 * @author: caw
	 * @param orderid
	 * @param scheduleDate
	 * @return
	 */
	ServiceResult getOrderScheduleInfo(Long orderid, String scheduleDate);
	
	/**
	 * 
	 * @description: 根据订单id获取订单进度信息
	 * @createTime: 2017年9月27日 下午2:21:09
	 * @author: caw
	 * @param orderid
	 * @return
	 */
	ServiceResult getOrderScheduleList(Long orderid);
	
	/**
	 * 
	 * @description:  获取订单进度详细信息(微信端)
	 * @createTime: 2017年9月27日 下午3:21:11
	 * @author: caw
	 * @param orderScheduleId
	 * @return
	 */
	OrderSchedule getWXOrderScheduleInfo(Long orderScheduleId);

	/**
	 *
	 * @description: 查询订单进度根据订单id
	 * @createTime: 2017年10月17日 下午7:39:59
	 * @author: hhd
	 * @param orderId
	 * @return
	 */
	List<OrderSchedule> getOrderSheduleByOrderId(Long orderId);
}

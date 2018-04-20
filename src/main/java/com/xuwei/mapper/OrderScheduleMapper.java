package com.xuwei.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xuwei.bean.OrderSchedule;


/**
 * @description: 订单_进度Mapper接口
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月18日 17:41:33
 * @author: caw
 * @version: 1.0
 */
public interface OrderScheduleMapper extends BaseMapper<OrderSchedule> {
	
	/**
	 * 
	 * @description: 分页查询
	 * @createTime: 2017年9月30日 下午4:38:46
	 * @author: caw
	 * @param scheduleDictId
	 * @param orderCodes
	 * @param customerNames
	 * @param pageIndex
	 * @param pageSize
	 * @param pageType
	 * @param viewType
	 * @param userId
	 * @param organizationId
	 * @return
	 */
	List<Map<String, Object>> listOrderScheduleByPage(@Param("scheduleDictId")Long scheduleDictId, @Param("orderCodes")String orderCodes,
			@Param("customerNames")String customerNames, @Param("pageIndex")int pageIndex, @Param("pageSize")int pageSize, 
			@Param("pageType")int pageType, @Param("viewType")int viewType, @Param("userId")Long userId, @Param("organizationid")Long organizationid);
	
	/**
	 * 
	 * @description: 分页查询(总数)
	 * @createTime: 2017年9月30日 下午4:41:01
	 * @author: caw
	 * @param scheduleDictId
	 * @param orderCodes
	 * @param customerNames
	 * @param pageIndex
	 * @param pageSize
	 * @param pageType
	 * @param viewType
	 * @param userId
	 * @param organizationid
	 * @return
	 */
	List<Map<String, Object>> listOrderScheduleByPageNum(@Param("scheduleDictId")Long scheduleDictId, @Param("orderCodes")String orderCodes,
			@Param("customerNames")String customerNames, @Param("pageIndex")int pageIndex, @Param("pageSize")int pageSize,
			@Param("pageType")int pageType, @Param("viewType")int viewType, @Param("userId")Long userId, @Param("organizationid")Long organizationid);
	
	/**
	 * 
	 * @description: 获取订单进度信息
	 * @createTime: 2017年9月20日 下午3:43:50
	 * @author: caw
	 * @param orderid
	 * @param scheduleId
	 * @return
	 */
	OrderSchedule getOrderScheduleInfo(@Param("orderid")Long orderid, @Param("scheduleId")Long scheduleId);
	
	/**
	 * 
	 * @description: 根据订单id获取订单进度
	 * @createTime: 2017年9月25日 上午10:08:06
	 * @author: caw
	 * @param orderId
	 * @return
	 */
	List<OrderSchedule> getOrderScheduleList(@Param("orderId")Long orderId);
	
	/**
	 * 
	 * @description: 根据订单id获取订单进度(微信端)
	 * @createTime: 2017年9月27日 下午2:38:11
	 * @author: caw
	 * @param orderid
	 * @return
	 */
	List<OrderSchedule> getWXOrderScheduleList(@Param("orderid")Long orderid);
	
	/**
	 * 
	 * @description: 获取订单进度详细信息（微信端）
	 * @createTime: 2017年9月27日 下午3:19:43
	 * @author: caw
	 * @param orderScheduleId
	 * @return
	 */
	OrderSchedule getWXOrderScheduleInfo(@Param("orderScheduleId")Long orderScheduleId);

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
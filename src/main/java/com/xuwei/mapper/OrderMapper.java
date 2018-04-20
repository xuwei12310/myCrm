package com.xuwei.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.xuwei.bean.Order;

/**
 * @description: 订单Mapper接口
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月05日 09:22:53
 * @author: hhd
 * @version: 1.0
 */
public interface OrderMapper extends BaseMapper<Order> {

	/**
	 * 根据类型分页查询订单
	 * @param pagination
	 * @param wrapper
	 * @param type  0全部 1部门 2个人
	 * @return
	 */
	List<Order> selectPageByType(Pagination pagination, @Param("ew") Wrapper<Order> wrapper, @Param("userid") Long userid, @Param("type") int type);

	/**
	 * 
	 * @description: 根据用户id获取订单信息
	 * @createTime: 2017年9月18日 上午9:56:17
	 * @author: caw
	 * @param searchValue
	 * @param page
	 * @return
	 */
	List<Order> getOrderList(@Param("searchValue")String searchValue,Page<Order> page,@Param("userId") Long userId);
	
	/**
	 * 
	 * @description: 获取今日更新订单信息（微信端）
	 * @createTime: 2017年9月18日 上午9:57:43
	 * @author: caw
	 * @param searchValue
	 * @param page
	 * @param currentTime
	 * @return
	 */
	List<Order> getOrderTodayUpdateList(@Param("searchValue")String searchValue,Page<Order> page, @Param("currentTime")String currentTime,@Param("userId") Long userId);
	
	/**
	 * 
	 * @description: 获取近一周订单信息（微信端）
	 * @createTime: 2017年9月18日 上午9:57:47
	 * @author: caw
	 * @param searchValue
	 * @param page
	 * @param currentTime
	 * @return
	 */
	List<Order> getOrderNearlyAWeekList(@Param("searchValue")String searchValue,Page<Order> page, @Param("currentTime")String currentTime,@Param("userId") Long userId);
	
	/**
	 * 
	 * @description: 获取近一个月订单信息（微信端）
	 * @createTime: 2017年9月18日 上午9:57:51
	 * @author: caw
	 * @param searchValue
	 * @param page
	 * @param currentTime
	 * @return
	 */
	List<Order> getOrderNearlyAMonthList(@Param("searchValue")String searchValue,Page<Order> page, @Param("currentTime")String currentTime,@Param("userId") Long userId);
	
	/**
	 * 
	 * @description: 根据id获取订单详细信息（微信端）
	 * @createTime: 2017年9月18日 下午2:27:39
	 * @author: caw
	 * @param orderid
	 * @return
	 */
	Order getOrderInfo(@Param("orderid")Long orderid);

	/**
	 * @description:
	 * @createTime 2017年9月15日 上午10:34:07
	 * @author xw
	 * @param userId
	 * @return
	 */
	List<Order> orderCombobox(@Param("userId")Long userId);

	/**
	 * @description:
	 * @createTime 2017年9月15日 上午10:53:08
	 * @author xw
	 * @param pageM
	 * @param m
	 * @return
	 */
	List<Order> listOrderBalanceByPage(Page<Order> pageM,@Param("m")Order m,@Param("ew")EntityWrapper<Order> ew);

	/**
	 * @description:修改订单结算状态为空
	 * @createTime 2017年9月20日 上午11:54:31
	 * @author xw
	 * @param idArray
	 * @return
	 */
	int deleteBalanceByIds(@Param("ids")String[] idArray);
	
	/**
	 * @description: 获取订单佣金信息
	 * @createTime: 2017年9月25日 下午8:21:37
	 * @author: caw
	 * @param pageM
	 * @param m
	 * @param viewType
	 * @param userid
	 * @return
	 */
	List<Order> getOrderBrokerageList(Page<Order> pageM,@Param("m")Order m, @Param("viewType")int viewType, @Param("userid")Long userid);
	
	/**
	 * 
	 * @description: 客户获取订单信息（微信端）
	 * @createTime: 2017年9月27日 下午4:47:48
	 * @author: caw
	 * @param page
	 * @param customerId
	 * @return
	 */
	List<Order> getCustomerOrderList(Page<Order> page, @Param("customerId")Long customerId);
	
	/**
	 * 
	 * @description: 
	 * @createTime: 2017年9月27日 下午6:38:54
	 * @author: caw
	 * @param productId
	 * @return
	 */
	List<Order> getOrderProductTypeList(@Param("productId")Long productId);
}
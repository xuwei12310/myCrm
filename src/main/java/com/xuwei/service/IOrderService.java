package com.xuwei.service;

import java.util.List;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xuwei.bean.Order;
import com.xuwei.util.ServiceResult;
 
/**
 * @description: 订单服务类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月05日 09:22:53
 * @author: hhd
 * @version: 1.0
 */
public interface IOrderService extends IService<Order> {


	/**
	 * 分页查询订单
	 * @param page
	 * @param wrapper
	 * @param type 0全部  1部门 2个人
	 * @return
	 */
	Page<Order> selectPage(Page<Order> page, Wrapper<Order> wrapper,int type);

	/**
	 * 新增订单
	 * @param m
	 * @return
	 */
	ServiceResult createOrder(Order m);
	/**
	 * 
	 * @description: 获取订单信息（微信端）
	 * @createTime: 2017年9月18日 上午9:49:50
	 * @author: caw
	 * @param searchValue
	 * @param page
	 * @param userId
	 * @param totalNum
	 * @param searchType
	 * @return
	 */
    ServiceResult getOrderList(String searchValue, Page<Order> page, Long userId, Long totalNum, Long searchType);
	
	/**
	 * 
	 * @description: 添加订单（微信端）
	 * @createTime: 2017年9月18日 下午12:07:05
	 * @author: caw
	 * @param m
	 * @param userId
	 * @return
	 */
    ServiceResult addOrder(Order m, Long userId);
	
	/**
	 * 
	 * @description: 根据id获取订单详细信息（微信端）
	 * @createTime: 2017年9月18日 下午2:25:35
	 * @author: caw
	 * @param orderid
	 * @return
	 */
    Order getOrderInfo(Long orderid);
	
	/**
	 * 
	 * @description: 修改订单信息（微信端）
	 * @createTime: 2017年9月18日 下午2:45:20
	 * @author: caw
	 * @param m
	 * @param userId
	 * @return
	 */
    ServiceResult modOrder(Order m, Long userId);

	/**
	 * @description:获取自己的订单下拉
	 * @createTime 2017年9月15日 上午10:31:43
	 * @author xw
	 * @param userId
	 * @return
	 */
	List<Order> orderCombobox(Long userId);

	/**
	 * @description:订单结算列表
	 * @createTime 2017年9月15日 上午10:50:09
	 * @author xw
	 * @param pageM
	 * @param m
	 * @return
	 */
	Page<Order> listOrderBalanceByPage(Page<Order> pageM, Order m, EntityWrapper<Order> ew);

	/**
	 * @description:保存订单结算
	 * @createTime 2017年9月15日 上午11:24:14
	 * @author xw
	 * @param m
	 * @return
	 */
	ServiceResult saveOrderBalance(Order m);
	/**
	 * @description:删除订单结算（实际修改订单的结算状态为空）
	 * @createTime 2017年9月20日 上午11:50:26
	 * @author xw
	 * @param idArray
	 * @return
	 */
	boolean deleteBalanceByIds(String[] idArray);

	/**
	 *
	 * @description: 修改订单贷款方案（微信端）
	 * @createTime: 2017年9月25日 下午11:29:20
	 * @author: hhd
	 * @param m
	 * @param userId
	 * @return
	 */
	ServiceResult updateLoan(Order m, Long userId);
	/**
	 *
	 * @description: 修改订单信息（微信端）
	 * @createTime: 2017年9月18日 下午2:45:20
	 * @author: caw
	 * @param m
	 * @param userId
	 * @return
	 */
	ServiceResult updateLending(Order m, Long userId);

	/**
	 *
	 * @description: 获取订单佣金信息
	 * @createTime: 2017年9月25日 下午8:20:08
	 * @author: caw
	 * @param pageM
	 * @param m
	 * @return
	 */
	List<Order> getOrderBrokerageList(Page<Order> pageM, Order m, int viewType, Long userid);
	
	/**
	 * 
	 * @description: 客户获取订单信息（微信端）
	 * @createTime: 2017年9月27日 下午4:44:24
	 * @author: caw
	 * @param page
	 * @param userId
	 * @param totalNum
	 * @return
	 */
	ServiceResult getOrderList(Page<Order> page, Long userId, Long totalNum);
}

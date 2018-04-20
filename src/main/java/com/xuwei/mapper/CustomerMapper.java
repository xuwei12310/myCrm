package com.xuwei.mapper;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xuwei.bean.Customer;
import com.xuwei.bean.User;

/**
 * @description: 客户Mapper接口
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年08月31日 10:49:27
 * @author: caw
 * @version: 1.0
 */
public interface CustomerMapper extends BaseMapper<Customer> {
	/**
	 * @description: 根据手机号获取客户数量
	 * @createTime: 2017年08月31日 5:49:27
	 * @author: caw 
	 * @param mobilePhone
	 * @return
	 */
	int mobilePhoneNum(@Param("mobilePhone")String mobilePhone,@Param("customerid")Long customerid);
	
	/**
	 * @description: 根据身份证号获取客户数量
	 * @createTime: 2017年08月31日 5:49:27
	 * @author: caw 
	 * @param mobilePhone
	 * @return
	 */
	int idNumberNum(@Param("idNumber")String idNumber,@Param("customerid")Long customerid);
	
	/**
	 * 根据id获取客户信息
	 * @createTime: 2017年9月1日 上午8:55:21
	 * @author: caw
	 * @param customerid
	 * @return
	 */
	Customer selectCustomerById(@Param("customerid")Long customerid);
	
	/**
	 * 获取客户信息
	 * @createTime: 2017年9月1日 上午8:55:21
	 * @author: caw
	 * @param pageM
	 * @param m
	 * @return
	 */
	List<Customer> listCustomerByPage(Page<Customer> pageM,@Param("m")Customer m, @Param("todayTime")String todayTime, @Param("viewType")int viewType);
	
	/**
	 * @description: 获取用户信息
	 * @createTime 2017年8月31日 下午2:21:33
	 * @author caw
	 * @return
	 */
	List<User> getOwnerByList(Page<User> pageM, @Param("userName")String userName);
	
	/**
	 * 
	 * @description: 获取客户信息
	 * @createTime: 2017年9月6日 上午11:47:36
	 * @author: caw
	 * @param m
	 * @return
	 */
	List<Map<String, Object>> findBySearch(@Param("m")Customer m);
	
	//----------统计--------------
	/**
	 * 全部客户
	 * @description: 
	 * @createTime: 2017年9月7日 下午2:04:43
	 * @author: caw
	 * @param viewType
	 * @param m
	 * @return
	 */
	int wholeCustomerNum(@Param("viewType")int viewType, @Param("m")Customer m);
	
	/**
	 * 个人客户
	 * @description: 
	 * @createTime: 2017年9月7日 下午2:04:41
	 * @author: caw
	 * @param viewType
	 * @param m
	 * @return
	 */
	int personalCustomerNum(@Param("viewType")int viewType, @Param("m")Customer m);
	
	/**
	 * 企业客户
	 * @description: 
	 * @createTime: 2017年9月7日 下午2:04:38
	 * @author: caw
	 * @param viewType
	 * @param m
	 * @return
	 */
	int enterpriseCustomerNum(@Param("viewType")int viewType, @Param("m")Customer m);
	
	/**
	 * 今日更新
	 * @description: 
	 * @createTime: 2017年9月7日 下午2:03:53
	 * @author: caw
	 * @param todayTime
	 * @param viewType
	 * @param m
	 * @return
	 */
	int todayUpdateNum(@Param("todayTime")String todayTime, @Param("viewType")int viewType, @Param("m")Customer m);
	
	/**
	 * 根据身份证号查询客户信息
	 * @description: 
	 * @createTime: 2017年9月8日 上午11:44:32
	 * @author: caw
	 * @param idNumber
	 * @return
	 */
	Customer findByIdNumber(@Param("idNumber")String idNumber);
	
	/**
	 * 获取客户信息（微信端）
	 * @description: 
	 * @createTime: 2017年9月12日 上午8:55:33
	 * @author: caw
	 * @param searchValue
	 * @param page
	 * @return
	 */
	List<Customer> getCustomerList(@Param("searchValue")String searchValue,Page<Customer> page, @Param("viewType")int viewType, @Param("m")Customer m);
	
	/**
	 * 
	 * @description: 获取今日更新客户信息（微信端）
	 * @createTime: 2017年9月12日 上午11:12:39
	 * @author: caw
	 * @param searchValue
	 * @param page
	 * @param currentTime
	 * @return
	 */
	List<Customer> getCustomerTodayUpdateList(@Param("searchValue")String searchValue,Page<Customer> page, @Param("currentTime")String currentTime, @Param("viewType")int viewType,@Param("m")Customer m);
	
	/**
	 * 
	 * @description: 获取近一周客户信息（微信端）
	 * @createTime: 2017年9月12日 上午11:12:39
	 * @author: caw
	 * @param searchValue
	 * @param page
	 * @param currentTime
	 * @return
	 */
	List<Customer> getCustomerNearlyAWeekList(@Param("searchValue")String searchValue,Page<Customer> page, @Param("currentTime")String currentTime, @Param("viewType")int viewType, @Param("m")Customer m);
	
	/**
	 * 
	 * @description: 获取近一个月客户信息（微信端）
	 * @createTime: 2017年9月12日 上午11:12:39
	 * @author: caw
	 * @param searchValue
	 * @param page
	 * @param currentTime
	 * @return
	 */
	List<Customer> getCustomerNearlyAMonthList(@Param("searchValue")String searchValue,Page<Customer> page, @Param("currentTime")String currentTime, @Param("viewType")int viewType, @Param("m")Customer m);

	/**
	 * @description:根据订单id获取客户
	 * @createTime 2017年9月14日 上午10:52:49
	 * @author xw
	 * @param pageM 
	 * @param orderId
	 * @return
	 */
	List<Customer> listCustomerByOrder(Page<Customer> pageM, @Param("id")Long orderId);
	
	/**
	 * 
	 * @description: 获取全部客户（微信端）
	 * @createTime: 2017年9月18日 上午11:48:15
	 * @author: caw
	 * @return
	 */
	List<Customer> getAllCustomerByList();
}
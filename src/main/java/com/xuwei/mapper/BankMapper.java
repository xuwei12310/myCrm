package com.xuwei.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xuwei.bean.Bank;
import com.xuwei.bean.Order;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description: 字典Mapper接口
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年07月19日 16:31:12
 * @author: xw
 * @version: 1.0
 */
public interface BankMapper extends BaseMapper<Bank> {
   /**
    * 
    * @description: 上移下移
    * @createTime: 2017年8月30日 上午14:36:19
    * @author: xw
    * @param srcId
    * @param destId
    */
	void changeArray(@Param("srcId")Long srcId, @Param("destId")Long destId);
	/**
	 * 
	 * @description: 取下个排序值
	 * @createTime: 2017年8月30日 上午14:36:19
	 * @author: xw
	 * @param dictType
	 * @return
	 */
	Integer nextArray();
	/**
	 * @description:根据订单id查找银行
	 * @createTime 2017年9月14日 上午9:43:54
	 * @author xw
	 * @param orderId
	 * @return
	 */
	List<Bank> listBankByOrder(@Param("id")Long orderId);

	List<Order> selectStatisticsByPage(Page<Order> pageM, @Param("ew")EntityWrapper ew);

}
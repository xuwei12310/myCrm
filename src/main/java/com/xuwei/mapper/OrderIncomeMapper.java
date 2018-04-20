package com.xuwei.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xuwei.bean.OrderIncome;

/**
 * @description: 订单_收入Mapper接口
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月18日 11:12:28
 * @author: xw
 * @version: 1.0
 */
public interface OrderIncomeMapper extends BaseMapper<OrderIncome> {

	/**
	 * @description:
	 * @createTime 2017年9月18日 上午11:24:08
	 * @author xw
	 * @param pageM
	 * @param income
	 * @return
	 */
	List<OrderIncome> listByPage(Page<OrderIncome> pageM,@Param("m")OrderIncome m);

}
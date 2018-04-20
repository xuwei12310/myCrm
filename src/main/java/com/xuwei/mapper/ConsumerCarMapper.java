package com.xuwei.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xuwei.bean.ConsumerCar;

/**
 * @description: 客户_车产Mapper接口
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月04日 09:45:08
 * @author: caw
 * @version: 1.0
 */
public interface ConsumerCarMapper extends BaseMapper<ConsumerCar> {
	
	/**
	 * 
	 * @description: 获取车产信息（微信端）
	 * @createTime: 2017年9月14日 下午4:45:37
	 * @author: caw
	 * @param page
	 * @param customerid
	 * @return
	 */
	List<ConsumerCar> getConsumerCarList(Page<ConsumerCar> page, @Param("customerid")Long customerid);

}
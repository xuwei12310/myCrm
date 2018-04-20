package com.xuwei.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xuwei.bean.OrderProperty;

/**
 * @description: 订单_抵押房产Mapper接口
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月15日 09:41:57
 * @author: hhd
 * @version: 1.0
 */
public interface OrderPropertyMapper extends BaseMapper<OrderProperty> {
    int deletePropertyByOrder(@Param("ids") List<String> ids,@Param("orderId") Long orderId);
}
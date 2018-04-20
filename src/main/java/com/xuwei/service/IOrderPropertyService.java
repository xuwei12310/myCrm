package com.xuwei.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xuwei.bean.OrderProperty;
import com.xuwei.util.ServiceResult;

/**
 * @description: 订单_抵押房产服务类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月15日 09:41:57
 * @author: hhd
 * @version: 1.0
 */
public interface IOrderPropertyService extends IService<OrderProperty> {
    /**
     *
     * @description: 删除订单房产表的信息
     * @createTime: 2017年9月15日 下午4:43:13
     * @author: hhd
     * @return
     */
    int deletePropertyByOrder(List<String> ids, Long orderId);

    /**
     *
     * @description: 获取产权信息（微信端）
     * @createTime: 2017年9月25日 下午4:43:13
     * @author: hhd
     * @param page
     * @param totalNum
     * @param orderid
     * @return
     */
    ServiceResult getPropertyByPage(Page<OrderProperty> page, Long totalNum, Long orderid);

  /*  *//**
     *
     * @description: 根据id获取产权信息（微信端）
     * @createTime: 2017年9月25日 下午4:43:13
     * @author: hhd
     * @param orderid
     * @return
     *//*
    CustomerProperty getPropertyById(Long orderid);*/
}

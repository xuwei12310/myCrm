package com.xuwei.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.IService;
import com.xuwei.bean.OrderBank;
import com.xuwei.util.ServiceResult;

/**
 * @description: 订单_合作银行服务类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月18日 15:00:36
 * @author: hhd
 * @version: 1.0
 */
public interface IOrderBankService extends IService<OrderBank> {
    /**
     * 查询订单合作银行信息  显示支行信息
     * @param page
     * @param orderId
     * @return
     */
    List<Map<String,Object>> queryByPage(Pagination page, Long orderId);

    /**
     *
     * @description: 获取评估公司（微信端）
     * @createTime: 2017年9月25日 下午4:43:13
     * @author: hhd
     * @param page
     * @param totalNum
     * @param orderid
     * @return
     */
    ServiceResult getBankByPage(Page<OrderBank> page, Long totalNum, Long orderid);

    /**
     *
     * @description: 根据id获取评估公司详细（微信端）
     * @createTime: 2017年9月26日 下午4:43:13
     * @author: hhd
     * @param id
     * @return
     */
    OrderBank getBankById(Long id);

    /**
     * 修改签约银行
     * @param ids
     * @param time
     * @return
     */
    ServiceResult mulUpdateStatus(Long ids,String time);

    /**
     * 新增签约银行
     * @param m
     * @return
     */
    ServiceResult addOrderBank(OrderBank m);
}

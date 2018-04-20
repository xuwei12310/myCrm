package com.xuwei.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xuwei.bean.OrderReceivables;
import com.xuwei.util.ServiceResult;
 
/**
 * @description: 订单_收款账号服务类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月15日 15:42:31
 * @author: hhd
 * @version: 1.0
 */
public interface IOrderReceivablesService extends IService<OrderReceivables> {
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
    ServiceResult getReceivablesByPage(Page<OrderReceivables> page, Long totalNum, Long orderid);
}

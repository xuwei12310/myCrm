package com.xuwei.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.IService;
import com.xuwei.bean.OrderCompany;
import com.xuwei.util.ServiceResult;

/**
 * @description: 订单_评估公司服务类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月18日 17:46:41
 * @author: hhd
 * @version: 1.0
 */
public interface IOrderCompanyService extends IService<OrderCompany> {
    /**
     * 分页查询订单的评估公司信息
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
    ServiceResult getCompanyByPage(Page<OrderCompany> page, Long totalNum, Long orderid);

    /**
     *
     * @description: 获取评估公司实体详情（微信端）
     * @createTime: 2017年9月25日 下午4:43:13
     * @author: hhd
     * @param id
     * @return
     */
    OrderCompany getCompanyById(Long id);
}

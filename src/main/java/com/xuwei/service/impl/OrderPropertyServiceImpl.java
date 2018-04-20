package com.xuwei.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xuwei.bean.CustomerProperty;
import com.xuwei.bean.OrderProperty;
import com.xuwei.mapper.CustomerPropertyMapper;
import com.xuwei.mapper.OrderPropertyMapper;
import com.xuwei.service.IOrderPropertyService;
import com.xuwei.util.ServiceResult;

/**
 * @description: 订单_抵押房产服务实现类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月15日 09:41:57
 * @author: hhd
 * @version: 1.0
 */
@Service
public class OrderPropertyServiceImpl extends ServiceImpl<OrderPropertyMapper, OrderProperty> implements IOrderPropertyService {

    @Resource
    private OrderPropertyMapper orderPropertyMapper;
    @Resource
    private CustomerPropertyMapper customerPropertyMapper;
    @Override
    public int deletePropertyByOrder(List<String> ids, Long orderId) {
        return orderPropertyMapper.deletePropertyByOrder(ids,orderId);
    }

    @Override
    public ServiceResult getPropertyByPage(Page<OrderProperty> page, Long totalNum, Long orderid) {
        ServiceResult result = new ServiceResult(false);
        EntityWrapper<OrderProperty> ew= new EntityWrapper<>();
        ew.eq("order_id",orderid);
        List<OrderProperty> list = orderPropertyMapper.selectPage(page, ew);
        EntityWrapper<CustomerProperty> ewcp = new EntityWrapper<>();
        List<Long> arrayId = new ArrayList<>();
        for (OrderProperty op:list){
            arrayId.add(op.getCustomerPropertyId());
        }
        if(arrayId.size()==0){
            List<CustomerProperty> lists = new ArrayList<>();
            result.addData("orderPropertyList", lists);
            result.setIsSuccess(true);
            return result;
        }
        ewcp.in("id",arrayId);
        List<CustomerProperty> cpList = customerPropertyMapper.selectPage(page,ewcp);
        if(page.getTotal()==totalNum && totalNum!=0){
            List<CustomerProperty> lists = new ArrayList<>();
            result.addData("orderPropertyList", lists);
            result.setIsSuccess(true);
            return result;
        }
        result.addData("orderPropertyList", cpList);
        result.setIsSuccess(true);
        return result;
    }

 /*   @Override
    public CustomerProperty getPropertyById(Long id) {
        CustomerProperty cp = customerPropertyMapper.getCustomerPropertyInfo(id);
        return cp;
    }*/
}

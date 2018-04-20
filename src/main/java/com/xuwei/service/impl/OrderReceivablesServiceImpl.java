package com.xuwei.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xuwei.bean.OrderReceivables;
import com.xuwei.mapper.OrderReceivablesMapper;
import com.xuwei.service.IOrderReceivablesService;
import com.xuwei.util.ServiceResult;

/**
 * @description: 订单_收款账号服务实现类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月15日 15:42:31
 * @author: hhd
 * @version: 1.0
 */
@Service
public class OrderReceivablesServiceImpl extends ServiceImpl<OrderReceivablesMapper, OrderReceivables> implements IOrderReceivablesService {
    @Resource
    private OrderReceivablesMapper orderReceivablesMapper;
    @Override
    public ServiceResult getReceivablesByPage(Page<OrderReceivables> page, Long totalNum, Long orderid) {
        ServiceResult result = new ServiceResult(false);
        EntityWrapper<OrderReceivables> ew = new EntityWrapper<>();
        ew.eq("order_id",orderid);
        List<OrderReceivables> list = orderReceivablesMapper.selectPage(page, ew);
        if(page.getTotal()==totalNum && totalNum!=0){
            List<OrderReceivables> lists = new ArrayList<>();
            result.addData("orderReceivablesList", lists);
            result.setIsSuccess(true);
            return result;
        }
        result.addData("orderReceivablesList", list);
        result.setIsSuccess(true);
        return result;
    }
}

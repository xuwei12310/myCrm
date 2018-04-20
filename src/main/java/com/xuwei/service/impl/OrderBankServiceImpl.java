package com.xuwei.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xuwei.bean.Order;
import com.xuwei.bean.OrderBank;
import com.xuwei.bean.OrderCompany;
import com.xuwei.mapper.OrderBankMapper;
import com.xuwei.mapper.OrderMapper;
import com.xuwei.service.IOrderBankService;
import com.xuwei.util.DateUtil;
import com.xuwei.util.OperateUtils;
import com.xuwei.util.ServiceResult;

/**
 * @description: 订单_合作银行服务实现类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月18日 15:00:36
 * @author: hhd
 * @version: 1.0
 */
@Service
public class OrderBankServiceImpl extends ServiceImpl<OrderBankMapper, OrderBank> implements IOrderBankService {

    @Resource
    private OrderBankMapper orderBankMapper;
    @Resource
    private OrderMapper orderMapper;

    @Override
    public List<Map<String, Object>> queryByPage(Pagination page, Long orderId) {
        return orderBankMapper.queryByPage(page,orderId);
    }

    @Override
    public ServiceResult getBankByPage(Page<OrderBank> page, Long totalNum, Long orderid) {
        ServiceResult result = new ServiceResult(false);
        List<OrderBank> list = orderBankMapper.selectListByPage(page, orderid);
        if(page.getTotal()==totalNum && totalNum!=0){
            List<OrderCompany> lists = new ArrayList<>();
            result.addData("orderBankList", lists);
            result.setIsSuccess(true);
            return result;
        }
        result.addData("orderBankList", list);
        result.setIsSuccess(true);
        return result;
    }

    @Override
    public OrderBank getBankById(Long id) {
        return orderBankMapper.getBankById(id);
    }

    @Transactional
    @Override
    public ServiceResult mulUpdateStatus(Long id, String time) {
        ServiceResult result = new ServiceResult(false);
        if(id==null){
            result.setMessage("请选择要修改的数据行");
            return result;
        }
        OrderBank orderBank = this.selectById(id);
        Long orderid = orderBank.getOrder().getId();
        EntityWrapper<OrderBank> ew=new EntityWrapper<>();
        ew.eq("order_id",orderid);
        List<OrderBank> list = this.selectList(ew);
        for (OrderBank ob:list){
            ob.setSingTime("");
            ob.setIsSign(0);
        }
        if(list.size()>0){
            this.updateBatchById(list);
        }
        Order order = orderMapper.selectById(orderid);
        order.setStatus(2);
        order.setSigningDate(time);
        order.updateById();
        orderBank.setIsSign(1);
        orderBank.setSingTime(time);
        boolean isSuccess = this.updateById(orderBank);
        result.setIsSuccess(isSuccess);
        return result;
    }

    @Transactional
    @Override
    public ServiceResult addOrderBank(OrderBank m) {
        ServiceResult result = new ServiceResult(false);
        m.setIsSign(0);
        m.setCreatorId(OperateUtils.getCurrentUserId());
        m.setCreateTime(DateUtil.getNowTimestampStr());
        m.setLastModifierId(OperateUtils.getCurrentUserId());
        m.setLastModifyTime(DateUtil.getNowTimestampStr());
        boolean isSuccess = this.insert(m);
        result.setIsSuccess(isSuccess);
        return result;
    }
}

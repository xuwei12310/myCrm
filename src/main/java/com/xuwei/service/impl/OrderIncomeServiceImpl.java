package com.xuwei.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xuwei.bean.Order;
import com.xuwei.bean.OrderIncome;
import com.xuwei.mapper.OrderIncomeMapper;
import com.xuwei.mapper.OrderMapper;
import com.xuwei.mapper.PayMapper;
import com.xuwei.service.IOrderIncomeService;
import com.xuwei.util.DateUtil;
import com.xuwei.util.OperateUtils;

/**
 * @description: 订单_收入服务实现类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月18日 11:12:28
 * @author: xw
 * @version: 1.0
 */
@Service
public class OrderIncomeServiceImpl extends ServiceImpl<OrderIncomeMapper, OrderIncome> implements IOrderIncomeService {
	@Resource
	private OrderIncomeMapper orderIncomeMapper;
	@Resource 
	private OrderMapper orderMapper;
	@Resource
	private PayMapper payMapper;
	@Override
	public Page<OrderIncome> listByPage(Page<OrderIncome> pageM, OrderIncome income) {
			List<OrderIncome> list=orderIncomeMapper.listByPage(pageM,income);
			pageM.setRecords(list);
			return pageM;
		}
	@Override
	@Transactional
	public void updateOrderIncome(OrderIncome orderIncome) {
		OrderIncome oldM = orderIncomeMapper.selectById(orderIncome.getId());
		oldM.setReceiveAmount(orderIncome.getReceiveAmount());
		oldM.setReceivedAmount(new BigDecimal("0"));
    	oldM.setReceivingAmount(orderIncome.getReceiveAmount());
		oldM.setNote(orderIncome.getNote());
    	oldM.setLastModifierId(OperateUtils.getCurrentUserId());
    	oldM.setLastModifyTime(DateUtil.getNowTimestampStr());
    	orderIncomeMapper.updateAllColumnById(oldM);
    	//修改返佣参考金额
    	Order order=orderMapper.selectById(orderIncome.getOrder().getId());
    	if(order.getCommissionRate()!=null){
    		BigDecimal commissionAmount=order.getCommissionRate().multiply(orderIncome.getReceiveAmount());
    		payMapper.updateCommission(order.getId(),commissionAmount);
    	}
	}
	
}

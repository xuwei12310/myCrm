package com.xuwei.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xuwei.bean.OrderLoan;
import com.xuwei.mapper.OrderLoanMapper;
import com.xuwei.service.IOrderLoanService;
import com.xuwei.util.ServiceResult;

/**
 * @description: 订单_借款服务实现类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月19日 09:29:11
 * @author: hhd
 * @version: 1.0
 */
@Service
public class OrderLoanServiceImpl extends ServiceImpl<OrderLoanMapper, OrderLoan> implements IOrderLoanService {
	

	@Resource
	private OrderLoanMapper orderLoanMapper;

	@Override
	public Page<OrderLoan> listByPage(Page<OrderLoan> pageM, OrderLoan orderLoan) {
		List<OrderLoan> list=orderLoanMapper.listByPage(pageM,orderLoan);
		pageM.setRecords(list);
		return pageM;
	}

	@Override
	public ServiceResult getLoanByPage(Page<OrderLoan> page, Long totalNum, Long orderid) {
		ServiceResult result = new ServiceResult(false);
		EntityWrapper<OrderLoan> ew= new EntityWrapper<>();
		ew.eq("order_id",orderid);
		List<OrderLoan> list = orderLoanMapper.selectPage(page, ew);
		if(page.getTotal()==totalNum && totalNum!=0){
			List<OrderLoan> lists = new ArrayList<>();
			result.addData("orderLoanList", lists);
			result.setIsSuccess(true);
			return result;
		}
		result.addData("orderLoanList", list);
		result.setIsSuccess(true);
		return result;
	}


}

package com.xuwei.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xuwei.bean.IncomeDistribute;
import com.xuwei.bean.OrderIncome;
import com.xuwei.bean.OrderLoan;
import com.xuwei.mapper.IncomeDistributeMapper;
import com.xuwei.mapper.OrderIncomeMapper;
import com.xuwei.mapper.OrderLoanMapper;
import com.xuwei.service.IIncomeDistributeService;

/**
 * @description: 收入分配服务实现类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月26日 09:36:12
 * @author: xw
 * @version: 1.0
 */
@Service
public class IncomeDistributeServiceImpl extends ServiceImpl<IncomeDistributeMapper, IncomeDistribute> implements IIncomeDistributeService {
	@Resource
	private IncomeDistributeMapper incomeDistributeMapper;
	@Resource
	private OrderLoanMapper orderLoanMapper;
	@Resource
	private OrderIncomeMapper orderIncomeMapper;
	@Override
	public Page<IncomeDistribute> listByPage(Page<IncomeDistribute> pageM, Long incomeId) {
		List<IncomeDistribute> list=incomeDistributeMapper.listByPage(pageM,incomeId);
		pageM.setRecords(list);
		return pageM;
	}
	@Override
	@Transactional
	public void updateItem(IncomeDistribute m, Long fromId, BigDecimal pay,BigDecimal result) {
		String costType=m.getCostType();
		if(costType.equals("回款")){
			OrderLoan orderLoan = orderLoanMapper.selectById(fromId);
			BigDecimal recevingAmount=orderLoan.getReceivingAmount().subtract(result);
			BigDecimal receviedAmount=orderLoan.getReceivedAmount().add(result);
			orderLoan.setReceivedAmount(receviedAmount);
			orderLoan.setReceivingAmount(recevingAmount);
			orderLoan.updateById();
		}else{
			OrderIncome orderIncome = orderIncomeMapper.selectById(fromId);
			BigDecimal recevingAmount=orderIncome.getReceivingAmount().subtract(result);
			BigDecimal receviedAmount=orderIncome.getReceivedAmount().add(result);
			orderIncome.setReceivedAmount(receviedAmount);
			orderIncome.setReceivingAmount(recevingAmount);
			orderIncome.updateById();
		}
		IncomeDistribute incomeDistribute = incomeDistributeMapper.selectById(m.getId());
		incomeDistribute.setAmount(pay);
		incomeDistribute.setNote(m.getNote());
		incomeDistribute.updateById();
	}
	@Override
	public List<IncomeDistribute> selectIncomeDistribute(IncomeDistribute id) {
		return incomeDistributeMapper.selectIncomeDistribute(id);
	}
	
}

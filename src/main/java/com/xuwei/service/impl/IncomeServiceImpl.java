package com.xuwei.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xuwei.bean.Customer;
import com.xuwei.bean.Income;
import com.xuwei.bean.IncomeDistribute;
import com.xuwei.bean.OrderIncome;
import com.xuwei.bean.OrderLoan;
import com.xuwei.bean.User;
import com.xuwei.mapper.IncomeMapper;
import com.xuwei.service.IIncomeDistributeService;
import com.xuwei.service.IIncomeService;
import com.xuwei.service.IOrderIncomeService;
import com.xuwei.service.IOrderLoanService;
import com.xuwei.util.DateUtil;
import com.xuwei.util.OperateUtils;

/**
 * @description: 收入登记服务实现类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月05日 09:07:52
 * @author: zyd
 * @version: 1.0
 */
@Service
public class IncomeServiceImpl extends ServiceImpl<IncomeMapper, Income> implements IIncomeService {

	@Resource
	private IncomeMapper incomeMapper;
	@Resource 
	private IOrderLoanService orderLoanService;
	@Resource
	private IOrderIncomeService orderIncomeService;
	@Resource
	private IIncomeDistributeService incomeDistributeService;
	@Override
	public List<Income> listIncomeByPage(Page<Income> pageM, Income m,EntityWrapper<Income> ew) {
		return incomeMapper.listIncomeByPage(pageM, m,ew);
	}

	@Override
	public List<Customer> getCustemerByList(Page<Customer> pageM, String customerName) {
		return incomeMapper.getCustemerByList(pageM, customerName);
	}

	@Override
	public List<User> getOwnerByList(Page<User> pageM, String userName) {
		return incomeMapper.getOwnerByList(pageM, userName);
	}

	@Override
	public Page<Map<String, Object>> getIncomeItemList(Page<Map<String, Object>> pageM, Long orderId) {
		List<Map<String,Object>> list=incomeMapper.getIncomeItemList(pageM,orderId);
		pageM.setRecords(list);
		return pageM;
	}

	@Override
	@Transactional
	public void insert(Income m, String incomeItem,String loanItem) {
		/*
		 * 1.插入收入登记
		 * 2.从订单借款和订单收入表取相关数据
		 * 3.更新借款和订单收入的已收和未收金额
		 * 4.插入分配
		 */
		incomeMapper.insert(m);
		if(!StringUtils.isEmpty(incomeItem)){
			String[] split = incomeItem.split("_");
			for(int i=0;i<split.length;i++){
				//spilt2[0]订单收入或借款Id,[1]本次收款金额[2]备注
				String[] split2 = split[i].split("\\^");
				OrderIncome orderIncome=orderIncomeService.selectById(split2[0]);
				IncomeDistribute distribute=new IncomeDistribute();
				distribute.setIncome(m);
				distribute.setOrderIncome(orderIncome);
				if(split2.length>1&&!StringUtils.isEmpty(split2[1])){
					BigDecimal bg=new BigDecimal(split2[1]);
					distribute.setAmount(bg);
					if(orderIncome.getReceivedAmount()!=null){
						bg=bg.add(orderIncome.getReceivedAmount());
					}
					orderIncome.setReceivedAmount(bg);
					if(orderIncome.getReceiveAmount()!=null){
						orderIncome.setReceivingAmount(orderIncome.getReceiveAmount().subtract(orderIncome.getReceivedAmount()));
					}
				}
				if(split2.length>2 &&!StringUtils.isEmpty(split2[2])){
					distribute.setNote(split2[2]);
				}
				distribute.setCreateTime(DateUtil.getNowTimesminutStr());
				distribute.setCreatorId(OperateUtils.getCurrentUserId());
				distribute.setLastModifierId(OperateUtils.getCurrentUserId());
				distribute.setLastModifyTime(DateUtil.getNowTimesminutStr());
				distribute.insert();
				orderIncome.updateById();
			}
		}
		if(!StringUtils.isEmpty(loanItem)){
			String[] split = loanItem.split("_");
			for(int i=0;i<split.length;i++){
				String[] split2 = split[i].split("\\^");
				OrderLoan orderLoan=orderLoanService.selectById(split2[0]);
				IncomeDistribute distribute=new IncomeDistribute();
				distribute.setIncome(m);
				distribute.setOrderLoan(orderLoan);
				if(split2.length>1&&!StringUtils.isEmpty(split2[1])){
					BigDecimal bg=new BigDecimal(split2[1]);
					BigDecimal bg1=bg.multiply(new BigDecimal("10000"));
					distribute.setAmount(bg1);
					if(orderLoan.getReceivedAmount()!=null){
						bg=bg.add(orderLoan.getReceivedAmount());
					}
					orderLoan.setReceivedAmount(bg);
					orderLoan.setReceivingAmount(orderLoan.getReceiveAmount().subtract(orderLoan.getReceivedAmount()));
				}
				if(split2.length>2 &&!StringUtils.isEmpty(split2[2])){
					distribute.setNote(split2[2]);
				}
				distribute.setCreateTime(DateUtil.getNowTimesminutStr());
				distribute.setCreatorId(OperateUtils.getCurrentUserId());
				distribute.setLastModifierId(OperateUtils.getCurrentUserId());
				distribute.setLastModifyTime(DateUtil.getNowTimesminutStr());
				distribute.insert();
				orderLoan.updateById();
			}
		}
		
	}

	@Override
	@Transactional
	public void update(Income oldM, String incomeItem, String loanItem) {
		/*
		 * 1.更新收入登记
		 * 2.从订单借款和订单收入表取相关数据
		 * 3.更新借款和订单收入的已收和未收金额
		 * 4.修改分配
		 */
		incomeMapper.updateAllColumnById(oldM);
		if(!StringUtils.isEmpty(incomeItem)){
			String[] split = incomeItem.split("_");
			for(int i=0;i<split.length;i++){
				//spilt2[0]收入登记id，[1]订单收入或借款Id,[2]本次收款金额[3]备注
				String[] split2 = split[i].split("\\^");
				OrderIncome orderIncome=orderIncomeService.selectById(split2[1]);
				IncomeDistribute distribute=incomeDistributeService.selectById(split2[0]);
				distribute.setIncome(oldM);
				distribute.setOrderIncome(orderIncome);
				if(split2.length>2&&!StringUtils.isEmpty(split2[2])){
					BigDecimal bg=new BigDecimal(split2[2]);
					BigDecimal bg2=bg.subtract(distribute.getAmount());
					distribute.setAmount(bg);
					if(orderIncome.getReceivedAmount()!=null){
						bg2=bg2.add(orderIncome.getReceivedAmount());
					}
					orderIncome.setReceivedAmount(bg2);
					if(orderIncome.getReceiveAmount()!=null){
						orderIncome.setReceivingAmount(orderIncome.getReceiveAmount().subtract(orderIncome.getReceivedAmount()));
					}
				}
				if(split2.length>3 &&!StringUtils.isEmpty(split2[3])){
					distribute.setNote(split2[3]);
				}
				distribute.setLastModifierId(OperateUtils.getCurrentUserId());
				distribute.setLastModifyTime(DateUtil.getNowTimesminutStr());
				distribute.updateById();
				orderIncome.updateById();
			}
		}
		if(!StringUtils.isEmpty(loanItem)){
			String[] split = loanItem.split("_");
			for(int i=0;i<split.length;i++){
				String[] split2 = split[i].split("\\^");
				OrderLoan orderLoan=orderLoanService.selectById(split2[1]);
				IncomeDistribute distribute=incomeDistributeService.selectById(split2[0]);
				distribute.setIncome(oldM);
				distribute.setOrderLoan(orderLoan);
				if(split2.length>1&&!StringUtils.isEmpty(split2[1])){
					BigDecimal bg=new BigDecimal(split2[2]);
					BigDecimal bg1=bg.multiply(new BigDecimal("10000"));
					BigDecimal bg2=bg.subtract(distribute.getAmount().divide(new BigDecimal("10000")));
					distribute.setAmount(bg1);
					if(orderLoan.getReceivedAmount()!=null){
						bg2=bg2.add(orderLoan.getReceivedAmount());
					}
					orderLoan.setReceivedAmount(bg2);
					orderLoan.setReceivingAmount(orderLoan.getReceiveAmount().subtract(orderLoan.getReceivedAmount()));
				}
				if(split2.length>3 &&!StringUtils.isEmpty(split2[3])){
					distribute.setNote(split2[3]);
				}
				distribute.setLastModifierId(OperateUtils.getCurrentUserId());
				distribute.setLastModifyTime(DateUtil.getNowTimesminutStr());
				distribute.updateById();
				orderLoan.updateById();
			}
		}
	}

	@Override
	@Transactional
	public void delete(List<String> asList) {
		/*
		 * 1.计算本次分配的金额
		 * 2.更新借款和订单收入的已收和未收金额
		 * 3.删除分配
		 * 4.删除收入
		 */
		for (String string : asList) {
			Long incomeId=Long.valueOf(string);
			Income income=new Income();
			income.setId(incomeId);
			IncomeDistribute id=new IncomeDistribute();
			id.setIncome(income);
			List<IncomeDistribute> list=incomeDistributeService.selectIncomeDistribute(id);
			for (IncomeDistribute incomeDistribute :list) {
				if(incomeDistribute.getOrderIncome()!=null && incomeDistribute.getOrderIncome().getReceiveAmount()!=null){
					OrderIncome orderIncome=incomeDistribute.getOrderIncome();
					BigDecimal bg=orderIncome.getReceivedAmount().subtract(incomeDistribute.getAmount());
					BigDecimal bg2=orderIncome.getReceivingAmount().add(incomeDistribute.getAmount());
					orderIncome.setReceivedAmount(bg);
					orderIncome.setReceivingAmount(bg2);
					orderIncome.updateById();
				}else if(incomeDistribute.getOrderLoan()!=null){
					OrderLoan orderLoan=incomeDistribute.getOrderLoan();
					BigDecimal bg1=incomeDistribute.getAmount().divide(new BigDecimal("10000"));
					BigDecimal bg=orderLoan.getReceivedAmount().subtract(bg1);
					BigDecimal bg2=orderLoan.getReceivingAmount().add(bg1);
					orderLoan.setReceivedAmount(bg);
					orderLoan.setReceivingAmount(bg2);
					orderLoan.updateById();
				}
				incomeDistribute.deleteById();
			}
			income.deleteById();
		}
	}
	
	
	
}

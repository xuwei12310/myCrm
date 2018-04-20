package com.xuwei.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.xuwei.bean.BrokerageDistribute;
import com.xuwei.bean.BrokerageRule;
import com.xuwei.bean.Order;
import com.xuwei.mapper.BrokerageDistributeMapper;
import com.xuwei.mapper.BrokerageRuleMapper;
import com.xuwei.mapper.OrderMapper;
import com.xuwei.service.IBrokerageDistributeService;
import com.xuwei.util.DateUtil;
import com.xuwei.util.OperateUtils;
import com.xuwei.util.ServiceResult;

/**
 * @description: 佣金分配服务实现类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月25日 19:57:40
 * @author: caw
 * @version: 1.0
 */
@Service
public class BrokerageDistributeServiceImpl extends ServiceImpl<BrokerageDistributeMapper, BrokerageDistribute> implements IBrokerageDistributeService {
	@Resource
	private BrokerageDistributeMapper brokerageDistributeMapper;
	@Resource
	private OrderMapper orderMapper;
	@Resource
	private BrokerageRuleMapper brokerageRuleMapper;
	
	/**
	 * 根据订单id获取佣金分配信息
	 */
	@Override
	public List<BrokerageDistribute> listBrokerageByPage(BrokerageDistribute m) {
		return brokerageDistributeMapper.listBrokerageByPage(m);
	}

	/**
	 * 生成佣金分配
	 */
	@Override
	public boolean addBrokerageDistribute(Long orderId) {
		Order order = orderMapper.selectById(orderId);
		BigDecimal brokerageNum = new BigDecimal(0);
		try{
			if(order!=null){
				BrokerageRule brokerageRule = brokerageRuleMapper.findByLIMITOne();
				for(int i = 1;i<5;i++){
					BrokerageDistribute brokerageDistribute = new BrokerageDistribute();
					brokerageDistribute.setCreatorId(OperateUtils.getCurrentUserId());
					brokerageDistribute.setCreateTime(DateUtil.getNowTimestampStr());
					brokerageDistribute.setLastModifierId(OperateUtils.getCurrentUserId());
					brokerageDistribute.setLastModifyTime(DateUtil.getNowTimestampStr());
					brokerageDistribute.setOrder(order);
					if(i==1){
						brokerageDistribute.setOwnerType(i);
						brokerageDistribute.setOwner(order.getOwner());
						if(order.getLoanMoney() != null && order.getReceiveAmount() != null){
							BigDecimal data = brokerageRule.getRuleOwner().divide(new BigDecimal(100));
							BigDecimal data2 = order.getReceiveAmount().subtract(order.getLoanMoney());
							BigDecimal data1 = data2.multiply(data);
							brokerageDistribute.setActual(data1);
							brokerageDistribute.setReference(data1);
						}else{
							brokerageDistribute.setActual(new BigDecimal(0));
							brokerageDistribute.setReference(new BigDecimal(0));
						}
						brokerageNum = brokerageNum.add(brokerageDistribute.getActual());
					}else if(i==2){
						brokerageDistribute.setOwnerType(i);
						brokerageDistribute.setOwner(order.getFollowUser());
						if(order.getFollowUser().getId().equals(order.getOwner().getId())){
							brokerageDistribute.setActual(new BigDecimal(0));
							brokerageDistribute.setReference(new BigDecimal(0));
						}else{
							brokerageDistribute.setActual(brokerageRule.getRuleFollow());
							brokerageDistribute.setReference(brokerageRule.getRuleFollow());
						}
						brokerageNum = brokerageNum.add(brokerageDistribute.getActual());
					}else if(i==3){
						brokerageDistribute.setOwnerType(i);
						brokerageDistribute.setOwner(order.getCsPrincipal());
						brokerageDistribute.setActual(brokerageRule.getRuleCsPrincipal());
						brokerageDistribute.setReference(brokerageRule.getRuleCsPrincipal());
						brokerageNum = brokerageNum.add(brokerageDistribute.getActual());
					}else if(i==4){
						brokerageDistribute.setOwnerType(i);
						brokerageDistribute.setOwner(order.getCsAssistant());
						brokerageDistribute.setActual(brokerageRule.getRuleCsAssistant());
						brokerageDistribute.setReference(brokerageRule.getRuleCsAssistant());
						brokerageNum = brokerageNum.add(brokerageDistribute.getActual());
					}
					brokerageDistributeMapper.insert(brokerageDistribute);
				}
				order.setBrokerageTotal(brokerageNum);
				orderMapper.updateAllColumnById(order);
			}
			return true;
		}catch(Exception e){
			return false;
		}
	}

	/**
	 * 修改发放状态
	 */
	@Override
	public ServiceResult modGrantStatus(Integer param, String[] idArray) {
		ServiceResult result = new ServiceResult(false);
		String data = "";
		try{
			for (String id : idArray) {
				Order order = orderMapper.selectById(id);
				if(param == 1){
					if(order.getBrokerageAuditStatus() != null && order.getBrokerageAuditStatus() == 3){
						order.setBrokerageGrantState(param);
					}else{
						data = data + order.getOrderCode() + ",";
					}
				}else{
					order.setBrokerageGrantState(param);
				}
				order.setLastModifierId(OperateUtils.getCurrentUserId());
				order.setLastModifyTime(DateUtil.getNowTimestampStr());
				orderMapper.updateAllColumnById(order);
			}
		}catch(Exception e){
			result.setMessage("没修改记录");
			return result;
		}
		if(StringUtils.isEmpty(data)){
			result.setIsSuccess(true);
			return result;
		}else{
			result.setMessage("订单["+data.substring(0,data.length() - 1)+"]未审核通过");
			return result;
		}
	}

	/**
	 * 获取订单信息
	 */
	@Override
	public Order getOrderBrokerageById(Long orderId) {
		return brokerageDistributeMapper.getOrderBrokerageById(orderId);
	}

	/**
	 * 修改佣金总额
	 */
	@Override
	public boolean modBrokerageTotal(Long orderId) {
		List<BrokerageDistribute> list = brokerageDistributeMapper.getBrokerageList(orderId);
		BigDecimal brokerageNum = new BigDecimal(0);
		for(BrokerageDistribute bd:list){
			brokerageNum = brokerageNum.add(bd.getActual());
		}
		Order order = orderMapper.selectById(orderId);
		order.setBrokerageTotal(brokerageNum);
		orderMapper.updateAllColumnById(order);
		return true;
	}

	@Override
	public List<BrokerageDistribute> getCommissionStatisticsList(Page<BrokerageDistribute> pageM, EntityWrapper ew, int viewType) {
		return brokerageDistributeMapper.getCommissionStatisticsList(pageM,ew,viewType);
	}

	@Override
	public List<BrokerageDistribute> grantOrderList(Page<BrokerageDistribute> pageM, EntityWrapper ew) {

		return brokerageDistributeMapper.grantOrderList(pageM,ew);
	}
}

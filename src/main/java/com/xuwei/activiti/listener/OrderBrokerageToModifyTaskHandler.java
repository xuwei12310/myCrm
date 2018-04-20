package com.xuwei.activiti.listener;

import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.xuwei.bean.Order;
import com.xuwei.service.IOrderService;
import com.xuwei.util.DateUtil;
import com.xuwei.util.OperateUtils;

/**
 * 
 * @description: 佣金结算重新调整监听
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年9月26日下午5:39:44
 * @author：caw
 * @version：1.0
 */
@Component
public class OrderBrokerageToModifyTaskHandler implements TaskListener {

	private static final long serialVersionUID = 1L;

	@Resource
	RuntimeService runtimeService;

	@Resource
	IOrderService orderService;
	@Override
	public void notify(DelegateTask delegateTask) {
		Object userObj = delegateTask.getVariable("userId");
		String processInstanceId = delegateTask.getProcessInstanceId();
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
				.processInstanceId(processInstanceId)// 使用流程实例ID查询
				.singleResult();
		String businessKey = pi.getBusinessKey();
		if(userObj!=null&&businessKey!=null){
			Object orderObj = delegateTask.getVariable("orderBrokerageId");
			Order order= orderService.selectById((Long)orderObj);
			order.setBrokerageAuditStatus(4);;
			order.setLastModifierId(OperateUtils.getCurrentUserId());
			order.setLastModifyTime(DateUtil.getNowTimestampStr());
			order.updateById();
			List<String> managerIdList = Lists.newArrayList();
			managerIdList.add(userObj.toString());
			delegateTask.addCandidateUsers(managerIdList);
		}
	}
}
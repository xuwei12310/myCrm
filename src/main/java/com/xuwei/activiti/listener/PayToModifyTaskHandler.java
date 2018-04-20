package com.xuwei.activiti.listener;


import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.xuwei.bean.Pay;
import com.xuwei.service.IPayService;
import com.xuwei.util.DateUtil;
import com.xuwei.util.OperateUtils;

/**
 * @description 重新调整监听
 * @copyright 福建骏华信息有限公司 (c)2015
 * @createDate 2017-9-7
 * @author hhd
 * @version 1.0
 */
@Component
public class PayToModifyTaskHandler implements TaskListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource
	RuntimeService runtimeService;

	@Resource
	IPayService payService;

	@Override
	public void notify(DelegateTask delegateTask) {
		Object userObj = delegateTask.getVariable("userId");
		String processInstanceId = delegateTask.getProcessInstanceId();
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
				.processInstanceId(processInstanceId)// 使用流程实例ID查询
				.singleResult();
		String businessKey = pi.getBusinessKey();
		if(userObj!=null&&businessKey!=null){
			Object orderObj = delegateTask.getVariable("payId");
			Pay pay = payService.selectById((Long)orderObj);
			pay.setAuditStatus(4);
			pay.setLastModifierId(OperateUtils.getCurrentUserId());
			pay.setLastModifyTime(DateUtil.getNowTimestampStr());
			pay.updateById();
			List<String> managerIdList = Lists.newArrayList();
			managerIdList.add(userObj.toString());
			delegateTask.addCandidateUsers(managerIdList);
		}
	}
}
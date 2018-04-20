package com.xuwei.activiti.listener;

import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.xuwei.bean.User;
import com.xuwei.service.IUserService;

/**
 * @description 公司总经理
 * @copyright 福建骏华信息有限公司 (c)2015
 * @createDate 2017-9-7
 * @author hhd
 * @version 1.0
 */
@Component
public class CompanyManagerTaskHandler implements TaskListener {

	private static final long serialVersionUID = -8689801216525149538L;

	@Resource
	IUserService userService;
	@Resource
	RuntimeService runtimeService;
	
	@Override
	public void notify(DelegateTask delegateTask) {
		Object userObj = delegateTask.getVariable("userId");
		String processInstanceId = delegateTask.getProcessInstanceId();
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
				.processInstanceId(processInstanceId)// 使用流程实例ID查询
				.singleResult();
		String businessKey = pi.getBusinessKey();
		if(userObj!=null&&businessKey!=null){
			List<User> managerUser = userService.getManagerByUserId(Long.parseLong(userObj.toString()));
			if(managerUser!=null){
				List<String> managerIdList = Lists.newArrayList();
				for (User u : managerUser){
					managerIdList.add(u.getId().toString());
				}
				delegateTask.addCandidateUsers(managerIdList);
			}
		}
	}
}

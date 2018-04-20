package com.xuwei.activiti.listener;/**
 * Created by hhd47 on 2017.9.7.
 */

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;
import com.xuwei.bean.Order;
import com.xuwei.bean.User;
import com.xuwei.service.IOrderService;
import com.xuwei.service.IUserService;

/**
 * @description:
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 9-7 10:41
 * @author：hhd
 * @version：1.0
 */
@Component
public class OrderLoanApprovalTaskHandler implements TaskListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
    IUserService userService;
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
        System.out.println(businessKey);
        if(userObj!=null&&businessKey!=null){
            List<User> managerUser = userService.getManagerByUserId(Long.parseLong(userObj.toString()));
            Map<String, Object> variables = Maps.newHashMap();
            if(managerUser!=null){
                int flag = 0;
                for (User u : managerUser){
                    if(u.getId().equals(userObj)){
                        Object orderObj = delegateTask.getVariable("orderId");
                        Order order = orderService.selectById((Long)orderObj);
                        order.setAuditStatus(3);
                        order.updateById();
                        flag=1;
                        break;
                    }
                }
                if(flag==1){
                    variables.put("deptManager",1);
                }else {
                    variables.put("deptManager",0);
                }
            }else {
                variables.put("deptManager",0);
            }
            delegateTask.setVariables(variables);
        }
    }
}

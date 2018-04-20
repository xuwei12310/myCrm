package com.xuwei.activiti.service.impl;/**
 * Created by hhd47 on 2017.9.7.
 */


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.google.common.collect.Maps;
import com.xuwei.activiti.service.IActivitiProcessService;
import com.xuwei.activiti.utils.ProcessEnum;
import com.xuwei.bean.Order;
import com.xuwei.bean.OrderIncome;
import com.xuwei.bean.OrderLoan;
import com.xuwei.bean.Pay;
import com.xuwei.bean.User;
import com.xuwei.mapper.ActivitiMapper;
import com.xuwei.service.IBrokerageDistributeService;
import com.xuwei.service.IOrderIncomeService;
import com.xuwei.service.IOrderLoanService;
import com.xuwei.service.IOrderService;
import com.xuwei.service.IPayService;
import com.xuwei.util.DateUtil;
import com.xuwei.util.OperateUtils;
import com.xuwei.util.ServiceResult;
import com.xuwei.util.SpringUtils;

/**
 * @description:
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 9-710:55
 * @author：hhd
 * @version：1.0
 */
@Service
public class ActivitiProcessServiceImpl implements IActivitiProcessService {

    @Resource
    private RuntimeService runtimeService;
    @Resource
    private IdentityService identityService;
    @Resource
    private TaskService taskService;
    @Resource
    private ActivitiMapper activitiMapper;
    @Resource
    RepositoryService repositoryService;
    @Resource
    private IOrderService orderService;
    @Resource
    private IOrderLoanService orderLoanService;
    @Resource
    private IPayService payService;
    @Resource
    private IBrokerageDistributeService brokerageDistributeService;
    @Resource
    private IOrderIncomeService orderIncomeService;

    @Override
    @Transactional
    public ServiceResult completeTask(String taskId, String transition, String comment) {
        ServiceResult result = new ServiceResult(false);

        // 插入备注
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();// 使用任务ID查询
        User user = OperateUtils.getCurrentUser();
        Authentication.setAuthenticatedUserId(user.getId().toString());
        // 获取流程实例ID
        String processInstanceId = task.getProcessInstanceId();
        ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
                .processInstanceId(processInstanceId)// 使用流程实例ID查询
                .singleResult();
        // 更新摘要
        //runtimeService.setProcessInstanceName(pi.getId(),user.getName()+transition);
        String processDefinitionKey = pi.getProcessDefinitionKey();
        if(comment==null){
            comment="";
        }
        String businessKey = pi.getBusinessKey();
        taskService.addComment(taskId, processInstanceId, transition, comment);
        Map<String, Object> variables = Maps.newHashMap();
        variables.put("transition", transition);
        taskService.complete(taskId, variables);
        if("重新提交".equals(transition)){
            switch (processDefinitionKey){
                case "order_approve":
                    Order order = orderService.selectById(businessKey);
                    order.setAuditStatus(2);
                    order.updateById();
                    break;
                case "order_loan_approve":
                    OrderLoan orderLoan = orderLoanService.selectById(businessKey);
                    orderLoan.setAuditStatus(2);
                    orderLoan.updateById();
                    break;
                case "pay":
                    Pay pay = payService.selectById(businessKey);
                    pay.setAuditStatus(2);
                    pay.updateById();
                    break;
                case "order_balance_approve":
                    Order orderBalance=orderService.selectById(businessKey);
                    orderBalance.setSettlementAuditStatus(2);
                    orderBalance.updateById();
                    break;
                case "brokerage_audit":
                    Order orderBrokerage=orderService.selectById(businessKey);
                    orderBrokerage.setBrokerageAuditStatus(2);
                    orderBrokerage.updateById();
                    break;
                default:
                    break;
            }
        }
        // 如果是结束节点，则更新审核状态
        pi = runtimeService.createProcessInstanceQuery()//
                .processInstanceId(processInstanceId)// 使用流程实例ID查询
                .singleResult();
        // 流程结束了
        if (pi == null) {
            if ("同意".equals(transition)) {
                switch (processDefinitionKey){
                    case "order_approve":
                        Order order = orderService.selectById(businessKey);
                        order.setAuditStatus(3);
                        order.setStatus(1);
                        order.updateById();
                        break;
                    case "order_loan_approve":
                        OrderLoan orderLoan = orderLoanService.selectById(businessKey);
                        orderLoan.setAuditStatus(3);
                        orderLoan.updateById();
                        Order orderByLoan = orderService.selectById(orderLoan.getOrder().getId());
                        BigDecimal companyLoanAmount = orderByLoan.getCompanyLoanAmount()==null? BigDecimal.valueOf(0.00):orderByLoan.getCompanyLoanAmount();
                        orderByLoan.setCompanyLoanAmount(companyLoanAmount.add(orderLoan.getAmount()==null?BigDecimal.valueOf(0.00):orderLoan.getAmount()));
                        BigDecimal companyLoanInterest = orderByLoan.getCompanyLoanInterest()==null?BigDecimal.valueOf(0.00):orderByLoan.getCompanyLoanInterest();
                        orderByLoan.setCompanyLoanInterest(companyLoanInterest.add(orderLoan.getInterest()==null?BigDecimal.valueOf(0.00):orderLoan.getInterest()));
                        orderByLoan.updateById();
                        break;
                    case "pay":
                    	Pay pay = payService.selectById(businessKey);
                    	pay.setAuditStatus(3);
                    	pay.updateById();
                        break;
                    case "order_balance_approve":
                    	Order orderBalance=orderService.selectById(businessKey);
                    	orderBalance.setSettlementAuditStatus(3);
                    	orderBalance.setBrokerageAuditStatus(1);
                    	orderBalance.setBrokerageGrantState(0);
                    	orderBalance.setStatus(4);
                    	Pay pay2=new Pay();
                    	pay2.setAuditStatus(3);
                    	EntityWrapper<Pay> ew=new EntityWrapper<Pay>();
                    	ew.eq("order_id", orderBalance.getId());
                    	payService.update(pay2, ew);
                    	//设置订单成本、应收金额，应付金额
                    	List<Pay> pays=payService.selectList(ew);
                    	EntityWrapper<OrderIncome> ew2=new EntityWrapper<OrderIncome>();
                    	ew.eq("order_id", orderBalance.getId());
                    	List<OrderIncome> incomes=orderIncomeService.selectList(ew2);
                    	BigDecimal costMoney=new BigDecimal(0);
                    	BigDecimal payMoney=new BigDecimal(0);
                    	BigDecimal incomeMoney =new BigDecimal(0);
                    	for (OrderIncome orderIncome : incomes) {
                    		if(orderIncome.getReceiveAmount()!=null){
                    			incomeMoney=incomeMoney.add(orderIncome.getReceiveAmount());
                    		}
						}
                    	for (Pay pay3 : pays) {
							if(pay3.getPayAmount()!=null){
								payMoney=payMoney.add(pay3.getPayAmount());
								if(pay3.getIsCost()==1){
									costMoney.add(pay3.getPayAmount());
								}
							}
						}
                    	orderBalance.setCost(costMoney);
                    	orderBalance.setReceiveAmount(incomeMoney);
                    	orderBalance.setPayAmount(payMoney);
                    	orderBalance.updateById();
                    	brokerageDistributeService.addBrokerageDistribute(orderBalance.getId());
                    	break;
                    case "brokerage_audit":
                    	Order orderBrokerage=orderService.selectById(businessKey);
                    	orderBrokerage.setBrokerageAuditStatus(3);
                    	orderBrokerage.updateById();
                    	break;
                    default:
                        break;
                }
            } else if ("放弃".equals(transition)) {
                switch (processDefinitionKey){
                    case "order_approve":
                        Order order = orderService.selectById(businessKey);
                        order.setAuditStatus(5);
                        order.updateById();
                        break;
                    case "order_loan_approve":
                        OrderLoan orderLoan = orderLoanService.selectById(businessKey);
                        orderLoan.setAuditStatus(5);
                        orderLoan.updateById();
                        break;
                    case "pay":
                    	Pay pay = payService.selectById(businessKey);
                    	pay.setAuditStatus(5);
                    	pay.updateById();
                        break;
                    case "order_balance_approve":
                    	 Order orderBalance = orderService.selectById(businessKey);
                    	 orderBalance.setSettlementAuditStatus(5);
                    	 orderBalance.updateById();
                    	 EntityWrapper<Pay> ew=new EntityWrapper<Pay>();
                    	 ew.eq("order_id", orderBalance.getId());
                    	 Pay payStatus=new Pay();
                    	 payStatus.setAuditStatus(3);
                    	 payService.update(payStatus, ew);
                         break;
                    case "brokerage_audit":
                    	Order orderBrokerage=orderService.selectById(businessKey);
                    	orderBrokerage.setBrokerageAuditStatus(5);
                    	orderBrokerage.updateById();
                    	break;
                    default:
                        break;
                }
            }
        }

        result.setIsSuccess(true);
        result.setMessage("操作成功");
        return result;
    }

    @Override
    public void toModify(DelegateTask delegateTask) {

    }

    @Override
    public List<Map<String, Object>> queryCommentsByProcessInstanceId(Page page,String processInstanceId) {
        List<Map<String,Object>> list = activitiMapper.queryCommentsByProcessInstanceId(page,processInstanceId);
        return list;
    }

    @Override
    public void submit(Model model, ProcessEnum processEnum) {
        submit(model,processEnum,"");
    }

    @Override
    public void submit(Model model, ProcessEnum processEnum, String comment) {
        String processEnumStr = processEnum.toString();
        String businessKey="";
        Order order = null;
        OrderLoan orderLoan=null;
        Pay pay=null;
        if(model instanceof Order){
            order = (Order) model;
            businessKey = order.getId().toString();
        }else if (model instanceof OrderLoan){
            orderLoan = (OrderLoan) model;
            businessKey = orderLoan.getId().toString();
        }else if(model instanceof Pay){
            pay = (Pay)model;
            businessKey = pay.getId().toString();
        } else {
            return;
        }
        ProcessInstance processInstance = null;
        try {
            User user = OperateUtils.getCurrentUser();
            Map<String,Object> map = new HashMap<>();
            map.put("userId",user.getId());
            // 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
            identityService.setAuthenticatedUserId(user.getId().toString());
            // 启动流程
            processInstance = runtimeService.startProcessInstanceByKey(processEnumStr, businessKey);
            // 更新摘要
            //runtimeService.setProcessInstanceName(processInstance.getId(),user.getName()+processEnum.getValue());
            // 更新业务表中的数据
            String processInstanceId = processInstance.getId();

            if(model instanceof Order){
            	if(processEnum.getValue().equals("订单结算")){
            		order.setProcessInstanceId(processInstanceId);
 	                order.setLastModifierId(user.getId());
 	                order.setLastModifyTime(DateUtil.getNowTimestampStr());
 	                order.setSettlementAuditStatus(2);
 	                order.updateById();
 	                map.put("orderBalanceId",order.getId());
                    runtimeService.setProcessInstanceName(processInstance.getId(),user.getName()+processEnum.getValue()+"   订单编号:"+order.getOrderCode());
            	}else if(processEnum.getValue().equals("订单送审")){
	                order.setProcessInstanceId(processInstanceId);
	                order.setLastModifierId(user.getId());
	                order.setLastModifyTime(DateUtil.getNowTimestampStr());
	                order.setAuditStatus(2);
	                order.updateById();
	                map.put("orderId",order.getId());
                    runtimeService.setProcessInstanceName(processInstance.getId(),user.getName()+processEnum.getValue()+"   订单编号:"+order.getOrderCode());
            	}else if(processEnum.getValue().equals("佣金结算")){
            		order.setProcessInstanceId(processInstanceId);
 	                order.setLastModifierId(user.getId());
 	                order.setLastModifyTime(DateUtil.getNowTimestampStr());
 	                order.setBrokerageAuditStatus(2);
 	                order.updateById();
 	                map.put("orderBrokerageId",order.getId());
                    runtimeService.setProcessInstanceName(processInstance.getId(),user.getName()+processEnum.getValue()+"   订单编号:"+order.getOrderCode());
            	}
            }else if(model instanceof OrderLoan){
                orderLoan.setProcessInstanceId(processInstanceId);
                orderLoan.setLastModifierId(user.getId());
                orderLoan.setLastModifyTime(DateUtil.getNowTimestampStr());
                orderLoan.setAuditStatus(2);
                orderLoan.updateById();
                map.put("orderId",orderLoan.getOrder().getId());
                map.put("orderLoanId",orderLoan.getId());
                runtimeService.setProcessInstanceName(processInstance.getId(),user.getName()+processEnum.getValue()+"   订单编号:"+queryOrderCodeById(orderLoan.getOrder().getId()));
            }else if(model instanceof Pay){
                pay.setProcessInstanceId(processInstanceId);
                pay.setLastModifierId(user.getId());
                pay.setLastModifyTime(DateUtil.getNowTimestampStr());
                pay.setAuditStatus(2);
                pay.updateById();
                map.put("payId",pay.getId());
                String orderCode = pay.getOrder()==null?"无订单号":queryOrderCodeById(pay.getOrder().getId());
                runtimeService.setProcessInstanceName(processInstance.getId(),user.getName()+processEnum.getValue()+"   订单编号:"+orderCode);
            }

            // 完成第一个任务，也就是提交任务
            Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
            taskService.setVariables(task.getId(),map);
            completeTask(task.getId(),  "提交", comment);
        } catch(Exception e){
            e.printStackTrace();
        }finally {
            identityService.setAuthenticatedUserId(null);
        }
    }

    @Override
    public void updateProcessInstanceName(String processInstanceId, String processInstanceName) {

    }

    @Override
    public Map<String, Object> getActivityInfo(String processInstanceId) {
        // 使用流程实例ID，查询正在执行的执行对象表，获取当前活动对应的流程实例对象
        ProcessInstance pi = runtimeService.createProcessInstanceQuery()// 创建流程实例查询
                .processInstanceId(processInstanceId)// 使用流程实例ID查询
                .singleResult();
        // 获取当前活动的ID
        String activityId = pi.getActivityId();
        if(StringUtils.isBlank(activityId)){//多实例

        }
        String processDefinitionId = pi.getProcessDefinitionId();
        // 获取流程定义的实体对象（对应.bpmn文件中的数据）
        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService
                .getProcessDefinition(processDefinitionId);
        // 获取当前活动对象
        ActivityImpl activityImpl = processDefinitionEntity
                .findActivity(activityId);// 活动ID

        Map<String, Object> activityInfo = Maps.newHashMap();
        // 获取坐标
        activityInfo.put("x", activityImpl.getX());
        activityInfo.put("y", activityImpl.getY());
        activityInfo.put("width", activityImpl.getWidth());
        activityInfo.put("height", activityImpl.getHeight());
        return activityInfo;
    }

    @Override
    public List<Map<String,Object>> queryDoneTask(Pagination pageable, String applyUserName) {
        User user = OperateUtils.getCurrentUser();
        return activitiMapper.queryDoneTask(pageable,user.getId(),applyUserName);
    }

    @Override
    public List<Map<String,Object>> queryTodoTask(Page page, String applyUserName) {
        User user = OperateUtils.getCurrentUser();
        return activitiMapper.queryTodoTask(page,user.getId(),applyUserName);
    }

    @Override
    public List<String> getButtonsForTransition(Task task) {
        return null;
    }

    @Override
    public Task getTaskId(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        return task;
    }

    @Override
    public List<Map<String, Object>> queryIndexTodoTask() {
        return null;
    }

    @Override
    public void completeBussinessTask(String processInstanceId) {

    }

    @Override
    public void toEnd(DelegateTask delegateTask) {
        String processInstanceId = delegateTask.getProcessInstanceId();
        ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
                .processInstanceId(processInstanceId)// 使用流程实例ID查询
                .singleResult();

        String processDefinitionKey = repositoryService.getProcessDefinition(pi.getProcessDefinitionId()).getKey();
        String businessKey = pi.getBusinessKey();

        Object baseDao = SpringUtils.getBean(processDefinitionKey+ "ServiceImpl");
        Class ref = baseDao.getClass();
        try {
            Method method = ref.getDeclaredMethod("updateStatus");
            method.invoke(baseDao);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Map<String, Object>> queryHiCommentsByProcessInstanceId(String processKey, String businessKey, String processInstanceId) {
        return null;
    }

    @Override
    public void toFinalPass(DelegateTask delegateTask) {

    }

    @Override
    public void deleteProcess(String processInstanceId) throws Exception {

    }


    private String queryOrderCodeById(Long id){
        return orderService.selectById(id).getOrderCode();
    }
}

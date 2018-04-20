package com.xuwei.controller;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xuwei.activiti.service.IActivitiProcessService;
import com.xuwei.activiti.utils.ProcessEnum;
import com.xuwei.bean.Order;
import com.xuwei.bean.OrderSchedule;
import com.xuwei.service.IOrderScheduleService;
import com.xuwei.service.IOrderService;
import com.xuwei.util.DateUtil;
import com.xuwei.util.JSONUtil;
import com.xuwei.util.OperateUtils;
import com.xuwei.util.ServiceResult;
import com.xuwei.util.StringUtil;


/**
 * @description: 订单控制器
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月05日 09:22:53
 * @author: hhd
 * @version: 1.0
 */
@Controller
@RequestMapping("/myWorkbench/order")
public class OrderController extends BaseController<Order> {
    @Resource
    private IOrderService orderService;
    @Resource
    private IActivitiProcessService activitiProcessService;
    @Resource
    private RuntimeService runtimeService;
    @Resource
    private IOrderScheduleService orderScheduleService;

    public OrderController(){
        setResourceIdentity("myWorkbench:order");
    }

    /**
    * @description: 转向模块主界面
    * @createTime: 2017年09月05日 09:22:53
    * @author: hhd
    * @param model
    * @return
    */
    @RequestMapping(value = "main",method = RequestMethod.GET)
    public String main(Model model){
        return defaultViewPrefix();
    }

    /**
    * @description: 添加
    * @createTime: 2017年09月05日 09:22:53
    * @author: hhd
    * @param model
    * @param m
    * @return
    */
    @RequestMapping(value = "create",method = RequestMethod.POST)
    @ResponseBody
    public Object create(Model model,Order m){
        ServiceResult result = new ServiceResult(false);
        if(!hasCreatePermission()){
            result.setMessage("没有添加权限");
        }else{
            result = orderService.createOrder(m);
        }
        String jsonString = result.toJSON();
        return jsonString;
    }

    /**
    * @description: 修改
    * @createTime: 2017年09月05日 09:22:53
    * @author: hhd
    * @param model
    * @param m
    * @return
    */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public Object update(Model model,Order m){
        ServiceResult result = new ServiceResult(false);
        if(!hasUpdatePermission()){
            result.setMessage("没有修改权限");
        }else{
            Order order = orderService.selectById(m.getId());
            order.setProduct(m.getProduct());
            order.setEstimateFinishTime(m.getEstimateFinishTime());
            order.setOwner(m.getOwner());
            order.setCsAssistant(m.getCsAssistant());
            order.setCsPrincipal(m.getCsPrincipal());
            order.setFollowUser(m.getFollowUser());
            order.setLastModifierId(OperateUtils.getCurrentUserId());
            order.setLastModifyTime(DateUtil.getNowTimestampStr());
            boolean isSuccess = orderService.updateAllColumnById(order);
            result.setIsSuccess(isSuccess);
        }
        String jsonString = result.toJSON();
        return jsonString;
    }

    /**
     * @description: 更新贷款方案
     * @createTime: 2017年09月14日 15:22:53
     * @author: hhd
     * @param model
     * @param m
     * @return
     */
    @RequestMapping(value = "updateLoan",method = RequestMethod.POST)
    @ResponseBody
    public Object updateLoan(Model model,Order m){
        ServiceResult result = new ServiceResult(false);
        if(!hasUpdatePermission()){
            result.setMessage("没有修改权限");
        }else{
            Order order = orderService.selectById(m.getId());
            order.setBank(m.getBank());
            order.setBranch(m.getBranch());
            order.setLoanAmount(m.getLoanAmount());
            order.setLendingRate(m.getLendingRate());
            order.setLoanTerm(m.getLoanTerm());
            order.setRepayWay(m.getRepayWay());
            order.setServiceChargePercent(m.getServiceChargePercent());
            order.setServiceCharge(m.getServiceCharge());
            order.setMatchmaker(m.getMatchmaker());
            order.setCommissionAmount(m.getCommissionAmount());
            order.setCommissionReason(m.getCommissionReason());
            order.setCommissionRate(m.getCommissionRate());
            order.setLastModifierId(OperateUtils.getCurrentUserId());
            order.setLastModifyTime(DateUtil.getNowTimestampStr());
            boolean isSuccess = orderService.updateAllColumnById(order);
            result.setIsSuccess(isSuccess);
        }
        String jsonString = result.toJSON();
        return jsonString;
    }

    /**
     * @description: 更新借款方案
     * @createTime: 2017年09月14日 15:22:53
     * @author: hhd
     * @param model
     * @param m
     * @return
     */
    @RequestMapping(value = "updateBorrow",method = RequestMethod.POST)
    @ResponseBody
    public Object updateBorrow(Model model,Order m){
        ServiceResult result = new ServiceResult(false);
        if(!hasUpdatePermission()){
            result.setMessage("没有修改权限");
        }else{
            Order order = orderService.selectById(m.getId());
            order.setLoanMoney(m.getLoanMoney());
            order.setLoanDate(m.getLoanDate());
            order.setServiceCharge(m.getServiceCharge());
            order.setServiceChargePercent(m.getServiceChargePercent());
            order.setStatus(3);
            order.setLastModifierId(OperateUtils.getCurrentUserId());
            order.setLastModifyTime(DateUtil.getNowTimestampStr());
            boolean isSuccess = orderService.updateAllColumnById(order);
            result.setIsSuccess(isSuccess);
        }
        String jsonString = result.toJSON();
        return jsonString;
    }

    /**
    * @description: 删除
    * @createTime: 2017年09月05日 09:22:53
    * @author: hhd
    * @param ids
    * @return
    */
    @RequestMapping(value = "mulDelete",method = RequestMethod.POST)
    @ResponseBody
    public Object mulDelete(String ids){
        ServiceResult result = new ServiceResult(false);
        if(!hasUpdatePermission()){
            result.setMessage("没有删除权限");
        }else{
            try {
                String[] idArray = StringUtil.split(ids);
                if(idArray==null||idArray.length==0){
                    result.setMessage("请选择要删除的数据行");
                    return result;
                }
                boolean isSuccess = orderService.deleteBatchIds(Arrays.asList(idArray));
                result.setIsSuccess(isSuccess);
            } catch (Exception e) {
                if(e instanceof org.springframework.dao.DataIntegrityViolationException){
                    result.setMessage("数据已被引用不能删除");
                }else{
                    result.setMessage("删除出错："+e.getMessage());
                }
            }
        }
        String jsonString = result.toJSON();
        return jsonString;
    }

    /**
    * @description: 分页查询
    * @createTime: 2017年09月05日 09:22:53
    * @author: hhd
    * @param m
    * @param rows
    * @param page
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "listByPage")
    public Object listByPage(Order m, int rows, int page){
        int type = 0;
        if(!hasPermissionByOpt("viewall")){
            if(hasPermissionByOpt("viewdepartment")){
                type = 1;
            }else if(hasViewPermission()){
                type = 2;
            }else{
                return  JSONUtil.EMPTYJSON;
            }
        }

        Page<Order> pageM= new Page<>(page,rows);
        EntityWrapper<Order> ew = new EntityWrapper<>(m);
        ew.isWhere(false).and("a.creator_id={0}", OperateUtils.getCurrentUserId())
        .or("a.owner_id={0}", OperateUtils.getCurrentUserId())
        .or("a.cs_assistant_id={0}", OperateUtils.getCurrentUserId())
        .or("a.cs_principal_id={0}", OperateUtils.getCurrentUserId())
        .or("a.follow_user_id={0}", OperateUtils.getCurrentUserId());
        pageM = orderService.selectPage(pageM,ew,type);
        String[] properties = {"orderCode","customer.customerName:customerName","product.name:productName","propertyId",
                "bank.bankName:bankName","branch.subbranchName:subbranchName","signingDate",
                "estimateFinishTime","loanAmount","lendingRate","loanTerm","repayWay.name:repayWayName","serviceChargePercent",
                "serviceCharge","commissionAmount","commissionReason","csAssistant.name:csAssistantName","csPrincipal.name:csPrincipalName",
                "followUser.name:followUserName", "owner.name:ownerName","auditStatus","status","cost","receiveAmount","payAmount","companyLoanAmount",
                "companyLoanInterest","receivablesAccountStatus","loanDate","loanMoney","settlementCharge",
                "settlementClerk","createName","id"};
        String jsonString = JSONUtil.toJson(pageM.getRecords(), properties,(long) pageM.getTotal());
        return jsonString;
    }

    /**
     * @description: 根据id查询
     * @createTime: 2017年09月05日 09:22:53
     * @author: hhd
     * @param orderId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "selectOneById")
    public Object selectOneById(Long orderId){
        if(orderId==null){
            return JSONUtil.EMPTY_COMBOBOX_JSON;
        }
        Order order = orderService.selectById(orderId);
        return order;
    }


    /**
     * @description: 审核
     * @createTime: 2017年09月05日 09:22:53
     * @author: hhd
     * @param ids
     * @return
     */
    @RequestMapping(value = "mulApprove",method = RequestMethod.POST)
    @ResponseBody
    public Object mulApprove(String ids){
        ServiceResult result = new ServiceResult(false);
        if(!hasPermissionByOpt("approve")){
            result.setMessage("没有送审权限");
        }else{
            try {
                String[] idArray = StringUtil.split(ids);
                if(idArray==null||idArray.length==0){
                    result.setMessage("请选择要送审的数据行");
                    return result;
                }
                EntityWrapper<Order> ew = new EntityWrapper<Order>();
                ew.in("id",idArray);
                List<Order> list = orderService.selectList(ew);
                for (Order item :list){
                    activitiProcessService.submit(item, ProcessEnum.order_approve);
                }
                result.setIsSuccess(true);
            } catch (Exception e) {
                result.setMessage("送审出错："+e.getMessage());
            }
        }
        String jsonString = result.toJSON();
        return jsonString;
    }

    /**
     * @description: 跟踪
     * @createTime: 2017年09月05日 09:22:53
     * @author: hhd
     * @param id
     * @return
     */
    @RequestMapping(value = "trace",method = RequestMethod.GET)
    public String traceApprove(Model model,Integer id){
        if(!hasPermissionByOpt("trace")){
            return "404-1";
        }else{
            try {
                if(id==null){
                    return "404-2";
                }
                Order order = orderService.selectById(id);
                if(StringUtils.isEmpty(order.getProcessInstanceId())){
                    return "404-3";
                }
                ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                        .processInstanceId(order.getProcessInstanceId()).singleResult();
                model.addAttribute("processInstanceId",order.getProcessInstanceId());
                model.addAttribute("processDefinitionId",processInstance.getProcessDefinitionId());
            } catch (Exception e) {
                return "404-4";
            }
        }
        return "/myWorkbench/tracePicture";
    }


    /**
     * @description: 审批界面
     * @createTime: 2017年09月05日 09:22:53
     * @author: hhd
     * @param id
     * @param taskId
     * @return
     */
    @RequestMapping(value = "approveView",method = RequestMethod.GET)
    public String approveView(Model model,String id,String taskId,String formKey){
        Order order=orderService.selectById(id);
        model.addAttribute("orderId",id);
        model.addAttribute("taskId",taskId);
        model.addAttribute("processInstanceId",order.getProcessInstanceId());
        if("manager".equals(formKey) ||("undefined".equals(formKey) && "undefined".equals(taskId))){
            return "/approval_template/order_approval_view";
        }else if ("business".equals(formKey)){
            return "/approval_template/order_approval_edit";
        }else {
            return "/approval_template/404";
        }
    }

    /**
     * @description: 完结
     * @createTime: 2017年09月05日 09:22:53
     * @author: hhd
     * @param ids
     * @return
     */
    @RequestMapping(value = "signEnd",method = RequestMethod.POST)
    @ResponseBody
    public Object signEnd(String ids){
        ServiceResult result = new ServiceResult(false);
        if(!hasPermissionByOpt("end")){
            result.setMessage("没有完结权限");
        }else{
            try {
                String[] idArray = StringUtil.split(ids);
                if(idArray==null||idArray.length==0){
                    result.setMessage("请选择要送审的数据行");
                    return result;
                }
                EntityWrapper<Order> ew = new EntityWrapper<Order>();
                ew.in("id",idArray);
                List<Order> list = orderService.selectList(ew);
                for (Order item :list){
                    item.setStatus(5);
                }
                orderService.updateBatchById(list);
                result.setIsSuccess(true);
            } catch (Exception e) {
                result.setMessage("送审出错："+e.getMessage());
            }
        }
        String jsonString = result.toJSON();
        return jsonString;
    }

    /**
     * @description: 查看进度
     * @createTime: 2017年09月05日 09:22:53
     * @author: hhd
     * @param id
     * @return
     */
    @RequestMapping(value = "viewProgress",method = RequestMethod.POST)
    @ResponseBody
    public Object viewProgress(String id){
        ServiceResult result = new ServiceResult(false);
        if(!hasPermissionByOpt("progress")){
            result.setMessage("没有查看进度的权限");
        }else{
            Order order = orderService.selectById(id);
            result.addData("orderData",order);
            List<OrderSchedule> list = orderScheduleService.getOrderSheduleByOrderId(order.getId());
            result.addData("sheduleData",list);
        }
        result.setIsSuccess(true);
        String jsonString = result.toJSON();
        return jsonString;
    }

}

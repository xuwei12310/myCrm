package com.xuwei.controller;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xuwei.activiti.service.IActivitiProcessService;
import com.xuwei.bean.OrderLoan;
import com.xuwei.service.IOrderLoanService;
import com.xuwei.util.DateUtil;
import com.xuwei.util.JSONUtil;
import com.xuwei.util.OperateUtils;
import com.xuwei.util.ServiceResult;
import com.xuwei.util.StringUtil;


/**
 * @description: 订单_借款控制器
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月19日 09:29:11
 * @author: hhd
 * @version: 1.0
 */
@Controller
@RequestMapping("/myWorkbench/orderLoan")
public class OrderLoanController extends BaseController<OrderLoan> {
    @Resource
    private IOrderLoanService orderLoanService;
    @Resource
    private IActivitiProcessService activitiProcessService;
    @Resource
    private RuntimeService runtimeService;
    /**
    * @description: 添加
    * @createTime: 2017年09月19日 09:29:11
    * @author: hhd
    * @param m
    * @return
    */
    @RequestMapping(value = "create",method = RequestMethod.POST)
    @ResponseBody
    public Object create(OrderLoan m){
        ServiceResult result = new ServiceResult(false);
        m.setAuditStatus(1);
        m.setCreatorId(OperateUtils.getCurrentUserId());
        m.setCreateTime(DateUtil.getNowTimestampStr());
        m.setLastModifierId(OperateUtils.getCurrentUserId());
        m.setLastModifyTime(DateUtil.getNowTimestampStr());
        boolean isSuccess = orderLoanService.insert(m);
        result.setIsSuccess(isSuccess);
        String jsonString = result.toJSON();
        return jsonString;
    }

    /**
    * @description: 修改
    * @createTime: 2017年09月19日 09:29:11
    * @author: hhd
    * @param m
    * @return
    */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public Object update(OrderLoan m){
        ServiceResult result = new ServiceResult(false);
        OrderLoan orderLoan = orderLoanService.selectById(m.getId());
        orderLoan.setAmount(m.getAmount());
        orderLoan.setRate(m.getRate());
        orderLoan.setRateType(m.getRateType());
        orderLoan.setBeginDate(m.getBeginDate());
        orderLoan.setEstimateRepayDate(m.getEstimateRepayDate());
        orderLoan.setEstimateInterest(m.getEstimateInterest());
        orderLoan.setEstimateLoanDay(m.getEstimateLoanDay());
        orderLoan.setLastModifierId(OperateUtils.getCurrentUserId());
        orderLoan.setLastModifyTime(DateUtil.getNowTimestampStr());
        boolean isSuccess = orderLoanService.updateAllColumnById(orderLoan);
        result.setIsSuccess(isSuccess);
        String jsonString = result.toJSON();
        return jsonString;
    }

    /**
    * @description: 删除
    * @createTime: 2017年09月19日 09:29:11
    * @author: hhd
    * @param ids
    * @return
    */
    @RequestMapping(value = "mulDelete",method = RequestMethod.POST)
    @ResponseBody
    public Object mulDelete(String ids){
        ServiceResult result = new ServiceResult(false);
        try {
            String[] idArray = StringUtil.split(ids);
            if(idArray==null||idArray.length==0){
                result.setMessage("请选择要删除的数据行");
                return result;
            }
            boolean isSuccess = orderLoanService.deleteBatchIds(Arrays.asList(idArray));
            result.setIsSuccess(isSuccess);
        } catch (Exception e) {
            if(e instanceof org.springframework.dao.DataIntegrityViolationException){
                result.setMessage("数据已被引用不能删除");
            }else{
                result.setMessage("删除出错："+e.getMessage());
            }
        }
        String jsonString = result.toJSON();
        return jsonString;
    }

    /**
    * @description: 分页查询
    * @createTime: 2017年09月19日 09:29:11
    * @author: hhd
    * @param m
    * @param rows
    * @param page
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "listByPage")
    public Object listByPage(OrderLoan m, int rows, int page){
        if(m.getOrder()==null||m.getOrder().getId()==null){
            return  JSONUtil.EMPTYJSON;
        }
        Page<OrderLoan> pageM= new Page<>(page,rows);
        EntityWrapper<OrderLoan> ew = new EntityWrapper<>(m);
        pageM = orderLoanService.selectPage(pageM,ew);
        String[] properties = {"amount","rate","rateType","beginDate","estimateRepayDate","estimateLoanDay","estimateInterest","referInterest","repayDate","loanDay","interest","receiveAmount","receivedAmount","receivingAmount","auditStatus","processInstanceId","id"};
        String jsonString = JSONUtil.toJson(pageM.getRecords(), properties,(long) pageM.getTotal());
        return jsonString;
    }

    /**
     * @description: 根据id查询
     * @createTime: 2017年09月05日 09:22:53
     * @author: hhd
     * @param orderLoanId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "selectOneById")
    public Object selectOneById(String orderLoanId){
        if(orderLoanId==null){
            return JSONUtil.EMPTY_COMBOBOX_JSON;
        }
        OrderLoan orderLoan = orderLoanService.selectById(orderLoanId);
        return orderLoan;
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
        try {
            String[] idArray = StringUtil.split(ids);
            if(idArray==null||idArray.length==0){
                result.setMessage("请选择要送审的数据行");
                return result;
            }
            EntityWrapper<OrderLoan> ew = new EntityWrapper<>();
            ew.in("id",idArray);
            List<OrderLoan> list = orderLoanService.selectList(ew);
            for (OrderLoan item :list){
               /* activitiProcessService.submit(item, ProcessEnum.order_loan_approve);*/
            }
            result.setIsSuccess(true);
        } catch (Exception e) {
            result.setMessage("送审出错："+e.getMessage());
        }
        String jsonString = result.toJSON();
        return jsonString;
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
    public String approveView(Model model,String id, String taskId, String formKey){
        OrderLoan orderLoan=orderLoanService.selectById(id);
        model.addAttribute("orderId",orderLoan.getOrder().getId());
        model.addAttribute("taskId",taskId);
        model.addAttribute("orderLoanId",id);
        model.addAttribute("processInstanceId",orderLoan.getProcessInstanceId());
        if(formKey.equals("manager")||formKey.equals("companyManager")||(formKey.equals("undefined")&&taskId.equals("undefined"))){
            return "/approval_template/orderLoan_approval_view";
        }else if (formKey.equals("business")){
            return "/approval_template/orderLoan_approval_edit";
        }else {
            return "/approval_template/404";
        }
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
        try {
            if(id==null){
                return "404-2";
            }
            OrderLoan orderLoan = orderLoanService.selectById(id);
            if(StringUtils.isEmpty(orderLoan.getProcessInstanceId())){
                return "404-3";
            }
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(orderLoan.getProcessInstanceId()).singleResult();
            model.addAttribute("processInstanceId",orderLoan.getProcessInstanceId());
            model.addAttribute("processDefinitionId",processInstance.getProcessDefinitionId());
        } catch (Exception e) {
            return "404-4";
        }
        return "/myWorkbench/tracePicture";
    }

}

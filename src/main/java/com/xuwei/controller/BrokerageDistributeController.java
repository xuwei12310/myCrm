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
import com.xuwei.bean.BrokerageDistribute;
import com.xuwei.bean.Order;
import com.xuwei.service.IBrokerageDistributeService;
import com.xuwei.service.IBrokerageRuleService;
import com.xuwei.service.IOrderService;
import com.xuwei.util.DateUtil;
import com.xuwei.util.JSONUtil;
import com.xuwei.util.OperateUtils;
import com.xuwei.util.ServiceResult;
import com.xuwei.util.StringUtil;


/**
 * @description: 佣金分配控制器
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月25日 19:57:40
 * @author: caw
 * @version: 1.0
 */
@Controller
@RequestMapping("/financial/brokerageDistribute")
public class BrokerageDistributeController extends BaseController<BrokerageDistribute> {
    @Resource
    private IBrokerageDistributeService brokerageDistributeService;
    @Resource
    private IOrderService orderService;
    @Resource
    private IBrokerageRuleService brokerageRuleService;
    @Resource
    private IActivitiProcessService activitiProcessService;
    @Resource
    private RuntimeService runtimeService;

    public BrokerageDistributeController(){
        setResourceIdentity("financial:brokerageDistribute");
    }

    /**
    * @description: 转向模块主界面
    * @createTime: 2017年09月25日 19:57:40
    * @author: caw
    * @param model
    * @return
    */
    @RequestMapping(value = "main",method = RequestMethod.GET)
    public String main(Model model){
        return defaultViewPrefix();
    }

    /**
    * @description: 添加
    * @createTime: 2017年09月25日 19:57:40
    * @author: caw
    * @param m
    * @return
    */
    @RequestMapping(value = "create",method = RequestMethod.POST)
    @ResponseBody
    public Object create(BrokerageDistribute m){
        ServiceResult result = new ServiceResult(false);
        if(!hasCreatePermission()){
            result.setMessage("没有添加权限");
        }else{
            boolean isSuccess = brokerageDistributeService.insert(m);
            result.setIsSuccess(isSuccess);
        }
        String jsonString = result.toJSON();
        return jsonString;
    }

    /**
    * @description: 修改
    * @createTime: 2017年09月25日 19:57:40
    * @author: caw
    * @param m
    * @return
    */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public Object update(BrokerageDistribute m, Long bid){
        ServiceResult result = new ServiceResult(false);
        if(!hasUpdatePermission()){
            result.setMessage("没有修改权限");
        }else{
        	BrokerageDistribute brokerageDistribute = brokerageDistributeService.selectById(bid);
        	brokerageDistribute.setActual(m.getActual());
        	brokerageDistribute.setReference(m.getReference());
        	brokerageDistribute.setNote(m.getNote());
        	brokerageDistribute.setLastModifierId(OperateUtils.getCurrentUserId());
        	brokerageDistribute.setLastModifyTime(DateUtil.getNowTimestampStr());
            boolean isSuccess = brokerageDistributeService.updateAllColumnById(brokerageDistribute);
            brokerageDistributeService.modBrokerageTotal(brokerageDistribute.getOrder().getId());
            result.setIsSuccess(isSuccess);
        }
        String jsonString = result.toJSON();
        return jsonString;
    }

    /**
    * @description: 删除
    * @createTime: 2017年09月25日 19:57:40
    * @author: caw
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
                boolean isSuccess = brokerageDistributeService.deleteBatchIds(Arrays.asList(idArray));
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
    * @createTime: 2017年09月25日 19:57:40
    * @author: caw
    * @param m
    * @param rows
    * @param page
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "listByPage")
    public Object listByPage(Order m, int rows, int page){
    	int viewType = 0;
        if(!hasPermission("myWorkbench:order:viewall")){
            if(hasPermission("myWorkbench:order:viewdepartment")){
            	viewType = 1;
            }else if(hasPermission("myWorkbench:order:view")){
            	viewType = 2;
            }else{
                return  JSONUtil.EMPTYJSON;
            }
        }
        Page<Order> pageM= new Page<>(page,rows);
        List<Order> list= orderService.getOrderBrokerageList(pageM, m, viewType, OperateUtils.getCurrentUserId());
        String[] properties = {"id:oid","orderCode","customer.id","customer.customerName:customerName","signingDate","loanAmount",
        		"lendingRate","commissionAmount","commissionRate","csAssistant.id","csAssistant.name","csPrincipal.id","csPrincipal.name",
        		"followUser.id","followUser.name","owner.id","owner.name:ownerName","cost","receiveAmount","payAmount","companyLoanAmount",
        		"companyLoanInterest","loanDate","loanMoney","settlementCharge","settlementClerk","brokerageAuditStatus","brokerageGrantState","brokerageTotal","note"};
        String jsonString = JSONUtil.toJson(list, properties,(long) pageM.getTotal());
        return jsonString;
    }
    
    /**
     * 
     * @description: 根据订单id获取佣金分配信息
     * @createTime: 2017年9月26日 上午9:07:38
     * @author: caw
     * @param m
     * @param rows
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "listBrokerageByPage")
    public Object listBrokerageByPage(BrokerageDistribute m, Long orderId){
    	if(!hasViewPermission()){
            return  JSONUtil.EMPTYJSON;
        }
    	m.setOrder(orderService.selectById(orderId));
    	List<BrokerageDistribute> list = brokerageDistributeService.listBrokerageByPage(m);
    	String[] properties = {"id:bid","owner.id","owner.name:ownerName","order.id","order.orderCode:orderCode","ownerType","reference","actual","note"};
        String jsonString = JSONUtil.toJson(list, properties);
        return jsonString;
    }
    
    /**
     * 
     * @description: 根据订单id获取佣金分配信息（审批）
     * @createTime: 2017年9月27日 上午8:23:41
     * @author: caw
     * @param m
     * @param orderId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "listApprovalBrokerageByPage")
    public Object listApprovalBrokerageByPage(BrokerageDistribute m, Long orderId){
    	m.setOrder(orderService.selectById(orderId));
    	List<BrokerageDistribute> list = brokerageDistributeService.listBrokerageByPage(m);
    	String[] properties = {"id:bid","owner.id","owner.name:ownerName","order.id","order.orderCode:orderCode","ownerType","reference","actual","note"};
        String jsonString = JSONUtil.toJson(list, properties);
        return jsonString;
    }
    
    /**
     * 
     * @description: 修改发放状态
     * @createTime: 2017年9月26日 下午2:29:57
     * @author: caw
     * @param model
     * @param ids
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "modGrantStatus")
    public Object modGrantStatus(Model model,String ids,Integer param){
    	ServiceResult result = new ServiceResult(false);
    	if(!hasUpdatePermission()){
            result.setMessage("没有修改权限");
        }else{
        	String[] idArray = StringUtil.split(ids);
        	if(idArray==null||idArray.length==0){
                result.setMessage("请选择要修改状态的数据行");
                return result;
            }else{
            	result = brokerageDistributeService.modGrantStatus(param, idArray);
            }
        }
    	String jsonString = result.toJSON();
        return jsonString;
    }
    
    /**
     * 
     * @description: 送审
     * @createTime: 2017年9月26日 下午8:42:44
     * @author: caw
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "verify")
    public Object verify(String ids){
    	ServiceResult result=new ServiceResult();
    	if(!hasPermissionByOpt("verify")){
            result.setMessage("没有送审权限");
        }else{
        	try {
        		String[] idArray = StringUtil.split(ids);
                if(idArray==null||idArray.length==0){
                    result.setMessage("请选择要送审的数据行");
                    return result;
                }
                EntityWrapper<Order> ew=new EntityWrapper<Order>();
                ew.in("id", idArray);
                //List<Order> list = orderService.selectList(ew);
                int count=0;
               /* for (Order order : list) {
                	if(order.getBrokerageAuditStatus() == 1){
                		activitiProcessService.submit(order, ProcessEnum.brokerage_audit);
                		count++;
                	}
                }*/
                if(count>0){
               	 	result.setIsSuccess(true);
                }else{
               	 	result.setIsSuccess(false);
               	 	result.setMessage("送审失败，目前状态无法送审");
               		return result.toJSON();
                }
                result.setIsSuccess(true);
        	} catch (Exception e) {
                result.setMessage("送审失败："+e.getMessage());
        	}
        }
    	return result.toJSON();
    }
    
    /**
     * 
     * @description: 审核界面
     * @createTime: 2017年9月26日 下午4:27:38
     * @author: caw
     * @param model
     * @param id
     * @param taskId
     * @param formKey
     * @return
     */
    @RequestMapping(value = "approveView",method = RequestMethod.GET)
    public String approveView(Model model,String id,String taskId,String formKey){
    	Order order= orderService.selectById(id);
        model.addAttribute("id",id);
        model.addAttribute("taskId",taskId);
        model.addAttribute("processInstanceId",order.getProcessInstanceId());
        if(formKey.equals("manager") || formKey.equals("companyManager")||(taskId.equals("undefined")&&formKey.equals("undefined"))){
            return "/approval_template/brokerage_audit_view";
        }else if (formKey.equals("business")){
            return "/approval_template/brokerage_audit_edit";
        }else {
            return "/approval_template/404";
        }
    }
    
    /**
     * 
     * @description: 
     * @createTime: 2017年9月26日 下午7:00:41
     * @author: caw
     * @param orderId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getOrderBrokerageById")
    public Object getOrderBrokerageById(Long orderId){
    	Order order = brokerageDistributeService.getOrderBrokerageById(orderId);
    	String[] properties = {"id:oid","orderCode","customer.id","customer.customerName:customerName","signingDate","loanAmount",
        		"lendingRate","commissionAmount","commissionRate","csAssistant.id","csAssistant.name","csPrincipal.id","csPrincipal.name",
        		"followUser.id","followUser.name","owner.id","owner.name:ownerName","cost","receiveAmount","payAmount","companyLoanAmount",
        		"companyLoanInterest","loanDate","loanMoney","settlementCharge","settlementClerk","brokerageAuditStatus","brokerageGrantState","brokerageTotal","note"};
    	String jsonString = JSONUtil.toJson(order, properties);
		return jsonString;
    }
    
    /**
     * 
     * @description: 跟踪
     * @createTime: 2017年9月26日 下午8:38:11
     * @author: caw
     * @param model
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
}

package com.xuwei.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.xuwei.activiti.utils.ProcessEnum;
import com.xuwei.bean.AssessmentCompany;
import com.xuwei.bean.BankSubbranch;
import com.xuwei.bean.Customer;
import com.xuwei.bean.Order;
import com.xuwei.bean.OrderIncome;
import com.xuwei.bean.OrderLoan;
import com.xuwei.bean.Organization;
import com.xuwei.bean.OtherPartners;
import com.xuwei.bean.Pay;
import com.xuwei.service.IOrderIncomeService;
import com.xuwei.service.IOrderLoanService;
import com.xuwei.service.IOrderService;
import com.xuwei.service.IPayService;
import com.xuwei.service.OrganizationService;
import com.xuwei.util.DateUtil;
import com.xuwei.util.JSONUtil;
import com.xuwei.util.OperateUtils;
import com.xuwei.util.ServiceResult;
import com.xuwei.util.StringUtil;


/**
 * @description: 订单结算控制器
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月12日 16:48:34
 * @author: xw
 * @version: 1.0
 */
@Controller
@RequestMapping("/financial/balance")
public class BalanceController extends BaseController<Pay> {
    @Resource
    private IPayService payService;
    @Resource
    private IOrderService orderService;
    @Resource
    private IOrderLoanService orderLoanService;
    @Resource
    private IOrderIncomeService orderIncomeService;
    @Resource
    private IActivitiProcessService activitiProcessService;
    @Resource
    private OrganizationService organizationService;
    @Resource
    private RuntimeService runtimeService;
    public BalanceController(){
        setResourceIdentity("financial:balance");
    }
   
    /**
    * @description: 转向模块主界面
    * @createTime: 2017年09月12日 16:48:34
    * @author: xw
    * @param model
    * @return
    */
    @RequestMapping(value = "main",method = RequestMethod.GET)
    public String main(Model model){
        return defaultViewPrefix();
    }

    /**
    * @description: 添加
    * @createTime: 2017年09月12日 16:48:34
    * @author: xw
    * @param m
    * @return
    */
    @RequestMapping(value = "create",method = RequestMethod.POST)
    @ResponseBody
    public Object create(Order m){
        ServiceResult result = new ServiceResult(false);
        if(!hasCreatePermission()){
            result.setMessage("没有添加权限");
        }else{
        	try{
            result = orderService.saveOrderBalance(m);
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("id", m.getId());
            result.setData(map);
        	}catch(Exception e){
        		e.printStackTrace();
        		result.setMessage("保存出错");
        		String jsonString = result.toJSON();
        	    return jsonString;
        	}
        }
        String jsonString = result.toJSON();
        return jsonString;
    }
    /**
     * 
     * @description:通过id获取订单
     * @createTime 2017年10月11日 上午10:58:42
     * @author xw
     * @param id
     * @return
     */
    @RequestMapping("getOrder")
    @ResponseBody
    public Object getOrder(Long id){
    	Order order = orderService.selectById(id);
    	 String[] properties = {"orderCode","customer.customerName:customerName","product.name:productName","propertyId",
                 "bank.bankName:bankName","branch.subbranchName:subbranchName","signingDate",
                 "estimateFinishTime","loanAmount","lendingRate","loanTerm","repayWay.name:repayWayName","serviceChargePercent",
                 "serviceCharge","commissionAmount","commissionReason","csAssistant.name:csAssistantName","csPrincipal.name:csPrincipalName",
                 "followUser.name:followUserName", "owner.name:ownerName","auditStatus","status","cost","receiveAmount","payAmount","companyLoanAmount",
                 "companyLoanInterest","receivablesAccountStatus","loanDate","loanMoney","settlementCharge",
                 "settlementClerk","createName","id","commissionRate","settlementAuditStatus"};
    	String json = JSONUtil.toJson(order, properties);
    	return json;
    }
    /**
    * @description: 修改
    * @createTime: 2017年09月12日 16:48:34
    * @author: xw
    * @param m
    * @return
    */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public Object update(Pay m,Long objectId){
        ServiceResult result = new ServiceResult(false);
        if(!hasUpdatePermission()){
            result.setMessage("没有修改权限");
        }else{
        	Pay oldM=payService.selectById(m.getId());
        	switch(m.getPayObjectType()){
        	case 1:
        		AssessmentCompany a=new AssessmentCompany();
        		a.setId(objectId);
        		oldM.setAssessmentCompany(a); 
        		break;
        	case 2:
        		BankSubbranch b=new BankSubbranch();
        		b.setId(objectId);
        		oldM.setBankSubbranch(b);
                break;
        	case 3:
        		OtherPartners o=new OtherPartners();
        		o.setId(objectId);
        		oldM.setOtherPartners(o);
        		break;
        	case 4:
        		Customer c=new Customer();
        		c.setId(objectId);
        		oldM.setCustomer(c);
        		break;
        	}
        	oldM.setCostType(m.getCostType());
        	oldM.setIsCost(m.getIsCost());
        	oldM.setPayObjectType(m.getPayObjectType());
        	oldM.setOrder(m.getOrder());
        	oldM.setPayAmount(m.getPayAmount());
        	oldM.setReceiveAccountName(m.getReceiveAccountName());
        	oldM.setReceiveAccountBank(m.getReceiveAccountBank());
        	oldM.setReceiveAccountNumber(m.getReceiveAccountNumber());
        	oldM.setPayBankAccount(m.getPayBankAccount());
        	oldM.setPayTime(m.getPayTime());
        	oldM.setNote(m.getNote());
        	oldM.setLastModifierId(OperateUtils.getCurrentUserId());
        	oldM.setLastModifyTime(DateUtil.getNowTimestampStr());
            boolean isSuccess = payService.updateAllColumnById(oldM);
            result.setIsSuccess(isSuccess);
        }
        String jsonString = result.toJSON();
        return jsonString;
    }

    /**
    * @description: 删除
    * @createTime: 2017年09月12日 16:48:34
    * @author: xw
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
                boolean isSuccess=orderService.deleteBalanceByIds(idArray);
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
     * 
     * @description:送审
     * @createTime 2017年9月14日 下午2:12:54
     * @author xw
     * @param ids
     * @return
     */
    @RequestMapping(value = "verify",method = RequestMethod.POST)
    @ResponseBody
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
                 List<Order> list = orderService.selectList(ew);
                 int count=0;
                 for (Order order : list) {
                	if(order.getSettlementAuditStatus()==1){
                		activitiProcessService.submit(order, ProcessEnum.order_balance_approve);
                		count++;
                	}
				}
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
    * @description: 分页查询
    * @createTime: 2017年09月12日 16:48:34
    * @author: xw
    * @param m
    * @param rows
    * @param page
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "listByPage")
    public Object listByPage(Order m, int rows, int page){
    	EntityWrapper<Order> ew=new EntityWrapper<Order>();
		Long userId=OperateUtils.getCurrentUserId();
		if(hasPermissionByOpt("viewAll")){
		}else if(hasPermissionByOpt("viewDept")){
			Organization organization=organizationService.selectByUserId(userId);
			ew.isWhere(false).and("l.id={0}",organization.getId());
		}else if(hasViewPermission()){
			ew.isWhere(false).and("a.owner_id={0}",userId);
		}else{
			return JSONUtil.EMPTYJSON;
		}
        Page<Order> pageM= new Page<>(page,rows);
        pageM = orderService.listOrderBalanceByPage(pageM,m,ew);
        String[] properties = {"orderCode","customer.customerName:customerName","product.name:productName","propertyId",
                "bank.bankName:bankName","branch.subbranchName:subbranchName","signingDate",
                "estimateFinishTime","loanAmount","lendingRate","loanTerm","repayWay.name:repayWayName","serviceChargePercent",
                "serviceCharge","commissionAmount","commissionReason","csAssistant.name:csAssistantName","csPrincipal.name:csPrincipalName",
                "followUser.name:followUserName", "owner.name:ownerName","auditStatus","status","cost","receiveAmount","payAmount","companyLoanAmount",
                "companyLoanInterest","receivablesAccountStatus","loanDate","loanMoney","settlementCharge",
                "settlementClerk","createName","id","commissionRate","settlementAuditStatus"};
        String jsonString = JSONUtil.toJson(pageM.getRecords(), properties,(long) pageM.getTotal());
        return jsonString;
    }
    /**
     * 
     * @description:
     * @createTime 2017年9月22日 上午9:50:30
     * @author xw
     * @param m
     * @param rows
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getBalanceById")
    public Object getBalanceById(Order m){
    	EntityWrapper<Order> ew=new EntityWrapper<Order>();
        Page<Order> pageM= new Page<>();
        pageM = orderService.listOrderBalanceByPage(pageM,m,ew);
        String[] properties = {"orderCode","customer.customerName:customerName","product.name:productName","propertyId",
                "bank.bankName:bankName","branch.subbranchName:subbranchName","signingDate",
                "estimateFinishTime","loanAmount","lendingRate","loanTerm","repayWay.name:repayWayName","serviceChargePercent",
                "serviceCharge","commissionAmount","commissionReason","csAssistant.name:csAssistantName","csPrincipal.name:csPrincipalName",
                "followUser.name:followUserName", "owner.name:ownerName","auditStatus","status","cost","receiveAmount","payAmount","companyLoanAmount",
                "companyLoanInterest","receivablesAccountStatus","loanDate","loanMoney","settlementCharge",
                "settlementClerk","createName","id","commissionRate","settlementAuditStatus"};
        String jsonString = JSONUtil.toJson(pageM.getRecords(), properties);
        return jsonString;
    }
    /**
     * 
     * @description:借款分页列表
     * @createTime 2017年9月15日 下午4:31:17
     * @author xw
     * @param orderId
     * @param rows
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "listLoanByPage")
    public Object listLoanByPage(OrderLoan orderLoan,int rows,int page,Long orderId){
    	if(orderLoan==null){
    		return JSONUtil.EMPTYJSON;
    	}
    	if(orderId!=null){
    		Order order=new Order();
    		order.setId(orderId);
    		orderLoan.setOrder(order);
    	}
    	Page<OrderLoan> pageM=new Page<OrderLoan>(page, rows);
    	pageM = orderLoanService.listByPage(pageM, orderLoan);
    	String [] properties={"id","amount","rate","loanDay","interest","auditStatus",
    			"note","beginDate","estimateRepayDate","estimateInterest","beginDate",
    			"estimateLoanDay","receivedAmount","receivingAmount",
    			"repayDate","loanDay","interest","receiveAmount",
    			"referInterest"};
    	String jsonString =JSONUtil.toJson(pageM.getRecords(), properties, (long)pageM.getTotal());
    	return jsonString;
    }
    /**
     * 
     * @description:收入项分页
     * @createTime 2017年9月18日 上午10:57:52
     * @author xw
     * @param orderId
     * @param rows
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "listOrderIncomeByPage")
    public Object listIncomeByPage(OrderIncome income,int rows,int page,Long orderId){
    	if(income==null){
    		return JSONUtil.EMPTYJSON;
    	}
    	if(orderId!=null){
    		Order order=new Order();
    		order.setId(orderId);
    		income.setOrder(order);
    	}
    	Page<OrderIncome> pageM=new Page<OrderIncome>(page, rows);
    	pageM = orderIncomeService.listByPage(pageM, income);
    	String [] properties={"id","costType.name:costTypeName",
    			"estimateAmount","receivedAmount","receivingAmount","note",
    			"receiveAmount"};
    	String jsonString =JSONUtil.toJson(pageM.getRecords(), properties, (long)pageM.getTotal());
    	return jsonString;
    }
    /**
     * 
     * @description:支出项分页
     * @createTime 2017年9月18日 上午10:57:52
     * @author xw
     * @param orderId
     * @param rows
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "listPayByPage")
    public Object listPayByPage(Pay pay,int rows,int page,Long orderId,Integer source){
    	if(pay==null){
    		return JSONUtil.EMPTYJSON;
    	}
    	if(orderId!=null){
    		Order order=new Order();
    		order.setId(orderId);
    		pay.setOrder(order);
    	}
    	if(source!=null){
    		pay.setSource(1);
    	}
    	Page<Pay> pageM=new Page<Pay>(page, rows);
    	pageM = payService.listByBanlancePage(pageM, pay);
    	 String [] properties=new String [24];
         properties[0]="source"; properties[1]="costType.id:costType.id";properties[2]="costType.name:costTypeName";
         properties[3]="isCost"; properties[4]="order.id:order.id"; properties[5]="payBankAccount.accountNumber:payBankAccountNumber";
         properties[6]="receiveAccountName";properties[7]="estimatePayAmount";properties[8]="payTime";
         properties[9]="payAmount";properties[10]="payBankAccount.id";properties[11]="submitUser.name:submitUserName";
         properties[12]="receiveAccountBank";properties[13]="receiveAccountNumber";properties[14]="submitDate";
         properties[15]="submitUser.id";properties[16]="auditStatus";properties[17]="id";
         properties[18]="payObjectType";properties[19]="customer.customerName:object";properties[20]="note";
         properties[21]="payBankAccount.id:payBankAccount.id";properties[22]="customer.id:objectId";
         properties[23]="order.orderCode:orderCode";
    	String jsonString =JSONUtil.toJson(pageM.getRecords(), properties, (long)pageM.getTotal());
    	return jsonString;
    }
    /**
     * 
     * @description:修改借款
     * @createTime 2017年9月20日 上午11:08:20
     * @author xw
     * @param orderLoan
     * @return
     */
    @RequestMapping("updateLoan")
    @ResponseBody
    public Object updateLoan(OrderLoan orderLoan){
    	ServiceResult result=new ServiceResult(false);
    	if(!hasUpdatePermission()){
    		result.setMessage("没有修改权限");
    		return result;
    	}
    	try{
	    	OrderLoan oldM = orderLoanService.selectById(orderLoan.getId());
	    	oldM.setRepayDate(orderLoan.getRepayDate());
	    	oldM.setInterest(orderLoan.getInterest());
	    	oldM.setReceivedAmount(new BigDecimal("0"));
	    	oldM.setReceivingAmount(orderLoan.getReceiveAmount());
	    	oldM.setReferInterest(orderLoan.getReferInterest());
	    	oldM.setLoanDay(orderLoan.getLoanDay());
	    	oldM.setReceiveAmount(orderLoan.getReceiveAmount());
	    	oldM.setLastModifierId(OperateUtils.getCurrentUserId());
	    	oldM.setLastModifyTime(DateUtil.getNowTimestampStr());
	    	orderLoanService.updateAllColumnById(oldM);
    	}catch(Exception e ){
    		e.printStackTrace();
    		result.setMessage("修改失败");
    	}
    	result.setIsSuccess(true);
    	return result;
    }
    /**
     * 
     * @description:修改收入
     * @createTime 2017年9月20日 上午11:19:35
     * @author xw
     * @param orderIncome
     * @return
     */
    @RequestMapping("updateIncome")
    @ResponseBody
    public Object updateIncome(OrderIncome orderIncome){
    	ServiceResult result=new ServiceResult(false);
    	if(!hasUpdatePermission()){
    		result.setMessage("没有修改权限");
    		return result;
    	}
    	try{
	    	orderIncomeService.updateOrderIncome(orderIncome);
    	}catch(Exception e ){
    		e.printStackTrace();
    		result.setMessage("修改失败");
    	}
    	result.setIsSuccess(true);
    	return result;
    }
    /**
     * 
     * @description:修改支出
     * @createTime 2017年9月20日 上午11:32:07
     * @author xw
     * @param pay
     * @return
     */
    @RequestMapping("updatePay")
    @ResponseBody
    public Object updatePay(Pay pay){
    	ServiceResult result=new ServiceResult(false);
    	if(!hasUpdatePermission()){
    		result.setMessage("没有修改权限");
    		return result;
    	}
    	try{
    		Pay oldM = payService.selectById(pay.getId());
    		oldM.setPayAmount(pay.getPayAmount());
    		oldM.setIsCost(pay.getIsCost());
    		oldM.setReceiveAccountBank(pay.getReceiveAccountBank());
    		oldM.setReceiveAccountName(pay.getReceiveAccountName());
    		oldM.setReceiveAccountNumber(pay.getReceiveAccountNumber());
	    	oldM.setLastModifierId(OperateUtils.getCurrentUserId());
	    	oldM.setLastModifyTime(DateUtil.getNowTimestampStr());
	    	oldM.setNote(pay.getNote());
	    	if(pay.getOtherPartners()!=null){
	    		oldM.setOtherPartners(pay.getOtherPartners());
	    	}
	    	payService.updateAllColumnById(oldM);
    	}catch(Exception e ){
    		e.printStackTrace();
    		result.setMessage("修改失败");
    	}
    	result.setIsSuccess(true);
    	return result;
    }
    /**
     * 
     * @description:接单结算订单下拉列表，状态为放款和完结的订单
     * @createTime 2017年9月15日 下午4:28:52
     * @author xw
     * @return
     */
    @RequestMapping("orderCombobox")
    @ResponseBody
    public Object orderCombobox(){
    	if(!hasViewPermission()){
            return  JSONUtil.EMPTYJSON;
        }
    	Long userId=OperateUtils.getCurrentUserId();
    	List<Order> list=orderService.orderCombobox(userId);
    	String [] properties={"id","orderCode:name"};
    	String jsonString=JSONUtil.toJson(list, properties);
    	return jsonString;
    }
    /**
     * 
     * @description:送审
     * @createTime 2017年9月22日 上午8:55:11
     * @author xw
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
        if(formKey.equals("manager") || formKey.equals("companyManager") ||(taskId.equals("undefined")&&formKey.equals("undefined"))){
            return "/approval_template/balance_approval_view";
        }else if (formKey.equals("business")){
            return "/approval_template/balance_approval_edit";
        }else {
            return "/approval_template/404";
        }
    }
    /**
     * 
     * @description:跟踪
     * @createTime 2017年10月13日 下午5:15:36
     * @author xw
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
            	e.printStackTrace();
                return "404-4";
            }
        }
        return "/myWorkbench/tracePicture";
    }
}

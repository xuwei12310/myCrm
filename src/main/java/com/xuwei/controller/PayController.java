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
import com.xuwei.bean.AssessmentCompany;
import com.xuwei.bean.BankSubbranch;
import com.xuwei.bean.Customer;
import com.xuwei.bean.Organization;
import com.xuwei.bean.OtherPartners;
import com.xuwei.bean.Pay;
import com.xuwei.service.IPayService;
import com.xuwei.service.OrganizationService;
import com.xuwei.util.DateUtil;
import com.xuwei.util.JSONUtil;
import com.xuwei.util.OperateUtils;
import com.xuwei.util.ServiceResult;
import com.xuwei.util.StringUtil;


/**
 * @description: 支出登记控制器
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月12日 16:48:34
 * @author: xw
 * @version: 1.0
 */
@Controller
@RequestMapping("/financial/pay")
public class PayController extends BaseController<Pay> {
    @Resource
    private IPayService payService;
    @Resource
    private IActivitiProcessService activitiProcessService;
    @Resource
    private OrganizationService organizationService;
    @Resource
    private RuntimeService runtimeService;


    public PayController(){
        setResourceIdentity("financial:pay");
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
    public Object create(Pay m,Long objectId){
        ServiceResult result = new ServiceResult(false);
        if(!hasCreatePermission()){
            result.setMessage("没有添加权限");
        }else{
        	switch(m.getPayObjectType()){
        	case 1:
        		AssessmentCompany a=new AssessmentCompany();
        		a.setId(objectId);
        		m.setAssessmentCompany(a); 
        		break;
        	case 2:
        		BankSubbranch b=new BankSubbranch();
        		b.setId(objectId);
        		m.setBankSubbranch(b);
                break;
        	case 3:
        		OtherPartners o=new OtherPartners();
        		o.setId(objectId);
        		m.setOtherPartners(o);
        		break;
        	case 4:
        		Customer c=new Customer();
        		c.setId(objectId);
        		m.setCustomer(c);
        		break;
        	}
        	m.setSource(2);
        	m.setAuditStatus(1);
        	m.setSubmitDate(DateUtil.getNowTimestampStr());
        	m.setSubmitUser(OperateUtils.getCurrentUser());
        	m.setCreatorId(OperateUtils.getCurrentUserId());
			m.setCreateTime(DateUtil.getNowTimestampStr());
			m.setLastModifierId(OperateUtils.getCurrentUserId());
			m.setLastModifyTime(DateUtil.getNowTimestampStr());
            boolean isSuccess = payService.insert(m);
            result.setIsSuccess(isSuccess);
        }
        String jsonString = result.toJSON();
        return jsonString;
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
                boolean isSuccess = payService.deleteBatchIds(Arrays.asList(idArray));
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
                 EntityWrapper<Pay> ew=new EntityWrapper<Pay>();
                 ew.in("id", idArray);
                 List<Pay> list = payService.selectList(ew);
                 int count=0;
                 for (Pay pay : list) {
                     /*activitiProcessService.submit(pay, ProcessEnum.pay);*/
                     count++;
				}
                 if(count>0){
                	 result.setIsSuccess(true);
                 }else{
                	 result.setIsSuccess(false);
                	 result.setMessage("送审失败，目前状态无法送审");
                 }
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
    public Object listByPage(Pay m, int rows, int page){
    	EntityWrapper<Pay> ew=new EntityWrapper<Pay>();
		Long userId=OperateUtils.getCurrentUserId();
		if(hasPermissionByOpt("viewAll")){
		}else if(hasPermissionByOpt("viewDept")){
			Organization organization=organizationService.selectByUserId(userId);
			ew.isWhere(false).and("k.id={0}",organization.getId());
		}else if(hasViewPermission()){
			ew.isWhere(false).and("a.creator_id={0}",userId);
		}else{
			return JSONUtil.EMPTYJSON;
		}
        Page<Pay> pageM= new Page<>(page,rows);
        pageM = payService.listByPage(pageM,m,ew);
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
        String jsonString = JSONUtil.toJson(pageM.getRecords(), properties,(long) pageM.getTotal());
        return jsonString;
    }
    /**
     * 
     * @description:通过id获取支出
     * @createTime 2017年10月11日 上午11:02:41
     * @author xw
     * @param m
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getPayById")
    public Object getPayById(Pay m){
    	EntityWrapper<Pay> ew=new EntityWrapper<Pay>();
        Page<Pay> pageM= new Page<>();
        pageM = payService.listByPage(pageM,m,ew);
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
        String jsonString = JSONUtil.toJson(pageM.getRecords(), properties);
        return jsonString;
    }
    /**
     * 
     * @description:送审
     * @createTime 2017年9月22日 上午8:55:29
     * @author xw
     * @param model
     * @param id
     * @param taskId
     * @param formKey
     * @return
     */
    @RequestMapping(value = "approveView",method = RequestMethod.GET)
    public String approveView(Model model,String id,String taskId,String formKey){
    	Pay pay = payService.selectById(id);
        model.addAttribute("id",id);
        model.addAttribute("taskId",taskId);
        model.addAttribute("processInstanceId",pay.getProcessInstanceId());
        if(formKey.equals("manager") || formKey.equals("finance")||(taskId.equals("undefined")&&formKey.equals("undefined"))){
            return "/approval_template/pay_approval_view";
        }else if (formKey.equals("business")){
            return "/approval_template/pay_approval_edit";
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
        if(!hasPermissionByOpt("trace")){
            return "404-1";
        }else{
            try {
                if(id==null){
                    return "404-2";
                }
                Pay pay = payService.selectById(id);
                if(StringUtils.isEmpty(pay.getProcessInstanceId())){
                    return "404-3";
                }
                ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                        .processInstanceId(pay.getProcessInstanceId()).singleResult();
                model.addAttribute("processInstanceId",pay.getProcessInstanceId());
                model.addAttribute("processDefinitionId",processInstance.getProcessDefinitionId());
            } catch (Exception e) {
                return "404-4";
            }
        }
        return "/myWorkbench/tracePicture";
    }
}

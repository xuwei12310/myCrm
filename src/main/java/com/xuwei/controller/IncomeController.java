package com.xuwei.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xuwei.bean.Income;
import com.xuwei.bean.IncomeDistribute;
import com.xuwei.bean.Order;
import com.xuwei.bean.Organization;
import com.xuwei.bean.User;
import com.xuwei.service.IIncomeDistributeService;
import com.xuwei.service.IIncomeService;
import com.xuwei.service.IOrderService;
import com.xuwei.service.OrganizationService;
import com.xuwei.util.DateUtil;
import com.xuwei.util.JSONUtil;
import com.xuwei.util.OperateUtils;
import com.xuwei.util.ServiceResult;
import com.xuwei.util.StringUtil;


/**
 * @description: 收入登记控制器
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月05日 09:07:52
 * @author: zyd
 * @version: 1.0
 */
@Controller
@RequestMapping("/financial/income")
public class IncomeController extends BaseController<Income> {
    @Resource
    private IIncomeService incomeService;
    @Resource
    private IOrderService orderService;
    @Resource
    private IIncomeDistributeService incomeDistributeService;
    @Resource
    private OrganizationService organizationService;
    public IncomeController(){
        setResourceIdentity("financial:income");
    }

    /**
    * @description: 转向模块主界面
    * @createTime: 2017年09月05日 09:07:52
    * @author: zyd
    * @param model
    * @return
    */
    @RequestMapping(value = "main",method = RequestMethod.GET)
    public String main(Model model){
        return defaultViewPrefix();
    }

    /**
    * @description: 添加
    * @createTime: 2017年09月05日 09:07:52
    * @author: zyd
    * @param model
    * @param m
    * @return
    */
    @RequestMapping(value = "create",method = RequestMethod.POST)
    @ResponseBody
    public Object create(Model model,Income m,String incomeItem,String loanItem){
        ServiceResult result = new ServiceResult(false);
        if(!hasCreatePermission()){
            result.setMessage("没有添加权限");
        }else{
        	try{
        	m.setCreateTime(DateUtil.getNowTimesminutStr());
        	m.setCreatorId(OperateUtils.getCurrentUserId());
        	m.setLastModifierId(OperateUtils.getCurrentUserId());
        	m.setLastModifyTime(DateUtil.getNowTimesminutStr());
            incomeService.insert(m,incomeItem,loanItem);
        	}catch(Exception e){
        		e.printStackTrace();
        		result.setMessage("添加失败");
        		return result.toJSON();
        	}
        }
        result.setIsSuccess(true);
        String jsonString = result.toJSON();
        return jsonString;
    }

    /**
    * @description: 修改
    * @createTime: 2017年09月05日 09:07:52
    * @author: zyd
    * @param model
    * @param m
    * @return
    */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public Object update(Model model,Income m,String incomeItem,String loanItem){
        ServiceResult result = new ServiceResult(false);
        if(!hasUpdatePermission()){
            result.setMessage("没有修改权限");
        }else{
        	try{
        	Income oldM = incomeService.selectById(m.getId());
        	oldM.setIncomeTime(m.getIncomeTime());
        	oldM.setAmount(m.getAmount());
        	oldM.setBilling(m.getBilling());
        	oldM.setOrder(m.getOrder());
        	oldM.setPayType(m.getPayType());
        	oldM.setPeriodTime(m.getPeriodTime());
        	oldM.setNote(m.getNote());
            incomeService.update(oldM,incomeItem,loanItem);
        	}catch(Exception e){
        		e.printStackTrace();
        		result.setMessage("修改失败");
        		return result.toJSON();
        	}
        }
        result.setIsSuccess(true);
        String jsonString = result.toJSON();
        return jsonString;
    }
    
    /**
    * @description: 删除
    * @createTime: 2017年09月25日 19:07:52
    * @author: xw
    * @param model
    * @param ids
    * @return
    */
    @RequestMapping(value = "mulDelete",method = RequestMethod.POST)
    @ResponseBody
    public Object mulDelete(Model model,String ids){
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
                incomeService.delete(Arrays.asList(idArray));
            } catch (Exception e) {
            	e.printStackTrace();
                if(e instanceof org.springframework.dao.DataIntegrityViolationException){
                    result.setMessage("数据已被引用不能删除");
                    return result.toJSON();
                }else{
                    result.setMessage("删除出错："+e.getMessage());
                    return result.toJSON();
                }
            }
        }
        result.setIsSuccess(true);
        String jsonString = result.toJSON();
        return jsonString;
    }

    /**
    * @description: 分页查询
    * @createTime: 2017年09月05日 09:07:52
    * @author: zyd
    * @param model
    * @param m
    * @param rows
    * @param page
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "listByPage")
    public Object listByPage(Model model, Income m, int rows, int page){
    	EntityWrapper<Income> ew=new EntityWrapper<Income>();
		Long userId=OperateUtils.getCurrentUserId();
		if(hasPermissionByOpt("viewAll")){
		}else if(hasPermissionByOpt("viewDept")){
			Organization organization=organizationService.selectByUserId(userId);
			ew.isWhere(false).and("h.id={0}",organization.getId());
		}else if(hasViewPermission()){
			ew.isWhere(false).and("a.creator_id={0}",userId);
		}else{
			return JSONUtil.EMPTYJSON;
		}
        Page<Income> pageM = new Page<>(page, rows);
		List<Income> list = incomeService.listIncomeByPage(pageM, m,ew);
        String[] properties = {"incomeTime","amount","order.customer.id:customerId","order.customer.customerName:customerName",
        		"order.orderCode:orderCode","order.id:orderId","billing","payType.id:payTypeId","payType.name:payTypeName",
        		"periodTime.id:periodTimeId","periodTime.name:periodTimeName","id","ownerName","note"};
        String jsonString = JSONUtil.toJson(list, properties,(long) pageM.getTotal());
        return jsonString;
    }
    /**
     * 
     * @description: 获取拥有人信息
     * @createTime: 2017年9月5日 上午9:58:41
     * @author: zyd
     * @param rows
     * @param page
     * @return
     */
    @RequestMapping(value = "getOwnerByList",method = RequestMethod.POST)
    @ResponseBody
    public Object getOwnerByList(int rows, int page , String userName){
    	Page<User> pageM= new Page<>(page,rows);
    	String[] properties = { "id", "username","name"};
        List<User> list = incomeService.getOwnerByList(pageM,userName);
    	return JSONUtil.toJson(list, properties,(long) pageM.getTotal());
    }
   /**
    * 
    * @description:订单分页查询
    * @createTime 2017年9月25日 下午2:52:12
    * @author xw
    * @param rows
    * @param page
    * @param orderCode
    * @return
    */
    @RequestMapping(value = "getOrderByList",method = RequestMethod.POST)
    @ResponseBody
    public Object getOrderByList(int rows, int page , Order order){
    	int type = 0;
        if(!hasPermissionByOpt("viewall")){
            if(hasPermissionByOpt("viewDept")){
                type = 1;
            }else if(hasViewPermission()){
                type = 2;
            }else{
                return  JSONUtil.EMPTYJSON;
            }
        }
    	Page<Order> pageM= new Page<>(page,rows);
    	   String[] properties = {"orderCode","customer.customerName:customerName","product.name:productName","propertyId",
                   "bank.bankName:bankName","branch.subbranchName:subbranchName","signingDate",
                   "estimateFinishTime","loanAmount","lendingRate","loanTerm","repayWay.name:repayWayName","serviceChargePercent",
                   "serviceCharge","commissionAmount","commissionReason","csAssistant.name:csAssistantName","csPrincipal.name:csPrincipalName",
                   "followUser.name:followUserName", "owner.name:ownerName","auditStatus","status","cost","receiveAmount","payAmount","companyLoanAmount",
                   "companyLoanInterest","receivablesAccountStatus","loanDate","loanMoney","settlementCharge",
                   "settlementClerk","createName","id"};
    	EntityWrapper<Order> ew=new EntityWrapper<Order>(order);
    	ew.isWhere(false).and("a.status={0}",4);
        pageM = orderService.selectPage(pageM, ew,type);
    	return JSONUtil.toJson(pageM.getRecords(), properties,(long) pageM.getTotal());
    }
    /**
     * 
     * @description:获取订单收入和订单借款分页列表
     * @createTime 2017年9月25日 下午4:21:36
     * @author xw
     * @param rows
     * @param page
     * @param orderId
     * @return
     */
    @RequestMapping("getIncomeItemList")
    @ResponseBody
    public Object getIncomeItemList(Long orderId){
    	if(orderId==null){
    		return JSONUtil.EMPTYJSON;
    	}
    	Page<Map<String,Object>> pageM=new Page<>();
    	pageM=incomeService.getIncomeItemList(pageM,orderId);
    	String [] properties={"id","name","receiveAmount","receivedAmount","receivingAmount","pay",
    			"type","note"};
    	return JSONUtil.toJson(pageM.getRecords(),properties);
    }
    /**
     * 
     * @description:修改时从订单分配获取分页列表
     * @createTime 2017年9月26日 上午11:16:15
     * @author xw
     * @param rows
     * @param page
     * @param incomeId
     * @return
     */
    @RequestMapping("getUpdateIncomeItemList")
    @ResponseBody
    public Object getUpdateIncomeItemList(Long incomeId){
    	Page<IncomeDistribute> pageM=new Page<IncomeDistribute>();
    	pageM=incomeDistributeService.listByPage(pageM,incomeId);
    	String [] properties={"id","income.id","orderIncome.id:fromId","costType:name","type",
    			"receiveAmount","receivedAmount","receivingAmount","note","amount:pay"};
    	String jsonString=JSONUtil.toJson(pageM.getRecords(), properties, (long)pageM.getTotal());
		return jsonString;
    }
}

package com.xuwei.controller;

import java.util.Arrays;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xuwei.bean.Bank;
import com.xuwei.bean.BankSubbranch;
import com.xuwei.service.IBankService;
import com.xuwei.service.IBankSubbranchService;
import com.xuwei.util.DateUtil;
import com.xuwei.util.JSONUtil;
import com.xuwei.util.OperateUtils;
import com.xuwei.util.ServiceResult;
import com.xuwei.util.StringUtil;

/**
 * @description:合作银行控制层
 * @createTime 上午10:26:45
 * @author xw
 *
 */
@Controller
@RequestMapping(value="/dict/cooperateBank")
public class CooperateBankController extends BaseController<Bank>{
	
	@Resource 
	private IBankService bankService;
	@Resource IBankSubbranchService bankSubbranchService;
	public CooperateBankController(){
		setResourceIdentity("dict:cooperateBank");
	}
	/**
	 * 
	 * @description:主页面
	 * @createTime 2017年8月30日 上午10:36:19
	 * @author xw
	 * @param mode
	 * @return
	 */
	@RequestMapping(value="main")
	public String Main(Model mode){
		return defaultViewPrefix();
	}
	/**
	 * 
	 * @description:添加
	 * @createTime 2017年8月30日 上午10:37:21
	 * @author xw
	 * @param model
	 * @param m
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public Object create(Model model, Bank m) {
		ServiceResult result = new ServiceResult(false);
		if (!hasPermissionByOpt("create")) {
			result.setMessage("没有添加权限");
		} else {
			EntityWrapper<Bank> ew = new EntityWrapper<Bank>();
			ew.eq("bank_name", m.getBankName());
			if (bankService.selectCount(ew) > 0) {
				result.setMessage("银行名称已存在，请重新输入");
				return result;
			}
			m.setArray(bankService.nextArray());
			m.setIsSys(0);
			m.setCreateTime(DateUtil.getNowTimestampStr());
			m.setCreatorId(OperateUtils.getCurrentUserId());
			m.setLastModifyTime(DateUtil.getNowTimestampStr());
			m.setLastModifierId(OperateUtils.getCurrentUserId());
			boolean isSuccess = bankService.insert(m);
			result.setIsSuccess(isSuccess);
			result.addData("id", m.getId());
		}
		String jsonString = result.toJSON();
		return jsonString;
	}
	/**
	 * 
	 * @description:添加支行
	 * @createTime 2017年8月30日 下午5:15:57
	 * @author xw
	 * @param model
	 * @param m
	 * @return
	 */
	@RequestMapping(value = "createSubbranch", method = RequestMethod.POST)
	@ResponseBody
	public Object createSubbranch(Model model, BankSubbranch m) {
		ServiceResult result = new ServiceResult(false);
		if (!hasPermissionByOpt("config")) {
			result.setMessage("没有添加支行权限");
		} else {
			EntityWrapper<BankSubbranch> ew = new EntityWrapper<BankSubbranch>();
			ew.eq("subbranch_name", m.getSubbranchName());
			if (bankSubbranchService.selectCount(ew) > 0) {
				result.setMessage("支行名称已存在，请重新输入");
				return result;
			}
			m.setArray(bankSubbranchService.nextArray());
			m.setCreateTime(DateUtil.getNowTimestampStr());
			m.setCreatorId(OperateUtils.getCurrentUserId());
			m.setLastModifyTime(DateUtil.getNowTimestampStr());
			m.setLastModifierId(OperateUtils.getCurrentUserId());
			boolean isSuccess = bankSubbranchService.insert(m);
			result.setIsSuccess(isSuccess);
			result.addData("id", m.getId());
		}
		String jsonString = result.toJSON();
		return jsonString;
	}

	/**
	 * 
	 * @description:修改
	 * @createTime 2017年8月30日 上午10:37:33
	 * @author xw
	 * @param model
	 * @param m
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	 public Object update(Model model,Bank m){
        ServiceResult result = new ServiceResult(false);
        if(!hasUpdatePermission()){
            result.setMessage("没有修改权限");
        }else{
         
           Bank oldM = bankService.selectById(m.getId());
           if(!oldM.getBankName().equals(m.getBankName())){
       		 Wrapper<Bank> wrapper = new EntityWrapper<>();
           	 wrapper.eq("bank_name",m.getBankName());
       		 if(bankService.selectCount(wrapper)>0){
             	  result.setMessage("银行名称已存在，请重新输入");
                   return result;
       		 }
               }
            oldM.setBankName(m.getBankName());
            oldM.setNote(m.getNote());
            //oldM.setArray(m.getArray());
            oldM.setStatus(m.getStatus());
            oldM.setLastModifyTime(DateUtil.getNowTimestampStr());
            oldM.setLastModifierId(OperateUtils.getCurrentUserId());
            boolean isSuccess = bankService.updateAllColumnById(oldM);
            result.setIsSuccess(isSuccess);
        }
        
        String jsonString = result.toJSON();
        return jsonString;
    }
	/**
	 * 
	 * @description:修改支行
	 * @createTime 2017年8月31日 上午8:40:06
	 * @author xw
	 * @param model
	 * @param m
	 * @return
	 */
	@RequestMapping(value = "updateSubbranch", method = RequestMethod.POST)
	@ResponseBody
	 public Object updateSubbranch(Model model,BankSubbranch m){
        ServiceResult result = new ServiceResult(false);
        if(!hasPermissionByOpt("config")){
            result.setMessage("没有修改支行权限");
        }else{
         
           BankSubbranch oldM = bankSubbranchService.selectById(m.getId());
           if(!oldM.getSubbranchName().equals(m.getSubbranchName())){
       		 Wrapper<BankSubbranch> wrapper = new EntityWrapper<>();
           	 wrapper.eq("subbranch_name",m.getSubbranchName());
       		 if(bankSubbranchService.selectCount(wrapper)>0){
             	  result.setMessage("支行名称已存在，请重新输入");
                   return result;
       		 }
               }
            oldM.setSubbranchName(m.getSubbranchName());
            oldM.setNote(m.getNote());
            //oldM.setArray(m.getArray());
            Bank bank=new Bank();
            bank.setId(m.getBank().getId());
            oldM.setBank(bank);
            oldM.setAddress(m.getAddress());
            oldM.setContacts(m.getContacts());
            oldM.setBankCode(m.getBankCode());
            oldM.setPhone(m.getPhone());
            oldM.setStatus(m.getStatus());
            oldM.setLastModifyTime(DateUtil.getNowTimestampStr());
            oldM.setLastModifierId(OperateUtils.getCurrentUserId());
            boolean isSuccess = bankSubbranchService.updateAllColumnById(oldM);
            result.setIsSuccess(isSuccess);
        }
        
        String jsonString = result.toJSON();
        return jsonString;
    }

	/**
	 * 
	 * @description:删除
	 * @createTime 2017年8月30日 上午10:37:44
	 * @author xw
	 * @param model
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "mulDelete", method = RequestMethod.POST)
	@ResponseBody
	public Object mulDelete(Model model, String ids) {
		ServiceResult result = new ServiceResult(false);
		if (!hasUpdatePermission()) {
			result.setMessage("没有删除权限");
		} else {
			try {
				String[] idArray = StringUtil.split(ids);
				if (idArray == null || idArray.length == 0) {
					result.setMessage("请选择要删除的数据行");
					return result;
				}
				boolean isSuccess = bankService.deleteBatchIds(Arrays.asList(idArray));
				result.setIsSuccess(isSuccess);
			} catch (Exception e) {
				if (e instanceof org.springframework.dao.DataIntegrityViolationException) {
					result.setMessage("数据已被引用不能删除");
				} else {
					result.setMessage("删除出错：" + e.getMessage());
				}
			}
		}
		String jsonString = result.toJSON();
		return jsonString;
	}
	/**
	 * 
	 * @description:删除支行
	 * @createTime 2017年8月31日 上午9:08:43
	 * @author xw
	 * @param model
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "mulDeleteSubbranch", method = RequestMethod.POST)
	@ResponseBody
	public Object mulDeleteSubbranch(Model model, String ids) {
		ServiceResult result = new ServiceResult(false);
		if (!hasPermissionByOpt("config")) {
			result.setMessage("没有删除支行权限");
		} else {
			try {
				String[] idArray = StringUtil.split(ids);
				if (idArray == null || idArray.length == 0) {
					result.setMessage("请选择要删除的数据行");
					return result;
				}
				boolean isSuccess = bankSubbranchService.deleteBatchIds(Arrays.asList(idArray));
				result.setIsSuccess(isSuccess);
			} catch (Exception e) {
				if (e instanceof org.springframework.dao.DataIntegrityViolationException) {
					result.setMessage("数据已被引用不能删除");
				} else {
					result.setMessage("删除出错：" + e.getMessage());
				}
			}
		}
		String jsonString = result.toJSON();
		return jsonString;
	}
	/**
	 * 
	 * @description:列表
	 * @createTime 2017年8月30日 上午10:37:58
	 * @author xw
	 * @param model
	 * @param m
	 * @param rows
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "listByPage")
	public Object listByPage(Model model, Bank m, int rows, int page) {
		if (!hasViewPermission()) {
			return JSONUtil.EMPTYJSON;
		}
		Page<Bank> pageM = new Page<>(page, rows);
		EntityWrapper<Bank> ew = new EntityWrapper<>(m);
		ew.orderBy("array", true);
		if (!StringUtils.isEmpty(m.getBankName())) {
			ew.like("bank_name", m.getBankName());
		}
		m.setBankName(null);
		pageM = bankService.selectPage(pageM, ew);
		String[] properties = { "bankName", "status","note", "isSys", "array", "id" };
		String jsonString = JSONUtil.toJson(pageM.getRecords(), properties, (long) pageM.getTotal());
		return jsonString;
	}
	/**
	 * 
	 * @description:分页查询支行
	 * @createTime 2017年8月30日 下午4:39:36
	 * @author xw
	 * @param model
	 * @param m
	 * @param rows
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "listBranchByPage")
	public Object listBranchByPage(Model model, BankSubbranch m,Long bankId, int rows, int page) {
		if (!hasViewPermission()) {
			return JSONUtil.EMPTYJSON;
		}
		Page<BankSubbranch> pageM = new Page<>(page, rows);
		EntityWrapper<BankSubbranch> ew = new EntityWrapper<>(m);
		ew.orderBy("array", true);
		if(bankId!=null){
			ew.eq("bank_id",bankId);
		}
		if(m.getSubbranchName()!=null){
			ew.eq("subbranch_name",m.getSubbranchName());
		}
		if(m.getStatus()!=null){
			ew.eq("status",m.getStatus());
		}
		m.setSubbranchName(null);
		pageM = bankSubbranchService.selectPage(pageM, ew);
		String[] properties = { "array", "id","bank.id:bankId", "subbranchName","bankCode","phone","contacts","address","note","status"};
		String jsonString = JSONUtil.toJson(pageM.getRecords(), properties, (long) pageM.getTotal());
		return jsonString;
	}
	/**
	 * 
	 * @description:上移下移
	 * @createTime 2017年8月30日 上午10:38:13
	 * @author xw
	 * @param model
	 * @param srcId
	 * @param destId
	 * @return
	 */
	@RequestMapping(value = "/changeArray", method = RequestMethod.POST)
	@ResponseBody
	public Object changeArray(Model model, @RequestParam(required = false) Long srcId,
			@RequestParam(required = false) Long destId) {
		ServiceResult result = new ServiceResult(false);
		if (!hasPermission("dict:cooperateBank:up") || !hasPermission("dict:cooperateBank:down")) {
			result.setMessage("没有移动权限");
			return result;
		}
		result = bankService.changeArray(srcId, destId);
		return result;
	}
	/**
	 * 
	 * @description:支行上移下移
	 * @createTime 2017年8月31日 上午9:41:08
	 * @author xw
	 * @param model
	 * @param srcId
	 * @param destId
	 * @return
	 */
	@RequestMapping(value = "/changeSubbranchArray", method = RequestMethod.POST)
	@ResponseBody
	public Object changeSubbranchArray(Model model, @RequestParam(required = false) Long srcId,
			@RequestParam(required = false) Long destId) {
		ServiceResult result = new ServiceResult(false);
		if (!hasPermission("dict:cooperateBank:config")) {
			result.setMessage("没有移动权限");
			return result;
		}
		result = bankSubbranchService.changeArray(srcId, destId);
		return result;
	}
	/**
	 * 
	 * @description:支出登记模块展示订单下的银行
	 * @createTime 2017年9月14日 上午9:41:07
	 * @author xw
	 * @param orderId
	 * @param rows
	 * @param page
	 * @return
	 */
    @RequestMapping(value="listBankByOrder")
    @ResponseBody
    public Object listBankByOrder(Long orderId,int rows,int page){
    	Page<BankSubbranch> pageM = new Page<>(page, rows);
    	pageM=bankSubbranchService.listBankByOrder(pageM,orderId);
    	String[] properties = { "array", "id","bank.id:bankId", "subbranchName","bankCode","phone","contacts","address","note","status"};
		String jsonString = JSONUtil.toJson(pageM.getRecords(), properties, (long) pageM.getTotal());
		return jsonString;
    }
}

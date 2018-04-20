package com.xuwei.controller;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.xuwei.bean.Customer;
import com.xuwei.bean.CustomerComplaint;
import com.xuwei.bean.Order;
import com.xuwei.bean.User;
import com.xuwei.service.ICustomerComplaintService;
import com.xuwei.service.ICustomerService;
import com.xuwei.service.IDictService;
import com.xuwei.service.IUserService;
import com.xuwei.util.DateUtil;
import com.xuwei.util.JSONUtil;
import com.xuwei.util.OperateUtils;
import com.xuwei.util.ServiceResult;
import com.xuwei.util.StringUtil;

/**
 * @description: 工作台-客户投诉
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年9月3日下午2:25:19
 * @author：zjy @version：1.0
 */
@Controller
@RequestMapping("myWorkbench/customerComplaint")
public class CustomerComplaintController extends BaseController<CustomerComplaint> {
	@Resource
	private IDictService dictService;
	@Resource
	private IUserService userService;
	@Resource
	private ICustomerService customerService;
	@Resource
	private ICustomerComplaintService customerComplaintService;

	public CustomerComplaintController() {
		setResourceIdentity("myWorkbench:customerComplaint");
	}

	/**
	 * 
	 * @description: 转向模块主界面
	 * @createTime: 2017年8月29日 上午11:09:08
	 * @author: zjy
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public String main(Model model) {
		return defaultViewPrefix();
	}

	/**
	 * @description: 添加
	 * @createTime: 2017年08月29日 10:52:02
	 * @author: wwh
	 * @param model
	 * @param m
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public Object create(Model model, CustomerComplaint m) {
		ServiceResult result = new ServiceResult(false);
		if (!hasCreatePermission()) {
			result.setMessage("没有添加权限");
		} else {
			m.setCreateTime(DateUtil.getNowTimestampStr());
			m.setCreatorId(OperateUtils.getCurrentUserId());
			m.setLastModifyTime(DateUtil.getNowTimestampStr());
			m.setLastModifierId(OperateUtils.getCurrentUserId());
			boolean isSuccess = customerComplaintService.insert(m);
			result.setIsSuccess(isSuccess);
			result.addData("id", m.getId());
		}
		String jsonString = result.toJSON();
		return jsonString;
	}

	/**
	 * 
	 * @description: 修改
	 * @createTime: 2017年8月29日 上午11:08:45
	 * @author: zjy
	 * @param model
	 * @param m
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public Object update(Model model, CustomerComplaint m) {
		ServiceResult result = new ServiceResult(false);
		if (!hasUpdatePermission()) {
			result.setMessage("没有修改权限");
		} else {

			CustomerComplaint oldM = customerComplaintService.selectById(m.getId());
			oldM.setCustomer(m.getCustomer());
			oldM.setOrder(m.getOrder());
			oldM.setComplaintContent(m.getComplaintContent());
			oldM.setComplaintObject(m.getComplaintObject());
			oldM.setComplaintTime(m.getComplaintTime());
			oldM.setHandle(m.getHandle());
			oldM.setHandleTime(m.getHandleTime());
			oldM.setHandleDetails(m.getHandleDetails());
			oldM.setStatus(m.getStatus());
			oldM.setNote(m.getNote());
			oldM.setLastModifyTime(DateUtil.getNowTimestampStr());
			oldM.setLastModifierId(OperateUtils.getCurrentUserId());
			boolean isSuccess = customerComplaintService.updateAllColumnById(oldM);
			result.setIsSuccess(isSuccess);
		}

		String jsonString = result.toJSON();
		return jsonString;
	}

	/**
	 * @description: 删除
	 * @createTime: 2017年08月31日 10:49:27
	 * @author: caw
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
				boolean isSuccess = customerComplaintService.deleteBatchIds(Arrays.asList(idArray));
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
	 * @description: 获取客户信息
	 * @createTime: 2017年9月4日 上午9:58:41
	 * @author: zjy
	 * @param rows
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "getCustomerByList", method = RequestMethod.POST)
	@ResponseBody
	public Object getCustomerByList(int rows, int page, String customerName) {
		Page<Customer> pageM = new Page<>(page, rows);
		String[] properties = { "id", "customerName" };
		List<Customer> list = customerComplaintService.getCustemerByList(pageM, customerName);
		return JSONUtil.toJson(list, properties, (long) pageM.getTotal());
	}

	/**
	 * 
	 * @description: 获取处理人信息
	 * @createTime: 2017年9月4日 上午9:58:41
	 * @author: zjy
	 * @param rows
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "getHandleByList", method = RequestMethod.POST)
	@ResponseBody
	public Object getHandleByList(int rows, int page, String userName) {
		Page<User> pageM = new Page<>(page, rows);
		String[] properties = { "id", "username", "name" };
		List<User> list = customerComplaintService.getHandleByList(pageM, userName);
		return JSONUtil.toJson(list, properties, (long) pageM.getTotal());
	}

	/**
	 * 
	 * @description: 获取订单信息
	 * @createTime: 2017年9月4日 上午9:58:41
	 * @author: zjy
	 * @param rows
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "getOrderByList", method = RequestMethod.POST)
	@ResponseBody
	public Object getOrderByList(int rows, int page, String orderCode,String customerId) {
		Page<Order> pageM = new Page<>(page, rows);
		String[] properties = { "id", "orderCode","signingDate","estimateFinishTime","loanAmount" };
		List<Order> list = customerComplaintService.getOrderByList(pageM, orderCode,customerId);
		return JSONUtil.toJson(list, properties, (long) pageM.getTotal());
	}

	/**
	 * 
	 * @description: 分页查询
	 * @createTime: 2017年8月29日 上午11:09:37
	 * @author: zjy
	 * @param model
	 * @param m
	 * @param rows
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "listByPage")
	public Object listByPage(Model model, CustomerComplaint m, int rows, int page) {
		int viewType = 0; 
		if (!hasPermissionByOpt("viewAll")) {
			if(hasPermissionByOpt("viewDepartment")){
        		viewType = 1;
        		User user = userService.getUserInfo(OperateUtils.getCurrentUserId());
        		m.setComplaintObject(user);
        	}else if(hasViewPermission()){
        		viewType = 2;
        		m.setComplaintObject(OperateUtils.getCurrentUser());
        	}else{
        		return  JSONUtil.EMPTYJSON;
        	}
		}
		Page<CustomerComplaint> pageM = new Page<>(page, rows);
		List<CustomerComplaint> list = customerComplaintService.listCustomerComplaintByPage(pageM, m,viewType);
		String[] properties = { "id", "customer.id:customerId", "order.id:orderId", "order.orderCode:orderCode",
				"complaintContent", "customer.customerName:customerName", "complaintObject.id:complaintObjectId",
				"complaintObject.username:complaintObjectUserName","complaintObject.name:complaintObjectName", "complaintTime", "handle.id:handleId",
				"handle.username:handleUserName","handle.name:handleName", "handleTime", "handleDetails", "status", "note", "creatorId"};
		String jsonString = JSONUtil.toJson(list, properties, (long) pageM.getTotal());
		return jsonString;
	}

}

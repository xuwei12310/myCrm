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
import com.xuwei.bean.CustomerCare;
import com.xuwei.bean.User;
import com.xuwei.service.ICustomerCareService;
import com.xuwei.service.ICustomerService;
import com.xuwei.service.IDictService;
import com.xuwei.service.IUserService;
import com.xuwei.util.DateUtil;
import com.xuwei.util.JSONUtil;
import com.xuwei.util.OperateUtils;
import com.xuwei.util.ServiceResult;
import com.xuwei.util.StringUtil;

/**
 * @description: 工作台-客户关怀
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年9月3日下午2:25:19
 * @author：zjy @version：1.0
 */
@Controller
@RequestMapping("/myWorkbench/customerCare")
public class CustomerCareController extends BaseController<CustomerCare> {
	@Resource
	private IDictService dictService;
	@Resource
	private IUserService userService;
	@Resource
	private ICustomerService customerService;
	@Resource
	private ICustomerCareService customerCareService;

	public CustomerCareController() {
		setResourceIdentity("myWorkbench:customerCare");
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
	public Object create(Model model, CustomerCare m) {
		ServiceResult result = new ServiceResult(false);
		if (!hasCreatePermission()) {
			result.setMessage("没有添加权限");
		} else {
			m.setCreateTime(DateUtil.getNowTimestampStr());
			m.setCreatorId(OperateUtils.getCurrentUserId());
			m.setLastModifyTime(DateUtil.getNowTimestampStr());
			m.setLastModifierId(OperateUtils.getCurrentUserId());
			boolean isSuccess = customerCareService.insert(m);
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
	public Object update(Model model, CustomerCare m) {
		ServiceResult result = new ServiceResult(false);
		if (!hasUpdatePermission()) {
			result.setMessage("没有修改权限");
		} else {

			CustomerCare oldM = customerCareService.selectById(m.getId());
			oldM.setOwner(m.getOwner());
			oldM.setCustomer(m.getCustomer());
			oldM.setCareContent(m.getCareContent());
			oldM.setDetails(m.getDetails());
			oldM.setHandleTime(m.getHandleTime());
			oldM.setHandle(m.getHandle());
			oldM.setStatus(m.getStatus());
			oldM.setNote(m.getNote());
			oldM.setLastModifyTime(DateUtil.getNowTimestampStr());
			oldM.setLastModifierId(OperateUtils.getCurrentUserId());
			boolean isSuccess = customerCareService.updateAllColumnById(oldM);
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
				boolean isSuccess = customerCareService.deleteBatchIds(Arrays.asList(idArray));
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
		List<Customer> list = customerCareService.getCustemerByList(pageM, customerName);
		return JSONUtil.toJson(list, properties, (long) pageM.getTotal());
	}

	/**
	 * 
	 * @description: 获取拥有人信息
	 * @createTime: 2017年9月4日 上午9:58:41
	 * @author: zjy
	 * @param rows
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "getOwnerByList", method = RequestMethod.POST)
	@ResponseBody
	public Object getOwnerByList(int rows, int page, String userName) {
		Page<User> pageM = new Page<>(page, rows);
		String[] properties = { "id", "username", "name" };
		List<User> list = customerCareService.getOwnerByList(pageM, userName);
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
	public Object listByPage(Model model, CustomerCare m, int rows, int page) {
		int viewType = 0; 
		if (!hasPermissionByOpt("viewAll")) {
			if(hasPermissionByOpt("viewDepartment")){
        		viewType = 1;
        		User user = userService.getUserInfo(OperateUtils.getCurrentUserId());
        		m.setOwner(user);
        	}else if(hasViewPermission()){
        		viewType = 2;
        		m.setOwner(OperateUtils.getCurrentUser());
        	}else{
        		return  JSONUtil.EMPTYJSON;
        	}
		}
		Page<CustomerCare> pageM = new Page<>(page, rows);
		List<CustomerCare> list = customerCareService.listCustomerCareByPage(pageM, m,viewType);
		String[] properties = { "id", "customer.id:customerId", "careContent", "customer.customerName:customerName",
				"owner.username:ownerUserName", "owner.id:ownerId", "details", "handle.id:handleId",
				"handle.username:handleUserName", "handleTime", "status", "note", "creatorId", "createTime",
				"lastModifyTime", "lastModifierId" };
		String jsonString = JSONUtil.toJson(list, properties, (long) pageM.getTotal());
		return jsonString;
	}

}

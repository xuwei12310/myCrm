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
import com.baomidou.mybatisplus.plugins.Page;
import com.xuwei.bean.Dict;
import com.xuwei.bean.DictType;
import com.xuwei.bean.Schedule;
import com.xuwei.mapper.DictMapper;
import com.xuwei.service.IDictService;
import com.xuwei.service.IScheduleService;
import com.xuwei.util.DateUtil;
import com.xuwei.util.JSONUtil;
import com.xuwei.util.OperateUtils;
import com.xuwei.util.ServiceResult;
import com.xuwei.util.StringUtil;

/**
 * 
 * @description:基础字典-产品与产品进度设置
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年8月29日下午3:35:34
 * @author：zjy @version：1.0
 */
@Controller
@RequestMapping("/dict/product")
public class ProductController extends BaseController<Dict> {
	@Resource
	private IDictService dictService;
	@Resource
	private DictMapper dictMapper;
    @Resource
	private IScheduleService scheduleService;
	public ProductController() {
		setResourceIdentity("dict:product");
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
	 * 
	 * @description: 添加
	 * @createTime: 2017年8月29日 上午11:08:54
	 * @author: zjy
	 * @param model
	 * @param m
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public Object create(Model model, Dict m) {
		ServiceResult result = new ServiceResult(false);
		if (!hasCreatePermission()) {
			result.setMessage("没有添加权限");
		} else {
			EntityWrapper<Dict> ew = new EntityWrapper<Dict>();
			ew.eq("name", m.getName());
			ew.eq("dict_type", DictType.product);
			if (dictService.selectCount(ew) > 0) {
				result.setMessage("产品名称已存在，请重新输入");
				return result;
			}
			m.setArray(dictService.nextArray(DictType.product));
			m.setIsSys(0);
			m.setDictType(DictType.product.toString());
			m.setCreateTime(DateUtil.getNowTimestampStr());
			m.setCreatorId(OperateUtils.getCurrentUserId());
			m.setLastModifyTime(DateUtil.getNowTimestampStr());
			m.setLastModifierId(OperateUtils.getCurrentUserId());
			boolean isSuccess = dictService.insert(m);
			result.setIsSuccess(isSuccess);
			result.addData("id", m.getId());
		}
		String jsonString = result.toJSON();
		return jsonString;
	}
	/**
	 * 
	 * @description: 添加产品进度
	 * @createTime: 2017年8月29日 上午11:08:54
	 * @author: zjy
	 * @param model
	 * @param m
	 * @return
	 */
	@RequestMapping(value = "createSchedule", method = RequestMethod.POST)
	@ResponseBody
	public Object createSchedule(Model model, Schedule m) {
		ServiceResult result = new ServiceResult(false);
		if (!hasCreatePermission()) {
			result.setMessage("没有添加权限");
		} else {
			EntityWrapper<Schedule> ew = new EntityWrapper<Schedule>();
			ew.eq("schedule_name", m.getScheduleName());
			ew.eq("product_id",m.getDict().getId());
			if (scheduleService.selectCount(ew) > 0) {
				result.setMessage("产品进度名称已存在，请重新输入");
				return result;
			}
			m.setArray(scheduleService.nextArray());
			m.setCreateTime(DateUtil.getNowTimestampStr());
			m.setCreatorId(OperateUtils.getCurrentUserId());
			m.setLastModifyTime(DateUtil.getNowTimestampStr());
			m.setLastModifierId(OperateUtils.getCurrentUserId());
			boolean isSuccess = scheduleService.insert(m);
			scheduleService.addScheduleToOrder(m.getDict().getId(), m.getId());
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
	public Object update(Model model, Dict m) {
		ServiceResult result = new ServiceResult(false);
		if (!hasUpdatePermission()) {
			result.setMessage("没有修改权限");
		} else {

			Dict oldM = dictService.selectById(m.getId());
			if (!oldM.getName().equals(m.getName())) {
				EntityWrapper<Dict> wrapper = new EntityWrapper<>();
				wrapper.eq("dict_type", DictType.product);
				wrapper.eq("name", m.getName());
				if (dictService.selectCount(wrapper) > 0) {
					result.setMessage("产品名称已存在，请重新输入");
					return result;
				}
			}
			oldM.setCode(m.getCode());
			oldM.setName(m.getName());
			oldM.setStatus(m.getStatus());
			oldM.setNote(m.getNote());
			oldM.setLastModifyTime(DateUtil.getNowTimestampStr());
			oldM.setLastModifierId(OperateUtils.getCurrentUserId());
			boolean isSuccess = dictService.updateAllColumnById(oldM);
			result.setIsSuccess(isSuccess);
		}

		String jsonString = result.toJSON();
		return jsonString;
	}
	/**
	 * 
	 * @description: 修改产品进度
	 * @createTime: 2017年8月29日 上午11:08:45
	 * @author: zjy
	 * @param model
	 * @param m
	 * @return
	 */
	@RequestMapping(value = "updateSchedule", method = RequestMethod.POST)
	@ResponseBody
	public Object updateSchedule(Model model, Schedule m) {
		ServiceResult result = new ServiceResult(false);
		if (!hasUpdatePermission()) {
			result.setMessage("没有修改权限");
		} else {

			Schedule oldM = scheduleService.selectById(m.getId());
			if (!oldM.getScheduleName().equals(m.getScheduleName())) {
				EntityWrapper<Schedule> ew = new EntityWrapper<>();
				ew.eq("schedule_name", m.getScheduleName());
				ew.eq("product_id",m.getDict().getId());
				if (scheduleService.selectCount(ew) > 0) {
					result.setMessage("产品进度名称已存在，请重新输入");
					return result;
				}
			}
			oldM.setScheduleName(m.getScheduleName());
			oldM.setNote(m.getNote());
			oldM.setRemind(m.getRemind());
			oldM.setRemindUnit(m.getRemindUnit());
			oldM.setRole(m.getRole());
			oldM.setDuration(m.getDuration());
			oldM.setLastModifyTime(DateUtil.getNowTimestampStr());
			oldM.setLastModifierId(OperateUtils.getCurrentUserId());
			boolean isSuccess = scheduleService.updateAllColumnById(oldM);
			result.setIsSuccess(isSuccess);
		}

		String jsonString = result.toJSON();
		return jsonString;
	}
	/**
	 * 
	 * @description: 删除
	 * @createTime: 2017年8月29日 上午11:09:29
	 * @author: zjy
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
				boolean isSuccess = dictService.deleteBatchIds(Arrays.asList(idArray));
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
	 * @description: 删除产品进度
	 * @createTime: 2017年8月29日 上午11:09:29
	 * @author: zjy
	 * @param model
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "mulDeleteSchedule", method = RequestMethod.POST)
	@ResponseBody
	public Object mulDeleteSchedule(Model model, String ids) {
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
				boolean isSuccess = scheduleService.deleteBatchIds(Arrays.asList(idArray));
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
	public Object listByPage(Model model, Dict m, int rows, int page) {
		if (!hasViewPermission()) {
			return JSONUtil.EMPTYJSON;
		}
		Page<Dict> pageM = new Page<>(page, rows);
		EntityWrapper<Dict> ew = new EntityWrapper<>(m);
		ew.eq("dict_type", DictType.product);
		ew.orderBy("array", true);
		if (!StringUtils.isEmpty(m.getName())) {
			ew.like("name", m.getName());
		}
		if (!StringUtils.isEmpty(m.getCode())) {
			ew.like("code", m.getCode());
		}
		m.setName(null);
		m.setCode(null);
		pageM = dictService.selectPage(pageM, ew);
		String[] properties = { "dictType", "code", "name","status", "note", "isSys", "array", "id" };
		String jsonString = JSONUtil.toJson(pageM.getRecords(), properties, (long) pageM.getTotal());
		return jsonString;
	}
	/**
	 * 
	 * @description: 分页查询产品进度设置
	 * @createTime: 2017年8月29日 上午11:09:37
	 * @author: zjy
	 * @param model
	 * @param m
	 * @param rows
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "listScheduleByPage")
	public Object listScheduleByPage(Model model, Schedule m,long productId, int rows, int page) {
		if (!hasViewPermission()) {
			return JSONUtil.EMPTYJSON;
		}
		Page<Schedule> pageM = new Page<>(page, rows);
		EntityWrapper<Schedule> ew = new EntityWrapper<>(m);
		ew.orderBy("array", true);
		ew.eq("product_id",productId);
		m.setScheduleName(null);
		pageM = scheduleService.selectPage(pageM,ew);
		String[] properties = {"dict.id:productId","scheduleName", "note", "array", "id", "role", "duration", "remind", "remindUnit"};
		String jsonString = JSONUtil.toJson(pageM.getRecords(), properties, (long) pageM.getTotal());
		return jsonString;
	}
	/**
	 * 
	 * @description: 上移下移
	 * @createTime: 2017年8月29日 上午11:09:45
	 * @author: zjy
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
		if (!hasPermission("dict:product:up") && !hasPermission("dict:product:down")) {
			result.setMessage("没有移动权限");
			return result;
		}
		result = dictService.changeArray(srcId, destId);
		return result;
	}
	/**
	 * 
	 * @description: 上移下移产品进度
	 * @createTime: 2017年8月29日 上午11:09:45
	 * @author: zjy
	 * @param model
	 * @param srcId
	 * @param destId
	 * @return
	 */
	@RequestMapping(value = "/changeArraySchedule", method = RequestMethod.POST)
	@ResponseBody
	public Object changeArraySchedule(Model model, @RequestParam(required = false) Long srcId,
			@RequestParam(required = false) Long destId) {
		ServiceResult result = new ServiceResult(false);
		if (!hasPermission("dict:product:upper") && !hasPermission("dict:product:below")) {
			result.setMessage("没有移动权限");
			return result;
		}
		result = scheduleService.changeArray(srcId, destId);
		return result;
	}
	
}

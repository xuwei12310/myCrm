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
import com.xuwei.bean.AssessmentCompany;
import com.xuwei.service.IAssessmentCompanyService;
import com.xuwei.util.DateUtil;
import com.xuwei.util.JSONUtil;
import com.xuwei.util.OperateUtils;
import com.xuwei.util.ServiceResult;
import com.xuwei.util.StringUtil;

/**
 * <p>
 * 评估公司 前端控制器
 * </p>
 *
 * @author徐威
 * @since 2017-08-31
 */
@Controller
@RequestMapping("/dict/assessmentCompany")
public class AssessmentCompanyController extends BaseController<AssessmentCompany> {

	@Resource
	private IAssessmentCompanyService assessmentCompanyService;

	public AssessmentCompanyController() {
		setResourceIdentity("dict:assessmentCompany");
	}

	/**
	 * 
	 * @description:主页面
	 * @createTime 2017年8月30日 上午10:36:19
	 * @author xw
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "main")
	public String Main(Model mode) {
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
	public Object create(Model model, AssessmentCompany m) {
		ServiceResult result = new ServiceResult(false);
		if (!hasPermissionByOpt("create")) {
			result.setMessage("没有添加权限");
		} else {
			EntityWrapper<AssessmentCompany> ew = new EntityWrapper<AssessmentCompany>();
			ew.eq("name", m.getName());
			if (assessmentCompanyService.selectCount(ew) > 0) {
				result.setMessage("公司名称已存在，请重新输入");
				return result;
			}
			m.setArray(assessmentCompanyService.nextArray());
			m.setIsSys(0);
			m.setCreateTime(DateUtil.getNowTimestampStr());
			m.setCreatorId(OperateUtils.getCurrentUserId());
			m.setLastModifyTime(DateUtil.getNowTimestampStr());
			m.setLastModifierId(OperateUtils.getCurrentUserId());
			boolean isSuccess = assessmentCompanyService.insert(m);
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
	public Object update(Model model, AssessmentCompany m) {
		ServiceResult result = new ServiceResult(false);
		if (!hasUpdatePermission()) {
			result.setMessage("没有修改权限");
		} else {

			AssessmentCompany oldM = assessmentCompanyService.selectById(m.getId());
			if (!oldM.getName().equals(m.getName())) {
				Wrapper<AssessmentCompany> wrapper = new EntityWrapper<>();
				wrapper.eq("name", m.getName());
				if (assessmentCompanyService.selectCount(wrapper) > 0) {
					result.setMessage("公司名称已存在，请重新输入");
					return result;
				}
			}
			oldM.setName(m.getName());
			oldM.setAddress(m.getAddress());
			oldM.setContacts(m.getContacts());
			oldM.setTelephone(m.getTelephone());
			oldM.setNote(m.getNote());
			// oldM.setArray(m.getArray());
			oldM.setStatus(m.getStatus());
			oldM.setCreateTime(DateUtil.getNowTimestampStr());
			oldM.setCreatorId(OperateUtils.getCurrentUserId());
			oldM.setLastModifyTime(DateUtil.getNowTimestampStr());
			oldM.setLastModifierId(OperateUtils.getCurrentUserId());
			boolean isSuccess = assessmentCompanyService.updateAllColumnById(oldM);
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
				boolean isSuccess = assessmentCompanyService.deleteBatchIds(Arrays.asList(idArray));
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
	public Object listByPage(Model model, AssessmentCompany m, int rows, int page) {
		if (!hasViewPermission()) {
			return JSONUtil.EMPTYJSON;
		}
		Page<AssessmentCompany> pageM = new Page<>(page, rows);
		EntityWrapper<AssessmentCompany> ew = new EntityWrapper<>(m);
		ew.orderBy("array", true);
		if (!StringUtils.isEmpty(m.getName())) {
			ew.like("name", m.getName());
		}
		if(m.getStatus()!=null){
			ew.eq("status", m.getStatus());
		}
		m.setName(null);
		pageM = assessmentCompanyService.selectPage(pageM, ew);
		String[] properties = { "name", "address","contacts","telephone","status", "note", "isSys", "array", "id" };
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
		if (!hasPermission("dict:assessmentCompany:up") || !hasPermission("dict:assessmentCompany:down")) {
			result.setMessage("没有移动权限");
			return result;
		}
		result = assessmentCompanyService.changeArray(srcId, destId);
		return result;
	}
	
	@RequestMapping("listAssessmentCompanyByOrder")
	@ResponseBody
	public Object listAssessmentCompanyByOrder(Long orderId,int rows,int page){
		Page<AssessmentCompany> pageM=new Page<AssessmentCompany>(page, rows);
		pageM=assessmentCompanyService.listAssessmentCompanyByOrder(pageM,orderId);
		String[] properties = { "name", "address","contacts","telephone","status", "note", "isSys", "array", "id" };
		String jsonString = JSONUtil.toJson(pageM.getRecords(), properties, (long) pageM.getTotal());
		return jsonString;
	}
}

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
import com.xuwei.mapper.DictMapper;
import com.xuwei.service.IDictService;
import com.xuwei.util.DateUtil;
import com.xuwei.util.JSONUtil;
import com.xuwei.util.OperateUtils;
import com.xuwei.util.ServiceResult;
import com.xuwei.util.StringUtil;

/**
 * 
 * @description:基础字典-土地性质 
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年8月31日下午2:46:19
 * @author：zjy
 * @version：1.0
 */
@Controller
@RequestMapping("/dict/landNature")
public class LandNatureController extends BaseController<Dict> {
	@Resource
	private IDictService dictService;
	@Resource
	private DictMapper dictMapper;

	public LandNatureController() {
		setResourceIdentity("dict:landNature");
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
			ew.eq("dict_type", DictType.landNature);
			if (dictService.selectCount(ew) > 0) {
				result.setMessage("名称已存在，请重新输入");
				return result;
			}
			m.setArray(dictService.nextArray(DictType.landNature));
			m.setIsSys(0);
			m.setDictType(DictType.landNature.toString());
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
	 * @description: 修改
	 * @createTime: 2017年8月29日 上午11:08:45
	 * @author: zjy
	 * @param model
	 * @param m
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	 public Object update(Model model,Dict m){
        ServiceResult result = new ServiceResult(false);
        if(!hasUpdatePermission()){
            result.setMessage("没有修改权限");
        }else{
         
           Dict oldM = dictService.selectById(m.getId());
           if(!oldM.getName().equals(m.getName())){
       		 EntityWrapper<Dict> wrapper = new EntityWrapper<>();
           	 wrapper.eq("dict_type",DictType.landNature);
           	 wrapper.eq("name",m.getName());
       		 if(dictService.selectCount(wrapper)>0){
             	  result.setMessage("名称已存在，请重新输入");
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
		ew.eq("dict_type", DictType.landNature);
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
		String[] properties = { "dictType", "code", "name", "status", "note", "isSys", "array", "id" };
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
		if (!hasPermission("dict:landNature:up") && !hasPermission("dict:landNature:down")) {
			result.setMessage("没有移动权限");
			return result;
		}
		result = dictService.changeArray(srcId, destId);
		return result;
	}
}

package com.xuwei.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.xuwei.bean.Resources;
import com.xuwei.service.IResourcesService;
import com.xuwei.util.EasyuiUtilResources;
import com.xuwei.util.JSONUtil;
import com.xuwei.util.ServiceResult;

/**
 * @description: 资源控制层
 * @copyright: 福建骏华信息有限公司 (c)2016
 * @createTime: 2016年8月12日下午10:30:25
 * @author：lys
 * @version：1.0
 */
@Controller
@RequestMapping(value = "/sys/resources")
public class ResourcesController extends BaseTreeController<IResourcesService, Resources> {

    @Resource
    private IResourcesService resourcesService;

	EasyuiUtilResources easyUIUtil = new EasyuiUtilResources();

    public ResourcesController(){
    	setResourceIdentity("sys:resources");
    }

	/**
	 * @description: 转向主界面
	 * @createTime: 2016年8月24日 下午3:07:47
	 * @author: lys
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "main",method = RequestMethod.GET)
	public String main(Model model){
		return defaultViewPrefix();
	}
	/**
	 * @description: 树跟节点
	 * @createTime: 2016年8月24日 下午3:08:00
	 * @author: lys
	 * @return
	 */
	@RequestMapping(value = "/treeRoot")
	@ResponseBody
	public Object treeRoot() {
		List<Resources> list = this.resourcesService.treeRoot();
		return easyUIUtil.convertToEasyuiTreeList(list);
	}
	/**
	 * @description: 树子节点列表
	 * @createTime: 2016年8月24日 下午3:08:13
	 * @author: lys
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/treeChildren")
	@ResponseBody
	public Object treeChildren(Long id) {
		List<Resources> list = resourcesService.treeChildren(id);
		return easyUIUtil.convertToEasyuiTreeList(list);
	}
	/**
	 * @description: 树
	 * @createTime: 2016年8月24日 下午3:08:32
	 * @author: lys
	 * @return
	 */
	@RequestMapping(value = "/tree")
	@ResponseBody
	public Object tree() {
		List<Resources> list = this.resourcesService.tree();
		return easyUIUtil.convertToEasyuiTreeList(list);
	}
	/**
	 * @description: 列表查询子节点
	 * @createTime: 2016年8月24日 下午3:08:46
	 * @author: lys
	 * @param model
	 * @param m
	 * @param page
	 * @return
	 */
	@RequestMapping(value = { "/findChildren" })
	@ResponseBody
	public Object findChildren(ModelMap model, Resources m, int rows, int page,@RequestParam(required=false) Long parentId) {
		Page<Resources> pageM= new Page<>(page,rows);
		m.setId(parentId);
		resourcesService.findChildrenByPage(m,pageM);
		String[] properties={"id","name","identity","url","icon","resourcesType","status"};
		String jsonString = JSONUtil.toJson(pageM.getRecords(), properties,(long)pageM.getTotal());
		return jsonString;
	}
	/**
	 * @description: 添加
	 * @createTime: 2016年8月24日 下午3:09:15
	 * @author: lys
	 * @param model
	 * @param m
	 * @return
	 */
	@RequestMapping(value = "create",method = RequestMethod.POST)
	@ResponseBody
	public Object create(Model model,Resources m){
		ServiceResult result = new ServiceResult(false);
		try {
			result = resourcesService.create(m);
		} catch (Exception e) {
			result.setMessage("添加出错："+e.getMessage());
		}
		String jsonString = result.toJSON();
		return jsonString;
	}
	/**
	 * @description: 修改
	 * @createTime: 2016年8月24日 下午3:09:24
	 * @author: lys
	 * @param model
	 * @param m
	 * @return
	 */
	@RequestMapping(value = "update",method = RequestMethod.POST)
	@ResponseBody
	public Object update(Model model,Resources m){
		ServiceResult result = new ServiceResult(false);
		try {
			result = resourcesService.update(m);
		} catch (Exception e) {
			result.setMessage("修改出错："+e.getMessage());
		}
		String jsonString = result.toJSON();
		return jsonString;
	}
	/**
	 * @description: 批量删除
	 * @createTime: 2016年8月24日 下午3:09:32
	 * @author: lys
	 * @param model
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "mulDelete",method = RequestMethod.POST)
	@ResponseBody
	public Object mulDelete(Model model,String ids){
		ServiceResult result = new ServiceResult(false);
		try {
			result = resourcesService.mulDelete(ids);
		} catch (Exception e) {
			if(e instanceof org.springframework.dao.DataIntegrityViolationException){
				result.setMessage("数据已被引用不能删除");
			}else{
				result.setMessage("删除出错："+e.getMessage());
			}
		}
		String jsonString = result.toJSON();
		return jsonString;
	}

	@RequestMapping(value = "move", method = RequestMethod.POST)
	@ResponseBody
	public Object move(
			HttpServletRequest request,
			@RequestParam("sourceId") Long sourceId,
			@RequestParam("targetId") Long targetId,
			@RequestParam("point") String point,
			Model model) {

		return resourcesService.move(sourceId, targetId, point);

	}



}

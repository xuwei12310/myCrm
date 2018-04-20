package com.xuwei.controller;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xuwei.bean.Plot;
import com.xuwei.service.IPlotService;
import com.xuwei.util.JSONUtil;
import com.xuwei.util.ServiceResult;
import com.xuwei.util.StringUtil;


/**
 * @description: 字典小区控制器
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年08月29日 10:52:54
 * @author: wwh
 * @version: 1.0
 */
@Controller
@RequestMapping("/dict/plot")
public class PlotController extends BaseController<Plot> {
    @Resource
    private IPlotService plotService;

    public PlotController(){
        setResourceIdentity("dict:plot");
    }

    /**
    * @description: 转向模块主界面
    * @createTime: 2017年08月29日 10:52:54
    * @author: wwh
    * @param model
    * @return
    */
    @RequestMapping(value = "main",method = RequestMethod.GET)
    public String main(Model model){
        return defaultViewPrefix();
    }

    /**
    * @description: 添加
    * @createTime: 2017年08月29日 10:52:54
    * @author: wwh
    * @param model
    * @param m
    * @return
    */
    @RequestMapping(value = "create",method = RequestMethod.POST)
    @ResponseBody
    public Object create(Model model,Plot m){
        ServiceResult result = new ServiceResult(false);
        if(!hasCreatePermission()){
            result.setMessage("没有添加权限");
        }else{
        	EntityWrapper<Plot> ew = new EntityWrapper<Plot>();
        	ew.eq("plot_name", m.getPlotName());
        	ew.eq("area_id", m.getArea().getId());
        	List<Plot> list  = plotService.selectList(ew);
        	if(list != null && !list.isEmpty()){
        		result.setMessage("该小区已经添加过！不能重复添加");
        	}else{
        		 Integer maxArryIndex =  plotService.maxArrayIndex();
        		 m.setArray(maxArryIndex +1);
            boolean isSuccess = plotService.insert(m);
            result.setIsSuccess(isSuccess);
        	}
        }
        String jsonString = result.toJSON();
        return jsonString;
    }

    /**
    * @description: 修改
    * @createTime: 2017年08月29日 10:52:54
    * @author: wwh
    * @param model
    * @param m
    * @return
    */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public Object update(Model model,Plot m){
        ServiceResult result = new ServiceResult(false);
        if(!hasUpdatePermission()){
            result.setMessage("没有修改权限");
        }else{
        	Plot plot = plotService.selectById(m.getId());
        	plot.setPlotName(m.getPlotName());
        	plot.setArea(m.getArea());
        	plot.setNote(m.getNote());
        	plot.setStatus(m.getStatus());
            boolean isSuccess = plotService.updateAllColumnById(plot);
            result.setIsSuccess(isSuccess);
        }
        String jsonString = result.toJSON();
        return jsonString;
    }

    /**
    * @description: 删除
    * @createTime: 2017年08月29日 10:52:54
    * @author: wwh
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
                boolean isSuccess = plotService.deleteBatchIds(Arrays.asList(idArray));
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
    * @description: 分页查询
    * @createTime: 2017年08月29日 10:52:54
    * @author: wwh
    * @param model
    * @param m
    * @param rows
    * @param page
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "listByPage")
    public Object listByPage(Model model, Plot m, int rows, int page){
        if(!hasViewPermission()){
            return  JSONUtil.EMPTYJSON;
        }
        Page<Plot> pageM= new Page<>(page,rows);
        List<Plot> list =  plotService.selectByPage(pageM,m);
        String[] properties = {"area.id:areaId","area.areaName:areaName","plotName","status","id","note"};
        String jsonString = JSONUtil.toJson(list, properties,(long) pageM.getTotal());
        return jsonString;
    }
    
    /**
     * @description: 上移/下移
     * @createTime: 2017年4月6日 上午9:12:24
     * @author: lys
     * @param model
     * @param srcId
     * @param destId
     * @return
     */
    @RequestMapping(value = "/changeArray",method = RequestMethod.POST)
    @ResponseBody
    public Object changeArray(Model model,@RequestParam(required=false)Long srcId,@RequestParam(required=false)Long destId){
    	ServiceResult result = new ServiceResult(false);
		/*if(!hasPermission("dict:province:up")&&!hasPermission("dict:province:down")){
			result.setMessage("没有移动权限");
			return result;
		}*/
		result = plotService.changeArray(srcId,destId);
    	return result;
    }

}

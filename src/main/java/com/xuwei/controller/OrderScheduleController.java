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

import com.baomidou.mybatisplus.plugins.Page;
import com.xuwei.bean.Order;
import com.xuwei.bean.OrderSchedule;
import com.xuwei.service.IOrderScheduleService;
import com.xuwei.util.DateUtil;
import com.xuwei.util.JSONUtil;
import com.xuwei.util.OperateUtils;
import com.xuwei.util.ServiceResult;
import com.xuwei.util.StringUtil;

/**
 * @description: 订单_进度控制器
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月18日 17:47:06
 * @author: caw
 * @version: 1.0
 */
@Controller
@RequestMapping("/myWorkbench/orderSchedule")
public class OrderScheduleController extends BaseController<OrderSchedule> {
    @Resource
    private IOrderScheduleService orderScheduleService;

    public OrderScheduleController(){
        setResourceIdentity("myWorkbench:orderSchedule");
    }

    /**
    * @description: 转向模块主界面
    * @createTime: 2017年09月18日 17:47:06
    * @author: caw
    * @param model
    * @return
    */
    @RequestMapping(value = "main",method = RequestMethod.GET)
    public String main(Model model){
        return defaultViewPrefix();
    }

    /**
    * @description: 添加
    * @createTime: 2017年09月18日 17:47:06
    * @author: caw
    * @param m
    * @return
    */
    @RequestMapping(value = "create",method = RequestMethod.POST)
    @ResponseBody
    public Object create(OrderSchedule m){
        ServiceResult result = new ServiceResult(false);
        if(!hasCreatePermission()){
            result.setMessage("没有添加权限");
        }else{
            boolean isSuccess = orderScheduleService.insert(m);
            result.setIsSuccess(isSuccess);
        }
        String jsonString = result.toJSON();
        return jsonString;
    }

    /**
    * @description: 修改
    * @createTime: 2017年09月18日 17:47:06
    * @author: caw
    * @param m
    * @return
    */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public Object update(OrderSchedule m){
        ServiceResult result = new ServiceResult(false);
        if(!hasUpdatePermission()){
            result.setMessage("没有修改权限");
        }else{
        	OrderSchedule orderSchedule = orderScheduleService.selectById(m.getId());
        	orderSchedule.setIsComplete(m.getIsComplete());
        	orderSchedule.setActualDate(m.getActualDate());
        	orderSchedule.setLastModifierId(OperateUtils.getCurrentUserId());
        	orderSchedule.setLastModifyTime(DateUtil.getNowTimestampStr());
            boolean isSuccess = orderScheduleService.updateAllColumnById(orderSchedule);
            result.setIsSuccess(isSuccess);
        }
        String jsonString = result.toJSON();
        return jsonString;
    }

    /**
    * @description: 删除
    * @createTime: 2017年09月18日 17:47:06
    * @author: caw
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
                boolean isSuccess = orderScheduleService.deleteBatchIds(Arrays.asList(idArray));
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
     * @description: 分页查询
     * @createTime: 2017年9月20日 下午2:20:26
     * @author: caw
     * @param scheduleDictId
     * @param orderCodes
     * @param customerNames
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "listOrderScheduleByPage")
    public Object listOrderScheduleByPage(int rows, int page,Long scheduleDictId,String orderCodes,String customerNames){
    	int viewType = 0;
        if(!hasPermission("myWorkbench:order:viewall")){
            if(hasPermission("myWorkbench:order:viewdepartment")){
            	viewType = 1;
            }else if(hasPermission("myWorkbench:order:view")){
            	viewType = 2;
            }else{
                return  JSONUtil.EMPTYJSON;
            }
        }
    	Page<Order> pageM= new Page<>(page,rows);
    	List<Map<String,Object>> listMap = orderScheduleService.listOrderScheduleByPage(pageM,scheduleDictId, orderCodes, customerNames, viewType);
    	return JSONUtil.toJsonFromListMap(listMap,(long) pageM.getTotal());
    }
    
    /**
     * 
     * @description: 获取自定义列
     * @createTime: 2017年9月20日 下午2:20:59
     * @author: caw
     * @param productType
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getColumns",method = RequestMethod.POST)
    public Object getColumns(Long productType){
    	ServiceResult result = new ServiceResult(false);
    	result = orderScheduleService.getColumns(productType);
    	String jsonString = result.toJSON();
        return jsonString;
    }
    
    /**
     * 
     * @description: 获取进度
     * @createTime: 2017年9月20日 下午2:33:14
     * @author: caw
     * @param productType
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getSchedule",method = RequestMethod.POST)
    public Object getSchedule(Long productType){
    	ServiceResult result = new ServiceResult(false);
    	result = orderScheduleService.getSchedule(productType);
    	String jsonString = result.toJSON();
        return jsonString;
    }
    
    /**
     * 
     * @description: 获取订单进度信息
     * @createTime: 2017年9月20日 下午2:45:24
     * @author: caw
     * @param orderid
     * @param scheduleDate
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getOrderScheduleInfo",method = RequestMethod.POST)
    public Object getOrderScheduleInfo(Long orderid, String scheduleDate){
    	ServiceResult result = new ServiceResult(false);
    	result = orderScheduleService.getOrderScheduleInfo(orderid, scheduleDate);
    	String jsonString = result.toJSON();
        return jsonString;
    }
}

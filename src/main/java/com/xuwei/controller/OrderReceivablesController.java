package com.xuwei.controller;

import java.util.Arrays;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xuwei.bean.OrderReceivables;
import com.xuwei.service.IOrderReceivablesService;
import com.xuwei.util.DateUtil;
import com.xuwei.util.JSONUtil;
import com.xuwei.util.OperateUtils;
import com.xuwei.util.ServiceResult;
import com.xuwei.util.StringUtil;


/**
 * @description: 订单_收款账号控制器
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月15日 15:42:31
 * @author: hhd
 * @version: 1.0
 */
@Controller
@RequestMapping("/myWorkbench/orderReceivables")
public class OrderReceivablesController extends BaseController<OrderReceivables> {
    @Resource
    private IOrderReceivablesService orderReceivablesService;

    public OrderReceivablesController(){
        setResourceIdentity("sys:orderReceivables");
    }

    /**
    * @description: 添加
    * @createTime: 2017年09月15日 15:42:31
    * @author: hhd
    * @param m
    * @return
    */
    @RequestMapping(value = "create",method = RequestMethod.POST)
    @ResponseBody
    public Object create(OrderReceivables m){
        ServiceResult result = new ServiceResult(false);
        m.setCreatorId(OperateUtils.getCurrentUserId());
        m.setCreateTime(DateUtil.getNowTimestampStr());
        m.setLastModifierId(OperateUtils.getCurrentUserId());
        m.setLastModifyTime(DateUtil.getNowTimestampStr());
        boolean isSuccess = orderReceivablesService.insert(m);
        result.setIsSuccess(isSuccess);
        String jsonString = result.toJSON();
        return jsonString;
    }

    /**
    * @description: 修改
    * @createTime: 2017年09月15日 15:42:31
    * @author: hhd
    * @param m
    * @return
    */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public Object update(OrderReceivables m){
        ServiceResult result = new ServiceResult(false);
        OrderReceivables orderReceivables = orderReceivablesService.selectById(m.getId());
        orderReceivables.setAccountBank(m.getAccountBank());
        orderReceivables.setAccountName(m.getAccountName());
        orderReceivables.setAccountNumber(m.getAccountNumber());
        orderReceivables.setAccountType(m.getAccountType());
        orderReceivables.setReason(m.getReason());
        orderReceivables.setLastModifierId(OperateUtils.getCurrentUserId());
        orderReceivables.setLastModifyTime(DateUtil.getNowTimestampStr());
        boolean isSuccess = orderReceivablesService.updateAllColumnById(orderReceivables);
        result.setIsSuccess(isSuccess);
        String jsonString = result.toJSON();
        return jsonString;
    }

    /**
    * @description: 删除
    * @createTime: 2017年09月15日 15:42:31
    * @author: hhd
    * @param ids
    * @return
    */
    @RequestMapping(value = "mulDelete",method = RequestMethod.POST)
    @ResponseBody
    public Object mulDelete(String ids){
        ServiceResult result = new ServiceResult(false);
        try {
            String[] idArray = StringUtil.split(ids);
            if(idArray==null||idArray.length==0){
                result.setMessage("请选择要删除的数据行");
                return result;
            }
            boolean isSuccess = orderReceivablesService.deleteBatchIds(Arrays.asList(idArray));
            result.setIsSuccess(isSuccess);
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

    /**
    * @description: 分页查询
    * @createTime: 2017年09月15日 15:42:31
    * @author: hhd
    * @param m
    * @param rows
    * @param page
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "listByPage")
    public Object listByPage(OrderReceivables m, int rows, int page){
        if(m==null || m.getOrder()==null||m.getOrder().getId()==null){
            return JSONUtil.EMPTYJSON;
        }
        Page<OrderReceivables> pageM= new Page<>(page,rows);
        EntityWrapper<OrderReceivables> ew = new EntityWrapper<>(m);
        pageM = orderReceivablesService.selectPage(pageM,ew);
        String[] properties = {"accountType","accountName","accountBank","accountNumber","reason","id"};
        String jsonString = JSONUtil.toJson(pageM.getRecords(), properties,(long) pageM.getTotal());
        return jsonString;
    }

}

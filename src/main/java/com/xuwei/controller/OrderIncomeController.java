package com.xuwei.controller;

import java.util.Arrays;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xuwei.bean.OrderIncome;
import com.xuwei.service.IOrderIncomeService;
import com.xuwei.util.JSONUtil;
import com.xuwei.util.ServiceResult;
import com.xuwei.util.StringUtil;


/**
 * @description: 订单_收入控制器
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月18日 11:12:28
 * @author: xw
 * @version: 1.0
 */
@Controller
@RequestMapping("/orderIncome")
public class OrderIncomeController extends BaseController<OrderIncome> {
    @Resource
    private IOrderIncomeService orderIncomeService;

    public OrderIncomeController(){
        setResourceIdentity("sys:orderIncome");
    }

    /**
    * @description: 转向模块主界面
    * @createTime: 2017年09月18日 11:12:28
    * @author: xw
    * @param model
    * @return
    */
    @RequestMapping(value = "main",method = RequestMethod.GET)
    public String main(Model model){
        return defaultViewPrefix();
    }

    /**
    * @description: 添加
    * @createTime: 2017年09月18日 11:12:28
    * @author: xw
    * @param m
    * @return
    */
    @RequestMapping(value = "create",method = RequestMethod.POST)
    @ResponseBody
    public Object create(OrderIncome m){
        ServiceResult result = new ServiceResult(false);
        if(!hasCreatePermission()){
            result.setMessage("没有添加权限");
        }else{
            boolean isSuccess = orderIncomeService.insert(m);
            result.setIsSuccess(isSuccess);
        }
        String jsonString = result.toJSON();
        return jsonString;
    }

    /**
    * @description: 修改
    * @createTime: 2017年09月18日 11:12:28
    * @author: xw
    * @param m
    * @return
    */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public Object update(OrderIncome m){
        ServiceResult result = new ServiceResult(false);
        if(!hasUpdatePermission()){
            result.setMessage("没有修改权限");
        }else{
            boolean isSuccess = orderIncomeService.updateAllColumnById(m);
            result.setIsSuccess(isSuccess);
        }
        String jsonString = result.toJSON();
        return jsonString;
    }

    /**
    * @description: 删除
    * @createTime: 2017年09月18日 11:12:28
    * @author: xw
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
                boolean isSuccess = orderIncomeService.deleteBatchIds(Arrays.asList(idArray));
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
    * @createTime: 2017年09月18日 11:12:28
    * @author: xw
    * @param m
    * @param rows
    * @param page
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "listByPage")
    public Object listByPage(OrderIncome m, int rows, int page){
        if(!hasViewPermission()){
            return  JSONUtil.EMPTYJSON;
        }
        Page<OrderIncome> pageM= new Page<>(page,rows);
        EntityWrapper<OrderIncome> ew = new EntityWrapper<>(m);
        pageM = orderIncomeService.selectPage(pageM,ew);
        String[] properties = {"orderId","costTypeId","estimateAmount","receiveAmount","receivedAmount","receivingAmount","id"};
        String jsonString = JSONUtil.toJson(pageM.getRecords(), properties,(long) pageM.getTotal());
        return jsonString;
    }

}

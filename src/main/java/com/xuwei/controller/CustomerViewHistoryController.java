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
import com.xuwei.bean.CustomerViewHistory;
import com.xuwei.service.ICustomerViewHistoryService;
import com.xuwei.util.JSONUtil;
import com.xuwei.util.ServiceResult;
import com.xuwei.util.StringUtil;


/**
 * @description: 客户_浏览历史控制器
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月06日 09:12:17
 * @author: caw
 * @version: 1.0
 */
@Controller
@RequestMapping("/myWorkbench/customerViewHistory")
public class CustomerViewHistoryController extends BaseController<CustomerViewHistory> {
    @Resource
    private ICustomerViewHistoryService customerViewHistoryService;

    public CustomerViewHistoryController(){
        setResourceIdentity("myWorkbench:customerViewHistory");
    }

    /**
    * @description: 转向模块主界面
    * @createTime: 2017年09月06日 09:12:17
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
    * @createTime: 2017年09月06日 09:12:17
    * @author: caw
    * @param model
    * @param m
    * @return
    */
    @RequestMapping(value = "create",method = RequestMethod.POST)
    @ResponseBody
    public Object create(Model model,CustomerViewHistory m, Long customerId){
        ServiceResult result = new ServiceResult(false);
        result = customerViewHistoryService.createCustomerViewHistory(m,customerId);
        String jsonString = result.toJSON();
        return jsonString;
    }

    /**
    * @description: 修改
    * @createTime: 2017年09月06日 09:12:17
    * @author: caw
    * @param model
    * @param m
    * @return
    */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public Object update(Model model,CustomerViewHistory m){
        ServiceResult result = new ServiceResult(false);
        boolean isSuccess = customerViewHistoryService.updateAllColumnById(m);
        result.setIsSuccess(isSuccess);
        String jsonString = result.toJSON();
        return jsonString;
    }

    /**
    * @description: 删除
    * @createTime: 2017年09月06日 09:12:17
    * @author: caw
    * @param model
    * @param ids
    * @return
    */
    @RequestMapping(value = "mulDelete",method = RequestMethod.POST)
    @ResponseBody
    public Object mulDelete(Model model,String ids){
        ServiceResult result = new ServiceResult(false);
        try {
            String[] idArray = StringUtil.split(ids);
            if(idArray==null||idArray.length==0){
                result.setMessage("请选择要删除的数据行");
                return result;
            }
            boolean isSuccess = customerViewHistoryService.deleteBatchIds(Arrays.asList(idArray));
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
    * @createTime: 2017年09月06日 09:12:17
    * @author: caw
    * @param model
    * @param m
    * @param rows
    * @param page
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "listByPage")
    public Object listByPage(Model model, CustomerViewHistory m, int rows, int page){
        Page<CustomerViewHistory> pageM= new Page<>(page,rows);
        List<CustomerViewHistory> list = customerViewHistoryService.listCustomerViewHistoryByPage(pageM,m);
        String[] properties = {"customer.id","customer.customerName:customerName","viewUser.id","viewUser.name:viewUserName","viewTime","id"};
        String jsonString = JSONUtil.toJson(list, properties,(long) pageM.getTotal());
        return jsonString;
    }

}

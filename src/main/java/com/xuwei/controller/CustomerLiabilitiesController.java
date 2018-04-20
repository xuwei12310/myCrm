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
import com.xuwei.bean.CustomerLiabilities;
import com.xuwei.service.ICustomerLiabilitiesService;
import com.xuwei.util.JSONUtil;
import com.xuwei.util.ServiceResult;
import com.xuwei.util.StringUtil;


/**
 * @description: 客户_负债控制器
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月04日 09:44:17
 * @author: caw
 * @version: 1.0
 */
@Controller
@RequestMapping("/myWorkbench/customerLiabilities")
public class CustomerLiabilitiesController extends BaseController<CustomerLiabilities> {
    @Resource
    private ICustomerLiabilitiesService customerLiabilitiesService;

    public CustomerLiabilitiesController(){
        setResourceIdentity("myWorkbench:customerLiabilities");
    }

    /**
    * @description: 转向模块主界面
    * @createTime: 2017年09月04日 09:44:17
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
    * @createTime: 2017年09月04日 09:44:17
    * @author: caw
    * @param model
    * @param m
    * @return
    */
    @RequestMapping(value = "create",method = RequestMethod.POST)
    @ResponseBody
    public Object create(Model model,CustomerLiabilities m){
        ServiceResult result = new ServiceResult(false);
        if(m.getValue().intValue()<0){
			result.setMessage("金额不能为负数");
		}else{
			boolean isSuccess = customerLiabilitiesService.insert(m);
	        result.setIsSuccess(isSuccess);
		}
        String jsonString = result.toJSON();
        return jsonString;
    }

    /**
    * @description: 修改
    * @createTime: 2017年09月04日 09:44:17
    * @author: caw
    * @param model
    * @param m
    * @return
    */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public Object update(Model model,CustomerLiabilities m){
        ServiceResult result = new ServiceResult(false);
        if(m.getValue().intValue()<0){
			result.setMessage("金额不能为负数");
		}else{
			boolean isSuccess = customerLiabilitiesService.updateAllColumnById(m);
	        result.setIsSuccess(isSuccess);
		}
        String jsonString = result.toJSON();
        return jsonString;
    }

    /**
    * @description: 删除
    * @createTime: 2017年09月04日 09:44:17
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
            boolean isSuccess = customerLiabilitiesService.deleteBatchIds(Arrays.asList(idArray));
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
    * @createTime: 2017年09月04日 09:44:17
    * @author: caw
    * @param model
    * @param m
    * @param rows
    * @param page
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "listByPage")
    public Object listByPage(Model model, CustomerLiabilities m, int rows, int page){
        Page<CustomerLiabilities> pageM= new Page<>(page,rows);
        EntityWrapper<CustomerLiabilities> ew = new EntityWrapper<>(m);
        pageM = customerLiabilitiesService.selectPage(pageM,ew);
        String[] properties = {"customerId","name","value","note","id"};
        String jsonString = JSONUtil.toJson(pageM.getRecords(), properties,(long) pageM.getTotal());
        return jsonString;
    }

}

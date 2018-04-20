package com.xuwei.controller;

import java.util.Arrays;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xuwei.bean.BrokerageRule;
import com.xuwei.service.IBrokerageRuleService;
import com.xuwei.util.JSONUtil;
import com.xuwei.util.ServiceResult;
import com.xuwei.util.StringUtil;


/**
 * @description: 佣金规则控制器
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月25日 19:04:18
 * @author: caw
 * @version: 1.0
 */
@Controller
@RequestMapping("/dict/brokerageRule")
public class BrokerageRuleController extends BaseController<BrokerageRule> {
    @Resource
    private IBrokerageRuleService brokerageRuleService;

    public BrokerageRuleController(){
        setResourceIdentity("dict:brokerageRule");
    }

    /**
    * @description: 转向模块主界面
    * @createTime: 2017年09月25日 19:04:18
    * @author: caw
    * @param model
    * @return
    */
    @RequestMapping(value = "main",method = RequestMethod.GET)
    public String main(Model model){
        return defaultViewPrefix();
    }

    /**
     * 
     * @description: 获取佣金规则第一条数据
     * @createTime: 2017年9月25日 下午7:26:57
     * @author: caw
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "selectById", method = RequestMethod.POST)
	@ResponseBody
    public Object selectById(Model model, HttpServletRequest request) {
    	BrokerageRule brokerageRule = brokerageRuleService.findByLIMITOne();
    	String[] properties = {"id","ruleOwner","ruleFollow","ruleCsPrincipal","ruleCsAssistant","note"};
    	String jsonString = JSONUtil.toJson(brokerageRule, properties);
		return jsonString;
    }
    
    /**
    * @description: 添加
    * @createTime: 2017年09月25日 19:04:18
    * @author: caw
    * @param m
    * @return
    */
    @RequestMapping(value = "create",method = RequestMethod.POST)
    @ResponseBody
    public Object create(BrokerageRule m){
        ServiceResult result = new ServiceResult(false);
        if(!hasCreatePermission()){
            result.setMessage("没有添加权限");
        }else{
            boolean isSuccess = brokerageRuleService.insert(m);
            result.setIsSuccess(isSuccess);
        }
        String jsonString = result.toJSON();
        return jsonString;
    }

    /**
    * @description: 修改
    * @createTime: 2017年09月25日 19:04:18
    * @author: caw
    * @param m
    * @return
    */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public Object update(BrokerageRule m){
        ServiceResult result = new ServiceResult(false);
        if(!hasUpdatePermission()){
            result.setMessage("没有修改权限");
        }else{
            boolean isSuccess = brokerageRuleService.updateAllColumnById(m);
            result.setIsSuccess(isSuccess);
        }
        String jsonString = result.toJSON();
        return jsonString;
    }

    /**
    * @description: 删除
    * @createTime: 2017年09月25日 19:04:18
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
                boolean isSuccess = brokerageRuleService.deleteBatchIds(Arrays.asList(idArray));
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
    * @createTime: 2017年09月25日 19:04:18
    * @author: caw
    * @param m
    * @param rows
    * @param page
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "listByPage")
    public Object listByPage(BrokerageRule m, int rows, int page){
        if(!hasViewPermission()){
            return  JSONUtil.EMPTYJSON;
        }
        Page<BrokerageRule> pageM= new Page<>(page,rows);
        EntityWrapper<BrokerageRule> ew = new EntityWrapper<>(m);
        pageM = brokerageRuleService.selectPage(pageM,ew);
        String[] properties = {"ruleOwner","ruleFollow","ruleCsPrincipal","ruleCsAssistant","id"};
        String jsonString = JSONUtil.toJson(pageM.getRecords(), properties,(long) pageM.getTotal());
        return jsonString;
    }

}

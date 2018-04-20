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
import com.xuwei.bean.Message;
import com.xuwei.service.IMessageService;
import com.xuwei.util.JSONUtil;
import com.xuwei.util.ServiceResult;
import com.xuwei.util.StringUtil;


/**
 * @description: 消息控制器
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月25日 09:27:40
 * @author: caw
 * @version: 1.0
 */
@Controller
@RequestMapping("/myWorkbench/message")
public class MessageController extends BaseController<Message> {
    @Resource
    private IMessageService messageService;

    public MessageController(){
        setResourceIdentity("myWorkbench:message");
    }

    /**
    * @description: 转向模块主界面
    * @createTime: 2017年09月25日 09:27:40
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
    * @createTime: 2017年09月25日 09:27:40
    * @author: caw
    * @param m
    * @return
    */
    @RequestMapping(value = "create",method = RequestMethod.POST)
    @ResponseBody
    public Object create(Message m){
        ServiceResult result = new ServiceResult(false);
        if(!hasCreatePermission()){
            result.setMessage("没有添加权限");
        }else{
            boolean isSuccess = messageService.insert(m);
            result.setIsSuccess(isSuccess);
        }
        String jsonString = result.toJSON();
        return jsonString;
    }

    /**
    * @description: 修改
    * @createTime: 2017年09月25日 09:27:40
    * @author: caw
    * @param m
    * @return
    */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public Object update(Message m){
        ServiceResult result = new ServiceResult(false);
        if(!hasUpdatePermission()){
            result.setMessage("没有修改权限");
        }else{
            boolean isSuccess = messageService.updateAllColumnById(m);
            result.setIsSuccess(isSuccess);
        }
        String jsonString = result.toJSON();
        return jsonString;
    }

    /**
    * @description: 删除
    * @createTime: 2017年09月25日 09:27:40
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
                boolean isSuccess = messageService.deleteBatchIds(Arrays.asList(idArray));
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
    * @createTime: 2017年09月25日 09:27:40
    * @author: caw
    * @param m
    * @param rows
    * @param page
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "listByPage")
    public Object listByPage(Message m, int rows, int page){
        if(!hasViewPermission()){
            return  JSONUtil.EMPTYJSON;
        }
        Page<Message> pageM= new Page<>(page,rows);
        List<Message> list = messageService.getMessageList(pageM, m);
        String[] properties = {"msgType","remindTime","msgContent","userId","todoId","status","id"};
        String jsonString = JSONUtil.toJson(list, properties,(long) pageM.getTotal());
        return jsonString;
    }

    /**
     * 
     * @description: 修改消息状态为已读
     * @createTime: 2017年9月25日 上午9:41:10
     * @author: caw
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "readSetUp")
    public Object readSetUp(String ids){
    	ServiceResult result = new ServiceResult(false);
    	if(!hasUpdatePermission()){
    		result.setMessage("没有修状态权限");
    		return result;
    	}
    	result = messageService.readSetUp(ids);
    	return result;
    }
}

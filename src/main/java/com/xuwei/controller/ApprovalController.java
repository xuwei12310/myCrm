package com.xuwei.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.xuwei.activiti.service.IActivitiProcessService;
import com.xuwei.activiti.utils.ProcessEnum;
import com.xuwei.bean.User;
import com.xuwei.mapper.ActivitiMapper;
import com.xuwei.service.IOrderService;
import com.xuwei.util.JSONUtil;
import com.xuwei.util.OperateUtils;
import com.xuwei.util.ServiceResult;


/**
 * @description: 我的审批控制器
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月05日 09:22:53
 * @author: hhd
 * @version: 1.0
 */
@Controller
@RequestMapping("/myWorkbench/approval")
public class ApprovalController extends BaseController{

    @Resource
    private IActivitiProcessService activitiProcessService;
    @Resource
    private IOrderService orderService;
    @Resource
    private ActivitiMapper activitiMapper;

    public ApprovalController(){
        setResourceIdentity("myWorkbench:approval");
    }


    /**
    * @description: 转向模块主界面
    * @createTime: 2017年09月05日 09:22:53
    * @author: hhd
    * @param model
    * @return
    */
    @RequestMapping(value = "main",method = RequestMethod.GET)
    public String main(Model model){
        return defaultViewPrefix();
    }

    /**
    * @description: 查询待办任务
    * @createTime: 2017年09月05日 09:22:53
    * @author: hhd
    * @param rows
    * @param page
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "todoListByPage")
    public Object todoListByPage(int rows, int page){

        Page<Map<String,Object>> pageM= new Page<>(page,rows);
        List<Map<String,Object>> list = activitiProcessService.queryTodoTask(pageM,null);
        for (Map<String,Object> map:list){
            map.put("url",ProcessEnum.valueOf((String) map.get("procDefKey")).getViewUrl());
            map.put("procDefKey", ProcessEnum.valueOf((String) map.get("procDefKey")).getValue());
        }
        String[] properties = {"procDefKey","applyUser","procInstName", "taskId","createTime","businessKey:id","formKey","url"};
        String jsonString = JSONUtil.toJson(list, properties,(long) pageM.getTotal());
        return jsonString;
    }

    /**
     * @description: 查询我提交的
     * @createTime: 2017年09月05日 09:22:53
     * @author: hhd
     * @param rows
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "submitListByPage")
    public Object submitListByPage(int rows, int page){
        User user = OperateUtils.getCurrentUser();
        Page<Map<String,Object>> pageM= new Page<>(page,rows);
        List<Map<String,Object>> list = activitiMapper.querySubmitTask(pageM,user.getId());
        for (Map<String,Object> map:list){
            map.put("url",ProcessEnum.valueOf((String) map.get("processInstanceKey")).getViewUrl());
            map.put("processInstanceKey", ProcessEnum.valueOf((String) map.get("processInstanceKey")).getValue());
        }
        String[] properties = {"processInstanceKey","processInstanceName","taskName", "action","time","msg","businessKey","applyUser","url"};
        String jsonString = JSONUtil.toJson(list, properties,(long) pageM.getTotal());
        return jsonString;
    }

    /**
     * @description: 查询已审批的
     * @createTime: 2017年09月05日 09:22:53
     * @author: hhd
     * @param rows
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "doneListByPage")
    public Object doneListByPage(int rows, int page){
        Page<Map<String,Object>> pageM= new Page<>(page,rows);
        List<Map<String,Object>> list = activitiProcessService.queryDoneTask(pageM,null);
        for (Map<String,Object> map:list){
            map.put("url",ProcessEnum.valueOf((String) map.get("processInstanceKey")).getViewUrl());
            map.put("processInstanceKey", ProcessEnum.valueOf((String) map.get("processInstanceKey")).getValue());
        }
        String[] properties = {"processInstanceKey","processInstanceName","taskName", "action","time","msg","businessKey","applyUser","url"};
        String jsonString = JSONUtil.toJson(list, properties,(long) pageM.getTotal());
        return jsonString;
    }

    /**
     * @description: 分页查询任务
     * @createTime: 2017年09月05日 09:22:53
     * @author: hhd
     * @param rows
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "commentlistByPage")
    public Object commentListByPage(int rows, int page,String id){
        if(id==null){
            return JSONUtil.EMPTYJSON;
        }
        Page<Map<String,Object>> pageM= new Page<>(page,rows);
        List<Map<String,Object>> list = activitiProcessService.queryCommentsByProcessInstanceId(pageM,id);
        String[] properties = {"taskName","action","userName", "time","msg"};
        String jsonString = JSONUtil.toJson(list, properties,(long) pageM.getTotal());
        return jsonString;
    }

    /**
     * @description: 处理任务
     * @createTime: 2017年09月05日 09:22:53
     * @author: hhd
     * @param taskId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "handleTask")
    public Object handleTask(String taskId,String transition,String comment){
        ServiceResult result = activitiProcessService.completeTask(taskId, transition, comment);
        return result.toJSON();
    }



}

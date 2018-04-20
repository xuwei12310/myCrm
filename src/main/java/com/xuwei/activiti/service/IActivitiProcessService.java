package com.xuwei.activiti.service;

import java.util.List;
import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.task.Task;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.xuwei.activiti.utils.ProcessEnum;
import com.xuwei.util.ServiceResult;

/**
 * Created by hhd47 on 2017.9.7.
 */
public interface IActivitiProcessService <M extends Model>{
    /**
     * @description  完成任务
     * @createTime 2015-12-15 下午12:29:44
     * @author lys
     * @param taskId
     * @param transition
     * @param comment
     */
    ServiceResult completeTask(String taskId, String transition, String comment) ;

    /**
     * @description  转向重新修改
     * @createTime 2015-12-15 下午12:29:29
     * @author lys
     * @param delegateTask
     */
    void toModify(DelegateTask delegateTask);
    /**
     * @description  根据流程实例Id查找批注
     * @createTime 2015-12-15 下午12:29:05
     * @author lys
     * @param processInstanceId
     * @return
     */
    List<Map<String,Object>> queryCommentsByProcessInstanceId(Page page, String processInstanceId);
    /**
     * @description  启动流程实例
     * @createTime 2015-12-15 下午12:28:48
     * @author lys
     * @param m
     * @param processEnum
     */
    void submit(M m, ProcessEnum processEnum);
    /**
     * @description  启动流程实例
     * @createTime 2015-12-15 下午12:28:48
     * @author lys
     * @param m
     * @param processEnum
     */
    void submit(M m, ProcessEnum processEnum, String comment);
    /**
     * @description  更新流程实例名称
     * @createTime 2015-12-15 下午12:27:58
     * @author lys
     * @param processInstanceId
     * @param processInstanceName
     */
    void updateProcessInstanceName(String processInstanceId,String processInstanceName);
    /**
     * @description  取得当前节点
     * @createTime 2015-12-15 下午12:26:10
     * @author lys
     * @param processInstanceId
     * @return
     */
    Map<String, Object> getActivityInfo(String processInstanceId);
    /**
     * @description  查询已办任务
     * @createTime 2015-12-15 下午3:36:50
     * @author lys
     * @param pageable
     * @param applyUserName
     * @return
     */
    List<Map<String,Object>> queryDoneTask(Pagination pageable, String applyUserName);
    /**
     * @description: 查询待办任务
     * @createTime: 2016年6月8日 下午4:11:07
     * @author: lys
     * @param pageable
     * @param applyUserName
     * @return
     */
    List<Map<String,Object>> queryTodoTask(Page pageable, String applyUserName);
    /**
     * @Description: 得到当前任务，出去的线,outgoing transition得到执行按钮列表
     * @Create: 2015-12-16 下午3:09:06
     * @Author: lys
     * @param task
     * @return
     */
    List<String> getButtonsForTransition(Task task);
    /**
     * @Description: 根据任务id取得任务
     * @Create: 2015-12-16 下午3:16:06
     * @Author: lys
     * @param taskId
     * @return
     */
    Task getTaskId(String taskId);
    /**
     * @description: 查询首页的待办事项
     * @createTime: 2016年1月8日 上午10:35:01
     * @author: lys
     * @return
     */
    List<Map<String, Object>> queryIndexTodoTask();

    void completeBussinessTask(String processInstanceId);
    /**
     * @description: 结束流程
     * @createTime: 2016年5月3日 下午12:13:20
     * @author: lys
     * @param delegateTask
     */
    void toEnd(DelegateTask delegateTask);
    /**
     * @description: 查询历史流程审批历史
     * @createTime: 2016年6月15日 下午5:33:54
     * @author: lys
     * @param processKey
     * @param businessKey
     * @param processInstanceId
     * @return
     */
    List<Map<String, Object>> queryHiCommentsByProcessInstanceId(String processKey, String businessKey, String processInstanceId);
    /**
     * @description: 业务单标记为“审核通过”
     * @createTime: 2016年6月21日 上午9:19:27
     * @author: xlf
     * @param delegateTask
     */
    void toFinalPass(DelegateTask delegateTask);

    /**
     * @description: 删除已提交的审核申请
     * @createTime: 2017年3月30日 上午11:32:20
     * @author: wsh
     * @param processInstanceId
     */
    void deleteProcess(String processInstanceId)throws Exception;
}

package com.xuwei.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by hhd47 on 2017.9.8.
 */
public interface ActivitiMapper {

    /**
     * 查询待办任务
     * @param page
     * @param userId
     * @param applyUserName
     * @return
     */
    List<Map<String, Object>> queryTodoTask(Page page, @Param("userId") Long userId, @Param("applyUserName") String applyUserName);

    /**
     * 查询批住
     * @param page
     * @param processInstanceId
     * @return
     */
    List<Map<String, Object>> queryCommentsByProcessInstanceId(Page page, @Param("processInstanceId") String processInstanceId);

    /**
     * 查询已完成的
     * @param pageable
     * @param userId
     * @param applyUserName
     * @return
     */
    List<Map<String, Object>> queryDoneTask(Pagination pageable, @Param("userId") Long userId, @Param("applyUserName") String applyUserName);
    /**
     * 查询我提交的
     * @param pageable
     * @param userId
     * @return
     */
    List<Map<String, Object>> querySubmitTask(Pagination pageable, @Param("userId") Long userId);
}

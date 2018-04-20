package com.xuwei.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xuwei.bean.TodoAttach;
 
/**
 * @description: 待办任务_附件服务类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月11日 10:11:57
 * @author: zjy
 * @version: 1.0
 */
public interface ITodoAttachService extends IService<TodoAttach> {
	 /**
     * 
     * @description: 获取跟进附件
     * @createTime: 2017年10月9日 下午4:30:36
     * @author: caw
     * @param followId
     * @return
     */
    List<TodoAttach> findListByTodoId(Long todoId);
}

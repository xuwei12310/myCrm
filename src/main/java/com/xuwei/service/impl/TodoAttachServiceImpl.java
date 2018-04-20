package com.xuwei.service.impl;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xuwei.bean.TodoAttach;
import com.xuwei.mapper.TodoAttachMapper;
import com.xuwei.service.ITodoAttachService;

/**
 * @description: 待办任务_附件服务实现类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月11日 10:11:57
 * @author: zjy
 * @version: 1.0
 */

  
@Service
public class TodoAttachServiceImpl extends ServiceImpl<TodoAttachMapper, TodoAttach> implements ITodoAttachService {
	@Resource
    private TodoAttachMapper TodoAttachMapper;

	@Override
	public List<TodoAttach> findListByTodoId(Long todoId) {
		return TodoAttachMapper.findListByTodoId(todoId);
	}
}

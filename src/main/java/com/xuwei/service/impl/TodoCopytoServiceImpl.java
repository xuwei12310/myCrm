package com.xuwei.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xuwei.bean.TodoCopyto;
import com.xuwei.mapper.TodoCopytoMapper;
import com.xuwei.service.ITodoCopytoService;

/**
 * @description: 待办任务_抄送服务实现类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月11日 09:06:31
 * @author: zjy
 * @version: 1.0
 */
@Service
public class TodoCopytoServiceImpl extends ServiceImpl<TodoCopytoMapper, TodoCopyto> implements ITodoCopytoService {

	@Resource
	private TodoCopytoMapper todoCopytoMapper;

	@Override
	public long batchDeleteTodo(Long TodoId) {
		return todoCopytoMapper.batchDeleteTodo(TodoId);
	}

	@Override
	public TodoCopyto getReceiverName(Long data) {
		return todoCopytoMapper.getReceiverName(data);
	}
	
}

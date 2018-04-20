package com.xuwei.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xuwei.bean.TodoComment;
import com.xuwei.mapper.TodoCommentMapper;
import com.xuwei.service.ITodoCommentService;

/**
 * @description: 待办任务_评论服务实现类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月14日 08:47:17
 * @author: zjy
 * @version: 1.0
 */
@Service
public class TodoCommentServiceImpl extends ServiceImpl<TodoCommentMapper, TodoComment> implements ITodoCommentService {

	@Resource
	private TodoCommentMapper todoCommentMapper;
	@Override
	public List<TodoComment> listCommentByPage(Long todoIdComment) {
		// TODO Auto-generated method stub
		return todoCommentMapper.listCommentByPage(todoIdComment);
	}
	@Override
	public TodoComment getCommentContent(Long comentId) {
		// TODO Auto-generated method stub
		return todoCommentMapper.getCommentContent(comentId);
	}
	
}

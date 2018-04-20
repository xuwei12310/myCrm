package com.xuwei.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xuwei.bean.TodoComment;
 
/**
 * @description: 待办任务_评论服务类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月14日 08:47:17
 * @author: zjy
 * @version: 1.0
 */
public interface ITodoCommentService extends IService<TodoComment> {
	/**
	 * 
	 * @description:  查询任务列表
	 * @createTime: 2017年9月3日 下午2:34:59
	 * @author: zjy
	 * @param pageM
	 * @param m
	 * @return
	 */
	List<TodoComment> listCommentByPage(Long todoIdComment);
	
	
	/**
	 * 
	 * @description: 获取评论内容
	 * @createTime: 2017年9月21日 下午4:26:39
	 * @author: zjy
	 * @return
	 */
	TodoComment getCommentContent(Long comentId);
	
}

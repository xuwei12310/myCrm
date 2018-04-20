package com.xuwei.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xuwei.bean.TodoComment;

/**
 * @description: 待办任务_评论Mapper接口
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月14日 08:47:17
 * @author: zjy
 * @version: 1.0
 */
public interface TodoCommentMapper extends BaseMapper<TodoComment> {
	/**
	 * 
	 * @description:获取评论列表 
	 * @createTime: 2017年9月14日 上午11:00:06
	 * @author: zjy
	 * @param pageM
	 * @param m
	 * @return
	 */
	List<TodoComment> listCommentByPage(@Param("CommentTodoId")Long todoIdComment);
	
	/**
	 * @description: 获取评论内容
	 * @createTime: 2017年9月21日 下午4:28:24
	 * @author: zjy
	 * @param data
	 * @return
	 */
	TodoComment getCommentContent(@Param("comentId")Long comentId);
}
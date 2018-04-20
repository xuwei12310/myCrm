package com.xuwei.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xuwei.bean.TodoAttach;

/**
 * @description: 待办任务_附件Mapper接口
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月11日 10:11:57
 * @author: zjy
 * @version: 1.0
 */
public interface TodoAttachMapper extends BaseMapper<TodoAttach> {
	/**
	 * 
	 * @description: 获取待办附件
	 * @createTime: 2017年10月9日 下午5:02:58
	 * @author: caw
	 * @param todoId
	 * @return
	 */
	List<TodoAttach> findListByTodoId(@Param("todoId")Long todoId);
	/**
	 * 
	 * @description: 
	 * @createTime: 2017年10月9日 下午5:26:09
	 * @author: caw
	 * @param id
	 * @return
	 */
	TodoAttach selectByFAId(@Param("id")Long id);
}

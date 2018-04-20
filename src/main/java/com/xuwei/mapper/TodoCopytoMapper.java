package com.xuwei.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xuwei.bean.TodoCopyto;

/**
 * @description: 待办任务_抄送Mapper接口
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月11日 09:06:31
 * @author: zjy
 * @version: 1.0
 */
public interface TodoCopytoMapper extends BaseMapper<TodoCopyto> {

	/**
	 * 
	 * @description: 根据id获取抄送信息
	 * @createTime: 2017年9月12日 下午3:06:31
	 * @author: zjy
	 * @param TodoId
	 * @return
	 */
	Long batchDeleteTodo(@Param("todoId")Long TodoId);
	
	/**
	 * @description: 获取抄送人名称
	 * @createTime: 2017年9月17日 上午12:04:55
	 * @author: zjy
	 * @param data
	 * @return
	 */
	TodoCopyto getReceiverName(@Param("data") Long data);
}
package com.xuwei.service;

import com.baomidou.mybatisplus.service.IService;
import com.xuwei.bean.TodoCopyto;
 
/**
 * @description: 待办任务_抄送服务类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月11日 09:06:31
 * @author: zjy
 * @version: 1.0
 */
public interface ITodoCopytoService extends IService<TodoCopyto> {
	
	/**
	 * 根据id获取抄送对象
	 * @createTime: 2017年9月1日 上午8:55:21
	 * @author: zjy
	 * @param customerid
	 * @return
	 */
	long batchDeleteTodo(Long TodoId);
	/**
	 * 
	 * @description: 获取抄送人名称
	 * @createTime: 2017年9月17日 上午12:00:11
	 * @author: zjy
	 * @param data
	 * @return
	 */
	TodoCopyto getReceiverName(Long data);
}

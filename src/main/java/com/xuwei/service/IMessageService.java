package com.xuwei.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xuwei.bean.Message;
import com.xuwei.util.ServiceResult;
 
/**
 * @description: 消息服务类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月22日 09:32:18
 * @author: caw
 * @version: 1.0
 */
public interface IMessageService extends IService<Message> {
	
	/**
	 * 
	 * @description: 分页查询
	 * @createTime: 2017年9月22日 上午10:04:23
	 * @author: caw
	 * @param page
	 * @param m
	 * @return
	 */
	List<Message> getMessageList(Page<Message> page, Message m);
	
	/**
	 * 
	 * @description: 修改消息状态为已读
	 * @createTime: 2017年9月22日 上午10:22:34
	 * @author: caw
	 * @param ids
	 * @return
	 */
	ServiceResult readSetUp(String ids);
	
	/**
	 * 
	 * @description: 新增跟进任务消息提醒
	 * @createTime: 2017年9月22日 上午11:29:29
	 * @author: caw
	 * @param todoId
	 * @return
	 */
	boolean addTodoMessage(Long todoId);
	
	/**
	 * 
	 * @description: 新增订单进度消息提醒
	 * @createTime: 2017年9月22日 上午11:27:26
	 * @author: caw
	 * @param orderId
	 * @return
	 */
	boolean addOrderScheduleMessage(Long orderId);
	
	/**
	 * 
	 * @description: 获取消息提醒(微信端)
	 * @createTime: 2017年9月25日 下午2:12:21
	 * @author: caw
	 * @param page
	 * @param userId
	 * @param totalNum
	 * @return
	 */
	ServiceResult getMessageList(Page<Message> page,Long userId, Long totalNum);
	
	/**
	 * 
	 * @description: 获取未读消息数量
	 * @createTime: 2017年9月25日 下午3:08:21
	 * @author: caw
	 * @param userId
	 * @return
	 */
	int getMessageNum(Long userId);
}

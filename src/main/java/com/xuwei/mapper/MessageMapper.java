package com.xuwei.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xuwei.bean.Message;

/**
 * @description: 消息Mapper接口
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月25日 09:27:40
 * @author: caw
 * @version: 1.0
 */
public interface MessageMapper extends BaseMapper<Message> {
	/**
	 * 
	 * @description: 分页查询
	 * @createTime: 2017年9月25日 上午9:50:18
	 * @author: caw
	 * @param page
	 * @param m
	 * @param userId
	 * @return
	 */
	List<Message> getMessageList(Page<Message> page, @Param("m")Message m, @Param("userId")Long userId);
	
	/**
	 * 
	 * @description: 获取消息提醒(微信端)
	 * @createTime: 2017年9月25日 下午2:14:50
	 * @author: caw
	 * @param page
	 * @param userId
	 * @return
	 */
	List<Message> getMessageWXList(Page<Message> page, @Param("userId")Long userId);
	
	/**
	 * 
	 * @description: 获取未读消息数量
	 * @createTime: 2017年9月25日 下午3:07:29
	 * @author: caw
	 * @param userId
	 * @return
	 */
	int getMessageNum(@Param("userId")Long userId);
}
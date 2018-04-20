package com.xuwei.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xuwei.bean.Customer;
import com.xuwei.bean.Message;
import com.xuwei.bean.Order;
import com.xuwei.bean.OrderSchedule;
import com.xuwei.bean.Schedule;
import com.xuwei.bean.Todo;
import com.xuwei.bean.User;
import com.xuwei.mapper.CustomerMapper;
import com.xuwei.mapper.MessageMapper;
import com.xuwei.mapper.OrderMapper;
import com.xuwei.mapper.OrderScheduleMapper;
import com.xuwei.mapper.ScheduleMapper;
import com.xuwei.mapper.TodoMapper;
import com.xuwei.mapper.UserMapper;
import com.xuwei.service.IMessageService;
import com.xuwei.util.DateUtil;
import com.xuwei.util.OperateUtils;
import com.xuwei.util.ServiceResult;
import com.xuwei.util.StringUtil;

/**
 * @description: 消息服务实现类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月25日 09:27:40
 * @author: caw
 * @version: 1.0
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {
	@Resource
	private MessageMapper messageMapper;
	@Resource
	private TodoMapper todoMapper;
	@Resource
	private OrderMapper orderMapper;
	@Resource
	private UserMapper userMapper;
	@Resource
	private OrderScheduleMapper orderScheduleMapper;
	@Resource
	private ScheduleMapper scheduleMapper;
	@Resource
	private CustomerMapper customerMapper;

	/**
	 * 分页查询
	 */
	@Override
	public List<Message> getMessageList(Page<Message> page, Message m) {
		return messageMapper.getMessageList(page, m, OperateUtils.getCurrentUserId());
	}

	/**
	 * 修改消息状态为已读
	 */
	@Override
	public ServiceResult readSetUp(String ids) {
		ServiceResult result = new ServiceResult(false);
		String[] idArray = StringUtil.split(ids);
		int mulUpdateCount = 0;
		if (idArray == null || idArray.length == 0) {
			result.setMessage("请选择要修改的数据行！");
			return result;
		}
		for (String id : idArray) {
			Message message = messageMapper.selectById(id);
			message.setStatus(1);
			message.setLastModifierId(OperateUtils.getCurrentUserId());
			message.setLastModifyTime(DateUtil.getNowTimestampStr());
			messageMapper.updateAllColumnById(message);
			mulUpdateCount++;
		}
		if (mulUpdateCount > 0) {
			result.setIsSuccess(true);
			return result;
		} else {
			result.setMessage("没修改状态的记录！");
			return result;
		}
	}

	/**
	 * 新增跟进任务消息提醒
	 */
	@Override
	public boolean addTodoMessage(Long todoId) {
		String data = null;
		try {
			Todo todo = todoMapper.getTodoPageInfo(todoId);
			if(todo!=null){
				if(todo.getRemind()!=null && todo.getRemindUnit()!=null){
					if(todo.getRemindUnit()==1){
						data = "天";
					}else{
						data = "小时";
					}
					//提前提醒消息
					Message message = new Message();
					//超期提醒消息
					Message messageOne = new Message();
					
					message.setMsgType(1);
					message.setRemindTime(timeOperation("yyyy-MM-dd HH:mm",todo.getDoTime(),-todo.getRemind().intValue(),todo.getRemindUnit()));
					message.setUserId(todo.getDoUserId().getId());
					message.setTodoId(todoId);
					message.setStatus(0);
					message.setCreateTime(DateUtil.getNowTimestampStr());
					message.setCreatorId(OperateUtils.getCurrentUserId());
					message.setLastModifierId(OperateUtils.getCurrentUserId());
					message.setLastModifyTime(DateUtil.getNowTimestampStr());
					
					messageOne.setMsgType(2);
					messageOne.setRemindTime(timeOperation("yyyy-MM-dd HH:mm",todo.getDoTime(),todo.getRemind().intValue(),todo.getRemindUnit()));
					messageOne.setUserId(todo.getDoUserId().getId());
					messageOne.setTodoId(todoId);
					messageOne.setStatus(0);
					messageOne.setCreateTime(DateUtil.getNowTimestampStr());
					messageOne.setCreatorId(OperateUtils.getCurrentUserId());
					messageOne.setLastModifierId(OperateUtils.getCurrentUserId());
					messageOne.setLastModifyTime(DateUtil.getNowTimestampStr());
					String taskType = null;
					if(todo.getTaskType()==1){
						taskType = "客户跟进";
					}else if(todo.getTaskType()==2){
						taskType = "客户日常";
					}else if(todo.getTaskType()==3){
						taskType = "客户投诉";
					}else if(todo.getTaskType()==4){
						taskType = "客户关怀";
					}
					
					if(todo.getTaskType()==1){
						if(todo.getUserCreateTimeId().equals(todo.getDoUserId().getId())){
							message.setMsgContent("您创建的"+todo.getCustomerId().getCustomerName()+"客户距离下次跟进时间还有"+todo.getRemind()+data);
							messageOne.setMsgContent("您创建的"+todo.getCustomerId().getCustomerName()+"客户已超过跟进时间"+todo.getRemind()+data);
						}else{
							User user = userMapper.selectById(todo.getUserCreateTimeId());
							message.setMsgContent(user.getName()+"创建的"+todo.getCustomerId().getCustomerName()+"客户距离下次跟进时间还有"+todo.getRemind()+data);
							messageOne.setMsgContent(user.getName()+"创建的"+todo.getCustomerId().getCustomerName()+"客户已超过跟进时间"+todo.getRemind()+data);
						}
					}else{
						if(todo.getUserCreateTimeId().equals(todo.getDoUserId().getId())){
							message.setMsgContent("您创建的"+todo.getCustomerId().getCustomerName()+"客户的"+taskType+"离执行时间还有"+todo.getRemind()+data);
							messageOne.setMsgContent("您创建的"+todo.getCustomerId().getCustomerName()+"客户的"+taskType+"已超过执行时间"+todo.getRemind()+data);
						}else{
							User user = userMapper.selectById(todo.getUserCreateTimeId());
							message.setMsgContent(user.getName()+"创建的"+todo.getCustomerId().getCustomerName()+"客户的"+taskType+"离执行时间还有"+todo.getRemind()+data);
							messageOne.setMsgContent(user.getName()+"创建的"+todo.getCustomerId().getCustomerName()+"客户的"+taskType+"已超过执行时间"+todo.getRemind()+data);
						}
					}
					messageMapper.insert(message);
					messageMapper.insert(messageOne);
					return true;
				}
			}
		} catch (Exception e) {
			return false;
		}
		
		return false;
	}

	/**
	 * 新增订单进度消息提醒
	 */
	@Override
	public boolean addOrderScheduleMessage(Long orderId) {
		String data = null;
		try{
			Order order = orderMapper.selectById(orderId);
			List<OrderSchedule> list = orderScheduleMapper.getOrderScheduleList(orderId);
			if(list.size()!=0){
				for(OrderSchedule os:list){
					Schedule schedule = scheduleMapper.selectById(os.getSchedule().getId());
					if(schedule.getRemind()!=null && schedule.getRemindUnit()!=null){
						if(schedule.getRemindUnit()==1){
							data = "天";
						}else{
							data = "小时";
						}
						Message message = new Message();
						message.setRemindTime(timeOperation("yyyy-MM-dd HH:mm",os.getEstimateDate()+" 00:00",-schedule.getRemind().intValue(),schedule.getRemindUnit()));
						if(schedule.getRole()==1){
							message.setUserId(order.getOwner().getId());
						}else if(schedule.getRole()==2){
							message.setUserId(order.getFollowUser().getId());
						}else if(schedule.getRole()==3){
							message.setUserId(order.getCsPrincipal().getId());
						}else if(schedule.getRole()==4){
							message.setUserId(order.getCsAssistant().getId());
						}
						message.setStatus(0);
						message.setCreateTime(DateUtil.getNowTimestampStr());
						message.setCreatorId(OperateUtils.getCurrentUserId());
						message.setLastModifierId(OperateUtils.getCurrentUserId());
						message.setLastModifyTime(DateUtil.getNowTimestampStr());
						Customer customer = customerMapper.selectById(order.getCustomer().getId());
						message.setMsgContent(customer.getCustomerName()+"客户的订单["+order.getOrderCode()+"]有一个"+schedule.getScheduleName()+"待您"+schedule.getRemind()+data+"内完成");
						messageMapper.insert(message);
					}
				}
				return true;
			}
		}catch(Exception e){
			return false;
		}
		return false;
	}
	
	
	/**
	 * 时间运算
	 */
	public String timeOperation(String pattern, String timeData, int remind, int type){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);   
        Date dt = null;
		try {
			dt = sdf.parse(timeData);
		} catch (ParseException e) {
			e.printStackTrace();
		}  
        Calendar rightNow = Calendar.getInstance();  
        rightNow.setTime(dt);  
        if(type == 1){
        	rightNow.add(Calendar.DATE, remind);
        }else{
        	rightNow.add(Calendar.HOUR_OF_DAY, remind);
        }
        return sdf.format(rightNow.getTime());
	}

	/**
	 * 获取消息提醒
	 */
	@Override
	public ServiceResult getMessageList(Page<Message> page, Long userId, Long totalNum) {
		ServiceResult result = new ServiceResult(false);
		List<Message> list = messageMapper.getMessageWXList(page, userId);
		if(page.getTotal()==totalNum && totalNum!=0){
			List<Message> lists = new ArrayList<Message>();
			result.addData("messagelist", lists);
			result.setIsSuccess(true);
			return result;
		}
		result.addData("messagelist", list);
		result.setIsSuccess(true);
		return result;
	}

	/**
	 * 获取未读消息数量
	 */
	@Override
	public int getMessageNum(Long userId) {
		return messageMapper.getMessageNum(userId);
	}
}

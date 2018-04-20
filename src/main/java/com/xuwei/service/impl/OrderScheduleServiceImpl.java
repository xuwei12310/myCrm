package com.xuwei.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xuwei.bean.Order;
import com.xuwei.bean.OrderSchedule;
import com.xuwei.bean.Schedule;
import com.xuwei.bean.User;
import com.xuwei.mapper.OrderScheduleMapper;
import com.xuwei.mapper.ScheduleMapper;
import com.xuwei.mapper.UserMapper;
import com.xuwei.service.IOrderScheduleService;
import com.xuwei.util.OperateUtils;
import com.xuwei.util.ServiceResult;

/**
 * @description: 订单_进度服务实现类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月18日 17:41:33
 * @author: caw
 * @version: 1.0
 */
@Service
public class OrderScheduleServiceImpl extends ServiceImpl<OrderScheduleMapper, OrderSchedule> implements IOrderScheduleService {
	@Resource
	private OrderScheduleMapper orderScheduleMapper;
	@Resource
	private ScheduleMapper scheduleMapper;
	@Resource
	private UserMapper userMapper;

	/**
	 * 获取自定义列
	 */
	@Override
	public ServiceResult getColumns(Long productType) {
		ServiceResult result = new ServiceResult(false);
		StringBuilder columns = new StringBuilder();
		List<Schedule> list = scheduleMapper.getTypeBylist(productType);
		columns.append("[[");
		for(int i=0;i<list.size();i++){
			columns.append("{field:'pro"+list.get(i).getId()+"',title:'"+list.get(i).getScheduleName()+"',width:80,align:'center',"
					+ "formatter:function(value,row,index){"
						+ " if(row.comp"+list.get(i).getId()+" == 0){"
							+ "if(row.pro"+list.get(i).getId()+" != '' && row.pro"+list.get(i).getId()+" != null){"
								+ "return '<span style=\"color:#FF0000\">'+value+'</span>'"
							+ "}"
						+ "}else{"
							+ "if(row.pro"+list.get(i).getId()+" != '' && row.pro"+list.get(i).getId()+" != null){"
								+ "return '<span>'+value+'</span>'"
							+ "}"
					+ "}}},");
		}
		columns.append("]]");
		result.addData("columns", columns.toString());
		result.setIsSuccess(true);
		return result;
	}

	/**
	 * 分页查询
	 */
	@Override
	public List<Map<String, Object>> listOrderScheduleByPage(Page<Order> pageM,Long scheduleDictId, String orderCodes,
			String customerNames, int viewType) {
		User user = userMapper.findById(OperateUtils.getCurrentUserId());
		List<Map<String, Object>> list ;
		if(user.getOrganization()==null){
			list= orderScheduleMapper.listOrderScheduleByPageNum(scheduleDictId, orderCodes, customerNames,0,0,1,
					viewType,user.getId(),null);
		}else{
			list = orderScheduleMapper.listOrderScheduleByPageNum(scheduleDictId, orderCodes, customerNames,0,0,1,
			viewType,user.getId(),user.getOrganization().getId());
		}
		pageM.setTotal(list.size());
		int current = pageM.getCurrent()-1;
		return orderScheduleMapper.listOrderScheduleByPage(scheduleDictId, orderCodes, customerNames,current*pageM.getSize(),pageM.getSize(),
				0,viewType,OperateUtils.getCurrentUserId(),user.getOrganization().getId());
	}
	
	/**
	 * 获取进度
	 */
	@Override
	public ServiceResult getSchedule(Long productType){
		ServiceResult result = new ServiceResult(false);
		List<Schedule> list = scheduleMapper.getTypeBylist(productType);
		String []data = new String[list.size()];
		for(int i=0;i<list.size();i++){
			data[i] ="pro" + list.get(i).getId();
		}
		result.addData("scheduleList", data);
		result.setIsSuccess(true);
		return result;
	}

	/**
	 * 获取订单进度信息
	 */
	@Override
	public ServiceResult getOrderScheduleInfo(Long orderid, String scheduleDate) {
		ServiceResult result = new ServiceResult(false);
		String data = scheduleDate.substring(3);
		OrderSchedule orderSchedule = orderScheduleMapper.getOrderScheduleInfo(orderid, Long.parseLong(data));
		result.addData("orderSchedule", orderSchedule);
		result.setIsSuccess(true);
		return result;
	}

	/**
	 * 根据订单id获取订单进度（微信端）
	 */
	@Override
	public ServiceResult getOrderScheduleList(Long orderid) {
		ServiceResult result = new ServiceResult(false);
		List<OrderSchedule> list = orderScheduleMapper.getWXOrderScheduleList(orderid);
		result.addData("orderSchedulelist", list);
		result.setIsSuccess(true);
		return result;
	}

	/**
	 * 获取订单进度详细信息(微信端)
	 */
	@Override
	public OrderSchedule getWXOrderScheduleInfo(Long orderScheduleId) {
		return orderScheduleMapper.getWXOrderScheduleInfo(orderScheduleId);
	}

	@Override
	public List<OrderSchedule> getOrderSheduleByOrderId(Long orderId) {
		return orderScheduleMapper.getOrderSheduleByOrderId(orderId);
	}
}

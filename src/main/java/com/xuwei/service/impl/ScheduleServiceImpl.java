package com.xuwei.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xuwei.bean.Schedule;
import com.xuwei.mapper.OrderMapper;
import com.xuwei.mapper.OrderScheduleMapper;
import com.xuwei.mapper.ScheduleMapper;
import com.xuwei.service.IScheduleService;
import com.xuwei.util.ServiceResult;
/**
 * @description: 银行服务实现类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年07月19日 16:31:12
 * @author: xw
 * @version: 1.0
 */
@Service
public class ScheduleServiceImpl extends ServiceImpl<ScheduleMapper, Schedule> implements IScheduleService {

	@Resource
	private ScheduleMapper ScheduleMapper;
	@Resource
	private OrderMapper orderMapper;
	@Resource
	private OrderScheduleMapper orderScheduleMapper;
	
  /**
    * 
    * @description: 上移下移
    * @createTime: 2017年8月30日 上午14:36:19
    * @author: xw
    * @param srcId
    * @param destId
    */
	@Override
	public ServiceResult changeArray(Long srcId, Long destId) {
		ServiceResult result = new ServiceResult(false);
		if(srcId==null){
			result.setMessage("请选中移动记录");
			return result;
		}
		if(destId==null){
			result.setMessage("请指定对换记录");
			return result;
		}
		ScheduleMapper.changeArray(srcId,destId);
		result.setIsSuccess(true);
		return result;
	}
	/**
	 * 
	 * @description: 取下个排序值
	 * @createTime:2017年8月30日 上午14:36:19
	 * @author: xw
	 * @param dictType
	 * @return
	 */
	@Override
	public Integer nextArray() {
		return ScheduleMapper.nextArray();
	}
	
	/**
	 * 新增产品进度对应生成一条订单进度
	 */
	@Override
	public boolean addScheduleToOrder(Long productId, Long scheduleId) {
		//TODO
		/*List<Order> list = orderMapper.getOrderProductTypeList(productId);
		Schedule schedule = ScheduleMapper.selectById(scheduleId);
		for(Order order:list){
			OrderSchedule orderSchedule = new OrderSchedule();
			orderSchedule.setOrder(order);
			orderSchedule.setSchedule(schedule);
			try {
				orderSchedule.setEstimateDate(DateUtil.dateToString(DateUtil.addDay(DateUtil.toDate(order.getCreateTime()),schedule.getDuration())));
			} catch (ParseException e) {
				return false;
			}
			orderSchedule.setIsComplete(0);
			orderSchedule.setActualDate(orderSchedule.getEstimateDate());
			orderSchedule.setCreatorId(OperateUtils.getCurrentUserId());
			orderSchedule.setCreateTime(DateUtil.getNowTimestampStr());
			orderSchedule.setLastModifierId(OperateUtils.getCurrentUserId());
			orderSchedule.setLastModifyTime(DateUtil.getNowTimestampStr());
			orderScheduleMapper.insert(orderSchedule);
		}*/
		return true;
	}


}

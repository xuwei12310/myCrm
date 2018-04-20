package com.xuwei.service;

import com.baomidou.mybatisplus.service.IService;
import com.xuwei.bean.Schedule;
import com.xuwei.util.ServiceResult;

/**
 * @description: 字典服务类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年07月19日 16:31:12
 * @author: zjy
 * @version: 1.0
 */
public interface IScheduleService extends IService<Schedule> {

	/**
	 * 
	 * @description: 上移下移
	 * @createTime: 2017年7月20日 上午11:20:59
	 * @author: lxb
	 * @param srcId
	 * @param destId
	 */

	ServiceResult changeArray(Long srcId, Long destId);

	/**
	 * 
	 * @description: 取下个排序值
	 * @createTime: 2017年8月9日 下午3:54:23
	 * @author: lxb
	 * @param dictType
	 * @return
	 */
	Integer nextArray();
	
	/**
	 * 
	 * @description: 新增产品进度对应生成一条订单进度
	 * @createTime: 2017年9月27日 下午6:00:45
	 * @author: caw
	 * @param productId
	 * @param scheduleId
	 * @return
	 */
	boolean addScheduleToOrder(Long productId, Long scheduleId);

}

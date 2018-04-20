package com.xuwei.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xuwei.bean.Schedule;

/**
 * @description: 进度设置Mapper接口
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年07月19日 16:31:12
 * @author: xw
 * @version: 1.0
 */
public interface ScheduleMapper extends BaseMapper<Schedule> {
   /**
    * 
    * @description: 上移下移
    * @createTime: 2017年8月30日 上午14:36:19
    * @author: xw
    * @param srcId
    * @param destId
    */
	void changeArray(@Param("srcId")Long srcId, @Param("destId")Long destId);
	/**
	 * 
	 * @description: 取下个排序值
	 * @createTime: 2017年8月30日 上午14:36:19
	 * @author: xw
	 * @param dictType
	 * @return
	 */
	Integer nextArray();

	/**
	 * 
	 * @description: 根据产品类型获取进度信息
	 * @createTime: 2017年9月19日 下午4:56:59
	 * @author: caw
	 * @return
	 */
	List<Schedule> getTypeBylist(@Param("productType")Long productType);

}
package com.xuwei.service;


import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xuwei.bean.Area;
import com.xuwei.util.ServiceResult;
 
/**
 * @description: 区/县服务类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年08月29日 10:52:26
 * @author: wwh
 * @version: 1.0
 */
public interface IAreaService extends IService<Area> {

	/**
	 * @description: 分页查询
	 * @createTime: 2017年8月31日 上午1:56:46
	 * @author: wwh
	 * @param pageM
	 * @param m
	 * @return
	 */
	List<Area> selectByPage(Page<Area> pageM, Area m);

	/**
	 * @description: 
	 * @createTime: 2017年8月31日 上午3:00:00
	 * @author: wwh
	 * @return
	 */
	Integer maxArrayIndex();

	/**
	 * @description: 
	 * @createTime: 2017年8月31日 上午3:16:29
	 * @author: wwh
	 * @param srcId
	 * @param destId
	 * @return
	 */
	ServiceResult changeArray(Long srcId, Long destId);
    
	/**
	 * 
	 * @description: 查询所有的县区
	 * @createTime: 2017年8月31日 上午10:41:53
	 * @author: wwh
	 * @return
	 */
	List<Area> queryAllArea();
	
	/**
	 * @description: 获取所有户籍地信息
	 * @createTime: 2017年8月31日 下午 2:30:12
	 * @author: caw
	 * @return
	 */
	List<Area> getPlaceByList(Page<Area> pageM,String areaName);
	
	/**
	 * 
	 * @description: 根据城市获取区
	 * @createTime: 2017年9月13日 上午9:27:47
	 * @author: caw
	 * @param cityId
	 * @return
	 */
	List<Area> getCityToArea(Long cityId);
}

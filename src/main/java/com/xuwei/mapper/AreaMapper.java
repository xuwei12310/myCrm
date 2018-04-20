package com.xuwei.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.xuwei.bean.Area;

/**
 * @description: 区/县Mapper接口
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年08月29日 10:52:26
 * @author: wwh
 * @version: 1.0
 */
public interface AreaMapper extends BaseMapper<Area> {

	/**
	 * @description: 分页查询
	 * @createTime: 2017年8月31日 上午1:59:42
	 * @author: wwh
	 * @param pageM
	 * @param m
	 * @return
	 */
	List<Area> findListByPage(Pagination pageM, @Param("m")Area m);

	/**
	 * @description: 
	 * @createTime: 2017年8月31日 上午2:55:26
	 * @author: wwh
	 * @return
	 */
	Integer maxArrayIndex();

	/**
	 * 
	 * @description: 上移下移
	 * @createTime: 2017年8月30日 上午8:44:33
	 * @author: wwh
	 * @param srcId
	 * @param destId
	 */
	void changeArray(@Param("srcId")Long srcId, @Param("destId")Long destId);
	
	/**
	 * @description: 获取所有户籍地信息
	 * @createTime: 2017年8月31日 下午 2:30:12
	 * @author: caw
	 * @return
	 */
	List<Area> getPlaceByList(Page<Area> pageM, @Param("areaName")String areaName);
    
	/**
	 * 
	 * @description: 查询所有的县区
	 * @createTime: 2017年8月31日 上午10:42:49
	 * @author: wwh
	 * @return
	 */
	List<Area> queryAllArea();
	
	/**
	 * 
	 * @description: 根据城市获取区
	 * @createTime: 2017年9月13日 上午9:29:48
	 * @author: caw
	 * @param cityId
	 * @return
	 */
	List<Area> getCityToArea(@Param("cityId")Long cityId);

}
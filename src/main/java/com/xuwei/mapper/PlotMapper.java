package com.xuwei.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.xuwei.bean.Plot;

/**
 * @description: 字典小区Mapper接口
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年08月29日 10:52:54
 * @author: wwh
 * @version: 1.0
 */
public interface PlotMapper extends BaseMapper<Plot> {

	/**
	 * @description: 
	 * @createTime: 2017年8月31日 上午4:04:06
	 * @author: wwh
	 * @param pageM
	 * @param m
	 * @return
	 */
	List<Plot> findListByPage(Pagination pageM, @Param("m")Plot m);
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
	 * @description: 
	 * @createTime: 2017年8月31日 上午2:55:26
	 * @author: wwh
	 * @return
	 */
	Integer maxArrayIndex();
	
	/**
	 * @description: 获取所有小区信息
	 * @createTime: 2017年8月31日 下午3:58:32
	 * @author: caw
	 * @param liveAreaid
	 * @return
	 */
	List<Plot> getLivePlotByList(@Param("liveAreaid")Long liveAreaid,Page<Plot> pageM, @Param("plotName")String plotName);
	
	/**
	 * 
	 * @description: 获取小区（微信端）
	 * @createTime: 2017年9月13日 上午10:16:36
	 * @author: caw
	 * @param liveAreaid
	 * @return
	 */
	List<Plot> getPlot(@Param("liveAreaid")Long liveAreaid);
}
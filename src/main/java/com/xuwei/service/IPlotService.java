package com.xuwei.service;


import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xuwei.bean.Plot;
import com.xuwei.util.ServiceResult;
 
/**
 * @description: 字典小区服务类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年08月29日 10:52:54
 * @author: wwh
 * @version: 1.0
 */
public interface IPlotService extends IService<Plot> {

	/**
	 * @description: 
	 * @createTime: 2017年8月31日 上午4:02:37
	 * @author: wwh
	 * @param pageM
	 * @param m
	 * @return
	 */
	List<Plot> selectByPage(Page<Plot> pageM, Plot m);
    /**
     * 
     * @description: 上移下移
     * @createTime: 2017年8月31日 上午8:18:34
     * @author: wwh
     * @param srcId
     * @param destId
     * @return
     */
	ServiceResult changeArray(Long srcId, Long destId);
	/**
	 * @description: 
	 * @createTime: 2017年8月31日 上午3:00:00
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
	List<Plot> getLivePlotByList(Long liveAreaid,Page<Plot> pageM, String plotName);
	
	/**
	 * 
	 * @description: 获取小区（微信端）
	 * @createTime: 2017年9月13日 上午10:14:56
	 * @author: caw
	 * @param liveAreaid
	 * @return
	 */
	List<Plot> getPlot(Long liveAreaid);
}

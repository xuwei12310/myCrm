package com.xuwei.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xuwei.bean.Plot;
import com.xuwei.mapper.PlotMapper;
import com.xuwei.service.IPlotService;
import com.xuwei.util.ServiceResult;

/**
 * @description: 字典小区服务实现类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年08月29日 10:52:54
 * @author: wwh
 * @version: 1.0
 */
@Service
public class PlotServiceImpl extends ServiceImpl<PlotMapper, Plot> implements IPlotService {
	@Resource
	private PlotMapper plotMapper ;
	
	public List<Plot> selectByPage(Page<Plot> pageM, Plot m) {
		return plotMapper.findListByPage(pageM,m);
	}
	
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
			plotMapper.changeArray(srcId,destId);
			result.setIsSuccess(true);
			return result;
			
		}
	
     public Integer maxArrayIndex() {
		
		return plotMapper.maxArrayIndex() == null?0:plotMapper.maxArrayIndex();
	}
     
     /**
      * 获取所有小区信息
      */
	@Override
	public List<Plot> getLivePlotByList(Long liveAreaid,Page<Plot> pageM, String plotName) {
		return plotMapper.getLivePlotByList(liveAreaid,pageM,plotName);
	}

	/**
	 * 获取小区（微信端）
	 */
	@Override
	public List<Plot> getPlot(Long liveAreaid) {
		return plotMapper.getPlot(liveAreaid);
	}
	
}

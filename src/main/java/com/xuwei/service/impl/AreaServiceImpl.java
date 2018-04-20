package com.xuwei.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xuwei.bean.Area;
import com.xuwei.mapper.AreaMapper;
import com.xuwei.service.IAreaService;
import com.xuwei.util.ServiceResult;

/**
 * @description: 区/县服务实现类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年08月29日 10:52:26
 * @author: wwh
 * @version: 1.0
 */
@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements IAreaService {
	
	@Resource
	private AreaMapper areaMapper ;
	public List<Area> selectByPage(Page<Area> pageM, Area m) {
		
		return areaMapper.findListByPage(pageM,m);
	}
	
    public Integer maxArrayIndex() {
		
		return areaMapper.maxArrayIndex() == null?0:areaMapper.maxArrayIndex();
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
			areaMapper.changeArray(srcId,destId);
			result.setIsSuccess(true);
			return result;
	}
    
    /**
	 * @description: 获取所有户籍地信息
	 */
    public List<Area> getPlaceByList(Page<Area> pageM,String areaName){
		return areaMapper.getPlaceByList(pageM,areaName);
	}
    
    /**
     * 获取所以区
     */
    public List<Area> queryAllArea() {
    	return areaMapper.queryAllArea();
    }

    /**
     * 根据城市获取区
     */
	@Override
	public List<Area> getCityToArea(Long cityId) {
		return areaMapper.getCityToArea(cityId);
	}
}

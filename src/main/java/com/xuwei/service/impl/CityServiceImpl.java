package com.xuwei.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xuwei.bean.City;
import com.xuwei.mapper.CityMapper;
import com.xuwei.service.ICityService;
import com.xuwei.util.ServiceResult;

/**
 * @description: 市服务实现类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年08月29日 10:52:02
 * @author: wwh
 * @version: 1.0
 */
@Service
public class CityServiceImpl extends ServiceImpl<CityMapper, City> implements ICityService {
	 @Resource
	private CityMapper cityMapper ;
	
	public Integer maxArrayIndex() {
		
		return cityMapper.maxArrayIndex() == null?0:cityMapper.maxArrayIndex();
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
			cityMapper.changeArray(srcId,destId);
			result.setIsSuccess(true);
			return result;
			
		}
	
	public List<City> getAllCity(String provinceCode) {
		return cityMapper.getAllCity(provinceCode);
	}
	
	public List<City> selectByPage(Page<City> pageM, City m) {
		
		return cityMapper.findListByPage(pageM, m);
	}

	/**
	 * 获取所有城市
	 */
	@Override
	public List<City> getAllCity() {
		return cityMapper.getAllCity();
	}

	/**
	 * 根据省获取城市
	 */
	@Override
	public List<City> getProvinceToCity(Long provinceId) {
		return cityMapper.getProvinceToCity(provinceId);
	}
}

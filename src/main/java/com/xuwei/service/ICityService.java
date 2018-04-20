package com.xuwei.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xuwei.bean.City;
import com.xuwei.util.ServiceResult;
 
/**
 * @description: 市服务类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年08月29日 10:52:02
 * @author: wwh
 * @version: 1.0
 */
public interface ICityService extends IService<City> {
    
	/**
	 * 
	 * @description:查询最大的arry
	 * @createTime: 2017年8月30日 上午11:54:55
	 * @author: wwh
	 * @return
	 */
	Integer maxArrayIndex();
    
	/**
	 * 
	 * @description: 上移下移
	 * @createTime: 2017年8月30日 下午2:03:42
	 * @author: wwh
	 * @param srcId
	 * @param destId
	 * @return
	 */
	
	ServiceResult changeArray(Long srcId, Long destId);
    
	
    /**
     * 
     * @description: 获取所有的城市
     * @createTime: 2017年8月30日 下午2:59:34
     * @author: wwh
     * @param provinceCode
     * @return
     */
	List<City> getAllCity(String provinceCode);
    
	/**
	 * 
	 * @description: 分页查询
	 * @createTime: 2017年8月30日 下午4:05:39
	 * @author: wwh
	 * @param pageM
	 * @param m
	 * @return
	 */
	List<City> selectByPage(Page<City> pageM, City m);
	
	/**
	 * 
	 * @description: 获取所有城市
	 * @createTime: 2017年9月13日 上午8:26:04
	 * @author: caw
	 * @return
	 */
	List<City> getAllCity();
	
	/**
	 * 
	 * @description: 根据省获取城市
	 * @createTime: 2017年9月13日 上午9:19:41
	 * @author: caw
	 * @param provinceId
	 * @return
	 */
	List<City> getProvinceToCity(Long provinceId);
	
}

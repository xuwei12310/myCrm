package com.xuwei.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.xuwei.bean.City;

/**
 * @description: 市Mapper接口
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年08月29日 10:52:02
 * @author: wwh
 * @version: 1.0
 */
public interface CityMapper extends BaseMapper<City> {
    
	
	/**
	 * 
	 * @description: 查询最大的array字段
	 * @createTime: 2017年8月30日 上午11:57:40
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
     * 
     * @description: 获取所有的城市
     * @createTime: 2017年8月30日 下午3:00:50
     * @author: wwh
     * @param provinceCode
     * @return
     */
	List<City> getAllCity(@Param("provinceId") String provinceId);
    
	/**
	 * 
	 * @description: 分页查询
	 * @createTime: 2017年8月30日 下午4:15:56
	 * @author: wwh
	 * @param pageM
	 * @param m
	 * @return
	 */
	List<City> findListByPage(Pagination page, @Param("m") City m);
	
	/**
	 * 
	 * @description: 获取所有城市
	 * @createTime: 2017年9月13日 上午8:27:26
	 * @author: caw
	 * @return
	 */
	List<City> getAllCity();
	
	/**
	 * 
	 * @description: 根据省获取城市
	 * @createTime: 2017年9月13日 上午9:20:43
	 * @author: caw
	 * @param provinceId
	 * @return
	 */
	List<City> getProvinceToCity(@Param("provinceId")Long provinceId);

}
package com.xuwei.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xuwei.bean.Province;

/**
 * @description: 省Mapper接口
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年08月29日 10:49:14
 * @author: wwh
 * @version: 1.0
 */
public interface ProvinceMapper extends BaseMapper<Province> {
	
	/**
	 * 
	 * @description: 查出最大的arry字段值
	 * @createTime: 2017年8月30日 上午8:47:08
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
     * @description: 获取所有省份
     * @createTime: 2017年8月30日 上午11:20:59
     * @author: wwh
     * @return
     */
    List<Province> getAllProvince();

}
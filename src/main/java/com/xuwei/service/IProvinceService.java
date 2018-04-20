package com.xuwei.service;


import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xuwei.bean.Province;
import com.xuwei.util.ServiceResult;
 
/**
 * @description: 省服务类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年08月29日 10:49:14
 * @author: wwh
 * @version: 1.0
 */
public interface IProvinceService extends IService<Province> {
	
	/**
	 * 
	 * @description: 查询最大的数组下标
	 * @createTime: 2017年8月29日 下午5:07:49
	 * @author: wwh
	 * @return
	 */
    Integer maxArrayIndex();
	
	/**
	 * 
	 * @description: 上移下移
	 * @createTime: 2017年8月30日 上午9:04:19
	 * @author: wwh
	 * @param srcId
	 * @param destId
	 * @return
	 */
    ServiceResult changeArray(Long srcId, Long destId) ;
    
	/**
	 * 
	 * @description: 获取所有省份
	 * @createTime: 2017年8月30日 上午11:19:57
	 * @author: wwh
	 * @return
	 */
    List<Province> getAllProvince();
}

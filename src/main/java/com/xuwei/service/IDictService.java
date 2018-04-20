package com.xuwei.service;


import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xuwei.bean.Dict;
import com.xuwei.bean.DictType;
import com.xuwei.util.ServiceResult;

/**
 * @description: 字典服务类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年07月19日 16:31:12
 * @author: lxb
 * @version: 1.0
 */
public interface IDictService extends IService<Dict> {

	/**
	 * 
	 * @description: 上移下移
	 * @createTime: 2017年7月20日 上午11:20:59
	 * @author: lxb
	 * @param srcId
	 * @param destId
	 */

	ServiceResult changeArray(Long srcId, Long destId);

	/**
	 * 
	 * @description: 取下个排序值
	 * @createTime: 2017年8月9日 下午3:54:23
	 * @author: lxb
	 * @param dictType
	 * @return
	 */
	Integer nextArray(DictType dictType);
	
	/**
	 * 
	 * @description: 根据id获取字典数据（微信端）
	 * @createTime: 2017年9月14日 上午11:44:13
	 * @author: caw
	 * @param id
	 * @return
	 */
	List<Dict> getDictList(Long id);

}

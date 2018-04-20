package com.xuwei.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xuwei.bean.Dict;
import com.xuwei.bean.DictType;


/**
 * @description: 字典Mapper接口
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年07月19日 16:31:12
 * @author: lxb
 * @version: 1.0
 */
public interface DictMapper extends BaseMapper<Dict> {
   /**
    * 
    * @description: 上移下移
    * @createTime: 2017年7月20日 上午11:20:59
    * @author: lxb
    * @param srcId
    * @param destId
    */
	void changeArray(@Param("srcId")Long srcId, @Param("destId")Long destId);
	/**
	 * 
	 * @description: 取下个排序值
	 * @createTime: 2017年8月9日 下午3:57:36
	 * @author: lxb
	 * @param dictType
	 * @return
	 */
	Integer nextArray(DictType dictType);
	
	/**
	 * 
	 * @description: 根据id获取字典数据（微信端）
	 * @createTime: 2017年9月14日 上午11:42:58
	 * @author: caw
	 * @param id
	 * @return
	 */
	List<Dict> getDictList(@Param("id")Long id);



}
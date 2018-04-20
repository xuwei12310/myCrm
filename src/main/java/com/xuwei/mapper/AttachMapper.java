package com.xuwei.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xuwei.bean.Attach;


/**
 * @description: 系统_附件Mapper接口
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年06月30日 11:46:47
 * @author: lys
 * @version: 1.0
 */
public interface AttachMapper extends BaseMapper<Attach> {
	boolean deleteAttachlist(@Param("array") Long[] array);
	
	/**
	 * 
	 * @description: 根据id获取附件信息（微信端）
	 * @createTime: 2017年9月14日 上午10:55:32
	 * @author: caw
	 * @param attachId
	 * @return
	 */
	List<Attach> getAttachList(@Param("attachId")Long attachId);
}
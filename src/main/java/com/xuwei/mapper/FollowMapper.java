package com.xuwei.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xuwei.bean.Follow;

/**
 * @description: 客户跟进Mapper接口
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年07月19日 16:31:12
 * @author: xw
 * @version: 1.0
 */
public interface FollowMapper extends BaseMapper<Follow> {

	/**
	 * @description:分页查询跟进
	 * @createTime 2017年9月4日 下午3:24:09
	 * @author xw
	 * @param m
	 * @param pageM
	 * @param ew 
	 * @return
	 */
	List<Follow> listByPage(@Param("m")Follow m, Page<Follow> pageM,@Param("search")String search,@Param("ew") EntityWrapper<Follow> ew);
	
	/**
	 * 
	 * @description:通过id查找跟进
	 * @createTime 2017年10月11日 上午10:57:41
	 * @author xw
	 * @param id
	 * @return
	 */
	Follow selectById(@Param("id") long id);

	/**
	 * @description:通过跟进id获取附件id
	 * @createTime 2017年9月5日 上午10:12:27
	 * @author xw
	 * @param followId
	 * @return
	 */
	String getAttachId(@Param("id")Long followId);


}
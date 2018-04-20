package com.xuwei.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xuwei.bean.FollowAttach;

/**
 * <p>
  * 跟进_附件 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2017-09-04
 */
public interface FollowAttachMapper extends BaseMapper<FollowAttach> {

	/**
	 * @description:通过跟进id查找跟进附件
	 * @createTime 2017年9月11日 上午10:10:18
	 * @author xw
	 * @param id
	 * @return
	 */
	List<FollowAttach> selectByFollowId(@Param("id")Long id);
	
	/**
	 * 
	 * @description: 根据附件id删除跟进附件
	 * @createTime: 2017年10月9日 下午2:45:42
	 * @author: caw
	 * @param attachId
	 */
	int delAttachIdPage(@Param("attachId")Long attachId);
	
	/**
	 * 
	 * @description: 获取跟进附件
	 * @createTime: 2017年10月9日 下午5:02:58
	 * @author: caw
	 * @param followId
	 * @return
	 */
	List<FollowAttach> findListByFollowId(@Param("followId")Long followId);
	
	/**
	 * 
	 * @description: 
	 * @createTime: 2017年10月9日 下午5:26:09
	 * @author: caw
	 * @param id
	 * @return
	 */
	FollowAttach selectByFAId(@Param("id")Long id);

}
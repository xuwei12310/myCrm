package com.xuwei.mapper;

import com.xuwei.bean.UserRole;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * @description: Mapper接口
 * @copyright: 福建骏华信息有限公司 (c)2018
 * @createTime: 2018年01月16日 19:57:11
 * @author: xw
 * @version: 1.0
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {
	List<Long> getGrantRole(Long userId);

    int deleteBatchByRoleIdAndUserId(List<UserRole> list);

    /**
     * @description: 插入对象
     * @createTime: 2016年8月12日 下午5:36:04
     * @author: lys
     * @param m
     * @return
     */
    Integer insert(UserRole m);
    /**
     * @description: 批量插入对象
     * @createTime: 2016年8月16日 下午5:44:46
     * @author: lys
     * @param list
     * @return
     */
    int insertBatch(List<UserRole> list);

	/**
	 * @description:
	 * @createTime 2017年8月30日 上午9:09:41
	 * @author xw
	 * @param id
	 */
	int deleteByUserId(@Param("id")long id);
}
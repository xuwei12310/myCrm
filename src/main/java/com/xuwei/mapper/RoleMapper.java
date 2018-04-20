package com.xuwei.mapper;


import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xuwei.bean.Role;

/**
 * @description: 用户角色Mapper接口
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年07月12日 11:07:39
 * @author: hhd
 * @version: 1.0
 */
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * @description: 通过主见数组删除对象
     * @createTime: 2016年8月12日 下午5:39:40
     * @author: lys
     * @param idArray
     * @return
     */
    int mulDelete(String[] idArray);

	/**
	 * @description:
	 * @createTime 2017年8月29日 上午11:33:25
	 * @author xw
	 * @return
	 */
	List<Role> findAll();
}
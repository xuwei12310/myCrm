package com.xuwei.service;


import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xuwei.bean.Role;
import com.xuwei.util.ServiceResult;
 
/**
 * @description: 角色
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年07月12日 11:07:39
 * @author: hhd
 * @version: 1.0
 */
public interface IRoleService extends IService<Role> {
    /**
     * @description: 插入对象
     * @createTime: 2016年8月12日 下午5:43:40
     * @author: lys
     * @param m
     * @return
     */
    ServiceResult create(Role m);
    /**
     * @description: 更新对象
     * @createTime: 2016年8月12日 下午5:44:07
     * @author: lys
     * @param m
     * @return
     */
    ServiceResult update(Role m);
    /**
     * @description: 批量删除
     * @createTime: 2016年8月16日 上午11:14:41
     * @author: lys
     * @param ids
     * @return
     */
    ServiceResult mulDelete(String ids);
	/**
	 * @description:查询所有角色（职务）
	 * @createTime 2017年8月29日 上午11:32:07
	 * @author xw
	 * @return
	 */
	List<Role> findAll();
}

package com.xuwei.service;


import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.xuwei.util.ServiceResult;


/**
 * @description: Service基类
 * @copyright: 福建骏华信息有限公司 (c)2016
 * @createTime: 2016年8月24日下午3:05:24
 * @author：lys
 * @version：1.0
 */
public interface BaseService<M extends com.xuwei.bean.BaseEntity<ID>, ID> {
    /**
     * @description: 插入对象
     * @createTime: 2016年8月12日 下午5:43:40
     * @author: lys
     * @param m
     * @return
     */
	ServiceResult create(M m);
    /**
     * @description: 更新对象
     * @createTime: 2016年8月12日 下午5:44:07
     * @author: lys
     * @param m
     * @return
     */
    ServiceResult update(M m);
    /**
     * @description: 批量删除
     * @createTime: 2016年8月16日 上午11:14:41
     * @author: lys
     * @param ids
     * @return
     */
    ServiceResult mulDelete(String ids);
    /**
     * @description: 根据Id删除对象
     * @createTime: 2016年8月24日 下午3:05:36
     * @author: lys
     * @param id
     * @return
     */
    ServiceResult deleteById(ID id);
    /**
     * @description: 根据Id发现对象
     * @createTime: 2016年8月24日 下午3:05:46
     * @author: lys
     * @param id
     * @return
     */
    M findById(ID id);
    /**
     * @description: 分页查询
     * @createTime: 2016年8月24日 下午3:05:57
     * @author: lys
     * @param m
     * @param page
     */
    void findListByPage(M m, Page<M> page);
    /**
     * @description: 查询所有
     * @createTime: 2016年8月24日 下午3:06:07
     * @author: lys
     * @return
     */
    List<M> findAll();
    
    int nextWeight(ID id);

}

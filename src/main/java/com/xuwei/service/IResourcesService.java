package com.xuwei.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xuwei.bean.Resources;
import com.xuwei.util.ServiceResult;

/**
 * @description: 资源Service
 * @copyright: 福建骏华信息有限公司 (c)2016
 * @createTime: 2016年8月12日下午10:26:56
 * @author：lys
 * @version：1.0
 */
public interface IResourcesService  extends IService<Resources> {
    /**
     * @description: 查询树跟节点
     * @createTime: 2016年8月15日 下午5:02:46
     * @author: lys
     * @return
     */
    List<Resources> treeRoot();
    /**
     * @description: 查找pk下的树子节点
     * @createTime: 2016年8月15日 下午9:18:28
     * @author: lys
     * @param
     * @return
     */
    List<Resources> treeChildren(Long id);
    /**
     * @description: 查找m.id下的子节点
     * @createTime: 2016年8月15日 下午5:38:13
     * @author: lys
     * @param m
     * @param page
     */
    void findChildrenByPage(Resources m, Page<Resources> page);
    /**
     * @description: 插入对象
     * @createTime: 2016年8月12日 下午5:43:40
     * @author: lys
     * @param m
     * @return
     */
    ServiceResult create(Resources m);
    /**
     * @description: 更新对象
     * @createTime: 2016年8月12日 下午5:44:07
     * @author: lys
     * @param m
     * @return
     */
    ServiceResult update(Resources m);
    /**
     * @description: 批量删除
     * @createTime: 2016年8月16日 上午11:14:41
     * @author: lys
     * @param ids
     * @return
     */
    ServiceResult mulDelete(String ids);
    /**
     * @description: 树
     * @createTime: 2016年8月16日 下午2:25:02
     * @author: lys
     * @return
     */
    List<Resources> tree();
    /**
     * @description: 启动
     * @createTime: 2016年9月30日 下午3:00:42
     * @author: lys
     * @param sourceId
     * @param targetId
     * @param point
     * @return
     */
    Object move(Long sourceId, Long targetId, String point);

    int nextWeight(Long id);

    void updateSelftAndChild(Resources source, Long parentId, String parentIds, Integer weight);
}

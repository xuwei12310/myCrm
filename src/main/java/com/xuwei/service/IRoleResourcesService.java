package com.xuwei.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xuwei.bean.RoleResources;
import com.xuwei.util.ServiceResult;

/**
 * @description: 服务类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年07月13日 10:08:42
 * @author: hhd
 * @version: 1.0
 */
public interface IRoleResourcesService extends IService<RoleResources> {
    List<Long> getGrantResources(Long roleId);
    /**
     * @description: 给角色roleId授权
     * @createTime: 2016年8月16日 下午5:05:45
     * @author: lys
     * @param roleId
     * @param ids
     * @return
     */
    ServiceResult grantResources(Long roleId, String ids);


}

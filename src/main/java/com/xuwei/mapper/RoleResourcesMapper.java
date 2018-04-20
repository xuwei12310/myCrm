package com.xuwei.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xuwei.bean.RoleResources;

import java.util.List;

/**
 * @description: Mapper接口
 * @copyright: 福建骏华信息有限公司 (c)${cfg.year}
 * @createTime: ${cfg.createTime}
 * @author: zyd
 * @version: ${cfg.version}
 */
public interface RoleResourcesMapper extends BaseMapper<RoleResources> {

    List<Long> getGrantResources(Long roleId);

    int deleteBatchByRoleIdAndResourcesId(List<RoleResources> list);

    /**
     * @description: 批量插入对象
     * @createTime: 2016年8月16日 下午5:44:46
     * @author: lys
     * @param list
     * @return
     */
    int insertBatch(List<RoleResources> list);
}
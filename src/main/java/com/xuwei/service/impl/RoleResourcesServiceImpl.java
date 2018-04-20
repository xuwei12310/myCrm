package com.xuwei.service.impl;

import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.xuwei.bean.RoleResources;
import com.xuwei.mapper.RoleResourcesMapper;
import com.xuwei.service.IRoleResourcesService;
import com.xuwei.service.IUserService;
import com.xuwei.util.ServiceResult;
import com.xuwei.util.StringUtil;

/**
 * @description: 服务实现类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年07月13日 10:08:42
 * @author: hhd
 * @version: 1.0
 */
@Service
public class RoleResourcesServiceImpl extends ServiceImpl<RoleResourcesMapper, RoleResources> implements IRoleResourcesService {
    @Resource
    private RoleResourcesMapper roleResourcesDao;
    @Resource
    private IUserService userService;

    @Override
    public List<Long> getGrantResources(Long roleId) {
        return roleResourcesDao.getGrantResources(roleId);
    }

    //@CacheEvict(value={"admin-authorization","admin-menus","admin-tree-menus"},allEntries=true)
    @Transactional
    @Override
    public ServiceResult grantResources(Long roleId, String ids) {
        ServiceResult result = new ServiceResult(false);
        if(roleId==null){
            result.setMessage("请选择授权角色");
            return result;
        }
        List<Long> grantedResources = roleResourcesDao.getGrantResources(roleId);
        Hashtable<String,Integer> hashtable = new Hashtable<String,Integer>();
        for (Long grantedResourceId : grantedResources) {
            hashtable.put(grantedResourceId+"", 1);
        }
        List<RoleResources> addList = Lists.newArrayList();
        if(StringUtils.isNotBlank(ids)){
            String[] idArray = StringUtil.split(ids);
            for (String id : idArray) {
                if(hashtable.get(id)==null){
                    RoleResources roleResources  = new RoleResources();
                    roleResources.setRoleId(roleId);
                    roleResources.setResourcesId(Long.parseLong(id));
                    addList.add(roleResources);
                }else{
                    hashtable.remove(id);
                }
            }
        }
        if(addList.size()>0){
            roleResourcesDao.insertBatch(addList);
        }
        //剩下的为删除的
        Set<String> deleteIdSet =  hashtable.keySet();
        if(deleteIdSet.size()>0){
            List<RoleResources> deleteList = Lists.newArrayList();
            for (String deleteId : deleteIdSet) {
                RoleResources roleResources  = new RoleResources();
                roleResources.setRoleId(roleId);
                roleResources.setResourcesId(Long.parseLong(deleteId));
                deleteList.add(roleResources);
            }
            roleResourcesDao.deleteBatchByRoleIdAndResourcesId(deleteList);
        }
        result.setIsSuccess(true);
       // User currentUser = userService.selectById(OperateUtils.getCurrentUser().getId());
       // LogUtils.logUser("权限模块","权限设置",currentUser.getId().toString(),"权限设置","角色ID:"+roleId+"权限设置",currentUser.getId().toString());
        return result;
    }
}

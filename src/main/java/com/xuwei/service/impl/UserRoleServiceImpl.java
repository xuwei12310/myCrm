package com.xuwei.service.impl;

import com.xuwei.bean.UserRole;
import com.xuwei.mapper.UserRoleMapper;
import com.xuwei.service.IUserRoleService;
import com.xuwei.util.ServiceResult;
import com.xuwei.util.StringUtil;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;

import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description: 服务实现类
 * @copyright: 福建骏华信息有限公司 (c)2018
 * @createTime: 2018年01月16日 19:57:11
 * @author: xw
 * @version: 1.0
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

    @Resource
    private UserRoleMapper userRoleDao;

    @Override
    public List<Long> getGrantRole(Long userId) {
        return userRoleDao.getGrantRole(userId);
    }
   // @CacheEvict(value={"admin-authorization","admin-menus","admin-tree-menus"},allEntries=true)
    @Transactional
    @Override
    public ServiceResult grantRole(Long userId, String ids) {
        ServiceResult result = new ServiceResult(false);
        if(userId==null){
            result.setMessage("请选择授权用戶");
            return result;
        }
        List<Long> grantedRole = userRoleDao.getGrantRole(userId);
        Hashtable<String,Integer> hashtable = new Hashtable<String,Integer>();
        for (Long grantedResourceId : grantedRole) {
            hashtable.put(grantedResourceId+"", 1);
        }
        List<UserRole> addList = Lists.newArrayList();
        if(StringUtils.isNotBlank(ids)){
            String[] idArray = StringUtil.split(ids);
            for (String id : idArray) {
                if(hashtable.get(id)==null){
                    UserRole userRole  = new UserRole();
                    userRole.setRoleId(Long.parseLong(id));
                    userRole.setUserId(userId);
                    addList.add(userRole);
                }else{
                    hashtable.remove(id);
                }
            }
        }
        if(addList.size()>0){
            userRoleDao.insertBatch(addList);
        }
        //剩下的为删除的
        Set<String> deleteIdSet =  hashtable.keySet();
        if(deleteIdSet.size()>0){
            List<UserRole> deleteList = Lists.newArrayList();
            for (String deleteId : deleteIdSet) {
                UserRole userRole  = new UserRole();
                userRole.setRoleId(Long.parseLong(deleteId));
                userRole.setUserId(userId);
                deleteList.add(userRole);
            }
            userRoleDao.deleteBatchByRoleIdAndUserId(deleteList);
        }
        result.setIsSuccess(true);
        //LogUtils.logUser("系统","职务与权限",OperateUtils.getCurrentUser().getId().toString(),"权限设置",null);
        return result;
    }
	@Override
	public ServiceResult deleteByUserId(String[] idArray) {
		ServiceResult result=new ServiceResult(false);
		if(idArray.length<1){
			result.setMessage("请选择数据行");
			return result;
		}
		for (String string : idArray) {
			long id=Long.valueOf(string);
			userRoleDao.deleteByUserId(id);
		}
		return result;
	}
	
}

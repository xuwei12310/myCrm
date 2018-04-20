/**
 * 
 */
package com.xuwei.service.impl;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authz.permission.WildcardPermission;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;
import com.xuwei.bean.Resources;
import com.xuwei.bean.User;
import com.xuwei.mapper.ResourcesMapper;
import com.xuwei.service.IMainService;
import com.xuwei.service.IUserService;

/**
 * @description:
 * @createTime 2018年2月9日 下午1:07:38
 * @copyright 福建骏华有限公司（c）2017
 * @author xw
 *
 */
@Service
public class MainServiceImpl implements IMainService{
	
	
	@Resource
	private ResourcesMapper resourcesMapper;
	@Resource
	private IUserService userService;
	@Override
	public List<Resources> findRootResourcesByUser(User user) {

		Resources m = new Resources();
		m.setId(1L);
		List<Resources> resources = resourcesMapper.findChildren(m);
		List<Resources> rootResources = Lists.newArrayList(resources);//复制一份，防止下面remove没有权限的资源时移除缓存
		
		List<String> userPermissions = userService.findAuthorities(user.getId());
        Iterator<Resources> iter = rootResources.iterator();
        while (iter.hasNext()) {
            if (!hasPermission(iter.next(), userPermissions)) {
                iter.remove();
            }
        }
		return rootResources;
	}
	public List<Resources> findMenuTreetResources(Long parentId, User user) {
		Resources parent = resourcesMapper.findById(parentId);
		List<Resources> children = resourcesMapper.menuTreeChildren(parent.getId());
		List<String> userPermissions = userService.findAuthorities(user.getId());
		
		List<Resources> childrenTree = Lists.newArrayList(children);//复制一份，防止下面remove没有权限的资源时移除缓存
        Iterator<Resources> iter = childrenTree.iterator();
        while (iter.hasNext()) {
            if (!hasPermission(iter.next(), userPermissions)) {
                iter.remove();
            }
        }
		return childrenTree;
	}
	/**
     * 得到真实的资源标识  即 父亲:儿子
     * @param resource
     * @return
     */
    public String findActualResourceIdentity(Resources resource) {

        if(resource == null) {
            return null;
        }

        StringBuilder s = new StringBuilder(resource.getIdentity());

        boolean hasResourceIdentity = !StringUtils.isEmpty(resource.getIdentity());

        Resources parent = resourcesMapper.findById(resource.getParentId());
        while(parent != null) {
            if(!StringUtils.isEmpty(parent.getIdentity())) {
                s.insert(0, parent.getIdentity() + ":");
                hasResourceIdentity = true;
            }
            parent = resourcesMapper.findById(parent.getParentId());
        }

        //如果用户没有声明 资源标识  且父也没有，那么就为空
        if(!hasResourceIdentity) {
            return "";
        }

        //如果最后一个字符是: 因为不需要，所以删除之
        int length = s.length();
        if(length > 0 && s.lastIndexOf(":") == length - 1) {
            s.deleteCharAt(length - 1);
        }
        return s.toString();
    }
	
	private boolean hasPermission(String permission, String actualResourceIdentity) {

        //得到权限字符串中的 资源部分，如a:b:create --->资源是a:b
		String permissionResourceIdentity=permission;
		if(permission.contains(":")){
			
			permissionResourceIdentity = permission.substring(0, permission.lastIndexOf(":"));
		}

        //如果权限字符串中的资源 是 以资源为前缀 则有权限 如a:b 具有a:b的权限
        if(permissionResourceIdentity.equals(actualResourceIdentity)) {
            return true;
        }
        //模式匹配
        WildcardPermission p1 = new WildcardPermission(permissionResourceIdentity);
        WildcardPermission p2 = new WildcardPermission(actualResourceIdentity);

        return p1.implies(p2) || p2.implies(p1);
    }
	private boolean hasPermission(Resources resources, List<String> userPermissions) {
        String actualResourceIdentity = findActualResourceIdentity(resources);
        if (StringUtils.isEmpty(actualResourceIdentity)) {
            return true;
        }

        for (String permission : userPermissions) {
            if (hasPermission(permission, actualResourceIdentity)) {
            	System.out.println(resources.getName());
                return true;
            }
        }
        return false;
    }
	
}

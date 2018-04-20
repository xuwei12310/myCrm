package com.xuwei.controller;


import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xuwei.util.GlobalConstants;
import com.xuwei.util.ReflectUtils;

/**
 * @description: 控制器基类
 * @copyright: 福建骏华信息有限公司 (c)2016
 * @createTime: 2016年8月12日下午7:46:53
 * @author：lys
 * @version：1.0
 */
public  class BaseController<M extends Serializable> {
	/**
     * 实体类型
     */
    protected final Class<M> entityClass;
    /**
     * 视图的前缀
     */
    protected String viewPrefix;
    /**
     * 资源前缀
     */
    private String resourceIdentity;
    
    protected BaseController() {
        this.entityClass = ReflectUtils.findParameterizedType(getClass(), 0);
        setViewPrefix(defaultViewPrefix());
    }
    /**
     * 当前模块 视图的前缀
     * 默认
     * 1、获取当前类头上的@RequestMapping中的value作为前缀
     * 2、如果没有就使用当前模型小写的简单类名
     */
    public void setViewPrefix(String viewPrefix) {
        this.viewPrefix = viewPrefix;
    }
    
    public String getViewPrefix() {
        return viewPrefix;
    }
    /**
     * @description: 默认前缀
     * @createTime: 2016年8月18日 下午5:34:37
     * @author: lys
     * @return
     */
    protected String defaultViewPrefix() {
        String currentViewPrefix = "";
        RequestMapping requestMapping = AnnotationUtils.findAnnotation(getClass(), RequestMapping.class);
        if (requestMapping != null && requestMapping.value().length > 0) {
            currentViewPrefix = requestMapping.value()[0];
        }

        if (StringUtils.isEmpty(currentViewPrefix)) {
            currentViewPrefix = this.entityClass.getSimpleName();
        }
        return currentViewPrefix;
    }
    
    /**
	 * @description  权限前缀：如sys:user
	 * @time 2015-11-22 下午8:07:29
	 * @author 以宋
	 * @param resourceIdentity
	 */
    public void setResourceIdentity(String resourceIdentity) {
        if (!StringUtils.isEmpty(resourceIdentity)) {
        	this.resourceIdentity=resourceIdentity;
        }
    }
	/**
	 * @description: 判断当前用户是否有权限permission
	 * @createTime: 2016年4月28日 上午9:30:31
	 * @author: lys
	 * @return
	 */
	public boolean hasPermission(String permission){
		return SecurityUtils.getSubject().isPermitted(permission);
	}
	/**
	 * @description: 是否有添加权限
	 * @createTime: 2016年8月18日 下午4:58:14
	 * @author: lys
	 * @return
	 */
	public boolean hasCreatePermission() {
		return hasPermissionByOpt("create");
	}
	/**
	 * @description: 是否有修改权限
	 * @createTime: 2016年8月18日 下午5:01:36
	 * @author: lys
	 * @return
	 */
	public boolean hasUpdatePermission() {
		return hasPermissionByOpt("update");
	}
	/**
	 * @description: 是否有删除权限
	 * @createTime: 2016年8月18日 下午5:02:17
	 * @author: lys
	 * @return
	 */
	public boolean hasDeletePermission() {
		return hasPermissionByOpt("delete");
	}
	/**
	 * @description: 是否有查看权限
	 * @createTime: 2016年8月18日 下午5:02:17
	 * @author: lys
	 * @return
	 */
	public boolean hasViewPermission() {
		return hasPermissionByOpt("view");
	}
	/**
	 * @description: 是否有权限（根据操作）
	 * @createTime: 2016年8月18日 下午5:34:09
	 * @author: lys
	 * @param opt
	 * @return
	 */
	public boolean hasPermissionByOpt(String opt) {
		if(StringUtils.isNotBlank(this.resourceIdentity)&&StringUtils.isNotBlank(opt)){
			String resourcePermission = this.resourceIdentity + ":"+opt;
			return hasPermission(resourcePermission);
		}
		return	false;
	}
	public String getResourceIdentity() {
		return resourceIdentity;
	}
	/**
	 * @description: 取得项目的根路径
	 * @createTime: 2016年10月10日 下午3:25:20
	 * @author: lys
	 * @param request
	 * @return
	 */
	public String getBasePath(HttpServletRequest request) {
		return request.getSession().getServletContext().getRealPath(
				"/")+"/";
	}
	/**
	 * 
	 * @description: 获取模板路径
	 * @createTime: 2017年7月28日 下午2:01:30
	 * @author: lxb
	 * @param request
	 * @return
	 */
	public String getTemplatePath(HttpServletRequest request) {
		return request.getSession().getServletContext().getRealPath(
				GlobalConstants.TEMPLATE_BATHPATH);
	}
	
}

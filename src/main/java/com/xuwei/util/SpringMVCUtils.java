package com.xuwei.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @description: springMVC工具类
 * @copyright: 福建骏华信息有限公司 (c)2016
 * @createTime: 2016年8月22日下午5:12:33
 * @author：lys
 * @version：1.0
 */
public class SpringMVCUtils {
	/**
	 * @description: 取得request
	 * @createTime: 2016年8月22日 下午5:13:40
	 * @author: lys
	 * @return
	 */
	public static HttpServletRequest getRequest(){
		return ((ServletRequestAttributes) RequestContextHolder
	            .getRequestAttributes()).getRequest();
	}
	/**
	 * @description: 取得session
	 * @createTime: 2016年8月22日 下午5:15:05
	 * @author: lys
	 * @return
	 */
	public static HttpSession getSession(){
		if(SpringMVCUtils.getRequest()==null){
			return null;
		}
		return SpringMVCUtils.getRequest().getSession();
	}
}

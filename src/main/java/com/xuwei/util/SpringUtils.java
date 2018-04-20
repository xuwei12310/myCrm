package com.xuwei.util;

import java.util.Locale;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.servlet.LocaleResolver;
/**
 * @Description:Spring工具类
 * @Copyright: 福州骏华信息有限公司 (c)2015
 * @Created Date : 2015-1-21
 * @author lys
 * @vesion 1.0
 */
@Component("springUtils")
@Lazy(false)
public final class SpringUtils implements DisposableBean,ApplicationContextAware {
	private static ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext) {
		SpringUtils.applicationContext = applicationContext;
	}

	public void destroy() {
		applicationContext = null;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static Object getBean(String name) {
		Assert.hasText(name);
		return applicationContext.getBean(name);
	}

	public static <T> T getBean(String name, Class<T> type) {
		Assert.hasText(name);
		Assert.notNull(type);
		return applicationContext.getBean(name, type);
	}

	public static String getMessage(String code, Object[] args) {
		LocaleResolver localLocaleResolver = getBean("localeResolver", LocaleResolver.class);
		Locale localLocale = localLocaleResolver.resolveLocale(null);
		return applicationContext.getMessage(code, args, localLocale);
	}
	/**
	 * @Description:从国际化文件中读取信息
	 * @Created: 2015-1-21 下午8:51:37
	 * @author lys
	 * @param code ：国际化文件中的key
	 * @param obj
	 * @return
	 */
	public static String getMessage(String code, Object obj) {
		LocaleResolver localLocaleResolver = getBean("localeResolver", LocaleResolver.class);
		Locale localLocale = localLocaleResolver.resolveLocale(null);
		Object[] args = { obj };
		return applicationContext.getMessage(code, args, localLocale);
	}
	
	/**
	 * 
	 * @Description: 从国际化文件中读取信息
	 * @Create: 2015-1-22 上午10:11:15
	 * @author longweier
	 * @update logs
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public static String getMessage(String code) {
		Object[] args = {};
		return getMessage(code, args);
	}
}

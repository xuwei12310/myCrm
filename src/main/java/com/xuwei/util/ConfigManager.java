package com.xuwei.util;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取配置文件的工具类-单例模式: 静态内部类=懒汉加饿汉
 * @author Administrator
 *
 */
public class ConfigManager {
	/**
	 * 唯一的实例。懒汉推迟上吃
	 */
	private static ConfigManager configManager;
	private static Properties props;

	/**
	 * 单例要素一：私有构造器——读取数据库配置文件
	 */
	private ConfigManager() {
		readConfig();
	}

	/**
	 * 单例要素三：全局访问点
	 */
	public static ConfigManager getInstance() {
		configManager = SingletonHelper.INSTANCE;
		return configManager;
	}
	
	/**
	 * 单例要素二：自行实例化
	 * @author super
	 *
	 */
	public static class SingletonHelper {
		/**
		 * 静态内部类，静态常量中，自行实例化
		 */
		private static final ConfigManager INSTANCE = new ConfigManager();
	}



	public String getValue(String prefix, String key) {
		if (prefix==null || prefix.trim().isEmpty()) {
			return props.getProperty(key);
		} else {
			return props.getProperty(prefix + "_" + key);
		}

	}

	public String getValue(String key) {
		return getValue(null, key);
	}
	

	/**
	 * 读取配置文件——初始化时调用
	 */
	private void readConfig() {
		String configFile = "database.properties";
		props = new Properties();
		InputStream is = null;

		try {
			//is = ClassLoader.getSystemResourceAsStream(configFile);
			is = ConfigManager.class.getClassLoader().getResourceAsStream(configFile);
			//is = new java.io.FileInputStream(Thread.currentThread().getContextClassLoader().getResource("").getPath() + configFile);  
			
			props.load(is);
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(ConfigManager.class.getName() + "出现异常：" + e.getMessage());
			e.printStackTrace();
		}
	}

}

package com.xuwei.fun;

public abstract class DsBuilder {
	
	public abstract javax.sql.DataSource build();

	public abstract javax.sql.DataSource build(String name);

	/**
	 * 根据builder和名称创建数据源
	 * 
	 * @param builder
	 * @param name
	 * @return
	 */
	public static javax.sql.DataSource getDataSource(DsBuilder builder, String name) {
		return builder.build(name);
	}
	
	/**
	 * 根据dsType和名称创建数据源
	 * 
	 * @param dsType
	 * @param name
	 * @return
	 */
	public static javax.sql.DataSource getDataSource(DsType dsType, String name) {
		DsBuilder builder = getDefaultBuilder(dsType);
		return builder.build(name);
	}
	
	
	
	

	/**
	 * 获取默认的数据源构造器： DsC3P0Builder/DsDBCPBuilder/DsJNDIBuilder，三者之一
	 * 
	 * @param dsType
	 * @return
	 */
	public static DsBuilder getDefaultBuilder(DsType dsType) {

		String packageName = DsBuilder.class.getPackage().getName();

		DsBuilder builder = null;

		Class<?> clazz = null;
		try {
			String pattern = packageName + ".Ds{0}Builder";
			String className = java.text.MessageFormat.format(pattern, dsType.toString());

			clazz = Class.forName(className);
			Object obj = clazz.newInstance();

			if (obj != null) {
				builder = (DsBuilder) obj;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return builder;
	}

}

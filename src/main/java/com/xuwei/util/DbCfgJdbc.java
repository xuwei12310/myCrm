package com.xuwei.util;

import java.sql.DriverManager;

public class DbCfgJdbc {

	public static java.sql.Connection getConnection() {
		System.out.println("DriverManager.");
		java.sql.Connection conn = null;
		try {

			String vDriverClass = null;
			String vUrl = null;
			String vUsername = null;
			String vPassword = null;

			/* mysql的四大金刚 */
			// vDriverClass = "com.mysql.jdbc.Driver";
			// vUrl =
			// "jdbc:mysql://192.168.1.250:23306/blog?useSSL=true&characterEncoding=utf8";
			// vUsername = "root";
			// vPassword = "root";

			/* oracle的四大金刚 */

			// vDriverClass = "oracle.jdbc.driver.OracleDriver";
			// vUrl = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
			// vUsername = "edu";
			// vPassword = "edu";

			vDriverClass = ConfigManager.getInstance().getValue("oracle", "driver");
			vUrl = ConfigManager.getInstance().getValue("oracle", "url");
			vUsername = ConfigManager.getInstance().getValue("oracle", "username");
			vPassword = ConfigManager.getInstance().getValue("oracle", "password");

			System.out.println(vDriverClass);
			Class.forName(vDriverClass);

			conn = DriverManager.getConnection(vUrl, vUsername, vPassword);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("DbCfgJdbc出现异常：" + e.getMessage());
			e.printStackTrace();
		}
		return conn;
	}

}

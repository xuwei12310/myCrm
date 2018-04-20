package com.xuwei.util;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 使用标准的DriverManager来获取连接，效率较慢。
 * @author super
 *
 */
public class DbUtilStd {

	public static java.sql.Connection getConn() {

		java.sql.Connection conn = null;


		conn = DbCfgJdbc.getConnection();

		return conn;
	}

	public static void close(Connection conn) {
		try {
			if (!conn.isClosed()) {
				conn.close();
				// DbUtils.close(conn);
				//System.out.println("conn closed.");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

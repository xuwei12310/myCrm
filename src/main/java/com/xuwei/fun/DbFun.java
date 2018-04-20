package com.xuwei.fun;


import java.sql.*;
import java.util.*;



/**
 * 数据库辅助类
 * 
 * @author super
 *
 */
public class DbFun {

	/**
	 * 操作的日志的LOGGER_NAME，默认为DBLog。
	 */
	public final static String LOGGER_NAME = "DBLog";

	/**
	 * 获得连接对象: 传入四大金刚
	 * 
	 * @param pDriver
	 *            数据库驱动类名
	 * @param pUrl
	 *            数据库连接Url
	 * @param pUserName
	 *            数据库用户名
	 * @param pPassword
	 *            数据库密码
	 * @return 连接对象
	 */
	public static Connection getConnection(String pDriver, String pUrl, String pUserName, String pPassword) {

		Connection conn = null;

		try {
			Class.forName(pDriver);// 这句最好放到静态块，以便只执行一次加载
			conn = DriverManager.getConnection(pUrl, pUserName, pPassword);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}

		return conn;

	}

	/**
	 * 获得连接对象: 传入四大金刚——调用getConnection()
	 * 
	 * @param pDriver
	 *            数据库驱动类名
	 * @param pUrl
	 *            数据库连接Url
	 * @param pUserName
	 *            数据库用户名
	 * @param pPassword
	 *            数据库密码
	 * @return 连接对象
	 */
	public static Connection getConn(String pDriver, String pUrl, String pUserName, String pPassword) {

		return getConnection(pDriver, pUrl, pUserName, pPassword);
	}

	/**
	 * 关闭连接对象
	 * 
	 * @param conn
	 *            欲关闭的连接对象
	 */
	public static void close(Connection conn) {
		if (conn != null) {
			try {
				// 关闭Connection数据库连接对象
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 关闭三大对象
	 * 
	 * @param conn
	 * @param stmt
	 * @param rs
	 */
	public static void close(Connection conn, Statement stmt, ResultSet rs) {

		// 如果rs不为空，则尝试关闭rs，以便释放资源
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {

			}
		}
		// 如果stmt不为空，则尝试关闭stmt，以便释放资源
		if (stmt != null) {
			try {
				stmt.close();
			} catch (Exception e) {
			}
		}
		// 如果conn不为空，则尝试关闭conn，以便释放资源
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 释放三大对象： 调用//close()
	 * 
	 * @param conn
	 * @param stmt
	 * @param rs
	 */
	public static void release(Connection conn, Statement stmt, ResultSet rs) {
		close(conn, stmt, rs);
	}

	/**
	 * 执行增删改语句
	 * 
	 * @param conn
	 *            连接对象
	 * @param sql
	 *            传入的预设 SQL语句
	 * @param params
	 *            问号参数列表
	 * @return 受影响行数
	 */
	public static int update(Connection conn, String sql, Object... params) {
		int result = 0;
		PreparedStatement pstmt = null;
		try {

			pstmt = conn.prepareStatement(sql);// 获得预设语句对象

			if (params != null) {
				// 设置参数列表
				for (int i = 0; i < params.length; i++) {
					// 因为问号参数的索引是从1开始，所以是i+1
					// preStmt.setObject(i + 1, params[i] +
					// "");//将所有值都转为字符串形式，好让setObject成功运行 ，是否要转为字符串形式？
					pstmt.setObject(i + 1, params[i]);// 不转为字符串形式的版本
				}
			}
			result = pstmt.executeUpdate(); // 执行更新，并返回影响行数

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			// close(null, pstmt, conn);
		}
		return result;
	}

	/**
	 * 批次执行SQL语句
	 * 
	 * @param conn
	 * @param sqlList
	 * @return
	 */
	public static int[] batch(Connection conn, List<String> sqlList) {
		return batch(conn, sqlList.toArray(new String[] {}));
	}

	/**
	 * 批次执行SQL语句
	 * 
	 * @param conn
	 * @param sqlArray
	 * @return
	 */
	public static int[] batch(Connection conn, String[] sqlArray) {
		int[] num = null;
		Statement stmt = null;

		try {
			stmt = conn.createStatement();
			for (String sql : sqlArray) {
				stmt.addBatch(sql);
			}
			num = stmt.executeBatch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// close(null, stmt, conn);
		}

		return num;
	}

	/**
	 * 批次执行SQL语句，主要是数据有变化
	 * 
	 * @param conn
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public static int[] batch(Connection conn, String sql, Object[]... params) throws SQLException {
		int[] num = null;
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sql);
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					Object[] rowParams = params[i];
					if (rowParams != null) {
						for (int k = 0; k < rowParams.length; k++) {
							Object obj = rowParams[k];
							stmt.setObject(k + 1, obj);
						}
					}
					stmt.addBatch();
				}
			}

			num = stmt.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// close(null, stmt, conn);
		}
		return num;
	}

	/**
	 * 执行query
	 * 
	 * @param conn
	 *            连接对象
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            问号参数列表
	 * @return 查询后的结果
	 */
	public static ResultSet query(Connection conn, String sql, Object... params) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			pstmt = conn.prepareStatement(sql);// 获得预设语句对象
			if (params != null) {
				// 设置参数列表
				for (int i = 0; i < params.length; i++) {
					// 因为问号参数的索引是从1开始，所以是i+1
					// preStmt.setObject(i + 1, params[i] +
					// "");//将所有值都转为字符串形式，好让setObject成功运行 ，是否要转为字符串形式？
					pstmt.setObject(i + 1, params[i]);// 不转为字符串形式的版本
				}
			}

			// 执行查询
			rs = pstmt.executeQuery();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// close(rs, pstmt, conn);
		}

		return rs;
	}

	/* --------------------------------------------------------------------- */

	/* --------------------------------------------------------------------- */

	/**
	 * 执行query
	 * 
	 * @param conn
	 *            连接对象
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            问号参数列表
	 * @return 查询后的结果
	 */
	public static Object queryScalar(Connection conn, String sql, Object... params) {

		Object obj = null;
		PreparedStatement pstmt = null;
		

		try {

			pstmt = conn.prepareStatement(sql);// 获得预设语句对象
			if (params != null) {
				// 设置参数列表
				for (int i = 0; i < params.length; i++) {
					// 因为问号参数的索引是从1开始，所以是i+1
					// preStmt.setObject(i + 1, params[i] +
					// "");//将所有值都转为字符串形式，好让setObject成功运行 ，是否要转为字符串形式？
					pstmt.setObject(i + 1, params[i]);// 不转为字符串形式的版本
				}
			}

			// 执行查询
			ResultSet rs = pstmt.executeQuery();
			if(rs!=null && rs.next()){
				
				obj = rs.getObject(1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// close(rs, pstmt, conn);
		}

		return obj;
	}


	/**
	 * 执行query
	 * 
	 * @param conn
	 *            连接对象
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            问号参数列表
	 * @return 查询后的结果
	 */
	public static int queryScalarInt(Connection conn, String sql, Object... params) {

		int obj = 0;
		PreparedStatement pstmt = null;
		

		try {

			pstmt = conn.prepareStatement(sql);// 获得预设语句对象
			if (params != null) {
				// 设置参数列表
				for (int i = 0; i < params.length; i++) {
					// 因为问号参数的索引是从1开始，所以是i+1
					// preStmt.setObject(i + 1, params[i] +
					// "");//将所有值都转为字符串形式，好让setObject成功运行 ，是否要转为字符串形式？
					pstmt.setObject(i + 1, params[i]);// 不转为字符串形式的版本
				}
			}

			// 执行查询
			ResultSet rs = pstmt.executeQuery();
			if(rs!=null && rs.next()){
				
				obj = rs.getInt(1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// close(rs, pstmt, conn);
		}

		return obj;
	}


}

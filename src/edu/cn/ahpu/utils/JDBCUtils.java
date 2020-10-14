package edu.cn.ahpu.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 
 * @Description JDBC工具类
 * @author CodeBee Email:CodeBee@gmial.com
 * @version 
 * @date 2020年10月13日下午4:33:03
 *
 */
public class JDBCUtils {

	/**
	 * 
	 * @Description 获取数据库连接
	 * @author CodeBee
	 * @date 2020年10月13日下午4:33:10
	 * @return 数据库连接对象
	 * @throws Exception
	 */
	public static Connection getConnection() throws Exception {
		// 3.1、用于为读取与数据库相关的配置文件信息做准备
		InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
		Properties pros = new Properties();
		pros.load(is);
		// 3.2、读取配置文件中的与数据库相关的信息，并将读取的参数传递到DriverManger的构造器中
		String user = pros.getProperty("user");
		String password = pros.getProperty("password");
		String url = pros.getProperty("url");
			// 为注册数据库做准备
		String driverClass = pros.getProperty("driverClass");
		
		// 1、注册数据驱动
		Class.forName(driverClass);
		
		// 2、通过DriverManager来获取连接
		Connection conn = DriverManager.getConnection(url, user, password);
		return conn ;
	}
	
	/**
	 * 
	 * @Description 数据库资源的关闭
	 * @author CodeBee
	 * @date 2020年10月13日下午4:34:35
	 * @param conn
	 * @param ps
	 */
	public static void closeResource(Connection conn,Statement ps) {
		// 资源的关闭
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 
	 * @Description 数据库资源的关闭
	 * @author CodeBee
	 * @date 2020年10月13日下午4:35:22
	 * @param conn
	 * @param ps
	 * @param rs
	 */
	public static void closeResource(Connection conn,Statement ps,ResultSet rs) {
		// 资源的关闭
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}

package edu.cn.ahpu2.PreparedStatement;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.junit.Test;

import edu.cn.ahpu.utils.JDBCUtils;

public class CUDTest {
	
	@Test
	public void testInsert(){
		Connection conn =null ;
		PreparedStatement ps =null ;
		try {
			InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
			Properties pros = new Properties();
			pros.load(is);
			String user = pros.getProperty("user");
			String password = pros.getProperty("password");
			String driverClass = pros.getProperty("driverClass");
			String url = pros.getProperty("url");
			Class.forName(driverClass);
			conn = DriverManager.getConnection(url, user, password);
			
			System.out.println(conn);
			
			
			String sql = "insert into customers(name,email,birth) values(?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, "哪吒");
			ps.setString(2, "nezha@gmail.com");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse("1000-01-01");
			ps.setDate(3, new java.sql.Date(date.getTime()));
			ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
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
	}
	
	@Test
	public void testUpdate(){
		Connection conn = null;
		PreparedStatement ps = null ;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "update customers set name = ? where id = ?";
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, "金吒");
			ps.setInt(2, 20);
			
			ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.closeResource(conn, ps);
		}
	}
	
	/**
	 * 测试CRUDTest2中的增删改通用操作的方法
	 */
	@Test
	public void testDelete() {
		String sql = "delete from customers where id = ?" ;
		CUDTest2.update(sql, 21);
	}
}

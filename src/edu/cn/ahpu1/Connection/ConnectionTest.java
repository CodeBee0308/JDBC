package edu.cn.ahpu1.Connection;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Properties;

import org.junit.Test;

/**
 * 
* @Description  获取数据库连接
* @author CodeBee Email:CodeBee@gmial.com
* @version
* @date 2020年10月12日下午9:53:09
*
 */

public class ConnectionTest {
	
	/**
	 *
	 * @throws Exception
	 */
	@Test
	public void testConnection01() throws Exception{
		//1、 获取Java原生API中Driver接口的实现类对象
		Driver driver = new com.mysql.cj.jdbc.Driver();
		
		// url:http://localhost:8080/gmall/keyboard.jpg
		// jdbc:mysql:协议
		// localhost:ip地址
		// 3306：默认mysql的端口号
		// test:test数据库
		String url = "jdbc:mysql://localhost:3306/test?serverTimezone=GMT%2B8";
		// 将用户名和密码封装在Properties中
		Properties info = new Properties();
		info.setProperty("user", "root") ;
		info.setProperty("password", "password");
		//2、调用方法
		Connection conn = driver.connect(url, info);
		System.out.println(conn);
		
	}
	
	@Test
	public void testConnection02() throws  Exception {
		// 获取Driver实现类对象：使用反射
		Class cla = Class.forName("com.mysql.cj.jdbc.Driver");
		Driver driver = (Driver) cla.newInstance();
		
		
		String url = "jdbc:mysql://localhost:3306/test?serverTimezone=GMT%2B8";
		
		Properties info = new Properties();
		info.setProperty("user", "root") ;
		info.setProperty("password", "password") ;
		
		// 获取连接
		Connection conn = driver.connect(url, info);
		System.out.println(conn) ;
		
	}
	
	@Test
	public void testConnection03() throws Exception {
		
		Class cla = Class.forName("com.mysql.cj.jdbc.Driver") ;
		Driver driver = (Driver) cla.newInstance() ;
		
		String url = "jdbc:mysql://localhost:3306/test?serverTimezone=GMT%2B8" ;
		String user = "root" ;
		String password = "password" ;
		
		DriverManager.registerDriver(driver);
		
		Connection conn = DriverManager.getConnection(url, user, password) ;
		System.out.println(conn);
		
		
	}
	
	@Test
	public void testConnection04() throws Exception {
		String url = "jdbc:mysql://localhost:3306/test?serverTimezone=GMT%2B8" ;
		String user = "root" ;
		String password = "password" ;
		
		Class.forName("com.mysql.cj.jdbc.Driver") ;
		
		Connection conn = DriverManager.getConnection(url, user, password) ;
		System.out.println(conn);
		
	}
	
	@Test
	public void testConnection05() throws Exception {
		InputStream is = ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties") ;
		Properties pros = new Properties();
		pros.load(is);
		
		String user = pros.getProperty("user");
		String password = pros.getProperty("password");
		String driverClass = pros.getProperty("driverClass");
		String url = pros.getProperty("url");
		
		Class.forName(driverClass) ;
		Connection conn = DriverManager.getConnection(url, user, password) ;
		System.out.println(conn);
		
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

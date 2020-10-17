package edu.cn.ahpu1.Connection;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Properties;

import org.junit.Test;

/**
 * 
 * @Description 获取数据库连接的五种方式
 * @author CodeBee Email:CodeBee@gmial.com
 * @version
 * @date 2020年10月13日下午4:54:22
 *
 */
public class ConnectionTest {
	
	/**
	 * 
	 * @Description 方式一
	 * @author CodeBee
	 * @date 2020年10月14日上午10:56:16
	 * @throws Exception
	 */
	@Test
	public void testConnection01() throws Exception{
		//1、 获取Java原生API中Driver接口的实现类对象
		// com.mysql.cj.jdbc.Driver()为java.sql.Driver接口的实现类
		// 子类对象指向父类的引用，多态性，所以此时driver在编译期调用的父类中的抽象方法，到运行时再调用子类中重写的方法 ；
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
		//2、编译器调用的是父接口中的抽象方法，运行时调用的是实现类中重写的方法 ；
		Connection conn = driver.connect(url, info);
		System.out.println(conn);
		
	}
	
	/**
	 * 
	 * @Description 方式二：解决方式一中使用了非Java原生的API(com.mysql.cj.jdbc.Driver())
	 * @author CodeBee
	 * @date 2020年10月14日上午10:56:32
	 * @throws Exception
	 */
	@Test
	public void testConnection02() throws  Exception {
		// 通过反射的方式获取实现类的父类
		Class cla = Class.forName("com.mysql.cj.jdbc.Driver");
		// 利用获取到的父类来实例化子类对象
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
	
	/**
	 * 
	 * @Description 获取数据库连接的最终方案
	 * @author CodeBee
	 * @date 2020年10月17日下午9:41:42
	 * @throws Exception
	 */
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

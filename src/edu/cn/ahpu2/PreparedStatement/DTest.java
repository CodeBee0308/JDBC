package edu.cn.ahpu2.PreparedStatement;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.Test;

import edu.cn.ahpu.bean.Customer;
import edu.cn.ahpu.utils.JDBCUtils;

/**
 * 针对customers表的查询操作
* @Description
* @author CodeBee Email:CodeBee@gmial.com
* @version
* @date 2020年10月12日下午5:08:36
*
 */
public class DTest {
	
	@Test
	public void testQuery1(){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "select id,name,email,birth from customers where id = ?" ;
			ps = conn.prepareStatement(sql);
			ps.setInt(1, 5);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				
				int id = rs.getInt(1) ;
				String name = rs.getString(2) ;
				String email = rs.getString(3) ;
				Date birth = rs.getDate(4) ;
				
				Customer customer = new Customer(id, name, email, birth);
				System.out.println(customer);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.closeResource(conn, ps,rs);
		}

	}
	
	/**
	 * 
	* @Description 测试DTutils2：针对Customer的通用查询操作
	* @author CodeBee
	* @date 2020年10月12日下午8:49:41
	 */
	@Test
	public void testQuery2() {
		String sql = "select id,name,email,birth from customers where id = ?" ;
		Customer customer = DTest2.QueryForCustomer(sql, 12) ;
		System.out.println(customer);
	}

}

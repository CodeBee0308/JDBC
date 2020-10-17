package edu.cn.ahpu2.PreparedStatement;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import org.junit.Test;

import edu.cn.ahpu.bean.Order;
import edu.cn.ahpu.utils.JDBCUtils;

public class RTest3 {
	
	
	/**
	 * 
	* @Description 针对order表的特定sql语句的查询操作
	* @author CodeBee
	* @date 2020年10月12日下午9:28:15
	 */
	@Test
	public void QueryForOrder1(){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "select order_id,order_name,order_date from `order` where order_id = ?" ;
			ps = conn.prepareStatement(sql);
			ps.setInt(1, 2);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				int order_id = rs.getInt(1);
				String order_name = rs.getString(2);
				Date order_date = rs.getDate(3);
				
				Order order = new Order(order_id, order_name, order_date);
				System.out.println(order);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.closeResource(conn, ps,rs);
		}
	}
	
	/**
	 * 
	* @Description 针对order单行的的通用查询操作
	* @author CodeBee
	* @date 2020年10月12日下午9:28:59
	* @param sql
	* @param args
	* @return
	* @throws Exception
	 */
	public Order QueryForOrder2(String sql,Object...args){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConnection();
			ps = conn.prepareStatement(sql);
			for(int i = 0;i < args.length;i++) {
				ps.setObject(i + 1, args[i]);
			}
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount() ;
			
			if(rs.next()) {
				Order order = new Order();
				for(int i = 0;i  < columnCount;i++) {
					Object columnValue = rs.getObject(i + 1) ;
					
					String columnLabel = rsmd.getColumnLabel(i + 1) ;
					
					Field field = Order.class.getDeclaredField(columnLabel);
					field.setAccessible(true);
					field.set(order, columnValue);
				}
				return order ;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.closeResource(conn, ps, rs);
		}
		return null  ;
	}

	@Test
	public void testQuery() {
		String sql = "select order_id orderId,order_name orderName,order_date orderDate from `order` where order_id = ?" ;
		Order order = QueryForOrder2(sql, 2) ;
		System.out.println(order);
		
	}
}

package edu.cn.ahpu2.PreparedStatement;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import edu.cn.ahpu.bean.Customer;
import edu.cn.ahpu.utils.JDBCUtils;

/**
 * 
* @Description
* @author CodeBee Email:CodeBee@gmial.com
* @version
* @date 2020年10月12日下午7:44:57
*
 */
public class DTest2 {
	
	public static Customer QueryForCustomer(String sql,Object...args) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConnection();
			ps = conn.prepareStatement(sql);
			
			for(int i = 0;i < args.length; i ++) {
				ps.setObject(i + 1, args[i]);
			}
			
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int count = rsmd.getColumnCount();
			
			if(rs.next()) {
				Customer customer = new Customer();
				for (int i = 0; i < count; i++) {
					Object columnValue = rs.getObject(i + 1) ;
					
					String columnName = rsmd.getColumnName(i + 1);
					Field field = Customer.class.getDeclaredField(columnName);
					field.setAccessible(true);
					field.set(customer, columnValue);
				}
				
				return customer ;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.closeResource(conn, ps,rs);
		}
		return null ;
	}

}

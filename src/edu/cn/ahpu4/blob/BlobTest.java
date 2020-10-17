package edu.cn.ahpu4.blob;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.Test;

import edu.cn.ahpu.bean.Customer;
import edu.cn.ahpu.utils.JDBCUtils;

/**
 * 
 * @Description
 * @author CodeBee Email:CodeBee@gmial.com
 * @version
 * @date 2020年10月17日下午8:15:32
 *
 */
public class BlobTest {
	
	/**
	 * 
	 * @Description 向Customer数据表中填充blob类型的数据
	 * @author CodeBee
	 * @throws Exception 
	 * @date 2020年10月17日下午8:20:02
	 */
	@Test
	public void testInsert(){
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = JDBCUtils.getConnection();
			
			String sql = "insert into customers(name,email,birth,photo) values(?,?,?,?)";
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, "钢铁侠");
			ps.setString(2, "stark@gmail.com");
			ps.setObject(3, "1956-10-1");
			
			FileInputStream fis = new FileInputStream(new File("src/edu/cn/ahpu4/blob/girl3.jpg"));
			ps.setBlob(4, fis);
			
			ps.execute() ;
			System.out.println("插入数据成功");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.closeResource(conn, ps);
		}
	}
	
	/**
	 * 
	 * @Description 读取Customers数据表中的blob类型的数据
	 * @author CodeBee
	 * @throws Exception 
	 * @date 2020年10月17日下午8:43:19
	 */
	@Test
	public void testQuery(){
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		InputStream is = null ;
		FileOutputStream fos = null ;
		try {
			conn = JDBCUtils.getConnection();
			
			String sql = "select id,name,email,birth,photo from customers where id = ?";
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1,22);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				java.sql.Date birth = (Date) rs.getObject("birth");
				Customer cust = new Customer(id, name, email, birth) ;
				System.out.println(cust);
				
				Blob photo = rs.getBlob("photo");
				is = photo.getBinaryStream();
				
				fos = new FileOutputStream("girl.jpg") ;
				
				byte[] buffer = new byte[1024] ;
				int len ;
				while((len = is.read(buffer)) != -1 ) {
					fos.write(buffer,0,len);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			JDBCUtils.closeResource(conn, ps, rs);
		}
		
	}

}

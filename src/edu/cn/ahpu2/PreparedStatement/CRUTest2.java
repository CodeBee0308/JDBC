package edu.cn.ahpu2.PreparedStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;

import edu.cn.ahpu.utils.JDBCUtils;

public class CRUTest2 {
	
	public static void update(String sql,Object...args) {
		Connection conn = null;
		PreparedStatement ps = null ;
		try {
			conn = JDBCUtils.getConnection();
			
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < args.length; i++) {
				ps.setObject(i + 1, args[i]);
			}
			ps.execute();
			System.out.println("操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.closeResource(conn, ps);
		}
	}

}

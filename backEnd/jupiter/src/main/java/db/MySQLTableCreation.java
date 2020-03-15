package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQLTableCreation {

	// Run this as Java application to reset the database.
	public static void main(String[] args) {
		try {
			System.out.println("Connecting to " + MySQLDBUtil.URL);
			
			//jdbc 官方写法：建立driver的对象-- 注册driver
			Class.forName("com.mysql.cj.jdbc.Driver").getConstructor().newInstance();
			
			//用上一句建立的driver的getConnection 方法建立连接
			Connection conn = DriverManager.getConnection(MySQLDBUtil.URL);
			
			if (conn == null) {
				return;
			}
			
			conn.close();
			System.out.println("Import done successfully");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

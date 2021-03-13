package training.assg10;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {

static Connection conn = null;
	
	public static Connection establishConnection() {
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/contactdb","root","");
			System.out.println("Connection established!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	public static void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

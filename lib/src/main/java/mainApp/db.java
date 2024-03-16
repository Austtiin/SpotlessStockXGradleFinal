//Purpose: To connect to the database and retrieve the data from the inventory table.
package mainApp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import logger.LoggerStockX;

public class db {

LoggerStockX logger = new LoggerStockX();

	public db() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/spotlessstockx", "root", "root");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from inventory");
			while (rs.next())
				System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

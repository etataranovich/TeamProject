package com.markava.itcompany.connectionpool;

import java.sql.*;

public class Connect {

	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/itdb?serverTimezone=UTC&useSSL=false";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "1234";

	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		// Statement stmt = null;
		try {
			// STEP 2: Register JDBC driver
			Class.forName(JDBC_DRIVER);
			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT id, name, last_name FROM employee";
			ResultSet rs = stmt.executeQuery(sql);

			// STEP 5: Extract data from result set
			while (rs.next()) {
				// Retrieve by column name
				int id = rs.getInt("id");
				String first = rs.getString("name");
				String last = rs.getString("last_name");

				// Display values
				System.out.print("ID: " + id);
				System.out.print(", Name: " + first);
				System.out.println(", Lastname: " + last);
			}
			// STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {

			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
				// end finally try
			} // end try
			System.out.println("Goodbye!");
		}

	}
}

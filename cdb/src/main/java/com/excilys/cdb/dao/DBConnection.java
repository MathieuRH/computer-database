package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database connection class 
 * @author Mathieu_RH
 *
 */
public class DBConnection {

	private static DBConnection instance;
	private static Connection connection;
	
	//TODO fichier properties
	private String url="jdbc:mysql://localhost:3306/computer-database-db";
	private String user="admincdb";
	private String pwd="qwerty1234";
	
	private DBConnection() {
		try {
			connection = DriverManager.getConnection(url, user, pwd);
		} catch (SQLException e) {
			e.printStackTrace();
			e.getMessage();
		}
	}
	
	public static DBConnection getInstance() throws SQLException {
		if (instance == null || connection.isClosed()){
			new DBConnection();
		}
		return instance;
	}
	
	public static void close() {
      try {
	      if (connection != null) {
	          connection.close();
	      }
      } catch (Exception e) {
      }
	}

	public static Connection getConnection() {
		return connection;
	}
	
}

package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	private static Connection connection;
	
	//TO-DO fichier properties
	private String url="jdbc:mysql://localhost/computer-database-db";
	private String user="admincdb";
	private String pwd="qwerty1234";
	
	private DBConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(url, user, pwd);
			System.out.println("Database connection successfull !");
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			e.getMessage();
		}
	}
	
	public static Connection getInstance() throws SQLException {
		if (connection == null || connection.isClosed()){
			new DBConnection();
		}
		return connection;
	}
	
	public static void close() {
      try {
    	  
	      if (connection != null) {
	          connection.close();
	          System.out.println("Database connection terminated.");
	      }
      } catch (Exception e) {
      }
	}
	
}

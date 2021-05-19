package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.exceptions.ConnectionException;

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

	private static Logger logger = LoggerFactory.getLogger(DBConnection.class);
	
	private DBConnection() throws SQLException {
		connection = DriverManager.getConnection(url, user, pwd);
	}
	
	public static DBConnection getInstance() throws ConnectionException {
		try {
			if (instance == null || connection.isClosed()){
				new DBConnection();
			}
		} catch (SQLException e) {
			logger.error("SQL Exception : " + e);
			throw new ConnectionException();
		}
		return instance;
	}
	
	public static void close() {
      try {
	      if (connection != null) {
	          connection.close();
	      }
      } catch (SQLException e) {
			logger.error("SQL Exception : " + e);
      }
	}

	public static Connection getConnection() {
		return connection;
	}
	
}

package com.excilys.cdb.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

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
	
	private static final String DATABASE_PROPERTIES_FILE_PATH = "/database.properties";
	private static final String DATABASE_PROPERTY_NAME_DRIVER = "jdbc.driver";
	private static final String DATABASE_PROPERTY_NAME_URL = "jdbc.url";
	private static final String DATABASE_PROPERTY_NAME_LOGIN = "jdbc.username";
	private static final String DATABASE_PROPERTY_NAME_PASSWORD = "jdbc.password";
	private static String connectionDriver = null;
	private static String connectionUrl = null;
	private static String connectionLogin = null;
	private static String connectionPassword = null;
	
	private static Logger logger = LoggerFactory.getLogger(DBConnection.class);
	
	private DBConnection() throws SQLException {
		try {
			loadProperties();
			Class.forName(connectionDriver);
		} catch (IOException e) {
			logger.error("Database properties: {} in {}", e, e.getStackTrace());
			throw new SQLException("Error, cannot load database properties");
		} catch (ClassNotFoundException e) {
			logger.error("SQL Exception : " + e);
		}
		connection = DriverManager.getConnection(connectionUrl, connectionLogin, connectionPassword);
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
	
	/**
	 * Load database properties from the properties file.
	 * @throws IOException 
	 */
	private void loadProperties() throws IOException {
		Properties properties = new Properties();
		properties.load(DBConnection.class.getResourceAsStream(DATABASE_PROPERTIES_FILE_PATH));
		connectionDriver = properties.getProperty(DATABASE_PROPERTY_NAME_DRIVER);
		connectionUrl = properties.getProperty(DATABASE_PROPERTY_NAME_URL);
		connectionLogin = properties.getProperty(DATABASE_PROPERTY_NAME_LOGIN);
		connectionPassword = properties.getProperty(DATABASE_PROPERTY_NAME_PASSWORD);
	}
	
}

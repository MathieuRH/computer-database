package com.excilys.cdb.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.exceptions.ConnectionException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Database connection class 
 * @author Mathieu_RH
 *
 */
public class DBConnection {

	private static DBConnection instance;
	
	private static final String DATABASE_PROPERTIES_FILE_PATH = "/database.properties";
	private static final String DATABASE_PROPERTY_NAME_DRIVER = "jdbc.driver";
	private static final String DATABASE_PROPERTY_NAME_URL = "jdbc.url";
	private static final String DATABASE_PROPERTY_NAME_LOGIN = "jdbc.username";
	private static final String DATABASE_PROPERTY_NAME_PASSWORD = "jdbc.password";
	private static String connectionDriver;
	private static String connectionUrl;
	private static String connectionLogin;
	private static String connectionPassword;
	
	private static HikariConfig config ;
	private static HikariDataSource ds;
	
	private static Logger logger = LoggerFactory.getLogger(DBConnection.class);
	
	private DBConnection() throws SQLException {
		try {
			loadProperties();
			Class.forName(connectionDriver);
		} catch (IOException e) {
			throw new SQLException("Error, cannot load database properties");
		} catch (ClassNotFoundException e) {
			logger.error("SQL Exception : " + e);
		}
		config = new HikariConfig();
		config.setJdbcUrl( connectionUrl );
        config.setUsername( connectionLogin );
        config.setPassword( connectionPassword );
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        ds = new HikariDataSource( config );
	}
	
	public static DBConnection getInstance() throws ConnectionException {
		try {
			if (instance == null || ds.isClosed()){
				instance = new DBConnection();
			}
		} catch (SQLException e) {
			System.out.println("Catch 1");
			logger.error("SQL Exception : " + e);
			throw new ConnectionException();
		}
		return instance;
	}


	public Connection getConnection() throws SQLException {
		return ds.getConnection();
	}
	
	public void close() {
      if (!ds.isClosed()) {
          ds.close();
          instance = null;
      }
	}

	private void loadProperties() throws IOException {
		Properties properties = new Properties();
		properties.load(DBConnection.class.getResourceAsStream(DATABASE_PROPERTIES_FILE_PATH));
		connectionDriver = properties.getProperty(DATABASE_PROPERTY_NAME_DRIVER);
		connectionUrl = properties.getProperty(DATABASE_PROPERTY_NAME_URL);
		connectionLogin = properties.getProperty(DATABASE_PROPERTY_NAME_LOGIN);
		connectionPassword = properties.getProperty(DATABASE_PROPERTY_NAME_PASSWORD);
	}
}

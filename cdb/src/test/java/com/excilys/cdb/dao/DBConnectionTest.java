package com.excilys.cdb.dao;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

import com.excilys.cdb.exceptions.ConnectionException;

public class DBConnectionTest {
	
	@Test
	public void testGetInstance() {
		try {
			DBConnection.getInstance();
			DBConnection.getConnection();
		} catch (ConnectionException e) {
			fail("Connection throw SQL exception:" + e.getMessage());
		}
	}
	
	@Test
	public void testClose() {
		Connection c = null;
		
		try {
			DBConnection.getInstance();
			c = DBConnection.getConnection();
		} catch (ConnectionException e) {
			fail("Connection throw SQL exception:" + e.getMessage());
		}
		
		try {
			c.close();
		} catch (SQLException e) {
			fail("Failed to close connection");
		}
	}

}

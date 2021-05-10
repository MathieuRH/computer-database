package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.excilys.cdb.model.Company;

public class CompanyDAO {

	private static Connection connection;
	private static final String LIST_COMPANIES_QUERY = "SELECT id,name FROM company;";
	
	public CompanyDAO() {
		
	}
	
	/**
	 * Print all the companies
	 */
	public void getListCompanies() {
		ResultSet res = null;
		PreparedStatement statement = null;
		try {
			connection = DBConnection.getInstance();
			statement = connection.prepareStatement(LIST_COMPANIES_QUERY);
			res = statement.executeQuery();
			writeListCompanies(res);
		} catch (SQLException e) {
			e.getMessage();
			e.printStackTrace();
		} 
		finally {
			closeSetStatement(res, statement);
			DBConnection.close();
			connection = null;
		}
	}
	
	/**
	 * Prints the result of the @getListCompanies function
	 * @param resultSet
	 * @throws SQLException
	 */
	private void writeListCompanies(ResultSet resultSet) throws SQLException {
        Company company = new Company();
		while (resultSet.next()) {
            company.setId(resultSet.getInt("id"));
            company.setName(resultSet.getString("name"));
            System.out.println(company);
        }
    }
	
	/**
	 * Closing function for resultSets and statements
	 * @param res ResultSet to close
	 * @param statement Statement to close
	 */
    private void closeSetStatement(ResultSet res, Statement statement) {
        try {
            if (res != null) {
            	res.close();
            }

            if (statement != null) {
                statement.close();
            }
        } catch (Exception e) {
        	e.getMessage();
			e.printStackTrace();
        }
    }
	
}

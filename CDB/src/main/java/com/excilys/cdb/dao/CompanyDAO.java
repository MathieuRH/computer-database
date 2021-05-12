package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.model.Company;

public class CompanyDAO {

	private static Connection connection;
	private static final String LIST_COMPANIES_QUERY = "SELECT id,name FROM company;";
	private static final String GET_COMPANY = "SELECT id, name FROM company WHERE id=?;";
	
	public CompanyDAO() {
		
	}
	
	/**
	 * DAO function for listCompanies
	 * @return a list of all companies.
	 */
	public ArrayList<Company> getListCompanies() {
		ArrayList<Company> listCompanies= new ArrayList<Company>();
		ResultSet rs = null;
		PreparedStatement statement = null;
		try {
			connection = DBConnection.getInstance();
			statement = connection.prepareStatement(LIST_COMPANIES_QUERY);
			rs = statement.executeQuery();
			listCompanies = CompanyMapper.getListCompanies(rs);
		} catch (SQLException e) {
			e.getMessage();
			e.printStackTrace();
		} 
		finally {
			closeSetStatement(rs, statement);
			DBConnection.close();
			connection = null;
		}
		return listCompanies;
	}
	
	public Company getOneCompany(int company_id) {
		Company company = null;
		ResultSet rs = null;
		PreparedStatement statement = null;
		try {
			connection = DBConnection.getInstance();
			statement = connection.prepareStatement(GET_COMPANY);
			statement.setInt(1, company_id);
			rs = statement.executeQuery();
			company = CompanyMapper.getOneCompany(rs);
		} catch (SQLException e) {
			e.getMessage();
			e.printStackTrace();
		} 
		finally {
			closeSetStatement(rs, statement);
		}
		return company;
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

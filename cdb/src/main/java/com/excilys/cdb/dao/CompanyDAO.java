package com.excilys.cdb.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.exceptions.ConnectionException;
import com.excilys.cdb.exceptions.QueryException;
import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.model.Company;

// import logger

/**
 * Data access object for computers.
 * @author Mathieu_RH
 *
 */
public class CompanyDAO {

	private static CompanyDAO instance;
	
	private static final String LIST_COMPANIES_QUERY = "SELECT id,name FROM company LIMIT ? OFFSET ?;";
	private static final String NUMBER_COMPANIES_QUERY = "SELECT COUNT(id) FROM company;";
	private static final String GET_COMPANY = "SELECT id, name FROM company WHERE id=?;";
	
	private static Logger logger = LoggerFactory.getLogger(CompanyDAO.class);
	
	private CompanyDAO() {
	}
	
	public static CompanyDAO getInstance(){
		if (instance == null) {
			instance = new CompanyDAO();
		}
		return instance;
	}
	
	public ArrayList<Company> getListCompanies(int limit, int offset) throws ConnectionException, QueryException {
		ArrayList<Company> listCompanies= new ArrayList<Company>();
		ResultSet rs = null;
		PreparedStatement statement = null;
		DBConnection.getInstance();
		try {
			statement = DBConnection.getConnection().prepareStatement(LIST_COMPANIES_QUERY);
			statement.setInt(1,limit);
			statement.setInt(2,offset);
			rs = statement.executeQuery();
			listCompanies = CompanyMapper.getListCompanies(rs);
		} catch (SQLException e) {
			logger.error("{} in {}", e.toString(), e.getStackTrace());
			throw new QueryException();
		}
		finally {
			closeSetStatement(rs, statement);
			DBConnection.close();
		}
		return listCompanies;
	}
	
	public Company getOneCompany(int company_id) throws ConnectionException, QueryException {
		Company company = null;
		ResultSet rs = null;
		PreparedStatement statement = null;
		DBConnection.getInstance();
		try {
			statement = DBConnection.getConnection().prepareStatement(GET_COMPANY);
			statement.setInt(1, company_id);
			rs = statement.executeQuery();
			company = CompanyMapper.getOneCompany(rs);
		} catch (SQLException e) {
			logger.error("{} in {}", e.toString(), e.getStackTrace());
		} 
		finally {
			closeSetStatement(rs, statement);
		}
		return company;
	}
	
	public int getNumberCompanies() throws ConnectionException, QueryException {
		int nbCompanies = 0;
		ResultSet rs = null;
		PreparedStatement statement = null;
		DBConnection.getInstance();
		try {
			statement = DBConnection.getConnection().prepareStatement(NUMBER_COMPANIES_QUERY);
			rs = statement.executeQuery();
			nbCompanies = getNumberCompanies_processed(rs);
		} catch (SQLException e) {
			logger.error("{} in {}", e.toString(), e.getStackTrace());
		} 
		finally {
			closeSetStatement(rs, statement);
			DBConnection.close();
		}
		return nbCompanies;
	}
	
	private int getNumberCompanies_processed(ResultSet rs) throws SQLException {
		int nbCompanies = 0;
		if (rs.next()) {
			nbCompanies = rs.getInt(1);
		}
		return nbCompanies;
	}
	
	
    private void closeSetStatement(ResultSet res, Statement statement) {
        try {
            if (res != null) {
            	res.close();
            }

            if (statement != null) {
                statement.close();
            }
        } catch (Exception e) {
			logger.error("{} in {}", e.toString(), e.getStackTrace());
        }
    }
}

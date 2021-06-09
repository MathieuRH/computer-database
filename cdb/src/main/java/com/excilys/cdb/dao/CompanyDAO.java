package com.excilys.cdb.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.exceptions.ConnectionException;
import com.excilys.cdb.exceptions.QueryException;
import com.excilys.cdb.model.Company;
import com.zaxxer.hikari.HikariDataSource;

@Repository
public class CompanyDAO {
	
	private static final String LIST_COMPANIES_QUERY = "SELECT id,name FROM company LIMIT ? OFFSET ?;";
	private static final String NUMBER_COMPANIES_QUERY = "SELECT COUNT(id) FROM company;";
	private static final String GET_COMPANY = "SELECT id, name FROM company WHERE id=?;";
	private static final String CREATE_ONE = "INSERT INTO company (name) VALUES (?);";
	private static final String DELETE_ONE = "DELETE FROM company WHERE id=?;";
	private static final String DELETE_LINKED_COMPUTERS = "DELETE FROM computer WHERE company_id=?;";
	
	private HikariDataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	public CompanyDAO(DBConnection dbConnection) {
		dataSource = dbConnection.getDataSource();
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public ArrayList<Company> getListCompanies(int limit, int offset) throws ConnectionException, QueryException {
		ArrayList<Company> listCompanies= new ArrayList<Company>();
		listCompanies = (ArrayList<Company>) jdbcTemplate.query(LIST_COMPANIES_QUERY, new CompanyMP(), limit, offset);
		return listCompanies;
	}
	
	public Company getOneCompany(int company_id) throws ConnectionException, QueryException {
		return (Company) jdbcTemplate.query(GET_COMPANY, new CompanyMP(), company_id);
	}
	
	public int getNumberCompanies() {
		return jdbcTemplate.queryForObject(NUMBER_COMPANIES_QUERY, Integer.class);
	}

	@Transactional
	public void deleteOne(int company_id) throws ConnectionException, QueryException {
		jdbcTemplate.update(DELETE_LINKED_COMPUTERS, company_id);
		jdbcTemplate.update(DELETE_ONE, company_id);
	}

	@Transactional
	public void createOne(String name) {
		jdbcTemplate.update(CREATE_ONE, name);
	}

	
	private static final class CompanyMP implements RowMapper<Company> {
		public Company mapRow(ResultSet rs, int rowNumber) throws SQLException {
	    	Company company = new Company(rs.getInt("id"), rs.getString("name"));
	        return company;
	    }     
	}
}

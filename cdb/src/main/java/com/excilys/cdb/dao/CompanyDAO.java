package com.excilys.cdb.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.exceptions.QueryException;
import com.excilys.cdb.model.Company;

@Repository
public class CompanyDAO {
	
	private static final String LIST_COMPANIES_QUERY = "SELECT id,name FROM company LIMIT ? OFFSET ?;";
	private static final String NUMBER_COMPANIES_QUERY = "SELECT COUNT(id) FROM company;";
	private static final String GET_COMPANY = "SELECT id, name FROM company WHERE id=?;";
	private static final String CREATE_ONE = "INSERT INTO company (name) VALUES (?);";
	private static final String DELETE_ONE = "DELETE FROM company WHERE id=?;";
	private static final String DELETE_LINKED_COMPUTERS = "DELETE FROM computer WHERE company_id=?;";
	
	private JdbcTemplate jdbcTemplate;
	
	public CompanyDAO(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public ArrayList<Company> getListCompanies(int limit, int offset) throws QueryException {
		ArrayList<Company> listCompanies= new ArrayList<Company>();
		try {
			listCompanies = (ArrayList<Company>) jdbcTemplate.query(LIST_COMPANIES_QUERY, new CompanyMP(), limit, offset);
		} catch (DataAccessException e) {
			throw new QueryException();
		}
		return listCompanies;
	}
	
	public Company getOneCompany(int company_id) throws QueryException {
		try {
			return (Company) jdbcTemplate.query(GET_COMPANY, new CompanyMP(), company_id);
		} catch (DataAccessException e) {
			throw new QueryException();
		}
	}
	
	public int getNumberCompanies() throws QueryException {
		try {
			return jdbcTemplate.queryForObject(NUMBER_COMPANIES_QUERY, Integer.class);
		} catch (DataAccessException e) {
			throw new QueryException();
		}
	}

	@Transactional
	public void deleteOne(int company_id) throws QueryException {
		try {
			jdbcTemplate.update(DELETE_LINKED_COMPUTERS, company_id);
			jdbcTemplate.update(DELETE_ONE, company_id);
		} catch (DataAccessException e) {
			throw new QueryException();
		}
	}

	@Transactional
	public void createOne(String name) throws QueryException {
		try {
			jdbcTemplate.update(CREATE_ONE, name);
		} catch (DataAccessException e) {
			throw new QueryException();
		}
	}

	
	private static final class CompanyMP implements RowMapper<Company> {
		public Company mapRow(ResultSet rs, int rowNumber) throws SQLException {
	    	Company company = new Company(rs.getInt("id"), rs.getString("name"));
	        return company;
	    }     
	}
}

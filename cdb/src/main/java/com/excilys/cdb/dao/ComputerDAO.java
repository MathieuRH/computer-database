package com.excilys.cdb.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.dto.ComputerDTOSQL;
import com.excilys.cdb.exceptions.ConnectionException;
import com.excilys.cdb.exceptions.QueryException;
import com.excilys.cdb.mapper.ComputerMapperSQL;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.model.Computer.ComputerBuilder;
import com.zaxxer.hikari.HikariDataSource;

@Repository
public class ComputerDAO {

	private ComputerMapperSQL computerMapperSQL;
	
	private static final String LIST_COMPUTERS_QUERY = "SELECT C.id,C.name,C.introduced,C.discontinued,Y.id,Y.name "
			+ "FROM computer AS C LEFT JOIN company AS Y ON C.company_id = Y.id "
			+ "WHERE C.name LIKE ? "
			+ "ORDER BY ";
	private static final String NUMBER_COMPUTERS_QUERY = "SELECT COUNT(id) FROM computer;";
	private static final String NUMBER_COMPUTERS_BY_NAME_QUERY = "SELECT COUNT(id) FROM computer "
			+ "WHERE name LIKE ?;";
	private static final String ONE_COMPUTER_QUERY = "SELECT C.id,C.name,C.introduced,C.discontinued,Y.id,Y.name "
			+ "FROM computer AS C LEFT JOIN company AS Y ON C.company_id = Y.id WHERE C.id=?;";
	private static final String CREATE_ONE = "INSERT INTO computer (name, introduced, discontinued, company_id) "
			+ "VALUES (:name,:introduced,:discontinued,:companyId);";
	private static final String UPDATE_ONE = "UPDATE computer SET name=:name, introduced=:introduced, discontinued=:discontinued, company_id=:companyId WHERE id=:id;";
	private static final String DELETE_ONE = "DELETE FROM computer WHERE id=?;";

	private HikariDataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public ComputerDAO(DBConnection dbConnection, ComputerMapperSQL computerMapperSQL) {
		this.computerMapperSQL=computerMapperSQL;
		dataSource = dbConnection.getDataSource();
		jdbcTemplate = new JdbcTemplate(dataSource);
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public ArrayList<Computer> getListComputers(Page pagination, String query, String name) throws ConnectionException, QueryException { 
		ArrayList<Computer> listComputers = new ArrayList<Computer>();
		String orderByType = "C.id " + pagination.getSortOrder();
		switch (query) {
			case "orderByName":
				orderByType = "C.name " + pagination.getSortOrder();
				break;
			case "orderByIntroduced":
				orderByType = "C.introduced IS NULL, C.introduced "+ pagination.getSortOrder() +", C.name ";
				break;
			case "orderByDiscontinued":
				orderByType = "C.discontinued IS NULL, C.discontinued "+ pagination.getSortOrder() +", C.introduced IS NULL, C.introduced, C.name";
				break;
			case "orderByCompany":
				orderByType = "Y.name IS NULL, Y.name "+ pagination.getSortOrder() +", C.name";
				break;
		}
		String specific_query = LIST_COMPUTERS_QUERY + orderByType + " LIMIT ? OFFSET ?;";
		String nameSearch = "%" + name + "%";
		int limit = pagination.getSize();
		int offset = pagination.getOffset();
		listComputers = (ArrayList<Computer>) jdbcTemplate.query(specific_query, new ComputerMP(), nameSearch, limit, offset);
		return listComputers;
	}
	
	public Optional<Computer> getOneComputer(int id_computer) throws ConnectionException, QueryException{
		return Optional.ofNullable(jdbcTemplate.queryForObject(ONE_COMPUTER_QUERY, new ComputerMP(), id_computer));
	}

	
	public int getNumberComputers() throws ConnectionException, QueryException {
		return jdbcTemplate.queryForObject(NUMBER_COMPUTERS_QUERY, Integer.class);
	}

	public int getNumberComputersByName(String name) throws ConnectionException, QueryException {
		String nameSearch = "%" + name + "%";
		return jdbcTemplate.queryForObject(NUMBER_COMPUTERS_BY_NAME_QUERY, Integer.class, nameSearch);	
	}
	
	
	public void createOne(Computer computer) throws ConnectionException, QueryException {
		ComputerDTOSQL computerDTO = computerMapperSQL.toComputerDTO(computer);
		SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(computerDTO);
		namedParameterJdbcTemplate.update(CREATE_ONE, namedParameters);
	}
	
	
	public void updateOne(Computer computer) throws ConnectionException, QueryException {
		ComputerDTOSQL computerDTO = computerMapperSQL.toComputerDTO(computer);
		SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(computerDTO);
		namedParameterJdbcTemplate.update(UPDATE_ONE, namedParameters);
	}
	
	public void deleteOne(int id_computer) throws ConnectionException, QueryException {
		jdbcTemplate.update(DELETE_ONE, id_computer);
	}

    
    private static final class ComputerMP implements RowMapper<Computer> {
		public Computer mapRow(ResultSet rs, int rowNumber) throws SQLException {
			int id = rs.getInt("C.id");
		    String name = rs.getString("C.name");
		    Date dateInt = rs.getDate("C.introduced");
		    Date dateDis = rs.getDate("C.discontinued");
		    int company_id = rs.getInt("Y.id");
		    String company_name = rs.getString("Y.name");
		    ComputerBuilder comp = new Computer.ComputerBuilder(id, name);
		    if (dateInt != null) {
		    	comp.introducedDate(dateInt.toLocalDate());
		    }
		    if (dateDis != null) {
		    	comp.discontinuedDate(dateDis.toLocalDate());
		    }
		    if (company_id != 0) {
		    	Company company = new Company(company_id, company_name);
		    	comp.company(company);
		    }
			return comp.build();
	    }     
	}
}

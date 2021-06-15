package com.excilys.cdb.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.dto.ComputerDTOSQL;
import com.excilys.cdb.exceptions.QueryException;
import com.excilys.cdb.mapper.ComputerMapperSQL;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.model.Computer.ComputerBuilder;

@Repository
@Transactional
public class ComputerDAO {
	
	private static final String LIST_COMPUTERS_QUERY = "FROM ComputerDTOSQL AS C LEFT JOIN CompanyDTOSQL AS Y ON C.company_id = Y.id "
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

	private SessionFactory sessionFactory;
	private ComputerMapperSQL computerMapper;
	
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public ComputerDAO(SessionFactory sessionFactory, ComputerMapperSQL computerMapper, DataSource dataSource) {
		this.sessionFactory = sessionFactory;
		this.computerMapper=computerMapper;
		jdbcTemplate = new JdbcTemplate(dataSource);
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public ArrayList<Computer> getListComputers(Page pagination, String query_name, String name) throws QueryException { 
		ArrayList<Computer> listComputers = new ArrayList<Computer>();
		String orderByType = "C.id " + pagination.getSortOrder();
		switch (query_name) {
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
		String specific_query = LIST_COMPUTERS_QUERY + orderByType;
		String nameSearch = "%" + name + "%";
		int limit = pagination.getSize();
		int offset = pagination.getOffset();
		try {
			Session session = sessionFactory.getCurrentSession();
			List<ComputerDTOSQL> listComputersDTO = new ArrayList<>();
			Query<ComputerDTOSQL> query = session.createQuery(specific_query, ComputerDTOSQL.class);
			query.setParameter(1, nameSearch);
			query.setFirstResult(offset);
			query.setMaxResults(limit);
			listComputersDTO = query.list();
			
			listComputers = computerMapper.toListComputers(listComputersDTO);
			return listComputers;
		} catch (HibernateException e) {
			throw new QueryException(e.getMessage());
		}
	}
	
	public Optional<Computer> getOneComputer(int id_computer) throws QueryException{
		Optional<Computer> computer = Optional.empty();
		try {
				computer = Optional.ofNullable(jdbcTemplate.queryForObject(ONE_COMPUTER_QUERY, new ComputerMP(), id_computer));
		} catch (DataAccessException e) {
			throw new QueryException();
		}
		return computer;
	}

	
	public int getNumberComputers() throws QueryException {
		try {
			return jdbcTemplate.queryForObject(NUMBER_COMPUTERS_QUERY, Integer.class);
		} catch (DataAccessException e) {
			throw new QueryException();
		}
	}

	public int getNumberComputersByName(String name) throws QueryException {
		try {
			String nameSearch = "%" + name + "%";
			return jdbcTemplate.queryForObject(NUMBER_COMPUTERS_BY_NAME_QUERY, Integer.class, nameSearch);
		} catch (DataAccessException e) {
			throw new QueryException();
		}
	}
	
	
	public void createOne(Computer computer) throws QueryException {
		try {
			ComputerDTOSQL computerDTO = computerMapper.toComputerDTO(computer);
			SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(computerDTO);
			namedParameterJdbcTemplate.update(CREATE_ONE, namedParameters);
		} catch (DataAccessException e) {
			throw new QueryException();
		}
	}
	
	
	public void updateOne(Computer computer) throws QueryException {
		try {
			ComputerDTOSQL computerDTO = computerMapper.toComputerDTO(computer);
			SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(computerDTO);
			namedParameterJdbcTemplate.update(UPDATE_ONE, namedParameters);
		} catch (DataAccessException e) {
			throw new QueryException();
		}
	}
	
	public void deleteOne(int id_computer) throws QueryException {
		try {
			jdbcTemplate.update(DELETE_ONE, id_computer);
		} catch (DataAccessException e) {
			throw new QueryException();
		}
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

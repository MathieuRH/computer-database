package com.excilys.cdb.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dto.ComputerDTOSQL;
import com.excilys.cdb.exceptions.ComputerNotFoundException;
import com.excilys.cdb.exceptions.ConnectionException;
import com.excilys.cdb.exceptions.QueryException;
import com.excilys.cdb.mapper.ComputerMapperSQL;
import com.excilys.cdb.model.Computer;


/**
 * Data access object for computers.
 * @author Mathieu_RH
 *
 */
public class ComputerDAO {
	
	private static ComputerDAO instance;
	private ComputerMapperSQL computerMapperSQL;
	private DBConnection dbConnection;
	
	private static final String LIST_COMPUTERS_QUERY = "SELECT C.id,C.name,C.introduced,C.discontinued,Y.id,Y.name "
			+ "FROM computer AS C LEFT JOIN company AS Y ON C.company_id = Y.id "
			+ "ORDER BY ";
	private static final String LIST_COMPUTERS_BY_NAME = "SELECT C.id,C.name,C.introduced,C.discontinued,Y.id,Y.name "
			+ "FROM computer AS C LEFT JOIN company AS Y ON C.company_id = Y.id "
			+ "WHERE C.name LIKE ? "
			+ "LIMIT ? OFFSET ?;";
	private static final String NUMBER_COMPUTERS_QUERY = "SELECT COUNT(id) FROM computer;";
	private static final String NUMBER_COMPUTERS_BY_NAME_QUERY = "SELECT COUNT(id) FROM computer "
			+ "WHERE name LIKE ?;";
	private static final String ONE_COMPUTER_QUERY = "SELECT C.id,C.name,C.introduced,C.discontinued,Y.id,Y.name "
			+ "FROM computer AS C LEFT JOIN company AS Y ON C.company_id = Y.id WHERE C.id=?;";
	private static final String CREATE_ONE = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?,?,?,?);";
	private static final String UPDATE_ONE = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?;";
	private static final String DELETE_ONE = "DELETE FROM computer WHERE id=?;";

	private static Logger logger = LoggerFactory.getLogger(ComputerDAO.class);
	
	private ComputerDAO() {
		computerMapperSQL = ComputerMapperSQL.getInstance();
	}
	
	public static ComputerDAO getInstance() {
		if (instance == null) {
			instance = new ComputerDAO();
		}
		return instance;
	}
	
	public ArrayList<Computer> getListComputers(int limit, int offset, String query) throws ConnectionException, QueryException { 
		ArrayList<Computer> listComputers = new ArrayList<Computer>();
		ResultSet rs = null;
		PreparedStatement statement = null;
		dbConnection = DBConnection.getInstance();
		try {
			String orderByType = "C.id";
			switch (query) {
				case "orderByName":
					orderByType = "C.name";
					break;
				case "orderByIntroduced":
					orderByType = "C.introduced IS NULL, C.introduced, C.name";
					break;
				case "orderByDiscontinued":
					orderByType = "C.discontinued IS NULL, C.discontinued, C.introduced IS NULL, C.introduced, C.name";
					break;
				case "orderByCompany":
					orderByType = "Y.name IS NULL, Y.name, C.name";
					break;
			}
			statement = dbConnection.getConnection().prepareStatement(LIST_COMPUTERS_QUERY + orderByType + " LIMIT ? OFFSET ?;");
			statement.setInt(1,limit);
			statement.setInt(2,offset);
			rs = statement.executeQuery();
			listComputers = computerMapperSQL.getListComputers(rs);
		} catch (SQLException e) {
			logger.error("SQL Exception : " + e);
		}
		finally {
			closeSetStatement(rs, statement);
		}
		dbConnection.close();
		return listComputers;
	}

	public ArrayList<Computer> getListByName(int limit, int offset, String name) throws ConnectionException, QueryException, ComputerNotFoundException {
		ArrayList<Computer> listComputers = new ArrayList<Computer>();
		ResultSet rs = null;
		PreparedStatement statement = null;
		dbConnection = DBConnection.getInstance();
		try {
			statement = dbConnection.getConnection().prepareStatement(LIST_COMPUTERS_BY_NAME);
			statement.setString(1,"%" + name + "%");
			statement.setInt(2,limit);
			statement.setInt(3,offset);
			rs = statement.executeQuery();
			listComputers = computerMapperSQL.getListComputers(rs);
		} catch (SQLException e) {
			logger.error("SQL Exception : " + e);
		}
		finally {
			closeSetStatement(rs, statement);
		}
		dbConnection.close();
		return listComputers;
	}
	
	public Optional<Computer> getOneComputer(int id_computer) throws ConnectionException, QueryException, ComputerNotFoundException {
		Optional<Computer> computer;
		ResultSet rs = null;
		PreparedStatement statement = null;
		dbConnection = DBConnection.getInstance();
		try {
			statement = dbConnection.getConnection().prepareStatement(ONE_COMPUTER_QUERY);
			statement.setInt(1, id_computer);
			rs = statement.executeQuery();
			computer = computerMapperSQL.getOneComputer(rs);
		} catch (SQLException e) {
			logger.error("SQL Exception : " + e);
			computer = Optional.empty();
		} 
		finally {
			closeSetStatement(rs, statement);
		}
		dbConnection.close();
		return computer;
	}
	
	public void createOne(Computer computer) throws ConnectionException, QueryException {
		ComputerDTOSQL computerDTO = computerMapperSQL.toComputerDTO(computer);
		String name = computerDTO.getName();
		ResultSet rs = null;
		PreparedStatement statement = null;
		dbConnection = DBConnection.getInstance();
		try {
			statement = dbConnection.getConnection().prepareStatement(CREATE_ONE);
			statement.setString(1, name);
			if (computerDTO.getIntroduced()!=null) {
				LocalDate introduced = LocalDate.parse(computerDTO.getIntroduced());
				statement.setDate(2, Date.valueOf(introduced));
			} else {statement.setNull(2, 0);
			}
			if (computerDTO.getDiscontinued()!=null) {
				LocalDate discontinued = LocalDate.parse(computerDTO.getDiscontinued());
				statement.setDate(3, Date.valueOf(discontinued));
			} else {statement.setNull(3, 0);
			}
			if (computerDTO.getCompanyId()!=null) {
				int id_company = Integer.parseInt(computerDTO.getCompanyId());
				if (id_company!=0) {
					statement.setInt(4, id_company);
				} else {statement.setNull(4, 0);}
			}
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.error("SQL Exception : " + e);
			throw new QueryException();
		}
		finally {
			closeSetStatement(rs, statement);
		}
		dbConnection.close();
	}
	
	
	public void updateOne(Computer computer) throws ConnectionException, QueryException {
		ResultSet rs = null;
		PreparedStatement statement = null;
		dbConnection = DBConnection.getInstance();
		ComputerDTOSQL computerDTO = computerMapperSQL.toComputerDTO(computer);
		try {
			statement = dbConnection.getConnection().prepareStatement(UPDATE_ONE);
			statement.setString(1, computer.getName());
			if (computerDTO.getIntroduced()!=null) {
				LocalDate introduced = LocalDate.parse(computerDTO.getIntroduced());
				statement.setDate(2, Date.valueOf(introduced));
			} else {statement.setNull(2, 0);}
			if (computerDTO.getDiscontinued()!=null) {
				LocalDate discontinued = LocalDate.parse(computerDTO.getDiscontinued());
				statement.setDate(3, Date.valueOf(discontinued));
			} else {statement.setNull(3, 0);
			}
			if (computerDTO.getCompanyId()!=null) {
				int id_company = Integer.parseInt(computerDTO.getCompanyId());
				if (id_company!=0) {
					statement.setInt(4, id_company);
				} else {statement.setNull(4, 0);}
			}
			statement.setInt(5, computer.getId());
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.error("SQL Exception : " + e);
			throw new QueryException();
		} 
		finally {
			closeSetStatement(rs, statement);
		}
		dbConnection.close();
	}
	
	public void deleteOne(int id_computer) throws ConnectionException, QueryException {
		if (id_computer !=0) {
			ResultSet rs = null;
			PreparedStatement statement = null;
			try {
				dbConnection = DBConnection.getInstance();
				statement = dbConnection.getConnection().prepareStatement(DELETE_ONE);
				try {
					statement.setInt(1, id_computer);
					statement.executeUpdate();
				} catch (SQLException e) {
					logger.error("SQL Exception : " + e);
					throw new QueryException();
				} 
			} catch (SQLException e) {
				logger.error("SQL Exception : " + e);
				throw new ConnectionException();
			} 
			finally {
				closeSetStatement(rs, statement);
				dbConnection.close();
			}
		}
	}
	

	public int getNumberComputers() throws ConnectionException, QueryException {
		int nbComputers = 0;
		ResultSet rs = null;
		PreparedStatement statement = null;
		dbConnection = DBConnection.getInstance();
		try {
			statement = dbConnection.getConnection().prepareStatement(NUMBER_COMPUTERS_QUERY);
			rs = statement.executeQuery();
			nbComputers = getNumberComputers_processed(rs);
		} catch (SQLException e) {
			logger.error("SQL Exception : " + e);
			throw new QueryException();
		} 
		finally {
			closeSetStatement(rs, statement);
			dbConnection.close();
		}
		return nbComputers;
	}

	public int getNumberComputersByName(String name) throws ConnectionException, QueryException {
		int nbComputers = 0;
		ResultSet rs = null;
		PreparedStatement statement = null;
		dbConnection = DBConnection.getInstance();
		try {
			statement = dbConnection.getConnection().prepareStatement(NUMBER_COMPUTERS_BY_NAME_QUERY);
			statement.setString(1,"%" + name + "%");
			rs = statement.executeQuery();
			nbComputers = getNumberComputers_processed(rs);
		} catch (SQLException e) {
			logger.error("SQL Exception : " + e);
			throw new QueryException();
		} 
		finally {
			closeSetStatement(rs, statement);
			dbConnection.close();
		}
		return nbComputers;
	}
	

	private int getNumberComputers_processed(ResultSet rs) throws SQLException {
		int nbComputers = 0;
		if (rs.next()) {
			nbComputers = rs.getInt(1);
		}
		return nbComputers;
	}

    private void closeSetStatement(ResultSet res, Statement statement) {
        try {
            if (res != null) {
            	res.close();
            }

            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
			logger.error("SQL Exception : " + e);
        }
    }
	
}

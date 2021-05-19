package com.excilys.cdb.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.exceptions.ComputerNotFoundException;
import com.excilys.cdb.exceptions.ConnectionException;
import com.excilys.cdb.exceptions.QueryException;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Computer;

/**
 * Data access object for computers.
 * @author Mathieu_RH
 *
 */
public class ComputerDAO {
	
	private static ComputerDAO instance;
	
	private static final String LIST_COMPUTERS_QUERY = "SELECT id,name,introduced,discontinued,company_id FROM computer LIMIT ? OFFSET ?;";
	private static final String NUMBER_COMPUTERS_QUERY = "SELECT COUNT(id) FROM computer;";
	private static final String ONE_COMPUTER_QUERY = "SELECT id,name,introduced,discontinued,company_id FROM computer WHERE id=?;";
	private static final String CREATE_ONE = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?,?,?,?);";
	private static final String UPDATE_ONE_NAME = "UPDATE computer SET name=? WHERE id=?;";
	private static final String UPDATE_ONE_INTRODUCED = "UPDATE computer SET introduced=? WHERE id=?;";
	private static final String UPDATE_ONE_DISCONTINUED = "UPDATE computer SET discontinued=? WHERE id=?;";
	private static final String UPDATE_ONE_COMPANY_ID = "UPDATE computer SET company_id=? WHERE id=?;";
	private static final String DELETE_ONE = "DELETE FROM computer WHERE id=?;";

	private static Logger logger = LoggerFactory.getLogger(ComputerDAO.class);
	
	private ComputerDAO() {
	}
	
	public static ComputerDAO getInstance() {
		if (instance == null) {
			instance = new ComputerDAO();
		}
		return instance;
	}
	
	public ArrayList<Computer> getListComputers(int limit, int offset) throws ConnectionException, QueryException { 
		ArrayList<Computer> listComputers = new ArrayList<Computer>();
		ResultSet rs = null;
		PreparedStatement statement = null;
		DBConnection.getInstance();
		try {
			statement = DBConnection.getConnection().prepareStatement(LIST_COMPUTERS_QUERY);
			statement.setInt(1,limit);
			statement.setInt(2,offset);
			rs = statement.executeQuery();
			listComputers = ComputerMapper.getListComputers(rs);
		} catch (SQLException e) {
			logger.error("{} in {}", e.toString(), e.getStackTrace());
		} 
		finally {
			closeSetStatement(rs, statement);
		}
		DBConnection.close();
		return listComputers;
	}
	
	public Computer getOneComputer(int id_computer) throws ConnectionException, QueryException, ComputerNotFoundException {
		Computer computer = null;
		ResultSet rs = null;
		PreparedStatement statement = null;
		DBConnection.getInstance();
		try {
			statement = DBConnection.getConnection().prepareStatement(ONE_COMPUTER_QUERY);
			statement.setInt(1, id_computer);
			rs = statement.executeQuery();
			computer = ComputerMapper.getOneComputer(rs);
		} catch (SQLException e) {
			logger.error("{} in {}", e.toString(), e.getStackTrace());
		} 
		finally {
			closeSetStatement(rs, statement);
		}
		DBConnection.close();
		return computer;
	}
	
	
	public void createOne(String name, LocalDate introduced, LocalDate discontinued, int id_company) throws ConnectionException, QueryException {
		if (name != null) {
			ResultSet rs = null;
			PreparedStatement statement = null;
			DBConnection.getInstance();
			try {
				statement = DBConnection.getConnection().prepareStatement(CREATE_ONE);
				statement.setString(1, name);
				if (introduced!=null) {
					statement.setDate(2, Date.valueOf(introduced));
				} else {statement.setNull(2, 0);}
				if (discontinued!=null) {
					statement.setDate(3, Date.valueOf(discontinued));
				} else {statement.setNull(3, 0);}
				if (id_company!=0) {
					statement.setInt(4, id_company);
				} else {statement.setNull(4, 0);}
				statement.executeUpdate();
			} catch (SQLException e) {
				logger.error("{} in {}", e.toString(), e.getStackTrace());
				throw new QueryException();
			}
			finally {
				closeSetStatement(rs, statement);
			}
			DBConnection.close();
		} else {System.out.println("Name can't be null.");}
	}
	
	
	public void updateOne(int id_computer, int field, Object value) throws ConnectionException, QueryException {
		if (id_computer !=0) {
			ResultSet rs = null;
			PreparedStatement statement = null;
			DBConnection.getInstance();
			if (field == 1 && (String)value != null) {
				try {
					statement = DBConnection.getConnection().prepareStatement(UPDATE_ONE_NAME);
					statement.setString(1, (String)value);
					statement.setInt(2, id_computer);
					statement.executeUpdate();
				
				} catch (SQLException e) {
					logger.error("{} in {}", e.toString(), e.getStackTrace());
					throw new QueryException();
				} 
				finally {
					closeSetStatement(rs, statement);
				}
			}
			else if (field == 2 || field==3) {
				DBConnection.getInstance();
				try {
					if (field == 2) {
						statement = DBConnection.getConnection().prepareStatement(UPDATE_ONE_INTRODUCED);
					} else {statement = DBConnection.getConnection().prepareStatement(UPDATE_ONE_DISCONTINUED);}
					statement.setDate(1, Date.valueOf((LocalDate) value));
					statement.setInt(2, id_computer);
					statement.executeUpdate();
				} catch (SQLException e) {
					logger.error("{} in {}", e.toString(), e.getStackTrace());
					throw new QueryException();
				} 
				finally {
					closeSetStatement(rs, statement);
				}
			} 
			else if (field == 4) {
				try {
					statement = DBConnection.getConnection().prepareStatement(UPDATE_ONE_COMPANY_ID);
					statement.setInt(1, (Integer) value);
					statement.setInt(2, id_computer);
					statement.executeUpdate();
				} catch (SQLException e) {
					logger.error("{} in {}", e.toString(), e.getStackTrace());
					throw new QueryException();
				} 
				finally {
					closeSetStatement(rs, statement);
				}
			}
			DBConnection.close();
		}
	}
	
	public void deleteOne(int id_computer) throws ConnectionException, QueryException {
		if (id_computer !=0) {
			ResultSet rs = null;
			PreparedStatement statement = null;
			try {
				DBConnection.getInstance();
				statement = DBConnection.getConnection().prepareStatement(DELETE_ONE);
				try {
					statement.setInt(1, id_computer);
					statement.executeUpdate();
				} catch (SQLException e) {
					logger.error("{} in {}", e.toString(), e.getStackTrace());
					throw new QueryException();
				} 
			} catch (SQLException e) {
				e.getMessage();
				e.printStackTrace();
				throw new ConnectionException();
			} 
			finally {
				closeSetStatement(rs, statement);
				DBConnection.close();
			}
		}
	}
	

	public int getNumberComputers() throws ConnectionException, QueryException {
		int nbComputers = 0;
		ResultSet rs = null;
		PreparedStatement statement = null;
		DBConnection.getInstance();
		try {
			statement = DBConnection.getConnection().prepareStatement(NUMBER_COMPUTERS_QUERY);
			rs = statement.executeQuery();
			nbComputers = getNumberComputers_processed(rs);
		} catch (SQLException e) {
			logger.error("{} in {}", e.toString(), e.getStackTrace());
			throw new QueryException();
		} 
		finally {
			closeSetStatement(rs, statement);
			DBConnection.close();
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
        } catch (Exception e) {
        	e.getMessage();
        }
    }
	
}

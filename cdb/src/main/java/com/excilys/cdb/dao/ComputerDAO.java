package com.excilys.cdb.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

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
	
	private ComputerDAO() {
	}
	
	public static ComputerDAO getInstance() {
		if (instance == null) {
			instance = new ComputerDAO();
		}
		return instance;
	}
	
	public ArrayList<Computer> getListComputers(int limit, int offset) { 
		ArrayList<Computer> listComputers = new ArrayList<Computer>();
		ResultSet rs = null;
		PreparedStatement statement = null;
		try {
			DBConnection.getInstance();
			statement = DBConnection.getConnection().prepareStatement(LIST_COMPUTERS_QUERY);
			statement.setInt(1,limit);
			statement.setInt(2,offset);
			rs = statement.executeQuery();
			listComputers = ComputerMapper.getListComputers(rs);
		} catch (SQLException e) {
			e.getMessage();
			e.printStackTrace();
		} 
		finally {
			closeSetStatement(rs, statement);
			DBConnection.close();
		}
		return listComputers;
	}
	
	public Computer getOneComputer(int id_computer) {
		Computer computer = null;
		ResultSet rs = null;
		PreparedStatement statement = null;
		try {
			DBConnection.getInstance();
			statement = DBConnection.getConnection().prepareStatement(ONE_COMPUTER_QUERY);
			statement.setInt(1, id_computer);
			rs = statement.executeQuery();
			computer = ComputerMapper.getOneComputer(rs);
		} catch (SQLException e) {
			e.getMessage();
			e.printStackTrace();
		} 
		finally {
			closeSetStatement(rs, statement);
			DBConnection.close();
		}
		return computer;
	}
	
	
	public void createOne(String name, LocalDate introduced, LocalDate discontinued, int id_company) {
		if (name != null) {
			ResultSet rs = null;
			PreparedStatement statement = null;
			try {
				DBConnection.getInstance();
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
				e.getMessage();
				e.printStackTrace();
			} 
			finally {
				closeSetStatement(rs, statement);
				DBConnection.close();
			}
		} else {System.out.println("Name can't be null.");}
	}
	
	
	public void updateOne(int id_computer, int field, Object value) {
		if (id_computer !=0) {
			if (field == 1 && (String)value != null) {
				ResultSet rs = null;
				PreparedStatement statement = null;
				try {
					DBConnection.getInstance();
					statement = DBConnection.getConnection().prepareStatement(UPDATE_ONE_NAME);
					statement.setString(1, (String)value);
					statement.setInt(2, id_computer);
					statement.executeUpdate();
				
				} catch (SQLException e) {
					e.getMessage();
					e.printStackTrace();
				} 
				finally {
					closeSetStatement(rs, statement);
					DBConnection.close();
				}
			}
			else if (field == 2 || field==3) {
				ResultSet rs = null;
				PreparedStatement statement = null;
				try {
					DBConnection.getInstance();
					if (field == 2) {
						statement = DBConnection.getConnection().prepareStatement(UPDATE_ONE_INTRODUCED);
					} else {statement = DBConnection.getConnection().prepareStatement(UPDATE_ONE_DISCONTINUED);}
					statement.setDate(1, Date.valueOf((LocalDate) value));
					statement.setInt(2, id_computer);
					statement.executeUpdate();
				} catch (SQLException e) {
					e.getMessage();
					e.printStackTrace();
				} 
				finally {
					closeSetStatement(rs, statement);
					DBConnection.close();
				}
			} 
			else if (field == 4) {
				ResultSet rs = null;
				PreparedStatement statement = null;
				try {
					DBConnection.getInstance();
					statement = DBConnection.getConnection().prepareStatement(UPDATE_ONE_COMPANY_ID);
					statement.setInt(1, (Integer) value);
					statement.setInt(2, id_computer);
					statement.executeUpdate();
				} catch (SQLException e) {
					e.getMessage();
					e.printStackTrace();
				} 
				finally {
					closeSetStatement(rs, statement);
					DBConnection.close();
				}
			} else {System.out.println("Wrong data entry types.");}
		}
	}
	
	public void deleteOne(int id_computer) {
		if (id_computer !=0) {
			ResultSet rs = null;
			PreparedStatement statement = null;
			try {
				DBConnection.getInstance();
				statement = DBConnection.getConnection().prepareStatement(DELETE_ONE);
				statement.setInt(1, id_computer);
				statement.executeUpdate();
			} catch (SQLException e) {
				e.getMessage();
				e.printStackTrace();
			} 
			finally {
				closeSetStatement(rs, statement);
				DBConnection.close();
			}
		}
	}
	

	public int getNumberComputers() {
		int nbComputers = 0;
		ResultSet rs = null;
		PreparedStatement statement = null;
		try {
			DBConnection.getInstance();
			statement = DBConnection.getConnection().prepareStatement(NUMBER_COMPUTERS_QUERY);
			rs = statement.executeQuery();
			nbComputers = getNumberComputers(rs);
		} catch (SQLException e) {
			e.getMessage();
			e.printStackTrace();
		} 
		finally {
			closeSetStatement(rs, statement);
			DBConnection.close();
		}
		return nbComputers;
	}
	

	private int getNumberComputers(ResultSet rs) {
		int nbComputers = 0;
		try {
			if (rs.next()) {
				nbComputers = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
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

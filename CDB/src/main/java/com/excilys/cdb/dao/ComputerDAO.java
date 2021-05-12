package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import com.excilys.cdb.dao.DBConnection;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

/**
 * Data access object for computers.
 * @author Mathieu_RH
 *
 */
public class ComputerDAO {
	
	private static Connection connection;
	private static final String LIST_COMPUTERS_QUERY = "SELECT id,name,introduced,discontinued,company_id FROM computer;";
	private static final String ONE_COMPUTER_QUERY = "SELECT id,name,introduced,discontinued,company_id FROM computer WHERE id=?;";
	private static final String CREATE_ONE = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?,?,?,?);";
	//private static final String UPDATE_ONE = "UPDATE computer SET ?=? WHERE id=?;";
	private static final String UPDATE_ONE_NAME = "UPDATE computer SET name=? WHERE id=?;";
	private static final String UPDATE_ONE_INTRODUCED = "UPDATE computer SET introduced=? WHERE id=?;";
	private static final String UPDATE_ONE_DISCONTINUED = "UPDATE computer SET discontinued=? WHERE id=?;";
	private static final String UPDATE_ONE_COMPANY_ID = "UPDATE computer SET company_id=? WHERE id=?;";
	private static final String DELETE_ONE = "DELETE FROM computer WHERE id=?;";
	
	public ComputerDAO() {
	}
	
	/**
	 * Query for all computers.
	 * @return listComputers 
	 */
	public ArrayList<Computer> getListComputers() { 
		ArrayList<Computer> listComputers = new ArrayList<Computer>();
		ResultSet rs = null;
		PreparedStatement statement = null;
		try {
			connection = DBConnection.getInstance();
			statement = connection.prepareStatement(LIST_COMPUTERS_QUERY);
			rs = statement.executeQuery();
			listComputers = ComputerMapper.getListComputers(rs);
		} catch (SQLException e) {
			e.getMessage();
			e.printStackTrace();
		} 
		finally {
			closeSetStatement(rs, statement);
			DBConnection.close();
			connection = null;
		}
		return listComputers;
	}
	
	/**
	 * Query for a specific computer
	 * @param id_computer
	 * @return computer
	 */
	public Computer getOneComputer(int id_computer) {
		Computer computer = null;
		ResultSet rs = null;
		PreparedStatement statement = null;
		try {
			connection = DBConnection.getInstance();
			statement = connection.prepareStatement(ONE_COMPUTER_QUERY);
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
			connection = null;
		}
		return computer;
	}
	
	
	/**
	 * Creation of a computer.
	 * @param name Mandatory field.
	 * @param introduced Optional LocalDate 
	 * @param discontinued Optional LocalDate 
	 * @param id_company Optional company id
	 */
	public void createOne(String name, LocalDate introduced, LocalDate discontinued, int id_company) {
		if (name != null) {
			ResultSet rs = null;
			PreparedStatement statement = null;
			try {
				connection = DBConnection.getInstance();
				statement = connection.prepareStatement(CREATE_ONE);
				statement.setString(1, name);
				if (introduced!=null) {
					statement.setDate(2, Date.valueOf(introduced));
				} else {statement.setNull(2, 0);}
				if (discontinued!=null && discontinued.isAfter(introduced)) {
					statement.setDate(3, Date.valueOf(discontinued));
				} else {statement.setNull(3, 0);}
				if (id_company!=0) {
					statement.setInt(4, id_company);
				} else {statement.setNull(4, 0);}
				statement.executeUpdate();
				System.out.println("Computer successfully added !");
			} catch (SQLException e) {
				e.getMessage();
				e.printStackTrace();
			} 
			finally {
				closeSetStatement(rs, statement);
				DBConnection.close();
				connection = null;
			}
		} else {System.out.println("Name can't be null.");}
	}
	
	/**
	 * Modification of a computer. 
	 * @param id_computer computer to modify
	 * @param field Stands for the position of the modified value : 1 for Name, 2 for introducedDate, 3 for discontinuedDate, 4 for company_id.
	 * @param value new value for the modified field
	 */
	public void updateOne(int id_computer, int field, Object value) {
		if (id_computer !=0) {
			if (field == 1 && (String)value != null) {
				ResultSet rs = null;
				PreparedStatement statement = null;
				try {
					connection = DBConnection.getInstance();
					statement = connection.prepareStatement(UPDATE_ONE_NAME);
					//statement.setString(1, "name");
					statement.setString(1, (String)value);
					statement.setInt(2, id_computer);
					statement.executeUpdate();
					System.out.println("Computer successfully modified !");
				
				} catch (SQLException e) {
					e.getMessage();
					e.printStackTrace();
				} 
				finally {
					closeSetStatement(rs, statement);
					DBConnection.close();
					connection = null;
				}
			}
			else if (field == 2 || field==3) {
				ResultSet rs = null;
				PreparedStatement statement = null;
				//String type = (field == 2) ? "introduced" : "discontinued";
				try {
					connection = DBConnection.getInstance();
					if (field == 2) {
						statement = connection.prepareStatement(UPDATE_ONE_INTRODUCED);
					} else {statement = connection.prepareStatement(UPDATE_ONE_DISCONTINUED);}
					//statement.setString(1, type);
					statement.setDate(1, Date.valueOf((LocalDate) value));
					statement.setInt(2, id_computer);
					statement.executeUpdate();
					System.out.println("Computer successfully modified !");
				} catch (SQLException e) {
					e.getMessage();
					e.printStackTrace();
				} 
				finally {
					closeSetStatement(rs, statement);
					DBConnection.close();
					connection = null;
				}
			} 
			else if (field == 4) {
				ResultSet rs = null;
				PreparedStatement statement = null;
				try {
					connection = DBConnection.getInstance();
					statement = connection.prepareStatement(UPDATE_ONE_COMPANY_ID);
					//statement.setString(1, "company_id");
					statement.setInt(1, (int) value);
					statement.setInt(2, id_computer);
					statement.executeUpdate();
					System.out.println("Computer successfully modified !");
				} catch (SQLException e) {
					e.getMessage();
					e.printStackTrace();
				} 
				finally {
					closeSetStatement(rs, statement);
					DBConnection.close();
					connection = null;
				}
			} else {System.out.println("Wrong data entry types.");}
		}
	}
	
	/**
	 * Deletion of a computer
	 * @param id_computer
	 */
	public void deleteOne(int id_computer) {
		if (id_computer !=0) {
			ResultSet rs = null;
			PreparedStatement statement = null;
			try {
				connection = DBConnection.getInstance();
				statement = connection.prepareStatement(DELETE_ONE);
				statement.setInt(1, id_computer);
				statement.executeUpdate();
				System.out.println("Computer successfully deleted !");
			} catch (SQLException e) {
				e.getMessage();
				e.printStackTrace();
			} 
			finally {
				closeSetStatement(rs, statement);
				DBConnection.close();
				connection = null;
			}
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
        }
    }
	
}

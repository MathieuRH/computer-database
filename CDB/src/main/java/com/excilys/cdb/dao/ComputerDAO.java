package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import com.excilys.cdb.dao.DBConnection;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class ComputerDAO {
	
	private static Connection connection;
	private static final String LIST_COMPUTERS_QUERY = "SELECT id,name,introduced,discontinued,company_id FROM computer;";
	private static final String GET_COMPANY = "SELECT id, name FROM company WHERE id=?;";
	private static final String ONE_COMPUTER_QUERY = "SELECT id,name,introduced,discontinued,company_id FROM computer WHERE id=?;";
	private static final String CREATE_ONE = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?,?,?,?);";
	private static final String UPDATE_ONE = "UPDATE computer SET ?=? WHERE id=?;";
	private static final String DELETE_ONE = "DELETE FROM computer WHERE id=?;";
	
	public ComputerDAO() {
	}
	
	/**
	 * Print all the computers
	 */
	public void getListComputers() {
		ResultSet res = null;
		PreparedStatement statement = null;
		try {
			connection = DBConnection.getInstance();
			statement = connection.prepareStatement(LIST_COMPUTERS_QUERY);
			res = statement.executeQuery();
			writeListComputers(res);
		} catch (SQLException e) {
			e.getMessage();
			e.printStackTrace();
		} 
		finally {
			closeSetStatement(res, statement);
			DBConnection.close();
			connection = null;
		}
	}
	
	/**
	 * Print one specific computer
	 */
	public void getOneComputer(int id_computer) {
		ResultSet rs = null;
		PreparedStatement statement = null;
		try {
			connection = DBConnection.getInstance();
			statement = connection.prepareStatement(ONE_COMPUTER_QUERY);
			statement.setInt(1, id_computer);
			rs = statement.executeQuery();
			writeListComputers(rs);
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
	
	
	/**
	 * Prints the result of the @getListComputers function
	 * @param resultSet
	 * @throws SQLException
	 */
	private void writeListComputers(ResultSet resultSet) throws SQLException {
        // ResultSet is initially before the first data set
        while (resultSet.next()) {
            // It is possible to get the columns via name
            // also possible to get the columns via the column number
            // which starts at 1
            // e.g. resultSet.getSTring(2);
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            Date dateInt = resultSet.getDate("introduced");
            Date dateDis = resultSet.getDate("discontinued");
            int company_id = resultSet.getInt("company_id");
            Computer comp = new Computer(id, name);
            if (dateInt != null) {
            	comp.setIntroducedDate(dateInt.toLocalDate());
            }
            if (dateDis != null) {
            	comp.setDiscontinuedDate(dateDis.toLocalDate());
            }
            if (company_id != 0) {
            	comp.setCompany(getCompany(company_id));
            }
            System.out.println(comp);
        }
    }
	

	/**
	 * Get a company from its id
	 */
	public Company getCompany(int company_id) {
		Company company = new Company();
		ResultSet res = null;
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(GET_COMPANY);
			statement.setInt(1, company_id);
			res = statement.executeQuery();
			res.next();
            int id = res.getInt("id");
            String name = res.getString("name");
            company.setId(id);
            company.setName(name);
		} catch (SQLException e) {
			e.getMessage();
			e.printStackTrace();
		} 
		finally {
			closeSetStatement(res, statement);
		}
		return company;
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
				if (discontinued!=null) {
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
		if (field == 1 && (String)value != null) {
			ResultSet rs = null;
			PreparedStatement statement = null;
			try {
				connection = DBConnection.getInstance();
				statement = connection.prepareStatement(UPDATE_ONE);
				statement.setString(1, "name");
				statement.setString(2, (String)value);
				statement.setInt(3, id_computer);
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
			String type = (field == 2) ? "introduced" : "discontinued";
			try {
				connection = DBConnection.getInstance();
				statement = connection.prepareStatement(UPDATE_ONE);
				statement.setString(1, type);
				statement.setDate(2, Date.valueOf((LocalDate) value));
				statement.setInt(3, id_computer);
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
				statement = connection.prepareStatement(UPDATE_ONE);
				statement.setString(1, "company_id");
				statement.setInt(2, (int) value);
				statement.setInt(3, id_computer);
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
	
	/**
	 * Deletion of a computer
	 * @param id_computer
	 */
	public void deleteOne(int id_computer) {
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

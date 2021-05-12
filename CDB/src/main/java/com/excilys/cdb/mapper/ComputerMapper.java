package com.excilys.cdb.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.model.Computer;

/**
 * Mapping class for computers
 * @author Mathieu_RH
 *	
 */
public class ComputerMapper {
	
	/**
	 * Conversion function from query ResultSet to Computer ArrayList
	 * @param rs ResultSet
	 * @return listComputers 
	 */
	public static ArrayList<Computer> getListComputers(ResultSet rs) {
		ArrayList<Computer> listComputers = new ArrayList<Computer>();
		try {
			while (rs.next()) {
			    int id = rs.getInt("id");
			    String name = rs.getString("name");
			    Date dateInt = rs.getDate("introduced");
			    Date dateDis = rs.getDate("discontinued");
			    int company_id = rs.getInt("company_id");
			    Computer comp = new Computer(id, name);
			    if (dateInt != null) {
			    	comp.setIntroducedDate(dateInt.toLocalDate());
			    }
			    if (dateDis != null) {
			    	comp.setDiscontinuedDate(dateDis.toLocalDate());
			    }
			    if (company_id != 0) {
			    	CompanyDAO companyDAO = new CompanyDAO();
			    	comp.setCompany(companyDAO.getOneCompany(company_id));
			    }
			    listComputers.add(comp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listComputers;
	}
	
	/**
	 * Conversion function from query ResultSet to single Computer
	 * @param rs ResultSet
	 * @return computer
	 */
	public static Computer getOneComputer(ResultSet rs) {
		return getListComputers(rs).get(0);
	}

	public static int getNumberComputers(ResultSet rs) {
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
	
}


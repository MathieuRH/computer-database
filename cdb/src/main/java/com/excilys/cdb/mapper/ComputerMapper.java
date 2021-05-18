package com.excilys.cdb.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Computer.ComputerBuilder;

/**
 * Mapping class for computers
 * @author Mathieu_RH
 *	
 */
public class ComputerMapper {
	
	public static ArrayList<Computer> getListComputers(ResultSet rs) throws SQLException {
		ArrayList<Computer> listComputers = new ArrayList<Computer>();
		while (rs.next()) {
		    int id = rs.getInt("id");
		    String name = rs.getString("name");
		    Date dateInt = rs.getDate("introduced");
		    Date dateDis = rs.getDate("discontinued");
		    int company_id = rs.getInt("company_id");
		    ComputerBuilder comp = new Computer.ComputerBuilder(id, name);
		    if (dateInt != null) {
		    	comp.introducedDate(dateInt.toLocalDate());
		    }
		    if (dateDis != null) {
		    	comp.discontinuedDate(dateDis.toLocalDate());
		    }
		    if (company_id != 0) {
		    	CompanyDAO companyDAO = CompanyDAO.getInstance();
		    	comp.company(companyDAO.getOneCompany(company_id));
		    }
		    listComputers.add(comp.build());
		}
		return listComputers;
	}
	
	public static Computer getOneComputer(ResultSet rs) throws SQLException {
		return getListComputers(rs).get(0);
	}	
}


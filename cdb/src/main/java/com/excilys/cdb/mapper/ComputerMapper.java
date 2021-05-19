package com.excilys.cdb.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.exceptions.ComputerNotFoundException;
import com.excilys.cdb.exceptions.ConnectionException;
import com.excilys.cdb.exceptions.QueryException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Computer.ComputerBuilder;

/**
 * Mapping class for computers
 * @author Mathieu_RH
 *	
 */
public class ComputerMapper {

	private static Logger logger = LoggerFactory.getLogger(ComputerMapper.class);
	
	public static ArrayList<Computer> getListComputers(ResultSet rs) throws SQLException, ConnectionException, QueryException {
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
	
	public static Computer getOneComputer(ResultSet rs) throws SQLException, ConnectionException, QueryException, ComputerNotFoundException {
		try {
			return getListComputers(rs).get(0);
		} catch (IndexOutOfBoundsException e) {
			logger.error("Index exception : " + e);
			throw new ComputerNotFoundException();
		}
	}
}


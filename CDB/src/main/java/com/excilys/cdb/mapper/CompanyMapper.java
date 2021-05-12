package com.excilys.cdb.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

/**
 * Mapping class for companies
 * @author Mathieu_RH
 *	
 */
public class CompanyMapper {
	
	/**
	 * Conversion function from query ResultSet to Company ArrayList
	 * @param rs ResultSet
	 * @return listCompanies 
	 */
	public static ArrayList<Company> getListCompanies(ResultSet rs){
		ArrayList <Company> listCompanies = new ArrayList<Company>();
		try {
			while (rs.next()) {
			    int id = rs.getInt("id");
			    String name = rs.getString("name");
			    Company company = new Company(id, name);
			    listCompanies.add(company);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listCompanies;
	}
	
	/**
	 * Conversion function from query ResultSet to single Company
	 * @param rs ResultSet
	 * @return company
	 */
	public static Company getOneCompany(ResultSet rs) throws SQLException {
		Company company = new Company();
		rs.next();
        int id = rs.getInt("id");
        String name = rs.getString("name");
        company.setId(id);
        company.setName(name);
        return company;
	}
	
}

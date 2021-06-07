package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Company;

@Component
public class CompanyMapperSQL {
	
	public ArrayList<Company> getListCompanies(ResultSet rs) throws SQLException {
		ArrayList <Company> listCompanies = new ArrayList<Company>();
		while (rs.next()) {
		    int id = rs.getInt("id");
		    String name = rs.getString("name");
		    Company company = new Company(id, name);
		    listCompanies.add(company);
		}
		return listCompanies;
	}
	

	public Company getOneCompany(ResultSet rs) throws SQLException {
        return getListCompanies(rs).get(0);
	}
}

package com.excilys.cdb.service;

import java.util.ArrayList;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.model.Company;

/**
 * Service class for companies.
 * @author Mathieu_RH
 *
 */
public class CompanyService {
	
	private CompanyDAO companyDAO;
	
	public CompanyService() {
		companyDAO = new CompanyDAO();
	}

	public CompanyDAO getComputerDAO() {
		return companyDAO;
	}

	public void setComputerDAO(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}

	public int getNumberCompanies() {
		return companyDAO.getNumberCompanies();
	}

	public ArrayList<Company> getListCompanies(int limit, int offset) {
		return companyDAO.getListCompanies(limit, offset);
	}
}

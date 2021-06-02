package com.excilys.cdb.service;

import java.util.ArrayList;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.exceptions.ConnectionException;
import com.excilys.cdb.exceptions.QueryException;
import com.excilys.cdb.model.Company;

/**
 * Service class for companies.
 * @author Mathieu_RH
 *
 */
public class CompanyService {
	
	private static CompanyService instance;
	private CompanyDAO companyDAO;
	
	private CompanyService() {
		companyDAO = CompanyDAO.getInstance();
	}
	
	public static CompanyService getInstance() {
		if (instance == null) {
			instance = new CompanyService();
		}
		return instance;
	}

	public CompanyDAO getComputerDAO() {
		return companyDAO;
	}

	public void setComputerDAO(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}

	public int getNumberCompanies() throws ConnectionException, QueryException {
		return companyDAO.getNumberCompanies();
	}

	public ArrayList<Company> getListCompanies(int limit, int offset) throws ConnectionException, QueryException {
		return companyDAO.getListCompanies(limit, offset);
	}

	public void deleteOne(int company_id) throws ConnectionException, QueryException {
		companyDAO.deleteOne(company_id);
	}

	public void createOne(String name) throws ConnectionException, QueryException {
		companyDAO.createOne(name);
	}
}

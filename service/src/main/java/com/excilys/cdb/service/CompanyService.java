package com.excilys.cdb.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.exceptions.QueryException;
import com.excilys.cdb.model.Company;

@Service
public class CompanyService {

	private CompanyDAO companyDAO;
	
	public CompanyService(CompanyDAO companyDAO) {
		this.companyDAO=companyDAO;
	}

	public int getNumberCompanies() throws QueryException {
		return companyDAO.getNumberCompanies();
	}

	public ArrayList<Company> getListCompanies(int limit, int offset) throws QueryException {
		return companyDAO.getListCompanies(limit, offset);
	}
	
	public Optional<Company> getCompany(int company_id) throws QueryException {
		return companyDAO.getOneCompany(company_id);
	}

	public void deleteOne(int company_id) throws QueryException {
		companyDAO.deleteOne(company_id);
	}

	public void createOne(String name) throws QueryException {
		companyDAO.createOne(name);
	}

	
	public CompanyDAO getCompanyDAO() {
		return companyDAO;
	}

	public void setCompanyDAO(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}
}

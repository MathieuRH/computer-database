package com.excilys.cdb.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.cdb.exceptions.ConnectionException;
import com.excilys.cdb.exceptions.QueryException;
import com.excilys.cdb.model.Company;

public class CompanyDAOTest {

	@Autowired
	CompanyDAO companyDAO;
	
	@Before
	public void setUp() {
	}
	 
	@After
	   public void tearDown() {
	}
	
	@Test
	public void testGetNbCompanies() {
		int nbCompanies;
		try {
			nbCompanies = companyDAO.getNumberCompanies();
			assertNotEquals(nbCompanies, 0);
		} catch (ConnectionException | QueryException e) {
			fail("Failed to get number companies :" + e.getMessage());
		}
	}

	@Test
	public void testGetListCompanies() {
		int nbCompanies;
		try {
			nbCompanies = companyDAO.getNumberCompanies();
			ArrayList<Company> result = companyDAO.getListCompanies(nbCompanies, 0);
			assertFalse(result.isEmpty());
		} catch (ConnectionException | QueryException e) {
			fail("Failed to get Company list :" + e.getMessage());
		}
	}
	
	@Test
	public void testGetOneCompany() {
		try {
			Company company1 = new Company(1,"Apple Inc.");
			Company getFirstCompany = companyDAO.getOneCompany(1);
			assertEquals(company1, getFirstCompany);
		} catch (ConnectionException | QueryException e) {
			fail("Failed to get number companies :" + e.getMessage());
		}
	}
	

}

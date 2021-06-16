package com.excilys.cdb.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.cdb.config.SpringTestConfig;
import com.excilys.cdb.exceptions.QueryException;
import com.excilys.cdb.model.Company;

@RunWith( SpringJUnit4ClassRunner.class) 
@ContextConfiguration(classes = SpringTestConfig.class)
public class CompanyDAOTest {

	@Autowired
	private CompanyDAO companyDAO;

	private final Company TEST_COMPANY = new Company(1,"Apple Inc.");

	@Test
	public void testGetNbCompanies() {
		int nbCompanies;
		try {
			nbCompanies = companyDAO.getNumberCompanies();
			assertNotEquals(nbCompanies, 0);
		} catch (QueryException e) {
			fail("Failed to get nb companies :" + e.getMessage());
		}
	}

	@Ignore
	@Test
	public void testGetListCompanies() {
		int nbCompanies;
		try {
			nbCompanies = companyDAO.getNumberCompanies();
			ArrayList<Company> result = companyDAO.getListCompanies(nbCompanies, 0);
			assertFalse(result.isEmpty());
		} catch (QueryException e) {
			fail("Failed to get Company list :" + e.getMessage());
		}
	}

	@Ignore
	@Test
	public void testGetOneCompany() {
		try {
			Company getFirstCompany = companyDAO.getOneCompany(1);
			System.out.println(getFirstCompany);
			assertEquals(TEST_COMPANY, getFirstCompany);
		} catch (QueryException e) {
			fail("Failed to get number companies :" + e.getMessage());
		}
	}
	

}

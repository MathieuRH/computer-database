package com.excilys.cdb.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.cdb.config.SpringTestConfig;
import com.excilys.cdb.exceptions.ComputerNotFoundException;
import com.excilys.cdb.exceptions.QueryException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;

@RunWith( SpringJUnit4ClassRunner.class) 
@ContextConfiguration(classes = SpringTestConfig.class)
public class ComputerDAOTest {
	
	@Autowired
	private ComputerDAO computerDAO;

	private final Company TEST_COMPANY= new Company(1,"Apple Inc.");
	private final Computer TEST_COMPUTER = new Computer.ComputerBuilder(1, "MacBook Pro 15.4 inch").company(TEST_COMPANY).build();
	
	@Ignore
	@Test
	public void testGetNbComputers() {
		int nbComputers;
		try {
			nbComputers = computerDAO.getNumberComputers();
			assertNotEquals(nbComputers, 0);
		} catch (QueryException e) {
			fail("Failed to get number companies :" + e.getMessage());
		}
	}

	@Ignore
	@Test
	public void testGetListComputers() {
		int nbComputers;
		try {
			nbComputers = computerDAO.getNumberComputers();
			Page pagination = new Page(nbComputers);
			ArrayList<Computer> result = computerDAO.getListComputers(pagination, "orderById", "");
			assertFalse(result.isEmpty());
		} catch (QueryException e) {
			fail("Failed to get Company list :" + e.getMessage());
		}
	}

	@Ignore
	@Test
	public void testGetOneComputer() {
		try {
			Optional<Computer> getFirstComputer = computerDAO.getOneComputer(1);
			if (getFirstComputer.isPresent()) {
				assertEquals(TEST_COMPUTER, getFirstComputer.get());
			} else {
				throw new ComputerNotFoundException();
			} 
		} catch (QueryException|ComputerNotFoundException e) {
			fail("Failed to get wanted company :" + e.getMessage());
		}
	}
}

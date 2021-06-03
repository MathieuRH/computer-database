package com.excilys.cdb.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.excilys.cdb.exceptions.ComputerNotFoundException;
import com.excilys.cdb.exceptions.ConnectionException;
import com.excilys.cdb.exceptions.QueryException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class ComputerDAOTest {

	ComputerDAO computerDAO;
	
	@Before
	public void setUp() {
		computerDAO = ComputerDAO.getInstance();
	}
	 
	@After
	   public void tearDown() {
		computerDAO = null;
	}

	@Test
	public void testGetNbComputers() {
		int nbComputers;
		try {
			nbComputers = computerDAO.getNumberComputers();
			assertNotEquals(nbComputers, 0);
		} catch (ConnectionException | QueryException e) {
			fail("Failed to get number companies :" + e.getMessage());
		}
	}
	
	@Test
	public void testGetListComputers() {
		int nbComputers;
		try {
			nbComputers = computerDAO.getNumberComputers();
			ArrayList<Computer> result = computerDAO.getListComputers(nbComputers, 0, "orderById");
			assertFalse(result.isEmpty());
		} catch (ConnectionException | QueryException e) {
			fail("Failed to get Company list :" + e.getMessage());
		}
	}

	@Test
	public void testGetOneComputer() {
		try {
			Company company1 = new Company(1,"Apple Inc.");
			Computer comp1 = new Computer.ComputerBuilder(1, "MacBook Pro 15.4 inch").company(company1).build();
			Optional<Computer> getFirstComputer = computerDAO.getOneComputer(1);
			if (getFirstComputer.isPresent()) {
				assertEquals(comp1, getFirstComputer.get());
			} else {
				throw new ComputerNotFoundException();
			} 
		} catch (ConnectionException | QueryException|ComputerNotFoundException e) {
			fail("Failed to get number companies :" + e.getMessage());
		}
	}

}

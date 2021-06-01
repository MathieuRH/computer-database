package com.excilys.cdb.service;

import java.time.LocalDate;
import java.util.ArrayList;

import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.dto.ComputerDTOJsp;
import com.excilys.cdb.exceptions.ComputerNotFoundException;
import com.excilys.cdb.exceptions.ConnectionException;
import com.excilys.cdb.exceptions.QueryException;
import com.excilys.cdb.model.Computer;

/**
 * Service class for computers.
 * @author Mathieu_RH
 *
 */
public class ComputerService {

	private static ComputerService instance;
	private ComputerDAO computerDAO;
	
	private ComputerService() {
		computerDAO = ComputerDAO.getInstance();
	}
	
	public static ComputerService getInstance() {
		if (instance == null) {
			instance = new ComputerService();
		}
		return instance;
	}

	public Computer getOneComputer(int id_computer) throws ConnectionException, QueryException, ComputerNotFoundException {
		return computerDAO.getOneComputer(id_computer);
	}
	
	public ArrayList<Computer> getListComputers(int limit, int offset) throws ConnectionException, QueryException { 
		return computerDAO.getListComputers(limit, offset);
	}
	
	public int getNumberComputers() throws ConnectionException, QueryException {
		return computerDAO.getNumberComputers();
	}
	
	public ComputerDAO getComputerDAO() {
		return computerDAO;
	}

	public void setComputerDAO(ComputerDAO computerDAO) {
		this.computerDAO = computerDAO;
	}

	//TODO : get rid of
	public void createOne(String name, LocalDate introduced, LocalDate discontinued, int company_id) throws ConnectionException, QueryException {
		computerDAO.createOne(name, introduced, discontinued, company_id);
	}
	
	public void createOne(Computer computer) throws ConnectionException, QueryException {
		computerDAO.createOne(computer);
	}

	public void updateOne(Computer computer) throws ConnectionException, QueryException {
		computerDAO.updateOne(computer);
	}

	public void deleteOne(int computer_id) throws ConnectionException, QueryException {
		computerDAO.deleteOne(computer_id);
	}

	public ArrayList<Computer> getListByName(int limit, int offset, String name) throws ConnectionException, QueryException, ComputerNotFoundException {
		return computerDAO.getListByName(limit, offset, name);
	}
}

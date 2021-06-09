package com.excilys.cdb.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.exceptions.ConnectionException;
import com.excilys.cdb.exceptions.QueryException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;

@Service
public class ComputerService {

	private ComputerDAO computerDAO;
	
	public ComputerService (ComputerDAO computerDAO) {
		this.computerDAO=computerDAO;
	}

	public Optional<Computer> getOneComputer(int id_computer) throws ConnectionException, QueryException{
		return computerDAO.getOneComputer(id_computer);
	}
	
	public ArrayList<Computer> getListComputers(Page pagination, String query, String name) throws ConnectionException, QueryException { 
		return computerDAO.getListComputers(pagination, query, name);
	}
	
	public int getNumberComputers() throws ConnectionException, QueryException {
		return computerDAO.getNumberComputers();
	}

	public int getNumberComputersByName(String name) throws ConnectionException, QueryException {
		return computerDAO.getNumberComputersByName(name);
	}
	
	public ComputerDAO getComputerDAO() {
		return computerDAO;
	}

	public void setComputerDAO(ComputerDAO computerDAO) {
		this.computerDAO = computerDAO;
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
}

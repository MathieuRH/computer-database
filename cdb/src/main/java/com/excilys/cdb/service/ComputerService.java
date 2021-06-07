package com.excilys.cdb.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.exceptions.ComputerNotFoundException;
import com.excilys.cdb.exceptions.ConnectionException;
import com.excilys.cdb.exceptions.QueryException;
import com.excilys.cdb.model.Computer;

@Service
public class ComputerService {

	@Autowired
	private ComputerDAO computerDAO;

	public Optional<Computer> getOneComputer(int id_computer) throws ConnectionException, QueryException{
		return computerDAO.getOneComputer(id_computer);
	}
	
	public ArrayList<Computer> getListComputers(int limit, int offset, String query) throws ConnectionException, QueryException { 
		return computerDAO.getListComputers(limit, offset, query);
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

	public ArrayList<Computer> getListByName(int limit, int offset, String name) throws ConnectionException, QueryException, ComputerNotFoundException {
		return computerDAO.getListByName(limit, offset, name);
	}
}

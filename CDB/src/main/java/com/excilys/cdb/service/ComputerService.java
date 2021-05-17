package com.excilys.cdb.service;

import java.time.LocalDate;
import java.util.ArrayList;

import com.excilys.cdb.dao.ComputerDAO;
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

	public Computer getOneComputer(int id_computer) {
		return computerDAO.getOneComputer(id_computer);
	}
	
	public ArrayList<Computer> getListComputers(int limit, int offset) { 
		return computerDAO.getListComputers(limit, offset);
	}
	
	public int getNumberComputers() {
		return computerDAO.getNumberComputers();
	}
	
	public ComputerDAO getComputerDAO() {
		return computerDAO;
	}

	public void setComputerDAO(ComputerDAO computerDAO) {
		this.computerDAO = computerDAO;
	}

	public void createOne(String name, LocalDate introduced, LocalDate discontinued, int company_id) {
		computerDAO.createOne(name, introduced, discontinued, company_id);
	}

	public void updateOne(int computer_id, int field, Object value) {
		computerDAO.updateOne(computer_id, field, value);
	}

	public void deleteOne(int computer_id) {
		computerDAO.deleteOne(computer_id);
	}
}

package com.excilys.cdb.service;

import java.time.LocalDate;
import java.util.ArrayList;

import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.model.Computer;

public class ComputerService {

	private ComputerDAO computerDAO;
	
	public ComputerService() {
		computerDAO = new ComputerDAO();
	}

	public Computer getOneComputer(int id_computer) {
		return computerDAO.getOneComputer(id_computer);
	}
	
	public ArrayList<Computer> getListComputers() { 
		return computerDAO.getListComputers();
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

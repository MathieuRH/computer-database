package com.excilys.cdb.controller;

import java.time.LocalDate;
import java.util.ArrayList;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

public class CLIController {
	
	private static CLIController instance;
	
	private CompanyService companyService;
	private ComputerService computerService;
	
	private CLIController() {
		computerService = ComputerService.getInstance();
		companyService = CompanyService.getInstance();
	}
	
	public static CLIController getInstance() {
		if (instance == null) {
			instance = new CLIController();
		}
		return instance;
	}
	
	public Computer getOneComputer(int id_computer) {
		return computerService.getOneComputer(id_computer);
	}
	
	public ArrayList<Computer> getListComputers(int limit, int offset) { 
		return computerService.getListComputers(limit, offset);
	}
	
	public int getNumberComputers() {
		return computerService.getNumberComputers();
	}

	public boolean createOne(String name, LocalDate introduced, LocalDate discontinued, int company_id) {
		boolean isCorrect = false;
		if (introduced == null || (introduced != null && introduced.isAfter(LocalDate.of(1970, 1, 1)))) {
			if (discontinued == null || (discontinued.isAfter(introduced) && discontinued.isAfter(LocalDate.of(1970, 1, 1)))) {
				computerService.createOne(name, introduced, discontinued, company_id);
				isCorrect = true;
			}
		}
		return isCorrect;
	}

	public boolean updateOne(int computer_id, int field, Object value) {
		boolean isCorrect = false;
		if (field == 1 || field == 4) {
			computerService.updateOne(computer_id, field, value);
			isCorrect = true;
		}
		else if (field == 2){
			LocalDate introduced = (LocalDate) value;
			if (introduced == null || (introduced != null && introduced.isAfter(LocalDate.of(1970, 1, 1)))) {
				computerService.updateOne(computer_id, field, value);
				isCorrect = true;
			}
		}
		else {
			LocalDate introduced = getOneComputer(computer_id).getIntroducedDate();
			LocalDate discontinued = (LocalDate) value;
			if (discontinued == null || (introduced != null && discontinued.isAfter(introduced) && discontinued.isAfter(LocalDate.of(1970, 1, 1)))) {
				computerService.updateOne(computer_id, field, value);
				isCorrect = true;
			}
		}
		return isCorrect;
	}


	public void deleteOne(int computer_id) {
		computerService.deleteOne(computer_id);
	}
	
	public int getNumberCompanies() {
		return companyService.getNumberCompanies();
	}

	public ArrayList<Company> getListCompanies(int limit, int offset) {
		return companyService.getListCompanies(limit, offset);
	}
	
}

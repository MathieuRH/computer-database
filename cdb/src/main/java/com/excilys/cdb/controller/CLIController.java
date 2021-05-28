package com.excilys.cdb.controller;

import java.time.LocalDate;
import java.util.ArrayList;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exceptions.ComputerNotFoundException;
import com.excilys.cdb.exceptions.ConnectionException;
import com.excilys.cdb.exceptions.QueryException;
import com.excilys.cdb.mapper.CompanyDTOMapper;
import com.excilys.cdb.mapper.ComputerDTOMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.ui.CLI;

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
	
	public ComputerDTO getOneComputer(int id_computer) {
		//TODO : Return optional ?
		try {
			Computer computer = computerService.getOneComputer(id_computer);
			return ComputerDTOMapper.computerToDTO(computer);
		} catch (ConnectionException | QueryException | ComputerNotFoundException e) {
			CLI.writeMessage(e.getMessage());
			return null;
		}
	}
	
	public ArrayList<ComputerDTO> getListComputers(int limit, int offset) { 
		try {
			ArrayList<Computer> listComputers = computerService.getListComputers(limit, offset);
			return ComputerDTOMapper.listComputersToDTO(listComputers);
		} catch (ConnectionException | QueryException e) {
			CLI.writeMessage(e.getMessage());
			return new ArrayList<ComputerDTO>();
		}
	}
	
	public int getNumberComputers() {
		//TODO : optional ??
		try {
			return computerService.getNumberComputers();
		} catch (ConnectionException | QueryException e) {
			CLI.writeMessage(e.getMessage());
			return 0;
		}
	}

	public boolean createOne(String name, LocalDate introduced, LocalDate discontinued, int company_id) {
		boolean isCorrect = false;
		if (introduced == null || (introduced != null && introduced.isAfter(LocalDate.of(1970, 1, 1)) && introduced.isBefore(LocalDate.of(2038,01,19)))) {
			if (discontinued == null || (discontinued.isAfter(introduced) && discontinued.isAfter(LocalDate.of(1970, 1, 1)) && discontinued.isBefore(LocalDate.of(2038,01,19)))) {
				try {
					computerService.createOne(name, introduced, discontinued, company_id);
					isCorrect = true;
				} catch (ConnectionException | QueryException e) {
					CLI.writeMessage(e.getMessage());
				}
			}
		}
		return isCorrect;
	}
	
	public boolean createOne(ComputerDTO computerDTO) {
		boolean isCorrect = false;
		LocalDate introduced = null;
		LocalDate discontinued = null;
		if (! "".equals(computerDTO.getIntroduced())) {
			introduced = LocalDate.parse(computerDTO.getIntroduced());
		} else {computerDTO.setIntroduced(null);}
		if (! "".equals(computerDTO.getDiscontinued())) {
			discontinued = LocalDate.parse(computerDTO.getDiscontinued());
		} else {computerDTO.setDiscontinued(null);}
		if (introduced == null || (introduced != null && introduced.isAfter(LocalDate.of(1970, 1, 1)) && introduced.isBefore(LocalDate.of(2038,01,19)))) {
			if (discontinued == null || (introduced != null && discontinued.isAfter(introduced) && discontinued.isAfter(LocalDate.of(1970, 1, 1)) && discontinued.isBefore(LocalDate.of(2038,01,19)))) {
				try {
					computerService.createOne(computerDTO);
					isCorrect = true;
				} catch (ConnectionException | QueryException e) {
					CLI.writeMessage(e.getMessage());
				}
			}
		}
		return isCorrect;
	}

	/*
	public boolean updateOne(int computer_id, int field, Object value) {
		//TODO : Exception catched from service
		boolean isCorrect = false;
		try {
			if (field == 1 || field == 4) {
				computerService.updateOne(computer_id, field, value);
				isCorrect = true;
			}
			else if (field == 2){
				LocalDate introduced = (LocalDate) value;
				if (introduced == null || (introduced != null && introduced.isAfter(LocalDate.of(1970, 1, 1)) && introduced.isBefore(LocalDate.of(2038,01,19)))) {
					computerService.updateOne(computer_id, field, value);
					isCorrect = true;
				}
			}
			else {
				LocalDate introduced = getOneComputer(computer_id).getIntroducedDate();
				LocalDate discontinued = (LocalDate) value;
				if (discontinued == null || (introduced != null && discontinued.isAfter(introduced) && discontinued.isAfter(LocalDate.of(1970, 1, 1)) && discontinued.isBefore(LocalDate.of(2038,01,19)))) {
					computerService.updateOne(computer_id, field, value);
					isCorrect = true;
				}
			}
		} catch (ConnectionException | QueryException e) {
			CLI.writeMessage(e.getMessage());
		}
		return isCorrect;
	}
	*/


	public void deleteOne(int computer_id) {
		try {
			computerService.deleteOne(computer_id);
		} catch (ConnectionException | QueryException e) {
			CLI.writeMessage(e.getMessage());
		}
	}
	
	public int getNumberCompanies() {
		try {
			return companyService.getNumberCompanies();
		} catch (ConnectionException | QueryException e) {
			CLI.writeMessage(e.getMessage());
			return 0;
		}
	}

	public ArrayList<CompanyDTO> getListCompanies(int limit, int offset) {
		try {
			ArrayList<Company> listCompanies = companyService.getListCompanies(limit, offset);
			return CompanyDTOMapper.listCompaniesToDTO(listCompanies);
		} catch (ConnectionException | QueryException e) {
			CLI.writeMessage(e.getMessage());
			return new ArrayList<CompanyDTO>();
		} 
	}
	
}

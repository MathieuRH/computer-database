package com.excilys.cdb.controller;

import java.util.ArrayList;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dto.CompanyDTOJsp;
import com.excilys.cdb.dto.ComputerDTOJsp;
import com.excilys.cdb.exceptions.ComputerNotFoundException;
import com.excilys.cdb.exceptions.ConnectionException;
import com.excilys.cdb.exceptions.InputException;
import com.excilys.cdb.exceptions.QueryException;
import com.excilys.cdb.mapper.CompanyMapperServlet;
import com.excilys.cdb.mapper.ComputerMapperServlet;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.ui.CLI;
import com.excilys.cdb.verification.Verificator;

public class CLIController {
	
	private static CLIController instance;
	
	private CompanyService companyService;
	private ComputerService computerService;
	private ComputerMapperServlet computerMapper;
	private CompanyMapperServlet companyMapper;
	private Verificator verificator;
	
	private static Logger logger = LoggerFactory.getLogger(CLIController.class);
	
	private CLIController() {
		computerService = ComputerService.getInstance();
		companyService = CompanyService.getInstance();
		computerMapper = ComputerMapperServlet.getInstance();
		companyMapper = CompanyMapperServlet.getInstance();
		verificator = Verificator.getInstance();
	}
	
	public static CLIController getInstance() {
		if (instance == null) {
			instance = new CLIController();
		}
		return instance;
	}
	
	public ComputerDTOJsp getOneComputer(int id_computer) {
		//TODO : Return optional ?
		try {
			Optional<Computer> computer = computerService.getOneComputer(id_computer);
			if (computer.isPresent()) {
				return computerMapper.toDTO(computer.get());
			} else {
				throw new ComputerNotFoundException();
			} 
		} catch (ConnectionException | QueryException | ComputerNotFoundException e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	public ArrayList<ComputerDTOJsp> getListComputers(int limit, int offset) { 
		try {
			ArrayList<Computer> listComputers = computerService.getListComputers(limit, offset, "orderById");
			return computerMapper.listToDTO(listComputers);
		} catch (ConnectionException | QueryException e) {
			logger.error(e.getMessage());
			return new ArrayList<ComputerDTOJsp>();
		}
	}
	
	public int getNumberComputers() {
		try {
			return computerService.getNumberComputers();
		} catch (ConnectionException | QueryException e) {
			CLI.writeMessage(e.getMessage());
			return 0;
		}
	}
	
	public void createOne(ComputerDTOJsp computerDTO) {
		try {
			verificator.verifyComputer(computerDTO);
		} catch (InputException e) {
			logger.error(e.getMessage());
		}
		Computer computer = computerMapper.toComputer(computerDTO);
		try {
			computerService.createOne(computer);
		} catch (ConnectionException | QueryException e) {
			logger.error(e.getMessage());
		}
	}

	public void updateOne(ComputerDTOJsp computerDTO) {
		try {
			verificator.verifyComputer(computerDTO);
		} catch (InputException e) {
			logger.error(e.getMessage());
		}
		Computer computer = computerMapper.toComputer(computerDTO);
		try {
			computerService.updateOne(computer);
		} catch (ConnectionException | QueryException e) {
			logger.error(e.getMessage());
		}
	}


	public void deleteOne(int computer_id) {
		try {
			computerService.deleteOne(computer_id);
		} catch (ConnectionException | QueryException e) {
			logger.error(e.getMessage());
		}
	}
	
	public int getNumberCompanies() {
		try {
			return companyService.getNumberCompanies();
		} catch (ConnectionException | QueryException e) {
			logger.error(e.getMessage());
			return 0;
		}
	}

	public ArrayList<CompanyDTOJsp> getListCompanies(int limit, int offset) {
		try {
			ArrayList<Company> listCompanies = companyService.getListCompanies(limit, offset);
			return companyMapper.listCompaniesToDTO(listCompanies);
		} catch (ConnectionException | QueryException e) {
			logger.error(e.getMessage());
			return new ArrayList<CompanyDTOJsp>();
		} 
	}

	public void deleteOneCompany(int company_id) {
		try {
			companyService.deleteOne(company_id);
		} catch (ConnectionException | QueryException e) {
			logger.error(e.getMessage());
		}
	}

	public void createOneCompany(String name) {
		try {
			companyService.createOne(name);
		} catch (ConnectionException | QueryException e) {
			logger.error(e.getMessage());
		}
	}
	
}

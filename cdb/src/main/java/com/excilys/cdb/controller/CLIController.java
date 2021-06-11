package com.excilys.cdb.controller;

import java.util.ArrayList;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.CompanyDTOJsp;
import com.excilys.cdb.dto.ComputerDTOJsp;
import com.excilys.cdb.exceptions.ComputerNotFoundException;
import com.excilys.cdb.exceptions.InputException;
import com.excilys.cdb.exceptions.QueryException;
import com.excilys.cdb.mapper.CompanyMapperServlet;
import com.excilys.cdb.mapper.ComputerMapperServlet;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.ui.CLI;
import com.excilys.cdb.verification.Verificator;

@Component
public class CLIController {

	private CompanyService companyService;
	private ComputerService computerService;
	private ComputerMapperServlet computerMapper;
	private CompanyMapperServlet companyMapper;
	private Verificator verificator;
	
	public CLIController(CompanyService companyService, ComputerService computerService, ComputerMapperServlet computerMapper,CompanyMapperServlet companyMapper,Verificator verificator) {
		this.companyService=companyService;
		this.computerService=computerService;
		this.computerMapper=computerMapper;
		this.companyMapper=companyMapper;
		this.verificator=verificator;
	}
	
	private static Logger logger = LoggerFactory.getLogger(CLIController.class);
	
	public ComputerDTOJsp getOneComputer(int id_computer) {
		try {
			Optional<Computer> computer = computerService.getOneComputer(id_computer);
			if (computer.isPresent()) {
				return computerMapper.toDTO(computer.get());
			} else {
				throw new ComputerNotFoundException();
			} 
		} catch (QueryException | ComputerNotFoundException e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	public ArrayList<ComputerDTOJsp> getListComputers(Page pagination) { 
		try {
			ArrayList<Computer> listComputers = computerService.getListComputers(pagination, "orderById", "");
			return computerMapper.listToDTO(listComputers);
		} catch (QueryException e) {
			logger.error(e.getMessage());
			return new ArrayList<ComputerDTOJsp>();
		}
	}
	
	public int getNumberComputers() {
		try {
			return computerService.getNumberComputers();
		} catch (QueryException e) {
			CLI.writeMessage(e.getMessage());
			return 0;
		}
	}
	
	public void createOne(ComputerDTOJsp computerDTO) {
		try {
			verificator.verifyComputer(computerDTO);
			Computer computer = computerMapper.toComputer(computerDTO);
			computerService.createOne(computer);
		} catch (InputException | QueryException e) {
			logger.error(e.getMessage());
		}
	}

	public void updateOne(ComputerDTOJsp computerDTO) {
		try {
			verificator.verifyComputer(computerDTO);
			Computer computer = computerMapper.toComputer(computerDTO);
			computerService.updateOne(computer);
		} catch (InputException | QueryException e) {
			logger.error(e.getMessage());
		}
	}


	public void deleteOne(int computer_id) {
		try {
			computerService.deleteOne(computer_id);
		} catch (QueryException e) {
			logger.error(e.getMessage());
		}
	}
	
	public int getNumberCompanies() {
		try {
			return companyService.getNumberCompanies();
		} catch (QueryException e) {
			logger.error(e.getMessage());
			return 0;
		}
	}

	public ArrayList<CompanyDTOJsp> getListCompanies(int limit, int offset) {
		try {
			ArrayList<Company> listCompanies = companyService.getListCompanies(limit, offset);
			return companyMapper.listCompaniesToDTO(listCompanies);
		} catch (QueryException e) {
			logger.error(e.getMessage());
			return new ArrayList<CompanyDTOJsp>();
		} 
	}

	public void deleteOneCompany(int company_id) {
		try {
			companyService.deleteOne(company_id);
		} catch (QueryException e) {
			logger.error(e.getMessage());
		}
	}

	public void createOneCompany(String name) {
		try {
			companyService.createOne(name);
		} catch (QueryException e) {
			logger.error(e.getMessage());
		}
	}
	
}

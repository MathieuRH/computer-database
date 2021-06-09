package com.excilys.cdb.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.cdb.config.SpringConfig;
import com.excilys.cdb.controller.session.SessionVariables;
import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.dto.CompanyDTOJsp;
import com.excilys.cdb.dto.ComputerDTOJsp;
import com.excilys.cdb.exceptions.ConnectionException;
import com.excilys.cdb.exceptions.InputException;
import com.excilys.cdb.exceptions.QueryException;
import com.excilys.cdb.mapper.CompanyMapperServlet;
import com.excilys.cdb.mapper.ComputerMapperServlet;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.verification.Verificator;

@Controller
public class AddComputerController{
	private static final String ADD_COMPUTER_VIEW = "addComputer";
	private static final String DASHBOARD_VIEW = "dashboard";

	private static final int OFFSET_COMPANIES = 0;

	private static Logger logger = LoggerFactory.getLogger(ComputerDAO.class);

	private ComputerService computerService;
	private CompanyService companyService;
	private ComputerMapperServlet computerMapper;
	private CompanyMapperServlet companyMapper;
	private Verificator verificator;

	private SessionVariables sessionVariables;
	
    public AddComputerController(CompanyService companyService, CompanyMapperServlet companyMapper, ComputerService computerService, ComputerMapperServlet computerMapper, SessionVariables sessionVariables, Verificator verificator) {
		this.computerService = computerService;
		this.computerMapper=computerMapper;
		this.companyService=companyService;
		this.companyMapper=companyMapper;
		this.sessionVariables=sessionVariables;
		this.verificator=verificator;
    }

    @GetMapping("addComputer")
	protected ModelAndView doGet() {
    	ModelAndView addComputerView = new ModelAndView(ADD_COMPUTER_VIEW);
    	
    	ArrayList<CompanyDTOJsp> listCompanyDTO = new ArrayList<>();
		try {
			int nbCompanies = companyService.getNumberCompanies();
			ArrayList<Company> listCompany;
			listCompany = companyService.getListCompanies(nbCompanies, OFFSET_COMPANIES);
			listCompanyDTO = companyMapper.listCompaniesToDTO(listCompany);
		} catch (ConnectionException | QueryException e) {
			logger.error(e.getMessage());
		}

		addComputerView.addObject("listCompanyDTO", listCompanyDTO );
		
		return addComputerView;
	}

    @PostMapping("addComputer")
	protected String doPost(@RequestParam(required = false) String computerName, 
			@RequestParam(required = false) String introduced,  
			@RequestParam(required = false) String discontinued, 
			@RequestParam(required = false) String companyId, 
			@RequestParam(required = false) String companyName) {
    	
    	String id = "";
		ComputerDTOJsp computerDTO = new ComputerDTOJsp.ComputerDTOJspBuilder(id, computerName).introduced(introduced)
				.discontinued(discontinued).companyId(companyId).companyName(companyName).build();
		
		try {
			verificator.verifyComputer(computerDTO);
			Computer computer = computerMapper.toComputer(computerDTO);
			computerService.createOne(computer);
			sessionVariables.getPagination().setPage(sessionVariables.getPagination().getNbPages());
			return "redirect:"+DASHBOARD_VIEW;
		} catch (InputException | ConnectionException | QueryException e) {
			logger.error(e.getMessage());
			return "redirect:"+ADD_COMPUTER_VIEW;
		}
	}

}
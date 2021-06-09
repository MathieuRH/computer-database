package com.excilys.cdb.controller;

import org.springframework.stereotype.Controller;

import com.excilys.cdb.controller.session.SessionVariables;
import com.excilys.cdb.mapper.CompanyMapperServlet;
import com.excilys.cdb.mapper.ComputerMapperServlet;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.verification.Verificator;

@Controller
public class EditComputerController {

	private static final String VIEW = "editComputer";
	private static final String DASHBOARD_VIEW = "dashboard";

	private static final int OFFSET_COMPANIES = 0;
	
	private ComputerService computerService;
	private CompanyService companyService;
	private ComputerMapperServlet computerMapper;
	private CompanyMapperServlet companyMapper;
	private Verificator verificator;

	private SessionVariables sessionVariables;
	
    public EditComputerController(CompanyService companyService, CompanyMapperServlet companyMapper, ComputerService computerService, ComputerMapperServlet computerMapper, SessionVariables sessionVariables, Verificator verificator) {
		this.computerService = computerService;
		this.computerMapper=computerMapper;
		this.companyService=companyService;
		this.companyMapper=companyMapper;
		this.sessionVariables=sessionVariables;
		this.verificator=verificator;
    }
	
}

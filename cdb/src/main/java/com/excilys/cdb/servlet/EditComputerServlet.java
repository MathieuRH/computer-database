package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dao.ComputerDAO;
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
import com.excilys.cdb.verification.Verificator;

@WebServlet("/editComputer")
public class EditComputerServlet extends HttpServlet {
	
	private static final String VIEW = "/WEB-INF/views/editComputer.jsp";
	private static final String DASHBOARD_VIEW = "dashboard";
	private static final String WRONG_ENTRIES_MESSAGE = "Computer update failed";

	private static final String PAGE_REQUEST = "page_request";
	private static final int OFFSET_COMPANIES = 0;

	private static Logger logger = LoggerFactory.getLogger(ComputerDAO.class);
	private ComputerService computerService = ComputerService.getInstance();
	private CompanyService companyService = CompanyService.getInstance();
	private ComputerMapperServlet computerMapper = ComputerMapperServlet.getInstance();
	private CompanyMapperServlet companyMapper = CompanyMapperServlet.getInstance();
	private Verificator verificator = Verificator.getInstance();
	
	private static final long serialVersionUID = 1L;

    public EditComputerServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.handleRequest(request, response);
	}

	private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<CompanyDTOJsp> listCompanyDTO = new ArrayList<>();
		ComputerDTOJsp computerDTO = null;
		try {
			int id = Integer.parseInt(request.getParameter("computerId"));
			Computer computer = computerService.getOneComputer(id);
			computerDTO = computerMapper.toDTO(computer);
			try {
				int nbCompanies = companyService.getNumberCompanies();
				ArrayList<Company> listCompany;
				listCompany = companyService.getListCompanies(nbCompanies, OFFSET_COMPANIES);
				listCompanyDTO = companyMapper.listCompaniesToDTO(listCompany);
			} catch (ConnectionException | QueryException e) {
				logger.error(e.getMessage());
			}

			request.setAttribute("computerDTO", computerDTO);
			request.setAttribute( "listCompanyDTO", listCompanyDTO );
			
			this.getServletContext().getRequestDispatcher(VIEW).forward( request, response );
			
			
		} catch (NumberFormatException | ConnectionException | QueryException | ComputerNotFoundException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			//this.getServletContext().getRequestDispatcher(DASHBOARD_VIEW).forward( request, response );
			this.getServletContext().getRequestDispatcher(VIEW).forward( request, response );
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.handlePOSTRequest(request, response);
	}

	private void handlePOSTRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String name = request.getParameter("computerName");
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		String companyId = request.getParameter("companyId");
		String companyName = request.getParameter("companyName");
		
		ComputerDTOJsp computerDTO = new ComputerDTOJsp.ComputerDTOJspBuilder(id, name).introduced(introduced)
				.discontinued(discontinued).companyId(companyId).companyName(companyName).build();
		
		try {
			verificator.verifyComputer(computerDTO);
			Computer computer = computerMapper.toComputer(computerDTO);
			boolean success = false;
			try {
				computerService.updateOne(computer);
				success = true;
			} catch (ConnectionException | QueryException e) {
				logger.error(e.getMessage());
			}
			
			if (success) {
				//TODO : go to computer page
				request.setAttribute(PAGE_REQUEST, "last");
				request.getRequestDispatcher(DASHBOARD_VIEW).forward(request,response);
			} else {
				logger.error(WRONG_ENTRIES_MESSAGE);
				request.getRequestDispatcher(VIEW).forward(request,response);
			}
		} catch (InputException e) {
			logger.error(e.getMessage());
			request.getRequestDispatcher(VIEW).forward(request,response);
		}
	}
	
}

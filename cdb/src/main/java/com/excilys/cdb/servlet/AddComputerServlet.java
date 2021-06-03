package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

@WebServlet("/addComputer")
public class AddComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/views/addComputer.jsp";
	private static final String DASHBOARD_VIEW = "dashboard";

	private static final int OFFSET_COMPANIES = 0;
	private static final String PAGE_SESSION = "page_session";

	private static Logger logger = LoggerFactory.getLogger(ComputerDAO.class);
	private ComputerService computerService = ComputerService.getInstance();
	private CompanyService companyService = CompanyService.getInstance();
	private ComputerMapperServlet computerMapper = ComputerMapperServlet.getInstance();
	private CompanyMapperServlet companyMapper = CompanyMapperServlet.getInstance();
	private Verificator verificator = Verificator.getInstance();

	private HttpSession session;
	
    public AddComputerServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.session = request.getSession();
		this.handleRequest(request, response);
	}

	private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<CompanyDTOJsp> listCompanyDTO = new ArrayList<>();
		try {
			int nbCompanies = companyService.getNumberCompanies();
			ArrayList<Company> listCompany;
			listCompany = companyService.getListCompanies(nbCompanies, OFFSET_COMPANIES);
			listCompanyDTO = companyMapper.listCompaniesToDTO(listCompany);
		} catch (ConnectionException | QueryException e) {
			logger.error(e.getMessage());
		}

		request.setAttribute( "listCompanyDTO", listCompanyDTO );
		
		this.getServletContext().getRequestDispatcher(VIEW).forward( request, response );
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.session = request.getSession();
		this.handlePOSTRequest(request, response);
	}
	
	private void handlePOSTRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = "";
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
			computerService.createOne(computer);
			Page pagination = (Page)session.getAttribute(PAGE_SESSION);
			pagination.setPage(pagination.getNbPages());
			request.getRequestDispatcher(DASHBOARD_VIEW).forward(request,response);
		} catch (InputException | ConnectionException | QueryException e) {
			logger.error(e.getMessage());
			request.getRequestDispatcher(VIEW).forward(request,response);
		}
	}

}

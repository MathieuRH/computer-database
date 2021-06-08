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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;

import com.excilys.cdb.config.springConfig;
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
@Controller
public class AddComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/views/addComputer.jsp";
	private static final String DASHBOARD_VIEW = "dashboard";

	private static final int OFFSET_COMPANIES = 0;
	private static final String PAGE_SESSION = "page_session";

	private static Logger logger = LoggerFactory.getLogger(ComputerDAO.class);

	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private ComputerMapperServlet computerMapper;
	@Autowired
	private CompanyMapperServlet companyMapper;
	@Autowired
	private Verificator verificator;

	private HttpSession session;
	
    public AddComputerServlet() {
        super();
    }
    
    @Override
	public void init() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(springConfig.class);
		computerService = context.getBean(ComputerService.class);
		computerMapper = context.getBean(ComputerMapperServlet.class);
		companyService = context.getBean(CompanyService.class);
		companyMapper = context.getBean(CompanyMapperServlet.class);
		verificator = context.getBean(Verificator.class);
		context.close();	
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

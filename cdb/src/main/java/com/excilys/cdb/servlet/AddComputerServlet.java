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

import com.excilys.cdb.controller.CLIController;
import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.model.Company;

@WebServlet("/addComputer")
public class AddComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/views/addComputer.jsp";
	private static final String RETURN_VIEW = "dashboard";
	private static final String WRONG_ENTRIES_MESSAGE = "Computer creation failed";

	private static final String PAGE_REQUEST = "page_request";
	private static final int OFFSET_COMPANIES = 0;

	private static Logger logger = LoggerFactory.getLogger(ComputerDAO.class);
	
    public AddComputerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.handleRequest(request, response);
	}

	private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CLIController cli = CLIController.getInstance();
		int nbCompanies = cli.getNumberCompanies();
		ArrayList<CompanyDTO> listCompanyDTO = cli.getListCompanies(nbCompanies, OFFSET_COMPANIES);
		
		request.setAttribute( "listCompanyDTO", listCompanyDTO );
		
		this.getServletContext().getRequestDispatcher(VIEW).forward( request, response );
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.handlePOSTRequest(request, response);
	}
	
	private void handlePOSTRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = "";
		String name = request.getParameter("computerName");
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		String companyId = request.getParameter("companyId");
		String companyName = request.getParameter("companyName");
		System.out.println(name + "_" + introduced + "_" +  discontinued + companyId);
		
		ComputerDTO computerDTO = new ComputerDTO(id, name, introduced, discontinued, companyId, companyName);
		CLIController controller = CLIController.getInstance();
		boolean success = controller.createOne(computerDTO);
		
		if (success) {
			//TODO : go to last page
			request.setAttribute(PAGE_REQUEST, "last");
			
			request.getRequestDispatcher(RETURN_VIEW).forward(request,response);
		} else {
			logger.error(WRONG_ENTRIES_MESSAGE);
			request.getRequestDispatcher(VIEW).forward(request,response);
		}
	}

}

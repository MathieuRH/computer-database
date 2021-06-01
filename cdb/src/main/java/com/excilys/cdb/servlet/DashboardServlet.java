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
import com.excilys.cdb.dto.ComputerDTOJsp;
import com.excilys.cdb.exceptions.ComputerNotFoundException;
import com.excilys.cdb.exceptions.ConnectionException;
import com.excilys.cdb.exceptions.QueryException;
import com.excilys.cdb.mapper.ComputerMapperServlet;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.ComputerService;

/**
 * Servlet implementation class DashboardServlet
 */
@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/views/dashboard.jsp";
	
	private static final int DEFAULT_SIZE = 10;
	private static final int FIRST_PAGE = 1;
	private static final String PAGE_REQUEST = "page_request";
	private static final String PAGE_NB_COMPUTERS_DISPLAY = "page_nb_comp";
	private static final String QUERY_BY_NAME="computer_name_request";
	private static final String PAGE_SESSION = "page_session";

	private Page pagination;

	private static Logger logger = LoggerFactory.getLogger(ComputerDAO.class);
	private ComputerService computerService = ComputerService.getInstance();
	private ComputerMapperServlet computerMapper = ComputerMapperServlet.getInstance();
	
	private HttpSession session;
	
    public DashboardServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.handleRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.session = request.getSession();
		if(request.getParameter("selection") != null) {
			deleteComputers(request.getParameter("selection"));			
		}
		doGet(request, response);
		this.handleRequest(request, response);
	}

	private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.session = request.getSession();
		int nbComputers = 0;
		try {
			nbComputers = computerService.getNumberComputers();
		} catch (ConnectionException | QueryException e) {
			logger.error(e.getMessage());
		}
		if (session.getAttribute(PAGE_SESSION) == null) {
			pagination = new Page(FIRST_PAGE, DEFAULT_SIZE, nbComputers);
			session.setAttribute(PAGE_SESSION, pagination);
		}
		updatePageAttributes(request, response, nbComputers);	
		int offset = pagination.getOffset();
		int limit = pagination.getSize();
		String nameRequestQuery = request.getParameter(QUERY_BY_NAME);
		ArrayList<Computer> listComputers = new ArrayList<>();
		if (!"".equals(nameRequestQuery) && nameRequestQuery!= null){
			try {
				listComputers = computerService.getListByName(limit, offset, nameRequestQuery);
			} catch (ConnectionException | QueryException | ComputerNotFoundException e) {
				logger.error(e.getMessage());
			}
		} else {
			try {
				listComputers = computerService.getListComputers(limit, offset);
			} catch (ConnectionException | QueryException e) {
				logger.error(e.getMessage());
			}
		}
		ArrayList<ComputerDTOJsp> listComputersDTO = computerMapper.listToDTO(listComputers);

		request.setAttribute( "listComputersDTO", listComputersDTO );
		request.setAttribute("nbComputers", nbComputers);
		
		this.getServletContext().getRequestDispatcher(VIEW).forward( request, response );
	}
	
	private void updatePageAttributes(HttpServletRequest request, HttpServletResponse response, int nbComputers) {
		String page_request = request.getParameter(PAGE_REQUEST);
		String page_nb_comp_display = request.getParameter(PAGE_NB_COMPUTERS_DISPLAY);
		pagination = (Page)session.getAttribute(PAGE_SESSION);
		if (page_request != null) {
			if ("last".equals(page_request)) {
				pagination.setPage(pagination.getNbPages());
				//TODO : sort last page pb
				//System.out.println("check last");
			} else {
				pagination.setPage(Integer.parseInt(page_request));
			}
		}
		if (page_nb_comp_display != null) {
			pagination.setPage(1) ;
			pagination.setSize(Integer.parseInt(page_nb_comp_display), nbComputers);	
		}
	}
	
	private void deleteComputers(String selectionToDelete) {
		for (String idString:selectionToDelete.split(",")) {
			int idComputer = 0;
			try {
				idComputer = Integer.parseInt(idString);
				computerService.deleteOne(idComputer);
			} catch (NumberFormatException | ConnectionException | QueryException e) {
				logger.error(e.getMessage());
			}
		}
		
	}

}

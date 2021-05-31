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
import com.excilys.cdb.dto.ComputerDTOJsp;
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
	private static final String PAGE_NB_COMPUTERS = "page_nb_comp";
	private static final String CURRENT_PAGE = "page";
	private static final String PAGE_MAX="page_max";
	
	//TODO : Put page in session
	private Page pagination;
	int page = FIRST_PAGE;
	int size = DEFAULT_SIZE;

	private static Logger logger = LoggerFactory.getLogger(ComputerDAO.class);
	private ComputerService computerService = ComputerService.getInstance();
	private ComputerMapperServlet computerMapper = ComputerMapperServlet.getInstance();
	
    public DashboardServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.handleRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//TODO : deleteSelected(request);
		this.handleRequest(request, response);
	}
	
	private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int nbComputers = 0;
		try {
			nbComputers = computerService.getNumberComputers();
		} catch (ConnectionException | QueryException e) {
			logger.error(e.getMessage());
		}
		setPageAttributes(request, response, nbComputers);		
		int offset = pagination.getOffset();
		int limit = pagination.getSize();
		ArrayList<Computer> listComputers = new ArrayList<>();
		try {
			listComputers = computerService.getListComputers(limit, offset);
		} catch (ConnectionException | QueryException e) {
			logger.error(e.getMessage());
		}
		ArrayList<ComputerDTOJsp> listComputersDTO = computerMapper.listToDTO(listComputers);

		request.setAttribute( "listComputersDTO", listComputersDTO );
		request.setAttribute("nbComputers", nbComputers);
		
		this.getServletContext().getRequestDispatcher(VIEW).forward( request, response );
	}

	private void setPageAttributes(HttpServletRequest request, HttpServletResponse response, int nbComputers) {
		pagination = new Page(page, size, nbComputers);
		String page_request = request.getParameter(PAGE_REQUEST);
		String page_nb_comp = request.getParameter(PAGE_NB_COMPUTERS);
		if (page_request != null) {
			if ("last".equals(page_request)) {
				pagination.setPage(pagination.getNbPages());
				//TODO : sort last page pb
				//System.out.println("check last");
			} else {
				page = Integer.parseInt(page_request);
				pagination.setPage(page);
			}
		}
		if (page_nb_comp != null) {
			page = 1 ;
			size = Integer.parseInt(page_nb_comp);
			pagination.setSize(size, nbComputers);
			pagination.setPage(page);				
		}
		
		request.setAttribute(CURRENT_PAGE, page);
		request.setAttribute(PAGE_MAX, pagination.getNbPages());
	}

}

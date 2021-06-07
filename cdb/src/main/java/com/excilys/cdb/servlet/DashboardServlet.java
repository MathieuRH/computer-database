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
import com.excilys.cdb.dto.ComputerDTOJsp;
import com.excilys.cdb.exceptions.ComputerNotFoundException;
import com.excilys.cdb.exceptions.ConnectionException;
import com.excilys.cdb.exceptions.QueryException;
import com.excilys.cdb.mapper.ComputerMapperServlet;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.ComputerService;

@WebServlet("/dashboard")
@Controller
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/views/dashboard.jsp";
	
	private static final int DEFAULT_SIZE = 10;
	private static final int FIRST_PAGE = 1;
	private static final String PAGE_REQUEST = "page_request";
	private static final String PAGE_NB_COMPUTERS_DISPLAY = "page_nb_comp";
	private static final String QUERY_BY_NAME="computer_name_request";
	private static final String PAGE_SESSION = "page_session";
	private static final String REQUEST_SESSION = "request_session";
	private static final String REQUEST_NAME_SESSION = "request_name_session";

	private Page pagination;
	private String query;
	private String name_search;

	private static Logger logger = LoggerFactory.getLogger(ComputerDAO.class);
	@Autowired
	private ComputerService computerService;
	@Autowired
	private ComputerMapperServlet computerMapper;
	
	private HttpSession session;
	
    public DashboardServlet() {
        super();
    }
    
    @Override
	public void init() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(springConfig.class);
		computerService = context.getBean(ComputerService.class);
		computerMapper = context.getBean(ComputerMapperServlet.class);
		context.close();	
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.session = request.getSession();
		this.handleRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.session = request.getSession();
		if(request.getParameter("selection") != null) {
			deleteComputers(request.getParameter("selection"));			
		}
		this.handleRequest(request, response);
	}

	private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int nbComputers = 0;
		this.getRequest(request, response);

		ArrayList<Computer> listComputers = new ArrayList<>();
		try {
			if ("getByName".equals(query)) {
				nbComputers = computerService.getNumberComputersByName(name_search);
				updatePageAttributes(request, response, nbComputers);	
				int offset = pagination.getOffset();
				int limit = pagination.getSize();
				listComputers = computerService.getListByName(limit, offset, name_search);
			} else {
				nbComputers = computerService.getNumberComputers();
				updatePageAttributes(request, response, nbComputers);	
				int offset = pagination.getOffset();
				int limit = pagination.getSize();
				listComputers = computerService.getListComputers(limit, offset, query);
			}
		} catch (ConnectionException | QueryException | ComputerNotFoundException e) {
			logger.error(e.getMessage());
		}
		ArrayList<ComputerDTOJsp> listComputersDTO = computerMapper.listToDTO(listComputers);

		request.setAttribute( "listComputersDTO", listComputersDTO );
		request.setAttribute("nbComputers", nbComputers);
		
		this.getServletContext().getRequestDispatcher(VIEW).forward( request, response );
	}

	
	private void getRequest(HttpServletRequest request, HttpServletResponse response) {
		if (session.getAttribute(REQUEST_SESSION) == null) {
			session.setAttribute(REQUEST_SESSION, "orderById");
		}
		String queryRequest = request.getParameter(REQUEST_SESSION);
		if (queryRequest != null) {
			session.setAttribute(REQUEST_SESSION, queryRequest);
		}
		String nameRequestQuery = request.getParameter(QUERY_BY_NAME);
		
		if (nameRequestQuery != null && !"".equals(nameRequestQuery)) {
			name_search = request.getParameter(QUERY_BY_NAME);
			session.setAttribute(REQUEST_SESSION, "getByName");
			session.setAttribute(REQUEST_NAME_SESSION, name_search);
		}		
		
		query = (String) session.getAttribute(REQUEST_SESSION);
		name_search = (String) session.getAttribute(REQUEST_NAME_SESSION);
	}

	
	private void updatePageAttributes(HttpServletRequest request, HttpServletResponse response, int nbComputers) {
		if (session.getAttribute(PAGE_SESSION) == null) {
			pagination = new Page(FIRST_PAGE, DEFAULT_SIZE, nbComputers);
			session.setAttribute(PAGE_SESSION, pagination);
		}
		String page_request = request.getParameter(PAGE_REQUEST);
		String page_nb_comp_display = request.getParameter(PAGE_NB_COMPUTERS_DISPLAY);
		pagination = (Page)session.getAttribute(PAGE_SESSION);
		if (page_request != null) {
			pagination.setPage(Integer.parseInt(page_request));
		}
		if (page_nb_comp_display != null) {
			pagination.setPage(1) ;
			pagination.setSize(Integer.parseInt(page_nb_comp_display), nbComputers);	
		}
		pagination.refreshNbPages(nbComputers);
	}
	

	private void deleteComputers(String selectionToDelete) {
		//TODO : Stream
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

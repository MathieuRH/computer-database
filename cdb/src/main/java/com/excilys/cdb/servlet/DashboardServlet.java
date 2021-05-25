package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.controller.CLIController;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;

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
	private static final String CURRENT_PAGE = "page";
	private static final String PAGE_MAX="page_max";
	
	
	private Page pagination;
       
    public DashboardServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.handleRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//TODO : deleteSelected(request);
		this.handleRequest(request, response);
	}
	
	private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CLIController cli = CLIController.getInstance();
		int nbComputers = cli.getNumberComputers();
		setPageAttributes(request, response, nbComputers);		
		int offset = pagination.getOffset();
		int limit = pagination.getSize();
		ArrayList<Computer> listComputers = cli.getListComputers(limit, offset);

		request.setAttribute( "listComputers", listComputers );
		request.setAttribute("nbComputers", nbComputers);
		
		this.getServletContext().getRequestDispatcher(VIEW).forward( request, response );
	}

	private void setPageAttributes(HttpServletRequest request, HttpServletResponse response, int nbComputers) {
		int page = FIRST_PAGE;
		pagination = new Page(page, DEFAULT_SIZE, nbComputers);
		String page_request = request.getParameter(PAGE_REQUEST);
//		System.out.println("page request : " + page_request);
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
		request.setAttribute(CURRENT_PAGE, page);
		request.setAttribute(PAGE_MAX, pagination.getNbPages());
	}

}

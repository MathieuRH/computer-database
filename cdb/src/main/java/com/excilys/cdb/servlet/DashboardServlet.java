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
	
	private static final int DEFAULT_SIZE = 10;
	private static final int FIRST_PAGE = 1;
	
	private Page pagination = new Page(FIRST_PAGE, DEFAULT_SIZE);
       
    public DashboardServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.handleRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int offset = pagination.getOffset();
		int limit = pagination.getSize();
		CLIController cli = CLIController.getInstance();
		ArrayList<Computer> listComputers = cli.getListComputers(limit, offset);
		int nbComputers = cli.getNumberComputers();

		request.setAttribute( "listComputers", listComputers );
		request.setAttribute("nbComputers", nbComputers);
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/dashboard.jsp" ).forward( request, response );
	}

}

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


@SuppressWarnings("serial")
@WebServlet("/index")
public class MyServlet extends HttpServlet {

	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		
		Page pagination = new Page();
		int offset = pagination.getOffset();
		int limit = pagination.getSize();
		CLIController cli = CLIController.getInstance();
		ArrayList<Computer> listComputers = cli.getListComputers(limit, offset);
		int nbComputers = cli.getNumberComputers();

		request.setAttribute( "listComputers", listComputers );
		request.setAttribute("nbComputers", nbComputers);
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/dashboard.jsp" ).forward( request, response );
	}
	
	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{

		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/addComputer.jsp" ).forward( request, response );
	}
	
}

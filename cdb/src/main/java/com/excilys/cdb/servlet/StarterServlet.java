package com.excilys.cdb.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
//import org.springframework.web.context.support.SpringBeanAutowiringSupport;


@WebServlet("/index")
@Controller
public class StarterServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

    public StarterServlet() {
        super();
    }
	
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		
		RequestDispatcher rd = request.getRequestDispatcher("dashboard");
		rd.forward(request,response);
	}
	
	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{

	}

//	@Override
//	public void init(ServletConfig config) throws ServletException {
//		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
//		super.init(config);
//	}
}

package com.excilys.cdb.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.cdb.config.SpringConfig;
import com.excilys.cdb.controller.session.SessionVariables;
import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.dto.ComputerDTOJsp;
import com.excilys.cdb.exceptions.ConnectionException;
import com.excilys.cdb.exceptions.QueryException;
import com.excilys.cdb.mapper.ComputerMapperServlet;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.ComputerService;

@Controller
@Scope(value="session")
public class DashboardController {
	
	private static final String DASBOARD_VIEW = "dashboard";
	
	private static final int DEFAULT_SIZE = 10;
	private static final int FIRST_PAGE = 1;

	private SessionVariables sessionVariables;

	private static Logger logger = LoggerFactory.getLogger(ComputerDAO.class);
	private ComputerService computerService;
	private ComputerMapperServlet computerMapper;
	
    
	public DashboardController(ComputerService computerService, ComputerMapperServlet computerMapper, SessionVariables sessionVariables) {
		this.computerService = computerService;
		this.computerMapper=computerMapper;
		this.sessionVariables=sessionVariables;
	}
	
	@GetMapping({"/", "/dashboard"})
	public ModelAndView getDashboard(@RequestParam(required = false) String request_session, 
			@RequestParam(required = false) String computer_name_request, 
			@RequestParam(required = false) String page_request,
			@RequestParam(required = false) String page_nb_comp){
		
		ModelAndView returnView = new ModelAndView(DASBOARD_VIEW);
		loadAttibutes(returnView,request_session, computer_name_request, page_request, page_nb_comp);
		return returnView;
	}
	
	@PostMapping("/dashboard")
	protected ModelAndView doPost(@RequestParam(required = false) String request_session, 
			@RequestParam(required = false) String computer_name_request,  
			@RequestParam(required = false) String page_request, 
			@RequestParam(required = false) String page_nb_comp, 
			@RequestParam(required = false) String selection){
		
		if(selection != null) {
			deleteComputers(selection);			
		}
		ModelAndView returnView = new ModelAndView(DASBOARD_VIEW);
		loadAttibutes(returnView, request_session,computer_name_request, page_request, page_nb_comp);
		return returnView;
	}

	private void loadAttibutes(ModelAndView view, String request_session, String computer_name_request, String page_request, String page_nb_comp){
		int nbComputers = 0;
		this.getQuery(request_session, computer_name_request);

		ArrayList<Computer> listComputers = new ArrayList<>();
		try {
			nbComputers = computerService.getNumberComputersByName(sessionVariables.getName_search());
			updatePageAttributes(nbComputers, page_request, page_nb_comp, request_session);
			listComputers = computerService.getListComputers(sessionVariables.getPagination(), sessionVariables.getQuery(), sessionVariables.getName_search());
		} catch (ConnectionException | QueryException e) {
			logger.error(e.getMessage());
		}
		ArrayList<ComputerDTOJsp> listComputersDTO = computerMapper.listToDTO(listComputers);

		view.addObject( "listComputersDTO", listComputersDTO );
		view.addObject("nbComputers", nbComputers);
		view.addObject("page_session", sessionVariables.getPagination());
	}
	
	private void getQuery(String request_session, String computer_name_request) {
		if (sessionVariables.getQuery() == null) {
			sessionVariables.setQuery("orderById");
			sessionVariables.setPrevious_query("orderById");
		}
		if (request_session != null) {
			sessionVariables.setQuery(request_session);
			if ("orderById".equals(request_session)) {
				sessionVariables.setName_search("");
			}
		}
		if (computer_name_request != null && !"".equals(computer_name_request)) {
			sessionVariables.setQuery("getByName");
			sessionVariables.setName_search(computer_name_request);
		}
	}

	
	private void updatePageAttributes(int nbComputers, String page_request, String page_nb_comp, String request_session) {
		if (sessionVariables.getPagination() == null) {
			sessionVariables.setPagination(new Page(FIRST_PAGE, DEFAULT_SIZE, nbComputers));
		}
		
		if (page_request != null) {
			sessionVariables.getPagination().setPage(Integer.parseInt(page_request));
		}
		if (page_nb_comp != null) {
			sessionVariables.getPagination().setPage(1);
			sessionVariables.getPagination().setSize(Integer.parseInt(page_nb_comp), nbComputers);	
		}
		sessionVariables.getPagination().refreshNbPages(nbComputers);
		
		if (request_session!=null && request_session.equals(sessionVariables.getPrevious_query())) {
			sessionVariables.getPagination().inverseSortOrder();
		} else {
			sessionVariables.getPagination().setSortOrder("ASC");
		}
		sessionVariables.setPrevious_query(request_session);
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
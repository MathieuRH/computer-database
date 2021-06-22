package com.excilys.cdb.controller;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.cdb.dao.UserDAO;
import com.excilys.cdb.dto.UserDTOSQL;
import com.excilys.cdb.exceptions.QueryException;
import com.excilys.cdb.mapper.UserMapperSQL;
import com.excilys.cdb.model.User;

@Controller
@RequestMapping("/adminPage")
public class AdminPageController {
	
	private static final String DASBOARD_VIEW = "dashboard";
	private static final String ADMIN_VIEW = "adminPage";
	
	private static Logger logger = LoggerFactory.getLogger(DashboardController.class);
	private UserDAO userService;
	private UserMapperSQL userMapper;
	
    
	public AdminPageController(UserDAO userService, UserMapperSQL userMapper) {
		this.userService = userService;
		this.userMapper=userMapper;
	}
	
	@GetMapping
	public ModelAndView getAdminPage(){
		ModelAndView returnView = new ModelAndView(ADMIN_VIEW);
		loadAttibutes(returnView);
		return returnView;
	}
	
	@PostMapping
	protected ModelAndView doPost(@RequestParam(required = false) String selection){
		if(selection != null) {
			deleteUsers(selection);			
		}
		ModelAndView returnView = new ModelAndView(DASBOARD_VIEW);
		loadAttibutes(returnView);
		return returnView;
	}

	private void loadAttibutes(ModelAndView view){
		int nbUsers = 0;

		ArrayList<User> listUsers = new ArrayList<>();
		try {
			nbUsers = userService.getNumberUsers();
			listUsers = userService.getListUsers();
		} catch (QueryException e) {
			logger.error(e.getMessage());
		}
		ArrayList<UserDTOSQL> listUsersDTO = userMapper.listToDTO(listUsers);

		view.addObject( "listUsersDTO", listUsersDTO );
		view.addObject("nbUsers", nbUsers);
	}
	
	
	private void deleteUsers(String selectionToDelete) {
		for (String idString:selectionToDelete.split(",")) {
			int idUser = 0;
			try {
				idUser = Integer.parseInt(idString);
				userService.delete(idUser);
			} catch (NumberFormatException | QueryException e) {
				logger.error(e.getMessage());
			}
		}
		
	}
}

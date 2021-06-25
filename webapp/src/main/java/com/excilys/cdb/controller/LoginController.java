package com.excilys.cdb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/loginPage")
public class LoginController {

	private static final String LOGIN_PAGE = "loginPage";
	
	@GetMapping
	public ModelAndView getLogin () {
		ModelAndView view = new ModelAndView(LOGIN_PAGE);
		return view;
	}
	
//	@PostMapping
//	public String authenticate () {
//		if (authenticated)
//		return ":dashboard";
//	}
}

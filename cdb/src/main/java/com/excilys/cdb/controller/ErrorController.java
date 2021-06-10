package com.excilys.cdb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {

	private static final String DENIED = "403";
	private static final String NOT_FOUND = "404";
	private static final String ERROR = "500";
	
	@GetMapping("403")
	protected ModelAndView redirect403() {
    	return new ModelAndView(DENIED);
	}
	
	@GetMapping("404")
	protected ModelAndView redirect404() {
    	return new ModelAndView(NOT_FOUND);
	}
	
	@GetMapping("500")
	protected ModelAndView redirect500() {
    	return new ModelAndView(ERROR);
	}
}

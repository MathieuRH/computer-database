package com.excilys.cdb.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {

	private static final String ERROR = "error";
	private static final String HANDLED_ERROR = "handled_error";
	
	@RequestMapping("/error")
	public ModelAndView redirectErrorPage (HttpServletRequest request) {
		String strErrorCode = request.getAttribute("javax.servlet.error.status_code").toString();
		String strErrorMessage = request.getAttribute("javax.servlet.error.message").toString();
		
		ModelAndView view;
		if ("403".equals(strErrorCode)) {
			view = new ModelAndView(HANDLED_ERROR);
			view.addObject("error_number", strErrorCode);
		}
		else {
			view = new ModelAndView(ERROR);
			view.addObject("error_number", strErrorCode);
			view.addObject("error_message", strErrorMessage);
		}
		return view;
	}
}

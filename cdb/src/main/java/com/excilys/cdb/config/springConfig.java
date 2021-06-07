package com.excilys.cdb.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
//import org.springframework.web.WebApplicationInitializer;
//import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;


@Configuration
@ComponentScan(basePackages = {"com.excilys.cdb.servlet", 
		"com.excilys.cdb.service", 
		"com.excilys.cdb.dao", 
		"com.excilys.cdb.mapper", 
		"com.excilys.cdb.verification", 
		"com.excilys.cdb.ui", 
		"com.excilys.cdb.controller"})
public class springConfig { //implements WebApplicationInitializer {

//	@Override
//	public void onStartup(ServletContext container) throws ServletException {
//		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
//		context.register(springConfig.class);
//		context.setServletContext(container);
//		context.close();
//	}
	

	
}

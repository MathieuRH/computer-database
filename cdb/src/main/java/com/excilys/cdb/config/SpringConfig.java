package com.excilys.cdb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.context.AbstractContextLoaderInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

@Configuration
@ComponentScan(basePackages = {"com.excilys.cdb.servlet", 
		"com.excilys.cdb.service", 
		"com.excilys.cdb.dao", 
		"com.excilys.cdb.mapper", 
		"com.excilys.cdb.verification", 
		"com.excilys.cdb.ui", 
		"com.excilys.cdb.controller"})
public class SpringConfig extends AbstractContextLoaderInitializer {
	
	@Override
	protected WebApplicationContext createRootApplicationContext() {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(SpringConfig.class);
		return context;
	}
	
}

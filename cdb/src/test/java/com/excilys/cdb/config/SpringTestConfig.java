package com.excilys.cdb.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
		"com.excilys.cdb.service", 
		"com.excilys.cdb.dao", 
		"com.excilys.cdb.mapper", 
		"com.excilys.cdb.verification", 
		"com.excilys.cdb.ui", 
		"com.excilys.cdb.controller", 
		"com.excilys.cdb.controller.session"})
public class SpringTestConfig {
	
}
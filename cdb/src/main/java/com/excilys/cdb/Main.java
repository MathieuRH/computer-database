package com.excilys.cdb;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.cdb.config.SpringConfig;
import com.excilys.cdb.ui.CLI;

public class Main {

	private static CLI dialogue;
	
	public static void main(String[] args) {
		
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
		dialogue = context.getBean(CLI.class);
		dialogue.start();
		context.close();
	}
}

package com.excilys.cdb;

import com.excilys.cdb.ui.CLI;

public class Main {
	
	public static void main(String[] args) {
		
		int a = 5, b=10;
		int res = a^b;
		System.out.println(res);
		
		CLI dialogue = CLI.getInstance();
		dialogue.start();
		
	}
}

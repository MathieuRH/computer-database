package com.excilys.cdb.exceptions;

public class ComputerNotFoundException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "Computer not found";
	
	public String getMessage() {
		return MESSAGE;
	}
}

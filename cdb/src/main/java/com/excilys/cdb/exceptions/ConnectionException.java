package com.excilys.cdb.exceptions;

public class ConnectionException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "Failed connection to database";
	
	public String getMessage() {
		return MESSAGE;
	}
}

package com.excilys.cdb.exceptions;

public class QueryException extends Exception {

	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "Query wasn't successfull";
	
	public String getMessage() {
		return MESSAGE;
	}
	
}

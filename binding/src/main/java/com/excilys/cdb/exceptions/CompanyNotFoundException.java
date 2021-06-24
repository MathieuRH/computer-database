package com.excilys.cdb.exceptions;

public class CompanyNotFoundException extends Exception{
	
	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "Company not found";

	public CompanyNotFoundException() {
		super(MESSAGE);
	}
	
	public CompanyNotFoundException(String message) {
		super(message);
	}

	public CompanyNotFoundException(int id) {
		super(MESSAGE + " : " + Integer.toString(id));
	}
}

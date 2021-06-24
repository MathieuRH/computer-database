package com.excilys.cdb.exceptions;

public class ComputerNotFoundException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "Computer not found";

	public ComputerNotFoundException() {
		super(MESSAGE);
	}
	
	public ComputerNotFoundException(String message) {
		super(message);
	}

	public ComputerNotFoundException(int id) {
		super(MESSAGE + " : " + Integer.toString(id));
	}
}

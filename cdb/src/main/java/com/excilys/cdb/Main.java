package com.excilys.cdb;

import java.util.ArrayList;

import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.exceptions.ConnectionException;
import com.excilys.cdb.exceptions.QueryException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.ui.CLI;

public class Main {
	
	public static void main(String[] args) {
		
		ComputerDAO computerDAO = ComputerDAO.getInstance();
		ArrayList<Computer> result = new ArrayList<>();
		try {
			result = computerDAO.getListComputers(50, 0, "orderById");
		} catch (ConnectionException | QueryException e) {
			System.out.println("cheh");
		}
		System.out.println(result.size());
		
		CLI dialogue = CLI.getInstance();
		dialogue.start();
		
	}
}

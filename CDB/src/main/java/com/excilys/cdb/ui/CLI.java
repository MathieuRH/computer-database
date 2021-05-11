package com.excilys.cdb.ui;

import java.time.LocalDate;
import java.util.Scanner;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.dao.ComputerDAO;

public class CLI {
	
	Scanner sc = new Scanner(System.in);
	private static final String LIST_ACTIONS = System.lineSeparator() + "List of available actions : " + System.lineSeparator() +
												"0 :  Exit program" + System.lineSeparator() +
												"1 :  Print the list of all computers" + System.lineSeparator() +
												"2 :  Print the list of all companies" + System.lineSeparator() +
												"3 :  Show the details of one computer" + System.lineSeparator() +
												"4 :  Create a computer" + System.lineSeparator() +
												"5 :  Update a computer" + System.lineSeparator() +
												"6 :  Delete a computer" ;
	private static final String LIST_PARAMETERS_MODIFICATION = System.lineSeparator() + "List of available actions : " + System.lineSeparator() +
			"1 :  Change name" + System.lineSeparator() +
			"2 :  Change introduction date" + System.lineSeparator() +
			"3 :  Change discontinuation date" + System.lineSeparator() +
			"4 :  Change company" ;
	private ComputerDAO computerDAO;
	private CompanyDAO companyDAO;
	
	public CLI() {
	}
	
	public void start() {
		System.out.println("Access and modifications in computer database.");
		boolean cond = true;
		while (cond) {
			int action = -1;
			while (action < 0 || action > 6) {
				System.out.println(LIST_ACTIONS);
				
				System.out.println("Please enter an action :");
				try {
					action = Integer.parseInt(sc.nextLine());
				} catch (NumberFormatException e){}
				switch (action) {
					case 0:
						cond = false;
						break;
					case 1:
						getListComputers();
						break;
					case 2: 
						getListCompanies();
						break;
					case 3:
						getOneComputer();
						break;
					case 4:
						createOneComputer();
						break;
					case 5:
						updateOneComputer();
						break;
					case 6:
						deleteOneComputer();
						break;
					default :System.out.println("Please enter a correct action...");
				}
				
			}
		}
		System.out.println("Session ended.");
		sc.close();
	}

	private void getListComputers() {
		computerDAO = new ComputerDAO();
		computerDAO.getListComputers();
	}
	
	private void getOneComputer() {
		computerDAO = new ComputerDAO();
		System.out.println("Please enter the computer id :");
		int computer_id = Integer.parseInt(sc.nextLine());
		computerDAO.getOneComputer(computer_id);
	}
	
	private void getListCompanies() {
		companyDAO = new CompanyDAO();
		companyDAO.getListCompanies();
	}
	
	private void createOneComputer() {
		LocalDate introduced = null;
		LocalDate discontinued = null;
		int company_id = 0;
		String immediate_answer = null;
		computerDAO = new ComputerDAO();
		System.out.println("Please enter the computer name :");
		String name = sc.nextLine();
		try {
			System.out.println("Add an introduction date ? (y/n)");
			immediate_answer = sc.nextLine();
			if (immediate_answer.equals("y")){
				System.out.println("Year ? ");
				int y = Integer.parseInt(sc.nextLine());
				System.out.println("Month ? ");
				int m = Integer.parseInt(sc.nextLine());
				System.out.println("Day ? ");
				int d = Integer.parseInt(sc.nextLine());
				introduced = LocalDate.of(y, m, d);
			}
		} catch (Exception e){}
		System.out.println("Add a discontinuation date ? (y/n)");
		try {
			immediate_answer = sc.nextLine();
			if (immediate_answer.equals("y")){
				System.out.println("Year ? ");
				int y = Integer.parseInt(sc.nextLine());
				System.out.println("Month ? ");
				int m = Integer.parseInt(sc.nextLine());
				System.out.println("Day ? ");
				int d = Integer.parseInt(sc.nextLine());
				discontinued = LocalDate.of(y, m, d);
			}
		} catch (Exception e){}
		System.out.println("Add a company id ? (y/n)");
		try {
			immediate_answer = sc.nextLine();
			if (immediate_answer.equals("y")){
				System.out.println("Please enter the id :");
				company_id = Integer.parseInt(sc.nextLine());
			}
		} catch (NumberFormatException e){}
		computerDAO.createOne(name, introduced, discontinued, company_id);
	}
	
	private void updateOneComputer() {
		computerDAO = new ComputerDAO();
		int computer_id = 0;
		int field = 0;
		Object value = null;
		System.out.println("Please enter the computer id :");
		try {
			computer_id = Integer.parseInt(sc.nextLine());
		} catch (NumberFormatException e){}
		System.out.println(LIST_PARAMETERS_MODIFICATION);
		System.out.println("What field is to be modified ? ");
		try {
			field = Integer.parseInt(sc.nextLine());
			switch (field){
			case 1:
				System.out.println("New name ? ");
				value = sc.nextLine();
				break;
			case 2:
			case 3:
				System.out.println("Year ? ");
				int y = Integer.parseInt(sc.nextLine());
				System.out.println("Month ? ");
				int m = Integer.parseInt(sc.nextLine());
				System.out.println("Day ? ");
				int d = Integer.parseInt(sc.nextLine());
				value = LocalDate.of(y, m, d);
				break;
			case 4:
				System.out.println("New company ? ");
				value = Integer.parseInt(sc.nextLine());
				break;
			}
		} catch (NumberFormatException e){}
		computerDAO.updateOne(computer_id, field, value);
	}
	
	private void deleteOneComputer() {
		computerDAO = new ComputerDAO();
		int computer_id = 0;
		System.out.println("Please enter the computer id :");
		try {
			computer_id = Integer.parseInt(sc.nextLine());
		} catch (NumberFormatException e){}
		computerDAO.deleteOne(computer_id);
	}
	
}

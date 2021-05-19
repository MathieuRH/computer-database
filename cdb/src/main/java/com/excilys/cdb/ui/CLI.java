package com.excilys.cdb.ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.controller.CLIController;

/**
 * Interaction class for the user
 * @author Mathieu_RH
 *
 */
public class CLI {
	
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
	private static final String LIST_PAGE_ACTIONS = "p : previous / n : next / page_number ?" + System.lineSeparator() +
													"0 :  Exit display";

	private Scanner sc = new Scanner(System.in);
	private static CLI instance;
	private CLIController cliController;
	private Page pagination;

	private static Logger logger = LoggerFactory.getLogger(CLI.class);
	
	private CLI() {
		cliController = CLIController.getInstance();
	}
	
	public static CLI getInstance() {
		if (instance==null) {
			instance = new CLI();
		}
		return instance;
	}
	
	public static void writeMessage(String message) {
		System.out.println(message);
	}

	public void start() {
		System.out.println("Access and modifications in computer database.");
		boolean goOn = true;
		while (goOn) {
			int action = -1 ;
			while (action < 0 || action > 6) {
				System.out.println(LIST_ACTIONS);
				
				System.out.println("Please enter an action :");
				try {
					action = Integer.parseInt(sc.nextLine());
				} catch (NumberFormatException e){}
				try {
					switch (DisplayValues.fromPropertyName(action)) {
						case EXIT:
							goOn = false;
							break;
						case GET_LIST_COMPUTERS:
							displayListComputers();
							break;
						case GET_LIST_COMPANIES: 
							displayListCompanies();
							break;
						case GET_ONE_COMPUTER:
							getOneComputer();
							break;
						case CREATE_COMPUTER:
							createOneComputer();
							break;
						case UPDATE_COMPUTER:
							updateOneComputer();
							break;
						case DELETE_COMPUTER:
							deleteOneComputer();
							break;
						default :System.out.println("Please enter a correct action...");
					}
				} catch (Exception e) {
					logger.error("{} in {}", e.getMessage(), e.getStackTrace());
					System.out.println("Choice out of scope");
				}
			}
		}
		System.out.println("Session ended.");
		sc.close();
	}



	private void displayListComputers() {
		int nbComputers = cliController.getNumberComputers();
		pagination = new Page(nbComputers);
		ArrayList<Computer> listDisplayComputers;
		int limit = pagination.getSize();
		int offset ;
		
		boolean keepDisplaying = true;
		String immediate_answer;
		while (keepDisplaying) {
			offset = pagination.getOffset();
			limit = (pagination.getPage() == pagination.getNbPages()) ? nbComputers % pagination.getSize() : pagination.getSize();
			listDisplayComputers = cliController.getListComputers(limit, offset);
			displayListComputers(listDisplayComputers);
			System.out.println("Page " + pagination.getPage() + "/" + pagination.getNbPages() + " | " + LIST_PAGE_ACTIONS);
			immediate_answer = sc.nextLine();
			if ("n".equals(immediate_answer)){
				pagination.nextPage();
			}
			else if ("p".equals(immediate_answer)) {
				pagination.previousPage();
			}
			else {
				try {
					int choice = Integer.parseInt(immediate_answer);
					if (choice == 0){
						keepDisplaying = false;
					}
					else if (choice > 0 && choice <= pagination.getNbPages()){
						pagination.setPage(choice);
					}
				} catch (NumberFormatException e){}
			}
		}
	}
	
	private void displayListComputers(ArrayList<Computer> listDisplayComputers) {
		for (Computer computer : listDisplayComputers) {
			System.out.println(computer);
		} 
	}
	
	private void displayListCompanies() {
		int nbCompanies = cliController.getNumberCompanies();
		pagination = new Page(nbCompanies);
		ArrayList<Company> listDisplayCompanies;
		int limit = pagination.getSize();
		int offset ;
		
		boolean keepDisplaying = true;
		String immediate_answer;
		while (keepDisplaying) {
			offset = pagination.getOffset();
			limit = (pagination.getPage() == pagination.getNbPages()) ? nbCompanies % pagination.getSize() : pagination.getSize();
			listDisplayCompanies = cliController.getListCompanies(limit, offset);
			displayListCompanies(listDisplayCompanies);
			System.out.println("Page " + pagination.getPage() + "/" + pagination.getNbPages() + " | " + LIST_PAGE_ACTIONS);
			immediate_answer = sc.nextLine();
			if ("n".equals(immediate_answer)){
				pagination.nextPage();
			}
			else if ("p".equals(immediate_answer)) {
				pagination.previousPage();
			}
			else {
				try {
					int choice = Integer.parseInt(immediate_answer);
					if (choice == 0){
						keepDisplaying = false;
					}
					else if (choice > 0 && choice <= pagination.getNbPages()){
						pagination.setPage(choice);
					}
				} catch (NumberFormatException e){}
			}
		}
	}

	private void displayListCompanies(ArrayList<Company> listDisplayCompanies) {
		for (Company company : listDisplayCompanies) {
			System.out.println(company);
		} 
	}
	
	
	private void getOneComputer() {
		System.out.println("Please enter the computer id :");
		int computer_id = Integer.parseInt(sc.nextLine());
		Computer computer = cliController.getOneComputer(computer_id);
		if (computer != null) {System.out.println(computer);}
	}
	

	private void createOneComputer() {
		LocalDate introduced = null;
		LocalDate discontinued = null;
		int company_id = 0;
		String immediate_answer = null;
		System.out.println("Please enter the computer name :");
		String name = sc.nextLine();
		try {
			System.out.println("Add an introduction date ? (y/n)");
			immediate_answer = sc.nextLine();
			if ("y".equals(immediate_answer)){
				introduced = askDate();
			}
		} catch (Exception e){}
		System.out.println("Add a discontinuation date ? (y/n)");
		try {
			immediate_answer = sc.nextLine();
			if ("y".equals(immediate_answer)){
				discontinued = askDate();
			}
		} catch (Exception e){}
		System.out.println("Add a company id ? (y/n)");
		try {
			immediate_answer = sc.nextLine();
			if ("y".equals(immediate_answer)){
				System.out.println("Please enter the id :");
				company_id = Integer.parseInt(sc.nextLine());
			}
		} catch (NumberFormatException e){}
		if (!cliController.createOne(name, introduced, discontinued, company_id)) {
			System.out.println("Sorry, can't process wrong dates");
		}
	}
	
	private LocalDate askDate() {
		System.out.println("Year ? ");
		int y = Integer.parseInt(sc.nextLine());
		System.out.println("Month ? ");
		int m = Integer.parseInt(sc.nextLine());
		System.out.println("Day ? ");
		int d = Integer.parseInt(sc.nextLine());
		return LocalDate.of(y, m, d);
	}

	private void updateOneComputer() {
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
			try {
				switch (UpdateChoice.fromPropertyName(field)) {
					case CHANGE_NAME:
						System.out.println("New name ? ");
						value = sc.nextLine();
						break;
					case CHANGE_INTRODUCED:
					case CHANGE_DISCONTINUED:
						value = askDate();
						break;
					case CHANGE_COMPANY:
						System.out.println("New company ? ");
						value = Integer.parseInt(sc.nextLine());
						break;
				}
			} catch (Exception e) {
				logger.error("{} in {}", e.toString(), e.getStackTrace());
				System.out.println("Choice out of scope");
			}
		} catch (NumberFormatException e){}
		if (!cliController.updateOne(computer_id, field, value)) {
			System.out.println("Sorry, can't process wrong dates");
		}
	}

	private void deleteOneComputer() {
		int computer_id = 0;
		System.out.println("Please enter the computer id :");
		try {
			computer_id = Integer.parseInt(sc.nextLine());
		} catch (NumberFormatException e){}
		cliController.deleteOne(computer_id);
	}
	
}

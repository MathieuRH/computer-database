package com.excilys.cdb.ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.model.Company;

/**
 * Interaction class for the user
 * @author Mathieu_RH
 *
 */
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
	private static final String LIST_PAGE_ACTIONS = "p : previous / n : next / page_number ?" + System.lineSeparator() +
													"0 :  Exit computer display";
	private static final int COND_EXIT = 0;
	private static final int COND_ONE = 1;
	private static final int COND_TWO = 2;
	private static final int COND_THREE = 3;
	private static final int COND_FOUR = 4;
	private static final int COND_FIVE = 5;
	private static final int COND_SIX = 6;
	private ComputerService computerService;
	private CompanyService companyService;
	private Page pagination;
	
	public CLI() {
	}
	
	/*
	 * Choice possibilities for database operations
	 */
	public void start() {
		System.out.println("Access and modifications in computer database.");
		boolean goOn = true;
		while (goOn) {
			int action = -1;
			while (action < 0 || action > 6) {
				System.out.println(LIST_ACTIONS);
				
				System.out.println("Please enter an action :");
				try {
					action = Integer.parseInt(sc.nextLine());
				} catch (NumberFormatException e){}
				switch (action) {
					case COND_EXIT:
						goOn = false;
						break;
					case COND_ONE:
						displayListComputers();
						break;
					case COND_TWO: 
						displayListCompanies();
						break;
					case COND_THREE:
						getOneComputer();
						break;
					case COND_FOUR:
						createOneComputer();
						break;
					case COND_FIVE:
						updateOneComputer();
						break;
					case COND_SIX:
						deleteOneComputer();
						break;
					default :System.out.println("Please enter a correct action...");
				}
			}
		}
		System.out.println("Session ended.");
		sc.close();
	}



	private void displayListComputers() {
		computerService = new ComputerService();
		ArrayList <Computer> listAllComputers = computerService.getListComputers();
		pagination = new Page(listAllComputers.size());
		ArrayList<Computer> listDisplayComputers;
		boolean keepDisplaying = true;
		String immediate_answer;
		while (keepDisplaying) {
			listDisplayComputers = pagination.getDisplayListComputer(listAllComputers);
			displayListComputers(listDisplayComputers);
			System.out.println("Page " + pagination.getPage() + "/" + pagination.getNbPages() + " | " + LIST_PAGE_ACTIONS);
			immediate_answer = sc.nextLine();
			if (immediate_answer.equals("n")){
				pagination.nextPage();
			}
			else if (immediate_answer.equals("p")) {
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
		companyService = new CompanyService();
		ArrayList <Company> listAllCompanies = companyService.getListCompanies();
		pagination = new Page(listAllCompanies.size());
		ArrayList<Company> listDisplayCompanies;
		boolean keepDisplaying = true;
		String immediate_answer;
		while (keepDisplaying) {
			listDisplayCompanies = pagination.getDisplayListCompanies(listAllCompanies);
			displayListCompanies(listDisplayCompanies);
			System.out.println("Page " + pagination.getPage() + "/" + pagination.getNbPages() + " | " + LIST_PAGE_ACTIONS);
			immediate_answer = sc.nextLine();
			if (immediate_answer.equals("n")){
				pagination.nextPage();
			}
			else if (immediate_answer.equals("p")) {
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
	
	
	/*
	 * Display of one computer, selected by id
	 */
	private void getOneComputer() {
		computerService = new ComputerService();
		System.out.println("Please enter the computer id :");
		int computer_id = Integer.parseInt(sc.nextLine());
		Computer computer = computerService.getOneComputer(computer_id);
		System.out.println(computer);
	}
	
	/*
	 * List of companies display
	 */
	private void getListCompanies() {
		ArrayList <Company> listCompanies = new ArrayList<>();
		companyService = new CompanyService();
		listCompanies = companyService.getListCompanies();
		for (Company company : listCompanies) {
			System.out.println(company);
		}
	}
	
	/*
	 * Creation of one computer.
	 * Only name is mandatory
	 */
	private void createOneComputer() {
		LocalDate introduced = null;
		LocalDate discontinued = null;
		int company_id = 0;
		String immediate_answer = null;
		computerService = new ComputerService();
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
		computerService.createOne(name, introduced, discontinued, company_id);
	}
	
	/*
	 * Modification of a computer.
	 * User must enter the field to be modified (number 1-4) and the new value.
	 */
	private void updateOneComputer() {
		computerService = new ComputerService();
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
			case COND_ONE:
				System.out.println("New name ? ");
				value = sc.nextLine();
				break;
			case COND_TWO:
			case COND_THREE:
				System.out.println("Year ? ");
				int y = Integer.parseInt(sc.nextLine());
				System.out.println("Month ? ");
				int m = Integer.parseInt(sc.nextLine());
				System.out.println("Day ? ");
				int d = Integer.parseInt(sc.nextLine());
				value = LocalDate.of(y, m, d);
				break;
			case COND_FOUR:
				System.out.println("New company ? ");
				value = Integer.parseInt(sc.nextLine());
				break;
			}
		} catch (NumberFormatException e){}
		computerService.updateOne(computer_id, field, value);
	}
	
	/*
	 * Deletion of a computer from the database.
	 */
	private void deleteOneComputer() {
		computerService = new ComputerService();
		int computer_id = 0;
		System.out.println("Please enter the computer id :");
		try {
			computer_id = Integer.parseInt(sc.nextLine());
		} catch (NumberFormatException e){}
		computerService.deleteOne(computer_id);
	}
	
}

package com.excilys.cdb.ui;

import java.util.ArrayList;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Page;
import com.excilys.cdb.controller.CLIController;
import com.excilys.cdb.dto.CompanyDTOJsp;
import com.excilys.cdb.dto.ComputerDTOJsp;
import com.excilys.cdb.exceptions.EnumException;

@Component
public class CLI {
	
	private static final String LIST_ACTIONS = System.lineSeparator() + "List of available actions : " + System.lineSeparator() +
												"0 :  Exit program" + System.lineSeparator() +
												"1 :  Print the list of all computers" + System.lineSeparator() +
												"2 :  Print the list of all companies" + System.lineSeparator() +
												"3 :  Show the details of one computer" + System.lineSeparator() +
												"4 :  Create a computer" + System.lineSeparator() +
												"5 :  Update a computer" + System.lineSeparator() +
												"6 :  Delete a computer" + System.lineSeparator() +
												"7 :  Add a company" + System.lineSeparator() +
												"8 :  Delete a company" ;
	private static final String LIST_PAGE_ACTIONS = "p : previous / n : next / page_number ?" + System.lineSeparator() +
													"0 :  Exit display";

	private Scanner sc = new Scanner(System.in);
	@Autowired
	private CLIController cliController;
	private Page pagination;

	private static Logger logger = LoggerFactory.getLogger(CLI.class);
	
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
						case ADD_COMPANY:
							createOneCompany();
							break;
						case DELETE_COMPANY:
							deleteOneCompany();
							break;
						default :System.out.println("Please enter a correct action...");
					}
				} catch (EnumException e) {
					logger.error(e.getMessage());
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
		ArrayList<ComputerDTOJsp> listDisplayComputers;
		int limit = pagination.getSize();
		int offset ;
		
		boolean keepDisplaying = true;
		String immediate_answer;
		while (keepDisplaying) {
			offset = pagination.getOffset();
			limit = (pagination.getPage() == pagination.getNbPages()) ? (nbComputers+1 % pagination.getSize()) : pagination.getSize();
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
	
	private void displayListComputers(ArrayList<ComputerDTOJsp> listDisplayComputers) {
		for (ComputerDTOJsp computerDTO : listDisplayComputers) {
			System.out.println(computerDTO);
		} 
	}
	
	private void displayListCompanies() {
		int nbCompanies = cliController.getNumberCompanies();
		pagination = new Page(nbCompanies);
		ArrayList<CompanyDTOJsp> listDisplayCompanies;
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

	private void displayListCompanies(ArrayList<CompanyDTOJsp> listDisplayCompanies) {
		for (CompanyDTOJsp companyDTO : listDisplayCompanies) {
			System.out.println(companyDTO);
		} 
	}
	
	
	private void getOneComputer() {
		System.out.println("Please enter the computer id :");
		int computer_id = Integer.parseInt(sc.nextLine());
		ComputerDTOJsp computerDTO = cliController.getOneComputer(computer_id);
		if (computerDTO != null) {System.out.println(computerDTO);}
	}
	

	private void createOneComputer() {
		String introduced = "";
		String discontinued = "";
		String company_id = "";
		String immediate_answer = "";
		System.out.println("Please enter the computer name :");
		String name = sc.nextLine();
		System.out.println("Add an introduction date ? (y/n)");
		immediate_answer = sc.nextLine();
		if ("y".equals(immediate_answer)){
			introduced = askDate();
		}
		System.out.println("Add a discontinuation date ? (y/n)");
		immediate_answer = sc.nextLine();
		if ("y".equals(immediate_answer)){
			discontinued = askDate();
		}
		System.out.println("Add a company id ? (y/n)");
		immediate_answer = sc.nextLine();
		if ("y".equals(immediate_answer)){
			System.out.println("Please enter the id :");
			company_id = sc.nextLine();
		}
		ComputerDTOJsp computerDTO = new ComputerDTOJsp.ComputerDTOJspBuilder(name).introduced(introduced).discontinued(discontinued).companyId(company_id).build();
		cliController.createOne(computerDTO);
	}
	
	private String askDate() {
		System.out.println("Year ? ");
		String y = sc.nextLine();
		System.out.println("Month ? ");
		String m = sc.nextLine();
		System.out.println("Day ? ");
		String d = sc.nextLine();
		return y + "-" + m + "-" + d;
	}

	private void updateOneComputer() {
		String id;
		System.out.println("Please enter the computer id :");
		id = sc.nextLine();
		String introduced = "";
		String discontinued = "";
		String company_id = "";
		String immediate_answer = "";
		System.out.println("Please enter the computer name :");
		String name = sc.nextLine();
		System.out.println("Add an introduction date ? (y/n)");
		immediate_answer = sc.nextLine();
		if ("y".equals(immediate_answer)){
			introduced = askDate();
		}
		System.out.println("Add a discontinuation date ? (y/n)");
		immediate_answer = sc.nextLine();
		if ("y".equals(immediate_answer)){
			discontinued = askDate();
		}
		System.out.println("Add a company id ? (y/n)");
		immediate_answer = sc.nextLine();
		if ("y".equals(immediate_answer)){
			System.out.println("Please enter the id :");
			company_id = sc.nextLine();
		}
		ComputerDTOJsp computerDTO = new ComputerDTOJsp.ComputerDTOJspBuilder(id, name).introduced(introduced).discontinued(discontinued).companyId(company_id).build();
		cliController.updateOne(computerDTO);
	}

	private void deleteOneComputer() {
		int computer_id = 0;
		System.out.println("Please enter the computer id :");
		try {
			computer_id = Integer.parseInt(sc.nextLine());
		} catch (NumberFormatException e){
			logger.error(e.getMessage());
		}
		cliController.deleteOne(computer_id);
	}

	private void createOneCompany() {
		System.out.println("Please enter the company name :");
		cliController.createOneCompany(sc.nextLine());
	}
	
	private void deleteOneCompany() {
		int company_id = 0;
		System.out.println("Please enter the company id :");
		try {
			company_id = Integer.parseInt(sc.nextLine());
		} catch (NumberFormatException e){
			logger.error(e.getMessage());
		}
		cliController.deleteOneCompany(company_id);
	}
	
}

package com.excilys.cdb.mapper;

import java.time.LocalDate;
import java.util.ArrayList;

import com.excilys.cdb.dto.ComputerDTOJsp;
import com.excilys.cdb.dto.ComputerDTOJsp.ComputerDTOJspBuilder;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Computer.ComputerBuilder;

public class ComputerMapperServlet {
	
	private static ComputerMapperServlet instance;
	
	private ComputerMapperServlet() {
		
	}
	
	public static ComputerMapperServlet getInstance() {
		if (instance == null) {
			instance = new ComputerMapperServlet();
		}
		return instance;
	}
	
	public ComputerDTOJsp toDTO(Computer computer){
		String id = "";
		String name = "";
		String introduced = "";
		String discontinued = "";
		String companyId = "";
		String companyName = "";
		
		id = Integer.toString(computer.getId());
		name = computer.getName();
		ComputerDTOJspBuilder builder = new ComputerDTOJsp.ComputerDTOJspBuilder(id, name);
		if (computer.getIntroducedDate()!=null) {
			introduced = computer.getIntroducedDate().toString();
			builder.introduced(introduced);
		}
		if (computer.getDiscontinuedDate()!=null) {
			discontinued = computer.getDiscontinuedDate().toString();
			builder.discontinued(discontinued);
		}
		if (computer.getCompany() != null) {
			companyId = Integer.toString(computer.getCompany().getId());
			companyName = computer.getCompany().getName();
			builder.companyId(companyId);
			builder.companyName(companyName);
		}
		return builder.build();
	}

	public ArrayList<ComputerDTOJsp> listToDTO(ArrayList<Computer> listComputers) {
		ArrayList<ComputerDTOJsp> listComputerDTO = new ArrayList<ComputerDTOJsp>();
		for (Computer computer:listComputers) {
			listComputerDTO.add(toDTO(computer));
		}
		return listComputerDTO;
	}

	public Computer toComputer(ComputerDTOJsp computerDTO) {
		String name = computerDTO.getName();
		ComputerBuilder builder = new Computer.ComputerBuilder(name);
		if (! "".equals(computerDTO.getId())) {
			int id = Integer.parseInt(computerDTO.getId());
			builder.id(id);
		}
		if (computerDTO.getIntroduced() != "") {
			LocalDate introduced = LocalDate.parse(computerDTO.getIntroduced()); 
			builder.introducedDate(introduced);
		}
		if (computerDTO.getDiscontinued() != "") {
			LocalDate discontinued = LocalDate.parse(computerDTO.getDiscontinued()); 
			builder.discontinuedDate(discontinued);
		}
		if (computerDTO.getCompanyId() != "") {
			Company company = new Company(Integer.parseInt(computerDTO.getCompanyId()), computerDTO.getCompanyName());
			builder.company(company);
		}
		return builder.build();
	}
	
	
}

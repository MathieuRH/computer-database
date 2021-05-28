package com.excilys.cdb.mapper;

import java.util.ArrayList;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.model.Computer;

public class ComputerDTOMapper {
	
	public static ComputerDTO computerToDTO(Computer computer){
		String id = "";
		String name = "";
		String introduced = "";
		String discontinued = "";
		String companyId = "";
		String companyName = "";
		
		id = Integer.toString(computer.getId());
		name = computer.getName();
		if (computer.getIntroducedDate()!=null) {
			introduced = computer.getIntroducedDate().toString();
		}
		if (computer.getDiscontinuedDate()!=null) {
			discontinued = computer.getDiscontinuedDate().toString();
		}
		if (computer.getCompany() != null) {
			companyId = Integer.toString(computer.getCompany().getId());
			companyName = computer.getCompany().getName();
		}
		ComputerDTO computerDTO = new ComputerDTO(id, name, introduced, discontinued, companyId, companyName);
		return computerDTO;
	}

	public static ArrayList<ComputerDTO> listComputersToDTO(ArrayList<Computer> listComputers) {
		ArrayList<ComputerDTO> listComputerDTO = new ArrayList<ComputerDTO>();
		for (Computer computer:listComputers) {
			listComputerDTO.add(computerToDTO(computer));
		}
		return listComputerDTO;
	}
}

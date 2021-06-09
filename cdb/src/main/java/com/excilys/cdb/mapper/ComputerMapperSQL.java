package com.excilys.cdb.mapper;

import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.ComputerDTOSQL;
import com.excilys.cdb.dto.ComputerDTOSQL.ComputerDTOSQLBuilder;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

@Component
public class ComputerMapperSQL {

	public ComputerDTOSQL toComputerDTO(Computer computer) {
		String id = Integer.toString(computer.getId());
		String name = computer.getName();
		ComputerDTOSQLBuilder builder = new ComputerDTOSQL.ComputerDTOSQLBuilder(id, name);
		if (computer.getIntroducedDate() != null) {
			String introduced = computer.getIntroducedDate().toString(); 
			builder.introduced(introduced);
		}
		if (computer.getDiscontinuedDate() != null) {
			String discontinued = computer.getDiscontinuedDate().toString(); 
			builder.discontinued(discontinued);
		}
		if (computer.getCompany() != null) {
			Company company = computer.getCompany();
			builder.companyId(Integer.toString(company.getId()));
			builder.companyName(company.getName());
		}
		return builder.build();
	}
}


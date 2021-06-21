package com.excilys.cdb.mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.ComputerDTOSQL;
import com.excilys.cdb.dto.ComputerDTOSQL.ComputerDTOSQLBuilder;
import com.excilys.cdb.dto.ComputerDTOFromDB;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Computer.ComputerBuilder;

@Component
public class ComputerMapperSQL {

	CompanyMapperSQL companyMapper;
	
	public ComputerMapperSQL(CompanyMapperSQL companyMapper) {
		this.companyMapper=companyMapper;
	}
	
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
		}
		return builder.build();
	}
	
	
	public ArrayList<Computer> DBtoListComputers(List<ComputerDTOFromDB> listComputersDTO) {
		ArrayList<Computer>  listComputers= new ArrayList<Computer>();
		listComputers = (ArrayList<Computer>) listComputersDTO.stream()
				.map(c -> toComputer(c))
				.collect(Collectors.toList());
		
		return listComputers;
	}

	public Computer toComputer(ComputerDTOFromDB computerDTO) {
		String name = computerDTO.getName();
		ComputerBuilder builder = new Computer.ComputerBuilder(name);
		if (!"".equals(computerDTO.getId())) {
			int id = Integer.parseInt(computerDTO.getId());
			builder.id(id);
		}
		if (computerDTO.getIntroduced() != null) {
			LocalDate introduced = computerDTO.getIntroduced().toLocalDate(); 
			builder.introducedDate(introduced);
		}
		if (computerDTO.getDiscontinued() != null) {
			LocalDate discontinued = computerDTO.getDiscontinued().toLocalDate(); 
			builder.discontinuedDate(discontinued);
		}
		if (computerDTO.getCompanyDTOSQL() != null) {
			Company company = companyMapper.toCompany(computerDTO.getCompanyDTOSQL());
			builder.company(company);
		}
		return builder.build();
	}
}


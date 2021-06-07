package com.excilys.cdb.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.ComputerDTOSQL;
import com.excilys.cdb.dto.ComputerDTOSQL.ComputerDTOSQLBuilder;
import com.excilys.cdb.exceptions.ConnectionException;
import com.excilys.cdb.exceptions.QueryException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Computer.ComputerBuilder;

@Component
public class ComputerMapperSQL {
	
	public ArrayList<Computer> getListComputers(ResultSet rs) throws SQLException, ConnectionException, QueryException {
		ArrayList<Computer> listComputers = new ArrayList<Computer>();
		while (rs.next()) {
		    int id = rs.getInt("C.id");
		    String name = rs.getString("C.name");
		    Date dateInt = rs.getDate("C.introduced");
		    Date dateDis = rs.getDate("C.discontinued");
		    int company_id = rs.getInt("Y.id");
		    String company_name = rs.getString("Y.name");
		    ComputerBuilder comp = new Computer.ComputerBuilder(id, name);
		    if (dateInt != null) {
		    	comp.introducedDate(dateInt.toLocalDate());
		    }
		    if (dateDis != null) {
		    	comp.discontinuedDate(dateDis.toLocalDate());
		    }
		    if (company_id != 0) {
		    	Company company = new Company(company_id, company_name);
		    	comp.company(company);
		    }
		    listComputers.add(comp.build());
		}
		return listComputers;
	}
	
	public Optional<Computer> getOneComputer(ResultSet rs) throws SQLException, ConnectionException, QueryException {
		try {
			return Optional.ofNullable(getListComputers(rs).get(0));
		} catch (IndexOutOfBoundsException e) {
			return Optional.empty();
		}
	}

	public ArrayList<Computer> toListComputers(ArrayList<ComputerDTOSQL> listComputersDTO) {
		ArrayList<Computer> listComputers = new ArrayList<Computer>();
		for (ComputerDTOSQL computerDTO:listComputersDTO) {
			listComputers.add(toComputer(computerDTO));
		}
		return listComputers;
	}

	public Computer toComputer(ComputerDTOSQL computerDTO) {
		int id = Integer.parseInt(computerDTO.getId());
		String name = computerDTO.getName();
		ComputerBuilder builder = new Computer.ComputerBuilder(id, name);
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


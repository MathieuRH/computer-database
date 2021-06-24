package com.excilys.cdb.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.CompanyDTOSQL;
import com.excilys.cdb.model.Company;

@Component
public class CompanyMapperSQL {

	public ArrayList<Company> toListCompanies(List<CompanyDTOSQL> listCompaniesDTO) {
		ArrayList<Company>  listCompanies= new ArrayList<Company>();
		listCompanies = (ArrayList<Company>) listCompaniesDTO.stream()
				.map(c -> toCompany(c))
				.collect(Collectors.toList());
		
		return listCompanies;
	}

	public Company toCompany(CompanyDTOSQL companyDTO) {
		if (companyDTO==null) {
			return null;
		}
		int companyId = Integer.parseInt(companyDTO.getId());
		String companyName = companyDTO.getName();
		Company company = new Company(companyId, companyName);
		return company;
	}
}
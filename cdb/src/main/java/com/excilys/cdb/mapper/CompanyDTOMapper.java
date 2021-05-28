package com.excilys.cdb.mapper;

import java.util.ArrayList;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.model.Company;

public class CompanyDTOMapper {
	
	public static CompanyDTO companyToDTO(Company company){
		String companyId = Integer.toString(company.getId());
		String companyName = company.getName();
		CompanyDTO companyDTO = new CompanyDTO(companyId, companyName);
		return companyDTO;
	}

	public static ArrayList<CompanyDTO> listCompaniesToDTO(ArrayList<Company> listCompanies) {
		ArrayList<CompanyDTO>  listCompanyDTO = new ArrayList<CompanyDTO>();
		for (Company company:listCompanies) {
			listCompanyDTO.add(companyToDTO(company));
		}
		return listCompanyDTO;
	}
}

package com.excilys.cdb.mapper;

import java.util.ArrayList;

import com.excilys.cdb.dto.CompanyDTOJsp;
import com.excilys.cdb.model.Company;

public class CompanyMapperServlet {
	
	public static CompanyDTOJsp companyToDTO(Company company){
		String companyId = Integer.toString(company.getId());
		String companyName = company.getName();
		CompanyDTOJsp companyDTO = new CompanyDTOJsp(companyId, companyName);
		return companyDTO;
	}

	public static ArrayList<CompanyDTOJsp> listCompaniesToDTO(ArrayList<Company> listCompanies) {
		ArrayList<CompanyDTOJsp>  listCompanyDTO = new ArrayList<CompanyDTOJsp>();
		for (Company company:listCompanies) {
			listCompanyDTO.add(companyToDTO(company));
		}
		return listCompanyDTO;
	}
}

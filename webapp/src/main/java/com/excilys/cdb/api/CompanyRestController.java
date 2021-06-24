package com.excilys.cdb.api;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.dto.CompanyDTOJsp;
import com.excilys.cdb.exceptions.CompanyNotFoundException;
import com.excilys.cdb.exceptions.QueryException;
import com.excilys.cdb.mapper.CompanyMapperServlet;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.service.CompanyService;

@RestController
@RequestMapping("/api/company")
public class CompanyRestController {

	private CompanyService companyService;
	private CompanyMapperServlet companyMapper;
	
	public CompanyRestController(CompanyService companyService, CompanyMapperServlet companyMapper) {
		this.companyService=companyService;
		this.companyMapper=companyMapper;
	}
	
	@GetMapping("/getOne/{id}")
	public ResponseEntity<?> getCompany(@PathVariable Integer id) {
		Company company;
		try {
			company = companyService.getCompany(id).orElseThrow(() -> new CompanyNotFoundException(id));
			return new ResponseEntity<>(companyMapper.companyToDTO(company), HttpStatus.OK);
		} catch (CompanyNotFoundException | QueryException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = { "/getList", "/getList/{limit}", "/getList/{limit}/{offset}"})
	public ResponseEntity<?> getListCompanies(@PathVariable(required=false) Integer limit, 
			@PathVariable(required=false) Integer offset) {

		if (limit==null) {
			try {
				limit = companyService.getNumberCompanies();
			} catch (QueryException e) {
				return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
			}
		}
		if (offset==null) {
			offset=0;
		}
		
		ArrayList<Company> listCompanies = new ArrayList<>();
		try {
			listCompanies = this.companyService.getListCompanies(limit, offset);
		} catch (QueryException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		ArrayList<CompanyDTOJsp> listCompaniesDTO = companyMapper.listCompaniesToDTO(listCompanies);
		return new ResponseEntity<>(listCompaniesDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/create/{name}", method = RequestMethod.PUT)
	public ResponseEntity<?> create(@PathVariable String name) {
		try {
			companyService.createOne(name);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (QueryException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.I_AM_A_TEAPOT);
		}
	}
	
	@RequestMapping(value="/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		try {
			companyService.deleteOne(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (QueryException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
}

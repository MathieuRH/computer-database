package com.excilys.cdb.api;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.dto.ComputerDTOJsp;
import com.excilys.cdb.exceptions.ComputerNotFoundException;
import com.excilys.cdb.exceptions.InputException;
import com.excilys.cdb.exceptions.QueryException;
import com.excilys.cdb.mapper.ComputerMapperServlet;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.verification.Verificator;

@RestController
@RequestMapping("/api/computer")
public class ComputerRestController {

	private ComputerService computerService;
	private ComputerMapperServlet computerMapper;
	private Verificator verificator;
	
	public ComputerRestController(ComputerService computerService, ComputerMapperServlet computerMapper, Verificator verificator) {
		this.computerService=computerService;
		this.computerMapper=computerMapper;
		this.verificator=verificator;
	}
	
	@GetMapping("/getOne/{id}")
	public ResponseEntity<?> getComputer(@PathVariable Integer id) {
		Computer computer;
		try {
			computer = computerService.getOneComputer(id).orElseThrow(() -> new ComputerNotFoundException(id));
			return new ResponseEntity<>(computerMapper.toDTO(computer), HttpStatus.OK);
		} catch (ComputerNotFoundException | QueryException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/getList")
	public ResponseEntity<?> getListComputers(@RequestBody(required=false) Page page, 
			@RequestParam(required=false) String query, 
			@RequestParam(required=false) String name) {

		if (page==null) {
			page = new Page();
		}
		if (query==null) {
			query = "";
		}
		if (name==null) {
			name = "";
		}
		
		ArrayList<Computer> listComputer = new ArrayList<>();
		try {
			listComputer = this.computerService.getListComputers(page, query, name);
		} catch (QueryException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		ArrayList<ComputerDTOJsp> listComputersDTO = computerMapper.listToDTO(listComputer);

		return new ResponseEntity<>(listComputersDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/count")
	public ResponseEntity<?> count() {
		try {
			int number = computerService.getNumberComputers();
			return new ResponseEntity<>(number, HttpStatus.OK);
		} catch (QueryException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value="/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> create(@RequestBody ComputerDTOJsp computerDTO) {
		try {
			verificator.verifyComputer(computerDTO);
			int id = computerService.createOne(computerMapper.toComputer(computerDTO));
			return new ResponseEntity<>(id, HttpStatus.OK);
		} catch (InputException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
		} catch (QueryException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value="/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody ComputerDTOJsp computerDTO) {
		try {
			verificator.verifyComputer(computerDTO);
			computerService.updateOne(computerMapper.toComputer(computerDTO));
			return new ResponseEntity<>(computerMapper.toComputer(computerDTO), HttpStatus.OK);
		} catch (InputException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
		} catch (QueryException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value="/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		try {
			computerService.deleteOne(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (QueryException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
}

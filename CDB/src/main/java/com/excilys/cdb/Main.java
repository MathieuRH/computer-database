package com.excilys.cdb;

import java.sql.SQLException;
import java.time.LocalDate;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.dao.DBConnection;

public class Main {
	
	public static void main(String[] args) {
		
		ComputerDAO c = new ComputerDAO();
		//c.getOneComputer(1);
		LocalDate d = LocalDate.of(2020,05,30);
		//c.createOne("Itouch 54", d, d, 0);
		
		//c.deleteOne(577);
		//c.updateOne(578, 4, 35);
		c.getListComputers();
		
		//CompanyDAO d = new CompanyDAO();
		//d.getListCompanies();
	}
	
}

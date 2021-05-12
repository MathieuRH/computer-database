package com.excilys.cdb.model;

import java.time.LocalDate;

public class Computer {
	
	private int id;
	private String name;
	private LocalDate introducedDate;
	private LocalDate discontinuedDate;
	private Company company;
	
	public Computer (int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Computer (int id, String name, Company company) {
		this(id, name);
		this.company = company;
	}
	
	public Computer(int id, String name, Company company, LocalDate introducedDate) {
		this(id, name, company);
		this.introducedDate = introducedDate;
	}
	
	public Computer(int id, String name, Company company, LocalDate introducedDate, LocalDate discontinuedDate) {
		this(id, name, company, introducedDate);
		this.discontinuedDate = discontinuedDate;
	}
	

	public void setIntroducedDate(LocalDate introducedDate) {
		if (introducedDate != null) {
			this.introducedDate = introducedDate;
		}
	}
	public void setDiscontinuedDate(LocalDate discontinuedDate) {
		if (discontinuedDate != null && discontinuedDate.isAfter(introducedDate)) {
			this.discontinuedDate = discontinuedDate;
		} else {System.out.println("DiscontinuationDate setting failed : Discontinued date can't be after introduced date");}
	}
	
	@Override
	public String toString() {
		String dateIntro = (introducedDate == null) ? "NA" : introducedDate.toString();
		String dateDisc = (discontinuedDate == null) ? "NA" : discontinuedDate.toString();
		String printCompany = (this.company == null) ? "Company : NA" : company.toString();
		String res = "Computer n°" + id +
				" : {" + name +
				", introduction date: " + dateIntro +
				", discard date: " + dateDisc +
				", " + printCompany + "}";
		return res;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getIntroducedDate() {
		return introducedDate;
	}
	public LocalDate getDiscontinuedDate() {
		return discontinuedDate;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	
	
	
}

package com.excilys.cdb.model;

import java.time.LocalDate;

/**
 * Computer definition.
 * Only name is mandatory for a creation.
 * @author Mathieu_RH
 *
 */
public class Computer {
	
	private final int id;
	private final String name;
	private final LocalDate introducedDate;
	private final LocalDate discontinuedDate;
	private final Company company;
	
	private Computer(ComputerBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.introducedDate = builder.introducedDate;
		this.discontinuedDate = builder.discontinuedDate;
		this.company = builder.company;
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
	
	public static class ComputerBuilder{
		private int id;
		private String name;
		private LocalDate introducedDate;
		private LocalDate discontinuedDate;
		private Company company;
		
		public ComputerBuilder(int id, String name) {
			this.id = id;
			this.name = name;
		}
		public ComputerBuilder introducedDate(LocalDate introducedDate) {
			this.introducedDate = introducedDate;
			return this;
		}
		public ComputerBuilder discontinuedDate(LocalDate discontinuedDate) {
			this.discontinuedDate = discontinuedDate;
			return this;
		}
		public ComputerBuilder company(Company company) {
			this.company = company;
			return this;
		}
		
		public Computer build() {
			return new Computer(this);
		}
	}
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
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
		
}

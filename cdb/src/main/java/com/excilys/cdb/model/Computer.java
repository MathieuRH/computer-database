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
		public ComputerBuilder(String name) {
			this.name = name;
		}
		public ComputerBuilder id(int id) {
			this.id = id;
			return this;
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + ((discontinuedDate == null) ? 0 : discontinuedDate.hashCode());
		result = prime * result + id;
		result = prime * result + ((introducedDate == null) ? 0 : introducedDate.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Computer other = (Computer) obj;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (discontinuedDate == null) {
			if (other.discontinuedDate != null)
				return false;
		} else if (!discontinuedDate.equals(other.discontinuedDate))
			return false;
		if (id != other.id)
			return false;
		if (introducedDate == null) {
			if (other.introducedDate != null)
				return false;
		} else if (!introducedDate.equals(other.introducedDate))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
		
}

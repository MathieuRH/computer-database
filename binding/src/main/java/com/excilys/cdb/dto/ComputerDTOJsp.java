package com.excilys.cdb.dto;

public class ComputerDTOJsp {
	private String id = "";
	private String name = "";
	private String introduced = "";
	private String discontinued = "";
	private String companyId = "";
	private String companyName = "";
	
	public ComputerDTOJsp() {
		
	}
	
	private ComputerDTOJsp(ComputerDTOJspBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.introduced = builder.introduced;
		this.discontinued = builder.discontinued;
		this.companyId = builder.companyId;
		this.companyName = builder.companyName;
	}
	
	public static class ComputerDTOJspBuilder {
		private String id = "";
		private String name = "";
		private String introduced = "";
		private String discontinued = "";
		private String companyId = "";
		private String companyName = "";

		public ComputerDTOJspBuilder() {
		}
		
		public ComputerDTOJspBuilder(String name) {
			this.name = name;
		}
		
		public ComputerDTOJspBuilder(String id, String name) {
			this.id = id;
			this.name = name;
		}

		public ComputerDTOJspBuilder id(String id) {
			this.id = id;
			return this;
		}
		public ComputerDTOJspBuilder introduced(String introduced) {
			this.introduced = introduced;
			return this;
		}
		public ComputerDTOJspBuilder discontinued(String discontinued) {
			this.discontinued = discontinued;
			return this;
		}
		public ComputerDTOJspBuilder companyId(String companyId) {
			this.companyId = companyId;
			return this;
		}
		public ComputerDTOJspBuilder companyName(String companyName) {
			this.companyName = companyName;
			return this;
		}
		
		public ComputerDTOJsp build() {
			return new ComputerDTOJsp(this);
		}
	}
	
	@Override
	public String toString() {
		String dateIntro = ("".equals(introduced)) ? "NA" : introduced;
		String dateDisc = ("".equals(discontinued)) ? "NA" : discontinued;
		String printCompany = ("".equals(companyName)) ? "Company : NA" : companyName;
		String res = "Computer n°" + id + 
				" : {" + name +
				", introduction date: " + dateIntro +
				", discard date: " + dateDisc +
				", " + printCompany + "}";
		return res;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getIntroduced() {
		return introduced;
	}

	public String getDiscontinued() {
		return discontinued;
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}

	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	
}

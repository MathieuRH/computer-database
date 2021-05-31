package com.excilys.cdb.dto;

public class ComputerDTOSQL {
	private String id;
	private String name;
	private String introduced;
	private String discontinued;
	private String companyId;
	private String companyName;
	
	private ComputerDTOSQL(ComputerDTOSQLBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.introduced = builder.introduced;
		this.discontinued = builder.discontinued;
		this.companyId = builder.companyId;
		this.companyName = builder.companyName;
	}
	
	public static class ComputerDTOSQLBuilder {
		private String id;
		private String name;
		private String introduced;
		private String discontinued;
		private String companyId;
		private String companyName;
		
		public ComputerDTOSQLBuilder(String name) {
			this.name = name;
		}
		
		public ComputerDTOSQLBuilder(String id, String name) {
			this.id = id;
			this.name = name;
		}
		public ComputerDTOSQLBuilder id(String id) {
			this.id = id;
			return this;
		}
		public ComputerDTOSQLBuilder introduced(String introduced) {
			this.introduced = introduced;
			return this;
		}
		public ComputerDTOSQLBuilder discontinued(String discontinued) {
			this.discontinued = discontinued;
			return this;
		}
		public ComputerDTOSQLBuilder companyId(String companyId) {
			this.companyId = companyId;
			return this;
		}
		public ComputerDTOSQLBuilder companyName(String companyName) {
			this.companyName = companyName;
			return this;
		}
		
		public ComputerDTOSQL build() {
			return new ComputerDTOSQL(this);
		}
	}
	
	@Override
	public String toString() {
		String dateIntro = ("".equals(introduced)) ? "NA" : introduced;
		String dateDisc = ("".equals(discontinued)) ? "NA" : discontinued;
		String printCompany = ("".equals(companyName)) ? "Company : NA" : companyName;
		String res = "Computer n° " + id + 
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
	
}

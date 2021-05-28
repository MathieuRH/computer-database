package com.excilys.cdb.dto;

public class ComputerDTO {
	private String id;
	private String name;
	private String introduced;
	private String discontinued;
	private String companyId;
	private String companyName;
	
	public ComputerDTO(String id, String name, String introduced, String discontinued, String companyId, String companyName) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyId = companyId;
		this.companyName = companyName;
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

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIntroduced() {
		return introduced;
	}
	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}
	public String getDiscontinued() {
		return discontinued;
	}
	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	public String getCompanyId() {
		return companyId;
	}


	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
}

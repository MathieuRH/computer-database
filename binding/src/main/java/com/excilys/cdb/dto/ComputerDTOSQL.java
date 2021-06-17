package com.excilys.cdb.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="computer")
public class ComputerDTOSQL {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;
	private String name;
	private String introduced;
	private String discontinued;
	
	@Column(name = "company_id")
	private String companyId;
	
	public ComputerDTOSQL() {
	}
	
	private ComputerDTOSQL(ComputerDTOSQLBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.introduced = builder.introduced;
		this.discontinued = builder.discontinued;
		this.companyId = builder.companyId;
	}
	
	public static class ComputerDTOSQLBuilder {
		private String id;
		private String name;
		private String introduced;
		private String discontinued;
		private String companyId;
		
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
		
		public ComputerDTOSQL build() {
			return new ComputerDTOSQL(this);
		}
	}
	
	@Override
	public String toString() {
		String dateIntro = ("".equals(introduced)) ? "NA" : introduced;
		String dateDisc = ("".equals(discontinued)) ? "NA" : discontinued;
		String printCompany = ("".equals(companyId)) ? "Company : NA" : "Company n° : "+companyId;
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

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
}

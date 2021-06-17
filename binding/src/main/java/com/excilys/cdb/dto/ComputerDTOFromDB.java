package com.excilys.cdb.dto;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="computer")
public class ComputerDTOFromDB {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;
	private String name;
	private Date introduced;
	private Date discontinued;
	@ManyToOne(targetEntity = CompanyDTOSQL.class)
	@JoinColumn(name = "company_id", nullable = true)
	private CompanyDTOSQL companyDTOSQL;
	
	public ComputerDTOFromDB() {
	}
	
	private ComputerDTOFromDB(ComputerDTOToDBBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.introduced = builder.introduced;
		this.discontinued = builder.discontinued;
		this.companyDTOSQL = new CompanyDTOSQL(builder.companyId, builder.companyName);
	}
	
	public static class ComputerDTOToDBBuilder {
		private String id;
		private String name;
		private Date introduced;
		private Date discontinued;
		private String companyId;
		private String companyName;
		
		public ComputerDTOToDBBuilder(String name) {
			this.name = name;
		}
		
		public ComputerDTOToDBBuilder(String id, String name) {
			this.id = id;
			this.name = name;
		}
		public ComputerDTOToDBBuilder id(String id) {
			this.id = id;
			return this;
		}
		public ComputerDTOToDBBuilder introduced(Date introduced) {
			this.introduced = introduced;
			return this;
		}
		public ComputerDTOToDBBuilder discontinued(Date discontinued) {
			this.discontinued = discontinued;
			return this;
		}
		public ComputerDTOToDBBuilder companyId(String companyId) {
			this.companyId = companyId;
			return this;
		}
		public ComputerDTOToDBBuilder companyName(String companyName) {
			this.companyName = companyName;
			return this;
		}
		
		public ComputerDTOFromDB build() {
			return new ComputerDTOFromDB(this);
		}
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

	public Date getIntroduced() {
		return introduced;
	}

	public void setIntroduced(Date introduced) {
		this.introduced = introduced;
	}

	public Date getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(Date discontinued) {
		this.discontinued = discontinued;
	}

	public CompanyDTOSQL getCompanyDTOSQL() {
		return companyDTOSQL;
	}

	public void setCompanyDTOSQL(CompanyDTOSQL companyDTOSQL) {
		this.companyDTOSQL = companyDTOSQL;
	}
	
	
}

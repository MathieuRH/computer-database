package com.excilys.cdb.dto;

public class CompanyDTOJsp {
	private String id;
	private String name;
	
	public CompanyDTOJsp(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Company n°" + id +" : " + name;
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
	
}

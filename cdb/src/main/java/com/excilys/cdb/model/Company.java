package com.excilys.cdb.model;

/**
 * Company definition.
 * @author Mathieu_RH
 *
 */
public class Company {

	private String name;
	private int id;

	public Company() {
	}
	
	public Company(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Company n°" + id +" : " + name;
	}
	
	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIdcompany() {
		return id;
	}
	
}

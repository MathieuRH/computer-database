package com.excilys.cdb.model;

import java.util.ArrayList;

/**
 * Pagination class.
 * @author Mathieu_RH
 *
 */
public class Page {

	private static final int DEFAULT_PAGE_NUMBER = 1;
	private static final int DEFAULT_PAGE_SIZE = 25;
	private static final int DEFAULT_NUMBER_PAGES = 1;
	
	private int page;
	private int size;
	private int nbPages;
	
	public Page() {
		this.page = DEFAULT_PAGE_NUMBER;
		this.size = DEFAULT_PAGE_SIZE;
		this.nbPages = DEFAULT_NUMBER_PAGES;
	}
	
	public Page(int page, int size) {
		this.page = page;
		this.size = size;
	}
	
	public Page(int nbLignes) {
		this(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE);
		if 	(nbLignes%size == 0) {
			nbPages = nbLignes/size;
		} else {nbPages = nbLignes/size + 1;}
	}

	
	public ArrayList<Computer> getDisplayListComputer(ArrayList<Computer> objectList){
		ArrayList<Computer> returnList = new ArrayList<Computer>();
		int start = (page-1)*size;
		int end = Math.min(objectList.size(), start + size);
		for (int i = start; i<end; i++) {
			returnList.add(objectList.get(i));
		}
		return returnList;
	}
	
	public ArrayList<Company> getDisplayListCompanies(ArrayList<Company> objectList){
		ArrayList<Company> returnList = new ArrayList<Company>();
		int start = (page-1)*size;
		int end = Math.min(objectList.size(), start + size);
		for (int i = start; i<end; i++) {
			returnList.add(objectList.get(i));
		}
		return returnList;
	}
	
	public void nextPage() {
		page = (page+1)%nbPages;
	}
	
	public void previousPage() {
		page = (page-1)%nbPages;
		if (page<1) {
			page += nbPages;
		}
	}
	
	
	
	public int getNbPages() {
		return nbPages;
	}

	public void setNbPages(int nbPages) {
		this.nbPages = nbPages;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}

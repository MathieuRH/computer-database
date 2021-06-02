package com.excilys.cdb.model;

/**
 * Pagination class.
 * @author Mathieu_RH
 *
 */
public class Page {

	private static final int DEFAULT_PAGE_NUMBER = 1;
	private static final int DEFAULT_PAGE_SIZE = 10;
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
		nbPages = deduceNbPages(size, nbLignes);
	}
	
	public Page(int page, int size, int nbLignes) {
		this(page, size);
		nbPages = deduceNbPages(size, nbLignes);
	}

	public int getOffset() {
		return (page-1)*size;
	}
	
	public void nextPage() {
		page = (page%nbPages)+1;
	}
	
	public void previousPage() {
		page = (page-1)%nbPages;
		if (page<1) {
			page += nbPages;
		}
	}
	
	private int deduceNbPages(int size, int nbLignes) {
		if 	(nbLignes%size == 0) {
			return nbLignes/size;
		} else {return nbLignes/size + 1;}
	}

	public void refreshNbPages(int nbLignes) {
		this.nbPages = deduceNbPages(this.size, nbLignes);
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

	//Does not refresh nbPages
	public void setSize(int size) {
		this.size = size;
	}

	public void setSize(int size, int nbLignes) {
		this.size = size;
		refreshNbPages(nbLignes);
	}
}

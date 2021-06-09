package com.excilys.cdb.controller.session;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Page;

@Component
@Scope(value="session", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class SessionVariables {

	private Page pagination;
	private String query;
	private String previous_query;
	private String name_search = "";
	
	public Page getPagination() {
		return pagination;
	}
	public void setPagination(Page pagination) {
		this.pagination = pagination;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getPrevious_query() {
		return previous_query;
	}
	public void setPrevious_query(String previous_query) {
		this.previous_query = previous_query;
	}
	public String getName_search() {
		return name_search;
	}
	public void setName_search(String name_search) {
		this.name_search = name_search;
	}
	
	
}

package com.excilys.cdb.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.exceptions.QueryException;
import com.excilys.cdb.mapper.CompanyMapperSQL;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.dto.CompanyDTOSQL;

@Repository
@Transactional
public class CompanyDAO {
	
	private static final String LIST_COMPANIES_QUERY = "FROM CompanyDTOSQL";
	private static final String NUMBER_COMPANIES_QUERY = "SELECT COUNT(id) FROM CompanyDTOSQL";
	private static final String GET_COMPANY = "FROM CompanyDTOSQL WHERE id=:id";
	private static final String DELETE_ONE = "DELETE FROM CompanyDTOSQL WHERE id=:id";
	private static final String DELETE_LINKED_COMPUTERS = "DELETE FROM ComputerDTOSQL WHERE company_id=:company_id";

	private SessionFactory sessionFactory;
	private CompanyMapperSQL companyMapper;
	
	public CompanyDAO(SessionFactory sessionFactory, CompanyMapperSQL companyMapper) {
		this.sessionFactory = sessionFactory;
		this.companyMapper=companyMapper;
	}
	
	public ArrayList<Company> getListCompanies(int limit, int offset) throws QueryException {
		try {
			Session session = sessionFactory.getCurrentSession();
			ArrayList<Company> listCompanies= new ArrayList<Company>();
			List<CompanyDTOSQL> listCompaniesDTO = new ArrayList<>();
			Query<CompanyDTOSQL> query = session.createQuery(LIST_COMPANIES_QUERY, CompanyDTOSQL.class);
			query.setFirstResult(offset);
			query.setMaxResults(limit);
			listCompaniesDTO = query.list();
			
			listCompanies = companyMapper.toListCompanies(listCompaniesDTO);
			return listCompanies;
		} catch (HibernateException e) {
			throw new QueryException();
		}
	}
	
	public Company getOneCompany(int company_id) throws QueryException {
		try {
			Session session = sessionFactory.getCurrentSession(); 
			Query<CompanyDTOSQL> query=session.createQuery(GET_COMPANY, CompanyDTOSQL.class);
			query.setParameter("id", company_id);
			CompanyDTOSQL companyDTO = query.getSingleResult();
			return companyMapper.toCompany(companyDTO);
		} catch (HibernateException e) {
			throw new QueryException();
		}
	}
	
	public int getNumberCompanies() throws QueryException {
		try {
			Session session = sessionFactory.getCurrentSession(); 
			return session.createQuery(NUMBER_COMPANIES_QUERY, Long.class).uniqueResult().intValue();
		} catch (HibernateException e) {
			throw new QueryException();
		}
	}

	@Transactional
	public void deleteOne(int company_id) throws QueryException {
		try {
			Session session = sessionFactory.getCurrentSession(); 
			Query<?> query=session.createQuery(DELETE_LINKED_COMPUTERS);
			query.setParameter("company_id", Integer.toString(company_id));
			query.executeUpdate();

			Query<?> query2=session.createQuery(DELETE_ONE);
			query2.setParameter("id", Integer.toString(company_id));
			query2.executeUpdate();
		} catch (HibernateException e) {
			throw new QueryException();
		}
	}

	public void createOne(String name) throws QueryException {
		try {
			Session session = sessionFactory.getCurrentSession(); 
			session.save(new CompanyDTOSQL(name));
		} catch (HibernateException e) {
			throw new QueryException();
		}
	}
}

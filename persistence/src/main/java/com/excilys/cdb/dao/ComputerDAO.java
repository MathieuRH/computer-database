package com.excilys.cdb.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.dto.ComputerDTOSQL;
import com.excilys.cdb.dto.ComputerDTOFromDB;
import com.excilys.cdb.exceptions.QueryException;
import com.excilys.cdb.mapper.ComputerMapperSQL;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;

@Repository
@Transactional
public class ComputerDAO {
	
	private static final String LIST_COMPUTERS_QUERY = "FROM ComputerDTOFromDB C LEFT JOIN FETCH C.companyDTOSQL Y "
			+ "WHERE C.name LIKE :name "
			+ "ORDER BY ";
	private static final String NUMBER_COMPUTERS_QUERY = "SELECT COUNT(id) FROM ComputerDTOFromDB";
	private static final String NUMBER_COMPUTERS_BY_NAME_QUERY = "SELECT COUNT(id) FROM ComputerDTOFromDB "
			+ "WHERE name LIKE :name";
	private static final String ONE_COMPUTER_QUERY = "FROM ComputerDTOFromDB C LEFT JOIN FETCH C.companyDTOSQL Y "
			+ "WHERE C.id=:id";
	private static final String DELETE_ONE = "DELETE FROM ComputerDTOFromDB WHERE id=:id";

	private SessionFactory sessionFactory;
	private ComputerMapperSQL computerMapper;
	
	
	public ComputerDAO(SessionFactory sessionFactory, ComputerMapperSQL computerMapper) {
		this.sessionFactory = sessionFactory;
		this.computerMapper=computerMapper;
	}
	
	public ArrayList<Computer> getListComputers(Page pagination, String query_name, String name) throws QueryException { 
		ArrayList<Computer> listComputers = new ArrayList<Computer>();
		String orderByType = "C.id " + pagination.getSortOrder();
		switch (query_name) {
			case "orderByName":
				orderByType = "C.name " + pagination.getSortOrder();
				break;
			case "orderByIntroduced":
				orderByType = "C.introduced "+ pagination.getSortOrder() +" NULLS LAST, C.introduced "+ pagination.getSortOrder() +", C.name ";
				break;
			case "orderByDiscontinued":
				orderByType = "C.discontinued "+ pagination.getSortOrder() +" NULLS LAST, C.discontinued "+ pagination.getSortOrder() +", C.introduced NULLS LAST, C.introduced, C.name";
				break;
			case "orderByCompany":
				orderByType = "Y.name "+ pagination.getSortOrder() +" NULLS LAST, Y.name "+ pagination.getSortOrder() +", C.name";
				break;
		}
		String specific_query = LIST_COMPUTERS_QUERY + orderByType;
		String nameSearch = "%" + name + "%";
		int limit = pagination.getSize();
		int offset = pagination.getOffset();
		try {
			Session session = sessionFactory.getCurrentSession();
			List<ComputerDTOFromDB> listComputersDTO = new ArrayList<>();
			Query<ComputerDTOFromDB> query = session.createQuery(specific_query,  ComputerDTOFromDB.class);
			query.setParameter("name", nameSearch);
			query.setFirstResult(offset);
			query.setMaxResults(limit);
			listComputersDTO = query.list();
			
			listComputers = computerMapper.DBtoListComputers(listComputersDTO);
			return listComputers;
		} catch (HibernateException e) {
			throw new QueryException();
		}
	}
	
	public Optional<Computer> getOneComputer(int id_computer) throws QueryException{
		try {
			Session session = sessionFactory.getCurrentSession(); 
			Query<ComputerDTOFromDB> query=session.createQuery(ONE_COMPUTER_QUERY, ComputerDTOFromDB.class);
			query.setParameter("id", Integer.toString(id_computer));
			ComputerDTOFromDB computerDTO = query.getSingleResult();
			return Optional.ofNullable(computerMapper.toComputer(computerDTO));
		} catch (HibernateException e) {
			throw new QueryException();
		}
	}

	
	public int getNumberComputers() throws QueryException {
		try {
			Session session = sessionFactory.getCurrentSession(); 
			return session.createQuery(NUMBER_COMPUTERS_QUERY, Long.class).uniqueResult().intValue();
		} catch (HibernateException e) {
			throw new QueryException();
		}
	}

	public int getNumberComputersByName(String name) throws QueryException {
		try {
			Session session = sessionFactory.getCurrentSession(); 
			String nameSearch = "%" + name + "%";
			Query<Long> query=session.createQuery(NUMBER_COMPUTERS_BY_NAME_QUERY, Long.class);
			query.setParameter("name", nameSearch);
			return query.uniqueResult().intValue();
		} catch (HibernateException e) {
			throw new QueryException();
		}
	}
	
	
	public void createOne(Computer computer) throws QueryException {
		try {
			Session session = sessionFactory.getCurrentSession(); 
			ComputerDTOSQL computerDTO = computerMapper.toComputerDTO(computer);
			session.save(computerDTO);
		} catch (HibernateException e) {
			throw new QueryException();
		}
	}
	
	
	public void updateOne(Computer computer) throws QueryException {
		try {
			Session session = sessionFactory.getCurrentSession();
			
			ComputerDTOSQL computerDTO = computerMapper.toComputerDTO(computer);
			session.saveOrUpdate(computerDTO);
		} catch (HibernateException e) {
			throw new QueryException();
		}
	}
	
	public void deleteOne(int id_computer) throws QueryException {
		try {
			Session session = sessionFactory.getCurrentSession(); 
			Query<?> query=session.createQuery(DELETE_ONE);
			query.setParameter("id", Integer.toString(id_computer));
			query.executeUpdate();
		} catch (HibernateException e) {
			throw new QueryException();
		}
	}
}

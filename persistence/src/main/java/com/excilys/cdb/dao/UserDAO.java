package com.excilys.cdb.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.dto.UserDTOSQL;
import com.excilys.cdb.exceptions.QueryException;
import com.excilys.cdb.mapper.UserMapperSQL;
import com.excilys.cdb.model.User;

@Repository
@Transactional
public class UserDAO {
	
	private static final String GET_USER = "FROM UserDTOSQL WHERE id=:id";
	private static final String GET_USER_BY_NAME = "FROM UserDTOSQL WHERE username=:username";
	private static final String NUMBER_USERS_QUERY = "SELECT COUNT(id) FROM UserDTOSQL";
	private static final String LIST_USERS_QUERY = "FROM UserDTOSQL";
	private static final String DELETE_ONE = "DELETE FROM UserDTOSQL WHERE id=:id";
	
	private SessionFactory sessionFactory;
	private UserMapperSQL userMapper;
	private PasswordEncoder encoder;
	
	public UserDAO (UserMapperSQL userMapper, SessionFactory sessionFactory, PasswordEncoder encoder) {
		this.userMapper=userMapper;
		this.sessionFactory=sessionFactory;
		this.encoder=encoder;
	}
	
	public User getUser(int idUser) throws QueryException {
		try {
			Session session = sessionFactory.getCurrentSession(); 
			Query<UserDTOSQL> query=session.createQuery(GET_USER, UserDTOSQL.class);
			query.setParameter("id", Integer.toString(idUser));
			UserDTOSQL userDTO = query.getSingleResult();
			return userMapper.toUser(userDTO);
		} catch (HibernateException e) {
			throw new QueryException();
		}
	}

	public User getUserByUsername(String username) throws UsernameNotFoundException {
		try {
			Session session = sessionFactory.getCurrentSession(); 
			Query<UserDTOSQL> query=session.createQuery(GET_USER_BY_NAME, UserDTOSQL.class);
			query.setParameter("username", username);
			UserDTOSQL userDTO = query.getSingleResult();
			return userMapper.toUser(userDTO);
		} catch (HibernateException e) {
			throw new UsernameNotFoundException(username);
		}
	}

	public int getNumberUsers() throws QueryException {
		try {
			Session session = sessionFactory.getCurrentSession(); 
			return session.createQuery(NUMBER_USERS_QUERY, Long.class).uniqueResult().intValue();
		} catch (HibernateException e) {
			throw new QueryException();
		}
	}

	public ArrayList<User> getListUsers() throws QueryException {
		try {
			Session session = sessionFactory.getCurrentSession();
			ArrayList<User> listUsers= new ArrayList<User>();
			List<UserDTOSQL> listUsersDTO = new ArrayList<>();
			Query<UserDTOSQL> query = session.createQuery(LIST_USERS_QUERY, UserDTOSQL.class);
			listUsersDTO = query.list();
			
			listUsers =userMapper.toListUsers(listUsersDTO);
			return listUsers;
		} catch (HibernateException e) {
			throw new QueryException();
		}
	}
	
	public void create(User user) throws QueryException {
		try {
			Session session = sessionFactory.getCurrentSession(); 
			UserDTOSQL userDTO = userMapper.toUserDTO(user);
			userDTO.setPassword(encoder.encode(userDTO.getPassword()));
			session.save(userDTO);
		} catch (HibernateException e) {
			throw new QueryException();
		}
	}

	public void update(User user) throws QueryException {
		try {
			Session session = sessionFactory.getCurrentSession(); 
			UserDTOSQL userDTO = userMapper.toUserDTO(user);
			session.saveOrUpdate(userDTO);
		} catch (HibernateException e) {
			throw new QueryException();
		}
	}
	
	public void delete(int idUser) throws QueryException {
		try {
			Session session = sessionFactory.getCurrentSession(); 
			Query<?> query=session.createQuery(DELETE_ONE);
			query.setParameter("id", Integer.toString(idUser));
			query.executeUpdate();
		} catch (HibernateException e) {
			throw new QueryException();
		}
	}
}

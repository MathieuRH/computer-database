package com.excilys.cdb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.excilys.cdb.dao.UserDAO;
import com.excilys.cdb.dto.UserDetailsImpl;
import com.excilys.cdb.model.User;

public class UserDetailServiceImpl implements UserDetailsService{
	
	@Autowired
    private UserDAO userDAO;
    
	private static Logger logger = LoggerFactory.getLogger(UserDetailServiceImpl.class);
    
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			User user = userDAO.getUserByUsername(username);
	        logger.info("loadUserByUsername() : {}", username);
	        return new UserDetailsImpl(user);
		} catch (UsernameNotFoundException e) {
			throw new UsernameNotFoundException("User "+e.getMessage()+" not found.");
		}
    }

}

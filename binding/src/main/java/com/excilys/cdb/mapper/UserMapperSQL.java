package com.excilys.cdb.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.UserDTOSQL;
import com.excilys.cdb.dto.UserDTOSQL.UserDTOBuilder;
import com.excilys.cdb.model.User;
import com.excilys.cdb.model.User.UserBuilder;

@Component
public class UserMapperSQL {

	public User toUser(UserDTOSQL userDTO) {
		int id = Integer.parseInt(userDTO.getId());
		String username = userDTO.getUsername();
		UserBuilder builder = new User.UserBuilder(id, username);
		if (! "".equals(userDTO.getPassword())) {
			builder.password(userDTO.getPassword());
		}
		if (! "".equals(userDTO.getEmail())) {
			builder.email(userDTO.getEmail());
		}
		if (! "".equals(userDTO.getRole())) {
			builder.role(userDTO.getRole());
		}
		return builder.build();
	}

	public UserDTOSQL toUserDTO(User user) {
		String id = Integer.toString(user.getId());
		String username = user.getUsername();
		UserDTOBuilder builder = new UserDTOSQL.UserDTOBuilder(id, username);
		if (! "".equals(user.getPassword())) {
			builder.password(user.getPassword());
		}
		if (! "".equals(user.getEmail())) {
			builder.email(user.getEmail());
		}
		if (! "".equals(user.getRole())) {
			builder.role(user.getRole());
		}
		return builder.build();
	}
	
	public ArrayList<User> toListUsers(List<UserDTOSQL> listUsersDTO) {
		ArrayList<User>  listUsers= new ArrayList<User>();
		listUsers = (ArrayList<User>) listUsersDTO.stream()
				.map(u -> toUser(u))
				.collect(Collectors.toList());
		
		return listUsers;
	}

	public ArrayList<UserDTOSQL> listToDTO(List<User> listUsers) {
		ArrayList<UserDTOSQL>  listUsersDTO= new ArrayList<UserDTOSQL>();
		listUsersDTO = (ArrayList<UserDTOSQL>) listUsers.stream()
				.map(u -> toUserDTO(u))
				.collect(Collectors.toList());
		
		return listUsersDTO;
	}

}

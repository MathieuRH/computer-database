package com.excilys.cdb.mapper;

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
			builder.password(userDTO.getEmail());
		}
		if (! "".equals(userDTO.getRole())) {
			builder.password(userDTO.getRole());
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
			builder.password(user.getEmail());
		}
		if (! "".equals(user.getRole())) {
			builder.password(user.getRole());
		}
		return builder.build();
	}

}

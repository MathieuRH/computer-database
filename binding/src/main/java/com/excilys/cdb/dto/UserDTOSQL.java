package com.excilys.cdb.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_table")
public class UserDTOSQL {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;
	private String username;
	private String password;
	private String email;
	private String role;
	
	private UserDTOSQL(UserDTOBuilder builder) {
		this.id = builder.id;
		this.username = builder.username;
		this.password = builder.password;
		this.email = builder.email;
		this.role = builder.role;
	}
	
	@Override
	public String toString() {
		String res = "User n°" + id +
				" : {" + username +
				", password: " + password +
				", email: " + email +
				", role: " + role + "}";
		return res;
	}
	
	public static class UserDTOBuilder{
		private String id;
		private String username;
		private String password;
		private String email;
		private String role;
		
		public UserDTOBuilder(String id, String username) {
			this.id = id;
			this.username = username;
		}
		public UserDTOBuilder(String username) {
			this.username = username;
		}
		public UserDTOBuilder id(String id) {
			this.id = id;
			return this;
		}
		public UserDTOBuilder password(String password) {
			this.password = password;
			return this;
		}
		public UserDTOBuilder email(String email) {
			this.email = email;
			return this;
		}
		public UserDTOBuilder role(String role) {
			this.role = role;
			return this;
		}
		
		public UserDTOSQL build() {
			return new UserDTOSQL(this);
		}
	}

	
	public void setId(String id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public String getRole() {
		return role;
	}
}

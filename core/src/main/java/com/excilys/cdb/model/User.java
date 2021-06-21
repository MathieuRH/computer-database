package com.excilys.cdb.model;

public class User {
	
	private final int id;
	private final String username;
	private final String password;
	private final String email;
	private final String role;
	
	private User(UserBuilder builder) {
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
	
	public static class UserBuilder{
		private int id;
		private String username;
		private String password;
		private String email;
		private String role;
		
		public UserBuilder(int id, String username) {
			this.id = id;
			this.username = username;
		}
		public UserBuilder(String username) {
			this.username = username;
		}
		public UserBuilder id(int id) {
			this.id = id;
			return this;
		}
		public UserBuilder password(String password) {
			this.password = password;
			return this;
		}
		public UserBuilder email(String email) {
			this.email = email;
			return this;
		}
		public UserBuilder role(String role) {
			this.role = role;
			return this;
		}
		
		public User build() {
			return new User(this);
		}
	}

	public int getId() {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + id;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id != other.id)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
	
}
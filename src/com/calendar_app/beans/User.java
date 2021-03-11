package com.calendar_app.beans;

import java.util.UUID;

public class User {
	
	public User() {};
	public User(String username, String password) {
		this.username = username;
		this.password = password;
		this.id = UUID.randomUUID();
	}
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	private UUID id;
	private String username;
	private String password;
}

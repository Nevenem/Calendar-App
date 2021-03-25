package com.calendar_app.beans;

import java.util.UUID;

public class User {

	public User() {
	};

	public User(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.id = UUID.randomUUID();
		this.validationCode = UUID.randomUUID();
		this.active = 0;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public UUID getValidationCode() {
		return validationCode;
	}

	public void setValidationCode(UUID validationCode) {
		this.validationCode = validationCode;
	}

	private UUID id;
	private String username;
	private String password;
	private String email;
	private int active;
	private UUID validationCode;

}

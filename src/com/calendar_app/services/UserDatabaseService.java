package com.calendar_app.services;

import java.util.List;

import com.calendar_app.beans.User;

public interface UserDatabaseService {
	
	public User readUser(String username);
	public void createUser(User User);
	public void updateUser(User User);
	public void deleteUser(User User);
	public List<User> readAllUsers();

}

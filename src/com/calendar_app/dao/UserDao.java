package com.calendar_app.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;

import com.calendar_app.beans.User;
import com.calendar_app.services.UserDatabaseService;

public class UserDao implements UserDatabaseService {

	private Connection conn;

	public UserDao() {
		conn = DBConnection.getConnectionToDatabase();

	}

	public User readUser (UUID userId) {
		User user = new User();
		try {
			Statement stm = conn.createStatement();
			String statement = String.format("SELECT * FROM User WHERE (uuid = '%s'); ", userId);
			ResultSet rs = stm.executeQuery(statement);
			
			if (rs.next() == false) {
				return null;
			} else {
				do {
					System.out.println("One result");

					user.setId(UUID.fromString(rs.getString("uuid")));
					user.setUsername(rs.getString("username"));
					user.setPassword(rs.getString("password"));
				} while (rs.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("Dao reading user " + user.getUsername() + ":" + user.getPassword());
		return user;	
	}
	
	
	@Override
	public User readUser(String username) {
		User user = new User();
		try {
			Statement stm = conn.createStatement();
			String statement = String.format("SELECT * FROM User WHERE (username = '%s'); ", username);
			ResultSet rs = stm.executeQuery(statement);
			
			if (rs.next() == false) {
				return null;
			} else {
				do {
					user.setId(UUID.fromString(rs.getString("uuid")));
					user.setUsername(username);
					user.setPassword(rs.getString("password"));
				}
			while (rs.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("Dao reading user " + user.getUsername() + ":" + user.getPassword());
		return user;
	}

	@Override
	public void createUser(User user) {
		try {
			Statement stm = conn.createStatement();
			String statement = String.format("INSERT INTO User VALUES ('%s', '%s', '%s'); ", user.getId().toString(),
					user.getUsername(), user.getPassword());
			stm.execute(statement);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Dao creating user " + user.getUsername() + ":" + user.getPassword());
	}

	@Override
	public void updateUser(User user) {
		try {
			Statement stm = conn.createStatement();
			String statement = String.format("UPDATE user SET password = '%s' WHERE (uuid = '%s'); ", user.getPassword(), user.getId().toString());
			stm.execute(statement);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Dao updating user " + user.getUsername() + ":" + user.getPassword());
	}

	@Override
	public void deleteUser(User user) {
		try {
			Statement stm = conn.createStatement();
			String statement = String.format("DELETE FROM user WHERE (uuid = '%s'); ", user.getPassword(), user.getId().toString());
			stm.execute(statement);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Dao deleting user " + user.getUsername() + ":" + user.getPassword());

	}

	@Override
	public List<User> readAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

}

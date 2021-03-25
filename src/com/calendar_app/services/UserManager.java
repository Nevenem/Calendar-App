package com.calendar_app.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;

import com.calendar_app.beans.Event;
import com.calendar_app.beans.User;
import com.calendar_app.dao.EventDao;
import com.calendar_app.dao.UserDao;

public class UserManager {
	UserDao userDao;
	EventDao eventDao;

	private static UserManager instance = null;
	private Map<String, User> loggedInUsers;

	private UserManager() {
		userDao = new UserDao();
		eventDao = new EventDao();
		loggedInUsers = new HashMap<>();
	};

	public static UserManager getInstance() {
		if (instance == null) {
			instance = new UserManager();
		}
		return instance;
	}

	public void registerUser(String name, String password, String email) {
		User user = new User(name, password, email);
		userDao.createUser(user);
		sendValidationEmail(user);
	};

	public String loginUser(String name, String password) throws FailedLoginException {
		User user = userDao.readUser(name);
		if ((user != null) && (user.getPassword().equals(password)) && (user.getActive() == 1)) {
			String sessionToken = generateNewSessionToken();
			loggedInUsers.put(sessionToken, user);
			return sessionToken;
		} else {
			throw new FailedLoginException();
		}
	};
	
	public String loginUserWithValidationCode(UUID validationCode) throws FailedLoginException, AccountNotFoundException {
		User user = userDao.readUserWithValidaitonCode(validationCode);
		if (user != null) {
			return loginUser(user.getUsername(), user.getPassword());
		} else {
			throw new AccountNotFoundException();
		}
	}

	private String generateNewSessionToken() {
		return UUID.randomUUID().toString();
	};

	public void logOutUser(String token) {
		loggedInUsers.remove(token);
	};

	public User getLoggedInUser(String token) throws LoginException {
		User user = loggedInUsers.get(token);
		if (user == null) {
			throw new LoginException();
		}
		return user;
	};

	public void deleteUser(String token) throws LoginException {
		User user = getLoggedInUser(token);
		List<Event> events = eventDao.readAllEvents(user);

		for (Event event : events) {
			eventDao.deleteEvent(event);
		}
		userDao.deleteUser(user);
		logOutUser(token);
	}

	public void sendValidationEmail(User user) {
		System.out.println("Email sent to " + user.getEmail());
		System.out.println(
				"Thank you for registering, please follow the link to activate your account: http://localhost:8080/calendar-app/register.jsp?activate="
						+ user.getValidationCode().toString());
	}

	public void changePassword(User user, String newPassword) {
		user.setPassword(newPassword);
		userDao.updateUser(user);
	}

	public void validate(UUID validationCode) throws AccountNotFoundException {
		User user = userDao.readUserWithValidaitonCode(validationCode);

		if (user != null) {
			user.setActive(1);
			userDao.updateUser(user);
		} else {
			throw new AccountNotFoundException();
		}

	}

	public void recoverPassword(String email) {
		User user = userDao.readUserByEmail(email);
		UUID recoveryCode = UUID.randomUUID();
		user.setValidationCode(recoveryCode);
		userDao.updateUser(user);
		System.out.println("Email sent to " + user.getEmail());
		System.out.println(
				"Please follow the link to reset your password: http://localhost:8080/calendar-app/passwordRecovery.jsp?recover="
						+ user.getValidationCode().toString());
	}
}

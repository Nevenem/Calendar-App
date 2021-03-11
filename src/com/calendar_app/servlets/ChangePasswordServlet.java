package com.calendar_app.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.calendar_app.beans.Event;
import com.calendar_app.beans.User;
import com.calendar_app.dao.DBConnection;
import com.calendar_app.dao.EventDao;
import com.calendar_app.dao.UserDao;

//ChangePasswordtServlet.java
@WebServlet(name = "ChangePasswordServlet", description = "ChangePassword Servlet", urlPatterns = { "/ChangePasswordServlet" })
public class ChangePasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao;

	public ChangePasswordServlet() {
		super();
	}

	@Override
	public void init() throws ServletException {
		super.init();
		userDao = new UserDao();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		PrintWriter writer = res.getWriter();

		HttpSession session = req.getSession(false);

		if (session == null) {
			writer.write("You are not logged in - no events");
		} else {
			String username = (String) session.getAttribute("name");
			User user = userDao.readUser(username);
			writer.write(drawChangePasswordForm(username));
		}
	}

	private String drawChangePasswordForm(String username) {
		String form = "<h3>Change password:</h3>" 
				+ "<form method = \"post\" action=\"ChangePasswordServlet\">\n"
				+ "<p>Username: " + username + "</p>"
				+ "<label for=\"password\">Password:</label>" 
			    + "<input type=\"text\" id=\"password\" name=\"password\">" 
				+ "<input type=\"submit\" value=\"Submit\">\n"
				+ "</form>";
		return form;
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession(true);

		String name = (String) session.getAttribute("name");
		String newPassword = req.getParameter("password");
		
		User user = userDao.readUser(name);
		user.setPassword(newPassword);
		userDao.updateUser(user);
		
		PrintWriter writer = res.getWriter();
		writer.write("Password changed to " + newPassword);
	}

}

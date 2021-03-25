package com.calendar_app.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.security.auth.login.LoginException;
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
import com.calendar_app.services.UserManager;

//ChangePasswordtServlet.java
@WebServlet(name = "ChangePasswordServlet", description = "ChangePassword Servlet", urlPatterns = {
		"/ChangePasswordServlet" })
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

		HttpSession session = req.getSession(true);

		try {
			User user = UserManager.getInstance().getLoggedInUser((String) session.getAttribute("sessionToken"));
			writer.write(drawChangePasswordForm(user.getUsername()));
		} catch (LoginException e) {
			writer.write("You are not logged in!");
		}
	}

	private String drawChangePasswordForm(String username) {
		String form = "<h3>Change password:</h3>" + "<form method = \"post\" action=\"ChangePasswordServlet\">\n"
				+ "<p>Username: " + username + "</p>" + "<label for=\"password\">Password:</label>"
				+ "<input type=\"text\" id=\"password\" name=\"password\">"
				+ "<input type=\"submit\" value=\"Submit\">\n" + "</form>";
		return form;
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession(true);
		PrintWriter writer = res.getWriter();

		try {
			User user = UserManager.getInstance().getLoggedInUser((String) session.getAttribute("sessionToken"));
			String newPassword = req.getParameter("password");
			UserManager.getInstance().changePassword(user, newPassword);
			res.sendRedirect("/calendar-app/events.jsp");
		} catch (LoginException e) {
			writer.write("You are not logged in!");
		}
	}

}

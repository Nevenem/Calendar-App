package com.calendar_app.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.calendar_app.beans.User;
import com.calendar_app.dao.DBConnection;
import com.calendar_app.dao.EventDao;
import com.calendar_app.dao.UserDao;
import com.calendar_app.exceptions.LoginFailedException;
import com.calendar_app.services.UserManager;

//LogintServlet.java
@WebServlet(name = "LoginServlet", description = "Login Servlet", urlPatterns = { "/LoginServlet" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao;
	private EventDao eventDao;

	public LoginServlet() {
		super();
	}

	@Override
	public void init() throws ServletException {
		super.init();
		userDao = new UserDao();
		eventDao = new EventDao();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		PrintWriter writer = res.getWriter();

		HttpSession session = req.getSession(true);

		String sessionToken = (String) session.getAttribute("sessionToken");
		User user;
		try {
			user = UserManager.getInstance().getLoggedInUser(sessionToken);
			writer.write("Welcome back " + (String) user.getUsername() + "<br>");
			writer.write("<a href=\"events.jsp\">Go to events</a>");
		} catch (LoginException e) {
			writer.write(drawEmptyForm());

		}

	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		HttpSession session = req.getSession(true);

		String name = req.getParameter("name");
		String password = req.getParameter("password");

		PrintWriter writer = res.getWriter();

		try {
			String sessionToken = UserManager.getInstance().loginUser(name, password);
			session.setAttribute("sessionToken", sessionToken);
			res.sendRedirect("/calendar-app/events.jsp");

		} catch (FailedLoginException e) {
			writer.write("Invalid username or password! Try again!");
			res.sendRedirect("/calendar-app/login.jsp?status=failed");
		}
	}

	private String drawEmptyForm() {
		String form = "<form method = \"post\" action=\"LoginServlet\">\r\n" + "<h3>Log in</h3>"
				+ "<input type=\"text\" placeholder=\"Name\" name=\"name\"><br><br>\r\n"
				+ "<input type=\"text\" placeholder=\"Password\" name=\"password\"><br><br>\r\n"
				+ "<input type=\"submit\" value=\"submit\"><br>\r\n" + "</form><br>"
				+ "<a href='/calendar-app/passwordRecovery.jsp'>Forgot your password?</a>";
		return form;
	}
}

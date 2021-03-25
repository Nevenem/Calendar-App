package com.calendar_app.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.LoginException;
import javax.servlet.RequestDispatcher;
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
import com.calendar_app.services.UserManager;

@WebServlet(name = "RegistrationServlet", description = "Registration Servlet", urlPatterns = {
		"/RegistrationServlet" })
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao;

	public RegistrationServlet() {
		super();
	}

	@Override
	public void init() throws ServletException {
		super.init();

		// get connection to database
		userDao = new UserDao();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		PrintWriter writer = res.getWriter();
		HttpSession session = req.getSession(true);

		if (req.getParameter("activate") != null) {
			try {
				UUID validationCode = UUID.fromString((String) req.getParameter("activate"));
				UserManager.getInstance().validate(validationCode);
				writer.write("<i>Activation successful!</i>");
			} catch (AccountNotFoundException e) {
				writer.write("<i>Activation failed!</i>");
			} catch (IllegalArgumentException e) {
				writer.write("<i>Bad UUID string!</i>");
			}
			writer.write("<br><a href='/calendar-app/login.jsp'>login</a>");
			return;
		} else {
			try {
				UserManager.getInstance().getLoggedInUser((String) session.getAttribute("sessionToken"));
				writer.write(drawSignoutPage(session));
			} catch (LoginException e) {
				writer.write(drawRegistrationForm());
			}
		}
	}

	private String drawSignoutPage(HttpSession session) {
		// TODO Auto-generated method stub
		return "<a href=\"SignoutServlet\">Sign out</a>";
	}

	private String drawRegistrationForm() {
		String form = "<form method = \"post\" action=\"RegistrationServlet\">\r\n" + "<h3>Registration</h3>"
				+ "<input type=\"text\" placeholder=\"Name\" name=\"name\"><br><br>\r\n"
				+ "<input type=\"text\" placeholder=\"Email\" name=\"email\"><br><br>\r\n"
				+ "<input type=\"text\" placeholder=\"Password\" name=\"password\"><br><br>\r\n"
				+ "<input type=\"submit\" value=\"submit\"><br>\r\n" + "</form>";
		return form;
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		HttpSession session = req.getSession(true);

		String name = req.getParameter("name");
		String password = req.getParameter("password");
		String email = req.getParameter("email");

		UserManager.getInstance().registerUser(name, password, email);

		PrintWriter writer = res.getWriter();
		writer.write("Thanks for registering " + name + ". Validation email sent!");
		res.sendRedirect("/calendar-app/login.jsp");
	}
}

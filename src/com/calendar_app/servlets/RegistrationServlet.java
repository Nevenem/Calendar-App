package com.calendar_app.servlets;

import java.io.IOException;
import java.io.PrintWriter;

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

@WebServlet(name = "RegistrationServlet", description = "Registration Servlet", urlPatterns = { "/RegistrationServlet" })
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
		
		if (session.getAttribute("loggedIn") == null) {
			writer.write(drawRegistrationForm());
		} else {
			writer.write(drawSignoutPage(session));
		}
	}
	
	private String drawSignoutPage(HttpSession session) {
		// TODO Auto-generated method stub
		return "<a href=\"SignoutServlet\">Sign out</a>";
	}

	private String drawRegistrationForm() {
		String form = "<form method = \"post\" action=\"RegistrationServlet\">\r\n"
				+"<h3>Registration</h3>"
				+ "<input type=\"text\" placeholder=\"Name\" name=\"name\"><br><br>\r\n"
				+ "<input type=\"text\" placeholder=\"Password\" name=\"password\"><br><br>\r\n"
				+ "<input type=\"submit\" value=\"submit\"><br>\r\n"
				+ "</form>";
		return form;
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		HttpSession session = req.getSession(true);

		String name = req.getParameter("name");
		String password = req.getParameter("password");
		session.setAttribute("name", name);
		session.setAttribute("password", password);
		
		userDao.createUser(new User(name, password));
		
		PrintWriter writer = res.getWriter();
		writer.write("Welcome " + (String) session.getAttribute("name"));
		res.sendRedirect("/calendar-app/login.jsp");
	}
	
}


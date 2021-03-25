package com.calendar_app.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

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

//CreateEventtServlet.java
@WebServlet(name = "CreateEventServlet", description = "CreateEvent Servlet", urlPatterns = { "/CreateEventServlet" })
public class CreateEventServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EventDao eventDao;
	private UserDao userDao;

	public CreateEventServlet() {
		super();
	}

	@Override
	public void init() throws ServletException {
		super.init();
		eventDao = new EventDao();
		userDao = new UserDao();
	}

	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		PrintWriter writer = res.getWriter();
		HttpSession session = req.getSession(false);
	
		if (session == null) {
			writer.write("You are not logged in - no events");
		} else {			
			writer.write(drawEventForm());
			
		}
	}
	
	private String drawEventForm() {
		String form =
				"<form method = \"post\" action=\"CreateEventServlet\">\n"
				+ "<label for=\"date\">Date:</label>" 
			    + "<input type=\"date\" id=\"date\" name=\"date\">"
			    + "<br>"
			    + "<label for=\"title\">Title:</label>" 
			    + "<input type=\"text\" id=\"title\" name=\"title\">"
			    + "<br>"
			    + "<label for=\"description\">Description:</label>" 
			    + "<input type=\"text\" id=\"description\" name=\"description\">"
			    + "<br>"
				+ "<input type=\"submit\" value=\"Submit\">\n"
				+ "</form>";
		return form;
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		PrintWriter writer = res.getWriter();
		HttpSession session = req.getSession(false);
	
			String sessionToken = (String) session.getAttribute("sessionToken");
			User user;
			try {
				user = UserManager.getInstance().getLoggedInUser(sessionToken);
				DateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
				Date date = new Date();
				try {
					date = format.parse(req.getParameter("date"));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				String title = req.getParameter("title");
				String description = req.getParameter("description");
							
				Event event = new Event(user, date, title, description);
				eventDao.createEvent(event);
				res.sendRedirect("/calendar-app/events.jsp");
			} catch (LoginException e1) {
				writer.write("You are not logged in - no events");
			}
			
		}
	}



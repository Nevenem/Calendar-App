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

//UpdateEventtServlet.java
@WebServlet(name = "UpdateEventServlet", description = "UpdateEvent Servlet", urlPatterns = { "/UpdateEventServlet" })
public class UpdateEventServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EventDao eventDao;

	public UpdateEventServlet() {
		super();
	}

	@Override
	public void init() throws ServletException {
		super.init();
		eventDao = new EventDao();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		PrintWriter writer = res.getWriter();
		HttpSession session = req.getSession(false);
	
		if (session == null) {
			writer.write("You are not logged in - cannot update events");
		} else {
			UUID id = UUID.fromString(req.getParameter("eventId"));
			Event event = eventDao.readEvent(id);
			
			writer.write(drawEventForm(event));			
		}
	}
	
	private String drawEventForm(Event event) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String date = formatter.format(event.getDate());
		System.out.println(date);
		String form =
				"<form method = \"post\" action=\"UpdateEventServlet\">\n"
				+ "<input type=\"hidden\" name=\"uuid\" value=\"" + event.getId() + "\">"
				+ "<label for=\"date\">Date:</label>" 
			    + "<input type=\"date\" id=\"date\" name=\"date\" value=\"" + date + "\">"
			    + "<br>"
			    + "<label for=\"title\">Title:</label>" 
			    + "<input type=\"text\" id=\"title\" name=\"title\" value=\"" + event.getTitle() + "\">"
			    + "<br>"
			    + "<label for=\"description\">Description:</label>" 
			    + "<input type=\"text\" id=\"description\" name=\"description\" value=\"" + event.getDescription() + "\">"
			    + "<br>"
				+ "<input type=\"submit\" value=\"Submit\">\n"
				+ "</form>";
		return form;
	}	
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		PrintWriter writer = res.getWriter();
		HttpSession session = req.getSession(false);
	
		if (session == null) {
			writer.write("You are not logged in");
		} else {
			DateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
			Date date = new Date();
			try {
				date = format.parse(req.getParameter("date"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			String title = req.getParameter("title");
			String description = req.getParameter("description");
			
			Event event = eventDao.readEvent(UUID.fromString(req.getParameter("uuid")));
			event.setDate(date);
			event.setTitle(title);
			event.setDescription(description);
			eventDao.updateEvent(event);
			res.sendRedirect("/calendar-app/events.jsp");
		}
	}
	
}


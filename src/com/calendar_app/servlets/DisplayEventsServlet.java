package com.calendar_app.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

//DisplayEventstServlet.java
@WebServlet(name = "DisplayEventsServlet", description = "DisplayEvents Servlet", urlPatterns = {
		"/DisplayEventsServlet" })
public class DisplayEventsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao;
	private EventDao eventDao;

	public DisplayEventsServlet() {
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

		HttpSession session = req.getSession(false);

		String sessionToken = (String) session.getAttribute("sessionToken");
		User user;
		try {
			user = UserManager.getInstance().getLoggedInUser(sessionToken);
			List<Event> events = eventDao.readAllEvents(user);


			String pattern = "MM-dd-yyyy";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			
			writer.write("<table>");
			for (Event event : events) {
						
				String date = simpleDateFormat.format(event.getDate());
				writer.write("<tr>");
				writer.write("<td>" + date + "</td>");
				writer.write("<td>" + event.getTitle() + "</td>");
				writer.write("<td>" + event.getDescription() + "</td>");
				writer.write("<td class=\"small_button\"> <a href=\"deleteEvent.jsp?eventId=" + event.getId() + "\">delete</a></td>");
				writer.write("<td class=\"small_button\"> <a href=\"modifyEvent.jsp?eventId=" + event.getId() + "\">modify</a></td>");
				writer.write("</tr>");
			}
			writer.write("</table>");
		} catch (LoginException e) {
			writer.write("You are not logged in - no events");
		}
	}
}

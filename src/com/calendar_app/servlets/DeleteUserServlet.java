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

//DeleteUsertServlet.java
@WebServlet(name = "DeleteUserServlet", description = "DeleteUser Servlet", urlPatterns = { "/DeleteUserServlet" })
public class DeleteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao;
	private EventDao eventDao;

	public DeleteUserServlet() {
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
	
		if (session == null) {
			writer.write("You are not logged in - cannot remove user!");
		} else {
			String username = (String) session.getAttribute("name");
			User user = userDao.readUser(username);
			List<Event> events = eventDao.readAllEvents(user);
			
			for (Event event : events) {
				eventDao.deleteEvent(event);
			}
			userDao.deleteUser(user);
			session.invalidate();
		}
	}

}


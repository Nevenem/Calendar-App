package com.calendar_app.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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

//DeleteEventtServlet.java
@WebServlet(name = "DeleteEventServlet", description = "DeleteEvent Servlet", urlPatterns = { "/DeleteEventServlet" })
public class DeleteEventServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EventDao eventDao;

	public DeleteEventServlet() {
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
			writer.write("You are not logged in - no events");
		} else {
			UUID id = UUID.fromString(req.getParameter("eventId"));
			
			Event event = eventDao.readEvent(id);
			eventDao.deleteEvent(event);
			writer.write("<a href=\"events.jsp\">Back to events</a>");
		}
	}

}


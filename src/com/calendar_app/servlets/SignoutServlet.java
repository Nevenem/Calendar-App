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

//SignouttServlet.java
@WebServlet(name = "SignoutServlet", description = "Signout Servlet", urlPatterns = { "/SignoutServlet" })
public class SignoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SignoutServlet() {
		super();
	}

	@Override
	public void init() throws ServletException {
		super.init();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		PrintWriter writer = res.getWriter();

		HttpSession session = req.getSession(true);
		session.invalidate();
		writer.write("<a href=\"login.jsp\">Back to login</a>");
	}
	
}

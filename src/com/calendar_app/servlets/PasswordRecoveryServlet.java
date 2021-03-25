package com.calendar_app.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.FailedLoginException;
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
import com.calendar_app.exceptions.LoginFailedException;
import com.calendar_app.services.UserManager;

//PasswordRecoverytServlet.java
@WebServlet(name = "PasswordRecoveryServlet", description = "PasswordRecovery Servlet", urlPatterns = {
		"/PasswordRecoveryServlet" })
public class PasswordRecoveryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao;

	public PasswordRecoveryServlet() {
		super();
	}

	@Override
	public void init() throws ServletException {
		super.init();
		userDao = new UserDao();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		PrintWriter writer = res.getWriter();
		HttpSession session = req.getSession(false);

		if (req.getParameter("recover") != null) {
			try {
				UUID recoveryCode = UUID.fromString((String) req.getParameter("recover"));
				String sessionToken = UserManager.getInstance().loginUserWithValidationCode(recoveryCode);
				session.setAttribute("sessionToken", sessionToken);
				writer.write("<a href='/calendar-app/changePassword.jsp'>Change password</a>");
			} catch (AccountNotFoundException e) {
				writer.write("Account not found!");

			} catch (FailedLoginException e) {
				writer.write("Failed login!");

			} catch (IllegalArgumentException e) {
				writer.write("Bad UUID!");
			}

		} else {
			writer.write(drawPasswordRecoveryForm());
		}
	}

	private String drawPasswordRecoveryForm() {
		String form = "<h3>Recover password:</h3>" + "<form method = \"post\" action=\"PasswordRecoveryServlet\">\n"
				+ "<label for=\"email\">Email:</label>" + "<input type=\"text\" id=\"email\" name=\"email\">"
				+ "<input type=\"submit\" value=\"Submit\">\n" + "</form>";
		return form;
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession(true);

		String email = req.getParameter("email");
		UserManager.getInstance().recoverPassword(email);
		res.sendRedirect("/calendar-app/login.jsp");
	}

}

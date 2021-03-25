package com.calendar_app.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

// DBConnection.java
// This class creates a connection with the database 
public class DBConnection {

	private static final String PASSWORD = "2prdavca";
	private static final String USERNAME = "root";
	private static final String CONN_STRING = "jdbc:mysql://localhost/oop_course_schema";

	public static Connection getConnectionToDatabase() {

		// register the driver`
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// open the database connection and create a table
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
			System.out.println("Connected!");
			Statement stm = conn.createStatement();
			stm.execute("CREATE TABLE IF NOT EXISTS `user` (\n"
					+ "  `uuid` char(40) COLLATE utf8_unicode_ci NOT NULL,\n"
					+ "  `username` char(128) COLLATE utf8_unicode_ci DEFAULT NULL,\n"
					+ "  `password` char(128) COLLATE utf8_unicode_ci DEFAULT NULL,\n"
					+ "  `email` char(128) COLLATE utf8_unicode_ci DEFAULT NULL,\n"
					+ "  `validationCode` char(128) COLLATE utf8_unicode_ci DEFAULT NULL,\n"
					+ "  `active` TINYINT DEFAULT 0,\n"
					+ "   PRIMARY KEY (`uuid`)\n" + ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;");
			System.out.println("Table User created");
			stm.execute(
					"CREATE TABLE IF NOT EXISTS `event` (\n" + "  `uuid` char(40) COLLATE utf8_unicode_ci NOT NULL,\n"
							+ "  `title` char(128) COLLATE utf8_unicode_ci DEFAULT NULL,\n"
							+ "  `description` text COLLATE utf8_unicode_ci,\n"
							+ "  `date` timestamp DEFAULT NULL,\n" 
							+ "  `userId` char(40) COLLATE utf8_unicode_ci NOT NULL, \n" 
							+ "  FOREIGN KEY (`userId`) REFERENCES User(`uuid`), \n"
							+ "  PRIMARY KEY (`uuid`)\n"
							+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;");
			System.out.println("Table Event created");
			} catch (SQLException e) {
			System.out.println("An error occurred while connecting to the MySQL database");
			e.printStackTrace();
		}

		return conn;

	}

}

package com.calendar_app.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.calendar_app.beans.Event;
import com.calendar_app.beans.User;
import com.calendar_app.services.EventDatabaseService;

public class EventDao implements EventDatabaseService {

	private Connection conn;

	public EventDao() {
		conn = DBConnection.getConnectionToDatabase();
	}

	@Override
	public Event readEvent(UUID id) {
		Event event = new Event();
		try {
			Statement stm = conn.createStatement();
			String statement = String.format("SELECT * FROM event WHERE (uuid = '%s'); ", id.toString());
			ResultSet rs = stm.executeQuery(statement);

			while (rs.next()) {
				event.setId(UUID.fromString(rs.getString("uuid")));
				event.setTitle(rs.getString("title"));
				event.setDescription(rs.getString("description"));
				event.setDate(rs.getTimestamp("date"));
				UserDao userDao = new UserDao();
				event.setOwner(userDao.readUser(UUID.fromString(rs.getString("userId"))));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("Dao reading event " + event.getTitle() + ":" + event.getDescription());
		return event;
	}

	@Override
	public void createEvent(Event event) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
		String date = formatter.format(event.getDate());
		try {
			Statement stm = conn.createStatement();
			String statement = String.format("INSERT INTO Event VALUES ('%s', '%s', '%s', '%s', '%s'); ",
					event.getId().toString(), event.getTitle(), event.getDescription(), date,
					event.getOwner().getId());

			stm.execute(statement);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Dao creating event " + event.getTitle() + ":" + event.getDescription());
	}

	@Override
	public void updateEvent(Event event) {
		try {
			Statement stm = conn.createStatement();
			String statement = String.format("UPDATE event SET title = '%s' WHERE (uuid = '%s'); ", event.getTitle(), event.getId().toString());
			stm.execute(statement);

			statement = String.format("UPDATE event SET description = '%s' WHERE (uuid = '%s'); ", event.getDescription(), event.getId().toString());
			stm.execute(statement);

			statement = String.format("UPDATE event SET date = '%s' WHERE (uuid = '%s'); ", event.getDate(), event.getId().toString());
			stm.execute(statement);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Dao updating event " + event.getTitle() + ":" + event.getDescription());

	}

	@Override
	public void deleteEvent(Event event) {
		try {
			Statement stm = conn.createStatement();
			String statement = String.format("DELETE FROM event WHERE (uuid = '%s'); ", event.getId().toString());
			stm.execute(statement);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Dao deleting event " + event.getTitle() + ":" + event.getDescription());
	}

	@Override
	public List<Event> readAllEvents(User user) {
		List<Event> events = new ArrayList<Event>();
		
		try {
			Statement stm = conn.createStatement();
			String statement = String.format("SELECT * FROM event WHERE (userId = '%s'); ", user.getId().toString());
			ResultSet rs = stm.executeQuery(statement);

			while (rs.next()) {
				Event event = new Event();
				event.setId(UUID.fromString(rs.getString("uuid")));
				event.setTitle(rs.getString("title"));
				event.setDescription(rs.getString("description"));
				event.setDate(rs.getTimestamp("date"));
				UserDao userDao = new UserDao();
				event.setOwner(userDao.readUser(UUID.fromString(rs.getString("userId"))));
				
				events.add(event);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.format("Dao reading all %d events ", events.size());
		return events;
	}

}

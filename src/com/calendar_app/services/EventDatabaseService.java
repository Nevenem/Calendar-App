package com.calendar_app.services;

import java.util.List;
import java.util.UUID;

import com.calendar_app.beans.Event;
import com.calendar_app.beans.User;

public interface EventDatabaseService {
	
	public Event readEvent(UUID id);
	public void createEvent(Event event);
	public void updateEvent(Event event);
	public void deleteEvent(Event event);
	public List<Event> readAllEvents(User user);

}

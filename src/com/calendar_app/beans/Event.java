package com.calendar_app.beans;

import java.util.Date;
import java.util.UUID;

public class Event {
	
	public Event() {};
	public Event(User owner, Date date, String title, String description) {
		this.id = UUID.randomUUID();
		this.owner = owner;
		this.date = date;
		this.title = title;
		this.description = description;	
	}
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	private UUID id;
	private User owner;
	private Date date;
	private String title;
	private String description;
}

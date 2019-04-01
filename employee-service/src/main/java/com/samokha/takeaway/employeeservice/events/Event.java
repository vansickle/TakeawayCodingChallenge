package com.samokha.takeaway.employeeservice.events;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.io.Serializable;
import java.util.UUID;

/**
 * Generic Event class
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY) //since no setters for fields
public class Event{

	/**
	 * Aggregate root entity id
	 */
	private final UUID id;

	/**
	 * Event type
	 * e.g. EMPLOYEE_CREATED
	 */
	private final String eventType;

	/**
	 * Event payload
	 * JSON-serialized
	 *
	 * e.g. serialized Employee object
	 */
	private final String body;

	public Event(UUID id, String eventType, String body) {
		this.id = id;
		this.eventType = eventType;
		this.body = body;
	}

	public UUID getId() {
		return id;
	}

	public String getEventType() {
		return eventType;
	}

	public String getBody() {
		return body;
	}
}

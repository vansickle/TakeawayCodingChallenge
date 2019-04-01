package com.samokha.takeaway.eventservice.model;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;
import java.util.UUID;

@Table(value="event")
public class Event {
	/**
	 * Aggregate root entity entityId
	 */
	@PrimaryKeyColumn(name="entity_id", type = PrimaryKeyType.PARTITIONED)
	private UUID entityId;


	@PrimaryKeyColumn(name="event_time", type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
	private Date eventTime;

	/**
	 * Event type
	 * e.g. EMPLOYEE_CREATED
	 */
	@Column("event_type")
	private String eventType;


	/**
	 * Event payload
	 * JSON-serialized
	 *
	 * e.g. serialized Employee object
	 */

	@Column("body")
	private String body;

	public Event(UUID entityId, String eventType, String body) {
		this.entityId = entityId;
		this.eventType = eventType;
		this.body = body;
	}

	public UUID getEntityId() {
		return entityId;
	}

	public String getEventType() {
		return eventType;
	}

	public String getBody() {
		return body;
	}

	public void setEntityId(UUID entityId) {
		this.entityId = entityId;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Date getEventTime() {
		return eventTime;
	}

	public void setEventTime(Date eventTime) {
		this.eventTime = eventTime;
	}
}

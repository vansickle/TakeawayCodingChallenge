package com.samokha.takeaway.employeeservice.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.samokha.takeaway.employeeservice.domain.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Factory to produce entity-related events
 */
@Component
public class EventFactory {

	private static final Logger log = LoggerFactory.getLogger(EventFactory.class);

	private ObjectMapper objectMapper;

	public EventFactory(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	public Event createEvent(UUID id, EventType eventType, Object entity) {

		String body = "";

		//in case of CREATE and UPDATE save full snapshot of employee in JSON
		if(entity != null)
		{
			try {
				//does have some overhead, e.g. saving id both in body and id field
				body = objectMapper.writeValueAsString(entity);
			} catch (JsonProcessingException e) {
				log.error("Can't build EmployeeEvent body", e);
			}
		}

		return new Event(id, eventType.toString(), body);
	}
}

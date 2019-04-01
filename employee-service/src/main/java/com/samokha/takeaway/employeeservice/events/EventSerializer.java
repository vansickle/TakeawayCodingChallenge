package com.samokha.takeaway.employeeservice.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Serializes events to string in JSON format
 * to put as a payload to AMQP message
 */
@Component
public class EventSerializer {

	private static final Logger log = LoggerFactory.getLogger(EventFactory.class);

	private final ObjectMapper objectMapper;

	public EventSerializer(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	public String serialize(Event event) {
		try {
			return objectMapper.writeValueAsString(event);
		} catch (JsonProcessingException e) {
			log.error("Can't serialize event", e);
		}

		//TODO consider how to handle case when we can't serialize and send event
		return "";
	}
}

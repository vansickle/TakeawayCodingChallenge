package com.samokha.takeaway.eventservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samokha.takeaway.eventservice.dal.EventRepository;
import com.samokha.takeaway.eventservice.model.Event;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
public class EventReceiver {

	private final ObjectMapper objectMapper;
	private final EventRepository eventRepository;

	public EventReceiver(ObjectMapper objectMapper, EventRepository eventRepository) {
		this.objectMapper = objectMapper;
		this.eventRepository = eventRepository;
	}

	public void receiveMessage(String message) throws IOException {

		System.out.println("Received <" + message + ">");

		Event event = objectMapper.readValue(message, Event.class);

		//TODO should time be set here?
		event.setEventTime(new Date());

		eventRepository.insert(event);
	}
}

package com.samokha.takeaway.employeeservice.events;

import com.samokha.takeaway.employeeservice.EmployeeServiceApplication;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Encapsulates operations with events
 */

@Component
public class EventBus {

	private final EventFactory eventFactory;
	private final RabbitTemplate rabbitTemplate;
	private final EventSerializer serializer;

	public EventBus(EventFactory eventFactory, RabbitTemplate rabbitTemplate, EventSerializer serializer) {
		this.eventFactory = eventFactory;
		this.rabbitTemplate = rabbitTemplate;
		this.serializer = serializer;
	}


	public void send(UUID id, EventType eventType, Object entity) {

		Event event = eventFactory.createEvent(id, eventType, entity);

		rabbitTemplate.convertAndSend(EmployeeServiceApplication.topicExchangeName,
				"events.all",
				serializer.serialize(event));
	}
}

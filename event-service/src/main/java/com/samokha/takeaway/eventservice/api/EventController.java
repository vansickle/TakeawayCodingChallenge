package com.samokha.takeaway.eventservice.api;

import com.samokha.takeaway.eventservice.dal.EventRepository;
import com.samokha.takeaway.eventservice.model.Event;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.cassandra.core.mapping.BasicMapId;
import org.springframework.data.cassandra.core.mapping.MapId;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${api.base.path}/events")
public class EventController {

	private final EventRepository repository;

	public EventController(EventRepository repository) {
		this.repository = repository;
	}

	/**
	 * Lists repository content
	 * @return
	 */
	@GetMapping("/{id}")
	@ApiOperation(value = "List events for specified entity id")
	public List<Event> listEvents(@PathVariable UUID id) {
//		System.out.println("id: "+id);
//		MapId id1 = BasicMapId.id("id", id);
		return repository.findByEntityId(id);

//		return repository.findAllById(Arrays.asList(id1));
	}
}

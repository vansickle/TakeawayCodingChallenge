package com.samokha.takeaway.employeeservice.api;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.samokha.takeaway.employeeservice.dal.EmployeeRepository;
import com.samokha.takeaway.employeeservice.domain.Employee;
import com.samokha.takeaway.employeeservice.events.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("${api.base.path}/employees")
public class EmployeeController {

	private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

	private final EmployeeRepository employeeRepository;
	private final ObjectMapper objectMapper;
	private final RabbitTemplate rabbitTemplate;
	private final EventFactory employeeEventFactory;
	private final EventSerializer eventSerializer;
	private final EventBus eventBus;

	public EmployeeController(EmployeeRepository employeeRepository,
							  ObjectMapper objectMapper,
							  RabbitTemplate rabbitTemplate,
							  EventFactory employeeEventFactory,
							  EventSerializer eventSerializer,
							  EventBus eventBus) {
		this.employeeRepository = employeeRepository;
		this.objectMapper = objectMapper;
		this.rabbitTemplate = rabbitTemplate;
		this.employeeEventFactory = employeeEventFactory;
		this.eventSerializer = eventSerializer;
		this.eventBus = eventBus;
	}

	@PostMapping()
	public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee){

		//have to save first since UUID currently generated via Hibernate
		//TODO may be generate UUID in controller?
		Employee saved = employeeRepository.save(employee);

		eventBus.send(saved.getId(), EventType.EMPLOYEE_CREATED, saved);

		return new ResponseEntity<>(saved, HttpStatus.CREATED);

		//TODO constraint violation exceptions should be propagated more user-friendly
	}

	@GetMapping("/{id}")
	public ResponseEntity<Employee> getEmployee(@PathVariable UUID id){

		Optional<Employee> optional = employeeRepository.findById(id);

		if(!optional.isPresent())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		return new ResponseEntity<>(optional.get(), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable UUID id, @RequestBody Employee employee) throws JsonMappingException {

		Optional<Employee> optional = employeeRepository.findById(id);

		if(!optional.isPresent())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		Employee entityToUpdate = optional.get();

		//map new values to existing entity
		employee.setId(id); //handle case when request body doesn't duplicate id
		objectMapper.updateValue(entityToUpdate, employee);

		eventBus.send(entityToUpdate.getId(), EventType.EMPLOYEE_UPDATED, entityToUpdate);

		return new ResponseEntity<>(employeeRepository.save(entityToUpdate), HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Employee> delete(@PathVariable UUID id){

		eventBus.send(id, EventType.EMPLOYEE_DELETED, null);

		employeeRepository.deleteById(id);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}

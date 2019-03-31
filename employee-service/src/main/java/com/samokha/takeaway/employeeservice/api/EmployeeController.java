package com.samokha.takeaway.employeeservice.api;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.samokha.takeaway.employeeservice.dal.EmployeeRepository;
import com.samokha.takeaway.employeeservice.domain.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	private final EmployeeRepository employeeRepository;
	private final ObjectMapper objectMapper;

	public EmployeeController(EmployeeRepository employeeRepository, ObjectMapper objectMapper) {
		this.employeeRepository = employeeRepository;
		this.objectMapper = objectMapper;
	}

	@PostMapping()
	public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee){

		//TODO Send message to EventBus here

		return new ResponseEntity<>(employeeRepository.save(employee), HttpStatus.CREATED);

		//TODO constraint violation exceptions should be propagated more user-friendly
	}

	@GetMapping("/{id}")
	public ResponseEntity<Employee> getEmployee(@PathVariable UUID id){
		Optional<Employee> optional = employeeRepository.findById(id);

		if(!optional.isPresent())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		return new ResponseEntity<>(optional.get(), HttpStatus.OK);
	}

	@PostMapping("/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable UUID id, @RequestBody Employee employee) throws JsonMappingException {

		Optional<Employee> optional = employeeRepository.findById(id);

		if(!optional.isPresent())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		Employee entityToUpdate = optional.get();

		objectMapper.updateValue(entityToUpdate, employee);

		//TODO send message to EventBus

		return new ResponseEntity<>(employeeRepository.save(entityToUpdate), HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Employee> delete(@PathVariable UUID id){

		//TODO send message to EventBus

		employeeRepository.deleteById(id);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}

package com.samokha.takeaway.employeeservice.api;

import static org.assertj.core.api.Assertions.assertThat;

import com.samokha.takeaway.employeeservice.dal.EmployeeRepository;
import com.samokha.takeaway.employeeservice.domain.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;


@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeControllerIntegrationTest {

	@Autowired
	private EmployeeController controller;

	@Test
	public void contexLoads() throws Exception {
		assertThat(controller).isNotNull();
	}

	@Test
	public void whenCreate_shouldReturnEmployeeWithUUID(){
		Employee employee = new Employee();
		employee.setEmail("test@example.com");

		ResponseEntity<Employee> response = controller.createEmployee(employee);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getId()).isNotNull();
	}


	@Test(expected = TransactionSystemException.class)
	//TODO should return proper response instead of throw exception
	public void givenEmployeeWithEmptyEmail_whenCreate_shouldReturnERROR(){
		Employee employee = new Employee();
		employee.setEmail("");

		ResponseEntity<Employee> response = controller.createEmployee(employee);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Test(expected = DataIntegrityViolationException.class)
	//TODO should return proper response instead of throw exception
	public void givenTwiceEmployeeWithTheSameEmail_whenCreate_shouldReturnERROR(){
		Employee employee1 = new Employee();
		employee1.setEmail("nonunique@example.com");

		ResponseEntity<Employee> response1 = controller.createEmployee(employee1);

		assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		Employee employee2 = new Employee();
		employee2.setEmail("nonunique@example.com");

		ResponseEntity<Employee> response2 = controller.createEmployee(employee2);
	}


}
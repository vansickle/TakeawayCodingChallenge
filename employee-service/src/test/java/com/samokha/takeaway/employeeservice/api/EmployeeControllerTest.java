package com.samokha.takeaway.employeeservice.api;

import com.samokha.takeaway.employeeservice.dal.EmployeeRepository;
import com.samokha.takeaway.employeeservice.domain.Employee;
import com.samokha.takeaway.employeeservice.events.EventBus;
import com.samokha.takeaway.employeeservice.events.EventType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeControllerTest {
	@Autowired
	private EmployeeController controller;

	@MockBean
	private EventBus eventBus;

	@MockBean
	private EmployeeRepository employeeRepository;

	@Test
	public void contexLoads() throws Exception {
		assertThat(controller).isNotNull();
	}

	@Test
	public void whenCreate_shouldSaveAndSendEvent(){
		//given
		Employee employee = new Employee();
		employee.setEmail("test@example.com");

		Employee savedEmployee = new Employee();
		savedEmployee.setEmail("test@example.com");
		savedEmployee.setId(UUID.randomUUID());

		given(this.employeeRepository.save(employee)).willReturn(savedEmployee);

		//when
		ResponseEntity<Employee> response = controller.createEmployee(employee);

		//then
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getId()).isNotNull();

		verify(this.employeeRepository, times(1)).save(employee);
		verify(this.eventBus, times(1)).send(savedEmployee.getId(), EventType.EMPLOYEE_CREATED, savedEmployee);
	}
}

package com.samokha.takeaway.employeeservice.dal;

import static org.assertj.core.api.Assertions.assertThat;

import com.samokha.takeaway.employeeservice.domain.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeRepositoryTest {

	@Autowired
	private EmployeeRepository repository;

	@Test
	public void contexLoads() throws Exception {
		assertThat(repository).isNotNull();
	}

	@Test
	public void givenSaveEmployee_whenFindById_returnSaveEmployee(){
		Employee employee = new Employee();
		employee.setEmail("employee-to-find@example.com");
		repository.save(employee);

		assertThat(employee.getId()).isNotNull();

		assertThat(repository.findById(employee.getId())).isPresent();
	}
}
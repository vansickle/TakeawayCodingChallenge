package com.samokha.takeaway.employeeservice.api;

import com.samokha.takeaway.employeeservice.dal.DepartmentRepository;
import com.samokha.takeaway.employeeservice.domain.Department;
import com.samokha.takeaway.employeeservice.domain.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/departments")
public class DepartmentController {
	private final DepartmentRepository departmentRepository;

	public DepartmentController(DepartmentRepository departmentRepository) {
		this.departmentRepository = departmentRepository;
	}


	@PostMapping()
	public ResponseEntity<Department> createDepartment(@RequestBody Department department){

		return new ResponseEntity<>(departmentRepository.save(department), HttpStatus.CREATED);
	}
}

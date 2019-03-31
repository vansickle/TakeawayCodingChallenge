package com.samokha.takeaway.employeeservice.dal;

import com.samokha.takeaway.employeeservice.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
}

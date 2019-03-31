package com.samokha.takeaway.employeeservice.dal;

import com.samokha.takeaway.employeeservice.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}

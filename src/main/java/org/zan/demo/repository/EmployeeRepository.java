package org.zan.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zan.demo.entity.Employee;

import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    Optional<Employee> findByEmployeeId(String employeeId);
}

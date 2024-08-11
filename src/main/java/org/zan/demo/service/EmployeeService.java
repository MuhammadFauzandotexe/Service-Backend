package org.zan.demo.service;

import org.springframework.data.domain.Pageable;
import org.zan.demo.data.GeneralResponse;
import org.zan.demo.data.PageableResponse;
import org.zan.demo.entity.Employee;

import java.util.UUID;

public interface EmployeeService {
    Employee add(Employee employee);
    Employee enroll(String uuid);
    PageableResponse<Employee> getAll(Pageable pageable);
    Boolean deleteById(UUID id);
    Employee editById(Employee employee,UUID id);
    Employee findByEmployeeId(String id);
}

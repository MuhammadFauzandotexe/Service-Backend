package org.zan.demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zan.demo.data.Esp32ResponseDto;
import org.zan.demo.data.GeneralResponse;
import org.zan.demo.data.PageableResponse;
import org.zan.demo.entity.Employee;
import org.zan.demo.service.EmployeeService;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
@CrossOrigin("*")
public class EmployeeController {
    private final EmployeeService employeeService;
    @PostMapping
    public ResponseEntity<?> add(@RequestBody Employee request){
        Employee employee = employeeService.add(request);
        return ResponseEntity.status(HttpStatus.OK).body(
                GeneralResponse.builder()
                        .message("success")
                        .data("success add "+ employee.getFirstname())
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(defaultValue = "1") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize){
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize);
        PageableResponse<Employee> all = employeeService.getAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(all);
    }

    @GetMapping("/enroll/{id}")
    public ResponseEntity<?> enroll(@PathVariable String id){
        Employee enroll = employeeService.enroll(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                Esp32ResponseDto.builder()
                        .data(enroll.getFirstname()+" "+enroll.getLastname())
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable UUID id){
        Boolean b = employeeService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                GeneralResponse.builder()
                        .message("success delete user")
                        .data(null)
                        .build()
        );
    }
    @PutMapping("/{id}")
    private ResponseEntity<?> editById(@PathVariable UUID id, @RequestBody Employee employee){
        Employee employee1 = employeeService.editById(employee, id);
        return  ResponseEntity.status(HttpStatus.OK).body(
                GeneralResponse.builder()
                        .message("success edit data")
                        .data(null)
                        .build()
        );
    }
}

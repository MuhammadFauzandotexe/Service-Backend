package org.zan.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zan.demo.data.PageableResponse;
import org.zan.demo.entity.AttendanceRecord;
import org.zan.demo.service.AttendanceService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/attendance")
@RequiredArgsConstructor
public class AttendanceRecordController {
    private final AttendanceService attendanceService;
    @GetMapping
    public ResponseEntity<?> getAllRecord(
            @RequestParam(defaultValue = "1") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize
    ){
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize);
        PageableResponse<AttendanceRecord> all = attendanceService.getAll(pageable);
    return ResponseEntity.status(HttpStatus.OK).body(all);
    }

    @GetMapping("/report")
    public ResponseEntity<?> getReport(){
        Boolean report = attendanceService.getReport();
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

}

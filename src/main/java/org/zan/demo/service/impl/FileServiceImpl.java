package org.zan.demo.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.zan.demo.data.PageableResponse;
import org.zan.demo.entity.AttendanceRecord;
import org.zan.demo.repository.AttendanceRecordRepository;
import org.zan.demo.repository.EmployeeRepository;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.*;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileServiceImpl {
    private final EmployeeRepository employeeRepository;

    private final AttendanceRecordRepository attendanceRecordRepository;

    public File generateEmployeeReport() throws IOException {
        String header = "Employee Id | Full Name | Email | Division | Job Title \n";
        String filename = "./files/employee.txt";

        // Check if the file exists. If not, create it.
        File file = new File(filename);
        if (!file.exists()) {
            file.createNewFile();
        }

        // Open the file in APPEND mode to avoid overwriting existing content
        FileChannel fileChannel = FileChannel.open(Path.of(filename), StandardOpenOption.TRUNCATE_EXISTING,StandardOpenOption.WRITE);


        // Write the header to the file
        fileChannel.write(ByteBuffer.wrap(header.getBytes()));

        // Iterate through employees and write their details to the file
        employeeRepository.findAll().stream().forEach(empl -> {
            StringBuilder sb = new StringBuilder();
            sb.append(empl.getEmployeeId()).append(" | ")
                    .append(empl.getFirstname().concat(" ").concat(empl.getLastname())).append(" | ")
                    .append(empl.getEmail()).append(" | ")
                    .append(empl.getDivision()).append(" | ")
                    .append(empl.getJobTitle()).append(" | \n");
            try {
                fileChannel.write(ByteBuffer.wrap(sb.toString().getBytes()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        fileChannel.close(); // Close the file channel

        return file;
    }

    public File generateEmployeeAttendance(Integer year,Integer month,Integer day ) throws IOException {
        LocalDateTime star = LocalDateTime.of(year, month, day, 00, 0, 0, 0);
        LocalDateTime end = LocalDateTime.of(year, month, day, 23, 59, 59, 999);
        String header = "Employee Id | Full Name | Division | Take In | Status | Late Time | Take Out | \n";
        String filename = "./files/attendance".concat(String.valueOf(year)).concat(String.valueOf(month)).concat(String.valueOf(day)).concat(".txt");

        // Check if the file exists. If not, create it.
        File file = new File(filename);
        if (!file.exists()) {
            file.createNewFile();
        }

        // Open the file in APPEND mode to avoid overwriting existing content
        FileChannel fileChannel = FileChannel.open(Path.of(filename), StandardOpenOption.TRUNCATE_EXISTING,StandardOpenOption.WRITE);

        // Write the header to the file
        fileChannel.write(ByteBuffer.wrap(header.getBytes()));

        List<AttendanceRecord> all = attendanceRecordRepository.findAll();

        all.stream().forEach(att -> {
            log.info("create {} |  start {}  | end {}",att.getCreatedAt(),star,end);
            if(att.getCreatedAt().isBefore(end) && att.getCreatedAt().isAfter(star)){
                StringBuilder sb = new StringBuilder();
                sb.append(att.getEmployee().getEmployeeId()).append(" | ");
                sb.append(att.getEmployee().getFirstname().concat(" ").concat(att.getEmployee().getLastname())).append(" | ");
                sb.append(att.getEmployee().getDivision()).append(" | ");
                sb.append(att.getTakeInTime()).append(" | ");
                sb.append(att.getAttendanceStatus()).append(" | ");
                sb.append(att.getLateTime()).append(" | ");
                sb.append(att.getTakeOutTime()).append(" | ");
                try {
                    fileChannel.write(ByteBuffer.wrap(sb.toString().getBytes()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        fileChannel.close(); // Close the file channel

        return file;
    }
}
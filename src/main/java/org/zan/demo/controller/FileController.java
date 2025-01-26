package org.zan.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zan.demo.service.impl.FileServiceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/file")
@RequiredArgsConstructor
@CrossOrigin("*")
public class FileController {

    private final FileServiceImpl fileService;

    @GetMapping("/employee")
    public ResponseEntity<InputStreamResource> downloadDatEmployee() throws IOException, InterruptedException {
        File file = fileService.generateEmployeeReport();
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length()) // Penting untuk file besar
                .body(resource);
    }

    @GetMapping("/attendance")
    public ResponseEntity<InputStreamResource> attendance(
            @RequestParam(required = true) Integer year,
            @RequestParam(required = true) Integer month,
            @RequestParam(required = true) Integer day
    ) throws IOException, InterruptedException {

        File file = fileService.generateEmployeeAttendance(year,month,day);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length()) // Penting untuk file besar
                .body(resource);
    }

}

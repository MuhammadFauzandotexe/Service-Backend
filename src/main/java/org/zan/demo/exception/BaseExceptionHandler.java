package org.zan.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.zan.demo.data.Esp32ResponseDto;

@ControllerAdvice
public class BaseExceptionHandler {


    @ExceptionHandler(RuntimeException.class)
    private ResponseEntity generalException(RuntimeException runtimeException){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Esp32ResponseDto.builder()
                        .data(runtimeException.getMessage())
                        .build());
    }

    @ExceptionHandler(AttendanceAlreadyTaken.class)
    public ResponseEntity handleRecordAlreadyTaken(AttendanceAlreadyTaken attendanceAlreadyTaken){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Esp32ResponseDto.builder()
                        .data("record already taken")
                        .build());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleNotFound(NotFoundException attendanceAlreadyTaken){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Esp32ResponseDto.builder()
                        .data("not founs")
                        .build());
    }

    @ExceptionHandler(DuplicateEnrolException.class)
    public ResponseEntity handleNotFound(DuplicateEnrolException duplicateEnrolException){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Esp32ResponseDto.builder()
                        .data("")
                        .build());
    }


}

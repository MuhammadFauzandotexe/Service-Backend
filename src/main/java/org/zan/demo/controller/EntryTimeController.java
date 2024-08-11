package org.zan.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zan.demo.data.GeneralResponse;
import org.zan.demo.data.dto.EntryTimeDto;
import org.zan.demo.entity.EntryTime;
import org.zan.demo.repository.EntryTimeRepository;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@RestController
@RequestMapping("/api/v1/entry-time")
@RequiredArgsConstructor
public class EntryTimeController {
    private final EntryTimeRepository entryTimeRepository;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @PostMapping
    public ResponseEntity addEntryTime(@RequestBody EntryTimeDto entryTimeDto) {
        try {
            Timestamp startEffectiveDate = new Timestamp(DATE_FORMAT.parse(entryTimeDto.getStartEffectiveDate()).getTime());
            Timestamp endEffectiveDate = new Timestamp(DATE_FORMAT.parse(entryTimeDto.getEndEffectiveDate()).getTime());

            EntryTime entryTime = new EntryTime();

            EntryTime save = entryTimeRepository.save(entryTime);
            return ResponseEntity.status(HttpStatus.OK).body(
                    GeneralResponse.builder()
                            .message("success")
                            .data(null)
                            .build()
            );
        } catch (ParseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    GeneralResponse.builder()
                            .message("Invalid date format")
                            .data(null)
                            .build()
            );
        }
    }
}

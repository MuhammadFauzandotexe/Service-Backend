package org.zan.demo.service.impl;

import org.zan.demo.entity.EntryTime;
import org.zan.demo.repository.EntryTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zan.demo.service.EmployeeService;
import org.zan.demo.service.EntryTimeService;

import java.sql.Timestamp;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class EntryTimeServiceImpl implements EntryTimeService {
    private final EntryTimeRepository entryTimeRepository;

    public Optional<EntryTime> getCurrentEffectiveDate() {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        return entryTimeRepository.findCurrentEffectiveDate(currentTimestamp);
    }
}

package org.zan.demo.service;

import org.springframework.data.domain.Pageable;
import org.zan.demo.data.PageableResponse;
import org.zan.demo.entity.AttendanceRecord;

import java.util.UUID;

public interface AttendanceService {
    PageableResponse<AttendanceRecord> getAll(Pageable pageable);
    Boolean getReport();
}

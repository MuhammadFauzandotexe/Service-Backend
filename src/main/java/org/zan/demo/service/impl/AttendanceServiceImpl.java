package org.zan.demo.service.impl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.zan.demo.data.PageableResponse;
import org.zan.demo.entity.AttendanceRecord;
import org.zan.demo.repository.AttendanceRecordRepository;
import org.zan.demo.service.AttendanceService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {
    private final AttendanceRecordRepository attendanceRecordRepository;
    @Override
    public PageableResponse<AttendanceRecord> getAll(Pageable pageable) {
        Page<AttendanceRecord> all = attendanceRecordRepository.findAll(pageable);
        PageableResponse<AttendanceRecord> attendanceRecordPageableResponse = PageableResponse.fromPage(all);
        return  attendanceRecordPageableResponse;
    }

    @Override
    public Boolean getReport() {
        List<AttendanceRecord> all = attendanceRecordRepository.findAll();

            return true;}
}

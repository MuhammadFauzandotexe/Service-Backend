package org.zan.demo.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.zan.demo.data.PageableResponse;
import org.zan.demo.entity.AttendanceRecord;
import org.zan.demo.entity.Employee;
import org.zan.demo.entity.EntryTime;
import org.zan.demo.exception.AttendanceAlreadyTaken;
import org.zan.demo.exception.DuplicateEnrolException;
import org.zan.demo.exception.NotFoundException;
import org.zan.demo.repository.AttendanceRecordRepository;
import org.zan.demo.repository.EmployeeRepository;
import org.zan.demo.repository.EntryTimeRepository;
import org.zan.demo.service.EmployeeService;
import org.zan.demo.service.EntryTimeService;

import java.time.*;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EntryTimeRepository entryTimeRepository;
    private final EntryTimeService entryTimeService;
    private final AttendanceRecordRepository attendanceRecordRepository;

    @Override
    public Employee add(Employee employee) {
        return employeeRepository.saveAndFlush(employee);
    }

    @Override
    public Employee enroll(String uuid) {
        Employee employee = employeeRepository.findByEmployeeId(uuid).orElseThrow(NotFoundException::new);
        EntryTime currentEffectiveDate = entryTimeService.getCurrentEffectiveDate()
                .orElseThrow(() -> new RuntimeException("Start Effective date not registered"));
        LocalDateTime startOfDayLocal = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime endOfDayLocal = startOfDayLocal.plusDays(1);
        OffsetDateTime startOfDay = startOfDayLocal.atOffset(ZoneOffset.systemDefault().getRules().getOffset(startOfDayLocal));
        OffsetDateTime endOfDay = endOfDayLocal.atOffset(ZoneOffset.systemDefault().getRules().getOffset(endOfDayLocal));
        Optional<AttendanceRecord> employeesCheckedInTodayById = attendanceRecordRepository.findEmployeesCheckedInTodayById(employee.getId(), startOfDay, endOfDay);
        if(employeesCheckedInTodayById.isPresent()){
            LocalDateTime lastUpdateLocal = employeesCheckedInTodayById.get().getUpdatedAt();
            ZoneOffset lastUpdateZoneOffset = ZoneOffset.systemDefault().getRules().getOffset(lastUpdateLocal);
            OffsetDateTime lastUpdateoffsetDateTime = lastUpdateLocal.atOffset(lastUpdateZoneOffset);
            Duration between = Duration.between(lastUpdateoffsetDateTime,ZonedDateTime.now());
            Double lastUpdateMinutes = (double) between.toMinutes();
            log.info("lastUpdate {}",String.valueOf(lastUpdateMinutes));
            if(lastUpdateMinutes<2){
                throw new DuplicateEnrolException();
            }
            if(employeesCheckedInTodayById.get().getIsAlreadyTakenIn() && employeesCheckedInTodayById.get().getIsAlreadyTakenOut()) throw new AttendanceAlreadyTaken();
            else {
                Integer hour = currentEffectiveDate.getHourOut();
                Integer minute = currentEffectiveDate.getMinuteOut();
                OffsetDateTime entryTime = OffsetDateTime.now();
                OffsetDateTime scheduleTime = OffsetDateTime.now().withHour(hour).withMinute(minute).withSecond(0);
                Duration lateDuration = Duration.between(scheduleTime, entryTime);
                Double lateTimeInMinutes = (double) lateDuration.toMinutes();
                employeesCheckedInTodayById.get().setOverTime(lateTimeInMinutes);
                employeesCheckedInTodayById.get().setIsAlreadyTakenOut(true);
                employeesCheckedInTodayById.get().setTakeOutTime(OffsetDateTime.now());
                attendanceRecordRepository.save(employeesCheckedInTodayById.get());
            }
        }

        if (employeesCheckedInTodayById.isEmpty()){
            Integer hour = currentEffectiveDate.getHourIn();
            Integer minute = currentEffectiveDate.getMinuteIn();
            String attendanceStatus = "ONTIME";
            OffsetDateTime entryTime = OffsetDateTime.now();
            OffsetDateTime scheduleTime = OffsetDateTime.now().withHour(hour).withMinute(minute).withSecond(0);
            if (entryTime.isAfter(scheduleTime)) {
                attendanceStatus = "LATE";
                Duration lateDuration = Duration.between(scheduleTime, entryTime);
                Double lateTimeInMinutes = (double) lateDuration.toMinutes();
                AttendanceRecord attendanceRecord = new AttendanceRecord();
                attendanceRecord.setAttendanceStatus(attendanceStatus);
                attendanceRecord.setLateTime(lateTimeInMinutes);
                attendanceRecord.setTakeInTime(entryTime);
                attendanceRecord.setEmployee(employee);
                attendanceRecord.setIsAlreadyTakenIn(true);
                attendanceRecord.setIsAlreadyTakenOut(false);
                attendanceRecordRepository.save(attendanceRecord);
            } else {
                Duration lateDuration = Duration.between(scheduleTime, entryTime);
                Double lateTimeInMinutes = (double) lateDuration.toMinutes();
                AttendanceRecord attendanceRecord = new AttendanceRecord();
                attendanceRecord.setAttendanceStatus(attendanceStatus);
                attendanceRecord.setLateTime(lateTimeInMinutes);
                attendanceRecord.setTakeInTime(entryTime);
                attendanceRecord.setEmployee(employee);
                attendanceRecord.setIsAlreadyTakenIn(true);
                attendanceRecord.setIsAlreadyTakenOut(false);
                attendanceRecordRepository.save(attendanceRecord);
            }
        }
        return employee;
    }

    @Override
    public PageableResponse<Employee> getAll(Pageable pageable) {
        Page<Employee> employees = employeeRepository.findAll(pageable);
        PageableResponse<Employee> employeePageableResponse = PageableResponse.fromPage(employees);
        return employeePageableResponse;
    }

    @Override
    public Boolean deleteById(UUID id) {
        Employee employee = employeeRepository
                .findById(id).orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));
        employeeRepository.deleteById(id);
        return true;
    }

    @Override
    public Employee editById(Employee request, UUID id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));
        employee.setFirstname(request.getFirstname());
        employee.setLastname(request.getLastname());
        employee.setDivision(request.getDivision());
        employee.setJobTitle(request.getJobTitle());
        Employee save = employeeRepository.save(employee);
        return save;
    }

    @Override
    public Employee findByEmployeeId(String id) {
        return employeeRepository.findByEmployeeId(id).orElseThrow(RuntimeException::new);
    }

}

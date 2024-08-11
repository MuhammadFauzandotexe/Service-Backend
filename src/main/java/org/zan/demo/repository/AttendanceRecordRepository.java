package org.zan.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zan.demo.entity.AttendanceRecord;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, UUID> {
    @Query("SELECT ar FROM AttendanceRecord ar WHERE" +
            " ar.employee.id = :employeeId" +
            " AND ar.takeInTime >= :startOfDay" +
            " AND ar.takeInTime < :endOfDay" )
    Optional<AttendanceRecord> findEmployeesCheckedInTodayById(
            @Param("employeeId") UUID employeeId,
            @Param("startOfDay") OffsetDateTime startOfDay,
            @Param("endOfDay") OffsetDateTime endOfDay);
}

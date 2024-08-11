package org.zan.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "t_attendance_record")
@Getter
@Setter
public class AttendanceRecord extends BaseEntity {
    private String attendanceStatus;
    private Double lateTime;
    private Double overTime;
    private OffsetDateTime takeInTime;
    private OffsetDateTime takeOutTime;
    private Boolean isAlreadyTakenIn;
    private Boolean isAlreadyTakenOut;

    @ManyToOne
    private Employee employee;
}

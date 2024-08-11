package org.zan.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "m_employee")
@Getter
@Setter
public class Employee extends BaseEntity {
    private String employeeId;
    private String firstname;
    private String lastname;

    @Column(unique = true)
    private String email;

    private String division;
    private String jobTitle;

    @PrePersist
    protected void onCreate() {
        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString().replaceAll("-", "");
        setEmployeeId(uuidStr.substring(0, 10));
    }
    @JsonIgnore
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AttendanceRecord> attendanceRecords;
}

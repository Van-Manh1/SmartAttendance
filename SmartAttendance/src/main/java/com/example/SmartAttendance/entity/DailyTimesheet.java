package com.example.SmartAttendance.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.SmartAttendance.enums.AttendanceStatus;

@Entity
@Table(name = "daily_timesheets", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "date"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DailyTimesheet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @Column(nullable = false)
    LocalDate date;

    @Column(name = "check_in")
    LocalTime checkIn;

    @Column(name = "check_out")
    LocalTime checkOut;

    @Column(name = "late_minutes")
    Integer lateMinutes; 

    @Column(name = "early_leave_minutes")
    Integer earlyLeaveMinutes;

    @Column(name = "overtime_hours")
    Double overtimeHours; 

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    AttendanceStatus status; 
}

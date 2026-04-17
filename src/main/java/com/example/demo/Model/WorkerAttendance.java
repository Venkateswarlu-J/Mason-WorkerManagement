package com.example.demo.Model;

import com.example.demo.enums.AttendanceStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class WorkerAttendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attendanceId;

    @ManyToOne
    @JsonBackReference//it tells that for one worker there is several attendance recors are there bcz diff dates
    @JoinColumn(name = "worker_id", nullable = false)//it creates the column with "name" for the FK
    private Worker worker;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "sup_id",nullable = false)
    private Supervisor supervisor;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDate attendanceDate;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus attendanceStatus;

    private String remarks;

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public Long getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(Long attendanceId) {
        this.attendanceId = attendanceId;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public AttendanceStatus getAttendanceStatus() {
        return attendanceStatus;
    }

    public void setAttendanceStatus(AttendanceStatus attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    @Override
    public String toString() {
        return "WorkerAttendance{" +
                "attendanceId=" + attendanceId +
                ", worker=" + worker +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", attendanceStatus=" + attendanceStatus +
                ", attendanceDate=" + attendanceDate +
                ", remarks='" + remarks + '\'' +
                ", supervisor=" + supervisor +
                '}';
    }
}

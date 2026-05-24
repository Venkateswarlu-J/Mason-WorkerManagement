package com.example.demo.dtoUser;

import com.example.demo.enums.AttendanceStatus;

public class AttendanceWeeklyDto {

    private Double totalDays;
    private AttendanceStatus status;

    public AttendanceWeeklyDto(Double totalDays, AttendanceStatus status) {
        this.totalDays = totalDays;
        this.status = status;
    }

    public Double getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(Double totalDays) {
        this.totalDays = totalDays;
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    public void setStatus(AttendanceStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AttendanceWeeklyDto{" +
                "totalDays=" + totalDays +
                ", status=" + status +
                '}';
    }
}

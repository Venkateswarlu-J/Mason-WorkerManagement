package com.example.demo.dtoUser;

import com.example.demo.enums.AttendanceStatus;

public class AttendanceRequset {
    private long workerId;
    private AttendanceStatus attendanceStatus;

    public long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(long workerId) {
        this.workerId = workerId;
    }

    public AttendanceStatus getAttendanceStatus() {
        return attendanceStatus;
    }

    public void setAttendanceStatus(AttendanceStatus attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }

    @Override
    public String toString() {
        return "AttendanceRequset{" +
                "workerId=" + workerId +
                ", attendanceStatus=" + attendanceStatus +
                '}';
    }
}

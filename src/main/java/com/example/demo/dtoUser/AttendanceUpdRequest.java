package com.example.demo.dtoUser;

import com.example.demo.enums.AttendanceStatus;

public class AttendanceUpdRequest {
    private long workerId;
    private String remark;
    private AttendanceStatus attendanceStatus;

    public long getWorkerId() {

        return workerId;
    }

    public void setWorkerId(long workerId) {
        this.workerId = workerId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public AttendanceStatus getAttendanceStatus() {
        return attendanceStatus;
    }

    public void setAttendanceStatus(AttendanceStatus attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }

    @Override
    public String toString() {
        return "AttendanceUpdRequest{" +
                "workerId=" + workerId +
                ", remark='" + remark + '\'' +
                ", attendanceStatus=" + attendanceStatus +
                '}';
    }
}

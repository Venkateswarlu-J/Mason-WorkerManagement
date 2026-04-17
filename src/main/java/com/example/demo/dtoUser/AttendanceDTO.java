package com.example.demo.dtoUser;

import com.example.demo.enums.AttendanceStatus;

public class AttendanceDTO {
    private String workerName;
    private long workerId;
    private AttendanceStatus attendanceStatus;
    private String projName;


    public long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(long workerId) {
        this.workerId = workerId;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public AttendanceStatus getAttendanceStatus() {
        return attendanceStatus;
    }

    public void setAttendanceStatus(AttendanceStatus attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    @Override
    public String toString() {
        return "AttendanceDTO{" +
                "workerName='" + workerName + '\'' +
                ", attendanceStatus=" + attendanceStatus +
                ", workerId=" + workerId +
                ", projName='" + projName + '\'' +
                '}';
    }
}

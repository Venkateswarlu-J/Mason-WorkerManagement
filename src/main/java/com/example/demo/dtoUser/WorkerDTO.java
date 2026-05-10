package com.example.demo.dtoUser;

import com.example.demo.enums.Categories;
import com.example.demo.enums.ProjectStatus;

public class WorkerDTO {
    private long workerId;
    private String workerName;
    private Categories workerCat;
    private String projectName;
    private long payPerDay;
    private String workerPhone;
//    private ProjectStatus status;

    public WorkerDTO(String workerName, Categories workerCat,String workerPhone ,long workerId,long payPerDay,String projectName) {
        this.workerName = workerName;
        this.workerCat = workerCat;
        this.projectName = projectName;
        this.workerId=workerId;
        this.workerPhone=workerPhone;
        this.payPerDay=payPerDay;
//        this.status = status;
    }

    public String getWorkerPhone() {
        return workerPhone;
    }

    public void setWorkerPhone(String workerPhone) {
        this.workerPhone = workerPhone;
    }

    public long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(long workerId) {
        this.workerId = workerId;
    }

    public long getPayPerDay() {
        return payPerDay;
    }

    public void setPayPerDay(long payPerDay) {
        this.payPerDay = payPerDay;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public Categories getWorkerCat() {
        return workerCat;
    }

    public void setWorkerCat(Categories workerCat) {
        this.workerCat = workerCat;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

//    public ProjectStatus getStatus() {
//        return status;
//    }

//    public void setStatus(ProjectStatus status) {
//        this.status = status;
//    }

    @Override
    public String toString() {
        return "WorkerDTO{" +
                "workerId='" + workerId + '\'' +
                "workerPhone='" + workerPhone + '\'' +
                "workerName='" + workerName + '\'' +
                ", workerCat=" + workerCat +
                ", projectName='" + projectName + '\'' +
                "dailyWage='" + payPerDay + '\'' +
//                ", status=" + status +
                '}';
    }
}

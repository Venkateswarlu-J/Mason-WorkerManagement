package com.example.demo.dtoUser;

import com.example.demo.enums.Categories;
import com.example.demo.enums.ProjectStatus;

public class WorkerDTO {
    private String workerName;
    private Categories workerCat;
    private String projectName;
//    private ProjectStatus status;

    public WorkerDTO(String workerName, Categories workerCat, String projectName) {
        this.workerName = workerName;
        this.workerCat = workerCat;
        this.projectName = projectName;
//        this.status = status;
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
                "workerName='" + workerName + '\'' +
                ", workerCat=" + workerCat +
                ", projectName='" + projectName + '\'' +
//                ", status=" + status +
                '}';
    }
}

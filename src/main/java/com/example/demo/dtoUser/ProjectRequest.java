package com.example.demo.dtoUser;

import com.example.demo.enums.ProjectStatus;

import java.util.Date;

public class ProjectRequest {

    private String projectOwner;
    private String ownerPhone;
    private String projName;
    private String projLocation;
    private ProjectStatus projStatus;


    public String getProjectOwner() {
        return projectOwner;
    }

    public void setProjectOwner(String projectOwner) {
        this.projectOwner = projectOwner;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    public ProjectStatus getProjStatus() {
        return projStatus;
    }

    public void setProjStatus(ProjectStatus projStatus) {
        this.projStatus = projStatus;
    }

    public String getProjName() {

        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public String getProjLocation() {
        return projLocation;
    }

    public void setProjLocation(String projLocation) {
        this.projLocation = projLocation;
    }

    @Override
    public String toString() {
        return "ProjectRequest{" +
                ", projOwenr='" + projectOwner + '\'' +
                ", ownerPhone='" + ownerPhone + '\'' +
                "projName='" + projName + '\'' +
                ", projLocation='" + projLocation + '\'' +
                ", projStatus='" + projStatus + '\'' +
                '}';
    }
}

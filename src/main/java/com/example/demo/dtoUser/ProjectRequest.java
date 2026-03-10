package com.example.demo.dtoUser;

import com.example.demo.enums.ProjectStatus;

import java.util.Date;

public class ProjectRequest {

    private String projName;
    private String projLocation;
    private ProjectStatus projStatus;

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
                "projName='" + projName + '\'' +
                ", projLocation='" + projLocation + '\'' +
                ", projStatus='" + projStatus + '\'' +
                '}';
    }
}

package com.example.demo.dtoUser;

import com.example.demo.enums.ProjectStatus;

public class ProjUpdateRequest {

        private String projName;
        private String projectOwner;
        private String projLocation;
        private ProjectStatus projStatus;
        private String ownerPhone;

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public String getProjectOwner() {
        return projectOwner;
    }

    public void setProjectOwner(String projectOwner) {
        this.projectOwner = projectOwner;
    }

    public String getProjLocation() {
        return projLocation;
    }

    public void setProjLocation(String projLocation) {
        this.projLocation = projLocation;
    }

    public ProjectStatus getProjStatus() {
        return projStatus;
    }

    public void setProjStatus(ProjectStatus projStatus) {
        this.projStatus = projStatus;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    @Override
    public String toString() {
        return "ProjUpdateRequest{" +
                "projName='" + projName + '\'' +
                ", projectOwner='" + projectOwner + '\'' +
                ", projLocation='" + projLocation + '\'' +
                ", projStatus=" + projStatus +
                ", ownerPhone='" + ownerPhone + '\'' +
                '}';
    }
}

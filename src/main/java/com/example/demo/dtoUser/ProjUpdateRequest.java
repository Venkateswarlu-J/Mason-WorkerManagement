package com.example.demo.dtoUser;

import com.example.demo.enums.ProjectStatus;

public class ProjUpdateRequest {

        private String projectName;
        private String renameProjectName;
        private String location;
        private ProjectStatus status;

        public String getProjectName() {

            return projectName;
        }

        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }

        public String getRenameProjectName() {
            return renameProjectName;
        }

        public void setRenameProjectName(String renameProjectName) {
            this.renameProjectName = renameProjectName;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public ProjectStatus getStatus() {
            return status;
        }

        public void setStatus(ProjectStatus status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "ProjectUpdateRequest{" +
                    "projectName='" + projectName + '\'' +
                    ", renameProjectName='" + renameProjectName + '\'' +
                    ", location='" + location + '\'' +
                    ", status='" + status + '\'' +
                    '}';
        }
    }

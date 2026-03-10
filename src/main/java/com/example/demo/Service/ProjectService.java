package com.example.demo.Service;


import com.example.demo.Model.Project;
import com.example.demo.Model.Supervisor;
import com.example.demo.Model.Users;
import com.example.demo.dtoUser.ProjUpdateRequest;
import com.example.demo.dtoUser.ProjectRequest;
import com.example.demo.enums.ProjectStatus;
import com.example.demo.enums.Roles;
import com.example.demo.repo.ProjRepo;
import com.example.demo.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ProjectService {
    @Autowired
    private ProjRepo projRepo;
    @Autowired
    private UserRepo userRepo;
    public String addProject(ProjectRequest projectRequest){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return "Unauthorized access";
        }
        String email = auth.getName();
        Users loggedUser = userRepo.findByEmail(email);
        if (loggedUser == null || !(loggedUser.getRole()==(Roles.SUPERVISOR))) {
            return "Access denied. Only supervisor allowed.";
        }
        Supervisor supervisor = loggedUser.getSupervisor();
        if (supervisor == null) {
            return "Supervisor profile not found";
        }

        LocalDateTime today=LocalDateTime.now();
        Project project=new Project();
        project.setProjectName(projectRequest.getProjName());
        project.setLocation(projectRequest.getProjLocation());
        ProjectStatus status=projectRequest.getProjStatus();
        project.setStatus(status);
        if (status== ProjectStatus.RUNNING && project.getStartDate() == null) {
            project.setStartDate(LocalDate.now());
        }
        project.setCreatedAt(today);
        project.setSupervisor(supervisor);
        projRepo.save(project);
        return "Successfully added new Project"+project;
    }

    public List<Project> getAllProjects(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
//            return "Unauthorized access";
            return new ArrayList<>();
        }

        String email = auth.getName();
        Users loggedUser = userRepo.findByEmail(email);
        if (loggedUser == null || !(loggedUser.getRole()== Roles.SUPERVISOR)) {
//            return "Access denied. Only supervisor allowed.";
            return new ArrayList<>();
        }
        Supervisor supervisor = loggedUser.getSupervisor();
        if (supervisor == null) {
//            return "Supervisor profile not found";
            return new ArrayList<>();
        }
        return projRepo.findAllBySupervisor_SupId(supervisor.getSupId());
    }

    @Transactional
    public String updateProjDetails(ProjUpdateRequest request) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Users user = userRepo.findByEmail(email);
        if (user == null || !(Roles.SUPERVISOR==user.getRole())) {
            return "Access denied";
        }

        Supervisor supervisor = user.getSupervisor();

        // Find project by name under that supervisor
        Project project = projRepo
                .findByProjectNameAndSupervisor_SupId(
                        request.getProjectName(), supervisor.getSupId()
                );

        // Rename project
        if (request.getRenameProjectName() != null &&
                !request.getRenameProjectName().isBlank()) {

            boolean exists = projRepo
                    .existsByProjectNameAndSupervisor_SupId(
                            request.getRenameProjectName(),
                            supervisor.getSupId()
                    );

            if (exists) {
                return "Project name already exists";
            }

            project.setProjectName(request.getRenameProjectName());
        }

        // Update location
        if (request.getLocation() != null &&
                !request.getLocation().isBlank()) {

            project.setLocation(request.getLocation());
        }

        // Update status
        if (request.getStatus() != null) {

            ProjectStatus status = request.getStatus();
            project.setStatus(status);

            if (status== ProjectStatus.RUNNING && project.getStartDate() == null) {
                project.setStartDate(LocalDate.now());
            }

            if (status==ProjectStatus.COMPLETED && project.getEndDate() == null) {
                project.setEndDate(LocalDate.now());
            }
        }
        return "Project updated successfully";
    }

}

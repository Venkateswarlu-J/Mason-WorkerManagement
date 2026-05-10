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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> addProject(ProjectRequest projectRequest){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Unauthorized access");
        }
        String username = auth.getName();
        Users loggedUser = userRepo.findByUsername(username);
        if (loggedUser == null || !(loggedUser.getRole()==(Roles.SUPERVISOR))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Access denied. Only supervisor allowed.");
        }
        Supervisor supervisor = loggedUser.getSupervisor();
        if (supervisor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Supervisor profile not found");
        }
        if(projRepo.existsByProjectNameAndSupervisor_SupId(projectRequest.getProjName(), supervisor.getSupId())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("The Project Name should be unique");
        }

        System.out.println(projectRequest);
        LocalDateTime today=LocalDateTime.now();
        Project project=new Project();
        project.setProjectOwner(projectRequest.getProjectOwner());
        project.setOwnerPhone(projectRequest.getOwnerPhone());
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
        return ResponseEntity.ok("Successfully added new Project"+project);
    }

    public List<Project> getAllProjects(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
//            return "Unauthorized access";
            return new ArrayList<>();
        }

        String username = auth.getName();
        Users loggedUser = userRepo.findByUsername(username);
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
    public ResponseEntity<?> updateProjDetails(ProjUpdateRequest request,Long projectId) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Users user = userRepo.findByUsername(username);
        if (user == null || !(Roles.SUPERVISOR==user.getRole())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Access denied");
        }

        Supervisor supervisor = user.getSupervisor();

        // Find project by name under that supervisor
        Project project = projRepo
                .findByProjectIdAndSupervisor_SupId(
                        projectId, supervisor.getSupId()
                );

        // Rename project
        if (request.getProjName() != null &&
                !request.getProjName().isBlank()) {

            boolean exists = projRepo
                    .existsByProjectNameAndSupervisor_SupId(
                            request.getProjName(),
                            supervisor.getSupId()
                    );

            if (exists) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Project name already exists");
            }

            project.setProjectName(request.getProjName());
        }

        // Update location
        if (request.getProjLocation() != null &&
                !request.getProjLocation().isBlank()) {

            project.setLocation(request.getProjLocation());
        }

        // Update status
        if (request.getProjLocation() != null) {

            ProjectStatus status = request.getProjStatus();
            project.setStatus(status);

            if (status== ProjectStatus.RUNNING && project.getStartDate() == null) {
                project.setStartDate(LocalDate.now());
            }

            if (status==ProjectStatus.COMPLETED && project.getEndDate() == null) {
                project.setEndDate(LocalDate.now());
            }
        }
        return ResponseEntity.ok("Project updated successfully");
    }

    @Transactional
    public ResponseEntity<?> updateStatus(Long projectId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Users user = userRepo.findByUsername(username);
        if (user == null || !(Roles.SUPERVISOR==user.getRole())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Access denied");
        }

        Supervisor supervisor = user.getSupervisor();

        // Find project by name under that supervisor
        Project project = projRepo
                .findByProjectIdAndSupervisor_SupId(
                        projectId, supervisor.getSupId()
                );
//        if(!project.getActive()) return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                        .body("Project Already Deactivated");
        project.setActive(!project.getActive());
        return ResponseEntity.ok(project.getProjectName()+" Successfully "+(project.getActive()?"Activated":"Deactivated"));
    }
}

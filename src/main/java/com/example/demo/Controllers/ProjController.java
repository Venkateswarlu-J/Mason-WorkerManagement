package com.example.demo.Controllers;


import com.example.demo.Model.Project;
import com.example.demo.Service.ProjectService;
import com.example.demo.dtoUser.ProjUpdateRequest;
import com.example.demo.dtoUser.ProjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProjController {
    @Autowired
    private ProjectService projectService;
    @PostMapping("/addProject")
    public ResponseEntity<?> addProject(@RequestBody ProjectRequest projectRequest){
        return projectService.addProject(projectRequest);
    }
    @GetMapping("/getAllProjects")
    public List<Project> getAllProjects(){
        return projectService.getAllProjects();
    }

    @PutMapping("/updateProjDetails/{projectId}")
    public ResponseEntity<?> updateProjDetails(@RequestBody ProjUpdateRequest projUpdateRequest,@PathVariable Long projectId){
//        System.out.println(projUpdateRequest+" "+projectId);
        return projectService.updateProjDetails(projUpdateRequest,projectId);
    }

    @PutMapping("/updateStatus/{projectId}")
    public ResponseEntity<?> updateStatus(@PathVariable Long projectId){
//        System.out.println(projUpdateRequest+" "+projectId);
//        System.out.println("called update status");
        return projectService.updateStatus(projectId);
    }
}

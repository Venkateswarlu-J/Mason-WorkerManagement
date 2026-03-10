package com.example.demo.Controllers;


import com.example.demo.Model.Project;
import com.example.demo.Service.ProjectService;
import com.example.demo.dtoUser.ProjUpdateRequest;
import com.example.demo.dtoUser.ProjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProjController {
    @Autowired
    private ProjectService projectService;
    @PostMapping("/addProject")
    public String addProject(@RequestBody ProjectRequest projectRequest){
        return projectService.addProject(projectRequest);
    }
    @GetMapping("/getAllProjects")
    public List<Project> getAllProjects(){
        return projectService.getAllProjects();
    }

    @PostMapping("/updateProjDetails")
    public String updateProjDetails(@RequestBody ProjUpdateRequest projUpdateRequest){
        return projectService.updateProjDetails(projUpdateRequest);
    }
}

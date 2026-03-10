package com.example.demo.Controllers;

import com.example.demo.Model.Worker;
import com.example.demo.Service.WorkerService;
import com.example.demo.dtoUser.WorkerDTO;
import com.example.demo.dtoUser.WorkerRequest;
import com.example.demo.enums.Categories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WorkerController {
    @Autowired
    private WorkerService wService;
    @PostMapping("/createWorker")
    public String register(@RequestBody WorkerRequest worker){
        System.out.println(worker.getEmail());
        System.out.println(worker);
        return wService.register(worker);
    }

    @GetMapping("/getWorkers")
    public List<WorkerDTO> getAll(){
        return wService.getWorkers();
    }

    @DeleteMapping("/removeWorker/{workerId}")
    public String removeWorker(@PathVariable Long workerId){
        return wService.remove(workerId);
    }

    @GetMapping("/getWorkersForAttendance")
    public List<WorkerDTO> getWorkersForAttendance(String search, Categories categories){
        return wService.getWorkersForAttendance(search,categories);
    }
}

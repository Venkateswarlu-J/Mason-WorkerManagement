package com.example.demo.Controllers;

import com.example.demo.Model.WorkerAttendance;
import com.example.demo.Service.WorkerService;
import com.example.demo.dtoUser.*;
import com.example.demo.enums.Categories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WorkerController {
    @Autowired
    private WorkerService wService;
    @PostMapping("/addWorker")
    public String register(@RequestBody WorkerRequest worker){
        System.out.println(worker.getEmail());
        System.out.println(worker);
        return wService.register(worker);
    }

//    @GetMapping("/getWorkers")
//    public List<WorkerDTO> getAll(){
//        return wService.getWorkers();
//    }

    @DeleteMapping("/removeWorker/{workerId}")
    public String removeWorker(@PathVariable Long workerId){
        return wService.remove(workerId);
    }

    @GetMapping("/getWorkers")
    public List<WorkerDTO> getWorkers(

            @RequestParam(required = false) String search,
            @RequestParam(required = false) Categories category,
            @RequestParam(required = false) String projectName
    ){
        return wService.getWorkers(search, category, projectName);
    }


    @PostMapping("/putAttendance")
    public String putAttendance(@RequestBody AttendanceRequset attendanceRequset){
        return wService.putAttendance(attendanceRequset);
    }

    @GetMapping("/getAttendance")
    public List<AttendanceDTO> getAttendance(){
        return wService.getAttendance();
    }

    @PostMapping("/updateAttendance")
    public String updateAttendance(@RequestBody AttendanceUpdRequest attendanceUpdRequest){
        return wService.updateAttendance(attendanceUpdRequest);
    }
}

package com.example.demo.Controllers;


import com.example.demo.Service.AttendanceService;
import com.example.demo.dtoUser.AttendanceWeeklyDto;
import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("/weeklyReport")
    public Map<Long, AttendanceWeeklyDto> getWeeklyReport(){
        return attendanceService.getWeeklyReport();
    }
}

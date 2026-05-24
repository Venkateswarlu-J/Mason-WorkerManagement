package com.example.demo.Service;


import com.example.demo.Model.Supervisor;
import com.example.demo.Model.Users;
import com.example.demo.Model.WorkerAttendance;
import com.example.demo.dtoUser.AttendanceWeeklyDto;
import com.example.demo.enums.AttendanceStatus;
import com.example.demo.enums.Roles;
import com.example.demo.repo.AttendanceRepo;
import com.example.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class AttendanceService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AttendanceRepo attendanceRepo;

    @Autowired
    private WorkerService workerService;

    public Map<Long, AttendanceWeeklyDto> getWeeklyReport(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Users loggedUser = userRepo.findByUsername(username);
        if(!(loggedUser.getRole() == Roles.SUPERVISOR)){
            throw new RuntimeException("Only supervisors can access this");
        }
        Supervisor supervisor = loggedUser.getSupervisor();
        LocalDateTime today=LocalDateTime.now();

        LocalDateTime monday = today.with(DayOfWeek.MONDAY).toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = today.toLocalDate().atTime(23, 59, 59);

        ArrayList<WorkerAttendance> attendances=attendanceRepo.findBySupervisorAndDateBetween(supervisor,monday,endOfDay);

        Map<Long, AttendanceWeeklyDto> result = new HashMap<>();
//        workerService.getWorkers().forEach(w->result.put(w.getWorkerId(),new AttendanceWeeklyDto(0.0,null)));

        workerService.getWorkers().forEach(w -> {
//            System.out.println("Worker in map: " + w.getWorkerId());
            result. put(w.getWorkerId(), new AttendanceWeeklyDto(0.0, null));
        });

//        for (WorkerAttendance a : attendances) {
//            System.out.println("Attendance workerId: " + a.getWorker().getWorkerID());
//        }

        for(WorkerAttendance a:attendances){
            Long wid = a.getWorker().getWorkerID();
            AttendanceWeeklyDto dto = result.get(wid);

            // Count present + half day toward daysPresent
            if (a.getAttendanceStatus() == AttendanceStatus.PRESENT) {
                dto.setTotalDays(dto.getTotalDays() + 1);
            } else if (a.getAttendanceStatus() == AttendanceStatus.HALF_DAY) {
                dto.setTotalDays(dto.getTotalDays() + 1); // or +0.5 if you want fractional
            }

            // If this record is for today, set todayStatus
            if (a.getCreatedAt().toLocalDate().equals(LocalDate.now())) {
                dto.setStatus(a.getAttendanceStatus());
            }
        }
        return result;
    }
}

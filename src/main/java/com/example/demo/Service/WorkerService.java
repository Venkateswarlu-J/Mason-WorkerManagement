package com.example.demo.Service;


import com.example.demo.Model.Supervisor;
import com.example.demo.Model.Users;
import com.example.demo.Model.Worker;
import com.example.demo.Model.WorkerAttendance;
import com.example.demo.dtoUser.*;
import com.example.demo.enums.Categories;
import com.example.demo.enums.Roles;
import com.example.demo.repo.AttendanceRepo;
import com.example.demo.repo.SupRepo;
import com.example.demo.repo.UserRepo;
import com.example.demo.repo.WorkerRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class WorkerService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private WorkerRepo wRepo;
    @Autowired
    private SupRepo supRepo;
    @Autowired
    private AttendanceRepo attendanceRepo;
    @Autowired
    private OtpService otpService;

    private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);

    public WorkerService(WorkerRepo wRepo) {
        this.wRepo = wRepo;
    }
//Need to find the worker by an category if any argument is passed other wise all workers
    public List<WorkerDTO> getWorkers() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Users loggedUser = userRepo.findByUsername(username);
        if(!(loggedUser.getRole()==Roles.SUPERVISOR)){
            return new ArrayList<>();
        }
        return wRepo.findWorkers(loggedUser.getSupervisor().getSupId(),null,null);
    }


    //this is for getAllWorkers
    public List<WorkerDTO> getWorkers(String search, Categories category, String projectName) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();//need to check whether it return email or an username; it return usernmae and it accessing the jwt subject
//        System.out.println("faa"+email);
        Users loggedUser = userRepo.findByUsername(username);
//        System.out.println(loggedUser.getEmail()+" "+loggedUser.getRole());

        //actually we don't give the option to create the worker for the worker role it is an optional
        if(!(loggedUser.getRole()==Roles.SUPERVISOR)){
            throw new RuntimeException("Access denied");
        }
        Supervisor supervisor = loggedUser.getSupervisor();
        return wRepo.findWorkers(supervisor.getSupId(),search,category);
    }

    public List<WorkerDTO> getRemovedWorkers() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Users loggedUser = userRepo.findByUsername(username);
        if(!(loggedUser.getRole()==Roles.SUPERVISOR)){
            throw new RuntimeException("Acess denied");
        }
        return wRepo.findRemovedWorkers(loggedUser.getSupervisor().getSupId(),null,null);
    }

    @Transactional
    public ResponseEntity<?> register(WorkerRequest request) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();//need to check whether it return email or an username;
//        System.out.println("faa"+email);
        Users loggedUser = userRepo.findByUsername(username);
//        System.out.println(loggedUser.getEmail()+" "+loggedUser.getRole());

        //actually we don't give the option to create the worker for the worker role it is an optional
        if(!(loggedUser.getRole()==Roles.SUPERVISOR)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Access denied");
        }
        Supervisor supervisor = loggedUser.getSupervisor();

        Users check=userRepo.findByUsername(request.getWorkerName());
//        System.out.println("worker email"+request.getEmail()+" supe role"+check);
        Users user = new Users();
        if(check!=null){
            if (!(check.getRole()==Roles.WORKER)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Email already registered with another role");
            }

            Worker exist=check.getWorker();
            if(exist.isActive()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Duplicate Data");
            }
            else{
                exist.setActive(true);
//                exist.setSupervisor(supervisor);
                if(!request.getEmail().trim().isEmpty()) {
                    user.setPassword(otpService.sendPass(request.getEmail(),supervisor.getSup_name()));
                    user.setEmail(request.getEmail());
                }
                return ResponseEntity.ok("Successfully Created !");
            }
        }


        user.setUsername(request.getWorkerName());
        if(request.getEmail() != null && !request.getEmail().isBlank()) {
            user.setPassword(otpService.sendPass(request.getEmail(),supervisor.getSup_name()));
            user.setEmail(request.getEmail());
        }
        else{
            user.setEmail(null);
            user.setPassword(otpService.encryptPass("NO_ACCESS_" + UUID.randomUUID()));
        }
        user.setRole(Roles.WORKER);
        user.setPhone(request.getWorkerPhone());

//        user.setActive(true);

        Worker worker = new Worker();
        worker.setWorkerName(request.getWorkerName());
        worker.setWorkerPhone(request.getWorkerPhone());
        worker.setWorkerCat(request.getWorkerCat());
        worker.setWorkerAddress(request.getWorkerAddress());
        worker.setPayPerDay(request.getPayPerDay());
        worker.setActive(true);

        worker.setSupervisor(supervisor);
        worker.setUser(user);
        user.setWorker(worker);
        user.setSupervisor(supervisor);
        //need to change the register
        userRepo.save(user);
        return ResponseEntity.ok("Successfully Created the worker"+worker.getWorkerName());
    }

    @Transactional
    public ResponseEntity<?> remove(Long workerID) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Users loggedUser = userRepo.findByUsername(username);
        if(!(loggedUser.getRole()==Roles.SUPERVISOR)){
            throw new RuntimeException("Access denied");
        }

        Worker worker=wRepo.findByWorkerIDAndSupervisor_SupId(workerID,loggedUser.getSupervisor().getSupId());
        worker.setActive(!worker.getActive());
        return ResponseEntity.ok("Successfully  "+(worker.getActive()?"Added":"Removed"));
    }




    @Transactional
    public ResponseEntity<?> putAttendance(AttendanceRequset attendanceRequset) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Users loggedUser = userRepo.findByUsername(username);
        if(!(loggedUser.getRole()==Roles.SUPERVISOR)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied");
        }
        Supervisor supervisor = loggedUser.getSupervisor();

//        System.out.println(attendanceRequset);
        WorkerAttendance workerAttendance=new WorkerAttendance();
        workerAttendance.setAttendanceStatus(attendanceRequset.getAttendanceStatus());
        workerAttendance.setSupervisor(supervisor);
        Worker worker = wRepo.findByWorkerIDAndIsActiveTrue(attendanceRequset.getWorkerId());
        if(worker==null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Worker Not found");

        //Handling no duplicates at one day only
        boolean exists = attendanceRepo.existsByWorkerAndAttendanceDate(worker, LocalDate.now());
        if (exists) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Attendance already marked for this worker today");
        }

        workerAttendance.setWorker(worker);
        workerAttendance.setCreatedAt(LocalDateTime.now());
        workerAttendance.setAttendanceDate(LocalDate.now());
        attendanceRepo.save(workerAttendance);
        return ResponseEntity.ok("Succesfully added attendance to the "+worker.getWorkerName());
    }

    public List<AttendanceDTO> getAttendance() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Users loggedUser = userRepo.findByEmail(email);
        if(!(loggedUser.getRole()==Roles.SUPERVISOR)){
            throw new RuntimeException("Access denied");
        }
        Supervisor supervisor = loggedUser.getSupervisor();

        LocalDate today=LocalDate.now();
         List<WorkerAttendance> list=attendanceRepo.findBySupervisor_SupIdAndAttendanceDate(supervisor.getSupId(),today);
         List<AttendanceDTO> res=new ArrayList<>();
         for(WorkerAttendance worker:list){
             AttendanceDTO attendanceDTO=new AttendanceDTO();
             attendanceDTO.setAttendanceStatus(worker.getAttendanceStatus());
             attendanceDTO.setWorkerName(worker.getWorker().getWorkerName());
             attendanceDTO.setProjName(worker.getRemarks());//just for temporary
             attendanceDTO.setWorkerId(worker.getWorker().getWorkerID());
             res.add(attendanceDTO);
         }
         return res;
    }

    @Transactional
    public String updateAttendance(AttendanceUpdRequest attendanceUpdRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Users loggedUser = userRepo.findByEmail(email);
        if(!(loggedUser.getRole()==Roles.SUPERVISOR)){
            throw new RuntimeException("Access denied");
        }
        Supervisor supervisor = loggedUser.getSupervisor();

        if (attendanceUpdRequest.getRemark() == null ||
                attendanceUpdRequest.getRemark().trim().isEmpty()) {
            throw new RuntimeException("Remark is mandatory while updating attendance");
        }

        WorkerAttendance workerAttendance=attendanceRepo.findBySupervisor_SupIdAndWorker_WorkerIDAndAttendanceDate(supervisor.getSupId(),attendanceUpdRequest.getWorkerId(),LocalDate.now());
        if(workerAttendance==null) return "No record found";
        workerAttendance.setRemarks(attendanceUpdRequest.getRemark());
        workerAttendance.setUpdatedAt(LocalDateTime.now());
        workerAttendance.setAttendanceStatus(attendanceUpdRequest.getAttendanceStatus());
        return "Updated successfully";
    }

    @Transactional
    public ResponseEntity<?> updateWorker(WorkerRequest workerRequest, Long workerId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Users loggedUser = userRepo.findByUsername(username);
        if(!(loggedUser.getRole()==Roles.SUPERVISOR)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Access denied");
        }
        Supervisor supervisor = loggedUser.getSupervisor();
        Worker worker=wRepo.findByWorkerNameAndSupervisor_SupIdAndIsActiveTrue(workerRequest.getWorkerName(),supervisor.getSupId());
        if(worker!=null&&!worker.getWorkerID().equals(workerId)) return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Username Already Existed");

        Worker worker1=wRepo.findByWorkerIDAndIsActiveTrue(workerId);
        worker1.setWorkerName(workerRequest.getWorkerName());
        worker1.setPayPerDay(workerRequest.getPayPerDay());
        worker1.setWorkerCat(workerRequest.getWorkerCat());
        worker1.setWorkerPhone(workerRequest.getWorkerPhone());

        return ResponseEntity.ok("Updated successfully");
    }
}

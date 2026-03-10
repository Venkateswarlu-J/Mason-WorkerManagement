package com.example.demo.Service;


import com.example.demo.Model.Supervisor;
import com.example.demo.Model.Users;
import com.example.demo.Model.Worker;
import com.example.demo.dtoUser.WorkerDTO;
import com.example.demo.dtoUser.WorkerRequest;
import com.example.demo.enums.Categories;
import com.example.demo.enums.Roles;
import com.example.demo.repo.SupRepo;
import com.example.demo.repo.UserRepo;
import com.example.demo.repo.WorkerRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkerService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private WorkerRepo wRepo;
    @Autowired
    private SupRepo supRepo;

    private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);

    public WorkerService(WorkerRepo wRepo) {
        this.wRepo = wRepo;
    }
//Need to find the worker by an category if any argument is passed other wise all workers
    public List<WorkerDTO> getWorkers() {
        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        String email= auth.getName();
        Users loggedUser = userRepo.findByEmail(email);
//        System.out.println(loggedUser);
        return wRepo.findBySupervisor_SupIdAndIsActiveTrue(loggedUser.getUserId());//
    }

    @Transactional
    public String register(WorkerRequest request) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();//need to check whether it return email or an username;
//        System.out.println("faa"+email);
        Users loggedUser = userRepo.findByEmail(email);
//        System.out.println(loggedUser.getEmail()+" "+loggedUser.getRole());

        //actually we don't give the option to create the worker for the worker role it is an optional
        if(!(loggedUser.getRole()==Roles.SUPERVISOR)){
            throw new RuntimeException("Access denied");
        }
        Supervisor supervisor = loggedUser.getSupervisor();

        Users check=userRepo.findByEmail(request.getEmail());
//        System.out.println("worker email"+request.getEmail()+" supe role"+check);

        if(check!=null){
            if (!(check.getRole()==Roles.WORKER)) {
                return "Email already registered with another role";
            }

            Worker exist=check.getWorker();
            if(exist.isActive()){
                return "Email already in use by active worker";
            }
            else{
                exist.setActive(true);
//                exist.setSupervisor(supervisor);
                return "Successfully Created !";
            }
        }

        Users user = new Users();
        user.setUsername(request.getWorkerName());
        user.setEmail(request.getEmail());
        user.setRole(Roles.WORKER);
        user.setPhone(request.getWorkerPhone());
        user.setPassword(encoder.encode(request.getEmail()));
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
        return "successfully Created the worker"+worker.getWorkerName();
    }

    @Transactional
    public String remove(Long workerID) {
//        int cnt=wRepo.deleteByWorkerIDAndSupervisor_supId(workerID,supId);
        Worker worker = wRepo.findByWorkerIDAndIsActiveTrue(workerID);
        if(worker==null) return "Not found !";
//        System.out.println(worker+" is going to dlete");
        worker.setActive(false);
//        worker.setSupervisor(null);
//        worker.setUser(null);
        return "Successfully deleted"+worker;
    }

    public List<WorkerDTO> getWorkersForAttendance(String search, Categories categories) {
        return new ArrayList<WorkerDTO>();
    }


}

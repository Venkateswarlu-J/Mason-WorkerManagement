package com.example.demo.Service;

import com.example.demo.Model.Supervisor;
import com.example.demo.Model.Users;
import com.example.demo.Model.Worker;
import com.example.demo.enums.Roles;
import com.example.demo.repo.SupRepo;
import com.example.demo.repo.UserRepo;
import com.example.demo.repo.WorkerRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupService {

    @Autowired
    private SupRepo supRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private WorkerRepo workerRepo;

    public List<Worker> getInactiveWorkers() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("Unauthorized access");
        }
        String email = auth.getName();
        Users loggedUser = userRepo.findByEmail(email);
        if (loggedUser == null || !(loggedUser.getRole()== Roles.SUPERVISOR)) {
            throw new RuntimeException("Access denied. Only supervisor allowed.");
        }
        Supervisor supervisor = loggedUser.getSupervisor();
        if (supervisor == null) {
            throw new RuntimeException("Supervisor profile not found");
        }
        return workerRepo.findBySupervisor_SupIdAndIsActiveFalse(
                supervisor.getSupId()
        );
    }
    @Transactional
    public String reactivateWorker(Long workerId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return "Unauthorized access";
        }
        String email = auth.getName();
        Users loggedUser = userRepo.findByEmail(email);
        if (loggedUser == null || !(loggedUser.getRole()== Roles.SUPERVISOR)) {
            return "Access denied. Only supervisor allowed.";
        }
        Supervisor supervisor = loggedUser.getSupervisor();
        if (supervisor == null) {
            return "Supervisor profile not found";
        }
        Worker worker = workerRepo
                .findByWorkerIDAndSupervisor_SupIdAndIsActiveFalse(
                        workerId,
                        supervisor.getSupId()
                );

        worker.setActive(true);
        return "Worker "+worker.getWorkerName()+" reactivated successfully";
    }
}

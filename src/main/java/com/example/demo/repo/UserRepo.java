package com.example.demo.repo;

import com.example.demo.Model.Users;
import com.example.demo.Model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<Users,Long> {
    Users findByUsername(String username);

    Users findByEmail(String workerGmail);

//    void deleteByWorkerNameAndSupervisor_supId(String workerName, Long supId);
}

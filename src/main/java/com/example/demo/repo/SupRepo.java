package com.example.demo.repo;

import com.example.demo.Model.Supervisor;
import com.example.demo.Model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupRepo extends JpaRepository<Supervisor,Integer> {
//    List<Worker> findBySupervisor_SupIdAndIsActiveFalse(Long supId);
}

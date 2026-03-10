package com.example.demo.repo;

import com.example.demo.Model.Worker;
import com.example.demo.dtoUser.WorkerDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkerRepo extends JpaRepository<Worker,Integer> {
    List<Worker> findBySupervisor_SupIdAndIsActiveTrue(Long supId);
    Worker findByWorkerIDAndIsActiveTrue(Long workerId);

    List<Worker> findBySupervisor_SupIdAndIsActiveFalse(Long supId);

    Worker findByWorkerIDAndSupervisor_SupIdAndIsActiveFalse(Long workerId, Long supId);
    @Query("""
            select (workerName,workerCat
            """)
    List<WorkerDTO> findBySupervisor_SupIdAndIsActiveTrue(Long supId);
//    @Modifying
//    @Transactional
//    int deleteByWorkerIDAndSupervisor_supId(Long workerID);
}

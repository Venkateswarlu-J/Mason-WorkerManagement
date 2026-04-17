package com.example.demo.repo;

import com.example.demo.Model.Worker;
import com.example.demo.dtoUser.WorkerDTO;
import com.example.demo.enums.Categories;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkerRepo extends JpaRepository<Worker,Integer> {
//    List<Worker> findBySupervisor_SupIdAndIsActiveTrue(Long supId);
    Worker findByWorkerIDAndIsActiveTrue(Long workerId);

    List<Worker> findBySupervisor_SupIdAndIsActiveFalse(Long supId);

    Worker findByWorkerIDAndSupervisor_SupIdAndIsActiveFalse(Long workerId, Long supId);

    @Query("""
    SELECT new com.example.demo.dtoUser.WorkerDTO(
        w.workerName,
        w.workerCat,
        ''
    )
    FROM Worker w
    WHERE w.supervisor.supId = :supId
    AND w.isActive = true
    AND (:category IS NULL OR w.workerCat = :category)
    AND (:search IS NULL OR LOWER(w.workerName) LIKE LOWER(CONCAT('%',:search,'%')))
""")
    List<WorkerDTO> findWorkers(
            @Param("supId") Long supId,
            @Param("search") String search,
            @Param("category") Categories category
    );
//    @Transactional
//    int deleteByWorkerIDAndSupervisor_supId(Long workerID);
}

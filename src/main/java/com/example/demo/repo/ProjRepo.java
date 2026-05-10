package com.example.demo.repo;

import com.example.demo.Model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjRepo extends JpaRepository<Project,Integer> {
    List<Project> findAllBySupervisor_SupId(Long supId);
    Project findByProjectIdAndSupervisor_SupId(Long projectId, Long supId);

    boolean existsByProjectNameAndSupervisor_SupId(String projectName, Long supId);
}

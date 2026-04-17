package com.example.demo.repo;

import com.example.demo.Model.Supervisor;
import com.example.demo.Model.TransactionHistory;
import com.example.demo.Model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionHistoryRepo extends JpaRepository<TransactionHistory,Integer> {
    @Query("""
            select coalesce(sum(t.curPaid),0) from TransactionHistory t
            where t.supervisor=:supervisor and
            t.worker=:worker
            and t.paidAt between :start and :end
            """)
    long findPaid(
            @Param("supervisor") Supervisor supervisor,
            @Param("worker") Worker worker,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);

    List<TransactionHistory> findBySupervisor_SupId(Long supId);
}

package com.example.demo.repo;

import com.example.demo.Model.Supervisor;
import com.example.demo.Model.Worker;
import com.example.demo.Model.WorkerAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepo extends JpaRepository<WorkerAttendance,Long> {
    List<WorkerAttendance> findBySupervisor_SupIdAndAttendanceDate(Long supId, LocalDate today);
    boolean existsByWorkerAndAttendanceDate(Worker worker, LocalDate attendanceDate);

    WorkerAttendance findBySupervisor_SupIdAndWorker_WorkerIDAndAttendanceDate(Long supId, long workerId, LocalDate now);

    @Query("""
            select w from WorkerAttendance w
            where w.supervisor.supId=:supId and
            w.attendanceDate between :start and :end""")
    List<WorkerAttendance> findWorkers(
            @Param("supId") Long supId,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end);


    @Query("""
            select count(w) from WorkerAttendance w
            where w.worker=:worker and
            w.supervisor=:supervisor and
            w.attendanceStatus = com.example.demo.enums.AttendanceStatus.PRESENT and
            w.attendanceDate between :start and :end
            """)
    long findFullDays(@Param("supervisor") Supervisor supervisor,
                      @Param("worker") Worker worker,
                      @Param("start") LocalDate start,
                      @Param("end") LocalDate end);


    @Query("""
            select count(w) from WorkerAttendance w
            where w.worker=:worker and
            w.supervisor=:supervisor and
            w.attendanceStatus = com.example.demo.enums.AttendanceStatus.HALF_DAY and
            w.attendanceDate between :start and :end
            """)
    long findHalfDays(@Param("supervisor") Supervisor supervisor,
                      @Param("worker") Worker worker,
                      @Param("start") LocalDate start,
                      @Param("end") LocalDate end);
}
